package ufrn.deart.reservas.management.model;

import jakarta.persistence.*;

@Entity
@Table(name = "pessoas")
public class Pessoa {
    @Id
    private String matricula;

    @Column(nullable = false)
    private String nome;

    private String curso;
    // Getters e Setters
}