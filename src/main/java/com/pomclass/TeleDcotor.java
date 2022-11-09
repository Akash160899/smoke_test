package com.pomclass;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class TeleDcotor {

	public WebDriver driver;

	@FindBy(xpath = "//td[text()='Tele Doctor']")
	public WebElement clickTeleDoctor;

	@FindBy(xpath = "(//input[@id='UserPartyName'])[2]")
	public WebElement searchDoctor;

	@FindBy(xpath = "(//input[@id='PatientPartyName'])[2]")
	public WebElement searchPatient;

	@FindBy(xpath = "(//div[@id='3355166'])[2]")
	public WebElement clickPatient;

	@FindBy(xpath = "//span[@class='circle1 center-cont']")
	public WebElement cancelCall;

	@FindBy(xpath = "//i[@id='audio-btn']")
	public WebElement muteUnmute;

	@FindBy(xpath = "//span[@class='circle1 center-cont video-camera']")
	public WebElement turnoffTurnOnVideo;

	public TeleDcotor(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);

	}

}
