package com.PDF_Validator.main;

import java.awt.Desktop;

import java.io.File;

import java.io.IOException;

import java.text.SimpleDateFormat;

import java.time.Duration;

import java.time.Instant;

import java.util.Calendar;

import java.util.HashSet;

import java.util.List;

import java.util.Set;

 

import javax.swing.JOptionPane;

 

import com.PDF_Validator.main.resourcePath.ResourcePaths;

import com.PDF_Validator.main.validator.PdfComparison;

import com.PDF_Validator.main.validator.ValidationUtils;

 

import com.aventstack.extentreports.ExtentReports;

import com.aventstack.extentreports.ExtentTest;

import com.aventstack.extentreports.Status;

import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

public class Main {

	public static void main(String[] args) {



		Instant start = Instant.now();

		ValidationUtils.Validation();



		List<String> sl = PdfComparison.createList;



		String timeStamp = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(Calendar.getInstance().getTime());

		try {

			String[] temp;

			String[] temps;

			String[] tempr;



			// Writer on LIST

			List<String> listOfCases = sl;



			ExtentHtmlReporter extentHtmlReporter = new ExtentHtmlReporter(ResourcePaths.resultPath+"\\"+timeStamp+"_ComparisonReport.html");



			ExtentReports extentReports = new ExtentReports();

			extentReports.attachReporter(extentHtmlReporter);



			extentReports.setSystemInfo("Host Name", "PDF Comparison Report");

			String username = System.getProperty("user.name");

			extentReports.setSystemInfo("User Name", username);

			extentHtmlReporter.config().setDocumentTitle("Comparison Report");

			extentHtmlReporter.config().setReportName("Comparison Status");

			// Name of the report

			extentHtmlReporter.config().setReportName("File Validation Report");



			Set<String> s = new HashSet<>();

			for(int i=0; i<listOfCases.size(); i++){

				String ss = listOfCases.get(i);

				tempr = ss.split("\\*");

				s.add(tempr[0]);

			}



			int noOfParent = s.size(); int counter = 1;



			for (String str : s) {

				if (counter <= noOfParent) {

					temps = str.split("\\*");

					ExtentTest extentTest = extentReports.createTest("File Name - "+ temps[0]);

					for(int i=0; i < listOfCases.size(); i++){

						temp = listOfCases.get(i).split("\\*");

						if (str.contains(temp[0])) {

							ExtentTest childTest = extentTest.createNode(temps[0]);

							childTest.log(Status.INFO, "<B><I>TARGET - </B></I>"+ temp[1]);

							childTest.log(Status.INFO, "<B><I>SOURCE - </B></I>"+ temp[2]);

							if (temp[3].equalsIgnoreCase("FAIL")) {

								childTest.log(Status.FAIL, temp[4]);

							} else {

								childTest.log(Status.PASS, temp[4]);

							}

						}

					}

					extentReports.flush();

				}

				counter++;

			}

		}

		catch (Exception e) {

			e.printStackTrace();

		}



		try {

			File file = new File(ResourcePaths.resultPath+"\\"+timeStamp+"_ComparisonReport.html");

			Desktop.getDesktop().browse(file.toURI());

		} catch (IOException e) {

			e.printStackTrace();

		}

		Instant end = Instant.now();

		Duration timeElapsed = Duration.between(start, end);

		JOptionPane.showMessageDialog(null, "Total Execution Time : "+ timeElapsed.toMillis() +" MilliSeconds", "Action Time", JOptionPane.INFORMATION_MESSAGE);



	}

}
