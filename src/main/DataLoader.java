package main.java.com.example.demoshop;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import main.java.com.example.demoshop.java.com.example.demoshop.domain.model.catalogue.Product;
import main.java.com.example.demoshop.java.com.example.demoshop.domain.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;

import java.io.InputStream;
import java.util.List;

public class DataLoader implements CommandLineRunner {

    private final ProductRepository repository;

    public DataLoader(ProductRepository repository) {
        this.repository = repository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (repository.count() == 0) { // load only if DB is empty
            ObjectMapper mapper = new ObjectMapper();
            TypeReference<List<Product>> typeReference = new TypeReference<List<Product>>() {};
            InputStream inputStream = TypeReference.class.getResourceAsStream("/data/products.json");
            List<Product> products = mapper.readValue(inputStream, typeReference);
            repository.saveAll(products);
            System.out.println("Demo products loaded.");
        }
    }
}
