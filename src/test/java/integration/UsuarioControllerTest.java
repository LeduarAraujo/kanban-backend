package integration;

import com.facilit.kanban_backend.KanbanBackendApplication;
import com.facilit.kanban_backend.exception.EmailEmUsoException;
import com.facilit.kanban_backend.exception.UsuarioInvalido;
import com.facilit.kanban_backend.service.UsuarioService;
import com.facilit.kanban_backend.web.controller.UsuarioController;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static fixtures.UsuarioFixtures.criarCadastrarUsuarioRequestRepresentation;
import static fixtures.UsuarioFixtures.criarLoginUsuarioRequestRepresentation;

@SpringBootTest(classes = KanbanBackendApplication.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@Transactional
public class UsuarioControllerTest {

    @Autowired
    private UsuarioController usuarioController;

    @Autowired
    private UsuarioService usuarioService;

    @BeforeEach
    void setup() {

    }

    @Test
    public void cadastrarUsuario_sucess() throws EmailEmUsoException {
        ResponseEntity resposta = usuarioController.cadastrarUsuario(
                criarCadastrarUsuarioRequestRepresentation());

        Assertions.assertEquals(HttpStatus.OK, resposta.getStatusCode());
    }

    @Test
    public void cadastrarUsuario_error() throws EmailEmUsoException {
        // Simula que o e-mail já está em uso

        usuarioService.cadastrarUsuario(criarCadastrarUsuarioRequestRepresentation());

        ResponseEntity resposta = usuarioController.cadastrarUsuario(criarCadastrarUsuarioRequestRepresentation());
        Assertions.assertEquals(HttpStatus.CONFLICT, resposta.getStatusCode());
    }

    @Test
    public void loginUsuario_sucess() throws EmailEmUsoException, UsuarioInvalido {
        var cadastro = usuarioController.cadastrarUsuario(criarCadastrarUsuarioRequestRepresentation());
        Assertions.assertEquals(HttpStatus.OK, cadastro.getStatusCode());
        var resposta = usuarioController.loginUsuario(criarLoginUsuarioRequestRepresentation());
        Assertions.assertEquals(HttpStatus.OK, resposta.getStatusCode());
    }

    @Test
    public void loginUsuario_error() throws EmailEmUsoException, UsuarioInvalido {
        ResponseEntity resposta = usuarioController.loginUsuario(criarLoginUsuarioRequestRepresentation());
        Assertions.assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, resposta.getStatusCode());
    }

}
