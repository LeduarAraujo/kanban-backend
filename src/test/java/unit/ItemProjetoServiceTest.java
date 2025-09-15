package unit;


import com.facilit.kanban_backend.repository.ItemProjetoRepository;
import com.facilit.kanban_backend.service.ItemProjetoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ItemProjetoServiceTest {

    @Mock
    private ItemProjetoRepository itemProjetoRepository;

    @InjectMocks
    private ItemProjetoService itemProjetoService;

    @BeforeEach
    void setUp() {

    }

    @Test
    void salvarItemProjeto_deveSalvarQuandoValido() {

    }

    @Test
    void atualizar_deveLancarQuandoNaoExistir() {

    }
}
