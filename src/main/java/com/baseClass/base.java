package com.baseClass;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;

public class base {

	static WebDriverManager driver;
	static WebDriver wd;
	public static String value;
	static WebDriverWait wait;

	public static WebDriver setUp(String name) {

		if (name.equalsIgnoreCase("chrome")) {
			driver.chromedriver().setup();
			wd = new ChromeDriver();
		} else if (name.equalsIgnoreCase("firefox")) {

			driver.firefoxdriver().setup();
			;
			wd = new FirefoxDriver();
		} else if (name.equalsIgnoreCase("edge")) {

			driver.edgedriver().setup();
			;
			wd = new EdgeDriver();
		} else if (name.equals("ie")) {
			driver.iedriver().setup();

			wd = new InternetExplorerDriver();
		}
		wd.manage().window().maximize();
		return wd;
	}

	// click...
	public static void click(WebElement element) {

		element.click();

	}
	// send keys...

	public static void sendkeys(WebElement values, String input) {
		values.sendKeys(input);

	}

	// get current url....
	public static void currenturl() {
		String currentUrl = wd.getCurrentUrl();
		System.out.println(currentUrl);
	}

	// get tittle......
	public static void gettitle() {
		String title = wd.getTitle();
		System.out.println(title);

	}

	// close....
	public static void close() {
		wd.close();
	}

	// quite
	public static void quite() {
		wd.quit();

	}

	// url....

	public static void geturl(String url) {
		wd.get(url);

	}

	// thread.sleep...
	public static void sleep(int x) throws InterruptedException {
		Thread.sleep(x);

	}

	// clear....
	public static void clear(WebElement e) {
		e.clear();

	}

	// java script executor.....
	public static void ScriptExecutor(WebElement gg) {
		JavascriptExecutor cj = (JavascriptExecutor) wd;
		cj.executeScript("arguments[0].scrollIntoView(true);", gg);

	}

	public static void javascriptclick(WebElement a) {
		JavascriptExecutor js = (JavascriptExecutor) wd;
		js.executeScript("arguments[0].click();", a);

	}

	// take screenshot method...
	public static void takeSnap(String ss) throws IOException {

		TakesScreenshot ts = (TakesScreenshot) wd;
		File start = ts.getScreenshotAs(OutputType.FILE);
		File end = new File(ss);
		FileHandler.copy(start, end);
	}

	// alerts
	public static void alert(String type) {

		if (type.equalsIgnoreCase("ok")) {

			Alert ok = wd.switchTo().alert();
			ok.accept();
		} else if (type.equalsIgnoreCase("cancel")) {

			Alert dismiss = wd.switchTo().alert();
			dismiss.dismiss();
		}

	}

	// navigation methods..
	public static void navigateto(String o) {
		wd.navigate().to(o);

	}
	// navigate back..

	public static void navigateback() {
		wd.navigate().back();

	}

	public void $scrollTillDim$(int range) {
		JavascriptExecutor js = (JavascriptExecutor) wd;
		js.executeScript("window.scrollBy(0," + range + ")", "");

	}

	// navigate forward
	public static void navigateforward() {
		wd.navigate().forward();

	}

	// refresh
	public static void refresh() {
		wd.navigate().refresh();

	}

	// get text
	public static void gettext(WebElement f) {
		String text = f.getText();
		System.out.println(text);

	}

	// get attribute...
	public static void getattribute(WebElement d) {
		String attribute = d.getAttribute("value");
		System.out.println(attribute);

	}

	// is displayed or not
	public static void displayornot(WebElement k) {
		boolean o = k.isDisplayed();
		System.out.println(o);

	}

	// is enabled...
	public static void enableornot(WebElement l) {

		boolean b = l.isEnabled();
		System.out.println(b);

	}

	// is selected
	public static void isselected(WebElement b) {
		boolean j = b.isSelected();
		System.out.println(j);

	}

	// dropdown..
	public static void dropDown(String type, WebElement u, String data) {

		Select s = new Select(u);

		if (type.equalsIgnoreCase("value")) {

			s.selectByValue(data);

		}

		else if (type.equalsIgnoreCase("index")) {

			int ps = Integer.parseInt(data);

			s.selectByIndex(ps);
		} else if (type.equalsIgnoreCase("text")) {

			s.selectByVisibleText(data);

		}

	}

