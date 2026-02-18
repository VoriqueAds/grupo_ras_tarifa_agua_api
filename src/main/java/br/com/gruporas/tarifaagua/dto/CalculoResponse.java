package br.com.gruporas.tarifaagua.dto;

import br.com.gruporas.tarifaagua.domain.CategoriaConsumidor;

import java.math.BigDecimal;
import java.util.List;

public record CalculoResponse(
    CategoriaConsumidor categoria,
    int consumoTotal,
    BigDecimal valorTotal,
    List<DetalhamentoFaixaResponse> detalhamento
) {}
