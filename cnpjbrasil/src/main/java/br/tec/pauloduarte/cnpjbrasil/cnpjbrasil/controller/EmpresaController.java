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

import br.tec.pauloduarte.cnpjbrasil.cnpjbrasil.model.Empresa;
import br.tec.pauloduarte.cnpjbrasil.cnpjbrasil.service.EmpresaService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/empresas")
@RequiredArgsConstructor
@Tag(name = "Empresas", description = "Empresas e seus dados")
public class EmpresaController {
    
    private final EmpresaService empresaService;
    
    @GetMapping
    @PreAuthorize("hasAnyRole('PREMIUM', 'BASIC', 'ADMIN', 'FREE')")
    public Page<Empresa> getAllEmpresas(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size,
        @RequestParam(defaultValue = "idEmpresa") String sort,
        @RequestParam(required = false) String cnpjBasico,
        @RequestParam(required = false) String razaoSocial,
        @RequestParam(required = false) String naturezaJuridicaCodigo,
        @RequestParam(required = false) String qualificacaoResponsavelCodigo,
        @RequestParam(required = false) String porteEmpresa
    ) {
        size = Math.max(1, Math.min(size, 30));
        return empresaService.findAll(
            page, size, sort, 
            cnpjBasico, razaoSocial, 
            naturezaJuridicaCodigo, qualificacaoResponsavelCodigo, 
            porteEmpresa
        );
    }
    
    // @GetMapping("/{id}")
    public Empresa getEmpresaById(@PathVariable Long id) {
        return empresaService.findById(id);
    }
    
    // @PostMapping
    public Empresa createEmpresa(@RequestBody Empresa empresa) {
        return empresaService.save(empresa);
    }
    
    // @PutMapping("/{id}")
    public Empresa updateEmpresa(@PathVariable Long id, @RequestBody Empresa empresa) {
        empresa.setIdEmpresa(id);
        return empresaService.save(empresa);
    }
    
    // @DeleteMapping("/{id}")
    public void deleteEmpresa(@PathVariable Long id) {
        empresaService.deleteById(id);
    }
}