package com.mhra.mdcm.devices.appian.steps.d1.business;

import com.mhra.mdcm.devices.appian.domains.AccountRequest;
import com.mhra.mdcm.devices.appian.pageobjects.MainNavigationBar;
import com.mhra.mdcm.devices.appian.pageobjects.business.ActionsPage;
import com.mhra.mdcm.devices.appian.session.SessionKey;
import com.mhra.mdcm.devices.appian.steps.common.CommonSteps;
import com.mhra.mdcm.devices.appian.utils.selenium.others.TestHarnessUtils;
import cucumber.api.java.en.When;
import org.springframework.context.annotation.Scope;

import java.util.Map;

/**
 * Created by TPD_Auto
 */
@Scope("cucumber-glue")
public class ActionsPageSteps extends CommonSteps {

    @When("^I go to actions page$")
    public void i_go_to_actions_page() throws Throwable {
        mainNavigationBar = new MainNavigationBar(driver);
        actionsPage = mainNavigationBar.clickActions();
    }


    @When("^I go to test harness page$")
    public void i_go_to_test_harness_page() throws Throwable {
        mainNavigationBar = new MainNavigationBar(driver);
        actionsPage = mainNavigationBar.clickActions();
        createTestsData = actionsPage.gotoTestsHarnessPage();
    }


    @When("^I create a new account using test harness page$")
    public void i_create_a_new_account_using_test_harness_page() throws Throwable {
        //go to accounts page > test harness page
        actionsPage = mainNavigationBar.clickActions();
        createTestsData = actionsPage.gotoTestsHarnessPage();

        //Now create the test data using harness page
        AccountRequest ar = new AccountRequest(scenarioSession);
        actionsPage = createTestsData.createTestOrganisation(ar);

        boolean createdSuccessfully = actionsPage.isInActionsPage();
        if(createdSuccessfully){
            log.warn("Created a new account : " + ar.organisationName);
            scenarioSession.putData(SessionKey.organisationName, ar.organisationName);
        }
    }


    @When("^I create a new account using test harness page with following data$")
    public void i_create_a_new_account_using_test_harness_page_with_following_data(Map<String, String> dataSets) throws Throwable {

        //Now create the test data using harness page
        AccountRequest newAccount = TestHarnessUtils.updateDefaultsWithData(dataSets, scenarioSession);
        log.info("New Account Requested With Following Data : \n" + newAccount);

        //go to accounts page > test harness page
        actionsPage = mainNavigationBar.clickActions();
        createTestsData = actionsPage.gotoTestsHarnessPage();
        actionsPage = createTestsData.createTestOrganisation(newAccount);

        //You may need to do it again, page is not really good
        boolean isInCorrectPage = actionsPage.isInActionsPage();
        if(!isInCorrectPage){
            int count = 1;
            do {
                actionsPage = createTestsData.clickCancel();
                isInCorrectPage = actionsPage.isInActionsPage();
                if(!isInCorrectPage) {
                    actionsPage = mainNavigationBar.clickActions();
                }else{
                    driver.navigate().refresh();
                    actionsPage = new ActionsPage(driver);
                }

                //In actions page try creating agaim
                createTestsData = actionsPage.gotoTestsHarnessPage();
                actionsPage = createTestsData.createTestOrganisation(newAccount);
                isInCorrectPage = actionsPage.isInActionsPage();
                count++;
            } while (!isInCorrectPage && count <= 3);
        }

        log.warn("Created a new account : " + newAccount.organisationName);
        scenarioSession.putData(SessionKey.organisationName, newAccount.organisationName);

    }

}
