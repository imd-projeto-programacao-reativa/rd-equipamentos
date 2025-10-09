package ufrn.deart.equipamentos.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ufrn.deart.equipamentos.model.Equipamento;
import ufrn.deart.equipamentos.service.EquipamentoService;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/equipamentos")
public class EquipamentosController {

    private final EquipamentoService equipamentoService;

    private static final Logger log = LoggerFactory.getLogger(EquipamentosController.class);

    public EquipamentosController(EquipamentoService equipamentoService) {
        this.equipamentoService = equipamentoService;
    }

    // GET /equipamentos
    @GetMapping
    public Flux<Equipamento> getEquipamentos() {
        return equipamentoService.findAll();
    }

    // GET /equipamentos/{id}
    @GetMapping("/{id}")
    public Mono<ResponseEntity<Equipamento>> getEquipamentoById(@PathVariable UUID id) {
        return equipamentoService.findById(id);
    }

    // GET /equipamentos/disponiveis
    @GetMapping("/disponiveis")
    public Mono<ResponseEntity<Boolean>> findByDisponiveis(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFim,
            @RequestBody List<UUID> equipamentosIds
    ) {
        log.info("Recebi requisição para verificar disponibilidade de equipamentos");
        return equipamentoService.equipamentosDisponiveis(equipamentosIds, dataInicio, dataFim)
                .map(ResponseEntity::ok);
    }
}
