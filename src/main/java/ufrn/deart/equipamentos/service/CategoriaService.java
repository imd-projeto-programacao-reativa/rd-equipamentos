package ufrn.deart.equipamentos.service;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ufrn.deart.equipamentos.dto.CategoriaEquipamentoDTO;
import ufrn.deart.equipamentos.dto.CategoriaEquipamentoEditDTO;
import ufrn.deart.equipamentos.model.CategoriaEquipamento;
import ufrn.deart.equipamentos.repository.CategoriaEquipamentoRepository;

@Service
public class CategoriaService {

    private final CategoriaEquipamentoRepository categoriaEquipamentoRepository;

    public CategoriaService(CategoriaEquipamentoRepository categoriaEquipamentoRepository) {
        this.categoriaEquipamentoRepository = categoriaEquipamentoRepository;
    }

    // Cria uma nova categoria
    public Mono<CategoriaEquipamento> save(CategoriaEquipamentoDTO dto) {
        CategoriaEquipamento categoria = new CategoriaEquipamento();
        categoria.setNome(dto.nome());
        return categoriaEquipamentoRepository.save(categoria);
    }

    // Retorna todas as categorias
    public Flux<CategoriaEquipamento> findAll() {
        return categoriaEquipamentoRepository.findAll();
    }

    // Edita uma categoria existente
    public Mono<CategoriaEquipamento> edit(CategoriaEquipamentoEditDTO dto) {
        return categoriaEquipamentoRepository.findById(dto.idTipoEquipamento())
                .flatMap(existing -> {
                    existing.setNome(dto.nome());
                    return categoriaEquipamentoRepository.save(existing);
                });
    }

    // Verifica se uma categoria existe pelo ID
    public Mono<Boolean> categoriaEquipamentoExiste(Integer idCategoriaEquipamento) {
        return categoriaEquipamentoRepository.existsById(idCategoriaEquipamento);
    }

    // Busca o nome da categoria pelo ID
    public Mono<String> categoriaEquipamentoExisteName(Integer idCategoriaEquipamento) {
        return categoriaEquipamentoRepository.findById(idCategoriaEquipamento)
                .map(CategoriaEquipamento::getNome)
                .defaultIfEmpty(null);
    }
}
