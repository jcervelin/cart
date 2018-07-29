package io.jcervelin.fruitshop.cart.usecases.offers;

import io.jcervelin.fruitshop.cart.config.UnitTestingSupport;
import io.jcervelin.fruitshop.cart.domains.CartResponse;
import org.junit.Test;
import org.mockito.InjectMocks;

import static br.com.six2six.fixturefactory.Fixture.from;
import static io.jcervelin.fruitshop.cart.templates.CartResponseTemplates.*;
import static org.assertj.core.api.Assertions.assertThat;

public class Oranges3For2UseCaseTest extends UnitTestingSupport {

    @InjectMocks
    private Oranges3For2UseCase target;

    @Test
    public void shouldGiveDiscountForTheOrange() {
        // GIVEN 3 oranges and 1 apple
        final CartResponse cartResponseBeforeOffers = from(CartResponse.class)
                .gimme(CART_4_FRUITS_135);

        final CartResponse expected = from(CartResponse.class)
                .gimme(CART_4_FRUITS_110_WITH_ORANGE_DISCOUNT);

        //WHEN orange offer is applied
        target.execute(cartResponseBeforeOffers);

        //THEN a discount of 25p is applied over total
        assertThat(cartResponseBeforeOffers.getFruits().size()).isEqualTo(4);
        assertThat(cartResponseBeforeOffers.getTotal()).isEqualTo(expected.getTotal());
        assertThat(cartResponseBeforeOffers.getFruits()).containsExactlyInAnyOrderElementsOf((expected.getFruits()));
    }

    @Test
    public void shouldNotGiveDiscountForTheOrange() {
        // GIVEN 2 oranges and 1 apple
        final CartResponse cartResponseBeforeOffers = from(CartResponse.class)
                .gimme(CART_3_FRUITS_110);

        final CartResponse expected = from(CartResponse.class)
                .gimme(CART_3_FRUITS_110);

        //WHEN orange offer is applied
        target.execute(cartResponseBeforeOffers);

        //THEN nothing happens, because there is only 2 oranges
        assertThat(cartResponseBeforeOffers.getFruits().size()).isEqualTo(3);
        assertThat(cartResponseBeforeOffers.getTotal()).isEqualTo(expected.getTotal());
        assertThat(cartResponseBeforeOffers.getFruits()).containsExactlyInAnyOrderElementsOf((expected.getFruits()));
    }
}