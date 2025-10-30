package example.demoshop;

import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableScheduling
@EnableJpaRepositories
public class DemoShopApplication {
    public static void main(String[] args) {
        SpringApplication.run(DemoShopApplication.class, args);
    }
}
