package com.facilit.kanban_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.facilit.kanban_backend.domain.Projeto;
import com.facilit.kanban_backend.domain.ProjetoStatus;

import java.util.List;
import java.util.UUID;

public interface ProjetoRepository extends JpaRepository<Projeto, UUID> {
    List<Projeto> findByStatus(ProjetoStatus status);
}
