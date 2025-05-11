package br.tec.pauloduarte.cnpjbrasil.cnpjbrasil.dto;

import lombok.Data;

@Data
public class LoginRequest {
    private String email;
    private String senha;
}