package br.tec.pauloduarte.cnpjbrasil.cnpjbrasil.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.tec.pauloduarte.cnpjbrasil.cnpjbrasil.dto.UsuarioUpdateDTO;
import br.tec.pauloduarte.cnpjbrasil.cnpjbrasil.model.Usuario;
import br.tec.pauloduarte.cnpjbrasil.cnpjbrasil.model.UsuarioRole;
import br.tec.pauloduarte.cnpjbrasil.cnpjbrasil.repository.UsuarioRepository;
import br.tec.pauloduarte.cnpjbrasil.cnpjbrasil.specification.UsuarioSpecification;
import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
@CacheConfig(cacheNames = "usuarios")
public class UsuarioService {
    
    private final UsuarioRepository usuarioRepository;
    private final RateLimitingService rateLimitingService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Cacheable
    public Page<Usuario> findAll(
        int page, 
        int size, 
        String sort, 
        String email, 
        String role
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
        
        return usuarioRepository.findAll(
            UsuarioSpecification.filtrarPor(email, role), 
            pageable
        );
    }

    @CacheEvict(allEntries = true)
    public Usuario createUsuario(String email, String password) {
        if (usuarioRepository.existsByEmail(email)) {
            throw new RuntimeException("Email já está em uso");
        }
        Usuario usuario = new Usuario();
        usuario.setEmail(email);
        usuario.setSenha(passwordEncoder.encode(password));

        usuario.setRole(UsuarioRole.FREE);

        return usuarioRepository.save(usuario);
    }

    @CacheEvict(allEntries = true)
    public Usuario updateUsuario(Long id, UsuarioUpdateDTO usuarioUpdateDTO) {
        Usuario existingUsuario = usuarioRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        
        if (usuarioUpdateDTO.getEmail() != null) {
            String newEmail = usuarioUpdateDTO.getEmail();
            if (!newEmail.equals(existingUsuario.getEmail())) {
                if (usuarioRepository.existsByEmail(newEmail)) {
                    throw new RuntimeException("Email já está em uso");
                }
                existingUsuario.setEmail(newEmail);
            }
        }
        
        if (usuarioUpdateDTO.getSenha() != null) {
            existingUsuario.setSenha(passwordEncoder.encode(usuarioUpdateDTO.getSenha()));
        }
        
        if (usuarioUpdateDTO.getRole() != null) {
            existingUsuario.setRole(usuarioUpdateDTO.getRole());
            rateLimitingService.updateBucketForUser(existingUsuario.getEmail(), usuarioUpdateDTO.getRole());
        }
        
        return usuarioRepository.save(existingUsuario);
    }
}