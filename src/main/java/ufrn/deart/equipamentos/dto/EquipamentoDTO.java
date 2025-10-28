package ufrn.deart.equipamentos.dto;

import ufrn.deart.equipamentos.model.CategoriaEquipamento;
import ufrn.deart.equipamentos.model.StatusEquipamento;

import java.util.UUID;

public record EquipamentoDTO (
        UUID id,
        String nome,
        String descricao,
        String tombamento,
        CategoriaEquipamento categoria,
        String imgUrl,
        StatusEquipamento status
)
{}
