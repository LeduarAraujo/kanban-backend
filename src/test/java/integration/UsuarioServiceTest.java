//package integration;
//
//import com.baeldung.openapi.model.UsuarioRepresentation;
//import com.baeldung.openapi.model.SucessMessageRepresentation;
//import com.facilit.kanban_backend.KanbanBackendApplication;
//import com.facilit.kanban_backend.exception.EmailEmUsoException;
//import com.facilit.kanban_backend.repository.UsuarioRepository;
//import com.facilit.kanban_backend.service.UsuarioService;
//import jakarta.transaction.Transactional;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mock;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import static fixtures.UsuarioFixtures.*;
//
//@SpringBootTest(classes = KanbanBackendApplication.class)
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
//@Transactional
//public class UsuarioServiceTest {
//
//    @Autowired
//    private UsuarioRepository usuarioRepository;
//
//    @Autowired
//    private UsuarioService usuarioService;
//
//    @BeforeEach
//    void setup() {
//        usuarioRepository.deleteAll(); // Limpa o banco antes de cada teste
//    }
//
//    @Test
//    public void cadastrarUsuario_sucess() throws EmailEmUsoException {
//        SucessMessageRepresentation result = usuarioService.
//                cadastrarUsuario(criarUsuario());
//
//        Assertions.assertNotNull(result);
//        Assertions.assertEquals("O usuário Usuário Teste foi cadastrado com sucesso!", result.getMessage());
//    }
//
//    @Test
//    public void cadastrarUsuario_error() throws EmailEmUsoException {
//        // Cria e salva usuário com o mesmo e-mail
//        usuarioRepository.save(criarUsuarioEntity());
//
//        // Tenta cadastrar outro com o mesmo e-mail
//        Assertions.assertThrows(EmailEmUsoException.class, () -> {
//            usuarioService.cadastrarUsuario(criarUsuario());
//        });
//    }
//}
