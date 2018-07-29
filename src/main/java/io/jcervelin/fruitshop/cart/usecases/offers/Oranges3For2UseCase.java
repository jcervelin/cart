package io.jcervelin.fruitshop.cart.usecases.offers;

import io.jcervelin.fruitshop.cart.domains.CartResponse;
import io.jcervelin.fruitshop.cart.domains.Fruit;
import io.jcervelin.fruitshop.cart.domains.FruitType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class Oranges3For2UseCase implements Offer {

    @Override
    public void execute(CartResponse response) {
        log.info(">> Oranges3For2UseCase.execute CartResponse: {}",response);

        final Fruit orange = FruitType.ORANGE.convertFruitTypeToFruit();

        final long numberOfOranges = response.getFruits()
                                        .stream()
                                        .filter(orange::equals)
                                        .count();

        long freeOranges = 0;

        if(numberOfOranges >= 3) {
            freeOranges = numberOfOranges / 3;
        }

        double newTotal = response.getTotal() - (freeOranges * orange.getPrice());

        response.setTotal(newTotal);
        log.info("<< Oranges3For2UseCase.execute CartResponse: {}",response);
    }
}
