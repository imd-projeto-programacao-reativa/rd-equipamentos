package ufrn.deart.reservas.management.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "reservas")
public class Reserva {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "equipamento_id", nullable = false)
    private Equipamento equipamento;

    @ManyToOne
    @JoinColumn(name = "pessoa_matricula", nullable = false)
    private Pessoa pessoa;

    private OffsetDateTime dataReserva; // TIMESTAMPTZ mapeia bem para OffsetDateTime
    private LocalDate dataDevolucaoPrevista;
    private LocalDate dataDevolucaoFinal;
    private String observacoes;
    // Getters e Setters
}