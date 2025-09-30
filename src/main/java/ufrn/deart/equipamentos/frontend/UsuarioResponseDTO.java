package ufrn.deart.equipamentos.frontend;

import java.util.List;

public record UsuarioResponseDTO(
        String username,
        List<String> roles
) {}
