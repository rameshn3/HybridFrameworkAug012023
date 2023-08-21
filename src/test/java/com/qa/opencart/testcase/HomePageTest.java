package com.qa.opencart.testcase;

import org.testng.annotations.Test;

import com.qa.opencart.factory.TestBase;
import com.qa.opencart.pages.HomePage;
import com.qa.opencart.pages.LoginPage;
import com.qa.opencart.pages.RegistrationPage;
import com.qa.opencart.utils.Constants;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.AfterClass;

public class HomePageTest extends TestBase{
	private Logger log=LogManager.getLogger(HomePageTest.class.getName());
  @Test(description="TC_01_verifying the opencartlogo",priority=1)
  public void verifyOpencartLogoTest() {
	  log.info("Verify the logo text");
	  Assert.assertTrue(homePg.isOpenCartLogoExists());
  }
  
  @Test(description="TC_02_verifying the opencart page title",priority=2)
  public void verifyOpencartPageTitleTest() {
	  log.info("Verify opencart page title test");
	  Assert.assertEquals(homePg.getTitle(), Constants.HOME_PAGE_TITLE);
  }
  
  
  @Test(description="TC_03_verifying the featured section cards count",priority=3)
  public void verifyFeaturedCardsCountTest() {
	  log.info("Verify the featured section cards count");
	  Assert.assertTrue(homePg.getfeaturedSectionCardCount()==4);
  }
  
  @Test(description="TC_04_navigate to registration page",priority=4)
  public void navigateToRegistrationPageTest() throws InterruptedException {
	  log.info("navigate to registration page");
	  homePg.navigateToRegisterPage();
	  regPg.waitForPageLoad(2000);
	  log.info("verify the registration page title");
	  Assert.assertEquals(regPg.getTitle(),Constants.REGISTRATION_PAGE_TITLE);
	  log.info("click on home breadcrumb icon in registration page");
	  regPg.clickOnBreadCrumbHomeIcon();
  }
  
  @Test(description="TC_05_navigate to login page",priority=5)
  public void navigateToLoginPageTest() throws InterruptedException {
	  log.info("navigate to login page");
	  homePg.navigateTologinPage();
	  loginPg.waitForPageLoad(2000);
	  log.info("verify the login page title");
	  Assert.assertEquals(loginPg.getTitle(),Constants.LOGIN_PAGE_TITLE);
	  log.info("click on home breadcrumb icon in login page");
	  loginPg.clickHomeIcon();
  }
  
  
  @BeforeMethod
  public void beforeMethod() {
	  homePg.waitForPageLoad(2000);
	 
  }

  @AfterMethod
  public void afterMethod() {
	  homePg.waitForPageLoad(2000);
	  Assert.assertEquals(homePg.getTitle(), Constants.HOME_PAGE_TITLE);  
  }

  @BeforeClass
  public void beforeClass() {
	  log.info("Initializing the page class objects");
	  homePg=new HomePage(driver);
		 regPg=new RegistrationPage(driver); 
	 loginPg=new LoginPage(driver);
  }

  @AfterClass
  public void afterClass() {
  }

}
