package com.mhra.mdcm.devices.appian.steps.d1.external;

import com.mhra.mdcm.devices.appian.domains.newaccounts.AccountManufacturerRequest;
import com.mhra.mdcm.devices.appian.domains.newaccounts.DeviceData;
import com.mhra.mdcm.devices.appian.pageobjects.MainNavigationBar;
import com.mhra.mdcm.devices.appian.pageobjects.external.sections.AddDevices;
import com.mhra.mdcm.devices.appian.session.SessionKey;
import com.mhra.mdcm.devices.appian.steps.common.CommonSteps;
import com.mhra.mdcm.devices.appian.utils.selenium.others.RandomDataUtils;
import com.mhra.mdcm.devices.appian.utils.selenium.others.StepsUtils;
import com.mhra.mdcm.devices.appian.utils.selenium.others.TestHarnessUtils;
import com.mhra.mdcm.devices.appian.utils.selenium.page.AssertUtils;
import com.mhra.mdcm.devices.appian.utils.selenium.page.WaitUtils;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.springframework.context.annotation.Scope;

import java.util.List;
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
        boolean isManufacturer = true;
        if(name.contains("thorised")){
            isManufacturer = false;
        }
        boolean isCorrectOrgLoaded = manufacturerDetails.isOrganisationNameCorrect(name, isManufacturer);
        Assert.assertThat("Organisation Name Expected : " + name, isCorrectOrgLoaded, Matchers.is(true));
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
        scenarioSession.putData(SessionKey.taskType, "New Manufacturer");
    }


    @Then("^I should see the registered manufacturers list$")
    public void iShouldSeeTheManufacturersList() throws Throwable {
        String name = (String) scenarioSession.getData(SessionKey.organisationName);
        externalHomePage = mainNavigationBar.clickExternalHOME();
        manufacturerList = externalHomePage.gotoListOfManufacturerPage();
        boolean isCorrect = manufacturerList.isSpecificTableHeadingCorrect("Organisation country");
        Assert.assertThat("Expected To See Manufacturer List : " + name, isCorrect, Matchers.is(true));
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
            if(!isFoundInManufacturerList && nop > 0){
                manufacturerList = manufacturerList.clickNext();
            }
        }while(!isFoundInManufacturerList && count <= nop);

        if(!isFoundInManufacturerList){
            //Try first page again
            externalHomePage = mainNavigationBar.clickExternalHOME();
            manufacturerList = externalHomePage.gotoListOfManufacturerPage();
            count = 0;
            do{
                count++;
                isFoundInManufacturerList = manufacturerList.isManufacturerLinkDisplayed(name);
                if(!isFoundInManufacturerList && nop > 0){
                    manufacturerList = manufacturerList.clickNext();
                }
            }while(!isFoundInManufacturerList && count <= nop);
        }

        Assert.assertThat("Organisation Name Expected In Manufacturer List : " + name, isFoundInManufacturerList, Matchers.is(true));
    }


    @When("^I add devices to NEWLY created manufacturer with following data$")
    public void iAddDevicesToNewlyCreatedManufacturerWithFollowingData(Map<String, String> dataSets) throws Throwable {
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


    @When("^I add a device to SELECTED manufacturer with following data$")
    public void i_add_a_device_to_selected_manufactuerer_of_type_with_following_data(Map<String, String> dataSets) throws Throwable {

        //If registered we need to click on a button, else devices page is displayed
        String registeredStatus = (String) scenarioSession.getData(SessionKey.organisationRegistered);
        try {
            if (registeredStatus != null && registeredStatus.toLowerCase().equals("registered"))
                addDevices = manufacturerDetails.clickAddDeviceBtn();
        }catch (Exception e){
            addDevices = new AddDevices(driver);
        }

        //Assumes we are in add device page
        DeviceData dd = TestHarnessUtils.updateDeviceData(dataSets, scenarioSession);
        addDevices = addDevices.addFollowingDevice(dd);

        StepsUtils.addToListOfStrings(scenarioSession, SessionKey.listOfGmndsAdded, AddDevices.gmdnSelected);
        scenarioSession.putData(SessionKey.deviceData, dd);
        StepsUtils.addToDeviceDataList(scenarioSession, dd);
    }

    @When("^I add multiple devices to SELECTED manufacturer with following data$")
    public void i_add_multiple_devices_to_selected_manufactuerer_of_type_with_following_data(Map<String, String> dataSets) throws Throwable {
        //If registered we need to click on a button, else devices page is displayed
        String registeredStatus = (String) scenarioSession.getData(SessionKey.organisationRegistered);
        if(registeredStatus!=null && registeredStatus.toLowerCase().equals("registered"))
            addDevices = manufacturerDetails.clickAddDeviceBtn();

        DeviceData dd = TestHarnessUtils.updateDeviceData(dataSets, scenarioSession);
        addDevices = addDevices.addFollowingDevice(dd);

        StepsUtils.addToListOfStrings(scenarioSession, SessionKey.listOfGmndsAdded, AddDevices.gmdnSelected);
        scenarioSession.putData(SessionKey.deviceData, dd);
    }

    @When("^I add another device to SELECTED manufacturer with following data$")
    public void i_add_another_device_to_selected_manufactuerer_of_type_with_following_data(Map<String, String> dataSets) throws Throwable {

        //Go and add another device
        addDevices = addDevices.addAnotherDevice();

        //Assumes we are in add device page
        DeviceData dd = TestHarnessUtils.updateDeviceData(dataSets, scenarioSession);
        addDevices = addDevices.addFollowingDevice(dd);

        StepsUtils.addToListOfStrings(scenarioSession, SessionKey.listOfGmndsAdded, AddDevices.gmdnSelected);
        scenarioSession.putData(SessionKey.deviceData, dd);
        StepsUtils.addToDeviceDataList(scenarioSession, dd);
    }

    @Then("^I should see error message in devices page with text \"([^\"]*)\"$")
    public void i_should_see_error_message_in_devices_page_with_text(String message) throws Throwable {
        boolean errorMessageDisplayed = addDevices.isErrorMessageDisplayed("Duplicate");
        if(errorMessageDisplayed){
            errorMessageDisplayed = addDevices.isErrorMessageCorrect(message);
        }
        Assert.assertThat("Expected error message to contain : " + message , errorMessageDisplayed, Matchers.is(true));
    }

    @Then("^I should see correct device types$")
    public void iShouldSeeCorrectDeviceTypes() throws Throwable {
        //If registered we need to click on a button, else devices page is displayed
        String registeredStatus = (String) scenarioSession.getData(SessionKey.organisationRegistered);
        if(registeredStatus!=null && registeredStatus.toLowerCase().equals("registered"))
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

        String name = manufacturerList.getARandomManufacturerNameWithStatus(status);
        String registered = manufacturerList.getRegistrationStatus(name);
        String country = manufacturerList.getOrganisationCountry(name);

        int nop = manufacturerList.getNumberOfPages();
        int count = 0;
        while(registered!=null && !registered.toLowerCase().equals(status.toLowerCase())){
            count++;
            if(count > nop){
                break;
            }else{
                //Go to next page and try again
                manufacturerList = manufacturerList.clickNext();

                //Try again
                name = manufacturerList.getARandomManufacturerNameWithStatus(status);
                registered = manufacturerList.getRegistrationStatus(name);
                country = manufacturerList.getOrganisationCountry(name);
            }
        }

        Assert.assertThat("Status of organisation should be : " + status , status.equals(registered), Matchers.is(true));

        log.info("Manufacturer selected : " + name + ", is " + registered);
        manufacturerDetails = manufacturerList.viewAManufacturer(name);
        scenarioSession.putData(SessionKey.organisationName, name);
        scenarioSession.putData(SessionKey.organisationCountry, country);
        scenarioSession.putData(SessionKey.organisationRegistered, registered);
        scenarioSession.putData(SessionKey.taskType, "Update Manufacturer");
    }


    @When("^I view a random manufacturer with status \"([^\"]*)\"$")
    public void i_view_a_random_manufacturer(String status) throws Throwable {
        String name = manufacturerList.getARandomManufacturerNameWithStatus(status);
        String actualStatus = manufacturerList.getRegistrationStatus(name);
        String country = manufacturerList.getOrganisationCountry(name);

        log.info("Manufacturer selected : " + name + ", is " + status);
        manufacturerDetails = manufacturerList.viewAManufacturer(name);
        scenarioSession.putData(SessionKey.organisationName, name);
        scenarioSession.putData(SessionKey.organisationCountry, country);
        scenarioSession.putData(SessionKey.organisationRegistered, actualStatus);
    }

    @And("^Provide indication of devices made$")
    public void provideIndicationOfDevicesMade() throws Throwable {
        WaitUtils.nativeWaitInSeconds(7);
        for(int x = 0; x < 9; x++) {
            externalHomePage = externalHomePage.provideIndicationOfDevicesMade(x);
        }

        //custom made
        externalHomePage.selectCustomMade(true);

        //Submit devices made
        externalHomePage = externalHomePage.submitIndicationOfDevicesMade(true);
        externalHomePage = externalHomePage.submitIndicationOfDevicesMade(false);

        WaitUtils.nativeWaitInSeconds(5);

        System.out.println("DONE");
    }


    @Then("^I should see option to add another device$")
    public void iShouldSeeOptionToAddAnotherDevice() throws Throwable {
        boolean isVisible = addDevices.isOptionToAddAnotherDeviceVisible();
        Assert.assertThat("Expected to see option to : Add another device" , isVisible, Matchers.is(true));
    }

    @Then("^I should not see option to add another device$")
    public void iShouldNotSeeOptionToAddAnotherDevice() throws Throwable {
        boolean isVisible = addDevices.isOptionToAddAnotherDeviceVisible();
        Assert.assertThat("Expected to see option to : Add another device" , isVisible, Matchers.is(false));
    }

    @And("^The gmdn code or term is \"([^\"]*)\" in summary section$")
    public void theGmdnCodeOrTermIsCorrect(String displayed) throws Throwable {
        DeviceData data = (DeviceData) scenarioSession.getData(SessionKey.deviceData);
        //addDevices.isOptionToAddAnotherDeviceVisible();
        boolean isGMDNCorrect = addDevices.isGMDNValueDisplayed(data);

        if(displayed!=null && displayed.equals("displayed"))
            Assert.assertThat("Expected gmdn code/definition : " + data.getGMDN() , isGMDNCorrect, Matchers.is(true));
        else
            Assert.assertThat("Expected not to see product with gmdn code/definition : " + data.getGMDN() , isGMDNCorrect, Matchers.is(false));
    }

    @And("^All the gmdn codes or terms are \"([^\"]*)\" in summary section$")
    public void allTheGmdnCodeOrTermIsCorrect(String displayed) throws Throwable {
        List<String> listOfGmdns = (List<String>) scenarioSession.getData(SessionKey.listOfGmndsAdded);
        boolean isGMDNCorrect = addDevices.isAllTheGMDNValueDisplayed(listOfGmdns);

        if(displayed!=null && displayed.equals("displayed"))
            Assert.assertThat("Expected gmdn code/definition : " + listOfGmdns , isGMDNCorrect, Matchers.is(true));
        else
            Assert.assertThat("Expected not to see product with gmdn code/definition : " + listOfGmdns , isGMDNCorrect, Matchers.is(false));
    }



    @Then("^Verify name make model and other details are correct$")
    public void product_name_make_and_model_detals_are_correct() throws Throwable {
        DeviceData data = (DeviceData) scenarioSession.getData(SessionKey.deviceData);
        addDevices = addDevices.viewDeviceWithGMDNValue(data.getGMDN());
        boolean isProductDetailCorrect = addDevices.isProductDetailsCorrect(data);

        addDevices = addDevices.viewAProduct(data);
        boolean isCTSAndOtherDetailsCorrect = addDevices.isCTSAndOthereDetailsCorrect(data);
        Assert.assertThat("Expected to see product details displayed", isProductDetailCorrect, Matchers.is(true));
    }

    @When("^I remove the device with gmdn \"([^\"]*)\" code$")
    public void iRemoveTheDeviceWithGmdnCode(String gmdnCode) throws Throwable {
        addDevices = addDevices.viewDeviceWithGMDNValue(gmdnCode);
        addDevices = addDevices.removeSelectedDevice();
    }

    @When("^I remove the stored device with gmdn code or definition$")
    public void iRemoveTheDeviceWithGmdnCode() throws Throwable {
        DeviceData data = (DeviceData) scenarioSession.getData(SessionKey.deviceData);
        String gmdnCode = data.getGMDN();
        addDevices = addDevices.viewDeviceWithGMDNValue(gmdnCode);
        addDevices = addDevices.removeSelectedDevice();
    }

    @When("^I remove ALL the stored device with gmdn code or definition$")
    public void iRemoveAllTheDeviceWithGmdnCode() throws Throwable {
        List<DeviceData> listOfDeviceData = (List<DeviceData>) scenarioSession.getData(SessionKey.deviceDataList);
        addDevices = addDevices.removeAllDevices(listOfDeviceData);
    }

    @Then("^Verify devices displayed and other details are correct$")
    public void verifyDevicesDisplayedAndOtherDetailsAreCorrect() throws Throwable {
        AccountManufacturerRequest manufacaturerData = (AccountManufacturerRequest) scenarioSession.getData(SessionKey.manufacturerData);
        DeviceData deviceData = (DeviceData) scenarioSession.getData(SessionKey.deviceData);

        //Verify manufacturer details showing correct data
        boolean isAllDataCorrect = manufacturerDetails.isDisplayedDeviceDataCorrect(manufacaturerData, deviceData);
        Assert.assertThat("Expected to see device : " + deviceData.gmdnTermOrDefinition , isAllDataCorrect, Matchers.is(true));
    }

    @Then("^I should be able to view products related to stored devices$")
    public void i_should_be_able_to_view_products_related_to_stored_devices() throws Throwable {
        DeviceData deviceData = (DeviceData) scenarioSession.getData(SessionKey.deviceData);
        productDetail = manufacturerDetails.viewProduct(deviceData);
        boolean isDetailValid = productDetail.isProductOrDeviceDetailValid(deviceData);
        Assert.assertThat("Expected to see device : \n" + deviceData , isDetailValid, Matchers.is(true));
    }


    @Then("^Landing page displays correct title and contact name$")
    public void landing_page_displays_correct_title_and_contact_name() throws Throwable {
        boolean titleCorrect = externalHomePage.isTitleCorrect();
        String loggedInUser = (String) scenarioSession.getData(SessionKey.loggedInUser);
        boolean userNameIsCorrect = externalHomePage.isCorrectUsernameDisplayed(loggedInUser);
        Assert.assertThat("Page heading is incorrect" , titleCorrect, Matchers.is(true));
        Assert.assertThat("Logged in user name should be displayed" , userNameIsCorrect, Matchers.is(true));
    }


    @Then("^I should see the following columns for \"([^\"]*)\" list of manufacturer table$")
    public void i_should_see_the_following_columns_for_list_of_manufacturer_table(String commaDelimitedHeading) throws Throwable {
        boolean isHeadingCorrect = manufacturerList.isTableHeadingCorrect(commaDelimitedHeading);
        Assert.assertThat("Expected to see the following headings : " + commaDelimitedHeading , isHeadingCorrect, Matchers.is(true));
    }

    @Then("^I should see organisation details$")
    public void i_should_see_organisation_details() throws Throwable {
        String org = (String) scenarioSession.getData(SessionKey.organisationName);
        boolean isCorrectFieldsDisplayed = manufacturerDetails.isDisplayedOrgFieldsCorrect(org);
        Assert.assertThat("Please check organisation fields displayed are correct for : " + org, isCorrectFieldsDisplayed, Matchers.is(true));
    }

    @Then("^I should see contact person details$")
    public void i_should_see_contact_person_details() throws Throwable {
        String org = (String) scenarioSession.getData(SessionKey.organisationName);
        boolean isCorrectFieldsDisplayed = manufacturerDetails.isDisplayedContactPersonFieldsCorrect(org);
        Assert.assertThat("Please check organisation fields displayed are correct for : " + org, isCorrectFieldsDisplayed, Matchers.is(true));
    }


    @When("^I enter \"([^\"]*)\" in the new country field in manufacturer test harness$")
    public void i_enter_in_the_new_country_field(String searchTerm) throws Throwable {
        if(searchTerm.contains("randomEU")){
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

}
