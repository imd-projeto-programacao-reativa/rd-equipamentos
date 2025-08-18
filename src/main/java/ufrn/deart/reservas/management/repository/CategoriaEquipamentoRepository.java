package ufrn.deart.reservas.management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ufrn.deart.reservas.management.model.CategoriaEquipamento;

public interface CategoriaEquipamentoRepository extends JpaRepository<CategoriaEquipamento, Integer> {
}
