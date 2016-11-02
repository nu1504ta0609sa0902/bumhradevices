package com.mhra.mdcm.devices.appian.junit.smoke;

import com.mhra.mdcm.devices.appian.domains.AccountRequest;
import com.mhra.mdcm.devices.appian.domains.junit.User;
import com.mhra.mdcm.devices.appian.pageobjects.LoginPage;
import com.mhra.mdcm.devices.appian.pageobjects.MainNavigationBar;
import com.mhra.mdcm.devices.appian.pageobjects.business.*;
import com.mhra.mdcm.devices.appian.pageobjects.business.sections.*;
import com.mhra.mdcm.devices.appian.pageobjects.external.PortalPage;
import com.mhra.mdcm.devices.appian.utils.datadriven.ExcelDataSheet;
import com.mhra.mdcm.devices.appian.utils.datadriven.JUnitUtils;
import com.mhra.mdcm.devices.appian.utils.driver.BrowserConfig;
import com.mhra.mdcm.devices.appian.utils.others.NetworkUtils;
import org.hamcrest.Matchers;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

/**
 * Created by TPD_Auto on 01/11/2016.
 */
@RunWith(Parameterized.class)
public class SmokeTestsAuthorisedRep {

    @Value("${base.url}")
    public static String baseUrl;

    public static WebDriver driver;
    private String username;
    private String password;

    //Pages
    private NewsPage newsPage;
    private TasksPage tasksPage;
    private RecordsPage recordsPage;
    private ReportsPage reportsPage;
    private ActionsPage actionsPage;

    private Accounts accounts;
    private Devices devices;
    private Products products;
    private AllOrganisations allOrganisations;
    private PortalPage portalPage;
    private CreateTestsData createTestsData;
    private TaskSection taskSection;

    @Parameterized.Parameters(name="{0}")
    public static Collection<User> spreadsheetData() throws IOException {
        ExcelDataSheet excelUtils = new ExcelDataSheet();//
        List<User> listOfUsers = excelUtils.getListOfUsers("configs/data/excel/users.xlsx", "Sheet1");
        listOfUsers = excelUtils.filterUsersBy(listOfUsers, "authorised");
        return listOfUsers;
    }

    public SmokeTestsAuthorisedRep(User user) {
        //super();
        this.username = user.getUserName();
        this.password = user.getPassword();
    }

    @BeforeClass
    public static void setUpDriver() {
        if (driver == null) {
            driver = new BrowserConfig().getDriver();
            baseUrl = NetworkUtils.getTestUrl(baseUrl);
        }
    }

    @AfterClass
    public static void clearBrowsers() {
        if (driver != null) {
            driver.quit();
        }
    }


    @Test
    public void asAUserIShouldSeeErrorMessagesIfCredentialsAreIncorrect() {
        System.out.println(username + ", " + password);

        LoginPage loginPage = new LoginPage(driver);
        loginPage = loginPage.loadPage(baseUrl);
        password = "IsIncorrectPassword";
        loginPage.loginDataDriver(username, password);

        String expectedErrorMsg = "The username/password entered is invalid";
        loginPage = new LoginPage(driver);
        boolean isCorrect = loginPage.isErrorMessageCorrect(expectedErrorMsg);
        Assert.assertThat("Error message should contain : " + expectedErrorMsg, isCorrect, Matchers.is(true));
    }

    @Test
    public void asAUserIShouldBeAbleToLoginAndLogout() {
        System.out.println(username + ", " + password);

        LoginPage loginPage = new LoginPage(driver);
        loginPage = loginPage.loadPage(baseUrl);
        MainNavigationBar mainNavigationBar = loginPage.loginDataDriver(username, password);
        String expectedHeading = JUnitUtils.getExpectedHeading(username);

        boolean isCorrectPage = mainNavigationBar.isCorrectPage(expectedHeading);
        Assert.assertThat("Expected page : " + expectedHeading, isCorrectPage, Matchers.is(true));

        //Logout and verify its in logout page
        loginPage = JUnitUtils.logoutIfLoggedIn(username, loginPage);

        boolean isLoginPage = loginPage.isInLoginPage();
        Assert.assertThat("Expected to be in login page", isLoginPage, Matchers.is(true));
    }


    @Test
    public void checkCorrectLinksAreDisplayedForManufacturerAndAuthorisedRep() {
        System.out.println(username + ", " + password);
        if (username.toLowerCase().contains("manufacturer") || username.toLowerCase().contains("authorised")) {
            System.out.println(username + ", " + password);
            LoginPage loginPage = new LoginPage(driver);
            loginPage = loginPage.loadPage(baseUrl);
            MainNavigationBar mainNavigationBar = loginPage.loginDataDriver(username, password);

            portalPage = mainNavigationBar.clickPortals();
            String delimitedLinks = "Manufacturer Registration";
            boolean areLinksVisible = portalPage.areLinksVisible(delimitedLinks);
            Assert.assertThat("Expected to see the following links : " + delimitedLinks, areLinksVisible, Matchers.is(true));
        }
    }


}
