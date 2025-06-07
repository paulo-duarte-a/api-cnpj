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
@Table(name = "socios")
public class Socio {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_socio")
    private Long idSocio;
    
    @Column(name = "cnpj_basico", nullable = false, length = 8)
    private String cnpjBasico;
    
    @Column(name = "identificador_socio")
    private Integer identificadorSocio;
    
    @Column(name = "nome_socio", length = 255)
    private String nomeSocio;
    
    @Column(name = "cpf_cnpj_socio", length = 20)
    private String cpfCnpjSocio;

    @ManyToOne
    @JoinColumn(name = "qualificacao_socio_codigo", referencedColumnName = "codigo")
    private QualificacaoSocio qualificacaoSocio;

    @Column(name = "data_entrada_sociedade", length = 10)
    private String dataEntradaSociedade;
    
    @Column(name = "pais_codigo")
    private Integer paisCodigo;
    
    @Column(name = "representante_legal_cpf", length = 20)
    private String representanteLegalCpf;
    
    @Column(name = "nome_representante_legal", length = 255)
    private String nomeRepresentanteLegal;
    
    @ManyToOne
    @JoinColumn(name = "qualificacao_representante_legal_codigo", referencedColumnName = "codigo")
    private QualificacaoSocio qualificacaoRepresentanteLegal;
    
    @Column(name = "faixa_etaria")
    private Integer faixaEtaria;
}