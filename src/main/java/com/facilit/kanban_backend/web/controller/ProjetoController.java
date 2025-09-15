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
     * @param pAcessToken                            (required)
     * @param pIdUsuarioLogado                       (required)
     * @param pAtualizarProjetoRequestRepresentation (required)
     * @return Successful operation (status code 200)
     * or Bad Request (status code 400)
     * or Projeto não encontrado (status code 404)
     * or Internal Server Error (status code 500)
     */
    @Override
    public ResponseEntity<ProjetoRepresentation> atualizarProjeto(Long pIdProjeto, String pAcessToken, Long pIdUsuarioLogado, AtualizarProjetoRequestRepresentation pAtualizarProjetoRequestRepresentation) {
        try {
            return ResponseEntity.ok().body(projetoService.atualizarProjeto(pIdProjeto, pAcessToken, pIdUsuarioLogado
                    , pAtualizarProjetoRequestRepresentation));
        } catch (Exception ex) {
            return (ResponseEntity) ErrorFormat.convertToEntity(ex);
        }
    }

    /**
     * GET /projetos/{id} : Buscar projeto por ID
     * Retorna um projeto específico pelo ID
     *
     * @param pIdProjeto              ID do projeto (required)
     * @param pAcessToken      (required)
     * @param pIdUsuarioLogado (required)
     * @return Successful operation (status code 200)
     * or Projeto não encontrado (status code 404)
     * or Internal Server Error (status code 500)
     */
    @Override
    public ResponseEntity<ProjetoRepresentation> buscarProjetoPorId(Long pIdProjeto, String pAcessToken, Long pIdUsuarioLogado) {
        try {
            return ResponseEntity.ok().body(projetoService.buscarProjetoPorId(pIdProjeto, pAcessToken, pIdUsuarioLogado));
        } catch (Exception ex) {
            return (ResponseEntity) ErrorFormat.convertToEntity(ex);
        }
    }

    /**
     * POST /projetos : Realiza o cadastro de Projetos
     * Realiza o cadastro de Projetos
     *
     * @param pAcessToken                            (required)
     * @param pIdUsuarioLogado                       (required)
     * @param pCadastrarProjetoRequestRepresentation (required)
     * @return Successful operation (status code 200)
     * or Bad Request (status code 400)
     * or Unauthorized (status code 401)
     * or Internal Server Error (status code 500)
     */
    @Override
    public ResponseEntity<ProjetoRepresentation> cadastrarProjeto(String pAcessToken, Long pIdUsuarioLogado, CadastrarProjetoRequestRepresentation pCadastrarProjetoRequestRepresentation) {
        try {
            return ResponseEntity.ok().body(projetoService.cadastrarProjeto(pAcessToken, pIdUsuarioLogado, pCadastrarProjetoRequestRepresentation));
        } catch (Exception ex) {
            return (ResponseEntity) ErrorFormat.convertToEntity(ex);
        }
    }

    /**
     * DELETE /projetos/{id} : Excluir projeto
     * Exclui um projeto
     *
     * @param pIdProjeto              ID do projeto (required)
     * @param pAcessToken      (required)
     * @param pIdUsuarioLogado (required)
     * @return Projeto excluído com sucesso (status code 200)
     * or Projeto não encontrado (status code 404)
     * or Internal Server Error (status code 500)
     */
    @Override
    public ResponseEntity<SuccessMessageRepresentation> excluirProjeto(Long pIdProjeto, String pAcessToken, Long pIdUsuarioLogado) {
        try {
            return ResponseEntity.ok().body(projetoService.excluirProjeto(pAcessToken, pIdUsuarioLogado, pIdProjeto));
        } catch (Exception ex) {
            return (ResponseEntity) ErrorFormat.convertToEntity(ex);
        }
    }

    /**
     * GET /projetos : Lista todos os projetos
     * Lista de projetos
     *
     * @param pAcessToken      (required)
     * @param pIdUsuarioLogado (required)
     * @param pPage            (optional, default to 0)
     * @param pSize            (optional, default to 20)
     * @param pSort            (optional, default to id)
     * @return Successful operation (status code 200)
     * or Bad Request (status code 400)
     * or Internal Server Error (status code 500)
     */
    @Override
    public ResponseEntity<ListaProjetoResponseRepresentation> listarProjetos(String pAcessToken, Long pIdUsuarioLogado, Integer pPage, Integer pSize, String pSort) {
        try {
            Pageable pageable = PageRequest.of(pPage, pSize, Sort.by(pSort));
            return ResponseEntity.ok().body(projetoService.listarProjetos(pAcessToken, pIdUsuarioLogado, pageable));
        } catch (Exception ex) {
            return (ResponseEntity) ErrorFormat.convertToEntity(ex);
        }
    }

    /**
     * GET /projetos/por-status : Listar projetos por status
     * Lista projetos filtrados por status específico
     *
     * @param pStatusProjetoRepresentation          Status para filtrar (required)
     * @param pAcessToken      (required)
     * @param pIdUsuarioLogado (required)
     * @param pPage            Número da página (optional, default to 0)
     * @param pSize            Tamanho da página (optional, default to 20)
     * @param pSort            Campo para ordenação (optional, default to id)
     * @return Successful operation (status code 200)
     * or Bad Request (status code 400)
     * or Internal Server Error (status code 500)
     */
    @Override
    public ResponseEntity<ListaProjetoResponseRepresentation> listarProjetosPorStatus(StatusProjetoRepresentation pStatusProjetoRepresentation
            , String pAcessToken, Long pIdUsuarioLogado, Integer pPage, Integer pSize, String pSort) {
        try {
            Pageable pageable = PageRequest.of(pPage, pSize, Sort.by(pSort));
            return ResponseEntity.ok().body(projetoService.listarProjetosPorStatus(pAcessToken, pIdUsuarioLogado,
                    pStatusProjetoRepresentation, pageable));
        } catch (Exception ex) {
            return (ResponseEntity) ErrorFormat.convertToEntity(ex);
        }
    }

    /**
     * PUT /projetos/{id}/status : Mover status do projeto
     * Move o projeto para um novo status seguindo as regras de transição
     *
     * @param pIdProjeto              ID do projeto (required)
     * @param pStatusProjetoRepresentation      Novo status do projeto (required)
     * @param pAcessToken      (required)
     * @param pIdUsuarioLogado (required)
     * @return Status alterado com sucesso (status code 200)
     * or Transição inválida (status code 400)
     * or Projeto não encontrado (status code 404)
     * or Internal Server Error (status code 500)
     */
    @Override
    public ResponseEntity<ProjetoRepresentation> moverStatusProjeto(Long pIdProjeto, StatusProjetoRepresentation pStatusProjetoRepresentation, String pAcessToken, Long pIdUsuarioLogado) {
        try {
            return ResponseEntity.ok().body(projetoService.moverStatus(pAcessToken, pIdUsuarioLogado, pIdProjeto, pStatusProjetoRepresentation));
        } catch (Exception ex) {
            return (ResponseEntity) ErrorFormat.convertToEntity(ex);
        }
    }
}


