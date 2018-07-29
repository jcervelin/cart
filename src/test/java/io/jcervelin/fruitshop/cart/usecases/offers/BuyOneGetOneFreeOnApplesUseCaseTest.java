package io.jcervelin.fruitshop.cart.usecases.offers;

import io.jcervelin.fruitshop.cart.config.UnitTestingSupport;
import io.jcervelin.fruitshop.cart.domains.CartResponse;
import org.junit.Test;
import org.mockito.InjectMocks;

import static br.com.six2six.fixturefactory.Fixture.from;
import static io.jcervelin.fruitshop.cart.templates.CartResponseTemplates.*;
import static org.assertj.core.api.Assertions.assertThat;

public class BuyOneGetOneFreeOnApplesUseCaseTest extends UnitTestingSupport {

    @InjectMocks
    private BuyOneGetOneFreeOnApplesUseCase target;

    @Test
    public void shouldGiveDiscountForTheApple() {
        // GIVEN 2 oranges and 1 apple
        final CartResponse cartResponseBeforeOffers = from(CartResponse.class)
                .gimme(CART_3_FRUITS_110);

        final CartResponse expected = from(CartResponse.class)
                .gimme(CART_3_FRUITS_110_PLUS_APPLE);

        //WHEN apple offer is applied
        target.execute(cartResponseBeforeOffers);

        //THEN one more apple is added in cart without any cost
        assertThat(cartResponseBeforeOffers.getFruits().size()).isEqualTo(4);
        assertThat(cartResponseBeforeOffers.getTotal()).isEqualTo(110L);
        assertThat(cartResponseBeforeOffers.getFruits()).containsExactlyInAnyOrderElementsOf((expected.getFruits()));

    }

    @Test
    public void shouldNotGiveDiscountForTheApple() {
        // GIVEN nothing
        final CartResponse cartResponseBeforeOffers = from(CartResponse.class)
                .gimme(EMPTY_CART);

        final CartResponse expected = from(CartResponse.class)
                .gimme(EMPTY_CART);

        //WHEN orange offer is applied
        target.execute(cartResponseBeforeOffers);

        //THEN nothing happens, because the cart is empty
        assertThat(cartResponseBeforeOffers.getFruits().size()).isEqualTo(0);
        assertThat(cartResponseBeforeOffers.getTotal()).isEqualTo(expected.getTotal());
        assertThat(cartResponseBeforeOffers.getFruits()).containsExactlyInAnyOrderElementsOf((expected.getFruits()));
    }
}