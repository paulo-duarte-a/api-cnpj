package br.tec.pauloduarte.cnpjbrasil.cnpjbrasil.service;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import br.tec.pauloduarte.cnpjbrasil.cnpjbrasil.model.Cnae;
import br.tec.pauloduarte.cnpjbrasil.cnpjbrasil.repository.CnaeRepository;
import br.tec.pauloduarte.cnpjbrasil.cnpjbrasil.specification.CnaeSpecification;
import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
@CacheConfig(cacheNames = "cnaes")
public class CnaeService {
    
    private final CnaeRepository cnaeRepository;
    
    @Cacheable
    public Page<Cnae> findAll(
        int page, 
        int size, 
        String sort, 
        String codigo, 
        String descricao
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
        
        return cnaeRepository.findAll(
            CnaeSpecification.filtrarPor(codigo, descricao), 
            pageable
        );
    }
}