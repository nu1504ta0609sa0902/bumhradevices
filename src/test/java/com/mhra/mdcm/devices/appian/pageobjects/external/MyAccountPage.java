package com.mhra.mdcm.devices.appian.pageobjects.external;

import com.mhra.mdcm.devices.appian.domains.newaccounts.AccountRequestDO;
import com.mhra.mdcm.devices.appian.pageobjects._Page;
import com.mhra.mdcm.devices.appian.pageobjects.business.sections.TaskSection;
import com.mhra.mdcm.devices.appian.pageobjects.external.myaccount.OrganisationDetails;
import com.mhra.mdcm.devices.appian.pageobjects.external.myaccount.ContactPersonDetails;
import com.mhra.mdcm.devices.appian.utils.selenium.page.AssertUtils;
import com.mhra.mdcm.devices.appian.utils.selenium.page.PageUtils;
import com.mhra.mdcm.devices.appian.utils.selenium.page.WaitUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

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

    //Buttons
    @FindBy(xpath = ".//button[contains(text(),'Organisation')]")
    WebElement amendOrganisationDetails;
    @FindBy(xpath = ".//button[contains(text(),'Add contact')]")
    WebElement btnAddContact;
    @FindBy(xpath = ".//button[contains(text(),'Edit contact')]")
    WebElement btnEditContact;
    @FindBy(xpath = ".//button[contains(text(),'Manage Contacts')]")
    WebElement btnManageContacts;
    @FindBy(xpath = ".//button[contains(text(),'Edit Account Information')]")
    WebElement btnEditAccountInformation;
    @FindBy(xpath = ".//button[contains(text(),'Person')]")
    WebElement amendContactPersonDetails;

    //Contact details
    @FindBy(xpath = ".//*[contains(text(),'Full name')]//following::td[2]")
    WebElement fullName;
    @FindBy(xpath = ".//*[contains(text(),'Full name')]//following::td[3]")
    WebElement jobTitle;
    @FindBy(xpath = ".//*[contains(text(),'Full name')]//following::td[4]")
    WebElement email;
    @FindBy(xpath = ".//*[contains(text(),'Full name')]//following::td[5]")
    WebElement telephone;
    @FindBy(xpath = ".//*[contains(text(),'Full name')]//following::td[6]")
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

    //Select contact from contacts table
    @FindBy(css = "td.GridWidget---checkbox")
    WebElement cbxFirstContact;
    @FindBy(css = "td.GridWidget---checkbox")
    List<WebElement> listOfAllContacts;
    @FindBy(xpath = ".//td[2]")
    List<WebElement> listOfAllFullNames;

    //Table headings
    @FindBy(xpath = ".//div[contains(text(),'Associated Date')]")
    WebElement thAssociatedDate;
    @FindBy(xpath = ".//div[contains(text(),'Telephone')]")
    WebElement thTelephone;

    @Autowired
    public MyAccountPage(WebDriver driver) {
        super(driver);
    }

    public ContactPersonDetails amendContactPersonDetails() {
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        WaitUtils.waitForElementToBeClickable(driver, amendContactPersonDetails, TIMEOUT_5_SECOND);
        amendContactPersonDetails.click();
        return new ContactPersonDetails(driver);
    }

    public OrganisationDetails amendOrganisationDetails() {
        WaitUtils.waitForElementToBeClickable(driver, amendOrganisationDetails, TIMEOUT_10_SECOND);
        amendOrganisationDetails.click();
        return new OrganisationDetails(driver);
    }

    public boolean isCorrectPage() {
        try {
            WaitUtils.waitForElementToBeClickable(driver, btnManageContacts, TIMEOUT_5_SECOND);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean verifyUpdatesDisplayedOnPage(String keyValuePairToUpdate, AccountRequestDO updatedData) {
        WaitUtils.waitForElementToBeVisible(driver, email, TIMEOUT_DEFAULT);
        WaitUtils.waitForElementToBeVisible(driver, fullName, TIMEOUT_5_SECOND);

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
                log.info("Data maybe incorrect : " + key);
                break;
            }
        }

        return allChangesDisplayed;
    }

    public boolean isRolesCorrect(String loggedInUser, String expectedRoles) {
        WaitUtils.waitForElementToBeClickable(driver, role, TIMEOUT_10_SECOND);
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

    public boolean verifyDatesDisplayedOnPage(AccountRequestDO updatedData) {
        boolean areDatesVisible = true;
        try {
            WaitUtils.waitForElementToBeVisible(driver, associatedDates, TIMEOUT_5_SECOND);
            //WaitUtils.waitForElementToBeVisible(driver, createdDates, TIMEOUT_5_SECOND);
        } catch (Exception e) {
            areDatesVisible = false;
        }
        return areDatesVisible;
    }

    public MyAccountPage refreshThePage() {
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        WaitUtils.waitForElementToBeClickable(driver, fullName, TIMEOUT_10_SECOND);
        driver.navigate().refresh();
        return new MyAccountPage(driver);
    }

    public MyAccountPage clickManageContacts() {
        WaitUtils.waitForElementToBeClickable(driver, btnManageContacts, TIMEOUT_10_SECOND);
        btnManageContacts.click();
        return new MyAccountPage(driver);
    }

    public ContactPersonDetails selectContactToEdit() {
        WaitUtils.waitForElementToBeClickable(driver, cbxFirstContact, TIMEOUT_10_SECOND);
        cbxFirstContact.click();
        WaitUtils.waitForElementToBeClickable(driver, btnEditContact, TIMEOUT_10_SECOND);
        btnEditContact.click();
        return new ContactPersonDetails(driver);
    }

    public ContactPersonDetails selectLastContactToEdit() {
        WaitUtils.waitForElementToBeClickable(driver, cbxFirstContact, TIMEOUT_10_SECOND);
        listOfAllContacts.get(listOfAllContacts.size()-1).click();
        WaitUtils.waitForElementToBeClickable(driver, btnEditContact, TIMEOUT_10_SECOND);
        btnEditContact.click();
        return new ContactPersonDetails(driver);
    }


    public MyAccountPage sortBy(String sortBy, int numberOfTimesToClick) {
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        WebElement toClick = null;
        if (sortBy.equals("Associated Date")) {
            toClick = thAssociatedDate;
        }else if (sortBy.equals("Telephone")) {
            toClick = thTelephone;
        }

        if(toClick!=null) {
            for (int c = 0; c < numberOfTimesToClick; c++) {
                WaitUtils.waitForElementToBeClickable(driver, toClick, TIMEOUT_5_SECOND);
                toClick.click();
                WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
            }
        }

        return new MyAccountPage(driver);
    }

    public OrganisationDetails editAccountInformation() {
        WaitUtils.waitForElementToBeClickable(driver, btnEditAccountInformation, TIMEOUT_10_SECOND);
        btnEditAccountInformation.click();
        return new OrganisationDetails(driver);
    }

    public MyAccountPage clickAddContact() {
        WaitUtils.waitForElementToBeClickable(driver, btnAddContact, TIMEOUT_10_SECOND);
        btnAddContact.click();
        return new MyAccountPage(driver);
    }

    public ContactPersonDetails selectNewContactToEdit(AccountRequestDO data) {
        WaitUtils.waitForElementToBeClickable(driver, btnAddContact, TIMEOUT_10_SECOND);
        int index = 0;
        for(WebElement el: listOfAllFullNames){
            String fnTxt = el.getText();
            log.info("Fullname : " + fnTxt);
            if(fnTxt.contains(data.firstName) && fnTxt.contains(data.lastName) && fnTxt.contains(data.title)){
                break;
            }
            index++;
        }

        //Open correct item to verify
        listOfAllContacts.get(index).click();
        WaitUtils.waitForElementToBeClickable(driver, btnEditContact, TIMEOUT_10_SECOND);
        btnEditContact.click();

        return new ContactPersonDetails(driver);
    }
}
