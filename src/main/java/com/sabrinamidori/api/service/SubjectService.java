package com.sabrinamidori.api.service;

import com.sabrinamidori.api.domain.entity.subject.Subject;
import com.sabrinamidori.api.domain.entity.subject.SubjectSchedules;
import com.sabrinamidori.api.domain.enums.WeekDay;
import com.sabrinamidori.api.dto.subject.CreateSubjectRequest;
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

    public Subject createSubject(CreateSubjectRequest data) {

        if (subjectRepository.existsByTitle(data.title())) {
            throw new DuplicateResourceException(
                    "Subject with title '" + data.title() + "' already exists."
            );
        }

        Subject subject = new Subject();
        subject.setTitle(data.title());
        subject.setTeacher(data.teacher());

        List<SubjectSchedules> schedules = data.schedules().stream()
                .map(item -> {
                    WeekDay wd = WeekDay.from(item.weekDay());

                    SubjectSchedules s = new SubjectSchedules();
                    s.setWeekDay(wd);
                    s.setStartTime(item.startTime());
                    s.setEndTime(item.endTime());
                    s.setSubject(subject);
                    return s;
                })
                .toList();

        subject.getSchedules().addAll(schedules);

        return subjectRepository.save(subject);
    }
}
