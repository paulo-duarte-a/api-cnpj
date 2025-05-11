package br.tec.pauloduarte.cnpjbrasil.cnpjbrasil.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class RegisterRequest {
    @Email(message = "O e-mail deve ser válido")
    @NotBlank(message = "O e-mail não pode estar vazio")
    private String email;

    @NotBlank(message = "A senha não pode estar vazia")
    @Pattern(
        regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
        message = "A senha deve conter no mínimo 8 caracteres, incluindo letra maiúscula, letra minúscula, número e caractere especial"
    )
    private String senha;
}