package com.solution.ultimategamez;

import org.openqa.selenium.By;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import net.lightbody.bmp.BrowserMobProxy;
import net.lightbody.bmp.BrowserMobProxyServer;
import net.lightbody.bmp.client.ClientUtil;

public class UltimateGamezSolution implements Runnable {

	String msisdn;

	String cmpUrl;

	String chromeDriverPath;

	String ip;

	public UltimateGamezSolution(String msisdn, String cmpUrl, String ipAddress, String chromeDriverPath) {
		this.msisdn = msisdn;
		this.cmpUrl = cmpUrl;
		this.ip = ipAddress;
		this.chromeDriverPath = chromeDriverPath;
	}

	public static int k = 0;

	public void usingSalenium(String msisdn, String cmpUrl, String chromeDriverPath) {
		ChromeDriver driver = null;

		try {
			System.setProperty("webdriver.chrome.driver", chromeDriverPath);

			BrowserMobProxyServer browserMobProxyServer = new BrowserMobProxyServer();
			browserMobProxyServer.start(0);
			Proxy seleniumProxy = ClientUtil.createSeleniumProxy((BrowserMobProxy) browserMobProxyServer);
			browserMobProxyServer.addRequestFilter((request, contents, messageInfo) -> {
				request.headers().add("msisdn", msisdn);
				request.headers().add("X-Forwarded-For", ip);
				return null;
			});

			ChromeOptions chromeOptions = new ChromeOptions();
			String proxyOption = "--proxy-server=" + seleniumProxy.getHttpProxy();
			chromeOptions.addArguments("--headless");
			chromeOptions.addArguments(new String[] { proxyOption });
			//chromeOptions.addArguments(new String[] { "no-sandbox" });
			chromeOptions.addArguments(new String[] { "disable-extensions" });
			chromeOptions.addArguments(new String[] { "--disable-web-security" });
			chromeOptions.addArguments(new String[] { "--allow-running-insecure-content" });
			chromeOptions.addArguments(new String[] { "--ignore-ssl-errors=yes" });
			chromeOptions.addArguments(new String[] { "--ignore-certificate-errors" });
			driver = new ChromeDriver(chromeOptions);
			driver.get(cmpUrl);
			

//			WebDriverWait wait = new WebDriverWait(driver, 20);
//			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("textPhonenumber")));
			driver.findElementById("textPhonenumber").sendKeys(msisdn);
			WebElement webElement = driver.findElementByXPath("/html/body//form//button[@type='submit']");
			System.out.println("WebElement::" + webElement);
			driver.findElementByXPath("/html/body//form//button[@type='submit']").click();
			
			Boolean isPresent = driver.findElements(By.className("message")).size() > 0;
			
			if(!isPresent) {
				System.out.println("------------ DONE --------------");	
			} else {
				System.out.println("Not Done::::");
			}

		} catch (Exception e) {
			System.out.println("Not able to find element::" + e.getMessage());
		} finally {
			driver.quit();
		}
	}

	public void run() {
		// getProcessSolution(this.msisdn, this.cmpUrl);
		usingSalenium(msisdn, cmpUrl, chromeDriverPath);
	}

}
