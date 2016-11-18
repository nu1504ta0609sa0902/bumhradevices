package com.mhra.mdcm.devices.appian.pageobjects.business.sections;

import com.mhra.mdcm.devices.appian.domains.AccountRequest;
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

    @FindBy(xpath = ".//h2[.='Status']//following::a")
    List<WebElement> listOfAccounts;
    @FindBy(xpath = ".//table//th")
    List<WebElement> listOfTableColumns;

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
    @FindBy(xpath = ".//span[contains(text(),'Telephone')]//following::p[1]")
    WebElement phoneNumber;

    //Search box
    @FindBy(xpath = ".//*[contains(@class, 'filter')]//following::input[1]")
    WebElement searchBox;
    @FindBy(linkText = "Follow")
    WebElement followBtn;

    @Autowired
    public Accounts(WebDriver driver) {
        super(driver);
    }


    public boolean isHeadingCorrect(String expectedHeadings) {
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
        List<String> listOfColumns = new ArrayList<>();
        for(WebElement el: listOfTableColumns){
            String text = el.getText();
            if(text!=null){
                listOfColumns.add(text);
            }
        }

        //Verify columns matches expectation
        List<String> columnsNotFound = new ArrayList<>();
        for(String c: columns){
            c = c.trim();
            if(!listOfColumns.contains(c)){
                System.out.println("Column Not Found : " + c);
                columnsNotFound.add(c);
            }
        }

        return columnsNotFound;
    }

    public Accounts searchForAccount(String orgName) {
        WaitUtils.waitForElementToBeClickable(driver, searchBox, TIMEOUT_DEFAULT, false);
        searchBox.clear();
        searchBox.sendKeys(orgName);
        searchBox.sendKeys(Keys.ENTER);
        //WaitUtils.forceWaitForPageToLoad(driver, By.xpath("Wait For Certain Time"), TIMEOUT_10_SECOND, 1);
        return new Accounts(driver);
    }

    public boolean atLeast1MatchFound(String searchText) {
        boolean atLeast1MatchFound = true;
        try{
            WaitUtils.waitForElementToBeClickable(driver, By.partialLinkText(searchText), TIMEOUT_DEFAULT, false);
            int actualCount = (listOfAccounts.size()-1)/2;
            atLeast1MatchFound = actualCount >= 1;
        }catch (Exception e){
            atLeast1MatchFound = false;
        }

        return atLeast1MatchFound;
    }

    /**
     * NOTE THERE MAY BE MORE THAN 1 LINK PER ROW
     * @return
     */
    public String getARandomAccount() {
        WaitUtils.forceWaitForPageToLoad(driver, By.xpath(".//h2[.='Status']//following::a[2]"), TIMEOUT_5_SECOND, 1);
        WaitUtils.waitForElementToBeClickable(driver, By.xpath(".//h2[.='Status']//following::a[2]"), TIMEOUT_5_SECOND, false);

        int position = RandomDataUtils.getSimpleRandomNumberBetween(1, listOfAccounts.size() - 1, false);
        WebElement accountLinks = listOfAccounts.get(position);
        String accountName = accountLinks.getText();
        return accountName;
    }

    public String getARandomAccountWithText(String name) {
        WaitUtils.forceWaitForPageToLoad(driver, By.xpath(".//h2[.='Status']//following::a[2]"), TIMEOUT_3_SECOND, 1);
        WaitUtils.waitForElementToBeClickable(driver, By.xpath(".//h2[.='Status']//following::a[2]"), TIMEOUT_5_SECOND, false);

        boolean found = false;
        int count = 0;
        String accountName = "";
        do {
            count++;
            int position = RandomDataUtils.getSimpleRandomNumberBetween(1, listOfAccounts.size() - 1, false);
            WebElement accountLinks = listOfAccounts.get(position);
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
        WaitUtils.waitForElementToBeVisible(driver, editAccountInfoLink, TIMEOUT_DEFAULT, false);
        WaitUtils.waitForElementToBeClickable(driver, editAccountInfoLink, TIMEOUT_5_SECOND, false);
        WaitUtils.waitForElementToBeClickable(driver, By.cssSelector(".gwt-Anchor.pull-down-toggle"), TIMEOUT_5_SECOND, false);
        boolean allChangesDisplayed = true;

        //Check for the following
        String[] dataPairs = keyValuePairToUpdate.split(",");

        for(String pairs: dataPairs){
            String[] split = pairs.split("=");
            String key = split[0];
            String value = split[1];
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
        }

        return allChangesDisplayed;
    }

    public boolean isOrderedAtoZ() {
        int getFirstX = 20;
        List<String> listOfOrderedOrganisations = new ArrayList<>();
        WaitUtils.waitForElementToBeClickable(driver, By.xpath(".//h2[.='Status']//following::a[2]"), TIMEOUT_5_SECOND, false);

        //Get list of organisation names
        int position = 0;   //Only even ones are organisation name
        int elementCount = 0;
        for(WebElement el: listOfAccounts){

            //At the moment only the even ones are organisation names
            if(position!=0 && position % 2 == 1){
                String orgName = el.getText();
                listOfOrderedOrganisations.add(orgName);
            }

            if(elementCount == (getFirstX*2)){  //Every 2nd link is an organisation name
                break;
            }

            elementCount++;
            position++;
        }

        //Check if a-Z
        String previous = "";
        for (final String current: listOfOrderedOrganisations) {
            if (!current.equals("") && current.compareToIgnoreCase(previous) <= 0)
                return false;
            previous = current;
        }

        return true;
    }

    public boolean isCorrectPage() {
        try {
            WaitUtils.waitForElementToBeClickable(driver, followBtn, TIMEOUT_5_SECOND, false);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
