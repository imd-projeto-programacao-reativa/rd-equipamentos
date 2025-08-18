package ufrn.deart.reservas.management.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ufrn.deart.reservas.management.dto.CategoriaEquipamentoDTO;
import ufrn.deart.reservas.management.dto.CategoriaEquipamentoEditDTO;
import ufrn.deart.reservas.management.model.CategoriaEquipamento;
import ufrn.deart.reservas.management.service.CategoriaService;

import java.util.List;

@RestController
@RequestMapping("/manage/categoria")
public class CategoriaController {

    @Autowired
    private final CategoriaService categoriaService;

    public CategoriaController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }


    @PostMapping("/add")
    public ResponseEntity<CategoriaEquipamento> addTipo(
            @Valid @RequestBody CategoriaEquipamentoDTO CategoriaEquipamentoDTO) {
        CategoriaEquipamento novaCategoriaEquipamento = categoriaService.save(CategoriaEquipamentoDTO);
        return new ResponseEntity<>(novaCategoriaEquipamento, HttpStatus.CREATED);
    }

    @GetMapping()
    public ResponseEntity<List<CategoriaEquipamento>> getTipos() {
        return new ResponseEntity<>(categoriaService.findAll(), HttpStatus.OK);
    }

    @PutMapping("/edit")
    public ResponseEntity<CategoriaEquipamento> editTipo(
            @Valid @RequestBody CategoriaEquipamentoEditDTO tipoEquipamentoEditDTO) {
        return new ResponseEntity<>(categoriaService.edit(tipoEquipamentoEditDTO), HttpStatus.OK);
    }



}
