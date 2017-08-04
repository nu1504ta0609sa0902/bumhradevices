package com.mhra.mdcm.devices.appian.steps.d1.external;

import com.mhra.mdcm.devices.appian.domains.newaccounts.DeviceDO;
import com.mhra.mdcm.devices.appian.domains.newaccounts.ManufacturerRequestDO;
import com.mhra.mdcm.devices.appian.enums.LinksRecordPage;
import com.mhra.mdcm.devices.appian.pageobjects.external.cfs.CFSAddDevices;
import com.mhra.mdcm.devices.appian.pageobjects.external.device.AddDevices;
import com.mhra.mdcm.devices.appian.session.SessionKey;
import com.mhra.mdcm.devices.appian.steps.common.CommonSteps;
import com.mhra.mdcm.devices.appian.utils.selenium.others.RandomDataUtils;
import com.mhra.mdcm.devices.appian.utils.selenium.others.TestHarnessUtils;
import com.mhra.mdcm.devices.appian.utils.selenium.page.*;
import cucumber.api.PendingException;
import cucumber.api.java.en.And;
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

        //Identify which harness created the manufacturer
        String orgName = newAccount.organisationName;
        String manufacturerType = "CFS";
        newAccount.organisationName = manufacturerType + "_" + orgName;

        //Setup data : Real address required NOW
        newAccount.lastName = RandomDataUtils.getRandomTestNameStartingWith(newAccount.lastName, 5);
        newAccount.address1 = "46 Drayton Gardens";
        newAccount.townCity = "London";

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
        boolean isListVisible = cfsManufacturerList.isManufacturerListDisplayed();
        Assert.assertThat("Expected to see a list of manufacturers", isListVisible, is(true));
    }


    @Then("^I should see the following columns for CFS manufacturer list page$")
    public void i_should_see_the_following_columns(Map<String, String> dataValues) throws Throwable {
        String tableColumnsNotFound = dataValues.get("columns");
        boolean isCorrect = cfsManufacturerList.isTableHeadingCorrect(tableColumnsNotFound);
        Assert.assertThat("Following columns not found : " + tableColumnsNotFound, isCorrect, is(true));
    }

    @When("^I click on a random organisation which needs cfs$")
    public void i_click_on_a_random_organisation_which_needs_cfs() throws Throwable {
        String name = cfsManufacturerList.getARandomOrganisationName();
        manufacturerDetails = cfsManufacturerList.viewManufacturer(name);
        log.info("CFS Manufacturer selected : " + name);

        scenarioSession.putData(SessionKey.organisationName, name);
    }

    @When("^I click on a organisation name begins with \"([^\"]*)\" which needs cfs$")
    public void iClickOnAOrganisationNameBeginsWithWhichNeedsCfs(String orgName) throws Throwable {
        String name = cfsManufacturerList.getARandomOrganisationName(orgName);
        log.info("Manufacturer selected : " + name);
        manufacturerDetails = cfsManufacturerList.viewManufacturer(name);

        //CFS list displays only registered organisations
        scenarioSession.putData(SessionKey.organisationName, name);
        scenarioSession.putData(SessionKey.registeredStatus, "registered");
    }

    @When("^I enter cfs order for a country with following data without submitting$")
    public void i_order_cfs_for_a_country_with_following_data_without_submitting(Map<String, String> dataSets) throws Throwable {
        //Data
        String countryName = dataSets.get("countryName");
        String noOfCFS = dataSets.get("noOfCFS");

        //Order CFS for a random device
        deviceDetails = manufacturerDetails.clickOrderCFSButton();
        //deviceDetails = deviceDetails.selectAGMDNTerm("Blood weighing");
        deviceDetails = deviceDetails.selectARandomGMDNTerm();
        deviceDetails = deviceDetails.selectADevices();
        deviceDetails = deviceDetails.enterACertificateDetails(countryName, noOfCFS, false);

        //Store data to be verified later
        scenarioSession.putData(SessionKey.organisationCountry, countryName);
        scenarioSession.putData(SessionKey.numberOfCertificates, noOfCFS);
        scenarioSession.putData(SessionKey.taskType, "CFS Order");
    }

    @When("^I order cfs for a country with following data$")
    public void i_order_cfs_for_a_country_with_following_data(Map<String, String> dataSets) throws Throwable {
        //Data
        String countryName = dataSets.get("countryName");
        String noOfCFS = dataSets.get("noOfCFS");

        //Order CFS for a random device
        deviceDetails = manufacturerDetails.clickOrderCFSButton();
        //deviceDetails = deviceDetails.selectAGMDNTerm("Blood weighing");
        deviceDetails = deviceDetails.selectARandomGMDNTerm();
        deviceDetails = deviceDetails.selectADevices();
        deviceDetails = deviceDetails.enterACertificateDetails(countryName, noOfCFS, true);

        //Store data to be verified later
        scenarioSession.putData(SessionKey.organisationCountry, countryName);
        scenarioSession.putData(SessionKey.numberOfCertificates, noOfCFS);
        scenarioSession.putData(SessionKey.taskType, "CFS Order");
    }


    @When("^I search for product by \"([^\"]*)\" for the value \"([^\"]*)\"$")
    public void i_search_for_product_by_for_the_value(String searchBy, String searchTerm) throws Throwable {
        if (searchTerm.equals("random")) {
            searchTerm = "Product";
        }

        //Order CFS for a random device
        deviceDetails = manufacturerDetails.clickOrderCFSButton();

        boolean atLeast1DeviceFound = false;
        int count = 1;
        do {
            if (searchBy.toLowerCase().contains("medical device name")) {
                deviceDetails = deviceDetails.searchByMedicalDeviceName(searchTerm);
            } else if (searchBy.toLowerCase().contains("gmdn term")) {
                deviceDetails = deviceDetails.selectARandomGMDNTerm(searchTerm);
            } else if (searchBy.toLowerCase().contains("device type")) {
                //deviceDetails = deviceDetails.selectARandomGMDNTerm();
            }
            atLeast1DeviceFound = deviceDetails.isDeviceFound();
            if (count <= 2 && !atLeast1DeviceFound) {
                searchTerm = "Product";
                count++;
            }
        } while (!atLeast1DeviceFound);

        //Store data to be verified later
        scenarioSession.putData(SessionKey.searchTerm, searchTerm);
    }

    @When("^I search by \"([^\"]*)\" for the value \"([^\"]*)\" and order cfs for a country with following data$")
    public void i_order_cfs_for_a_country_with_following_data(String searchBy, String searchTerm, Map<String, String> dataSets) throws Throwable {
        if (searchTerm.equals("random")) {
            searchTerm = "Ford";
        }
        //Data
        String countryName = dataSets.get("countryName");
        String noOfCFS = dataSets.get("noOfCFS");

        //Order CFS for a random device
        deviceDetails = manufacturerDetails.clickOrderCFSButton();

        boolean atLeast1DeviceFound = false;
        int count = 1;
        do {
            if (searchBy.toLowerCase().contains("medical device name")) {
                deviceDetails = deviceDetails.searchByMedicalDeviceName(searchTerm);
            } else if (searchBy.toLowerCase().contains("gmdn term")) {
                deviceDetails = deviceDetails.selectARandomGMDNTerm();
            } else if (searchBy.toLowerCase().contains("device type")) {
                //deviceDetails = deviceDetails.selectARandomGMDNTerm();
            }
            atLeast1DeviceFound = deviceDetails.isDeviceFound();
            if (count <= 2 && !atLeast1DeviceFound) {
                searchTerm = "Product";
                count++;
            }
        } while (!atLeast1DeviceFound);

        deviceDetails = deviceDetails.selectADevices();
        deviceDetails = deviceDetails.enterACertificateDetails(countryName, noOfCFS, true);

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
        deviceDetails = manufacturerDetails.clickOrderCFSButton();
        deviceDetails = deviceDetails.selectARandomGMDNTerm();
        deviceDetails = deviceDetails.selectADevices();

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
        deviceDetails = deviceDetails.clickEditButton();
        new PendingException();
    }

    @Then("^I should see the correct number of certificates \"([^\"]*)\" in cfs order review page$")
    public void i_should_see_the_correct_number_of_certificates_in_review_page(String number) throws Throwable {
        boolean isNumberOfCertificatesCorrect = deviceDetails.isNumberOfCertificatesCorrect(number);
        Assert.assertThat("Expected number of certifictes : " + number, isNumberOfCertificatesCorrect, is(true));
    }


    @Then("^I should see the correct details in cfs order review page$")
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

    @Then("^I should see correct details for all the countries and certificate in cfs order review page$")
    public void i_should_multiple_country_details_in_cfs_review_page() throws Throwable {
        String[] data = (String[]) scenarioSession.getData(SessionKey.listOfCFSCountryPairs);
        String name = (String) scenarioSession.getData(SessionKey.organisationName);

        //Verify data number of cfs and country and name
        boolean isManufacturerNameCorrect = deviceDetails.isManufacturerNameCorrect(name);
        //boolean isListOfCertificateCountCorrect = deviceDetails.areTheCertificateCountCorrect(data);
        boolean isTotalNumberOfCertCorrect = deviceDetails.isTotalNumberOfCertificatesCorrect(data);

        int totalCostOfCerts = CommonUtils.calculateTotalCost(data);
        boolean isTotalCostOfCertificateCorrect = deviceDetails.isTotalCostOfCertificatesCorrect(totalCostOfCerts);

        Assert.assertThat("Expected manufacturer name to be : " + name, isManufacturerNameCorrect, is(true));
        Assert.assertThat("Total number of certificates may not be correct : " + data, isTotalNumberOfCertCorrect, is(true));
        Assert.assertThat("Expected total cost of certificates : " + totalCostOfCerts, isTotalCostOfCertificateCorrect, is(true));
        //Assert.assertThat("Expected to see following countries and asssociated CFS count : " + data, isListOfCertificateCountCorrect, is(true));

    }


    @Then("^I edit the list of devices added for CFS$")
    public void i_edit_the_list_of_devices_added_for_CFS() throws Throwable {
        deviceDetails = deviceDetails.clickEditButton();

        //Complete editing of devices, at the moment I only have 1 device
        //deviceDetails = deviceDetails.editDevicesAddedForCFS();
        deviceDetails.clickContinueButton();
    }


    @When("^I update the country added for CFS to \"([^\"]*)\"$")
    public void i_edit_the_country_added_for_CFS_to(String countryName) throws Throwable {
        deviceDetails = deviceDetails.clickEditButton();
        deviceDetails = deviceDetails.updateCountryNumber(1, countryName);
        deviceDetails = deviceDetails.clickContinueButton();
        scenarioSession.putData(SessionKey.organisationCountry, countryName);
    }

    @When("^I update the no of certificates for CFS to (\\d+)$")
    public void i_edit_the_country_added_for_CFS_to(int numberOfCFS) throws Throwable {
        deviceDetails = deviceDetails.clickEditButton();
        deviceDetails = deviceDetails.updateNumberOfCFS(1, String.valueOf(numberOfCFS));
        deviceDetails = deviceDetails.clickContinueButton();
        scenarioSession.putData(SessionKey.numberOfCertificates, String.valueOf(numberOfCFS));
    }

    @When("^I update the country to \"([^\"]*)\" and number of certificates to (\\d+)$")
    public void i_edit_the_country_to_and_the_number_of_certificates_to(String countryName, int numberOfCFS) throws Throwable {
        deviceDetails = deviceDetails.clickEditButton();
        deviceDetails = deviceDetails.updateCountryNumber(1, countryName);
        deviceDetails = deviceDetails.updateNumberOfCFS(1, String.valueOf(numberOfCFS));
        deviceDetails = deviceDetails.clickContinueButton();
        scenarioSession.putData(SessionKey.organisationCountry, countryName);
        scenarioSession.putData(SessionKey.numberOfCertificates, String.valueOf(numberOfCFS));
    }

    @When("^I submit payment for the CFS$")
    public void i_submit_payment_for_the_CFS() throws Throwable {
        String method = "Worldpay";
        deviceDetails = deviceDetails.agreeToTermsAndConditions();
        paymentDetails = deviceDetails.continueToPaymentAfterReviewFinished();
        paymentDetails = paymentDetails.enterPaymentDetails(method, scenarioSession);   //OR WorldPay
        String reference = paymentDetails.getApplicationReferenceNumber();
        log.info("New Application reference number : " + reference);

        //deviceDetails = deviceDetails.finishPayment();
        scenarioSession.putData(SessionKey.newApplicationReferenceNumber, reference);
        scenarioSession.putData(SessionKey.paymentMethod, method);
    }


    @And("^I submit payment via \"([^\"]*)\" and confirm submit device details$")
    public void proceedToPaymentAndConfirmSubmitDeviceDetails(String method) throws Throwable {
        paymentDetails = deviceDetails.continueToPaymentAfterReviewFinished();
        paymentDetails = paymentDetails.enterPaymentDetails(method, scenarioSession);   //OR WorldPay
        String reference = paymentDetails.getApplicationReferenceNumber();
        log.info("New Application reference number : " + reference);

        deviceDetails = deviceDetails.finishPayment();
        scenarioSession.putData(SessionKey.newApplicationReferenceNumber, reference);
        scenarioSession.putData(SessionKey.paymentMethod, method);
    }

    @When("^I save cfs order application for later$")
    public void i_save_cfs_application_for_later() throws Throwable {
        manufacturerDetails = deviceDetails.saveAndExitCFSOrderApplication();
        String reference = RandomDataUtils.getTempReference("TEMP", "");
        scenarioSession.putData(SessionKey.temporaryReference, reference);
    }

    @When("^I add devices to NEWLY created CFS manufacturer with following data$")
    public void iAddDevicesToNewlyCreatedCFSManufacturerWithFollowingData(Map<String, String> dataSets) throws Throwable {
        String registeredStatus = (String) scenarioSession.getData(SessionKey.registeredStatus);
        //Its not registered
        cfsAddDevices = new CFSAddDevices(driver);

        //Assumes we are in add device page
        DeviceDO dd = TestHarnessUtils.updateDeviceData(dataSets, scenarioSession);
        if (registeredStatus != null && registeredStatus.toLowerCase().equals("registered")) {
            cfsAddDevices = manufacturerDetails.clickAddDeviceCFS();
            cfsAddDevices = cfsAddDevices.addFollowingDevice(dd, true);
        } else {
            cfsAddDevices = manufacturerDetails.clickContinue();
            cfsAddDevices = cfsAddDevices.addFollowingDevice(dd, false);
        }

        StepsUtils.addToListOfStrings(scenarioSession, SessionKey.listOfGmndsAdded, AddDevices.gmdnSelected);
        StepsUtils.addToListOfStrings(scenarioSession, SessionKey.listOfProductsAdded, dd.listOfProductName);
        StepsUtils.addToDeviceDataList(scenarioSession, dd);
        StepsUtils.addCertificatesToAllCertificateList(scenarioSession, dd);
        scenarioSession.putData(SessionKey.deviceData, dd);
    }

    @When("^I add a device to SELECTED CFS manufacturer with following data$")
    public void i_add_a_device_to_selected_manufactuerer_of_type_with_following_data(Map<String, String> dataSets) throws Throwable {

        //If registered we need to click on a button, else devices page is displayed
        String registeredStatus = (String) scenarioSession.getData(SessionKey.registeredStatus);

        //Assumes we are in add device page
        DeviceDO dd = TestHarnessUtils.updateDeviceData(dataSets, scenarioSession);
        if (registeredStatus != null && registeredStatus.toLowerCase().equals("registered")) {
            cfsAddDevices = manufacturerDetails.clickAddDeviceCFS();
            cfsAddDevices = cfsAddDevices.addFollowingDevice(dd, true);
        } else {
            cfsAddDevices = manufacturerDetails.clickContinue();
            cfsAddDevices = cfsAddDevices.addFollowingDevice(dd, false);
        }

        StepsUtils.addToListOfStrings(scenarioSession, SessionKey.listOfGmndsAdded, AddDevices.gmdnSelected);
        scenarioSession.putData(SessionKey.deviceData, dd);
        StepsUtils.addToDeviceDataList(scenarioSession, dd);
    }


    @Then("^I click on add devices button for CFS$")
    public void i_go_to_add_devices_page() throws Throwable {
        cfsAddDevices = manufacturerDetails.clickAddDeviceCFS();
    }

    @When("^I try to add a device to SELECTED CFS manufacturer with following data$")
    public void i_try_to_add_a_device_to_SELECTED_CFS_manufacturer_with_following_data(Map<String, String> dataSets) throws Throwable {
        DeviceDO dd = TestHarnessUtils.updateDeviceData(dataSets, scenarioSession);
        String registeredStatus = (String) scenarioSession.getData(SessionKey.registeredStatus);
        if (registeredStatus != null && registeredStatus.toLowerCase().equals("registered")) {
            cfsAddDevices = manufacturerDetails.clickAddDeviceCFS();
            cfsAddDevices = cfsAddDevices.addPartiallyFilledDevices(dd);
        }else {
            cfsAddDevices = manufacturerDetails.clickContinue();
            cfsAddDevices = cfsAddDevices.addPartiallyFilledDevices(dd);
        }
    }



    @When("^I select a specific device for CFS manufacturer with following data$")
    public void i_select_a_specific_device_for_CFS_manufacturer_with_following_data(Map<String, String> dataSets) throws Throwable {
        DeviceDO dd = TestHarnessUtils.updateDeviceData(dataSets, scenarioSession);
        String registeredStatus = (String) scenarioSession.getData(SessionKey.registeredStatus);

        //Go and add another device
        cfsAddDevices = cfsAddDevices.addAnotherDevice();

        if (registeredStatus != null && registeredStatus.toLowerCase().equals("registered"))
            cfsAddDevices = cfsAddDevices.selectADeviceWithoutAddingOtherDetails(dd);
        else {
            cfsAddDevices = manufacturerDetails.clickContinue();
            cfsAddDevices = cfsAddDevices.selectADeviceWithoutAddingOtherDetails(dd);
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
        if (registeredStatus != null && registeredStatus.toLowerCase().equals("registered")) {
            cfsAddDevices = cfsAddDevices.addFollowingDevice(dd, true);
        } else {
            //cfsAddDevices = manufacturerDetails.clickContinue();
            cfsAddDevices = cfsAddDevices.addFollowingDevice(dd, false);
        }

        StepsUtils.addToListOfStrings(scenarioSession, SessionKey.listOfGmndsAdded, AddDevices.gmdnSelected);
        StepsUtils.addToListOfStrings(scenarioSession, SessionKey.listOfProductsAdded, dd.listOfProductName);
        StepsUtils.addToDeviceDataList(scenarioSession, dd);
        StepsUtils.addCertificatesToAllCertificateList(scenarioSession, dd);
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

        if (clickYes.toLowerCase().equals("yes")) {
            cfsManufacturerList = createNewCFSManufacturer.clickAlertButtonYes();
        } else {
            createNewCFSManufacturer = createNewCFSManufacturer.clickAlertButtonNo();
        }
    }

    @Then("^I should see the following \"([^\"]*)\" error message$")
    public void i_should_see_the_following_error_message(String errorMessage) throws Throwable {
        boolean errorMessageDisplayed = cfsAddDevices.isErrorMessageDisplayed(errorMessage);
        Assert.assertEquals("Expected error message : " + errorMessage, true, errorMessageDisplayed);
    }

    @Then("^I should see the following MHRA error message \"([^\"]*)\"$")
    public void i_should_see_the_following_MHRA_error_message(String errorMessage) throws Throwable {
        boolean errorMessageDisplayed = cfsAddDevices.isMHRAErrorMessageCorrect(errorMessage);
        Assert.assertEquals("Expected error message : " + errorMessage, true, errorMessageDisplayed);
    }

    @Then("^I should see the following field \"([^\"]*)\" error message$")
    public void i_should_see_the_following_field_error_message(String errorMessage) throws Throwable {
        boolean errorMessageDisplayed = cfsAddDevices.isFieldErrorMessageDisplayed(errorMessage);
        Assert.assertEquals("Expected field error message : " + errorMessage, true, errorMessageDisplayed);
    }

    @When("^I submit the cfs application for approval$")
    public void i_submit_the_cfs_application_for_approval() throws Throwable {
        cfsAddDevices = cfsAddDevices.agreeToTandC();
        cfsManufacturerList = cfsAddDevices.submitApplicationForApproval();
        //cfsManufacturerList.isManufacturerListDisplayed();
        String reference = cfsAddDevices.getApplicationReferenceNumber();
        log.info("New Application reference number : " + reference);
        scenarioSession.putData(SessionKey.newApplicationReferenceNumber, reference);
    }

    @Then("^Check the application reference number format is valid$")
    public void checkTheApplicationReferenceNumberFormatIsValid() throws Throwable {
        String dateFormat = "yyyyDDmm";
        List<String> invalidReferences = taskSection.isApplicationReferenceFormatCorrect(15, dateFormat);
        Assert.assertThat("Following references may not be correct : " + invalidReferences, invalidReferences.size() == 0, is(true));
    }


    @When("^I remove the attached certificate$")
    public void i_remove_the_attached_certificate() throws Throwable {
        cfsAddDevices = cfsAddDevices.removeAttachedCertificate();
    }

    @When("^I remove the attached product$")
    public void i_remove_the_attached_product() throws Throwable {
        cfsAddDevices = cfsAddDevices.removeAddedDevice();
    }



    @Then("^I should not be able to proceed to the next step$")
    public void i_should_not_be_able_to_proceed_to_the_next_step() throws Throwable {
        boolean isContinueBtnEnabled = cfsAddDevices.isContinueButtonEnabled();
        Assert.assertThat("Form not fully completed therefore the continue button should be disabled", isContinueBtnEnabled, is(false));
    }


    @Then("^I should see correct device data in the review page$")
    public void i_should_see_correct_device_data_in_the_review_page() throws Throwable {
        List<DeviceDO> listOfDeviceDataObjects = (List<DeviceDO>) scenarioSession.getData(SessionKey.listOfDeviceDO);
        boolean isListOfDeviceCorrect = cfsAddDevices.isReviewPageShowingCorrectNumberOfDevices(listOfDeviceDataObjects.size());
        boolean isListOfDeviceNamesCorrect = cfsAddDevices.isReviewPageShowingCorrectDeviceNames(listOfDeviceDataObjects);
        boolean isDeviceDetailsCorrect = cfsAddDevices.isDeviceDetailsCorrect(listOfDeviceDataObjects);
        boolean isCECertificateCorrect = cfsAddDevices.isCECerficatesCorrect(listOfDeviceDataObjects);
        boolean isProductDetailsCorrect = cfsAddDevices.isProductDetailsCorrect(listOfDeviceDataObjects);
        Assert.assertThat("Expected to see " + listOfDeviceDataObjects.size() + " devices", isListOfDeviceCorrect, is(true));
    }

    @When("^I remove device called \"([^\"]*)\" from list of devices$")
    public void i_remove_device_called_from_list_of_devices(String deviceName) throws Throwable {
        cfsAddDevices = cfsAddDevices.removeDevice(deviceName);
        StepsUtils.removeFromDeviceDataList(scenarioSession, deviceName);
    }

    @When("^I go back to the CE certificates page$")
    public void iGoBackToTheCECertificatesPage() throws Throwable {
        //Go to products page
        cfsAddDevices = cfsAddDevices.clickBackButton();
        boolean isInProductsPage = cfsAddDevices.isInProductsPage();
        Assert.assertThat("Expected to be in ADD PRODUCTS page", isInProductsPage, is(true));

        //Go back to CE certificate page
        cfsAddDevices = cfsAddDevices.clickBackButton();
        boolean isInCertificatesPage = cfsAddDevices.isInCertificatesPage();
        Assert.assertThat("Expected to be in ADD CERTIFICATES page", isInCertificatesPage, is(true));

    }

    @Then("^I should see all the certificates previously uploaded$")
    public void iShouldSeeAllTheCertificatesPreviouslyUploaded() throws Throwable {
        List<String> certs = (List<String>) scenarioSession.getData(SessionKey.listOfAllCertificatesAddedToApplication);
        boolean isCertsVisible = cfsAddDevices.isCECertificateCorrect(certs);
        Assert.assertThat("Expected to see following certificates : " + certs, isCertsVisible, is(true));
    }

    @When("^I search for registered manufacturer \"([^\"]*)\"$")
    public void iSearchForRegisteredManufacturer(String searchTerm) throws Throwable {
        if (searchTerm == null || searchTerm.equals(""))
            searchTerm = (String) scenarioSession.getData(SessionKey.organisationName);
        cfsManufacturerList = cfsManufacturerList.searchForManufacturer(searchTerm);
    }

    @Then("^I should see (\\d+) products matching search results$")
    public void i_should_see_products_matching_search_results(int expectedCount) throws Throwable {
        boolean isCorrect = deviceDetails.isNumberOfProductsDisplayedCorrect(expectedCount);
        Assert.assertThat("Expected number of products to be : " + expectedCount, isCorrect, is(true));
    }

    @Then("^I should see application tab showing my application with correct details$")
    public void iShouldSeeApplicatonTabShowingMyApplicationWithCorrectDetails() throws Throwable {
        String reference = (String) scenarioSession.getData(SessionKey.temporaryReference);
        reference = reference.substring(0, reference.length()-1);

        //Go to applications tab and verify details are correct
        manufacturerDetails = manufacturerDetails.clickApplicationTab();
        boolean isRefVisible = manufacturerDetails.isApplicationReferenceVisible(reference);
        boolean isStatusCorrect = manufacturerDetails.isApplicaitonStatusCorrect("Draft");
        boolean isDateCorrect = manufacturerDetails.isApplicationDateCorrect();
        Assert.assertThat("Expected to see entry with reference : " + reference, isRefVisible, is(true));
        Assert.assertThat("Expected status : Draft " + reference, isStatusCorrect, is(true));
        Assert.assertThat("Expected date formatted DD/MM/YYYY " + reference, isDateCorrect, is(true));

        //boolean isApplicationTypeCorrect = manufacturerDetails.isApplicationTypeCorrect("CFS New Order");
        //Assert.assertThat("Expected application type : CFS New Order" + reference, isApplicationTypeCorrect, is(true));
    }

    @When("^I save cfs new manufacturer application for later$")
    public void iSaveCfsNewManufacturerApplicationForLater() throws Throwable {
        manufacturerDetails = cfsAddDevices.saveAndExitNewCFSManufacturerApplication();
        String reference = RandomDataUtils.getTempReference("TEMP", "");
        scenarioSession.putData(SessionKey.temporaryReference, reference);
    }

    @When("^I search and view for the newly created cfs manufacturer$")
    public void iSearchAndViewForTheNewlyCreatedCfsManufacturer() throws Throwable {
        String searchTerm = (String) scenarioSession.getData(SessionKey.organisationName);
        cfsManufacturerList = cfsManufacturerList.searchForManufacturer(searchTerm);

        //Search result should only return 1 match
        String name = cfsManufacturerList.getARandomOrganisationName();
        manufacturerDetails = cfsManufacturerList.viewManufacturer(name);
    }

    @Then("^I should see the correct addresses displayed$")
    public void iShouldSeeTheCorrectAddressesDisplayed() throws Throwable {
        boolean isRAVisible = deviceDetails.isAddressToBePrintedOnCFSVisible();
        boolean isDeliveryAddVisible = deviceDetails.isDeliveryAddressVisible();
        Assert.assertThat("Printed address should be displayed", isRAVisible, is(true));
        Assert.assertThat("Delivery address should be displayed", isDeliveryAddVisible, is(true));

        //These below need to be verified when story 5959 is updated
        boolean isSAVisible = deviceDetails.isSiteAddressVisible();
        boolean isARAVisible = deviceDetails.isAuthorisedRepAddressVisible();
        boolean isDAVisible = deviceDetails.isDistributorAddressVisible();
    }
}
