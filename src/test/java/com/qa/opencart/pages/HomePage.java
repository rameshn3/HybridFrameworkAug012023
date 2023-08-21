package com.qa.opencart.pages;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.qa.opencart.utils.WebDriverUtils;

public class HomePage extends WebDriverUtils{

	private static Logger log=LogManager.getLogger(HomePage.class.getName());
	
	public HomePage(WebDriver driver) {
		super(driver);
	
	}

	@FindBy(css="#logo > a > img")
	private WebElement openCartLogo;
	
	@FindBy(xpath="//div[@id='top-links']/ul/li[2]/a")
	private WebElement myAccountMenu;
	
	@FindBy(linkText="Register")
	private WebElement registerLink;
	
	@FindBy(linkText="Login")
	private WebElement loginLink;
	
	@FindBy(xpath="//div[@id='content']/div[2]/div")
	private List<WebElement> featuredSectionCardsList;
	
	public void openMyAccountMenu() throws InterruptedException {
		log.info("opening my account menu");
		click(myAccountMenu);
	}
	
	public void navigateToRegisterPage() throws InterruptedException {
		openMyAccountMenu();
		log.info("click on Register link under myaccountMenu");
		click(registerLink);
	}
	public void navigateTologinPage() throws InterruptedException {
		openMyAccountMenu();
		log.info("click on login link under myaccountMenu");
		click(loginLink);
	}
	
	public boolean isOpenCartLogoExists() {
		log.info("Verify opencart logo is present or not");
		return openCartLogo.isDisplayed();
	}
	
	public int getfeaturedSectionCardCount() {
		return featuredSectionCardsList.size();
	}
}
