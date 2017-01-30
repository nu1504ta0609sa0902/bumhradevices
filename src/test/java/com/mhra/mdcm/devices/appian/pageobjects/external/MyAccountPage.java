package com.mhra.mdcm.devices.appian.pageobjects.external;

import com.mhra.mdcm.devices.appian.domains.newaccounts.AccountRequest;
import com.mhra.mdcm.devices.appian.pageobjects._Page;
import com.mhra.mdcm.devices.appian.pageobjects.external.sections.OrganisationDetails;
import com.mhra.mdcm.devices.appian.pageobjects.external.sections.PersonDetails;
import com.mhra.mdcm.devices.appian.utils.selenium.page.AssertUtils;
import com.mhra.mdcm.devices.appian.utils.selenium.page.WaitUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by TPD_Auto
 * <p>
 * We can use this for both editing of PERSON AND ORGANISATION
 * <p>
 * As the pages are small and simple
 * <p>
 * We can move to its own PO later on
 */
@Component
public class MyAccountPage extends _Page {

    @FindBy(xpath = ".//button[contains(text(),'Organisation')]")
    WebElement amendOrganisationDetails;

    @FindBy(xpath = ".//button[contains(text(),'Person')]")
    WebElement amendContactPersonDetails;

    //Contact details
    @FindBy(xpath = ".//span[contains(text(),'Full')]//following::p[1]")
    WebElement fullName;
    @FindBy(xpath = ".//span[contains(text(),'Job')]//following::p[1]")
    WebElement jobTitle;
    @FindBy(xpath = ".//span[contains(text(),'Email')]//following::p[1]")
    WebElement email;
    @FindBy(xpath = ".//h3[contains(text(),'Person Details')]//following::span[.='Telephone']/following::p[1]")
    WebElement telephone;
    @FindBy(xpath = ".//span[contains(text(),'Associated date')]//following::p[1]")
    WebElement associatedDates;

    //Organisation details
    @FindBy(xpath = ".//span[contains(text(),'Organisation name')]//following::p[1]")
    WebElement orgName;
    @FindBy(xpath = ".//span[contains(text(),'Company type')]//following::p[1]")
    WebElement orgCompanyType;
    @FindBy(xpath = ".//span[contains(text(),'Address type')]//following::p[1]")
    WebElement orgAddressType;
    @FindBy(xpath = ".//span[contains(text(),'line 1')]//following::p[1]")
    WebElement orgAddressLine1;
    @FindBy(xpath = ".//span[contains(text(),'line 2')]//following::p[1]")
    WebElement orgAddressLine2;
    @FindBy(xpath = ".//span[contains(text(),'Town')]//following::p[1]")
    WebElement orgCity;
    @FindBy(xpath = ".//span[contains(text(),'Postcode')]//following::p[1]")
    WebElement orgPostCode;
    @FindBy(xpath = ".//span[contains(text(),'Country')]//following::p[1]")
    WebElement orgCountry;
    @FindBy(xpath = ".//span[contains(text(),'Telephone')]//following::p[1]")
    WebElement orgTelephone;
    @FindBy(xpath = ".//span[contains(text(),'Website')]//following::p[1]")
    WebElement orgWebsite;
    @FindBy(xpath = ".//span[contains(text(),'Role')]//following::p[1]")
    WebElement role;
    @FindBy(xpath = ".//span[contains(text(),'Created date')]//following::p[1]")
    WebElement createdDates;


    @Autowired
    public MyAccountPage(WebDriver driver) {
        super(driver);
    }

    public PersonDetails amendContactPersonDetails() {
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        WaitUtils.waitForElementToBeClickable(driver, amendContactPersonDetails, TIMEOUT_5_SECOND, false);
        amendContactPersonDetails.click();
        return new PersonDetails(driver);
    }

    public OrganisationDetails amendOrganisationDetails() {
        WaitUtils.waitForElementToBeClickable(driver, amendOrganisationDetails, TIMEOUT_10_SECOND, false);
        amendOrganisationDetails.click();
        return new OrganisationDetails(driver);
    }

