package com.facilit.kanban_backend.service;

import com.baeldung.openapi.model.CadastrarSecretariaRequestRepresentation;
import com.baeldung.openapi.model.SecretariaRepresentation;
import com.baeldung.openapi.model.SuccessMessageRepresentation;
import com.facilit.kanban_backend.domain.entity.SecretariaEntity;
import com.facilit.kanban_backend.exception.BusinessException;
import com.facilit.kanban_backend.repository.SecretariaRepository;
import com.facilit.kanban_backend.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SecretariaService {

    private final SecretariaRepository secretariaRepository;

    @Transactional(rollbackFor = Exception.class)
    public SuccessMessageRepresentation atualizarSecretariaPorId(String pAcessToken, Long pIdUsuarioLogado, Long pIdSecretaria
            , CadastrarSecretariaRequestRepresentation pCadastrarSecretariaRequestRepresentation) {
        // Validar token
        JwtUtil.validateToken(pAcessToken, pIdUsuarioLogado.toString());

        Optional<SecretariaEntity> secretariaEntity= secretariaRepository.findById(pIdSecretaria);
        if (secretariaEntity.isPresent()) {
            SecretariaEntity secretariaToUpdate = secretariaEntity.get();
            secretariaToUpdate.setNmSecretaria(pCadastrarSecretariaRequestRepresentation.getNmSecretaria());
            secretariaToUpdate.setDescricao(pCadastrarSecretariaRequestRepresentation.getDescricao());
            secretariaRepository.save(secretariaToUpdate);

            return SuccessMessageRepresentation.builder().build();
        } else {
            throw new BusinessException("Secretaria not found with id: " + pIdSecretaria);
        }
    }

    public SecretariaRepresentation buscarSecretariaPorId(String pAcessToken, Long pIdUsuarioLogado, Long pIdSecretaria) {
        // Validar token
        JwtUtil.validateToken(pAcessToken, pIdUsuarioLogado.toString());

        Optional<SecretariaEntity> secretariaEntity = secretariaRepository.findById(pIdSecretaria);
        if (secretariaEntity.isPresent()) {
            SecretariaEntity secretaria = secretariaEntity.get();
            SecretariaRepresentation secretariaRepresentation = new SecretariaRepresentation();
            secretariaRepresentation.setId(secretaria.getId());
            secretariaRepresentation.setNmSecretaria(secretaria.getNmSecretaria());
            secretariaRepresentation.setDescricao(secretaria.getDescricao());
            return secretariaRepresentation;
        } else {
            throw new BusinessException("Secretaria not found with id: " + pIdSecretaria);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public SuccessMessageRepresentation cadastrarSecretaria(String pAcessToken, Long pIdUsuarioLogado
            , CadastrarSecretariaRequestRepresentation pCadastrarSecretariaRequestRepresentation) {
        // Validar token
        JwtUtil.validateToken(pAcessToken, pIdUsuarioLogado.toString());

        SecretariaEntity novaSecretaria = new SecretariaEntity();
        novaSecretaria.setNmSecretaria(pCadastrarSecretariaRequestRepresentation.getNmSecretaria());
        novaSecretaria.setDescricao(pCadastrarSecretariaRequestRepresentation.getDescricao());
        secretariaRepository.save(novaSecretaria);

        return SuccessMessageRepresentation.builder()
                .message("Secretaria cadastrada com sucesso!")
                .code(0)
                .build();
    }

    @Transactional(rollbackFor = Exception.class)
    public SuccessMessageRepresentation excluirSecretaria(String pAcessToken, Long pIdUsuarioLogado, Long pIdSecretaria) {
        // Validar token
        JwtUtil.validateToken(pAcessToken, pIdUsuarioLogado.toString());

        Optional<SecretariaEntity> secretariaEntity = secretariaRepository.findById(pIdSecretaria);
        if (secretariaEntity.isPresent()) {
            secretariaRepository.deleteById(pIdSecretaria);
            return SuccessMessageRepresentation.builder()
                    .message("Secretaria excluída com sucesso!")
                    .code(0)
                    .build();
        } else {
            throw new BusinessException("Secretaria not found with id: " + pIdSecretaria);
        }
    }

    public List<SecretariaRepresentation> listarSecretarias(String pAcessToken, Long pIdUsuarioLogado) {
        // Validar token
        JwtUtil.validateToken(pAcessToken, pIdUsuarioLogado.toString());

        List<SecretariaEntity> secretarias = secretariaRepository.findAll();
        return secretarias.stream().map(secretaria -> {
            SecretariaRepresentation representation = new SecretariaRepresentation();
            representation.setId(secretaria.getId());
            representation.setNmSecretaria(secretaria.getNmSecretaria());
            representation.setDescricao(secretaria.getDescricao());
            return representation;
        }).toList();
    }
}
