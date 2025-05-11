-- SCRIPT DE CRIAÇÃO DE TABELAS PARA BASE DE DADOS CNPJ - POSTGRESQL

-- TABELAS DE DOMÍNIO (LOOKUP TABLES)

CREATE TABLE paises (
    codigo VARCHAR(10) PRIMARY KEY,
    descricao VARCHAR(255) NOT NULL
);

CREATE TABLE municipios (
    codigo VARCHAR(10) PRIMARY KEY,
    descricao VARCHAR(255) NOT NULL
);

CREATE TABLE qualificacoes_socios (
    codigo VARCHAR(10) PRIMARY KEY,
    descricao VARCHAR(255) NOT NULL
);

CREATE TABLE naturezas_juridicas (
    codigo VARCHAR(10) PRIMARY KEY,
    descricao VARCHAR(255) NOT NULL
);

CREATE TABLE cnaes (
    codigo VARCHAR(10) PRIMARY KEY,
    descricao VARCHAR(255) NOT NULL
);

-- TABELAS PRINCIPAIS

CREATE TABLE empresas (
	id_empresa SERIAL PRIMARY KEY,
    cnpj_basico VARCHAR(8) NULL,
    razao_social VARCHAR(255) NULL, -- Nome empresarial da pessoa jurídica
    natureza_juridica_codigo VARCHAR(10) NULL, -- Código da natureza jurídica
    qualificacao_responsavel_codigo VARCHAR(10) NULL, -- Qualificação da pessoa física responsável pela empresa
    capital_social VARCHAR(20) NULL, -- Capital social da empresa
    porte_empresa VARCHAR(2) NULL, -- Código do porte da empresa: 00 (Não Informado), 01 (Micro Empresa), 03 (Empresa de Pequeno Porte), 05 (Demais)
    ente_federativo_responsavel VARCHAR(100) NULL -- Ente Federativo Responsável (para Natureza Jurídica 1XXX)
);

CREATE TABLE estabelecimentos (
	id_empresa SERIAL PRIMARY KEY,
    cnpj_basico VARCHAR(8) NOT NULL, -- Oito primeiros dígitos do CNPJ
    cnpj_ordem VARCHAR(4) NOT NULL, -- Nono ao décimo segundo dígito do CNPJ
    cnpj_dv VARCHAR(2) NOT NULL, -- Dois últimos dígitos verificadores do CNPJ
    identificador_matriz_filial INTEGER NULL, -- 1: Matriz, 2: Filial
    nome_fantasia VARCHAR(255) NULL,
    situacao_cadastral INTEGER NULL, -- 1: Nula, 2: Ativa, 3: Suspensa, 4: Inapta, 8: Baixada
    data_situacao_cadastral VARCHAR(10) NULL,
    motivo_situacao_cadastral_codigo VARCHAR(10) NULL, -- Código do motivo da situação cadastral
    nome_cidade_exterior VARCHAR(255) NULL,
    pais_codigo VARCHAR(10) NULL, -- Código do país (se no exterior)
    data_inicio_atividade VARCHAR(10) NULL,
    cnae_fiscal_principal_codigo VARCHAR(10) NULL, -- Código da atividade econômica principal
    cnae_fiscal_secundaria TEXT NULL, -- Lista de códigos CNAE secundários, separados por vírgula
    tipo_logradouro VARCHAR(50) NULL,
    logradouro VARCHAR(255) NULL,
    numero VARCHAR(20) NULL,
    complemento VARCHAR(255) NULL,
    bairro VARCHAR(100) NULL,
    cep VARCHAR(8) NULL,
    uf VARCHAR(2) NULL,
    municipio_codigo VARCHAR(10) NULL, -- Código do município
    ddd1 VARCHAR(4) NULL,
    telefone1 VARCHAR(20) NULL,
    ddd2 VARCHAR(4) NULL,
    telefone2 VARCHAR(20) NULL,
    ddd_fax VARCHAR(4) NULL,
    fax VARCHAR(20) NULL,
    correio_eletronico VARCHAR(255) NULL,
    situacao_especial VARCHAR(255) NULL,
    data_situacao_especial VARCHAR(10) NULL
);

CREATE TABLE simples ( -- Informações sobre Simples Nacional e MEI
	id_simples SERIAL PRIMARY KEY,
    cnpj_basico VARCHAR(8) NULL,
    opcao_pelo_simples CHAR(1) NULL, -- S: Sim, N: Não, ' ': Outros
    data_opcao_simples VARCHAR(10)  NULL,
    data_exclusao_simples VARCHAR(10)  NULL,
    opcao_pelo_mei CHAR(1) NULL, -- S: Sim, N: Não, ' ': Outros
    data_opcao_mei VARCHAR(10) NULL,
    data_exclusao_mei VARCHAR(10) NULL
);

