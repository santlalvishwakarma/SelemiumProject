package com.solution.sparklemob;

import java.net.URL;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.HttpMethod;
import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebRequest;
import com.gargoylesoftware.htmlunit.html.HtmlButtonInput;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class SparkleMobSolution implements Runnable {

	String msisdn;

	String cmpUrl;

	public SparkleMobSolution(String msisdn, String cmpUrl) {
		this.msisdn = msisdn;
		this.cmpUrl = cmpUrl;
	}

	public static int k = 0;

	public String getProcessSolution(String msisdn, String cmpUrl) {
		WebClient webClient = new WebClient(BrowserVersion.CHROME);
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

			URL url2 = new URL(cmpUrl);
			WebRequest requestSettings2 = new WebRequest(url2, HttpMethod.GET);
			requestSettings2.setAdditionalHeader("msisdn", msisdn);
			System.out.println("Request Params -->\n" + requestSettings2.getRequestParameters() + "-->\nURL-->"
					+ requestSettings2.getUrl());
			HtmlPage firstHtmlPage = (HtmlPage) webClient.getPage(requestSettings2);

			if (firstHtmlPage != null) {

				synchronized (firstHtmlPage) {
					webClient.waitForBackgroundJavaScript(3000L);
				}

				System.out.println("URL--> " + firstHtmlPage.getUrl().toString());
				System.out.println("Mobile Number --> " + msisdn);

				HtmlButtonInput htmlButton = (HtmlButtonInput) firstHtmlPage.getElementById("btn_sbmit");

				HtmlInput htmlInput = (HtmlInput) firstHtmlPage.getElementById("telnumber");

				if (htmlInput != null) {
					htmlInput.setValueAttribute(msisdn);
					HtmlPage secondPage = (HtmlPage) htmlButton.click();

					Thread.sleep(5000);

					// DialogWindow dialogWindow = webClient.openDialogWindow(url, opener,
					// dialogArguments)

					HtmlPage htmlPage = (HtmlPage) webClient.getWebWindows().get(0).getEnclosedPage();

					HtmlButtonInput secondHtmlButton = (HtmlButtonInput) htmlPage.getElementById("btn_submit_pin");
					
					if(secondHtmlButton != null) {
						System.out.println("second button found");
					}

					synchronized (secondPage) {
						webClient.waitForBackgroundJavaScript(3000L);
					}

					if (secondPage != null) {

//						HtmlButtonInput secondHtmlButton = (HtmlButtonInput) secondPage
//								.getElementById("btn_submit_pin");

						HtmlInput secondHtmlInput = (HtmlInput) secondPage.getElementById("pin_txt");

						if (secondHtmlInput != null) {
							secondHtmlInput.setValueAttribute("1234");
							Page lastPage = secondHtmlButton.click();

							if (lastPage != null) {
								System.out.println("------------ DONE --------------");
							}
						} else {
							System.out.println("Second page input not found");
						}

					} else {
						System.out.println("Second Page not found");
					}
				} else {
					System.out.println("First page input not found");
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
		getProcessSolution(this.msisdn, this.cmpUrl);
	}
}
