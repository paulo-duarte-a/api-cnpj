package br.tec.pauloduarte.cnpjbrasil.cnpjbrasil.exception;

import java.util.Map;

public class ErrorResponse {
    private String message;
    private String details;
    private Map<String, String> errors; // Para validações

    // Construtores
    public ErrorResponse(String message) {
        this.message = message;
    }

    public ErrorResponse(String message, String details) {
        this.message = message;
        this.details = details;
    }

    public ErrorResponse(String message, Map<String, String> errors) {
        this.message = message;
        this.errors = errors;
    }

    // Getters
    public String getMessage() { return message; }
    public String getDetails() { return details; }
    public Map<String, String> getErrors() { return errors; }
}