package com.facilit.kanban_backend.api;

import com.baeldung.openapi.api.UsuarioApi;
import com.baeldung.openapi.model.SucessMessageRepresentation;
import com.baeldung.openapi.model.UsuarioRepresentation;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UsuarioController implements UsuarioApi {
    
    @Override
    public ResponseEntity<SucessMessageRepresentation> cadastrarUsuario(
            @Valid UsuarioRepresentation usuarioRepresentation) {
        // TODO Auto-generated method stub
        return ResponseEntity.ok().body(new SucessMessageRepresentation().message("Usuário cadastrado com sucesso"));
    }
    
}
