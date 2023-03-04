package ua.com.rozetka.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.com.rozetka.services.WebDriverHolder;

import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;

@Service
public class ProductPage extends AbstractPage<ProductPage> {

    private static final String PRODUCT_TITLE_CSS_SELECTOR = "h1.product__title";
    private static final String ADD_TO_CART_BUTTON_CSS_SELECTOR = "button.buy-button.button--medium";

    @Autowired
    private Function<AbstractPage<?>, ShoppingCartPage> shoppingCartPagePrototyper;


    public ProductPage(WebDriverHolder webDriverHolder) {
        super(webDriverHolder);
    }

    public ShoppingCartPage addToShoppingCart() {
        doWithRetry(() -> {
            // moving pointer outside the window in order to hide big product picture
            WebElement element = webDriverWait().until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(ADD_TO_CART_BUTTON_CSS_SELECTOR)));
            new Actions(getWebDriverHolder().getWebDriver())
                    .moveToElement(element)
                    .build()
                    .perform();
            element = webDriverWait().until(ExpectedConditions.elementToBeClickable(By.cssSelector(ADD_TO_CART_BUTTON_CSS_SELECTOR)));
            element.click();
        }, ElementClickInterceptedException.class);
        return shoppingCartPagePrototyper.apply(this);
    }


    public ProductPage checkProductNameHasText(String text) {
        WebElement element = webDriverWait().until(
                ExpectedConditions.visibilityOfElementLocated(By.cssSelector(PRODUCT_TITLE_CSS_SELECTOR))
        );
        assertThat(element.getText()).containsIgnoringCase(text);
        return self();
    }
}
