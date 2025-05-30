package br.tec.pauloduarte.cnpjbrasil.cnpjbrasil.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "estabelecimentos")
public class EstabelecimentoDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_estabelecimento")
    private Long idEstabelecimento;

    @Column(name = "cnpj_basico", nullable = false, length = 8)
    private String cnpjBasico;

    @Column(name = "cnpj_ordem", nullable = false, length = 4)
    private String cnpjOrdem;

    @Column(name = "cnpj_dv", nullable = false, length = 2)
    private String cnpjDv;

    @Column(name = "identificador_matriz_filial")
    private Integer identificadorMatrizFilial;

    @Column(name = "nome_fantasia", length = 255)
    private String nomeFantasia;

    @Column(name = "situacao_cadastral")
    private Integer situacaoCadastral;

    @Column(name = "data_situacao_cadastral", length = 10)
    private String dataSituacaoCadastral;

    @Column(name = "motivo_situacao_cadastral_codigo", length = 10)
    private String motivoSituacaoCadastralCodigo;

    @Column(name = "nome_cidade_exterior", length = 255)
    private String nomeCidadeExterior;

    @Column(name = "pais_codigo", length = 10)
    private String paisCodigo;

    @Column(name = "data_inicio_atividade", length = 10)
    private String dataInicioAtividade;

    @Column(name = "cnae_fiscal_principal_codigo", length = 10)
    private String cnaeFiscalPrincipalCodigo;

    @Column(name = "cnae_fiscal_secundaria", columnDefinition = "TEXT")
    private String cnaeFiscalSecundaria;

    @Column(name = "tipo_logradouro", length = 50)
    private String tipoLogradouro;

    @Column(name = "logradouro", length = 255)
    private String logradouro;

    @Column(name = "numero", length = 20)
    private String numero;

    @Column(name = "complemento", length = 255)
    private String complemento;

    @Column(name = "bairro", length = 100)
    private String bairro;

    @Column(name = "cep", length = 8)
    private String cep;

    @Column(name = "uf", length = 2)
    private String uf;

    @Column(name = "municipio_codigo", length = 10)
    private String municipioCodigo;

    @Column(name = "ddd1", length = 4)
    private String ddd1;

    @Column(name = "telefone1", length = 20)
    private String telefone1;

    @Column(name = "ddd2", length = 4)
    private String ddd2;

    @Column(name = "telefone2", length = 20)
    private String telefone2;

    @Column(name = "ddd_fax", length = 4)
    private String dddFax;

    @Column(name = "fax", length = 20)
    private String fax;

    @Column(name = "correio_eletronico", length = 255)
    private String correioEletronico;

    @Column(name = "situacao_especial", length = 255)
    private String situacaoEspecial;

    @Column(name = "data_situacao_especial", length = 10)
    private String dataSituacaoEspecial;
}