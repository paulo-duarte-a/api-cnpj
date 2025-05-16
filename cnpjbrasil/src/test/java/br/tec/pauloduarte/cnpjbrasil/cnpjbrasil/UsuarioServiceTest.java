package br.tec.pauloduarte.cnpjbrasil.cnpjbrasil.service;

import br.tec.pauloduarte.cnpjbrasil.cnpjbrasil.dto.UsuarioUpdateDTO;
import br.tec.pauloduarte.cnpjbrasil.cnpjbrasil.model.Usuario;
import br.tec.pauloduarte.cnpjbrasil.cnpjbrasil.model.UsuarioRole;
import br.tec.pauloduarte.cnpjbrasil.cnpjbrasil.repository.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UsuarioServiceTest {

    @Mock private UsuarioRepository usuarioRepository;
    @Mock private PasswordEncoder passwordEncoder;
    @InjectMocks private UsuarioService usuarioService;

    @Test
    void createUsuario_Successful() {
        when(usuarioRepository.existsByEmail("email@test.com")).thenReturn(false);
        when(passwordEncoder.encode("Senha123")).thenReturn("senha-criptografada");
        when(usuarioRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        Usuario user = usuarioService.createUsuario("email@test.com", "Senha123");

        assertEquals("email@test.com", user.getEmail());
        assertEquals("senha-criptografada", user.getSenha());
        assertEquals(UsuarioRole.FREE, user.getRole());
    }

    @Test
    void updateUsuario_Throws_WhenUserNotFound() {
        when(usuarioRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () ->
            usuarioService.updateUsuario(1L, new UsuarioUpdateDTO())
        );
    }

    @Test
    void updateUsuario_Throws_WhenEmailExists() {
        Usuario existing = new Usuario();
        existing.setId(1L);
        existing.setEmail("old@test.com");

        UsuarioUpdateDTO dto = new UsuarioUpdateDTO();
        dto.setEmail("new@test.com");

        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(usuarioRepository.existsByEmail("new@test.com")).thenReturn(true);

        assertThrows(RuntimeException.class, () ->
            usuarioService.updateUsuario(1L, dto)
        );
    }
}
