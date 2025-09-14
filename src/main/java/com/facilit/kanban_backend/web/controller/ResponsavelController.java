package com.facilit.kanban_backend.web.controller;

import com.baeldung.openapi.api.ResponsavelApi;
import com.baeldung.openapi.model.AtualizarResponsavelBodyRepresentation;
import com.baeldung.openapi.model.CadastroResponsavelBodyRepresentation;
import com.baeldung.openapi.model.ResponsavelRepresentation;
import com.baeldung.openapi.model.SuccessMessageRepresentation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ResponsavelController  implements ResponsavelApi {

    /**
     * GET /responsavel : Lista todos os responsáveis
     *
     * @return Successful operation (status code 200)
     * or Bad Request (status code 400)
     * or Unauthorized (status code 401)
     * or Internal Server Error (status code 500)
     */
    @Override
    public ResponseEntity<List<ResponsavelRepresentation>> listarResponsaveis() {
        return ResponsavelApi.super.listarResponsaveis();
    }

    /**
     * DELETE /responsavel : Exclui um responsável existente
     *
     * @param id ID do responsável a ser excluído (required)
     * @return Successful operation (status code 200)
     * or Bad Request (status code 400)
     * or Unauthorized (status code 401)
     * or Internal Server Error (status code 500)
     */
    @Override
    public ResponseEntity<SuccessMessageRepresentation> excluirResponsavel(Long id) {
        return ResponsavelApi.super.excluirResponsavel(id);
    }

    /**
     * POST /responsavel : Cadastra um novo responsável
     *
     * @param cadastroResponsavelBodyRepresentation (required)
     * @return Successful operation (status code 200)
     * or Bad Request (status code 400)
     * or Unauthorized (status code 401)
     * or Internal Server Error (status code 500)
     */
    @Override
    public ResponseEntity<ResponsavelRepresentation> cadastrarResponsavel(CadastroResponsavelBodyRepresentation cadastroResponsavelBodyRepresentation) {
        return ResponsavelApi.super.cadastrarResponsavel(cadastroResponsavelBodyRepresentation);
    }

    /**
     * PUT /responsavel : Atualiza um responsável existente
     *
     * @param id                                     ID do responsável a ser atualizado (required)
     * @param atualizarResponsavelBodyRepresentation (required)
     * @return Successful operation (status code 200)
     * or Bad Request (status code 400)
     * or Unauthorized (status code 401)
     * or Internal Server Error (status code 500)
     */
    @Override
    public ResponseEntity<List<ResponsavelRepresentation>> atualizarResponsavelPorId(Long id, AtualizarResponsavelBodyRepresentation atualizarResponsavelBodyRepresentation) {
        return ResponsavelApi.super.atualizarResponsavelPorId(id, atualizarResponsavelBodyRepresentation);
    }
}
