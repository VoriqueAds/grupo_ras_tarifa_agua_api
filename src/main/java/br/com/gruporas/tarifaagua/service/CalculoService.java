package br.com.gruporas.tarifaagua.service;

import br.com.gruporas.tarifaagua.domain.CategoriaConsumidor;
import br.com.gruporas.tarifaagua.domain.FaixaTarifaria;
import br.com.gruporas.tarifaagua.dto.CalculoResponse;
import br.com.gruporas.tarifaagua.dto.DetalhamentoFaixaResponse;
import br.com.gruporas.tarifaagua.exception.ApiException;
import br.com.gruporas.tarifaagua.repository.FaixaTarifariaRepository;
import br.com.gruporas.tarifaagua.repository.TabelaTarifariaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class CalculoService {

  private static final int FIM_PADRAO = 99999;

  private final TabelaTarifariaRepository tabelaRepo;
  private final FaixaTarifariaRepository faixaRepo;

  public CalculoService(TabelaTarifariaRepository tabelaRepo, FaixaTarifariaRepository faixaRepo) {
    this.tabelaRepo = tabelaRepo;
    this.faixaRepo = faixaRepo;
  }

  @Transactional(readOnly = true)
  public CalculoResponse calcular(CategoriaConsumidor categoria, int consumoTotal) {
    if (consumoTotal < 0) {
      throw new ApiException(HttpStatus.UNPROCESSABLE_ENTITY, "consumo deve ser >= 0");
    }

    // O PDF não define critério de vigência; então usamos a tabela ativa mais recente.
    var tabelas = tabelaRepo.findAllByAtivaTrueOrderByCriadoEmDesc();
    if (tabelas.isEmpty()) {
      throw new ApiException(HttpStatus.UNPROCESSABLE_ENTITY, "Nenhuma tabela tarifária ativa cadastrada");
    }
    var tabela = tabelas.get(0);

    List<FaixaTarifaria> faixas = faixaRepo.findAllByTabelaIdAndCategoriaOrderByInicioAsc(tabela.getId(), categoria);
    if (faixas.isEmpty()) {
      throw new ApiException(HttpStatus.UNPROCESSABLE_ENTITY,
          "Não existem faixas cadastradas para a categoria " + categoria + " na tabela ativa");
    }

    faixas = faixas.stream()
        .sorted(Comparator.comparingInt(FaixaTarifaria::getInicio))
        .toList();

    // Regra do exemplo do PDF:
    // - Faixa 0 a 10: cobra 10 m³ (não 11)
    // - Demais faixas: (fim - inicio + 1)
    int restante = consumoTotal;
    BigDecimal total = BigDecimal.ZERO;
    List<DetalhamentoFaixaResponse> detalhamento = new ArrayList<>();

    for (FaixaTarifaria f : faixas) {
      if (restante <= 0) break;

      int inicio = f.getInicio();
      int fim = (f.getFim() == null) ? FIM_PADRAO : f.getFim();

      int capacidade;
      if (inicio == 0) {
        capacidade = fim; // 0..10 -> 10
      } else {
        capacidade = fim - inicio + 1; // 11..20 -> 10
      }

      if (capacidade <= 0) continue;

      int cobrados = Math.min(restante, capacidade);
      BigDecimal subtotal = f.getValorUnitario().multiply(BigDecimal.valueOf(cobrados));

      total = total.add(subtotal);
      restante -= cobrados;

      detalhamento.add(new DetalhamentoFaixaResponse(
          new DetalhamentoFaixaResponse.FaixaInfo(inicio, fim),
          cobrados,
          f.getValorUnitario(),
          subtotal
      ));
    }

    if (restante > 0) {
      // Não deveria ocorrer se o cadastro respeitar "cobertura suficiente"
      throw new ApiException(HttpStatus.UNPROCESSABLE_ENTITY,
          "Faixas não cobrem o consumo informado (consumo=" + consumoTotal + ")");
    }

    return new CalculoResponse(categoria, consumoTotal, total, detalhamento);
  }
}
