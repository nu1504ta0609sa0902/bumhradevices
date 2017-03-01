package com.mhra.mdcm.devices.appian.pageobjects.business.sections.records;

import com.mhra.mdcm.devices.appian.domains.newaccounts.AccountRequest;
import com.mhra.mdcm.devices.appian.pageobjects._Page;
import com.mhra.mdcm.devices.appian.utils.selenium.others.RandomDataUtils;
import com.mhra.mdcm.devices.appian.utils.selenium.page.AssertUtils;
import com.mhra.mdcm.devices.appian.utils.selenium.page.PageUtils;
import com.mhra.mdcm.devices.appian.utils.selenium.page.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TPD_Auto
 */
@Component
public class Accounts extends _Page {

    @FindBy(xpath = ".//h2[.='Status']//following::tr")
    List<WebElement> listOfAccounts;
    @FindBy(xpath = ".//td[1]")
    List<WebElement> listOfAccountsNames;
    @FindBy(xpath = ".//table//th")
    List<WebElement> listOfTableColumns;
    @FindBy(xpath = ".//*[.='Status']//following::tr//td[3]")
    List<WebElement> listOfOrganisationRoles;

    //TABLE Heading
    @FindBy(linkText = "Organisation name")
    WebElement thOrganisationName;

    //Edit information related to an account
    @FindBy(linkText = "Edit Account Information")
    WebElement editAccountInfoLink;
    @FindBy(xpath = ".//h4")
    WebElement orgName;

    //ORGANISATION DETAILS
    @FindBy(xpath = ".//span[.='Address line 1']//following::p[1]")
    WebElement orgAddressLine1;
    @FindBy(xpath = ".//span[.='Address line 2']//following::p[1]")
    WebElement orgAddressLine2;
    @FindBy(xpath = ".//span[contains(text(),'City')]//following::p[1]")
    WebElement orgCityTown;
    @FindBy(xpath = ".//span[.='Postcode']//following::p[1]")
    WebElement orgPostCode;
    @FindBy(xpath = ".//span[.='Country']//following::p[1]")
    WebElement orgCountry;
    @FindBy(xpath = ".//span[contains(text(),'Address type')]//following::input[1]")
    WebElement addressType;
    @FindBy(xpath = ".//span[contains(text(),'Telephone')]//following::p[1]")
    WebElement orgTelephone;
    @FindBy(xpath = ".//span[contains(text(),'Fax')]//following::p[1]")
    WebElement orgFax;
    @FindBy(xpath = ".//span[contains(text(),'Website')]//following::p[1]")
    WebElement webSite;

    //CONTACT PERSON DETAILS
    @FindBy(xpath = ".//span[contains(text(),'Job title')]//following::p[1]")
    WebElement jobTitle;
    @FindBy(xpath = ".//span[contains(text(),'Email')]//following::p[1]")
    WebElement emailAddress;
    @FindBy(xpath = ".//span[contains(text(),'Full')]//following::p[1]")
    WebElement fullName;
    @FindBy(xpath = ".//h3[contains(text(),'Person Details')]//following::span[.='Telephone']/following::p[1]")
    WebElement telephone;

    //Search box and filters
    @FindBy(xpath = ".//*[contains(@class, 'filter')]//following::input[1]")
    WebElement searchBox;
    @FindBy(css = ".selected")
    List<WebElement> listOfFilters;
    @FindBy(linkText = "Follow")
    WebElement followBtn;


    @Autowired
    public Accounts(WebDriver driver) {
        super(driver);
    }


    public boolean isHeadingCorrect(String expectedHeadings) {
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        WaitUtils.waitForElementToBeClickable(driver, By.xpath(".//h2[.='" + expectedHeadings + "']") , TIMEOUT_DEFAULT, false);
        WebElement heading = driver.findElement(By.xpath(".//h2[.='" + expectedHeadings + "']"));
        boolean contains = heading.getText().contains(expectedHeadings);
        return contains;
    }


