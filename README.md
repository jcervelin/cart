STEP 1 and STEP 2 are in package io.jcervelin.fruitshop.cart.usecases

In class CartApplication I put logic of the offers decorating the previous logic of the STEP 1

Those usecases are with 100% Test coverage certified by jacoco lib

I put swagger to make more easy to test by browser.

Test:
mvn test

Swagger:
mvn clean install

java -jar target/cart-1.jar

http://localhost:8080
