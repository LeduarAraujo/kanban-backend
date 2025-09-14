package com.facilit.kanban_backend.repository;

import com.facilit.kanban_backend.domain.entity.ResponsavelEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResponsavelRepository extends JpaRepository<ResponsavelEntity, Long> {
}
