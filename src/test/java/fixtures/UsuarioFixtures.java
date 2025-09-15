package fixtures;

import com.baeldung.openapi.model.CadastrarUsuarioRequestRepresentation;
import com.baeldung.openapi.model.LoginUsuarioRequestRepresentation;
import com.baeldung.openapi.model.UsuarioRepresentation;
import com.facilit.kanban_backend.domain.entity.UsuarioEntity;

public class UsuarioFixtures {

    public static UsuarioRepresentation criarUsuario() {
        return UsuarioRepresentation.builder()
                .nome("Usuário Teste")
                .email("teste@teste.com")
                .senha("$as7d6-da9#08sd")
                .build();
    }

    public static UsuarioEntity criarUsuarioEntity() {
        UsuarioEntity usuarioEntity = new UsuarioEntity();
        usuarioEntity.setNome("Usuário Existente");
        usuarioEntity.setEmail("teste@teste.com");
        usuarioEntity.setSenha("$as7d6-da9#08sd");

        return usuarioEntity;
    }

    public static UsuarioEntity criarUsuarioEntityExistente() {
        UsuarioEntity usuarioExistente = new UsuarioEntity();
        usuarioExistente.setId(1L);
        usuarioExistente.setNome("Usuário Existente");
        usuarioExistente.setEmail("teste@teste.com");
        usuarioExistente.setSenha("$as7d6-da9#08sd");

        return usuarioExistente;
    }

    public static CadastrarUsuarioRequestRepresentation criarCadastrarUsuarioRequestRepresentation() {
        return CadastrarUsuarioRequestRepresentation.builder()
                .nome("Usuário Teste")
                .email("teste@teste.com")
                .senha("$as7d6-da9#08sd")
                .build();
    }

    public static LoginUsuarioRequestRepresentation criarLoginUsuarioRequestRepresentation() {
        return LoginUsuarioRequestRepresentation.builder()
                .email("teste@teste.com")
                .senha("$as7d6-da9#08sd")
                .build();
    }
}
