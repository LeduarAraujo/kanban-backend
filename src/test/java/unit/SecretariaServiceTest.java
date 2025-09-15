package unit;

import com.baeldung.openapi.model.SecretariaRepresentation;
import com.baeldung.openapi.model.SuccessMessageRepresentation;
import com.facilit.kanban_backend.exception.BusinessException;
import com.facilit.kanban_backend.repository.SecretariaRepository;
import com.facilit.kanban_backend.service.SecretariaService;
import fixtures.SecretariaFixtures;
import fixtures.TokenFixtures;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class SecretariaServiceTest {

    @Mock
    private SecretariaRepository secretariaRepository;

    @InjectMocks
    private SecretariaService secretariaService;

    String accessToken;
    Long idUsuarioLogado;

    @BeforeEach
    void setup() {
        accessToken = TokenFixtures.getAcessToken();
        idUsuarioLogado = TokenFixtures.getIdUsuarioLogado();
    }

    @Test
    public void atualizarSecretariaPorId_Sucesso() {
        when(secretariaRepository.findById(1L))
                .thenReturn(java.util.Optional.of(SecretariaFixtures.criarSecretariaEntity_comId()));

        SuccessMessageRepresentation retorno= secretariaService.atualizarSecretariaPorId( accessToken, idUsuarioLogado, 1L
                , SecretariaFixtures.criarCadastroSecretariaRequestRepresentation());

        Assertions.assertNotNull(retorno);
    }

    @Test
    public void buscarSecretariaPorId_Sucesso() {
        when(secretariaRepository.findById(1L))
                .thenReturn(java.util.Optional.of(SecretariaFixtures.criarSecretariaEntity_comId()));

        SecretariaRepresentation retorno = secretariaService.buscarSecretariaPorId( accessToken
                , idUsuarioLogado, 1L);
        Assertions.assertEquals(
                SecretariaFixtures.criarSecretariaEntity_comId().getId(),
                retorno.getId()
        );
    }

    @Test
    public void cadastrarSecretaria_Sucesso() {
        SuccessMessageRepresentation retorno = secretariaService.cadastrarSecretaria(accessToken, idUsuarioLogado
                , SecretariaFixtures.criarCadastroSecretariaRequestRepresentation());

        Assertions.assertNotNull(retorno);
    }

    @Test
    public void excluirSecretaria_Sucesso() {
        when(secretariaRepository.findById(1L))
                .thenReturn(java.util.Optional.of(SecretariaFixtures.criarSecretariaEntity_comId()));

        SuccessMessageRepresentation retorno = secretariaService.excluirSecretaria(accessToken
                , idUsuarioLogado, 1L);

        Assertions.assertNotNull(retorno);
    }

    @Test
    public void listarSecretarias_Sucesso() {
        when(secretariaRepository.findAll())
                .thenReturn(List.of(SecretariaFixtures.criarSecretariaEntity_comId()));

        List<SecretariaRepresentation> retorno= secretariaService.listarSecretarias(
                accessToken, idUsuarioLogado
        );

        Assertions.assertTrue(retorno.size() > 0);
    }

    // Erro cases

    @Test
    public void atualizarSecretariaPorId_Erro() {
        Assertions.assertThrows(BusinessException.class, () -> {
            secretariaService.atualizarSecretariaPorId( accessToken, idUsuarioLogado, 1L
                    , SecretariaFixtures.criarCadastroSecretariaRequestRepresentation());
        });
    }

    @Test
    public void buscarSecretariaPorId_Erro() {
        Assertions.assertThrows(BusinessException.class, () -> { secretariaService.buscarSecretariaPorId( accessToken
                , idUsuarioLogado, 1L);
        });
    }

    @Test
    public void excluirSecretaria_Erro() {
        Assertions.assertThrows(BusinessException.class, () -> { secretariaService.excluirSecretaria(accessToken
                , idUsuarioLogado, 1L);
        });
    }
}
