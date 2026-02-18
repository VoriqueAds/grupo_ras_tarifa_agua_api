package br.com.gruporas.tarifaagua;

import br.com.gruporas.tarifaagua.domain.CategoriaConsumidor;
import br.com.gruporas.tarifaagua.domain.FaixaTarifaria;
import br.com.gruporas.tarifaagua.domain.TabelaTarifaria;
import br.com.gruporas.tarifaagua.repository.FaixaTarifariaRepository;
import br.com.gruporas.tarifaagua.repository.TabelaTarifariaRepository;
import br.com.gruporas.tarifaagua.service.CalculoService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CalculoServiceTest {

  @Test
  void deveCalcularExemploDoPdf() {
    var tabelaRepo = Mockito.mock(TabelaTarifariaRepository.class);
    var faixaRepo = Mockito.mock(FaixaTarifariaRepository.class);

    UUID tabelaId = UUID.randomUUID();
    var tabela = new TabelaTarifaria(tabelaId, "Tabela PDF", null, null, true);

    Mockito.when(tabelaRepo.findAllByAtivaTrueOrderByCriadoEmDesc()).thenReturn(List.of(tabela));

    var f1 = new FaixaTarifaria(UUID.randomUUID(), tabela, CategoriaConsumidor.INDUSTRIAL, 0, 10, new BigDecimal("1.00"));
    var f2 = new FaixaTarifaria(UUID.randomUUID(), tabela, CategoriaConsumidor.INDUSTRIAL, 11, 20, new BigDecimal("2.00"));

    Mockito.when(faixaRepo.findAllByTabelaIdAndCategoriaOrderByInicioAsc(tabelaId, CategoriaConsumidor.INDUSTRIAL))
        .thenReturn(List.of(f1, f2));

    var service = new CalculoService(tabelaRepo, faixaRepo);

    var resp = service.calcular(CategoriaConsumidor.INDUSTRIAL, 18);
    assertEquals(new BigDecimal("26.00"), resp.valorTotal());
    assertEquals(2, resp.detalhamento().size());
    assertEquals(10, resp.detalhamento().get(0).m3Cobrados());
    assertEquals(8, resp.detalhamento().get(1).m3Cobrados());
  }
}
