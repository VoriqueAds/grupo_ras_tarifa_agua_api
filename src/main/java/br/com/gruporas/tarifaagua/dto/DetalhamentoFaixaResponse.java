package br.com.gruporas.tarifaagua.dto;

import java.math.BigDecimal;

public record DetalhamentoFaixaResponse(
    FaixaInfo faixa,
    int m3Cobrados,
    BigDecimal valorUnitario,
    BigDecimal subtotal
) {
  public record FaixaInfo(int inicio, int fim) {}
}
