package io.jcervelin.fruitshop.cart.usecases;

import io.jcervelin.fruitshop.cart.domains.CartResponse;
import io.jcervelin.fruitshop.cart.domains.Fruit;

import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;

public interface FruitUseCase {

    CartResponse startUseCase(final Collection<String> fruits);

    default Predicate<Fruit> verifyFruitAvailability (final String str) {
        return fruit -> fruit.getFruit().equalsIgnoreCase(str);
    }

    default Double getTotal(final List<Fruit> fruits) {
        return fruits.stream()
                .map(Fruit::getPrice)
                .reduce(Double::sum)
                .orElse(0d);
    }

}