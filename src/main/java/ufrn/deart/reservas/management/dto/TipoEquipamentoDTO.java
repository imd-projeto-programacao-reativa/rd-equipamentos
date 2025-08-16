package ufrn.deart.reservas.management.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record TipoEquipamentoDTO(
        @NotNull(message = "O nome do tipo não pode ser nulo")
        @Size(min = 3, max = 30, message = "O nome precisa ter entre 3 e 30 caracteres")
        String nome

) {}