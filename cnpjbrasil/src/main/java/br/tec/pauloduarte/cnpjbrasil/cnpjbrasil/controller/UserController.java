package br.tec.pauloduarte.cnpjbrasil.cnpjbrasil.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import br.tec.pauloduarte.cnpjbrasil.cnpjbrasil.dto.UsuarioDTO;
import br.tec.pauloduarte.cnpjbrasil.cnpjbrasil.dto.UsuarioUpdateDTO;
import br.tec.pauloduarte.cnpjbrasil.cnpjbrasil.model.Usuario;
import br.tec.pauloduarte.cnpjbrasil.cnpjbrasil.service.UsuarioService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
@Tag(name = "Usuários", description = "Usuários do sistema")
public class UserController {

    private final UsuarioService usuarioService;
    
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Page<Usuario> getAllUsuarios(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size,
        @RequestParam(defaultValue = "id") String sort,
        @RequestParam(required = false) String email,
        @RequestParam(required = false) String role
    ) {
        size = Math.max(1, Math.min(size, 30));
        return usuarioService.findAll(page, size, sort, email, role);
    }

    @GetMapping("/me")
    public ResponseEntity<UsuarioDTO> getCurrentUser() {
        return ResponseEntity.ok(usuarioService.getCurrentUser());
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Usuario> updateUsuario(
        @PathVariable Long id,
        @RequestBody UsuarioUpdateDTO usuarioUpdateDTO
    ) {
        Usuario updatedUsuario = usuarioService.updateUsuario(id, usuarioUpdateDTO);
        return ResponseEntity.ok(updatedUsuario);
    }
}