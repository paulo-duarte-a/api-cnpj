package br.tec.pauloduarte.cnpjbrasil.cnpjbrasil.controller;

import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.tec.pauloduarte.cnpjbrasil.cnpjbrasil.model.Pais;
import br.tec.pauloduarte.cnpjbrasil.cnpjbrasil.service.PaisService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/api/paises")
@RequiredArgsConstructor
@Tag(name = "Países", description = "Países do mundo")
public class PaisController {
    
    private final PaisService paisService;
    
    @GetMapping
    @PreAuthorize("hasAnyRole('PREMIUM', 'BASIC', 'ADMIN', 'FREE')")
    public Page<Pais> getAllPaises(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size,
        @RequestParam(defaultValue = "codigo") String sort,
        @RequestParam(required = false) String codigo,
        @RequestParam(required = false) String descricao
    ) {
        size = Math.max(1, Math.min(size, 30));
        return paisService.findAll(page, size, sort, codigo, descricao);
    }
}