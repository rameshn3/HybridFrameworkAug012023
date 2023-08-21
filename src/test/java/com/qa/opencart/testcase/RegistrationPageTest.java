package com.qa.opencart.testcase;

import org.testng.annotations.Test;

import com.github.javafaker.Faker;
import com.qa.opencart.factory.TestBase;
import com.qa.opencart.factory.WebDriverFactory;
import com.qa.opencart.pages.ForgotPasswordPage;
import com.qa.opencart.pages.HomePage;
import com.qa.opencart.pages.LoginPage;
import com.qa.opencart.pages.LogoutPage;
import com.qa.opencart.pages.MyAccountPage;
import com.qa.opencart.pages.RegistrationPage;
import com.qa.opencart.utils.Constants;
import com.qa.opencart.utils.ExcelUtils;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;

import java.io.IOException;
import java.util.Locale;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.testng.Assert;
import org.testng.annotations.AfterClass;

public class RegistrationPageTest extends TestBase{
	private Logger log=LogManager.getLogger(RegistrationPageTest.class.getName());
  
	String fname,lname,email,telephone,fax,compName,addr1,addr2,city,postcode,state,country;
	
	@BeforeTest
	public void testDataSetup() {
		log.info("Creating Object for Faker class");
		Faker fkobj=new Faker(new Locale("en-US"));
		fname=fkobj.address().firstName();
		lname=fkobj.address().lastName();
		email=fkobj.internet().emailAddress();
		telephone=fkobj.phoneNumber().cellPhone();
		fax=fkobj.phoneNumber().phoneNumber();
		compName=fkobj.company().name();
		addr1=fkobj.address().buildingNumber();
		addr2=fkobj.address().latitude();
		city=fkobj.address().city();
		postcode=fkobj.address().zipCode();
		country="United States";
		state=fkobj.address().state();
	}
	
	 @BeforeClass
	  public void beforeClass() throws InterruptedException {
		  log.info("Initilizing the page class objects");
		  homePg=new HomePage(driver);
		  regPg=new RegistrationPage(driver);
		  myaccountPg=new MyAccountPage(driver);
		  logoutPg=new LogoutPage(driver);
		 
	  }
	
  @BeforeMethod
  public void beforeMethod() throws InterruptedException {
	  log.info("navigate to registration page");
	  homePg.navigateToRegisterPage();
	  regPg.waitForPageLoad(2000);
	  log.info("Verify the registration page title");
	  Assert.assertEquals(regPg.getTitle(), Constants.REGISTRATION_PAGE_TITLE);
  }
 

  @Test(description="TC_01_Register an account with faker data",priority=1)
  public void RegisterAccountTest() throws InterruptedException {
	  log.info("Register an account with faker class data test");
	  try {
		log.info("fill personal details");
		  regPg.setPersonalDetails(fname, lname, email, telephone, fax);
		  log.info("Fill address details");
		  regPg.setAddressDetails(compName, addr1, addr2, city, postcode, country, state);
		  log.info("setting the password ");
		  String pwd = WebDriverFactory.randomAlphaNumeric();
		  regPg.setPasswordDetails(pwd, pwd);
		  log.info("selecting the subscribe option yes");
		  regPg.selectSubscribe("Yes");
		  log.info("check the privacy policy checkbox");
		  regPg.checkAgreeCheckbox();
		  log.info("click on Continue button in registration page");
		  regPg.clickContinueBtn();
		  regPg.waitForPageLoad(2000);
		  log.info("Verify the Account creation Success message and header text");
		  Assert.assertEquals(regPg.getAccntCreatedHeader(), Constants.YOUR_ACCNT_CREATED_HEADER);
		  Assert.assertEquals(regPg.getAccntCreatedSuccMsg(), Constants.YOUR_ACCNT_CREATED_SUCC_MSG);
		  log.info("Click on Account Created Continue button");
		  regPg.clickAccntHasBeenCreatedContinueBtn();
		  log.info("wait for the my account page");
		  myaccountPg.waitForPageLoad(2000);
	} catch (InterruptedException e) {
		log.info("Account Creation Failed");
		e.printStackTrace();
	}
  }

  @Test(description="TC_02_Register an account with excel data and datadriven test",dataProvider="excelData",priority=2)
  public void registerAccountWithExcelDataTest(String fName,String lName,String telePhone,String passwd,String subScribe) {
	  log.info("Register an account with Excel data");
	  try {
		  String emailVal=WebDriverFactory.randomeString()+"@gmail.com";
			log.info("Entering personal details");
			  regPg.setPersonalDetails(fName, lName, emailVal, telePhone, fax);
			  log.info("Entering address details");
			  regPg.setAddressDetails(compName, addr1, addr2, city, postcode, country, state);
			  log.info("setting the password ");
			  regPg.setPasswordDetails(passwd, passwd);
			  log.info("selecting the subscribe option yes");
			  regPg.selectSubscribe(subScribe);
			  log.info("check the privacy policy checkbox");
			  regPg.checkAgreeCheckbox();
			  log.info("click on Continue button in registration page");
			  regPg.clickContinueBtn();
			  regPg.waitForPageLoad(2000);
			  log.info("Verify the Account creation Success message and header text");
			  Assert.assertEquals(regPg.getAccntCreatedHeader(), Constants.YOUR_ACCNT_CREATED_HEADER);
			  Assert.assertEquals(regPg.getAccntCreatedSuccMsg(), Constants.YOUR_ACCNT_CREATED_SUCC_MSG);
			  log.info("Click on Account Created Continue button");
			  regPg.clickAccntHasBeenCreatedContinueBtn();
			  log.info("wait for the my account page");
			  myaccountPg.waitForPageLoad(2000);
		} catch (InterruptedException e) {
			log.info("Account Creation Failed");
			e.printStackTrace();
		}
  }
  
  @DataProvider
  public Object[][] excelData() throws InvalidFormatException, IOException{
	  Object[][] data=new ExcelUtils().getTestData(Constants.TEST_DATA_FILE_PATH, Constants.REGISTER_SHEET_NAME);
	  
	  return data;
  }
  
  @AfterMethod
  public void doLogout() throws InterruptedException {
	  log.info("Verify my account page title");
	  Assert.assertEquals(myaccountPg.getTitle(), Constants.MY_ACCOUNT_PAGE_TITLE);
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
