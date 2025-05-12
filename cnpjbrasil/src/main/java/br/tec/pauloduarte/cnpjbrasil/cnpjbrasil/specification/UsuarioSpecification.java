package br.tec.pauloduarte.cnpjbrasil.cnpjbrasil.specification;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import br.tec.pauloduarte.cnpjbrasil.cnpjbrasil.model.Usuario;
import jakarta.persistence.criteria.Predicate;

public class UsuarioSpecification {
    
    public static Specification<Usuario> filtrarPor(String email, String role) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            
            if (email != null && !email.isEmpty()) {
                predicates.add(criteriaBuilder.equal(root.get("email"), email));
            }
            
            if (role != null && !role.isEmpty()) {
                predicates.add(criteriaBuilder.like(
                    criteriaBuilder.lower(root.get("role")), 
                    "%" + role.toLowerCase() + "%"
                ));
            }
            
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}