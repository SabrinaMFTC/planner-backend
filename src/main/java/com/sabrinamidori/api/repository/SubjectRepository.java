package com.sabrinamidori.api.repository;

import com.sabrinamidori.api.domain.entity.subject.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface SubjectRepository extends JpaRepository<Subject, UUID> {

    @Query("""
            SELECT (count(s) > 0)
            FROM Subject s
            WHERE lower(s.title) = lower(:title)
    """)
    boolean existsByTitle(@Param("title") String title);
}
