package com.sabrinamidori.api.domain.entity.schedule;

import com.sabrinamidori.api.domain.entity.subject.Subject;
import com.sabrinamidori.api.domain.enums.Period;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.DayOfWeek;
import java.util.UUID;

@Entity
@Table(
    name = "subject_schedules",
    uniqueConstraints = {
        @UniqueConstraint(
            name = "uk_subject_schedules_weekday_period",
            columnNames = {"week_day", "period"}
        )
    }
)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Enumerated(EnumType.STRING)
    @Column(name = "week_day", nullable = false)
    private DayOfWeek weekDay;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Period period;

    @ManyToOne(optional = false)
    @JoinColumn(name = "subject_id", nullable = false)
    private Subject subject;
}
