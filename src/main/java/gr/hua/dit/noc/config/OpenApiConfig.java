package gr.hua.dit.noc.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// Configuration κλάση για ρύθμιση OpenAPI and  Swagger documentation
// Χρησιμοποιείται για την τεκμηρίωση των REST endpoints του NOC service
@Configuration
public class OpenApiConfig {

    // Ορισμός βασικών πληροφοριών του OpenAPI specification
    @Bean
    public OpenAPI api() {
        return new OpenAPI()
                .info(new Info()
                        .title("NOC Services")
                        .version("v1")
                        .description("Stateless REST API for lookups and notifications"));
    }

    // Ομαδοποίηση των REST endpoints που θα εμφανίζονται στο Swagger UI
    @Bean
    public GroupedOpenApi apiGroup() {
        return GroupedOpenApi.builder()
                .group("api")
                // Σάρωση των REST controllers του NOC service
                .packagesToScan("gr.hua.dit.noc.web.rest")
                // Περιορισμός στα endpoints κάτω από /api/v1/**
                .pathsToMatch("/api/v1/**")
                .build();
    }
}