    public boolean isItemsDisplayed(String expectedHeadings) {
        boolean itemsDisplayed = false;
        WaitUtils.waitForElementToBeClickable(driver, By.xpath(".//h2[.='" + expectedHeadings + "']") , TIMEOUT_DEFAULT, false);

        if(expectedHeadings.contains("Accounts")){
            itemsDisplayed = listOfAccounts.size() > 0;
        }

        return itemsDisplayed;
    }

    public List<String> isTableColumnCorrect(String[] columns) {
        WaitUtils.waitForElementToBeClickable(driver, By.xpath(".//table//th") , TIMEOUT_DEFAULT, false);
        List<String> columnsNotFound = PageUtils.areTheColumnsCorrect(columns, listOfTableColumns);
        return columnsNotFound;
    }

    public Accounts searchForAccount(String searchTerm) {
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        WaitUtils.waitForElementToBeClickable(driver, searchBox, TIMEOUT_30_SECOND, false);
        PageUtils.searchPageFor(searchTerm, searchBox);
        return new Accounts(driver);
    }

    public boolean atLeast1MatchFound(String searchText) {
        boolean atLeast1MatchFound = true;
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        WaitUtils.waitForElementToBeClickable(driver, By.cssSelector(".appian-informationPanel b"), TIMEOUT_40_SECOND, false);
        try{
            WaitUtils.waitForElementToBeClickable(driver, By.partialLinkText(searchText), TIMEOUT_5_SECOND, false);
            int actualCount = (listOfAccounts.size()-1);
            atLeast1MatchFound = actualCount >= 1;
        }catch (Exception e){
            log.error("Timeout : Trying to search");
            atLeast1MatchFound = false;
        }

        return atLeast1MatchFound;
    }

    /**
     * NOTE THERE MAY BE MORE THAN 1 LINK PER ROW
     * @return
     */
    public String getARandomAccount() {
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        //WaitUtils.forceWaitForPageToLoad(driver, By.xpath(".//h2[.='Status']//following::a[2]"), TIMEOUT_5_SECOND, 1);
        WaitUtils.waitForElementToBeClickable(driver, By.xpath(".//h2[.='Status']//following::a[2]"), TIMEOUT_5_SECOND, false);

        int position = RandomDataUtils.getSimpleRandomNumberBetween(1, listOfAccountsNames.size() - 1, false);
        WebElement accountLinks = listOfAccountsNames.get(position);
        String accountName = accountLinks.getText();
        return accountName;
    }

    public String getARandomAccountWithText(String name) {
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        //WaitUtils.forceWaitForPageToLoad(driver, By.xpath(".//h2[.='Status']//following::a[2]"), TIMEOUT_3_SECOND, 1);
        WaitUtils.waitForElementToBeClickable(driver, By.xpath(".//h2[.='Status']//following::a[2]"), TIMEOUT_5_SECOND, false);

        boolean found = false;
        int count = 0;
        String accountName = "";
        do {
            count++;
            int position = RandomDataUtils.getSimpleRandomNumberBetween(1, listOfAccountsNames.size() - 1, false);
            WebElement accountLinks = listOfAccountsNames.get(position);
            accountName = accountLinks.getText();
            if(accountName.contains(name)){
                found = true;
            }
        }while(!found && count <= 3);

        return accountName;
    }

    public Accounts viewSpecifiedAccount(String randomAccountName) {
        WaitUtils.waitForElementToBeClickable(driver, By.partialLinkText(randomAccountName), TIMEOUT_DEFAULT, false);
        WebElement accountLinks = driver.findElement(By.partialLinkText(randomAccountName));
        //accountLinks.click();
        PageUtils.doubleClick(driver, accountLinks);
        return new Accounts(driver);
    }

