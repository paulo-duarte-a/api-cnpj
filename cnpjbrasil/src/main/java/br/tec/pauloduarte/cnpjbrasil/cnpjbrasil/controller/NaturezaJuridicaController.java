package br.tec.pauloduarte.cnpjbrasil.cnpjbrasil.controller;

import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.tec.pauloduarte.cnpjbrasil.cnpjbrasil.model.NaturezaJuridica;
import br.tec.pauloduarte.cnpjbrasil.cnpjbrasil.service.NaturezaJuridicaService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/naturezas-juridicas")
@RequiredArgsConstructor
@Tag(name = "Natureza Jurídica", description = "Natureza Jurídica das empresas")
public class NaturezaJuridicaController {
    
    private final NaturezaJuridicaService naturezaJuridicaService;
    
    @GetMapping
    @PreAuthorize("hasAnyRole('PREMIUM', 'ADMIN', 'FREE')")
    public Page<NaturezaJuridica> getAllNaturezasJuridicas(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size,
        @RequestParam(defaultValue = "codigo") String sort,
        @RequestParam(required = false) String codigo,
        @RequestParam(required = false) String descricao
    ) {
        size = Math.max(1, Math.min(size, 30));
        return naturezaJuridicaService.findAll(page, size, sort, codigo, descricao);
    }
}