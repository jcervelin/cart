package io.jcervelin.fruitshop.cart.usecases;

import io.jcervelin.fruitshop.cart.domains.CartResponse;
import io.jcervelin.fruitshop.cart.domains.Fruit;
import io.jcervelin.fruitshop.cart.gateways.FruitGateway;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BuyFruitUsecase {

    @Autowired
    private final FruitGateway fruitGateway;

    public CartResponse startUseCase(final Collection<String> fruits) {

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


        final Double total = fruitsWithPrice.stream()
                .map(Fruit::getPrice)
                .reduce(Double::sum)
                .orElse(0d);

        return CartResponse.builder()
                .fruits(fruitsWithPrice)
                .total(total)
                .build();
    }

    private Predicate<Fruit> verifyFruitAvailability (final String str) {
        return fruit -> fruit.getFruit().equalsIgnoreCase(str);
    }
}
