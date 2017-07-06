package com.mhra.mdcm.devices.appian.steps.d1.external;

import com.mhra.mdcm.devices.appian.domains.newaccounts.AccountRequestDO;
import com.mhra.mdcm.devices.appian.pageobjects.MainNavigationBar;
import com.mhra.mdcm.devices.appian.pageobjects.external.manufacturer.ManufacturerDetails;
import com.mhra.mdcm.devices.appian.session.SessionKey;
import com.mhra.mdcm.devices.appian.steps.common.CommonSteps;
import com.mhra.mdcm.devices.appian.utils.selenium.others.RandomDataUtils;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;
import org.springframework.context.annotation.Scope;

import java.util.Date;

import static org.hamcrest.Matchers.is;

/**
 * Created by TPD_Auto
 */
@Scope("cucumber-glue")
public class MyAccountPageSteps extends CommonSteps {


    @When("^I go to my accounts page$")
    public void iGoToMyAccountsPage() throws Throwable {
        mainNavigationBar = new MainNavigationBar(driver);
        myAccountPage = mainNavigationBar.gotoMyAccountProfilePage();
        myAccountPage = mainNavigationBar.clickMyOrganisationsTab();
    }


    @When("^I update the contact person details with following data \"([^\"]*)\"$")
    public void i_update_the_following_data(String keyValuePair) throws Throwable {
        myAccountPage = myAccountPage.clickManageContacts();
        myAccountPage = myAccountPage.sortBy("Associated Date", 1);
        amendContactPersonDetails = myAccountPage.selectLastContactToEdit();
        //amendContactPersonDetails = myAccountPage.amendContactPersonDetails();

        //Update details, firstName and lastName
        AccountRequestDO updatedData = new AccountRequestDO(scenarioSession);
        updatedData.updateName(scenarioSession);

        boolean errorMsgDisplayed = false;
        int count = 0;
        do {
            count++;
            amendContactPersonDetails = amendContactPersonDetails.updateFollowingFields(keyValuePair, updatedData);
            errorMsgDisplayed = amendContactPersonDetails.isErrorMessageDisplayed();
        }while (errorMsgDisplayed && count < 2);

        scenarioSession.putData(SessionKey.manufacturerData, updatedData);
    }

    @And("^I add a new contact person with random data$")
    public void iAddANewContactPersonWithRandomData() throws Throwable {
        myAccountPage = myAccountPage.clickManageContacts();
        myAccountPage = myAccountPage.clickAddContact();

        AccountRequestDO contactNew = new AccountRequestDO(scenarioSession);
        contactNew.isMainPointOfContact = false;
        String hourMin = RandomDataUtils.getTimeMinHour(false).replaceAll("_","");
        contactNew.firstName = RandomDataUtils.getRandomTestNameStartingWith(contactNew.firstName, 6);
        String x = "9" + hourMin + new Date().getTime();
        contactNew.telephone = x.substring(0, x.length()-7);

        //Add new contact
        amendContactPersonDetails = amendContactPersonDetails.addNewContactPerson(contactNew, true);
        scenarioSession.putData(SessionKey.manufacturerData, contactNew);
    }

    @Then("^I should see the changes \"([^\"]*)\" in my accounts page$")
    public void iShouldSeeTheChangesInMyAccountsPage(String keyValuePairToUpdate) throws Throwable {
        AccountRequestDO updatedData = (AccountRequestDO) scenarioSession.getData(SessionKey.manufacturerData);

        //BUG requires another refresh
        //myAccountPage = myAccountPage.refreshThePage();
        boolean isCorrectPage = myAccountPage.isCorrectPage();
        boolean updatesFound = myAccountPage.verifyAccountUpdatesDisplayedOnPage(keyValuePairToUpdate, updatedData);
        Assert.assertThat("Expected to see following updates : " + keyValuePairToUpdate, updatesFound, is(true));
    }


    @Then("^I should see creation and association dates$")
    public void i_should_see_creation_and_association_dates() throws Throwable {
        AccountRequestDO updatedData = (AccountRequestDO) scenarioSession.getData(SessionKey.manufacturerData);
        boolean updatesFound = myAccountPage.verifyDatesDisplayedOnPage(updatedData);
        Assert.assertThat("Expected to see created and association dates", updatesFound, is(true));
    }


    @When("^I update the organisation details with following data \"([^\"]*)\"$")
    public void i_update_the_organisation_details_with_following_data(String keyValuePair) throws Throwable {
        amendOrganisationDetails = myAccountPage.editAccountInformation();

        //Update details, firstName and lastName
        AccountRequestDO updatedData = new AccountRequestDO(scenarioSession);
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

        if(country !=null && country.equals("United Kingdom")){
            editManufacturer = manufacturerDetails.editAccountInformation();
        }else {
            editManufacturer = manufacturerDetails.amendRepresentedParty();
        }

        //Update details, firstName and lastName
        AccountRequestDO updatedData = new AccountRequestDO(scenarioSession);
        updatedData.updateName(scenarioSession);

        boolean errorMsgDisplayed = false;
        int count = 0;
        do {
            count++;
            editManufacturer = editManufacturer.updateFollowingFields(keyValuePair, updatedData);
            errorMsgDisplayed = editManufacturer.isErrorMessageDisplayed();
        }while (errorMsgDisplayed && count < 2);

        //confirm and save
        //manufacturerDetails = editManufacturer.confirmChanges(true);
        manufacturerDetails = new ManufacturerDetails(driver);

        scenarioSession.putData(SessionKey.manufacturerData, updatedData);
    }

    @Then("^I should see the changes \"([^\"]*)\" in my manufacturer details page$")
    public void iShouldSeeTheChangesInManufacturerDetailsPage(String keyValuePairToUpdate) throws Throwable {
        AccountRequestDO updatedData = (AccountRequestDO) scenarioSession.getData(SessionKey.manufacturerData);
        //boolean isCorrectPage = manufacturerDetails.isCorrectPage();
        manufacturerDetails = manufacturerDetails.refreshThePage();
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

    @And("^I view the newly created contact person$")
    public void iViewTheNewlyCreatedContactPerson() throws Throwable {
        AccountRequestDO data = (AccountRequestDO) scenarioSession.getData(SessionKey.manufacturerData);
        myAccountPage = myAccountPage.sortBy("Telephone", 1);
    }

    @And("^I remove the newly created contact person$")
    public void iRemoveTheNewlyCreatedContactPerson() throws Throwable {
        AccountRequestDO data = (AccountRequestDO) scenarioSession.getData(SessionKey.manufacturerData);
        myAccountPage.selectNewContactToEdit(data, false);
        myAccountPage = myAccountPage.clickRemoveContact();
        myAccountPage = myAccountPage.confirmRemoveContact(true);
    }

    @Then("^I should not see the contact person information on the page$")
    public void iShouldNotSeeTheContactPersonInformationOnThePage() throws Throwable {
        AccountRequestDO data = (AccountRequestDO) scenarioSession.getData(SessionKey.manufacturerData);
        boolean isContactVisible = myAccountPage.isContactVisible(data);
        Assert.assertThat("Contact Should be removed : " + data.firstName, isContactVisible, is(false));
    }

    @Then("^I should see the contact person information on the page$")
    public void iShouldSeeTheContactPersonInformationOnThePage() throws Throwable {
        AccountRequestDO data = (AccountRequestDO) scenarioSession.getData(SessionKey.manufacturerData);
        boolean isContactVisible = myAccountPage.isContactVisible(data);
        Assert.assertThat("Contact Should be removed : " + data.firstName, isContactVisible, is(true));
    }
}
