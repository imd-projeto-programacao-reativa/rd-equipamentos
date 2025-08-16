package ufrn.deart.reservas.management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import ufrn.deart.reservas.management.model.Reserva;

import java.time.LocalDate;
import java.util.UUID;

public interface ReservaRepository extends JpaRepository<Reserva, UUID> {


    @Procedure(name = "reservar_equipamento")
    UUID reservarEquipamento(
            @Param("p_equipamento_id") UUID equipamentoId,
            @Param("p_pessoa_matricula") String pessoaMatricula,
            @Param("p_devolucao_prevista") LocalDate devolucaoPrevista
    );

    @Procedure(name = "devolver_equipamento")
    boolean devolverEquipamento(@Param("p_reserva_id") UUID reservaId);

}
