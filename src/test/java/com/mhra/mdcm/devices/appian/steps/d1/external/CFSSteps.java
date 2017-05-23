package com.mhra.mdcm.devices.appian.steps.d1.external;

import com.mhra.mdcm.devices.appian.domains.newaccounts.DeviceDO;
import com.mhra.mdcm.devices.appian.domains.newaccounts.ManufacturerRequestDO;
import com.mhra.mdcm.devices.appian.pageobjects.MainNavigationBar;
import com.mhra.mdcm.devices.appian.pageobjects.external.device.AddDevices;
import com.mhra.mdcm.devices.appian.session.SessionKey;
import com.mhra.mdcm.devices.appian.steps.common.CommonSteps;
import com.mhra.mdcm.devices.appian.utils.selenium.others.RandomDataUtils;
import com.mhra.mdcm.devices.appian.utils.selenium.others.TestHarnessUtils;
import com.mhra.mdcm.devices.appian.utils.selenium.page.AssertUtils;
import com.mhra.mdcm.devices.appian.utils.selenium.page.PageUtils;
import com.mhra.mdcm.devices.appian.utils.selenium.page.StepsUtils;
import com.mhra.mdcm.devices.appian.utils.selenium.page.WaitUtils;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.hamcrest.Matchers;
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
        Assert.assertThat("Expected number of certifictes : " + numberOfCFS, isNumberOfCertificatesCorrect, is(true));
        Assert.assertThat("Expected manufacturer name to be : " + name, isManufacturerNameCorrect, is(true));
        Assert.assertThat("Expected manufacturer name to be : " + name, isManufacturerNameCorrect, is(true));

    }

    @Then("^I should see multiple country details in cfs review page$")
    public void i_should_multiple_country_details_in_cfs_review_page() throws Throwable {
        String[] data = (String[] )scenarioSession.getData(SessionKey.listOfCFSCountryPairs);
        String name = (String) scenarioSession.getData(SessionKey.organisationName);

        //Verify data number of cfs and country and name
        boolean isManufacturerNameCorrect = deviceDetails.isManufacturerNameCorrect(name);
        //boolean isListOfCountriesCorrect = deviceDetails.areTheCountriesDisplayedCorrect(data);
        boolean isListOfCertificateCountCorrect = deviceDetails.areTheCertificateCountCorrect(data);
        boolean isTotalNumberOfCertCorrect = deviceDetails.isTotalNumberOfCertificatesCorrect(data);
        Assert.assertThat("Expected to see following countries and asssoficated CFS count : " + data, isListOfCertificateCountCorrect, is(true));
        Assert.assertThat("Expected manufacturer name to be : " + name, isManufacturerNameCorrect, is(true));
        Assert.assertThat("Total number of certificates may not be correct : " + data, isTotalNumberOfCertCorrect, is(true));

    }


    @Then("^I edit the list of devices added for CFS$")
    public void i_edit_the_list_of_devices_added_for_CFS() throws Throwable {
        deviceDetails = deviceDetails.clickEditDevicesLink();

        //Complete editing of devices, at the moment I only have 1 device
        //deviceDetails = deviceDetails.editDevicesAddedForCFS();
        deviceDetails.clickContinueButton();
        deviceDetails.clickContinueButton();
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
        addDevices = new AddDevices(driver);

        //Assumes we are in add device page
        DeviceDO dd = TestHarnessUtils.updateDeviceData(dataSets, scenarioSession);
        if (registeredStatus != null && registeredStatus.toLowerCase().equals("registered"))
            addDevices = addDevices.addFollowingDevice(dd, true);
        else
            addDevices = addDevices.addFollowingDevice(dd, false);

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
}
