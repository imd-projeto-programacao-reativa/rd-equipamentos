package ufrn.deart.equipamentos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ufrn.deart.equipamentos.model.CategoriaEquipamento;

public interface CategoriaEquipamentoRepository extends JpaRepository<CategoriaEquipamento, Integer> {
}
