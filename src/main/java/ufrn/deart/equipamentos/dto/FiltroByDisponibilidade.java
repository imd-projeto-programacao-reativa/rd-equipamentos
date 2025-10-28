package ufrn.deart.equipamentos.dto;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record FiltroByDisponibilidade(
    LocalDate dataInicio,
    LocalDate dataFim,
    List<UUID> equipamentosIds
) {}
