package br.com.gruporas.tarifaagua.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record FaixaResponse(
    UUID id,
    int inicio,
    int fim,
    BigDecimal valorUnitario
) {}
