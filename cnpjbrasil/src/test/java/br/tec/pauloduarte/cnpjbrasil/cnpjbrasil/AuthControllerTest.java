package br.tec.pauloduarte.cnpjbrasil.cnpjbrasil.controller;

import br.tec.pauloduarte.cnpjbrasil.cnpjbrasil.dto.AuthResponse;
import br.tec.pauloduarte.cnpjbrasil.cnpjbrasil.dto.LoginRequest;
import br.tec.pauloduarte.cnpjbrasil.cnpjbrasil.dto.RegisterRequest;
import br.tec.pauloduarte.cnpjbrasil.cnpjbrasil.repository.UsuarioRepository;
import br.tec.pauloduarte.cnpjbrasil.cnpjbrasil.security.JwtTokenProvider;
import br.tec.pauloduarte.cnpjbrasil.cnpjbrasil.service.UsuarioService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthControllerTest {

    @Mock private AuthenticationManager authenticationManager;
    @Mock private UsuarioRepository usuarioRepository;
    @Mock private UsuarioService usuarioService;
    @Mock private JwtTokenProvider tokenProvider;
    @InjectMocks private AuthController authController;

    @Test
    void authenticateUser_ReturnsToken_WhenCredentialsValid() {
        LoginRequest loginRequest = new LoginRequest("email@teste.com", "Senha123!");
        Authentication auth = mock(Authentication.class);
        when(authenticationManager.authenticate(any())).thenReturn(auth);
        when(tokenProvider.generateToken(auth)).thenReturn("mocked-token");

        ResponseEntity<?> response = authController.authenticateUser(loginRequest);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(((AuthResponse) response.getBody()).getAccessToken().contains("mocked-token"));
    }

    @Test
    void registerUser_ReturnsError_WhenEmailExists() {
        RegisterRequest req = new RegisterRequest();
        req.setEmail("email@teste.com");
        req.setSenha("Senha123!");

        when(usuarioRepository.existsByEmail(req.getEmail())).thenReturn(true);

        ResponseEntity<?> response = authController.registerUser(req);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void registerUser_Successful() {
        RegisterRequest req = new RegisterRequest();
        req.setEmail("novo@teste.com");
        req.setSenha("Senha123!");

        when(usuarioRepository.existsByEmail(req.getEmail())).thenReturn(false);

        ResponseEntity<?> response = authController.registerUser(req);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Usuário registrado com sucesso!", response.getBody());
    }
}
