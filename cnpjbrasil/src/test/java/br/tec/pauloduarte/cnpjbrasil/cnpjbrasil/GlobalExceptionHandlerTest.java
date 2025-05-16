package br.tec.pauloduarte.cnpjbrasil.cnpjbrasil.exception;

import org.junit.jupiter.api.Test;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.servlet.NoHandlerFoundException;

import static org.junit.jupiter.api.Assertions.*;

class GlobalExceptionHandlerTest {

    GlobalExceptionHandler handler = new GlobalExceptionHandler();

    @Test
    void handleGenericException_Returns500() {
        ResponseEntity<ErrorResponse> response = handler.handleGenericException(new RuntimeException("Erro"));
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Erro interno no servidor", response.getBody().getMessage());
    }

    @Test
    void handleAccessDeniedException_Returns403() {
        ResponseEntity<ErrorResponse> response = handler.handleAccessDeniedException(new AccessDeniedException("Negado"));
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }

    @Test
    void handleMethodNotAllowed_Returns405() {
        HttpRequestMethodNotSupportedException ex = new HttpRequestMethodNotSupportedException("POST");
        ResponseEntity<ErrorResponse> response = handler.handleMethodNotAllowed(ex);
        assertEquals(HttpStatus.METHOD_NOT_ALLOWED, response.getStatusCode());
    }

    @Test
    void handleDataIntegrityViolation_Returns409() {
        DataIntegrityViolationException ex = new DataIntegrityViolationException("Erro", new RuntimeException("Detalhe"));
        ResponseEntity<ErrorResponse> response = handler.handleDataIntegrityViolation(ex);
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
    }
}
