package com.facilit.kanban_backend.service;

import com.baeldung.openapi.model.*;
import com.facilit.kanban_backend.domain.entity.UsuarioEntity;
import com.facilit.kanban_backend.exception.EmailEmUsoException;
import com.facilit.kanban_backend.exception.UsuarioInvalido;
import com.facilit.kanban_backend.mapper.UsuarioMapper;
import com.facilit.kanban_backend.repository.UsuarioRepository;
import com.facilit.kanban_backend.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    @Transactional(rollbackFor = Exception.class)
    public SuccessMessageRepresentation cadastrarUsuario(CadastrarUsuarioRequestRepresentation pCadastrarUsuarioRequestRepresentation)
            throws EmailEmUsoException {

        this.cadastroUsuarioRetornoEntity(pCadastrarUsuarioRequestRepresentation);

        return SuccessMessageRepresentation.builder()
                .message("O usuário " + pCadastrarUsuarioRequestRepresentation.getNome()
                        + " foi cadastrado com sucesso!")
                .code(0)
                .build();
    }

    @Transactional(rollbackFor = Exception.class)
    public UsuarioEntity cadastroUsuarioRetornoEntity(CadastrarUsuarioRequestRepresentation pCadastrarUsuarioRequestRepresentation)
            throws EmailEmUsoException {

        if (!usuarioRepository.findByEmail(pCadastrarUsuarioRequestRepresentation.getEmail()).isEmpty()) {
            throw new EmailEmUsoException();
        }

        return usuarioRepository.save(UsuarioMapper.usuarioMapper(pCadastrarUsuarioRequestRepresentation));
    }

    public LoginResponseRepresentation loginUsuario(LoginUsuarioRequestRepresentation pLoginUsuarioRequestRepresentation) throws UsuarioInvalido {
        var usuarioOpt = usuarioRepository.findByEmailAndSenha(
                pLoginUsuarioRequestRepresentation.getEmail(),
                pLoginUsuarioRequestRepresentation.getSenha()
        );

        if (usuarioOpt != null) {
            String token = JwtUtil.generateToken(usuarioOpt.getId().toString());

            return LoginResponseRepresentation.builder()
                    .token(token)
                    .id(usuarioOpt.getId())
                    .nome(usuarioOpt.getNome())
                    .email(usuarioOpt.getEmail())
                    .build();
        }
        throw new UsuarioInvalido();
    }
}
