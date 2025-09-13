package com.facilit.kanban_backend.repository;

import com.facilit.kanban_backend.domain.entity.SecretariaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SecretariaRepository extends JpaRepository<SecretariaEntity, Long> {

    @Override
    Optional<SecretariaEntity> findById(Long aLong);
}
