package com.mhra.mdcm.devices.appian.pageobjects.business;

import com.mhra.mdcm.devices.appian.domains.newaccounts.AccountRequestDO;
import com.mhra.mdcm.devices.appian.pageobjects._Page;
import com.mhra.mdcm.devices.appian.utils.selenium.page.PageUtils;
import com.mhra.mdcm.devices.appian.utils.selenium.page.WaitUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by TPD_Auto
 */
@Component
public class _CreateAccountTestHarnessPage extends _Page {

    //Organisation details
    @FindBy(xpath = ".//label[.='Organisation name']//following::input[1]")
    WebElement orgName;
    @FindBy(xpath = ".//*[contains(text(),'Address line 1')]//following::input[1]")
    WebElement addressLine1;
    @FindBy(xpath = ".//*[contains(text(),'Address line 2')]//following::input[1]")
    WebElement addressLine2;
    @FindBy(xpath = ".//label[contains(text(),'City')]//following::input[1]")
    WebElement townCity;
    @FindBy(xpath = ".//label[contains(text(),'County')]//following::input[2]")
    WebElement postCode;
    @FindBy(xpath = ".//*[contains(text(),'Address type')]/following::input[6]")
    WebElement telephone;
    @FindBy(xpath = ".//label[contains(text(),'Fax')]//following::input[1]")
    WebElement fax;
    @FindBy(xpath = ".//label[contains(text(),'Website')]//following::input[1]")
    WebElement website;
    @FindBy(xpath = ".//label[contains(text(),'Registered Address')]")
    WebElement addressType;
    @FindBy(xpath = ".//*[contains(text(),'Country')]//following::input[1]")
    WebElement country;
    @FindBy(xpath = ".//a/u[contains(text(), 'Enter address')]")
    WebElement linkEnterAddressManually;
    @FindBy(xpath = ".//a/u[contains(text(), 'Add Line')]")
    WebElement linkAddLine;

    //Organisation Type
    @FindBy(xpath = ".//label[contains(text(),'Limited Company')]")
    WebElement limitedCompany;
    @FindBy(xpath = ".//label[contains(text(),'Business Partnership')]")
    WebElement businessPartnership;
    @FindBy(xpath = ".//label[contains(text(),'Unincorporated Association')]")
    WebElement unincorporatedAssociation;
    @FindBy(xpath = ".//label[contains(text(),'Other')]")
    WebElement other;
    final String selectedType = "Selected type";
    @FindBy(xpath = ".//span[.='" + selectedType + "']//following::input[5]")
    WebElement vatRegistrationNumber;
    @FindBy(xpath = ".//span[.='" + selectedType + "']//following::input[6]")
    WebElement companyRegistrationNumber;

    //Contact Person Details
    @FindBy(xpath = ".//span[contains(text(),'Title')]//following::div[@role='listbox']")
    WebElement title;
    @FindBy(xpath = ".//label[contains(text(),'First ')]//following::input[1]")
    WebElement firstName;
    @FindBy(xpath = ".//label[contains(text(),'Last ')]//following::input[1]")
    WebElement lastName;
    @FindBy(xpath = ".//label[contains(text(),'Job ')]//following::input[1]")
    WebElement jobTitle;
    @FindBy(xpath = ".//label[contains(text(),'Job ')]//following::input[2]")
    WebElement phoneNumber;
    @FindBy(xpath = ".//label[.='Email']//following::input[1]")
    WebElement emailAddress;
    @FindBy(xpath = ".//label[.='User name']//following::input[1]")
    WebElement userName;

    //Organisational Role
    final String selectedRoles = "Selected roles";
    @FindBy(xpath = ".//label[contains(text(),'Authorised Rep')]")
    WebElement authorisedRep;
    @FindBy(xpath = ".//label[contains(text(),'Manufacturer')]")
    WebElement manufacturer;
    @FindBy(xpath = ".//label[contains(text(),'Distributor')]")
    WebElement distributor;
    @FindBy(xpath = ".//label[contains(text(),'Notified Body')]")
    WebElement notifiedBody;

    //Services of Interests
    @FindBy(xpath = ".//label[contains(text(),'Account Management')]")
    WebElement accountManagement;
    @FindBy(xpath = ".//label[contains(text(),'Device Registration')]")
    WebElement deviceReg;
    @FindBy(xpath = ".//label[contains(text(),'Certificate of Freesales')]")
    WebElement cfsCertification;
    @FindBy(xpath = ".//label[contains(text(),'Clinical Investigation')]")
    WebElement clinicalInvestigation;
    @FindBy(xpath = ".//label[contains(text(),'Adverse Incident Tracking System')]")
    WebElement aitsAdverseIncidient;

