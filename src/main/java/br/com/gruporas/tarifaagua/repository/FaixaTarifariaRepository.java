package br.com.gruporas.tarifaagua.repository;

import br.com.gruporas.tarifaagua.domain.CategoriaConsumidor;
import br.com.gruporas.tarifaagua.domain.FaixaTarifaria;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

// Repositório para a entidade FaixaTarifaria, estendendo JpaRepository para fornecer operações CRUD e consultas personalizadas. Contém métodos para buscar faixas tarifárias por tabela e categoria, ordenadas por início da faixa. Utilizado para acessar os dados de faixas tarifárias no banco de dados.

public interface FaixaTarifariaRepository extends JpaRepository<FaixaTarifaria, UUID> {

  List<FaixaTarifaria> findAllByTabelaIdAndCategoriaOrderByInicioAsc(UUID tabelaId, CategoriaConsumidor categoria);

  List<FaixaTarifaria> findAllByTabelaIdOrderByCategoriaAscInicioAsc(UUID tabelaId);
}
