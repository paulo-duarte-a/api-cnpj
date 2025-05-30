package br.tec.pauloduarte.cnpjbrasil.cnpjbrasil.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import br.tec.pauloduarte.cnpjbrasil.cnpjbrasil.model.Estabelecimento;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;


@Repository
public class EstabelecimentoRepositoryImpl implements EstabelecimentoRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Estabelecimento> buscarProximaPaginaComFiltro(
        Long lastId,
        int limit,
        Specification<Estabelecimento> filtros
    ) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Estabelecimento> query = cb.createQuery(Estabelecimento.class);
        Root<Estabelecimento> root = query.from(Estabelecimento.class);

        List<Predicate> predicates = new ArrayList<>();

        if (filtros != null) {
            Predicate filtroPredicate = filtros.toPredicate(root, query, cb);
            if (filtroPredicate != null) {
                predicates.add(filtroPredicate);
            }
        }

        if (lastId != null) {
            predicates.add(cb.greaterThan(root.get("idEstabelecimento"), lastId));
        }

        query.select(root)
            .where(cb.and(predicates.toArray(new Predicate[0])))
            .orderBy(cb.asc(root.get("idEstabelecimento")));

        return entityManager.createQuery(query)
            .setMaxResults(limit)
            .getResultList();
    }
}
