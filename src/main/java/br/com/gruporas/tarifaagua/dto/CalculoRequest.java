package br.com.gruporas.tarifaagua.dto;

import br.com.gruporas.tarifaagua.domain.CategoriaConsumidor;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record CalculoRequest(
    @NotNull CategoriaConsumidor categoria,
    @Min(0) int consumo
) {}
