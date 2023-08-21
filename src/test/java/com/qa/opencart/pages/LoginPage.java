package com.qa.opencart.pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.qa.opencart.utils.Constants;
import com.qa.opencart.utils.WebDriverUtils;

public class LoginPage extends WebDriverUtils{

	private static Logger log=LogManager.getLogger(LoginPage.class.getName());
	
	public LoginPage(WebDriver driver) {
		super(driver);
	
	}

	@FindBy(xpath="//ul[@class='breadcrumb']/li[3]/a[text()='Login']")
	private WebElement loginBreadCrumb;
	
	@FindBy(css="div.well>h2")
	private WebElement newCustomerHeader;
	
	@FindBy(css="a.btn.btn-primary")
	private WebElement newCustomerContinueBtn;
	
	@FindBy(xpath="//h2[normalize-space()='Returning Customer']")
	private WebElement returningCustomerHeader;
	
	@FindBy(id="input-email")
	private WebElement emailAddressEditbox;
	
	@FindBy(name="password")
	private WebElement passwordEditbox;
	
	@FindBy(xpath="//*[@type='submit' and @value='Login']")
	private WebElement loginBtn;
	
	@FindBy(linkText="Forgotten Password")
	private WebElement forgottenPasswordLink;

	@FindBy(xpath="//*[contains(@class,'fa fa-home')]")
	private WebElement loginHomeIcon;
	
	@FindBy(css="div.alert.alert-danger")
	private WebElement emptyCredsErrMsg;
	
	public String getEmptyCredsErrMessage() {
		return emptyCredsErrMsg.getText();
	}
	
	public void clickHomeIcon() throws InterruptedException {
		log.info("click on breadcrumb homeicon");
		click(loginHomeIcon);
	}
	
	public String getLoginPageUrl() {
		return waitForUrlContains(Constants.LOGIN_PAGE_FRACTION_URL);
	}
	
	public void doLogin(String email,String pwd) throws InterruptedException {
		log.info("Performing the login operation");
		sendData(emailAddressEditbox, email);
		sendData(passwordEditbox,pwd);
		log.info("click on login button");
		click(loginBtn);
	}
	
	public void navigateToForgotPasswordPage() throws InterruptedException {
		log.info("click on forgot password link");
		click(forgottenPasswordLink);
	}
	
	public void clickNewCustomerContinueBtn() throws InterruptedException {
		log.info("click on NewCustomerContinueBtn");
		click(newCustomerContinueBtn);
	}
	
	public boolean isNewCustomerHeaderExists() {
		return isDisplayed(newCustomerHeader);
	}
	public boolean isReturningCustomerHeaderExists() {
		return isDisplayed(returningCustomerHeader);
	}
	public boolean isloginBreadCrumbExists() {
		return isDisplayed(loginBreadCrumb);
	}
}
