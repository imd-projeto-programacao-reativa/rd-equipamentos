package ufrn.deart.equipamentos.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ufrn.deart.equipamentos.dto.*;
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

    public EquipamentoService(EquipamentoRepository equipamentoRepository) {
        this.equipamentoRepository = equipamentoRepository;
    }

    public List<Equipamento> findAll(){
        return equipamentoRepository.findAll();
    }

    public ResponseEntity<EquipamentoDTO> getById(UUID id){
        return equipamentoRepository.findById(id)
                        .map(equipamento -> ResponseEntity.ok(createEquipamentoDTO(equipamento)))
                        .orElse(ResponseEntity.notFound().build());
    }

    private EquipamentoDTO createEquipamentoDTO(Equipamento e){
       return new EquipamentoDTO(e.getId(), e.getNome(), e.getDescricao(), e.getTombamento(), e.getCategoria(), e.getUrlImagem(), e.getStatus());
    }

    @Transactional
    public EquipamentoDTO saveEquipamento(EquipamentoDTO dto) {
        Equipamento novoEquipamento = new Equipamento();

        CategoriaEquipamento categoria = new CategoriaEquipamento(dto.categoria().getId());

        novoEquipamento.setNome(dto.nome());
        novoEquipamento.setDescricao(dto.descricao());
        novoEquipamento.setTombamento(dto.tombamento());
        novoEquipamento.setCategoria(categoria);
        novoEquipamento.setUrlImagem(dto.imgUrl());
        novoEquipamento.setStatus(StatusEquipamento.DISPONIVEL);

        Equipamento equipamentoSalvo = equipamentoRepository.save(novoEquipamento);

        return dto;
    }

    public ResponseEntity<?> deleteEquipamento(UUID equipamentoId){
        return equipamentoRepository.findById(equipamentoId)
                        .map(e -> {
                            equipamentoRepository.delete(e);
                            return ResponseEntity.noContent().build();
                        })
                                .orElse(ResponseEntity.notFound().build());
    }


    public ResponseEntity<EquipamentoDTO> editarEquipamento(EquipamentoDTO dto) {
        CategoriaEquipamento categoria = new CategoriaEquipamento(dto.categoria().getId());

        return equipamentoRepository.findById(dto.id())
                .map(e -> {
                    e.setNome(dto.nome());
                    e.setDescricao(dto.descricao());
                    e.setTombamento(dto.tombamento());
                    e.setCategoria(categoria);
                    e.setUrlImagem(dto.imgUrl());
                    Equipamento equipamentoSalvo = equipamentoRepository.save(e);
                    return ResponseEntity.ok(dto);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    public Boolean equipamentosDisponiveis(FiltroByDisponibilidade filtro){

        long unvailable = equipamentoRepository.countUnavailableEquipmentsByIdsAndDateRange(filtro.equipamentosIds(), filtro.dataInicio(), filtro.dataFim());

        if (unvailable > 0) {
            return Boolean.FALSE;
        } else {
            return Boolean.TRUE;
        }


    }

}
