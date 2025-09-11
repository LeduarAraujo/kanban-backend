package com.facilit.kanban_backend.repository;

import com.facilit.kanban_backend.domain.entity.ProjetoEntity;
import com.facilit.kanban_backend.domain.enums.StatusProjetoEnum;
import com.facilit.kanban_backend.dto.ProjetoResponsavelDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProjetoRepository  extends JpaRepository<ProjetoEntity, Long> {

    @Query("""
        SELECT 
            p.nome AS nomeProjeto,
            u.nome AS nomeResponsavel,
            s.nome AS nomeSecretaria,
            c.nome AS nomeCargo,
            p.status AS statusProjeto
        FROM Projeto p
        JOIN ProjetoResponsavel pr ON pr.projeto = p
        JOIN Responsavel r ON r = pr.responsavel
        JOIN Usuario u ON u = r.usuario
        JOIN Secretaria s ON s = r.secretaria
        JOIN Cargo c ON c = r.cargo
    """)
    List<ProjetoResponsavelDTO> buscarDetalhesDosProjetos();

    Page<ProjetoEntity> findByStatus(StatusProjetoEnum status, Pageable pageable);

    @Query("SELECT p.status, COUNT(p) FROM ProjetoEntity p GROUP BY p.status")
    List<Object[]> countProjetosByStatus();

    @Query("SELECT AVG(p.diasAtraso) FROM ProjetoEntity p WHERE p.status = :status")
    Double avgDiasAtrasoByStatus(@Param("status") StatusProjetoEnum status);
}
