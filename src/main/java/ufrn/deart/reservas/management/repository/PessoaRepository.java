package ufrn.deart.reservas.management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ufrn.deart.reservas.management.model.Pessoa;

import java.util.UUID;

public interface PessoaRepository extends JpaRepository<Pessoa, UUID> {

}
