package com.solution.honduras;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.HttpMethod;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebRequest;
import com.gargoylesoftware.htmlunit.WebResponse;
import com.gargoylesoftware.htmlunit.html.HtmlButton;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.util.NameValuePair;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class OneClickHnV2Solution {
  public static int k = 0;
  
  public String getProcessSolution(String msisdn, String ip, String cmpUrl) {
    WebClient webClient = null;
    try {
      webClient = new WebClient(BrowserVersion.FIREFOX_52);
      webClient.getOptions().setUseInsecureSSL(true);
      webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
      webClient.getOptions().setJavaScriptEnabled(true);
      webClient.getOptions().setRedirectEnabled(true);
      webClient.getOptions().setCssEnabled(true);
      webClient.getOptions().setThrowExceptionOnScriptError(false);
      webClient.getOptions().setTimeout(20000);
      webClient.setJavaScriptTimeout(8000L);
      webClient.addRequestHeader("X-FORWARDED-FOR", ip);
      webClient.addRequestHeader("MSISDN", msisdn);
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
      requestSettings2.setAdditionalHeader("MSISDN", msisdn);
      System.out.println("Request Params -->\n" + requestSettings2.getRequestParameters() + "-->\nURL-->" + 
          requestSettings2.getUrl());
      HtmlPage page22 = (HtmlPage)webClient.getPage(requestSettings2);
      Thread.sleep(8000L);
      System.out.println("page-->\n" + page22.asXml());
      String relocateString = page22.asXml().toString().split("\\(")[1].split("\\);")[0];
      String toPost = page22.asXml().toString().split("\\(")[1].split("\\);")[0];
      String[] allstr2 = page22.asXml().toString().split("\\(")[1].split("\\);")[0].split(",");
      for (int i = 0; i < allstr2.length; i++)
        System.out.println("Details of Str -->" + allstr2[i]); 
      String urltopost = "http://wap.pconnection.net:8081/wsedge/conexion_tigo_wapgeneral.php";
      String address = allstr2[1].split(":\"")[1].split("\"")[0];
      String groupCode = allstr2[2].split(":\"")[1].split("\"")[0];
      String urlredirect = allstr2[3].split(":\"")[1].split("\"")[0];
      String serviceCode = allstr2[4].split(":\"")[1].split("\"")[0];
      List<NameValuePair> params3 = new ArrayList<NameValuePair>();
      params3.add(new NameValuePair("address", address));
      params3.add(new NameValuePair("groupCode", groupCode));
      params3.add(new NameValuePair("serviceCode", serviceCode));
      params3.add(new NameValuePair("urlredirect", urlredirect));
      URL urltofire = new URL(urltopost);
      WebRequest requestSettings22 = new WebRequest(urltofire, HttpMethod.POST);
      requestSettings22.setAdditionalHeader("X-FORWARDED-FOR", ip);
      requestSettings22.setAdditionalHeader("MSISDN", msisdn);
      requestSettings22.setRequestParameters(params3);
      System.out.println("Request Params -->\n" + requestSettings22.getRequestParameters() + "-->\nURL-->" + 
          requestSettings22.getUrl());
      page22 = (HtmlPage)webClient.getPage(requestSettings22);
      WebResponse response22 = page22.getWebResponse();
      System.out.println("Page opened-->\n" + Jsoup.parse(response22.getContentAsString()));
      int j = 0;
      HtmlPage page2 = page22;
      Document doc = Jsoup.parse(page2.asXml());
      Elements e = doc.getAllElements();
      Element testForm = e.forms().get(0);
      Elements inputElements = testForm.getElementsByTag("input");
      List<NameValuePair> params = new ArrayList<NameValuePair>();
      for (Element inputElement : inputElements) {
        String key = inputElement.attr("name");
        String value = inputElement.attr("value");
        j++;
        params.add(new NameValuePair(key, value));
      } 
      HtmlForm formsubmit = page2.getForms().get(0);
      HtmlButton hi = formsubmit.getButtonByName("action:accept");
      System.out.println("details of button-->\n" + hi.asXml());
      Document d = Jsoup.parse(page2.asXml());
      Element formElement = (Element)doc.getElementsByTag("form").get(0);
      Elements pageelements = formElement.getElementsByTag("input");
      List<NameValuePair> params2 = new ArrayList<NameValuePair>();
      for (Element inputElement : pageelements) {
        String key = inputElement.attr("name");
        String value = inputElement.attr("value");
        j++;
        params2.add(new NameValuePair(key, value));
        formsubmit.getInputByName(key);
        formsubmit.setAttribute(key, value);
      } 
      HtmlButton b = (HtmlButton)page2.getElementByName("action:accept");
      HtmlPage p = (HtmlPage)b.click();
      Thread.sleep(1000L);
      formsubmit.getActionAttribute();
      System.out.println("Action URL-->" + formsubmit.getBaseURI());
      System.out.println("Final Actino URL-->" + formsubmit.getActionAttribute());
      System.out.println("Final Response-->\n" + p.asXml());
      System.out.println(
          "Process Completed with URI as -->" + p.getBaseURI() + "--> Total Submitted CG Request-->" + k);
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
    try {
      OneClickHnV2Solution oneClickObjSolution = new OneClickHnV2Solution();
      oneClickObjSolution.getProcessSolution("50498782448", "186.2.136.21", "https://opicle.g2afse.com/click?pid=29&offer_id=710");
    } catch (Exception e) {
      System.out.println("Error with msisdn or request already processed-->" + tempmssidn);
    } 
  }
}
