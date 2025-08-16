package ufrn.deart.reservas.management.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record EquipamentoDTO(
        @NotNull(message = "O nome não pode ser nulo")
        @Size(min = 3, max = 25, message = "O nome deve ter entre 3 e 25 caracteres")
        String nome,

        String descricao,

        @NotNull(message = "O id do tipo é obrigatório")
        Integer tipoId,

        String patrimonio
) {}
