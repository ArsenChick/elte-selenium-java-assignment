package pages;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;


public class HomePage extends BasePage {
    private By logoutButtonBy = By.xpath("//*[contains(@class, 'navbar-end')]" +
        "//*[contains(@class, 'button') and contains(@class, 'is-primary')]");
    private By loginButtonBy = By.xpath("//*[contains(@class, 'navbar-end')]" +
        "//*[contains(@class, 'button') and @href='/signin']");
    private By notificationBy = By.xpath("//*[starts-with(@id, 'toast-container')]" +
        "//*[contains(@class, 'message')]");

    public HomePage(WebDriver driver) {
        super(driver);
    }
    
    public String getToastNotificationText() {
        return this.waitAndReturnElement(notificationBy).getText();
    }

    public Boolean isLoginButtonPresent() {
        try {
            this.waitAndReturnElement(loginButtonBy);
        } catch (TimeoutException ex) {
            return false;
        }
        return true;
    }

    public HomePage logOut() {
        WebElement logoutButton;
        logoutButton = this.waitAndReturnElement(logoutButtonBy);
        logoutButton = this.waitForElementToBeClickable(logoutButton);

        // Needs to be here as the toast notification intercepts the click
        this.clickOnElementUsingJSExecutor(logoutButton);
        return new HomePage(this.driver);
    }
    
}
