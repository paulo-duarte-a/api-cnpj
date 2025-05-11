package br.tec.pauloduarte.cnpjbrasil.cnpjbrasil.specification;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import br.tec.pauloduarte.cnpjbrasil.cnpjbrasil.model.Estabelecimento;
import jakarta.persistence.criteria.Predicate;

public class EstabelecimentoSpecification {

    public static Specification<Estabelecimento> filtrarPor(
        String cnpjBasico,
        Integer identificadorMatrizFilial,
        String nomeFantasia,
        Integer situacaoCadastral,
        String uf,
        String municipioCodigo
    ) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (cnpjBasico != null && !cnpjBasico.isEmpty()) {
                predicates.add(criteriaBuilder.equal(root.get("cnpjBasico"), cnpjBasico));
            }

            if (identificadorMatrizFilial != null) {
                predicates.add(criteriaBuilder.equal(root.get("identificadorMatrizFilial"), identificadorMatrizFilial));
            }

            if (nomeFantasia != null && !nomeFantasia.isEmpty()) {
                predicates.add(criteriaBuilder.like(
                    criteriaBuilder.lower(root.get("nomeFantasia")),
                    "%" + nomeFantasia.toLowerCase() + "%"
                ));
            }

            if (situacaoCadastral != null) {
                predicates.add(criteriaBuilder.equal(root.get("situacaoCadastral"), situacaoCadastral));
            }

            if (uf != null && !uf.isEmpty()) {
                predicates.add(criteriaBuilder.equal(root.get("uf"), uf));
            }

            if (municipioCodigo != null && !municipioCodigo.isEmpty()) {
                predicates.add(criteriaBuilder.equal(root.get("municipioCodigo"), municipioCodigo));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}