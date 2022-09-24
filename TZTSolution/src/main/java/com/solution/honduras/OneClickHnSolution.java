package com.solution.honduras;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import com.gargoylesoftware.htmlunit.AjaxController;
import com.gargoylesoftware.htmlunit.ElementNotFoundException;
import com.gargoylesoftware.htmlunit.HttpMethod;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebRequest;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import com.linkedin.urls.Url;
import com.linkedin.urls.detection.UrlDetector;
import com.linkedin.urls.detection.UrlDetectorOptions;

public class OneClickHnSolution {
  public static int k = 0;
  
  public String getProcessSolution(String msisdn, String ip, String cmpUrl) {
    WebClient webClient = null;
    try {
      //webClient = new WebClient(BrowserVersion.FIREFOX_52);
      webClient.getOptions().setUseInsecureSSL(true);
      webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
      webClient.getOptions().setJavaScriptEnabled(true);
      webClient.getOptions().setRedirectEnabled(true);
      webClient.getOptions().setCssEnabled(true);
      webClient.getOptions().setThrowExceptionOnScriptError(false);
      webClient.getOptions().setTimeout(20000);
      webClient.setJavaScriptTimeout(8000L);
      webClient.addRequestHeader("X-FORWARDED-FOR", ip);
      webClient.addRequestHeader("msisdn", msisdn);
      webClient.addRequestHeader("USER-AGENT", 
          "Mozilla/5.0 (iPhone; CPU iPhone OS 9_2 like Mac OS X) AppleWebKit/601.1 (KHTML, like Gecko) CriOS/47.0.2526.70 Mobile/13C71 Safari/601.1.46");
      webClient.waitForBackgroundJavaScript(10000L);
      webClient.addRequestHeader("Access-Control-Allow-Credentials", "true");
      webClient.addRequestHeader("Access-Control-Allow-Headers", 
          "Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With");
      webClient.addRequestHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE, PUT");
      webClient.addRequestHeader("Access-Control-Max-Age", "3600");
      webClient.addRequestHeader("Cache-Control", "no-cache, must-revalidate, private");
      webClient.addRequestHeader("X-FRAME-OPTIONS", "SAMEORIGIN");
      webClient.addRequestHeader("Upgrade-Insecure-Requests", "1");
      webClient.addRequestHeader("Content-Type", "text/html; charset=utf-8");
      webClient.addRequestHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
      webClient.addRequestHeader("Purpose", "prefetch");
      webClient.addRequestHeader("Upgrade-Insecure-Requests", "1");
      webClient.getOptions().setAppletEnabled(true);
      webClient.getOptions().setUseInsecureSSL(true);
      webClient.getCache().setMaxSize(0);
      webClient.setAjaxController((AjaxController)new NicelyResynchronizingAjaxController());
      URL url2 = new URL(cmpUrl);
      WebRequest requestSettings2 = new WebRequest(url2, HttpMethod.GET);
      requestSettings2.setAdditionalHeader("X-FORWARDED-FOR", ip);
      requestSettings2.setAdditionalHeader("msisdn", msisdn);
      System.out.println("Request Params -->\n" + requestSettings2.getRequestParameters() + "-->\nURL-->" + 
          requestSettings2.getUrl());
      HtmlPage page22 = (HtmlPage)webClient.getPage(requestSettings2);
      synchronized (page22) {
        webClient.waitForBackgroundJavaScript(3000L);
      } 
      System.out.println("URL--> " + page22.getBaseURI());
      System.out.println("IP & Mobile Number -->" + ip + " & " + msisdn);
      Page click = null;
      HtmlSubmitInput submitButton2 = (HtmlSubmitInput)page22.getElementByName("action:accept");
      if (submitButton2 != null)
        click = submitButton2.click(); 
      if (click != null) {
        System.out.println("------------ DONE --------------");
      } else {
        System.out.println("-----------------Not done yet---------------");
      } 
    } catch (Exception e) {
      e.printStackTrace();
      System.out.println("Exception occured on MSISDN-->" + msisdn + "--> Error Msg-->" + e.getMessage());
    } finally {
      webClient.close();
    } 
    return null;
  }
  
  private HtmlPage crawlUrlFindElement(String msisdn, String ip, WebClient webClient, HtmlPage page22) throws MalformedURLException, IOException {
    String asXmlText = page22.asXml();
    UrlDetector parser = new UrlDetector(asXmlText, UrlDetectorOptions.Default);
    List<Url> found = parser.detect();
    URL cgUrl = null;
    for (Url url : found) {
      if (url != null) {
        String strUrl = url.toString();
        if (strUrl.contains("tusitio"))
          cgUrl = new URL(strUrl); 
      } 
    } 
    System.out.println("Next URL --> " + cgUrl.toString());
    WebRequest requestSettings3 = new WebRequest(cgUrl, HttpMethod.GET);
    requestSettings3.setAdditionalHeader("X-FORWARDED-FOR", ip);
    requestSettings3.setAdditionalHeader("msisdn", msisdn);
    HtmlPage cgPage = (HtmlPage)webClient.getPage(requestSettings3);
    try {
      if (page22.getElementByName("action:accept") == null) {
        cgPage = crawlUrlFindElement(msisdn, ip, webClient, page22);
      } else {
        return cgPage;
      } 
    } catch (ElementNotFoundException e) {
      cgPage = crawlUrlFindElement(msisdn, ip, webClient, page22);
      return cgPage;
    } 
    return null;
  }
  
  public static void main(String[] args) {
    String tempmssidn = "";
    BufferedReader in = null;
    try {
      OneClickHnSolution saSolution = new OneClickHnSolution();
      saSolution.getProcessSolution("50496731347", "186.2.138.70", "https://opicle.g2afse.com/click?pid=29&offer_id=710");
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
