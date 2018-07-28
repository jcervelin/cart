package io.jcervelin.fruitshop.cart.usecases;

import io.jcervelin.fruitshop.cart.domains.CartResponse;
import io.jcervelin.fruitshop.cart.domains.Fruit;
import io.jcervelin.fruitshop.cart.domains.FruitType;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class BuyFruitSimpleUseCase {

    public CartResponse startUseCase(final Collection<String> fruits) {

        final List<Fruit> fruitTypeList = Arrays.asList(FruitType.values())
                .stream()
                .map(this::convert)
                .collect(toList());

        final List<Fruit> fruitsWithPrice = CollectionUtils.emptyIfNull(fruits).stream()
                .filter(s -> fruitTypeList.stream()
                        .anyMatch(verifyFruitAvailability(s)))
                .map(s -> Fruit.builder()
                        .price(fruitTypeList.stream()
                                .filter(verifyFruitAvailability(s))
                                .findAny()
                                .orElse(Fruit.builder().build())
                                .getPrice()
                        )
                        .fruit(s)
                        .build()
                ).collect(toList());

        return CartResponse.builder()
                .fruits(fruitsWithPrice)
                .total(getTotal(fruitsWithPrice))
                .build();
    }

    private Predicate<Fruit> verifyFruitAvailability (final String str) {
        return fruit -> fruit.getFruit().equalsIgnoreCase(str);
    }

    private Fruit convert(FruitType type) {
        return Fruit.builder()
                .price(type.getPrice())
                .fruit(type.getFruit())
                .build();
    }

    private Double getTotal(final List<Fruit> fruits) {
        return fruits.stream()
                .map(Fruit::getPrice)
                .reduce(Double::sum)
                .orElse(0d);
    }
}