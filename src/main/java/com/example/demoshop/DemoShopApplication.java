package main.java.com.example.demoshop.java.com.example.demoshop;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableScheduling
public class DemoShopApplication {
    public static void main(String[] args) {
        SpringApplication.run(DemoShopApplication.class, args);
    }
}
