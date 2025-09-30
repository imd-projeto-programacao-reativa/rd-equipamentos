package ufrn.deart.equipamentos.dto;

import java.util.UUID;

public record EquipamentoForEmbeddingsDTO(
        UUID id,
        String nome,
        String descricao,
        String status
        ) {
}
