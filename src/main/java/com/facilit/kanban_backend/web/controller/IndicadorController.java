package com.facilit.kanban_backend.web.controller;

import com.baeldung.openapi.api.IndicadoresApi;
import com.baeldung.openapi.model.QuantidadeProjetosPorStatus200ResponseInnerRepresentation;
import com.baeldung.openapi.model.StatusProjetoRepresentation;
import com.facilit.kanban_backend.service.ProjetoService;
import com.facilit.kanban_backend.utils.ErrorFormat;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class IndicadorController implements IndicadoresApi {

    private final ProjetoService projetoService;

    /**
     * GET /indicadores/projetos/media-atraso-por-status : Média de dias de atraso por status
     * Retorna a média de dias de atraso para um status específico
     *
     * @param pStatusProjetoRepresentation          Status do projeto para calcular a média (required)
     * @param pAcessToken      (required)
     * @param pIdUsuarioLogado (required)
     * @return Média calculada com sucesso (status code 200)
     * or Bad Request (status code 400)
     * or Internal Server Error (status code 500)
     */
    @Override
    public ResponseEntity<Double> mediaDiasAtrasoPorStatus(StatusProjetoRepresentation pStatusProjetoRepresentation, String pAcessToken, Long pIdUsuarioLogado) {
        try {
            Double media = projetoService.mediaDiasAtrasoPorStatus(pAcessToken, pIdUsuarioLogado, pStatusProjetoRepresentation);
            return ResponseEntity.ok(media != null ? media : 0.0);
        } catch (Exception ex) {
            return (ResponseEntity) ErrorFormat.convertToEntity(ex);
        }
    }

    /**
     * GET /indicadores/projetos/quantidade-por-status : Quantidade de projetos por status
     * Retorna a quantidade de projetos agrupados por status
     *
     * @param pAcessToken      (required)
     * @param pIdUsuarioLogado (required)
     * @return Indicadores retornados com sucesso (status code 200)
     * or Internal Server Error (status code 500)
     */
    @Override
    public ResponseEntity<List<QuantidadeProjetosPorStatus200ResponseInnerRepresentation>> quantidadeProjetosPorStatus(String pAcessToken, Long pIdUsuarioLogado) {
        try {
            return ResponseEntity.ok(projetoService.quantidadeProjetosPorStatus(pAcessToken, pIdUsuarioLogado));
        } catch (Exception ex) {
            return (ResponseEntity) ErrorFormat.convertToEntity(ex);
        }
    }
}
