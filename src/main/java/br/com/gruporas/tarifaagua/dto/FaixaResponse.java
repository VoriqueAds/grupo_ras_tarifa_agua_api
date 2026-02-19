package br.com.gruporas.tarifaagua.dto;

import java.math.BigDecimal;
import java.util.UUID;

// DTO para resposta de uma faixa tarifária, contendo o ID, início, fim e valor unitário da faixa. Utilizado para retornar os detalhes de uma faixa tarifária em respostas de API.

public record FaixaResponse(
    UUID id,
    int inicio,
    int fim,
    BigDecimal valorUnitario
) {}
