package unit;

import com.facilit.kanban_backend.domain.entity.UsuarioEntity;
import com.facilit.kanban_backend.exception.EmailEmUsoException;
import com.facilit.kanban_backend.exception.UsuarioInvalido;
import com.facilit.kanban_backend.repository.UsuarioRepository;
import com.facilit.kanban_backend.service.UsuarioService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static fixtures.UsuarioFixtures.*;

import java.util.List;

@ExtendWith(MockitoExtension.class)
public class UsuarioServiceTest {
    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioService usuarioService;

    @Test
    public void cadastrarUsuario_sucess() throws EmailEmUsoException {
        Assertions.assertNotNull(usuarioService.cadastrarUsuario(
                criarCadastrarUsuarioRequestRepresentation()));
    }

    @Test
    public void cadastrarUsuario_error() throws EmailEmUsoException {
        // Simula que o e-mail já está em uso
        Mockito.when(usuarioRepository.findByEmail(criarCadastrarUsuarioRequestRepresentation().getEmail()))
                .thenReturn(List.of(new UsuarioEntity()));

        Assertions.assertThrows(EmailEmUsoException.class, () -> usuarioService.cadastrarUsuario(
                criarCadastrarUsuarioRequestRepresentation()));
    }

    @Test
    public void loginUsuario_sucess() throws EmailEmUsoException, UsuarioInvalido {
        usuarioRepository.save(criarUsuarioEntityExistente());

        Mockito.when(usuarioRepository.findByEmailAndSenha(
                criarLoginUsuarioRequestRepresentation().getEmail(),
                criarLoginUsuarioRequestRepresentation().getSenha())
        ).thenReturn(criarUsuarioEntityExistente());

        var response = usuarioService.loginUsuario(criarLoginUsuarioRequestRepresentation());
        Assertions.assertNotNull(response);
    }

    @Test
    public void loginUsuario_error() throws EmailEmUsoException, UsuarioInvalido {
        Assertions.assertThrows(UsuarioInvalido.class, () -> {
            usuarioService.loginUsuario(criarLoginUsuarioRequestRepresentation());
        });
    }
}
