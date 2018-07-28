package io.jcervelin.fruitshop.cart.domains;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum FruitType {

    ORANGE("Orange",25d),
    APPLE("Apple",60d);

    private final String fruit;
    private final Double price;

}
