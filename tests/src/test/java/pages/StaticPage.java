package pages;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;


public class StaticPage extends BasePage {
    private By headerTagBy = By.xpath("//h1[contains(@class, 'title')]");

    public StaticPage(WebDriver driver, String url) {
        super(driver);
        this.driver.get(url);
    }

    public String getPageTitle() {
        return this.driver.getTitle();
    }

    public String getHeaderElementText() {
        return this.waitAndReturnElement(headerTagBy).getText();
    }
}
