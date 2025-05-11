package br.tec.pauloduarte.cnpjbrasil.cnpjbrasil.service;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import br.tec.pauloduarte.cnpjbrasil.cnpjbrasil.model.QualificacaoSocio;
import br.tec.pauloduarte.cnpjbrasil.cnpjbrasil.repository.QualificacaoSocioRepository;
import br.tec.pauloduarte.cnpjbrasil.cnpjbrasil.specification.QualificacaoSocioSpecification;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@CacheConfig(cacheNames = "qualificacoesSocio")
public class QualificacaoSocioService {
    
    private final QualificacaoSocioRepository qualificacaoSocioRepository;

    @Cacheable
    public Page<QualificacaoSocio> findAll(
        int page, 
        int size, 
        String sort, 
        String codigo, 
        String descricao
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
        
        return qualificacaoSocioRepository.findAll(
            QualificacaoSocioSpecification.filtrarPor(codigo, descricao), 
            pageable
        );
    }
}