package com.facilit.kanban_backend.service;

import com.baeldung.openapi.model.ItemProjetoResponseRepresentation;
import com.facilit.kanban_backend.domain.entity.ItemProjetoEntity;
import com.facilit.kanban_backend.mapper.ItemProjetoMapper;
import com.facilit.kanban_backend.repository.ItemProjetoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ItemProjetoService {

    private final ItemProjetoRepository itemProjetoRepository;

    public ItemProjetoResponseRepresentation incluirItemProjeto(ItemProjetoResponseRepresentation pItemProjetoResponseRepresentation) {
        ItemProjetoEntity retornoInclusao=  itemProjetoRepository.save(ItemProjetoMapper
                .toEntity(pItemProjetoResponseRepresentation));

        return ItemProjetoMapper.toRepresentation(retornoInclusao);
    }

    public Optional<ItemProjetoEntity> buscarPorId(Long id) {
        return itemProjetoRepository.findById(id);
    }

    public void excluir(Long id) {
        itemProjetoRepository.deleteById(id);
    }

    public List<ItemProjetoResponseRepresentation> listarPorProjeto(Long pIdItemProjeto) {
        List<ItemProjetoEntity> retornoConsulta = itemProjetoRepository.findByProjetoId(pIdItemProjeto);
        return ItemProjetoMapper.toRepresentationList(retornoConsulta);
    }
}


