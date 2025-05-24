package br.tec.pauloduarte.cnpjbrasil.cnpjbrasil.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import br.tec.pauloduarte.cnpjbrasil.cnpjbrasil.model.Estabelecimento;
import br.tec.pauloduarte.cnpjbrasil.cnpjbrasil.repository.EstabelecimentoRepository;
import br.tec.pauloduarte.cnpjbrasil.cnpjbrasil.specification.EstabelecimentoSpecification;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EstabelecimentoService {

    private final EstabelecimentoRepository estabelecimentoRepository;

    public Page<Estabelecimento> findAll(
        int page,
        int size,
        String sort,
        String cnpjBasico,
        Integer identificadorMatrizFilial,
        String nomeFantasia,
        Integer situacaoCadastral,
        String uf,
        String municipioCodigo,
        String cnaeFiscalPrincipalCodigo
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
        
        return estabelecimentoRepository.findAll(
            EstabelecimentoSpecification.filtrarPor(
                cnpjBasico,
                identificadorMatrizFilial,
                nomeFantasia,
                situacaoCadastral,
                uf,
                municipioCodigo,
                cnaeFiscalPrincipalCodigo
            ),
            pageable
        );
    }

    public Estabelecimento save(Estabelecimento estabelecimento) {
        return estabelecimentoRepository.save(estabelecimento);
    }

    public Estabelecimento findById(Long id) {
        return estabelecimentoRepository.findById(id).orElse(null);
    }

    public void deleteById(Long id) {
        estabelecimentoRepository.deleteById(id);
    }
}