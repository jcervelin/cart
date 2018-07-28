package io.jcervelin.fruitshop.cart.domains;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
@Builder
@EqualsAndHashCode(of="fruit")
public class Fruit {

    @Id
    private String fruit;
    private double price;

}
