package com.facilit.kanban_backend.mapper;

import com.facilit.kanban_backend.domain.entity.HistoricoItemProjetoEntity;
import com.facilit.kanban_backend.domain.entity.ItemProjetoEntity;

import java.util.stream.Collectors;

public class HistoricoItemProjetoMapper {

    public static HistoricoItemProjetoEntity entityAtualToEntityHistorico(ItemProjetoEntity pItemProjetoEntity) {
        HistoricoItemProjetoEntity historicoItemProjetoEntity = new HistoricoItemProjetoEntity();

        historicoItemProjetoEntity.setId(pItemProjetoEntity.getId());
        historicoItemProjetoEntity.setTitulo(pItemProjetoEntity.getTitulo());
        historicoItemProjetoEntity.setDescricao(pItemProjetoEntity.getDescricao());
        historicoItemProjetoEntity.setStatus(pItemProjetoEntity.getStatus());
        historicoItemProjetoEntity.setPrioridade(pItemProjetoEntity.getPrioridade());
        historicoItemProjetoEntity.setProjeto(pItemProjetoEntity.getProjeto().getId());
        historicoItemProjetoEntity.setResponsavel(pItemProjetoEntity.getResponsavel().stream()
                .map(responsavelEntity -> String.valueOf(responsavelEntity.getId()))
                .collect(Collectors.joining(";")));

        return historicoItemProjetoEntity;
    }
}
