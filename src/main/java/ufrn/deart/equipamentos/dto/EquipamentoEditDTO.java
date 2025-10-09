package ufrn.deart.equipamentos.dto;

import org.springframework.data.annotation.Id;

import java.util.UUID;

public record EquipamentoEditDTO(

        @Id
        UUID id,
        String nome,

        String descricao,

        Integer categoriaId,

        String tombamento,

        String url_imagem
)  {
}
