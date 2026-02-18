package br.com.gruporas.tarifaagua.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record FaixaRequest(
    @Min(0) int inicio,
    @NotNull Integer fim,
    @NotNull @DecimalMin("0.00") BigDecimal valorUnitario
) {}
