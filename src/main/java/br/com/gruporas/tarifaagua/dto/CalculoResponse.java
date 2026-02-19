package br.com.gruporas.tarifaagua.dto;

import br.com.gruporas.tarifaagua.domain.CategoriaConsumidor;

import java.math.BigDecimal;
import java.util.List;

// DTO para resposta do cálculo do valor a pagar, contendo a categoria do consumidor, consumo total, valor total e detalhamento por faixa tarifária. Utilizado para retornar os resultados do cálculo de forma estruturada.

public record CalculoResponse(
    CategoriaConsumidor categoria,
    int consumoTotal,
    BigDecimal valorTotal,
    List<DetalhamentoFaixaResponse> detalhamento
) {}
