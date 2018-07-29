package io.jcervelin.fruitshop.cart.config;

import io.jcervelin.fruitshop.cart.gateways.mongo.FruitGateway;
import io.jcervelin.fruitshop.cart.usecases.BuyFruitSimpleUseCase;
import io.jcervelin.fruitshop.cart.usecases.BuyFruitUsecase;
import io.jcervelin.fruitshop.cart.usecases.BuyFruitWithOffersUseCase;
import io.jcervelin.fruitshop.cart.usecases.FruitUseCase;
import io.jcervelin.fruitshop.cart.usecases.offers.Offer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.List;

@Configuration
public class DecoratorConfig {

    @Bean
    @Primary
    public FruitUseCase buyFruitUseCase(@Autowired final FruitGateway fruitGateway) {
        return new BuyFruitUsecase(fruitGateway);
    }

    @Bean
    public FruitUseCase buyFruitSimpleUseCase() {
        return new BuyFruitSimpleUseCase();
    }

    @Bean
    public FruitUseCase buyFruitWithOffersUseCase(@Autowired final List<Offer> offers, @Autowired final FruitUseCase useCase) {
        return new BuyFruitWithOffersUseCase(offers,useCase);
    }
}