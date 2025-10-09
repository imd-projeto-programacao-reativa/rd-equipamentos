package ufrn.deart.equipamentos.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import ufrn.deart.equipamentos.model.CategoriaEquipamento;

public interface CategoriaEquipamentoRepository extends ReactiveCrudRepository<CategoriaEquipamento, Integer> {
}
