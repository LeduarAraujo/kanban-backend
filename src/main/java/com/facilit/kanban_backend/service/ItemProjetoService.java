package com.facilit.kanban_backend.service;

import com.baeldung.openapi.model.ItemProjetoResponseRepresentation;
import com.baeldung.openapi.model.SuccessMessageRepresentation;
import com.facilit.kanban_backend.domain.entity.HistoricoItemProjetoEntity;
import com.facilit.kanban_backend.domain.entity.ItemProjetoEntity;
import com.facilit.kanban_backend.domain.entity.ResponsavelEntity;
import com.facilit.kanban_backend.domain.enums.PrioridadeEnum;
import com.facilit.kanban_backend.domain.enums.StatusItemProjetoEnum;
import com.facilit.kanban_backend.mapper.HistoricoItemProjetoMapper;
import com.facilit.kanban_backend.mapper.ItemProjetoMapper;
import com.facilit.kanban_backend.repository.ItemProjetoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ItemProjetoService {

    private final ItemProjetoRepository itemProjetoRepository;
    private final HistoricoItemProjetoService historicoItemProjetoService;

    @Transactional(rollbackFor = Exception.class)
    public ItemProjetoResponseRepresentation incluirItemProjeto(ItemProjetoResponseRepresentation pItemProjetoResponseRepresentation) {
        ItemProjetoEntity retornoInclusao = itemProjetoRepository.save(ItemProjetoMapper
                .toEntity(pItemProjetoResponseRepresentation));

        return ItemProjetoMapper.toRepresentation(retornoInclusao);
    }

    @Transactional(rollbackFor = Exception.class)
    public SuccessMessageRepresentation excluir(Long pIdItemProjeto) {
        ItemProjetoEntity itemProjetoEntity = itemProjetoRepository.findById(pIdItemProjeto).get();
        this.gravarHistorico(itemProjetoEntity);

        itemProjetoRepository.deleteById(pIdItemProjeto);
        return SuccessMessageRepresentation.builder().message("Item de projeto excluído com sucesso").build();
    }

    public List<ItemProjetoResponseRepresentation> listarPorProjeto(Long pIdItemProjeto) {
        List<ItemProjetoEntity> retornoConsulta = itemProjetoRepository.findByProjetoId(pIdItemProjeto);
        return ItemProjetoMapper.toRepresentationList(retornoConsulta);
    }

    @Transactional(rollbackFor = Exception.class)
    public ItemProjetoResponseRepresentation atualizarItemProjeto(ItemProjetoResponseRepresentation pItemProjetoResponseRepresentation) {
        ItemProjetoEntity itemProjetoEntity = itemProjetoRepository.findById(pItemProjetoResponseRepresentation.getId()).get();
        this.gravarHistorico(itemProjetoEntity);

        itemProjetoEntity.setTitulo(pItemProjetoResponseRepresentation.getTitulo());
        itemProjetoEntity.setDescricao(pItemProjetoResponseRepresentation.getDescricao());
        itemProjetoEntity.setStatus(StatusItemProjetoEnum.valueOf(pItemProjetoResponseRepresentation.getStatusItem().toString()));
        itemProjetoEntity.setPrioridade(PrioridadeEnum.valueOf(pItemProjetoResponseRepresentation.getPrioridade().toString()));

        Set<ResponsavelEntity> listResponsavel = new HashSet<>();
        pItemProjetoResponseRepresentation.getResponsavelId().forEach( responsavelId -> {
            ResponsavelEntity responsavelEntity = new ResponsavelEntity();
            responsavelEntity.setId(responsavelId.getId());
            listResponsavel.add(responsavelEntity);
        });

        itemProjetoEntity.setResponsaveis(listResponsavel);

        return null;
    }

    private void gravarHistorico(ItemProjetoEntity pItemProjetoEntity) {
        HistoricoItemProjetoEntity historicoItemProjetoEntity = HistoricoItemProjetoMapper
                .entityAtualToEntityHistorico(pItemProjetoEntity);
        historicoItemProjetoService.salvar(historicoItemProjetoEntity);
    }
}


