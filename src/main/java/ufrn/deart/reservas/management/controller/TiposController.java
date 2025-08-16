package ufrn.deart.reservas.management.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ufrn.deart.reservas.management.dto.TipoEquipamentoDTO;
import ufrn.deart.reservas.management.dto.TipoEquipamentoEditDTO;
import ufrn.deart.reservas.management.model.TipoEquipamento;
import ufrn.deart.reservas.management.service.TiposService;

import java.util.List;

@RestController
@RequestMapping("/manage/tipos")
public class TiposController {

    @Autowired
    private final TiposService tiposService;

    public TiposController(TiposService tiposService) {
        this.tiposService = tiposService;
    }

    @PostMapping("/add")
    public ResponseEntity<TipoEquipamento> addTipo(
            @Valid @RequestBody TipoEquipamentoDTO tipoEquipamentoDTO) {
        TipoEquipamento novoTipoEquipamento = tiposService.save(tipoEquipamentoDTO);
        return new ResponseEntity<>(novoTipoEquipamento, HttpStatus.CREATED);
    }

    @GetMapping()
    public ResponseEntity<List<TipoEquipamento>> getTipos() {
        return new ResponseEntity<>(tiposService.findAll(), HttpStatus.OK);
    }

    @PutMapping("/edit")
    public ResponseEntity<TipoEquipamento> editTipo(
            @Valid @RequestBody TipoEquipamentoEditDTO tipoEquipamentoEditDTO) {
        return new ResponseEntity<>(tiposService.edit(tipoEquipamentoEditDTO), HttpStatus.OK);
    }



}
