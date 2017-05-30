package com.mhra.mdcm.devices.appian.steps.d1.external;

import com.mhra.mdcm.devices.appian.domains.newaccounts.DeviceDO;
import com.mhra.mdcm.devices.appian.domains.newaccounts.ManufacturerRequestDO;
import com.mhra.mdcm.devices.appian.pageobjects.external.cfs.CFSAddDevices;
import com.mhra.mdcm.devices.appian.pageobjects.external.device.AddDevices;
import com.mhra.mdcm.devices.appian.session.SessionKey;
import com.mhra.mdcm.devices.appian.steps.common.CommonSteps;
import com.mhra.mdcm.devices.appian.utils.selenium.others.TestHarnessUtils;
import com.mhra.mdcm.devices.appian.utils.selenium.page.*;
import cucumber.api.PendingException;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;
import org.springframework.context.annotation.Scope;

import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.is;

/**
 * Created by TPD_Auto
 */
@Scope("cucumber-glue")
public class CFSSteps extends CommonSteps {

    @When("^I create a new manufacturer using CFS manufacturer test harness page with following data$")
    public void i_create_a_new_manufacturer_using_CFS_test_harness_page_with_following_data(Map<String, String> dataSets) throws Throwable {

        createNewCFSManufacturer = cfsManufacturerList.addNewManufacturer();
        ManufacturerRequestDO newAccount = TestHarnessUtils.updateManufacturerDefaultsWithData(dataSets, scenarioSession);
        log.info("New Manufacturer Account Requested With Following Data : \n" + newAccount);

        //Create new manufacturer data
        addDevices = createNewCFSManufacturer.createTestOrganisation(newAccount);
        if (createNewCFSManufacturer.isErrorMessageDisplayed()) {
            externalHomePage = mainNavigationBar.clickExternalHOME();
            cfsManufacturerList = externalHomePage.gotoCFSPage();
            cfsManufacturerList = cfsManufacturerList.tellUsAboutYourOrganisation();
            createNewCFSManufacturer = cfsManufacturerList.addNewManufacturer();
            addDevices = createNewCFSManufacturer.createTestOrganisation(newAccount);
        }
        scenarioSession.putData(SessionKey.organisationName, newAccount.organisationName);
        scenarioSession.putData(SessionKey.manufacturerData, newAccount);
        scenarioSession.putData(SessionKey.taskType, "New Manufacturer");
    }

    @When("^I goto add a new cfs manufacturer page$")
    public void i_goto_add_a_new_cfs_manufacturer_page() throws Throwable {
        createNewCFSManufacturer = cfsManufacturerList.addNewManufacturer();
    }

    @Then("^I should see current stage of indication$")
    public void i_should_see_current_stage_of_indication() throws Throwable {
        String indicators = "Manufacturer, Device, CE certificates, Products, Review";
        boolean isDisplayed = createNewCFSManufacturer.isIndicationStageDisplayed(indicators);
        Assert.assertEquals("Expected to see following milestone indicators : " + indicators, isDisplayed, true);
    }

    @When("^I fill out the form called tell us about your organisation$")
    public void i_fill_form_called_tell_us_about_your_org() throws Throwable {
        cfsManufacturerList = cfsManufacturerList.tellUsAboutYourOrganisation();
    }



    @Then("^I should see a list of manufacturers available for CFS$")
    public void i_should_see_a_list_of_manufacturers_available_for_CFS() throws Throwable {
        cfsManufacturerList = cfsManufacturerList.tellUsAboutYourOrganisation();
        boolean isListVisible = cfsManufacturerList.isManufacturerListDisplayed();
        Assert.assertThat("Expected to see a list of manufacturers", isListVisible, is(true));
    }

    @When("^I click on a random organisation which needs cfs$")
    public void i_click_on_a_random_organisation_which_needs_cfs() throws Throwable {
        String name = cfsManufacturerList.getARandomOrganisationName();
        deviceDetails = cfsManufacturerList.viewManufacturer(name);

        scenarioSession.putData(SessionKey.organisationName, name);
    }

    @When("^I click on a organisation name begins with \"([^\"]*)\" which needs cfs$")
    public void iClickOnAOrganisationNameBeginsWithWhichNeedsCfs(String orgName) throws Throwable {
        String name = cfsManufacturerList.getARandomOrganisationName(orgName);
        log.info("Manufacturer selected : " + name);
        deviceDetails = cfsManufacturerList.viewManufacturer(name);
        scenarioSession.putData(SessionKey.organisationName, name);
    }

