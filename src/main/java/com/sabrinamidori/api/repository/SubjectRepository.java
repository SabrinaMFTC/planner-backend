package com.sabrinamidori.api.repository;

import com.sabrinamidori.api.domain.entity.subject.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface SubjectRepository extends JpaRepository<Subject, UUID> {

    @Query("""
            SELECT (count(s) > 0)
            FROM Subject s
            WHERE lower(s.title) = lower(:title)
    """)
    boolean existsByTitle(@Param("title") String title);

    @Query("""
            SELECT s
            FROM Subject s
            WHERE s.normalizedTitle = :normalizedTitle
    """)
    Optional<Subject> findByNormalizedTitle(@Param("normalizedTitle") String normalizedTitle);

    @Query("""
            SELECT (count(s) > 0)
            FROM Subject s
            WHERE s.normalizedTitle = :normalizedTitle
    """)
    boolean existsByNormalizedTitle(@Param("normalizedTitle") String normalizedTitle);

}
