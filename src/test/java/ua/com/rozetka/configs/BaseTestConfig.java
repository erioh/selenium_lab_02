package ua.com.rozetka.configs;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import ua.com.rozetka.pages.AbstractPage;
import ua.com.rozetka.pages.ShoppingCartPage;
import ua.com.rozetka.services.WebDriverHolder;

import java.util.function.Function;

@Configuration
@ComponentScan("ua.com.rozetka")
public class BaseTestConfig {

    @Bean
    public Function<AbstractPage<?>, ShoppingCartPage> shoppingCartPagePrototyper(WebDriverHolder webDriverHolder) {
        return previousPage -> new ShoppingCartPage(webDriverHolder, previousPage);
    }
}
