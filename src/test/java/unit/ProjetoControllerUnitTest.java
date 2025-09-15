package unit;

import com.facilit.kanban_backend.service.ProjetoService;
import com.facilit.kanban_backend.web.controller.ProjetoController;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ProjetoControllerUnitTest {

    @Mock
    private ProjetoService projetoService;

    @InjectMocks
    private ProjetoController projetoController;

    @Test
    void criarProjeto_deveDelegarParaService() {

    }

    @Test
    void listarProjetos_deveRetornarLista() {

    }
}