    @When("^I order cfs for a country with following data$")
    public void i_order_cfs_for_a_country_with_following_data(Map<String, String> dataSets) throws Throwable {
        //Data
        String countryName = dataSets.get("countryName");
        String noOfCFS = dataSets.get("noOfCFS");

        //Order CFS for a random device
        deviceDetails = deviceDetails.orderCFS();
        deviceDetails = deviceDetails.selectDevices();
        deviceDetails = deviceDetails.enterACertificateDetails(countryName, noOfCFS);

        //Store data to be verified later
        scenarioSession.putData(SessionKey.organisationCountry, countryName);
        scenarioSession.putData(SessionKey.numberOfCertificates, noOfCFS);
    }

    @When("^I order cfs for multiple countries with following data$")
    public void i_order_cfs_for_multiple_countries_with_following_data(Map<String, String> dataSets) throws Throwable {
        //Data pair of cfs and country, Bangladesh=10,Brazi=2,India=5 etc
        String cfsAndCountryPairs = dataSets.get("listOfCFSCountryPair");
        String[] data = cfsAndCountryPairs.split(",");

        //Enter CFS data, Only click "Continue" after adding all the countries
        deviceDetails = deviceDetails.orderCFS();
        deviceDetails = deviceDetails.selectDevices();

        int count = 1;
        boolean clickAddCountryLink = true;
        for (String d : data) {
            if (count == data.length) {
                clickAddCountryLink = false;
            }
            deviceDetails = deviceDetails.enterMultipleCertificateDetails(d, clickAddCountryLink, count);
            count++;
        }
        deviceDetails = deviceDetails.clickContinueButton();

        //Store for future varification
        scenarioSession.putData(SessionKey.listOfCFSCountryPairs, data);
    }

    @Then("^I update cfs for multiple countries with following data$")
    public void i_update_cfs_for_multiple_countries_with_following_data(Map<String, String> dataSets) throws Throwable {
        String cfsAndCountryPairs = dataSets.get("listOfCFSCountryPair");
        String[] data = cfsAndCountryPairs.split(",");
        deviceDetails = deviceDetails.clickEditCountryAndCertificateLink();
        new PendingException();
    }

    @Then("^I should see the correct number of certificates \"([^\"]*)\" in review page$")
    public void i_should_see_the_correct_number_of_certificates_in_review_page(String number) throws Throwable {
        boolean isNumberOfCertificatesCorrect = deviceDetails.isNumberOfCertificatesCorrect(number);
        Assert.assertThat("Expected number of certifictes : " + number, isNumberOfCertificatesCorrect, is(true));
    }


    @Then("^I should see the correct details in cfs review page$")
    public void i_should_see_the_correct_details_in_cfs_review_page() throws Throwable {
        String country = (String) scenarioSession.getData(SessionKey.organisationCountry);
        String numberOfCFS = (String) scenarioSession.getData(SessionKey.numberOfCertificates);
        String name = (String) scenarioSession.getData(SessionKey.organisationName);

        //Verify data number of cfs and country and name
        boolean isNumberOfCertificatesCorrect = deviceDetails.isNumberOfCertificatesCorrect(numberOfCFS);
        boolean isManufacturerNameCorrect = deviceDetails.isManufacturerNameCorrect(name);
        boolean isTotalNumberOfCertificateCorrect = deviceDetails.isTotalNumberOfCertificatesCorrect(numberOfCFS);

        int totalCostOfCerts = CommonUtils.calculateTotalCost(numberOfCFS);
        boolean isTotalCostOfCertificateCorrect = deviceDetails.isTotalCostOfCertificatesCorrect(totalCostOfCerts);
        Assert.assertThat("Expected number of certifictes : " + numberOfCFS, isNumberOfCertificatesCorrect, is(true));
        Assert.assertThat("Expected manufacturer name to be : " + name, isManufacturerNameCorrect, is(true));
        Assert.assertThat("Expected total number of certificates : " + numberOfCFS, isTotalNumberOfCertificateCorrect, is(true));
        Assert.assertThat("Expected total cost of certificates : " + totalCostOfCerts, isTotalCostOfCertificateCorrect, is(true));

    }

