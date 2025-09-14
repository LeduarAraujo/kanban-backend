package com.facilit.kanban_backend.mapper;

import com.baeldung.openapi.model.CadastrarProjetoRequestResponsavelIdInnerRepresentation;
import com.baeldung.openapi.model.ItemProjetoResponseRepresentation;
import com.baeldung.openapi.model.PrioridadeItemProjetoRepresentation;
import com.baeldung.openapi.model.StatusItemProjetoRepresentation;
import com.facilit.kanban_backend.domain.entity.ItemProjetoEntity;
import com.facilit.kanban_backend.domain.entity.ProjetoEntity;
import com.facilit.kanban_backend.domain.entity.ResponsavelEntity;
import com.facilit.kanban_backend.domain.enums.PrioridadeEnum;
import com.facilit.kanban_backend.domain.enums.StatusItemProjetoEnum;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ItemProjetoMapper {


    public static List<ItemProjetoResponseRepresentation> toRepresentationList(List<ItemProjetoEntity> pRetornoConsulta) {
        return pRetornoConsulta.stream().map(ItemProjetoMapper::toRepresentation).toList();
    }

    public static ItemProjetoResponseRepresentation toRepresentation(ItemProjetoEntity pItemProjetoEntity) {
        if (pItemProjetoEntity == null) {
            return null;
        }

        List<CadastrarProjetoRequestResponsavelIdInnerRepresentation> listResponsavel = new ArrayList<>();

        pItemProjetoEntity.getResponsavel().stream().forEach(responsavel -> {
            listResponsavel.add(
                    CadastrarProjetoRequestResponsavelIdInnerRepresentation.builder()
                            .id(responsavel.getId())
                            .build()
            );
        });

        return ItemProjetoResponseRepresentation.builder()
                .id(pItemProjetoEntity.getId())
                .projetoId(pItemProjetoEntity.getProjeto().getId())
                .titulo(pItemProjetoEntity.getTitulo())
                .descricao(pItemProjetoEntity.getDescricao())
                .responsavelId(listResponsavel)
                .statusItem(StatusItemProjetoRepresentation.valueOf(pItemProjetoEntity.getStatus().toString()))
                .prioridade(PrioridadeItemProjetoRepresentation.valueOf(pItemProjetoEntity.getPrioridade().toString()))
                .build();
    }

    public static ItemProjetoEntity toEntity(ItemProjetoResponseRepresentation pItemProjetoResponseRepresentation) {
        if (pItemProjetoResponseRepresentation == null) {
            return null;
        }

        ProjetoEntity projetoEntity = new ProjetoEntity();
        projetoEntity.setId(pItemProjetoResponseRepresentation.getProjetoId());

        Set<ResponsavelEntity> listResponsavel = new HashSet<>();

        pItemProjetoResponseRepresentation.getResponsavelId().forEach( responsavelId -> {
            ResponsavelEntity responsavelEntity = new ResponsavelEntity();
            responsavelEntity.setId(responsavelId.getId());
            listResponsavel.add(responsavelEntity);
        });

        ItemProjetoEntity itemProjetoEntity = new ItemProjetoEntity();
        itemProjetoEntity.setId(pItemProjetoResponseRepresentation.getId());
        itemProjetoEntity.setTitulo(pItemProjetoResponseRepresentation.getTitulo());
        itemProjetoEntity.setDescricao(pItemProjetoResponseRepresentation.getDescricao());
        itemProjetoEntity.setStatus(StatusItemProjetoEnum.valueOf(pItemProjetoResponseRepresentation.getStatusItem().toString()));
        itemProjetoEntity.setPrioridade(PrioridadeEnum.valueOf(pItemProjetoResponseRepresentation.getPrioridade().toString()));
        itemProjetoEntity.setResponsavel(listResponsavel);
        itemProjetoEntity.setProjeto(projetoEntity);

        return itemProjetoEntity;
    }
}
