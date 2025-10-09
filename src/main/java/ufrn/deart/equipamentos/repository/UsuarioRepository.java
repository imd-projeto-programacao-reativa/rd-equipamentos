package ufrn.deart.equipamentos.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import ufrn.deart.equipamentos.model.Usuario;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends ReactiveCrudRepository<Usuario, Long> {
    Optional<Usuario> findByUsername(String username);
}