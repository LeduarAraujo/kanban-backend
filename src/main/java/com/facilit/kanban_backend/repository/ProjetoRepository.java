package com.facilit.kanban_backend.repository;

import com.facilit.kanban_backend.domain.entity.ProjetoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import com.facilit.kanban_backend.domain.enums.ProjetoStatusEnum;

import java.util.List;

public interface ProjetoRepository extends JpaRepository<ProjetoEntity, Long> {
    List<ProjetoEntity> findByStatus(ProjetoStatusEnum status);
}
