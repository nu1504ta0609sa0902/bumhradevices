package com.mhra.mdcm.devices.appian.steps.d1.external;

import com.mhra.mdcm.devices.appian.domains.newaccounts.AccountRequest;
import com.mhra.mdcm.devices.appian.pageobjects.MainNavigationBar;
import com.mhra.mdcm.devices.appian.session.SessionKey;
import com.mhra.mdcm.devices.appian.steps.common.CommonSteps;
import cucumber.api.java.en.And;
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

        scenarioSession.putData(SessionKey.manufacturerData, updatedData);
    }

    @Then("^I should see the changes \"([^\"]*)\" in my accounts page$")
    public void iShouldSeeTheChangesInMyAccountsPage(String keyValuePairToUpdate) throws Throwable {
        boolean isCorrectPage = myAccountPage.isCorrectPage();
        AccountRequest updatedData = (AccountRequest) scenarioSession.getData(SessionKey.manufacturerData);
        boolean updatesFound = myAccountPage.verifyUpdatesDisplayedOnPage(keyValuePairToUpdate, updatedData);
        Assert.assertThat("Expected to see following updates : " + keyValuePairToUpdate, updatesFound, is(true));
    }


    @Then("^I should see creation and association dates$")
    public void i_should_see_creation_and_association_dates() throws Throwable {
        AccountRequest updatedData = (AccountRequest) scenarioSession.getData(SessionKey.manufacturerData);
        boolean updatesFound = myAccountPage.verifyDatesDisplayedOnPage(updatedData);
        Assert.assertThat("Expected to see created and association dates", updatesFound, is(true));
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

        scenarioSession.putData(SessionKey.manufacturerData, updatedData);
    }

    @When("^I update the manufacturer details with following data \"([^\"]*)\"$")
    public void i_update_the_manufacturer_details_with_following_data(String keyValuePair) throws Throwable {
        String country = (String) scenarioSession.getData(SessionKey.organisationCountry);

        if(country !=null && country.equals("United Kingdon")){
            editManufacturer = manufacturerDetails.editAccountInformation();
        }else {
            editManufacturer = manufacturerDetails.amendRepresentedParty();
        }

        //Update details, firstName and lastName
        AccountRequest updatedData = new AccountRequest(scenarioSession);
        updatedData.updateName(scenarioSession);

        boolean errorMsgDisplayed = false;
        int count = 0;
        do {
            count++;
            editManufacturer = editManufacturer.updateFollowingFields(keyValuePair, updatedData);
            errorMsgDisplayed = editManufacturer.isErrorMessageDisplayed();
        }while (errorMsgDisplayed && count < 2);

        //confirm and save
        manufacturerDetails = editManufacturer.confirmChanges(true);

        scenarioSession.putData(SessionKey.manufacturerData, updatedData);
    }

    @Then("^I should see the changes \"([^\"]*)\" in my manufacturer details page$")
    public void iShouldSeeTheChangesInManufacturerDetailsPage(String keyValuePairToUpdate) throws Throwable {
        AccountRequest updatedData = (AccountRequest) scenarioSession.getData(SessionKey.manufacturerData);
        //boolean isCorrectPage = manufacturerDetails.isCorrectPage();
        boolean updatesFound = manufacturerDetails.verifyManufacturerUpdatesDisplayedOnPage(keyValuePairToUpdate, updatedData);
        Assert.assertThat("Expected to see following updates : " + keyValuePairToUpdate, updatesFound, is(true));
    }


    @Then("^I should see the correct \"([^\"]*)\" roles$")
    public void i_should_see_the_correct_roles(String expectedRoles) throws Throwable {
        String loggedInUser = (String) scenarioSession.getData(SessionKey.loggedInUser);
        boolean rolesFound = myAccountPage.isRolesCorrect(loggedInUser, expectedRoles);
        Assert.assertThat("Expected to see following roles : " + expectedRoles, rolesFound, is(true));
    }

    @And("^Address type is not editable$")
    public void addressTypeIsNotEditable() throws Throwable {
        boolean isEditable = amendOrganisationDetails.isAddressTypeEditable();
        Assert.assertThat("Address type should not be editable" , isEditable, is(false));
    }

    @When("^I click on edit \"([^\"]*)\" details$")
    public void iClickOnEditDetails(String editSection) throws Throwable {
        if(editSection.contains("Organisation")){
            amendOrganisationDetails = myAccountPage.amendOrganisationDetails();
        }
    }
}
