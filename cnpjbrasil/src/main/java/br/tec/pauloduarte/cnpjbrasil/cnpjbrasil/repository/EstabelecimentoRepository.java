package br.tec.pauloduarte.cnpjbrasil.cnpjbrasil.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import br.tec.pauloduarte.cnpjbrasil.cnpjbrasil.model.Estabelecimento;

@Repository
public interface EstabelecimentoRepository extends JpaRepository<Estabelecimento, Long>, JpaSpecificationExecutor<Estabelecimento> {
}