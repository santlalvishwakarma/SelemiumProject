package com.solution.honduras;

import com.gargoylesoftware.htmlunit.AjaxController;
import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.HttpMethod;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebRequest;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import java.io.BufferedReader;
import java.io.IOException;
import java.net.URL;

public class HnSolution {
  public static int k = 0;
  
  public String getProcessSolution(String msisdn, String ip, String cmpUrl) {
    WebClient webClient = null;
    try {
      webClient = new WebClient(BrowserVersion.BEST_SUPPORTED);
      webClient.getOptions().setUseInsecureSSL(true);
      webClient.setAjaxController((AjaxController)new NicelyResynchronizingAjaxController());
      webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
      webClient.getOptions().setJavaScriptEnabled(true);
      webClient.getOptions().setRedirectEnabled(true);
      webClient.getOptions().setCssEnabled(true);
      webClient.getOptions().setThrowExceptionOnScriptError(false);
      webClient.setJavaScriptTimeout(8000L);
      webClient.addRequestHeader("X-FORWARDED-FOR", ip);
      webClient.addRequestHeader("msisdn", msisdn);
      webClient.addRequestHeader("USER-AGENT", 
          "Mozilla/5.0 (iPhone; CPU iPhone OS 9_2 like Mac OS X) AppleWebKit/601.1 (KHTML, like Gecko) CriOS/47.0.2526.70 Mobile/13C71 Safari/601.1.46");
      webClient.waitForBackgroundJavaScript(25000L);
      webClient.addRequestHeader("Access-Control-Allow-Credentials", "true");
      webClient.addRequestHeader("Access-Control-Allow-Headers", 
          "Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With");
      webClient.addRequestHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE, PUT");
      webClient.addRequestHeader("Access-Control-Max-Age", "3600");
      webClient.addRequestHeader("Cache-Control", "no-cache, must-revalidate, private");
      webClient.addRequestHeader("X-FRAME-OPTIONS", "SAMEORIGIN");
      webClient.getOptions().setUseInsecureSSL(true);
      webClient.getCache().setMaxSize(0);
      URL url2 = new URL(cmpUrl);
      WebRequest requestSettings2 = new WebRequest(url2, HttpMethod.GET);
      requestSettings2.setAdditionalHeader("X-FORWARDED-FOR", ip);
      requestSettings2.setAdditionalHeader("msisdn", msisdn);
      System.out.println("Request Params -->\n" + requestSettings2.getRequestParameters() + "-->\nURL-->" + 
          requestSettings2.getUrl());
      HtmlPage page22 = null;
      page22 = (HtmlPage)webClient.getPage(requestSettings2);
      synchronized (page22) {
        webClient.waitForBackgroundJavaScript(3000L);
      } 
      System.out.println("URL--> " + page22.getBaseURI());
      System.out.println("IP & Mobile Number -->" + ip + " & " + msisdn);
      HtmlSubmitInput submitButton = (HtmlSubmitInput)page22.getElementById("button_ok");
      if (submitButton == null) {
        URL url3 = new URL(
            "http://japiwap.com/hn/index.php?operator=6&landing=6&provider=2?msisdn=" + msisdn + 
            "&click_id=kHN25Q1F00ALPG1003PN1EB9J026O6010MIP0GIb07TU00MD026O600&medio=NONE");
        WebRequest requestSettings3 = new WebRequest(url3, HttpMethod.GET);
        requestSettings3.setAdditionalHeader("X-FORWARDED-FOR", ip);
        requestSettings3.setAdditionalHeader("msisdn", msisdn);
        System.out.println("Request Params -->\n" + requestSettings3.getRequestParameters() + "-->\nURL-->" + 
            requestSettings3.getUrl());
        page22 = (HtmlPage)webClient.getPage(requestSettings3);
        submitButton = (HtmlSubmitInput)page22.getElementById("button_ok");
      } 
      if (submitButton != null) {
        HtmlPage clickPage = (HtmlPage)submitButton.click();
        Page click = null;
        DomElement domElement = clickPage.getElementByName("action:accept");
        if (domElement != null)
          click = domElement.click(); 
        if (click != null) {
          System.out.println("------------ DONE --------------");
        } else {
          System.out.println("-----------------Not done yet---------------");
        } 
      } else {
        System.out.println("YES button on first page not found!");
      } 
    } catch (Exception e) {
      e.printStackTrace();
      System.out.println("Exception occured on MSISDN-->" + msisdn + "--> Error Msg-->" + e.getMessage());
    } finally {
      webClient.close();
    } 
    return null;
  }
  
  public static void main(String[] args) {
    String tempmssidn = "";
    BufferedReader in = null;
    try {
      HnSolution saSolution = new HnSolution();
      saSolution.getProcessSolution("50498737153", "186.2.129.197", "http://mediaxpedia.g2afse.com/click?pid=112&offer_id=24249");
    } catch (Exception e) {
      System.out.println("Error with msisdn or request already processed-->" + tempmssidn);
    } finally {
      if (in != null)
        try {
          in.close();
        } catch (IOException e) {
          System.out.println(e.getMessage());
        }  
    } 
  }
}
