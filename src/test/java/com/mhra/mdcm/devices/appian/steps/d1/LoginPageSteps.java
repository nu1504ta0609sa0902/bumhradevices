package com.mhra.mdcm.devices.appian.steps.d1;

import com.mhra.mdcm.devices.appian.pageobjects.LoginPage;
import com.mhra.mdcm.devices.appian.pageobjects._Page;
import com.mhra.mdcm.devices.appian.session.SessionKey;
import com.mhra.mdcm.devices.appian.steps.common.CommonSteps;
import com.mhra.mdcm.devices.appian.utils.selenium.page.AssertUtils;
import com.mhra.mdcm.devices.appian.utils.selenium.page.PageUtils;
import com.mhra.mdcm.devices.appian.utils.selenium.page.WaitUtils;
import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import static org.hamcrest.CoreMatchers.*;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.springframework.context.annotation.Scope;

/**
 * Created by TPD_Auto
 */
@Scope("cucumber-glue")
public class LoginPageSteps extends CommonSteps {

    @Given("^I am in login page$")
    public void i_am_in_login_page() throws Throwable {
        loginPage = loginPage.loadPage(baseUrl);
    }

    @When("^I am logged into appian as \"([^\"]*)\" user$")
    public void i_am_logged_into_appian_as_user(String username) throws Throwable {
        try {
            //boolean alreadyLoggedInAsUser = loginPage.isAlreadyLoggedInAsUser(username);
            loginPage = loginPage.loadPage(baseUrl);
            PageUtils.acceptAlert(driver, "accept", 1);
            mainNavigationBar = loginPage.login(username);
            scenarioSession.putData(SessionKey.loggedInUser, username);
        } catch (Exception e) {
            PageUtils.acceptAlert(driver, "accept", 1);
            try {
                mainNavigationBar = loginPage.reloginUsing(username);
                scenarioSession.putData(SessionKey.loggedInUser, username);
            } catch (Exception e2) {
            }
        }
    }

    @When("^I login to appian as \"([^\"]*)\" user$")
    public void i_login_to_appian_as_user(String username) throws Throwable {
        loginPage = loginPage.loadPage(baseUrl);
        try {
            PageUtils.acceptAlert(driver, "accept", 1);
            mainNavigationBar = loginPage.login(username);
            scenarioSession.putData(SessionKey.loggedInUser, username);
        } catch (Exception e) {
            PageUtils.acceptAlert(driver, "accept", 1);
            try {
                mainNavigationBar = loginPage.reloginUsing(username);
                scenarioSession.putData(SessionKey.loggedInUser, username);
            } catch (Exception e2) {
            }
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
        String actualName = loginPage.getLoggedInUserName(expectedName);
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
        if (currentLoggedInUser != null) {
            if (currentLoggedInUser.toLowerCase().contains("business")) {
                loginPage = loginPage.logoutIfLoggedIn();
            } else if (currentLoggedInUser.toLowerCase().contains("manufacturer")) {
                loginPage = loginPage.logoutIfLoggedInOthers();
            } else if (currentLoggedInUser.toLowerCase().contains("authorised")) {
                loginPage = loginPage.logoutIfLoggedInOthers();
            }
        }
    }

    @Then("^I should be in login page$")
    public void iShouldBeInLoginPage() throws Throwable {
        boolean isLoginPage = loginPage.isInLoginPage(_Page.TIMEOUT_10_SECOND);
        Assert.assertThat("Expected to be in login page", isLoginPage, Matchers.is(true));
    }

    @When("^I go to \"([^\"]*)\" page$")
    public void iGoToPage(String page) throws Throwable {
        if (page.equals("News")) {
            newsPage = mainNavigationBar.clickNews();
        } else if (page.equals("Tasks")) {
            tasksPage = mainNavigationBar.clickTasks();
        } else if (page.equals("Records")) {
            recordsPage = mainNavigationBar.clickRecords();
            recordsPage.isCorrectPage();
        } else if (page.equals("Reports")) {
            reportsPage = mainNavigationBar.clickReports();
        } else if (page.equals("Actions")) {
            actionsTabPage = mainNavigationBar.clickActions();
        }
    }

    @When("^I logout and log back into appian as \"([^\"]*)\" user$")
    public void iLogoutAndLogBackIntoAppianAsUser(String username) throws Throwable {
        iLogoutOfTheApplication();
        i_am_logged_into_appian_as_user(username);
    }


    @When("^I logout and logback in with newly created account and update the password to \"([^\"]*)\"$")
    public void i_logout_and_logback_in_with_newly_created_account_and_set_the_password_to(String updatePasswordTo) throws Throwable {
        String userName = (String) scenarioSession.getData(SessionKey.newUserName);
        String tempPassword = (String) scenarioSession.getData(SessionKey.temporaryPassword);

        //This should take user to change password page
        iLogoutOfTheApplication();
        i_am_logged_into_appian_as_user(userName, tempPassword);
        mainNavigationBar = loginPage.changePasswordTo(tempPassword, updatePasswordTo);
        scenarioSession.putData(SessionKey.updatedPassword, updatePasswordTo);
    }

    @When("^I am logged back into appian using the newly created account details$")
    public void i_am_logged_back_into_appian_using_newly_created_acccount_details() throws Throwable {
        String userName = (String) scenarioSession.getData(SessionKey.newUserName);
        String password = (String) scenarioSession.getData(SessionKey.temporaryPassword);
        i_am_logged_into_appian_as_user(userName, password);
    }

    @When("^I request a new password for stored user$")
    public void iRequestANewPasswordForStoredUser() throws Throwable {
        String userName = (String) scenarioSession.getData(SessionKey.newUserName);
        //userName = "Manufacturer1307_50598";
        loginPage = loginPage.gotoForgottenPassword();
        loginPage = loginPage.enterUsername(userName);
    }

    @When("^I click on the password reset link$")
    public void iClickOnThePasswordResetLink() throws Throwable {
        String messageBody = (String) scenarioSession.getData(SessionKey.emailBody);
        if(messageBody!=null){
            String link = messageBody.substring(messageBody.indexOf("low:")+5, messageBody.indexOf("This l")-1);
            loginPage = loginPage.loadPage(link);
        }
    }

    @Then("^I should see the correct username in change password page$")
    public void iShouldSeeTheCorrectUsername() throws Throwable {
        String userNameTxt = (String) scenarioSession.getData(SessionKey.newUserName);
        //userNameTxt = "Manufacturer1307_50598";
        boolean isCorrect = loginPage.isChangePasswordUsernameCorrect(userNameTxt);
        Assert.assertThat("Expected username : " + userNameTxt, isCorrect, is(true));
    }

    @When("^I update the password to \"([^\"]*)\"$")
    public void iUpdateThePasswordTo(String newPassword) throws Throwable {
        mainNavigationBar = loginPage.updateThePasswordTo(newPassword);
        scenarioSession.putData(SessionKey.updatedPassword, newPassword);
    }

    @And("^I should be able to logout and logback in with new password$")
    public void iLogoutAndLogbackInWithNewPassword() throws Throwable {
        String userNameTxt = (String) scenarioSession.getData(SessionKey.newUserName);
        String updatedPassword = (String) scenarioSession.getData(SessionKey.updatedPassword);
        //userNameTxt = "Manufacturer1307_50598";
        iLogoutOfTheApplication();
        i_am_logged_into_appian_as_user(userNameTxt, updatedPassword);
    }
}
