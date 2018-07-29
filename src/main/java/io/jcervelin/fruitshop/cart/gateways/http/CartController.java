package io.jcervelin.fruitshop.cart.gateways.http;

import io.jcervelin.fruitshop.cart.domains.CartResponse;
import io.jcervelin.fruitshop.cart.usecases.FruitUseCase;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

import static io.jcervelin.fruitshop.cart.domains.Endpoints.CART;
import static io.jcervelin.fruitshop.cart.domains.Endpoints.OFFERS;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(CART)
@Api(value = CART, description = "Shopping Cart")
@Slf4j
@RequiredArgsConstructor
public class CartController {

    @Resource(name="buyFruitUseCase")
    private final FruitUseCase useCase;

    @Resource(name="buyFruitWithOffersUseCase")
    private final FruitUseCase useCaseWithOffers;

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    @ApiOperation("Fruits checkout")
    public ResponseEntity<CartResponse> cart(@RequestParam(value="fruit") final List<String> fruits) {
        log.info("Starting checkout with: {}", fruits);
        final CartResponse cartResponse = useCase.startUseCase(fruits);
        log.info("Ending checkout with: {}", cartResponse);
        return new ResponseEntity<>(cartResponse, HttpStatus.OK);
    }

    @GetMapping(value = OFFERS, produces = APPLICATION_JSON_VALUE)
    @ApiOperation("Fruits checkout with offers")
    public ResponseEntity<CartResponse> cartWithOffers(
            @RequestParam(value="fruit") final List<String> fruits) {
        log.info("Starting checkout with: {}", fruits);
        final CartResponse cartResponse = useCaseWithOffers.startUseCase(fruits);
        log.info("Ending checkout with: {}", cartResponse);
        return new ResponseEntity<>(cartResponse, HttpStatus.OK);
    }

}
