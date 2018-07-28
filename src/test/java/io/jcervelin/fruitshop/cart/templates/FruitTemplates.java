package io.jcervelin.fruitshop.cart.templates;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;
import io.jcervelin.fruitshop.cart.domains.Fruit;

public class FruitTemplates implements TemplateLoader {
    public static final String ORANGE = "Orange";
    public static final String APPLE = "Apple";

    @Override
    public void load() {
        Fixture.of(Fruit.class).addTemplate(ORANGE, new Rule() {{
            add("fruit", "Orange");
            add("price", 25d);
        }});

        Fixture.of(Fruit.class).addTemplate(APPLE, new Rule() {{
            add("fruit", "Apple");
            add("price", 60d);
        }});


    }

}

