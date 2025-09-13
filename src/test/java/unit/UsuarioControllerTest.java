package unit;

import com.facilit.kanban_backend.exception.EmailEmUsoException;
import com.facilit.kanban_backend.exception.UsuarioInvalido;
import com.facilit.kanban_backend.service.UsuarioService;
import com.facilit.kanban_backend.web.controller.UsuarioController;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static fixtures.UsuarioFixtures.criarCadastrarUsuarioRequestRepresentation;
import static fixtures.UsuarioFixtures.criarLoginUsuarioRequestRepresentation;

@ExtendWith(MockitoExtension.class)
public class UsuarioControllerTest {

    @Mock
    private UsuarioService usuarioService;

    @InjectMocks
    private UsuarioController usuarioController;

    @Test
    public void cadastrarUsuario_sucess() throws EmailEmUsoException {
        ResponseEntity resposta = usuarioController.cadastrarUsuario(
                criarCadastrarUsuarioRequestRepresentation());

        Assert.assertEquals(HttpStatus.OK, resposta.getStatusCode());
    }

    @Test
    public void cadastrarUsuario_error() throws EmailEmUsoException {
        // Simula que o e-mail já está em uso
        Mockito.when(usuarioService.cadastrarUsuario(Mockito.any())).thenThrow(EmailEmUsoException.class);
        ResponseEntity resposta = usuarioController.cadastrarUsuario(criarCadastrarUsuarioRequestRepresentation());
        Assert.assertEquals(HttpStatus.CONFLICT, resposta.getStatusCode());
    }

    @Test
    public void loginUsuario_sucess() throws EmailEmUsoException, UsuarioInvalido {
        var resposta = usuarioController.loginUsuario(criarLoginUsuarioRequestRepresentation());
        Assert.assertEquals(HttpStatus.OK, resposta.getStatusCode());
    }

    @Test
    public void loginUsuario_error() throws EmailEmUsoException, UsuarioInvalido {
        Mockito.when(usuarioService.loginUsuario(Mockito.any())).thenThrow(UsuarioInvalido.class);
        ResponseEntity resposta = usuarioController.loginUsuario(criarLoginUsuarioRequestRepresentation());
        Assert.assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, resposta.getStatusCode());
    }
}
