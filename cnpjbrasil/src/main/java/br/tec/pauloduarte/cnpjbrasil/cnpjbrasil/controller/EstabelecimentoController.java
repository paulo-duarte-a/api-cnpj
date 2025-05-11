package br.tec.pauloduarte.cnpjbrasil.cnpjbrasil.controller;

import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import br.tec.pauloduarte.cnpjbrasil.cnpjbrasil.model.Estabelecimento;
import br.tec.pauloduarte.cnpjbrasil.cnpjbrasil.service.EstabelecimentoService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/estabelecimentos")
@RequiredArgsConstructor
@Tag(name = "Estabelecimentos", description = "Estabelecimentos das empresas")
public class EstabelecimentoController {

    private final EstabelecimentoService estabelecimentoService;

    @GetMapping
    @PreAuthorize("hasAnyRole('PREMIUM', 'ADMIN')")
    public Page<Estabelecimento> getAllEstabelecimentos(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size,
        @RequestParam(defaultValue = "idEmpresa") String sort,
        @RequestParam(required = false) String cnpjBasico,
        @RequestParam(required = false) Integer identificadorMatrizFilial,
        @RequestParam(required = false) String nomeFantasia,
        @RequestParam(required = false) Integer situacaoCadastral,
        @RequestParam(required = false) String uf,
        @RequestParam(required = false) String municipioCodigo
    ) {
        return estabelecimentoService.findAll(
            page, size, sort,
            cnpjBasico, identificadorMatrizFilial,
            nomeFantasia, situacaoCadastral,
            uf, municipioCodigo
        );
    }

    // @GetMapping("/{id}")
    public Estabelecimento getEstabelecimentoById(@PathVariable Long id) {
        return estabelecimentoService.findById(id);
    }

    // @PostMapping
    public Estabelecimento createEstabelecimento(@RequestBody Estabelecimento estabelecimento) {
        return estabelecimentoService.save(estabelecimento);
    }

    // @PutMapping("/{id}")
    public Estabelecimento updateEstabelecimento(@PathVariable Long id, @RequestBody Estabelecimento estabelecimento) {
        estabelecimento.setIdEmpresa(id);
        return estabelecimentoService.save(estabelecimento);
    }

    // @DeleteMapping("/{id}")
    public void deleteEstabelecimento(@PathVariable Long id) {
        estabelecimentoService.deleteById(id);
    }
}