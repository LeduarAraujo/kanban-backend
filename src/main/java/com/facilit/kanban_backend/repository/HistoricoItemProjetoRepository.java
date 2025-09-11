package com.facilit.kanban_backend.repository;

import com.facilit.kanban_backend.domain.entity.HistoricoItemProjetoEntity;
import com.facilit.kanban_backend.domain.entity.ProjetoEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HistoricoItemProjetoRepository extends JpaRepository<HistoricoItemProjetoEntity, Long> {
    Page<HistoricoItemProjetoEntity> findByProjeto(ProjetoEntity projeto, Pageable pageable);
}


