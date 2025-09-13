package com.facilit.kanban_backend.web.controller;

import com.baeldung.openapi.api.SecretariaApi;
import com.baeldung.openapi.model.CadastrarSecretariaRequestRepresentation;
import com.baeldung.openapi.model.SecretariaRepresentation;
import com.baeldung.openapi.model.SuccessMessageRepresentation;
import com.facilit.kanban_backend.service.SecretariaService;
import com.facilit.kanban_backend.utils.ErrorFormat;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class SecretariaController implements SecretariaApi {

    private final SecretariaService secretariaService;

    /**
     * PUT /secretaria/{id} : Atualiza uma secretaria existente
     *
     * @param pIdSecretaria    ID da secretaria a ser atualizada (required)
     * @param pCadastrarSecretariaRequestRepresentation (required)
     * @return Successful operation (status code 200)
     * or Bad Request (status code 400)
     * or Unauthorized (status code 401)
     * or Internal Server Error (status code 500)
     */
    @Override
    public ResponseEntity<SuccessMessageRepresentation> atualizarSecretariaPorId(Long pIdSecretaria
            , CadastrarSecretariaRequestRepresentation pCadastrarSecretariaRequestRepresentation) {
        try {
            return ResponseEntity.ok().body(secretariaService.atualizarSecretariaPorId(pIdSecretaria
                    , pCadastrarSecretariaRequestRepresentation));
        } catch (Exception ex) {
            return (ResponseEntity) ErrorFormat.convertToEntity(ex);
        }
    }

    /**
     * GET /secretaria/{id} : Busca uma secretaria pelo ID
     *
     * @param pIdSecretaria ID da secretaria a ser buscada (required)
     * @return Successful operation (status code 200)
     * or Bad Request (status code 400)
     * or Unauthorized (status code 401)
     * or Not Found (status code 404)
     * or Internal Server Error (status code 500)
     */
    @Override
    public ResponseEntity<SecretariaRepresentation> buscarSecretariaPorId(Long pIdSecretaria) {
        try{
            return ResponseEntity.ok().body(secretariaService.buscarSecretariaPorId(pIdSecretaria));
        } catch (Exception ex) {
            return (ResponseEntity) ErrorFormat.convertToEntity(ex);
        }
    }

    /**
     * POST /secretaria : Cadastra uma nova secretaria
     *
     * @param pCadastrarSecretariaRequestRepresentation (required)
     * @return Successful operation (status code 200)
     * or Bad Request (status code 400)
     * or Unauthorized (status code 401)
     * or Internal Server Error (status code 500)
     */
    @Override
    public ResponseEntity<SuccessMessageRepresentation> cadastrarSecretaria(CadastrarSecretariaRequestRepresentation pCadastrarSecretariaRequestRepresentation) {
        try{
            return ResponseEntity.ok().body(secretariaService.cadastrarSecretaria(pCadastrarSecretariaRequestRepresentation));
        } catch (Exception ex) {
            return (ResponseEntity) ErrorFormat.convertToEntity(ex);
        }
    }

    /**
     * DELETE /secretaria/{id} : Exclui uma secretaria existente
     *
     * @param id ID da secretaria a ser excluída (required)
     * @return Successful operation (status code 200)
     * or Bad Request (status code 400)
     * or Unauthorized (status code 401)
     * or Internal Server Error (status code 500)
     */
    @Override
    public ResponseEntity<SuccessMessageRepresentation> excluirSecretaria(Long id) {
        try{
            return ResponseEntity.ok().body(secretariaService.excluirSecretaria(id));
        } catch (Exception ex) {
            return (ResponseEntity) ErrorFormat.convertToEntity(ex);
        }
    }

    /**
     * GET /secretaria : Lista todas as secretarias
     *
     * @return Successful operation (status code 200)
     * or Bad Request (status code 400)
     * or Unauthorized (status code 401)
     * or Internal Server Error (status code 500)
     */
    @Override
    public ResponseEntity<List<SecretariaRepresentation>> listarSecretarias() {
        try{
            return ResponseEntity.ok().body(secretariaService.listarSecretarias());
        } catch (Exception ex) {
            return (ResponseEntity) ErrorFormat.convertToEntity(ex);
        }
    }
}
