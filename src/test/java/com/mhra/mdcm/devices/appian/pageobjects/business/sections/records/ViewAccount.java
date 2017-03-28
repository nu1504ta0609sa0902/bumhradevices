package com.mhra.mdcm.devices.appian.pageobjects.business.sections.records;

import com.mhra.mdcm.devices.appian.domains.newaccounts.AccountRequest;
import com.mhra.mdcm.devices.appian.pageobjects._Page;
import com.mhra.mdcm.devices.appian.utils.selenium.others.RandomDataUtils;
import com.mhra.mdcm.devices.appian.utils.selenium.page.AssertUtils;
import com.mhra.mdcm.devices.appian.utils.selenium.page.PageUtils;
import com.mhra.mdcm.devices.appian.utils.selenium.page.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by TPD_Auto
 */
@Component
public class ViewAccount extends _Page {

    //Edit information related to an account
    @FindBy(xpath = ".//button[contains(text(),'Edit Account Information')]")
    WebElement editAccountInfoLink;
    @FindBy(xpath = ".//h4")
    WebElement orgName;

    //VIEW ORGANISATION DETAILS
    @FindBy(xpath = ".//span[contains(text(),'Address line 1')]//following::p[1]")
    WebElement orgAddressLine1;
    @FindBy(xpath = ".//span[contains(text(),'Address line 2')]//following::p[1]")
    WebElement orgAddressLine2;
    @FindBy(xpath = ".//span[contains(text(),'City')]//following::p[1]")
    WebElement orgCityTown;
    @FindBy(xpath = ".//span[contains(text(),'Postcode')]//following::p[1]")
    WebElement orgPostCode;
    @FindBy(xpath = ".//span[contains(text(),'Country')]//following::p[1]")
    WebElement orgCountry;
    @FindBy(xpath = ".//span[contains(text(),'Address type')]//following::input[1]")
    WebElement addressType;
    @FindBy(xpath = ".//span[contains(text(),'Telephone')]//following::p[1]")
    WebElement orgTelephone;
    @FindBy(xpath = ".//span[contains(text(),'Fax')]//following::p[1]")
    WebElement orgFax;
    @FindBy(xpath = ".//span[contains(text(),'Website')]//following::p[1]")
    WebElement webSite;

    //VIEW CONTACT PERSON DETAILS
    @FindBy(xpath = ".//span[contains(text(),'Job title')]//following::p[1]")
    WebElement jobTitle;
    @FindBy(xpath = ".//span[contains(text(),'Email')]//following::p[1]")
    WebElement emailAddress;
    @FindBy(xpath = ".//span[contains(text(),'Full')]//following::p[1]")
    WebElement fullName;
    @FindBy(xpath = ".//span[contains(text(),'Email')]//following::p[2]")
    WebElement telephone;


    @Autowired
    public ViewAccount(WebDriver driver) {
        super(driver);
    }


    public boolean isHeadingCorrect(String expectedHeadings) {
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        WaitUtils.waitForElementToBeClickable(driver, By.xpath(".//h1[.='" + expectedHeadings + "']") , TIMEOUT_DEFAULT, false);
        WebElement heading = driver.findElement(By.xpath(".//h1[.='" + expectedHeadings + "']"));
        boolean contains = heading.getText().contains(expectedHeadings);
        return contains;
    }

    public EditAccount gotoEditAccountInformation() {
        WaitUtils.waitForElementToBeClickable(driver, By.cssSelector(".gwt-Anchor.pull-down-toggle"), TIMEOUT_5_SECOND, false);
        WaitUtils.waitForElementToBeClickable(driver, editAccountInfoLink, TIMEOUT_DEFAULT, false);
        PageUtils.doubleClick(driver, editAccountInfoLink);
        //editAccountInfoLink.click();
        return new EditAccount(driver);
    }

    public boolean verifyUpdatesDisplayedOnPage(String keyValuePairToUpdate, AccountRequest updatedData) {
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        WaitUtils.waitForElementToBeVisible(driver, editAccountInfoLink, TIMEOUT_10_SECOND, false);
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

}