    public EditAccounts gotoEditAccountInformation() {
        WaitUtils.waitForElementToBeClickable(driver, By.cssSelector(".gwt-Anchor.pull-down-toggle"), TIMEOUT_5_SECOND, false);
        WaitUtils.waitForElementToBeClickable(driver, editAccountInfoLink, TIMEOUT_DEFAULT, false);
        PageUtils.doubleClick(driver, editAccountInfoLink);
        //editAccountInfoLink.click();
        return new EditAccounts(driver);
    }

    public boolean verifyUpdatesDisplayedOnPage(String keyValuePairToUpdate, AccountRequest updatedData) {
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        WaitUtils.waitForElementToBeVisible(driver, editAccountInfoLink, TIMEOUT_DEFAULT, false);
        WaitUtils.waitForElementToBeClickable(driver, editAccountInfoLink, TIMEOUT_5_SECOND, false);
        WaitUtils.waitForElementToBeClickable(driver, By.cssSelector(".gwt-Anchor.pull-down-toggle"), TIMEOUT_5_SECOND, false);
        boolean allChangesDisplayed = true;

        //Check for the following
        String[] dataPairs = keyValuePairToUpdate.split(",");

        for(String pairs: dataPairs){
            //String[] split = pairs.split("=");
            String key = pairs;

            if(key.equals("job.title")){
                allChangesDisplayed = AssertUtils.areChangesDisplayed(jobTitle,  updatedData.jobTitle);
            }else if(key.equals("org.name")){
                allChangesDisplayed = AssertUtils.areChangesDisplayed(orgName,  updatedData.organisationName);
            }else if(key.equals("address.line1")){
                allChangesDisplayed = AssertUtils.areChangesDisplayed(orgAddressLine1,  updatedData.address1);
            }else if(key.equals("address.line2")){
                allChangesDisplayed = AssertUtils.areChangesDisplayed(orgAddressLine2,  updatedData.address2);
            }else if(key.equals("city.town")){
                allChangesDisplayed = AssertUtils.areChangesDisplayed(orgCityTown,  updatedData.townCity);
            }else if(key.equals("country")){
                allChangesDisplayed = AssertUtils.areChangesDisplayed(orgCountry,  updatedData.country);
            }else if(key.equals("postcode")){
                allChangesDisplayed = AssertUtils.areChangesDisplayed(orgPostCode,  updatedData.postCode);
            }else if(key.equals("org.telephone")){
                allChangesDisplayed = AssertUtils.areChangesDisplayed(orgTelephone,  updatedData.telephone);
            }else if(key.equals("org.fax")){
                allChangesDisplayed = AssertUtils.areChangesDisplayed(orgFax,  updatedData.fax);
            }

            //Every single changes need to match
            if(!allChangesDisplayed){
                break;
            }
        }

        return allChangesDisplayed;
    }


