package com.facilit.kanban_backend.web.controller;

import com.facilit.kanban_backend.domain.entity.ProjetoEntity;
import com.facilit.kanban_backend.domain.enums.StatusProjetoEnum;
import com.facilit.kanban_backend.service.ProjetoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/projetos")
@RequiredArgsConstructor
@Tag(name = "Projetos", description = "API para gerenciamento de projetos")
public class ProjetoController {

    private final ProjetoService projetoService;

    @Operation(summary = "Criar projeto", description = "Cria um novo projeto com status e métricas calculadas automaticamente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Projeto criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    @PostMapping
    public ResponseEntity<ProjetoEntity> criar(@RequestBody ProjetoEntity projeto) {
        return ResponseEntity.ok(projetoService.salvar(projeto));
    }

    @Operation(summary = "Listar projetos", description = "Lista todos os projetos com paginação")
    @GetMapping
    public ResponseEntity<Page<ProjetoEntity>> listar(Pageable pageable) {
        return ResponseEntity.ok(projetoService.listar(pageable));
    }

    @Operation(summary = "Buscar projeto por ID", description = "Retorna um projeto específico pelo ID")
    @GetMapping("/{id}")
    public ResponseEntity<ProjetoEntity> buscar(@Parameter(description = "ID do projeto") @PathVariable Long id) {
        return ResponseEntity.ok(projetoService.buscarOuFalhar(id));
    }

    @Operation(summary = "Atualizar projeto", description = "Atualiza um projeto existente")
    @PutMapping("/{id}")
    public ResponseEntity<ProjetoEntity> atualizar(@Parameter(description = "ID do projeto") @PathVariable Long id, @RequestBody ProjetoEntity projeto) {
        ProjetoEntity existente = projetoService.buscarOuFalhar(id);
        projeto.setId(existente.getId());
        return ResponseEntity.ok(projetoService.salvar(projeto));
    }

    @Operation(summary = "Excluir projeto", description = "Exclui um projeto")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@Parameter(description = "ID do projeto") @PathVariable Long id) {
        projetoService.buscarOuFalhar(id);
        // Implementar exclusão se necessário
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Mover status do projeto", description = "Move o projeto para um novo status seguindo as regras de transição")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Status alterado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Transição inválida")
    })
    @PutMapping("/{id}/status")
    public ResponseEntity<ProjetoEntity> moverStatus(
            @Parameter(description = "ID do projeto") @PathVariable Long id, 
            @Parameter(description = "Novo status do projeto") @RequestParam("novoStatus") StatusProjetoEnum novoStatus) {
        return ResponseEntity.ok(projetoService.moverStatus(id, novoStatus));
    }

    @Operation(summary = "Listar projetos por status", description = "Lista projetos filtrados por status específico")
    @GetMapping("/por-status")
    public ResponseEntity<Page<ProjetoEntity>> listarPorStatus(
            @Parameter(description = "Status para filtrar") @RequestParam StatusProjetoEnum status, 
            Pageable pageable) {
        return ResponseEntity.ok(projetoService.listarPorStatus(status, pageable));
    }
}


