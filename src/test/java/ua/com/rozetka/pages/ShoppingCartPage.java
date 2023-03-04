package ua.com.rozetka.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import ua.com.rozetka.services.WebDriverHolder;

public class ShoppingCartPage extends AbstractPage<ShoppingCartPage> {
    public static final String CONTINUE_SHOPPING_CSS_SELECTOR = "button.cart-footer__continue";
    private final AbstractPage<?> previousPage;

    public ShoppingCartPage(WebDriverHolder webDriverHolder, AbstractPage<?> previousPage) {
        super(webDriverHolder);
        this.previousPage = previousPage;
    }

    public <T extends AbstractPage<T>> T clickProceedShopping() {
        webDriverWait()
                .until(ExpectedConditions.elementToBeClickable(By.cssSelector(CONTINUE_SHOPPING_CSS_SELECTOR))).click();
        return (T) previousPage;
    }
}
