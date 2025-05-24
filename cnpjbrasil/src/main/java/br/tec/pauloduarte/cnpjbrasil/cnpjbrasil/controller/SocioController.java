package br.tec.pauloduarte.cnpjbrasil.cnpjbrasil.controller;

import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
// import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.tec.pauloduarte.cnpjbrasil.cnpjbrasil.model.Socio;
import br.tec.pauloduarte.cnpjbrasil.cnpjbrasil.service.SocioService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/socios")
@RequiredArgsConstructor
@Tag(name = "Sócios", description = "Sócios da empresa")
public class SocioController {
    
    private final SocioService socioService;
    
    @GetMapping
    @PreAuthorize("hasAnyRole('PREMIUM', 'ADMIN', 'FREE')")
    public Page<Socio> getAllSocios(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size,
        @RequestParam(defaultValue = "idSocio") String sort,
        @RequestParam(required = false) String cnpjBasico,
        @RequestParam(required = false) Integer identificadorSocio,
        @RequestParam(required = false) String nomeSocio,
        @RequestParam(required = false) String qualificacaoSocioCodigo,
        @RequestParam(required = false) String paisCodigo,
        @RequestParam(required = false) Integer faixaEtaria
    ) {
        size = Math.max(1, Math.min(size, 30));
        return socioService.findAll(
            page, size, sort, 
            cnpjBasico, identificadorSocio, 
            nomeSocio, qualificacaoSocioCodigo, 
            paisCodigo, faixaEtaria
        );
    }
    
    // @GetMapping("/{id}")
    public Socio getSocioById(@PathVariable Long id) {
        return socioService.findById(id);
    }
    
    // @PostMapping
    public Socio createSocio(@RequestBody Socio socio) {
        return socioService.save(socio);
    }
    
    // @PutMapping("/{id}")
    public Socio updateSocio(@PathVariable Long id, @RequestBody Socio socio) {
        socio.setIdSocio(id);
        return socioService.save(socio);
    }
    
    // @DeleteMapping("/{id}")
    public void deleteSocio(@PathVariable Long id) {
        socioService.deleteById(id);
    }
}