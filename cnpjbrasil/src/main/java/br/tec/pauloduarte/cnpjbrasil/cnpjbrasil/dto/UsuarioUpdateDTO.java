package br.tec.pauloduarte.cnpjbrasil.cnpjbrasil.dto;

import br.tec.pauloduarte.cnpjbrasil.cnpjbrasil.model.UsuarioRole;
import lombok.Data;

@Data
public class UsuarioUpdateDTO {
    private String email;
    private String senha;
    private UsuarioRole role;
}
