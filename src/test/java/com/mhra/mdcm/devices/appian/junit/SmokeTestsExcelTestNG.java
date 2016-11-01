package com.mhra.mdcm.devices.appian.junit;

import com.mhra.mdcm.devices.appian.domains.junit.User;
import com.mhra.mdcm.devices.appian.pageobjects.LoginPage;
import com.mhra.mdcm.devices.appian.pageobjects.MainNavigationBar;
import com.mhra.mdcm.devices.appian.pageobjects.business.*;
import com.mhra.mdcm.devices.appian.utils.datadriven.ExcelDataSheet;
import com.mhra.mdcm.devices.appian.utils.datadriven.JUnitUtils;
import com.mhra.mdcm.devices.appian.utils.datadriven.TestNgExcelUtils;
import com.mhra.mdcm.devices.appian.utils.driver.BrowserConfig;
import com.mhra.mdcm.devices.appian.utils.others.NetworkUtils;
import org.hamcrest.Matchers;

import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Value;
import org.testng.Assert;
import org.testng.annotations.*;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

/**
 * Created by TPD_Auto on 01/11/2016.
 */
public class SmokeTestsExcelTestNG {

    @Value("${base.url}")
    public static String baseUrl;

    public static WebDriver driver;

    //Pages
    private NewsPage newsPage;
    private TasksPage tasksPage;
    private RecordsPage recordsPage;
    private ReportsPage reportsPage;
    private ActionsPage actionsPage;


    @DataProvider(name = "userLoginData")
    public Object[][] createData1() {
        TestNgExcelUtils eu = new TestNgExcelUtils();
        Object [][] o = eu.getListOfUsers("configs/data/excel/userlist.xls.xlsx", "Sheet1");
        return o;
    }


    @BeforeMethod
    public void setUp() throws Exception {
        if(driver == null){
            driver = new BrowserConfig().getDriver();
            baseUrl = NetworkUtils.getTestUrl(baseUrl);
        }
    }

    @AfterMethod
    public void tearDown() throws Exception {
        if(driver!=null){
            //driver.quit();
        }
    }

    @BeforeClass
    public static void setUpDriver(){
        if(driver == null){
            driver = new BrowserConfig().getDriver();
            baseUrl = NetworkUtils.getTestUrl(baseUrl);
        }
    }

    @AfterClass
    public static void clearBrowsers(){
        if(driver!=null){
            driver.quit();
        }
    }



    @Test(dataProvider = "userLoginData")
    public void testSearchUsers(User user) throws Exception {
        System.out.println(user);
    }

    @Test(dataProvider = "userLoginData")
    public void asAUserIShouldSeeErrorMessagesIfCredentialsAreIncorrect(User user) {
        String username = user.getUserName();
        String password = user.getPassword();
        System.out.println(username + ", " + password);

        LoginPage loginPage = new LoginPage(driver);
        loginPage = loginPage.loadPage(baseUrl);
        password = "IsIncorrectPassword";
        loginPage.loginWithSpecificUsernamePasswordDataDriver(username, password);

        String expectedErrorMsg = "The username/password entered is invalid";
        loginPage = new LoginPage(driver);
        boolean isCorrect = loginPage.isErrorMessageCorrect(expectedErrorMsg);
        Assert.assertEquals("Error message should contain : " + expectedErrorMsg, isCorrect == true);
    }

    @Test(dataProvider = "userLoginData")
    public void asAUserIShouldBeAbleToLoginAndLogout(User user) {
        String username = user.getUserName();
        String password = user.getPassword();
        System.out.println(username + ", " + password);

        LoginPage loginPage = new LoginPage(driver);
        loginPage = loginPage.loadPage(baseUrl);
        MainNavigationBar mainNavigationBar = loginPage.loginWithSpecificUsernamePasswordDataDriver(username, password);
        String expectedHeading = JUnitUtils.getExpectedHeading(username);

        boolean isCorrectPage = mainNavigationBar.isCorrectPage(expectedHeading);
        Assert.assertEquals("Expected page : " + expectedHeading, isCorrectPage==true);

        //Logout and verify its in logout page
        loginPage = JUnitUtils.logoutIfLoggedIn(username, loginPage);

        boolean isLoginPage = loginPage.isInLoginPage();
        Assert.assertEquals("Expected to be in login page", isLoginPage==true);
    }


    @Test(dataProvider = "userLoginData")
    public void asABusinessUserIShouldBeAbleToNavigateToDifferentSections(User user) {
        String username = user.getUserName();
        String password = user.getPassword();
        System.out.println(username + ", " + password);

        LoginPage loginPage = new LoginPage(driver);
        loginPage = loginPage.loadPage(baseUrl);
        MainNavigationBar mainNavigationBar = loginPage.loginWithSpecificUsernamePasswordDataDriver(username, password);

        List<String> listOfSections = JUnitUtils.getListOfTabSections();
        String expectedHeading = "News";
        //For each page
        for(String page: listOfSections) {
            expectedHeading = page;
            if (page.equals("News")) {
                newsPage = mainNavigationBar.clickNews();
            } else if (page.equals("Tasks")) {
                tasksPage = mainNavigationBar.clickTasks();
            } else if (page.equals("Records")) {
                recordsPage = mainNavigationBar.clickRecords();
            } else if (page.equals("Reports")) {
                reportsPage = mainNavigationBar.clickReports();
            } else if (page.equals("Actions")) {
                actionsPage = mainNavigationBar.clickActions();
            }
        }

        boolean isCorrectPage = mainNavigationBar.isCorrectPage(expectedHeading);
        Assert.assertEquals("Expected page : " + expectedHeading, isCorrectPage==true);
    }


}
