package com.mhra.mdcm.devices.appian.steps.d1.external;

import com.mhra.mdcm.devices.appian.domains.newaccounts.AccountManufacturerRequest;
import com.mhra.mdcm.devices.appian.domains.newaccounts.DeviceData;
import com.mhra.mdcm.devices.appian.pageobjects.MainNavigationBar;
import com.mhra.mdcm.devices.appian.pageobjects.external.sections.AddDevices;
import com.mhra.mdcm.devices.appian.pageobjects.external.sections.ProductDetails;
import com.mhra.mdcm.devices.appian.session.SessionKey;
import com.mhra.mdcm.devices.appian.steps.common.CommonSteps;
import com.mhra.mdcm.devices.appian.utils.selenium.others.TestHarnessUtils;
import com.mhra.mdcm.devices.appian.utils.selenium.page.WaitUtils;
import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.springframework.context.annotation.Scope;

import java.util.Map;

/**
 * Created by TPD_Auto
 */
@Scope("cucumber-glue")
public class ExternalHomePageSteps extends CommonSteps {


    @When("^I go to portal page$")
    public void gotoPortalPage(){
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

    @Then("^I should see the correct manufacturer details$")
    public void i_should_see_the_correct_manufacturer_details() throws Throwable {
        String name = (String) scenarioSession.getData(SessionKey.organisationName);
        boolean isCorrectOrgLoaded = manufacturerDetails.isOrganisationNameCorrect(name);
        Assert.assertThat("Organisation Name Expected : " + name, isCorrectOrgLoaded, Matchers.is(true));
    }

    @And("^I go to register a new manufacturer page$")
    public void iGoToRegisterANewManufacturerPage() throws Throwable {
        manufacturerList = externalHomePage.gotoListOfManufacturerPage();
        createNewManufacturer = manufacturerList.registerNewManufacturer();
    }

    @And("^I go to register a new authorisedRep page$")
    public void iGoToRegisterANewAuthorisedRepPage() throws Throwable {
        manufacturerList = externalHomePage.gotoListOfManufacturerPage();
    }


    @When("^I create a new manufacturer using manufacturer test harness page with following data$")
    public void i_create_a_new_manufacturer_using_test_harness_page_with_following_data(Map<String, String> dataSets) throws Throwable {

        AccountManufacturerRequest newAccount = TestHarnessUtils.updateManufacturerDefaultsWithData(dataSets, scenarioSession);
        log.info("New Manufacturer Account Requested With Following Data : \n" + newAccount);

        //Create new manufacturer data
        addDevices = createNewManufacturer.createTestOrganisation(newAccount);
        if(createNewManufacturer.isErrorMessageDisplayed()){
            externalHomePage = mainNavigationBar.clickExternalHOME();
            manufacturerList = externalHomePage.gotoListOfManufacturerPage();
            createNewManufacturer = manufacturerList.registerNewManufacturer();
            addDevices = createNewManufacturer.createTestOrganisation(newAccount);
        }
        scenarioSession.putData(SessionKey.organisationName, newAccount.organisationName);
        scenarioSession.putData(SessionKey.manufacturerData, newAccount);
    }

    @Then("^I should see stored manufacturer appear in the manufacturers list$")
    public void iShouldSeeTheManufacturerAppearInTheManufacturersList() throws Throwable {
        externalHomePage = mainNavigationBar.clickExternalHOME();
        manufacturerList = externalHomePage.gotoListOfManufacturerPage();

        String name = (String) scenarioSession.getData(SessionKey.organisationName);
        int nop = manufacturerList.getNumberOfPages();
        boolean isFoundInManufacturerList = false;
        int count = 0;

        do{
            count++;
            isFoundInManufacturerList = manufacturerList.isManufacturerDisplayedInList(name);
            if(!isFoundInManufacturerList){
                manufacturerList = manufacturerList.clickNext();
            }
        }while(!isFoundInManufacturerList && count <= nop);

        Assert.assertThat("Organisation Name Expected In Manufacturer List : " + name, isFoundInManufacturerList, Matchers.is(true));
    }


//    @Then("^I should see stored manufacturer appear in the manufacturers list$")
//    public void iShouldSeeTheManufacturerAppearInTheManufacturersList2() throws Throwable {
//        externalHomePage = mainNavigationBar.clickExternalHOME();
//        externalHomePage = externalHomePage.gotoListOfManufacturerPage();
//        //externalHomePage = externalHomePage.registerAnotherManufacturer();
//
//        String name = (String) scenarioSession.getData(SessionKey.organisationName);
//        int nop = externalHomePage.getNumberOfPages();
//        boolean isFoundInManufacturerList = false;
//        int count = 0;
//        externalHomePage = externalHomePage.clickLastPage();
//        do{
//            count++;
//            isFoundInManufacturerList = externalHomePage.isManufacturerDisplayedInList(name);
//            if(!isFoundInManufacturerList){
//                externalHomePage = externalHomePage.clickPrev();
//            }
//        }while(!isFoundInManufacturerList && count <= nop);
//
//        Assert.assertThat("Organisation Name Expected In Manufacturer List : " + name, isFoundInManufacturerList, Matchers.is(true));
//    }


//    @When("^I select the stored manufacturer$")
//    public void iSelectTheStoredManufacturer() throws Throwable {
//        String name = (String) scenarioSession.getData(SessionKey.organisationName);
//        externalHomePage = externalHomePage.selectManufacturerFromList(name);
//    }

//    @And("^I should see the following link \"([^\"]*)\"$")
//    public void iShouldSeeTheFollowingLink(String partialLink) throws Throwable {
//        String name = (String) scenarioSession.getData(SessionKey.organisationName);
//        boolean isDisplayed = externalHomePage.isLinkDisplayed(partialLink);
//        boolean isNameDisplayed = externalHomePage.isLinkDisplayed(name);
//        Assert.assertThat("Declare devices link not found for manufacturer : " + name, isDisplayed, Matchers.is(true));
//        Assert.assertThat("Declare devices link not found for manufacturer : " + name, isNameDisplayed, Matchers.is(true));
//    }

//    @When("^I go to add devices page for the stored manufacturer")
//    public void iGoToAddDevicesPage() throws Throwable {
//        String name = (String) scenarioSession.getData(SessionKey.organisationName);
//        //Go to add devices page
//        externalHomePage = new ExternalHomePage(driver);
//        clickAddDeviceBtn = externalHomePage.gotoAddDevicesPageForManufacturer(name);
//        //clickAddDeviceBtn = clickAddDeviceBtn.addDevice(); removed 02/12/2016
//    }

    @When("^I add devices to NEWLY created manufacturer with following data$")
    public void iAddDevicesToNewlyCreatedManufacturerWithFollowingData(Map<String, String> dataSets) throws Throwable {
        //If registered we need to click on a button, else devices page is displayed
//        String registeredStatus = (String) scenarioSession.getData(SessionKey.organisationRegistered);
//        if(registeredStatus!=null && registeredStatus.equals("REGISTERED"))
//            addDevices = manufacturerDetails.clickAddDeviceBtn();
//        else
//            addDevices = new AddDevices(driver);

        //Its not registered
        addDevices = new AddDevices(driver);

        //Assumes we are in add device page
        DeviceData dd = TestHarnessUtils.updateDeviceData(dataSets, scenarioSession);
        addDevices = addDevices.addFollowingDevice(dd);

        scenarioSession.putData(SessionKey.deviceData, dd);
    }


    @And("^Proceed to payment and confirm submit device details$")
    public void proceedToPaymentAndConfirmSubmitDeviceDetails() throws Throwable {
        addDevices = addDevices.proceedToPayment();
        addDevices = addDevices.submitRegistration();
        externalHomePage = addDevices.finish();
    }


//    @When("^I add a device to STORED manufacturer with following data$")
//    public void i_add_a_device_of_type_with_following_data(Map<String, String> dataSets) throws Throwable {
//        String name = (String) scenarioSession.getData(SessionKey.organisationName);
//        //Go to add devices page
//        externalHomePage = new ExternalHomePage(driver);
//        clickAddDeviceBtn = externalHomePage.gotoAddDevicesPageForManufacturer(name);
//        //clickAddDeviceBtn = clickAddDeviceBtn.addDevice(); Removed on 02/12/2016
//
//        //Assumes we are in add device page
//        DeviceData dd = TestHarnessUtils.updateDeviceData(dataSets, scenarioSession);
//        clickAddDeviceBtn = clickAddDeviceBtn.addFollowingDevice(dd);

//    scenarioSession.putData(SessionKey.deviceData, dd);
//    }



    @When("^I add a device to SELECTED manufacturer with following data$")
    public void i_add_a_device_to_selected_manufactuerer_of_type_with_following_data(Map<String, String> dataSets) throws Throwable {

        //If registered we need to click on a button, else devices page is displayed
        String registeredStatus = (String) scenarioSession.getData(SessionKey.organisationRegistered);
        if(registeredStatus!=null && registeredStatus.equals("REGISTERED"))
            addDevices = manufacturerDetails.clickAddDeviceBtn();

        //Assumes we are in add device page
        //addDevices = manufacturerDetails.clickAddDeviceBtn();
        DeviceData dd = TestHarnessUtils.updateDeviceData(dataSets, scenarioSession);
        addDevices = addDevices.addFollowingDevice(dd);

        scenarioSession.putData(SessionKey.deviceData, dd);
    }

    @When("^I add multiple devices to SELECTED manufacturer with following data$")
    public void i_add_multiple_devices_to_selected_manufactuerer_of_type_with_following_data(Map<String, String> dataSets) throws Throwable {
        //If registered we need to click on a button, else devices page is displayed
        String registeredStatus = (String) scenarioSession.getData(SessionKey.organisationRegistered);
        if(registeredStatus!=null && registeredStatus.equals("REGISTERED"))
            addDevices = manufacturerDetails.clickAddDeviceBtn();

        DeviceData dd = TestHarnessUtils.updateDeviceData(dataSets, scenarioSession);
        addDevices = addDevices.addFollowingDevice(dd);

        scenarioSession.putData(SessionKey.deviceData, dd);
    }

    @When("^I add another device to SELECTED manufacturer with following data$")
    public void i_add_another_device_to_selected_manufactuerer_of_type_with_following_data(Map<String, String> dataSets) throws Throwable {

        //Go and add another device
        addDevices = addDevices.addAnotherDevice();

        //Assumes we are in add device page
        DeviceData dd = TestHarnessUtils.updateDeviceData(dataSets, scenarioSession);
        addDevices = addDevices.addFollowingDevice(dd);

        scenarioSession.putData(SessionKey.deviceData, dd);
    }

    @Then("^I should see correct device types$")
    public void iShouldSeeCorrectDeviceTypes() throws Throwable {
        //If registered we need to click on a button, else devices page is displayed
        String registeredStatus = (String) scenarioSession.getData(SessionKey.organisationRegistered);
        if(registeredStatus!=null && registeredStatus.equals("REGISTERED"))
            addDevices = manufacturerDetails.clickAddDeviceBtn();

        boolean isCorrect = addDevices.isDeviceTypeCorrect();
        Assert.assertThat("Expected 4 different device types " , isCorrect, Matchers.is(true));
    }

//    @When("^I select a random manufacturer from list$")
//    public void iSelectARandomManufacturerFromList() throws Throwable {
//        String name = externalHomePage.getARandomManufacturerNameFromDropDown();
//        externalHomePage = externalHomePage.selectManufacturerFromList(name);
//        scenarioSession.putData(SessionKey.organisationName, name);
//    }

    @When("^I click on a random manufacturer$")
    public void i_click_on_a_random_manufacturer() throws Throwable {
        String name = manufacturerList.getARandomManufacturerName();
        String registered = manufacturerList.getRegistrationStatus(name);
        log.info("Manufacturer selected : " + name + ", is " + registered);
        manufacturerDetails = manufacturerList.viewAManufacturer(name);
        scenarioSession.putData(SessionKey.organisationName, name);
        scenarioSession.putData(SessionKey.organisationRegistered, registered);
    }

    @When("^I click on stored manufacturer$")
    public void i_click_on_stored_manufacturer() throws Throwable {
        String name = (String) scenarioSession.getData(SessionKey.organisationName);
        String registered = manufacturerList.getRegistrationStatus(name);
        log.info("Manufacturer selected : " + name + ", is " + registered);

        manufacturerDetails = manufacturerList.viewAManufacturer(name);
        scenarioSession.putData(SessionKey.organisationName, name);
        scenarioSession.putData(SessionKey.organisationRegistered, registered);
    }



    @Then("^I go to list of manufacturers page and click on stored manufacturer$")
    public void i_go_to_list_of_manufacturer_and_click_on_stored_manufacturer() throws Throwable {
        //externalHomePage = mainNavigationBar.clickExternalHOME();
        manufacturerList = externalHomePage.gotoListOfManufacturerPage();

        String name = (String) scenarioSession.getData(SessionKey.organisationName);
        int nop = manufacturerList.getNumberOfPages();
        boolean isFoundInManufacturerList = false;
        int count = 0;

        do{
            count++;
            isFoundInManufacturerList = manufacturerList.isManufacturerDisplayedInList(name);
            if(!isFoundInManufacturerList){
                manufacturerList = manufacturerList.clickNext();
            }else{
                manufacturerDetails = manufacturerList.viewAManufacturer(name);
            }
        }while(!isFoundInManufacturerList && count <= nop);

        Assert.assertThat("Organisation Name Expected In Manufacturer List : " + name, isFoundInManufacturerList, Matchers.is(true));
    }



    @When("^I click on random manufacturer with status \"([^\"]*)\"$")
    public void i_click_on_random_manufacturer(String status) throws Throwable {
        //manufacturerList.sortBy("Registration Status", 2);

        String name = manufacturerList.getARandomManufacturerName();
        String registered = manufacturerList.getRegistrationStatus(name);
        String country = manufacturerList.getOrganisationCountry(name);
        int count = 0;
        while(registered!=null && !registered.toLowerCase().equals(status.toLowerCase())){
            count++;
            if(count > 5){
                break;
            }

            //Try again
            name = manufacturerList.getARandomManufacturerName();
            registered = manufacturerList.getRegistrationStatus(name);
            country = manufacturerList.getOrganisationCountry(name);
        }

        Assert.assertThat("Status of organisation should be : " + status , status.equals(registered), Matchers.is(true));

        log.info("Manufacturer selected : " + name + ", is " + registered);
        manufacturerDetails = manufacturerList.viewAManufacturer(name);
        scenarioSession.putData(SessionKey.organisationName, name);
        scenarioSession.putData(SessionKey.organisationCountry, country);
        scenarioSession.putData(SessionKey.organisationRegistered, registered);
    }

    @And("^Provide indication of devices made$")
    public void provideIndicationOfDevicesMade() throws Throwable {
        WaitUtils.nativeWaitInSeconds(5);
        for(int x = 0; x < 9; x++) {
            externalHomePage = externalHomePage.provideIndicationOfDevicesMade(x);
        }

        //custom made
        //externalHomePage.selectCustomMade(true);

        //Submit devices made
        externalHomePage = externalHomePage.submitIndicationOfDevicesMade(true);
        externalHomePage = externalHomePage.submitIndicationOfDevicesMade(false);

        System.out.println("DONE");
    }


    @Then("^I should see option to add another device$")
    public void iShouldSeeOptionToAddAnotherDevice() throws Throwable {
        boolean isVisible = addDevices.isOptionToAddAnotherDeviceVisible();
        Assert.assertThat("Expected to see option to : Add another device" , isVisible, Matchers.is(true));
    }

    @And("^The gmdn code or term is \"([^\"]*)\" in summary section$")
    public void theGmdnCodeOrTermIsCorrect(String displayed) throws Throwable {
        DeviceData data = (DeviceData) scenarioSession.getData(SessionKey.deviceData);
        addDevices.isOptionToAddAnotherDeviceVisible();
        boolean isGMDNCorrect = addDevices.isGMDNValueDisplayed(data);

        if(displayed!=null && displayed.equals("displayed"))
            Assert.assertThat("Expected gmdn code : " + data.gmdnCode + ", OR gmdn term : " + data.gmdnTermOrDefinition , isGMDNCorrect, Matchers.is(true));
        else
            Assert.assertThat("Expected not to see gmdn code : " + data.gmdnCode + ", OR gmdn term : " + data.gmdnTermOrDefinition , isGMDNCorrect, Matchers.is(false));
    }

    @When("^I remove the device with gmdn \"([^\"]*)\" code$")
    public void iRemoveTheDeviceWithGmdnCode(String gmdnCode) throws Throwable {
        addDevices = addDevices.viewDeviceWithGMDNValue(gmdnCode);
        addDevices = addDevices.removeSelectedDevice();
    }

    @Then("^Verify devices displayed and other details are correct$")
    public void verifyDevicesDisplayedAndOtherDetailsAreCorrect() throws Throwable {
        AccountManufacturerRequest manufacaturerData = (AccountManufacturerRequest) scenarioSession.getData(SessionKey.manufacturerData);
        DeviceData deviceData = (DeviceData) scenarioSession.getData(SessionKey.deviceData);

        //Verify manufacturer details showing correct data
        boolean isAllDataCorrect = manufacturerDetails.isDisplayedDataCorrect(manufacaturerData, deviceData);
        Assert.assertThat("Expected to see device : " + deviceData.gmdnTermOrDefinition , isAllDataCorrect, Matchers.is(true));
    }

    @Then("^I should be able to view products related to stored devices$")
    public void i_should_be_able_to_view_products_related_to_stored_devices() throws Throwable {
        DeviceData deviceData = (DeviceData) scenarioSession.getData(SessionKey.deviceData);
        productDetail = manufacturerDetails.viewProduct(deviceData);
        boolean isDetailValid = productDetail.isProductOrDeviceDetailValid(deviceData);
        Assert.assertThat("Expected to see device : \n" + deviceData , isDetailValid, Matchers.is(true));
    }
}
