package com.PDF_Validator.main.validator;

import java.io.File;

import java.util.ArrayList;

import java.util.List;

 

import org.apache.pdfbox.pdmodel.PDDocument;

import org.apache.pdfbox.util.PDFTextStripper;

public class PdfComparison {

	public static List<String> createList = new ArrayList<>();



	public static void PDFComparater(String srcFile, String tgtFile, String Filename) {

		try {



			// Get PDFs

			File Src_pdfFile = new File(srcFile);

			File tgt_pdfFile1 = new File(tgtFile);



			// Load PDFs

			PDDocument pdDocument_SRC = PDDocument.load(Src_pdfFile);

			PDDocument pdDocument_TGT = PDDocument.load(tgt_pdfFile1);



			// Get the number of pages

			List allPages_SRC = pdDocument_SRC.getDocumentCatalog().getAllPages();

			List allPages_TGT = pdDocument_TGT.getDocumentCatalog().getAllPages();



			int i =1; int j =1;



			// Until the same amount of lines

			for (i = 1; i <= Math.min(allPages_SRC.size(), allPages_TGT.size()); i++) {

				PDFTextStripper stripper = new PDFTextStripper();

				stripper.setStartPage(i);

				stripper.setEndPage(i);

				String text_SRC = stripper.getText(pdDocument_SRC).replaceAll("javaWhitespace", " ");

				String text_TGT = stripper.getText(pdDocument_TGT).replaceAll("javaWhitespace", " ");



				String[] lines_SRC = text_SRC.split("\n");

				String[] lines_TGT = text_TGT.split("\n");

				int passCreator=1;

				String createPassString;

				String createFailString;



				for (j = 0; j < Math.min(lines_SRC.length, lines_TGT.length) ; j++) {

					String src =  lines_SRC[j].replace("\n", "*");

					String tgt = lines_TGT[j].replace("\n", "*");

					if (src.equals(tgt)) {

						passCreator++;

					} else {

						createFailString = Filename+"*Source - "+src+"*Target - "+tgt+"*"+"FAIL"+"*"+"Mismatch in line number - " + (j+1);

						createList.add(createFailString);

					}

				}



				if (passCreator > 1) {

					createPassString = Filename+"*Source - "+"Source File is same as Target"+"*Target - "+"Target file is same as Source"+"*"+"PASS"+"*"+"Matched "+ passCreator + " lines";

					createList.add(createPassString);

				}

			}





			//                                 System.out.println("ODD CASE");

			// When SOURCE has more lines

			if(allPages_SRC.size() > allPages_TGT.size()) {

				for (int x = i; x <= allPages_SRC.size(); x++) {

					PDFTextStripper stripper = new PDFTextStripper();

					stripper.setStartPage(x);

					stripper.setEndPage(x);

					String text_SRC = stripper.getText(pdDocument_SRC).replaceAll("javaWhitespace", " ");



					String[] lines_SRC = text_SRC.split("\n");



					for (int y = 0; y < lines_SRC.length; y++) {

						String src = lines_SRC[y];

						String createIssueString = "Source - " + src + " Target -" + " No value on TARGET file " + "This line exists only on SOURCE File";

						String createFailWhenNoTARGET = Filename+"*Source - " + src + "*Target - "+ "No line is found on Target" + "*FAIL*"+createIssueString;

						createList.add(createFailWhenNoTARGET);

					}

				}

			} else {

				// When TARGET has more lines

				for (int x = i; x <= allPages_TGT.size(); x++) {

					PDFTextStripper stripper = new PDFTextStripper();

					stripper.setStartPage(x);

					stripper.setEndPage(x);



					String text_TGT = stripper.getText(pdDocument_TGT).replaceAll("visiblespace", " ");



					String[] lines_TGT = text_TGT.split("\n");

					System.out.println("Target is graters : " +j);

					System.out.println("Target is graters : " + lines_TGT.length);

					for ( int y = 0; y < lines_TGT.length; y++) {

						String tgt = lines_TGT[y];

						String createIssueString = "Source - " + "No value on SOURCE file " + "Target - " + tgt + " This line exists only on TARGET File";

						String createFailWhenNoSOURCE = Filename+"*Source - " + "No line is found on Source" + "*Target - "+ tgt  + "*FAIL*"+createIssueString;

						createList.add(createFailWhenNoSOURCE);

					}

				}

			}

			pdDocument_SRC.close();

			pdDocument_TGT.close();





		} catch(Exception e){

			System.out.print(e);

		}



	}

}
