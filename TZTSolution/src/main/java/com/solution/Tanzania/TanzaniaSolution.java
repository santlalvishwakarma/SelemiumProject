package com.solution.Tanzania;

import java.net.URL;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.ConfirmHandler;
import com.gargoylesoftware.htmlunit.HttpMethod;
import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebRequest;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class TanzaniaSolution implements Runnable {
	String msisdn;

	String ip;

	String cmpUrl;

	int counter;

	public TanzaniaSolution(String msisdn, String ip, String cmpUrl) {
		this.msisdn = msisdn;
		this.ip = ip;
		this.cmpUrl = cmpUrl;
	}

	public static int k = 0;

	public String getProcessSolution(String msisdn, String ip, String cmpUrl) {
		WebClient webClient = null;
		try {
			java.util.logging.Logger.getLogger("com.gargoylesoftware").setLevel(java.util.logging.Level.OFF);
			webClient = new WebClient(BrowserVersion.BEST_SUPPORTED);

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

			webClient.setConfirmHandler(new ConfirmHandler() {

				public boolean handleConfirm(Page page, String message) {
					counter++;
					//System.out.println("confirm popup message::" + message);
					System.out.println("popup count--->" + counter);
					// System.out.println("------------ DONE --------------");
					return true;
				}
			});

			URL url2 = new URL(cmpUrl);
			WebRequest requestSettings2 = new WebRequest(url2, HttpMethod.GET);
			requestSettings2.setAdditionalHeader("X-Forwarded-For", ip);
			requestSettings2.setAdditionalHeader("msisdn", msisdn);
			System.out.println("Request Params -->\n" + requestSettings2.getRequestParameters() + "-->\nURL-->"
					+ requestSettings2.getUrl());
			HtmlPage page22 = (HtmlPage) webClient.getPage(requestSettings2);

			System.out.println("URL--> " + page22.getBaseURI());

			boolean anchorFlag = false;

			while (!anchorFlag) {
				HtmlAnchor anchor = (HtmlAnchor) (page22.getElementById("gnrbtn"));
				if (anchor != null) {

					HtmlPage htmlPage = anchor.click();

					synchronized (htmlPage) {
						webClient.waitForBackgroundJavaScript(3000L);
					}
					
					if (htmlPage.getUrl().getPath().contains("Success")) {
						anchorFlag = true;
						System.out.println("page Url Path::" + htmlPage.getUrl());
						System.out.println("------------ DONE --------------" + counter);
					}
					
					if (htmlPage.getUrl().getPath().contains("Failed") || htmlPage.getUrl().getPath().contains("failed")) {
						anchorFlag = true;
						System.out.println("page Url Path::" + htmlPage.getUrl());
						System.out.println("url failed not done");
					}

//					HtmlAnchor secondAnchor = (HtmlAnchor) (page22.getElementById("gnrbtn"));
//
//					if (secondAnchor != null) {
//
//						HtmlPage secondClickPage = (HtmlPage) secondAnchor.click();
//
//						Page page = secondClickPage.getPage();
//						page.getUrl();
//						page.getWebResponse().getContentAsString();
//						synchronized (secondClickPage) {
//							webClient.waitForBackgroundJavaScript(3000L);
//						}
//					}

				} else {
					System.out.println("anchorFlag true::");
					anchorFlag = true;
				}
			}

		} catch (Exception e) {
			// e.printStackTrace();
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
