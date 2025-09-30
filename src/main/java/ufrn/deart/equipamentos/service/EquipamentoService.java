package ufrn.deart.equipamentos.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ufrn.deart.equipamentos.dto.*;
import ufrn.deart.equipamentos.frontend.EquipamentoFrontDTO;
import ufrn.deart.equipamentos.model.CategoriaEquipamento;
import ufrn.deart.equipamentos.model.Equipamento;
import ufrn.deart.equipamentos.model.StatusEquipamento;
import ufrn.deart.equipamentos.repository.EquipamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Service
public class EquipamentoService {

    @Autowired
    private final EquipamentoRepository equipamentoRepository;

    private final CategoriaService categoriaService;

    public EquipamentoService(EquipamentoRepository equipamentoRepository, CategoriaService categoriaService) {
        this.equipamentoRepository = equipamentoRepository;
        this.categoriaService = categoriaService;
    }

    public List<Equipamento> findAll(){
        return equipamentoRepository.findAll();
    }

    @Transactional
    public EquipamentoResponseDTO saveEquipamento(EquipamentoCreateDTO dto) {
        Equipamento novoEquipamento = new Equipamento();

        CategoriaEquipamento categoria = new CategoriaEquipamento(dto.categoriaId());

        novoEquipamento.setNome(dto.nome());
        novoEquipamento.setDescricao(dto.descricao());
        novoEquipamento.setTombamento(dto.tombamento());
        novoEquipamento.setCategoria(categoria);
        novoEquipamento.setUrlImagem(dto.url_imagem());
        novoEquipamento.setStatus(StatusEquipamento.disponivel);

        Equipamento equipamentoSalvo = equipamentoRepository.save(novoEquipamento);

        return new EquipamentoResponseDTO(equipamentoSalvo);
    }

    public ResponseEntity<?> deleteEquipamento(UUID equipamentoId){
        return equipamentoRepository.findById(equipamentoId)
                        .map(e -> {
                            equipamentoRepository.delete(e);
                            return ResponseEntity.noContent().build();
                        })
                                .orElse(ResponseEntity.notFound().build());
    }

    public EquipamentoResponseDTO findEquipamentoById(UUID equipamentoId){
        return new EquipamentoResponseDTO(equipamentoRepository.findById(equipamentoId).get());
    }

    public ResponseEntity<EquipamentoResponseDTO> editarEquipamento(EquipamentoEditDTO dto) {
        CategoriaEquipamento categoria = new CategoriaEquipamento(dto.categoriaId());

        return equipamentoRepository.findById(dto.id())
                .map(e -> {
                    e.setNome(dto.nome());
                    e.setDescricao(dto.descricao());
                    e.setTombamento(dto.tombamento());
                    e.setCategoria(categoria);
                    e.setUrlImagem(dto.url_imagem());
                    Equipamento equipamentoSalvo = equipamentoRepository.save(e);
                    return ResponseEntity.ok(new EquipamentoResponseDTO(equipamentoSalvo));
                })
                .orElse(ResponseEntity.notFound().build());
    }



    // front functions
    public List<EquipamentoFrontDTO> frontFindAll(){
        List<EquipamentoFrontDTO> equipamentos = new ArrayList<>();

        equipamentoRepository.findAll().forEach(e -> {
            equipamentos.add(new EquipamentoFrontDTO(
                    e.getId(),
                    e.getNome(),
                    e.getDescricao(),
                    e.getUrlImagem(),
                    e.getCategoria().getNome(),
                    e.getStatus().name()
                    ));
        });
        return equipamentos;
    }

    public List<EquipamentoFrontDTO> frontFindByCategoria(Integer id) {
        List<EquipamentoFrontDTO> equipamentos = new ArrayList<>();

        equipamentoRepository.findByCategoriaId(id).forEach(e -> {
            equipamentos.add(new EquipamentoFrontDTO(
                    e.getId(),
                    e.getNome(),
                    e.getDescricao(),
                    e.getUrlImagem(),
                    e.getCategoria().getNome(),
                    e.getStatus().name()
            ));
        });
        return equipamentos;
    }

    public Boolean equipamentosDisponiveis(List<UUID> ids, LocalDate dataInicio, LocalDate dataFim){

        long unvailable = equipamentoRepository.countUnavailableEquipmentsByIdsAndDateRange(ids, dataInicio, dataFim);

        if (unvailable > 0) {
            return Boolean.FALSE;
        } else {
            return Boolean.TRUE;
        }


    }

}
