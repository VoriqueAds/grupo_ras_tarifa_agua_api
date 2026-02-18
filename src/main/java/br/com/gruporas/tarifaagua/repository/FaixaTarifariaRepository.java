package br.com.gruporas.tarifaagua.repository;

import br.com.gruporas.tarifaagua.domain.CategoriaConsumidor;
import br.com.gruporas.tarifaagua.domain.FaixaTarifaria;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface FaixaTarifariaRepository extends JpaRepository<FaixaTarifaria, UUID> {

  List<FaixaTarifaria> findAllByTabelaIdAndCategoriaOrderByInicioAsc(UUID tabelaId, CategoriaConsumidor categoria);

  List<FaixaTarifaria> findAllByTabelaIdOrderByCategoriaAscInicioAsc(UUID tabelaId);
}
