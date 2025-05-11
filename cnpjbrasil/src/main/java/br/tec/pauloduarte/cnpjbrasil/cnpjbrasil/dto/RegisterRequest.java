package br.tec.pauloduarte.cnpjbrasil.cnpjbrasil.dto;

import lombok.Data;

@Data
public class RegisterRequest {
    private String email;
    private String senha;
}