	public static void getOptions(String type, WebElement t) {

		Select ss = new Select(t);
		if (type.equalsIgnoreCase("get option")) {
			List<WebElement> options = ss.getOptions();
			System.out.println(options);
		} else if (type.equalsIgnoreCase("is multiple")) {
			boolean multiple = ss.isMultiple();
			System.out.println(multiple);
		} else if (type.equalsIgnoreCase("get all selected")) {

			List<WebElement> allSelectedOptions = ss.getAllSelectedOptions();
			System.out.println(allSelectedOptions);
		} else {
			WebElement firstSelectedOption = ss.getFirstSelectedOption();
			System.out.println(firstSelectedOption);
		}

	}

	// waits...

	public static void implicitWait(int x, TimeUnit yy) {
		wd.manage().timeouts().implicitlyWait(x, yy);

	}

	public static void explicitWait(int seconds, WebElement z) {

		// wait.until(ExpectedConditions.invisibilityOf(z));
	}

	// frames
	public static void frame(String type, WebElement u) {

		if (type.equalsIgnoreCase("index")) {

			wd.switchTo().frame(u);

		} else if (type.equalsIgnoreCase("WebElement")) {

			wd.switchTo().frame(u);
		} else if (type.equalsIgnoreCase("id or name")) {

			wd.switchTo().frame(u);
		}
	}

	// to come out of frame...
	public static void defaultcontent() {

		wd.switchTo().defaultContent();

	}

	// robot class.... key press
	public static void keypress() throws AWTException {
		Robot r = new Robot();
		r.keyPress(KeyEvent.VK_DOWN);
		r.keyRelease(KeyEvent.VK_DOWN);

	}

	// robot class enter...
	public static void keyenter() throws AWTException {
		Robot vv = new Robot();
		vv.keyPress(KeyEvent.VK_ENTER);
		vv.keyRelease(KeyEvent.VK_ENTER);
	}

	public static void actions(String type, WebElement a) {

		Actions ac = new Actions(wd);

		if (type.equalsIgnoreCase("click")) {

			ac.click(a).build().perform();
		} else if (type.equalsIgnoreCase("right click")) {

			ac.contextClick(a).build().perform();

		} else if (type.equalsIgnoreCase("double click")) {

			ac.doubleClick(a).build().perform();

		} else if (type.equalsIgnoreCase("move to element")) {

			ac.moveToElement(a).build().perform();
		}

	}

	public static void intoRange(int x) {
		JavascriptExecutor js = (JavascriptExecutor) wd;
		js.executeScript("window.scrollBy(0," + x + ")", "");
	}

	// Webdriver Waits...

	public static void visbility(WebDriver driver, WebElement element, int seconds) {
		WebDriverWait wait = new WebDriverWait(driver, seconds);
		wait.until(ExpectedConditions.visibilityOf(element));

	}

	public static void clickble(WebDriver driver, WebElement element, int seconds) {
		WebDriverWait wait = new WebDriverWait(driver, seconds);
		wait.until(ExpectedConditions.elementToBeClickable(element));

	}

	// Webdriver Waits...

	// public static String readData(String path, int sheetIndex, int rowIndex, int
	// cellIndex) throws Throwable {

	/*
	 * File f = new File(path);
	 * 
	 * FileInputStream fis = new FileInputStream(f);
	 * 
	 * Workbook w = new XSSFWorkbook(fis);
	 * 
	 * Sheet sh = w.getSheetAt(sheetIndex); Row r = sh.getRow(rowIndex); Cell c =
	 * r.getCell(cellIndex);
	 * 
	 * CellType ctype = c.getCellType();
	 * 
	 * if (ctype.equals(CellType.STRING)) {
	 * 
	 * value = c.getStringCellValue();
	 * 
	 * } else if (ctype.equals(CellType.NUMERIC)) {
	 * 
	 * double num = c.getNumericCellValue(); int a = (int) num;
	 * 
	 * value=String.valueOf(a);
	 * 
	 * }
	 * 
	 * return value;
	 */
	// }

	// carsol clic

}
