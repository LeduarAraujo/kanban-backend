package com.facilit.kanban_backend.web.controller;

import com.baeldung.openapi.api.ProjetosApi;

import com.baeldung.openapi.model.ListaProjetoResponseRepresentation;
import com.baeldung.openapi.model.ProjetoRepresentation;
import com.baeldung.openapi.model.SucessMessageRepresentation;
import com.facilit.kanban_backend.mapper.ProjetoMapper;
import com.facilit.kanban_backend.service.ProjetoService;
import com.facilit.kanban_backend.utils.ErrorFormat;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.Set;

@RestController
@RequiredArgsConstructor
public class ProjetoController implements ProjetosApi{

    private final ProjetoService projetoService;

    /**
     * POST /projetos : Realiza o cadastro de Projetos
     * Realiza o cadastro de Projetos
     *
     * @param projetoRepresentation (required)
     * @return Successful operation (status code 200)
     * or Bad Request (status code 400)
     * or Unauthorized (status code 401)
     * or Internal Server Error (status code 500)
     */
    @Override
    public ResponseEntity<SucessMessageRepresentation> cadastrarProjeto(ProjetoRepresentation projetoRepresentation) {
        try {
            Set<Long> responsaveisIds = new HashSet<>();

            return ResponseEntity.ok().body(projetoService.criarOuAtualizar(ProjetoMapper.
                    projetoEntityToRepresentation(projetoRepresentation), responsaveisIds));
        } catch (Exception ex) {
            return (ResponseEntity) ErrorFormat.convertToEntity(ex);
        }
    }

    /**
     * GET /projetos : Lista todos os projetos
     * Lista de projetos
     *
     * @return Successful operation (status code 200)
     * or Bad Request (status code 400)
     * or Internal Server Error (status code 500)
     */
    @Override
    public ResponseEntity<ListaProjetoResponseRepresentation> listarProcessos() {
        try {
            return ResponseEntity.ok().body(ProjetoMapper.listaProjetoResponseRepresentationMapper(projetoService.listar()));
        } catch (Exception ex) {
            return (ResponseEntity) ErrorFormat.convertToEntity(ex);
        }
    }
}
