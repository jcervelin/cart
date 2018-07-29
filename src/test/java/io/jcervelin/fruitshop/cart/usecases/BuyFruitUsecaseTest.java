package io.jcervelin.fruitshop.cart.usecases;

import io.jcervelin.fruitshop.cart.config.UnitTestingSupport;
import io.jcervelin.fruitshop.cart.domains.CartResponse;
import io.jcervelin.fruitshop.cart.domains.Fruit;
import io.jcervelin.fruitshop.cart.gateways.mongo.FruitGateway;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Arrays;
import java.util.List;

import static br.com.six2six.fixturefactory.Fixture.from;
import static io.jcervelin.fruitshop.cart.templates.CartResponseTemplates.*;
import static io.jcervelin.fruitshop.cart.templates.FruitTemplates.APPLE;
import static io.jcervelin.fruitshop.cart.templates.FruitTemplates.ORANGE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

public class BuyFruitUsecaseTest extends UnitTestingSupport {

    @InjectMocks
    private BuyFruitUsecase target;

    @Mock
    private FruitGateway fruitGateway;

    @Test
    public void shouldReturn4FruitsWithPrice() {

        // GIVEN a list of fruits
        final CartResponse expected = from(CartResponse.class)
                .gimme(CART_4_FRUITS_135);
        final List<Fruit> fruits = from(Fruit.class)
                .gimme(2, ORANGE,APPLE);
        final List<String> stringFruits = Arrays.asList("Orange","Apple","Orange","Orange");

        // WHEN 4 fruits are put in cart
        when(fruitGateway.findAll())
                .thenReturn(fruits);

        // THEN return fruits in cart with total price
        final CartResponse result = target.startUseCase(stringFruits);

        assertThat(result.getFruits().size()).isEqualTo(4);
        assertThat(result).isEqualToComparingFieldByField(expected);

    }

    @Test
    public void givenInvalidFruitShouldReturnOnlyAvailableFruitsWithPrice() {

        // GIVEN a list of fruits
        final CartResponse expected = from(CartResponse.class)
                .gimme(CART_3_FRUITS_110);
        final List<Fruit> fruits = from(Fruit.class)
                .gimme(2, ORANGE,APPLE);
        final List<String> stringFruits = Arrays.asList("Banana","Apple","Orange","Orange");

        // WHEN 3 valid fruits and 1 invalid fruit are put in cart
        when(fruitGateway.findAll())
                .thenReturn(fruits);

        // THEN return only valid fruits in cart with total price
        final CartResponse result = target.startUseCase(stringFruits);

        assertThat(result.getFruits().size()).isEqualTo(3);
        assertThat(result).isEqualToComparingFieldByField(expected);
    }

    @Test
    public void givenOnlyInvalidFruitsCartShouldReturnEmpty() {

        // GIVEN a list of invalid fruits
        final List<String> stringFruits = Arrays.asList("Banana","Carrot","Peach");
        final CartResponse expected = from(CartResponse.class)
                .gimme(EMPTY_CART);
        final List<Fruit> fruits = from(Fruit.class)
                .gimme(2, ORANGE,APPLE);

        // WHEN invalid fruits are put in cart
        when(fruitGateway.findAll())
                .thenReturn(fruits);

        // THEN return only valid fruits in cart with total price
        final CartResponse result = target.startUseCase(stringFruits);

        assertThat(result.getFruits().size()).isEqualTo(0);
        assertThat(result).isEqualToComparingFieldByField(expected);
    }

    @Test
    public void givenNullShouldReturnEmptyCart() {

        // GIVEN null
        final List<String> stringFruits = null;
        final CartResponse expected = from(CartResponse.class)
                .gimme(EMPTY_CART);
        final List<Fruit> fruits = from(Fruit.class)
                .gimme(2, ORANGE,APPLE);

        // WHEN null are put in cart
        when(fruitGateway.findAll())

                // THEN return only valid fruits in cart with total price
                .thenReturn(fruits);

        final CartResponse result = target.startUseCase(stringFruits);

        assertThat(result.getFruits().size()).isEqualTo(0);
        assertThat(result).isEqualToComparingFieldByField(expected);
    }

    @Test(expected = Exception.class)
    public void shouldReturnException() {

        // GIVEN a list of fruits
        final List<String> stringFruits = Arrays.asList("Orange","Apple","Orange","Orange");

        // WHEN 4 fruits are put in cart
        when(fruitGateway.findAll())
                .thenThrow(Exception.class);

        // THEN a exception is thrown
        target.startUseCase(stringFruits);

    }

}