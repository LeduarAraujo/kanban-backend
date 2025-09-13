package com.facilit.kanban_backend.service;

import com.facilit.kanban_backend.domain.entity.ItemProjetoEntity;
import com.facilit.kanban_backend.domain.entity.ProjetoEntity;
import com.facilit.kanban_backend.domain.entity.StatusItemProjetoEntity;
import com.facilit.kanban_backend.repository.ItemProjetoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ItemProjetoService {

    private final ItemProjetoRepository itemProjetoRepository;

    public ItemProjetoEntity salvar(ItemProjetoEntity entity) {
        return itemProjetoRepository.save(entity);
    }

    public Optional<ItemProjetoEntity> buscarPorId(Long id) {
        return itemProjetoRepository.findById(id);
    }

    public void excluir(Long id) {
        itemProjetoRepository.deleteById(id);
    }

    public Page<ItemProjetoEntity> listarPorProjeto(ProjetoEntity projeto, Pageable pageable) {
        return itemProjetoRepository.findByProjeto(projeto, pageable);
    }

    public Page<ItemProjetoEntity> listarPorStatus(StatusItemProjetoEntity status, Pageable pageable) {
        return itemProjetoRepository.findByStatus(status, pageable);
    }
}


