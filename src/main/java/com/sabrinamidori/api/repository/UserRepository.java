package com.sabrinamidori.api.repository;

import com.sabrinamidori.api.domain.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    @Query("""
        SELECT u
        FROM User u
        WHERE u.email = :email
    """)
    Optional<User> findByEmail(@Param("email") String email);
}