CREATE TABLE socios (
    id_socio SERIAL PRIMARY KEY, -- Chave primária auto-incremental para garantir unicidade
    cnpj_basico VARCHAR(8) NOT NULL, -- CNPJ base da empresa à qual o sócio pertence
    identificador_socio INTEGER NULL, -- 1: Pessoa Jurídica, 2: Pessoa Física, 3: Estrangeiro
    nome_socio VARCHAR(255) NULL, -- Nome do sócio (PF) ou Razão Social (PJ)
    cpf_cnpj_socio VARCHAR(20) NULL, -- CPF ou CNPJ do sócio (armazenar valor descaracterizado)
    qualificacao_socio_codigo VARCHAR(10) NULL, -- Código da qualificação do sócio
    data_entrada_sociedade VARCHAR(10) NULL,
    pais_codigo VARCHAR(10) NULL, -- Código do país do sócio estrangeiro (pode ser nulo)
    representante_legal_cpf VARCHAR(20) NULL, -- CPF do representante legal (descaracterizado, pode ser nulo)
    nome_representante_legal VARCHAR(255) NULL, -- Nome do representante legal (pode ser nulo)
    qualificacao_representante_legal_codigo VARCHAR(10) NULL, -- Código da qualificação do representante legal (pode ser nulo)
    faixa_etaria INTEGER NULL -- 1 (0-12 anos)...9 (>80 anos), 0 (Não se aplica)
);

-- TABELA DE USUÁRIOS (para controle de acesso ao sistema)
CREATE TYPE usuario_role AS ENUM ('FREE', 'BASIC', 'PREMIUM', 'ADMIN');

CREATE TABLE usuarios (
    id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    email VARCHAR UNIQUE,
    senha VARCHAR,
    role usuario_role
);

-- DEFINIÇÃO DE CHAVES ESTRANGEIRAS (LIGAÇÕES ENTRE TABELAS)

-- Tabela EMPRESAS
ALTER TABLE empresas
    ADD CONSTRAINT fk_empresas_natureza_juridica FOREIGN KEY (natureza_juridica_codigo) REFERENCES naturezas_juridicas(codigo),
    ADD CONSTRAINT fk_empresas_qualificacao_responsavel FOREIGN KEY (qualificacao_responsavel_codigo) REFERENCES qualificacoes_socios(codigo);

-- Tabela ESTABELECIMENTOS
ALTER TABLE estabelecimentos
    ADD CONSTRAINT fk_estabelecimentos_pais FOREIGN KEY (pais_codigo) REFERENCES paises(codigo),
    ADD CONSTRAINT fk_estabelecimentos_cnae_principal FOREIGN KEY (cnae_fiscal_principal_codigo) REFERENCES cnaes(codigo),
    ADD CONSTRAINT fk_estabelecimentos_municipio FOREIGN KEY (municipio_codigo) REFERENCES municipios(codigo);

-- Tabela SIMPLES
--ALTER TABLE simples
--    ADD CONSTRAINT fk_simples_empresa FOREIGN KEY (cnpj_basico) REFERENCES empresas(cnpj_basico) ON DELETE CASCADE ON UPDATE CASCADE;

-- Tabela SOCIOS
ALTER TABLE socios
--    ADD CONSTRAINT fk_socios_empresa FOREIGN KEY (cnpj_basico) REFERENCES empresas(cnpj_basico) ON DELETE CASCADE ON UPDATE CASCADE,
    ADD CONSTRAINT fk_socios_qualificacao FOREIGN KEY (qualificacao_socio_codigo) REFERENCES qualificacoes_socios(codigo),
    ADD CONSTRAINT fk_socios_pais FOREIGN KEY (pais_codigo) REFERENCES paises(codigo),
    ADD CONSTRAINT fk_socios_qualificacao_representante FOREIGN KEY (qualificacao_representante_legal_codigo) REFERENCES qualificacoes_socios(codigo);

-- CRIAÇÃO DE ÍNDICES PARA OTIMIZAÇÃO DE CONSULTAS

-- Tabela USUÁRIOS
CREATE INDEX idx_usuarios_email ON usuarios (email);
CREATE INDEX idx_usuarios_role ON usuarios (role);

-- Tabela EMPRESAS
CREATE INDEX idx_empresas_cnpj_basico ON empresas (cnpj_basico);
CREATE INDEX idx_empresas_razao_social ON empresas USING GIN (to_tsvector('portuguese', razao_social)); -- Para busca textual
CREATE INDEX idx_empresas_natureza_juridica_codigo ON empresas (natureza_juridica_codigo);
CREATE INDEX idx_empresas_porte_empresa ON empresas (porte_empresa);
CREATE INDEX idx_empresas_qualificacao_responsavel_codigo ON empresas (qualificacao_responsavel_codigo);


