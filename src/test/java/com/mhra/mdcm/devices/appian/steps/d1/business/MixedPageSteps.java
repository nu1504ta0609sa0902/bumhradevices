package com.mhra.mdcm.devices.appian.steps.d1.business;

import com.mhra.mdcm.devices.appian.session.SessionKey;
import com.mhra.mdcm.devices.appian.steps.common.CommonSteps;
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
        if (page.equals("Accounts")) {
        } else if (page.equals("Devices")) {
        } else if (page.equals("Products")) {
        } else if (page.equals("All Products")) {
            businessManufacturerDetails = allProducts.viewManufacturerByText(searchTerm);
        } else if (page.equals("All Organisations")) {
        }
        scenarioSession.putData(SessionKey.searchTerm, searchTerm);
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
}
