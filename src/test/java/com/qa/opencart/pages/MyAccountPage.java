package com.qa.opencart.pages;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.qa.opencart.utils.Constants;
import com.qa.opencart.utils.WebDriverUtils;

public class MyAccountPage extends WebDriverUtils{

	private Logger log=LogManager.getLogger(MyAccountPage.class.getName());
	private WebDriver driver;
	public MyAccountPage(WebDriver driver) {
		super(driver);
		this.driver=driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath="//*[@id='top-links']/ul/li[2]/a")
	private WebElement myAccountMenu;
	
	@FindBy(css="input[placeholder='Search']")
	private WebElement searchEditbox;
	
	
	@FindBy(css="button[class='btn btn-default btn-lg']")
	private WebElement searchTorchIcon;
	
	@FindBy(linkText="Logout")
	private WebElement logoutLink;
	
	@FindBy(css="body div[class='container'] ul[class='breadcrumb'] li:nth-child(1) a:nth-child(1)")
	private WebElement accountBreadCrumb;
	
	@FindBy(xpath="//*[@id='content']/h2")
	private List<WebElement>myAccountHeaderList;
	
	@FindBy(xpath="//*[@id='top-links']/ul/li[2]/ul/li/a")
	private List<WebElement>myAccountMenuOptionList;
	
	@FindBy(xpath="//*[@id='content']/ul[1]/li/a")
	private List<WebElement>myAccountHeaderMenuOptionList;
	
	@FindBy(xpath="/html/body/div[2]/div/div/ul[2]/li/a")
	private List<WebElement>myOrdersHeaderMenuOptionList;
	
	public void clickMyAccountLink() throws InterruptedException {
		log.info("Click on My account menu link");
		click(myAccountMenu);
	}
	
	public String getMyAccountPageUrl() {
		log.info("fetch my Account page url");
		return waitForUrlContains(Constants.ACC_PAGE_FRACTION_URL);
	}
	
	public boolean isSearchExist() {
		return waitForElementVisible(By.name("search")).isDisplayed();
	}
	
	public boolean isLogoutExist() throws InterruptedException {
		clickMyAccountLink();
		return waitForElementVisible(By.linkText("Logout")).isDisplayed();
	}
	
	public void clickLogoutLink() throws InterruptedException {
		clickMyAccountLink();
		waitForElementVisible(By.linkText("Logout"));
		click(logoutLink);
	}
	
	public List<String> getMyAccountMenuOptionList() throws InterruptedException{
		clickMyAccountLink();
		List<String>myAccMenuOptionTxtList=new ArrayList();
		//iterate the myAccountMenuOptionList collection
		for(WebElement ele:myAccountMenuOptionList) {
			String txt=ele.getText();
			//add the element text to myAccMenuOptionTxtList
			myAccMenuOptionTxtList.add(txt);
		}
		return myAccMenuOptionTxtList;
	}
	
	public List<String> getMyAccountHeaderList(){
		List<String>myAccHeaderTxtList=new ArrayList();
		//iterate the myAccountHeaderList; collection
		for(WebElement ele:myAccountHeaderList) {
			String txt=ele.getText();
			//add the element text to myAccHeaderTxtList
			myAccHeaderTxtList.add(txt);
		}
		return myAccHeaderTxtList;
	}
	
	
	public List<String> getMyAccountheaderOptionList(){
		List<String>myAccHeaderOptionTxtList=new ArrayList();
		//iterate the myAccountMenuOptionList collection
		for(WebElement ele:myAccountHeaderMenuOptionList) {
			String txt=ele.getText();
			//add the element text to myAccHeaderOptionTxtList
			myAccHeaderOptionTxtList.add(txt);
		}
		return myAccHeaderOptionTxtList;
	}
	
	public List<String> getMyOrdersHeaderOptionList(){
		List<String>myOrdersHeaderTxtList=new ArrayList();
		//iterate the myAccountHeaderList; collection
		for(WebElement ele:myOrdersHeaderMenuOptionList) {
			String txt=ele.getText();
			//add the element text to myAccHeaderTxtList
			myOrdersHeaderTxtList.add(txt);
		}
		return myOrdersHeaderTxtList;
	}
	public ResultsPage doProductSearch(String productName) throws InterruptedException {
		log.info("searching for the product:"+productName);
		
		if(isSearchExist()) {
			sendData(searchEditbox, productName);
			click(searchTorchIcon);
			return new ResultsPage(driver);
		}
		return null;
	}
	
	
	
	
}
