package com.mhra.mdcm.devices.appian.utils.reporter;

import net.masterthought.cucumber.sandwich.CucumberReportMonitor;

import java.io.File;
import java.util.Calendar;
import java.util.Date;

/**
 * 
 * IF YOUR USING JENKINS THAN DON'T USE THIS
 * 
 * USE JENKINS PLUGINS FROM MASTERTHOUGHT
 * 
 * This will be automatically run once before starting the tests
 * This will monitor the specified folders and generate tests automatically
 * 
 * assumptions:
 * 	input folder is the target folder

 * @author Noor
 *
 */
public class CreatePrettyReport {

	public static void main(String[] args) {
		CreatePrettyReport cpr = new CreatePrettyReport();
		cpr.monitorFolder("PrettyReport", false);
	}

	/**
	 * Monitors folder for changes and than generates pretty reports
	 */
	public void monitorFolder(String outFolderName, boolean cheat) {
		String target = "target";
		if(cheat){
			target = "bu";
		}

		if(outFolderName == null){
			outFolderName = "PrettyReport";
		}
		//Where to put pretty reports
		String res = new File("").getAbsolutePath();
	
		String [] aaa = new String[4];
		aaa[0] = "-f";
		aaa[1] = res + File.separatorChar + target ;
		aaa[2] = "-o";
		String fname = new Date().toString().replace(":", "").substring(0,16).replace(" ", "");
		fname = formatName(fname);
		String outFile = res + File.separatorChar + target + File.separatorChar + outFolderName + File.separatorChar + fname;

		//File to monitor
		System.out.println("Monitoring folder : " + outFile);
		//Create folder
		File f = new File(outFile);
		f.mkdirs();
		aaa[3] = outFile;
		try {
			CucumberReportMonitor.main(aaa);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private String formatName(String fname) {
		Calendar instance = Calendar.getInstance();
		int dom = instance.get(Calendar.DAY_OF_MONTH);
		String ds = stringify(dom);
		fname = fname.replaceFirst(ds, ds + "_");
		return fname;
	}

	private String stringify(int dom) {
		String x = "" + dom;
		if(dom < 10){
			x = "0" + dom;
		}
		return x;
	}


}
