package br.com.gruporas.tarifaagua.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.time.LocalDate;
import java.util.List;

// DTO para requisição de criação de uma tabela tarifária, contendo o nome da tabela, vigência e uma lista de categorias com suas faixas tarifárias associadas, com validação de campos. Utilizado para criar novas tabelas tarifárias através de requisições de API.

public record TabelaTarifariaCreateRequest(
    @NotBlank String nome,
    LocalDate vigenciaInicio,
    LocalDate vigenciaFim,
    @NotEmpty @Valid List<CategoriaComFaixasRequest> categorias
) {}
