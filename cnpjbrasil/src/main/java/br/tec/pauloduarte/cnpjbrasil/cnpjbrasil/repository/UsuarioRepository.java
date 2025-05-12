package br.tec.pauloduarte.cnpjbrasil.cnpjbrasil.repository;

import br.tec.pauloduarte.cnpjbrasil.cnpjbrasil.model.Usuario;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByEmail(String email);
    boolean existsByEmail(String email);
    Page<Usuario> findAll(Specification<Usuario> filtrarPor, Pageable pageable);
}