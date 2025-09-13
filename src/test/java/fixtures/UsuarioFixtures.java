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
                .password("$as7d6-da9#08sd")
                .build();
    }

    public static UsuarioEntity criarUsuarioEntity() {
        UsuarioEntity usuarioExistente = new UsuarioEntity();
        usuarioExistente.setNome("Usuário Existente");
        usuarioExistente.setEmail("teste@teste.com");
        usuarioExistente.setPassword("$as7d6-da9#08sd");

        return usuarioExistente;
    }

    public static CadastrarUsuarioRequestRepresentation criarCadastrarUsuarioRequestRepresentation() {
        return CadastrarUsuarioRequestRepresentation.builder()
                .nome("Usuário Teste")
                .email("teste@teste.com")
                .password("$as7d6-da9#08sd")
                .build();
    }

    public static LoginUsuarioRequestRepresentation criarLoginUsuarioRequestRepresentation() {
        return LoginUsuarioRequestRepresentation.builder()
                .email("teste@teste.com")
                .password("$as7d6-da9#08sd")
                .build();
    }
}
