package com.mhra.mdcm.devices.appian.steps.d1;

import com.mhra.mdcm.devices.appian.pageobjects.LoginPage;
import com.mhra.mdcm.devices.appian.session.SessionKey;
import com.mhra.mdcm.devices.appian.steps.common.CommonSteps;
import com.mhra.mdcm.devices.appian.utils.selenium.page.AssertUtils;
import com.mhra.mdcm.devices.appian.utils.selenium.page.PageUtils;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.springframework.context.annotation.Scope;

/**
 * Created by TPD_Auto
 */
@Scope("cucumber-glue")
public class LoginPageSteps extends CommonSteps {


    @When("^I am logged into appian as \"([^\"]*)\" user$")
    public void i_am_logged_into_appian_as_user(String username) throws Throwable {
        loginPage = loginPage.loadPage(baseUrl);
        try {
            mainNavigationBar = loginPage.login(username);
            scenarioSession.putData(SessionKey.loggedInUser, username);
        }catch(Exception e) {
            PageUtils.acceptAlert(driver, "accept");
            try {
                mainNavigationBar = loginPage.reloginUsing(username);
                scenarioSession.putData(SessionKey.loggedInUser, username);
            }catch (Exception e2){}
        }
    }

    @When("^I try to login to appian as username \"([^\"]*)\" and password \"([^\"]*)\"$")
    public void i_am_logged_into_appian_as_user(String username, String password) throws Throwable {
        loginPage = loginPage.loadPage(baseUrl);
        mainNavigationBar = loginPage.loginWithSpecificUsernamePassword(username, password);
        log.info("Login as : " + username + "/" + password);
        scenarioSession.putData(SessionKey.loggedInUser, username);
    }


    @Then("^I should see name of logged in user as \"([^\"]*)\"$")
    public void i_should_see_name_of_logged_in_user_as(String expectedName) throws Throwable {
        String actualName = loginPage.getLoggedInUserName();
        expectedName = AssertUtils.getExpectedName(expectedName);
        Assert.assertThat(actualName.toLowerCase(), Matchers.equalTo(expectedName));
    }

    @Then("^I should see error message with text \"([^\"]*)\"$")
    public void iShouldSeeErrorMessage(String expectedErrorMsg) throws Throwable {
        loginPage = new LoginPage(driver);
        boolean isCorrect = loginPage.isErrorMessageCorrect(expectedErrorMsg);
        Assert.assertThat("Error message should contain : " + expectedErrorMsg, isCorrect, Matchers.is(true));
    }

    @When("^I logout of the application$")
    public void iLogoutOfTheApplication() throws Throwable {
        String currentLoggedInUser = (String) scenarioSession.getData(SessionKey.loggedInUser);

        //Note page displayed to Business user is different from Manufacturer and AuthorisedRep
        if(currentLoggedInUser!=null){
            if(currentLoggedInUser.toLowerCase().contains("business")){
                loginPage = loginPage.logoutIfLoggedIn();
            }else if(currentLoggedInUser.toLowerCase().contains("manufacturer")){
                loginPage = loginPage.logoutIfLoggedInOthers();
            }else if(currentLoggedInUser.toLowerCase().contains("authorised")){
                loginPage = loginPage.logoutIfLoggedInOthers();
            }
        }
    }

    @Then("^I should be in login page$")
    public void iShouldBeInLoginPage() throws Throwable {
        boolean isLoginPage = loginPage.isInLoginPage();
        Assert.assertThat("Expected to be in login page", isLoginPage, Matchers.is(true));
    }

    @When("^I go to \"([^\"]*)\" page$")
    public void iGoToPage(String page) throws Throwable {
        if(page.equals("News")){
            newsPage = mainNavigationBar.clickNews();
        }else if(page.equals("Tasks")){
            tasksPage = mainNavigationBar.clickTasks();
        }else if(page.equals("Records")){
            recordsPage = mainNavigationBar.clickRecords();
        }else if(page.equals("Reports")){
            reportsPage = mainNavigationBar.clickReports();
        }else if(page.equals("Actions")){
            actionsPage = mainNavigationBar.clickActions();
        }
    }
}