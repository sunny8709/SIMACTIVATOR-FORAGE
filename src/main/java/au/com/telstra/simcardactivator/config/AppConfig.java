package au.com.telstra.simcardactivator.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * Application configuration class.
 * Provides beans for the application context.
 */
@Configuration
public class AppConfig {

    /**
     * Creates a RestTemplate bean for making HTTP requests.
     *
     * @return a new RestTemplate instance
     */
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}