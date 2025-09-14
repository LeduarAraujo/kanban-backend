package com.facilit.kanban_backend.web.controller;

import com.baeldung.openapi.api.ResponsavelApi;
import com.baeldung.openapi.model.*;
import com.facilit.kanban_backend.service.ResponsavelService;
import com.facilit.kanban_backend.utils.ErrorFormat;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
public class ResponsavelController  implements ResponsavelApi {

    private final ResponsavelService responsavelService;

    /**
     * GET /responsavel : Lista todos os responsáveis
     *
     * @param pPage (optional, default to 0)
     * @param pSize (optional, default to 20)
     * @param pSort (optional, default to id)
     * @return Successful operation (status code 200)
     * or Bad Request (status code 400)
     * or Unauthorized (status code 401)
     * or Internal Server Error (status code 500)
     */
    @Override
    public ResponseEntity<ListaResponsavelResponseRepresentation> listarResponsaveis(Integer pPage, Integer pSize, String pSort) {
        try {
            Pageable pageable = PageRequest.of(pPage, pSize, Sort.by(pSort));
            return ResponseEntity.ok().body(responsavelService.listarResponsaveis(pageable));
        } catch (Exception ex) {
            return (ResponseEntity) ErrorFormat.convertToEntity(ex);
        }
    }

    /**
     * DELETE /responsavel : Exclui um responsável existente
     *
     * @param pIdResponsavel ID do responsável a ser excluído (required)
     * @return Successful operation (status code 200)
     * or Bad Request (status code 400)
     * or Unauthorized (status code 401)
     * or Internal Server Error (status code 500)
     */
    @Override
    public ResponseEntity<SuccessMessageRepresentation> excluirResponsavel(Long pIdResponsavel) {
        try {
            return ResponseEntity.ok().body(responsavelService.excluirResponsavel(pIdResponsavel));
        } catch (Exception ex) {
            return (ResponseEntity) ErrorFormat.convertToEntity(ex);
        }
    }

    /**
     * POST /responsavel : Cadastra um novo responsável
     *
     * @param pCadastroResponsavelBodyRepresentation (required)
     * @return Successful operation (status code 200)
     * or Bad Request (status code 400)
     * or Unauthorized (status code 401)
     * or Internal Server Error (status code 500)
     */
    @Override
    public ResponseEntity<ResponsavelRepresentation> cadastrarResponsavel(CadastroResponsavelBodyRepresentation pCadastroResponsavelBodyRepresentation) {
        try {
            return ResponseEntity.ok().body(responsavelService.cadastrarResponsavel(pCadastroResponsavelBodyRepresentation));
        } catch (Exception ex) {
            return (ResponseEntity) ErrorFormat.convertToEntity(ex);
        }
    }

    /**
     * PUT /responsavel : Atualiza um responsável existente
     *
     * @param pIdResponsavel                                     ID do responsável a ser atualizado (required)
     * @param pAtualizarResponsavelBodyRepresentation (required)
     * @return Successful operation (status code 200)
     * or Bad Request (status code 400)
     * or Unauthorized (status code 401)
     * or Internal Server Error (status code 500)
     */
    @Override
    public ResponseEntity<ResponsavelRepresentation> atualizarResponsavelPorId(Long pIdResponsavel, AtualizarResponsavelBodyRepresentation pAtualizarResponsavelBodyRepresentation) {
        try {
            return ResponseEntity.ok().body(responsavelService.atualizarResponsavelPorId(pIdResponsavel
                    , pAtualizarResponsavelBodyRepresentation));
        } catch (Exception ex) {
            return (ResponseEntity) ErrorFormat.convertToEntity(ex);
        }
    }
}
