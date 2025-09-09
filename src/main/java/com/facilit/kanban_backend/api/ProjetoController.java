package com.facilit.kanban_backend.api;

import com.baeldung.openapi.api.ApiApi;

import com.baeldung.openapi.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class ProjetoController implements ApiApi{

    @Override
    public Optional<NativeWebRequest> getRequest() {
        return ApiApi.super.getRequest();
    }

    @Override
    public ResponseEntity<SucessMessageRepresentation> cadastroFuncionario(String tokenJwt, Long idFuncionario, CadastroFuncionarioRequestRepresentation cadastroFuncionarioRequestRepresentation) {
        return ApiApi.super.cadastroFuncionario(tokenJwt, idFuncionario, cadastroFuncionarioRequestRepresentation);
    }

    @Override
    public ResponseEntity<SucessMessageRepresentation> incluirEspaco(String tokenJwt, Long idFuncionario, String nmEspaco, String dsEndereco, String instagram, MultipartFile logoEspaco) {
        return ApiApi.super.incluirEspaco(tokenJwt, idFuncionario, nmEspaco, dsEndereco, instagram, logoEspaco);
    }

    @Override
    public ResponseEntity<SigninUsuarioResponseRepresentation> iniciarSessao(SigninUsuarioRequestRepresentation signinUsuarioRequestRepresentation) {
        return ApiApi.super.iniciarSessao(signinUsuarioRequestRepresentation);
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public ResponseEntity<ListaEspacosResponseRepresentation> listarEspacos(String tokenJwt, Long idFuncionario) {

        return ResponseEntity.ok().body(new ListaEspacosResponseRepresentation());
    }
}
