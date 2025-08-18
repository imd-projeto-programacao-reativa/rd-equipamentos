package ufrn.deart.reservas.management.service;

import org.springframework.stereotype.Service;
import ufrn.deart.reservas.management.dto.EquipamentoDTO;
import ufrn.deart.reservas.management.dto.EquipamentoFrontDTO;
import ufrn.deart.reservas.management.dto.EquipamentoResponseDTO;
import ufrn.deart.reservas.management.model.CategoriaEquipamento;
import ufrn.deart.reservas.management.model.Equipamento;
import ufrn.deart.reservas.management.model.StatusEquipamento;
import ufrn.deart.reservas.management.repository.EquipamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


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

    @Transactional
    public EquipamentoResponseDTO criarEquipamento(EquipamentoDTO dto) {
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



    // front functions
    public List<EquipamentoFrontDTO> frontFindAll(){
        List<EquipamentoFrontDTO> equipamentos = new ArrayList<>();

        equipamentoRepository.findAll().forEach(e -> {
            equipamentos.add(new EquipamentoFrontDTO(
                    e.getId(),
                    e.getNome(),
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
                    e.getUrlImagem(),
                    e.getCategoria().getNome(),
                    e.getStatus().name()
            ));
        });
        return equipamentos;
    }
}
