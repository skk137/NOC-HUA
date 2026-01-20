package gr.hua.dit.noc.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

// Configuration κλάση για ορισμό HTTP client
// Παρέχει RestTemplate bean για κλήσεις σε εξωτερικά REST APIs
@Configuration
public class RestClientConfig {

    // Δημιουργία και καταχώρηση RestTemplate στο Spring context
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}