package integration;

import com.baeldung.openapi.model.UsuarioRepresentation;
import com.baeldung.openapi.model.SucessMessageRepresentation;
import com.facilit.kanban_backend.KanbanBackendApplication;
import com.facilit.kanban_backend.domain.entity.UsuarioEntity;
import com.facilit.kanban_backend.exception.EmailEmUsoException;
import com.facilit.kanban_backend.repository.UsuarioRepository;
import com.facilit.kanban_backend.service.UsuarioService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = KanbanBackendApplication.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@Transactional
public class UsuarioServiceTest {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UsuarioService usuarioService;

    @Mock
    private UsuarioRepresentation  usuarioRepresentation;

    @BeforeEach
    void setup() {
        usuarioRepository.deleteAll(); // Limpa o banco antes de cada teste
    }

    @Test
    public void cadastrarUsuario_sucess() throws EmailEmUsoException {
        UsuarioRepresentation usuario = new UsuarioRepresentation();
        usuario.setEmail("teste@teste.com");
        usuario.setNome("Usuário Teste");

        SucessMessageRepresentation result = usuarioService.cadastrarUsuario(usuario);

        Assertions.assertNotNull(result);
        Assertions.assertEquals("O usuário Usuário Teste foi cadastrado com sucesso!", result.getMessage());

    }

    @Test
    public void cadastrarUsuario_error() throws EmailEmUsoException {
        // Cria e salva usuário com o mesmo e-mail
        UsuarioEntity usuarioExistente = new UsuarioEntity();
        usuarioExistente.setEmail("teste@teste.com");
        usuarioExistente.setNome("Usuário Existente");

        usuarioRepository.save(usuarioExistente);

        // Tenta cadastrar outro com o mesmo e-mail
        UsuarioRepresentation usuarioNovo = new UsuarioRepresentation();
        usuarioNovo.setEmail("teste@teste.com");
        usuarioNovo.setNome("Outro Nome");

        Assertions.assertThrows(EmailEmUsoException.class, () -> {
            usuarioService.cadastrarUsuario(usuarioNovo);
        });
    }
}
