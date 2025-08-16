package ufrn.deart.reservas.management.service;

import org.springframework.stereotype.Service;
import ufrn.deart.reservas.management.dto.EquipamentoDTO;
import ufrn.deart.reservas.management.dto.EquipamentoResponseDTO;
import ufrn.deart.reservas.management.model.Equipamento;
import ufrn.deart.reservas.management.model.StatusEquipamento;
import ufrn.deart.reservas.management.model.TipoEquipamento;
import ufrn.deart.reservas.management.repository.EquipamentoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

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

        TipoEquipamento tipo = new TipoEquipamento(dto.tipoId());

        novoEquipamento.setNome(dto.nome());
        novoEquipamento.setDescricao(dto.descricao());
        novoEquipamento.setPatrimonio(dto.patrimonio());
        novoEquipamento.setTipo(tipo);

        novoEquipamento.setStatus(StatusEquipamento.disponivel);

        Equipamento equipamentoSalvo = equipamentoRepository.save(novoEquipamento);

        return new EquipamentoResponseDTO(equipamentoSalvo);
    }
}
