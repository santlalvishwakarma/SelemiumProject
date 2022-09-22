package com.solution.footballzone;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ProcessFootballZoneSolution {
	public static void main(String[] args) throws InterruptedException, ExecutionException {
		String tempmssidn = "";
		BufferedReader in = null;
		ExecutorService executorService = Executors.newFixedThreadPool(10);
		try {
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
				FootballZoneSolution taSolution = new FootballZoneSolution(strMobileNumber, ipAddress, cmpUrl);
				executorService.execute(taSolution);
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
