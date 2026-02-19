package br.com.gruporas.tarifaagua.dto;

import br.com.gruporas.tarifaagua.domain.CategoriaConsumidor;

import java.util.List;

// DTO para resposta de uma categoria de consumidor com suas faixas tarifárias associadas, contendo a categoria e uma lista de faixas. Utilizado para retornar os detalhes de uma categoria e suas faixas em respostas de API.

public record CategoriaComFaixasResponse(
    CategoriaConsumidor categoria,
    List<FaixaResponse> faixas
) {}
