package ufrn.deart.reservas.management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ufrn.deart.reservas.management.model.TipoEquipamento;

public interface TiposEquipamentoRepository extends JpaRepository<TipoEquipamento, Integer> {
}
