//package integration;
//
//import com.facilit.kanban_backend.model.Responsavel;
//import com.facilit.kanban_backend.service.ResponsavelService;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.test.web.servlet.MockMvc;
//
//import java.util.List;
//
//import static org.mockito.Mockito.*;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//
//@WebMvcTest(controllers = ResponsavelController.class)
//class ResponsavelControllerIntegrationTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockBean
//    private ResponsavelService responsavelService;
//
//    @Autowired
//    private ObjectMapper objectMapper;
//
//    @Test
//    void getResponsaveis_shouldReturnOk() throws Exception {
//        Responsavel r = new Responsavel();
//        r.setId(1L);
//        r.setNome("Responsavel X");
//
//        when(responsavelService.listarTodos()).thenReturn(List.of(r));
//
//        mockMvc.perform(get("/responsaveis"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$[0].nome").value("Responsavel X"));
//
//        verify(responsavelService).listarTodos();
//    }
//
//    @Test
//    void postarResponsavel_deveCriar() throws Exception {
//        Responsavel novo = new Responsavel();
//        novo.setNome("Novo R");
//
//        Responsavel criado = new Responsavel();
//        criado.setId(2L);
//        criado.setNome("Novo R");
//
//        when(responsavelService.cadastrarResponsavel(any(Responsavel.class))).thenReturn(criado);
//
//        mockMvc.perform(post("/responsaveis")
//                        .contentType("application/json")
//                        .content(objectMapper.writeValueAsString(novo)))
//                .andExpect(status().isCreated())
//                .andExpect(jsonPath("$.id").value(2));
//
//        verify(responsavelService).cadastrarResponsavel(any(Responsavel.class));
//    }
//}
