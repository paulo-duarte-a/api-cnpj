-- SCRIPT DE CRIAÇÃO DE TABELAS PARA BASE DE DADOS CNPJ - POSTGRESQL

-- TABELAS DE DOMÍNIO (LOOKUP TABLES)
CREATE TABLE paises (
    codigo    VARCHAR(10) PRIMARY KEY,
    descricao VARCHAR(255) NOT NULL
);

CREATE TABLE municipios (
    codigo    VARCHAR(10) PRIMARY KEY,
    descricao VARCHAR(255) NOT NULL
);

CREATE TABLE qualificacoes_socios (
    codigo    VARCHAR(10) PRIMARY KEY,
    descricao VARCHAR(255) NOT NULL
);

CREATE TABLE naturezas_juridicas (
    codigo    VARCHAR(10) PRIMARY KEY,
    descricao VARCHAR(255) NOT NULL
);

CREATE TABLE cnaes (
    codigo    VARCHAR(10) PRIMARY KEY,
    descricao VARCHAR(255) NOT NULL
);

-- TABELAS PRINCIPAIS

CREATE TABLE empresas (
    id_empresa                        SERIAL PRIMARY KEY,
    cnpj_basico                       VARCHAR(8),
    razao_social                      VARCHAR(255),
    natureza_juridica_codigo          VARCHAR(10),
    qualificacao_responsavel_codigo   VARCHAR(10),
    capital_social                    VARCHAR(20), -- Mantido como VARCHAR pois no Oracle estava assim, pode conter R$ etc. Idealmente seria NUMERIC.
    porte_empresa                     VARCHAR(2),
    ente_federativo_responsavel       VARCHAR(100)
);

CREATE TABLE estabelecimentos (
    id_estabelecimento                SERIAL PRIMARY KEY,
    cnpj_basico                       VARCHAR(8)  NOT NULL,
    cnpj_ordem                        VARCHAR(4)  NOT NULL,
    cnpj_dv                           VARCHAR(2)  NOT NULL,
    identificador_matriz_filial       INTEGER, -- Oracle NUMBER -> PostgreSQL INTEGER
    nome_fantasia                     VARCHAR(255),
    situacao_cadastral                INTEGER, -- Oracle NUMBER -> PostgreSQL INTEGER
    data_situacao_cadastral           VARCHAR(10), -- Idealmente seria DATE
    motivo_situacao_cadastral_codigo  VARCHAR(10),
    nome_cidade_exterior              VARCHAR(255),
    pais_codigo                       VARCHAR(10),
    data_inicio_atividade             VARCHAR(10), -- Idealmente seria DATE
    cnae_fiscal_principal_codigo      VARCHAR(10),
    cnae_fiscal_secundaria            TEXT, -- Oracle CLOB -> PostgreSQL TEXT
    tipo_logradouro                   VARCHAR(50),
    logradouro                        VARCHAR(255),
    numero                            VARCHAR(20),
    complemento                       VARCHAR(255),
    bairro                            VARCHAR(100),
    cep                               VARCHAR(8),
    uf                                VARCHAR(2),
    municipio_codigo                  VARCHAR(10),
    ddd1                              VARCHAR(4),
    telefone1                         VARCHAR(20),
    ddd2                              VARCHAR(4),
    telefone2                         VARCHAR(20),
    ddd_fax                           VARCHAR(4),
    fax                               VARCHAR(20),
    correio_eletronico                VARCHAR(255),
    situacao_especial                 VARCHAR(255),
    data_situacao_especial            VARCHAR(10) -- Idealmente seria DATE
);

CREATE TABLE simples (
    id_simples                        SERIAL PRIMARY KEY,
    cnpj_basico                       VARCHAR(8),
    opcao_pelo_simples                CHAR(1),
    data_opcao_simples                VARCHAR(10), -- Idealmente seria DATE
    data_exclusao_simples             VARCHAR(10), -- Idealmente seria DATE
    opcao_pelo_mei                    CHAR(1),
    data_opcao_mei                    VARCHAR(10), -- Idealmente seria DATE
    data_exclusao_mei                 VARCHAR(10)  -- Idealmente seria DATE
);

CREATE TABLE socios (
    id_socio                          SERIAL PRIMARY KEY,
    cnpj_basico                       VARCHAR(8)  NOT NULL,
    identificador_socio               INTEGER, -- Oracle NUMBER -> PostgreSQL INTEGER
    nome_socio                        VARCHAR(255),
    cpf_cnpj_socio                    VARCHAR(20),
    qualificacao_socio_codigo         VARCHAR(10),
    data_entrada_sociedade            VARCHAR(10), -- Idealmente seria DATE
    pais_codigo                       VARCHAR(10),
    representante_legal_cpf           VARCHAR(20),
    nome_representante_legal          VARCHAR(255),
    qualificacao_representante_legal_codigo VARCHAR(10),
    faixa_etaria                      INTEGER -- Oracle NUMBER -> PostgreSQL INTEGER
);

-- TABELA DE USUÁRIOS (controle de acesso e autenticação)
CREATE TABLE usuarios (
    id     SERIAL PRIMARY KEY,
    email  VARCHAR(255) UNIQUE,
    senha  VARCHAR(255),
    role   VARCHAR(10) DEFAULT 'FREE' CHECK (role IN ('FREE','BASIC','PREMIUM','ADMIN'))
);

-- DEFESA DE CHAVES ESTRANGEIRAS

