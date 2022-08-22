package com.solution.twoclick.solingo;

import java.net.URL;

import com.gargoylesoftware.htmlunit.AlertHandler;
import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.HttpMethod;
import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebRequest;
import com.gargoylesoftware.htmlunit.html.HtmlButton;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class SolingoSolution implements Runnable {

	String msisdn;

	String ip;

	String cmpUrl;

	String updatedUrl;

	WebClient webClient = new WebClient(BrowserVersion.BEST_SUPPORTED);

	public SolingoSolution(String msisdn, String ip, String cmpUrl) {
		this.msisdn = msisdn;
		this.ip = ip;
		this.cmpUrl = cmpUrl;
	}

	public static int k = 0;

	public String getProcessSolution(String msisdn, String ip, String cmpUrl) {

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

//			webClient.setRefreshHandler(new RefreshHandler() {
//
//				public void handleRefresh(Page page, URL url, int seconds) throws IOException {
//					System.out.println("RefreshHandler::" + url.toString());
//					updatedUrl = url.toString();
//					webClient.getCache().clear();
//					HtmlPage htmlPage = webClient.getPage(url);
//					synchronized (htmlPage) {
//						webClient.waitForBackgroundJavaScript(5000L);
//					}
//				}
//			});
			
			webClient.setAlertHandler(new AlertHandler() {
				
				public void handleAlert(Page page, String message) {
					System.out.println("AlertHandler::");
				}
			});

			URL url2 = new URL(cmpUrl);
			WebRequest requestSettings2 = new WebRequest(url2, HttpMethod.GET);
			requestSettings2.setAdditionalHeader("X-Forwarded-For", ip);
			requestSettings2.setAdditionalHeader("msisdn", msisdn);
			System.out.println("Request Params -->\n" + requestSettings2.getRequestParameters() + "-->\nURL-->"
					+ requestSettings2.getUrl());
			HtmlPage firstHtmlPage = (HtmlPage) webClient.getPage(requestSettings2);

			Thread.sleep(5000);

			handlePage();

//			if (firstHtmlPage != null) {
//
//				synchronized (firstHtmlPage) {
//					webClient.waitForBackgroundJavaScript(3000L);
//				}
//
//				Thread.sleep(10000);
//				// firstHtmlPage.initialize();
//				System.out.println("URL--> " + firstHtmlPage.getUrl().toString());
//				System.out.println("IP & Mobile Number -->" + ip + " & " + msisdn);
//				firstHtmlPage.getUrl();
//
//				System.out.println("after while URL--> " + firstHtmlPage.getUrl().toString());
//
//				List<Object> submitFormList = firstHtmlPage.getByXPath("/html/body//form//input[@type='submit']");
//
//				if (submitFormList != null && submitFormList.size() > 0) {
//					HtmlSubmitInput htmlSubmitInput = (HtmlSubmitInput) firstHtmlPage
//							.getByXPath("/html/body//form//input[@type='submit']").get(0);
//
//					htmlSubmitInput.getValueAttribute();
//
//					HtmlInput htmlInput = (HtmlInput) firstHtmlPage.getElementById("inputPhone");
//
//					htmlInput.getValueAttribute();
//
//					if (htmlSubmitInput != null) {
//						HtmlPage secondPage = (HtmlPage) htmlSubmitInput.click();
//
//						synchronized (secondPage) {
//							webClient.waitForBackgroundJavaScript(3000L);
//						}
//
//						List<Object> secondPageFormList = secondPage
//								.getByXPath("/html/body//form//input[@type='submit']");
//
//						if (secondPageFormList != null && secondPageFormList.size() > 0) {
//							HtmlSubmitInput secondPageButton = (HtmlSubmitInput) secondPage
//									.getByXPath("/html/body//form//input[@type='submit']").get(0);
//
//							HtmlInput htmlInput2 = (HtmlInput) secondPage.getElementByName("pin_code");
//
//							htmlInput2.getValueAttribute();
//
//							if (secondPageButton != null) {
//								secondPageButton.click();
//
//								System.out.println("------------ DONE --------------");
//							} else {
//								System.out.println("-----------------Not done yet---------------");
//							}
//						}
//
//					}
//				} else {
//					System.out.println("submit link on page not found");
//				}
//
//			} else {
//				System.out.println("First HTML page not found");
//			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Exception occured on MSISDN-->" + msisdn + "--> Error Msg-->" + e.getMessage());
		} finally {
			webClient.close();
		}
		return null;
	}

	private void handlePage() {

		try {

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
			webClient.getCache().setMaxSize(0);

			URL url2 = new URL(cmpUrl);
			WebRequest requestSettings2 = new WebRequest(url2, HttpMethod.GET);
			requestSettings2.setAdditionalHeader("X-Forwarded-For", ip);
			requestSettings2.setAdditionalHeader("msisdn", msisdn);
			System.out.println("Request Params -->\n" + requestSettings2.getRequestParameters() + "-->\nURL-->"
					+ requestSettings2.getUrl());

			HtmlPage firstHtmlPage = webClient.getPage(requestSettings2);

			firstHtmlPage.getUrl().toString();
			
			System.out.println("URL--> " + firstHtmlPage.getUrl().toString());
			System.out.println("IP & Mobile Number -->" + ip + " & " + msisdn);

			HtmlInput htmlInput1 = (HtmlInput) firstHtmlPage.getElementById("inputPhone");
			
			htmlInput1.setValueAttribute("+923152001786");
			htmlInput1.setTextContent("+923152001786");
			firstHtmlPage.getPage().getWebResponse().getContentAsString();
			System.out.println("value of 1st input attribute--> " + htmlInput1.getValueAttribute());

			HtmlButton htmlButton = (HtmlButton) (firstHtmlPage.getForms().get(0).getElementsByTagName("button").get(0));
			
			if(htmlButton != null) {
				HtmlPage secondHtmlPage = (HtmlPage) htmlButton.click();
				
				Thread.sleep(5000);
				
				secondHtmlPage.getPage().getWebResponse().getContentAsString();
				
				HtmlButton secondHtmlButton = (HtmlButton) (secondHtmlPage.getForms().get(0).getElementsByTagName("button").get(0));
				secondHtmlPage.getByXPath("/html/body//h2");
				secondHtmlButton.getVisibleText();
				
				if(secondHtmlButton != null) {
					Page page = secondHtmlButton.click();
					page.getWebResponse().getContentAsString();
					page.getClass();
					
					HtmlInput htmlInput2 = (HtmlInput) secondHtmlPage.getElementById("inputPhone");

					System.out.println("value of 2nd input attribute--> " + htmlInput2.getValueAttribute());
					
					System.out.println("------------ DONE --------------");
				}
			}

			

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			webClient.close();
		}
	}

	public void run() {
		getProcessSolution(this.msisdn, this.ip, this.cmpUrl);
	}

}
