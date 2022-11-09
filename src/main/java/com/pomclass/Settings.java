package com.pomclass;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class Settings {
	public WebDriver driver;

	@FindBy(xpath = "//td[text()='Settings']")
	public WebElement clickSettings;

	@FindBy(xpath = "//button[@id='trash-btn']")
	public WebElement scrollTillDelete;

	@FindBy(xpath = "//button[@id='taxbutton']")
	public WebElement scrollTillTax;

	@FindBy(xpath = "//button[@onclick='hospitalcodedropdown();']")
	public WebElement scrollTillHospitalCode;

	@FindBy(xpath = "//button[@id='edit-print-preference']")
	public WebElement scrollTillPrintSettings;

	@FindBy(xpath = "//div[@id='subscription-head']")
	public WebElement scrollTillSubscription;

	@FindBy(xpath = "(//div[text()='Notification Messages'])[1]")
	public WebElement scrollTillNotification;

	@FindBy(xpath = "//button[@onclick='setting.audit()']")
	public WebElement scrollTillAuditReport;

	@FindBy(xpath = "//button[@onclick='setting.userList()']")
	public WebElement clickmanageuser;

	@FindBy(xpath = "//button[text()='Active Users']")
	public WebElement clickactiveuserpage;

	@FindBy(xpath = "//button[text()='Payment History']")
	public WebElement paymenthistorypage;

	@FindBy(xpath = "//button[@onclick='Health.user_new()']")
	public WebElement addnewuser;
	
	@FindBy(xpath = "//span[text()='Subscription Options']//following::button[2]")
	public WebElement $sub$scribe$;
	
	@FindBy(xpath = "//span[text()='Subscription Options']//following::button[1]")
	public WebElement $dismissSubscribe$;
	
	@FindBy(xpath ="(//span[@title='Next'])[2]")
	public WebElement $Carosel$;
	

	public Settings(WebDriver driver) {
		this.driver = driver;

		PageFactory.initElements(driver, this);
	}
}
