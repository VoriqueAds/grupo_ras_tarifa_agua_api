package br.com.gruporas.tarifaagua.dto;

import br.com.gruporas.tarifaagua.domain.CategoriaConsumidor;

import java.util.List;

public record CategoriaComFaixasResponse(
    CategoriaConsumidor categoria,
    List<FaixaResponse> faixas
) {}
