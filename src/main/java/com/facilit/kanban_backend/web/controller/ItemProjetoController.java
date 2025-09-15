package com.facilit.kanban_backend.web.controller;

import com.baeldung.openapi.api.ItemProjetoApi;
import com.baeldung.openapi.model.ItemProjetoResponseRepresentation;
import com.baeldung.openapi.model.SuccessMessageRepresentation;
import com.facilit.kanban_backend.service.ItemProjetoService;
import com.facilit.kanban_backend.utils.ErrorFormat;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ItemProjetoController implements ItemProjetoApi {

    private final ItemProjetoService itemProjetoService;

    /**
     * PUT /item-projeto : Atualiza um item de projeto existente
     * Atualiza um item de projeto existente
     *
     * @param pAcessToken      (required)
     * @param pIdUsuarioLogado (required)
     * @param pItemProjetoResponseRepresentation (optional)
     * @return successful operation (status code 200)
     * or Bad Request (status code 400)
     * or Internal Server Error (status code 500)
     */
    @Override
    public ResponseEntity<ItemProjetoResponseRepresentation> atualizarItemProjeto(String pAcessToken, Long pIdUsuarioLogado
            , ItemProjetoResponseRepresentation pItemProjetoResponseRepresentation) {
        try {
            return ResponseEntity.ok().body(itemProjetoService.atualizarItemProjeto(pAcessToken, pIdUsuarioLogado
                    , pItemProjetoResponseRepresentation));
        } catch (Exception ex) {
            return (ResponseEntity) ErrorFormat.convertToEntity(ex);
        }
    }

    /**
     * DELETE /item-projeto : Exclui um item de projeto existente
     * Exclui um item de projeto existente
     *
     * @param pIdItemProjeto (required)
     * @param pAcessToken      (required)
     * @param pIdUsuarioLogado (required)
     * @return successful operation (status code 200)
     * or Bad Request (status code 400)
     * or Internal Server Error (status code 500)
     */
    @Override
    public ResponseEntity<SuccessMessageRepresentation> excluirItemProjeto(Long pIdItemProjeto, String pAcessToken, Long pIdUsuarioLogado) {
        try {
            return ResponseEntity.ok().body(itemProjetoService.excluir(pAcessToken, pIdUsuarioLogado, pIdItemProjeto));
        } catch (Exception ex) {
            return (ResponseEntity) ErrorFormat.convertToEntity(ex);
        }
    }

    /**
     * POST /item-projeto : Inclui um novo item de projeto
     * Inclui um novo item de projeto
     *
     * @param pAcessToken      (required)
     * @param pIdUsuarioLogado (required)
     * @param pItemProjetoResponseRepresentation (optional)
     * @return successful operation (status code 200)
     * or Bad Request (status code 400)
     * or Internal Server Error (status code 500)
     */
    @Override
    public ResponseEntity<ItemProjetoResponseRepresentation> incluirItemProjeto(String pAcessToken, Long pIdUsuarioLogado
            , ItemProjetoResponseRepresentation pItemProjetoResponseRepresentation) {
        try {
            return ResponseEntity.ok().body(itemProjetoService.incluirItemProjeto(pAcessToken, pIdUsuarioLogado,
                    pItemProjetoResponseRepresentation));
        } catch (Exception ex) {
            return (ResponseEntity) ErrorFormat.convertToEntity(ex);
        }
    }

    /**
     * GET /item-projeto : Listar todos os itens de projeto
     * Lista todos os itens de projeto
     *
     * @param pIdItemProjeto (required)
     * @param pAcessToken      (required)
     * @param pIdUsuarioLogado (required)
     * @return successful operation (status code 200)
     * or Bad Request (status code 400)
     * or Internal Server Error (status code 500)
     */
    @Override
    public ResponseEntity<List<ItemProjetoResponseRepresentation>> listarItensProjeto(Long pIdItemProjeto
            , String pAcessToken, Long pIdUsuarioLogado) {
        try {
            return ResponseEntity.ok().body(itemProjetoService.listarPorProjeto(pAcessToken, pIdUsuarioLogado,
                    pIdItemProjeto));
        } catch (Exception ex) {
            return (ResponseEntity) ErrorFormat.convertToEntity(ex);
        }
    }
}
