package com.solution.perusalenium;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ProcessPeruSaleniumSolution {

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		String tempmssidn = "";
		BufferedReader in = null;
		ExecutorService executorService = Executors.newFixedThreadPool(5);
		try {
			
			PrintStream out = new PrintStream(
			        new FileOutputStream("output.txt", false), true);
			System.setOut(out);
			
			String filename = args[0];
			String cmpUrl = args[1];
			String chromeDriverPath = args[2];
			in = new BufferedReader(new FileReader(filename));
			int j = 0;
			String s;
			int userAgentCounter = 0;
			while ((s = in.readLine()) != null) {
				j++;
				userAgentCounter++;
				
				System.out.println("\n Process Starts...\n");
				System.out.println("Details--> Product URL -->" + cmpUrl + "--> Data Details -->" + filename);
				String[] var = s.split("\\|");
				String strMobileNumber = var[0];
				String ipAddress = var[1];
				//strMobileNumber = strMobileNumber.substring(2, strMobileNumber.lastIndexOf(""));
				PeruSaleniumSolution taSolution = new PeruSaleniumSolution(strMobileNumber, cmpUrl, ipAddress, chromeDriverPath, userAgentCounter);
				executorService.execute(taSolution);
				if(userAgentCounter == 20) {
					userAgentCounter = 0;
				}
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
