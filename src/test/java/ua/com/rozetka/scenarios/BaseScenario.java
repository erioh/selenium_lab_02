package ua.com.rozetka.scenarios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.AfterMethod;
import ua.com.rozetka.configs.BaseTestConfig;
import ua.com.rozetka.services.WebDriverHolder;

@ContextConfiguration(classes = BaseTestConfig.class)
public abstract class BaseScenario extends AbstractTestNGSpringContextTests {
    @Autowired
    private WebDriverHolder webDriverHolder;


    @AfterMethod
    public void tearDown() {
        webDriverHolder.quit();
    }
}
