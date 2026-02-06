package com.sabrinamidori.api.service;

import com.sabrinamidori.api.domain.entity.subject.Subject;
import com.sabrinamidori.api.domain.entity.schedule.Schedule;
import com.sabrinamidori.api.dto.schedule.ScheduleRequest;
import com.sabrinamidori.api.dto.schedule.ScheduleResponse;
import com.sabrinamidori.api.dto.subject.*;
import com.sabrinamidori.api.exception.DuplicateResourceException;
import com.sabrinamidori.api.exception.ResourceNotFoundException;
import com.sabrinamidori.api.repository.ScheduleRepository;
import com.sabrinamidori.api.repository.SubjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import static com.sabrinamidori.api.domain.util.TextNormalizer.normalize;

@Service
@RequiredArgsConstructor
public class SubjectService {

    private final SubjectRepository subjectRepository;
    private final ScheduleRepository scheduleRepository;

    public SubjectResponse createSubject(SubjectRequest data) {
        validateUniqueTitle(data.title(), null);

        Subject subject = new Subject();
        subject.setTitle(data.title());
        subject.setProfessor(data.professor());

        List<Schedule> schedules = buildSchedules(data.schedules(), subject);
        validateUniqueSchedule(schedules, null);

        replaceSchedules(subject, schedules);

        Subject saved = subjectRepository.save(subject);
        return toSubjectResponse(saved);
    }

    public List<SubjectResponse> getAllSubjects() {
        return subjectRepository.findAll()
                .stream()
                .map(this::toSubjectResponse)
                .toList();
    }

    public SubjectResponse getSubjectByTitle(String title) {
        String normalized = normalize(title);

        Subject subject = subjectRepository.findByNormalizedTitle(normalized)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Subject with title " + title + " not found"
                ));

        return toSubjectResponse(subject);
    }

    public SubjectResponse updateSubject(UUID subjectId, SubjectRequest data) {
        Subject subject = subjectRepository.findById(subjectId)
                .orElseThrow(() -> new ResourceNotFoundException(
                    "Subject with id " + subjectId + " not found"
                ));

        if (data.title() != null) {
            validateUniqueTitle(data.title(), subjectId);
            subject.setTitle(data.title());
        }

        if (data.professor() != null) {
            subject.setProfessor(data.professor());
        }

        if (data.schedules() != null) {
            List<Schedule> schedules = buildSchedules(data.schedules(), subject);
            validateUniqueSchedule(schedules, subject.getId());
            replaceSchedules(subject, schedules);
        }

        Subject saved = subjectRepository.save(subject);
        return toSubjectResponse(saved);
    }

    public void deleteSubject(UUID subjectId) {
        Subject subject = subjectRepository.findById(subjectId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Subject with id " + subjectId + " not found"
                ));

        subjectRepository.delete(subject);
    }

    private void validateUniqueTitle(String title, UUID subjectId) {
        String normalized = normalize(title);

        boolean exists = (subjectId == null)
                ? subjectRepository.existsByNormalizedTitle(normalized)
                : subjectRepository.existsByNormalizedTitleAndIdNot(normalized, subjectId);

        if (exists) {
            throw new DuplicateResourceException(
                "Subject with title '" + title + "' already exists"
            );
        }
    }

    private List<Schedule> buildSchedules(List<ScheduleRequest> schedules, Subject subject) {
        if (schedules == null || schedules.isEmpty()) {
            return List.of();
        }

        return schedules.stream()
                .map(item -> {
                    Schedule schedule = new Schedule();
                    schedule.setWeekDay(item.weekDay());
                    schedule.setPeriod(item.period());
                    schedule.setSubject(subject);
                    return schedule;
                })
                .toList();
}

    private void validateUniqueSchedule(List<Schedule> schedules, UUID subjectId) {
        Set<String> seen = new HashSet<>();

        for (Schedule schedule : schedules) {
            String key = schedule.getWeekDay() + ":" + schedule.getPeriod();

            if (!seen.add(key)) {
                throw new DuplicateResourceException(
                    "Duplicate schedule in request: " +
                    schedule.getWeekDay() + " " + schedule.getPeriod()
                );
            }
        }

        for (Schedule schedule : schedules) {
            boolean exists = (subjectId == null)
                    ? scheduleRepository.existsByWeekDayAndPeriod(
                        schedule.getWeekDay(),
                        schedule.getPeriod())
                    : scheduleRepository.existsByWeekDayAndPeriodAndSubjectNot(
                        schedule.getWeekDay(),
                        schedule.getPeriod(),
                        subjectId
                    );

            if (exists) {
                throw new DuplicateResourceException(
                    "Schedule already taken: " +
                    schedule.getWeekDay() + " " + schedule.getPeriod()
                );
            }
        }
    }

    private SubjectResponse toSubjectResponse(Subject subject) {
        return new SubjectResponse(
                subject.getId(),
                subject.getTitle(),
                subject.getProfessor(),
                subject.getSchedules()
                        .stream()
                        .map(this::toScheduleResponse)
                        .toList()
        );
    }

    private ScheduleResponse toScheduleResponse(Schedule schedule) {
        return new ScheduleResponse(
                schedule.getWeekDay(),
                schedule.getPeriod()
        );
    }

    private void replaceSchedules(Subject subject, List<Schedule> schedules) {
        subject.getSchedules().clear();
        subject.getSchedules().addAll(schedules);
    }
}
