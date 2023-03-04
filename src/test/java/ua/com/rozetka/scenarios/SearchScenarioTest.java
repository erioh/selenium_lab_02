package ua.com.rozetka.scenarios;

import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;
import ua.com.rozetka.pages.MainPage;
import ua.com.rozetka.pages.ProductPage;

public class SearchScenarioTest extends BaseScenario {

    @Autowired
    private MainPage mainPage;

    @Test
    public void should_add_macbook_to_the_shopping_cart() {
        mainPage.open()
                .typeInSearchField("Macbook")
                .clickSearchButton()
                .selectFirstProduct()
                .checkProductNameHasText("Macbook")
                .addToShoppingCart()
                .<ProductPage>clickProceedShopping()
                .checkThatNumberOfAddedProductsIs(1);
    }

    @Test
    public void should_add_iphone_to_the_shopping_cart() {
        mainPage.open()
                .typeInSearchField("Iphone")
                .clickSearchButton()
                .selectFirstProduct()
                .checkProductNameHasText("Iphone")
                .addToShoppingCart()
                .<ProductPage>clickProceedShopping()
                .checkThatNumberOfAddedProductsIs(1);
    }
}
