package br.tec.pauloduarte.cnpjbrasil.cnpjbrasil.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import br.tec.pauloduarte.cnpjbrasil.cnpjbrasil.model.Empresa;
import br.tec.pauloduarte.cnpjbrasil.cnpjbrasil.repository.EmpresaRepository;
import br.tec.pauloduarte.cnpjbrasil.cnpjbrasil.specification.EmpresaSpecification;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmpresaService {
    
    private final EmpresaRepository empresaRepository;
    
    public Page<Empresa> findAll(
        int page, 
        int size, 
        String sort, 
        String cnpjBasico, 
        String razaoSocial,
        String naturezaJuridicaCodigo,
        String qualificacaoResponsavelCodigo,
        String porteEmpresa
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
        
        return empresaRepository.findAll(
            EmpresaSpecification.filtrarPor(
                cnpjBasico, 
                razaoSocial, 
                naturezaJuridicaCodigo, 
                qualificacaoResponsavelCodigo, 
                porteEmpresa
            ), 
            pageable
        );
    }
    
    public Empresa save(Empresa empresa) {
        return empresaRepository.save(empresa);
    }
    
    public Empresa findById(Long id) {
        return empresaRepository.findById(id).orElse(null);
    }
    
    public void deleteById(Long id) {
        empresaRepository.deleteById(id);
    }
}