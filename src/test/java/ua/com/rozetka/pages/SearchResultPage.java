package ua.com.rozetka.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.com.rozetka.services.WebDriverHolder;

import java.util.List;

@Service
public class SearchResultPage extends AbstractPage<SearchResultPage> {
    public static final String SEARCH_RESULTS_XPATH = "//div[@class='goods-tile__inner']/a[contains(@class, 'goods-tile__heading')]";
    @Autowired
    private ProductPage productPage;

    public SearchResultPage(WebDriverHolder webDriverHolder) {
        super(webDriverHolder);
    }

    public ProductPage selectFirstProduct() {
        doWithRetry(() -> {
            List<WebElement> webElements = webDriverWait()
                    .until(
                            ExpectedConditions.numberOfElementsToBeMoreThan(By.xpath(SEARCH_RESULTS_XPATH), 0));
            webElements.iterator().next().click();
        }, StaleElementReferenceException.class);
        return productPage;
    }

}
