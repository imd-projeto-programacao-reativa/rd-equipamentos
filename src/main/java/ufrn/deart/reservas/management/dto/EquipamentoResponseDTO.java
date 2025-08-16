package ufrn.deart.reservas.management.dto;

import ufrn.deart.reservas.management.model.Equipamento;

import java.util.UUID;

public class EquipamentoResponseDTO {

    private UUID id;
    private String nome;
    private String descricao;
    private String patrimonio;
    private String tipo;
    private String status;

    // Construtor para facilitar o mapeamento a partir da Entidade
    public EquipamentoResponseDTO(Equipamento equipamento) {
        this.id = equipamento.getId();
        this.nome = equipamento.getNome();
        this.descricao = equipamento.getDescricao();
        this.patrimonio = equipamento.getPatrimonio();
        this.tipo = equipamento.getTipo().getNome();
        this.status = equipamento.getStatus().name();
    }

    public UUID getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getPatrimonio() {
        return patrimonio;
    }

    public String getTipo() {
        return tipo;
    }

    public String getStatus() {
        return status;
    }

}
