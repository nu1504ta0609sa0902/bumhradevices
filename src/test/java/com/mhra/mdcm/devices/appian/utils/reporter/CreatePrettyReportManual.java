//package com.mhra.mdcm.devices.appian.utils.reporter;
//
//import net.masterthought.cucumber.Configuration;
//import net.masterthought.cucumber.ReportBuilder;
//import net.masterthought.cucumber.sandwich.CucumberReportMonitor;
//
//import java.io.File;
//import java.util.ArrayList;
//import java.util.Calendar;
//import java.util.Date;
//import java.util.List;
//
///**
// * IF YOUR USING JENKINS THAN DON'T USE THIS
// * <p>
// * USE JENKINS PLUGINS FROM MASTERTHOUGHT
// * <p>
// * This will be automatically run once before starting the tests
// * This will monitor the specified folders and generate tests automatically
// * <p>
// * assumptions:
// * input folder is the target folder
// *
// * @author Noor
// */
//public class CreatePrettyReportManual {
//
//    public static void main(String[] args) {
//        CreatePrettyReportManual cpr = new CreatePrettyReportManual();
//        //cpr.monitorFolder("PrettyReport", true);
//        //cpr.createReport("PrettyReport", true);
//        cpr.createReport("PrettyReport2", false);
//    }
//
//    private String formatName(String fname) {
//        Calendar instance = Calendar.getInstance();
//        int dom = instance.get(Calendar.DAY_OF_MONTH);
//        String ds = stringify(dom);
//        fname = fname.replaceFirst(ds, ds + "_");
//        return fname;
//    }
//
//    private String stringify(int dom) {
//        String x = "" + dom;
//        if (dom < 10) {
//            x = "0" + dom;
//        }
//        return x;
//    }
//
//
//    public void createReport(String outFolderName, boolean userBackUpFolder) {
//        String target = "target";
//        if (userBackUpFolder) {
//            target = "bu";
//        }
//
//        if (outFolderName == null) {
//            outFolderName = "PrettyReport";
//        }
//        //Where to put pretty reports
//        String res = new File("").getAbsolutePath();
//
//        String[] aaa = new String[4];
//        aaa[0] = "-f";
//        aaa[1] = res + File.separatorChar + target;
//        aaa[2] = "-o";
//        String folderName = new Date().toString().replace(":", "").substring(0, 16).replace(" ", "");
//        folderName = formatName(folderName);
//        String outFile = res + File.separatorChar + target + File.separatorChar + outFolderName + File.separatorChar + folderName;
//
//        //File to monitor
//        System.out.println("Monitoring folder : " + outFile);
//        //Create folder
//        File f = new File(outFile);
//        f.mkdirs();
//        aaa[3] = outFile;
//        try {
//            String jenkinsBasePath = "";
//            String buildNumber = "1";
//            String projectName = "PrettyReport";
//            boolean runWithJenkins = false;
//            boolean parallelTesting = false;
//            String jsonFileFolder = res + File.separatorChar + target;// + File.separatorChar + outFolderName;
//            Configuration configuration = new Configuration(new File(outFile), "MHRA_MDCM_DEVICES");
//            // optional configuration
//            configuration.setParallelTesting(parallelTesting);
//            //configuration.setJenkinsBasePath(jenkinsBasePath);
//            configuration.setRunWithJenkins(runWithJenkins);
//            configuration.setBuildNumber(buildNumber);
//
//            //Read and generate json file report
//            List<String> jsonFiles = new ArrayList<>();
//            File jf = new File(jsonFileFolder);
//            File[] files = jf.listFiles();
//            for(File fi: files){
//                String name = fi.getName();
//                if(name!=null && name.contains(".json")){
//                    System.out.println("File name : " + name);
//                    jsonFiles.add(jsonFileFolder + File.separatorChar + name);
//                }
//            }
//
//            System.out.println("Generating Report New");
//            ReportBuilder rb = new ReportBuilder(jsonFiles, configuration);
//            rb.generateReports();
//
//            //CucumberReportMonitor.main(aaa);
//        } catch (Exception e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//    }
//
//}
