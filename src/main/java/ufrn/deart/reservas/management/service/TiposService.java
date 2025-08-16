package ufrn.deart.reservas.management.service;

import org.springframework.stereotype.Service;
import ufrn.deart.reservas.management.dto.TipoEquipamentoDTO;
import ufrn.deart.reservas.management.dto.TipoEquipamentoEditDTO;
import ufrn.deart.reservas.management.model.TipoEquipamento;
import ufrn.deart.reservas.management.repository.TiposEquipamentoRepository;

import java.util.List;


@Service
public class TiposService {

    private final TiposEquipamentoRepository tiposEquipamentoRepository;
    private final List<TipoEquipamento> TIPOS_CACHE;

    public TiposService(TiposEquipamentoRepository tiposEquipamentoRepository) {
        this.tiposEquipamentoRepository = tiposEquipamentoRepository;
        this.TIPOS_CACHE = tiposEquipamentoRepository.findAll();
        TIPOS_CACHE.forEach(tipos -> System.out.println(tipos.getNome()));
    }

    public TipoEquipamento save(TipoEquipamentoDTO tipoEquipamentoDTO) {
        TipoEquipamento tipoEquipamento = new TipoEquipamento();
        tipoEquipamento.setNome(tipoEquipamentoDTO.nome());
        TIPOS_CACHE.add(tipoEquipamento);
        return tiposEquipamentoRepository.save(tipoEquipamento);
    }

    public List<TipoEquipamento> findAll() {
        TIPOS_CACHE.clear();
        TIPOS_CACHE.addAll(tiposEquipamentoRepository.findAll());
        return TIPOS_CACHE;
    }

    public TipoEquipamento edit(TipoEquipamentoEditDTO tipoEquipamentoEditDTO) {
        TipoEquipamento tipoEquipamento = new TipoEquipamento();
        tipoEquipamento.setNome(tipoEquipamentoEditDTO.nome());
        return tiposEquipamentoRepository.save(tipoEquipamento);
    }

    public boolean tipoEquipamentoExiste(Integer idTipoEquipamento) {
        return tiposEquipamentoRepository.existsById(idTipoEquipamento);
    }

    public String tipoEquipamentoExisteName(Integer idTipoEquipamento) {
        if(TIPOS_CACHE.contains(idTipoEquipamento)) {
            return TIPOS_CACHE.get(idTipoEquipamento).getNome();
        }
        else if (tiposEquipamentoRepository.existsById(idTipoEquipamento)) {
            return tiposEquipamentoRepository.getOne(idTipoEquipamento).getNome();
        }
        else {
            return null;
        }
    }
}
