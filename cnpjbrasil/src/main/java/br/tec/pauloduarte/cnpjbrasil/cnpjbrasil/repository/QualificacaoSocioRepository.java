package br.tec.pauloduarte.cnpjbrasil.cnpjbrasil.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import br.tec.pauloduarte.cnpjbrasil.cnpjbrasil.model.QualificacaoSocio;

@Repository
public interface QualificacaoSocioRepository extends JpaRepository<QualificacaoSocio, String>, JpaSpecificationExecutor<QualificacaoSocio> {
}