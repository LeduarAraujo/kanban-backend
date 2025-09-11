package com.facilit.kanban_backend.service;

import com.facilit.kanban_backend.domain.entity.HistoricoItemProjetoEntity;
import com.facilit.kanban_backend.domain.entity.ProjetoEntity;
import com.facilit.kanban_backend.repository.HistoricoItemProjetoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HistoricoItemProjetoService {

    private final HistoricoItemProjetoRepository historicoRepository;

    public HistoricoItemProjetoEntity salvar(HistoricoItemProjetoEntity entity) {
        return historicoRepository.save(entity);
    }

    public Page<HistoricoItemProjetoEntity> listarPorProjeto(ProjetoEntity projeto, Pageable pageable) {
        return historicoRepository.findByProjeto(projeto, pageable);
    }
}


