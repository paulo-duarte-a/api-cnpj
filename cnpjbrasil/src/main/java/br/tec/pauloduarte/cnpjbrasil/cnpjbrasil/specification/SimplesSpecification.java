package br.tec.pauloduarte.cnpjbrasil.cnpjbrasil.specification;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import br.tec.pauloduarte.cnpjbrasil.cnpjbrasil.model.Simples;
import jakarta.persistence.criteria.Predicate;

public class SimplesSpecification {
    
    public static Specification<Simples> filtrarPor(
        String cnpjBasico, 
        String opcaoPeloSimples,
        String opcaoPeloMei
    ) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            
            if (cnpjBasico != null && !cnpjBasico.isEmpty()) {
                predicates.add(criteriaBuilder.equal(root.get("cnpjBasico"), cnpjBasico));
            }
            
            if (opcaoPeloSimples != null && !opcaoPeloSimples.isEmpty()) {
                predicates.add(criteriaBuilder.equal(root.get("opcaoPeloSimples"), opcaoPeloSimples));
            }
            
            if (opcaoPeloMei != null && !opcaoPeloMei.isEmpty()) {
                predicates.add(criteriaBuilder.equal(root.get("opcaoPeloMei"), opcaoPeloMei));
            }
            
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}