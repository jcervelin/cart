package io.jcervelin.fruitshop.cart;

import io.jcervelin.fruitshop.cart.domains.CartResponse;
import io.jcervelin.fruitshop.cart.domains.Fruit;
import io.jcervelin.fruitshop.cart.gateways.FruitGateway;
import io.jcervelin.fruitshop.cart.usecases.BuyFruitSimpleUseCase;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;
import java.util.List;

@SpringBootApplication
@Slf4j
public class CartApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(CartApplication.class, args);
	}

	@Autowired
	private FruitGateway fruitGateway;

	@Autowired
	private BuyFruitSimpleUseCase simpleUseCase;

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

		final List<String> candidate1Cart = Arrays.asList("Orange","Apple","Orange");
		final List<String> candidate2Cart = Arrays.asList("Orange","Orange","Orange","Apple");

		final CartResponse cartToCandidate1 = simpleUseCase.startUseCase(candidate1Cart);
		final CartResponse cartToCandidate2 = simpleUseCase.startUseCase(candidate2Cart);
		log.info("Candidate 1's cart: {}",cartToCandidate1);
		log.info("Candidate 2's cart: {}",cartToCandidate2);
	}
}
