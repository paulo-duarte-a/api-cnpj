package br.tec.pauloduarte.cnpjbrasil.cnpjbrasil.repository;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import br.tec.pauloduarte.cnpjbrasil.cnpjbrasil.model.Estabelecimento;

public interface EstabelecimentoRepositoryCustom {
    List<Estabelecimento> buscarProximaPaginaComFiltro(
        Long lastId,
        int limit,
        Specification<Estabelecimento> filtros
    );
}
