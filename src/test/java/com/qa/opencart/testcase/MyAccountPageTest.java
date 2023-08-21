package com.qa.opencart.testcase;

import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.qa.opencart.factory.TestBase;
import com.qa.opencart.pages.ForgotPasswordPage;
import com.qa.opencart.pages.HomePage;
import com.qa.opencart.pages.LoginPage;
import com.qa.opencart.pages.LogoutPage;
import com.qa.opencart.pages.MyAccountPage;
import com.qa.opencart.pages.RegistrationPage;
import com.qa.opencart.pages.ResultsPage;
import com.qa.opencart.utils.Constants;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;

import java.io.IOException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.AfterClass;

public class MyAccountPageTest extends TestBase{
	private Logger log=LogManager.getLogger(MyAccountPageTest.class.getName());
  
	
	 @BeforeClass
	  public void beforeClass() throws InterruptedException {
		  log.info("Initilizing the page class objects");
		  homePg=new HomePage(driver);
		  loginPg=new LoginPage(driver);
		  myaccountPg=new MyAccountPage(driver);
		  logoutPg=new LogoutPage(driver);
		  resultPg=new ResultsPage(driver);
		  homePg.navigateTologinPage();
		  loginPg.doLogin(rb.getString("username"), rb.getString("pwd"));
	  }
	
  @BeforeMethod
  public void beforeMethod() {
	  myaccountPg.waitForPageLoad(2000);
  }
 

  @Test(description="TC_01_VerifyingMyAccountPage url",priority=1)
  public void verifyMyAccountPageUrlTest() {
	  log.info("verify MyAccountPage URL test");
	  Assert.assertTrue(myaccountPg.getMyAccountPageUrl().contains(Constants.ACC_PAGE_FRACTION_URL));
  }

  @Test(description="TC_02_Verifying the MyAccount Page Title",priority=2)
  public void verifyMyAccountPageTitleTest() {
	  log.info("Verifying the MyAccount Page Title");
	  Assert.assertEquals(myaccountPg.getTitle(),Constants.MY_ACCOUNT_PAGE_TITLE);
  }
  
  @Test(description="TC_03_verify My Account Page Elements",priority=3)
  public void verifyMyAccountPageElementsTest() throws InterruptedException {
	  log.info("TC_03_verify MyAccountPageElements in login Page");
	  
	  Assert.assertTrue(myaccountPg.isSearchExist());
	  Assert.assertTrue(myaccountPg.isLogoutExist());
	  Actions act=new Actions(driver);
	  act.sendKeys(Keys.ESCAPE).perform();
  }
  
  @Test(description="TC_04_verify My account options list",priority=4)
  public void verifyMyAccountMenuOptionsTest() throws InterruptedException {
	  log.info("TC_04_verify My account Menu Options test");
	  
	  Assert.assertEquals(myaccountPg.getMyAccountMenuOptionList(),Constants.EXPECTED_MYACC_MENU_OPTS_LIST);
	 
  }
  
  @Test(description="TC_05_Verify My Account headers list ",priority=5)
  public void verifyMyAccountHeadersListTest() throws InterruptedException {
	 
	  Assert.assertEquals(myaccountPg.getMyAccountHeaderList(),Constants.EXPECTED_MYACC_HEADER_LIST);
	
  }
  
  
  @Test(description="TC_06_verify the broken links in my account page",priority=6)
  public void verifyBrokenLinksInMyAccountPageTest() throws InterruptedException, IOException {
	  log.info("TC_06_verify the broken links in my account page");
	  List<WebElement>pageLinksList=driver.findElements(By.tagName("a"));
	  for(WebElement el:pageLinksList) {
		  String eleUrls=el.getAttribute("href");
		  verifyUrls(eleUrls);
	  }
	  Actions act=new Actions(driver);
	  act.sendKeys(Keys.ESCAPE).perform();
  }
  
  @Test(description="TC_07_Perform product search",priority=7,dataProvider="products")
  public void PerformProductSearchTest(String productName) throws InterruptedException {
	  log.info("TC_07_Perform product search for:"+productName);
	  resultPg=myaccountPg.doProductSearch(productName);
	  String actualResultPgTitle=resultPg.getTitle();
	  log.info("search results page title :"+actualResultPgTitle);
	  new SoftAssert().assertEquals(actualResultPgTitle, "Search - "+productName);
	  Assert.assertTrue(resultPg.getSearchProductListSize()>0);
	 
  }
  
 @DataProvider(name="products")
 public Object[][] productTestData(){
	 
	 return new Object[][] {
		 {"MacBook"},
		 {"iMac"},
		 {"Samsung"}
	 };
 }
  
  
  @AfterClass
  public void doLogout() throws InterruptedException {
	  log.info("click on logout link");
	  myaccountPg.clickLogoutLink();
	  logoutPg.waitForPageLoad(2000);
	  Assert.assertEquals(logoutPg.getTitle(), Constants.ACCOUNT_LOGOUT_PAGE_TITLE);
	  log.info("Click on Continue button in Logout page");
	  logoutPg.clickContinueBtn();
	  homePg.waitForPageLoad(2000);
	  Assert.assertEquals(homePg.getTitle(), Constants.HOME_PAGE_TITLE);
  }
  
}
