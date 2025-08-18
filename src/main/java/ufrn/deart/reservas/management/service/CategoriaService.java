package ufrn.deart.reservas.management.service;

import org.springframework.stereotype.Service;
import ufrn.deart.reservas.management.dto.CategoriaEquipamentoDTO;
import ufrn.deart.reservas.management.dto.CategoriaEquipamentoEditDTO;
import ufrn.deart.reservas.management.model.CategoriaEquipamento;
import ufrn.deart.reservas.management.repository.CategoriaEquipamentoRepository;

import java.util.List;


@Service
public class CategoriaService {

    private final CategoriaEquipamentoRepository categoriaEquipamentoRepository;
    private final List<CategoriaEquipamento> CATEGORIAS_CACHE;

    public CategoriaService(CategoriaEquipamentoRepository categoriaEquipamentoRepository) {
        this.categoriaEquipamentoRepository = categoriaEquipamentoRepository;

        this.CATEGORIAS_CACHE = categoriaEquipamentoRepository.findAll();

    }

    public CategoriaEquipamento save(CategoriaEquipamentoDTO CategoriaEquipamentoDTO) {
        CategoriaEquipamento CategoriaEquipamento = new CategoriaEquipamento();
        CategoriaEquipamento.setNome(CategoriaEquipamentoDTO.nome());
        CATEGORIAS_CACHE.add(CategoriaEquipamento);
        return categoriaEquipamentoRepository.save(CategoriaEquipamento);
    }

    public List<CategoriaEquipamento> findAll() {
        CATEGORIAS_CACHE.clear();
        CATEGORIAS_CACHE.addAll(categoriaEquipamentoRepository.findAll());
        return CATEGORIAS_CACHE;
    }

    public CategoriaEquipamento edit(CategoriaEquipamentoEditDTO CategoriaEquipamentoEditDTO) {
        CategoriaEquipamento CategoriaEquipamento = new CategoriaEquipamento();
        CategoriaEquipamento.setNome(CategoriaEquipamentoEditDTO.nome());
        return categoriaEquipamentoRepository.save(CategoriaEquipamento);
    }

    public boolean CategoriaEquipamentoExiste(Integer idCategoriaEquipamento) {
        return categoriaEquipamentoRepository.existsById(idCategoriaEquipamento);
    }

    public String CategoriaEquipamentoExisteName(Integer idCategoriaEquipamento) {
        if(CATEGORIAS_CACHE.contains(idCategoriaEquipamento)) {
            return CATEGORIAS_CACHE.get(idCategoriaEquipamento).getNome();
        }
        else if (categoriaEquipamentoRepository.existsById(idCategoriaEquipamento)) {
            return categoriaEquipamentoRepository.getOne(idCategoriaEquipamento).getNome();
        }
        else {
            return null;
        }
    }
}
