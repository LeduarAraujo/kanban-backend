package com.facilit.kanban_backend.service;

import com.facilit.kanban_backend.repository.StatusItemProjetoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StatusItemProjetoService {

    private final StatusItemProjetoRepository statusRepository;

    public Optional<StatusItemProjetoEntity> buscarPorNome(String nome) {
        return statusRepository.findByNome(nome);
    }

    public StatusItemProjetoEntity salvar(StatusItemProjetoEntity entity) {
        return statusRepository.save(entity);
    }
}


