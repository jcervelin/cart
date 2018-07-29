package io.jcervelin.fruitshop.cart.gateways.mongo;

import io.jcervelin.fruitshop.cart.domains.Fruit;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface FruitGateway extends MongoRepository<Fruit,String> {

}
