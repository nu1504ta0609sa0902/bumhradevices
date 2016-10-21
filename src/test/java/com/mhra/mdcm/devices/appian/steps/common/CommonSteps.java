package com.mhra.mdcm.devices.appian.steps.common;

import com.mhra.mdcm.devices.appian.pageobjects.LoginPage;
import com.mhra.mdcm.devices.appian.pageobjects.MainNavigationBar;
import com.mhra.mdcm.devices.appian.pageobjects.business.*;
import com.mhra.mdcm.devices.appian.session.ScenarioSession;
import com.mhra.mdcm.devices.appian.utils.others.NetworkUtils;
import com.mhra.mdcm.devices.appian.utils.selenium.page.PageUtils;
import com.mhra.mdcm.devices.appian.utils.selenium.page.WaitUtils;

import net.masterthought.cucumber.ReportBuilder;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;


@ContextConfiguration(locations = {"/cucumber.mhra.devices.xml"})
public class CommonSteps {

    static boolean onlyOnce;

    @Value("${base.url}")
    public String baseUrl;
    @Value("${current.browser}")
    public String currentBrowser;

    @Autowired
    public WebDriver driver;

    @Autowired
    public ScenarioSession scenarioSession;

    public static final Logger log = LoggerFactory.getLogger(CommonSteps.class);
    public static final String PRETTY_REPORT = "PrettyReport";
    public static Map<String, Map> mapOfExcelDataAsMap;

    /**
     * PageObjects: Main _Page objects, These page objects should create section objects
     */
    @Autowired
    public MainNavigationBar mainNavigationBar;
    @Autowired
    public LoginPage loginPage;
    @Autowired
    public NewsPage newsPage;
    @Autowired
    public RecordsPage recordsPage;
    @Autowired
    public ActionsPage actionsPage;
    @Autowired
    public TasksPage tasksPage;
    @Autowired
    public ReportsPage reportsPage;

    //SECTIONS
    //@Autowired
    //public NotificationDetails notificationDetails;

    public static boolean oneDriverOnly = true;
    public CommonSteps() {
        String selectedProfile = System.getProperty("spring.profiles.active");
        // for prod we need to replace url with www
        if (selectedProfile.equals("mhra")) {
            baseUrl = baseUrl.replace("mhra.", "www.");
        }

        //Make sure network connection is available
        String testUrl = NetworkUtils.getTestUrl(baseUrl);
        boolean connected = NetworkUtils.verifyConnectedToNetwork(testUrl, 10);
        if(!connected){
            NetworkUtils.shutdownIfRequired(connected, testUrl, log);
        }else {
            //loadMapOfExcelData();
            addShutdownHooks();
        }
    }

//    private void loadMapOfExcelData() {
//        if(mapOfExcelDataAsMap == null && driver == null){
//            //Load excel test data
//            ExcelUtils excelUtils = new ExcelUtils();
//            //mapOfExcelDataAsMap = excelUtils.getAllData("configs/data/xmlTestData1.xlsx");
//            mapOfExcelDataAsMap = excelUtils.getAllDataAsMap("configs/data/xmlTestData2.xlsx");
//            log.info("TEST DATA LOADED FROM : configs/data/xmlTestData2.xlsx");
//        }
//    }

    private void addShutdownHooks() {
        if(driver==null){
            if(!onlyOnce){
                onlyOnce=true;
                quit();
            }
        }else{
            //WaitUtils.setImplicitWaits(driver);
            //PageUtils.setBrowserZoom(driver, currentBrowser);
        }
    }


    /**
     * Shutdown all the browsers after its done
     */
    public void quit(){
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                try {
                    if (driver != null) {
                        log.info("If -Dgenerate.report=true than it generate the pretty report");
                        //createReport("PrettyReport", true);
                        sleep(20000);
                        driver.quit();
                        //IE driver doesn't quit, so forced to try this
                        //Runtime.getRuntime().exec("taskkill /F /IM IEDriverServer.exe");
                        //Runtime.getRuntime().exec("taskkill /F /IM iexplore.exe");
                        log.info("All browsers closed after tests");
                    }
                } catch (Exception e) {

                }
            }
        });

    }

//    private void createReport(String outFolderName, boolean cheat) {
//        String target = "target";
//        if(cheat){
//            target = "bu";
//        }
//
//        if(outFolderName == null){
//            outFolderName = "PrettyReport";
//        }
//        //Where to put pretty reports
//        String res = new File("").getAbsolutePath();
//
//        String [] aaa = new String[4];
//        aaa[0] = "-f";
//        aaa[1] = res + File.separatorChar + target ;
//        aaa[2] = "-o";
//        String fname = new Date().toString().replace(":", "").substring(0,16).replace(" ", "");
//        //fname = formatName(fname);
//        String outFile = res + File.separatorChar + target + File.separatorChar + outFolderName + File.separatorChar + fname;
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
//            String rod = res + File.separatorChar + target;// + File.separatorChar + outFolderName;
//            Configuration configuration = new Configuration(new File(rod), "MHRA_MDCM_DEVICES");
//            // optional configuration
//            configuration.setParallelTesting(parallelTesting);
//            //configuration.setJenkinsBasePath(jenkinsBasePath);
//            configuration.setRunWithJenkins(runWithJenkins);
//            configuration.setBuildNumber(buildNumber);
//
//            List<String> jsonFiles = new ArrayList<>();
//            jsonFiles.add(rod + File.separatorChar + "anotherreport.json");
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


}
