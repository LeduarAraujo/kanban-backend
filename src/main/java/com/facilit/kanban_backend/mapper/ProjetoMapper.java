package com.facilit.kanban_backend.mapper;

import com.baeldung.openapi.model.ProjetoRepresentation;
import com.facilit.kanban_backend.domain.Projeto;
import java.util.ArrayList;
import java.util.List;

public class ProjetoMapper {


    public static List<ProjetoRepresentation> dtoToRepresentation(List<Projeto> listar) {
        List<ProjetoRepresentation> retorno = new ArrayList<>();

        listar.stream().forEach(projeto -> {
            ProjetoRepresentation representation = new ProjetoRepresentation();
            representation.setId(Integer.valueOf(projeto.getId().toString()));
            representation.setNome(projeto.getNome());
            representation.setStatus(projeto.getStatus().toString());
            representation.setResponsavelId(1);
        });

        return retorno;
    }
}
