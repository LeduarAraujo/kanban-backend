package com.facilit.kanban_backend.mapper;

import com.baeldung.openapi.model.ListaProjetoResponseRepresentation;
import com.baeldung.openapi.model.ProjetoRepresentation;
import com.facilit.kanban_backend.domain.entity.ProjetoEntity;
import jakarta.validation.Valid;

import java.util.ArrayList;
import java.util.List;

public class ProjetoMapper {

    /**
     * Metodo responsável por fazer a migração de um objeto Entity para um objeto Representation
     *
    */
    public static ListaProjetoResponseRepresentation listaProjetoResponseRepresentationMapper (List<ProjetoEntity> pListaProjeto) {
        List<ProjetoRepresentation> listaprojetos = new ArrayList<>();

        pListaProjeto.stream().forEach((projeto) -> {
            listaprojetos.add(
                    ProjetoRepresentation.builder()
                            .id(projeto.getId())
//                            .status(projeto.getStatus())
                            .nome(projeto.getNome())
                            .dtInicioPrevisto(projeto.getInicioPrevisto())
                            .dtTerminoPrevisto(projeto.getTerminoPrevisto())
                            .dtInicioRealizado(projeto.getInicioRealizado())
                            .dtTerminoRealizado(projeto.getTerminoRealizado())
//                            .responsavelId()
                            .build()
            );
        });

        return ListaProjetoResponseRepresentation.builder()
                .listaprojetos(listaprojetos)
                .build();
    }

    public static ProjetoEntity projetoEntityToRepresentation(ProjetoRepresentation pProjetoRepresentation) {
        ProjetoEntity retorno = new ProjetoEntity();

        return retorno;
    }
}
