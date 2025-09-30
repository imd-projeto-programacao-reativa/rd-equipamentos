package ufrn.deart.equipamentos.dto;

import java.util.UUID;

public interface EquipamentoDisponibilidadeDTO {
    UUID getEquipamento_id();
    String getEquipamento_nome();
    String getStatus_disponibilidade();
}