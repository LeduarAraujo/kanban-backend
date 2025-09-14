package com.facilit.kanban_backend.repository;

import com.facilit.kanban_backend.domain.entity.CargoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CargoRepository extends JpaRepository<CargoEntity, Long> {
}
