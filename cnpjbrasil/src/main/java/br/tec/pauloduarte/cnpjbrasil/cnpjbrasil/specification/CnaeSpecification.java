package br.tec.pauloduarte.cnpjbrasil.cnpjbrasil.specification;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import br.tec.pauloduarte.cnpjbrasil.cnpjbrasil.model.Cnae;
import jakarta.persistence.criteria.Predicate;

public class CnaeSpecification {
    
    public static Specification<Cnae> filtrarPor(String codigo, String descricao) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            
            if (codigo != null && !codigo.isEmpty()) {
                predicates.add(criteriaBuilder.equal(root.get("codigo"), codigo));
            }
            
            if (descricao != null && !descricao.isEmpty()) {
                predicates.add(criteriaBuilder.like(
                    criteriaBuilder.lower(root.get("descricao")), 
                    "%" + descricao.toLowerCase() + "%"
                ));
            }
            
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}