    @Then("^I should see correct details for all the countries and certificate in cfs review page$")
    public void i_should_multiple_country_details_in_cfs_review_page() throws Throwable {
        String[] data = (String[] )scenarioSession.getData(SessionKey.listOfCFSCountryPairs);
        String name = (String) scenarioSession.getData(SessionKey.organisationName);

        //Verify data number of cfs and country and name
        boolean isManufacturerNameCorrect = deviceDetails.isManufacturerNameCorrect(name);
        boolean isListOfCertificateCountCorrect = deviceDetails.areTheCertificateCountCorrect(data);
        boolean isTotalNumberOfCertCorrect = deviceDetails.isTotalNumberOfCertificatesCorrect(data);

        int totalCostOfCerts = CommonUtils.calculateTotalCost(data);
        boolean isTotalCostOfCertificateCorrect = deviceDetails.isTotalCostOfCertificatesCorrect(totalCostOfCerts);

        Assert.assertThat("Expected to see following countries and asssoficated CFS count : " + data, isListOfCertificateCountCorrect, is(true));
        Assert.assertThat("Expected manufacturer name to be : " + name, isManufacturerNameCorrect, is(true));
        Assert.assertThat("Total number of certificates may not be correct : " + data, isTotalNumberOfCertCorrect, is(true));
        Assert.assertThat("Expected total cost of certificates : " + totalCostOfCerts, isTotalCostOfCertificateCorrect, is(true));

    }


    @Then("^I edit the list of devices added for CFS$")
    public void i_edit_the_list_of_devices_added_for_CFS() throws Throwable {
        deviceDetails = deviceDetails.clickEditDevicesLink();

        //Complete editing of devices, at the moment I only have 1 device
        //deviceDetails = deviceDetails.editDevicesAddedForCFS();
        deviceDetails.clickContinueButton();
        deviceDetails.clickContinueButton();
    }


    @When("^I update the country added for CFS to \"([^\"]*)\"$")
    public void i_edit_the_country_added_for_CFS_to(String countryName) throws Throwable {
        deviceDetails = deviceDetails.clickEditCountryAndCertificateLink();
        deviceDetails = deviceDetails.updateCountryNumber(1, countryName);
        deviceDetails = deviceDetails.clickContinueButton();
        scenarioSession.putData(SessionKey.organisationCountry, countryName);
    }

    @When("^I update the no of certificates for CFS to (\\d+)$")
    public void i_edit_the_country_added_for_CFS_to(int numberOfCFS) throws Throwable {
        deviceDetails = deviceDetails.clickEditCountryAndCertificateLink();
        deviceDetails = deviceDetails.updateNumberOfCFS(1, String.valueOf(numberOfCFS));
        deviceDetails = deviceDetails.clickContinueButton();
        scenarioSession.putData(SessionKey.numberOfCertificates, String.valueOf(numberOfCFS));
    }

    @When("^I submit payment for the CFS$")
    public void i_submit_payment_for_the_CFS() throws Throwable {
        deviceDetails = deviceDetails.continueToPaymentAfterReviewFinished();
        deviceDetails = deviceDetails.submitPayment();
        deviceDetails = deviceDetails.finishPayment();
    }

    @When("^I add devices to NEWLY created CFS manufacturer with following data$")
    public void iAddDevicesToNewlyCreatedCFSManufacturerWithFollowingData(Map<String, String> dataSets) throws Throwable {
        String registeredStatus = (String) scenarioSession.getData(SessionKey.registeredStatus);
        //Its not registered
        cfsAddDevices = new CFSAddDevices(driver);

        //Assumes we are in add device page
        DeviceDO dd = TestHarnessUtils.updateDeviceData(dataSets, scenarioSession);
        if (registeredStatus != null && registeredStatus.toLowerCase().equals("registered"))
            cfsAddDevices = cfsAddDevices.addFollowingDevice(dd, true);
        else {
            cfsAddDevices = cfsManufacturerList.clickContinue();
            cfsAddDevices = cfsAddDevices.addFollowingDevice(dd, false);
        }

        scenarioSession.putData(SessionKey.deviceData, dd);
    }

    @When("^I add a device to SELECTED CFS manufacturer with following data$")
    public void i_add_a_device_to_selected_manufactuerer_of_type_with_following_data(Map<String, String> dataSets) throws Throwable {

        //If registered we need to click on a button, else devices page is displayed
        String registeredStatus = (String) scenarioSession.getData(SessionKey.registeredStatus);
        //cfsAddDevices = manufacturerDetails.gotoAddDevicesPage(registeredStatus);

        //Assumes we are in add device page
        DeviceDO dd = TestHarnessUtils.updateDeviceData(dataSets, scenarioSession);
        if (registeredStatus != null && registeredStatus.toLowerCase().equals("registered"))
            cfsAddDevices = cfsAddDevices.addFollowingDevice(dd, true);
        else {
            cfsAddDevices = cfsManufacturerList.clickContinue();
            cfsAddDevices = cfsAddDevices.addFollowingDevice(dd, false);
        }

        StepsUtils.addToListOfStrings(scenarioSession, SessionKey.listOfGmndsAdded, AddDevices.gmdnSelected);
        scenarioSession.putData(SessionKey.deviceData, dd);
        StepsUtils.addToDeviceDataList(scenarioSession, dd);
    }

