package ufrn.deart.equipamentos.controller;

import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ufrn.deart.equipamentos.dto.*;
import ufrn.deart.equipamentos.model.CategoriaEquipamento;
import ufrn.deart.equipamentos.model.Equipamento;
import ufrn.deart.equipamentos.model.Usuario;
import ufrn.deart.equipamentos.repository.UsuarioRepository;
import ufrn.deart.equipamentos.service.CategoriaService;
import ufrn.deart.equipamentos.service.EquipamentoService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/management")
public class EquipamentosManagementController {

    @Autowired
    private final EquipamentoService equipamentoService;

    @Autowired
    private final CategoriaService categoriaService;

    @Autowired
    private final UsuarioRepository usuarioRepository;


    public EquipamentosManagementController(EquipamentoService equipamentoService, CategoriaService categoriaService, UsuarioRepository usuarioRepository) {
        this.equipamentoService = equipamentoService;
        this.categoriaService = categoriaService;
        this.usuarioRepository = usuarioRepository;
    }

    @GetMapping
    public List<Equipamento> listarEquipamentos(){
        return equipamentoService.findAll();
    }

    @GetMapping("/ids")
    public List<UUID> getEquipamentosIds() {
        return equipamentoService.findAll().stream().map(Equipamento::getId).toList();
    }

    @GetMapping("/{id}")
//    @RolesAllowed("ROLE_USER")
    public ResponseEntity<EquipamentoResponseDTO> getById(@PathVariable UUID id){
        return equipamentoService.getById(id);
    }

    // embeddings para ia service
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


    @PostMapping("/adicionar")
    public ResponseEntity<EquipamentoResponseDTO> addEquipamentos(
            @Valid @RequestBody EquipamentoCreateDTO equipamentoDTO ){
        EquipamentoResponseDTO novoEquipamento = equipamentoService.saveEquipamento(equipamentoDTO);
        return new ResponseEntity<>(novoEquipamento, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteEquipamentos(UUID equipamentoId){
        return equipamentoService.deleteEquipamento(equipamentoId);
    }

    @PutMapping("/editar")
    public ResponseEntity<EquipamentoResponseDTO> editEquipamentos(
            @Valid @RequestBody EquipamentoEditDTO equipamentoDTO) {
       return equipamentoService.editarEquipamento(equipamentoDTO);
    }


    // categorias dos equipamentos

    @PostMapping("/categorias/add")
    public ResponseEntity<CategoriaEquipamento> addTipo(
            @Valid @RequestBody CategoriaEquipamentoDTO CategoriaEquipamentoDTO) {
        CategoriaEquipamento novaCategoriaEquipamento = categoriaService.save(CategoriaEquipamentoDTO);
        return new ResponseEntity<>(novaCategoriaEquipamento, HttpStatus.CREATED);
    }

    @GetMapping("/categorias")

    public ResponseEntity<List<CategoriaEquipamento>> getTipos() {
        return new ResponseEntity<>(categoriaService.findAll(), HttpStatus.OK);
    }

    @PutMapping("/categorias/edit")
    public ResponseEntity<CategoriaEquipamento> editTipo(
            @Valid @RequestBody CategoriaEquipamentoEditDTO tipoEquipamentoEditDTO) {
        return new ResponseEntity<>(categoriaService.edit(tipoEquipamentoEditDTO), HttpStatus.OK);
    }

    @GetMapping("/disponiveis")
    public ResponseEntity<Boolean> findByDisponiveis(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFim,
            @RequestBody List<UUID> equipamentosIds
    ){
        Boolean listaEquipamentos = equipamentoService.equipamentosDisponiveis(equipamentosIds, dataInicio, dataFim);
        return ResponseEntity.ok(listaEquipamentos);
    }
}



