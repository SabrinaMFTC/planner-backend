package com.sabrinamidori.api.service;

import com.sabrinamidori.api.domain.entity.subject.Subject;
import com.sabrinamidori.api.domain.entity.subject.SubjectSchedule;
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

    public SubjectResponse create(SubjectRequest data) {
        String normalized = normalize(data.title());

        if (subjectRepository.existsByNormalizedTitle(normalized)) {
            throw new DuplicateResourceException(
                    "Subject with title '" + data.title() + "' already exists"
            );
        }

        Subject subject = new Subject();
        subject.setTitle(data.title());
        subject.setProfessor(data.professor());

        List<SubjectSchedule> schedules = buildSchedules(data.schedules(), subject);
        subject.getSchedules().addAll(schedules);

        Subject saved = subjectRepository.save(subject);
        return toSubjectResponse(saved);
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

    public void delete(UUID id) {
        Subject subject = subjectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Subject with id " + id + " not found"
                ));

        subjectRepository.delete(subject);
    }

    private List<SubjectSchedule> buildSchedules(List<ScheduleRequest> schedules, Subject subject) {
        return schedules
                .stream()
                .map(item -> {
                    WeekDay weekDay = WeekDay.from(item.weekDay());

                    SubjectSchedule schedule = new SubjectSchedule();
                    schedule.setWeekDay(weekDay);
                    schedule.setStartTime(item.startTime());
                    schedule.setEndTime(item.endTime());
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
                schedule.getStartTime(),
                schedule.getEndTime()
        );
    }
}