    @When("^I try to add a device to SELECTED CFS manufacturer with following data$")
    public void i_try_to_add_a_device_to_SELECTED_CFS_manufacturer_with_following_data(Map<String, String> dataSets) throws Throwable {
        DeviceDO dd = TestHarnessUtils.updateDeviceData(dataSets, scenarioSession);
        String registeredStatus = (String) scenarioSession.getData(SessionKey.registeredStatus);
        if (registeredStatus != null && registeredStatus.toLowerCase().equals("registered"))
            cfsAddDevices = cfsAddDevices.addPartiallyFilledDevices(dd);
        else {
            cfsAddDevices = cfsManufacturerList.clickContinue();
            cfsAddDevices = cfsAddDevices.addPartiallyFilledDevices(dd);
        }
    }


    @When("^I add another device to SELECTED CFS manufacturer with following data$")
    public void i_add_another_device_to_selected_manufactuerer_of_type_with_following_data(Map<String, String> dataSets) throws Throwable {

        String registeredStatus = (String) scenarioSession.getData(SessionKey.registeredStatus);

        //Go and add another device
        cfsAddDevices = cfsAddDevices.addAnotherDevice();

        //Assumes we are in add device page
        DeviceDO dd = TestHarnessUtils.updateDeviceData(dataSets, scenarioSession);
        dd.setAnotherCertificate(true);
        if (registeredStatus != null && registeredStatus.toLowerCase().equals("registered"))
            cfsAddDevices = cfsAddDevices.addFollowingDevice(dd, true);
        else {
            cfsAddDevices = cfsManufacturerList.clickContinue();
            cfsAddDevices = cfsAddDevices.addFollowingDevice(dd, false);
        }

        StepsUtils.addToListOfStrings(scenarioSession, SessionKey.listOfGmndsAdded, AddDevices.gmdnSelected);
        StepsUtils.addToListOfStrings(scenarioSession, SessionKey.listOfProductsAdded, dd.listOfProductName);
        StepsUtils.addToDeviceDataList(scenarioSession, dd);
        scenarioSession.putData(SessionKey.deviceData, dd);
    }


    @When("^I click on the back button$")
    public void i_click_on_the_back_button() throws Throwable {
        boolean isInCorrectPage = createNewCFSManufacturer.isInProvideManufacturerDetailsPage();
        createNewCFSManufacturer = createNewCFSManufacturer.clickBackButton();
    }

    @Then("^I should see an alert box asking for confirmation$")
    public void i_should_see_an_alert_box_asking_for_confirmation() throws Throwable {
        //Alert box should appear asking for confirmation
        boolean isAlertBoxPresent = createNewCFSManufacturer.isAlertBoxPresent();
        Assert.assertEquals("Expected to see an alert box asking for confirmation", isAlertBoxPresent, true);
    }

    @When("^I click \"([^\"]*)\" on the alert box$")
    public void i_click_on_the_alert_box(String clickYes) throws Throwable {

        if(clickYes.toLowerCase().equals("yes")){
            cfsManufacturerList = createNewCFSManufacturer.clickAlertButtonYes();
        }else{
            createNewCFSManufacturer = createNewCFSManufacturer.clickAlertButtonNo();
        }
    }

    @Then("^I should see the following \"([^\"]*)\" error message$")
    public void i_should_see_the_following_error_message(String errorMessage) throws Throwable {
        boolean errorMessageDisplayed = cfsAddDevices.isErrorMessageDisplayed(errorMessage);
        Assert.assertEquals("Expected error message : " + errorMessage, true, errorMessageDisplayed);
    }

    @When("^I submit the cfs application for approval$")
    public void i_submit_the_cfs_application_for_approval() throws Throwable {
        cfsManufacturerList = cfsAddDevices.submitApplicationForApproval();
        cfsManufacturerList.isManufacturerListDisplayed();
    }

    @Then("^Check the application reference number format is valid$")
    public void checkTheApplicationReferenceNumberFormatIsValid() throws Throwable {
        String dateFormat = "yyyyDDmm";
        List<String> invalidReferences = taskSection.isApplicationReferenceFormatCorrect(12,dateFormat);
        Assert.assertThat("Following references may not be correct : " + invalidReferences, invalidReferences.size() == 0, is(true));
    }


    @Then("^I should not be able to proceed to the next step$")
    public void i_should_not_be_able_to_proceed_to_the_next_step() throws Throwable {
        boolean isContinueBtnEnabled = cfsAddDevices.isContinueButtonEnabled();
        Assert.assertThat("Form not fully completed therefore the continue button should be disabled", isContinueBtnEnabled, is(false));
    }
}
