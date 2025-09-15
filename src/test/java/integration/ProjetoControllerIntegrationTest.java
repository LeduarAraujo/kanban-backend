//package integration;
//
//import com.facilit.kanban_backend.service.ProjetoService;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.facilit.kanban_backend.model.Projeto;
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
//@WebMvcTest(controllers = ProjetoController.class)
//class ProjetoControllerIntegrationTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockBean
//    private ProjetoService projetoService;
//
//    @Autowired
//    private ObjectMapper objectMapper;
//
//    @Test
//    void getProjetos_endpoint_shouldReturnOk() throws Exception {
//        Projeto p = new Projeto();
//        p.setId(1L);
//        p.setNome("Projeto Teste");
//
//        when(projetoService.listarTodos()).thenReturn(List.of(p));
//
//        mockMvc.perform(get("/projetos"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$[0].nome").value("Projeto Teste"));
//
//        verify(projetoService).listarTodos();
//    }
//
//    @Test
//    void postProjeto_deveCriarRetornarCreated() throws Exception {
//        Projeto novo = new Projeto();
//        novo.setNome("Novo Projeto");
//
//        Projeto criado = new Projeto();
//        criado.setId(10L);
//        criado.setNome("Novo Projeto");
//
//        when(projetoService.criarProjeto(any(Projeto.class))).thenReturn(criado);
//
//        mockMvc.perform(post("/projetos")
//                        .contentType("application/json")
//                        .content(objectMapper.writeValueAsString(novo)))
//                .andExpect(status().isCreated())
//                .andExpect(jsonPath("$.id").value(10));
//
//        verify(projetoService).criarProjeto(any(Projeto.class));
//    }
//}
