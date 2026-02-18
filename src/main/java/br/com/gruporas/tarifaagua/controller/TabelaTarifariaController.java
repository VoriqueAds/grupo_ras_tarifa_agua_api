package br.com.gruporas.tarifaagua.controller;

import br.com.gruporas.tarifaagua.domain.CategoriaConsumidor;
import br.com.gruporas.tarifaagua.dto.FaixaResponse;
import br.com.gruporas.tarifaagua.dto.TabelaTarifariaCreateRequest;
import br.com.gruporas.tarifaagua.dto.TabelaTarifariaResponse;
import br.com.gruporas.tarifaagua.service.TabelaTarifariaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/tabelas-tarifarias")
public class TabelaTarifariaController {

  private final TabelaTarifariaService service;

  public TabelaTarifariaController(TabelaTarifariaService service) {
    this.service = service;
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public TabelaTarifariaResponse criar(@Valid @RequestBody TabelaTarifariaCreateRequest req) {
    return service.criarTabelaCompleta(req);
  }

  @GetMapping
  public List<TabelaTarifariaResponse> listar() {
    return service.listarTabelas();
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void excluir(@PathVariable UUID id) {
    service.excluirTabela(id);
  }

  @GetMapping("/ativa/faixas")
  public List<FaixaResponse> listarFaixasDaTabelaAtiva(@RequestParam CategoriaConsumidor categoria) {
    return service.listarFaixasDaTabelaAtivaPorCategoria(categoria);
  }
}
