package com.sabrinamidori.api.service;

import com.sabrinamidori.api.domain.entity.subject.Subject;
import com.sabrinamidori.api.domain.entity.subject.SubjectSchedule;
import com.sabrinamidori.api.domain.enums.WeekDay;
import com.sabrinamidori.api.dto.subject.CreateScheduleRequest;
import com.sabrinamidori.api.dto.subject.CreateSubjectRequest;
import com.sabrinamidori.api.dto.subject.ScheduleResponse;
import com.sabrinamidori.api.dto.subject.SubjectResponse;
import com.sabrinamidori.api.exception.DuplicateResourceException;
import com.sabrinamidori.api.repository.SubjectRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubjectService {

    private final SubjectRepository subjectRepository;

    public SubjectService(SubjectRepository subjectRepository) {
        this.subjectRepository = subjectRepository;
    }

    public SubjectResponse create(CreateSubjectRequest data) {
        if (subjectRepository.existsByTitle(data.title())) {
            throw new DuplicateResourceException(
                    "Subject with title '" + data.title() + "' already exists."
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

    private List<SubjectSchedule> buildSchedules(List<CreateScheduleRequest> schedules, Subject subject) {
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
