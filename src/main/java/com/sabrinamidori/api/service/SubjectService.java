package com.sabrinamidori.api.service;

import com.sabrinamidori.api.domain.entity.subject.Subject;
import com.sabrinamidori.api.domain.entity.schedule.Schedule;
import com.sabrinamidori.api.domain.enums.Period;
import com.sabrinamidori.api.domain.enums.WeekDay;
import com.sabrinamidori.api.dto.schedule.ScheduleRequest;
import com.sabrinamidori.api.dto.schedule.ScheduleResponse;
import com.sabrinamidori.api.dto.subject.*;
import com.sabrinamidori.api.exception.DuplicateResourceException;
import com.sabrinamidori.api.exception.ResourceNotFoundException;
import com.sabrinamidori.api.repository.SubjectRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static com.sabrinamidori.api.domain.util.TextNormalizer.normalize;

@Service
public class SubjectService {

    private final SubjectRepository subjectRepository;

    public SubjectService(SubjectRepository subjectRepository) {
        this.subjectRepository = subjectRepository;
    }

    public SubjectResponse createSubject(SubjectRequest data) {
        validateUniqueTitle(data.title(), null);

        Subject subject = new Subject();
        setData(subject, data);

        Subject saved = subjectRepository.save(subject);
        return toSubjectResponse(saved);
    }

    public List<SubjectResponse> getSubjects() {
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

    public SubjectResponse updateSubject(UUID id, SubjectRequest data) {
        Subject subject = subjectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Subject with id " + id + " not found"
                ));

        validateUniqueTitle(data.title(), id);

        setData(subject, data);

        Subject saved = subjectRepository.save(subject);
        return toSubjectResponse(saved);
    }

    public void deleteSubject(UUID id) {
        Subject subject = subjectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Subject with id " + id + " not found"
                ));

        subjectRepository.delete(subject);
    }

    private void validateUniqueTitle(String title, UUID id) {
        String normalized = normalize(title);

        boolean exists = (id == null)
                ? subjectRepository.existsByNormalizedTitle(normalized)
                : subjectRepository.existsByNormalizedTitleAndIdNot(normalized, id);

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
                    WeekDay weekDay = WeekDay.from(item.weekDay());
                    Period period = Period.from(item.period());

                    Schedule schedule = new Schedule();
                    schedule.setWeekDay(weekDay);
                    schedule.setPeriod(period);
                    schedule.setSubject(subject);

                    return schedule;
                })
                .toList();
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

    private void setData(Subject subject, SubjectRequest data) {
        subject.setTitle(data.title());
        subject.setProfessor(data.professor());

        List<Schedule> schedules = buildSchedules(data.schedules(), subject);

        subject.getSchedules().clear();
        subject.getSchedules().addAll(schedules);
    }
}
