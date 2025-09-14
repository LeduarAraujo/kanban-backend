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
}
