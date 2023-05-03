package com.solution.sparklemob;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SparkleMobSolution implements Runnable {

	String msisdn;

	String cmpUrl;
	
	String chromeDriverPath;

	public SparkleMobSolution(String msisdn, String cmpUrl, String chromeDriverPath) {
		this.msisdn = msisdn;
		this.cmpUrl = cmpUrl;
		this.chromeDriverPath = chromeDriverPath;
	}

	public static int k = 0;

	public void usingSalenium(String msisdn, String cmpUrl, String chromeDriverPath) {
		ChromeDriver driver = null;

		try {
			System.setProperty("webdriver.chrome.driver", chromeDriverPath);
//			ChromeOptions chromeOptions = new ChromeOptions();
//			chromeOptions.addArguments("--headless");
//			chromeOptions.addArguments("no-sandbox");
//			chromeOptions.addArguments("disable-extensions");
			driver = new ChromeDriver();
			driver.get(cmpUrl);

			driver.findElementById("telnumber").sendKeys(msisdn);
			driver.findElementById("btn_sbmit").click();

			// wait to let the modal box be visible
			WebDriverWait wait = new WebDriverWait(driver, 10);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("myModal")));

			// to fetch the web element of the modal container
			//System.out.println("Fetching the web element for modal container");
			WebElement modalContainer = driver.findElement(By.id("myModal"));

			if (modalContainer != null) {
				// to fetch the web elements of the modal content and interact with them
				// code to fetch content of modal body and verify it
				//System.out.println("Fetching modal body content and asserting it");
				WebElement elementInput = driver.findElement(By.id("pin_txt"));
				elementInput.sendKeys("1234");
				WebElement modalContentButton = driver.findElement(By.id("btn_submit_pin"));
				modalContentButton.click();
				System.out.println("------------ DONE --------------");
				driver.quit();
			} else {
				System.out.println("invalid number");
			}

		} catch (Exception e) {
			System.out.println("Invalid number");
		} finally {
			driver.quit();
		}
	}

	public void run() {
		// getProcessSolution(this.msisdn, this.cmpUrl);
		usingSalenium(msisdn, cmpUrl, chromeDriverPath);
	}
}
