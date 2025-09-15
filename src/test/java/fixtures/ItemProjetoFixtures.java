package fixtures;

import com.baeldung.openapi.model.CadastrarProjetoRequestResponsavelIdInnerRepresentation;
import com.baeldung.openapi.model.ItemProjetoResponseRepresentation;
import com.baeldung.openapi.model.PrioridadeItemProjetoRepresentation;
import com.baeldung.openapi.model.StatusItemProjetoRepresentation;
import com.facilit.kanban_backend.domain.entity.ItemProjetoEntity;
import com.facilit.kanban_backend.domain.enums.PrioridadeEnum;
import com.facilit.kanban_backend.domain.enums.StatusItemProjetoEnum;

import java.util.List;


public class ItemProjetoFixtures {
    public static ItemProjetoEntity criarItemProjetoEntity_comId() {
        ItemProjetoEntity item = new ItemProjetoEntity();
        item.setId(1L);
        item.setProjeto(ProjetoFixtures.criarProjetoEntity_comId());
        item.setTitulo("Item de Projeto Teste");
        item.setDescricao("Descrição do item de projeto teste");
        item.setPrioridade(PrioridadeEnum.MEDIA);
        item.setStatus(StatusItemProjetoEnum.A_FAZER);
        return item;
    }

    public static ItemProjetoResponseRepresentation criarItemProjetoResponseRepresentation() {
        return ItemProjetoResponseRepresentation.builder()
                .id(1L)
                .projetoId(ProjetoFixtures.criarProjetoEntity_comId().getId())
                .responsavelId(List.of(CadastrarProjetoRequestResponsavelIdInnerRepresentation.builder()
                        .id(1L).build()))
                .titulo("Item de Projeto Teste")
                .descricao("Descrição do item de projeto teste")
                .prioridade(PrioridadeItemProjetoRepresentation.MEDIA)
                .statusItem(StatusItemProjetoRepresentation.A_FAZER)
                .build();
    }



}
