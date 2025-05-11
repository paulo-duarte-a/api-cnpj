package br.tec.pauloduarte.cnpjbrasil.cnpjbrasil.service;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import br.tec.pauloduarte.cnpjbrasil.cnpjbrasil.model.Pais;
import br.tec.pauloduarte.cnpjbrasil.cnpjbrasil.repository.PaisRepository;
import br.tec.pauloduarte.cnpjbrasil.cnpjbrasil.specification.PaisSpecification;
import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
@CacheConfig(cacheNames = "paises")
public class PaisService {
    
    private final PaisRepository paisRepository;

    @Cacheable
    public Page<Pais> findAll(
        int page, 
        int size, 
        String sort, 
        String codigo, 
        String descricao
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
        
        return paisRepository.findAll(
            PaisSpecification.filtrarPor(codigo, descricao), 
            pageable
        );
    }
}