package com.facilit.kanban_backend.web.controller;

import com.baeldung.openapi.api.UsuarioApi;
import com.baeldung.openapi.model.SucessMessageRepresentation;
import com.baeldung.openapi.model.UsuarioRepresentation;

import com.facilit.kanban_backend.service.UsuarioService;
import com.facilit.kanban_backend.utils.ErrorFormat;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UsuarioController implements UsuarioApi {

    private final UsuarioService usuarioService;

    /**
     * POST /usuario : realiza o cadastro do usuario
     * realiza o cadastro do usuario para permitir o uso dos usuarios
     *
     * @param usuarioRepresentation (required)
     * @return Successful operation (status code 200)
     * or Bad Request (status code 400)
     * or Unauthorized (status code 401)
     * or Internal Server Error (status code 500)
     */
    @Override
    public ResponseEntity<SucessMessageRepresentation> cadastrarUsuario(UsuarioRepresentation usuarioRepresentation) {
        try {
            return ResponseEntity.ok().body(usuarioService.cadastrarUsuario(usuarioRepresentation));
        } catch (Exception ex) {
            return (ResponseEntity) ErrorFormat.convertToEntity(ex);
        }
    }
}
