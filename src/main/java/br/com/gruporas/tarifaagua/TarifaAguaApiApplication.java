package br.com.gruporas.tarifaagua;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// Classe principal da aplicação Spring Boot, anotada com @SpringBootApplication para habilitar a configuração automática, a varredura de componentes e outras funcionalidades do Spring. O método main é o ponto de entrada da aplicação, onde a classe é executada para iniciar o contexto do Spring e lançar a aplicação. Esta classe serve como o ponto central para a configuração e inicialização da API de tarifa de água.

@SpringBootApplication
public class TarifaAguaApiApplication {
  public static void main(String[] args) {
    SpringApplication.run(TarifaAguaApiApplication.class, args);
  }
}
