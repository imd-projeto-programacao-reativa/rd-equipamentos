package ufrn.deart.equipamentos.controller;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ufrn.deart.equipamentos.dto.EquipamentoDisponibilidadeDTO;
import ufrn.deart.equipamentos.model.Equipamento;
import ufrn.deart.equipamentos.service.EquipamentoService;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/equipamentos")
public class EquipamentosController {

    @Autowired
    private final EquipamentoService equipamentoService;

    private final static Logger log = LoggerFactory.getLogger(EquipamentosController.class);


    public EquipamentosController(EquipamentoService equipamentoService) {
        this.equipamentoService = equipamentoService;
    }

    @GetMapping
    public List<Equipamento> getEquipamentos() {
        return equipamentoService.findAll();
    }

    @GetMapping("/ids")
    public List<UUID> getEquipamentosIds() {
        return equipamentoService.findAll().stream().map(Equipamento::getId).toList();
    }


    @GetMapping("/disponiveis")
    public ResponseEntity<Boolean> findByDisponiveis(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFim,
            @RequestBody List<UUID> equipamentosIds
    ){
        log.info("Recebi do reserve service");
        Boolean listaEquipamentos = equipamentoService.equipamentosDisponiveis(equipamentosIds, dataInicio, dataFim);
        return ResponseEntity.ok(listaEquipamentos);
    }
}
