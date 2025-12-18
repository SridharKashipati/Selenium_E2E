package pages;

import utils.Config;
import utils.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoginPage {

	private static final Logger log = LoggerFactory.getLogger(LoginPage.class);

	private WebDriver driver;
	private WaitUtils wait;

	private By usernameInput = By.id("username");
	private By passwordInput = By.id("password");
	private By loginButton = By.cssSelector("button[type='submit']");
	private By successMessage = By.cssSelector(".flash.success");
	private By errorMessage = By.cssSelector(".flash.error");

	public LoginPage(WebDriver driver) {
		this.driver = driver;
		this.wait = new WaitUtils(driver);
	}

	public void navigate() {
		log.info("Navigating to login page: {}", Config.BASE_URL + "/login");
		driver.get(Config.BASE_URL + "/login");
	}

	public void login(String username, String password) {
		log.info("Attempting login with username: {}", username);
		wait.waitForVisible(usernameInput).sendKeys(username);
		driver.findElement(passwordInput).sendKeys(password);
		driver.findElement(loginButton).click();
		log.info("Login form submitted");
	}

	public boolean isLoginSuccessful() {
		try {
			wait.waitForVisible(successMessage);
			log.info("Login successful");
			return true;
		} catch (Exception e) {
			log.warn("Login success check failed");
			return false;
		}
	}

	public boolean isLoginFailed() {
		try {
			wait.waitForVisible(errorMessage);
			log.info("Login failed as expected");
			return true;
		} catch (Exception e) {
			log.warn("Login failure check failed");
			return false;
		}
	}

	public String getErrorMessage() {
		String message = wait.waitForVisible(errorMessage).getText();
		log.info("Error message: {}", message);
		return message;
	}
}
