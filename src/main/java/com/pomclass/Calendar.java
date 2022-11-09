package com.pomclass;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class Calendar {
	public WebDriver driver;

	@FindBy(xpath = "//td[text()='Calendar']")
	public WebElement clickCalendar;

	@FindBy(xpath = "(//span[@class='hidden-doctor hover icon-md fa fa-times-circle'])[2]")
	public WebElement clickXicon;

	@FindBy(xpath = "(//input[@id='doctorPartyName-cal'])[1]")
	public WebElement serachDoctorUserName;
	//(//div[@title='Click to book appointment'])[14]
	@FindBy(xpath = "(//div[@title='Click to book appointment'])[14]")
	public WebElement timePick;

	@FindBy(xpath = "(//input[@id='AppointmentPatientName'])[1]")
	public WebElement searchPatientName;

	@FindBy(xpath = "(//select[@id='triage-appointment'])[1]")
	public WebElement selectAppointmentType;

	@FindBy(xpath = "(//textarea[@id='description'])[3]")
	public WebElement enterDiscription;

	@FindBy(xpath = "(//button[@id='accept-btn'])[1]")
	public WebElement saveAppointment;

	@FindBy(xpath = "(//button[@id='cancel-btn'])[7]")
	public WebElement cancelAppointment;

	@FindBy(xpath = "(//button[@class='settings-heading settings-buttonColor settings-button mobile-button'])[1]")
	public WebElement clickColour;

	@FindBy(xpath = "(//ul[@id='color-dropdown'])[1]")
	public WebElement chooseColor;

	@FindBy(xpath = "(//button[@id='statusId_dropdown'])[1]")
	public WebElement clickSchedule;

	@FindBy(xpath = "(//ul[@id='statusIdDropdown'])[1]")
	public WebElement chooseSchedule;

	@FindBy(xpath = "(//div[@class='btn-group dropup'])[3]")
	public WebElement clickmoreOption;

	@FindBy(xpath = "(//ul[@class='dropdown-menu dropdown-menu-right'])[16]")
	public WebElement hideOptions;

	@FindBy(xpath = "(//span[@id='del-btn'])[3]")
	public WebElement deleteSchedule;
//(//span[@id='del-btn'])[3]
	//(//span[@id='del-btn'])[6]
	@FindBy(xpath = "(//span[@id='yes-btn'])[25]")
	public WebElement yes;

	@FindBy(xpath = "(//button[@id='cancel-btn1'])[1]")
	public WebElement goBackToEhr;

	@FindBy(xpath = "(//span[@id='cancel-btn'])[16]")
	public WebElement maxCancel;
	
	@FindBy(xpath = "(//div[@class='col-xs-12 scheduled row-2 hover'])[1]")
	public WebElement clickonBookedAppointment;

	public Calendar(WebDriver driver) {

		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

}
