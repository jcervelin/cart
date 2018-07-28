package io.jcervelin.fruitshop.cart.templates;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;
import io.jcervelin.fruitshop.cart.domains.CartResponse;
import io.jcervelin.fruitshop.cart.domains.Fruit;

import java.util.Collections;

import static io.jcervelin.fruitshop.cart.templates.FruitTemplates.APPLE;
import static io.jcervelin.fruitshop.cart.templates.FruitTemplates.ORANGE;

public class CartResponseTemplates implements TemplateLoader {
    public static final String CART_4_FRUITS_135 = "Cart with 4 fruits and price 135";
    public static final String CART_3_FRUITS_110 = "Cart with 3 fruits and price 110";
    public static final String EMPTY_CART = "Empty cart";

    @Override
    public void load() {
        Fixture.of(CartResponse.class).addTemplate(CART_4_FRUITS_135, new Rule() {{
            add("total", 135d);
            add("fruits", has(4).of(Fruit.class, ORANGE, APPLE, ORANGE, ORANGE));
        }});

        Fixture.of(CartResponse.class).addTemplate(CART_3_FRUITS_110, new Rule() {{
            add("total", 110d);
            add("fruits", has(3).of(Fruit.class, APPLE, ORANGE, ORANGE));
        }});

        Fixture.of(CartResponse.class).addTemplate(EMPTY_CART, new Rule() {{
            add("total", 0d);
            add("fruits", Collections.emptyList());
        }});

    }

}
