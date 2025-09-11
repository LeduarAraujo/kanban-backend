package unit;

import com.facilit.kanban_backend.domain.entity.ProjetoEntity;
import com.facilit.kanban_backend.domain.enums.StatusProjetoEnum;
import com.facilit.kanban_backend.exception.BusinessException;
import com.facilit.kanban_backend.repository.ProjetoRepository;
import com.facilit.kanban_backend.service.ProjetoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProjetoServiceTest {

    @Mock
    private ProjetoRepository projetoRepository;

    @InjectMocks
    private ProjetoService projetoService;

    private ProjetoEntity projeto;

    @BeforeEach
    void setUp() {
        projeto = new ProjetoEntity();
        projeto.setId(1L);
        projeto.setNome("Projeto Teste");
        projeto.setInicioPrevisto(LocalDateTime.now().minusDays(5));
        projeto.setTerminoPrevisto(LocalDateTime.now().plusDays(10));
    }

    @Test
    void salvar_DeveCalcularStatusEAtualizarMetricas() {
        // Given
        when(projetoRepository.save(any(ProjetoEntity.class))).thenReturn(projeto);

        // When
        ProjetoEntity resultado = projetoService.salvar(projeto);

        // Then
        assertNotNull(resultado);
        assertNotNull(resultado.getStatus());
        assertNotNull(resultado.getDiasAtraso());
        assertNotNull(resultado.getPercentualTempoRestante());
        verify(projetoRepository).save(projeto);
    }

    @Test
    void moverStatus_AIniciarParaEmAndamento_DeveDefinirInicioRealizado() {
        // Given
        projeto.setStatus(StatusProjetoEnum.A_INICIAR);
        when(projetoRepository.findById(1L)).thenReturn(Optional.of(projeto));
        when(projetoRepository.save(any(ProjetoEntity.class))).thenReturn(projeto);

        // When
        ProjetoEntity resultado = projetoService.moverStatus(1L, StatusProjetoEnum.EM_ANDAMENTO);

        // Then
        assertNotNull(resultado.getInicioRealizado());
        assertEquals(StatusProjetoEnum.EM_ANDAMENTO, resultado.getStatus());
        verify(projetoRepository).save(projeto);
    }

    @Test
    void moverStatus_AIniciarParaConcluido_DeveDefinirTerminoRealizado() {
        // Given
        projeto.setStatus(StatusProjetoEnum.A_INICIAR);
        when(projetoRepository.findById(1L)).thenReturn(Optional.of(projeto));
        when(projetoRepository.save(any(ProjetoEntity.class))).thenReturn(projeto);

        // When
        ProjetoEntity resultado = projetoService.moverStatus(1L, StatusProjetoEnum.CONCLUIDO);

        // Then
        assertNotNull(resultado.getTerminoRealizado());
        assertEquals(StatusProjetoEnum.CONCLUIDO, resultado.getStatus());
        verify(projetoRepository).save(projeto);
    }

    @Test
    void moverStatus_EmAndamentoParaAtrasado_DeveLancarExcecao() {
        // Given
        projeto.setStatus(StatusProjetoEnum.EM_ANDAMENTO);
        when(projetoRepository.findById(1L)).thenReturn(Optional.of(projeto));

        // When & Then
        BusinessException exception = assertThrows(BusinessException.class, 
            () -> projetoService.moverStatus(1L, StatusProjetoEnum.ATRASADO));
        
        assertTrue(exception.getMessage().contains("Para ir a ATRASADO a partir de EM ANDAMENTO"));
    }

    @Test
    void calcularStatus_ComTerminoRealizado_DeveRetornarConcluido() {
        // Given
        projeto.setTerminoRealizado(LocalDateTime.now());

        // When
        StatusProjetoEnum status = projetoService.calcularStatus(projeto);

        // Then
        assertEquals(StatusProjetoEnum.CONCLUIDO, status);
    }

    @Test
    void calcularStatus_SemDatasRealizadas_DeveRetornarAIniciar() {
        // Given
        projeto.setInicioRealizado(null);
        projeto.setTerminoRealizado(null);

        // When
        StatusProjetoEnum status = projetoService.calcularStatus(projeto);

        // Then
        assertEquals(StatusProjetoEnum.A_INICIAR, status);
    }

    @Test
    void calcularPercentualTempoRestante_ComDatasValidas_DeveCalcularCorretamente() {
        // Given
        LocalDate hoje = LocalDate.now();
        projeto.setInicioPrevisto(hoje.minusDays(5).atStartOfDay());
        projeto.setTerminoPrevisto(hoje.plusDays(10).atStartOfDay());

        // When
        int percentual = projetoService.calcularPercentualTempoRestante(projeto);

        // Then
        assertTrue(percentual > 0 && percentual <= 100);
    }

    @Test
    void calcularDiasAtraso_ComTerminoPrevistoPassado_DeveCalcularAtraso() {
        // Given
        LocalDate hoje = LocalDate.now();
        projeto.setTerminoPrevisto(hoje.minusDays(3).atStartOfDay());
        projeto.setTerminoRealizado(null);

        // When
        int diasAtraso = projetoService.calcularDiasAtraso(projeto);

        // Then
        assertEquals(3, diasAtraso);
    }

    @Test
    void calcularDiasAtraso_ProjetoConcluido_DeveRetornarZero() {
        // Given
        projeto.setStatus(StatusProjetoEnum.CONCLUIDO);

        // When
        int diasAtraso = projetoService.calcularDiasAtraso(projeto);

        // Then
        assertEquals(0, diasAtraso);
    }
}
