package br.com.gruporas.tarifaagua.dto;

import br.com.gruporas.tarifaagua.domain.CategoriaConsumidor;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record CategoriaComFaixasRequest(
    @NotNull CategoriaConsumidor categoria,
    @NotEmpty @Valid List<FaixaRequest> faixas
) {}
