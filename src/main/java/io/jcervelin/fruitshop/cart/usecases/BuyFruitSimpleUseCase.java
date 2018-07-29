package io.jcervelin.fruitshop.cart.usecases;

import io.jcervelin.fruitshop.cart.domains.CartResponse;
import io.jcervelin.fruitshop.cart.domains.Fruit;
import io.jcervelin.fruitshop.cart.domains.FruitType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@Slf4j
@RequiredArgsConstructor
public class BuyFruitSimpleUseCase implements FruitUseCase {

    public CartResponse startUseCase(final Collection<String> fruits) {
        log.info(">> Fruits: {}",fruits);

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

        final CartResponse cartResponse = CartResponse.builder()
                .fruits(fruitsWithPrice)
                .total(getTotal(fruitsWithPrice))
                .build();
        log.info("<< CartResponse: {}",cartResponse);
        return cartResponse;
    }

    private Fruit convert(FruitType type) {
        return Fruit.builder()
                .price(type.getPrice())
                .fruit(type.getFruit())
                .build();
    }

}