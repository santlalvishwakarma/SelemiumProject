package com.solution.VodacomSolution;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;

public enum SolutionUtil {
  INSTANCE;
  
  public WebClient getWebClient(String msisdnKey, String ipKey, String msisdn, String ip) {
    WebClient webClient = getWebClient(msisdnKey, ipKey, msisdn, ip, BrowserVersion.FIREFOX_60);
    return webClient;
  }
  
  public WebClient getWebClient(String msisdnKey, String ipKey, String msisdn, String ip, BrowserVersion browserVersion) {
    WebClient webClient = null;
    webClient = new WebClient(browserVersion);
    webClient.getOptions().setUseInsecureSSL(true);
    webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
    webClient.getOptions().setJavaScriptEnabled(true);
    webClient.getOptions().setRedirectEnabled(true);
    webClient.getOptions().setCssEnabled(true);
    webClient.getOptions().setThrowExceptionOnScriptError(false);
    webClient.getOptions().setTimeout(20000);
    webClient.setJavaScriptTimeout(25000L);
    webClient.waitForBackgroundJavaScript(10000L);
    webClient.addRequestHeader(msisdnKey, msisdn);
    webClient.addRequestHeader(ipKey, ip);
    webClient.addRequestHeader("User-Agent", 
        "Mozilla/5.0 (Linux; Android 7.0; PLUS Build/NRD90M) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/61.0.3163.98 Mobile Safari/537.36");
    webClient.addRequestHeader("Access-Control-Allow-Credentials", "true");
    webClient.addRequestHeader("Access-Control-Allow-Headers", "Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With");
    webClient.addRequestHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE, PUT");
    webClient.addRequestHeader("Access-Control-Max-Age", "3600");
    webClient.addRequestHeader("Cache-Control", "no-cache, must-revalidate, private");
    webClient.addRequestHeader("X-FRAME-OPTIONS", "SAMEORIGIN");
    webClient.getOptions().setAppletEnabled(false);
    webClient.getOptions().setUseInsecureSSL(true);
    webClient.getCache().setMaxSize(0);
    webClient.getOptions().setUseInsecureSSL(true);
    return webClient;
  }
}
