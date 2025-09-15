package com.facilit.kanban_backend.repository;

import com.facilit.kanban_backend.domain.entity.ItemProjetoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemProjetoRepository extends JpaRepository<ItemProjetoEntity, Long> {
    List<ItemProjetoEntity> findByProjetoId(Long projetoId);
}


