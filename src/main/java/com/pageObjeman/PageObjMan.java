package com.pageObjeman;

import org.openqa.selenium.WebDriver;

import com.pomclass.Billing;
import com.pomclass.Calendar;
import com.pomclass.HealthRecord;
import com.pomclass.LoginPage;
import com.pomclass.Message;
import com.pomclass.PatientCreation;
import com.pomclass.Settings;
import com.pomclass.TeleDcotor;

public class PageObjMan {

	private LoginPage lg;
	private PatientCreation pc;
	private HealthRecord hr;
	private Calendar c;
	private Billing b;
	private TeleDcotor t;
	private Message m;
	private Settings s;

	public WebDriver driver;

	public PageObjMan(WebDriver driver) {
		this.driver = driver;
	}

	public LoginPage getInstanceLoginPage() {
		if (lg == null) {

			lg = new LoginPage(driver);

		}
		return lg;
	}

	public PatientCreation getInstanceNewPatient() {
		if (pc == null) {
			pc = new PatientCreation(driver);
		}
		return pc;
	}

	public HealthRecord getInstanceHealthRec() {
		if (hr == null) {

			hr = new HealthRecord(driver);
		}
		return hr;
	}

	public Calendar getInstanceCalendar() {
		if (c == null) {
			c = new Calendar(driver);
		}
		return c;
	}

	public Billing getInstanceBilling() {
		if (b == null) {

			b = new Billing(driver);
		}
		return b;
	}

	public TeleDcotor getInstanceTeleDoctor() {
		if (t == null) {

			t = new TeleDcotor(driver);
		}
		return t;
	}

	public Message getInstanceMessage() {
		if (m == null) {

			m = new Message(driver);
		}
		return m;

	}

	public Settings getInstanceSetting() {
		if (s == null) {

			s = new Settings(driver);

		}
		return s;
	}

}
