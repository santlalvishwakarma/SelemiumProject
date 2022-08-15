package com.solution.Tanzania;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.HttpMethod;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebRequest;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;

public class CongoSolution {
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
      webClient.addRequestHeader("X-Forwarded-For", ip);
      webClient.addRequestHeader("msisdn", msisdn);
      webClient.addRequestHeader("User-Agent", 
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
      URL url2 = new URL(cmpUrl);
      WebRequest requestSettings2 = new WebRequest(url2, HttpMethod.GET);
      requestSettings2.setAdditionalHeader("X-Forwarded-For", ip);
      requestSettings2.setAdditionalHeader("msisdn", msisdn);
      System.out.println("Request Params -->\n" + requestSettings2.getRequestParameters() + "-->\nURL-->" + 
          requestSettings2.getUrl());
      HtmlPage domDocType = (HtmlPage)webClient.getPage(requestSettings2);
      synchronized (domDocType) {
        webClient.waitForBackgroundJavaScript(25000L);
      } 
      System.out.println("URL--> " + domDocType.getBaseURI());
      System.out.println("IP & Mobile Number -->" + ip + " & " + msisdn);
      HtmlAnchor anchor = (HtmlAnchor)domDocType.getElementById("lnkSubscribe_fre");
      if (anchor != null) {
        HtmlPage clickPage = null;
        clickPage = (HtmlPage)anchor.click();
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
      CongoSolution congoSolution = new CongoSolution();
      String filename = args[0];
      String cmpUrl = args[1];
      in = new BufferedReader(new FileReader(filename));
      int j = 0;
      String s;
      while ((s = in.readLine()) != null) {
        j++;
        System.out.println("\n Process Starts...\n");
        System.out.println("Details--> Product URL -->" + cmpUrl + "--> Data Details -->" + filename);
        String[] var = s.split("\\|");
        String strMobileNumber = var[0];
        String ipAddress = var[1];
        congoSolution.getProcessSolution(strMobileNumber, ipAddress, cmpUrl);
        System.out.println("Total Number Processed-->" + j + "--> Last MSISDN-->" + var[0]);
        System.out.println("\n Process Ends...\n");
      } 
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
