package com.solution.perusalenium;

import org.openqa.selenium.By;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import net.lightbody.bmp.BrowserMobProxy;
import net.lightbody.bmp.BrowserMobProxyServer;
import net.lightbody.bmp.client.ClientUtil;

public class PeruSaleniumSolution implements Runnable {
	
	String msisdn;

	String cmpUrl;
	
	String chromeDriverPath;
	
	String ip;

	public PeruSaleniumSolution(String msisdn, String cmpUrl, String ip, String chromeDriverPath) {
		this.msisdn = msisdn;
		this.cmpUrl = cmpUrl;
		this.chromeDriverPath = chromeDriverPath;
		this.ip = ip;
	}

	public static int k = 0;

	public void usingSalenium(String msisdn, String cmpUrl, String ip, String chromeDriverPath) {
		//OperaDriver driver = null;
		ChromeDriver driver = null;
		try {
			
			BrowserMobProxy proxy = new BrowserMobProxyServer();
	        proxy.start(0);
	        Proxy seleniumProxy = ClientUtil.createSeleniumProxy(proxy);

	        // put our custom header to each request
	        proxy.addRequestFilter((request, contents, messageInfo)->{
	            request.headers().add("msisdn", msisdn);
	            request.headers().add("X-Forwarded-For", ip);
	           // request.headers().add("User-Agent", "Mozilla/5.0 (Linux; Android 12; SM-S906N Build/QP1A.190711.020; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/80.0.3987.119 Mobile Safari/537.36\\r\\n");
	            System.out.println(request.headers().entries().toString());
	            return null;
	        });

			
			ChromeOptions chromeOptions = new ChromeOptions();
			//OperaOptions operaOptions = new OperaOptions();
			 String proxyOption = "--proxy-server=" + seleniumProxy.getHttpProxy();
			chromeOptions.addArguments("--headless");
			chromeOptions.addArguments("no-sandbox");
			chromeOptions.addArguments("disable-extensions");
			chromeOptions.addArguments(proxyOption);
			chromeOptions.addArguments("--disable-web-security");
			chromeOptions.addArguments("--allow-running-insecure-content");
			chromeOptions.addArguments("--ignore-ssl-errors=yes");
			chromeOptions.addArguments("--ignore-certificate-errors");
			//operaOptions.addArguments(proxyOption);
			//operaOptions.addArguments("--disable-web-security");
			//operaOptions.addArguments("--allow-running-insecure-content");
			System.setProperty("webdriver.chrome.driver", chromeDriverPath);
			//System.setProperty("webdriver.opera.driver", chromeDriverPath);
			//driver = new OperaDriver(operaOptions);
			driver = new ChromeDriver(chromeOptions);
			driver.get(cmpUrl);
			
			WebDriverWait wait = new WebDriverWait(driver, 10);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("btnSubmitSendPin")));
			
			//driver.findElementById("inputMobile").sendKeys(msisdn);
			driver.findElementByClassName("btnSubmitSendPin").click();
			WebDriverWait wait2 = new WebDriverWait(driver, 10);
			wait2.until(ExpectedConditions.visibilityOfElementLocated(By.className("btnSubmitSendPin")));
			
			//driver.findElementById("pin").sendKeys("1234");
			driver.findElementByClassName("btnSubmitSendPin").click();
			
			System.out.println("------------ DONE --------------");
			driver.quit();
			
		} catch (Exception e) {
			System.out.println("Error in getting response from url::" + e.getMessage());
		} finally {
			driver.quit();
		}
	}

	public void run() {
		usingSalenium(msisdn, cmpUrl, ip, chromeDriverPath);
	}

}
