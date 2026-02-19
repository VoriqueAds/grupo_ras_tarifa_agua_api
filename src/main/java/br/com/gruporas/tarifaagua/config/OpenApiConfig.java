package br.com.gruporas.tarifaagua.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// Configuração para o OpenAPI (Swagger) para documentação da API

@Configuration
public class OpenApiConfig {

  @Bean
  public OpenAPI api() {
    return new OpenAPI()
        .info(new Info()
            .title("API Tabela Tarifária de Água - GrupoRAS")
            .version("1.0")
            .description("API REST para gerenciamento de tabelas tarifárias e calculo de valor a pagar por faixas progressivas."));
  }
}
