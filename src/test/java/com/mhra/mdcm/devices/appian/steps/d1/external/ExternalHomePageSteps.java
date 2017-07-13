package com.mhra.mdcm.devices.appian.steps.d1.external;

import com.mhra.mdcm.devices.appian.domains.newaccounts.ManufacturerRequestDO;
import com.mhra.mdcm.devices.appian.domains.newaccounts.DeviceDO;
import com.mhra.mdcm.devices.appian.pageobjects.MainNavigationBar;
import com.mhra.mdcm.devices.appian.pageobjects._Page;
import com.mhra.mdcm.devices.appian.pageobjects.external.device.AddDevices;
import com.mhra.mdcm.devices.appian.session.SessionKey;
import com.mhra.mdcm.devices.appian.steps.common.CommonSteps;
import com.mhra.mdcm.devices.appian.utils.selenium.others.RandomDataUtils;
import com.mhra.mdcm.devices.appian.utils.selenium.page.StepsUtils;
import com.mhra.mdcm.devices.appian.utils.selenium.others.TestHarnessUtils;
import com.mhra.mdcm.devices.appian.utils.selenium.page.AssertUtils;
import com.mhra.mdcm.devices.appian.utils.selenium.page.PageUtils;
import com.mhra.mdcm.devices.appian.utils.selenium.page.WaitUtils;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.springframework.context.annotation.Scope;

import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.is;

/**
 * Created by TPD_Auto
 */
@Scope("cucumber-glue")
public class ExternalHomePageSteps extends CommonSteps {


    @When("^I go to portal page$")
    public void gotoPortalPage() {
        mainNavigationBar = new MainNavigationBar(driver);
        externalHomePage = mainNavigationBar.clickExternalHOME();
    }

    @Then("^I should see the following portal \"([^\"]*)\" links$")
    public void i_should_see_the_following_links(String delimitedLinks) throws Throwable {
        externalHomePage = mainNavigationBar.clickExternalHOME();
        boolean areLinksVisible = externalHomePage.areLinksVisible(delimitedLinks);
        Assert.assertThat("Expected to see the following links : " + delimitedLinks, areLinksVisible, Matchers.is(true));
    }

    @And("^All the links \"([^\"]*)\" are clickable$")
    public void allTheAreClickable(String delimitedLinks) throws Throwable {
        externalHomePage = mainNavigationBar.clickExternalHOME();
        boolean areLinksClickable = externalHomePage.areLinksClickable(delimitedLinks);
        Assert.assertThat("Not all links are clickable : " + delimitedLinks, areLinksClickable, Matchers.is(true));
    }

    @And("^I go to list of manufacturers page$")
    public void iGoToManufacturerRegistrationPage() throws Throwable {
        manufacturerList = externalHomePage.gotoListOfManufacturerPage();
    }

    @And("^I go to device certificate of free sale page$")
    public void i_go_to_device_certificate_of_free_sale_page() throws Throwable {
        cfsManufacturerList = externalHomePage.gotoCFSPage();
        cfsManufacturerList = cfsManufacturerList.tellUsAboutYourOrganisation();
    }

    @Then("^I goto list of manufacturers page again$")
    public void iGotoListOfManufacturersPage() throws Throwable {
        externalHomePage = mainNavigationBar.clickExternalHOME();
        manufacturerList = externalHomePage.gotoListOfManufacturerPage();
    }

    @Then("^I should see the correct manufacturer details$")
    public void i_should_see_the_correct_manufacturer_details() throws Throwable {
        String name = (String) scenarioSession.getData(SessionKey.organisationName);
        if (name == null) {
            boolean isManufacturer = true;
            if (name.contains("thorised")) {
                isManufacturer = false;
            }
            boolean isCorrectOrgLoaded = manufacturerDetails.isOrganisationNameCorrect(name, isManufacturer);
            Assert.assertThat("Organisation Name Expected : " + name, isCorrectOrgLoaded, Matchers.is(true));
        }
    }

    @And("^I go to register my organisations page$")
    public void iGoToRegisterMyOrganisationPage() throws Throwable {
        manufacturerList = externalHomePage.gotoListOfManufacturerPage();
        createNewManufacturer = manufacturerList.registerMyOrganisation();
    }

    @And("^I go to register a new manufacturer page$")
    public void iGoToRegisterANewManufacturerPage() throws Throwable {
        manufacturerList = externalHomePage.gotoListOfManufacturerPage();
        createNewManufacturer = manufacturerList.registerNewManufacturer();
    }

    @And("^I click on register new manufacturer$")
    public void iGoToRegisterANewAuthorisedRepPage() throws Throwable {
        createNewManufacturer = manufacturerList.registerNewManufacturer();
    }


