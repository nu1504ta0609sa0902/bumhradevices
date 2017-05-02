package com.mhra.mdcm.devices.appian.steps.d1.business;

import com.mhra.mdcm.devices.appian.domains.newaccounts.AccountRequestDO;
import com.mhra.mdcm.devices.appian.pageobjects.MainNavigationBar;
import com.mhra.mdcm.devices.appian.pageobjects.business.ActionsTabPage;
import com.mhra.mdcm.devices.appian.session.SessionKey;
import com.mhra.mdcm.devices.appian.steps.common.CommonSteps;
import com.mhra.mdcm.devices.appian.utils.selenium.others.TestHarnessUtils;
import com.mhra.mdcm.devices.appian.utils.selenium.page.AssertUtils;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.springframework.context.annotation.Scope;

import java.util.List;
import java.util.Map;

/**
 * Created by TPD_Auto
 */
@Scope("cucumber-glue")
public class ActionsPageSteps extends CommonSteps {

    @When("^I go to actions page$")
    public void i_go_to_actions_page() throws Throwable {
        mainNavigationBar = new MainNavigationBar(driver);
        actionsTabPage = mainNavigationBar.clickActions();
    }


    @When("^I go to test harness page$")
    public void i_go_to_test_harness_page() throws Throwable {
        mainNavigationBar = new MainNavigationBar(driver);
        actionsTabPage = mainNavigationBar.clickActions();
        createTestsData = actionsTabPage.gotoTestsHarnessPage();
    }

    @When("^I get a list of countries matching searchterm \"([^\"]*)\" in business test harness$")
    public void i_enter_in_the_new_country_field(String searchTerm) throws Throwable {
        List<String> listOfCountries = createTestsData.getListOfAutosuggestionsFor(searchTerm);
        scenarioSession.putData(SessionKey.autoSuggestResults, listOfCountries);
    }

    @Then("^I should see following \"([^\"]*)\" returned by autosuggests$")
    public void i_should_see_following_returned_by_autosuggests(String commaDelimitedExpectedMatches) throws Throwable {
        List<String> listOfMatches = (List<String>) scenarioSession.getData(SessionKey.autoSuggestResults);
        boolean isResultMatchingExpectation = AssertUtils.areAllDataInAutosuggestCorrect(listOfMatches, commaDelimitedExpectedMatches);
        Assert.assertThat("Expected to see : " + commaDelimitedExpectedMatches + ", in auto suggested list : " + listOfMatches, isResultMatchingExpectation, Matchers.is(true));
    }


    @When("^I create a new account using test harness page$")
    public void i_create_a_new_account_using_test_harness_page() throws Throwable {
        //go to accounts page > test harness page
        actionsTabPage = mainNavigationBar.clickActions();
        createTestsData = actionsTabPage.gotoTestsHarnessPage();

        //Now create the test data using harness page
        AccountRequestDO ar = new AccountRequestDO(scenarioSession);
        actionsTabPage = createTestsData.createTestOrganisation(ar);

        boolean createdSuccessfully = actionsTabPage.isInActionsPage();
        if(createdSuccessfully){
            log.warn("Created a new account : " + ar.organisationName);
            scenarioSession.putData(SessionKey.organisationName, ar.organisationName);
        }
    }


    /**
     * DON'T USE THE AUTOMATION ACCOUNT WITH THIS, UNLESS YOU WANT ALL THE DATA TO BE CLEANED
     * @param dataSets
     * @throws Throwable
     */
    @When("^I create a new account using business test harness page with following data$")
    public void i_create_a_new_account_using_test_harness_page_with_following_data(Map<String, String> dataSets) throws Throwable {

        //Now create the test data using harness page
        AccountRequestDO newAccount = TestHarnessUtils.updateBusinessDefaultsWithData(dataSets, scenarioSession);
        //log.info("New Account Requested With Following Data : \n" + newAccount);

        //go to accounts page > test harness page
        actionsTabPage = mainNavigationBar.clickActions();
        createTestsData = actionsTabPage.gotoTestsHarnessPage();
        actionsTabPage = createTestsData.createTestOrganisation(newAccount);

        //You may need to do it again, page is not really good
        boolean isInCorrectPage = actionsTabPage.isInActionsPage();
        if(!isInCorrectPage){
            int count = 1;
            do {
                actionsTabPage = createTestsData.clickCancel();
                isInCorrectPage = actionsTabPage.isInActionsPage();
                if(!isInCorrectPage) {
                    actionsTabPage = mainNavigationBar.clickActions();
                }else{
                    driver.navigate().refresh();
                    actionsTabPage = new ActionsTabPage(driver);
                }

                //In actions page try creating agaim
                createTestsData = actionsTabPage.gotoTestsHarnessPage();
                actionsTabPage = createTestsData.createTestOrganisation(newAccount);
                isInCorrectPage = actionsTabPage.isInActionsPage();
                count++;
            } while (!isInCorrectPage && count <= 3);
        }

        log.warn("Created a new account : " + newAccount.organisationName);
        scenarioSession.putData(SessionKey.organisationName, newAccount.organisationName);
        scenarioSession.putData(SessionKey.newAccountName, newAccount.organisationName);
        scenarioSession.putData(SessionKey.manufacturerData, newAccount);
        scenarioSession.putData(SessionKey.taskType, "New Account");

    }

}
