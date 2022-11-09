package runner;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.baseClass.base;
import com.data.ConfigManager;
import com.pageObjeman.PageObjMan;

public class Runer extends base {

	public static WebDriver driver;
	public static PageObjMan pom;
	public static JavascriptExecutor j;
	public static WebDriverWait ww;
	String kpid;

	@BeforeClass
	public void LaunchBrwoser() throws InterruptedException, IOException {

		driver = base.setUp("chrome");

		pom = new PageObjMan(driver);
		j = (JavascriptExecutor) driver;
		ww = new WebDriverWait(driver, 60);

		String ur = ConfigManager.getconfigManager().getInstanceConfigReader().getUrl();

		while (true) {
			if (ur.equals("https://localhost:8443/")) {

				driver.get(ConfigManager.getconfigManager().getInstanceConfigReader().getUrl());
				sleep(3000);
				driver.findElement(By.id("details-button")).click();
				sleep(3000);

				driver.findElement(By.id("proceed-link")).click();
				sleep(4000);
				implicitWait(30, TimeUnit.SECONDS);

				break;
			} else if (ur.equals("https://www.75health.com/login.jsp")) {
				driver.get("https://www.75health.com/login.jsp");

				break;
			}

		}

		click(pom.getInstanceLoginPage().sigIn);
		while (true) {
			try {
				sendkeys(pom.getInstanceLoginPage().email,
						ConfigManager.getconfigManager().getInstanceConfigReader().getEmail());
				sendkeys(pom.getInstanceLoginPage().pass,
						ConfigManager.getconfigManager().getInstanceConfigReader().getpass());
				click(pom.getInstanceLoginPage().login);
				break;
			} catch (Exception e) {
				// TODO: handle exception
			}
		}

		try {
			sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		while (true) {
			try {
				if (driver.getCurrentUrl().equals("https://www.75health.com/health/#home")) {
					break;
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
		}

	}

	@Test(priority = 0)
	public void HomeModule() throws InterruptedException {

		while (true) {
			try {

				WebElement ata = driver.findElement(By.xpath("(//span[contains(text(),'New Pa')])[4]//parent::button"));
				j.executeScript("arguments[0].click();", ata);
				break;
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		sendkeys(pom.getInstanceNewPatient().firstName, "sam");
		sendkeys(pom.getInstanceNewPatient().lastname, "n");
		click(pom.getInstanceNewPatient().clickGenderIcon);

		List<WebElement> genders = driver.findElements(By.xpath("(//ul[@id='genderDropdown'])[1]/li"));
		for (WebElement opt : genders) {

			if (opt.getText().equals("Male")) {

				driver.findElement(By.xpath("(//ul[@id='genderDropdown'])[1]/li")).click();

			}
			break;
		}
		// Acc gets Created..
		click(pom.getInstanceNewPatient().CreatePatient);
		String hkpid;
		while (true) {
			try {
				WebElement $patietcreateid$ = driver.findElement(By.xpath("//td[@id='val-kpid']"));
				visbility(driver, $patietcreateid$, 60);
				hkpid = $patietcreateid$.getText();
				break;
			} catch (Exception e) {
				// TODO: handle exception
			}
		}

		System.out.println("patient hkpid:" + hkpid);
		driver.navigate().to("https://www.75health.com/health/#home");
		// driver.navigate().back();
		// navigateback();
		// refresh();

		implicitWait(60, TimeUnit.SECONDS);

		// calendar appointment...
		List<WebElement> totalnumberrowdy;
		while (true) {
			try {

				totalnumberrowdy = driver.findElements(By.xpath("//div[@id='date-data']"));
				System.out.println("totle row:" + totalnumberrowdy.size());
				break;
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		int totalr = totalnumberrowdy.size();

		boolean cond = false;

		for (int i = 1; i <= totalr; i++) {
			int a = 1 + i;
			WebElement ss;
			while (true) {
				try {
					ss = driver.findElement(By.xpath("//div[@id='date-data'][" + i + "]/div[2]/div[2]/div"));
					// System.out.println("loop irteration");

					break;
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
			boolean $drWorking = false;
			while (true) {
				try {
					if (ss.getText().equals("Doctor/User not working")) {
						$drWorking = true;
						break;

					} else if (!ss.getText().contentEquals("Doctor/User not working")) {
						break;
					}
				} catch (Exception e) {
					// TODO: handle exception
				}
			}

			if ($drWorking == true) {
				WebElement abcd = driver.findElement(By.xpath("(//span[@id='editCalendar'])[" + a + "]"));
				visbility(driver, abcd, 60);
				actions("click", abcd);
				sleep(3000);
				WebElement checkbx = driver.findElement(By.xpath("(//input[@id='is-working-day'])[1]"));
				visbility(driver, checkbx, 60);

				actions("click", checkbx);
				WebElement ampm = driver.findElement(By.xpath("(//div[@id='thru-ampm'])[1]"));
				visbility(driver, ampm, 60);
				actions("click", ampm);
				driver.findElement(By.xpath("(//div[@id='save-btn'])[1]")).click();
				sleep(5000);
			}

			// System.out.println("not dr using is okay");

			// represent total in a part..
			List<WebElement> rchange;
			while (true) {
				try {
					rchange = driver.findElements(
							By.xpath("(//div[@id='date-data'][" + i + "]/div[2]/div/div[1]/div[1]/div[1])"));
					System.out.println("tottal number of col in row is" + rchange.size());
					break;
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
			int avaiable = rchange.size();

			for (int b = 1; b <= avaiable; b++) {
				WebElement tp;
				while (true) {
					try {
						tp = driver.findElement(
								// represent the row change and time..
								By.xpath("(//div[@id='date-data'][" + i + "]/div[2]/div[" + b
										+ "]/div[1]/div[1]/div[1])"));
						System.out.println("row chaange and time");
						System.out.println(tp.getText());
						break;
					} catch (Exception e) {
						// TODO: handle exception
					}
				}
				// String tr = tp.getText();
				// System.out.println(tr);
				// boolean trp = tp.isDisplayed();
				// String rrs = tp.getAttribute("id");

				// the $$$ pcrid $$$$ ..
				WebElement kp;
				while (true) {
					try {
						kp = driver.findElement(By
								.xpath("(//div[@id='date-data'])[" + i + "]/div[2]/div[" + b + "]/div/div[2]/span[2]"));
						System.out.println("kpid empy checking");
						System.out.println(kp.getText());
						break;
					} catch (Exception e) {

					}
				}

				while (true) {

					try {

						if (kp.getText().isEmpty() && tp.isDisplayed()) {
							System.out.println("home apppointment calendar met the condition");
							cond = true;
							visbility(driver, tp, 60);
							javascriptclick(tp);
							// && !tp.getText().isEmpty()
						}
						break;
					} catch (Exception e) {
						// TODO: handle exception
					}
				}

				if (cond == true) {
					while (true) {
						try {
							WebElement qrs = driver
									.findElement(By.xpath("(//input[@id='AppointmentPatientName'])[" + i + "]"));
							visbility(driver, qrs, 60);
							sendkeys(qrs, hkpid);

							break;
						} catch (Exception e) {
							// TODO: handle exception
						}

					}
				}
				if (cond == true) {
					implicitWait(60, TimeUnit.SECONDS);
					while (true) {
						try {
							WebElement choosepatient = driver
									.findElement(By.xpath("(//td[text()='" + hkpid + "'])[2]//parent::td"));
							visbility(driver, choosepatient, 60);
							// ww.until(ExpectedConditions.elementToBeClickable(choosepatient));
							// actions("click", choosepatient);
							click(choosepatient);
							break;
						} catch (Exception e) {

						}
					}

					WebElement ut = driver.findElement(By.xpath("(//select[@id='triage-appointment'])[" + i + "]"));
					visbility(driver, ut, 60);

					dropDown("text", ut, "Emergency");

					WebElement qt = driver.findElement(By.xpath("(//textarea[@id='description'])[" + i + "]"));
					visbility(driver, qt, 60);

					sendkeys(qt, "no worries...");
					while (true) {
						try {
							WebElement $statusdropdwn$ = driver
									.findElement(By.xpath("(//button[@id='statusId_dropdown'])[" + i + "]"));
							visbility(driver, $statusdropdwn$, 60);
							click($statusdropdwn$);
							break;
						} catch (Exception e) {

						}
					}
					// j.executeScript("arguments[0].click();", utt);

					List<WebElement> lop = driver.findElements(
							By.xpath("(//button[@id='statusId_dropdown'])[" + i + "]//following::ul[1]/li/a"));
					for (WebElement w : lop) {
						if (w.getText().trim().equals("In Progress")) {

							visbility(driver, w, 60);
							w.click();
							break;
						}

					}

					WebElement $saveappointment$ = driver.findElement(By.xpath("(//button[@id='accept-btn'])[1]"));
					visbility(driver, $saveappointment$, 60);
					javascriptclick($saveappointment$);

					while (true) {
						try {
							WebElement $fnd_idfrm$appointmnet$ = driver
									.findElement(By.xpath("//span[text()='" + hkpid + "']"));
							visbility(driver, $fnd_idfrm$appointmnet$, 60);
							javascriptclick($fnd_idfrm$appointmnet$);
							break;
						} catch (Exception e) {

						}
					}

					while (true) {
						try {
							WebElement $delAppoinKpop$ = driver
									.findElement(By.xpath("(//span[@id='del-btn'])[" + i + "]"));
							visbility(driver, $delAppoinKpop$, 60);
							javascriptclick($delAppoinKpop$);

							break;
						} catch (Exception e) {
							// TODO: handle exception
						}
					}

					while (true) {
						try {
							WebElement $del_app$ = driver.findElement(
									By.xpath("//div[@id='AppointmentCreateMessage']/div[2]/div[2]/button[2]"));
							visbility(driver, $del_app$, 60);
							javascriptclick($del_app$);
							break;
						} catch (Exception e) {
							// TODO: handle exception
						}
					}

					// $$$$$$$$$$$$?//cond ends$$$$$$$..

					break;
				}
			}

			while (true)

			{
				try {
					WebElement fdf = driver.findElement(By.xpath("(//input[@id='patientPartyName'])[3]"));
					visbility(driver, fdf, 60);
					sendkeys(fdf, hkpid);
					break;
				} catch (Exception e) {

				}
			}

			implicitWait(50, TimeUnit.SECONDS);
			WebElement cli = driver.findElement(By.xpath("(//td[text()='" + hkpid + "'])[2]"));
			visbility(driver, cli, 60);
			clickble(driver, cli, 60);
			javascriptclick(cli);
			sleep(2000);
			ScriptExecutor(pom.getInstanceNewPatient().deletePatient);
			visbility(driver, pom.getInstanceNewPatient().deletePatient, 60);
			click(pom.getInstanceNewPatient().deletePatient);

			WebElement c = driver.findElement(By.xpath("(//button[text()='Delete'])[1]"));
			visbility(driver, c, 65);
			click(c);
			sleep(3000);
			if (cond == true) {
				break;
			}
		}

	}

	@Test(priority = 1)
	public void PatientModule() throws InterruptedException {
		sleep(2000);
		while (true) {
			try {

				WebElement pit = driver.findElement(By.xpath("//td[text()='Patient']"));

				visbility(driver, pit, 60);
				j.executeScript("arguments[0].click();", pit);
				break;
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		implicitWait(60, TimeUnit.SECONDS);
		while (true) {
			try {
				WebElement ata = driver.findElement(By.xpath("(//button[@title='Add new Patient'])[1]"));
				visbility(driver, ata, 60);
				j.executeScript("arguments[0].click();", ata);
				break;
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		visbility(driver, pom.getInstanceNewPatient().firstName, 60);
		sendkeys(pom.getInstanceNewPatient().firstName, "sam");
		visbility(driver, pom.getInstanceNewPatient().lastname, 60);
		sendkeys(pom.getInstanceNewPatient().lastname, "n");
		visbility(driver, pom.getInstanceNewPatient().clickGenderIcon, 60);
		click(pom.getInstanceNewPatient().clickGenderIcon);

		List<WebElement> genders = driver.findElements(By.xpath("(//ul[@id='genderDropdown'])[1]/li"));
		for (WebElement opt : genders) {

			if (opt.getText().equals("Male")) {

				driver.findElement(By.xpath("(//ul[@id='genderDropdown'])[1]/li")).click();

			}
			break;
		}

		// Acc gets Created..
		visbility(driver, pom.getInstanceNewPatient().CreatePatient, 60);
		click(pom.getInstanceNewPatient().CreatePatient);
		sleep(2000);
		while (true) {
			try {
				WebElement es = driver.findElement(By.xpath("//td[@id='val-kpid']"));
				visbility(driver, es, 60);
				kpid = es.getText();
				break;
			} catch (Exception e) {
				// TODO: handle exception
			}
		}

		while (true) {
			try {
				WebElement abcd = driver.findElement(By.xpath("//td[text()='Patient']"));

				visbility(driver, abcd, 60);
				abcd.click();
				break;
			} catch (Exception e) {
				// TODO: handle exception
			}
		}

		while (true) {
			try {
				WebElement s = driver.findElement(By.xpath("(//input[@id='patientPartyName'])[2]"));
				visbility(driver, s, 60);
				s.sendKeys(kpid);
				break;
			} catch (Exception e) {
				// TODO: handle exception
			}
		}

		while (true) {
			try {
				WebElement ki = driver.findElement(By.xpath("//div[text()=" + "'" + kpid + "']"));
				visbility(driver, ki, 60);
				javascriptclick(ki);
				break;
			} catch (Exception e) {
				// TODO: handle exception
			}
		}

		sleep(2000);

		// med info..

		/*
		 * WebElement ed =
		 * driver.findElement(By.xpath("//div[@id='basicinfo-div']/div/span[2]"));
		 * ww.until(ExpectedConditions.elementToBeClickable(ed)); actions("click", ed);
		 * 
		 * WebElement fname = driver.findElement(By.
		 * xpath("//span[text()='Medical Info ']//following::div[2]/input[2]"));
		 * fname.clear(); fname.sendKeys("Rolls"); WebElement lname =
		 * driver.findElement(By.
		 * xpath("//span[text()='Medical Info ']//following::div[2]/input[3]"));
		 * lname.clear(); lname.sendKeys("Royals");
		 */

		/*
		 * driver.findElement(By.
		 * xpath("//span[text()='Medical Info ']//following::div[2]/input[4]")).click();
		 * sleep(4000);
		 * 
		 * WebElement mnth =
		 * driver.findElement(By.xpath("//select[@class='ui-datepicker-month']"));
		 * mnth.click(); Select s = new Select(mnth); s.selectByIndex(5);
		 * 
		 * WebElement yr =
		 * driver.findElement(By.xpath("//select[@class='ui-datepicker-year']")); Select
		 * ss = new Select(yr); ss.selectByIndex(9);
		 */
		/*
		 * List<WebElement> bldgrp = driver .findElements(By.
		 * xpath("//span[text()='Medical Info ']//following::div[3]/ul/li/a")); for
		 * (WebElement web : bldgrp) { if (web.getText().trim().equals("O Positive")) {
		 * web.click(); break; }
		 * 
		 * }
		 * 
		 * driver.findElement(By.
		 * xpath("//span[text()='Medical Info ']//following::div[3]//following::input[1]"
		 * )) .sendKeys("ins12234"); driver.findElement(By.
		 * xpath("//span[text()='Medical Info ']//following::div[3]//following::input[2]"
		 * )) .sendKeys("Riseup sTRONGER"); WebElement sav =
		 * driver.findElement(By.xpath(
		 * "//div[@id='p_previousname']/div//following::div[5]/button[2]"));
		 * ww.until(ExpectedConditions.elementToBeClickable(sav)); javascriptclick(sav);
		 * sleep(2000);
		 */

		// contact info
		while (true) {
			try {

				WebElement cnin = driver.findElement(
						By.xpath("//div[@id='p-address-phone']/div/div/div[1]/div[1]//following::div[1]/div[1]/div"));
				visbility(driver, cnin, 60);
				click(cnin);
				break;
			} catch (Exception e) {
				// TODO: handle exception
			}
		}

		WebElement roy = driver
				.findElement(By.xpath("//div[@id='p-address-phone']/div/div/div[2]/div/div[1]/div[2]/input[4]"));
		visbility(driver, roy, 60);
		roy.sendKeys("no 224 washington");
		WebElement r1 = driver
				.findElement(By.xpath("//div[@id='p-address-phone']/div/div/div[2]/div/div[1]/div[2]/input[5]"));
		visbility(driver, r1, 60);
		r1.sendKeys("arizona");
		WebElement sr = driver
				.findElement(By.xpath("//div[@id='p-address-phone']/div/div/div[2]/div/div[1]/div[2]/input[6]"));
		visbility(driver, sr, 60);
		sr.sendKeys("los");

		while (true) {
			try {
				WebElement selcntry = driver.findElement(By.xpath("//select[@class='edit-select country']"));
				visbility(driver, selcntry, 60);
				dropDown("index", selcntry, "03");
				break;
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		while (true) {
			try {
				WebElement asd = driver.findElement(
						By.xpath("//select[@class='edit-select country']//following::label[1]//following::select[1]"));
				visbility(driver, asd, 60);
				dropDown("index", asd, "05");
				break;
			} catch (Exception e) {
				// TODO: handle exception
			}
		}

		WebElement r2 = driver
				.findElement(By.xpath("//div[@id='p-address-phone']/div/div/div[2]/div/div[1]/div[2]/input[7]"));
		visbility(driver, r2, 60);

		r2.sendKeys("200786");

		WebElement savecon = driver.findElement(
				By.xpath("//div[@id='p-address-phone']/div/div/div[2]/div/div[2]/div[1]/div[1]/button[2]"));
		visbility(driver, savecon, 60);

		javascriptclick(savecon);

		// alternate contact info...

		while (true) {
			try {

				WebElement ad = driver.findElement(By.xpath(
						"(//div[@id='family-health-patient']/div/div/div[1]/div[1]//following::div[1]/div[1])[1]"));
				visbility(driver, ad, 60);
				javascriptclick(ad);
				break;
			} catch (Exception e) {
				// TODO: handle exception
			}
		}

		while (true) {
			try {
				WebElement r3 = driver
						.findElement(By.xpath("(//span[text()='Alternate Contact'])[2]//following::input[1]"));
				visbility(driver, r3, 60);
				r3.sendKeys("Hope");
				break;
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		WebElement r4 = driver.findElement(By.xpath("(//span[text()='Alternate Contact'])[2]//following::input[2]"));
		visbility(driver, r4, 60);
		r4.sendKeys("2013550367");
		while (true) {
			try {
				WebElement sa = driver
						.findElement(By.xpath("(//span[text()='Alternate Contact'])[2]//following::div[1]/img[1]"));
				visbility(driver, sa, 60);
				actions("click", sa);
				break;
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		sleep(2000);
		// care tream member..

		// WebElement crm =
		// driver.findElement(By.xpath("//div[@id='p-care-team-member']/div/div/div[1]/div/div[2]/div"));
		// javascriptclick(crm);

		// patient info..
		while (true) {
			try {
				WebElement ptin = driver.findElement(By.xpath("//div[@id='patientInfo']/div[1]/span[2]"));
				visbility(driver, ptin, 60);
				javascriptclick(ptin);
				break;
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		while (true) {
			try {
				WebElement r6 = driver.findElement(By.xpath("//label[text()='Occupation']//following::div[1]/input"));
				visbility(driver, r6, 60);
				sendkeys(r6, "tester");
				break;
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		while (true) {
			try {
				WebElement savepatinfo = driver
						.findElement(By.xpath("(//span[text()='Patient Info '])//following::div[1]/img[1]"));
				visbility(driver, savepatinfo, 60);
				javascriptclick(savepatinfo);
				break;
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		driver.navigate().to("https://www.75health.com/health/#list_patient");
		driver.manage().timeouts().pageLoadTimeout(40, TimeUnit.SECONDS);

		while (true) {
			try {
				WebElement u1 = driver.findElement(By.xpath("(//button[@id='advanceIcon'])[1]"));
				visbility(driver, u1, 60);

				j.executeScript("arguments[0].click();", u1);
				break;
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		sleep(2000);
		while (true) {
			try {
				WebElement u2 = driver.findElement(By.xpath("(//input[@id='patient-aname'])[1]"));
				visbility(driver, u2, 60);
				u2.sendKeys("Akash n");
				break;
			} catch (Exception e) {
				// TODO: handle exception
			}
		}

		while (true) {
			try {
				WebElement ellipse = driver.findElement(By.xpath("(//div[@id='list_patient']//following::span[3])[1]"));
				visbility(driver, ellipse, 60);

				j.executeScript("arguments[0].click();", ellipse);
				break;
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		List<WebElement> u3;
		while (true) {
			try {
				u3 = driver.findElements(
						By.xpath("(//div[@id='list_patient']//following::span[3])[1]//following::ul[1]/li"));
				break;
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		for (WebElement t : u3) {
			if (t.getText().trim().equals("Import")) {
				visbility(driver, t, 60);

				t.click();
				break;
			}

		}
		sleep(2000);
		List<WebElement> u5;
		while (true) {
			try {
				u5 = driver.findElements(
						By.xpath("(//ul[@class='dropdown-menu settings-buttonColor settings-heading'])[3]/li"));
				break;
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		for (WebElement w : u5) {
			if (w.getText().trim().equals("CCD Import")) {
				visbility(driver, w, 60);
				w.click();
				break;
			}

		}
		sleep(2000);
		while (true) {
			try {

				WebElement u6 = driver
						.findElement(By.xpath("//span[text()='Import Health Record']/following::div[1]/span"));
				visbility(driver, u6, 60);

				javascriptclick(u6);
				break;
			} catch (Exception e) {
				// TODO: handle exception
			}
		}

		sleep(3000);

	}

	@Test(priority = 2)
	public void HealthRec() throws InterruptedException, IOException {

		while (true) {
			try {
				click(pom.getInstanceHealthRec().clickHealthRec);
				break;
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		sleep(3000);
		implicitWait(60, TimeUnit.SECONDS);
		while (true) {
			try {
				WebElement remv = driver.findElement(
						By.xpath("(//div[@id='ehr-search']/div[2]/div[1]/div[1]/div//following::span[4])[1]"));
				javascriptclick(remv);
				break;
			} catch (Exception e) {
				// TODO: handle exception
			}
		}

		List<WebElement> wwe;
		while (true) {
			try {
				wwe = driver.findElements(By.xpath("//div[@id='vvid']//following::ul[2]/li/a/table/tbody/tr/td[2]"));
				break;
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		for (WebElement web : wwe) {

			if (web.getText().trim().equals(kpid)) {
				visbility(driver, web, 60);
				web.click();
				break;
			}

		}
		sleep(2000);
		while (true) {
			try {
				WebElement newehr = driver.findElement(By.id("newMedicalRecordButton"));
				visbility(driver, newehr, 60);
				click(newehr);// .click();
				break;
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		implicitWait(30, TimeUnit.SECONDS);
		// driver.navigate().refresh();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		sleep(3000);
		List<WebElement> rowfor = driver.findElements(By.xpath("(//div[@id='cols'])[2]/div"));

		int ehrrow = rowfor.size();

		boolean bl = false;
		for (int i = 1; i <= ehrrow; i++) {

			List<WebElement> ds = driver.findElements(By.xpath("(//div[@id='cols'])[2]/div[" + i + "]/div"));

			for (int b = 1; b < ds.size(); b++) {

				WebElement sf = driver.findElement(By.xpath("(//div[@id='cols'])[2]/div[" + i + "]/div[" + b + "]"));

				if (sf.isDisplayed() == false) {

					bl = true;
					driver.findElement(By.xpath("(//button[@id='options-img'])[1]")).click();
					List<WebElement> fin = driver.findElements(By.xpath("(//ul[@id='matchKey'])[2]/li/span/a"));
					driver.findElement(By.xpath("(//input[@id='optionsSearch'])[2]")).sendKeys("show");
					implicitWait(30, TimeUnit.SECONDS);

					for (WebElement web : fin) {

						if (web.getText().trim().equals("Show Allergy")) {

							actions("click", web);

						}
						if (web.getText().trim().equals("Show Alert")) {

							actions("click", web);

						}
						if (web.getText().trim().equals("Show Social History")) {

							actions("click", web);

						}
						if (web.getText().trim().equals("Show Family Health")) {

							actions("click", web);

						}
						if (web.getText().trim().equals("Show Symptoms")) {

							actions("click", web);

						}
						if (web.getText().trim().equals("Show Problems")) {

							actions("click", web);

						}
						if (web.getText().trim().equals("Show Vital")) {

							actions("click", web);

						}
						if (web.getText().trim().equals("Show Visit Reason")) {

							actions("click", web);

						}
						if (web.getText().trim().equals("Show Procedure")) {

							actions("click", web);

						}
						if (web.getText().trim().equals("Show Medications")) {

							actions("click", web);

						}
						if (web.getText().trim().equals("Show Test Order")) {

							actions("click", web);

						}
						if (web.getText().trim().equals("Show Note")) {

							actions("click", web);

						}
						if (web.getText().trim().equals("Show Vaccine")) {

							actions("click", web);

						}
						if (web.getText().trim().equals("Show Attach File")) {

							actions("click", web);

						}
						if (web.getText().trim().equals("Show Inpatient")) {

							actions("click", web);

						}
						if (web.getText().trim().equals("Show Referral")) {

							actions("click", web);

						}
						if (web.getText().trim().equals("Show Custom-form")) {

							actions("click", web);

						}
						if (web.getText().trim().equals("Show Goals")) {

							actions("click", web);

						}
						if (web.getText().trim().equals("Show Amendment")) {

							actions("click", web);

						}
						if (web.getText().trim().equals("Show Implantable Devices")) {

							actions("click", web);

						}
						if (web.getText().trim().equals("Show Advance Directives")) {

							actions("click", web);

						}
						if (web.getText().trim().equals("Show Physical Examination")) {

							actions("click", web);

						}
						if (web.getText().trim().equals("Show Status")) {

							actions("click", web);

						} else {

							continue;
						}

					}

				}

			}
			if (bl == true) {
				driver.findElement(By.xpath("/html/body/div[9]/div/div[2]/div[2]/button")).click();
				break;
			}
		}

		for (int i = 1; i <= ehrrow; i++) {

			List<WebElement> qf = driver.findElements(By.xpath("(//div[@id='cols'])[2]/div[" + i + "]/div"));
			for (int j = 1; j <= qf.size(); j++) {

				WebElement gettag = driver
						.findElement(By.xpath("(//div[@id='cols'])[2]/div[" + i + "]/div[" + j + "]"));

				String tagnames = gettag.getAttribute("id");

				if (tagnames.equals("vital")) {
					sleep(4000);
					implicitWait(30, TimeUnit.SECONDS);

					driver.findElement(By.xpath("(//button[contains(@title,'Add Multiple Vitals')])[1]")).click();

					driver.findElement(By.id("wresult")).sendKeys("55");

					WebElement sel = driver.findElement(By.xpath("(//select[@id='unit'])[1]"));
					visbility(driver, sel, 60);
					sel.click();
					sleep(2000);
					dropDown("text", sel, "kilograms");

					driver.findElement(By.id("hresult")).sendKeys("7'7");

					WebElement hdrp = driver.findElement(By.xpath("(//select[@id='unit'])[2]"));
					visbility(driver, hdrp, 60);
					hdrp.click();

					dropDown("text", hdrp, "ft-in");

					WebElement edity = driver.findElement(By.xpath("(//button[@id='accept-btn'])[1]"));
					visbility(driver, edity, 60);
					javascriptclick(edity);

					sleep(2000);
					WebElement edicon = driver.findElement(By.xpath("(//span[text()='55 kilograms'])[1]"));
					visbility(driver, edicon, 60);
					actions("click", edicon);
					sleep(2000);
					driver.findElement(By.xpath("//input[@id='wresult']")).clear();
					driver.findElement(By.xpath("//input[@id='wresult']")).sendKeys("59");

					driver.findElement(By.xpath("(//button[@id='accept-btn'])[1]")).click();
					sleep(3000);
					/*
					 * WebElement mv =
					 * driver.findElement(By.xpath("//span[text()='59 kilograms']"));
					 * actions("click", mv); WebElement mm =
					 * driver.findElement(By.xpath("(//span[@id='del-btn'])[1]")); actions("click",
					 * mm); sleep(2000)
					 */;

				} else if (tagnames.equals("visit-reason")) {
					implicitWait(60, TimeUnit.SECONDS);

					WebElement u = driver.findElement(By.xpath("//div[contains(@title,'Add Visit R')]"));

					actions("move to element", u);
					visbility(driver, u, 60);
					javascriptclick(u);
					WebElement y = driver.findElement(By.xpath("//select[@id='triage-type']"));
					visbility(driver, y, 60);
					y.click();

					List<WebElement> ff = driver.findElements(By.xpath("//select[@id='triage-type']/option"));
					for (WebElement w : ff) {
						if (w.getText().trim().equals("Emergency")) {
							visbility(driver, w, 60);
							w.click();
							break;
						}

					}
					sleep(2000);
					WebElement mj = driver
							.findElement(By.xpath("//div[@title='Enter the description of the patient visit']"));
					visbility(driver, mj, 60);
					mj.sendKeys("cold");

					WebElement svg = driver
							.findElement(By.xpath("//div[@id='Visit_ReasonKpop2']/div[2]/div[2]/button[2]"));
					visbility(driver, svg, 60);
					//// div[@title='Enter the description of the patient
					//// visit']//following::div[28]/button[2]

					svg.click();
					sleep(2000);
					WebElement afk = driver.findElement(By.xpath("(//div[text()='cold'])[1]"));
					visbility(driver, afk, 60);

					actions("click", afk);

					implicitWait(30, TimeUnit.SECONDS);
					WebElement mjj = driver
							.findElement(By.xpath("//div[@title='Enter the description of the patient visit']"));
					visbility(driver, mjj, 60);

					mjj.clear();
					mjj.sendKeys("KAASPRO");

					WebElement svg1 = driver
							.findElement(By.xpath("//div[@id='Visit_ReasonKpop2']/div[2]/div[2]/button[2]"));
					visbility(driver, svg1, 60);
					svg1.click();

					/*
					 * WebElement delvis = driver
					 * .findElement(By.xpath("//div[@id='Visit_ReasonKpop2']/div[1]/div[2]/span"));
					 * javascriptclick(delvis);
					 */
					sleep(3000);

				} else if (tagnames.equals("alert-allergy")) {

					WebElement add = driver.findElement(By.xpath("//div[contains(@title,'Add Allergy')]"));

					actions("move to element", add);
					visbility(driver, add, 60);
					actions("click", add);
					WebElement se = driver.findElement(By.xpath("//select[@id='codeType']"));
					visbility(driver, se, 60);

					se.click();
					dropDown("text", se, "Food Allergy");

					/* WebElement alcl = */ WebElement fd1 = driver
							.findElement(By.xpath("//div[@id='AllergyKpop2']/div[2]/div[1]/div[2]/div[2]/input"));
					visbility(driver, fd1, 60);
					fd1.sendKeys("food1");

					sleep(2000);
					WebElement alrgy = driver.findElement(By.xpath("//input[@placeholder='Reaction']"));
					visbility(driver, alrgy, 60);
					alrgy.sendKeys("stomach pain");

					WebElement rer = driver.findElement(By.xpath("//div[@id='AllergyKpop2']/div[2]/div[2]/div/button"));
					visbility(driver, rer, 60);

					javascriptclick(rer);

					sleep(3000);
					List<WebElement> wtw = driver.findElements(By.xpath("//div[@id='smore-btn']/ul/li"));
					for (WebElement w : wtw) {
						if (w.getText().trim().equals("Show Severity")) {
							w.click();
							break;
						}

					}
					sleep(2000);

					javascriptclick(rer);

					for (WebElement w : wtw) {
						if (w.getText().trim().equals("Show Status")) {
							visbility(driver, rer, 60);
							w.click();
							break;
						}

					}
					WebElement s = driver.findElement(By.xpath("//select[@id='severity']"));

					dropDown("text", s, "Mild");
					sleep(2000);
					WebElement ss = driver.findElement(By.xpath("//select[@id='status']"));

					dropDown("text", ss, "Inactive");

					WebElement cl2 = driver.findElement(By.xpath("//div[@id='saveadd-btn']/button"));
					visbility(driver, cl2, 60);
					cl2.click();
					List<WebElement> sss = driver.findElements(By.xpath("//div[@id='saveadd-btn']/ul/li"));
					for (WebElement w : sss) {
						if (w.getText().trim().equals("Save")) {
							visbility(driver, w, 60);
							w.click();
							break;
						}

					}
					sleep(2000);
					WebElement mk = driver.findElement(By.xpath("//span[text()='food1']"));
					visbility(driver, mk, 60);

					actions("click", mk);
					sleep(2000);
					WebElement jk = driver
							.findElement(By.xpath("//div[@id='AllergyKpop2']/div[2]/div[1]/div[2]/div[2]/input"));
					visbility(driver, jk, 60);
					jk.clear();
					jk.sendKeys("st");
					sleep(2000);
					WebElement scq = driver.findElement(By.xpath("//div[text()='Strawberry ']"));
					visbility(driver, scq, 60);

					actions("click", scq);

					WebElement cl5 = driver.findElement(By.xpath("//div[@id='saveadd-btn']/button"));
					visbility(driver, cl5, 60);
					cl5.click();
					List<WebElement> ssss = driver.findElements(By.xpath("//div[@id='saveadd-btn']/ul/li"));
					for (WebElement w : ssss) {
						if (w.getText().trim().equals("Save")) {
							visbility(driver, w, 60);
							w.click();
							break;
						}

					}

					sleep(3000);

					// Social history....

					WebElement sh = driver.findElement(By.xpath("//div[contains(@title,'Add Social History')]"));
					actions("move to element", sh);
					visbility(driver, sh, 60);
					actions("click", sh);
					sleep(2000);
					WebElement ssc = driver.findElement(By.xpath("//select[@id='habitType']"));
					visbility(driver, ssc, 60);
					ssc.click();
					dropDown("text", ssc, "Alcohol");
					WebElement hbt = driver.findElement(By.xpath("//select[@id='habitType']//following::div[3]/input"));
					visbility(driver, hbt, 60);
					hbt.sendKeys("social histry");

					WebElement hbt2 = driver.findElement(By.xpath("(//button[@id='accept-btn'])[1]"));
					visbility(driver, hbt2, 60);
					hbt2.click();
					sleep(2000);
					WebElement jj = driver.findElement(By.xpath("//div[text()='( Alcohol )']"));
					visbility(driver, jj, 60);

					actions("click", jj);
					sleep(2000);
					WebElement hbt4 = driver
							.findElement(By.xpath("//select[@id='habitType']//following::div[3]/input"));
					visbility(driver, hbt4, 60);
					hbt4.clear();
					// .clear();
					WebElement kspr = driver
							.findElement(By.xpath("//select[@id='habitType']//following::div[3]/input"));
					visbility(driver, kspr, 60);
					kspr.sendKeys("Kaaspro");
					WebElement hbt5 = driver.findElement(By.xpath("(//button[@id='accept-btn'])[1]"));
					visbility(driver, hbt5, 60);
					hbt5.click();

					sleep(3000);

					// Family Health...

					WebElement a = driver.findElement(By.xpath("//div[contains(@title,'Add Family Health')]"));

					actions("move to element", a);
					visbility(driver, a, 60);

					actions("click", a);
					WebElement ee = driver.findElement(By.xpath("//div[@id='Family_HealthKpop2']/div[2]/div/select"));
					visbility(driver, ee, 60);
					ee.click();
					dropDown("text", ee, "Half Brother");

					WebElement fh = driver.findElement(By.xpath(
							"//div[@id='Family_HealthKpop2']/div[2]/div/select//following::div[1]/div[2]/input"));
					visbility(driver, fh, 60);
					fh.sendKeys("24781");
					List<WebElement> fhkpop;

					while (true) {
						fhkpop = driver
								.findElements(By.xpath("//div[@id='Family_HealthKpop2']/div[2]/ul/li/a/div/small"));

						// System.out.println("element has been finded.." + "size is:" + fhkpop.size());
						if (fhkpop.size() > 5) {

							break;
						}

					}
					boolean $familcond$ = false;
					for (WebElement web : fhkpop) {

						if (web.getText().trim().equals("ICD10 : F40.2 | SNOMED : 247810008")) {
							$familcond$ = true;
							visbility(driver, web, 60);
							click(web);

							break;
						}

					}
					if ($familcond$ == true) {
						WebElement atf = driver.findElement(By.xpath("//button[@id='btnSaveAdd']"));
						visbility(driver, atf, 60);
						javascriptclick(atf);
						List<WebElement> rr = driver.findElements(By.xpath("//div[@id='saveadd-btn']/ul/li"));
						for (WebElement w : rr) {

							if (w.getText().trim().equals("Save")) {
								visbility(driver, w, 60);
								w.click();
								break;
							}

						}
					}

					sleep(3000);
					// Alert...
					WebElement $alert$;
					while (true) {
						try {

							$alert$ = driver.findElement(By.xpath("//div[contains(@title,'Add Alert')]"));

							System.out.println("finded the alert icon");

							break;
						} catch (StaleElementReferenceException e) {
							// TODO: handle exception
						}
					}

					while (true) {

						try {
							actions("move to element", $alert$);

							System.out.println("element is visble able to click");
							break;
						} catch (Exception e) {

						}
					}
					while (true) {
						try {
							System.out.println("hello exception occurs here...");
							actions("click", $alert$);
							;
							break;
						} catch (Exception e) {
							// TODO: handle exception
						}
					}

					while (true) {
						try {
							WebElement fh2 = driver
									.findElement(By.xpath("//div[@title='Enter the description of the alert ']"));
							visbility(driver, fh2, 60);
							fh2.sendKeys("hello");
							break;
						} catch (Exception e) {
							// TODO: handle exception
						}
					}
					WebElement r = driver.findElement(By.xpath("//select[@id='visibility']"));

					click(r);
					dropDown("text", r, "Only to me");
					WebElement hbt6 = driver.findElement(By.xpath("//div[@id='AlertKpop2']/div[2]/div[2]/button[2]"));// .click();
					visbility(driver, hbt6, 60);
					click(hbt6);
					sleep(3000);
					WebElement $alertContent$;
					while (true) {
						try {
							$alertContent$ = driver.findElement(By.xpath("//div[text()='hello']"));
							break;
						} catch (Exception e) {
							// TODO: handle exception
						}
					}

					visbility(driver, $alertContent$, 60);

					javascriptclick($alertContent$);
					WebElement $contentchng$ = null;
					while (true) {
						try {
							$contentchng$ = driver
									.findElement(By.xpath("//div[@title='Enter the description of the alert ']"));
							break;
						} catch (Exception e) {
							// TODO: handle exception
						}
					}
					visbility(driver, $contentchng$, 60);
					clear($contentchng$);
					WebElement alrt2 = driver
							.findElement(By.xpath("//div[@title='Enter the description of the alert ']"));
					visbility(driver, alrt2, 60);
					sendkeys(alrt2, "wELCOME");
					WebElement alrt3 = driver.findElement(By.xpath("//div[@id='AlertKpop2']/div[2]/div[2]/button[2]"));// .click();
					visbility(driver, alrt3, 60);
					click(alrt3);
					sleep(3000);
				} else if (tagnames.equals("vaccine")) {

					WebElement k = driver.findElement(By.xpath("//div[contains(@title,'Add Vaccine')]"));
					actions("move to element", k);
					visbility(driver, k, 60);

					actions("click", k);
					WebElement x = driver.findElement(By.xpath("//select[@id='date-type']"));
					visbility(driver, x, 60);
					x.click();
					dropDown("text", x, "Taken Date");
					WebElement vcne = driver.findElement(By.id("vaccine-cvx"));
					visbility(driver, vcne, 60);
					vcne.sendKeys("kaaspro");

					WebElement vcne2 = driver.findElement(By.id("vaccineName"));
					visbility(driver, vcne2, 60);
					vcne2.sendKeys("TT");
					WebElement sv = driver
							.findElement(By.xpath("//div[@id='VaccineKpop2']/div[2]/div[3]/button[@id='accept-btn']"));
					visbility(driver, sv, 60);
					javascriptclick(sv);
					sleep(3000);
					WebElement l = driver.findElement(By.xpath("//div[text()='TT']"));
					visbility(driver, l, 60);
					actions("click", l);

					WebElement vcna = driver.findElement(By.id("vaccineName"));// .clear();
					visbility(driver, vcna, 60);
					vcna.clear();
					WebElement vcna1 = driver.findElement(By.id("vaccineName"));// .sendKeys("TT INJECTION");
					visbility(driver, vcna1, 60);
					sendkeys(vcna1, "TT INJECTION");
					implicitWait(60, TimeUnit.SECONDS);
					/*
					 * WebElement delvc = driver .findElement(By.xpath(
					 * "(//div[@id='VaccineKpop2']//following::div[1]/span[1])[1]"));
					 * javascriptclick(delvc);
					 */
					WebElement vcc = driver
							.findElement(By.xpath("//div[@id='VaccineKpop2']/div[2]/div[3]/button[@id='accept-btn']"));
					visbility(driver, vcc, 60);
					click(vcc);

					sleep(3000);

				} else if (tagnames.equals("implantable-devices")) {

					WebElement b = driver.findElement(By.xpath("//div[contains(@title,'Add Implantable')]"));
					actions("move to element", b);
					visbility(driver, b, 60);
					actions("click", b);
					WebElement impl = driver.findElement(By.id("udi"));// .sendKeys("(01)00844588003288");
					visbility(driver, impl, 60);
					sendkeys(impl, "(01)00844588003288");
					WebElement svimpl = driver.findElement(By.xpath("//button[@id='verify-btn']"));// .click();
					visbility(driver, svimpl, 60);
					click(svimpl);
					sleep(3000);
					driver.findElement(By.id("deviceactive"));

					WebElement a1 = driver.findElement(By.id("deviceNote"));
					visbility(driver, a1, 60);
					sleep(1000);
					a1.sendKeys("hello123");
					WebElement savimp = driver
							.findElement(By.xpath("//div[@id='Implantable_DevicesKpop2']/div[2]/div[2]/button[2]"));
					visbility(driver, savimp, 60);
					javascriptclick(savimp);

					sleep(2000);
					WebElement a2 = driver
							.findElement(By.xpath("//div[text()='Coated femoral stem prosthesis, modular']"));
					visbility(driver, a2, 60);

					actions("click", a2);
					WebElement a26 = driver.findElement(By.id("deviceNote"));
					ScriptExecutor(a26);
					visbility(driver, a26, 60);
					a26.clear();
					a26.sendKeys("JUst Rise up..");
					WebElement savimp1 = driver
							.findElement(By.xpath("//div[@id='Implantable_DevicesKpop2']/div[2]/div[2]/button[2]"));
					visbility(driver, savimp1, 60);
					javascriptclick(savimp1);

					/*
					 * driver.findElement(By.xpath("//div[@title='Remove UDI']")).click();
					 * WebElement delimp = driver .findElement(By.xpath(
					 * "//div[@id='Implantable_DevicesKpop2']/div/div[2]/span[1]"));
					 * javascriptclick(delimp);
					 */
					sleep(3000);

				} else if (tagnames.equals("amendment")) {

					WebElement d = driver.findElement(By.xpath("//div[contains(@title,'Add Amendment')]"));

					actions("move to element", d);
					visbility(driver, d, 60);
					clickble(driver, d, 25);
					actions("click", d);
					WebElement s1 = driver.findElement(By.xpath("//select[@id='source']"));
					visbility(driver, s1, 60);
					s1.click();
					dropDown("text", s1, "Patient");
					WebElement amd = driver
							.findElement(By.xpath("//div[@id='AmendmentKpop2']/div[2]/div/div[2]/div[2]/input"));
					visbility(driver, amd, 60);
					sendkeys(amd, "Akash");// .sendKeys("Akash");

					WebElement s2 = driver.findElement(By.xpath("//select[@id='status']"));
					visbility(driver, s2, 60);
					s2.click();
					dropDown("text", s2, "Accept");
					WebElement vs = driver.findElement(By.xpath("//input[@id='reason']"));
					visbility(driver, vs, 60);
					sendkeys(vs, "whats up...");// .sendKeys("whats up...");
					WebElement svamen = driver
							.findElement(By.xpath("//div[@id='AmendmentKpop2']/div[2]/div[2]/button[2]"));
					visbility(driver, svamen, 60);

					javascriptclick(svamen);
					sleep(3000);
					WebElement ac = driver.findElement(By.xpath("//div[text()='Accepted : whats up...']"));
					visbility(driver, ac, 60);
					actions("click", ac);
					sleep(2000);
					WebElement clr = driver.findElement(By.xpath("//input[@id='reason']"));
					visbility(driver, clr, 60);
					clear(clr);// .clear();

					WebElement ips = driver.findElement(By.xpath("//input[@id='reason']"));
					visbility(driver, ips, 60);

					sendkeys(ips, "warrior");// .sendKeys("warrior");
					WebElement iis = driver.findElement(By.xpath("//input[@id='reason']"));// .sendKeys("WAR BEGINS");
					visbility(driver, iis, 60);
					sendkeys(iis, "WAR BEGINS");
					WebElement svamen1 = driver
							.findElement(By.xpath("//div[@id='AmendmentKpop2']/div[2]/div[2]/button[2]"));
					visbility(driver, svamen1, 60);
					javascriptclick(svamen1);
					// driver.findElement(By.xpath("//div[@id='AmendmentKpop2']/div[1]/div[2]/span[1]")).click();
					sleep(3000);

				} else if (tagnames.equals("diagnosis")) {

					WebElement a3 = driver.findElement(By.xpath("//div[contains(@title,'Add Problems')]"));
					actions("move to element", a3);
					visbility(driver, a3, 60);

					actions("click", a3);
					sleep(2000);

					WebElement ct = driver
							.findElement(By.xpath("//div[@id='ProblemsKpop2']/div[2]/div/div[1]/div[2]/input"));
					visbility(driver, ct, 60);
					// driver.findElement(By.xpath("//div[@id='ProblemsKpop2']/div[2]/div/div[1]/div[2]/input"))
					ct.sendKeys("Cleft uvula");

					sleep(3000);
					WebElement prbcl = driver.findElement(By.xpath("//small[text()='ICD10 : Q35.7 | SNOMED : --']"));
					visbility(driver, prbcl, 60);

					actions("click", prbcl);
					sleep(2000);

					WebElement gg = driver.findElement(By.xpath("//button[@id='btnSaveAdd']"));
					visbility(driver, gg, 60);

					javascriptclick(gg);

					List<WebElement> a5 = driver.findElements(By.xpath("//div[@id='saveadd-btn']/ul/li"));
					for (WebElement w : a5) {
						if (w.getText().trim().equals("Save")) {
							visbility(driver, w, 60);
							w.click();
							break;
						}

					}
					sleep(3000);
					WebElement a6 = driver.findElement(By.xpath("//div[text()='Cleft uvula']"));
					visbility(driver, a6, 60);

					actions("click", a6);
					sleep(3000);
					WebElement clr = driver.findElement(
							By.xpath("//div[@id='ProblemsKpop2']/div[2]/div[1]/div[1]/div[2]//following::div[2]"));
					visbility(driver, clr, 60);

					javascriptclick(clr);

					WebElement ljv = driver
							.findElement(By.xpath("//div[@id='ProblemsKpop2']/div[2]/div/div[1]/div[2]/input"));
					// driver.findElement(By.xpath("//div[@id='ProblemsKpop2']/div[2]/div/div[1]/div[2]/input"))
					visbility(driver, ljv, 60);
					ljv.sendKeys("test");
					sleep(2000);

					List<WebElement> prbdrp = driver
							.findElements(By.xpath("//div[@id='ProblemsKpop2']/div[2]/ul/li/a/div/span"));
					for (WebElement web : prbdrp) {

						if (web.getText().trim().equals("Malignant neoplasm of testis")) {
							visbility(driver, web, 60);
							web.click();

							WebElement gg1 = driver.findElement(By.xpath("//button[@id='btnSaveAdd']"));
							visbility(driver, gg1, 60);

							javascriptclick(gg1);
							sleep(3000);
							List<WebElement> a56 = driver.findElements(By.xpath("//div[@id='saveadd-btn']/ul/li"));
							for (WebElement w : a56) {
								if (w.getText().trim().equals("Save")) {
									visbility(driver, w, 60);
									w.click();
									break;
								}

							}
							break;
						}

					}
					sleep(3000);

				} else if (tagnames.equals("symptom")) {

					WebElement a7 = driver.findElement(By.xpath("//div[contains(@title,'Add Symptoms')]"));

					actions("move to element", a7);
					visbility(driver, a7, 60);

					actions("click", a7);
					WebElement prsend = driver
							.findElement(By.xpath("//div[@id='SymptomsKpop2']/div[2]/div/div[2]/div[2]/input"));
					visbility(driver, prsend, 60);
					sendkeys(prsend, "R10.12:");// .sendKeys("R10.12:");
					implicitWait(30, TimeUnit.SECONDS);
					WebElement clsymi = driver
							.findElement(By.xpath("//div[contains(text(),'R10.12: Left upper quadrant pain')]"));
					visbility(driver, clsymi, 60);

					actions("click", clsymi);

					sleep(2000);
					WebElement sydes = driver
							.findElement(By.xpath("//div[@id='SymptomsKpop2']/div[2]/div/div[3]/div[2]/input"));
					// driver.findElement(By.xpath("//div[@id='SymptomsKpop2']/div[2]/div/div[3]/div[2]/input"))
					visbility(driver, sydes, 60);
					sydes.sendKeys("fever");
					WebElement svsymp = driver
							.findElement(By.xpath("//div[@id='SymptomsKpop2']/div[2]/div[2]/button[2]"));
					visbility(driver, svsymp, 60);
					javascriptclick(svsymp);
					sleep(3000);
					WebElement a8 = driver
							.findElement(By.xpath("//div[contains(text(),'R10.12: Left upper quadrant pain')]"));
					visbility(driver, a8, 60);

					actions("click", a8);
					WebElement smp1 = driver
							.findElement(By.xpath("//div[@id='SymptomsKpop2']/div[2]/div/div[3]/div[2]/input"));
					visbility(driver, smp1, 60);
					clear(smp1);// .clear();

					sleep(2000);
					WebElement smp2 = driver
							.findElement(By.xpath("//div[@id='SymptomsKpop2']/div[2]/div/div[3]/div[2]/input"));
					visbility(driver, smp2, 60);
					sendkeys(smp2, "covid");// .sendKeys("covid");

					WebElement svsymp1 = driver
							.findElement(By.xpath("//div[@id='SymptomsKpop2']/div[2]/div[2]/button[2]"));
					visbility(driver, svsymp1, 60);

					javascriptclick(svsymp1);
					// driver.findElement(By.xpath("//div[@id='SymptomsKpop2']/div/div[2]/span[1]")).click();
					sleep(4000);

				} else if (tagnames.equals("procedure")) {

					WebElement b1 = driver.findElement(By.xpath("//div[contains(@title,'Add Procedure')]"));
					actions("move to element", b1);
					visbility(driver, b1, 60);

					actions("click", b1);

					WebElement b2 = driver.findElement(By.xpath("//select[@id='codeType']"));
					visbility(driver, b2, 60);

					b2.click();
					dropDown("text", b2, "SNOMED CT");
					WebElement ers = driver
							.findElement(By.xpath("//div[@id='ProcedureKpop2']/div[2]/div[1]/div[1]/div[2]/input"));
					visbility(driver, ers, 60);
					// driver.findElement(By.xpath("//div[@id='ProcedureKpop2']/div[2]/div[1]/div[1]/div[2]/input"))
					ers.sendKeys("test");
					sleep(2000);
					WebElement clprcd = driver
							.findElement(By.xpath("//b[text()='Cytomegalovirus antigen test (procedure)']"));
					visbility(driver, clprcd, 60);

					sleep(2000);
					actions("click", clprcd);
					WebElement smp3 = driver
							.findElement(By.xpath("//div[@id='ProcedureKpop2']/div[2]/div/div[2]/div[2]/input"));
					visbility(driver, smp3, 60);
					sendkeys(smp3, "gdgdg");// .sendKeys("gdgdg");

					WebElement svprcd1 = driver.findElement(By.id("btnSaveAdd"));
					visbility(driver, svprcd1, 60);
					click(svprcd1);// .click();
					sleep(2000);
					List<WebElement> b6 = driver.findElements(By.xpath("//div[@id='saveadd-btn']/ul/li"));
					for (WebElement w : b6) {
						if (w.getText().trim().equals("Save")) {
							visbility(driver, w, 60);
							w.click();

							break;
						}

					}
					sleep(3000);
					WebElement b7 = driver.findElement(
							By.xpath("//div[text()='134287002: Cytomegalovirus antigen test (procedure)']"));
					visbility(driver, b7, 60);
					javascriptclick(b7);
					// actions("click", b7);
					sleep(2000);
					WebElement clrprc = driver
							.findElement(By.xpath("//div[@id='ProcedureKpop2']/div[2]/div/div[2]/div[2]/input"));
					visbility(driver, clrprc, 60);
					clear(clrprc);// .clear();
					WebElement prcd3 = driver
							.findElement(By.xpath("//div[@id='ProcedureKpop2']/div[2]/div/div[2]/div[2]/input"));
					visbility(driver, prcd3, 60);

					sendkeys(prcd3, "LARA");// .sendKeys("LARA");

					WebElement prcd4 = driver.findElement(By.id("btnSaveAdd"));
					visbility(driver, prcd4, 60);
					click(prcd4);// .click();
					sleep(3000);
					List<WebElement> b64 = driver.findElements(By.xpath("//div[@id='saveadd-btn']/ul/li"));
					for (WebElement w : b64) {
						if (w.getText().trim().equals("Save")) {
							visbility(driver, w, 60);
							w.click();
							break;
						}

					}

					// driver.findElement(By.xpath("//div[@id='ProcedureKpop2']/div[1]/div[2]/span[1]")).click();
					sleep(4000);

				} else if (tagnames.equals("goals")) {
					implicitWait(30, TimeUnit.SECONDS);
					WebElement b8 = driver.findElement(By.xpath("//div[contains(@title,'Add Goals')]"));
					actions("move to element", b8);
					visbility(driver, b8, 60);

					actions("click", b8);
					sleep(2000);
					WebElement gl1 = driver.findElement(By.xpath("//div[@title='Enter goal']"));
					visbility(driver, gl1, 60);
					sendkeys(gl1, "goal1");// .sendKeys("goal1");

					WebElement gl2 = driver
							.findElement(By.xpath("//div[@id='GoalsKpop2']/div[2]/div[1]/div[2]/div/input"));// .click();
					visbility(driver, gl2, 60);
					click(gl2);
					sleep(2000);
					WebElement month = driver.findElement(By.xpath("//select[@class='ui-datepicker-month']"));

					implicitWait(30, TimeUnit.SECONDS);

					dropDown("index", month, "09");

					WebElement uyr = driver.findElement(By.xpath("//a[text()='14']"));// .click();
					visbility(driver, uyr, 60);
					click(uyr);
					sleep(2000);
					WebElement hk = driver.findElement(By.xpath("//div[@id='GoalsKpop2']/div[2]/div[2]/button[2]"));
					visbility(driver, hk, 60);
					javascriptclick(hk);
					sleep(3000);
					WebElement b10 = driver.findElement(By.xpath("//div[text()='goal1']"));
					visbility(driver, b10, 60);

					actions("click", b10);
					implicitWait(60, TimeUnit.SECONDS);

					WebElement ft = driver.findElement(By.xpath("//div[@title='Enter goal']"));// .clear();
					visbility(driver, ft, 60);
					clear(ft);
					WebElement glr1 = driver.findElement(By.xpath("//div[@title='Enter goal']"));
					// .sendKeys("HELLO THIS IS GOALS MODULE.");
					visbility(driver, glr1, 60);
					sendkeys(glr1, "HELLO THIS IS GOALS MODULE.");
					WebElement hk1 = driver.findElement(By.xpath("//div[@id='GoalsKpop2']/div[2]/div[2]/button[2]"));
					visbility(driver, hk1, 60);
					javascriptclick(hk1);

					/*
					 * WebElement jl =
					 * driver.findElement(By.xpath("//div[@id='GoalsKpop2']/div/div[2]/span[1]"));
					 * 
					 * javascriptclick(jl);
					 */
					sleep(4000);

				} else if (tagnames.equals("directives")) {

					WebElement c1 = driver.findElement(By.xpath("//div[contains(@title,'Add Advance directives')]"));
					actions("move to element", c1);
					visbility(driver, c1, 60);

					actions("click", c1);

					WebElement c2 = driver.findElement(By.xpath("//div[@id='Assessment-div']/select"));
					visbility(driver, c2, 60);
					c2.click();
					dropDown("text", c2, "Assessment");
					WebElement cc1 = driver.findElement(By.xpath("//input[@id='directive_desc']"));
					visbility(driver, cc1, 60);
					sendkeys(cc1, "lets hope");// .sendKeys("lets hope");
					WebElement cc2 = driver
							.findElement(By.xpath("//div[@id='Advance_DirectivesKpop2']/div[2]/div[2]/button[2]"));
					visbility(driver, cc2, 60);
					click(cc2);// .click();
					sleep(3000);
					WebElement c4 = driver.findElement(By.xpath("//div[text()='lets hope']"));
					visbility(driver, c4, 60);
					actions("click", c4);
					sleep(2000);
					WebElement cc5 = driver.findElement(By.xpath("//input[@id='directive_desc']"));
					visbility(driver, cc5, 60);
					sendkeys(cc5, "Advance directives");// .sendKeys("Advance directives");
					WebElement cc6 = driver
							.findElement(By.xpath("//div[@id='Advance_DirectivesKpop2']/div[2]/div[2]/button[2]"));
					visbility(driver, cc6, 60);
					click(cc6);// .click();

					/*
					 * WebElement deladvfac = driver .findElement(By.xpath(
					 * "//div[@id='Advance_DirectivesKpop2']/div/div[2]/span[1]"));
					 * javascriptclick(deladvfac);
					 */
					sleep(3000);

				} else if (tagnames.equals("status-module")) {

					WebElement c5 = driver.findElement(By.xpath("//div[contains(@title,'Add Status')]"));
					actions("move to element", c5);
					visbility(driver, c5, 60);

					actions("click", c5);
					WebElement c6 = driver.findElement(By.xpath("(//select[@id='statusType'])[1]"));
					visbility(driver, c6, 60);
					c6.click();
					dropDown("text", c6, "Cognitive status");
					WebElement cc7 = driver.findElement(By
							.xpath("//div[@id='StatusKpop2']/div[2]/div[1]/select[1]//following::div[1]/div[2]/input"));
					visbility(driver, cc7, 60);
					sendkeys(cc7, "test");// .sendKeys("test");
					sleep(2000);
					WebElement clsmd = driver
							.findElement(By.xpath("//div[text()='134374006: Hearing test bilateral abnormality ']"));
					visbility(driver, clsmd, 60);
					sleep(3000);

					actions("click", clsmd);
					WebElement cc8 = driver.findElement(By.xpath("//div[@id='StatusKpop2']/div[2]/div[2]/button[2]"));// .click();
					visbility(driver, cc8, 60);
					click(cc8);
					sleep(4000);
					WebElement c7 = driver
							.findElement(By.xpath("//div[text()='134374006: Hearing test bilateral abnormality']"));
					visbility(driver, c7, 60);

					actions("click", c7);
					sleep(3000);
					WebElement hjj = driver
							.findElement(By.xpath("//div[@id='StatusKpop2']/div[2]/div[1]/select//following::span[5]"));
					visbility(driver, hjj, 60);
					actions("click", hjj);
					WebElement cc9 = driver.findElement(By
							.xpath("//div[@id='StatusKpop2']/div[2]/div[1]/select[1]//following::div[1]/div[2]/input"));
					visbility(driver, cc9, 60);
					sendkeys(cc9, "yang");// .sendKeys("yang");
					sleep(2000);
					WebElement yng = driver.findElement(By.xpath("//div[text()='370534002: Yang deficiency ']"));
					visbility(driver, yng, 60);
					actions("click", yng);
					WebElement cc10 = driver.findElement(By.xpath("//div[@id='StatusKpop2']/div[2]/div[2]/button[2]"));// .click();
					visbility(driver, cc10, 60);
					click(cc10);
					/*
					 * WebElement delsmd =
					 * driver.findElement(By.xpath("//div[@id='StatusKpop2']/div[1]/div[2]/span[1]")
					 * ); javascriptclick(delsmd);
					 */
					sleep(3000);

				} else if (tagnames.equals("")) {

					/*
					 * WebElement ad1 =
					 * driver.findElement(By.xpath("//div[contains(@title,'Add Test Order')]"));
					 * 
					 * actions("move to element", ad1); actions("click", ad1);
					 * driver.findElement(By.xpath(
					 * "//div[@id='Test_OrderKpop2']/div[2]/div[1]/div[1]/div[2]/input"))
					 * .sendKeys("test"); sleep(4000); WebElement b =
					 * driver.findElement(By.xpath("(//div[text()='test'])")); actions("click", b);
					 */
					/*
					 * List<WebElement> tyr = driver.findElements( By.xpath(
					 * "//div[@id='Test_OrderKpop2']/div[2]/div[1]//following::ul[3]/li/a/div/span")
					 * ); for (WebElement webE : tyr) { if
					 * (webE.getText().contains("LOINC NUM :5802-4")) { webE.click(); break; }
					 * 
					 * }
					 */
					/*
					 * sleep(2000);
					 * 
					 * driver.findElement(By.xpath(
					 * "//div[@id='Test_OrderKpop2']/div[2]/div[2]/div/button")).click();
					 * List<WebElement> chs = driver.findElements( By.xpath(
					 * "//div[@id='Test_OrderKpop2']/div[2]/div[2]/div/button//following::ul[1]/li")
					 * ); for (WebElement w : chs) {
					 * 
					 * if (w.getText().trim().equals("Show Notes")) { w.click(); break; }
					 * 
					 * }
					 */

					/*
					 * sleep(3000); driver.findElement(By.xpath(
					 * 
					 * "//div[@id='Test_OrderKpop2']/div[2]/div[1]/div[1]/div[2]//following::div[1]//following::div[1]/div[2]/input"
					 * )) .sendKeys("ERROR"); driver.findElement( By.xpath(
					 * "//div[@id='Test_OrderKpop2']/div[2]/div[2]/div[1]//following::div[2]/button"
					 * )) .click(); List<WebElement> dss = driver.findElements(By.xpath(
					 * "//div[@id='Test_OrderKpop2']/div[2]/div[2]/div[1]//following::div[2]/button//following::ul[1]/li"
					 * )); for (WebElement w : dss) { if (w.getText().trim().equals("Save")) {
					 * 
					 * w.click(); break; }
					 * 
					 * }
					 */
					/*
					 * sleep(3000); WebElement testorder =
					 * driver.findElement(By.xpath("//div[text()='ERROR']")); actions("click",
					 * testorder);
					 * 
					 * driver.findElement(By.xpath(
					 * 
					 * "//div[@id='Test_OrderKpop2']/div[2]/div[1]/div[1]/div[2]//following::div[1]//following::div[1]/div[2]/input"
					 * )) .clear(); driver.findElement(By.xpath(
					 * 
					 * "//div[@id='Test_OrderKpop2']/div[2]/div[1]/div[1]/div[2]//following::div[1]//following::div[1]/div[2]/input"
					 * )) .sendKeys("Test order..");
					 * 
					 * driver.findElement( By.xpath(
					 * "//div[@id='Test_OrderKpop2']/div[2]/div[2]/div[1]//following::div[2]/button"
					 * )) .click(); List<WebElement> dsss = driver.findElements(By.xpath(
					 * "//div[@id='Test_OrderKpop2']/div[2]/div[2]/div[1]//following::div[2]/button//following::ul[1]/li"
					 * )); for (WebElement w : dsss) { if (w.getText().trim().equals("Save")) {
					 * 
					 * w.click(); break; }
					 * 
					 * }
					 */

					/*
					 * WebElement de = driver.findElement(By.xpath(
					 * "//div[@id='Test_OrderKpop2']/div[1]/div[2]/span[2]")); sleep(2000);
					 * actions("click", de);
					 */
					sleep(3000);

				} else if (tagnames.equals("drug")) {

					WebElement ci = driver.findElement(By.xpath("(//div[contains(@title,'Add Medications')])[1]"));
					actions("move to element", ci);
					visbility(driver, ci, 60);

					actions("click", ci);
					sleep(2000);
					driver.findElement(By.id("DRUG_NAME")).sendKeys("tata");
					driver.findElement(By.id("STRENGTH")).sendKeys("str");
					driver.findElement(By.id("DISP_QUANTITY")).sendKeys("1");
					driver.findElement(By.id("SIG_DIRECTIONS")).sendKeys("q1");
					List<WebElement> medq = driver
							.findElements(By.xpath("//div[@id='addfav-div']//following::ul[1]/li/a/div"));
					for (WebElement web : medq) {
						if (web.getText().trim().equals("q12h - Every twelve hours")) {
							visbility(driver, web, 60);
							javascriptclick(web);
							break;
						}

					}
					// driver.findElement(By.id("startdateiid")).sendKeys("2022-07-20");
					// driver.findElement(By.id("enddateiid")).sendKeys("2022-07-22");
					WebElement dd1 = driver
							.findElement(By.xpath("//div[@id='MedicationsKpop2']/div[2]/div[3]/div[1]/button"));// .click();
					visbility(driver, dd1, 60);
					click(dd1);
					sleep(2000);
					List<WebElement> d1 = driver.findElements(
							By.xpath("//div[@id='MedicationsKpop2']/div[2]/div[3]/div[1]/button//following::ul[1]/li"));
					for (WebElement w : d1) {

						if (w.getText().trim().equals("Save")) {
							visbility(driver, w, 60);
							w.click();
							break;
						}

					}

					sleep(3000);
					WebElement d3 = driver.findElement(By.xpath("//div[text()='q12h - Every twelve hours']"));
					visbility(driver, d3, 60);

					actions("click", d3);
					sleep(2000);

					/*
					 * WebElement delmed = driver
					 * .findElement(By.xpath("//div[@id='MedicationsKpop2']/div[1]/div[2]/span[1]"))
					 * ; javascriptclick(delmed);
					 */
					WebElement rqw = driver.findElement(By.xpath(
							"//div[@id='MedicationsKpop2']/div[2]/div[1]/div[1]/div[3]/table/tbody/tr/td[2]/div"));
					visbility(driver, rqw, 60);
					javascriptclick(rqw);
					sleep(2000);
					WebElement jsk = driver.findElement(By.id("DRUG_NAME"));
					visbility(driver, jsk, 60);
					sendkeys(jsk, "1190953");
					sleep(2500);

					List<WebElement> meddrpdwm = driver.findElements(By
							.xpath("//div[@id='MedicationsKpop2']/div[2]/div[3]//following::ul[2]/li/a/div/small/em"));

					for (WebElement wet : meddrpdwm) {

						if (wet.getText().trim().equals("RXNORM : 1190953")) {
							visbility(driver, wet, 60);
							javascriptclick(wet);
							break;
						}

					}
					/*
					 * WebElement rt = driver.findElement(
					 * By.xpath("//b[text()='testosterone enanthate 100 MG/ML Injectable Solution']"
					 * )); visbility(driver, rt, 60); javascriptclick(rt);
					 */

					driver.findElement(By.id("DISP_QUANTITY")).sendKeys("1");
					driver.findElement(By.id("SIG_DIRECTIONS")).sendKeys("12");

					List<WebElement> med2 = driver
							.findElements(By.xpath("//div[@id='addfav-div']//following::ul[1]/li/a/div"));
					for (WebElement web : med2) {
						if (web.getText().trim().equals("q12h - Every twelve hours")) {
							visbility(driver, web, 60);
							web.click();
							break;
						}

					}

					WebElement dd2 = driver
							.findElement(By.xpath("//div[@id='MedicationsKpop2']/div[2]/div[3]/div[1]/button"));// .click();
					visbility(driver, dd2, 60);
					click(dd2);
					sleep(2000);
					List<WebElement> d2 = driver.findElements(
							By.xpath("//div[@id='MedicationsKpop2']/div[2]/div[3]/div[1]/button//following::ul[1]/li"));
					for (WebElement w : d2) {

						if (w.getText().trim().equals("Save")) {
							visbility(driver, w, 60);
							w.click();
							break;
						}

					}

					/*
					 * WebElement ry = driver
					 * .findElement(By.xpath("//div[@id='MedicationsKpop2']/div[2]/div[2]/button[2]"
					 * )); visbility(driver, ry, 60); javascriptclick(ry);
					 */

					// sleep(3000);
					/*
					 * List<WebElement> d1R = driver.findElements( By.xpath(
					 * "//div[@id='MedicationsKpop2']/div[2]/div[3]/div[1]/button//following::ul[1]/li"
					 * )); for (WebElement w : d1R) {
					 * 
					 * if (w.getText().trim().equals("Save")) { w.click(); break; }
					 * 
					 * }
					 */
					sleep(3000);

				} else if (tagnames.equals("delivery-note")) {
					WebElement kk = driver.findElement(By.xpath("//div[contains(@title,'Add Notes')]"));
					actions("move to element", kk);
					visbility(driver, kk, 60);

					actions("click", kk);
					WebElement kk1 = driver
							.findElement(By.xpath("//div[@title='Enter the notes description of the patient visit']"));
					visbility(driver, kk1, 60);
					sendkeys(kk1, "hell");// .sendKeys("hell");
					WebElement zv = driver.findElement(By.xpath("//div[@id='NotesKpop2']/div[2]/div[2]/button[2]"));
					visbility(driver, zv, 60);
					javascriptclick(zv);
					sleep(3000);
					WebElement jl = driver.findElement(By.xpath("//div[text()='hell']"));
					visbility(driver, jl, 60);
					actions("click", jl);
					WebElement kk2 = driver
							.findElement(By.xpath("//div[@title='Enter the notes description of the patient visit']"));
					visbility(driver, kk2, 60);
					clear(kk2);// .clear();
					WebElement kk3 = driver
							.findElement(By.xpath("//div[@title='Enter the notes description of the patient visit']"));
					visbility(driver, kk3, 60);
					sendkeys(kk3, "NOTES--MMM");// .sendKeys("NOTES--MMM");
					WebElement zv1 = driver.findElement(By.xpath("//div[@id='NotesKpop2']/div[2]/div[2]/button[2]"));
					visbility(driver, zv1, 60);
					javascriptclick(zv1);

					/*
					 * WebElement fg =
					 * driver.findElement(By.xpath("//div[@id='NotesKpop2']/div/div[2]/span[1]"));
					 * javascriptclick(fg);
					 */
					sleep(3000);

				} else if (tagnames.equals("physical-examination")) {

					WebElement x1 = driver.findElement(By.xpath("//div[contains(@title,'Add Physical Examination')]"));
					actions("move to element", x1);
					visbility(driver, x1, 60);

					actions("click", x1);
					WebElement x2 = driver.findElement(By.id("bodyParts"));
					visbility(driver, x2, 60);
					sendkeys(x2, "hello");// .sendKeys("hello");

					WebElement x3 = driver.findElement(By.id("finding"));
					visbility(driver, x3, 60);
					sendkeys(x3, "hw are you");// .sendKeys("hw are you");
					sleep(2000);

					WebElement abc = driver
							.findElement(By.xpath("//div[@id='Physical_ExaminationsKpop2']/div[2]/div[2]/div/button"));
					visbility(driver, abc, 60);

					javascriptclick(abc);
					List<WebElement> abcd = driver.findElements(By.xpath(
							"//div[@id='Physical_ExaminationsKpop2']/div[2]/div[2]/div/button//following::ul[1]/li"));
					for (WebElement w : abcd) {
						if (w.getText().trim().equals("Show Notes")) {
							visbility(driver, w, 60);
							w.click();
							break;
						}

					}

					sleep(2000);
					WebElement x5 = driver.findElement(By.xpath("//input[@id='notes']"));
					visbility(driver, x5, 60);
					sendkeys(x5, "lets goo");// .sendKeys("lets goo");
					//// div[@id='Physical_ExaminationsKpop2']/div[2]/div[1]/div[5]/div[2]/input
					sleep(2000);
					WebElement nn = driver
							.findElement(By.xpath("//div[@id='Physical_ExaminationsKpop2']/div[2]/div[3]/button[2]"));
					visbility(driver, nn, 60);
					javascriptclick(nn);
					sleep(3000);
					WebElement et = driver.findElement(By.xpath("//div[text()='lets goo']"));
					visbility(driver, et, 60);
					actions("click", et);
					/*
					 * WebElement sf = driver .findElement(By.xpath(
					 * "//div[@id='Physical_ExaminationsKpop2']/div[1]/div[2]/span[1]"));
					 * javascriptclick(sf);
					 */

					WebElement x7 = driver.findElement(By.xpath("//input[@id='notes']"));
					visbility(driver, x7, 60);
					clear(x7);// .clear();
					WebElement x8 = driver.findElement(By.xpath("//input[@id='notes']"));
					visbility(driver, x8, 60);
					sendkeys(x8, "physical condition");// .sendKeys("physical condition");
					WebElement nnn = driver
							.findElement(By.xpath("//div[@id='Physical_ExaminationsKpop2']/div[2]/div[3]/button[2]"));
					visbility(driver, nnn, 60);
					javascriptclick(nnn);
					sleep(4000);

				} else if (tagnames.equals("custom-form")) {

					WebElement lj = driver.findElement(By.xpath("//div[contains(@title,'Add Forms')]"));
					actions("move to element", lj);
					visbility(driver, lj, 60);

					actions("click", lj);
					sleep(3000);

					List<WebElement> numberofformspresent = driver
							.findElements(By.xpath("(//div[@class='form-pop-body'])[10]/div/div[1]"));
					int ffs = numberofformspresent.size();
					// System.out.println(ffs);

					// u;
					for (int imp = 4; imp <= ffs; imp++) {

						// u = 1 + i;
						WebElement rtt = driver.findElement(
								By.xpath("(//div[@class='form-pop-body'])[10]/div[" + imp + "]/div/div[1]/span[2]"));
						// System.out.println(rtt.getText());

						if (rtt.getText().trim().equals("form6")) {

							visbility(driver, rtt, 60);
							System.out.println("ok");
							rtt.click();
							WebElement js = driver.findElement(By.xpath("(//span[@id='del-form'])[2]"));
							visbility(driver, js, 60);
							javascriptclick(js);

							break;

						}

					}
					sleep(3000);

					WebElement addfrm = driver.findElement(By.xpath("//div[@id='FormsKpop2']/div[1]/span"));
					visbility(driver, addfrm, 60);
					actions("click", addfrm);
					WebElement x9 = driver
							.findElement(By.xpath("(//label[text()='Form Title*'])[2]//following::input[1]"));
					visbility(driver, x9, 60);
					sendkeys(x9, "form6");// .sendKeys("form5");

					List<WebElement> drk = driver
							.findElements(By.xpath("(//div[@id='build-wrap'])[2]/div[1]/div[2]/ul/li"));

					for (WebElement web : drk) {

						if (web.getText().trim().equals("Checkbox Group")) {

							WebElement drop = driver.findElement(By.xpath(
									"(//div[contains(@data-content,'Drag a field from the right to this area')])[2]/ul"));

							Actions ac = new Actions(driver);
							ac.dragAndDrop(web, drop).build().perform();
							driver.findElement(By.xpath("//label[text()='Label']//following::div[1]/input")).clear();
							driver.findElement(By.xpath("//label[text()='Label']//following::div[1]/input"))
									.sendKeys("Kaaspro Enterprise");
							driver.findElement(
									By.xpath("(//div[@id='build-wrap'])[2]/div[1]/div[2]/ul//following::div[1]/button"))
									.click();
							sleep(7000);
							implicitWait(30, TimeUnit.SECONDS);
							WebElement addit = driver
									.findElement(By.xpath("//span[text()='form6']//following::div[1]/span"));
							visbility(driver, addit, 60);
							actions("click", addit);
							sleep(6000);
							WebElement ytt = driver.findElement(By.xpath("//div[@id='FormsKpop2']/div[1]/div[2]/span"));
							javascriptclick(ytt);
							sleep(5000);
							WebElement ffr = driver
									.findElement(By.xpath("//span[text()='form6']//following::div[1]/div"));
							actions("click", ffr);
							sleep(4000);
							WebElement delfr = driver
									.findElement(By.xpath("(//span[text()='form6'])[2]//following::div[1]/span[1]"));
							actions("click", delfr);
							break;
						}

					}
					sleep(3000);
				} else if (tagnames.equals("attachFile")) {

					WebElement ar = driver.findElement(By.xpath("//div[contains(@title,'Add Attach File')]"));
					actions("move to element", ar);
					visbility(driver, ar, 60);

					actions("click", ar);

					WebElement selweb = driver
							.findElement(By.xpath("//div[@id='Attach_FileKpop2']/div[2]/div[1]/select"));
					visbility(driver, selweb, 60);
					dropDown("text", selweb, "Web link");
					WebElement y1 = driver.findElement(
							By.xpath("(//div[@id='Attach_FileKpop2']/div[2]/div[1]//following::div[3]/input[1])[1]"));
					visbility(driver, y1, 60);
					sendkeys(y1, "https://www.75health.com/");// .sendKeys("https://www.75health.com/");
					WebElement ps1 = driver
							.findElement(By.xpath("//div[@id='Attach_FileKpop2']/div[2]/div[3]/button[2]"));
					visbility(driver, ps1, 60);
					javascriptclick(ps1);
					sleep(3000);

				} else if (tagnames.equals("inpatient")) {

					WebElement qq = driver.findElement(By.xpath("//div[contains(@title,'Add Inpatient')]"));
					actions("move to element", qq);
					visbility(driver, qq, 60);

					actions("click", qq);
					// driver.findElement(By.id("admissioniid")).click();

					WebElement inpmnth = driver.findElement(By.xpath("//select[@class='ui-datepicker-month']"));
					visbility(driver, inpmnth, 60);
					dropDown("index", inpmnth, "08");

					WebElement ipmyr = driver.findElement(By.xpath("//select[@class='ui-datepicker-year']"));
					visbility(driver, ipmyr, 60);
					dropDown("text", ipmyr, "2022");
					WebElement y2 = driver.findElement(By.xpath("//a[text()='21']"));// .click();
					visbility(driver, y2, 60);
					click(y2);

					sleep(2000);
					driver.findElement(By.id("dischargeiid")).click();
					WebElement inpmnth1 = driver.findElement(By.xpath("//select[@class='ui-datepicker-month']"));
					visbility(driver, inpmnth1, 60);
					dropDown("index", inpmnth1, "10");
					WebElement ipmyr1 = driver.findElement(By.xpath("//select[@class='ui-datepicker-year']"));
					visbility(driver, ipmyr1, 60);
					dropDown("text", ipmyr1, "2022");
					WebElement y3 = driver.findElement(By.xpath("//a[text()='26']"));
					visbility(driver, y3, 60);
					click(y3);// .click();

					sleep(2000);
					WebElement re = driver.findElement(By.xpath("//div[@id='InpatientKpop2']/div[2]/div[1]/select"));
					visbility(driver, re, 60);
					// (//select[@id='admissionType'])[1]

					javascriptclick(re);
					dropDown("text", re, "Urgent");
					driver.findElement(By.id("rmNo")).sendKeys("777");
					WebElement y4 = driver.findElement(By.id("dischargeSummary"));
					visbility(driver, y4, 60);
					sendkeys(y4, "okay");// .sendKeys("okay");
					WebElement yt = driver.findElement(By.xpath("//div[@id='InpatientKpop2']/div[2]/div[2]/button[2]"));
					visbility(driver, yt, 60);
					javascriptclick(yt);

					sleep(3000);

				} /*
					 * else if (tagnames.equals("refer")) {
					 * 
					 * WebElement ju =
					 * driver.findElement(By.xpath("//div[contains(@title,'Add Referral')]"));
					 * actions("move to element", ju); actions("click", ju); WebElement uk = driver
					 * .findElement(By.xpath(
					 * "//div[@id='ReferralKpop2']/div[2]/div/div[2]/div[2]/input"));
					 * uk.sendKeys(ConfigManager.getconfigManager().getInstanceConfigReader().
					 * doctorKpid());// kpid mention // dr... sleep(3000); List<WebElement> rwr =
					 * driver.findElements(By.xpath(
					 * "//div[@id='ReferralKpop2']/div[2]/div[2]//following::ul[1]/li/a/table[1]/tbody/tr/td[2]"
					 * )); for (WebElement w : rwr) { if (w.getText()
					 * .contains(ConfigManager.getconfigManager().getInstanceConfigReader().
					 * doctorKpid())) { w.click(); break;
					 * 
					 * }
					 * 
					 * }
					 * 
					 * sleep(4000); //
					 * driver.findElement(By.xpath("(//input[@id='phone'])[5]")).sendKeys(
					 * "201-525-2236"); sleep(2000); driver.findElement(By.xpath(
					 * "//div[@id='ReferralKpop2']/div[2]/div[1]/div[6]/div[2]/input"))
					 * .sendKeys("hello"); WebElement cv = driver.findElement(By.xpath(
					 * "//div[@id='ReferralKpop2']/div[2]/div[2]/button[2]")); javascriptclick(cv);
					 * sleep(2000);
					 * 
					 * WebElement df = driver.findElement(By.xpath("//div[text()='hello']"));
					 * actions("click", df); WebElement ssf = driver.findElement(By.xpath(
					 * "//div[@id='ReferralKpop2']/div[1]/div[2]/span[1]")); javascriptclick(ssf);
					 * 
					 * 
					 * }
					 */

			}

		}

		implicitWait(30, TimeUnit.SECONDS);
		sleep(2000);
		WebElement gnbill = driver.findElement(By.xpath("(//div[@id='generate_bill'])[1]/button[2]"));
		javascriptclick(gnbill);
		List<WebElement> yjj = driver
				.findElements(By.xpath("(//div[@id='generate_bill'])[1]/button[2]//following::ul[1]/li"));
		for (WebElement we : yjj) {
			if (we.getText().trim().equals("Generate Bill from EHR")) {
				we.click();
				break;
			}

		}
		sleep(4000);
		WebElement backtoehr = driver.findElement(By.xpath("//button[@onclick='window.history.back();']"));
		javascriptclick(backtoehr);
		sleep(2000);
		driver.navigate().to("https://www.75health.com/health/#list_ehr");
		implicitWait(30, TimeUnit.SECONDS);

		driver.findElement(By.id("newMedicalRecordButton")).click();
		sleep(3000);

		// Salt options Scenarios...

		for (int i = 1; i <= ehrrow; i++) {

			List<WebElement> qf = driver.findElements(By.xpath("(//div[@id='cols'])[2]/div[" + i + "]/div"));
			for (int j = 1; j <= qf.size(); j++) {
				sleep(2000);

				WebElement gettag = driver
						.findElement(By.xpath("(//div[@id='cols'])[2]/div[" + i + "]/div[" + j + "]"));

				String tagnames = gettag.getAttribute("id");

				if (tagnames.equals("vital")) {

					WebElement vitalsal = driver
							.findElement(By.xpath("//div[@id='vital']/div[1]/div[1]/div/div[2]/div[1]"));

					actions("click", vitalsal);

				} else if (tagnames.equals("visit-reason")) {
					WebElement vistsal = driver
							.findElement(By.xpath("(//div[@id='visit-reason'])[2]/div/div[1]/div/div[2]/div[1]"));
					actions("click", vistsal);

				} else if (tagnames.equals("diagnosis")) {

					WebElement prbsal = driver.findElement(By.xpath("//div[contains(@title,'SALT Problems')]"));
					actions("click", prbsal);

				} else if (tagnames.equals("symptom")) {

					WebElement symsalt = driver.findElement(By.xpath("//div[contains(@title,'SALT Symptoms')]"));
					actions("click", symsalt);

				} else if (tagnames.equals("procedure")) {

					WebElement prosalt = driver.findElement(By.xpath("//div[contains(@title,'SALT Procedure')]"));
					actions("click", prosalt);
				} else if (tagnames.equals("goals")) {

					WebElement goalsalt = driver.findElement(By.xpath("//div[contains(@title,'SALT Goals')]"));
					actions("click", goalsalt);
				} else if (tagnames.equals("directives")) {

					WebElement advsalt = driver
							.findElement(By.xpath("//div[contains(@title,'SALT Advance directives')]"));
					actions("click", advsalt);
				} else if (tagnames.equals("status-module")) {

					WebElement statussalt = driver.findElement(By.xpath("//div[contains(@title,'SALT Status')]"));
					actions("click", statussalt);
				} else if (tagnames.equals("drug")) {

					WebElement medsalt = driver.findElement(By.xpath("//div[contains(@title,'SALT Medications')]"));
					actions("click", medsalt);
				} else if (tagnames.equals("delivery-note")) {
					WebElement notesalt = driver.findElement(By.xpath("//div[contains(@title,'SALT Notes')]"));
					actions("click", notesalt);
				}

			}
		}
		sleep(3000);
		WebElement printehr = driver.findElement(By.xpath("//i[@onclick='ehr.ehrShowPrintOptions();']"));
		ScriptExecutor(printehr);
		WebElement timest = driver
				.findElement(By.xpath("//i[@onclick='ehr.ehrShowPrintOptions();']//following::span[3]"));

		visbility(driver, timest, 60);
		javascriptclick(timest);

		List<WebElement> stmp = driver.findElements(
				By.xpath("//i[@onclick='ehr.ehrShowPrintOptions();']//following::span[3]//following::ul[1]/li"));
		for (WebElement web : stmp) {
			if (web.getText().trim().equals("Show Timestamp")) {
				visbility(driver, web, 60);
				web.click();
				break;
			}

		}
		implicitWait(30, TimeUnit.SECONDS);
		sleep(2500);
		// follow up creation...
		j.executeScript("window.scroll(0,0)");
		WebElement createfollowup = driver.findElement(By.xpath("(//button[@id='followUpAdd'])[1]/div[1]"));

		actions("click", createfollowup);
		sleep(4000);
		implicitWait(30, TimeUnit.SECONDS);
		while (true) {
			try {
				WebElement crt = driver.findElement(
						By.xpath("//div[@id='followupEhr']/div[2]/div[3]/div[1]//following::div[2]/input"));
				visbility(driver, crt, 60);
				click(crt);
				break;
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		sleep(2000);
		WebElement folowypyr = driver.findElement(By.xpath("//select[@class='ui-datepicker-year']"));
		dropDown("text", folowypyr, "2022");
		WebElement folowupmnth = driver.findElement(By.xpath("//select[@class='ui-datepicker-month']"));
		dropDown("index", folowupmnth, "1");
		driver.findElement(By.xpath("(//a[text()='6'])[3]")).click();
		WebElement fixappo = driver.findElement(By.xpath("(//button[@id='fixAppointment'])[1]"));

		sleep(3000);
		javascriptclick(fixappo);

		List<WebElement> tot = driver.findElements(By.xpath("(//div[@id='date-data'])[1]"));
		int totaly = tot.size();
		// System.out.println("found you>>>" + totaly);
		implicitWait(30, TimeUnit.SECONDS);

		boolean b = false;
		for (int i = 1; i <= totaly; i++) {
			sleep(3000);
			List<WebElement> rqq = driver
					.findElements(By.xpath("(//div[@id='formAppointment'])[2]/div/div[2]/div[2]/div[2]/div"));

			// System.out.println("total number of displayed time in the list:" +
			// rqq.size());
			for (int jj = 1; jj <= rqq.size(); jj++) {

				WebElement checkcn = driver
						.findElement(By.xpath("(//div[@id='date-data'])[1]/div[2]/div[" + jj + "]/div/div[1]"));
				// System.out.println(checkcn.getText());

				WebElement sm = driver
						.findElement(By.xpath("(//div[@id='date-data'])[1]/div[2]/div[" + jj + "]/div/div[2]/span[3]"));
				// System.out.println(sm.getText().isEmpty());

				if (checkcn.isDisplayed() == true && sm.getText().isEmpty() == true) {
					b = true;
					checkcn.click();
					break;

				}

			}
			if (b == true) {
				WebElement seltyp = driver.findElement(By.xpath("(//div[@id='emergencyFollowup'])[1]/div/select"));
				dropDown("index", seltyp, "1");
				driver.findElement(
						By.xpath("(//div[@id='emergencyFollowup'])[1]/div/following::div[3]/div[2]/textarea")).clear();
				driver.findElement(
						By.xpath("(//div[@id='emergencyFollowup'])[1]/div/following::div[3]/div[2]/textarea"))
						.sendKeys("yes follow");

				driver.findElement(By.xpath("(//div[@id='emergencyFollowup'])[1]/div/following::div[6]/div/div"))
						.click();
				sleep(3000);
				WebElement delfolup = driver
						.findElement(By.xpath("(//button[@id='fixAppointment'])[1]//following::div[1]/div[1]/span[2]"));
				actions("move to element", delfolup);
				actions("click", delfolup);
				WebElement bckfl = driver.findElement(By.xpath("//div[@id='followupEhr']/div[1]/div/span[1]"));
				javascriptclick(bckfl);
				break;
			}
		}

		sleep(2000);
		WebElement delehrf = driver.findElement(By.xpath("(//button[contains(@title,'Delete Health Record')])[1]"));
		javascriptclick(delehrf);
		sleep(2000);
		WebElement zr = driver
				.findElement(By.xpath("//span[text()='Delete Health Record']//following::div[4]/button[2]"));
		javascriptclick(zr);
		sleep(4000);
	}

	@Test(priority = 3)

	public void calendar() throws InterruptedException {
		driver.navigate().to("https://www.75health.com/health/#calendar");
		/*
		 * explicitWait(20, pom.getInstanceCalendar().clickCalendar);
		 * click(pom.getInstanceCalendar().clickCalendar);
		 */
		driver.navigate().refresh();
		sleep(3000);

		implicitWait(30, TimeUnit.SECONDS);
		driver.manage().timeouts().pageLoadTimeout(40, TimeUnit.SECONDS);
		while (true) {
			try {

				WebElement clbtn = driver.findElement(By.xpath("(//button[@id='calendar-day-month'])[1]"));

				visbility(driver, clbtn, 60);

				clbtn.click();
				break;
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		sleep(3000);
		List<WebElement> choose = driver.findElements(By.xpath("//ul[@id='calendarul']/li"));

		for (WebElement web : choose) {
			if (web.getText().equals("Today")) {
				driver.findElement(By.xpath("//ul[@id='calendarul']/li")).click();
				break;
			}
		}
		sleep(2000);

		List<WebElement> totalnumberrowdy = driver.findElements(By.xpath("//div[@id='date-data']"));
		int totalr = totalnumberrowdy.size();
		// System.out.println("found you>>>" + totalr);

		boolean cond = false;

		for (int i = 1; i <= totalr; i++) {
			int a = 1 + i;
			WebElement ss = driver.findElement(By.xpath("//div[@id='date-data'][" + i + "]/div[2]/div[2]/div"));
			if (ss.getText().equals("Doctor/User not working")) {
				System.out.println("yes doctor not working for the:" + i);
				WebElement abcd = driver.findElement(By.xpath("(//span[@id='editCalendar'])[" + a + "]"));
				visbility(driver, abcd, 60);
				actions("click", abcd);
				sleep(3000);
				WebElement checkbx = driver.findElement(By.xpath("(//input[@id='is-working-day'])[1]"));
				System.out.println("(//input[@id='is-working-day'])[" + i + "]");
				visbility(driver, checkbx, 60);
				actions("click", checkbx);
				WebElement ampm = driver.findElement(By.xpath("(//div[@id='thru-ampm'])[1]"));
				visbility(driver, ampm, 60);
				actions("click", ampm);
				WebElement rre = driver.findElement(By.xpath("(//div[@id='save-btn'])[1]"));
				visbility(driver, rre, 60);
				javascriptclick(rre);// .click();
				sleep(5000);

			}

			// represent total in a part..
			List<WebElement> rchange = driver
					.findElements(By.xpath("(//div[@id='date-data'][" + i + "]/div[2]/div/div[1]/div[1]/div[1])"));
			int avaiable = rchange.size();

			for (int b = 1; b <= avaiable; b++) {

				WebElement tp = driver.findElement(

						By.xpath("(//div[@id='date-data'][" + i + "]/div[2]/div[" + b + "]/div[1]/div[1]/div[1])"));

				// String tr = tp.getText();
				// boolean trp = tp.isDisplayed();

				// the kpid ..
				WebElement kp = driver.findElement(
						By.xpath("(//div[@id='date-data'])[" + i + "]/div[2]/div[" + b + "]/div/div[2]/span[2]"));

				if (kp.getText().isEmpty() && tp.isDisplayed()) {

					cond = true;
					visbility(driver, tp, 60);
					javascriptclick(tp);

					WebElement prp = driver.findElement(By.xpath("(//input[@id='AppointmentPatientName'])[" + i + "]"));
					visbility(driver, prp, 60);
					sendkeys(prp, kpid);// .sendKeys(kpid);
					break;
				}

			}
			if (cond == true) {
				sleep(2000);
				implicitWait(30, TimeUnit.SECONDS);
				List<WebElement> choosepatient = driver
						.findElements(By.xpath("//ul[@id='ui-id-2']/li/a/table/tbody/tr/td[2]"));
				// (//td[text()='" + kpid + "'])[2]//parent::td
				for (WebElement web : choosepatient) {
					if (web.getText().trim().equals(kpid)) {
						visbility(driver, web, 60);
						web.click();
						break;
					}

				}
				sleep(3000);

				WebElement ut = driver.findElement(By.xpath("(//select[@id='triage-appointment'])[" + i + "]"));
				visbility(driver, ut, 60);
				// ut.click();
				dropDown("text", ut, "Emergency");

				WebElement qt = driver.findElement(By.xpath("(//textarea[@id='description'])[" + i + "]"));
				visbility(driver, qt, 60);
				qt.sendKeys("no worries...");
				WebElement utt = driver.findElement(By.xpath("(//button[@id='statusId_dropdown'])[" + i + "]"));
				visbility(driver, utt, 60);
				javascriptclick(utt);

				List<WebElement> lop = driver.findElements(
						By.xpath("(//button[@id='statusId_dropdown'])[" + i + "]//following::ul[1]/li/a"));
				for (WebElement w : lop) {
					if (w.getText().trim().equals("In Progress")) {
						visbility(driver, w, 60);
						javascriptclick(w);
						break;
					}

				}

				WebElement vcv = driver.findElement(By.xpath("(//button[@id='accept-btn'])[1]"));
				visbility(driver, vcv, 60);
				ScriptExecutor(vcv);

				javascriptclick(vcv);

				sleep(1000);

				WebElement ez = driver.findElement(By.xpath("//span[text()='" + kpid + "']"));
				visbility(driver, ez, 60);
				javascriptclick(ez);

				sleep(2000);
				// goto ehr..
				WebElement ehr = driver.findElement(By.xpath("(//button[@id='cancel-btn1'])[1]"));
				visbility(driver, ehr, 60);
				click(ehr);// .click();

				sleep(3000);
				driver.navigate().to("https://www.75health.com/health/#calendar");
				driver.navigate().refresh();
				driver.manage().timeouts().pageLoadTimeout(50, TimeUnit.SECONDS);
				WebElement pipes = driver.findElement(By.xpath("(//span[text()='" + kpid + "'])"));
				visbility(driver, pipes, 60);
				j.executeScript("arguments[0].click();", pipes);
				sleep(1000);
				WebElement wtw = driver.findElement(By.xpath("(//span[@id='del-btn'])[" + i + "]"));
				visbility(driver, wtw, 60);
				j.executeScript("arguments[0].click();", wtw);
				sleep(2000);

				/*
				 * List<WebElement> rtr = driver .findElements(By.xpath(
				 * "(//span[@id='del-btn'])[1]//following::ul[1]/li/div/div[2]/span"));
				 * 
				 * for (WebElement web : rtr) { if (web.getAttribute("id").equals("yes-btn")) {
				 * // System.out.println("yes it is deleted"); implicitWait(30,
				 * TimeUnit.SECONDS); actions("click", web);
				 * 
				 * break; }
				 * 
				 * }
				 */

				WebElement delappp = driver
						.findElement(By.xpath("//div[@id='AppointmentCreateMessage']/div[2]/div[2]/button[2]"));
				visbility(driver, delappp, 60);
				javascriptclick(delappp);

				break;
			}
		}
		sleep(2000);

	}

	@Test(priority = 4)
	public void BillingModule() throws InterruptedException {

		visbility(driver, pom.getInstanceBilling().clickBill, 60);
		click(pom.getInstanceBilling().clickBill);

		driver.navigate().refresh();
		implicitWait(50, TimeUnit.SECONDS);
		sleep(3000);

		visbility(driver, pom.getInstanceBilling().clickCreateNewBill, 60);
		click(pom.getInstanceBilling().clickCreateNewBill);
		visbility(driver, pom.getInstanceBilling().addItem, 60);

		click(pom.getInstanceBilling().addItem);
		visbility(driver, pom.getInstanceBilling().enterTheItem, 60);

		sendkeys(pom.getInstanceBilling().enterTheItem, "dolo"); //
		visbility(driver, pom.getInstanceBilling().addPrice, 60);
		sendkeys(pom.getInstanceBilling().addPrice, "10"); //
		visbility(driver, pom.getInstanceBilling().addQuantity, 60);
		clear(pom.getInstanceBilling().addQuantity); //
		visbility(driver, pom.getInstanceBilling().addQuantity, 60);
		sendkeys(pom.getInstanceBilling().addQuantity, "2");

		visbility(driver, pom.getInstanceBilling().saveItem, 60);
		click(pom.getInstanceBilling().saveItem);
		sleep(3000);
		while (true) {
			try {
				WebElement r = driver.findElement(By.xpath("//div[@id='assign-side']/div[1]/div"));
				visbility(driver, r, 60);
				actions("click", r);
				break;
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		while (true) {
			try {
				WebElement addiconfav = driver.findElement(
						By.xpath("//div[@id='assign-side']/div[2]/div[1]/div/table/tbody/tr/td[4]/span[2]"));
				visbility(driver, addiconfav, 60);
				javascriptclick(addiconfav);
				break;
			} catch (Exception e) {
				// TODO: handle exception
			}
		}

		while (true) {
			try {
				WebElement favdes = driver.findElement(
						By.xpath("//div[@id='assign-side']/div[3]/div/div/div[2]/div[3]/div[2]/div/input"));
				visbility(driver, favdes, 60);
				favdes.sendKeys("Kaaspro");
				break;
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		WebElement ree = driver
				.findElement(By.xpath("//div[@id='assign-side']/div[3]/div/div/div[2]/div[4]/div[2]/input"));
		visbility(driver, ree, 60);
		ree.sendKeys("3");
		WebElement savefav = driver.findElement(By.xpath(
				"//div[@id='assign-side']/div[3]/div/div/div[2]/div[4]/div[2]//following::div[1]/div/div[4]//following::div[1]/div/button"));
		visbility(driver, savefav, 60);
		javascriptclick(savefav);
		// WebDriverWait wait = new WebDriverWait(driver, 30);
		sleep(2500);
		WebElement until;
		while (true) {
			try {
				until = driver.findElement(By.xpath(
						"(//span[text()='Kaaspro']//parent::div//parent::div[1]//parent::div[1]/div[1]/span[1])[1]"));
				break;
			} catch (Exception e) {
				// TODO: handle exception
			}
		}

		actions("click", until);
		sleep(2000);
		while (true) {
			try {
				WebElement edi = driver.findElement(
						By.xpath("(//span[text()='Kaaspro']//parent::div//parent::div[1]//parent::div[1]/div[2])[1]"));
				visbility(driver, edi, 60);
				actions("click", edi);
				break;
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		sleep(2000);
		while (true) {
			try {
				WebElement delfav = driver
						.findElement(By.xpath("//div[@id='assign-side']/div[3]/div/div/div[1]/div[2]/span[1]"));

				visbility(driver, delfav, 60);
				actions("click", delfav);
				break;
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		sleep(2500);
		while (true) {
			try {
				WebElement itser = driver.findElement(By.xpath("//div[@id='item-code-side']/div[1]/div"));
				visbility(driver, itser, 60);

				actions("click", itser);
				break;
			} catch (Exception e) {
				// TODO: handle exception
			}
		}

		while (true) {
			try {
				WebElement additser = driver.findElement(
						By.xpath("//div[@id='item-code-side']/div[2]/div[1]/div/table/tbody/tr/td[4]/span[3]"));
				visbility(driver, additser, 60);

				javascriptclick(additser);
				break;
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		while (true) {
			try {
				WebElement wrr = driver
						.findElement(By.xpath("//div[@id='item-code-side']/div[3]/div/div/div[2]/div[3]/div/input"));
				visbility(driver, wrr, 60);
				sendkeys(wrr, "product001");// .sendKeys("product001");
				break;
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		WebElement err = driver
				.findElement(By.xpath("//div[@id='item-code-side']/div[3]/div/div/div[2]/div[4]/div/textarea"));
		visbility(driver, err, 60);
		sendkeys(err, "sr");// .sendKeys("sr");
		WebElement weq = driver
				.findElement(By.xpath("//div[@id='item-code-side']/div[3]/div/div/div[2]/div[5]/div/input"));
		visbility(driver, weq, 60);
		sendkeys(weq, "5");// .sendKeys("5");
		while (true) {
			try {
				WebElement itersav = driver
						.findElement(By.xpath("//div[@id='item-code-side']/div[3]/div/div/div[2]/div[7]/div/button"));
				visbility(driver, itersav, 60);
				javascriptclick(itersav);
				break;
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		sleep(2000);
		for (int i = 1; i <= 5; i++) {
			try {
				WebElement ft = driver.findElement(
						By.xpath("//div[text()='PRODUCT001']//parent::div[1]//parent::div[1]/div[1]/span"));
				visbility(driver, ft, 60);

				javascriptclick(ft);
				break;
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		sleep(2000);
		for (int i = 1; i <= 5; i++) {
			try {
				WebElement edititser = driver.findElement(By.xpath("//div[text()='PRODUCT001']"));
				visbility(driver, edititser, 60);

				actions("click", edititser);
				break;
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		for (int i = 1; i <= 5; i++) {
			try {
				WebElement delitser = driver
						.findElement(By.xpath("//div[@id='item-code-side']/div[3]/div/div/div[1]/div[2]/span[1]"));
				visbility(driver, delitser, 60);

				javascriptclick(delitser);
				break;
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		sleep(2000);

		// service/charge tax..
		for (int i = 1; i <= 5; i++) {
			try {

				WebElement sct = driver.findElement(By.xpath("//div[@id='tax-side']/div[1]/div/span[3]"));
				visbility(driver, sct, 60);

				actions("click", sct);
				break;
			} catch (Exception e) {
				// TODO: handle exception
			}
		}

		for (int i = 1; i <= 5; i++) {
			try {

				WebElement sctadd = driver
						.findElement(By.xpath("//div[@id='tax-side']/div[2]/div[1]/div/table/tbody/tr/td[4]/span[3]"));
				visbility(driver, sctadd, 60);

				javascriptclick(sctadd);
				break;
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		WebElement tre = driver.findElement(By.xpath("//div[@id='tax-side']/div[3]/div/div/div[2]/div[3]/div/input"));
		visbility(driver, tre, 60);
		sendkeys(tre, "service charge<>"); // .sendKeys("service charge<>");
		WebElement tp1 = driver.findElement(By.xpath("//div[@id='tax-side']/div[3]/div/div/div[2]/div[4]/div/input"));
		visbility(driver, tp1, 60);
		sendkeys(tp1, "5");// .sendKeys("5");
		WebElement sctsav = driver
				.findElement(By.xpath("//div[@id='tax-side']/div[3]/div/div/div[2]/div[6]/div/button"));
		visbility(driver, sctsav, 60);
		for (int i = 1; i <= 5; i++) {
			try {

				javascriptclick(sctsav);
				WebElement adsct = driver.findElement(
						By.xpath("//div[text()='service charge<>']//parent::div[1]//parent::div[1]/div/span"));
				visbility(driver, adsct, 60);

				actions("click", adsct);
				break;
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		sleep(2000);
		for (int i = 1; i <= 5; i++) {
			try {

				WebElement edsct = driver.findElement(By.xpath("//div[text()='service charge<>']"));
				visbility(driver, edsct, 60);

				actions("click", edsct);
				break;
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		for (int i = 1; i <= 5; i++) {
			try {

				WebElement delsct = driver
						.findElement(By.xpath("//div[@id='tax-side']/div[3]/div/div/div[1]/div[2]/span[1]"));
				visbility(driver, delsct, 60);
				javascriptclick(delsct);
				break;
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		sleep(2000);
		// discount..
		for (int i = 1; i <= 5; i++) {
			try {

				WebElement dis = driver.findElement(By.xpath("//div[@id='discount-side']/div[1]/div"));
				visbility(driver, dis, 60);

				actions("click", dis);
				break;
			} catch (Exception e) {
				// TODO: handle exception
			}
		}

		for (int i = 1; i <= 5; i++) {
			try {

				WebElement addds = driver.findElement(
						By.xpath("//div[@id='discount-side']/div[2]/div[1]/div/table/tbody/tr/td[4]/span[3]"));
				visbility(driver, addds, 60);

				javascriptclick(addds);
				break;
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		WebElement trp3 = driver
				.findElement(By.xpath("//div[@id='discount-side']/div[3]/div/div/div[2]/div[3]/div/input"));
		visbility(driver, trp3, 60);
		sendkeys(trp3, "Discnt");// .sendKeys("Discnt");
		WebElement cldrpdis = driver
				.findElement(By.xpath("//div[@id='discount-side']/div[3]/div/div/div[2]/div[4]/div/button"));
		visbility(driver, cldrpdis, 60);
		javascriptclick(cldrpdis);
		List<WebElement> dispercen = null;
		for (int i = 1; i <= 5; i++) {
			try {

				dispercen = driver
						.findElements(By.xpath("//div[@id='discount-side']/div[3]/div/div/div[2]/div[4]/div/ul/li/a"));
				break;
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		for (WebElement we : dispercen) {
			if (we.getText().trim().equals("Percentage Discount")) {
				visbility(driver, we, 60);
				we.click();
				break;
			}

		}
		WebElement trp6 = driver
				.findElement(By.xpath("//div[@id='discount-side']/div[3]/div/div/div[2]/div[5]/div/div/input"));
		visbility(driver, trp6, 60);
		sendkeys(trp6, "5");// .sendKeys("5");
		WebElement dissav = driver
				.findElement(By.xpath("//div[@id='discount-side']/div[3]/div/div/div[2]/div[8]/div/button"));
		visbility(driver, dissav, 60);
		javascriptclick(dissav);
		WebElement adddiscc = driver
				.findElement(By.xpath("//div[text()='Discnt']//parent::div[1]//parent::div/div/span"));
		visbility(driver, adddiscc, 60);

		actions("click", adddiscc);
		sleep(2000);
		for (int i = 1; i <= 5; i++) {
			try {
				WebElement editdis = driver.findElement(By.xpath("//div[text()='Discnt']"));
				visbility(driver, editdis, 60);

				actions("click", editdis);
				break;
			} catch (Exception e) {
				// TODO: handle exception
			}
		}

		for (int i = 1; i <= 5; i++) {
			try {
				WebElement deldis = driver
						.findElement(By.xpath("//div[@id='discount-side']/div[3]/div/div/div[1]/div[2]/span[1]"));
				visbility(driver, deldis, 60);
				javascriptclick(deldis);
				break;
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		sleep(2000);
		for (int i = 1; i <= 5; i++) {
			try {
				WebElement ee = driver.findElement(By.xpath("(//button[@id='add-payment-btn'])[1]"));
				visbility(driver, ee, 60);
				javascriptclick(ee);
				break;
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		// driver.findElement(By.xpath("(//button[@id='add-payment-btn'])[1]")).click();
		WebElement trp8 = driver.findElement(By.xpath("(//span[@id='paymentMethodTypeSelectValue'])[2]"));
		visbility(driver, trp8, 60);
		click(trp8);
		// .click();
		sleep(2000);
		List<WebElement> choosepy = driver
				.findElements(By.xpath("(//span[@id='paymentMethodTypeSelectValue'])[2]//following::ul[1]/li"));
		for (WebElement w : choosepy) {
			if (w.getText().trim().equals("Cash Payment")) {
				visbility(driver, w, 60);
				w.click();
				break;
			}

		}

		WebElement dsp1 = driver.findElement(By.xpath(
				"(//button[@id='paymentMethodTypeId'])[2]/span[2]//following::div[4]/div[4]//following::div[2]//following::div[1]/div[2]/textarea"));
		visbility(driver, dsp1, 60);
		actions("click", dsp1);
		sleep(2000);
		dsp1.sendKeys("cash pay+++++++++");

		for (int i = 1; i <= 5; i++) {
			try {
				WebElement tet = driver.findElement(By.xpath("(//button[@title='Make Payment'])[3]"));
				visbility(driver, tet, 60);
				j.executeScript("arguments[0].click();", tet);
				break;
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		sleep(2000);
		for (int i = 1; i <= 5; i++) {
			try {
				WebElement fnls = driver.findElement(By.xpath("//button[@id='finalize-bill']"));
				visbility(driver, fnls, 60);

				javascriptclick(fnls);
				break;
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		sleep(2000);
		WebElement dz = driver.findElement(By.xpath("(//button[@title='Finalize'])[1]"));
		visbility(driver, dz, 60);

		javascriptclick(dz);
		sleep(2000);
		WebElement trp7 = driver.findElement(By.xpath("//button[@id='finalize-bill']//following::button[2]"));// .click();
		visbility(driver, trp7, 60);
		javascriptclick(trp7);
		sleep(2000);
		WebElement delbil = driver.findElement(
				By.xpath("//center[text()='Do you like to delete Invoice?']//following::div[1]/button[2]"));
		visbility(driver, delbil, 60);
		javascriptclick(delbil);
		sleep(2000);

		driver.navigate().to("https://www.75health.com/health/#bill_report");
		driver.navigate().refresh();
		WebElement trp9;
		while (true) {
			try {

				trp9 = driver.findElement(By.xpath(
						"//div[@id='bill_report']/div[1]/div[2]//following::div[1]/div[2]/div/div[2]/div[3]/div[1]/div[1]/div[1]//following::div[1]/input"));
				visbility(driver, trp9, 60);
				break;
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		sendkeys(trp9, kpid);
		// .sendKeys(kpid);
		sleep(2000);
		List<WebElement> billdrp;
		while (true) {
			try {
				billdrp = driver.findElements(By.xpath(
						"//div[@id='dmain']/div[4]/div[2]//following::div[2]//following::ul[3]/li/a/table/tbody/tr/td[2]"));
				break;
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		for (WebElement we : billdrp) {
			if (we.getText().trim().equals(kpid)) {
				visbility(driver, we, 60);
				we.click();
				break;
			}

		}
		visbility(driver, pom.getInstanceBilling().clickCreateNewBill, 60);
		click(pom.getInstanceBilling().clickCreateNewBill);

		sleep(2000);
		visbility(driver, pom.getInstanceBilling().addItem, 60);
		click(pom.getInstanceBilling().addItem);
		sleep(2000);
		visbility(driver, pom.getInstanceBilling().enterTheItem, 60);
		sendkeys(pom.getInstanceBilling().enterTheItem, "dolo"); //
		visbility(driver, pom.getInstanceBilling().addPrice, 60);
		sendkeys(pom.getInstanceBilling().addPrice, "10"); //
		visbility(driver, pom.getInstanceBilling().addQuantity, 60);
		clear(pom.getInstanceBilling().addQuantity); //
		visbility(driver, pom.getInstanceBilling().addQuantity, 60);
		sendkeys(pom.getInstanceBilling().addQuantity, "2");
		sleep(3000);
		visbility(driver, pom.getInstanceBilling().saveItem, 60);
		click(pom.getInstanceBilling().saveItem);
		sleep(2000);
		String inc;
		while (true) {
			try {
				WebElement getinc = driver.findElement(By.xpath("//span[@id='receiptId']"));
				visbility(driver, getinc, 60);
				inc = getinc.getText();
				System.out.println(inc);
				break;
			} catch (Exception e) {
				// TODO: handle exception
			}
		}

		sleep(3000);

		driver.navigate().back();

		while (true) {
			if (driver.getCurrentUrl().equals("https://www.75health.com/health/#bill_report")) {
				break;
			}
		}

		while (true) {
			try {
				WebElement rqs = driver.findElement(By.xpath("//div[text()='" + inc + "']"));
				visbility(driver, rqs, 60);

				actions("click", rqs);
				break;
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		sleep(1000);
		for (int i = 1; i <= 5; i++) {
			try {
				WebElement editit = driver.findElement(By.xpath("//div[text()='dolo']"));
				visbility(driver, editit, 60);
				javascriptclick(editit);
				break;
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		visbility(driver, pom.getInstanceBilling().enterTheItem, 60);
		clear(pom.getInstanceBilling().enterTheItem);

		visbility(driver, pom.getInstanceBilling().enterTheItem, 60);
		sendkeys(pom.getInstanceBilling().enterTheItem, "Paracetamal");

		visbility(driver, pom.getInstanceBilling().addPrice, 60);
		clear(pom.getInstanceBilling().addPrice);
		sendkeys(pom.getInstanceBilling().addPrice, "90");

		WebElement saveit = driver.findElement(
				By.xpath("//div[text()='dolo']//following::div[6]/div[2]//following::div[1]/div[2]/div[1]/button[3]"));
		visbility(driver, saveit, 60);
		javascriptclick(saveit);

	}

	@Test(priority = 5)
	public void TeleDoctor() throws InterruptedException {

		while (true) {
			try {
				visbility(driver, pom.getInstanceTeleDoctor().clickTeleDoctor, 60);

				click(pom.getInstanceTeleDoctor().clickTeleDoctor);
				break;
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		driver.navigate().refresh();
		implicitWait(60, TimeUnit.SECONDS);

		while (true) {
			try {
				WebElement np1 = driver.findElement(By.xpath("//button[@title='Create new Patinet']"));
				visbility(driver, np1, 60);
				j.executeScript("arguments[0].click();", np1);
				break;
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		WebElement np2 = driver.findElement(By.id("firstname"));
		visbility(driver, np2, 60);
		sendkeys(np2, "Abigazi");// .sendKeys("Abigazi");
		WebElement np3 = driver.findElement(By.id("lastname"));
		visbility(driver, np3, 60);
		sendkeys(np3, "Ak");// .sendKeys("Ak");
		WebElement gn1 = driver.findElement(By.xpath("(//button[@id='gender_dropdown'])[1]"));
		visbility(driver, gn1, 60);
		javascriptclick(gn1);

		List<WebElement> jj = driver.findElements(By.xpath("(//ul[@id='genderDropdown'])[1]/li"));
		for (WebElement w : jj) {
			if (w.getText().trim().equals("Male")) {
				visbility(driver, w, 60);
				w.click();
				break;
			}

		}

		WebElement cp1 = driver.findElement(By.xpath("//div[@id='createPatient']"));
		visbility(driver, cp1, 60);
		javascriptclick(cp1);

		implicitWait(30, TimeUnit.SECONDS);
		ScriptExecutor(pom.getInstanceNewPatient().deletePatient);
		click(pom.getInstanceNewPatient().deletePatient);

		while (true) {
			try {

				WebElement $delpat$ = driver.findElement(By.xpath("//button[text()='Delete']"));
				javascriptclick($delpat$);
				break;
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		/*
		 * click(pom.getInstanceTeleDoctor().clickTeleDoctor);
		 * sendkeys(pom.getInstanceTeleDoctor().searchDoctor, "3523943"); sleep(3000);
		 * explicitWait(20, pom.getInstanceTeleDoctor().searchDoctor); WebElement srdr =
		 * driver.findElement(By.xpath("//td[@id='nameh']//following::td[1]"));
		 * actions("click", srdr); sleep(3000);
		 */

		// clickble(driver, pom.getInstanceTeleDoctor().clickTeleDoctor, 60);

		// ww.until(ExpectedConditions.urlToBe("https://www.75health.com/health/#call_history"));

		driver.navigate().to("https://www.75health.com/health/#call_history");
		driver.navigate().refresh();
		while (true) {
			try {
				visbility(driver, pom.getInstanceTeleDoctor().clickTeleDoctor, 60);
				javascriptclick(pom.getInstanceTeleDoctor().clickTeleDoctor);
				break;
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		visbility(driver, pom.getInstanceTeleDoctor().searchPatient, 60);
		sendkeys(pom.getInstanceTeleDoctor().searchPatient, kpid);

		WebElement pstl = driver.findElement(By.xpath("//td[@id='nameh']//following::td[1]"));
		visbility(driver, pstl, 60);
		actions("click", pstl);

		// ScriptExecutor(pom.getInstanceCalendar().saveAppointment);

		WebElement clickpatie = driver.findElement(By.xpath("(//div[@title='Click to view'])[4]"));

		visbility(driver, clickpatie, 60);
		actions("click", clickpatie);

		sleep(3000);

	}

	@Test(priority = 6)
	public void Message() throws InterruptedException {
		while (true) {
			try {
				visbility(driver, pom.getInstanceMessage().clickMessage, 60);
				click(pom.getInstanceMessage().clickMessage);
				break;
			} catch (Exception e) {
				// TODO: handle exception
			}
		}

		for (int i = 1; i <= 5; i++) {
			try {
				visbility(driver, pom.getInstanceMessage().clickComposemMessage, 60);
				click(pom.getInstanceMessage().clickComposemMessage); //
				break;
			} catch (Exception e) {
				// TODO: handle exception
			}
		}

		for (int i = 1; i <= 5; i++) {
			try {
				sendkeys(pom.getInstanceMessage().search, kpid);
				break;
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		sleep(2000); //

		for (int i = 1; i <= 5; i++) {
			try {
				WebElement msg = driver.findElement(By.xpath("(//td[@id='nameh'])[1]//following::td[1]"));
				visbility(driver, msg, 60);
				actions("click", msg);
				break;
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		visbility(driver, pom.getInstanceMessage().enterSubject, 60);
		sendkeys(pom.getInstanceMessage().enterSubject, "GOOD MORNING");

		sendkeys(pom.getInstanceMessage().enterMessage, "hello welcome to chennai");
		visbility(driver, pom.getInstanceMessage().sendMessage, 60);
		click(pom.getInstanceMessage().sendMessage);
		while (true) {
			try {
				visbility(driver, pom.getInstanceMessage().seeSentMessage, 60);
				click(pom.getInstanceMessage().seeSentMessage);
				break;
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		sleep(3000);

	}

	@Test(priority = 7)
	public void Settings() throws InterruptedException, IOException {

		while (true) {
			driver.navigate().to("https://www.75health.com/health/#setting");
			String s = "https://www.75health.com/health/#setting";
			if (driver.getCurrentUrl().equals(s)) {
				break;
			}

			sleep(3000);
		}
		implicitWait(40, TimeUnit.SECONDS);
		JavascriptExecutor js = (JavascriptExecutor) driver;

		driver.findElement(By.xpath("//button[text()='Manage your Account']")).click();
		sleep(3000);
		WebElement Basicinfo = driver.findElement(By.xpath("(//span[@title='Edit'])[2]"));
		visbility(driver, Basicinfo, 60);
		javascriptclick(Basicinfo);

		WebElement set1 = driver.findElement(By.id("hospitalName"));
		visbility(driver, set1, 60);
		clear(set1);// .clear();
		sleep(2000);
		WebElement set2 = driver.findElement(By.id("hospitalName"));
		visbility(driver, set2, 60);
		sendkeys(set2, "75health organisation");// .sendKeys("75health organisation");
		sleep(3000);
		WebElement set3 = driver.findElement(By.xpath("//button[@title='Administrator Title']"));// .click();
		visbility(driver, set3, 60);
		click(set3);
		sleep(3000);
		List<WebElement> titledrp = driver
				.findElements(By.xpath("//button[@title='Administrator Title']//following::ul[1]/li"));
		for (WebElement choose : titledrp) {
			if (choose.getText().trim().equals("Dr")) {
				visbility(driver, set3, 60);
				choose.click();
				break;
			}

		}
		WebElement Firstnname = driver.findElement(By.id("firstName"));
		visbility(driver, Firstnname, 60);
		Firstnname.clear();

		Firstnname.sendKeys("Automation Acc");

		WebElement lastname = driver.findElement(By.id("lastName"));
		visbility(driver, lastname, 60);
		lastname.clear();
		lastname.sendKeys("Account");
		sleep(3000);
		WebElement hospitalActive = driver.findElement(By.xpath("//button[@id='hospitalActiveId']"));
		visbility(driver, hospitalActive, 60);
		j.executeScript("arguments[0].click();", hospitalActive);
		sleep(2000);
		List<WebElement> Adminstatus = driver.findElements(By.xpath("(//ul[@id='advBillType_Dropdown'])[2]/li"));
		for (WebElement Ch1 : Adminstatus) {
			if (Ch1.getText().trim().equals("ACTIVE")) {
				Ch1.click();
				break;
			}

		}
		sleep(2000);
		WebElement phn1 = driver.findElement(By.id("phone"));
		visbility(driver, phn1, 60);
		phn1.clear();
		phn1.sendKeys("2013445245");
		sleep(2000);

		WebElement em = driver.findElement(By.xpath("(//button[@id='smsP'])[1]"));
		visbility(driver, em, 60);
		j.executeScript("arguments[0].click();", em);

		sleep(2000);
		List<WebElement> smsnotification = driver.findElements(By.xpath("//ul[@id='Smsul']/li"));
		for (WebElement web : smsnotification) {
			if (web.getText().trim().equals("ON")) {
				visbility(driver, web, 60);
				web.click();
				break;
			}

		}
		sleep(2000);
		WebElement fint = driver.findElement(By.xpath("(//button[@id='save-btn'])[3]"));
		visbility(driver, fint, 60);
		j.executeScript("arguments[0].click();", fint);
		sleep(3000);

		// Contact info..

		WebElement contactinfoadd = driver.findElement(By.xpath("(//div[@id='edit-btn'])[2]"));
		visbility(driver, contactinfoadd, 60);
		actions("click", contactinfoadd);
		sleep(4000);
		WebElement add1 = driver.findElement(By.xpath("(//input[@id='address1'])[1]"));
		visbility(driver, add1, 60);
		add1.clear();
		add1.sendKeys("no.224,Main avenue");
		WebElement add2 = driver.findElement(By.xpath("(//input[@id='address2'])[1]"));
		visbility(driver, add2, 60);
		add2.clear();
		add2.sendKeys("watson street usa");
		sleep(2000);
		WebElement city = driver.findElement(By.xpath("(//input[@id='city'])[1]"));
		visbility(driver, city, 60);
		city.clear();
		city.sendKeys("usa");
		sleep(2000);
		WebElement set = driver.findElement(By.xpath("(//select[@id='countryGeoId'])[1]"));
		visbility(driver, set, 60);
		click(set);// .click();
		sleep(2000);
		WebElement selectcountry = driver.findElement(By.xpath("(//select[@id='countryGeoId'])[1]"));
		visbility(driver, selectcountry, 60);
		dropDown("text", selectcountry, "Germany");
		sleep(2000);
		WebElement selectstate = driver.findElement(By.xpath("(//select[@id='stateProvinceGeoId'])[1]"));
		visbility(driver, selectstate, 60);
		dropDown("text", selectstate, "Berlin");
		sleep(2000);
		WebElement trp6 = driver.findElement(By.xpath("(//input[@id='postalCode'])[1]"));// .sendKeys("2001143");
		visbility(driver, trp6, 60);
		sendkeys(trp6, "2001143");
		WebElement savecontactinfo = driver.findElement(By.xpath("(//button[@id='save-btn'])[5]"));
		visbility(driver, savecontactinfo, 60);
		j.executeScript("arguments[0].click();", savecontactinfo);

		// specailaity

		/*
		 * WebElement add = driver.findElement(By.xpath("(//div[@id='add-btn'])[3]"));
		 * 
		 * sleep(2000); js.executeScript("arguments[0].click();", add); WebElement rr =
		 * driver.findElement(By.
		 * xpath("(//input[@placeholder='Select or Enter Specialty'])[1]"));
		 * sleep(2000); actions("click", rr); sleep(2000);
		 * rr.sendKeys("abdominal surgery"); sleep(3000); WebElement splm =
		 * driver.findElement(By.xpath("//span[text()='Abdominal Surgery']"));
		 * actions("click", splm);
		 * driver.findElement(By.xpath("(//button[@id='save-btn'])[7]")).click();
		 * sleep(3000); WebElement splrem =
		 * driver.findElement(By.xpath("//div[text()='Abdominal Surgery']"));
		 * actions("click", splrem); sleep(3000); WebElement spldel =
		 * driver.findElement(By.xpath("(//img[@id='del-btn'])[1]"));
		 * j.executeScript("arguments[0].click();", spldel);
		 * 
		 */ sleep(2000);

		// patient info
		WebElement patientinfoedit = driver.findElement(By.xpath("(//span[@id='edit-btn'])[1]"));
		visbility(driver, patientinfoedit, 60);
		actions("click", patientinfoedit);
		sleep(2000);
		WebElement gender = driver.findElement(By.xpath("(//button[@id='gender'])[1]"));
		visbility(driver, gender, 60);
		j.executeScript("arguments[0].click();", gender);
		List<WebElement> genderdropdown = driver
				.findElements(By.xpath("(//button[@id='gender'])[1]//following::ul[1]/li"));
		for (WebElement web : genderdropdown) {
			if (web.getText().trim().equals("Male")) {
				visbility(driver, web, 60);
				web.click();
				break;
			}

		}
		WebElement education = driver.findElement(By.xpath("(//input[@id='educationform'])[1]"));
		visbility(driver, education, 60);
		education.clear();
		education.sendKeys("B.tech");
		sleep(2000);
		WebElement license = driver.findElement(By.xpath("(//input[@id='licensenum'])[1]"));
		visbility(driver, license, 60);
		license.clear();

		license.sendKeys("trt43534");
		sleep(2000);
		WebElement saveadmininfo = driver.findElement(By.xpath("(//button[@id='save-btn'])[1]"));
		visbility(driver, saveadmininfo, 60);
		j.executeScript("arguments[0].click();", saveadmininfo);

		WebElement trp7 = driver.findElement(By.xpath("(//button[@onclick='Page.goBack()'])[1]"));
		visbility(driver, trp7, 60);
		click(trp7);// .click();

		WebElement trp9 = driver.findElement(By.xpath("//button[@onclick='setting.changep()']"));
		visbility(driver, trp9, 60);
		click(trp9);// .click();
		WebElement trp10 = driver.findElement(By.id("currentPassword"));
		visbility(driver, trp10, 60);
		sendkeys(trp10, "1");// sendKeys("1");
		WebElement trp11 = driver.findElement(By.id("newPassword"));
		visbility(driver, trp11, 60);
		sendkeys(trp11, "1");// .sendKeys("1");
		WebElement trp12 = driver.findElement(By.id("confirmNewPassword"));
		visbility(driver, trp12, 60);
		sendkeys(trp12, "1");// .sendKeys("1");

		WebElement trp13 = driver.findElement(By.xpath("//button[@onclick='changep.save()']"));
		visbility(driver, trp13, 60);
		click(trp13);// .click(); sleep(3000);

		// Manage users...
		WebElement manageuser = driver.findElement(By.xpath("//button[@onclick='setting.userList()']"));
		visbility(driver, manageuser, 60);
		manageuser.click();
		sleep(2000);
		WebElement adduser = driver.findElement(By.xpath("//button[@onclick='Health.user_new()']"));
		visbility(driver, adduser, 60);
		adduser.click();
		sleep(2000);
		WebElement trp14 = driver.findElement(By.xpath("//input[@id='Firstname']"));
		visbility(driver, trp14, 60);
		sendkeys(trp14, "Akash");// .sendKeys("Akash");
		WebElement trp15 = driver.findElement(By.xpath("//input[@id='LastName']"));
		visbility(driver, trp15, 60);
		sendkeys(trp15, "N");// .sendKeys("N");
		sleep(2000);
		WebElement trp16 = driver.findElement(By.xpath("//input[@id='UserLoginId']"));
		visbility(driver, trp16, 60);
		sendkeys(trp16, "Akashn1212@gmail.com");
		// .sendKeys("Akashn1212@gmail.com");
		sleep(2000);
		WebElement trp17 = driver.findElement(By.xpath("//button[@id='user_dropdown']"));
		visbility(driver, trp17, 60);
		click(trp17);// .click();
		List<WebElement> usserdrp = driver.findElements(By.xpath("//button[@id='user_dropdown']//following::ul[1]/li"));
		for (WebElement web : usserdrp) {
			if (web.getText().trim().equals("Standard User")) {
				visbility(driver, web, 60);
				web.click();
				break;
			}

		}
		sleep(2000);
		WebElement phone = driver.findElement(By.xpath("//input[@id='PhonE']"));

		phone.sendKeys("2013556237");
		sleep(2000);
		// WebElement createuser =
		// driver.findElement(By.xpath("(//button[@id='createButton'])[2]"));
		WebElement canceluser = driver.findElement(By.xpath("(//button[@onclick='window.history.back()'])[2]"));
		visbility(driver, canceluser, 60);
		canceluser.click(); //
		// createuser.click();
		sleep(3000);
		click(pom.getInstanceSetting().clickSettings);
		sleep(5000);

		WebElement frs = driver.findElement(By.xpath("//button[@id='auto-logout-time']"));
		visbility(driver, frs, 60);
		click(frs);// .click(); sleep(2000);
		List<WebElement> time = driver.findElements(By.xpath("//ul[@id='logoutt']/li"));
		for (WebElement w : time) {

			if (w.getText().trim().equals("2 Hour")) {
				visbility(driver, w, 60);
				w.click();
				break;
			}

		}
		sleep(3000);

		/*
		 * WebElement s1 = driver.findElement(By.xpath("//button[@id='taxbutton']"));
		 * visbility(driver, s1, 60); click(s1);// .click();
		 * 
		 * WebElement cl = driver.findElement(By.xpath(
		 * "/html/body/div[4]/div[4]/div[1]/div[2]/div/table/tbody/tr/td[2]/div/div[6]/div[8]/div/div[1]/div[1]/div[6]/div[1]/div/div[3]/div[1]/ul/li/a"
		 * )); visbility(driver, cl, 60); actions("click", cl); sleep(3000); WebElement
		 * s2 = driver.findElement(By.xpath("(//span[@id='plus-add'])[1]"));
		 * visbility(driver, s2, 60); click(s2);// .click(); sleep(4000); WebElement s3
		 * = driver.findElement(By.xpath("(//input[@id='description'])[2]"));
		 * visbility(driver, s3, 60); sendkeys(s3, "DK");// .sendKeys("DK"); WebElement
		 * s4 = driver.findElement(By.xpath("(//input[@id='percentage'])[2]"));
		 * visbility(driver, s4, 60); sendkeys(s4, "5");// .sendKeys("5");
		 * 
		 * WebElement s5 =
		 * driver.findElement(By.xpath("(//button[@id='save-btn'])[10]"));
		 * visbility(driver, s5, 60); click(s5);// .click(); sleep(3000); WebElement mn
		 * = driver.findElement(By.xpath("//div[text()='DK']")); visbility(driver, mn,
		 * 60); actions("click", mn); sleep(3000); WebElement s6 =
		 * driver.findElement(By.xpath("(//span[@id='del-btn'])[2]")); visbility(driver,
		 * s6, 60); click(s6);// .click(); sleep(2000); WebElement s7 =
		 * driver.findElement(By.xpath("(//span[@title='Cancel'])[2]"));
		 * visbility(driver, s7, 60); click(s7);// .click(); sleep(3000);
		 */
		// cds

		WebElement cdsclick = driver.findElement(By.xpath("//button[contains(text(),'Clinical Decision')]"));
		visbility(driver, cdsclick, 60);
		click(cdsclick);
		sleep(2000);
		WebElement newcds = driver.findElement(By.xpath("//span[contains(text(),'New Clinical')]"));
		visbility(driver, newcds, 60);
		click(newcds);

		sleep(2000);
		while (true) {
			try {
				WebElement s8 = driver.findElement(By.xpath("(//input[@title='Enter CDS name.'])"));
				visbility(driver, s8, 60);
				sendkeys(s8, "Akash");// .sendKeys("Akash");
				break;
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		sleep(2000);
		WebElement scrolltill = driver.findElement(By.xpath("//input[@id='weight_from']"));
		ScriptExecutor(scrolltill);
		sleep(2000);
		visbility(driver, scrolltill, 60);
		WebElement s9 = driver.findElement(By.xpath("(//input[@id='problem_icd_10'])[2]"));
		visbility(driver, s9, 60);
		sendkeys(s9, "test");// .sendKeys("test");
		sleep(3000);
		WebElement actionproblem = driver.findElement(By.xpath("//div[text()='Malignant neoplasm of testis']"));
		visbility(driver, actionproblem, 60);
		actions("click", actionproblem);
		sleep(2000);
		WebElement activecheck = driver.findElement(By.xpath("//input[@id='active']"));
		visbility(driver, activecheck, 60);
		actions("click", activecheck);
		sleep(2000);
		WebElement savecds = driver.findElement(By.xpath("//input[@id='active']//following::div[4]/div/button"));
		visbility(driver, savecds, 60);
		javascriptclick(savecds);

		sleep(4000);

		WebElement clicksett = driver.findElement(By.xpath("//td[text()='Settings']"));
		visbility(driver, clicksett, 60);
		javascriptclick(clicksett);
		visbility(driver, cdsclick, 60);
		ScriptExecutor(cdsclick);

		sleep(3000);

		// Set Favorities..

		// problems.

		driver.findElement(By.xpath("//button[@onclick='setfavdropdown();']")).click();
		sleep(3000);
		List<WebElement> setfav1 = driver.findElements(By.xpath("//ul[@id='setfavoritesul']/li"));
		for (WebElement w : setfav1) {
			if (w.getText().trim().equals("Problems")) {
				visbility(driver, w, 60);
				w.click();

				WebElement problemadd = driver.findElement(By.xpath(
						"//div[@id='setfavorites']//following::div[2]/div[2]/div[1]/div/table/tbody/tr/td[4]/span[2]"));
				visbility(driver, problemadd, 60);
				javascriptclick(problemadd);
				sleep(2000);
				driver.findElement(By.xpath(
						"(//div[contains(text(),'ICD-10 / SNOMED / DESCRIPTION*')])[2]//following::div[2]/textarea"))
						.sendKeys("test");
				sleep(2000);
				List<WebElement> prbq = driver.findElements(By.xpath(
						"(//div[contains(text(),'ICD-10 / SNOMED / DESCRIPTION*')])[2]//following::div[3]/div/div//following::div[1]//following::ul[1]/li/a/div/small"));
				for (WebElement web : prbq) {

					if (web.getText().trim().equals("ICD10 : C62 | SNOMED : --")) {
						System.out.println("gello");
						visbility(driver, web, 60);
						javascriptclick(web);
						break;
					}
				}

				sleep(2000);
				WebElement dr2 = driver.findElement(By.xpath(
						"(//div[contains(text(),'ICD-10 / SNOMED / DESCRIPTION*')])[2]//parent::div[1]/div[3]//following::textarea[1]"));
				visbility(driver, dr2, 60);
				sendkeys(dr2, "high");

				WebElement saveproblem = driver.findElement(
						By.xpath("//div[@id='gap']//following::div[1]/div[4]/div[2]//following::div[3]/div"));
				visbility(driver, saveproblem, 60);
				javascriptclick(saveproblem);

				sleep(2000);

				WebElement editprob = driver.findElement(By.xpath("//span[text()='Malignant neoplasm of testis']"));

				actions("click", editprob);
				sleep(3000);
				WebElement delprob = driver
						.findElement(By.xpath("(//div[@id='diagnosis'])[1]/div[3]/div/div/div[1]/div[2]/span[1]"));
				visbility(driver, delprob, 60);
				j.executeScript("arguments[0].click();", delprob);

				WebElement cancelfavprob = driver.findElement(
						By.xpath("(//span[contains(text(),'Favorite Problems')])[1]//parent::div/span[1]"));
				visbility(driver, cancelfavprob, 60);
				j.executeScript("arguments[0].click();", cancelfavprob);
				sleep(3000);
				driver.findElement(By.xpath("//button[@onclick='setfavdropdown();']")).click();

				implicitWait(30, TimeUnit.SECONDS);

			} else if (w.getText().trim().equals("Item/service")) {

				visbility(driver, w, 60);
				w.click();

				WebElement clickadditem = driver.findElement(By.xpath(
						"//div[@id='referral']//following::div[1]/div[2]/div[1]/div/table/tbody/tr/td[4]/span[2]"));
				visbility(driver, clickadditem, 60);
				click(clickadditem);
				WebElement sdf = driver.findElement(By.xpath(
						"(//div[contains(text(),'Type or select item/service and price')])[2]//following::input[1]"));
				visbility(driver, sdf, 60);
				sendkeys(sdf, "test"); // .sendKeys("test");
				WebElement sdf2 = driver.findElement(By.xpath(
						"(//div[contains(text(),'Type or select item/service and price')])[2]//following::input[2]"));
				visbility(driver, sdf2, 60);
				sendkeys(sdf2, "5"); // .sendKeys("5");
				WebElement saveitem = driver.findElement(
						By.xpath("//div[@id='referral']//following::div[1]/div[3]/div/div/div[2]/div[6]/div/button"));
				visbility(driver, saveitem, 60);
				javascriptclick(saveitem);

				sleep(2000);
				WebElement edititem = driver.findElement(By.xpath("//span[text()='test']"));
				visbility(driver, edititem, 60);
				actions("click", edititem);
				WebElement deleteitem = driver.findElement(
						By.xpath("//div[@id='referral']//following::div[1]/div[3]/div/div/div[1]/div[2]/span[1]"));
				visbility(driver, deleteitem, 60);
				javascriptclick(deleteitem);

				WebElement itemservicebackarrow = driver
						.findElement(By.xpath("(//div[@id='invoiceAdd'])[1]/div[1]/div[1]/span[1]"));
				visbility(driver, itemservicebackarrow, 60);
				javascriptclick(itemservicebackarrow);
				sleep(3000);
				driver.findElement(By.xpath("//button[@onclick='setfavdropdown();']")).click();

			} else if (w.getText().trim().equals("Message")) {
				visbility(driver, w, 60);
				w.click();

				WebElement addnewfavmessage = driver
						.findElement(By.xpath("(//div[@id='message'])[1]/div[1]/div//following::td[4]/span[2]"));
				visbility(driver, addnewfavmessage, 60);
				javascriptclick(addnewfavmessage);

				WebElement msf = driver.findElement(By.xpath(
						"(//div[contains(text(),'Add your favorite message and use them quickly')])[2]//following::div[2]/textarea"));

				visbility(driver, msf, 60);
				sendkeys(msf, "hello");
				WebElement savemesssage = driver
						.findElement(By.xpath("(//div[@id='message'])[1]/div[3]/div/div/div[2]/div[5]/div/div"));
				visbility(driver, savemesssage, 60);
				javascriptclick(savemesssage);
				sleep(2500);
				WebElement editmessage = driver.findElement(By.xpath("//div[text()='hello']"));
				actions("click", editmessage);
				WebElement deletemessage = driver
						.findElement(By.xpath("(//div[@id='message'])[1]/div[3]/div/div/div[1]/div[2]/span[1]"));
				visbility(driver, deletemessage, 60);
				javascriptclick(deletemessage);

				sleep(3000);
				WebElement gobackmessage = driver
						.findElement(By.xpath("(//span[text()='Favorite Message'])[1]//parent::div/span[1]"));
				visbility(driver, gobackmessage, 60);
				javascriptclick(gobackmessage);
				sleep(3000);
				driver.findElement(By.xpath("//button[@onclick='setfavdropdown();']")).click();

				sleep(3000);

			} else if (w.getText().trim().equals("Advance Directives")) {
				visbility(driver, w, 60);
				w.click();

				WebElement addadvfav = driver
						.findElement(By.xpath("(//div[@id='directives'])[1]/div[1]/div//following::td[4]/span[2]"));
				visbility(driver, addadvfav, 60);
				javascriptclick(addadvfav);

				WebElement selectadvdir = driver.findElement(
						By.xpath("(//div[contains(text(),'Enter favorite directives')])[2]//following::select[1]"));
				visbility(driver, selectadvdir, 60);
				selectadvdir.click();

				dropDown("text", selectadvdir, "Assessment");
				WebElement fv = driver
						.findElement(By.xpath("//textarea[contains(@title,'Enter favorite Assessment details')]"));

				visbility(driver, fv, 60);
				sendkeys(fv, "test");// .sendKeys("test");
				WebElement saveaddvancedir = driver
						.findElement(By.xpath("(//div[@id='directives'])[1]/div[3]/div/div/div[2]/div[6]/div/div"));
				visbility(driver, saveaddvancedir, 60);
				javascriptclick(saveaddvancedir);

				sleep(3000);
				WebElement editadv = driver.findElement(By.xpath("//span[text()='Assessment']"));
				visbility(driver, editadv, 60);
				actions("click", editadv);
				WebElement deladv = driver
						.findElement(By.xpath("(//div[@id='directives'])[1]/div[3]/div/div/div[1]/div[2]/span[1]"));
				visbility(driver, deladv, 60);
				javascriptclick(deladv);

				WebElement gobackadvfav = driver.findElement(
						By.xpath("(//span[contains(text(),'Favorite Advance Directives')])[1]//parent::div/span[1]"));
				visbility(driver, gobackadvfav, 60);
				javascriptclick(gobackadvfav);
				sleep(2500);
				driver.findElement(By.xpath("//button[@onclick='setfavdropdown();']")).click();

				sleep(3000);

			} // else if (w.getText().trim().equals("Physical Examinations")) {//
			/*
			 * visbility(driver, w, 60); w.click();
			 * 
			 * WebElement ps1 = driver.findElement( By.xpath(
			 * "(//div[@id='physical-module'])[1]/div[1]/div//following::td[4]/span[2]"));
			 * visbility(driver, ps1, 60); javascriptclick(ps1); WebElement ps2 =
			 * driver.findElement( By.
			 * xpath("(//input[contains(@placeholder,'Enter name of the examined part here')])[2]"
			 * )); visbility(driver, ps2, 60); sendkeys(ps2, "physical examin");
			 * 
			 * WebElement ps3 = driver .findElement(By.
			 * xpath("(//textarea[contains(@placeholder,'Enter finding details here')])[2]")
			 * );
			 * 
			 * visbility(driver, ps3, 60); sendkeys(ps3, "hello"); WebElement ps4 = driver
			 * .findElement(By.
			 * xpath("(//textarea[contains(@placeholder,'Enter notes summary')])[2]"));
			 * visbility(driver, ps4, 60); sendkeys(ps4, "jsp"); WebElement ps5 =
			 * driver.findElement( By.xpath(
			 * "(//div[@id='physical-module'])[1]/div[3]/div/div/div[2]/div[8]/div/div"));
			 * visbility(driver, ps5, 60); javascriptclick(ps5); sleep(2500); WebElement ps8
			 * = driver.findElement(By.xpath("(//div[text()='physical examin'])[1]"));
			 * visbility(driver, ps8, 60); javascriptclick(ps8);
			 * 
			 * WebElement delpys = driver.findElement( By.xpath(
			 * "(//div[@id='physical-module'])[1]/div[3]/div/div/div[1]/div[2]/span[1]"));
			 * visbility(driver, delpys, 60); javascriptclick(delpys);
			 * 
			 * WebElement ps6 = driver.findElement(By
			 * .xpath("(//span[contains(text(),'Favorite Physical Examinations')])[1]//parent::div/span[1]"
			 * )); visbility(driver, ps6, 60); javascriptclick(ps6);
			 */
			// }

		} // Hospital codes... // Item/service code...
		sleep(3000);
		WebElement jkk = driver.findElement(By.xpath("//button[@onclick='hospitalcodedropdown();']"));
		visbility(driver, jkk, 60);
		javascriptclick(jkk);
		List<WebElement> fh = driver.findElements(By.xpath("//ul[@id='hospitalcodeul']/li"));
		for (WebElement w : fh) {
			if (w.getText().trim().equals("Item/Service Code")) {
				visbility(driver, w, 60);
				w.click();

				WebElement itemcodeadd = driver.findElement(
						By.xpath("(//div[@id='item-code'])[1]/div[2]/div[1]/div/table/tbody/tr/td[4]/span[3]"));
				visbility(driver, itemcodeadd, 60);
				javascriptclick(itemcodeadd);

				WebElement itemdis = driver
						.findElement(By.xpath("(//label[text()='Item/Service Code*'])[2]//following::div[1]/input"));
				visbility(driver, itemdis, 60);
				sendkeys(itemdis, "160899"); // .sendKeys("160899");
				WebElement code2 = driver.findElement(
						By.xpath("(//label[text()='Item/Service Name/Description*'])[2]//following::div[1]/textarea"));
				visbility(driver, code2, 60);
				sendkeys(code2, "Birthday"); // .sendKeys("Birthday");
				WebElement code3 = driver.findElement(
						By.xpath("(//input[contains(@title,'Enter price value for the Item/Service')])[2]"));
				visbility(driver, code3, 60);
				sendkeys(code3, "5"); // .sendKeys("5");
				WebElement hg = driver.findElement(By.xpath(
						"(//input[contains(@title,'Enter price value for the Item/Service')])[2]//following::button[1]"));

				visbility(driver, hg, 60);

				javascriptclick(hg);

				sleep(3000);
				WebElement cc = driver.findElement(By.xpath("//div[text()='160899']"));
				actions("click", cc);

				WebElement vv = driver
						.findElement(By.xpath("(//div[@id='item-code'])[1]/div[3]/div/div/div[1]/div[2]/span[1]"));
				visbility(driver, vv, 60);
				javascriptclick(vv);

				WebElement th = driver.findElement(
						By.xpath("(//span[contains(text(),'Item/Service Code')])[1]//parent::div/span[1]"));
				visbility(driver, th, 60);
				javascriptclick(th);
				sleep(2400);
				driver.findElement(By.xpath("//button[@onclick='hospitalcodedropdown();']")).click();

			} /*
				 * else if (w.getText().trim().equals("Procedure Code")) { visbility(driver, w,
				 * 60); w.click();
				 * 
				 * WebElement addnewprocedurecode = driver.findElement( By.xpath(
				 * "(//div[@id='procedure-code'])[1]/div[2]/div[1]/div/table/tbody/tr/td[4]/span[3]"
				 * )); visbility(driver, addnewprocedurecode, 60);
				 * javascriptclick(addnewprocedurecode);
				 * 
				 * WebElement prcd2 = driver .findElement(By.
				 * xpath("(//label[text()='Procedure Code*'])[2]//following::div[1]/input"));
				 * visbility(driver, prcd2, 60); sendkeys(prcd2, "Procedure12"); //
				 * .sendKeys("Procedure12");
				 * 
				 * WebElement prcd3 = driver.findElement( By.
				 * xpath("(//label[text()='Procedure Name/Description*'])[2]//following::div[1]/textarea"
				 * )); visbility(driver, prcd3, 60); sendkeys(prcd3, "medicine");//
				 * .sendKeys("medicine"); WebElement prcd4 = driver .findElement(By.
				 * xpath("(//input[contains(@title,'Enter amount value for the procedure')])[2]"
				 * )); visbility(driver, prcd4, 60); sendkeys(prcd4, "2");// .sendKeys("2");
				 * WebElement saveprocedure = driver.findElement(By.xpath(
				 * "(//input[contains(@title,'Enter amount value for the procedure')])[2]//following::button[1]"
				 * )); visbility(driver, saveprocedure, 60); javascriptclick(saveprocedure);
				 * 
				 * sleep(3000); WebElement editprocedure =
				 * driver.findElement(By.xpath("//div[text()='PROCEDURE12']"));
				 * visbility(driver, editprocedure, 60); actions("click", editprocedure);
				 * WebElement delprocd = driver .findElement(By.xpath(
				 * "(//div[@id='procedure-code'])[1]/div[3]/div/div/div[1]/div[2]/span[1]"));
				 * visbility(driver, delprocd, 60); javascriptclick(delprocd);
				 * 
				 * WebElement gobackproced = driver .findElement(By.
				 * xpath("(//span[text()='Procedure Code'])[1]//parent::div/span[1]"));
				 * visbility(driver, gobackproced, 60); javascriptclick(gobackproced);
				 * sleep(2400);
				 * driver.findElement(By.xpath("//button[@onclick='hospitalcodedropdown();']")).
				 * click();
				 */

			// }
			else if (w.getText().trim().equals("Medication Code")) {

				visbility(driver, w, 60);
				w.click();

				WebElement clickaddmedd = driver.findElement(
						By.xpath("(//div[@id='drug-code'])[1]/div[2]/div[1]/div/table/tbody/tr/td[4]/span[3]"));
				visbility(driver, clickaddmedd, 60);
				j.executeScript("arguments[0].click();", clickaddmedd);
				WebElement md2 = driver
						.findElement(By.xpath("(//label[text()='Medication Code*'])[2]//following::div[1]/input"));
				visbility(driver, md2, 60);
				sendkeys(md2, "MED1");// .sendKeys("MED1");
				WebElement med3 = driver
						.findElement(By.xpath("(//input[contains(@title,'Enter name of the Medication')])[2]"));
				visbility(driver, med3, 60);
				sendkeys(med3, "medication med");// .sendKeys("medication med");
				WebElement med4 = driver.findElement(
						By.xpath("(//input[contains(@title,'Enter name of the Medication')])[2]//following::input[1]"));
				visbility(driver, med4, 60);
				sendkeys(med4, "powerful");// .sendKeys("powerful");
				WebElement med5 = driver.findElement(
						By.xpath("(//input[contains(@title,'Enter name of the Medication')])[2]//following::input[2]"));
				visbility(driver, med5, 60);
				sendkeys(med5, "kaaspro");// .sendKeys("kaaspro");
				WebElement med6 = driver.findElement(
						By.xpath("(//input[contains(@title,'Enter name of the Medication')])[2]//following::input[3]"));
				visbility(driver, med6, 60);
				sendkeys(med6, "3");// .sendKeys("3");
				WebElement savemedication = driver.findElement(By.xpath(
						"(//input[contains(@title,'Enter amount value for the Medication')])[2]//following::button[1]"));
				visbility(driver, savemedication, 60);
				javascriptclick(savemedication);
				sleep(3000);
				WebElement editmedication = driver.findElement(By.xpath("(//div[text()='MED1'])[1]"));
				visbility(driver, editmedication, 60);
				actions("click", editmedication);
				sleep(3000);
				WebElement delmed = driver
						.findElement(By.xpath("(//div[@id='drug-code'])[1]/div[3]/div/div/div[1]/div[2]/span[1]"));
				visbility(driver, delmed, 60);
				javascriptclick(delmed);

				WebElement gobackmed = driver
						.findElement(By.xpath("(//span[text()='Medication Code'])[1]//parent::div/span[1]"));
				visbility(driver, gobackmed, 60);
				j.executeScript("arguments[0].click();", gobackmed);
				sleep(3000);

			}

		}

		// forms...

		WebElement f1 = driver.findElement(By.xpath("//button[@id='form-script']"));
		visbility(driver, f1, 60);
		click(f1);// .click();
		sleep(5000);

		List<WebElement> numberofformspresent = driver
				.findElements(By.xpath("//div[@id='FormsKpop2']/div[2]/div/div[1]/div[1]/span[2]"));

		int ffs = numberofformspresent.size();
		System.out.println(ffs);

		for (WebElement web : numberofformspresent) {

			if (web.getText().isEmpty()) {
				// System.out.println("yes one is empty");
				continue;
			}

			else if (web.getText().trim().equals("form100")) {

				System.out.println("condition met the form");

				visbility(driver, web, 60);
				web.click();
				WebElement jsp = driver.findElement(By.xpath("(//span[@id='del-form'])[1]"));
				visbility(driver, jsp, 60);
				javascriptclick(jsp);
				driver.navigate().refresh();
				WebElement jspq = driver.findElement(By.xpath("//button[@id='form-script']"));
				visbility(driver, jspq, 60);
				javascriptclick(jspq);

				break;
			}

		}

		WebElement addfrm = driver.findElement(By.xpath("//div[@id='FormsKpop2']/div[1]/span"));
		visbility(driver, addfrm, 60);
		actions("click", addfrm);

		sleep(4000);
		WebElement f2 = driver.findElement(By.xpath("(//label[text()='Form Title*'])[1]//following::input[1]"));
		visbility(driver, f2, 60);
		sendkeys(f2, "form100");

		List<WebElement> drk = driver.findElements(By.xpath("(//div[@id='build-wrap'])[1]/div[1]/div[2]/ul/li"));

		for (WebElement web : drk) {

			if (web.getText().trim().equals("Checkbox Group")) {

				WebElement drop = driver.findElement(
						By.xpath("(//div[contains(@data-content,'Drag a field from the right to this area')])[1]/ul"));

				Actions ac = new Actions(driver);
				ac.dragAndDrop(web, drop).build().perform();
				WebElement f3 = driver.findElement(By.xpath("//label[text()='Label']//following::div[1]/input"));
				visbility(driver, f3, 60);
				clear(f3);// .clear();
				WebElement f4 = driver.findElement(By.xpath("//label[text()='Label']//following::div[1]/input"));
				visbility(driver, f4, 60);
				sendkeys(f4, "Kaaspro Enterprise");// .sendKeys("Kaaspro Enterprise");
				WebElement f5 = driver.findElement(
						By.xpath("(//div[@id='build-wrap'])[1]/div[1]/div[2]/ul//following::div[1]/button"));
				visbility(driver, f5, 60);
				click(f5);

				sleep(3000);

				break;
			}

		}

		/*
		 * WebElement cancelfrm =
		 * driver.findElement(By.xpath("//div[@id='FormsKpop2']/div[1]/div[2]/span"));
		 * javascriptclick(cancelfrm);
		 */
		sleep(3000); // edit preference....

		WebElement s10 = driver.findElement(By.xpath("//button[@id='edit-print-preference']"));
		visbility(driver, s10, 60);
		click(s10);
		sleep(3000);
		WebElement s11 = driver.findElement(By.xpath("(//button[@id='cancel-btn'])[18]"));
		visbility(driver, s11, 60);
		click(s11);
		sleep(3000);
		WebElement s12 = driver.findElement(By.xpath("//button[@title='Reset All Your Setting']"));
		visbility(driver, s12, 60);
		click(s12);// .click();
		sleep(2000);
		WebElement df = driver.findElement(By.xpath("(//button[text()='Cancel'])[1]"));
		visbility(driver, df, 60);
		javascriptclick(df);

		// sleep(3000);
		/*
		 * // notification
		 * 
		 * // WebElement er = driver.findElement(By.xpath("//span[text()='Edit
		 * Notification // Messages']"));
		 * 
		 * WebElement notify =
		 * driver.findElement(By.xpath("//button[@id='custom-notify']"));
		 * 
		 * boolean bb = true; while (bb) {
		 * 
		 * if (!notify.isDisplayed()) { WebElement uip = driver.findElement(By
		 * .xpath("//span[text()='Edit Notification Messages']//parent::div//parent::div[1]/label/input"
		 * )); // visbility(driver, uip, 60); actions("click", uip);
		 * 
		 * javascriptclick(notify); WebElement sd =
		 * driver.findElement(By.xpath("(//span[@class='slider1 round1'])[4]"));
		 * ScriptExecutor(sd); sleep(2000); js.executeScript("window.scrollBy(0,0)");
		 * 
		 * }
		 * 
		 * bb = false; } sleep(1500);
		 * 
		 * click(pom.getInstanceSetting().clickSettings);
		 * 
		 * // notify ehr complte... WebElement ntfyehr = driver.findElement(By
		 * 
		 * .xpath("//span[text()='Notify user when EHR is completed.']//parent::div//parent::div[1]/label/input"
		 * )); ScriptExecutor(ntfyehr); // visbility(driver, ntfyehr, 60);
		 * actions("click", ntfyehr);
		 * 
		 * // set interval time for emial...
		 * 
		 * WebElement rr = driver.findElement(By
		 * .xpath("//span[text()='Set interval for receiving emails.']//parent::div//parent::div[1]/label/input"
		 * )); // visbility(driver, rr, 60); actions("click", rr);
		 */
		// Audit Report...

		sleep(3000);
		visbility(driver, pom.getInstanceSetting().clickSettings, 60);
		click(pom.getInstanceSetting().clickSettings);
		WebDriverWait wait = new WebDriverWait(driver, 60);
		driver.navigate().refresh();
		sleep(3000);

		WebElement rqqa = driver.findElement(By.xpath("//button[@onclick='setting.audit()']"));
		ScriptExecutor(rqqa);
		visbility(driver, rqqa, 60);
		javascriptclick(rqqa);
		sleep(3000);
		WebElement s13 = driver.findElement(By.xpath("(//input[@id='patientPartyName'])[1]"));
		visbility(driver, s13, 60);
		sendkeys(s13, kpid);// .sendKeys(kpid);
		sleep(3000);
		List<WebElement> patdr = driver
				.findElements(By.xpath("//div[@id='vvid']//following::ul[1]/li/a/table/tbody/tr[1]/td[2]"));
		for (WebElement we : patdr) {

			if (we.getText().contains(kpid)) {
				visbility(driver, we, 60);
				we.click();
				break;
			}

		}

		sleep(3000);

		/*
		 * driver.findElement(By.xpath("(//input[@id='userPartyName'])[2]"))
		 * .sendKeys(ConfigManager.getconfigManager().getInstanceConfigReader().
		 * doctorKpid()); List<WebElement> userd = driver .findElements(By.xpath(
		 * "//div[@id='vvid']//following::ul[2]/li/a/table/tbody/tr[1]/td[2]"));
		 * 
		 * for (WebElement we : userd) { if
		 * (we.getText().contains(ConfigManager.getconfigManager().
		 * getInstanceConfigReader().doctorKpid())) {
		 * 
		 * we.click(); break;
		 * 
		 * }
		 * 
		 * }
		 */
		WebElement seldate = driver.findElement(By.xpath("//select[@id='byDate']"));
		visbility(driver, seldate, 60);
		dropDown("text", seldate, "All");
		sleep(4000);
		WebElement selmod = driver.findElement(By.xpath("//select[@id='auditEventModule']"));
		visbility(driver, selmod, 60);
		dropDown("text", selmod, "Allergy");
		sleep(3000);
		WebElement s14 = driver.findElement(By.xpath("//button[@id='advanceSearching']"));
		visbility(driver, s14, 60);
		click(s14);// .click();

		// explicitWait(50, pom.getInstanceSetting().clickSettings);
		sleep(3000);
		click(pom.getInstanceSetting().clickSettings);
		sleep(2000);

		/*
		 * explicitWait(40, pom.getInstanceSetting().scrollTillDelete);
		 * ScriptExecutor(pom.getInstanceSetting().scrollTillDelete); sleep(2000);
		 */
		ScriptExecutor(pom.getInstanceSetting().scrollTillTax);
		sleep(2000);
		ScriptExecutor(pom.getInstanceSetting().scrollTillHospitalCode);
		sleep(2000);
		// ScriptExecutor(pom.getInstanceSetting().scrollTillPrintSettings); //
		sleep(2000); //
		ScriptExecutor(pom.getInstanceSetting().scrollTillSubscription); //
		sleep(2000); //
		ScriptExecutor(pom.getInstanceSetting().scrollTillNotification); //
		sleep(2000); //
		ScriptExecutor(pom.getInstanceSetting().scrollTillAuditReport); // //
		// JavascriptExecutor js = (JavascriptExecutor) driver; // //
		js.executeScript("window.scroll(0,0)");
		sleep(2000);
		driver.navigate().refresh();

		// Dashboard
		WebElement $dashBoard$;
		while (true) {
			try {

				$dashBoard$ = driver.findElement(By.xpath("(//div[@id='option-setting'])[1]/div/img"));
				javascriptclick($dashBoard$);
				break;
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		List<WebElement> ths = driver
				.findElements(By.xpath("(//div[@id='option-setting'])[1]/div/img//following::ul[1]/li"));

		while (true) {

			for (WebElement st : ths) {
				if (st.getText().trim().equals("Home")) {
					st.click();
					sleep(1000);
					actions("click", $dashBoard$);
				} else if (st.getText().trim().equals("Dashboard")) {
					st.click();
					sleep(2000);
					actions("click", $dashBoard$);
				} else if (st.getText().trim().equals("Quick Tour")) {
					st.click();
					WebElement cncltr = driver.findElement(By.xpath("(//li[text()='NO, CANCEL TOUR'])[1]"));
					javascriptclick(cncltr);
					sleep(2000);
					actions("click", $dashBoard$);
				} else if (st.getText().trim().equals("Settings")) {
					st.click();
					sleep(2000);
					actions("click", $dashBoard$);
				} else if (st.getText().trim().equals("Migration Services")) {
					st.click();
					WebElement clmgr = driver
							.findElement(By.xpath("//h4[text()='Migration Services']//parent::div/button"));
					sleep(2000);
					actions("click", clmgr);
					sleep(2000);
					actions("click", $dashBoard$);
				} else if (st.getText().trim().equals("Sign out")) {
					st.click();

					break;
				}

			}
			break;
		}

	}

}
