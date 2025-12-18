package tests;

import pages.LoginPage;
import utils.DataReader;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.util.List;
import java.util.Map;

public class LoginTest extends BaseTest {

    @Test
    public void testSuccessfulLogin() {
        List<Map<String, String>> validData = DataReader.getTestData("loginData.json", "validCredentials");
        Map<String, String> credentials = validData.get(0);

        LoginPage loginPage = new LoginPage(driver);
        loginPage.navigate();
        loginPage.login(credentials.get("username"), credentials.get("password"));

        Assert.assertTrue(loginPage.isLoginSuccessful(), "Login should succeed with valid credentials");
    }

    @Test
    public void testFailedLogin() {
        List<Map<String, String>> invalidData = DataReader.getTestData("loginData.json", "invalidCredentials");
        Map<String, String> credentials = invalidData.get(0);

        LoginPage loginPage = new LoginPage(driver);
        loginPage.navigate();
        loginPage.login(credentials.get("username"), credentials.get("password"));

        Assert.assertTrue(loginPage.isLoginFailed(), "Login should fail with invalid credentials");
        Assert.assertTrue(loginPage.getErrorMessage().contains(credentials.get("expectedError")),
                "Error message should match expected");
    }
}
