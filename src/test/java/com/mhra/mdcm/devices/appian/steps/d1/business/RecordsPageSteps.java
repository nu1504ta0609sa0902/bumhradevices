package com.mhra.mdcm.devices.appian.steps.d1.business;

import com.mhra.mdcm.devices.appian.pageobjects.LoginPage;
import com.mhra.mdcm.devices.appian.pageobjects.business.sections.Accounts;
import com.mhra.mdcm.devices.appian.pageobjects.business.sections.AllOrganisations;
import com.mhra.mdcm.devices.appian.pageobjects.business.sections.Devices;
import com.mhra.mdcm.devices.appian.pageobjects.business.sections.Products;
import com.mhra.mdcm.devices.appian.session.SessionKey;
import com.mhra.mdcm.devices.appian.steps.common.CommonSteps;
import com.mhra.mdcm.devices.appian.utils.selenium.page.AssertUtils;
import com.mhra.mdcm.devices.appian.utils.selenium.page.PageUtils;
import com.mhra.mdcm.devices.appian.utils.selenium.page.WaitUtils;
import cucumber.api.PendingException;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.springframework.context.annotation.Scope;

import java.util.List;
import java.util.Map;

/**
 * Created by TPD_Auto
 */
@Scope("cucumber-glue")
public class RecordsPageSteps extends CommonSteps {

    @When("^I go to records page and click on \"([^\"]*)\"$")
    public void iGoToPage(String page) throws Throwable {
        recordsPage = mainNavigationBar.clickRecords();

        if (page.equals("Accounts")) {
            accounts = recordsPage.clickOnAccounts();
        } else if (page.equals("Devices")) {
            devices = recordsPage.clickOnDevices();
        } else if (page.equals("Products")) {
            products = recordsPage.clickOnProducts();
        } else if (page.equals("All Organisations")) {
            allOrganisations = recordsPage.clickOnAllOrganisations();
        }
    }

    @Then("^I should see items and heading \"([^\"]*)\" for link \"([^\"]*)\"$")
    public void iShouldSeeItems(String expectedHeadings, String page) throws Throwable {

        boolean isHeadingVisibleAndCorrect = false;
        boolean isItemsDisplayedAndCorrect = false;

        if (page.equals("Accounts")) {
            isHeadingVisibleAndCorrect =  accounts.isHeadingCorrect(expectedHeadings);
            isItemsDisplayedAndCorrect = accounts.isItemsDisplayed(expectedHeadings);
        } else if (page.equals("Devices")) {
            isHeadingVisibleAndCorrect = devices.isHeadingCorrect(expectedHeadings);
            isItemsDisplayedAndCorrect = devices.isItemsDisplayed(expectedHeadings);
        } else if (page.equals("Products")) {
            isHeadingVisibleAndCorrect = products.isHeadingCorrect(expectedHeadings);
            isItemsDisplayedAndCorrect = products.isItemsDisplayed(expectedHeadings);
        } else if (page.equals("All Organisations")) {
            isHeadingVisibleAndCorrect = allOrganisations.isHeadingCorrect(expectedHeadings);
            isItemsDisplayedAndCorrect = allOrganisations.isItemsDisplayed(expectedHeadings);
        }

        //Verify results
        Assert.assertThat("Heading should be : " + expectedHeadings, isHeadingVisibleAndCorrect, Matchers.is(true));
        Assert.assertThat("Expected to see at least 1 item", isItemsDisplayedAndCorrect, Matchers.is(true));
    }

    @Then("^I should see the following columns for \"([^\"]*)\" page$")
    public void i_should_see_the_following_columns(String page, Map<String, String> dataValues) throws Throwable {
        String columnsDelimitedTxt = dataValues.get("columns");
        String[] columns = columnsDelimitedTxt.split(",");
        log.info("Expected columns : " + columns);

        List<String> tableColumnsNotFound = null;

        if (page.equals("Accounts")) {
            tableColumnsNotFound = accounts.isTableColumnCorrect(columns);
        } else if (page.equals("Devices")) {
        } else if (page.equals("Products")) {
        } else if (page.equals("All Organisations")) {
            tableColumnsNotFound = allOrganisations.isTableColumnCorrect(columns);
        }

        Assert.assertThat("Following columns not found : " + tableColumnsNotFound, tableColumnsNotFound.size() == 0, Matchers.is(true));
    }

//    @Then("^I should see the following columns for \"([^\"]*)\" page$")
//    public void i_should_see_the_following_columns_to(List<String> dataValues, String page) throws Throwable {
//        String columnsDelimitedTxt = dataValues.get(0);
//        String[] columns = columnsDelimitedTxt.split(",");
//        log.info("Expected columns : " + columns);
//
//        List<String> tableColumnsNotFound = null;
//
//        if (page.equals("Accounts")) {
//            tableColumnsNotFound = accounts.isTableColumnCorrect(columns);
//        } else if (page.equals("Devices")) {
//        } else if (page.equals("Products")) {
//        } else if (page.equals("All Organisations")) {
//        }
//
//        Assert.assertThat("Following columns not found : " + tableColumnsNotFound, tableColumnsNotFound.size() == 0, Matchers.is(true));
//    }


    @When("^I search for a \"([^\"]*)\" organisation$")
    public void i_search_for_a_organisation(String existing) throws Throwable {
        boolean exists = true;
        if(existing.contains("non") || existing.contains("not")){
            exists = false;
        }

        String organisationName = allOrganisations.getRandomOrganisation(exists);
        allOrganisations = allOrganisations.searchForOrganisation(organisationName);

        scenarioSession.putData(SessionKey.organisationName, organisationName);
    }

    @Then("^The search result should \"([^\"]*)\" the organisation$")
    public void the_search_result_should_contain_the_organisation(String contain) throws Throwable {
        String organisationName = (String) scenarioSession.getData(SessionKey.organisationName);
        int count = allOrganisations.getNumberOfMatches();
        if(contain.contains("not")){
            Assert.assertThat("Searching for " + organisationName + " should return 0 matches, but it was : " + count, count == 0, Matchers.is(true));
        }else{
            //Search was performed with an existing organisation
            Assert.assertThat("Searching for " + organisationName + " should return at least 1 matches, but it was : " + count, count >= 1, Matchers.is(true));
        }
    }

}
