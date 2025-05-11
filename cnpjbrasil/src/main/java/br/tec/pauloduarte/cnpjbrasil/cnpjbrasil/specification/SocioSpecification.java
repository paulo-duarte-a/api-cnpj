package br.tec.pauloduarte.cnpjbrasil.cnpjbrasil.specification;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import br.tec.pauloduarte.cnpjbrasil.cnpjbrasil.model.Socio;
import jakarta.persistence.criteria.Predicate;

public class SocioSpecification {
    
    public static Specification<Socio> filtrarPor(
        String cnpjBasico, 
        Integer identificadorSocio,
        String nomeSocio,
        String qualificacaoSocioCodigo,
        String paisCodigo,
        Integer faixaEtaria
    ) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            
            if (cnpjBasico != null && !cnpjBasico.isEmpty()) {
                predicates.add(criteriaBuilder.equal(root.get("cnpjBasico"), cnpjBasico));
            }
            
            if (identificadorSocio != null) {
                predicates.add(criteriaBuilder.equal(root.get("identificadorSocio"), identificadorSocio));
            }
            
            if (nomeSocio != null && !nomeSocio.isEmpty()) {
                predicates.add(criteriaBuilder.like(
                    criteriaBuilder.lower(root.get("nomeSocio")), 
                    "%" + nomeSocio.toLowerCase() + "%"
                ));
            }
            
            if (qualificacaoSocioCodigo != null && !qualificacaoSocioCodigo.isEmpty()) {
                predicates.add(criteriaBuilder.equal(
                    root.get("qualificacaoSocio").get("codigo"), 
                    qualificacaoSocioCodigo
                ));
            }
            
            if (paisCodigo != null && !paisCodigo.isEmpty()) {
                predicates.add(criteriaBuilder.equal(
                    root.get("pais").get("codigo"), 
                    paisCodigo
                ));
            }
            
            if (faixaEtaria != null) {
                predicates.add(criteriaBuilder.equal(root.get("faixaEtaria"), faixaEtaria));
            }
            
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}