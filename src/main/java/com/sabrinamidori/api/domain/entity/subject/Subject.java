package com.sabrinamidori.api.domain.entity.subject;

import com.sabrinamidori.api.domain.entity.schedule.Schedule;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.sabrinamidori.api.domain.util.TextNormalizer.normalize;

@Entity
@Table(name = "subjects")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Subject {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, unique = true)
    private String normalizedTitle;

    @Column(nullable = false)
    private String professor;

    @OneToMany(mappedBy = "subject", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Schedule> schedules = new ArrayList<>();

    @PrePersist
    @PreUpdate
    private void normalizeTitle() {
        this.normalizedTitle = normalize(this.title);
    }
}
