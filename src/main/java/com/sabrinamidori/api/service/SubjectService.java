package com.sabrinamidori.api.service;

import com.sabrinamidori.api.domain.entity.subject.Subject;
import com.sabrinamidori.api.domain.entity.subject.SubjectSchedule;
import com.sabrinamidori.api.domain.entity.subject.Task;
import com.sabrinamidori.api.domain.enums.Period;
import com.sabrinamidori.api.domain.enums.TaskStatus;
import com.sabrinamidori.api.domain.enums.WeekDay;
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
        return toSubjectResponse(setData(subject, data));
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

        return toSubjectResponse(setData(subject, data));
    }

    public void deleteSubject(UUID id) {
        Subject subject = subjectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Subject with id " + id + " not found"
                ));

        subjectRepository.delete(subject);
    }

    public TaskResponse createTask(TaskRequest data) {
        Task task = new Task();
        task.setTaskStatus(TaskStatus.valueOf(data.status()));
        task.setDescription(data.description());
        task.setDueDateTime(data.dueDatetime());

        String subjectTitle = normalize(data.subject());

        Subject subject = subjectRepository
                .findByNormalizedTitle(subjectTitle)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Subject with title " + subjectTitle + " not found"
                ));

        task.setSubject(subject);

        try {
            subject.getTasks().add(task);
        } catch (Exception e) {
            System.out.println("Could not add task: " + e);
        };

        return toTaskResponse(task);
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

    private TaskResponse toTaskResponse(Task task) {
        return new TaskResponse(
                task.getId(),
                task.getTaskStatus(),
                task.getDescription(),
                task.getDueDateTime()
        );
    }

    private Subject setData(Subject subject, SubjectRequest data) {
        subject.setTitle(data.title());
        subject.setProfessor(data.professor());

        List<SubjectSchedule> schedules = buildSchedules(data.schedules(), subject);

        subject.getSchedules().clear();
        subject.getSchedules().addAll(schedules);

        return subjectRepository.save(subject);
    }
}
