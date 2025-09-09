package com.facilit.kanban_backend.repository;

import com.facilit.kanban_backend.domain.entity.Responsavel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ResponsavelRepository extends JpaRepository<Responsavel, UUID> {
    Optional<Responsavel> findByEmail(String email);

    boolean existsByEmail(String email);
}