    @When("^I create a new manufacturer using manufacturer test harness page with following data$")
    public void i_create_a_new_manufacturer_using_test_harness_page_with_following_data(Map<String, String> dataSets) throws Throwable {

        ManufacturerRequestDO newAccount = TestHarnessUtils.updateManufacturerDefaultsWithData(dataSets, scenarioSession);
        log.info("New Manufacturer Account Requested With Following Data : \n" + newAccount);

        //Create new manufacturer data
        addDevices = createNewManufacturer.createTestOrganisation(newAccount);
        if (createNewManufacturer.isErrorMessageDisplayed()) {
            externalHomePage = mainNavigationBar.clickExternalHOME();
            manufacturerList = externalHomePage.gotoListOfManufacturerPage();
            createNewManufacturer = manufacturerList.registerNewManufacturer();
            addDevices = createNewManufacturer.createTestOrganisation(newAccount);
        }
        scenarioSession.putData(SessionKey.organisationName, newAccount.organisationName);
        scenarioSession.putData(SessionKey.manufacturerData, newAccount);
        scenarioSession.putData(SessionKey.taskType, "New Manufacturer");
    }




    @When("^I save progress without adding a new device$")
    public void i_save_without_adding_a_new_device() throws Throwable {
        //Not sure which page this should take us to : ManufacturerList or AddDevices
        addDevices = addDevices.save();
        PageUtils.acceptAlert(driver, true, 2);
    }


    @Then("^I should see the registered manufacturers list$")
    public void iShouldSeeTheManufacturersList() throws Throwable {
        String name = (String) scenarioSession.getData(SessionKey.organisationName);
        externalHomePage = mainNavigationBar.clickExternalHOME();
        manufacturerList = externalHomePage.gotoListOfManufacturerPage();
        boolean isCorrect = manufacturerList.isSpecificTableHeadingCorrect("Manufacturer name");
        Assert.assertThat("Expected To See Manufacturer List : " + name, isCorrect, Matchers.is(true));
    }



    @Then("^I should be returned to the manufacturers list page$")
    public void iShouldReturnedToTheManufacturersList() throws Throwable {
        String name = (String) scenarioSession.getData(SessionKey.organisationName);
        boolean isCorrect = manufacturerList.isSpecificTableHeadingCorrect("Manufacturer name");
        Assert.assertThat("Expected To See Manufacturer List : " + name, isCorrect, Matchers.is(true));
    }


    @Then("^I should see stored manufacturer appear in the registration in progress list$")
    public void i_should_see_stored_manufacturer_appear_in_the_registration_in_progress_list() throws Throwable {
        String name = (String) scenarioSession.getData(SessionKey.organisationName);

        //Check stored manufacturer appears in the registration in progress list
        boolean isFoundInManufacturerList = manufacturerList.isManufacturerLinkDisplayedOnInProgressTable(name);
        Assert.assertThat("Organisation Name Expected In Registration In Progress List : " + name, isFoundInManufacturerList, Matchers.is(true));
    }

    @Then("^I should see stored manufacturer appear in the manufacturers list$")
    public void iShouldSeeTheManufacturerAppearInTheManufacturersList() throws Throwable {
        externalHomePage = mainNavigationBar.clickExternalHOME();
        manufacturerList = externalHomePage.gotoListOfManufacturerPage();

        String name = (String) scenarioSession.getData(SessionKey.organisationName);
        int nop = manufacturerList.getNumberOfPages(1);
        boolean isFoundInManufacturerList = false;
        int count = 0;

        //Check in Manufacturers list
        do {
            count++;
            isFoundInManufacturerList = manufacturerList.isManufacturerDisplayedInList(name);
            if (!isFoundInManufacturerList && nop > 0) {
                manufacturerList = manufacturerList.clickNext();
            }
        } while (!isFoundInManufacturerList && count <= nop);

        if (!isFoundInManufacturerList) {
            //Check in registration in progress list
            externalHomePage = mainNavigationBar.clickExternalHOME();
            manufacturerList = externalHomePage.gotoListOfManufacturerPage();
            nop = manufacturerList.getNumberOfPages(2);
            count = 0;
            do {
                count++;
                isFoundInManufacturerList = manufacturerList.isRegistraionInProgressDisplayingManufacturer(name);
                if (!isFoundInManufacturerList && nop > 0) {
                    manufacturerList = manufacturerList.clickNext();
                } else {
                    manufacturerDetails = manufacturerList.viewAManufacturer(name);
                }
            } while (!isFoundInManufacturerList && count <= nop);
        }

        Assert.assertThat("Organisation Name Expected In Manufacturer List : " + name, isFoundInManufacturerList, Matchers.is(true));
    }


    @When("^I add devices to NEWLY created manufacturer with following data$")
    public void iAddDevicesToNewlyCreatedManufacturerWithFollowingData(Map<String, String> dataSets) throws Throwable {
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
        StepsUtils.addToDeviceDataList(scenarioSession, dd);
    }


    @And("^Proceed to payment and confirm submit device details$")
    public void proceedToPaymentAndConfirmSubmitDeviceDetails() throws Throwable {
        addDevices = addDevices.proceedToReview();
        addDevices = addDevices.proceedToPayment();
        addDevices = addDevices.enterPaymentDetails("Worldpay", scenarioSession);   //OR WorldPay
        String reference = addDevices.getApplicationReferenceNumber();
        log.info("New Applicaiton reference number : " + reference);
        //addDevices = addDevices.confirmPayment();
        manufacturerList = addDevices.backToService();
        scenarioSession.putData(SessionKey.newApplicationReferenceNumber, reference);
    }