-- Constraints da tabela EMPRESAS
ALTER TABLE empresas
  ADD CONSTRAINT fk_empresas_natureza_juridica
    FOREIGN KEY (natureza_juridica_codigo)
    REFERENCES naturezas_juridicas(codigo),
  ADD CONSTRAINT fk_empresas_qualificacao_responsavel
    FOREIGN KEY (qualificacao_responsavel_codigo)
    REFERENCES qualificacoes_socios(codigo);

ALTER TABLE empresas
ADD CONSTRAINT UQ_EMPRESAS_CNPJ_BASICO UNIQUE (cnpj_basico);

-- Constraints da tabela ESTABELECIMENTOS
ALTER TABLE estabelecimentos
  ADD CONSTRAINT fk_estabelecimentos_pais
    FOREIGN KEY (pais_codigo)
    REFERENCES paises(codigo),
  ADD CONSTRAINT fk_estabelecimentos_cnae_principal
    FOREIGN KEY (cnae_fiscal_principal_codigo)
    REFERENCES cnaes(codigo),
  ADD CONSTRAINT fk_estabelecimentos_municipio
    FOREIGN KEY (municipio_codigo)
    REFERENCES municipios(codigo);

-- Constraint para SIMPLES (opcional)
-- ALTER TABLE simples ADD CONSTRAINT fk_simples_empresa
--   FOREIGN KEY (cnpj_basico) REFERENCES empresas(cnpj_basico); -- Se cnpj_basico em empresas for UNIQUE

-- Constraints da tabela SOCIOS
ALTER TABLE socios
  ADD CONSTRAINT fk_socios_qualificacao
    FOREIGN KEY (qualificacao_socio_codigo)
    REFERENCES qualificacoes_socios(codigo),
  ADD CONSTRAINT fk_socios_pais
    FOREIGN KEY (pais_codigo)
    REFERENCES paises(codigo),
  ADD CONSTRAINT fk_socios_qualificacao_representante
    FOREIGN KEY (qualificacao_representante_legal_codigo)
    REFERENCES qualificacoes_socios(codigo);

-- CRIAÇÃO DE ÍNDICES

CREATE INDEX idx_usuarios_email ON usuarios (email);
CREATE INDEX idx_usuarios_role ON usuarios (role);

CREATE INDEX idx_empresas_cnpj_basico ON empresas (cnpj_basico);
-- Para busca de texto em PostgreSQL utilizar Full-Text Search (tsvector, tsquery, GIN/GiST indexes)
CREATE INDEX idx_empresas_razao_social  ON empresas(razao_social); -- Pode ser útil para LIKE, ou use FTS
CREATE INDEX idx_empresas_natureza_juridica_codigo ON empresas (natureza_juridica_codigo);
CREATE INDEX idx_empresas_porte_empresa ON empresas (porte_empresa);
CREATE INDEX idx_empresas_qualificacao_responsavel_codigo ON empresas (qualificacao_responsavel_codigo);

CREATE INDEX idx_estabelecimentos_cnpj_basico ON estabelecimentos (cnpj_basico);
CREATE INDEX idx_estabelecimentos_nome_fantasia ON estabelecimentos(nome_fantasia); -- Pode ser útil para LIKE, ou use FTS
CREATE INDEX idx_estabelecimentos_situacao_cadastral ON estabelecimentos (situacao_cadastral);
CREATE INDEX idx_estabelecimentos_pais_codigo ON estabelecimentos (pais_codigo);
CREATE INDEX idx_estabelecimentos_cnae_principal_codigo ON estabelecimentos (cnae_fiscal_principal_codigo);
CREATE INDEX idx_estabelecimentos_municipio_codigo ON estabelecimentos (municipio_codigo);
CREATE INDEX idx_estabelecimentos_uf ON estabelecimentos (uf);
CREATE INDEX idx_estabelecimentos_cep ON estabelecimentos (cep);

CREATE INDEX idx_socios_cnpj_basico ON socios (cnpj_basico);
CREATE INDEX idx_socios_nome_socio ON socios(nome_socio); -- Pode ser útil para LIKE, ou use FTS
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

DO $$
DECLARE
  i       INTEGER;
  v_code  TEXT;
  v_code2 TEXT;
  v_code3 TEXT;
BEGIN
  -- Inserts fixos (com ON CONFLICT para ignorar duplicatas)
  INSERT INTO qualificacoes_socios(codigo, descricao) VALUES('0', 'Nao Definido') ON CONFLICT (codigo) DO NOTHING;
  INSERT INTO qualificacoes_socios(codigo, descricao) VALUES('36', 'Nao Definido') ON CONFLICT (codigo) DO NOTHING;
  INSERT INTO paises(codigo, descricao) VALUES('0', 'Nao Definido') ON CONFLICT (codigo) DO NOTHING;

  FOR i IN 1..999 LOOP
    v_code  := i::TEXT;                 -- Equivalente a TO_CHAR(i)
    v_code2 := LPAD(i::TEXT, 2, '0');   -- Gera versão com 2 dígitos (ou trunca se i > 99)
    v_code3 := LPAD(i::TEXT, 3, '0');   -- Versão com 3 dígitos (ou trunca se i > 999, mas o loop vai até 999)

    -- Insere as variações numéricas na tabela PAISES, ignorando duplicatas
    INSERT INTO paises(codigo, descricao) VALUES(v_code, 'Nao Definido') ON CONFLICT (codigo) DO NOTHING;
    INSERT INTO paises(codigo, descricao) VALUES(v_code2, 'Nao Definido') ON CONFLICT (codigo) DO NOTHING;
    INSERT INTO paises(codigo, descricao) VALUES(v_code3, 'Nao Definido') ON CONFLICT (codigo) DO NOTHING;
  END LOOP;
  -- COMMIT é implícito para blocos DO bem-sucedidos
END;
$$;
