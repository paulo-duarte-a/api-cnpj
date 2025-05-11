package br.tec.pauloduarte.cnpjbrasil.cnpjbrasil.controller;

import org.springframework.data.domain.Page;
// import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.tec.pauloduarte.cnpjbrasil.cnpjbrasil.model.Simples;
import br.tec.pauloduarte.cnpjbrasil.cnpjbrasil.service.SimplesService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/simples")
@RequiredArgsConstructor
@Tag(name = "Simples", description = "Simples Nacional e MEI")
public class SimplesController {
    
    private final SimplesService simplesService;
    
    @GetMapping
    public Page<Simples> getAllSimples(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size,
        @RequestParam(defaultValue = "idSimples") String sort,
        @RequestParam(required = false) String cnpjBasico,
        @RequestParam(required = false) String opcaoPeloSimples,
        @RequestParam(required = false) String opcaoPeloMei
    ) {
        return simplesService.findAll(page, size, sort, cnpjBasico, opcaoPeloSimples, opcaoPeloMei);
    }
    
    @GetMapping("/{id}")
    public Simples getSimplesById(@PathVariable Long id) {
        return simplesService.findById(id);
    }
    
    // @PostMapping
    public Simples createSimples(@RequestBody Simples simples) {
        return simplesService.save(simples);
    }
    
    // @PutMapping("/{id}")
    public Simples updateSimples(@PathVariable Long id, @RequestBody Simples simples) {
        simples.setIdSimples(id);
        return simplesService.save(simples);
    }
    
    // @DeleteMapping("/{id}")
    public void deleteSimples(@PathVariable Long id) {
        simplesService.deleteById(id);
    }
}