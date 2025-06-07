-- SCRIPT DE CRIAÇÃO DE TABELAS PARA BASE DE DADOS CNPJ - ORACLE

-- TABELAS DE DOMÍNIO (LOOKUP TABLES)
CREATE TABLE paises (
    codigo    VARCHAR2(10) PRIMARY KEY,
    descricao VARCHAR2(255) NOT NULL
);

CREATE TABLE municipios (
    codigo    VARCHAR2(10) PRIMARY KEY,
    descricao VARCHAR2(255) NOT NULL
);

CREATE TABLE qualificacoes_socios (
    codigo    VARCHAR2(10) PRIMARY KEY,
    descricao VARCHAR2(255) NOT NULL
);

CREATE TABLE naturezas_juridicas (
    codigo    VARCHAR2(10) PRIMARY KEY,
    descricao VARCHAR2(255) NOT NULL
);

CREATE TABLE cnaes (
    codigo    VARCHAR2(10) PRIMARY KEY,
    descricao VARCHAR2(255) NOT NULL
);

-- TABELAS PRINCIPAIS

CREATE TABLE empresas (
    id_empresa                        NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    cnpj_basico                       VARCHAR2(8),
    razao_social                      VARCHAR2(255),
    natureza_juridica_codigo          VARCHAR2(10),
    qualificacao_responsavel_codigo   VARCHAR2(10),
    capital_social                    VARCHAR2(20),
    porte_empresa                     VARCHAR2(2),
    ente_federativo_responsavel       VARCHAR2(100)
);

CREATE TABLE estabelecimentos (
    id_estabelecimento                NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    cnpj_basico                       VARCHAR2(8)  NOT NULL,
    cnpj_ordem                        VARCHAR2(4)  NOT NULL,
    cnpj_dv                           VARCHAR2(2)  NOT NULL,
    identificador_matriz_filial       NUMBER,
    nome_fantasia                     VARCHAR2(255),
    situacao_cadastral                NUMBER,
    data_situacao_cadastral           VARCHAR2(10),
    motivo_situacao_cadastral_codigo  VARCHAR2(10),
    nome_cidade_exterior              VARCHAR2(255),
    pais_codigo                       VARCHAR2(10),
    data_inicio_atividade             VARCHAR2(10),
    cnae_fiscal_principal_codigo      VARCHAR2(10),
    cnae_fiscal_secundaria            CLOB,
    tipo_logradouro                   VARCHAR2(50),
    logradouro                        VARCHAR2(255),
    numero                            VARCHAR2(20),
    complemento                       VARCHAR2(255),
    bairro                            VARCHAR2(100),
    cep                               VARCHAR2(8),
    uf                                VARCHAR2(2),
    municipio_codigo                  VARCHAR2(10),
    ddd1                              VARCHAR2(4),
    telefone1                         VARCHAR2(20),
    ddd2                              VARCHAR2(4),
    telefone2                         VARCHAR2(20),
    ddd_fax                           VARCHAR2(4),
    fax                               VARCHAR2(20),
    correio_eletronico               VARCHAR2(255),
    situacao_especial                 VARCHAR2(255),
    data_situacao_especial            VARCHAR2(10)
);

CREATE TABLE simples (
    id_simples                        NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    cnpj_basico                       VARCHAR2(8),
    opcao_pelo_simples                CHAR(1),
    data_opcao_simples                VARCHAR2(10),
    data_exclusao_simples             VARCHAR2(10),
    opcao_pelo_mei                    CHAR(1),
    data_opcao_mei                    VARCHAR2(10),
    data_exclusao_mei                 VARCHAR2(10)
);

CREATE TABLE socios (
    id_socio                          NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    cnpj_basico                       VARCHAR2(8)  NOT NULL,
    identificador_socio               NUMBER,
    nome_socio                        VARCHAR2(255),
    cpf_cnpj_socio                    VARCHAR2(20),
    qualificacao_socio_codigo         VARCHAR2(10),
    data_entrada_sociedade            VARCHAR2(10),
    pais_codigo                       VARCHAR2(10),
    representante_legal_cpf           VARCHAR2(20),
    nome_representante_legal          VARCHAR2(255),
    qualificacao_representante_legal_codigo VARCHAR2(10),
    faixa_etaria                      NUMBER
);

-- TABELA DE USUÁRIOS (controle de acesso e autenticação)
CREATE TABLE usuarios (
    id     NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    email  VARCHAR2(255) UNIQUE,
    senha  VARCHAR2(255),
    role   VARCHAR2(10) DEFAULT 'FREE' CHECK (role IN ('FREE','BASIC','PREMIUM','ADMIN'))
);

-- DEFESA DE CHAVES ESTRANGEIRAS

-- Constraints da tabela EMPRESAS
ALTER TABLE empresas ADD (
  CONSTRAINT fk_empresas_natureza_juridica
    FOREIGN KEY (natureza_juridica_codigo)
    REFERENCES naturezas_juridicas(codigo),
  CONSTRAINT fk_empresas_qualificacao_responsavel
    FOREIGN KEY (qualificacao_responsavel_codigo)
    REFERENCES qualificacoes_socios(codigo)
);

ALTER TABLE empresas
ADD CONSTRAINT UQ_EMPRESAS_CNPJ_BASICO UNIQUE (cnpj_basico);

-- Constraints da tabela ESTABELECIMENTOS
ALTER TABLE estabelecimentos ADD (
  CONSTRAINT fk_estabelecimentos_pais
    FOREIGN KEY (pais_codigo)
    REFERENCES paises(codigo),
  CONSTRAINT fk_estabelecimentos_cnae_principal
    FOREIGN KEY (cnae_fiscal_principal_codigo)
    REFERENCES cnaes(codigo),
  CONSTRAINT fk_estabelecimentos_municipio
    FOREIGN KEY (municipio_codigo)
    REFERENCES municipios(codigo)
);