-- Tabela ESTABELECIMENTOS
-- A chave primária (cnpj_basico, cnpj_ordem, cnpj_dv) já é indexada.
CREATE INDEX idx_estabelecimentos_cnpj_basico ON estabelecimentos (cnpj_basico);
CREATE INDEX idx_estabelecimentos_nome_fantasia ON estabelecimentos USING GIN (to_tsvector('portuguese', nome_fantasia)); -- Para busca textual
CREATE INDEX idx_estabelecimentos_situacao_cadastral ON estabelecimentos (situacao_cadastral);
CREATE INDEX idx_estabelecimentos_pais_codigo ON estabelecimentos (pais_codigo);
CREATE INDEX idx_estabelecimentos_cnae_principal_codigo ON estabelecimentos (cnae_fiscal_principal_codigo);
CREATE INDEX idx_estabelecimentos_municipio_codigo ON estabelecimentos (municipio_codigo);
CREATE INDEX idx_estabelecimentos_uf ON estabelecimentos (uf);
CREATE INDEX idx_estabelecimentos_cep ON estabelecimentos (cep);
-- Para cnae_fiscal_secundaria (TEXT), se buscas por CNAEs específicos forem comuns,
-- considere normalizar para uma tabela de ligação ou usar índices GIN em arrays (se os dados forem pré-processados para array).
-- CREATE INDEX idx_estabelecimentos_cnae_secundaria_gin ON estabelecimentos USING GIN (to_tsvector('simple', cnae_fiscal_secundaria)); -- Exemplo simples

-- Tabela SIMPLES
-- A chave primária (cnpj_basico) já é indexada.

-- Tabela SOCIOS
CREATE INDEX idx_socios_cnpj_basico ON socios (cnpj_basico); -- Chave estrangeira
CREATE INDEX idx_socios_nome_socio ON socios USING GIN (to_tsvector('portuguese', nome_socio)); -- Para busca textual
CREATE INDEX idx_socios_cpf_cnpj_socio ON socios (cpf_cnpj_socio); -- Considerar implicações de privacidade e busca em campos descaracterizados
CREATE INDEX idx_socios_qualificacao_socio_codigo ON socios (qualificacao_socio_codigo);
CREATE INDEX idx_socios_pais_codigo ON socios (pais_codigo);
CREATE INDEX idx_socios_representante_legal_cpf ON socios (representante_legal_cpf);
CREATE INDEX idx_socios_qualificacao_representante_legal_codigo ON socios (qualificacao_representante_legal_codigo);


-- Índices para colunas de DESCRIÇÃO nas tabelas de domínio (se forem frequentemente usadas em buscas)
CREATE INDEX idx_paises_descricao ON paises USING GIN (to_tsvector('portuguese', descricao));
CREATE INDEX idx_municipios_descricao ON municipios USING GIN (to_tsvector('portuguese', descricao));
CREATE INDEX idx_qualificacoes_socios_descricao ON qualificacoes_socios USING GIN (to_tsvector('portuguese', descricao));
CREATE INDEX idx_naturezas_juridicas_descricao ON naturezas_juridicas USING GIN (to_tsvector('portuguese', descricao));
CREATE INDEX idx_cnaes_descricao ON cnaes USING GIN (to_tsvector('portuguese', descricao));



INSERT INTO qualificacoes_socios(codigo, descricao) VALUES('0', 'Nao Definido');
INSERT INTO qualificacoes_socios(codigo, descricao) VALUES('36', 'Nao Definido');
INSERT INTO paises(codigo, descricao) VALUES('0', 'Nao Definido');


BEGIN; -- Inicia transação

-- Verifica quantos serão inseridos (opcional)
SELECT count(*)
FROM (
    SELECT s::text AS codigo FROM generate_series(1, 999) s -- Formato simples (ex: 1)
    UNION ALL
    SELECT lpad(s::text, 3, '0') FROM generate_series(1, 999) s -- Formato com zeros (ex: 001)
) AS todos_codigos
WHERE codigo NOT IN (SELECT codigo FROM paises);

-- Insere os códigos faltantes (ambos formatos)
INSERT INTO paises (codigo, descricao)
SELECT codigo, 'Nao Definido'
FROM (
    SELECT s::text AS codigo FROM generate_series(1, 999) s
    UNION ALL
    SELECT lpad(s::text, 3, '0') FROM generate_series(1, 999) s
) AS todos_codigos
WHERE codigo NOT IN (SELECT codigo FROM paises)
GROUP BY codigo -- Evita duplicatas caso ambos formatos sejam iguais (ex: 100)
ORDER BY codigo;
COMMIT; -- Se os resultados estiverem corretos