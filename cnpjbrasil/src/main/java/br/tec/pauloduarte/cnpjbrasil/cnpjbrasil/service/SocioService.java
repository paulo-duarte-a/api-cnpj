package br.tec.pauloduarte.cnpjbrasil.cnpjbrasil.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import br.tec.pauloduarte.cnpjbrasil.cnpjbrasil.model.Socio;
import br.tec.pauloduarte.cnpjbrasil.cnpjbrasil.repository.SocioRepository;
import br.tec.pauloduarte.cnpjbrasil.cnpjbrasil.specification.SocioSpecification;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SocioService {
    
    private final SocioRepository socioRepository;
    
    public Page<Socio> findAll(
        int page, 
        int size, 
        String sort, 
        String cnpjBasico, 
        Integer identificadorSocio,
        String nomeSocio,
        String qualificacaoSocioCodigo,
        String paisCodigo,
        Integer faixaEtaria
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
        
        return socioRepository.findAll(
            SocioSpecification.filtrarPor(
                cnpjBasico, 
                identificadorSocio, 
                nomeSocio, 
                qualificacaoSocioCodigo, 
                paisCodigo, 
                faixaEtaria
            ), 
            pageable
        );
    }
    
    public Socio save(Socio socio) {
        return socioRepository.save(socio);
    }
    
    public Socio findById(Long id) {
        return socioRepository.findById(id).orElse(null);
    }
    
    public void deleteById(Long id) {
        socioRepository.deleteById(id);
    }
}