package hello;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@ComponentScan
@Configuration
public class CommonConfig {
    @Bean
    public RestTemplate restTemplate() {
        System.out.println("Returning new rest template");
        return new RestTemplate();
    }
}
