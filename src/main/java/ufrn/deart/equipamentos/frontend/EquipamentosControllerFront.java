package ufrn.deart.equipamentos.frontend;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import ufrn.deart.equipamentos.service.EquipamentoService;

import java.time.LocalDate;
import java.util.Map;

@RestController
@RequestMapping("/front")
public class EquipamentosControllerFront {

    private final Map<String, Integer> categorias = Map.of(
            "audio", 1,
            "computadores", 2,
            "projecao", 3,
            "cameras", 4,
            "diversos", 5
    );

    private final EquipamentoService equipamentoService;

    public EquipamentosControllerFront(EquipamentoService equipamentoService) {
        this.equipamentoService = equipamentoService;
    }

    @GetMapping("/equipamentos")
    @PreAuthorize("hasRole('USER')")
    public Flux<EquipamentoFrontDTO> findAll() {
        System.err.println("buscando por todos");
        return equipamentoService.frontFindAll();
    }

    @GetMapping("/categoria/{nome}")
    @PreAuthorize("hasRole('ADMIN')")
    public Flux<EquipamentoFrontDTO> findByCategoria(@PathVariable String nome) {
        System.err.println("buscando por categoria: " + nome);
        Integer categoriaId = categorias.get(nome);
        if (categoriaId == null) {
            return Flux.empty();
        }
        return equipamentoService.frontFindByCategoria(categoriaId);
    }

//    Exemplo de endpoint reativo para disponibilidade (se quiser usar)
//    @GetMapping("/equipamentos/disponiveis")
//    public Mono<Boolean> findByDisponiveis(
//            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicio,
//            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFim,
//            @RequestBody List<UUID> equipamentosIds
//    ){
//        return equipamentoService.equipamentosDisponiveisReactive(equipamentosIds, dataInicio, dataFim);
//    }
}
