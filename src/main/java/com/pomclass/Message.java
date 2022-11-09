package com.pomclass;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class Message {
	public WebDriver driver;
	@FindBy(xpath = "//td[text()='Message ']")
	public WebElement clickMessage;
	
	@FindBy(xpath = "//button[@title='Compose new Message']")
	public WebElement clickComposemMessage;
	
	@FindBy(xpath = "(//input[@id='emailSearch'])[1]")
	public WebElement search;
	
	@FindBy(xpath = "(//input[@id='subject'])[1]")
	public WebElement enterSubject;
	
	
	@FindBy(xpath = "//div[@contenteditable='true']")
	public WebElement enterMessage;
	
	@FindBy(xpath = "(//button[@id='send-btn'])[1]")
	public WebElement sendMessage;
	
	
	@FindBy(xpath = "//a[@id='sent']")
	public WebElement seeSentMessage;
	
	
	public Message(WebDriver driver) {
		this.driver=driver;
		
		PageFactory.initElements(driver, this);
	}

}
