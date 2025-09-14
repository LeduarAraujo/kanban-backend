package com.facilit.kanban_backend.mapper;

import com.baeldung.openapi.model.CadastrarUsuarioRequestRepresentation;
import com.baeldung.openapi.model.UsuarioRepresentation;
import com.facilit.kanban_backend.domain.entity.UsuarioEntity;

public class UsuarioMapper {

    public static UsuarioEntity usuarioMapper(CadastrarUsuarioRequestRepresentation pCadastrarUsuarioRequestRepresentation) {
        UsuarioEntity usuarioEntity = new UsuarioEntity();
        usuarioEntity.setNome(pCadastrarUsuarioRequestRepresentation.getNome());
        usuarioEntity.setEmail(pCadastrarUsuarioRequestRepresentation.getEmail());
        usuarioEntity.setSenha(pCadastrarUsuarioRequestRepresentation.getSenha());

        return  usuarioEntity;
    }

    public static UsuarioRepresentation toRepresentation(UsuarioEntity pUsuarioEntity) {
        if (pUsuarioEntity == null) {
            return null;
        }

        UsuarioRepresentation usuarioRepresentation = new UsuarioRepresentation();
        usuarioRepresentation.setId(pUsuarioEntity.getId());
        usuarioRepresentation.setNome(pUsuarioEntity.getNome());
        usuarioRepresentation.setEmail(pUsuarioEntity.getEmail());
        usuarioRepresentation.senha(pUsuarioEntity.getSenha());

        return usuarioRepresentation;
    }

    public static UsuarioEntity toEntity(UsuarioRepresentation pUsuarioRepresentation) {
        if (pUsuarioRepresentation == null) {
            return null;
        }

        UsuarioEntity usuarioEntity = new UsuarioEntity();
        usuarioEntity.setId(pUsuarioRepresentation.getId());
        usuarioEntity.setNome(pUsuarioRepresentation.getNome());
        usuarioEntity.setEmail(pUsuarioRepresentation.getEmail());
        usuarioEntity.setSenha(pUsuarioRepresentation.getSenha());

        return usuarioEntity;
    }
}
