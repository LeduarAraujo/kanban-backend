package com.facilit.kanban_backend.repository;

import com.facilit.kanban_backend.domain.entity.ProjetoEntity;
import com.facilit.kanban_backend.domain.enums.StatusProjetoEnum;
import com.facilit.kanban_backend.dto.ProjetoResponsavelDTO;
import com.facilit.kanban_backend.dto.RespostaContagemProjetosPorStatusDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjetoRepository  extends JpaRepository<ProjetoEntity, Long> {

    @Query("""
        SELECT 
            p.nome AS nomeProjeto,
            u.nome AS nomeResponsavel,
            s.nmSecretaria AS nomeSecretaria,
            c.nome AS nomeCargo,
            p.status AS statusProjeto
        FROM ProjetoResponsavelEntity pr
        JOIN pr.projeto p
        JOIN pr.responsavel r
        JOIN r.usuario u
        JOIN r.secretaria s
        JOIN r.cargo c
    """)
    List<ProjetoResponsavelDTO> buscarDetalhesDosProjetos();

    Page<ProjetoEntity> findByStatus(StatusProjetoEnum status, Pageable pageable);

    @Query("SELECT p.status, COUNT(p) FROM ProjetoEntity p GROUP BY p.status")
    List<RespostaContagemProjetosPorStatusDTO> countProjetosByStatus();

    @Query("SELECT AVG(p.diasAtraso) FROM ProjetoEntity p WHERE p.status = :status")
    Double avgDiasAtrasoByStatus(@Param("status") StatusProjetoEnum status);
}
