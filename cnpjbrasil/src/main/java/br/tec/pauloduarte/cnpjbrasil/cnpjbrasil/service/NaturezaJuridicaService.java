package br.tec.pauloduarte.cnpjbrasil.cnpjbrasil.service;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import br.tec.pauloduarte.cnpjbrasil.cnpjbrasil.model.NaturezaJuridica;
import br.tec.pauloduarte.cnpjbrasil.cnpjbrasil.repository.NaturezaJuridicaRepository;
import br.tec.pauloduarte.cnpjbrasil.cnpjbrasil.specification.NaturezaJuridicaSpecification;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@CacheConfig(cacheNames = "naturezasJuridicas")
public class NaturezaJuridicaService {
    
    private final NaturezaJuridicaRepository naturezaJuridicaRepository;
    
    @Cacheable
    public Page<NaturezaJuridica> findAll(
        int page, 
        int size, 
        String sort, 
        String codigo, 
        String descricao
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
        
        return naturezaJuridicaRepository.findAll(
            NaturezaJuridicaSpecification.filtrarPor(codigo, descricao), 
            pageable
        );
    }
}