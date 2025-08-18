package ufrn.deart.reservas.management.dto;

/*
* [
  {
    "id": 1,
    "nome": "Projetor",
    "imagem": "https://via.placeholder.com/200",
    "categoria": "Audiovisual",
    "status": "disponivel"
  },
  ...
]*/

import java.util.UUID;

public record EquipamentoFrontDTO(
        UUID id,
        String nome,
        String url_image,
        String categoria,
        String status

) {
}
