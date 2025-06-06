package br.tec.pauloduarte.cnpjbrasil.cnpjbrasil.controller;

import br.tec.pauloduarte.cnpjbrasil.cnpjbrasil.dto.AuthResponse;
import br.tec.pauloduarte.cnpjbrasil.cnpjbrasil.dto.LoginRequest;
import br.tec.pauloduarte.cnpjbrasil.cnpjbrasil.dto.RegisterRequest;
import br.tec.pauloduarte.cnpjbrasil.cnpjbrasil.repository.UsuarioRepository;
import br.tec.pauloduarte.cnpjbrasil.cnpjbrasil.security.JwtTokenProvider;
import br.tec.pauloduarte.cnpjbrasil.cnpjbrasil.service.UsuarioService;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    JwtTokenProvider tokenProvider;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getSenha()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = tokenProvider.generateToken(authentication);
        return ResponseEntity.ok(new AuthResponse(jwt));
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterRequest registerRequest) {
        // verifica se o email já está em uso
        if (usuarioRepository.existsByEmail(registerRequest.getEmail())) {
            return new ResponseEntity<>("Erro: Email já está em uso!", HttpStatus.BAD_REQUEST);
        }
        
        usuarioService.createUsuario(
            registerRequest.getEmail(),
            registerRequest.getSenha()
        );

        // Opcionalmente, pode logar o usuário e retornar um token JWT aqui também.
        // Para simplificar, apenas confirmamos o registro.
        return ResponseEntity.ok("Usuário registrado com sucesso!");
    }
}