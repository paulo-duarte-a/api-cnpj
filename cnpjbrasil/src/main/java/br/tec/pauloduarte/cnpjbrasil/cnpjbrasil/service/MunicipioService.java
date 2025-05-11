package br.tec.pauloduarte.cnpjbrasil.cnpjbrasil.service;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import br.tec.pauloduarte.cnpjbrasil.cnpjbrasil.model.Municipio;
import br.tec.pauloduarte.cnpjbrasil.cnpjbrasil.repository.MunicipioRepository;
import br.tec.pauloduarte.cnpjbrasil.cnpjbrasil.specification.MunicipioSpecification;
import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
@CacheConfig(cacheNames = "municipios")
public class MunicipioService {
    
    private final MunicipioRepository municipioRepository;
    
    @Cacheable
    public Page<Municipio> findAll(
        int page, 
        int size, 
        String sort, 
        String codigo, 
        String descricao
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
        
        return municipioRepository.findAll(
            MunicipioSpecification.filtrarPor(codigo, descricao), 
            pageable
        );
    }
}