package io.jcervelin.fruitshop.cart.usecases;

import io.jcervelin.fruitshop.cart.domains.CartResponse;
import io.jcervelin.fruitshop.cart.domains.Fruit;
import io.jcervelin.fruitshop.cart.gateways.mongo.FruitGateway;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class BuyFruitUsecase implements FruitUseCase {

    @Autowired
    private final FruitGateway fruitGateway;

    public CartResponse startUseCase(final Collection<String> fruits) {
        log.info(">> Fruits: {}", fruits);
        final Collection<Fruit> priceFruits = fruitGateway.findAll();

        final List<Fruit> fruitsWithPrice = CollectionUtils.emptyIfNull(fruits).stream()
                .filter(s -> priceFruits.stream()
                        .anyMatch(verifyFruitAvailability(s)))
                .map(s -> Fruit.builder()
                        .price(priceFruits.stream()
                                .filter(verifyFruitAvailability(s))
                                .findAny()
                                .orElse(Fruit.builder().build())
                                .getPrice()
                        )
                        .fruit(s)
                        .build()
                ).collect(Collectors.toList());


        final CartResponse cartResponse = CartResponse.builder()
                .fruits(fruitsWithPrice)
                .total(getTotal(fruitsWithPrice))
                .build();
        log.info("<< CartResponse: {}",cartResponse);
        return cartResponse;
    }

}
