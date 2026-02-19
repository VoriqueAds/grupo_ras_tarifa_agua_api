package br.com.gruporas.tarifaagua.dto;

import br.com.gruporas.tarifaagua.domain.CategoriaConsumidor;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

// DTO para requisição de criação ou atualização de uma categoria de consumidor com suas faixas tarifárias associadas, contendo a categoria e uma lista de faixas, com validação de campos.

public record CategoriaComFaixasRequest(
    @NotNull CategoriaConsumidor categoria,
    @NotEmpty @Valid List<FaixaRequest> faixas
) {}
