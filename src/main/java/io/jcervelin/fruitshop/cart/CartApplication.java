package io.jcervelin.fruitshop.cart;

import io.jcervelin.fruitshop.cart.domains.Fruit;
import io.jcervelin.fruitshop.cart.gateways.FruitGateway;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class CartApplication implements CommandLineRunner {

	@Autowired
	private FruitGateway fruitGateway;

	public static void main(String[] args) {
		SpringApplication.run(CartApplication.class, args);
	}

	@Override
	public void run(String... args) {
        fruitGateway.save(Fruit.builder()
                .fruit("Orange")
                .price(25L)
                .build());
        fruitGateway.save(Fruit.builder()
                .fruit("Apple")
                .price(60L)
                .build());

	}
}
