package io.jcervelin.fruitshop.cart.usecases.offers;

import io.jcervelin.fruitshop.cart.domains.CartResponse;
import io.jcervelin.fruitshop.cart.domains.Fruit;
import io.jcervelin.fruitshop.cart.domains.FruitType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class BuyOneGetOneFreeOnApplesUseCase implements Offer {

    @Override
    public void execute(final CartResponse response) {
        log.info(">> BuyOneGetOneFreeOnApplesUseCase.execute CartResponse: {}",response);
        final Fruit apple = FruitType.APPLE.convertFruitTypeToFruit();

        if (response.getFruits().size() >= 1) {
            response.getFruits().add(apple);
        }
        log.info("<< BuyOneGetOneFreeOnApplesUseCase.execute CartResponse: {}",response);
    }
}
