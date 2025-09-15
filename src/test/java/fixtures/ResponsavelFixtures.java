package fixtures;

import com.baeldung.openapi.model.AtualizarResponsavelBodyRepresentation;
import com.baeldung.openapi.model.CadastroResponsavelBodyRepresentation;
import com.facilit.kanban_backend.domain.entity.ResponsavelEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public class ResponsavelFixtures {

    public static ResponsavelEntity criarResponsavelEntity_comId() {
        ResponsavelEntity responsavel = new ResponsavelEntity();
        responsavel.setId(1L);
        responsavel.setCargo(CargoFixtures.criarCargoEntity());
        responsavel.setUsuario(UsuarioFixtures.criarUsuarioEntityExistente());
        responsavel.setSecretaria(SecretariaFixtures.criarSecretariaEntity_comId());

        return responsavel;
    }

    public static ResponsavelEntity criarResponsavelEntity_semId() {
        ResponsavelEntity responsavel = new ResponsavelEntity();
        responsavel.setId(1L);
        responsavel.setCargo(CargoFixtures.criarCargoEntity());
        responsavel.setUsuario(UsuarioFixtures.criarUsuarioEntityExistente());
        responsavel.setSecretaria(SecretariaFixtures.criarSecretariaEntity_comId());

        return responsavel;
    }

    public static CadastroResponsavelBodyRepresentation criarCadastroResponsavelBodyRepresentation() {
        return CadastroResponsavelBodyRepresentation.builder()
                .usuario(UsuarioFixtures.criarUsuario())
                .cargoId(1L)
                .secretariaId(1L)
                .build();
    }

    public static Page<ResponsavelEntity> criarPageResponsavelEntity() {
        return new PageImpl<>(
                List.of(criarResponsavelEntity_comId()),
                PageRequest.of(0, 10),
                1
        );
    }

    public static AtualizarResponsavelBodyRepresentation criarAtualizarResponsavelBodyRepresentationAntigo() {
        return AtualizarResponsavelBodyRepresentation.builder()
                .cargoId(1L)
                .secretariaId(1L)
                .build();
    }
    public static AtualizarResponsavelBodyRepresentation criarAtualizarResponsavelBodyRepresentationNovo() {
        return AtualizarResponsavelBodyRepresentation.builder()
                .cargoId(2L)
                .secretariaId(2L)
                .build();
    }
}
