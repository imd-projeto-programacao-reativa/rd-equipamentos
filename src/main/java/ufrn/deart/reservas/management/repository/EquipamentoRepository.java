package ufrn.deart.reservas.management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ufrn.deart.reservas.management.model.Equipamento;

import java.util.List;
import java.util.UUID;

public interface EquipamentoRepository extends JpaRepository<Equipamento, UUID> {

    List<Equipamento> findByCategoriaId(Integer categoriaId);


}
