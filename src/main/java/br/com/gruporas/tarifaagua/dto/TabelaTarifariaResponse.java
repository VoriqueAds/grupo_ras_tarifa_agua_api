package br.com.gruporas.tarifaagua.dto;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record TabelaTarifariaResponse(
    UUID id,
    String nome,
    LocalDate vigenciaInicio,
    LocalDate vigenciaFim,
    boolean ativa,
    List<CategoriaComFaixasResponse> categorias
) {}
