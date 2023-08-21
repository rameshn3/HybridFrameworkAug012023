package com.qa.opencart.testcase;

import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.qa.opencart.factory.TestBase;
import com.qa.opencart.pages.ForgotPasswordPage;
import com.qa.opencart.pages.HomePage;
import com.qa.opencart.pages.LoginPage;
import com.qa.opencart.pages.LogoutPage;
import com.qa.opencart.pages.MyAccountPage;
import com.qa.opencart.pages.ProductDetailsPage;
import com.qa.opencart.pages.RegistrationPage;
import com.qa.opencart.pages.ResultsPage;
import com.qa.opencart.utils.Constants;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.AfterClass;

public class ProductDetailsPageTest extends TestBase{
	private Logger log=LogManager.getLogger(ProductDetailsPageTest.class.getName());
  
	
	 @BeforeClass
	  public void beforeClass() throws InterruptedException {
		  log.info("Initilizing the page class objects");
		  homePg=new HomePage(driver);
		  loginPg=new LoginPage(driver);
		  myaccountPg=new MyAccountPage(driver);
		  logoutPg=new LogoutPage(driver);
		  resultPg=new ResultsPage(driver);
		  productDetailPg=new ProductDetailsPage(driver);
		  homePg.navigateTologinPage();
		  loginPg.doLogin(rb.getString("username"), rb.getString("pwd"));
	  }
	
  @BeforeMethod
  public void beforeMethod() {
	  myaccountPg.waitForPageLoad(2000);
  }
 
@DataProvider
public Object[][] productImageTestData(){
	
	return new Object[][] {
		{"Macbook","MacBook Pro",4},
		{"Macbook","MacBook Air",4},
		{"iMac","iMac",3},
		{"Samsung","Samsung SyncMaster 941BW",1},
		{"Apple","Apple Cinema 30\"",6}
	};
}
  
  @Test(description="TC_01_Verify the product image test",priority=1,dataProvider="productImageTestData")
  public void productImageTest(String searchKeyword,String productName,int imgCount) throws InterruptedException {
	  log.info("Perform product search in MyAccountPage ");
	  resultPg=myaccountPg.doProductSearch(searchKeyword);
	  log.info("select product name in search results page");
	  resultPg.selectProduct(productName);
	 log.info("fetch the product image count for particular searched product");
	 int actProdImageCount=productDetailPg.getProductImageCount();
	  Assert.assertEquals(actProdImageCount,imgCount);
  }


  
  @Test(description="TC_02_ product_metadata Test",priority=2)
  public void ProductMetadataTest() throws InterruptedException {
	 log.info("Perform product search in MyAccountPage ");
	 resultPg=myaccountPg.doProductSearch("MacBook");
	 log.info("select product name in search results page");
	 resultPg.selectProduct("MacBook Pro");
	 Map<String,String>actualProductInfoMap= productDetailPg.getProductInformation();
	 SoftAssert softAssert=new SoftAssert();
	 softAssert.assertEquals(actualProductInfoMap.get("Brand"), "Apple");
	 softAssert.assertEquals(actualProductInfoMap.get("Product Code"), "Product 18");
	 softAssert.assertEquals(actualProductInfoMap.get("Reward Points"), "800");
	 softAssert.assertEquals(actualProductInfoMap.get("Availability"), "In Stock");
	 softAssert.assertEquals(actualProductInfoMap.get("actualprice"), "$2,000.00");
	 softAssert.assertAll();
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
