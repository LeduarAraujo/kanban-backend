package unit;

import com.baeldung.openapi.model.ItemProjetoResponseRepresentation;
import com.baeldung.openapi.model.SuccessMessageRepresentation;
import com.facilit.kanban_backend.service.ItemProjetoService;
import com.facilit.kanban_backend.service.UsuarioService;
import com.facilit.kanban_backend.web.controller.ItemProjetoController;
import fixtures.TokenFixtures;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static fixtures.ItemProjetoFixtures.criarItemProjetoResponseRepresentation;

@ExtendWith(MockitoExtension.class)
public class ItemProjetoControllerTest {

    @InjectMocks
    ItemProjetoController itemProjetoController;

    @Mock
    UsuarioService usuarioService;

    @Mock
    ItemProjetoService itemProjetoService;

    String accessToken;
    Long idUsuarioLogado;

    @BeforeEach
    void setUp() {
        accessToken = TokenFixtures.getAcessToken();
        idUsuarioLogado = TokenFixtures.getIdUsuarioLogado();
    }

    @Test
    public void atualizarItemProjeto() {
        ResponseEntity<ItemProjetoResponseRepresentation> response = itemProjetoController
                .atualizarItemProjeto(
                        accessToken,
                        idUsuarioLogado,
                        criarItemProjetoResponseRepresentation());

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void excluirItemProjeto() {
        ResponseEntity<SuccessMessageRepresentation> response = itemProjetoController
                .excluirItemProjeto(
                        1L,
                        accessToken,
                        idUsuarioLogado);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void incluirItemProjeto() {
        ResponseEntity<ItemProjetoResponseRepresentation> response = itemProjetoController
                .incluirItemProjeto(
                        accessToken,
                        idUsuarioLogado,
                        criarItemProjetoResponseRepresentation()
                );

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void listarItensProjeto() {
        ResponseEntity<List<ItemProjetoResponseRepresentation>> response = itemProjetoController
                .listarItensProjeto(
                        1L,
                        accessToken,
                        idUsuarioLogado
                );

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}
