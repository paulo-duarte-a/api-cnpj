package br.tec.pauloduarte.cnpjbrasil.cnpjbrasil.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "empresas")
public class Empresa {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_empresa")
    private Long idEmpresa;
    
    @Column(name = "cnpj_basico", length = 8)
    private String cnpjBasico;
    
    @Column(name = "razao_social", length = 255)
    private String razaoSocial;
    
    @ManyToOne
    @JoinColumn(name = "natureza_juridica_codigo", referencedColumnName = "codigo")
    private NaturezaJuridica naturezaJuridica;
    
    @ManyToOne
    @JoinColumn(name = "qualificacao_responsavel_codigo", referencedColumnName = "codigo")
    private QualificacaoSocio qualificacaoResponsavel;
    
    @Column(name = "capital_social", length = 20)
    private String capitalSocial;
    
    @Column(name = "porte_empresa", length = 2)
    private String porteEmpresa;
    
    @Column(name = "ente_federativo_responsavel", length = 100)
    private String enteFederativoResponsavel;
}