    @And("^Proceed to payment via \"([^\"]*)\" and confirm submit device details$")
    public void proceedToPaymentAndConfirmSubmitDeviceDetails(String method) throws Throwable {
        addDevices = addDevices.proceedToReview();
        addDevices = addDevices.proceedToPayment();
        addDevices = addDevices.enterPaymentDetails(method, scenarioSession);   //OR WorldPay
        String reference = addDevices.getApplicationReferenceNumber();
        log.info("New Applicaiton reference number : " + reference);
        //addDevices = addDevices.confirmPayment();
        manufacturerList = addDevices.backToService();
        scenarioSession.putData(SessionKey.newApplicationReferenceNumber, reference);
    }


    @When("^I add a device to SELECTED manufacturer with following data$")
    public void i_add_a_device_to_selected_manufactuerer_of_type_with_following_data(Map<String, String> dataSets) throws Throwable {

        //If registered we need to click on a button, else devices page is displayed
        String registeredStatus = (String) scenarioSession.getData(SessionKey.registeredStatus);
        deviceDetails = manufacturerDetails.clickOnDevicesAndProductDetailsLink();
        deviceDetails = deviceDetails.clickManageDevices();
        addDevices = deviceDetails.clickAddDeviceBtn();

        //Assumes we are in add device page
        DeviceDO dd = TestHarnessUtils.updateDeviceData(dataSets, scenarioSession);
        if (registeredStatus != null && registeredStatus.toLowerCase().equals("registered"))
            addDevices = addDevices.addFollowingDevice(dd, true);
        else
            addDevices = addDevices.addFollowingDevice(dd, false);

        StepsUtils.addToListOfStrings(scenarioSession, SessionKey.listOfGmndsAdded, AddDevices.gmdnSelected);
        scenarioSession.putData(SessionKey.deviceData, dd);
        StepsUtils.addToDeviceDataList(scenarioSession, dd);
    }


    @When("^I try to add an incomplete device to SELECTED manufacturer with following data$")
    public void i_try_to_add_an_incomplete_device_to_selected_manufactuerer_of_type_with_following_data(Map<String, String> dataSets) throws Throwable {

        //If registered we need to click on a button, else devices page is displayed
        String registeredStatus = (String) scenarioSession.getData(SessionKey.registeredStatus);
        deviceDetails = manufacturerDetails.clickOnDevicesAndProductDetailsLink();
        deviceDetails = deviceDetails.clickManageDevices();
        addDevices = deviceDetails.clickAddDeviceBtn();

        //Assumes we are in add device page
        DeviceDO dd = TestHarnessUtils.updateDeviceData(dataSets, scenarioSession);
        addDevices = addDevices.addInvalidFollowingDevice(dd);

        StepsUtils.addToListOfStrings(scenarioSession, SessionKey.listOfGmndsAdded, AddDevices.gmdnSelected);
        scenarioSession.putData(SessionKey.deviceData, dd);
        StepsUtils.addToDeviceDataList(scenarioSession, dd);
    }

    @Then("^I should be prevented from adding the devices$")
    public void i_should_be_prevented_from_adding_the_devices() throws Throwable {
        //        addDevices = addDevices.addAnotherDevice();
        String registeredStatus = (String) scenarioSession.getData(SessionKey.registeredStatus);
        addDevices = manufacturerDetails.gotoAddDevicesPage(registeredStatus);

        boolean isErrorMessageDisplayed = addDevices.isValidationErrorMessageVisible();
        Assert.assertThat("Error message should still be displayed and user should be prevented going forward", isErrorMessageDisplayed, Matchers.is(true));
    }

    @Then("^I should be prevented from adding the high risk devices$")
    public void i_should_be_prevented_from_adding_the_aimd_devices() throws Throwable {
        boolean isAbleToSubmitForReview = addDevices.isAbleToSubmitForReview();
        Assert.assertThat("User should be prevented from proceeding to the next step", isAbleToSubmitForReview, Matchers.is(false));
    }

    @When("^I add multiple devices to SELECTED manufacturer with following data$")
    public void i_add_multiple_devices_to_selected_manufactuerer_of_type_with_following_data(Map<String, String> dataSets) throws Throwable {
        //If registered we need to click on a button, else devices page is displayed
        String registeredStatus = (String) scenarioSession.getData(SessionKey.registeredStatus);
        deviceDetails = manufacturerDetails.clickOnDevicesAndProductDetailsLink();
        deviceDetails = deviceDetails.clickManageDevices();
        addDevices = deviceDetails.clickAddDeviceBtn();

        DeviceDO dd = TestHarnessUtils.updateDeviceData(dataSets, scenarioSession);
        if (registeredStatus != null && registeredStatus.toLowerCase().equals("registered"))
            addDevices = addDevices.addFollowingDevice(dd, true);
        else
            addDevices = addDevices.addFollowingDevice(dd, false);

        StepsUtils.addToListOfStrings(scenarioSession, SessionKey.listOfGmndsAdded, AddDevices.gmdnSelected);
        StepsUtils.addToListOfStrings(scenarioSession, SessionKey.listOfProductsAdded, dd.listOfProductName);
        scenarioSession.putData(SessionKey.deviceData, dd);
    }

