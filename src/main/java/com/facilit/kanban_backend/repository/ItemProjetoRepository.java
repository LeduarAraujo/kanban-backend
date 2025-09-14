package com.facilit.kanban_backend.repository;

import com.facilit.kanban_backend.domain.entity.ItemProjetoEntity;
import com.facilit.kanban_backend.domain.entity.ProjetoEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemProjetoRepository extends JpaRepository<ItemProjetoEntity, Long> {
    Page<ItemProjetoEntity> findByProjeto(ProjetoEntity projeto, Pageable pageable);
    Page<ItemProjetoEntity> findByStatus(StatusItemProjetoEntity status, Pageable pageable);
    List<ItemProjetoEntity> findByProjetoId(Long projetoId);
}


