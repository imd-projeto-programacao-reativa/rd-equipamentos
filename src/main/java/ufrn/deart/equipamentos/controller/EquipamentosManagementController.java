package ufrn.deart.equipamentos.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ufrn.deart.equipamentos.dto.*;
import ufrn.deart.equipamentos.model.Equipamento;
import ufrn.deart.equipamentos.service.EquipamentoService;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/management")
public class EquipamentosManagementController {

    @Autowired
    private final EquipamentoService equipamentoService;


    public EquipamentosManagementController(EquipamentoService equipamentoService) {
        this.equipamentoService = equipamentoService;
    }

    @QueryMapping(name = "getEquipamentos")
    public List<Equipamento> listarEquipamentos(){
        return equipamentoService.findAll();
    }

    @QueryMapping(name = "getEquipamentosIds")
    public List<String> getEquipamentosIds() {
        return equipamentoService.findAll().stream().map(
                equipamento -> equipamento.getId().toString()
        ).toList();
    }

    @QueryMapping(name = "getEquipamentosById")
    public ResponseEntity<EquipamentoDTO> getById(@Argument String id){
        return equipamentoService.getById(java.util.UUID.fromString(id));
    }

    @QueryMapping(name = "getEquipamentosDisponiveis")
    public ResponseEntity<Boolean> findByDisponiveis(@Argument FiltroByDisponibilidade filtro){
        Boolean listaEquipamentos = equipamentoService.equipamentosDisponiveis(filtro);
        return ResponseEntity.ok(listaEquipamentos);
    }


    @MutationMapping(name = "createEquipamento")
    public ResponseEntity<EquipamentoDTO> addEquipamentos(@Argument @Valid EquipamentoDTO equipamentoDTO){
        EquipamentoDTO novoEquipamento = equipamentoService.saveEquipamento(equipamentoDTO);
        return new ResponseEntity<>(novoEquipamento, HttpStatus.CREATED);
    }

    @MutationMapping(name = "deleteEquipamento")
    public ResponseEntity<?> deleteEquipamentos(@Argument UUID idEquipamento){
        return equipamentoService.deleteEquipamento(idEquipamento);
    }


    @MutationMapping(name = "editEquipamento")
    public ResponseEntity<EquipamentoDTO> editEquipamentos(@Argument EquipamentoDTO equipamentoDTO) {
       return equipamentoService.editarEquipamento(equipamentoDTO);
    }

}