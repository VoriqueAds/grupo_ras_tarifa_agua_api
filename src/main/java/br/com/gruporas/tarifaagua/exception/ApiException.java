package br.com.gruporas.tarifaagua.exception;

import org.springframework.http.HttpStatus;

// Exceção personalizada para erros de API, contendo um status HTTP e uma mensagem de erro. Utilizada para lançar exceções específicas em casos de erros de validação, recursos não encontrados ou outros erros relacionados à lógica de negócios da API.

public class ApiException extends RuntimeException {
  private final HttpStatus status;

  public ApiException(HttpStatus status, String message) {
    super(message);
    this.status = status;
  }

  public HttpStatus getStatus() {
    return status;
  }
}
