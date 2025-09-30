package ufrn.deart.equipamentos.frontend;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ufrn.deart.equipamentos.dto.EquipamentoDisponibilidadeDTO;
import ufrn.deart.equipamentos.service.EquipamentoService;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/front")
public class EquipamentosControllerFront {

    private Map<String, Integer> categorias = Map.of(
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
    public List<EquipamentoFrontDTO> findAll() {
        System.err.println("buscando por todos");

        return equipamentoService.frontFindAll();
    }

    @GetMapping("/categoria/{nome}")
    @PreAuthorize("hasRole('ADMIN')")
    public List<EquipamentoFrontDTO> findByCategoria(@PathVariable String nome) {
        System.err.println("buscando por categoria + " +  nome);
        return equipamentoService.frontFindByCategoria(categorias.get(nome));

    }

//    @GetMapping("/equipamentos/disponiveis")
//    public ResponseEntity<List<EquipamentoDisponibilidadeDTO>> findByDisponiveis(
//            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicio,
//            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFim
//            ){
//        List<EquipamentoDisponibilidadeDTO> listaEquipamentos = equipamentoService.equipamentosDisponiveis(dataInicio, dataFim);
//        return ResponseEntity.ok(listaEquipamentos);
//    }




}
