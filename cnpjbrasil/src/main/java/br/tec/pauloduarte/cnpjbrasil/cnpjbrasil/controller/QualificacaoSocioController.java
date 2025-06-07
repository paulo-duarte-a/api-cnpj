package br.tec.pauloduarte.cnpjbrasil.cnpjbrasil.controller;

import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.tec.pauloduarte.cnpjbrasil.cnpjbrasil.model.QualificacaoSocio;
import br.tec.pauloduarte.cnpjbrasil.cnpjbrasil.service.QualificacaoSocioService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/qualificacoes-socios")
@RequiredArgsConstructor
@Tag(name = "Qualificação do Sócio", description = "Qualificação do sócio da empresa")
public class QualificacaoSocioController {
    
    private final QualificacaoSocioService qualificacaoSocioService;
    
    @GetMapping
    @PreAuthorize("hasAnyRole('PREMIUM', 'BASIC', 'ADMIN', 'FREE')")
    public Page<QualificacaoSocio> getAllQualificacoesSocios(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size,
        @RequestParam(defaultValue = "codigo") String sort,
        @RequestParam(required = false) String codigo,
        @RequestParam(required = false) String descricao
    ) {
        size = Math.max(1, Math.min(size, 30));
        return qualificacaoSocioService.findAll(page, size, sort, codigo, descricao);
    }
}