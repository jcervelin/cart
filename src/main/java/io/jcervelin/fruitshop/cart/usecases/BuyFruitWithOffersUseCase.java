package io.jcervelin.fruitshop.cart.usecases;

import io.jcervelin.fruitshop.cart.domains.CartResponse;
import io.jcervelin.fruitshop.cart.usecases.offers.Offer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class BuyFruitWithOffersUseCase implements FruitUseCase {

    private final List<Offer> offers;

    private final FruitUseCase decorated;

    @Override
    public CartResponse startUseCase(Collection<String> fruits) {
        log.info(">> Fruits: {}", fruits);
        final CartResponse cartResponse = decorated.startUseCase(fruits);
        offers.forEach(offer -> offer.execute(cartResponse));
        log.info("<< CartResponse: {}", cartResponse);
        return cartResponse;
    }
}
