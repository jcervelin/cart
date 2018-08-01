package io.jcervelin.fruitshop.cart;

import io.jcervelin.fruitshop.cart.domains.CartResponse;
import io.jcervelin.fruitshop.cart.domains.Fruit;
import io.jcervelin.fruitshop.cart.gateways.mongo.FruitGateway;
import io.jcervelin.fruitshop.cart.usecases.BuyFruitSimpleUseCase;
import io.jcervelin.fruitshop.cart.usecases.BuyFruitWithOffersUseCase;
import io.jcervelin.fruitshop.cart.usecases.FruitUseCase;
import io.jcervelin.fruitshop.cart.usecases.offers.Offer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;
import java.util.List;

@SpringBootApplication
@Slf4j
@RequiredArgsConstructor
public class CartApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(CartApplication.class, args);
	}

	private final FruitGateway fruitGateway;

	private final List<Offer> offers;

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

        //GIVEN a list of fruits
		final List<String> candidate1Cart = Arrays.asList("Orange","Apple","Orange");
		final List<String> candidate2Cart = Arrays.asList("Orange","Orange","Orange","Apple");

        FruitUseCase useCase = new BuyFruitSimpleUseCase();

        // WHEN a candidate buy some fruits without offer
		final CartResponse cartToCandidate1 = useCase.startUseCase(candidate1Cart);
		final CartResponse cartToCandidate2 = useCase.startUseCase(candidate2Cart);

		// THEN...
		log.info("Candidate 1's cart: {}",cartToCandidate1);
		log.info("Candidate 2's cart: {}",cartToCandidate2);

		// GIVEN another (or the same) candidate with a coupon
		useCase = new BuyFruitWithOffersUseCase(offers,useCase);

		//WHEN they buy some fruits with discount
		final CartResponse cartToCandidate3 = useCase.startUseCase(candidate1Cart);
		final CartResponse cartToCandidate4 = useCase.startUseCase(candidate2Cart);

		//THEN they earn discount and free fruits
		log.info("Candidate 3's cart: {}",cartToCandidate3);
		log.info("Candidate 4's cart: {}",cartToCandidate4);
	}

}
