package br.com.gruporas.tarifaagua.dto;

import java.math.BigDecimal;

// DTO para detalhamento do cálculo por faixa tarifária, contendo informações da faixa, metros cúbicos cobrados, valor unitário e subtotal. Utilizado para fornecer um detalhamento completo do cálculo do valor a pagar por faixa tarifária.

public record DetalhamentoFaixaResponse(
    FaixaInfo faixa,
    int m3Cobrados,
    BigDecimal valorUnitario,
    BigDecimal subtotal
) {
  public record FaixaInfo(int inicio, int fim) {}
}
