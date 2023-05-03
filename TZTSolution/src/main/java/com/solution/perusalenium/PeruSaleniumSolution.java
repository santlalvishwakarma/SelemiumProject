package com.solution.perusalenium;

import java.util.HashMap;
import java.util.Map;

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

public class PeruSaleniumSolution implements Runnable {

	String msisdn;

	String cmpUrl;

	String chromeDriverPath;

	String ip;
	static Map<Integer, String> userAgentMap = new HashMap<Integer, String>();
	int userAgentCounter;

	public PeruSaleniumSolution(String msisdn, String cmpUrl, String ip, String chromeDriverPath, int userAgentCounter) {
		this.msisdn = msisdn;
		this.cmpUrl = cmpUrl;
		this.chromeDriverPath = chromeDriverPath;
		this.ip = ip;
		this.userAgentCounter = userAgentCounter;
	}

	public static int k = 0;

	static {
		userAgentMap.put(1,
				"Mozilla/5.0 (Linux; Android 12; SM-S906N Build/QP1A.190711.020; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/80.0.3987.119 Mobile Safari/537.36");
		userAgentMap.put(2,
				"Mozilla/5.0 (Linux; Android 10; SM-G996U Build/QP1A.190711.020; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Mobile Safari/537.36");
		userAgentMap.put(3,
				"Mozilla/5.0 (Linux; Android 10; SM-G980F Build/QP1A.190711.020; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/78.0.3904.96 Mobile Safari/537.36");
		userAgentMap.put(4,
				"Mozilla/5.0 (Linux; Android 9; SM-G973U Build/PPR1.180610.011) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/69.0.3497.100 Mobile Safari/537.36");
		userAgentMap.put(5,
				"Mozilla/5.0 (Linux; Android 8.0.0; SM-G960F Build/R16NW) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/62.0.3202.84 Mobile Safari/537.36");
		userAgentMap.put(6,
				"Mozilla/5.0 (Linux; Android 7.0; SM-G892A Build/NRD90M; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/60.0.3112.107 Mobile Safari/537.36");
		userAgentMap.put(7,
				"Mozilla/5.0 (Linux; Android 7.0; SM-G930VC Build/NRD90M; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/58.0.3029.83 Mobile Safari/537.36");
		userAgentMap.put(8,
				"Mozilla/5.0 (Linux; Android 6.0.1; SM-G935S Build/MMB29K; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/55.0.2883.91 Mobile Safari/537.36");
		userAgentMap.put(9,
				"Mozilla/5.0 (Linux; Android 6.0.1; SM-G920V Build/MMB29K) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/52.0.2743.98 Mobile Safari/537.36");
		userAgentMap.put(10,
				"Mozilla/5.0 (Linux; Android 5.1.1; SM-G928X Build/LMY47X) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/47.0.2526.83 Mobile Safari/537.36");
		userAgentMap.put(11,
				"Mozilla/5.0 (Linux; Android 12; Pixel 6 Build/SD1A.210817.023; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/94.0.4606.71 Mobile Safari/537.36");
		userAgentMap.put(12,
				"Mozilla/5.0 (Linux; Android 11; Pixel 5 Build/RQ3A.210805.001.A1; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/92.0.4515.159 Mobile Safari/537.36");
		userAgentMap.put(13,
				"Mozilla/5.0 (Linux; Android 10; Google Pixel 4 Build/QD1A.190821.014.C2; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/78.0.3904.108 Mobile Safari/537.36");
		userAgentMap.put(14,
				"Mozilla/5.0 (Linux; Android 10; Google Pixel 4 Build/QD1A.190821.014.C2; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/78.0.3904.108 Mobile Safari/537.36");
		userAgentMap.put(15,
				"Mozilla/5.0 (Linux; Android 8.0.0; Pixel 2 Build/OPD1.170811.002; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/59.0.3071.125 Mobile Safari/537.36");
		userAgentMap.put(16,
				"Mozilla/5.0 (Linux; Android 7.1.1; Google Pixel Build/NMF26F; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/54.0.2840.85 Mobile Safari/537.36");
		userAgentMap.put(17,
				"Mozilla/5.0 (Linux; Android 6.0.1; Nexus 6P Build/MMB29P) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/47.0.2526.83 Mobile Safari/537.36");
		userAgentMap.put(18,
				"Mozilla/5.0 (Linux; Android 9; J8110 Build/55.0.A.0.552; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/71.0.3578.99 Mobile Safari/537.36");
		userAgentMap.put(19,
				"Mozilla/5.0 (Linux; Android 7.1.1; G8231 Build/41.2.A.0.219; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/59.0.3071.125 Mobile Safari/537.36");
		userAgentMap.put(20,
				"Mozilla/5.0 (Linux; Android 6.0.1; E6653 Build/32.2.A.0.253) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/52.0.2743.98 Mobile Safari/537.36");

	}

//	public void usingSalenium(String msisdn, String cmpUrl, String ip, String chromeDriverPath, int userAgentCounter) {
//		// OperaDriver driver = null;
//		ChromeDriver driver = null;
//		try {
//
//			BrowserMobProxy proxy = new BrowserMobProxyServer();
//			proxy.start(0);
//			Proxy seleniumProxy = ClientUtil.createSeleniumProxy(proxy);
//
//			// put our custom header to each request
//			proxy.addRequestFilter((request, contents, messageInfo) -> {
//				request.headers().remove("User-Agent");
//				request.headers().add("msisdn", msisdn);
//				request.headers().add("X-Forwarded-For", ip);
//				request.headers().add("Access-Control-Allow-Credentials", "True");
//				request.headers().add("Access-Control-Allow-Headers", "Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With");
//				request.headers().add("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE, PUT");
//				request.headers().add("Access-Control-Max-Age", "3600");
//				request.headers().add("Cache-Control", "no-cache, must-revalidate, private");
//				request.headers().add("X-FRAME-OPTIONS", "SAMEORIGIN");
//				//request.headers().add("User-Agent", userAgentMap.get(userAgentCounter));
//				//System.out.println(request.headers().entries().toString());
//				return null;
//			});
//
//			ChromeOptions chromeOptions = new ChromeOptions();
//			// OperaOptions operaOptions = new OperaOptions();
//			String proxyOption = "--proxy-server=" + seleniumProxy.getHttpProxy();
////			chromeOptions.addArguments("--headless");
////			chromeOptions.addArguments("no-sandbox");
//			chromeOptions.addArguments("disable-extensions");
//			chromeOptions.addArguments(proxyOption);
//			chromeOptions.addArguments("--disable-web-security");
//			chromeOptions.addArguments("--allow-running-insecure-content");
//			chromeOptions.addArguments("--ignore-ssl-errors=yes");
//			chromeOptions.addArguments("--ignore-certificate-errors");
//			//chromeOptions.addArguments("--user-agent="+ userAgentMap.get(userAgentCounter));
//			// operaOptions.addArguments(proxyOption);
//			// operaOptions.addArguments("--disable-web-security");
//			// operaOptions.addArguments("--allow-running-insecure-content");
//			System.setProperty("webdriver.chrome.driver", chromeDriverPath);
//			// System.setProperty("webdriver.opera.driver", chromeDriverPath);
//			// driver = new OperaDriver(operaOptions);
//			driver = new ChromeDriver(chromeOptions);
//			driver.get(cmpUrl);
//
//			System.out.println("user agent::::" + driver.executeScript("return navigator.userAgent"));
//			
//			WebDriverWait wait = new WebDriverWait(driver, 5);
//			wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("btnSubmitSendPin")));
//
//			// driver.findElementById("inputMobile").sendKeys(msisdn);
//			driver.findElementByClassName("btnSubmitSendPin").click();
//			wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("btnSubmitSendPin")));
//
//			Thread.sleep(1000);
//			driver.findElementByClassName("btnSubmitSendPin").click();
//
//			Thread.sleep(3000);
//			if(driver.getPageSource().contains("Lo sentimos, no puedes acceder a esta oferta. Hemos detectado actividad sospechosa."))
//				System.out.println("Sorry, you can't access this offer. We have detected suspicious activity.");
//			else
//				System.out.println("------------ DONE --------------");
//			driver.quit();
//
//		} catch (Exception e) {
//			System.out.println("Error in getting response from url::" + e.getMessage());
//		} finally {
//			driver.quit();
//		}
//	}
	
	
	  public void usingSalenium(String msisdn, String cmpUrl, String ip, String chromeDriverPath, int userAgentCounter) {
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
		      //chromeOptions.addArguments(new String[] { "--headless" });
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
		      WebDriverWait wait = new WebDriverWait((WebDriver)driver, 5);
		      wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("btnSubmitSendPin")));
		      driver.findElementByClassName("btnSubmitSendPin").click();
		      WebDriverWait wait2 = new WebDriverWait((WebDriver)driver, 5);
		      wait2.until(ExpectedConditions.visibilityOfElementLocated(By.className("btnSubmitSendPin")));
		      Thread.sleep(1000);
		      driver.findElementByClassName("btnSubmitSendPin").click();
		      Thread.sleep(3000);
		      
				if(driver.getPageSource().contains("Lo sentimos, no puedes acceder a esta oferta. Hemos detectado actividad sospechosa."))
				System.out.println("Sorry, you can't access this offer. We have detected suspicious activity.");
			else
				System.out.println("------------ DONE --------------");
		      
		      driver.quit();
		    } catch (Exception e) {
		      System.out.println("Error in getting response from url::" + e.getMessage());
		    } finally {
		      driver.quit();
		    } 
		  }

	public void run() {
		usingSalenium(msisdn, cmpUrl, ip, chromeDriverPath, userAgentCounter);
	}

}
