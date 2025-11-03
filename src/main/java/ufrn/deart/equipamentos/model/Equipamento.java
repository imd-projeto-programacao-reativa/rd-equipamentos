package ufrn.deart.equipamentos.model;

import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.UUID;

@Entity
@Table(name = "equipamentos")
public class Equipamento {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false)
    private String nome;
    private String descricao;
    private String tombamento;

    @ManyToOne
    @JoinColumn(name = "categoria_id", nullable = false)
    private CategoriaEquipamento categoria;

    @JoinColumn(name = "img_url", nullable = false)
    private String imgUrl;

//    @Enumerated(EnumType.STRING)
@Enumerated(EnumType.STRING)

//    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Column(nullable = false)
    private StatusEquipamento status;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getTombamento() {
        return tombamento;
    }

    public void setTombamento(String tombamento) {
        this.tombamento = tombamento;
    }

    public CategoriaEquipamento getCategoria() {
        return categoria;
    }

    public void setCategoria(CategoriaEquipamento categoria) {
        this.categoria = categoria;
    }

    public String getUrlImagem() {
        return imgUrl;
    }

    public void setUrlImagem(String urlImagem) {
        this.imgUrl = urlImagem;
    }

    public StatusEquipamento getStatus() {
        return status;
    }

    public void setStatus(StatusEquipamento status) {
        this.status = status;
    }
}

