package unit;

import com.baeldung.openapi.model.ListaProjetoResponseRepresentation;
import com.baeldung.openapi.model.ProjetoRepresentation;
import com.baeldung.openapi.model.StatusProjetoRepresentation;
import com.baeldung.openapi.model.SuccessMessageRepresentation;
import com.facilit.kanban_backend.exception.BusinessException;
import com.facilit.kanban_backend.repository.ItemProjetoRepository;
import com.facilit.kanban_backend.repository.ProjetoRepository;
import com.facilit.kanban_backend.repository.ResponsavelRepository;
import com.facilit.kanban_backend.service.ProjetoService;
import fixtures.ProjetoFixtures;
import fixtures.TokenFixtures;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static fixtures.ItemProjetoFixtures.criarItemProjetoEntity_comId;
import static fixtures.ProjetoFixtures.*;
import static fixtures.ProjetoFixtures.criarProjetoEntity_comId;
import static fixtures.ResponsavelFixtures.criarResponsavelEntity_comId;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class ProjetoServiceTest {

    @Mock
    private ProjetoRepository projetoRepository;

    @Mock
    private ResponsavelRepository responsavelRepository;

    @Mock
    private ItemProjetoRepository itemProjetoRepository;

    @InjectMocks
    private ProjetoService projetoService;

    @Mock
    private Pageable pageable;

    String accessToken;
    Long idUsuarioLogado;

    @BeforeEach
    void setUp() {
        accessToken = TokenFixtures.getAcessToken();
        idUsuarioLogado = TokenFixtures.getIdUsuarioLogado();
    }

    @Test
    public void atualizarProjeto_Sucesso() {
        when(projetoRepository.findById(Mockito.any())).thenReturn(Optional.of(criarProjetoEntity_comId()));
        when(responsavelRepository.findById(Mockito.any())).thenReturn(Optional.of(criarResponsavelEntity_comId()));
        when(projetoRepository.save(Mockito.any())).thenReturn(criarProjetoEntity_comId());

        ProjetoRepresentation retorno = projetoService.atualizarProjeto(
                1L,
                accessToken,
                idUsuarioLogado,
                criarAtualizarProjetoBodyRepresentation()
        );

        Assertions.assertNotNull(retorno);
    }

    @Test
    public void buscarProjetoPorId_Sucesso() {

        when(projetoRepository.findById(1L)).thenReturn(Optional.of(ProjetoFixtures.criarProjetoEntity_comId()));

        ProjetoRepresentation response = projetoService.buscarProjetoPorId(1L, accessToken, idUsuarioLogado);

        Assertions.assertNotNull(response);
        Assertions.assertEquals("Projeto Teste", response.getNome());
    }

    @Test
    public void cadastrarProjeto_Sucesso() {

        when(projetoRepository.save(Mockito.any())).thenReturn(ProjetoFixtures.criarProjetoEntity_comId());

        ProjetoRepresentation response = projetoService.cadastrarProjeto(accessToken, idUsuarioLogado
                , ProjetoFixtures.criarCadastrarProjetoRequestRepresentation());

        Assertions.assertNotNull(response);
        Assertions.assertEquals("Projeto Teste", response.getNome());
    }

    @Test
    public void excluirProjeto_Sucesso() {
        when(projetoRepository.findById(1L)).thenReturn(Optional.of(criarProjetoEntity_comId()));
        when(itemProjetoRepository.findByProjetoId(Mockito.any())).thenReturn(List.of(criarItemProjetoEntity_comId()));

        SuccessMessageRepresentation response = projetoService.excluirProjeto(accessToken, idUsuarioLogado, 1L);

        Assertions.assertNotNull(response);
        Assertions.assertEquals("O projeto foi excluido com sucesso!", response.getMessage());
    }

    @Test
    public void listarProjetos_Sucesso() {
        when(projetoRepository.findAll(pageable)).thenReturn(criarPageProjetoEntity());

        ListaProjetoResponseRepresentation response = projetoService.listarProjetos(accessToken
                , idUsuarioLogado, pageable);

        Assertions.assertNotNull(response);
    }

    @Test
    public void listarProjetosPorStatus_Sucesso() {
        when(projetoRepository.findByStatus(Mockito.any(), Mockito.any())).thenReturn(criarPageProjetoEntity());

        ListaProjetoResponseRepresentation response = projetoService.listarProjetosPorStatus(accessToken, idUsuarioLogado
                , StatusProjetoRepresentation.A_INICIAR, pageable);

        Assertions.assertNotNull(response);
    }

    @Test
    public void moverStatusProjeto_Sucesso() {
        when(projetoRepository.findById(1L)).thenReturn(Optional.of(criarProjetoEntity_comId()));
        when(projetoRepository.save(Mockito.any())).thenReturn(criarProjetoEntity_comId());

        ProjetoRepresentation response = projetoService.moverStatus(accessToken, idUsuarioLogado, 1L, StatusProjetoRepresentation.A_INICIAR);

        Assertions.assertNotNull(response);
    }

    // Case error

    @Test
    public void atualizarProjeto_Erro() {
        Assertions.assertThrows(BusinessException.class, () -> projetoService.atualizarProjeto(
                1L,
                accessToken,
                idUsuarioLogado,
                criarAtualizarProjetoBodyRepresentation()
        ));
    }
}
