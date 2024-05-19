package pages;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;


class BasePage {
    protected WebDriver driver;
    protected WebDriverWait wait;
    
    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }
    
    protected WebElement waitAndReturnElement(By locator) {
        this.wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        return this.driver.findElement(locator);
    }

    protected List<WebElement> waitAndReturnAllElements(By locator) {
        this.wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
        return this.driver.findElements(locator);
    }

    protected WebElement waitForElementToBeClickable(WebElement element) {
        this.wait.until(ExpectedConditions.elementToBeClickable(element));
        return element;
    }

    protected void clickOnElementUsingJSExecutor(WebElement element) {
        ((JavascriptExecutor) this.driver).executeScript("arguments[0].click();", element);
    }

    protected void scrollDownUsingJSExecutor() {
        ((JavascriptExecutor) this.driver)
            .executeScript("window.scrollTo(0, document.body.scrollHeight)");
    }
    
    public String getBodyText() {
        WebElement bodyElement = this.waitAndReturnElement(By.tagName("body"));
        return bodyElement.getText();
    }
    
}
