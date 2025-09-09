package unit;

import com.baeldung.openapi.model.UsuarioRepresentation;
import com.facilit.kanban_backend.domain.entity.UsuarioEntity;
import com.facilit.kanban_backend.exception.EmailEmUsoException;
import com.facilit.kanban_backend.repository.UsuarioRepository;
import com.facilit.kanban_backend.service.UsuarioService;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class UsuarioServiceTest {
    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioService usuarioService;

    @Mock
    private UsuarioRepresentation  usuarioRepresentation;

    @BeforeEach
    void setUp() {
        usuarioRepresentation = new UsuarioRepresentation();
        usuarioRepresentation.setEmail("teste@exemplo.com");
        usuarioRepresentation.setNome("Usuário Teste");
    }

    @Test
    public void cadastrarUsuario_sucess() throws EmailEmUsoException {
        Assert.assertNotNull(usuarioService.cadastrarUsuario(usuarioRepresentation));
    }

    @Test
    public void cadastrarUsuario_error() throws EmailEmUsoException {
        // Simula que o e-mail já está em uso
        Mockito.when(usuarioRepository.findByEmail(usuarioRepresentation.getEmail()))
                .thenReturn(List.of(new UsuarioEntity()));

        Assert.assertThrows(EmailEmUsoException.class, () -> usuarioService.cadastrarUsuario(usuarioRepresentation));
    }
}
