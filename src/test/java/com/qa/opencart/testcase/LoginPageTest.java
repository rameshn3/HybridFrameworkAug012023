package com.qa.opencart.testcase;

import org.testng.annotations.Test;

import com.qa.opencart.factory.TestBase;
import com.qa.opencart.pages.ForgotPasswordPage;
import com.qa.opencart.pages.HomePage;
import com.qa.opencart.pages.LoginPage;
import com.qa.opencart.pages.LogoutPage;
import com.qa.opencart.pages.MyAccountPage;
import com.qa.opencart.pages.RegistrationPage;
import com.qa.opencart.utils.Constants;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.AfterClass;

public class LoginPageTest extends TestBase{
	private Logger log=LogManager.getLogger(LoginPageTest.class.getName());
  @Test(description="TC_01_verifying the login page url",priority=1)
  public void verifyLoginPageUrlTest() {
	  log.info("Verify the login page url test");
	  Assert.assertTrue(loginPg.getLoginPageUrl().contains(Constants.LOGIN_PAGE_FRACTION_URL));
  }
  
  @Test(description="TC_02_verifying the login page title",priority=2)
  public void verifyLoginPageTitleTest() {
	  log.info("Verify login page title test");
	  Assert.assertEquals(loginPg.getTitle(), Constants.LOGIN_PAGE_TITLE);
  }
  
  
  @Test(description="TC_03_verifying the login page elements",priority=3)
  public void verifyLoginPageElementsTest() {
	  log.info("Verify login page elements ");
	  Assert.assertTrue(loginPg.isNewCustomerHeaderExists());
	  Assert.assertTrue(loginPg.isReturningCustomerHeaderExists());
	  Assert.assertTrue(loginPg.isloginBreadCrumbExists());
	 
  }
  
  @Test(description="TC_04_navigate to registration  from login page",priority=4)
  public void navigateToRegistrationPageTest() throws InterruptedException {
	  log.info("navigate to registration page");
	  loginPg.clickNewCustomerContinueBtn();
	  regPg.waitForPageLoad(2000);
	  log.info("verify the registration page title");
	  Assert.assertEquals(regPg.getTitle(),Constants.REGISTRATION_PAGE_TITLE);
	  log.info("click on browser back button in registration page");
	  regPg.navigateBrowserBack();
  }
  
  @Test(description="TC_05_navigate to forgot password page",priority=5)
  public void navigateToForgotPasswordPageTest() throws InterruptedException {
	  log.info("navigate to forgot password page");
	  loginPg.navigateToForgotPasswordPage();
	  forgotpwdPg.waitForPageLoad(2000);
	  log.info("verify the login page title");
	  Assert.assertEquals(forgotpwdPg.getTitle(),Constants.FORGOT_PWD_PAGE_TITLE);
	  log.info("click on browser back button in forgot password page");
	  loginPg.navigateBrowserBack();
  }
  @Test(description="TC_06_Happy path flow login to opencart app",priority=7)
  public void happyPathLoginTest() throws InterruptedException {
	  log.info("TC_06_Happy path flow login to opencart app");
	  loginPg.doLogin(rb.getString("username"), rb.getString("pwd"));
	  myaccountPg.waitForPageLoad(2000);
	  log.info("verify the My account page title");
	  Assert.assertEquals(myaccountPg.getTitle(),Constants.MY_ACCOUNT_PAGE_TITLE);
	  
  }
  
  @Test(description="TC_07_Empty Credentils login to opencart app",priority=6)
  public void emptyCredsLoginTest() throws InterruptedException {
	  log.info("TC_07_Empty Credentils login to opencart app");
	  loginPg.doLogin("", "");
	  loginPg.waitForPageLoad(2000);
	  log.info("verify the empty creds error message in login page");
	  Assert.assertTrue(loginPg.getEmptyCredsErrMessage().contains(Constants.LOGIN_NOMATCH_ERR_MSG));
	  
  }
  
  @Test(description="TC_08_logout test",dependsOnMethods={"happyPathLoginTest"})
  public void logoutTest() throws InterruptedException {
	  log.info("TC_08_logout from opencart app");
	  myaccountPg.clickLogoutLink();
	  logoutPg.waitForPageLoad(2000);
	  log.info("verify the logout page title");
	  Assert.assertEquals(logoutPg.getTitle(),Constants.ACCOUNT_LOGOUT_PAGE_TITLE);
	  log.info("click on continue button in the logout page");
	  logoutPg.clickContinueBtn();
	  homePg.waitForPageLoad(2000);
	  Assert.assertEquals(homePg.getTitle(), Constants.HOME_PAGE_TITLE);
  }
  
  @BeforeMethod
  public void beforeMethod() {
	  loginPg.waitForPageLoad(2000);
	 
  }



  @BeforeClass
  public void beforeClass() throws InterruptedException {
	  log.info("Initializing the page class objects");
	  homePg=new HomePage(driver);
	 regPg=new RegistrationPage(driver); 
	 loginPg=new LoginPage(driver);
	 logoutPg=new LogoutPage(driver);
	 myaccountPg=new MyAccountPage(driver);
	forgotpwdPg=new ForgotPasswordPage(driver);
	log.info("navigate to login page");
	homePg.navigateTologinPage();
  }

 
}
