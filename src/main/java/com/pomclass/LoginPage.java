package com.pomclass;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;



public class LoginPage  {
	public  WebDriver driver;
	 
	@FindBy(xpath="//a[text()='Sign In']")
	public WebElement sigIn;
	
	@FindBy(id = "userLoginId")
	public WebElement email;
	
	@FindBy(id = "currentPassword")
	public WebElement pass;
	
	@FindBy(xpath = "//h3[@id='signin_button']")
	public WebElement login;
	
 public LoginPage(WebDriver driver) {
	 this.driver=driver;
	 PageFactory.initElements(driver,this);
	
}

}
