package com.facilit.kanban_backend.service;

import com.baeldung.openapi.model.LoginResponseRepresentation;
import com.baeldung.openapi.model.LoginUsuarioRequestRepresentation;
import com.baeldung.openapi.model.SuccessMessageRepresentation;
import com.baeldung.openapi.model.UsuarioRepresentation;
import com.facilit.kanban_backend.exception.EmailEmUsoException;
import com.facilit.kanban_backend.exception.UsuarioInvalido;
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

    public SuccessMessageRepresentation cadastrarUsuario(UsuarioRepresentation pUsuarioRepresentation) throws EmailEmUsoException {

        if (!usuarioRepository.findByEmail(pUsuarioRepresentation.getEmail()).isEmpty()) {
            throw new EmailEmUsoException();
        }

        usuarioRepository.save(UsuarioMapper.usuarioMapper(pUsuarioRepresentation));
        return SuccessMessageRepresentation.builder()
                .message("O usuário " + pUsuarioRepresentation.getNome() + " foi cadastrado com sucesso!")
                .code(0)
                .build();
    }

    public LoginResponseRepresentation loginUsuario(LoginUsuarioRequestRepresentation pLoginUsuarioRequestRepresentation) throws UsuarioInvalido {
        // Lógica de autenticação (exemplo simplificado)
        var usuarioOpt = usuarioRepository.findByEmailAndPassword(
                pLoginUsuarioRequestRepresentation.getEmail(),
                pLoginUsuarioRequestRepresentation.getPassword()
        );

        if (usuarioOpt != null) {
            return LoginResponseRepresentation.builder()
                    .token("token-de-exemplo")
                    .id(usuarioOpt.getId())
                    .nome(usuarioOpt.getNome())
                    .email(usuarioOpt.getEmail())
                    .build();
        } else {
                throw new UsuarioInvalido();
        }
    }
}
