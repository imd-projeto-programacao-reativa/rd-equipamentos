package ufrn.deart.equipamentos.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import org.springframework.transaction.reactive.TransactionalOperator;
import ufrn.deart.equipamentos.dto.*;
import ufrn.deart.equipamentos.frontend.EquipamentoFrontDTO;
import ufrn.deart.equipamentos.model.CategoriaEquipamento;
import ufrn.deart.equipamentos.model.Equipamento;
import ufrn.deart.equipamentos.model.StatusEquipamento;
import ufrn.deart.equipamentos.repository.EquipamentoRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class EquipamentoService {

    private final EquipamentoRepository equipamentoRepository;
    private final CategoriaService categoriaService;
    private final TransactionalOperator txOperator;

    public EquipamentoService(EquipamentoRepository equipamentoRepository,
                              CategoriaService categoriaService,
                              TransactionalOperator txOperator) {
        this.equipamentoRepository = equipamentoRepository;
        this.categoriaService = categoriaService;
        this.txOperator = txOperator;
    }

    public Flux<Equipamento> findAll() {
        return equipamentoRepository.findAll();
    }

    public Mono<ResponseEntity<Equipamento>> findById(UUID id) {
        return equipamentoRepository.findById(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    public Mono<EquipamentoResponseDTO> saveEquipamento(EquipamentoCreateDTO dto) {
        Equipamento novoEquipamento = new Equipamento();
        CategoriaEquipamento categoria = new CategoriaEquipamento(dto.categoriaId());

        novoEquipamento.setNome(dto.nome());
        novoEquipamento.setDescricao(dto.descricao());
        novoEquipamento.setTombamento(dto.tombamento());
        novoEquipamento.setCategoriaId(categoria.getId());
        novoEquipamento.setImgUrl(dto.url_imagem());
        novoEquipamento.setStatus(StatusEquipamento.disponivel);

        return equipamentoRepository.save(novoEquipamento)
                .map(EquipamentoResponseDTO::new)
                .as(txOperator::transactional);
    }

    public Mono<ResponseEntity<Object>> deleteEquipamento(UUID equipamentoId) {
        return equipamentoRepository.findById(equipamentoId)
                .flatMap(e -> equipamentoRepository.delete(e)
                        .then(Mono.just(ResponseEntity.noContent().build())))
                .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()))
                ;
    }

    public Mono<EquipamentoResponseDTO> findEquipamentoById(UUID equipamentoId) {
        return equipamentoRepository.findById(equipamentoId)
                .map(EquipamentoResponseDTO::new);
    }

    public Mono<EquipamentoResponseDTO> editarEquipamento(EquipamentoEditDTO dto) {
        CategoriaEquipamento categoria = new CategoriaEquipamento(dto.categoriaId());

        return equipamentoRepository.findById(dto.id())
                .flatMap(e -> {
                    e.setNome(dto.nome());
                    e.setDescricao(dto.descricao());
                    e.setTombamento(dto.tombamento());
                    e.setCategoriaId(categoria.getId());
                    e.setImgUrl(dto.url_imagem());
                    return equipamentoRepository.save(e);
                })
                .map(EquipamentoResponseDTO::new);
    }


    public Flux<EquipamentoFrontDTO> frontFindAll() {
        return equipamentoRepository.findAll()
                .map(e -> new EquipamentoFrontDTO(
                        e.getId(),
                        e.getNome(),
                        e.getDescricao(),
                        e.getImgUrl(),
                        e.getCategoriaId() != null ? e.getCategoriaId() : null,
                        e.getStatus() != null ? e.getStatus().name() : null
                ));
    }

    public Flux<EquipamentoFrontDTO> frontFindByCategoria(Integer id) {
        return equipamentoRepository.findByCategoriaId(id)
                .map(e -> new EquipamentoFrontDTO(
                        e.getId(),
                        e.getNome(),
                        e.getDescricao(),
                        e.getImgUrl(),
                        e.getCategoriaId() != null ? e.getCategoriaId() : null,
                        e.getStatus() != null ? e.getStatus().name() : null
                ));
    }

    public Mono<Boolean> equipamentosDisponiveis(List<UUID> ids, LocalDate dataInicio, LocalDate dataFim) {
        return equipamentoRepository
                .countUnavailableEquipmentsByIdInAndDateRange(ids, dataInicio, dataFim)
                .map(Objects::isNull);
    }
}
