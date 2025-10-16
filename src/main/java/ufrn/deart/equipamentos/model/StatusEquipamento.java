package ufrn.deart.equipamentos.model;

import java.util.stream.Stream;

public enum StatusEquipamento {
    DISPONIVEL(0),
    RESERVADO(1),
    EM_MANUTENCAO(2);

    private final int codigo;

    StatusEquipamento(int codigo) {
        this.codigo = codigo;
    }

    public int getCodigo() {
        return codigo;
    }

    public static StatusEquipamento of(int codigo) {
        return Stream.of(StatusEquipamento.values())
                .filter(s -> s.getCodigo() == codigo)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Código de status inválido: " + codigo));
    }
}