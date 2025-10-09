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

    @Query("SELECT COUNT(*) FROM alguma_tabela_de_reservas r WHERE r.equipamento_id IN (:ids) AND r.data_reserva BETWEEN :startDate AND :endDate")
    Mono<Equipamento> countUnavailableEquipmentsByIdInAndDateRange(@Param("ids") List<UUID> ids, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
}
