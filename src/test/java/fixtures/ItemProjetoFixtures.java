package fixtures;

import com.facilit.kanban_backend.domain.entity.ItemProjetoEntity;
import com.facilit.kanban_backend.domain.enums.PrioridadeEnum;
import com.facilit.kanban_backend.domain.enums.StatusItemProjetoEnum;


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

}