    //Terms and condition checkbox
    @FindBy(xpath = ".//input[@type='checkbox']/following::label[1]")
    WebElement cbxTermsAndConditions;

    //Submit and cancel
    @FindBy(xpath = ".//button[contains(text(),'Submit')]")
    WebElement submit;
    @FindBy(xpath = ".//button[.='Cancel']")
    WebElement cancel;

    //Post code lookup
    @FindBy(xpath = ".//label[contains(text(),'Postcode lookup')]//following::input[1]")
    WebElement postCodeLookup;
    @FindBy(xpath = ".//button[contains(text(),'Find UK')]")
    WebElement btnFindUKAddress;
    @FindBy(xpath = ".//*[contains(text(),'Pick an address')]//following::div[1]")
    WebElement pickAnAddressDD;


    @Autowired
    public _CreateAccountTestHarnessPage(WebDriver driver) {
        super(driver);
    }

    /**
     * HELPS TESTERS CREATE TEST DATA ON THE GO
     * @param ar
     * @return
     */
    public ActionsTabPage createTestOrganisation(AccountRequestDO ar) throws Exception {
        //WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        WaitUtils.waitForElementToBeClickable(driver, orgName, TIMEOUT_15_SECOND);
        orgName.sendKeys(ar.organisationName);

        //Enter address manually if required
        enterAddressManually();

        //Selecting country has changed to auto suggest
        boolean exception = false;
        try {
            orgName.click();
            PageUtils.selectFromAutoSuggestedListItems(driver, ".PickerWidget---picker_value", ar.country, true);
        }catch (Exception e){
            exception = true;
        }

        //Organisation details
        WaitUtils.waitForElementToBeClickable(driver, addressLine1, TIMEOUT_10_SECOND);
        addressLine1.clear();
        addressLine1.sendKeys(ar.address1);
        addressLine2.sendKeys(ar.address2);
        townCity.sendKeys(ar.townCity);
        postCode.sendKeys(ar.postCode);
        telephone.sendKeys(ar.telephone);
        fax.sendKeys(ar.fax);
        website.sendKeys(ar.website);
        if(ar.addressType){
            PageUtils.doubleClick(driver, addressType);
        }

        //Organisation Type
        if(ar.organisationType.equals("Limited Company")){
            PageUtils.clickIfVisible(driver, limitedCompany);
            WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
            WaitUtils.waitForElementToBeVisible(driver, companyRegistrationNumber, TIMEOUT_5_SECOND);
            WaitUtils.waitForElementToBeClickable(driver, companyRegistrationNumber, TIMEOUT_5_SECOND);
            vatRegistrationNumber.sendKeys(ar.vatRegistrationNumber);
            companyRegistrationNumber.sendKeys(ar.companyRegistrationNumber);

        }else if(ar.organisationType.equals("Business Partnership")){
            PageUtils.clickIfVisible(driver, businessPartnership);
            WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
            WaitUtils.waitForElementToBeVisible(driver, vatRegistrationNumber, TIMEOUT_5_SECOND);
            WaitUtils.waitForElementToBeClickable(driver, vatRegistrationNumber, TIMEOUT_5_SECOND);
            vatRegistrationNumber.sendKeys(ar.vatRegistrationNumber);

        }else if(ar.organisationType.equals("Unincorporated Association")){
            PageUtils.clickIfVisible(driver, unincorporatedAssociation);
            WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);

        }else if(ar.organisationType.equals("Other")){
            PageUtils.clickIfVisible(driver, other);
            WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);

        }

        //Contact Person Details
        try {
            PageUtils.singleClick(driver, title);
            WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
            WaitUtils.waitForElementToBeClickable(driver, By.xpath(".//div[contains(text(), '"+ ar.title + "')]"), TIMEOUT_3_SECOND);
            WebElement titleToSelect = driver.findElement(By.xpath(".//div[contains(text(), '"+ ar.title + "')]"));
            PageUtils.singleClick(driver, titleToSelect);
        } catch (Exception e) {
            e.printStackTrace();
        }

        firstName.sendKeys(ar.firstName);
        lastName.sendKeys(ar.lastName);
        jobTitle.sendKeys(ar.jobTitle);
        phoneNumber.sendKeys(ar.phoneNumber);
        emailAddress.sendKeys(ar.email);
        userName.sendKeys(ar.getUserName(true));


        //Organisation Role
        if(ar.organisationRole.equals("distributor")){
            PageUtils.doubleClick(driver, distributor);
        }else if(ar.organisationRole.equals("notifiedbody")){
            PageUtils.doubleClick(driver, notifiedBody);
        }else{
            //Is either a manufacturer or authorisedRep
            if(ar.isManufacturer){
                PageUtils.doubleClick(driver, manufacturer);
            }else{
                PageUtils.doubleClick(driver, authorisedRep);
            }
        }

        try {
            //Services of Interests
            if (ar.deviceRegistration) {
                PageUtils.singleClick(driver, deviceReg);
            }
            if (ar.cfsCertificateOfFreeSale) {
                WaitUtils.waitForElementToBeClickable(driver, cfsCertification, TIMEOUT_DEFAULT);
                PageUtils.singleClick(driver, cfsCertification);
            }
            if (ar.clinicalInvestigation) {
                WaitUtils.waitForElementToBeClickable(driver, By.xpath(".//span[.='Selected Services']//following::input[3]"), TIMEOUT_10_SECOND);
                PageUtils.singleClick(driver, clinicalInvestigation);
            }
            if (ar.aitsAdverseIncidentTrackingSystem) {
                WaitUtils.waitForElementToBeClickable(driver, By.xpath(".//span[.='Selected Services']//following::input[4]"), TIMEOUT_10_SECOND);
                PageUtils.singleClick(driver, aitsAdverseIncidient);
            }
        }catch (Exception e){
            //Service of interest section not displaying since 17/05/2017
        }

        //Some weired bug where input boxes looses value on focus
        if(exception) {
            try {
                PageUtils.selectFromAutoSuggestedListItems(driver, ".PickerWidget---picker_value", ar.country, false);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        //Terms and condition checkbox introduced 17/05
        cbxTermsAndConditions.click();

        //Submit form : remember to verify
        WaitUtils.waitForElementToBeClickable(driver, submit, TIMEOUT_10_SECOND);
        submit.click();

        return new ActionsTabPage(driver);
    }

    private void enterAddressManually() {
        try {
            WaitUtils.waitForElementToBeClickable(driver, linkEnterAddressManually, TIMEOUT_10_SECOND);
            PageUtils.singleClick(driver, linkEnterAddressManually);
            WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        }catch (Exception e){
            //Introduced suddenly on 17/05 sprint 19 changes
        }
    }


    public ActionsTabPage clickCancel() {
        PageUtils.doubleClick(driver, cancel);
        return new ActionsTabPage(driver);
    }

    public List<String> getListOfAutosuggestionsFor(String searchTerm) {
        //WaitUtils.isPageLoadingComplete(driver,TIMEOUT_PAGE_LOAD);
        enterAddressManually();
        WaitUtils.waitForElementToBeClickable(driver, country, TIMEOUT_10_SECOND);
        List<String> matchesFromAutoSuggests = PageUtils.getListOfMatchesFromAutoSuggests(driver, By.cssSelector(".PickerWidget---picker_value"), searchTerm);
        System.out.println("Matches from autosuggests : " + matchesFromAutoSuggests);
        return matchesFromAutoSuggests;
    }

    public _CreateAccountTestHarnessPage lookUpAddressViaPostCode(String postCode, String expectedRoad) {
        WaitUtils.waitForElementToBeClickable(driver, orgName, TIMEOUT_15_SECOND);
        postCodeLookup.sendKeys(postCode);
        WaitUtils.waitForElementToBeClickable(driver, btnFindUKAddress, TIMEOUT_15_SECOND);
        btnFindUKAddress.click();
        WaitUtils.waitForElementToBeClickable(driver, pickAnAddressDD, TIMEOUT_15_SECOND);
        PageUtils.selectFromDropDown(driver, pickAnAddressDD, expectedRoad, true);
        return new _CreateAccountTestHarnessPage(driver);
    }

    public boolean isAddressLookupDataCorrect(String addressExpected, String postCodeExpected) {
        WaitUtils.isPageLoadingComplete(driver,TIMEOUT_PAGE_LOAD);
        WaitUtils.waitForElementToBeClickable(driver, linkAddLine, TIMEOUT_10_SECOND);
        WaitUtils.waitForElementToBeClickable(driver, addressLine2, TIMEOUT_5_SECOND);

        String addressVal = PageUtils.getText(addressLine1) + "," + PageUtils.getText(addressLine2);
        String postCodeVal = PageUtils.getText(postCode);

        boolean postCodeFound = postCodeExpected.contains(postCodeVal);
        boolean addressFound = addressExpected.contains(addressVal);
        return postCodeFound && addressFound;
    }
}
