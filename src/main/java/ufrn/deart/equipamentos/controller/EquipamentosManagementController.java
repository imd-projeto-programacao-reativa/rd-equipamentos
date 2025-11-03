package ufrn.deart.equipamentos.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ufrn.deart.equipamentos.dto.*;
import ufrn.deart.equipamentos.model.CategoriaEquipamento;
import ufrn.deart.equipamentos.model.Equipamento;
import ufrn.deart.equipamentos.service.CategoriaService;
import ufrn.deart.equipamentos.service.EquipamentoService;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/management")
public class EquipamentosManagementController {

    private final EquipamentoService equipamentoService;
    private final CategoriaService categoriaService;

    @Autowired
    public EquipamentosManagementController(EquipamentoService equipamentoService, CategoriaService categoriaService) {
        this.equipamentoService = equipamentoService;
        this.categoriaService = categoriaService;
    }

    // ---------------------- EQUIPAMENTOS ----------------------

    @GetMapping
    public Flux<Equipamento> listarEquipamentos() {
        return equipamentoService.findAll();
    }

    // Endpoint para gerar embeddings (dados para IA)
    @PostMapping("/embeddings")
    public Flux<EquipamentoForEmbeddingsDTO> getEquipamentos() {
        return equipamentoService.findAll()
                .map(equipamento -> new EquipamentoForEmbeddingsDTO(
                        equipamento.getId(),
                        equipamento.getNome(),
                        equipamento.getDescricao(),
                        equipamento.getStatus().name()
                ));
    }

    @PostMapping("/adicionar")
    public Mono<ResponseEntity<EquipamentoResponseDTO>> addEquipamentos(
            @Valid @RequestBody EquipamentoCreateDTO equipamentoDTO) {

        return equipamentoService.saveEquipamento(equipamentoDTO)
                .map(novoEquipamento -> ResponseEntity.status(HttpStatus.CREATED).body(novoEquipamento))
                .defaultIfEmpty(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build());
    }

    @DeleteMapping("/delete/{equipamentoId}")
    public Mono<ResponseEntity<Void>> deleteEquipamentos(@PathVariable UUID equipamentoId) {
        return equipamentoService.deleteEquipamento(equipamentoId)
                .then(Mono.just(ResponseEntity.noContent().<Void>build()))
                .onErrorResume(e -> Mono.just(ResponseEntity.notFound().build()));
    }

    @PutMapping("/editar")
    public Mono<ResponseEntity<EquipamentoResponseDTO>> editEquipamentos(
            @Valid @RequestBody EquipamentoEditDTO equipamentoDTO) {

        return equipamentoService.editarEquipamento(equipamentoDTO)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }


    // ---------------------- CATEGORIAS ----------------------

    @PostMapping("/categorias/add")
    public Mono<ResponseEntity<CategoriaEquipamento>> addTipo(
            @Valid @RequestBody CategoriaEquipamentoDTO categoriaEquipamentoDTO) {

        return categoriaService.save(categoriaEquipamentoDTO)
                .map(cat -> ResponseEntity.status(HttpStatus.CREATED).body(cat));
    }

    @GetMapping("/ids")
    public Flux<UUID> getEquipamentosIds() {
        return equipamentoService.findAll().map(Equipamento::getId);
    }

    @GetMapping("/disponiveis")
    public Mono<ResponseEntity<Boolean>> findByDisponiveis(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFim,
            @RequestBody List<UUID> equipamentosIds
    ) {
        return equipamentoService.equipamentosDisponiveis(equipamentosIds, dataInicio, dataFim)
                .map(ResponseEntity::ok);
    }
    @GetMapping("/categorias")
    public Flux<CategoriaEquipamento> getTipos() {
        return categoriaService.findAll();
    }

    @PutMapping("/categorias/edit")
    public Mono<ResponseEntity<CategoriaEquipamento>> editTipo(
            @Valid @RequestBody CategoriaEquipamentoEditDTO tipoEquipamentoEditDTO) {

        return categoriaService.edit(tipoEquipamentoEditDTO)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
}
