package com.facilit.kanban_backend.repository;


import com.facilit.kanban_backend.domain.entity.ProjetoResponsavelEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjetoResponsavelRepository extends JpaRepository<ProjetoResponsavelEntity, Long> {

    List<ProjetoResponsavelEntity> findByProjetoId(Long projetoId);
}
