package com.solution.Tanzania;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URL;

import com.gargoylesoftware.htmlunit.HttpMethod;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebRequest;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class TigoSolution {
  public static int k = 0;
  
  public String getProcessSolution(String msisdn, String ip, String cmpUrl) {
    WebClient webClient = null;
    try {
    //  webClient = new WebClient(BrowserVersion.FIREFOX_52);
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
          "Mozilla/5.0 (Linux; Android 7.0; PLUS Build/NRD90M) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/61.0.3163.98 Mobile Safari/537.36");
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
      System.out.println("Request Params -->\n" + requestSettings2.getRequestParameters() + "-->\nURL-->" + 
          requestSettings2.getUrl());
      HtmlPage page22 = (HtmlPage)webClient.getPage(requestSettings2);
      synchronized (page22) {
        webClient.waitForBackgroundJavaScript(25000L);
      } 
      System.out.println("URL--> " + page22.getBaseURI());
      System.out.println("IP & Mobile Number -->" + ip + " & " + msisdn);
      HtmlAnchor anchor = (HtmlAnchor)page22.getElementById("lnkSubscribe");
      if (anchor != null) {
        HtmlPage clickPage = null;
        if (clickPage != null) {
          System.out.println("------------ Button click DONE --------------");
        } else {
          System.out.println("----------------- Button Click Not Done ---------------");
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
      TigoSolution saSolution = new TigoSolution();
      saSolution.getProcessSolution("233269450440", "41.215.160.13", "http://alpasrame.offerstrack.net/index.php?offer_id=634&aff_id=5");
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