    @When("^I add another device to SELECTED manufacturer with following data$")
    public void i_add_another_device_to_selected_manufactuerer_of_type_with_following_data(Map<String, String> dataSets) throws Throwable {

        String registeredStatus = (String) scenarioSession.getData(SessionKey.registeredStatus);

        //Go and add another device
        addDevices = addDevices.addAnotherDevice();

        //Assumes we are in add device page
        DeviceDO dd = TestHarnessUtils.updateDeviceData(dataSets, scenarioSession);
        if (registeredStatus != null && registeredStatus.toLowerCase().equals("registered"))
            addDevices = addDevices.addFollowingDevice(dd, true);
        else
            addDevices = addDevices.addFollowingDevice(dd, false);

        StepsUtils.addToListOfStrings(scenarioSession, SessionKey.listOfGmndsAdded, AddDevices.gmdnSelected);
        StepsUtils.addToListOfStrings(scenarioSession, SessionKey.listOfProductsAdded, dd.listOfProductName);
        StepsUtils.addToDeviceDataList(scenarioSession, dd);
        scenarioSession.putData(SessionKey.deviceData, dd);
    }

    @Then("^I should see error message in devices page with text \"([^\"]*)\"$")
    public void i_should_see_error_message_in_devices_page_with_text(String message) throws Throwable {
        boolean errorMessageDisplayed = addDevices.isErrorMessageCorrect(message);
        Assert.assertThat("Expected error message to contain : " + message, errorMessageDisplayed, Matchers.is(true));
    }

    @Then("^I should see validation error message in devices page with text \"([^\"]*)\"$")
    public void i_should_see_validation_error_message_in_devices_page_with_text(String message) throws Throwable {
        boolean errorMessageDisplayed = addDevices.isValidationErrorMessageCorrect(message);
        Assert.assertThat("Expected to see validation error message to contain : " + message, errorMessageDisplayed, Matchers.is(true));
    }

    @Then("^I should see correct device types$")
    public void iShouldSeeCorrectDeviceTypes() throws Throwable {
        boolean isCorrect = addDevices.isDeviceTypeCorrect();
        Assert.assertThat("Expected 4 different device types ", isCorrect, Matchers.is(true));
    }


    @When("^I click on a random manufacturer$")
    public void i_click_on_a_random_manufacturer() throws Throwable {
        String name = manufacturerList.getARandomManufacturerName();
        String registered = manufacturerList.getRegistrationStatus(name);
        log.info("Manufacturer selected : " + name + ", is " + registered);
        manufacturerDetails = manufacturerList.viewAManufacturer(name);
        scenarioSession.putData(SessionKey.organisationName, name);
        scenarioSession.putData(SessionKey.registeredStatus, registered);
        scenarioSession.putData(SessionKey.taskType, "Update Manufacturer");
    }

    @When("^I click on a random manufacturer to add devices$")
    public void i_click_on_a_random_manufacturer_to_add_devices() throws Throwable {
        String name = manufacturerList.getARandomManufacturerName();
        String registered = manufacturerList.getRegistrationStatus(name);
        log.info("Manufacturer selected : " + name + ", is " + registered);
        manufacturerDetails = manufacturerList.viewAManufacturer(name);

//        if(manufacturerDetails.isDevicesAndProductTabVisible()){
//            deviceDetails = manufacturerDetails.clickOnDevicesAndProductDetailsLink();
//            deviceDetails = deviceDetails.clickManageDevices();
//            addDevices = deviceDetails.clickAddDeviceBtn();
//        }else {
//            addDevices = manufacturerDetails.clickContinueToAddDevices(registered);
//        }

        scenarioSession.putData(SessionKey.organisationName, name);
        scenarioSession.putData(SessionKey.registeredStatus, registered);
        scenarioSession.putData(SessionKey.taskType, "Update Manufacturer");
    }

    @When("^I click on stored manufacturer$")
    public void i_click_on_stored_manufacturer() throws Throwable {
        String name = (String) scenarioSession.getData(SessionKey.organisationName);
        String registered = manufacturerList.getRegistrationStatus(name);
        log.info("Manufacturer selected : " + name + ", is " + registered);

        manufacturerDetails = manufacturerList.viewAManufacturer(name);
        scenarioSession.putData(SessionKey.organisationName, name);
        scenarioSession.putData(SessionKey.registeredStatus, registered);
    }


