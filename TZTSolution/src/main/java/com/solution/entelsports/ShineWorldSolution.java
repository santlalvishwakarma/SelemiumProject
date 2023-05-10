package com.solution.entelsports;

import org.openqa.selenium.By;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import net.lightbody.bmp.BrowserMobProxy;
import net.lightbody.bmp.BrowserMobProxyServer;
import net.lightbody.bmp.client.ClientUtil;

public class ShineWorldSolution implements Runnable {
	
	String msisdn;

	String cmpUrl;

	String chromeDriverPath;

	String ip;
	
	int count;

	public ShineWorldSolution(String msisdn, String cmpUrl, String ip, String chromeDriverPath, int j) {
		this.msisdn = msisdn;
		this.cmpUrl = cmpUrl;
		this.chromeDriverPath = chromeDriverPath;
		this.ip = ip;
		this.count = j;
	}

	public static int k = 0;
	
	public void usingSalenium(String msisdn, String cmpUrl, String ip, String chromeDriverPath) {
	    ChromeDriver driver = null;
	    try {
	      BrowserMobProxyServer browserMobProxyServer = new BrowserMobProxyServer();
	      browserMobProxyServer.start(0);
	      Proxy seleniumProxy = ClientUtil.createSeleniumProxy((BrowserMobProxy)browserMobProxyServer);
	      browserMobProxyServer.addRequestFilter((request, contents, messageInfo) -> {
	            request.headers().add("msisdn", msisdn);
	            request.headers().add("X-Forwarded-For", ip);
	            return null;
	          });
	      ChromeOptions chromeOptions = new ChromeOptions();
	      String proxyOption = "--proxy-server=" + seleniumProxy.getHttpProxy();
	      chromeOptions.addArguments(new String[] { "--headless" });
	      chromeOptions.addArguments(new String[] { "no-sandbox" });
	      chromeOptions.addArguments(new String[] { "disable-extensions" });
	      chromeOptions.addArguments(new String[] { proxyOption });
	      chromeOptions.addArguments(new String[] { "--disable-web-security" });
	      chromeOptions.addArguments(new String[] { "--allow-running-insecure-content" });
	      chromeOptions.addArguments(new String[] { "--ignore-ssl-errors=yes" });
	      chromeOptions.addArguments(new String[] { "--ignore-certificate-errors" });
	      //chromeOptions.addArguments("--user-agent="+ userAgentMap.get(userAgentCounter));
	      System.setProperty("webdriver.chrome.driver", chromeDriverPath);
	      driver = new ChromeDriver(chromeOptions);
	      driver.get(cmpUrl);
	      WebDriverWait wait = new WebDriverWait((WebDriver)driver, 2);
	      wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("confirm_subscription")));
	     
//	      WebDriverWait wait2 = new WebDriverWait((WebDriver)driver, 10);
//	      wait2.until(ExpectedConditions.visibilityOfElementLocated(By.id("knowlege_of_subscription_rules")));
	      
	      driver.findElementById("knowlege_of_subscription_rules").click();
	      
	      driver.findElementById("confirm_subscription").click();
	      System.out.println("Waiting for 15 seconds.");
	      Thread.sleep(15000);
	      
	      
			if(driver.getPageSource().contains("Для продолжения подписки нужно подтвердить, что Вы ознакомлены с условиями и стоимостью оказания услуги."))
			System.out.println("Not Done.");
		else
			System.out.println("------------ DONE --------------");
	      System.out.println("count::" + count);
	      //driver.quit();
	    } catch (Exception e) {
	      System.out.println("Error in getting response from url::" + e.getMessage());
	    } finally {
	    	if(driver != null)
	    		driver.quit();
	    } 
	  }

public void run() {
	usingSalenium(msisdn, cmpUrl, ip, chromeDriverPath);
}

}
