package br.tec.pauloduarte.cnpjbrasil.cnpjbrasil.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import br.tec.pauloduarte.cnpjbrasil.cnpjbrasil.model.Municipio;

@Repository
public interface MunicipioRepository extends JpaRepository<Municipio, String>, JpaSpecificationExecutor<Municipio> {
}