    @Then("^I go to list of manufacturers page and click on stored manufacturer$")
    public void i_go_to_list_of_manufacturer_and_click_on_stored_manufacturer() throws Throwable {
        //externalHomePage = mainNavigationBar.clickExternalHOME();
        manufacturerList = externalHomePage.gotoListOfManufacturerPage();

        String name = (String) scenarioSession.getData(SessionKey.organisationName);
        manufacturerList = manufacturerList.sortBy("Registration status", 2);
        int nop = manufacturerList.getNumberOfPages(1);
        boolean isFoundInManufacturerList = false;
        int count = 0;

        do {
            count++;
            isFoundInManufacturerList = manufacturerList.isManufacturerDisplayedInList(name);
            if (!isFoundInManufacturerList) {
                manufacturerList = manufacturerList.clickNext();
            } else {
                manufacturerDetails = manufacturerList.viewAManufacturer(name);
            }
        } while (!isFoundInManufacturerList && count <= nop);


        if (!isFoundInManufacturerList) {
            //Check in registration in progress list
            externalHomePage = mainNavigationBar.clickExternalHOME();
            manufacturerList = externalHomePage.gotoListOfManufacturerPage();
            nop = manufacturerList.getNumberOfPages(2);
            count = 0;
            do {
                count++;
                isFoundInManufacturerList = manufacturerList.isRegistraionInProgressDisplayingManufacturer(name);
                if (!isFoundInManufacturerList && nop > 0) {
                    manufacturerList = manufacturerList.clickNext();
                } else {
                    manufacturerDetails = manufacturerList.viewAManufacturer(name);
                }
            } while (!isFoundInManufacturerList && count <= nop);
        }

        Assert.assertThat("Organisation Name Expected In Manufacturer List : " + name, isFoundInManufacturerList, Matchers.is(true));
    }


    @When("^I click on random manufacturer with status \"([^\"]*)\"$")
    public void i_click_on_random_manufacturer(String status) throws Throwable {

        if(status.equals("Registered")){
            manufacturerList = manufacturerList.sortBy("Registration status", 2);
        }

        String name = manufacturerList.getARandomManufacturerNameWithStatus(status);
        String registered = manufacturerList.getRegistrationStatus(name);
        String country = manufacturerList.getOrganisationCountry(name);

        int nop = manufacturerList.getNumberOfPages(1);
        int count = 0;
        while (registered != null && !registered.toLowerCase().equals(status.toLowerCase())) {
            count++;
            if (count > nop) {
                break;
            } else {
                //Go to next page and try again
                manufacturerList = manufacturerList.clickNext();

                //Try again
                name = manufacturerList.getARandomManufacturerNameWithStatus(status);
                registered = manufacturerList.getRegistrationStatus(name);
                country = manufacturerList.getOrganisationCountry(name);
            }
        }

        Assert.assertThat("Status of organisation should be : " + status, status.toLowerCase().equals(registered.toLowerCase()), Matchers.is(true));

        log.info("Manufacturer selected : " + name + ", is " + registered);
        manufacturerDetails = manufacturerList.viewAManufacturer(name);
        scenarioSession.putData(SessionKey.organisationName, name);
        scenarioSession.putData(SessionKey.organisationCountry, country);
        scenarioSession.putData(SessionKey.registeredStatus, registered);
        scenarioSession.putData(SessionKey.taskType, "Update Manufacturer");
    }


    @When("^I click on random manufacturer with status \"([^\"]*)\" to add device$")
    public void i_click_on_random_manufacturer_with_status_to_add_device(String status) throws Throwable {
        if(status.equals("Registered")){
            manufacturerList = manufacturerList.sortBy("Registration status", 2);
        }

        String name = manufacturerList.getARandomManufacturerNameWithStatus(status);
        String registered = manufacturerList.getRegistrationStatus(name);
        String country = manufacturerList.getOrganisationCountry(name);

        int nop = manufacturerList.getNumberOfPages(1);
        int count = 0;
        while (registered != null && !registered.toLowerCase().equals(status.toLowerCase())) {
            count++;
            if (count > nop) {
                break;
            } else {
                //Go to next page and try again
                manufacturerList = manufacturerList.clickNext();

                //Try again
                name = manufacturerList.getARandomManufacturerNameWithStatus(status);
                registered = manufacturerList.getRegistrationStatus(name);
                country = manufacturerList.getOrganisationCountry(name);
            }
        }

        Assert.assertThat("Status of organisation should be : " + status, status.toLowerCase().equals(registered.toLowerCase()), Matchers.is(true));

        log.info("Manufacturer selected : " + name + ", is " + registered);
        manufacturerDetails = manufacturerList.viewAManufacturer(name);
//
//        if(manufacturerDetails.isDevicesAndProductTabVisible()){
//            deviceDetails = manufacturerDetails.clickOnDevicesAndProductDetailsLink();
//            deviceDetails = deviceDetails.clickManageDevices();
//            addDevices = deviceDetails.clickAddDeviceBtn();
//        }else {
//            addDevices = manufacturerDetails.clickContinueToAddDevices(registered);
//        }

        scenarioSession.putData(SessionKey.organisationName, name);
        scenarioSession.putData(SessionKey.organisationCountry, country);
        scenarioSession.putData(SessionKey.registeredStatus, registered);
        scenarioSession.putData(SessionKey.taskType, "Update Manufacturer");
    }


    @When("^I view a random manufacturer with status \"([^\"]*)\"$")
    public void i_view_a_random_manufacturer(String status) throws Throwable {
        manufacturerList = manufacturerList.sortBy("Registered status", 2);
        String name = manufacturerList.getARandomManufacturerNameWithStatus(status);
        String actualStatus = manufacturerList.getRegistrationStatus(name);
        String country = manufacturerList.getOrganisationCountry(name);

        log.info("Manufacturer selected : " + name + ", is " + status);
        manufacturerDetails = manufacturerList.viewAManufacturer(name);
        scenarioSession.putData(SessionKey.organisationName, name);
        scenarioSession.putData(SessionKey.organisationCountry, country);
        scenarioSession.putData(SessionKey.registeredStatus, actualStatus);
    }

