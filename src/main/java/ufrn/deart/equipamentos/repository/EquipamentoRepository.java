package ufrn.deart.equipamentos.repository;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ufrn.deart.equipamentos.model.Equipamento;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface EquipamentoRepository extends ReactiveCrudRepository<Equipamento, UUID> {

    Flux<Equipamento> findByCategoriaId(Integer categoriaId);

    @Query("SELECT COUNT(*)\n" +
            "FROM reservas r\n" +
            "JOIN reserva_equipamentos re ON r.id = re.reserva_id\n" +
            "WHERE re.equipamento_id IN (:ids)\n" +  // <-- Correção 1
            "  AND (r.data_inicio, r.data_fim) OVERLAPS (:startDate, :endDate)\n")
    Mono<Long> countUnavailableEquipmentsByIdInAndDateRange( // <-- Correção 3
                                                             @Param("ids") List<UUID> ids,
                                                             @Param("startDate") LocalDate startDate,
                                                             @Param("endDate") LocalDate endDate
    );
}
