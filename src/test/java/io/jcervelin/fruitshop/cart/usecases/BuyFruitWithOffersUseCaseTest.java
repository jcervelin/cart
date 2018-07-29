package io.jcervelin.fruitshop.cart.usecases;

import io.jcervelin.fruitshop.cart.config.UnitTestingSupport;
import io.jcervelin.fruitshop.cart.domains.CartResponse;
import io.jcervelin.fruitshop.cart.usecases.offers.BuyOneGetOneFreeOnApplesUseCase;
import io.jcervelin.fruitshop.cart.usecases.offers.Offer;
import io.jcervelin.fruitshop.cart.usecases.offers.Oranges3For2UseCase;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.internal.util.reflection.Whitebox;

import java.util.Arrays;
import java.util.List;

import static br.com.six2six.fixturefactory.Fixture.from;
import static io.jcervelin.fruitshop.cart.templates.CartResponseTemplates.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

public class BuyFruitWithOffersUseCaseTest extends UnitTestingSupport {

    @InjectMocks
    private BuyFruitWithOffersUseCase target;

    @Mock
    private BuyFruitSimpleUseCase buyFruitSimpleUseCase;

    private List<Offer> offers;

    @Before
    public void init() {
        offers = Arrays.asList(new Oranges3For2UseCase(),new BuyOneGetOneFreeOnApplesUseCase());
    }

    @Test
    public void shouldReturn5FruitsWithPriceAndWithOrangeAndAppleOffer() {

        // GIVEN a list of fruits
        final CartResponse cartResponseBeforeOffers = from(CartResponse.class)
                .gimme(CART_4_FRUITS_135);

        final CartResponse expected = from(CartResponse.class)
                .gimme(CART_5_FRUITS_110_PROMO_ORANGES);

        final List<String> stringFruits = Arrays.asList("Orange","Apple","Orange","Orange");

        Whitebox.setInternalState(target,"offers",offers);

        // WHEN 4 fruits are put in cart
        when(buyFruitSimpleUseCase.startUseCase(stringFruits)).thenReturn(cartResponseBeforeOffers);

        // THEN return fruits in cart with total price with plus one free apple and one orange
        final CartResponse result = target.startUseCase(stringFruits);

        assertThat(result.getFruits().size()).isEqualTo(5);
        assertThat(result.getTotal()).isEqualTo(expected.getTotal());
        assertThat(result.getFruits()).containsExactlyInAnyOrderElementsOf((expected.getFruits()));
    }

    @Test
    public void shouldReturn4FruitsWithPriceAndWithOrangeOfferAndWithAppleOffer() {

        // GIVEN a list of fruits
        final CartResponse cartResponseBeforeOffers = from(CartResponse.class)
                .gimme(CART_3_FRUITS_110);

        final CartResponse expected = from(CartResponse.class)
                .gimme(CART_4_FRUITS_110_PROMO_APPLES);

        final List<String> stringFruits = Arrays.asList("Orange","Apple","Orange");

        Whitebox.setInternalState(target,"offers",offers);

        // WHEN 3 fruits are put in cart
        when(buyFruitSimpleUseCase.startUseCase(stringFruits)).thenReturn(cartResponseBeforeOffers);

        // THEN return 4 fruits in cart with total price plus one free apple
        final CartResponse result = target.startUseCase(stringFruits);

        assertThat(result.getFruits().size()).isEqualTo(4);
        assertThat(result.getTotal()).isEqualTo(expected.getTotal());
        assertThat(result.getFruits()).containsExactlyInAnyOrderElementsOf((expected.getFruits()));
    }

    @Test
    public void shouldReturn0Fruits() {

        // GIVEN none
        final CartResponse cartResponseBeforeOffers = from(CartResponse.class)
                .gimme(EMPTY_CART);

        final CartResponse expected = from(CartResponse.class)
                .gimme(EMPTY_CART);

        final List<String> stringFruits = Arrays.asList("");

        Whitebox.setInternalState(target,"offers",offers);

        // WHEN put none in cart
        when(buyFruitSimpleUseCase.startUseCase(stringFruits)).thenReturn(cartResponseBeforeOffers);

        // THEN return empty list
        final CartResponse result = target.startUseCase(stringFruits);

        assertThat(result.getFruits().size()).isEqualTo(0);
        assertThat(result.getTotal()).isEqualTo(expected.getTotal());
        assertThat(result.getFruits()).containsExactlyInAnyOrderElementsOf((expected.getFruits()));
    }


}