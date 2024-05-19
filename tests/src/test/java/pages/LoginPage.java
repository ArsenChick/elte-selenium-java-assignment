package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage extends BasePage {
    private By headerTagBy = By.tagName("h1");

    private By emailFieldBy = this.getInoutLocatorByName("email");
    private By passwordFieldBy = this.getInoutLocatorByName("password");
    private By loginButtonBy = this.getButtonLocatorByText("LOGIN");

    private By forgotPasswordBtnBy = By.xpath("//*[@class='field']//button[contains(@class, 'is-link')]");
    private By resetEmailInputBy = By.xpath("//*[@name='fgEmail']//self::input");
    private By resetPasswordBy = this.getButtonLocatorByText("RESET");
    private By resetNotificationBy = By.xpath("//*[contains(@class, 'notification')]");

    public LoginPage(WebDriver driver) {
        super(driver);
        this.driver.get("https://letcode.in/signin");
    }
    
    public HomePage logIn(String email, String password) {
        this.waitAndReturnElement(emailFieldBy).sendKeys(email);
        this.waitAndReturnElement(passwordFieldBy).sendKeys(password);
        this.waitAndReturnElement(loginButtonBy).click();

        this.waitAndReturnElement(headerTagBy);
        return new HomePage(this.driver);
    }

    public void sendResetPasswordEmail(String email) {
        this.waitAndReturnElement(forgotPasswordBtnBy).click();
        this.waitAndReturnElement(resetEmailInputBy).sendKeys(email);
        this.waitAndReturnElement(resetPasswordBy).click();
        this.waitAndReturnElement(resetNotificationBy);
    }

    private By getButtonLocatorByText(String text) {
        return By.xpath(String.format("//*[contains(@class, 'field')]//button[text()='%s']", text));
    }

    private By getInoutLocatorByName(String name) {
        return By.xpath(String.format("//*[@class='field']//input[@name='%s']", name));
    }
}
