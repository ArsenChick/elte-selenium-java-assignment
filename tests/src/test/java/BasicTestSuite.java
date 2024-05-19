import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static org.junit.jupiter.api.Assertions.assertTrue;

import pages.*;
import java.util.*; 


public class BasicTestSuite extends BaseTestSuite {

    @Test
    public void testLogin() {
        HomePage homePage = performTestLogin();
        Assertions.assertTrue(homePage.getToastNotificationText()
            .contains(configProperties.getProperty("username")));
    }

    @ParameterizedTest
    @CsvSource({
        "https://letcode.in/edit, Interact with Input fields, Input",
        "https://letcode.in/buttons, Interact with Button fields, Button",
        "https://letcode.in/draggable, Draggable - LetCode, Drag",
        "https://letcode.in/dropable, Droppable - LetCode, Drop",
    })
    public void testStaticPageLoads(String url, String pageTitle, String headerText) {
        StaticPage staticPage = new StaticPage(this.driver, url);
        Assertions.assertAll(
            () -> assertTrue(staticPage.getPageTitle().equals(pageTitle)),
            () -> assertTrue(staticPage.getHeaderElementText().equals(headerText))
        );
    }

    @Test
    public void testFormSending() {
        performTestLogin();
        FormPage formPage = new FormPage(this.driver,
            new Properties(BaseTestSuite.configProperties));
        
        // Fill visible fields
        formPage.fillTextFields();
        formPage.fillEmailField();
        formPage.fillDatepickerField();
        formPage.fillSelectFields();

        // Avoid ads not to get ElementClickInterceptedException
        formPage.scrollDown();
        formPage.pickRandomRadioOption();
        formPage.markCheckbox();
        FormSubmitResult formSendResult = formPage.submitForm();

        Assertions.assertTrue(formSendResult.checkIfPageWasReset());
    }

    @Test
    public void testLogout() {
        HomePage homePage = performTestLogin();
        homePage.logOut();
        
        Assertions.assertAll(
            () -> assertTrue(homePage.getToastNotificationText().contains("Bye!")),
            () -> assertTrue(homePage.isLoginButtonPresent())
        );
    }

    private HomePage performTestLogin() {
        LoginPage loginPage = new LoginPage(this.driver);
        HomePage homePage = loginPage.logIn(
            configProperties.getProperty("userEmail"),
            configProperties.getProperty("password"));
        return homePage;
    }

}
