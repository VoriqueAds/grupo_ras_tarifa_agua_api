-- V1 - Modelo relacional para Tabela Tarifária e Faixas (parametrizável)

/* 
  Esta migração inicial cria as tabelas necessárias para armazenar as informações de tabelas tarifárias e suas faixas associadas. A tabela "tariff_table" armazena os dados principais da tabela tarifária, enquanto a tabela "tariff_bracket" armazena as faixas tarifárias associadas a cada tabela. As constraints e índices garantem a integridade dos dados e otimizam as consultas.
*/

CREATE TABLE IF NOT EXISTS tariff_table (
  id UUID PRIMARY KEY,
  name VARCHAR(120) NOT NULL,
  valid_from DATE NULL,
  valid_to DATE NULL,
  active BOOLEAN NOT NULL DEFAULT TRUE,
  created_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

CREATE TABLE IF NOT EXISTS tariff_bracket (
  id UUID PRIMARY KEY,
  tariff_table_id UUID NOT NULL REFERENCES tariff_table(id),
  category VARCHAR(20) NOT NULL,
  start_m3 INTEGER NOT NULL,
  end_m3 INTEGER NULL,
  unit_value NUMERIC(12,2) NOT NULL,
  created_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

CREATE INDEX IF NOT EXISTS idx_bracket_lookup
  ON tariff_bracket (tariff_table_id, category, start_m3);

ALTER TABLE tariff_bracket
  ADD CONSTRAINT chk_start_nonneg CHECK (start_m3 >= 0);

ALTER TABLE tariff_bracket
  ADD CONSTRAINT chk_end_gt_start CHECK (end_m3 IS NULL OR end_m3 > start_m3);

CREATE UNIQUE INDEX IF NOT EXISTS uq_bracket_start
  ON tariff_bracket (tariff_table_id, category, start_m3);
