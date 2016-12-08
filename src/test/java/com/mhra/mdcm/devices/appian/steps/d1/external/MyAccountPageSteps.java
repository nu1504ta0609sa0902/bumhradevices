package com.mhra.mdcm.devices.appian.steps.d1.external;

import com.mhra.mdcm.devices.appian.domains.newaccounts.AccountRequest;
import com.mhra.mdcm.devices.appian.pageobjects.MainNavigationBar;
import com.mhra.mdcm.devices.appian.session.SessionKey;
import com.mhra.mdcm.devices.appian.steps.common.CommonSteps;
import cucumber.api.PendingException;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;
import org.springframework.context.annotation.Scope;

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


    @When("^I update the contact person details with following data \"([^\"]*)\"$")
    public void i_update_the_following_data(String keyValuePair) throws Throwable {
        amendPersonDetails = myAccountPage.amendContactPersonDetails();

        //Update details, firstName and lastName
        AccountRequest updatedData = new AccountRequest(scenarioSession);
        updatedData.updateName(scenarioSession);

        boolean errorMsgDisplayed = false;
        int count = 0;
        do {
            count++;
            amendPersonDetails = amendPersonDetails.updateFollowingFields(keyValuePair, updatedData);
            errorMsgDisplayed = amendPersonDetails.isErrorMessageDisplayed();
        }while (errorMsgDisplayed && count < 2);

        //confirm and save
        amendPersonDetails = amendPersonDetails.confirmChangesRelateToOrganisation(true);
        myAccountPage = amendPersonDetails.saveChanges(true);

        scenarioSession.putData(SessionKey.updatedData, updatedData);
    }

    @Then("^I should see the changes \"([^\"]*)\" in my accounts page$")
    public void iShouldSeeTheChangesInMyAccountsPage(String keyValuePairToUpdate) throws Throwable {
        boolean isCorrectPage = myAccountPage.isCorrectPage();
        AccountRequest updatedData = (AccountRequest) scenarioSession.getData(SessionKey.updatedData);
        boolean updatesFound = myAccountPage.verifyUpdatesDisplayedOnPage(keyValuePairToUpdate, updatedData);
        Assert.assertThat("Expected to see following updates : " + keyValuePairToUpdate, updatesFound, is(true));
    }


    @When("^I update the organisation details with following data \"([^\"]*)\"$")
    public void i_update_the_organisation_details_with_following_data(String keyValuePair) throws Throwable {
        amendOrganisationDetails = myAccountPage.amendOrganisationDetails();

        //Update details, firstName and lastName
        AccountRequest updatedData = new AccountRequest(scenarioSession);
        updatedData.updateName(scenarioSession);

        boolean errorMsgDisplayed = false;
        int count = 0;
        do {
            count++;
            amendOrganisationDetails = amendOrganisationDetails.updateFollowingFields(keyValuePair, updatedData);
            errorMsgDisplayed = amendOrganisationDetails.isErrorMessageDisplayed();
        }while (errorMsgDisplayed && count < 2);

        //confirm and save
        amendOrganisationDetails = amendOrganisationDetails.confirmChanges(true);
        myAccountPage = amendOrganisationDetails.saveChanges(true);

        scenarioSession.putData(SessionKey.updatedData, updatedData);
    }


    @Then("^I should see the correct \"([^\"]*)\" roles$")
    public void i_should_see_the_correct_roles(String expectedRoles) throws Throwable {
        String loggedInUser = (String) scenarioSession.getData(SessionKey.loggedInUser);
        boolean rolesFound = myAccountPage.isRolesCorrect(loggedInUser, expectedRoles);
        Assert.assertThat("Expected to see following roles : " + expectedRoles, rolesFound, is(true));
    }

}
