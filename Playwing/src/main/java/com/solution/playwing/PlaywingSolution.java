package com.solution.playwing;

import com.gargoylesoftware.htmlunit.UnexpectedPage;
import com.gargoylesoftware.htmlunit.WebClient;

public class PlaywingSolution implements Runnable {
	String msisdn;

	String ip;

	String cmpUrl;

	public PlaywingSolution(String msisdn, String ip, String cmpUrl) {
		this.msisdn = msisdn;
		this.ip = ip;
		this.cmpUrl = cmpUrl;
	}

	public static int k = 0;

	public String getProcessSolution(String msisdn, String ip, String cmpUrl) {
		WebClient webClient = new WebClient();
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

			UnexpectedPage unexpectedPage = webClient.getPage(cmpUrl);

//			if (unexpectedPage != null) {
//
//				synchronized (unexpectedPage) {
//					webClient.waitForBackgroundJavaScript(3000L);
//				}
//
//				Page page = unexpectedPage.getEnclosingWindow().getEnclosedPage();
//
//				StringWebResponse stringWebResponse = new StringWebResponse(page.getWebResponse().getContentAsString(),
//						page.getUrl());
//				HtmlPage firstHtmlPage = new HtmlUnitNekoHtmlParser().parseHtml(stringWebResponse,
//						webClient.getCurrentWindow());
//
//				if (firstHtmlPage != null) {
//					
//					synchronized (firstHtmlPage) {
//						webClient.waitForBackgroundJavaScript(3000L);
//					}
//
//					System.out.println("URL--> " + page.getUrl().toString());
//					System.out.println("IP & Mobile Number -->" + ip + " & " + msisdn);
//
//					HtmlSubmitInput htmlSubmitInput = (HtmlSubmitInput) firstHtmlPage
//							.getElementById("submitformbutton");
//
//					if (htmlSubmitInput != null) {
//						UnexpectedPage secondPage = (UnexpectedPage) htmlSubmitInput.click();
//
//						synchronized (secondPage) {
//							webClient.waitForBackgroundJavaScript(3000L);
//						}
//
//						Page page2 = secondPage.getEnclosingWindow().getEnclosedPage();
//
//						StringWebResponse stringWebResponse2 = new StringWebResponse(
//								page2.getWebResponse().getContentAsString(), page2.getUrl());
//						HtmlPage secondHtmlPage = new HtmlUnitNekoHtmlParser().parseHtml(stringWebResponse2,
//								webClient.getCurrentWindow());
//
//						secondHtmlPage.getBody();
//
//						UnexpectedPage click = null;
//						HtmlSubmitInput secondPageButton = (HtmlSubmitInput) secondHtmlPage
//								.getElementById("verifyformbuttonAR");
//						if (secondPageButton != null)
//							click = secondPageButton.click();
//
//						if (click != null) {
//							System.out.println("------------ DONE --------------");
//						} else {
//							System.out.println("-----------------Not done yet---------------");
//						}
//					}
//
//				} else {
//					System.out.println("First HTML page not found");
//				}
//			} else {
//				System.out.println("Page Not Found");
//			}
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
