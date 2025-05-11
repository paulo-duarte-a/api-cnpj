package br.tec.pauloduarte.cnpjbrasil.cnpjbrasil.specification;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import br.tec.pauloduarte.cnpjbrasil.cnpjbrasil.model.Empresa;
import jakarta.persistence.criteria.Predicate;

public class EmpresaSpecification {
    
    public static Specification<Empresa> filtrarPor(
        String cnpjBasico, 
        String razaoSocial,
        String naturezaJuridicaCodigo,
        String qualificacaoResponsavelCodigo,
        String porteEmpresa
    ) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            
            if (cnpjBasico != null && !cnpjBasico.isEmpty()) {
                predicates.add(criteriaBuilder.equal(root.get("cnpjBasico"), cnpjBasico));
            }
            
            if (razaoSocial != null && !razaoSocial.isEmpty()) {
                predicates.add(criteriaBuilder.like(
                    criteriaBuilder.lower(root.get("razaoSocial")), 
                    "%" + razaoSocial.toLowerCase() + "%"
                ));
            }
            
            if (naturezaJuridicaCodigo != null && !naturezaJuridicaCodigo.isEmpty()) {
                predicates.add(criteriaBuilder.equal(
                    root.get("naturezaJuridica").get("codigo"), 
                    naturezaJuridicaCodigo
                ));
            }
            
            if (qualificacaoResponsavelCodigo != null && !qualificacaoResponsavelCodigo.isEmpty()) {
                predicates.add(criteriaBuilder.equal(
                    root.get("qualificacaoResponsavel").get("codigo"), 
                    qualificacaoResponsavelCodigo
                ));
            }
            
            if (porteEmpresa != null && !porteEmpresa.isEmpty()) {
                predicates.add(criteriaBuilder.equal(root.get("porteEmpresa"), porteEmpresa));
            }
            
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}