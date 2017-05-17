package com.mhra.mdcm.devices.appian.steps.d1.business;

import com.mhra.mdcm.devices.appian.domains.newaccounts.AccountRequestDO;
import com.mhra.mdcm.devices.appian.enums.LinksRecordPage;
import com.mhra.mdcm.devices.appian.pageobjects.MainNavigationBar;
import com.mhra.mdcm.devices.appian.session.SessionKey;
import com.mhra.mdcm.devices.appian.steps.common.CommonSteps;
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

        if (page.equals(LinksRecordPage.LINK_ACCOUNTS.link)) {
            accounts = recordsPage.clickOnAccounts();
        } else if (page.equals(LinksRecordPage.LINK_REGISTERED_DEVICES.link)) {
            registeredDevices = recordsPage.clickOnRegisteredDevices();
        } else if (page.equals(LinksRecordPage.LINK_ORGANISATIONS.link)) {
            organisations = recordsPage.clickOnOrganisations();
        } else if (page.equals(LinksRecordPage.LINK_GMDN_DEVICES.link)) {
            registeredDevices = recordsPage.clickOnGMDNDevices();
        } else if (page.equals(LinksRecordPage.LINK_REGISTERED_PRODUCTS.link)) {
            registeredProducts = recordsPage.clickOnRegisteredProducts();
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

        if (page.equals(LinksRecordPage.LINK_ACCOUNTS.link)) {
            isHeadingVisibleAndCorrect = accounts.isHeadingCorrect(expectedHeadings);
            isItemsDisplayedAndCorrect = accounts.isItemsDisplayed(expectedHeadings);
        } else if (page.equals(LinksRecordPage.LINK_REGISTERED_DEVICES.link)) {
            isHeadingVisibleAndCorrect = registeredDevices.isHeadingCorrect(expectedHeadings);
            isItemsDisplayedAndCorrect = registeredDevices.isItemsDisplayed(expectedHeadings);
        } else if (page.equals(LinksRecordPage.LINK_ORGANISATIONS.link)) {
            isHeadingVisibleAndCorrect = organisations.isHeadingCorrect(expectedHeadings);
            isItemsDisplayedAndCorrect = organisations.isItemsDisplayed(expectedHeadings);
        } else if (page.equals(LinksRecordPage.LINK_GMDN_DEVICES.link)) {
            isHeadingVisibleAndCorrect = devicesGMDN.isHeadingCorrect(expectedHeadings);
            isItemsDisplayedAndCorrect = devicesGMDN.isItemsDisplayed(expectedHeadings);
        } else if (page.equals(LinksRecordPage.LINK_REGISTERED_PRODUCTS.link)) {
            isHeadingVisibleAndCorrect = registeredProducts.isHeadingCorrect(expectedHeadings);
            isItemsDisplayedAndCorrect = registeredProducts.isItemsDisplayed(expectedHeadings);
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

        if (page.equals(LinksRecordPage.LINK_ACCOUNTS.link)) {
            tableColumnsNotFound = accounts.isTableColumnCorrect(columns);
        } else if (page.equals(LinksRecordPage.LINK_REGISTERED_DEVICES.link)) {
        } else if (page.equals(LinksRecordPage.LINK_GMDN_DEVICES.link)) {
            tableColumnsNotFound = devicesGMDN.isTableColumnCorrect(columns);
        } else if (page.equals(LinksRecordPage.LINK_REGISTERED_PRODUCTS.link)) {
            tableColumnsNotFound = registeredProducts.isTableColumnCorrect(columns);
        } else if (page.equals(LinksRecordPage.LINK_ORGANISATIONS.link)) {
            tableColumnsNotFound = organisations.isTableColumnCorrect(columns);
        }

        Assert.assertThat("Following columns not found : " + tableColumnsNotFound, tableColumnsNotFound.size() == 0, is(true));
    }

    @When("^I perform a search for \"([^\"]*)\" in \"([^\"]*)\" page$")
    public void i_search_for_a_organisation(String searchTerm, String page) throws Throwable {
        if (page.equals(LinksRecordPage.LINK_ACCOUNTS.link)) {
            accounts = accounts.searchForAccount(searchTerm);
        } else if (page.equals(LinksRecordPage.LINK_REGISTERED_DEVICES.link)) {
        } else if (page.equals(LinksRecordPage.LINK_GMDN_DEVICES.link)) {
            devicesGMDN = devicesGMDN.searchForAllDevices(searchTerm);
        } else if (page.equals(LinksRecordPage.LINK_REGISTERED_PRODUCTS.link)) {
            registeredProducts = registeredProducts.searchForAllProducts(searchTerm);
        } else if (page.equals(LinksRecordPage.LINK_ORGANISATIONS.link)) {
            organisations = organisations.searchForAllOrganisation(searchTerm);
        }

        scenarioSession.putData(SessionKey.searchTerm, searchTerm);
    }

    @When("^I perform a search for random account organisation or product in \"([^\"]*)\" page$")
    public void i_search_for_a_random_value_in_the_specified_page(String page) throws Throwable {
        String searchTerm = "";
        if (page.equals(LinksRecordPage.LINK_ACCOUNTS.link)) {
            searchTerm = accounts.getARandomAccount();
            accounts = accounts.searchForAccount(searchTerm);
        } else if (page.equals(LinksRecordPage.LINK_REGISTERED_DEVICES.link)) {
        } else if (page.equals(LinksRecordPage.LINK_GMDN_DEVICES.link)) {
            searchTerm = devicesGMDN.getARandomGMDNCode();
            devicesGMDN = devicesGMDN.searchForAllDevices(searchTerm);
        } else if (page.equals(LinksRecordPage.LINK_REGISTERED_PRODUCTS.link)) {
            searchTerm = registeredProducts.getARandomProductEntry();
            registeredProducts = registeredProducts.searchForAllProducts(searchTerm);
        } else if (page.equals(LinksRecordPage.LINK_ORGANISATIONS.link)) {
            searchTerm = organisations.getRandomOrganisation(true);
            organisations = organisations.searchForAllOrganisation(searchTerm);
        }

        scenarioSession.putData(SessionKey.searchTerm, searchTerm);
    }



    @When("^I search for stored organisation in \"([^\"]*)\" page$")
    public void i_search_for_stored_organisation_in_specified_page(String page) throws Throwable {
        String searchTerm = (String) scenarioSession.getData(SessionKey.organisationName);
        if (page.equals(LinksRecordPage.LINK_ACCOUNTS.link)) {
            accounts = accounts.searchForAccount(searchTerm);
        } else if (page.equals(LinksRecordPage.LINK_REGISTERED_DEVICES.link)) {
        } else if (page.equals(LinksRecordPage.LINK_GMDN_DEVICES.link)) {
            devicesGMDN = devicesGMDN.searchForAllDevices(searchTerm);
        } else if (page.equals(LinksRecordPage.LINK_REGISTERED_PRODUCTS.link)) {
            registeredProducts = registeredProducts.searchForAllProducts(searchTerm);
        } else if (page.equals(LinksRecordPage.LINK_ORGANISATIONS.link)) {
            organisations = organisations.searchForAllOrganisation(searchTerm);
        }

        scenarioSession.putData(SessionKey.searchTerm, searchTerm);
    }

    @When("^I search for a \"([^\"]*)\" organisation$")
    public void i_search_for_a_organisation(String existing) throws Throwable {
        boolean exists = true;
        if (existing.contains("non") || existing.contains("not")) {
            exists = false;
        }

        String organisationName = organisations.getRandomOrganisation(exists);
        organisations = organisations.searchForAllOrganisation(organisationName);

        scenarioSession.putData(SessionKey.organisationName, organisationName);
    }

    @When("^I search for a stored organisation in all organisation page$")
    public void i_search_for_a_stored_organisation(String existing) throws Throwable {
        String organisationName = (String) scenarioSession.getData(SessionKey.organisationName);
        organisations = organisations.searchForAllOrganisation(organisationName);

        scenarioSession.putData(SessionKey.organisationName, organisationName);
    }

    @Then("^All organisation search result should return (\\d+) matches$")
    public void the_allorganisation_search_result_should_contain_the_organisation(int matchCount) throws Throwable {
        String organisationName = (String) scenarioSession.getData(SessionKey.organisationName);
        int count = organisations.getNumberOfMatches();
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
        //Go to records page
        mainNavigationBar = new MainNavigationBar(driver);
        recordsPage = mainNavigationBar.clickRecords();
        accounts = recordsPage.clickOnAccounts();
        accounts = accounts.searchForAccount(orgName);

    }

    @When("^I search for account with following text \"([^\"]*)\"$")
    public void i_search_for(String searchTerm) throws Throwable {
        accounts = accounts.searchForAccount(searchTerm);
        accounts.isSearchingCompleted();
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

        if (page.equals(LinksRecordPage.LINK_ACCOUNTS.link)) {
            atLeast1Match = accounts.atLeast1MatchFound(searchTerm);
        } else if (page.equals(LinksRecordPage.LINK_REGISTERED_DEVICES.link)) {
        } else if (page.equals(LinksRecordPage.LINK_GMDN_DEVICES.link)) {
            atLeast1Match = devicesGMDN.atLeast1MatchFound(searchTerm);
        } else if (page.equals(LinksRecordPage.LINK_REGISTERED_PRODUCTS.link)) {
            atLeast1Match = registeredProducts.atLeast1MatchFound(searchTerm);
        } else if (page.equals(LinksRecordPage.LINK_ORGANISATIONS.link)) {
            atLeast1Match = organisations.atLeast1MatchFound(searchTerm);
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
        businessProductDetails = registeredProducts.viewProductBy(tableHeading);
    }

    @When("^I should see no account matches$")
    public void i_should_see_no_account_matches() throws Throwable {
        String orgName = (String) scenarioSession.getData(SessionKey.organisationName);
        boolean atLeast1Match = accounts.atLeast1MatchFound(orgName);
        Assert.assertThat("Expected to see no matches ", atLeast1Match, is(false));
    }


    @When("^I view a random account from search result$")
    public void i_view_a_randomly_selected_account() throws Throwable {
        String randomAccountName = accounts.getARandomAccount();
        log.info("View Account : " + randomAccountName);
        viewAccount = accounts.viewSpecifiedAccount(randomAccountName);
    }


    @When("^I view a randomly searched account and update the following data \"([^\"]*)\"$")
    public void i_view_a_randomly_selected_account(String keyValuePairToUpdate) throws Throwable {
        String searchTerm = (String) scenarioSession.getData(SessionKey.searchTerm);
        String randomAccountName = accounts.getARandomAccountWithText(searchTerm);
        viewAccount = accounts.viewSpecifiedAccount(randomAccountName);
        log.info("Account to update : " + randomAccountName);

        //Edit the data now
        AccountRequestDO updatedData = new AccountRequestDO(scenarioSession);
        updatedData.updateName(randomAccountName);
        editAccounts = viewAccount.gotoEditAccountInformation();
        accounts = editAccounts.editAccountInformation(keyValuePairToUpdate, updatedData);

        scenarioSession.putData(SessionKey.organisationName, randomAccountName);
        scenarioSession.putData(SessionKey.updatedData, updatedData);
    }

    @Then("^I should see account displaying correct fields$")
    public void i_should_see_account_displaying_correct_fields() throws Throwable {
        boolean isCorrect = viewAccount.verifyCorrectFieldsDisplayedOnPage();
        Assert.assertThat("Account, Organisation Details and Contact Details May Not Be Valid", isCorrect, is(true));
    }


    @Then("^I should see associated organisations related to this account$")
    public void i_should_see_associated_organisations_related_to_this_account() throws Throwable {
        boolean isDisplayed = viewAccount.isDisplayingAssociatedOrganisations();
        Assert.assertThat("Expected to see list of associated orgs", isDisplayed, is(true));
    }


    @When("^I select a random account and update the following data \"([^\"]*)\"$")
    public void i_update_the_following_data_pair_for_randomly_selected_account_data(String keyValuePairToUpdate) throws Throwable {
        //Select a random account
        String randomAccountName = accounts.getARandomAccount();
        viewAccount = accounts.viewSpecifiedAccount(randomAccountName);
        log.info("Edit the following account : " + randomAccountName);

        //Edit the data now
        AccountRequestDO updatedData = new AccountRequestDO(scenarioSession);
        editAccounts = viewAccount.gotoEditAccountInformation();
        accounts = editAccounts.editAccountInformation(keyValuePairToUpdate, updatedData);

        scenarioSession.putData(SessionKey.organisationName, randomAccountName);
        scenarioSession.putData(SessionKey.updatedData, updatedData);
    }


    @Then("^I should see the changes \"([^\"]*)\" in the account page$")
    public void i_should_see_the_changes_in_the_account_page(String keyValuePairToUpdate) throws Throwable {
        //boolean isCorrectPage = accounts.isCorrectPage();
        AccountRequestDO updatedData = (AccountRequestDO) scenarioSession.getData(SessionKey.updatedData);
        boolean updatesFound = false;
        int numberOfRefresh = 0;
        do {
            numberOfRefresh++;
            boolean isCorrectPage = viewAccount.isEditAccountInformationButtonDisplayed();
            if(isCorrectPage) {
                //A bug : refresh is required
                viewAccount = viewAccount.refreshThePage();
                updatesFound = viewAccount.verifyUpdatesDisplayedOnPage(keyValuePairToUpdate, updatedData);
                if (!updatesFound) {
                    WaitUtils.nativeWaitInSeconds(1);
                }
            }
        } while (!updatesFound && numberOfRefresh < 3);

        Assert.assertThat("Expected to see following updates : " + keyValuePairToUpdate, updatesFound, is(true));
    }

    @Then("^The items in \"([^\"]*)\" page are displayed in alphabetical order$")
    public void the_items_are_displayed_in_alphabetical_order(String page) throws Throwable {
        boolean isOrderAtoZ = false;
        if (page.equals(LinksRecordPage.LINK_ACCOUNTS.link)) {
            isOrderAtoZ = accounts.isOrderedAtoZ();
        } else if (page.equals(LinksRecordPage.LINK_ORGANISATIONS.link)) {
            isOrderAtoZ = organisations.isOrderedAtoZ();
        }

        Assert.assertThat("Either list is not ordered A to Z OR there are duplicates", isOrderAtoZ, is(true));

    }

    @When("^I filter by \"([^\"]*)\" for the value \"([^\"]*)\" in \"([^\"]*)\" page$")
    public void i_filter_by_for_the_value_in_page(String filterBy, String value, String page) throws Throwable {

        String searchTerm = (String) scenarioSession.getData(SessionKey.searchTerm);

        //Filter by organisation role
        if (page.equals(LinksRecordPage.LINK_ACCOUNTS.link) && filterBy.contains("Organisation")) {
            accounts.isSearchingCompleted();
            //Filter accounts by organisation role
            accounts = accounts.filterByOrganistionRole(value);
        } else if (page.equals(LinksRecordPage.LINK_ORGANISATIONS.link) && filterBy.contains("Organisation")) {
            organisations.isSearchingCompleted();
            //Filter organisation by
            organisations = organisations.filterBy(value);
        }else if (page.equals(LinksRecordPage.LINK_ORGANISATIONS.link) && filterBy.contains("Registered")) {
            accounts.isSearchingCompleted();
            //Filter by registered status
            accounts = accounts.filterByRegisteredStatus(value);
        }else if (page.equals(LinksRecordPage.LINK_REGISTERED_PRODUCTS.link) && filterBy.contains("Device type")) {
            registeredProducts.isSearchingCompleted();
            //Filter by device type
            registeredProducts = registeredProducts.filterByDeviceType(value);
        }
    }

    @When("^I filter items in \"([^\"]*)\" page by device type \"([^\"]*)\"$")
    public void i_filter_items_in_page_by_device_type(String page, String deviceType) throws Throwable {
        if (page.equals(LinksRecordPage.LINK_ACCOUNTS.link)) {
        } else if (page.equals(LinksRecordPage.LINK_REGISTERED_DEVICES.link)) {
            registeredDevices = registeredDevices.filterBy(deviceType);
        } else if (page.equals(LinksRecordPage.LINK_GMDN_DEVICES.link)) {
            devicesGMDN = devicesGMDN.filterByDeviceType(deviceType);
        } else if (page.equals(LinksRecordPage.LINK_REGISTERED_PRODUCTS.link)) {
        } else if (page.equals(LinksRecordPage.LINK_ORGANISATIONS.link)) {
        }
    }

    @When("^I sort items in \"([^\"]*)\" page by \"([^\"]*)\"$")
    public void i_sort_by(String page, String tableHeading) throws Throwable {
        if (page.equals(LinksRecordPage.LINK_ACCOUNTS.link)) {
            accounts = accounts.sortBy(tableHeading, 1);
        } else if (page.equals(LinksRecordPage.LINK_ORGANISATIONS.link)) {
            organisations = organisations.sortBy(tableHeading, 2);
        } else if(page.equals(LinksRecordPage.LINK_REGISTERED_PRODUCTS.link)){
            registeredProducts = registeredProducts.sortBy(tableHeading, 1);
        } else if(page.equals(LinksRecordPage.LINK_REGISTERED_DEVICES.link)){
            //registeredDevices = registeredDevices.sortBy(tableHeading, 2);
        }
    }

    @Then("^I should see table column \"([^\"]*)\" displaying only \"([^\"]*)\" in \"([^\"]*)\" page$")
    public void i_should_see_table_column_only_displaying_in_page(String tableColumnName, String value, String page) throws Throwable {
        boolean isDataAsExpected = false;
        String searchTerm = (String) scenarioSession.getData(SessionKey.searchTerm);

        if (page.equals(LinksRecordPage.LINK_ACCOUNTS.link) && tableColumnName.toLowerCase().contains("role")) {
            accounts.isSearchingCompleted();
            //Filtered accounts by organisation role
            isDataAsExpected = accounts.areAllOrganisationRoleOfType(value);
        } else if (page.equals(LinksRecordPage.LINK_ORGANISATIONS.link) && tableColumnName.toLowerCase().contains("role")) {
            organisations.isSearchingCompleted();
            //Filter organisations by
            isDataAsExpected = organisations.areAllOrganisationRoleOfType(value);
        }else if (page.equals(LinksRecordPage.LINK_ORGANISATIONS.link) && tableColumnName.toLowerCase().contains("status")) {
            organisations.isSearchingCompleted();
            //Filtered by registered status
            isDataAsExpected = organisations.areAllStatusOfType(value);
        }else if (page.equals(LinksRecordPage.LINK_REGISTERED_PRODUCTS.link) && tableColumnName.toLowerCase().contains("device type")) {
            registeredProducts.isSearchingCompleted();
            //Filter by device type
            isDataAsExpected = registeredProducts.areAllProductOfType(value);
        }
        Assert.assertThat("Data may not be correct after filtering ", isDataAsExpected, is(true));
    }


    @Then("^I should see table column \"([^\"]*)\" also displaying \"([^\"]*)\" in \"([^\"]*)\" page$")
    public void i_should_see_table_column_also_displaying_in_page(String tableColumnName, String value, String page) throws Throwable {
        boolean isDataAsExpected = false;


        if (page.equals(LinksRecordPage.LINK_ACCOUNTS.link) && tableColumnName.toLowerCase().contains("role")) {
            //If filtered by organisation roles
            accounts.isSearchingCompleted();
            isDataAsExpected = accounts.areOrganisationOfRoleVisible(value);
        } else if (page.equals(LinksRecordPage.LINK_ORGANISATIONS.link) && tableColumnName.toLowerCase().contains("role")) {
            organisations.isSearchingCompleted();
            isDataAsExpected = organisations.areOrganisationOfRoleVisible(value);
        }else if (page.equals(LinksRecordPage.LINK_ORGANISATIONS.link) && tableColumnName.toLowerCase().contains("status")) {
            organisations.isSearchingCompleted();
            //If filtered by status
            isDataAsExpected = organisations.areStatusOfTypeVisible(value);
        }else if (page.equals(LinksRecordPage.LINK_REGISTERED_PRODUCTS.link) && tableColumnName.toLowerCase().contains("evice type")) {
            registeredProducts.isSearchingCompleted();
            //If filtered by device types
            isDataAsExpected = registeredProducts.areDevicesOfTypeVisible(value);
        }

        Assert.assertThat("Data may not be correct after filtering ", isDataAsExpected, is(true));
    }

    @Then("^I should see only see device of type \"([^\"]*)\" in \"([^\"]*)\" page$")
    public void i_should_see_only_see_device_of_type_in_page(String deviceType, String page) throws Throwable {
        boolean allSame = true;
        if (page.equals(LinksRecordPage.LINK_ACCOUNTS.link)) {
        } else if (page.equals(LinksRecordPage.LINK_REGISTERED_DEVICES.link)) {
            allSame = registeredDevices.areAllDevicesOfType(deviceType);
        } else if (page.equals(LinksRecordPage.LINK_GMDN_DEVICES.link)) {
            allSame = devicesGMDN.areAllDevicesOfType(deviceType);
        } else if (page.equals(LinksRecordPage.LINK_REGISTERED_PRODUCTS.link)) {
        } else if (page.equals(LinksRecordPage.LINK_ORGANISATIONS.link)) {
        }
        Assert.assertThat("Device Type Should Be Of Type : " + deviceType, allSame, is(true));
    }


    @When("^I clear the filter than I should see device of type \"([^\"]*)\"$")
    public void i_clear_the_filter_than_I_should_see_device_of_type(String deviceType) throws Throwable {
        devicesGMDN = devicesGMDN.clearFilter();
        boolean deviceTypeVisible = devicesGMDN.areDevicesOfTypeVisible(deviceType);
        Assert.assertThat("Expected To See Device Of Type : " + deviceType, deviceTypeVisible, is(true));
    }

    @When("^I clear the filter by \"([^\"]*)\" in \"([^\"]*)\" page$")
    public void i_clear_the_filter_in_page(String filterBy, String page) throws Throwable {

        //If filtered by organisation role
        if (page.equals(LinksRecordPage.LINK_ACCOUNTS.link) && filterBy.contains("Organisation")) {
            accounts = accounts.clearFilterByOrganisation();
        } else if (page.equals(LinksRecordPage.LINK_ORGANISATIONS.link) && filterBy.contains("Organisation")) {
            organisations = organisations.clearFilterByOrganisation();
        }

        //If filtered by registered status
        if (page.equals(LinksRecordPage.LINK_ORGANISATIONS.link) && filterBy.contains("status")) {
            organisations = organisations.clearFilterByStatus();
        }

        //If filtered by device type
        if (page.equals(LinksRecordPage.LINK_REGISTERED_PRODUCTS.link) && filterBy.contains("evice type")) {
            registeredProducts = registeredProducts.clearFilterByStatus();
        }
    }

    @When("^I click on a random gmdn in all devices page$")
    public void i_click_on_a_random_gmdn_in_all_devices_page() throws Throwable {
        devicesGMDN = devicesGMDN.clickOnARandomGMDNCode();
    }

    @Then("^I should see a list of manufacturers using this gmdn product$")
    public void i_should_see_a_list_of_manufacturers_using_this_gmdn_product() throws Throwable {
        boolean isListOfManufacturersVisible = devicesGMDN.isListOfManufacturersVisible();
        Assert.assertThat("Expected to list of manufacturers using the selected GMDN code", isListOfManufacturersVisible, is(true));
    }

    @Then("^I should see the following columns \"([^\"]*)\" for all devices list of manufacturer table$")
    public void i_should_see_the_following_columns_for_all_devices_list_of_manufacturer_table(String expectedColumns) throws Throwable {
        String[] columns = expectedColumns.split(",");
        boolean allColumnsVisible = devicesGMDN.isListOfManufacturersUsingDeviceTableColumnCorrect(columns);
        Assert.assertThat("Expected to see the following columns : " + expectedColumns, allColumnsVisible, is(true));
    }

    @When("^I click on edit account information$")
    public void iClickOnEditAccountInformation() throws Throwable {
        editAccounts = businessManufacturerDetails.gotoEditAccountInformation();
    }

    @Then("^I update PARD option to \"([^\"]*)\" for organisation \"([^\"]*)\"$")
    public void iShouldSeePARDOptionToBeSelected(String pardOption, String nameOrAddress) throws Throwable {
        businessManufacturerDetails = editAccounts.updatePARDOptionFor(pardOption, nameOrAddress);
    }

    @Then("^I update PARD options to \"([^\"]*)\" for both name and address$")
    public void iShouldSeePARDOptionsToBeSelected(String pardOptions) throws Throwable {
        businessManufacturerDetails = editAccounts.updatePARDOptionsFor(pardOptions);
    }

    @Then("^I should see PARD option \"([^\"]*)\" to be selected for \"([^\"]*)\"$")
    public void iShouldSeePARDOptionToBeSelectedForNameAndAddress(String pardOption, String nameOrAddress) throws Throwable {
        boolean correctOptionSelected = editAccounts.isPardOptionSelected(pardOption, nameOrAddress);
        Assert.assertThat("Expected to see the following option selected : " + pardOption + " for : " + nameOrAddress, correctOptionSelected, is(true));
    }

    @Then("^I should see following PARD \"([^\"]*)\" message$")
    public void iShouldSeeFollowingPARDMessage(String expectedMessage) throws Throwable {
        boolean isMessageCorrect = businessManufacturerDetails.isPARDMessaageCorrect(expectedMessage);
        Assert.assertThat("Expected to see the following message : " + expectedMessage, isMessageCorrect, is(true));
    }
}
