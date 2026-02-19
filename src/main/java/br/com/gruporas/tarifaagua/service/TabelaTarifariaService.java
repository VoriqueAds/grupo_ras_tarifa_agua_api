package br.com.gruporas.tarifaagua.service;

import br.com.gruporas.tarifaagua.domain.CategoriaConsumidor;
import br.com.gruporas.tarifaagua.domain.FaixaTarifaria;
import br.com.gruporas.tarifaagua.domain.TabelaTarifaria;
import br.com.gruporas.tarifaagua.dto.*;
import br.com.gruporas.tarifaagua.exception.ApiException;
import br.com.gruporas.tarifaagua.repository.FaixaTarifariaRepository;
import br.com.gruporas.tarifaagua.repository.TabelaTarifariaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

// Serviço para gerenciamento de tabelas tarifárias e faixas progressivas, contendo a lógica para criar novas tabelas tarifárias com validação das faixas, listar tabelas ativas, excluir (desativar) tabelas e listar faixas da tabela ativa por categoria. Utilizado pelos controladores para realizar operações relacionadas às tabelas tarifárias e suas faixas.

@Service
public class TabelaTarifariaService {

  private static final int FIM_PADRAO = 99999;

  private final TabelaTarifariaRepository tabelaRepo;
  private final FaixaTarifariaRepository faixaRepo;

  public TabelaTarifariaService(TabelaTarifariaRepository tabelaRepo, FaixaTarifariaRepository faixaRepo) {
    this.tabelaRepo = tabelaRepo;
    this.faixaRepo = faixaRepo;
  }

  @Transactional
  public TabelaTarifariaResponse criarTabelaCompleta(TabelaTarifariaCreateRequest req) {
    if (req.vigenciaInicio() != null && req.vigenciaFim() != null && req.vigenciaFim().isBefore(req.vigenciaInicio())) {
      throw new ApiException(HttpStatus.UNPROCESSABLE_ENTITY, "vigenciaFim deve ser >= vigenciaInicio");
    }

    UUID tabelaId = UUID.randomUUID();
    var tabela = new TabelaTarifaria(tabelaId, req.nome(), req.vigenciaInicio(), req.vigenciaFim(), true);
    tabelaRepo.save(tabela);

    List<FaixaTarifaria> persistir = new ArrayList<>();

    for (CategoriaComFaixasRequest cat : req.categorias()) {
      validarFaixas(cat.faixas(), cat.categoria());

      for (FaixaRequest f : cat.faixas()) {
        Integer fim = (f.fim() == null) ? FIM_PADRAO : f.fim();
        persistir.add(new FaixaTarifaria(UUID.randomUUID(), tabela, cat.categoria(), f.inicio(), fim, f.valorUnitario()));
      }
    }

    faixaRepo.saveAll(persistir);
    return montarResponse(tabela, persistir);
  }

  @Transactional(readOnly = true)
  public List<TabelaTarifariaResponse> listarTabelas() {
    var tabelas = tabelaRepo.findAllByAtivaTrueOrderByCriadoEmDesc();
    if (tabelas.isEmpty()) return List.of();

    Map<UUID, List<FaixaTarifaria>> faixasPorTabela = new HashMap<>();
    for (TabelaTarifaria t : tabelas) {
      faixasPorTabela.put(t.getId(), faixaRepo.findAllByTabelaIdOrderByCategoriaAscInicioAsc(t.getId()));
    }

    List<TabelaTarifariaResponse> out = new ArrayList<>();
    for (TabelaTarifaria t : tabelas) {
      out.add(montarResponse(t, faixasPorTabela.getOrDefault(t.getId(), List.of())));
    }
    return out;
  }