    /**
     * NO LONGER NEED : WE HAVE DEVICE INJECTION CODE AS PART OF THE SMOKE TESTS DATA CREATION TOOL
     *
     * @throws Throwable
     */
    @And("^Provide indication of devices made$")
    public void provideIndicationOfDevicesMade() throws Throwable {
        try {
            WaitUtils.waitForElementToBeClickable(driver, By.xpath(".//*[contains(text(),'ype of device')]//following::label[1]"), _Page.TIMEOUT_10_SECOND);

            WaitUtils.nativeWaitInSeconds(3);
            for (int x = 0; x < 9; x++) {
                try {
                    externalHomePage = externalHomePage.provideIndicationOfDevicesMade(x);
                } catch (Exception e) {
                }
            }

            //custom made
            try {
                externalHomePage.selectCustomMade(true);
            }catch (Exception e){}

            //Submit devices made : They changed the work flow on 03/02/2017
            //createNewManufacturer = externalHomePage.submitIndicationOfDevicesMade(true);
            //createNewManufacturer = externalHomePage.submitIndicationOfDevicesMade(false);

            createNewManufacturer = externalHomePage.submitIndicationOfDevicesMade(false);
        }catch (Exception e){
            e.printStackTrace();

        }
    }


    @Then("^I should see option to add another device$")
    public void iShouldSeeOptionToAddAnotherDevice() throws Throwable {
        boolean isVisible = addDevices.isOptionToAddAnotherDeviceVisible();
        Assert.assertThat("Expected to see option to : Add another device", isVisible, Matchers.is(true));
    }

    @Then("^I should not see option to add another device$")
    public void iShouldNotSeeOptionToAddAnotherDevice() throws Throwable {
        boolean isVisible = addDevices.isOptionToAddAnotherDeviceVisible();
        Assert.assertThat("Expected to see option to : Add another device", isVisible, Matchers.is(false));
    }

    @And("^The gmdn code or term is \"([^\"]*)\" in summary section$")
    public void theGmdnCodeOrTermIsCorrect(String displayed) throws Throwable {
        DeviceDO data = (DeviceDO) scenarioSession.getData(SessionKey.deviceData);
        //addDevices.isOptionToAddAnotherDeviceVisible();
        boolean isGMDNCorrect = addDevices.isGMDNValueDisplayed(data);

        if (displayed != null && displayed.equals("displayed"))
            Assert.assertThat("Expected gmdn code/definition : " + data.getGMDN(), isGMDNCorrect, Matchers.is(true));
        else
            Assert.assertThat("Expected not to see product with gmdn code/definition : " + data.getGMDN(), isGMDNCorrect, Matchers.is(false));
    }

    @And("^All the gmdn codes or terms are \"([^\"]*)\" in summary section$")
    public void allTheGmdnCodeOrTermIsCorrect(String displayed) throws Throwable {
        List<String> listOfGmdns = (List<String>) scenarioSession.getData(SessionKey.listOfGmndsAdded);
        boolean isGMDNCorrect = addDevices.isAllTheGMDNValueDisplayed(listOfGmdns);

        if (displayed != null && displayed.equals("displayed"))
            Assert.assertThat("Expected gmdn code/definition : " + listOfGmdns, isGMDNCorrect, Matchers.is(true));
        else
            Assert.assertThat("Expected not to see product with gmdn code/definition : " + listOfGmdns, isGMDNCorrect, Matchers.is(false));
    }


    @Then("^Verify name make model and other details are correct$")
    public void product_name_make_and_model_detals_are_correct() throws Throwable {
        DeviceDO data = (DeviceDO) scenarioSession.getData(SessionKey.deviceData);
        addDevices = addDevices.viewDeviceWithGMDNValue(data.getGMDN());
        boolean isProductDetailCorrect = addDevices.isProductDetailsCorrect(data);

        addDevices = addDevices.viewAProduct(data);
        boolean isCTSAndOtherDetailsCorrect = addDevices.isCTSAndOthereDetailsCorrect(data);
        Assert.assertThat("Expected to see product details displayed", isProductDetailCorrect, Matchers.is(true));
        Assert.assertThat("Expected to see product details displayed", isCTSAndOtherDetailsCorrect, Matchers.is(true));
    }

    @When("^I remove the device with gmdn \"([^\"]*)\" code$")
    public void iRemoveTheDeviceWithGmdnCode(String gmdnCode) throws Throwable {
        addDevices = addDevices.viewDeviceWithGMDNValue(gmdnCode);
        addDevices = addDevices.removeSelectedDevice();
    }

    @When("^I remove the stored device with gmdn code or definition$")
    public void iRemoveTheDeviceWithGmdnCode() throws Throwable {
        DeviceDO data = (DeviceDO) scenarioSession.getData(SessionKey.deviceData);
        String gmdnCode = data.getGMDN();
        addDevices = addDevices.viewDeviceWithGMDNValue(gmdnCode);
        addDevices = addDevices.removeSelectedDevice();
        addDevices = addDevices.confirmRemovalOfDevice(true);
    }

