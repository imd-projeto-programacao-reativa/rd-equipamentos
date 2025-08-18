package ufrn.deart.reservas.management.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ufrn.deart.reservas.management.dto.EquipamentoFrontDTO;
import ufrn.deart.reservas.management.model.Equipamento;
import ufrn.deart.reservas.management.service.EquipamentoService;

import java.util.List;
import java.util.Map;
import java.util.UUID;

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
    public List<EquipamentoFrontDTO> findAll() {
        System.err.println("buscando por todos");

        return equipamentoService.frontFindAll();
    }

    @GetMapping("/categoria/{nome}")
    public List<EquipamentoFrontDTO> findByCategoria(@PathVariable String nome) {
        System.err.println("buscando por categoria + " +  nome);
        return equipamentoService.frontFindByCategoria(categorias.get(nome));

    }



}