  @Transactional
  public void excluirTabela(UUID id) {
    var tabela = tabelaRepo.findByIdAndAtivaTrue(id)
        .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "Tabela tarifária não encontrada"));
    tabela.desativar();
    tabelaRepo.save(tabela);
  }

  @Transactional(readOnly = true)
  public List<FaixaResponse> listarFaixasDaTabelaAtivaPorCategoria(CategoriaConsumidor categoria) {
    var tabelas = tabelaRepo.findAllByAtivaTrueOrderByCriadoEmDesc();
    if (tabelas.isEmpty()) {
      throw new ApiException(HttpStatus.UNPROCESSABLE_ENTITY, "Nenhuma tabela tarifária ativa cadastrada");
    }
    var tabela = tabelas.get(0);

    var faixas = faixaRepo.findAllByTabelaIdAndCategoriaOrderByInicioAsc(tabela.getId(), categoria);
    return faixas.stream()
        .sorted(Comparator.comparingInt(FaixaTarifaria::getInicio))
        .map(f -> new FaixaResponse(f.getId(), f.getInicio(), (f.getFim() == null ? FIM_PADRAO : f.getFim()), f.getValorUnitario()))
        .toList();
  }

  private void validarFaixas(List<FaixaRequest> faixas, CategoriaConsumidor categoria) {
    if (faixas == null || faixas.isEmpty()) {
      throw new ApiException(HttpStatus.UNPROCESSABLE_ENTITY, "Categoria " + categoria + " deve conter faixas");
    }

    var ordenadas = faixas.stream()
        .sorted(Comparator.comparingInt(FaixaRequest::inicio))
        .toList();

    // Cobertura completa: deve iniciar em 0
    if (ordenadas.get(0).inicio() != 0) {
      throw new ApiException(HttpStatus.UNPROCESSABLE_ENTITY,
          "Cobertura completa: primeira faixa da categoria " + categoria + " deve iniciar em 0");
    }

    Integer ultimoFim = null;
    for (FaixaRequest f : ordenadas) {
      if (f.fim() == null) {
        throw new ApiException(HttpStatus.UNPROCESSABLE_ENTITY,
            "fim não pode ser nulo (use 99999) na categoria " + categoria);
      }
      // Regra do PDF: inicio < fim
      if (f.inicio() < 0 || f.fim() <= f.inicio()) {
        throw new ApiException(HttpStatus.UNPROCESSABLE_ENTITY,
            "Ordem inválida: início < fim deve ser respeitado na categoria " + categoria);
      }

      if (ultimoFim != null) {
        // Sem sobreposição E cobertura contínua: próximo início = últimoFim + 1
        if (f.inicio() != ultimoFim + 1) {
          throw new ApiException(HttpStatus.UNPROCESSABLE_ENTITY,
              "Faixas devem ser contínuas e sem sobreposição: esperado início " + (ultimoFim + 1) +
                  " após fim " + ultimoFim + " (categoria " + categoria + ")");
        }
      }
      ultimoFim = f.fim();
    }

    // Cobertura suficiente: deve cobrir qualquer consumo informado
    if (ultimoFim == null || ultimoFim < FIM_PADRAO) {
      throw new ApiException(HttpStatus.UNPROCESSABLE_ENTITY,
          "Cobertura suficiente: última faixa da categoria " + categoria + " deve terminar em pelo menos " + FIM_PADRAO);
    }
  }

  private TabelaTarifariaResponse montarResponse(TabelaTarifaria tabela, List<FaixaTarifaria> faixas) {
    Map<CategoriaConsumidor, List<FaixaTarifaria>> porCat =
        faixas.stream().collect(Collectors.groupingBy(FaixaTarifaria::getCategoria));

    List<CategoriaComFaixasResponse> categorias = new ArrayList<>();
    for (CategoriaConsumidor c : CategoriaConsumidor.values()) {
      var lista = porCat.getOrDefault(c, List.of()).stream()
          .sorted(Comparator.comparingInt(FaixaTarifaria::getInicio))
          .map(f -> new FaixaResponse(
              f.getId(),
              f.getInicio(),
              (f.getFim() == null ? FIM_PADRAO : f.getFim()),
              f.getValorUnitario()
          ))
          .toList();

      if (!lista.isEmpty()) categorias.add(new CategoriaComFaixasResponse(c, lista));
    }

    return new TabelaTarifariaResponse(
        tabela.getId(),
        tabela.getNome(),
        tabela.getVigenciaInicio(),
        tabela.getVigenciaFim(),
        tabela.isAtiva(),
        categorias
    );
  }
}
