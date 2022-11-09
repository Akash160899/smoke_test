package com.pomclass;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HealthRecord {
	public WebDriver driver;
	
	@FindBy(xpath = "//td[text()='Health Record']")
	public WebElement clickHealthRec;
	
	@FindBy(xpath = "//span[@onclick='list_ehr.patientRemoved();']")
	public WebElement patientRemove;
	
	@FindBy(id = "(//input[@title='Search Patient by partial name, p:phone, full KPiD or email'])[2]")
	public WebElement patientName;
	
	@FindBy(xpath = "(//div[@id='provider'])[1]")
	public WebElement clickRecord;
	
	@FindBy(xpath = "//div[@title='Add Goals']")
	public WebElement add;
	
	
	@FindBy(xpath = "//div[@contenteditable='true']")
	public WebElement discripition;
	
	@FindBy(xpath = "//button[@title='Bold']")
	public WebElement bold;
	
	@FindBy(xpath = "//button[@title='Italic']")
	public WebElement italic;
	
	@FindBy(xpath = "//button[@title='Underline']")
	public WebElement underline;
	
	
	@FindBy(xpath = "//button[@title='Heading 1']")
	public WebElement h1;
	
	@FindBy(xpath = "//button[@title='Heading 2']")
	public WebElement h2;

	
	@FindBy(xpath = "//button[@title='Ordered List']")
	public WebElement orderList;
	
	@FindBy(xpath = "//button[@title='Unordered List']")
	public WebElement unorderList;
	
	@FindBy(xpath = "//button[@title='Indent']")
	public WebElement indent;
	
	@FindBy(xpath = "//button[@title='Outdent']")
	public WebElement outdent;
	
	@FindBy(xpath = "//input[@class='form-control minWidth hasDatepicker']")
	public WebElement clickdate;
	
	@FindBy(xpath = "//select[@class='ui-datepicker-month']")
	public WebElement month;
	
	@FindBy(xpath = "//select[@class='ui-datepicker-year']")
	public WebElement year;
	
	@FindBy(xpath = "//table[@class='ui-datepicker-calendar']")
	public WebElement choosedate;
	
	@FindBy(xpath = "(//button[@id='cancel-btn'])[7]")
	public WebElement cancel;
	
	@FindBy(xpath = "(//button[@id='accept-btn'])[1]")
	public WebElement save;
	
	@FindBy(xpath = "(//span[@class='hover icon-md fa fa-trash'])[4]")
	public WebElement deletegoal;
	
	@FindBy(xpath ="(//div[@class='col-xs-11 word-break-all print-color'])[3]" )
	public WebElement editGoal;
	
	
	@FindBy(xpath = "//div[@title='Show my favorite goals list for selection']")
	public WebElement showMyFavorites;
	
	@FindBy(xpath ="//div[@title='SALT Goals']")
	public WebElement clickSalt;
	
	@FindBy(xpath = "(//input[@id='search-string'])[1]")
	public WebElement searchFavorities;
	
	@FindBy(xpath = "(//span[@id='plus-add'])[1]")
	public WebElement addFavorities;
	
	@FindBy(xpath = "(//span[@class='hover icon-md fas fa-times-circle'])[7]")
	public WebElement cancelinFav;
	
	@FindBy(xpath = "(//div[@title='Show more options'])[9]")
	public WebElement showMoreOption;
	
	
	@FindBy(xpath = "(//button[@id='followUpAdd'])[1]")
	public WebElement Createfollowup;
	
	@FindBy(xpath = "(//input[@title='Select future date for the follow up'])[1]")
	public WebElement choosedateFromFollowup;
	
	@FindBy(xpath = "//a[@class='ui-state-default ui-state-highlight']")
	public WebElement date;
	
	@FindBy(xpath = "(//button[@id='fixAppointment'])[1]")
	public WebElement fixOppintment;
	
@FindBy(xpath = "(//div[@id='save-btn'])[9]")
public WebElement saveFollowup;

@FindBy(xpath = "(//div[@class='col-xs-12 row-0'])[1]")
public WebElement choosetimefromFixAppointment;


@FindBy(xpath = "(//select[@id='triage-follow'])[1]")
public WebElement selectAppintmentType;


@FindBy(xpath = "(//textarea[@id='description'])[1]")
public WebElement enterDiscriptionInappointment;

@FindBy(xpath = "(//div[@id='save-btn'])[12]")
public WebElement saveFolloAppointment;

@FindBy(xpath = "(//span[@id='assign-cancel-btn'])[1]")
public WebElement cancelAppointment;

@FindBy(xpath = "(//span[@id='fixAppointmentDelete'])[1]")
public WebElement deleteFixAppiontment;

@FindBy(xpath = "(//button[@id='back-btn'])[6]")
public 	WebElement goBack;

@FindBy(xpath = "(//button[@id='complete-btn'])[1]")
public WebElement complete;

@FindBy(xpath = "(//button[@id='Billing'])[1]")
public WebElement generateBill;

@FindBy(xpath = "(//button[@id='delete-btn'])[1]")
public WebElement delete;


public HealthRecord(WebDriver driver) {
	this.driver=driver;
	PageFactory.initElements(driver, this);
	
	
}

	
	
	
	
	
}
