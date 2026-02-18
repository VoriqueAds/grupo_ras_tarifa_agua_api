package br.com.gruporas.tarifaagua.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.time.LocalDate;
import java.util.List;

public record TabelaTarifariaCreateRequest(
    @NotBlank String nome,
    LocalDate vigenciaInicio,
    LocalDate vigenciaFim,
    @NotEmpty @Valid List<CategoriaComFaixasRequest> categorias
) {}
