package br.com.gruporas.tarifaagua.dto;

import br.com.gruporas.tarifaagua.domain.CategoriaConsumidor;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

// DTO para requisição de cálculo do valor a pagar, contendo a categoria do consumidor e o consumo em metros cúbicos, com validação de campos.

public record CalculoRequest(
    @NotNull CategoriaConsumidor categoria,
    @Min(0) int consumo
) {}
