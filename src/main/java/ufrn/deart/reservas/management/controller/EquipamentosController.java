package ufrn.deart.reservas.management.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ufrn.deart.reservas.management.dto.EquipamentoDTO;
import ufrn.deart.reservas.management.dto.EquipamentoForEmbeddingsDTO;
import ufrn.deart.reservas.management.dto.EquipamentoResponseDTO;
import ufrn.deart.reservas.management.model.Equipamento;
import ufrn.deart.reservas.management.service.EquipamentoService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/manage")
public class EquipamentosController {

    @Autowired
    private final EquipamentoService equipamentoService;

    public EquipamentosController(EquipamentoService equipamentoService) {
        this.equipamentoService = equipamentoService;
    }

    // endpoint para enviar os dados para embeddings no mcp
    @PostMapping("/embeddings")
    public List<EquipamentoForEmbeddingsDTO> getEquipamentos() {
        List<Equipamento> equipamentos = this.equipamentoService.findAll();
        List<EquipamentoForEmbeddingsDTO> equipamentosEmbeddings = new ArrayList<>();
        equipamentos.forEach(equipamento -> {
            equipamentosEmbeddings.add(new EquipamentoForEmbeddingsDTO(equipamento.getId(),
                    equipamento.getNome(),
                    equipamento.getDescricao(),
                    equipamento.getStatus().name()));
        });
        equipamentosEmbeddings.forEach(eq -> {
            System.out.println("fez o pedido" + eq.nome());
        });

        return equipamentosEmbeddings;
    }

    @GetMapping("/all")
    public List<Equipamento> findAll() {
        return equipamentoService.findAll();
    }

    @PostMapping("/add")
    public ResponseEntity<EquipamentoResponseDTO> addEquipamento(
            @Valid @RequestBody EquipamentoDTO requestDTO
    ) {
        EquipamentoResponseDTO novoEquipamento = equipamentoService.criarEquipamento(requestDTO);

        return new ResponseEntity<>(novoEquipamento, HttpStatus.CREATED);
    }

}
