package com.facilit.kanban_backend.mapper;

import com.baeldung.openapi.model.UsuarioRepresentation;
import com.facilit.kanban_backend.domain.entity.UsuarioEntity;

public class UsuarioMapper {

    public static UsuarioEntity usuarioMapper(UsuarioRepresentation pUsuarioRepresentation) {
        UsuarioEntity usuarioEntity = new UsuarioEntity();

        usuarioEntity.setId(pUsuarioRepresentation.getId());
        usuarioEntity.setNome(pUsuarioRepresentation.getNome());
        usuarioEntity.setEmail(pUsuarioRepresentation.getEmail());
        usuarioEntity.setPassword(pUsuarioRepresentation.getPassword());

        return  usuarioEntity;
    }
}
