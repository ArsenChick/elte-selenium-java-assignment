package pages;

import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.stream.*;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class FormPage extends BasePage {
    private Random rand = new Random();
    private Properties configProperties;
    private final String controlFieldXPath = "//*[@class='card']//div[contains(@class, 'control')]";

    private final List<String> configTextInputKeys =
        List.of("firstName", "lastName", "phone", "address1", "address2", "state", "postalCode");
    private final List<String> configSelectKeys = List.of("countryCodeValue", "country");

    private By fieldTextInputsBy = this.getLocatorByInputType("text");
    private By fieldEmailInputBy = this.getLocatorByInputType("email");
    private By fieldDatePickerBy = this.getLocatorByInputType("date");

    private By fieldRadioBtnsBy = this.getLocatorByInputType("radio");
    private By fieldCheckboxBy = this.getLocatorByInputType("checkbox");
    private By fieldSelectBy = By.xpath(controlFieldXPath + "//select");

    private By submitBtnBy = this.getLocatorByInputType("submit");

    public FormPage(WebDriver driver, Properties config) {
        super(driver);
        this.driver.get("https://letcode.in/forms");
        this.configProperties = config;
    }
    
    public void fillTextFields() {
        List<WebElement> textFields = this.waitAndReturnAllElements(fieldTextInputsBy);
        List<String> inputValues = this.inputValuesFromKeysList(configTextInputKeys);
        
        if (textFields.size() == inputValues.size()) {
            for (int i = 0; i < inputValues.size(); i++) {
                textFields.get(i).sendKeys(inputValues.get(i));
            }
        }
    }

    public void fillSelectFields() {
        List<Select> selectFields = this.waitAndReturnAllElements(fieldSelectBy)
            .stream().map(select -> new Select(select)).collect(Collectors.toList());
        List<String> inputValues = this.inputValuesFromKeysList(configSelectKeys);
        
        if (selectFields.size() == inputValues.size()) {
            for (int i = 0; i < inputValues.size(); i++) {
                selectFields.get(i).selectByValue(inputValues.get(i));
            }
        }
    }

    public void fillEmailField() {
        fillSingularInstanceOfTypeField(fieldEmailInputBy, "forms.email");
    }

    public void fillDatepickerField() {
        fillSingularInstanceOfTypeField(fieldDatePickerBy, "forms.date");
    }

    public void pickRandomRadioOption() {
        List<WebElement> radioFields = this.waitAndReturnAllElements(fieldRadioBtnsBy);
        radioFields.get(rand.nextInt(3)).click();
    }

    public void markCheckbox() {
        this.waitAndReturnElement(fieldCheckboxBy).click();
    }

    public FormSubmitResult submitForm() {
        this.waitAndReturnElement(submitBtnBy).click();
        return new FormSubmitResult(this.driver);
    }

    public void scrollDown() {
        this.scrollDownUsingJSExecutor();
    }

    private void fillSingularInstanceOfTypeField(By locator, String configKey) {
        WebElement inputField = this.waitAndReturnElement(locator);
        inputField.clear();
        inputField.sendKeys(configProperties.getProperty(configKey));
    }

    private List<String> inputValuesFromKeysList(List<String> keys) {
        return keys.stream().map((key) -> "forms." + key)
            .map((key) -> configProperties.getProperty(key))
            .collect(Collectors.toList());
    }

    private By getLocatorByInputType(String type) {
        return By.xpath(String.format(controlFieldXPath + "//input[@type='%s']", type));
    }

}
