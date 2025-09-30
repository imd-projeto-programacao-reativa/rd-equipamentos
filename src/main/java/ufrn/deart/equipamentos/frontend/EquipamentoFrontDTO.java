package ufrn.deart.equipamentos.frontend;

import java.util.UUID;

public record EquipamentoFrontDTO(
        UUID id,
        String nome,
        String descricao,
        String url_image,
        String categoria,
        String status

) {
}
