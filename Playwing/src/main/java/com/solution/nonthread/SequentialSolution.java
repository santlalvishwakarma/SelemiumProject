package com.solution.nonthread;

import java.io.IOException;
import java.net.URL;

import com.gargoylesoftware.htmlunit.AlertHandler;
import com.gargoylesoftware.htmlunit.AppletConfirmHandler;
import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.HttpMethod;
import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.PromptHandler;
import com.gargoylesoftware.htmlunit.RefreshHandler;
import com.gargoylesoftware.htmlunit.StatusHandler;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebRequest;
import com.gargoylesoftware.htmlunit.WebResponse;
import com.gargoylesoftware.htmlunit.html.HtmlApplet;
import com.gargoylesoftware.htmlunit.html.HtmlObject;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.webstart.WebStartHandler;

public class SequentialSolution implements Runnable {

	String msisdn;

	String ip;

	String cmpUrl;

	public SequentialSolution(String msisdn, String ip, String cmpUrl) {
		this.msisdn = msisdn;
		this.ip = ip;
		this.cmpUrl = cmpUrl;
	}

	public static int k = 0;

	public String getProcessSolution(String msisdn, String ip, String cmpUrl) {
		WebClient webClient = new WebClient(BrowserVersion.BEST_SUPPORTED);
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
			
			webClient.setStatusHandler(new StatusHandler() {
				
				private static final long serialVersionUID = 2862902795870220693L;

				public void statusMessageChanged(Page page, String message) {
					System.out.println("status handler message::" + message);
				}
			});
			
			webClient.setAlertHandler(new AlertHandler() {
				
				private static final long serialVersionUID = -5473045241892709789L;

				public void handleAlert(Page page, String message) {
					System.out.println("alert handler message::" + message);
				}
			});
			
			webClient.setAppletConfirmHandler(new AppletConfirmHandler() {
				
				public boolean confirm(HtmlObject applet) {
					System.out.println("applet controller::");
					return false;
				}
				
				public boolean confirm(HtmlApplet applet) {
					System.out.println("applet controller::");
					return false;
				}
			});
			
			webClient.setPromptHandler(new PromptHandler() {
				
				public String handlePrompt(Page page, String message, String defaultValue) {
					System.out.println("PromptHandler::");
					return null;
				}
			});
			
			webClient.setRefreshHandler(new RefreshHandler() {
				
				public void handleRefresh(Page page, URL url, int seconds) throws IOException {
					System.out.println("RefreshHandler::" + url.toString());					
				}
			});
			
			webClient.setWebStartHandler(new WebStartHandler() {
				
				public void handleJnlpResponse(WebResponse webResponse) {
					System.out.println("WebStartHandler::");
				}
			});

			URL url2 = new URL(cmpUrl);
			WebRequest requestSettings2 = new WebRequest(url2, HttpMethod.GET);
			requestSettings2.setAdditionalHeader("X-Forwarded-For", ip);
			requestSettings2.setAdditionalHeader("msisdn", msisdn);
			System.out.println("Request Params -->\n" + requestSettings2.getRequestParameters() + "-->\nURL-->"
					+ requestSettings2.getUrl());
			HtmlPage firstHtmlPage = (HtmlPage) webClient.getPage(requestSettings2);
			
			System.out.println("URL--> " + firstHtmlPage.getUrl().toString());
			System.out.println("IP & Mobile Number -->" + ip + " & " + msisdn);

			if (firstHtmlPage != null) {
				
				long start = System.currentTimeMillis();
				
				synchronized (firstHtmlPage) {
					webClient.waitForBackgroundJavaScript(5000L);
				}
				System.out.println("current page url::" + webClient.getTopLevelWindows().get(0).getEnclosedPage().getUrl());
				long end = System.currentTimeMillis();
				System.out.println("------------ DONE --------------");
				System.out.println("Total time taken::::" + (end - start));
				
				

//				HtmlSubmitInput htmlSubmitInput = (HtmlSubmitInput) firstHtmlPage
//						.getByXPath("/html/body//form//input[@type='submit']").get(0);

//				if (htmlSubmitInput != null) {
//					HtmlPage secondPage = (HtmlPage) htmlSubmitInput.click();
//
//					synchronized (secondPage) {
//						webClient.waitForBackgroundJavaScript(3000L);
//					}
//
//					HtmlSubmitInput secondPageButton = (HtmlSubmitInput) secondPage.getElementById("submit");
//					if (secondPageButton != null) {
//						secondPageButton.click();
//						System.out.println("------------ DONE --------------");
//					} else {
//						System.out.println("-----------------Not done yet---------------");
//					}
//
//				}

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