    public boolean isCorrectPage() {
        try {
            WaitUtils.waitForElementToBeClickable(driver, followBtn, TIMEOUT_5_SECOND, false);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Accounts filterByOrganistionRole(String organisationRole) {
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        By by = By.partialLinkText(organisationRole);
        WaitUtils.waitForElementToBeClickable(driver, by, TIMEOUT_10_SECOND, false);
        WebElement element = driver.findElement(by);
        PageUtils.doubleClick(driver, element);
        return new Accounts(driver);
    }

    public Accounts filterByRegisteredStatus(String status) {
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        By by = By.linkText(status);
        WaitUtils.waitForElementToBeClickable(driver, by, TIMEOUT_10_SECOND, false);
        WebElement element = driver.findElement(by);
        PageUtils.doubleClick(driver, element);
        return new Accounts(driver);
    }

    public Accounts sortBy(String tableHeading, int numberOfTimesToClick) {
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        if (tableHeading.equals("Organisation name")) {
            for (int c = 0; c < numberOfTimesToClick; c++) {
                WaitUtils.waitForElementToBeClickable(driver, thOrganisationName, TIMEOUT_DEFAULT, false);
                thOrganisationName.click();
                WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
                //WaitUtils.nativeWaitInSeconds(2);
            }
        }

        return new Accounts(driver);
    }

    public boolean areAllOrganisationRoleOfType(String organisationType) {
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        boolean allMatched = true;
        for(WebElement el: listOfOrganisationRoles){
            String text = el.getText();
            log.info(text);
            if(!text.contains("revious") && !text.contains("ext")) {
                allMatched = text.contains(organisationType);
                if(!allMatched){
                    break;
                }
            }
        }

        return allMatched;
    }

    public boolean isOrderedAtoZ() {
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        WaitUtils.waitForElementToBeClickable(driver, By.xpath(".//h2[.='Status']//following::a[2]"), TIMEOUT_5_SECOND, false);
        boolean isOrderedAToZ = PageUtils.isOrderedAtoZ(listOfAccountsNames, 2);
        return isOrderedAToZ;
    }

    public boolean verifyCorrectFieldsDisplayedOnPage() {
        boolean isCorrect = isDisplayedOrgFieldsCorrect();
        if(isCorrect){
            isCorrect = isDisplayedContactPersonFieldsCorrect();
        }
        return isCorrect;
    }

    public boolean isDisplayedOrgFieldsCorrect() {
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        boolean fieldsDisplayed = true;
        try {
            //WaitUtils.waitForElementToBeClickable(driver, orgName, TIMEOUT_3_SECOND, false);
            WaitUtils.waitForElementToBeClickable(driver, orgAddressLine1, TIMEOUT_3_SECOND, false);
            WaitUtils.waitForElementToBeClickable(driver, orgAddressLine2, TIMEOUT_3_SECOND, false);
            WaitUtils.waitForElementToBeClickable(driver, orgCityTown, TIMEOUT_3_SECOND, false);
            WaitUtils.waitForElementToBeClickable(driver, orgPostCode, TIMEOUT_3_SECOND, false);
            WaitUtils.waitForElementToBeClickable(driver, orgCountry, TIMEOUT_3_SECOND, false);
            WaitUtils.waitForElementToBeClickable(driver, orgTelephone, TIMEOUT_3_SECOND, false);
            WaitUtils.waitForElementToBeClickable(driver, orgFax, TIMEOUT_3_SECOND, false);
            WaitUtils.waitForElementToBeClickable(driver, webSite, TIMEOUT_3_SECOND, false);
        }catch (Exception e){
            e.printStackTrace();
            fieldsDisplayed = false;
        }
        return fieldsDisplayed;
    }

    public boolean isDisplayedContactPersonFieldsCorrect() {
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        boolean fieldsDisplayed = true;
        try {
            //WaitUtils.waitForElementToBeClickable(driver, orgName, TIMEOUT_3_SECOND, false);
            WaitUtils.waitForElementToBeClickable(driver, fullName, TIMEOUT_3_SECOND, false);
            WaitUtils.waitForElementToBeClickable(driver, jobTitle, TIMEOUT_3_SECOND, false);
            WaitUtils.waitForElementToBeClickable(driver, emailAddress, TIMEOUT_3_SECOND, false);
            WaitUtils.waitForElementToBeClickable(driver, telephone, TIMEOUT_3_SECOND, false);
        }catch (Exception e){
            e.printStackTrace();
            fieldsDisplayed = false;
        }
        return fieldsDisplayed;
    }

    public Accounts clearFilterByOrganisation() {
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        WaitUtils.waitForElementToBeClickable(driver, listOfFilters.get(0), TIMEOUT_3_SECOND, false);
        listOfFilters.get(0).click();
        return new Accounts(driver);
    }

    public boolean areOrganisationOfRoleVisible(String organisationType) {
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        boolean aMatchFound = false;
        for(WebElement el: listOfOrganisationRoles){
            String text = el.getText();
            log.info(text);
            aMatchFound = text.contains(organisationType);
            if (aMatchFound) {
                break;
            }
        }
        return aMatchFound;
    }
}
