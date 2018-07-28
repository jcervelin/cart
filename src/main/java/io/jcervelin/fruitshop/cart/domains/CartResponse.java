package io.jcervelin.fruitshop.cart.domains;

import lombok.Builder;
import lombok.Data;

import java.util.Collection;

@Data
@Builder
public class CartResponse {
    private Collection<Fruit> fruits;
    private Double total;
}
