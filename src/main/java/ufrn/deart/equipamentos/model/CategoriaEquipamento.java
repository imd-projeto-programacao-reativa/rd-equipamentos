package ufrn.deart.equipamentos.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("categoria_equipamento")
public class CategoriaEquipamento {

    @Id
    private Integer id;

    private String nome;

    public CategoriaEquipamento() {
    }

    public CategoriaEquipamento(Integer id) {
        this.id = id;
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
