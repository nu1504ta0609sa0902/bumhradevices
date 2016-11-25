package com.mhra.mdcm.devices.appian.steps.d1.external;

import com.mhra.mdcm.devices.appian.domains.AccountRequest;
import com.mhra.mdcm.devices.appian.domains.MyAccount;
import com.mhra.mdcm.devices.appian.pageobjects.MainNavigationBar;
import com.mhra.mdcm.devices.appian.session.SessionKey;
import com.mhra.mdcm.devices.appian.steps.common.CommonSteps;
import com.mhra.mdcm.devices.appian.utils.selenium.others.FileUtils;
import com.mhra.mdcm.devices.appian.utils.selenium.page.WaitUtils;
import cucumber.api.PendingException;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;
import org.springframework.context.annotation.Scope;

import java.io.File;
import java.util.Properties;

import static org.hamcrest.Matchers.is;

/**
 * Created by TPD_Auto
 */
@Scope("cucumber-glue")
public class MyAccountPageSteps extends CommonSteps {

    @When("^I go to my accounts page$")
    public void iGoToMyAccountsPage() throws Throwable {
        mainNavigationBar = new MainNavigationBar(driver);
        myAccountPage = mainNavigationBar.clickMyAccount();
    }


    @When("^I update the following data \"([^\"]*)\"$")
    public void i_update_the_following_data(String keyValuePair) throws Throwable {
        amendPersonDetails = myAccountPage.amendContactPersonDetails();

        //Update details, firstName and lastName
        AccountRequest updatedData = new AccountRequest(scenarioSession);

        boolean errorMsgDisplayed = false;
        int count = 0;
        do {
            count++;
            amendPersonDetails = amendPersonDetails.updateFollowingFields(keyValuePair, updatedData);
            errorMsgDisplayed = amendPersonDetails.isErrorMessageDisplayed();
        }while (errorMsgDisplayed && count < 2);

        //confirm and save
        amendPersonDetails = amendPersonDetails.confirmChanges(true);
        myAccountPage = amendPersonDetails.saveChanges(true);

        scenarioSession.putData(SessionKey.updatedData, updatedData);
    }

    @Then("^I should see the changes \"([^\"]*)\" in my accounts page$")
    public void iShouldSeeTheChangesInMyAccountsPage(String keyValuePairToUpdate) throws Throwable {
        boolean isCorrectPage = myAccountPage.isCorrectPage();
        AccountRequest updatedData = (AccountRequest) scenarioSession.getData(SessionKey.updatedData);
        boolean updatesFound = false;
//        int numberOfRefresh = 0;
//        do {
//            numberOfRefresh++;
//            updatesFound = myAccountPage.verifyUpdatesDisplayedOnPage(keyValuePairToUpdate, updatedData);
//            if (!updatesFound) {
//                WaitUtils.nativeWaitInSeconds(1);
//            }
//        } while (!updatesFound && numberOfRefresh < 3);
        updatesFound = myAccountPage.verifyUpdatesDisplayedOnPage(keyValuePairToUpdate, updatedData);
        Assert.assertThat("Expected to see following updates : " + keyValuePairToUpdate, updatesFound, is(true));
    }
}
