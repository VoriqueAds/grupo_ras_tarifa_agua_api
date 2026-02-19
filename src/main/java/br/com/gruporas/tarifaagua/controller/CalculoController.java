package br.com.gruporas.tarifaagua.controller;

import br.com.gruporas.tarifaagua.dto.CalculoRequest;
import br.com.gruporas.tarifaagua.dto.CalculoResponse;
import br.com.gruporas.tarifaagua.service.CalculoService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

// Controlador REST para cálculos de valor a pagar com base nas faixas progressivas

@RestController
@RequestMapping("/api/calculos")
public class CalculoController {

  private final CalculoService service;

  public CalculoController(CalculoService service) {
    this.service = service;
  }

  @PostMapping
  public CalculoResponse calcular(@Valid @RequestBody CalculoRequest req) {
    return service.calcular(req.categoria(), req.consumo());
  }
}