    @When("^I remove ALL the stored device with gmdn code or definition$")
    public void iRemoveAllTheDeviceWithGmdnCode() throws Throwable {
        List<DeviceDO> listOfDeviceData = (List<DeviceDO>) scenarioSession.getData(SessionKey.listOfDeviceDO);
        addDevices = addDevices.removeAllDevices(listOfDeviceData);
    }

    @Then("^Verify devices displayed and GMDN details are correct$")
    public void verifyDevicesDisplayedAndOtherDetailsAreCorrect() throws Throwable {
        ManufacturerRequestDO manufacaturerData = (ManufacturerRequestDO) scenarioSession.getData(SessionKey.manufacturerData);
        DeviceDO deviceData = (DeviceDO) scenarioSession.getData(SessionKey.deviceData);

        //Go to devices page by clicking on the "Devices and product details" link
        deviceDetails = manufacturerDetails.clickOnDevicesAndProductDetailsLink();

        //Verify manufacturer details showing correct data
        boolean isAllDataCorrect = deviceDetails.isDisplayedDeviceDataCorrect(manufacaturerData, deviceData);
        Assert.assertThat("Expected to see device : " + deviceData.gmdnTermOrDefinition, isAllDataCorrect, Matchers.is(true));
    }

    @Then("^I should be able to view stored device details$")
    public void i_should_be_able_to_view_products_related_to_stored_devices() throws Throwable {
        DeviceDO deviceData = (DeviceDO) scenarioSession.getData(SessionKey.deviceData);
        productDetail = manufacturerDetails.viewProduct(deviceData);
        boolean isDetailValid = productDetail.isProductOrDeviceDetailValid(deviceData);
        Assert.assertThat("Expected to see device : \n" + deviceData, isDetailValid, Matchers.is(true));
    }


    @Then("^Landing page displays correct title and contact name$")
    public void landing_page_displays_correct_title_and_contact_name() throws Throwable {
        boolean titleCorrect = externalHomePage.isTitleCorrect();
        String loggedInUser = (String) scenarioSession.getData(SessionKey.loggedInUser);
        boolean userNameIsCorrect = externalHomePage.isCorrectUsernameDisplayed(loggedInUser);
        Assert.assertThat("Page heading is incorrect", titleCorrect, Matchers.is(true));
        Assert.assertThat("Logged in user name should be displayed", userNameIsCorrect, Matchers.is(true));
    }


    @Then("^I should see the following columns for \"([^\"]*)\" list of manufacturer table$")
    public void i_should_see_the_following_columns_for_list_of_manufacturer_table(String commaDelimitedHeading) throws Throwable {
        boolean isHeadingCorrect = manufacturerList.isTableHeadingCorrect(commaDelimitedHeading);
        Assert.assertThat("Expected to see the following headings : " + commaDelimitedHeading, isHeadingCorrect, Matchers.is(true));
    }

    @Then("^I should see organisation details$")
    public void i_should_see_organisation_details() throws Throwable {
        String org = (String) scenarioSession.getData(SessionKey.organisationName);
        String status = (String) scenarioSession.getData(SessionKey.registeredStatus);

        boolean isCorrectFieldsDisplayed = manufacturerDetails.isDisplayedOrgFieldsCorrect(org, status);
        Assert.assertThat("Please check organisation fields displayed are correct for : " + org, isCorrectFieldsDisplayed, Matchers.is(true));
    }

    @Then("^I should see contact person details$")
    public void i_should_see_contact_person_details() throws Throwable {
        String org = (String) scenarioSession.getData(SessionKey.organisationName);
        String registeredStatus = (String) scenarioSession.getData(SessionKey.registeredStatus);
        boolean isCorrectFieldsDisplayed = manufacturerDetails.isDisplayedContactPersonFieldsCorrect(org);
        if(registeredStatus!=null && registeredStatus.toLowerCase().equals("registered"))
            isCorrectFieldsDisplayed = manufacturerDetails.isDisplayedContactPersonFieldsCorrect(org);
        else
            isCorrectFieldsDisplayed = manufacturerDetails.isDisplayedContactPersonFieldsCorrectForNonRegisteredManufacturer(org);
        Assert.assertThat("Please check organisation fields displayed are correct for : " + org, isCorrectFieldsDisplayed, Matchers.is(true));
    }


    @When("^I get a list of countries matching searchterm \"([^\"]*)\" from manufacturer test harness$")
    public void i_enter_in_the_new_country_field(String searchTerm) throws Throwable {
        if (searchTerm.contains("randomEU")) {
            searchTerm = RandomDataUtils.getRandomEUCountryName();
        }
        List<String> listOfCountries = createNewManufacturer.getListOfAutosuggestionsFor(searchTerm);
        scenarioSession.putData(SessionKey.autoSuggestResults, listOfCountries);
    }

