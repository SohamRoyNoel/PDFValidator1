package com.PDF_Validator.main.validator;

import java.io.File;

import com.PDF_Validator.main.resourcePath.ResourcePaths;

public class ValidationUtils {

	public static void Validation(){

		 

        File[] sourceElements = getListOfFiles(ResourcePaths.sourcePath);

        File[] targetElements = getListOfFiles(ResourcePaths.targetPath);

       

        int sourceElementCount = sourceElements.length;

        int targetElementCount = targetElements.length;

       

        int getGreaterFileCounter = Math.max(sourceElementCount, targetElementCount);

       

        // Get file names to read

        for(int i=0; i<getGreaterFileCounter; i++){

//                    String filename = sourceElements[i].getName();

                        if (sourceElements[i].getName().equals(targetElements[i].getName())) {

                                        String filename = sourceElements[i].getName();

                                       

                                        String createSourceFileName = ResourcePaths.sourcePath+"\\"+ sourceElements[i].getName();

                                        String craeteTargetFileName = ResourcePaths.targetPath+"\\"+targetElements[i].getName();

                                                                                                       

                                        PdfComparison.PDFComparater(createSourceFileName, craeteTargetFileName, filename);

                        }

                       

        }



}



// Returns the list of Files

public static File[] getListOfFiles(String ResourcePath) {

        File[] directoryNames = new File(ResourcePath).listFiles(File::isFile);

        return directoryNames;

}
	
}
