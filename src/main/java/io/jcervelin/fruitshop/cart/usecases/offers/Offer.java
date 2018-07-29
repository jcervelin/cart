package io.jcervelin.fruitshop.cart.usecases.offers;

import io.jcervelin.fruitshop.cart.domains.CartResponse;

public interface Offer {

    void execute(final CartResponse response);
}