    @Then("^I should see following \"([^\"]*)\" returned by manufacturer test harness autosuggests$")
    public void i_should_see_following_returned_by_autosuggests(String commaDelimitedExpectedMatches) throws Throwable {
        List<String> listOfMatches = (List<String>) scenarioSession.getData(SessionKey.autoSuggestResults);
        boolean isResultMatchingExpectation = AssertUtils.areAllDataInAutosuggestCorrect(listOfMatches, commaDelimitedExpectedMatches);
        Assert.assertThat("Expected to see : " + commaDelimitedExpectedMatches + ", in auto suggested list : " + listOfMatches, isResultMatchingExpectation, Matchers.is(true));
    }


    @When("^I search for device type \"([^\"]*)\" with gmdn \"([^\"]*)\"$")
    public void i_search_for_device_type_General_Medical_Device_with_gmdn_gmdnCode(String deviceType, String gmdnTermCodeOrDefinition) throws Throwable {
        //If registered we need to click on a button, else devices page is displayed
        String registeredStatus = (String) scenarioSession.getData(SessionKey.registeredStatus);

        //Search for specific device of a specific type
        DeviceDO dd = TestHarnessUtils.updateDeviceData(null, scenarioSession);
        addDevices = addDevices.searchForDevice(dd, deviceType, gmdnTermCodeOrDefinition);
        scenarioSession.putData(SessionKey.searchTerm, gmdnTermCodeOrDefinition);
    }

    @When("^I search for gmdn \"([^\"]*)\"$")
    public void i_search_for_gmdn_gmdnCode(String gmdnTermCodeOrDefinition) throws Throwable {
        //Search for specific device of a specific type
        DeviceDO dd = TestHarnessUtils.updateDeviceData(null, scenarioSession);
        addDevices = addDevices.searchForDevice(dd, null, gmdnTermCodeOrDefinition);
        scenarioSession.putData(SessionKey.searchTerm, gmdnTermCodeOrDefinition);
    }

    @Then("^I should see at least (\\d+) devices matches$")
    public void i_should_see_at_least_count_devices_matches(int expectedMinCount) throws Throwable {
        String searchTerm = (String) scenarioSession.getData(SessionKey.searchTerm);
        boolean atLeast1Match = addDevices.atLeast1MatchFound(searchTerm);
        if (expectedMinCount == 0) {
            Assert.assertThat("Expected to see no matches ", atLeast1Match, is(false));
        } else {
            Assert.assertThat("Expected to see atleast 1 matches", atLeast1Match, is(true));
        }
    }


    @When("^I click on view all gmdn term or definitions for device type \"([^\"]*)\"$")
    public void i_click_on_view_all_gmdn_term_or_definitions(String deviceType) throws Throwable {
        //View all gmdn
        DeviceDO dd = TestHarnessUtils.updateDeviceData(null, scenarioSession);
        addDevices = addDevices.viewAllGmdnTermDefinitions(dd, deviceType);
    }

    @Then("^I should see all gmdn term and definition table$")
    public void i_should_see_all_gmdn_term_and_definition_table() throws Throwable {
        addDevices = addDevices.clickViewAllGmdnTermDefinitions();
        boolean allGmdnTableVisible = addDevices.isAllGMDNTableDisplayed();
        Assert.assertThat("Expected to see All GMDN Table", allGmdnTableVisible, is(true));
    }


    /**
     * WHY IS THIS IN THE HOME PAGE
     *
     * @param message
     * @throws Throwable
     */
    @Then("^I should see error message \"([^\"]*)\" in instead of list of manufacturers$")
    public void i_should_see_error_message_in_instead_of_list_of_manufacturers(String message) throws Throwable {
        boolean isDisplayed = externalHomePage.isErrorMessageDsiplayed(message);
        Assert.assertThat("Expected to see error message with : " + message, isDisplayed, is(true));
    }

    @When("^I click the back button$")
    public void i_click_the_back_button() throws Throwable {
        externalHomePage = externalHomePage.clickBackButton();
    }

    @Then("^I should be in the portal home page$")
    public void i_should_be_in_the_portal_home_page() throws Throwable {
        boolean correctPage = externalHomePage.isInExternalHomePage();
        Assert.assertThat("Expected to be in portal home page", correctPage, is(true));
    }


    @When("^I save the application and confirm to exit$")
    public void iSaveTheApplicationAndExit() throws Throwable {
        addDevices = addDevices.saveAndExit();
        manufacturerList = addDevices.confirmSaveApplication(true);
        manufacturerList = manufacturerList.clickOnLinkToDisplayManufacturers();
    }

    @Then("^I should option to unregister the manufacturer$")
    public void iShouldOptionToUnregisterTheManufacturer() throws Throwable {
        boolean isDisplayed = manufacturerDetails.isUnregisterBtnDisplayed();
        Assert.assertThat("Expected to see UNREGISTER BUTTON", isDisplayed, is(true));
    }

    @When("^I unregister the manufacturer with the following reasons \"([^\"]*)\"$")
    public void iUnregisterTheManufacturerWithTheFollowingReasons(String reasons) throws Throwable {
        manufacturerDetails = manufacturerDetails.clickUnregisterManufacturerBtn();
        manufacturerDetails = manufacturerDetails.submitUnregistrationWithReasons(reasons, true);
    }
}
