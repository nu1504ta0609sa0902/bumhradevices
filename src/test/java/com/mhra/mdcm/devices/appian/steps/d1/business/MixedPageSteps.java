package com.mhra.mdcm.devices.appian.steps.d1.business;

import com.mhra.mdcm.devices.appian.domains.newaccounts.DeviceDO;
import com.mhra.mdcm.devices.appian.enums.LinksRecordPage;
import com.mhra.mdcm.devices.appian.session.SessionKey;
import com.mhra.mdcm.devices.appian.steps.common.CommonSteps;
import cucumber.api.PendingException;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;
import org.springframework.context.annotation.Scope;

import static org.hamcrest.Matchers.is;

/**
 * Created by TPD_Auto on 01/02/2017.
 */
@Scope("cucumber-glue")
public class MixedPageSteps extends CommonSteps {

    @When("^I click on a random organisation link \"([^\"]*)\" in \"([^\"]*)\" page$")
    public void i_click_on_a_random_organisation_link(String searchTerm, String page) throws Throwable {
        if (page.equals(LinksRecordPage.LINK_ACCOUNTS.link)) {
            businessManufacturerDetails = accounts.viewManufacturerByText(searchTerm);
        } else if (page.equals(LinksRecordPage.LINK_REGISTERED_DEVICES.link)) {
        } else if (page.equals(LinksRecordPage.LINK_REGISTERED_PRODUCTS.link)) {
            businessManufacturerDetails = registeredProducts.viewManufacturerByText(searchTerm);
        } else if (page.equals(LinksRecordPage.LINK_ORGANISATIONS.link)) {
            businessManufacturerDetails = organisations.viewManufacturerByText(searchTerm);
        }
        scenarioSession.putData(SessionKey.searchTerm, searchTerm);
    }

    @When("^I click on a link which matches the stored organisations in \"([^\"]*)\" page$")
    public void i_click_on_stored_organisation_link(String page) throws Throwable {
        String searchTerm = (String) scenarioSession.getData(SessionKey.organisationName);

        if (page.equals(LinksRecordPage.LINK_ACCOUNTS.link)) {
            businessManufacturerDetails = accounts.viewManufacturerByText(searchTerm);
        } else if (page.equals(LinksRecordPage.LINK_REGISTERED_DEVICES.link)) {
        } else if (page.equals(LinksRecordPage.LINK_REGISTERED_PRODUCTS.link)) {
            businessManufacturerDetails = registeredProducts.viewManufacturerByText(searchTerm);
        } else if (page.equals(LinksRecordPage.LINK_ORGANISATIONS.link)) {
            businessManufacturerDetails = organisations.viewManufacturerByText(searchTerm);
        }
        scenarioSession.putData(SessionKey.searchTerm, searchTerm);
    }


    @Then("^I should see new product id generated for my device$")
    public void i_should_see_new_product_id_generated_for_my_device() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("^I should see business manufacturer details page for the manufacturer$")
    public void i_should_see_business_manufacturer_details_page_for_the_manufacturer() throws Throwable {
        String searchTerm = (String) scenarioSession.getData(SessionKey.searchTerm);
        boolean matched = businessManufacturerDetails.isManufacturerHeadingCorrect(searchTerm);
        Assert.assertThat("Manufacturer heading should contain : " + searchTerm, matched, is(true));
    }

    @Then("^I should see all the correct product details$")
    public void i_should_see_all_the_correct_product_details() throws Throwable {
        boolean fieldsVisible = businessProductDetails.areAllFieldsVisible();
        Assert.assertThat("Product details didn't show all expected fields", fieldsVisible, is(true));

    }

    @When("^I click on link \"([^\"]*)\" and go to \"([^\"]*)\" page$")
    public void i_click_on_link(String link, String page) throws Throwable {
        if(page.equals("devices")){
            businessDevicesDetails = businessManufacturerDetails.clickOnDevicesLink(link);
        }
    }

    @Then("^I should see device table with devices$")
    public void i_should_see_device_table_with_devices() throws Throwable {
        String expectedHeadings = "GMDN code,GMDN definition,Risk classification";
        boolean isDevicesTableDisplayed = businessDevicesDetails.isDeviceTableDisplayed();
        boolean isTableHeadingCorrect = businessDevicesDetails.isTableColumnsCorrect(expectedHeadings);
        Assert.assertThat("Table heading expected : " + expectedHeadings, isTableHeadingCorrect, is(true));
        Assert.assertThat("Table should have data " , isDevicesTableDisplayed, is(true));
    }


    @When("^I click on a device for device type \"([^\"]*)\"$")
    public void iClickOnADeviceForDeviceType(String deviceType) throws Throwable {
        DeviceDO dd = (DeviceDO) scenarioSession.getData(SessionKey.deviceData);
        deviceType = deviceType.toLowerCase();
        businessDevicesDetails = businessDevicesDetails.viewDeviceOfType(deviceType, dd.gmdnTermOrDefinition);
    }


    @When("^I click on a device with link \"([^\"]*)\" for device type \"([^\"]*)\"$")
    public void iClickOnADeviceForDeviceType(String link, String deviceType) throws Throwable {
        deviceType = deviceType.toLowerCase();
        businessDevicesDetails = businessDevicesDetails.viewDeviceOfType(deviceType, link);
    }


    @Then("^I should see correct information for device type \"([^\"]*)\"$")
    public void i_should_see_correct_information_for_device_type(String deviceType) throws Throwable {
        deviceType = deviceType.toLowerCase();
        boolean areFieldsCorrect = businessDevicesDetails.areDeviceInformationPageShowingCorrectFields(deviceType);
        Assert.assertThat("Not all the expected FIELDS displayed for device type : " + deviceType, areFieldsCorrect, is(true));
    }


}
