package com.mhra.mdcm.devices.appian.steps.common;

import com.mhra.mdcm.devices.appian.pageobjects.LoginPage;
import com.mhra.mdcm.devices.appian.pageobjects.MainNavigationBar;
import com.mhra.mdcm.devices.appian.pageobjects.business.*;
import com.mhra.mdcm.devices.appian.pageobjects.business.sections.TaskSection;
import com.mhra.mdcm.devices.appian.pageobjects.business.sections.records.*;
import com.mhra.mdcm.devices.appian.pageobjects.external.ExternalHomePage;
import com.mhra.mdcm.devices.appian.pageobjects.external.MyAccountPage;
import com.mhra.mdcm.devices.appian.pageobjects.external._CreateManufacturerTestsData;
import com.mhra.mdcm.devices.appian.pageobjects.external.device.AddDevices;
import com.mhra.mdcm.devices.appian.pageobjects.external.device.DeviceDetails;
import com.mhra.mdcm.devices.appian.pageobjects.external.device.ProductDetails;
import com.mhra.mdcm.devices.appian.pageobjects.external.manufacturer.ManufacturerEditDetails;
import com.mhra.mdcm.devices.appian.pageobjects.external.manufacturer.ManufacturerList;
import com.mhra.mdcm.devices.appian.pageobjects.external.manufacturer.ManufacturerViewDetails;
import com.mhra.mdcm.devices.appian.pageobjects.external.myaccount.ContactPersonDetails;
import com.mhra.mdcm.devices.appian.pageobjects.external.myaccount.OrganisationDetails;
import com.mhra.mdcm.devices.appian.session.ScenarioSession;
import com.mhra.mdcm.devices.appian.utils.selenium.page.PageUtils;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;


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

    /**
     * PageObjects: Main _Page objects, These page objects should create section objects
     */
    @Autowired
    public MainNavigationBar mainNavigationBar;
    @Autowired
    public LoginPage loginPage;
    @Autowired
    public NewsTabPage newsPage;
    @Autowired
    public RecordsTabPage recordsPage;
    @Autowired
    public ActionsTabPage actionsTabPage;
    @Autowired
    public TasksTabPage tasksPage;
    @Autowired
    public ReportsTabPage reportsPage;

    //SECTIONS
    @Autowired
    public Accounts accounts;
    @Autowired
    public RegisteredDevices registeredDevices;
    @Autowired
    public Organisations organisations;
    @Autowired
    public GMDNDevices devicesGMDN;
    @Autowired
    public RegisteredProducts registeredProducts;
    @Autowired
    public _CreateAccountTestsData createTestsData;
    @Autowired
    public TaskSection taskSection;
    @Autowired
    public EditAccount editAccounts;
    @Autowired
    public ViewAccount viewAccount;
    @Autowired
    public BusinessManufacturerDetails businessManufacturerDetails;
    @Autowired
    public BusinessProductDetails businessProductDetails;
    @Autowired
    public BusinessDeviceDetails businessDevicesDetails;


    /**
     * MAIN PO FOR : AUTHORISEDREP AND MANUFACTURERS
     */
    @Autowired
    public ExternalHomePage externalHomePage;
    @Autowired
    public MyAccountPage myAccountPage;

    //SECTIONS
    @Autowired
    public ContactPersonDetails amendPersonDetails;
    @Autowired
    public ManufacturerViewDetails manufacturerDetails;
    @Autowired
    public _CreateManufacturerTestsData createNewManufacturer;
    @Autowired
    public AddDevices addDevices;
    @Autowired
    public OrganisationDetails amendOrganisationDetails;
    @Autowired
    public ManufacturerList manufacturerList;
    @Autowired
    public ManufacturerEditDetails editManufacturer;
    @Autowired
    public ProductDetails productDetail;
    @Autowired
    public DeviceDetails deviceDetails;



    public static boolean oneDriverOnly = true;
    public CommonSteps() {
        String selectedProfile = System.getProperty("spring.profiles.active");
        // for prod we need to replace url with www
        if (selectedProfile.equals("mhra")) {
            baseUrl = baseUrl.replace("mhra.", "www.");
        }

        addShutdownHooks();
    }

    private void addShutdownHooks() {
        if(driver==null){
            if(!onlyOnce){
                onlyOnce=true;
                quit();
            }
        }else{
            //WaitUtils.setImplicitWaits(driver);
            PageUtils.setBrowserZoom(driver, currentBrowser);
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

                        //This looks slightly better than other cucumber report, less intrusive and better memory managements
                        //CreatePrettyReport cpr = new CreatePrettyReport();
                        //cpr.monitorFolder(PRETTY_REPORT, false);
                        String generateReport = System.getProperty("generate.report");
                        if(generateReport != null && ( generateReport.trim().equals("true") || generateReport.trim().equals("yes"))){
                            log.info("Generating report may take upto 20 seconds");
                            sleep(30000);
                        }else {
                            //No need to wait for report to be generated
                            log.info("Report generation is disabled, to enable set -Dgenerate.report=true");
                            sleep(2000);
                        }

                        driver.quit();
                        log.info("All browsers closed after tests");
                    }
                } catch (Exception e) {

                }
            }
        });

    }


}
