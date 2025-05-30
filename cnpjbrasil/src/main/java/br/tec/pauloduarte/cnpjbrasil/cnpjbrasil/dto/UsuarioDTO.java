package br.tec.pauloduarte.cnpjbrasil.cnpjbrasil.dto;

import br.tec.pauloduarte.cnpjbrasil.cnpjbrasil.model.UsuarioRole;
import lombok.Data;

@Data
public class UsuarioDTO {
    private String email;
    private UsuarioRole role;
}
