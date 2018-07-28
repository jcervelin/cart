package io.jcervelin.fruitshop.cart.gateways.http;

import io.jcervelin.fruitshop.cart.domains.CartResponse;
import io.jcervelin.fruitshop.cart.usecases.BuyFruitUsecase;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static io.jcervelin.fruitshop.cart.domains.Endpoints.CART;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(CART)
@Api(value = CART, description = "Shopping Cart")
@RequiredArgsConstructor
@Slf4j
public class CartController {
    @Autowired
    private final BuyFruitUsecase useCase;

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    @ApiOperation("Fruits checkout")
    public ResponseEntity<CartResponse> cart(@RequestParam(value="fruit") final List<String> fruits) {
        log.info("Starting checkout with: {}", fruits);
        final CartResponse cartResponse = useCase.startUseCase(fruits);
        return new ResponseEntity<>(cartResponse, HttpStatus.OK);
    }

}
