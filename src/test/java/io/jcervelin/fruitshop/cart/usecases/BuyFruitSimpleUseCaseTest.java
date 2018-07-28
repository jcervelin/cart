package io.jcervelin.fruitshop.cart.usecases;

import io.jcervelin.fruitshop.cart.domains.CartResponse;
import io.jcervelin.fruitshop.cart.domains.Fruit;
import io.jcervelin.fruitshop.cart.domains.FruitType;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static br.com.six2six.fixturefactory.Fixture.from;
import static br.com.six2six.fixturefactory.loader.FixtureFactoryLoader.loadTemplates;
import static io.jcervelin.fruitshop.cart.templates.CartResponseTemplates.*;
import static io.jcervelin.fruitshop.cart.templates.FruitTemplates.APPLE;
import static io.jcervelin.fruitshop.cart.templates.FruitTemplates.ORANGE;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class BuyFruitSimpleUseCaseTest {

    private static final String TEMPLATE_PACKAGE = "io.jcervelin.fruitshop.cart";
    @Rule
    public final ExpectedException thrown = ExpectedException.none();
    private List<FruitType> fruits;
    @InjectMocks
    private BuyFruitSimpleUseCase target;

    @BeforeClass
    public static void setup() {
        loadTemplates(TEMPLATE_PACKAGE);
    }

    @Before
    public void before() {
        fruits = Arrays.asList(FruitType.values());
    }

    @Test
    public void shouldReturn4FruitsWithPrice() {

        // GIVEN a list of fruits
        final CartResponse expected = from(CartResponse.class)
                .gimme(CART_4_FRUITS_135);
        final Collection<Fruit> fruits = from(Fruit.class)
                .gimme(2, ORANGE,APPLE);
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
        final Collection<Fruit> fruits = from(Fruit.class)
                .gimme(2, ORANGE,APPLE);
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
        final Collection<Fruit> fruits = from(Fruit.class)
                .gimme(2, ORANGE,APPLE);

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
        final Collection<Fruit> fruits = from(Fruit.class)
                .gimme(2, ORANGE,APPLE);

        final CartResponse result = target.startUseCase(stringFruits);

        assertThat(result.getFruits().size()).isEqualTo(0);
        assertThat(result).isEqualToComparingFieldByField(expected);
    }
}