-- Constraint para SIMPLES (opcional)
-- ALTER TABLE simples ADD CONSTRAINT fk_simples_empresa
--   FOREIGN KEY (cnpj_basico) REFERENCES empresas(cnpj_basico);

-- Constraints da tabela SOCIOS
ALTER TABLE socios ADD (
  CONSTRAINT fk_socios_qualificacao
    FOREIGN KEY (qualificacao_socio_codigo)
    REFERENCES qualificacoes_socios(codigo),
  CONSTRAINT fk_socios_pais
    FOREIGN KEY (pais_codigo)
    REFERENCES paises(codigo),
  CONSTRAINT fk_socios_qualificacao_representante
    FOREIGN KEY (qualificacao_representante_legal_codigo)
    REFERENCES qualificacoes_socios(codigo)
);

-- CRIAÇÃO DE ÍNDICES

CREATE INDEX idx_usuarios_email ON usuarios (email);
CREATE INDEX idx_usuarios_role ON usuarios (role);

CREATE INDEX idx_empresas_cnpj_basico ON empresas (cnpj_basico);
-- Para busca de texto em Oracle utilizar Oracle Text
CREATE INDEX idx_empresas_razao_social  ON empresas(razao_social);
CREATE INDEX idx_empresas_natureza_juridica_codigo ON empresas (natureza_juridica_codigo);
CREATE INDEX idx_empresas_porte_empresa ON empresas (porte_empresa);
CREATE INDEX idx_empresas_qualificacao_responsavel_codigo ON empresas (qualificacao_responsavel_codigo);

CREATE INDEX idx_estabelecimentos_cnpj_basico ON estabelecimentos (cnpj_basico);
CREATE INDEX idx_estabelecimentos_nome_fantasia ON estabelecimentos(nome_fantasia);
CREATE INDEX idx_estabelecimentos_situacao_cadastral ON estabelecimentos (situacao_cadastral);
CREATE INDEX idx_estabelecimentos_pais_codigo ON estabelecimentos (pais_codigo);
CREATE INDEX idx_estabelecimentos_cnae_principal_codigo ON estabelecimentos (cnae_fiscal_principal_codigo);
CREATE INDEX idx_estabelecimentos_municipio_codigo ON estabelecimentos (municipio_codigo);
CREATE INDEX idx_estabelecimentos_uf ON estabelecimentos (uf);
CREATE INDEX idx_estabelecimentos_cep ON estabelecimentos (cep);

CREATE INDEX idx_socios_cnpj_basico ON socios (cnpj_basico);
CREATE INDEX idx_socios_nome_socio ON socios(nome_socio);
CREATE INDEX idx_socios_cpf_cnpj_socio ON socios (cpf_cnpj_socio);
CREATE INDEX idx_socios_qualificacao_socio_codigo ON socios (qualificacao_socio_codigo);
CREATE INDEX idx_socios_pais_codigo ON socios (pais_codigo);
CREATE INDEX idx_socios_representante_legal_cpf ON socios (representante_legal_cpf);
CREATE INDEX idx_socios_qualificacao_representante_legal_codigo
  ON socios (qualificacao_representante_legal_codigo);

CREATE INDEX idx_paises_descricao
  ON paises(descricao);
CREATE INDEX idx_municipios_descricao
  ON municipios(descricao);
CREATE INDEX idx_qualificacoes_socios_descricao
  ON qualificacoes_socios(descricao);
CREATE INDEX idx_naturezas_juridicas_descricao
  ON naturezas_juridicas(descricao);
CREATE INDEX idx_cnaes_descricao
  ON cnaes(descricao);

DECLARE
  v_code  VARCHAR2(3);
  v_code2 VARCHAR2(2);  -- Nova variável para 2 dígitos
  v_code3 VARCHAR2(3);
BEGIN
  FOR i IN 1..999 LOOP
    v_code  := TO_CHAR(i);
    v_code2 := LPAD(v_code, 2, '0');  -- Gera versão com 2 dígitos
    v_code3 := LPAD(v_code, 3, '0');  -- Versão com 3 dígitos

    -- Inserts fixos (mantidos da versão original)
    BEGIN
      INSERT INTO qualificacoes_socios(codigo, descricao) VALUES('0', 'Nao Definido');
    EXCEPTION WHEN OTHERS THEN NULL; END;
    
    BEGIN
      INSERT INTO qualificacoes_socios(codigo, descricao) VALUES('36', 'Nao Definido');
    EXCEPTION WHEN OTHERS THEN NULL; END;
    
    BEGIN
      INSERT INTO paises(codigo, descricao) VALUES('0', 'Nao Definido');
    EXCEPTION WHEN OTHERS THEN NULL; END;

    -- Loop para inserir as variações numéricas na tabela PAISES
    FOR v IN (
      SELECT v_code  AS c FROM dual UNION ALL
      SELECT v_code2 AS c FROM dual UNION ALL  -- Inclui versão de 2 dígitos
      SELECT v_code3 AS c FROM dual
    ) 
    LOOP
      BEGIN
        INSERT INTO paises(codigo, descricao) VALUES(v.c, 'Nao Definido');
      EXCEPTION WHEN DUP_VAL_ON_INDEX THEN NULL;  -- Ignora duplicatas
      END;
    END LOOP;
  END LOOP;
  COMMIT;
END;
