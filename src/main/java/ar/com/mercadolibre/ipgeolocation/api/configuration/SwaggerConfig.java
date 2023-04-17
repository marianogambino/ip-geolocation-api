package ar.com.mercadolibre.ipgeolocation.api.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

  @Bean
  public GroupedOpenApi publicApi() {
    return GroupedOpenApi.builder()
      .group("public")
      .packagesToScan("ar.com.mercadolibre.ipgeolocation.api.controller")
      .build();
  }

  @Bean
  public OpenAPI springOpenAPI() {
    return (new OpenAPI())
      .info((new Info()).title("IP Geolocation API"));
  }

}
