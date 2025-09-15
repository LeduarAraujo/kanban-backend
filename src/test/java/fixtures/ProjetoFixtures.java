package fixtures;

import com.baeldung.openapi.model.AtualizarProjetoRequestRepresentation;
import com.baeldung.openapi.model.CadastrarProjetoRequestRepresentation;
import com.baeldung.openapi.model.StatusProjetoRepresentation;
import com.facilit.kanban_backend.domain.entity.ProjetoEntity;
import com.facilit.kanban_backend.domain.enums.StatusProjetoEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDate;
import java.util.List;

public class ProjetoFixtures {

    public static ProjetoEntity criarProjetoEntity_comId() {
        ProjetoEntity projeto = new ProjetoEntity();
        projeto.setId(1L);
        projeto.setNome("Projeto Teste");
        projeto.setDescricao("Descrição do Projeto Teste");
        projeto.setStatus(StatusProjetoEnum.A_INICIAR); // Defina o status conforme necessário
        projeto.setDiasAtraso(5); // Exemplo de dias de atraso
        return projeto;
    }

    public static Page<ProjetoEntity> criarPageProjetoEntity() {
        return new PageImpl<>(
                List.of(criarProjetoEntity_comId()),
                PageRequest.of(0, 10),
                1
        );
    }

    public static AtualizarProjetoRequestRepresentation criarAtualizarProjetoBodyRepresentation() {
        return AtualizarProjetoRequestRepresentation.builder()
                .nome("Projeto Teste")
                .descricao("Descrição do Projeto Teste")
                .status(StatusProjetoRepresentation.A_INICIAR)
                .percentualTempoRestante(50f)
                .diasAtraso(0)
                .responsavelId(List.of(1L))
                .dtInicioPrevisto(LocalDate.now())
                .dtTerminoPrevisto(LocalDate.now())
                .dtInicioRealizado(LocalDate.now())
                .dtTerminoRealizado(LocalDate.now())
                .build();
    }

    public static CadastrarProjetoRequestRepresentation criarCadastrarProjetoRequestRepresentation() {
        CadastrarProjetoRequestRepresentation request = new CadastrarProjetoRequestRepresentation();
        request.setNome("Projeto Teste");
        request.setDescricao("Descrição do Projeto Teste");
        request.setStatus(StatusProjetoRepresentation.A_INICIAR);
        request.setDtInicioPrevisto(LocalDate.now().plusDays(1));
        request.setDtTerminoPrevisto(LocalDate.now().plusDays(10));
        return request;
    }

}
