package ua.com.rozetka.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import ua.com.rozetka.services.WebDriverHolder;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;

public abstract class AbstractPage<T extends AbstractPage<T>> {
    private static final Integer MAX_RETRY = 10;
    private static final String NUMBER_OF_ADDED_PRODUCTS = "rz-icon-badge > span.ng-star-inserted.badge--green";
    private final WebDriverHolder webDriverHolder;

    protected AbstractPage(WebDriverHolder webDriverHolder) {
        this.webDriverHolder = webDriverHolder;
    }


    public T checkThatNumberOfAddedProductsIs(Integer count) {
        WebElement element = webDriverWait()
                .until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(NUMBER_OF_ADDED_PRODUCTS)));
        assertThat(element.getText()).isEqualTo(count.toString());
        return self();
    }

    protected WebDriverHolder getWebDriverHolder() {
        return webDriverHolder;
    }

    protected T self() {
        return (T) this;
    }

    protected WebDriverWait webDriverWait() {
        return new WebDriverWait(getWebDriverHolder().getWebDriver(), Duration.ofSeconds(20L));
    }

    protected <E extends RuntimeException> void doWithRetry(Runnable action, Class<E> expectedException) {
        doWithRetry(action, expectedException, 0);
    }

    private <E extends RuntimeException> void doWithRetry(Runnable action, Class<E> expectedException, Integer retryCount) {
        try {
            action.run();
        } catch (RuntimeException exception) {
            if (retryCount > MAX_RETRY || !exception.getClass().equals(expectedException)) {
                throw exception;
            }
            sleep();
            doWithRetry(action, expectedException, retryCount + 1);
        }
    }

    private void sleep() {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
