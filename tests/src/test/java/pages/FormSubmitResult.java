package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class FormSubmitResult extends BasePage {
    private By checkboxBy = By.xpath("//div[contains(@class, 'control')]//input[@type='checkbox']");

    public FormSubmitResult(WebDriver driver) {
        super(driver);
    }

    public Boolean checkIfPageWasReset() {
        return !this.waitAndReturnElement(checkboxBy).isSelected();
    }
}
