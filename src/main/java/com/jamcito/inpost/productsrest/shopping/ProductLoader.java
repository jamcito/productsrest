package com.jamcito.inpost.productsrest.shopping;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProductLoader {

    public class LoadProducts {

        private static final Logger log = LoggerFactory.getLogger(LoadProducts.class);

        @Bean
        CommandLineRunner initDatabase(ProductRepository repository) {

            return args -> {
                log.info("Inserting " + repository.save(new Product(10, 1000)));
                log.info("Inserting " + repository.save(new Product(20, 175, DiscountPolicy.COUNT_BASED, 100)));
                log.info("Inserting " + repository.save(new Product(50, 300, DiscountPolicy.PERCENTAGE, 10)));
            };
        }
    }
}
