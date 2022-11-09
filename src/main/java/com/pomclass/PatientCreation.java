package com.pomclass;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import org.openqa.selenium.support.PageFactory;

public class PatientCreation {
	public  WebDriver driver;

	@FindBy(xpath = "(//button[@title='Add new Patient'])[2]")
	public WebElement addPatient;

	@FindBy(id = "firstname")
	public WebElement firstName;

	@FindBy(id = "lastname")
	public WebElement lastname;

	@FindBy(xpath = "(//button[@id='gender_dropdown'])[1]")
	public WebElement clickGenderIcon;

	@FindBy(xpath = "(//ul[@id='genderDropdown'])[1]")
	public WebElement chooseGender;

	@FindBy(id = "birthDate2")
	public WebElement dateOfBirth;

	@FindBy(id = "userloginId")
	public WebElement emailId;

	@FindBy(id = "Phone")
	private WebElement phoneNumber;

	@FindBy(xpath = "(//button[@id='cancel-btn'])[1]")
	public WebElement cancel;

	@FindBy(id = "createPatient")
	public WebElement CreatePatient;

	@FindBy(xpath = "//button[@title='Unlink patient from hospital']")
	public WebElement deletePatient;

	@FindBy(xpath = "//button[@id='new-video-visit']")
	public WebElement videoChat;

	@FindBy(xpath = "(//button[@id='print'])[3]")
	public WebElement printId;
	@FindBy(xpath = "(//button[@onclick='Page.goBack()'])[3]")
	public WebElement goBack;

	@FindBy(xpath = "//button[@id='new-health-record']")
	public WebElement newHealthRecordCreation;

	@FindBy(xpath = "(//button[@title='Add Contact Information'])[2]")
	public WebElement addContact;
	
	@FindBy(xpath = "(//input[@id='address1'])[2]")
	public WebElement addAddress;
	
	@FindBy(xpath = "(//input[@id='city'])[2]")
	public WebElement addCity;
	
	@FindBy(xpath = "(//select[@id='countryGeoId'])[2]")
	public WebElement selectCountry;
	
	@FindBy(xpath = "(//select[@id='stateProvinceGeoId'])[2]")
	public WebElement selectState;
	
	@FindBy(xpath = "(//input[@id='postalCode'])[2]")
	public WebElement zipCode;
	
	@FindBy(xpath = "(//div[@class='selected-flag'])[3]")
	public WebElement selectFlag;
	
	@FindBy(xpath = "(//input[@id='phone1'])[2]")
	public WebElement enterPhoneNumber;
	
	@FindBy(xpath = "//ul[@class='country-list']/li" )
	public WebElement chooseFlag;
	
	@FindBy(xpath = "(//textarea[@id='notes'])[1]")
	public WebElement enterNotes;
	
	@FindBy(xpath = "(//div[@id='save-btn'])[8]")
	public WebElement saveContact;
	
	
	public PatientCreation(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

}
