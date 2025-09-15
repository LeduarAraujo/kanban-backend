package fixtures;

import com.baeldung.openapi.model.CadastrarSecretariaRequestRepresentation;
import com.facilit.kanban_backend.domain.entity.SecretariaEntity;

public class SecretariaFixtures {

    public static SecretariaEntity criarSecretariaEntity_comId() {
        SecretariaEntity secretariaEntity = new SecretariaEntity();
        secretariaEntity.setId(1L);
        secretariaEntity.setNmSecretaria("Secretaria Teste");
        secretariaEntity.setDescricao("Descrição da Secretaria Teste");

        return secretariaEntity;
    }

    public static SecretariaEntity criarSecretariaEntity_semId() {
        SecretariaEntity secretariaEntity = new SecretariaEntity();
        secretariaEntity.setNmSecretaria("Secretaria Teste");
        secretariaEntity.setDescricao("Descrição da Secretaria Teste");

        return secretariaEntity;
    }

    public static CadastrarSecretariaRequestRepresentation criarCadastroSecretariaRequestRepresentation() {
        CadastrarSecretariaRequestRepresentation request = new CadastrarSecretariaRequestRepresentation();
        request.setNmSecretaria("Secretaria Teste");
        request.setDescricao("Descrição da Secretaria Teste");

        return request;
    }
}
