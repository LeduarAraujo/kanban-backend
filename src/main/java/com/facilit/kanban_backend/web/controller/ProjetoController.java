package com.facilit.kanban_backend.web.controller;

import com.baeldung.openapi.api.ProjetosApi;
import com.baeldung.openapi.model.*;
import com.facilit.kanban_backend.service.ProjetoService;
import com.facilit.kanban_backend.utils.ErrorFormat;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.Pageable;

@RestController
@RequiredArgsConstructor
public class ProjetoController implements ProjetosApi {

    private final ProjetoService projetoService;
    /**
     * PUT /projetos/{id} : Atualizar projeto
     * Atualiza um projeto existente
     *
     * @param pIdProjeto                                    ID do projeto (required)
     * @param pAtualizarProjetoRequestRepresentation (required)
     * @return Successful operation (status code 200)
     * or Bad Request (status code 400)
     * or Projeto não encontrado (status code 404)
     * or Internal Server Error (status code 500)
     */
    @Override
    public ResponseEntity<ProjetoRepresentation> atualizarProjeto(Long pIdProjeto, AtualizarProjetoRequestRepresentation pAtualizarProjetoRequestRepresentation) {
        try {
            return ResponseEntity.ok().body(projetoService.atualizarProjeto(pIdProjeto, pAtualizarProjetoRequestRepresentation));
        } catch (Exception ex) {
            return (ResponseEntity) ErrorFormat.convertToEntity(ex);
        }
    }

    /**
     * GET /projetos/{id} : Buscar projeto por ID
     * Retorna um projeto específico pelo ID
     *
     * @param id ID do projeto (required)
     * @return Successful operation (status code 200)
     * or Projeto não encontrado (status code 404)
     * or Internal Server Error (status code 500)
     */
    @Override
    public ResponseEntity<ProjetoRepresentation> buscarProjetoPorId(Long id) {
        try {
            return ResponseEntity.ok().body(projetoService.buscarProjetoPorId(id));
        } catch (Exception ex) {
            return (ResponseEntity) ErrorFormat.convertToEntity(ex);
        }
    }

    /**
     * POST /projetos : Realiza o cadastro de Projetos
     * Realiza o cadastro de Projetos
     *
     * @param pCadastrarProjetoRequestRepresentation (required)
     * @return Successful operation (status code 200)
     * or Bad Request (status code 400)
     * or Unauthorized (status code 401)
     * or Internal Server Error (status code 500)
     */
    @Override
    public ResponseEntity<ProjetoRepresentation> cadastrarProjeto(CadastrarProjetoRequestRepresentation pCadastrarProjetoRequestRepresentation) {
        try {
            return ResponseEntity.ok().body(projetoService.cadastrarProjeto(pCadastrarProjetoRequestRepresentation));
        } catch (Exception ex) {
            return (ResponseEntity) ErrorFormat.convertToEntity(ex);
        }
    }

    /**
     * DELETE /projetos/{id} : Excluir projeto
     * Exclui um projeto
     *
     * @param pIdProjeto ID do projeto (required)
     * @return Projeto excluído com sucesso (status code 200)
     * or Projeto não encontrado (status code 404)
     * or Internal Server Error (status code 500)
     */
    @Override
    public ResponseEntity<SuccessMessageRepresentation> excluirProjeto(Long pIdProjeto) {
        try {
            return ResponseEntity.ok().body(projetoService.excluirProjeto(pIdProjeto));
        } catch (Exception ex) {
            return (ResponseEntity) ErrorFormat.convertToEntity(ex);
        }
    }

    /**
     * GET /projetos : Lista todos os projetos
     * Lista de projetos
     *
     * @param page (optional, default to 0)
     * @param size (optional, default to 20)
     * @param sort (optional)
     * @return Successful operation (status code 200)
     * or Bad Request (status code 400)
     * or Internal Server Error (status code 500)
     */
    @Override
    public ResponseEntity<ListaProjetoResponseRepresentation> listarProjetos(Integer page, Integer size, String sort) {
        try {
            Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
            return ResponseEntity.ok().body(projetoService.listarProjetos(pageable));
        } catch (Exception ex) {
            return (ResponseEntity) ErrorFormat.convertToEntity(ex);
        }
    }

    /**
     * GET /projetos/por-status : Listar projetos por status
     * Lista projetos filtrados por status específico
     *
     * @param status Status para filtrar (required)
     * @param page   Número da página (optional, default to 0)
     * @param size   Tamanho da página (optional, default to 20)
     * @param sort   Campo para ordenação (optional)
     * @return Successful operation (status code 200)
     * or Bad Request (status code 400)
     * or Internal Server Error (status code 500)
     */
    @Override
    public ResponseEntity<ListaProjetoResponseRepresentation> listarProjetosPorStatus(StatusProjetoRepresentation status, Integer page, Integer size, String sort) {
        try {
            Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
            return ResponseEntity.ok().body(projetoService.listarProjetosPorStatus(status, pageable));
        } catch (Exception ex) {
            return (ResponseEntity) ErrorFormat.convertToEntity(ex);
        }
    }

    /**
     * PUT /projetos/{id}/status : Mover status do projeto
     * Move o projeto para um novo status seguindo as regras de transição
     *
     * @param idProjeto         ID do projeto (required)
     * @param novoStatus Novo status do projeto (required)
     * @return Status alterado com sucesso (status code 200)
     * or Transição inválida (status code 400)
     * or Projeto não encontrado (status code 404)
     * or Internal Server Error (status code 500)
     */
    @Override
    public ResponseEntity<ProjetoRepresentation> moverStatusProjeto(Long idProjeto, StatusProjetoRepresentation novoStatus) {
        try {
            return ResponseEntity.ok().body(projetoService.moverStatus(idProjeto, novoStatus));
        } catch (Exception ex) {
            return (ResponseEntity) ErrorFormat.convertToEntity(ex);
        }
    }
}


