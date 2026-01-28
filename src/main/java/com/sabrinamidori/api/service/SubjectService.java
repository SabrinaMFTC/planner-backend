package com.sabrinamidori.api.service;

import com.sabrinamidori.api.domain.entity.subject.Subject;
import com.sabrinamidori.api.domain.entity.subject.SubjectSchedule;
import com.sabrinamidori.api.domain.enums.Period;
import com.sabrinamidori.api.domain.enums.WeekDay;
import com.sabrinamidori.api.dto.subject.ScheduleRequest;
import com.sabrinamidori.api.dto.subject.SubjectRequest;
import com.sabrinamidori.api.dto.subject.ScheduleResponse;
import com.sabrinamidori.api.dto.subject.SubjectResponse;
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
        String normalized = normalize(data.title());

        if (subjectRepository.existsByNormalizedTitle(normalized)) {
            throw new DuplicateResourceException(
                    "Subject with title '" + data.title() + "' already exists"
            );
        }

        Subject subject = new Subject();
        return setData(subject, data);
    }

    public List<SubjectResponse> findSubjects() {
        return subjectRepository.findAll()
                .stream()
                .map(this::toSubjectResponse)
                .toList();
    }

    public SubjectResponse findSubjectByTitle(String title) {
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

        String normalized = normalize(data.title());

        if (subjectRepository.existsByNormalizedTitleAndIdNot(normalized, id)) {
            throw new DuplicateResourceException(
                    "Subject with title '" + data.title() + "' already exists"
            );
        }

        return setData(subject, data);
    }

    public void deleteSubject(UUID id) {
        Subject subject = subjectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Subject with id " + id + " not found"
                ));

        subjectRepository.delete(subject);
    }

    private List<SubjectSchedule> buildSchedules(List<ScheduleRequest> schedules, Subject subject) {
        if (schedules == null || schedules.isEmpty()) {
            return List.of();
        }

        return schedules.stream()
                .map(item -> {
                    WeekDay weekDay = WeekDay.from(item.weekDay());
                    Period period = Period.from(item.period());

                    SubjectSchedule schedule = new SubjectSchedule();
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

    private ScheduleResponse toScheduleResponse(SubjectSchedule schedule) {
        return new ScheduleResponse(
                schedule.getWeekDay(),
                schedule.getPeriod()
        );
    }

    private SubjectResponse setData(Subject subject, SubjectRequest data) {
        subject.setTitle(data.title());
        subject.setProfessor(data.professor());

        List<SubjectSchedule> schedules = buildSchedules(data.schedules(), subject);

        subject.getSchedules().clear();
        subject.getSchedules().addAll(schedules);

        Subject saved = subjectRepository.save(subject);
        return toSubjectResponse(saved);
    }
}
