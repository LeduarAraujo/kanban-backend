package com.facilit.kanban_backend.service;

import com.baeldung.openapi.model.*;
import com.facilit.kanban_backend.domain.entity.ResponsavelEntity;
import com.facilit.kanban_backend.domain.entity.UsuarioEntity;
import com.facilit.kanban_backend.exception.EmailEmUsoException;
import com.facilit.kanban_backend.mapper.ResponsavelMapper;
import com.facilit.kanban_backend.mapper.UsuarioMapper;
import com.facilit.kanban_backend.repository.CargoRepository;
import com.facilit.kanban_backend.repository.ProjetoRepository;
import com.facilit.kanban_backend.repository.ResponsavelRepository;
import com.facilit.kanban_backend.repository.SecretariaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class ResponsavelService {

    private final UsuarioService usuarioService;

    private final ResponsavelRepository responsavelRepository;
    private final ProjetoRepository projetoRepository;
    private final SecretariaRepository secretariaRepository;
    private final CargoRepository cargoRepository;

    public ListaResponsavelResponseRepresentation listarResponsaveis(Pageable pPageable) {
        Page<ResponsavelEntity> retornoConsulta = responsavelRepository.findAll(pPageable);

        return ListaResponsavelResponseRepresentation.builder()
                .content(retornoConsulta.map(ResponsavelMapper::toRepresentation).getContent())
                .pageable(ListaProjetoResponsePageableRepresentation.builder()
                        .pageNumber(retornoConsulta.getNumber())
                        .pageSize(retornoConsulta.getTotalPages())
                        .offset((int) retornoConsulta.getPageable().getOffset()).build())
                .totalElements((int) retornoConsulta.getTotalElements())
                .totalPages(retornoConsulta.getTotalPages())
                .last(retornoConsulta.isLast())
                .first(retornoConsulta.isFirst())
                .numberOfElements(retornoConsulta.getNumberOfElements())
                .size(retornoConsulta.getSize())
                .build();
    }

    public SuccessMessageRepresentation excluirResponsavel(Long pIdResponsavel) {
        // Verifica se o responsável está associado a algum projeto
        if (projetoRepository.findByResponsavelId(pIdResponsavel).isPresent()) {
            throw new IllegalArgumentException("Não é possível excluir o responsável, pois ele está associado a projetos.");
        }

        responsavelRepository.deleteById(pIdResponsavel);
        return SuccessMessageRepresentation.builder()
                .message("Responsável excluído com sucesso")
                .build();
    }

    public ResponsavelRepresentation cadastrarResponsavel(CadastroResponsavelBodyRepresentation pCadastroResponsavelBodyRepresentation) throws EmailEmUsoException {
        ResponsavelEntity responsavelEntity = new ResponsavelEntity();
        UsuarioEntity usuario = UsuarioMapper.toEntity(pCadastroResponsavelBodyRepresentation.getUsuario());
        if (pCadastroResponsavelBodyRepresentation.getUsuario().getId() == null) {
            usuario = usuarioService.cadastroUsuarioRetornoEntity(CadastrarUsuarioRequestRepresentation.builder()
                    .nome(pCadastroResponsavelBodyRepresentation.getUsuario().getNome())
                    .email(pCadastroResponsavelBodyRepresentation.getUsuario().getEmail())
                    .senha(pCadastroResponsavelBodyRepresentation.getUsuario().getSenha())
                    .build());
        }

        responsavelEntity.setUsuario(usuario);
        responsavelEntity.setSecretaria(secretariaRepository.findById(
                pCadastroResponsavelBodyRepresentation.getSecretariaId()).get());
        responsavelEntity.setCargo(cargoRepository.findById(
                pCadastroResponsavelBodyRepresentation.getCargoId()).get());

        return ResponsavelMapper.toRepresentation(responsavelRepository.save(responsavelEntity));
    }

    public ResponsavelRepresentation atualizarResponsavelPorId(Long pIdResponsavel
            , AtualizarResponsavelBodyRepresentation pAtualizarResponsavelBodyRepresentation) {
        ResponsavelEntity responsavelEntity = responsavelRepository.findById(pIdResponsavel)
                .orElseThrow(() -> new IllegalArgumentException("Responsável não encontrado"));

        responsavelEntity.setSecretaria(secretariaRepository.findById(
                pAtualizarResponsavelBodyRepresentation.getSecretariaId()).get());
        responsavelEntity.setCargo(cargoRepository.findById(
                pAtualizarResponsavelBodyRepresentation.getCargoId()).get());

        return ResponsavelMapper.toRepresentation(responsavelRepository.save(responsavelEntity));
    }
}
