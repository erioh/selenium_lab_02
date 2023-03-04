package ua.com.rozetka.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.com.rozetka.services.WebDriverHolder;

@Service
public class MainPage extends AbstractPage<MainPage> {

    private static final String MAIN_URL = "https://rozetka.com.ua/ua/";
    private static final String SEARCH_FIELD_XPATH = "//form[contains(@class, 'search-form')]//input[@name='search']";
    private static final String SEARCH_FORM_SUBMIT_XPATH = "//form[contains(@class, 'search-form')]//button[contains(@class, 'search-form__submit')]";

    @Autowired
    private SearchResultPage searchResultPage;

    public MainPage(WebDriverHolder webDriverHolder) {
        super(webDriverHolder);
    }

    public MainPage open() {
        getWebDriverHolder().getWebDriver().get(MAIN_URL);
        return self();
    }

    public MainPage typeInSearchField(String text) {
        WebElement element = webDriverWait()
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath(SEARCH_FIELD_XPATH)));
        element.clear();
        element.sendKeys(text);
        return self();
    }

    public SearchResultPage clickSearchButton() {
        WebElement element = webDriverWait()
                .until(ExpectedConditions.elementToBeClickable(By.xpath(SEARCH_FORM_SUBMIT_XPATH)));
        element.click();
        return searchResultPage;
    }
}
