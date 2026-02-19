package br.com.gruporas.tarifaagua.dto;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

// DTO para resposta de uma tabela tarifária, contendo o ID, nome, vigência, status de ativação e uma lista de categorias com suas faixas tarifárias associadas. Utilizado para retornar os detalhes de uma tabela tarifária em respostas de API.

public record TabelaTarifariaResponse(
    UUID id,
    String nome,
    LocalDate vigenciaInicio,
    LocalDate vigenciaFim,
    boolean ativa,
    List<CategoriaComFaixasResponse> categorias
) {}
