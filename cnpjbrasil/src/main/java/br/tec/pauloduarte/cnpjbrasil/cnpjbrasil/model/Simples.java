package br.tec.pauloduarte.cnpjbrasil.cnpjbrasil.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "simples")
public class Simples {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_simples")
    private Long idSimples;
    
    @Column(name = "cnpj_basico", length = 8)
    private String cnpjBasico;
    
    @Column(name = "opcao_pelo_simples", length = 1)
    private String opcaoPeloSimples;
    
    @Column(name = "data_opcao_simples", length = 10)
    private String dataOpcaoSimples;
    
    @Column(name = "data_exclusao_simples", length = 10)
    private String dataExclusaoSimples;
    
    @Column(name = "opcao_pelo_mei", length = 1)
    private String opcaoPeloMei;
    
    @Column(name = "data_opcao_mei", length = 10)
    private String dataOpcaoMei;
    
    @Column(name = "data_exclusao_mei", length = 10)
    private String dataExclusaoMei;
}