    public boolean isCorrectPage() {
        try {
            WaitUtils.waitForElementToBeClickable(driver, amendContactPersonDetails, TIMEOUT_5_SECOND, false);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean verifyUpdatesDisplayedOnPage(String keyValuePairToUpdate, AccountRequest updatedData) {
        WaitUtils.waitForElementToBeVisible(driver, email, TIMEOUT_DEFAULT, false);
        WaitUtils.waitForElementToBeVisible(driver, fullName, TIMEOUT_5_SECOND, false);

        boolean allChangesDisplayed = true;

        //Check for the following
        String[] dataPairs = keyValuePairToUpdate.split(",");

        for (String pairs : dataPairs) {
            //String[] split = pairs.split("=");
            String key = pairs;

            //Contact details
            if (key.equals("contact.title")) {
                allChangesDisplayed = AssertUtils.areChangesDisplayed(fullName, updatedData.title);
            } else if (key.equals("contact.firstname")) {
                allChangesDisplayed = AssertUtils.areChangesDisplayed(fullName, updatedData.firstName);
            } else if (key.equals("contact.lastname")) {
                allChangesDisplayed = AssertUtils.areChangesDisplayed(fullName, updatedData.lastName);
            } else if (key.equals("contact.job.title")) {
                allChangesDisplayed = AssertUtils.areChangesDisplayed(jobTitle, updatedData.jobTitle);
            } else if (key.equals("contact.email")) {
                allChangesDisplayed = AssertUtils.areChangesDisplayed(email, updatedData.email);
            } else if (key.equals("contact.telephone")) {
                allChangesDisplayed = AssertUtils.areChangesDisplayed(telephone, updatedData.telephone);
            }

            //Organisation details  : ,,,,,org.website
            boolean orgNameChanged = false;
            if (key.equals("org.name")) {
                allChangesDisplayed = AssertUtils.areChangesDisplayed(orgName, updatedData.organisationName);
                orgNameChanged = true;
            } else if (key.equals("org.company.type")) {
                allChangesDisplayed = AssertUtils.areChangesDisplayed(orgCompanyType, updatedData.organisationType);
            } else if (key.equals("org.role")) {
                //This is already checked
            } else if (key.equals("org.address.type")) {
                //@todo Address type check needs to be added
            } else if (key.equals("org.address1")) {
                allChangesDisplayed = AssertUtils.areChangesDisplayed(orgAddressLine1, updatedData.address1);
            } else if (key.equals("org.address2")) {
                allChangesDisplayed = AssertUtils.areChangesDisplayed(orgAddressLine2, updatedData.address2);
            } else if (key.equals("org.city")) {
                allChangesDisplayed = AssertUtils.areChangesDisplayed(orgCity, updatedData.townCity);
            } else if (key.equals("org.postcode")) {
                allChangesDisplayed = AssertUtils.areChangesDisplayed(orgPostCode, updatedData.postCode);
            } else if (key.equals("org.country")) {
                allChangesDisplayed = AssertUtils.areChangesDisplayed(orgCountry, updatedData.country);
            } else if (key.equals("org.telephone")) {
                allChangesDisplayed = AssertUtils.areChangesDisplayed(orgTelephone, updatedData.telephone);
            } else if (key.equals("org.website") && orgNameChanged) {
                allChangesDisplayed = AssertUtils.areChangesDisplayed(orgWebsite, updatedData.website);
            } else if (key.equals("org.created.date")) {
                //Already checked below in the date checking methods
            }

            if (!allChangesDisplayed) {
                break;
            }
        }

        return allChangesDisplayed;
    }

    public boolean isRolesCorrect(String loggedInUser, String expectedRoles) {
        WaitUtils.waitForElementToBeClickable(driver, role, TIMEOUT_10_SECOND, false);
        String[] roles = expectedRoles.split(",");
        String rolesDisplayed = role.getText();

        boolean contains = true;
        //Get list of roles
        for (String role : roles) {
            if (!rolesDisplayed.contains(role)) {
                contains = false;
                break;
            }
        }

        return contains;
    }

    public boolean verifyDatesDisplayedOnPage(AccountRequest updatedData) {
        boolean areDatesVisible = true;
        try {
            WaitUtils.waitForElementToBeVisible(driver, associatedDates, TIMEOUT_5_SECOND, false);
            WaitUtils.waitForElementToBeVisible(driver, createdDates, TIMEOUT_5_SECOND, false);
        } catch (Exception e) {
            areDatesVisible = false;
        }
        return areDatesVisible;
    }
}
