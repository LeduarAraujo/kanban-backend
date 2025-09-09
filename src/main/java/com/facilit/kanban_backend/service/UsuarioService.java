package com.facilit.kanban_backend.service;

import com.baeldung.openapi.model.SucessMessageRepresentation;
import com.baeldung.openapi.model.UsuarioRepresentation;
import com.facilit.kanban_backend.exception.EmailEmUsoException;
import com.facilit.kanban_backend.mapper.UsuarioMapper;
import com.facilit.kanban_backend.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public SucessMessageRepresentation cadastrarUsuario(UsuarioRepresentation pUsuarioRepresentation) throws EmailEmUsoException {

        if (!usuarioRepository.findByEmail(pUsuarioRepresentation.getEmail()).isEmpty()) {
            throw new EmailEmUsoException();
        }

        usuarioRepository.save(UsuarioMapper.usuarioMapper(pUsuarioRepresentation));
        return SucessMessageRepresentation.builder()
                .message("O usuário " + pUsuarioRepresentation.getNome() + " foi cadastrado com sucesso!")
                .code(0)
                .build();
    }
}
