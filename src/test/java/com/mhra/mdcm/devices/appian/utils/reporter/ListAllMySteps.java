package com.mhra.mdcm.devices.appian.utils.reporter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class ListAllMySteps {

	public static void main(String[] args) {
		String basePath = new File("").getAbsolutePath();

		// Get list of all files in project
		ArrayList<File> list = new ArrayList<File>();
		listf(basePath, list);

		// find only steps java classes : assumes files under package called
		// steps
		ArrayList<File> listOfStepsClass = getListOfStepsDefinitionClass(list);
		//System.out.println(listOfStepsClass);

		// List all the steps : Read content of each file and print out the
		// steps
		for (File steps : listOfStepsClass) {
			printStepsDefindedInTheClasses(steps);
		}

	}

	/**
	 * We are only interested in steps definitions class
	 * @param list
	 * @return
	 */
	private static ArrayList<File> getListOfStepsDefinitionClass(
			ArrayList<File> list) {
		// find only steps java classes : assumes files under package called
		// steps
		ArrayList<File> listOfStepsClass = new ArrayList<File>();
		// We are only interested in .steps directories
		for (File name : list) {
			String name2 = name.getAbsolutePath();
			// System.out.println(name2);
			if (name2.contains("steps") && name2.contains(".java") && !name2.contains("CommonSteps") && !name2.contains("SharedSteps"))
				listOfStepsClass.add(name);
		}
		return listOfStepsClass;
	}

	/**
	 * Print steps
	 * @param steps
	 */
	private static void printStepsDefindedInTheClasses(File steps) {
		System.out.println();
		System.out.println("STEPS DEFINED IN CLASS : " + steps.getName());
		System.out.println();
		BufferedReader br = null;
		try {

			br = new BufferedReader(new FileReader(steps));
			String line;
			while ((line = br.readLine()) != null) {
				if (line.contains("@")) {
					displayOnlySimpleTexts(line, true);
				}
			}
		} catch (Exception e) {

		} finally {
			try {
				br.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	/**
	 * Filter out @ and other cucumber-jvm related symbols
	 * 
	 * @param line
	 * @param b
	 *            if true than filter
	 */
	private static void displayOnlySimpleTexts(String line, boolean b) {
		if (b) {
			line = line.replace("@", "");
			
			//This over
			//int index = line.indexOf("^");
			//String xx = line.substring(0,index+1).replace("^", "");
			//line = xx + line.substring(index+1);
			line = line.replace("^", "");
			line = line.replace("[\\\"]", "[^\\\"]");
			line = line.replace("$", "");
			line = line.replace("(\"", " ");
			line = line.replace("\")", "");
			line = line.replace("\\\"([^\\\"]*)\\\"", "\"\"");
			line = line.replace("\\\"(.*?)\\\"", "\"\"");
			System.out.println(line);
		} else {
			System.out.println(line);
		}
		
	}

	public static void listf(String directoryName, ArrayList<File> files) {
		File directory = new File(directoryName);

		// get all the files from a directory
		File[] fList = directory.listFiles();
		for (File file : fList) {
			if (file.isFile()) {
				files.add(file);
			} else if (file.isDirectory()) {
				listf(file.getAbsolutePath(), files);
			}
		}
	}
}
