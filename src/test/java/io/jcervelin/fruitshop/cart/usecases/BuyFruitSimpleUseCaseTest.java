package io.jcervelin.fruitshop.cart.usecases;

import io.jcervelin.fruitshop.cart.config.UnitTestingSupport;
import io.jcervelin.fruitshop.cart.domains.CartResponse;
import org.junit.Test;
import org.mockito.InjectMocks;

import java.util.Arrays;
import java.util.List;

import static br.com.six2six.fixturefactory.Fixture.from;
import static io.jcervelin.fruitshop.cart.templates.CartResponseTemplates.*;
import static org.assertj.core.api.Assertions.assertThat;

public class BuyFruitSimpleUseCaseTest extends UnitTestingSupport {

    @InjectMocks
    private BuyFruitSimpleUseCase target;

    @Test
    public void shouldReturn4FruitsWithPrice() {

        // GIVEN a list of fruits
        final CartResponse expected = from(CartResponse.class)
                .gimme(CART_4_FRUITS_135);

        final List<String> stringFruits = Arrays.asList("Orange","Apple","Orange","Orange");

        // WHEN 4 fruits are put in cart

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

        final List<String> stringFruits = Arrays.asList("Banana","Apple","Orange","Orange");

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

        final CartResponse result = target.startUseCase(stringFruits);

        assertThat(result.getFruits().size()).isEqualTo(0);
        assertThat(result).isEqualToComparingFieldByField(expected);
    }
}