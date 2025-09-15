package unit;

import com.baeldung.openapi.model.ListaResponsavelResponseRepresentation;
import com.baeldung.openapi.model.ResponsavelRepresentation;
import com.baeldung.openapi.model.SuccessMessageRepresentation;
import com.facilit.kanban_backend.exception.EmailEmUsoException;
import com.facilit.kanban_backend.repository.*;
import com.facilit.kanban_backend.service.ResponsavelService;
import com.facilit.kanban_backend.service.UsuarioService;
import fixtures.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static fixtures.ResponsavelFixtures.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ResponsavelServiceTest {

    @Mock
    private ResponsavelRepository responsavelRepository;

    @Mock
    private ProjetoRepository projetoRepository;

    @Mock
    private UsuarioService usuarioService;

    @Mock
    private CargoRepository cargoRepository;

    @Mock
    private SecretariaRepository secretariaRepository;

    @Mock
    private Pageable pageable;

    @InjectMocks
    private ResponsavelService responsavelService;

    String accessToken;
    Long idUsuarioLogado;

    @BeforeEach
    void setUp() {
        accessToken = TokenFixtures.getAcessToken();
        idUsuarioLogado = TokenFixtures.getIdUsuarioLogado();
    }

    @Test
    public void listarResponsaveis_Sucesso() {
        when(responsavelRepository.findAll(pageable))
                .thenReturn(criarPageResponsavelEntity());

        ListaResponsavelResponseRepresentation retorno = responsavelService.listarResponsaveis(
                accessToken, idUsuarioLogado, pageable);
        Assertions.assertNotNull(retorno);
    }

    @Test
    public void excluirResponsavel_Sucesso() {
        responsavelRepository.save(criarResponsavelEntity_semId());
        SuccessMessageRepresentation retorno = responsavelService.excluirResponsavel(accessToken
                , idUsuarioLogado, 1L);

        Assertions.assertNotNull(retorno);
    }

    @Test
    public void cadastrarResponsavel_Sucesso() throws EmailEmUsoException {
        when(secretariaRepository.findById(
                Mockito.any())).thenReturn(Optional.of(SecretariaFixtures.criarSecretariaEntity_comId()));

        when(usuarioService.cadastroUsuarioRetornoEntity(UsuarioFixtures.criarCadastrarUsuarioRequestRepresentation()))
                .thenReturn(UsuarioFixtures.criarUsuarioEntityExistente());

        when(cargoRepository.findById(Mockito.any())).thenReturn(Optional.of(CargoFixtures.criarCargoEntity()));

        when(responsavelRepository.save(Mockito.any()))
                .thenReturn(ResponsavelFixtures.criarResponsavelEntity_comId());

        ResponsavelRepresentation responsavelRepresentation = responsavelService.cadastrarResponsavel(
                accessToken, idUsuarioLogado, criarCadastroResponsavelBodyRepresentation()
        );

        Assertions.assertNotNull(responsavelRepresentation.getId());
    }

    @Test
    public void atualizarResponsavelPorId_Sucesso() {
        when(responsavelRepository.findById(Mockito.any())).thenReturn(Optional.of(ResponsavelFixtures.criarResponsavelEntity_comId()));

        when(secretariaRepository.findById(
                Mockito.any())).thenReturn(Optional.of(SecretariaFixtures.criarSecretariaEntity_comId()));

        when(cargoRepository.findById(Mockito.any())).thenReturn(Optional.of(CargoFixtures.criarCargoEntity()));

        when(responsavelRepository.save(Mockito.any())).thenReturn(ResponsavelFixtures.criarResponsavelEntity_comId());

        ResponsavelRepresentation retorno = responsavelService.atualizarResponsavelPorId(
                accessToken, idUsuarioLogado, 1L, criarAtualizarResponsavelBodyRepresentationNovo()
        );

        Assertions.assertEquals(1L, retorno.getCargoId());
    }

    // case erros

    @Test
    public void excluirResponsavel_erro() {
        when(projetoRepository.findByResponsaveisId(Mockito.any())).thenReturn(List.of(ProjetoFixtures.criarProjetoEntity_comId()));

        Assertions.assertThrows(IllegalArgumentException.class, () -> responsavelService.excluirResponsavel(accessToken
                , idUsuarioLogado, 1L));
    }

}

