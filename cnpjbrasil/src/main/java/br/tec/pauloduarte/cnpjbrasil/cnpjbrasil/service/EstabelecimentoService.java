package br.tec.pauloduarte.cnpjbrasil.cnpjbrasil.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import br.tec.pauloduarte.cnpjbrasil.cnpjbrasil.dto.EstabelecimentoScrollResponseDTO;
import br.tec.pauloduarte.cnpjbrasil.cnpjbrasil.model.Estabelecimento;
import br.tec.pauloduarte.cnpjbrasil.cnpjbrasil.repository.EstabelecimentoRepository;
import br.tec.pauloduarte.cnpjbrasil.cnpjbrasil.repository.EstabelecimentoRepositoryImpl;
import br.tec.pauloduarte.cnpjbrasil.cnpjbrasil.specification.EstabelecimentoSpecification;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EstabelecimentoService {

    private final EstabelecimentoRepository estabelecimentoRepository;
    private final EstabelecimentoRepositoryImpl estabelecimentoRepositoryImpl;

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
            String cnaeFiscalPrincipalCodigo) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort));

        return estabelecimentoRepository.findAll(
                EstabelecimentoSpecification.filtrarPor(
                        cnpjBasico,
                        identificadorMatrizFilial,
                        nomeFantasia,
                        situacaoCadastral,
                        uf,
                        municipioCodigo,
                        cnaeFiscalPrincipalCodigo),
                pageable);
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

    public EstabelecimentoScrollResponseDTO findNextPageWithFiltro(
            Long lastId,
            int limit,
            String cnpjBasico,
            Integer identificadorMatrizFilial,
            String nomeFantasia,
            Integer situacaoCadastral,
            String uf,
            String municipioCodigo,
            String cnaeFiscalPrincipalCodigo) {
        Specification<Estabelecimento> filtros = EstabelecimentoSpecification.filtrarPor(
                cnpjBasico,
                identificadorMatrizFilial,
                nomeFantasia,
                situacaoCadastral,
                uf,
                municipioCodigo,
                cnaeFiscalPrincipalCodigo);

        List<Estabelecimento> resultados = estabelecimentoRepositoryImpl.buscarProximaPaginaComFiltro(
                lastId, limit + 1, filtros);

        boolean hasNext = resultados.size() > limit;
        List<Estabelecimento> dadosFinais = hasNext ? resultados.subList(0, limit) : resultados;

        Long novoLastId = dadosFinais.isEmpty()
                ? lastId
                : dadosFinais.get(dadosFinais.size() - 1).getIdEstabelecimento();

        return new EstabelecimentoScrollResponseDTO(dadosFinais, novoLastId, hasNext);
    }

}