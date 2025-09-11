package com.facilit.kanban_backend.web.controller;

import com.facilit.kanban_backend.domain.enums.StatusProjetoEnum;
import com.facilit.kanban_backend.service.ProjetoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/indicadores")
@RequiredArgsConstructor
@Tag(name = "Indicadores", description = "API para consulta de indicadores e métricas dos projetos")
public class IndicadorController {

    private final ProjetoService projetoService;

    @Operation(summary = "Quantidade de projetos por status", description = "Retorna a quantidade de projetos agrupados por status")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Indicadores retornados com sucesso")
    })
    @GetMapping("/projetos/quantidade-por-status")
    public ResponseEntity<List<Object[]>> quantidadeProjetosPorStatus() {
        return ResponseEntity.ok(projetoService.quantidadeProjetosPorStatus());
    }

    @Operation(summary = "Média de dias de atraso por status", description = "Retorna a média de dias de atraso para um status específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Média calculada com sucesso")
    })
    @GetMapping("/projetos/media-atraso-por-status")
    public ResponseEntity<Double> mediaDiasAtrasoPorStatus(
            @Parameter(description = "Status do projeto para calcular a média") @RequestParam StatusProjetoEnum status) {
        Double media = projetoService.mediaDiasAtrasoPorStatus(status);
        return ResponseEntity.ok(media != null ? media : 0.0);
    }
}
