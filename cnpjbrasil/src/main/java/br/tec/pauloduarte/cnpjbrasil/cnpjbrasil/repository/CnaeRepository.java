package br.tec.pauloduarte.cnpjbrasil.cnpjbrasil.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import br.tec.pauloduarte.cnpjbrasil.cnpjbrasil.model.Cnae;

@Repository
public interface CnaeRepository extends JpaRepository<Cnae, String>, JpaSpecificationExecutor<Cnae> {
}