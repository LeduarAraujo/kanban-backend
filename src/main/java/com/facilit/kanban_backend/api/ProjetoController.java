package com.facilit.kanban_backend.api;

import com.baeldung.openapi.api.ProjetosApi;
import com.baeldung.openapi.model.ProjetoDTORepresentation;
import com.baeldung.openapi.model.ProjetoRepresentation;
import com.baeldung.openapi.model.TransicaoStatusDTORepresentation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.context.request.NativeWebRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class ProjetoController implements ProjetosApi {

//    private final ProjetoService projetoService;


    @Override
    public Optional<NativeWebRequest> getRequest() {
        return ProjetosApi.super.getRequest();
    }

    @Override
    public ResponseEntity<List<ProjetoRepresentation>> projetosGet() {
        return ResponseEntity.ok().body(new ArrayList<>());
    }

    @Override
    public ResponseEntity<Void> projetosIdDelete(Integer id) {
        return ProjetosApi.super.projetosIdDelete(id);
    }

    @Override
    public ResponseEntity<ProjetoRepresentation> projetosIdPut(Integer id, ProjetoDTORepresentation projetoDTORepresentation) {
        return ProjetosApi.super.projetosIdPut(id, projetoDTORepresentation);
    }

    @Override
    public ResponseEntity<ProjetoRepresentation> projetosIdTransicoesPost(Integer id, TransicaoStatusDTORepresentation transicaoStatusDTORepresentation) {
        return ProjetosApi.super.projetosIdTransicoesPost(id, transicaoStatusDTORepresentation);
    }

    @Override
    public ResponseEntity<ProjetoRepresentation> projetosPost(ProjetoDTORepresentation projetoDTORepresentation) {
        return ProjetosApi.super.projetosPost(projetoDTORepresentation);
    }

    @Override
    public ResponseEntity<List<ProjetoRepresentation>> projetosStatusStatusGet(String status) {
        return ResponseEntity.ok().body(new ArrayList<>());
    }
}
