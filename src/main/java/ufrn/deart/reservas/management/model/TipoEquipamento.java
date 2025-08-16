package ufrn.deart.reservas.management.model;

import jakarta.persistence.*;

@Entity
@Table(name = "tipos_equipamento")
public class TipoEquipamento {

    public TipoEquipamento(Integer id) {
        this.id = id;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true)
    private String nome;

    public TipoEquipamento() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}