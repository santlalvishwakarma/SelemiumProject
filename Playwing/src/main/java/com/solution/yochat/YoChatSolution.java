package com.solution.yochat;

import java.io.IOException;
import java.net.URL;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.HttpMethod;
import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.RefreshHandler;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebRequest;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;

public class YoChatSolution implements Runnable {
	
	String msisdn;

	String ip;

	String cmpUrl;

	public YoChatSolution(String msisdn, String ip, String cmpUrl) {
		this.msisdn = msisdn;
		this.ip = ip;
		this.cmpUrl = cmpUrl;
	}

	public static int k = 0;

	public String getProcessSolution(String msisdn, String ip, String cmpUrl) {
		WebClient webClient = new WebClient(BrowserVersion.FIREFOX);
		try {
			java.util.logging.Logger.getLogger("com.gargoylesoftware").setLevel(java.util.logging.Level.OFF);

			webClient.getOptions().setUseInsecureSSL(true);
			webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
			webClient.getOptions().setJavaScriptEnabled(true);
			webClient.getOptions().setRedirectEnabled(true);
			webClient.getOptions().setCssEnabled(true);
			webClient.getOptions().setThrowExceptionOnScriptError(false);
			webClient.getOptions().setTimeout(20000);
			webClient.setJavaScriptTimeout(8000L);
			webClient.addRequestHeader("X-Forwarded-For", ip);
			webClient.addRequestHeader("msisdn", msisdn);
			webClient.addRequestHeader("User-Agent",
					"Mozilla/5.0 (Linux; Android 12; SM-S906N Build/QP1A.190711.020; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/80.0.3987.119 Mobile Safari/537.36\r\n");
			webClient.waitForBackgroundJavaScript(10000L);
			webClient.addRequestHeader("Access-Control-Allow-Credentials", "true");
			webClient.addRequestHeader("Access-Control-Allow-Headers",
					"Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With");
			webClient.addRequestHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE, PUT");
			webClient.addRequestHeader("Access-Control-Max-Age", "3600");
			webClient.addRequestHeader("Cache-Control", "no-cache, must-revalidate, private");
			webClient.addRequestHeader("X-FRAME-OPTIONS", "SAMEORIGIN");
			webClient.addRequestHeader("Upgrade-Insecure-Requests", "1");
			webClient.getOptions().setUseInsecureSSL(true);
			
			webClient.setRefreshHandler(new RefreshHandler() {
				
				public void handleRefresh(Page page, URL url, int seconds) throws IOException {
					System.out.println("Refresh Handler::::" + url);
				}
			});

			URL url2 = new URL(cmpUrl);
			WebRequest requestSettings2 = new WebRequest(url2, HttpMethod.POST);
			requestSettings2.setAdditionalHeader("X-Forwarded-For", ip);
			requestSettings2.setAdditionalHeader("msisdn", msisdn);
			System.out.println("Request Params -->\n" + requestSettings2.getRequestParameters() + "-->\nURL-->"
					+ requestSettings2.getUrl());
			HtmlPage firstHtmlPage = (HtmlPage) webClient.getPage(requestSettings2);
			
			//Thread.sleep(3000);

			if (firstHtmlPage != null) {

				synchronized (firstHtmlPage) {
					webClient.waitForBackgroundJavaScript(3000L);
				}

				System.out.println("URL--> " + firstHtmlPage.getUrl().toString());
				System.out.println("IP & Mobile Number -->" + ip + " & " + msisdn);
				
				
				HtmlPage secondHtmlPage = (HtmlPage) webClient.getPage(firstHtmlPage.getUrl().toString());
				
				System.out.println("2nd page URL--> " + secondHtmlPage.getUrl().toString());
				
				HtmlInput htmlInput = secondHtmlPage.getElementByName("mobileno");
				
				if(htmlInput != null) {
					htmlInput.setValueAttribute(msisdn);
					
					HtmlSubmitInput htmlSubmitInput = (HtmlSubmitInput) firstHtmlPage.getElementById("someButton");
					
					if(htmlSubmitInput != null) {
						Page page = htmlSubmitInput.click();
						
						synchronized (page) {
							webClient.waitForBackgroundJavaScript(3000L);
						}
						
						if(page != null) {
							System.out.println("------------ DONE --------------");
						} else {
							System.out.println("-----------------Not done yet---------------");
						}
					} else {
						System.out.println("submit button on page not found");
					}
				} else {
					System.out.println("input element not found");
				}

			} else {
				System.out.println("First HTML page not found");
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Exception occured on MSISDN-->" + msisdn + "--> Error Msg-->" + e.getMessage());
		} finally {
			webClient.close();
		}
		return null;
	}

	public void run() {
		getProcessSolution(this.msisdn, this.ip, this.cmpUrl);
	}

}
