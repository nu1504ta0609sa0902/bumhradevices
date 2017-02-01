package com.mhra.mdcm.devices.appian.steps.d1.business;

import com.mhra.mdcm.devices.appian.domains.newaccounts.AccountRequest;
import com.mhra.mdcm.devices.appian.pageobjects.MainNavigationBar;
import com.mhra.mdcm.devices.appian.pageobjects.business.sections.records.BusinessProductDetails;
import com.mhra.mdcm.devices.appian.session.SessionKey;
import com.mhra.mdcm.devices.appian.steps.common.CommonSteps;
import com.mhra.mdcm.devices.appian.utils.selenium.others.TestHarnessUtils;
import com.mhra.mdcm.devices.appian.utils.selenium.page.WaitUtils;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import static org.hamcrest.Matchers.*;

import org.junit.Assert;
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
        } else if (page.equals("All Devices")) {
            devices = recordsPage.clickOnAllDevices();
        } else if (page.equals("All Products")) {
            products = recordsPage.clickOnAllProducts();
        }
    }

    @When("^I go to records page$")
    public void iGoToRecordsPage() throws Throwable {
        recordsPage = mainNavigationBar.clickRecords();
    }

    @Then("^I should see items and heading \"([^\"]*)\" for link \"([^\"]*)\"$")
    public void iShouldSeeItems(String expectedHeadings, String page) throws Throwable {

        boolean isHeadingVisibleAndCorrect = false;
        boolean isItemsDisplayedAndCorrect = false;

        if (page.equals("Accounts")) {
            isHeadingVisibleAndCorrect = accounts.isHeadingCorrect(expectedHeadings);
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
        } else if (page.equals("All Devices")) {
            isHeadingVisibleAndCorrect = allDevices.isHeadingCorrect(expectedHeadings);
            isItemsDisplayedAndCorrect = allDevices.isItemsDisplayed(expectedHeadings);
        } else if (page.equals("All Products")) {
            isHeadingVisibleAndCorrect = allProducts.isHeadingCorrect(expectedHeadings);
            isItemsDisplayedAndCorrect = allProducts.isItemsDisplayed(expectedHeadings);
        }

        //Verify results
        Assert.assertThat("Heading should be : " + expectedHeadings, isHeadingVisibleAndCorrect, is(true));
        Assert.assertThat("Expected to see at least 1 item", isItemsDisplayedAndCorrect, is(true));
    }

    @Then("^I should see the following columns for \"([^\"]*)\" page$")
    public void i_should_see_the_following_columns(String page, Map<String, String> dataValues) throws Throwable {
        String columnsDelimitedTxt = dataValues.get("columns");
        String[] columns = columnsDelimitedTxt.split(",");
        log.info("Expected columns : " + columnsDelimitedTxt);

        List<String> tableColumnsNotFound = null;

        if (page.equals("Accounts")) {
            tableColumnsNotFound = accounts.isTableColumnCorrect(columns);
        } else if (page.equals("Devices")) {
        } else if (page.equals("Products")) {
        } else if (page.equals("All Products")) {
            tableColumnsNotFound = allProducts.isTableColumnCorrect(columns);
        } else if (page.equals("All Organisations")) {
            tableColumnsNotFound = allOrganisations.isTableColumnCorrect(columns);
        }

        Assert.assertThat("Following columns not found : " + tableColumnsNotFound, tableColumnsNotFound.size() == 0, is(true));
    }

    @When("^I perform a search for \"([^\"]*)\" in \"([^\"]*)\" page$")
    public void i_search_for_a_organisation(String searchTerm, String page) throws Throwable {
        if (page.equals("Accounts")) {
            accounts = accounts.searchForAccount(searchTerm);
        } else if (page.equals("Devices")) {
        } else if (page.equals("Products")) {
        } else if (page.equals("All Products")) {
            allProducts = allProducts.searchForAllProducts(searchTerm);
        } else if (page.equals("All Organisations")) {
            allOrganisations = allOrganisations.searchForAllOrganisation(searchTerm);
        }

        scenarioSession.putData(SessionKey.searchTerm, searchTerm);
    }

    @When("^I perform a search for random account organisation or product in \"([^\"]*)\" page$")
    public void i_search_for_a_random_value_in_the_specified_page(String page) throws Throwable {
        String searchTerm = "";
        if (page.equals("Accounts")) {
            searchTerm = accounts.getARandomAccount();
            accounts = accounts.searchForAccount(searchTerm);
        } else if (page.equals("Devices")) {
        } else if (page.equals("Products")) {
        } else if (page.equals("All Products")) {
            searchTerm = allProducts.getARandomProductEntry();
            allProducts = allProducts.searchForAllProducts(searchTerm);
        } else if (page.equals("All Organisations")) {
            searchTerm = allOrganisations.getRandomOrganisation(true);
            allOrganisations = allOrganisations.searchForAllOrganisation(searchTerm);
        }

        scenarioSession.putData(SessionKey.searchTerm, searchTerm);
    }

    @When("^I search for a \"([^\"]*)\" organisation$")
    public void i_search_for_a_organisation(String existing) throws Throwable {
        boolean exists = true;
        if (existing.contains("non") || existing.contains("not")) {
            exists = false;
        }

        String organisationName = allOrganisations.getRandomOrganisation(exists);
        allOrganisations = allOrganisations.searchForAllOrganisation(organisationName);

        scenarioSession.putData(SessionKey.organisationName, organisationName);
    }

    @When("^I search for a stored organisation in all organisation page$")
    public void i_search_for_a_stored_organisation(String existing) throws Throwable {
        String organisationName = (String) scenarioSession.getData(SessionKey.organisationName);
        allOrganisations = allOrganisations.searchForAllOrganisation(organisationName);

        scenarioSession.putData(SessionKey.organisationName, organisationName);
    }

    @Then("^All organisation search result should return (\\d+) matches$")
    public void the_allorganisation_search_result_should_contain_the_organisation(int matchCount) throws Throwable {
        String organisationName = (String) scenarioSession.getData(SessionKey.organisationName);
        int count = allOrganisations.getNumberOfMatches();
        if (matchCount == 0) {
            Assert.assertThat("Searching for " + organisationName + " should return 0 matches, but it was : " + count, count == 0, is(true));
        } else {
            //Search was performed with an existing organisation
            Assert.assertThat("Searching for " + organisationName + " should return at least 1 matches, but it was : " + count, count >= matchCount, is(true));
        }
    }


    @When("^I search accounts for the stored organisation name$")
    public void i_search_accounts_for_stored_organisation() throws Throwable {
        String orgName = (String) scenarioSession.getData(SessionKey.organisationName);
        //orgName = "OrganisationTest2610146859";   //Approved
        //orgName = "OrganisationTest261074026";    //Rejected
        //Go to records page
        mainNavigationBar = new MainNavigationBar(driver);
        recordsPage = mainNavigationBar.clickRecords();
        accounts = recordsPage.clickOnAccounts();
        accounts = accounts.searchForAccount(orgName);

    }

    @When("^I search for account with following text \"([^\"]*)\"$")
    public void i_search_for(String searchTerm) throws Throwable {
        accounts = accounts.searchForAccount(searchTerm);
        scenarioSession.putData(SessionKey.searchTerm, searchTerm);
        scenarioSession.putData(SessionKey.organisationName, searchTerm);
    }


    @When("^I should see at least (\\d+) account matches$")
    public void i_should_see_account_matches(int expectedMinCount) throws Throwable {
        String orgName = (String) scenarioSession.getData(SessionKey.organisationName);
        boolean atLeast1Match = accounts.atLeast1MatchFound(orgName);
        if (expectedMinCount == 0) {
            Assert.assertThat("Expected to see no matches ", atLeast1Match, is(false));
        } else {
            Assert.assertThat("Expected to see atleast 1 matches", atLeast1Match, is(true));
        }
    }

    @When("^I should see at least (\\d+) matches in \"([^\"]*)\" page search results$")
    public void i_should_see_at_least_X_matches_in_page_search_results(int expectedMinCount, String page) throws Throwable {
        String searchTerm = (String) scenarioSession.getData(SessionKey.searchTerm);
        boolean atLeast1Match = accounts.atLeast1MatchFound(searchTerm);

        if (page.equals("Accounts")) {
            atLeast1Match = accounts.atLeast1MatchFound(searchTerm);
        } else if (page.equals("Devices")) {
        } else if (page.equals("Products")) {
        } else if (page.equals("All Products")) {
            atLeast1Match = allProducts.atLeast1MatchFound(searchTerm);
        } else if (page.equals("All Organisations")) {
            atLeast1Match = allOrganisations.atLeast1MatchFound(searchTerm);
        }

        //Assert the expected results
        if (expectedMinCount == 0) {
            Assert.assertThat("Expected to see no matches ", atLeast1Match, is(false));
        } else {
            Assert.assertThat("Expected to see atleast 1 matches", atLeast1Match, is(true));
        }

    }

    @When("^I view a random product by \"([^\"]*)\"$")
    public void i_view_a_random_product_by(String tableHeading) throws Throwable {
        businessProductDetails = allProducts.viewProductBy(tableHeading);
    }

    @When("^I should see no account matches$")
    public void i_should_see_no_account_matches() throws Throwable {
        String orgName = (String) scenarioSession.getData(SessionKey.organisationName);
        boolean atLeast1Match = accounts.atLeast1MatchFound(orgName);
        Assert.assertThat("Expected to see no matches ", atLeast1Match, is(false));
    }


    @When("^I view a randomly selected account$")
    public void i_view_a_randomly_selected_account() throws Throwable {
        String randomAccountName = accounts.getARandomAccount();
        accounts = accounts.viewSpecifiedAccount(randomAccountName);
    }

    @When("^I view a randomly searched account and update the following data \"([^\"]*)\"$")
    public void i_view_a_randomly_selected_account(String keyValuePairToUpdate) throws Throwable {
        String searchTerm = (String) scenarioSession.getData(SessionKey.searchTerm);
        String randomAccountName = accounts.getARandomAccountWithText(searchTerm);
        accounts = accounts.viewSpecifiedAccount(randomAccountName);
        log.info("Account to update : " + randomAccountName);

        //Edit the data now
        AccountRequest updatedData = new AccountRequest(scenarioSession);
        updatedData.updateName(searchTerm);
        editAccounts = accounts.gotoEditAccountInformation();
        accounts = editAccounts.editAccountInformation(keyValuePairToUpdate, updatedData);

        scenarioSession.putData(SessionKey.organisationName, randomAccountName);
        scenarioSession.putData(SessionKey.updatedData, updatedData);
    }


    @When("^I select a random account and update the following data \"([^\"]*)\"$")
    public void i_update_the_following_data_pair_for_randomly_selected_account_data(String keyValuePairToUpdate) throws Throwable {
        //Select a random account
        String randomAccountName = accounts.getARandomAccount();
        accounts = accounts.viewSpecifiedAccount(randomAccountName);
        log.info("Edit the following account : " + randomAccountName);

        //Edit the data now
        AccountRequest updatedData = new AccountRequest(scenarioSession);
        editAccounts = accounts.gotoEditAccountInformation();
        accounts = editAccounts.editAccountInformation(keyValuePairToUpdate, updatedData);

        scenarioSession.putData(SessionKey.organisationName, randomAccountName);
        scenarioSession.putData(SessionKey.updatedData, updatedData);
    }


    @Then("^I should see the changes \"([^\"]*)\" in the account page$")
    public void i_should_see_the_changes_in_the_account_page(String keyValuePairToUpdate) throws Throwable {
        boolean isCorrectPage = accounts.isCorrectPage();
        AccountRequest updatedData = (AccountRequest) scenarioSession.getData(SessionKey.updatedData);
        boolean updatesFound = false;
        int numberOfRefresh = 0;
        do {
            numberOfRefresh++;
            //A bug : refresh is required
            driver.navigate().refresh();
            updatesFound = accounts.verifyUpdatesDisplayedOnPage(keyValuePairToUpdate, updatedData);
            if (!updatesFound) {
                WaitUtils.nativeWaitInSeconds(1);
            }
        } while (!updatesFound && numberOfRefresh < 3);

        Assert.assertThat("Expected to see following updates : " + keyValuePairToUpdate, updatesFound, is(true));
    }

    @Then("^The items in \"([^\"]*)\" page are displayed in alphabetical order$")
    public void the_items_are_displayed_in_alphabetical_order(String page) throws Throwable {
        boolean isOrderAtoZ = false;
        if (page.equals("Accounts")) {
            isOrderAtoZ = accounts.isOrderedAtoZ();
        } else if (page.equals("All Organisations")) {
            isOrderAtoZ = allOrganisations.isOrderedAtoZ();
        }

        Assert.assertThat("Default ordering of organisation name should be A to Z", isOrderAtoZ, is(true));

    }


    @When("^I filter items in \"([^\"]*)\" page by organisation role \"([^\"]*)\"$")
    public void filter_by_organisation_role(String page, String organisationRole) throws Throwable {
        if (page.equals("Accounts")) {
            accounts = accounts.filterBy(organisationRole);
        } else if (page.equals("All Organisations")) {
            allOrganisations = allOrganisations.filterBy(organisationRole);
        }
    }

    @When("^I sort items in \"([^\"]*)\" page by \"([^\"]*)\"$")
    public void i_sort_by(String page, String tableHeading) throws Throwable {
        if (page.equals("Accounts")) {
            accounts = accounts.sortBy(tableHeading, 1);
        } else if (page.equals("All Organisations")) {
            allOrganisations = allOrganisations.sortBy(tableHeading, 2);
        }
    }

    @Then("^I should see only see organisation of type \"([^\"]*)\" in \"([^\"]*)\" page$")
    public void i_should_see_only_see_organisation_of_type(String organisationType, String page) throws Throwable {
        boolean isOrganisationTypeAllSame = false;
        if (page.equals("Accounts")) {
            isOrganisationTypeAllSame = accounts.areAllOrganisationRoleOfType(organisationType);
        } else if (page.equals("All Organisations")) {
            isOrganisationTypeAllSame = allOrganisations.areAllOrganisationRoleOfType(organisationType);
        }
        Assert.assertThat("Organisation Roles Should Be Of Type : " + organisationType, isOrganisationTypeAllSame, is(true));
    }
}
