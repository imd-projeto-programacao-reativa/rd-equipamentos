package ufrn.deart.equipamentos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ufrn.deart.equipamentos.dto.EquipamentoDisponibilidadeDTO;
import ufrn.deart.equipamentos.model.Equipamento;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface EquipamentoRepository extends JpaRepository<Equipamento, UUID> {

    List<Equipamento> findByCategoriaId(Integer categoriaId);

    @Query(value = """
        SELECT
            e.id AS equipamento_id,
            e.nome AS equipamento_nome,
            CASE
                WHEN COUNT(r.id) > 0 THEN 'Reservado'
                ELSE 'Disponível'
            END AS status_disponibilidade
        FROM
            equipamentos e
        LEFT JOIN
            reserva_equipamentos re ON e.id = re.equipamento_id
        LEFT JOIN
            reservas r ON re.reserva_id = r.id
            AND r.status = 'ATIVO'
            AND r.data_inicio <= :dataFim
            AND r.data_fim >= :dataInicio
        GROUP BY
            e.id, e.nome
        ORDER BY
            e.nome
    """, nativeQuery = true)
    List<EquipamentoDisponibilidadeDTO> findEquipamentoStatusBetweenDates(
            @Param("dataInicio") LocalDate dataInicio,
            @Param("dataFim") LocalDate dataFim
    );

    @Query(value = """
    SELECT
        COUNT(DISTINCT e.id)
    FROM
        equipamentos e
    INNER JOIN
        reserva_equipamentos re ON e.id = re.equipamento_id
    INNER JOIN
        reservas r ON re.reserva_id = r.id
    WHERE
        e.id IN (:ids)
        AND r.status = 'ATIVO'
        AND r.data_inicio <= :dataFim
        AND r.data_fim >= :dataInicio
""", nativeQuery = true)
    long countUnavailableEquipmentsByIdsAndDateRange(
            @Param("ids") List<UUID> ids,
            @Param("dataInicio") LocalDate dataInicio,
            @Param("dataFim") LocalDate dataFim
    );


}
