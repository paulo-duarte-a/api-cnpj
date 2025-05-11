package br.tec.pauloduarte.cnpjbrasil.cnpjbrasil.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import br.tec.pauloduarte.cnpjbrasil.cnpjbrasil.model.Simples;
import br.tec.pauloduarte.cnpjbrasil.cnpjbrasil.repository.SimplesRepository;
import br.tec.pauloduarte.cnpjbrasil.cnpjbrasil.specification.SimplesSpecification;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SimplesService {
    
    private final SimplesRepository simplesRepository;
    
    public Page<Simples> findAll(
        int page, 
        int size, 
        String sort, 
        String cnpjBasico, 
        String opcaoPeloSimples,
        String opcaoPeloMei
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
        
        return simplesRepository.findAll(
            SimplesSpecification.filtrarPor(cnpjBasico, opcaoPeloSimples, opcaoPeloMei), 
            pageable
        );
    }
    
    public Simples save(Simples simples) {
        return simplesRepository.save(simples);
    }
    
    public Simples findById(Long id) {
        return simplesRepository.findById(id).orElse(null);
    }
    
    public void deleteById(Long id) {
        simplesRepository.deleteById(id);
    }
}