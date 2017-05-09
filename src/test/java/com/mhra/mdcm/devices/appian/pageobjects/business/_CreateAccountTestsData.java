package com.mhra.mdcm.devices.appian.pageobjects.business;

import com.mhra.mdcm.devices.appian.domains.newaccounts.AccountRequestDO;
import com.mhra.mdcm.devices.appian.pageobjects._Page;
import com.mhra.mdcm.devices.appian.utils.selenium.page.PageUtils;
import com.mhra.mdcm.devices.appian.utils.selenium.page.WaitUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by TPD_Auto
 */
@Component
public class _CreateAccountTestsData extends _Page {

    //Organisation details
    @FindBy(xpath = ".//label[.='Organisation name']//following::input[1]")
    WebElement orgName;
    @FindBy(xpath = ".//label[contains(text(),'Address line 1')]//following::input[1]")
    WebElement addressLine1;
    @FindBy(xpath = ".//label[contains(text(),'Address line 2')]//following::input[1]")
    WebElement addressLine2;
    @FindBy(xpath = ".//label[contains(text(),'City')]//following::input[1]")
    WebElement townCity;
    @FindBy(xpath = ".//label[contains(text(),'Postcode')]//following::input[1]")
    WebElement postCode;
    @FindBy(xpath = ".//label[contains(text(),'Postcode')]//following::input[@type='text'][2]")
    WebElement telephone;
    @FindBy(xpath = ".//label[contains(text(),'Fax')]//following::input[1]")
    WebElement fax;
    @FindBy(xpath = ".//label[contains(text(),'Website')]//following::input[1]")
    WebElement website;
    @FindBy(xpath = ".//label[contains(text(),'Registered Address')]")
    WebElement addressType;
    @FindBy(xpath = ".//label[contains(text(),'Country')]//following::input[1]")
    WebElement country;

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
    @FindBy(xpath = ".//label[.='First name']//following::input[1]")
    WebElement firstName;
    @FindBy(xpath = ".//label[.='Last name']//following::input[1]")
    WebElement lastName;
    @FindBy(xpath = ".//label[contains(text(),'Job title')]//following::input[1]")
    WebElement jobTitle;
    @FindBy(xpath = ".//label[contains(text(),'Job title')]//following::input[2]")
    WebElement phoneNumber;
    @FindBy(xpath = ".//label[.='Email']//following::input[1]")
    WebElement emailAddress;

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

    //Submit and cancel
    @FindBy(xpath = ".//button[contains(text(),'Submit')]")
    WebElement submit;
    @FindBy(xpath = ".//button[.='Cancel']")
    WebElement cancel;


    @Autowired
    public _CreateAccountTestsData(WebDriver driver) {
        super(driver);
    }

    /**
     * HELPS TESTERS CREATE TEST DATA ON THE GO
     * @param ar
     * @return
     */
    public ActionsTabPage createTestOrganisation(AccountRequestDO ar) throws Exception {
        WaitUtils.waitForPageToLoad(driver, By.xpath(".//label[.='Organisation name']//following::input[1]"), 5, false); ;
        WaitUtils.waitForElementToBeClickable(driver, orgName, 5, false);
        orgName.sendKeys(ar.organisationName);

        //Selecting country has changed to auto suggest
        boolean exception = false;
        try {
            orgName.click();
            PageUtils.selectFromAutoSuggestedListItems(driver, ".PickerWidget---picker_value", ar.country, true);
        }catch (Exception e){
            exception = true;
        }

        //Organisation details
        WaitUtils.waitForElementToBeClickable(driver, addressLine1, TIMEOUT_DEFAULT, false);
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
            PageFactory.initElements(driver, this);
            WaitUtils.waitForElementToBeVisible(driver, companyRegistrationNumber, TIMEOUT_5_SECOND, false);
            WaitUtils.waitForElementToBeClickable(driver, companyRegistrationNumber, TIMEOUT_5_SECOND, false);
            vatRegistrationNumber.sendKeys(ar.vatRegistrationNumber);
            companyRegistrationNumber.sendKeys(ar.companyRegistrationNumber);

        }else if(ar.organisationType.equals("Business Partnership")){
            PageUtils.clickIfVisible(driver, businessPartnership);
            PageFactory.initElements(driver, this);
            WaitUtils.waitForElementToBeVisible(driver, vatRegistrationNumber, TIMEOUT_5_SECOND, false);
            WaitUtils.waitForElementToBeClickable(driver, vatRegistrationNumber, TIMEOUT_5_SECOND, false);
            vatRegistrationNumber.sendKeys(ar.vatRegistrationNumber);

        }else if(ar.organisationType.equals("Unincorporated Association")){
            PageUtils.clickIfVisible(driver, unincorporatedAssociation);
            PageFactory.initElements(driver, this);

        }else if(ar.organisationType.equals("Other")){
            PageUtils.clickIfVisible(driver, other);
            PageFactory.initElements(driver, this);

        }

        //Contact Person Details
        try {
            PageUtils.singleClick(driver, title);
            WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
            WaitUtils.waitForElementToBeClickable(driver, By.xpath(".//div[contains(text(), '"+ ar.title + "')]"), TIMEOUT_3_SECOND, false);
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

        //Services of Interests
        if(ar.deviceRegistration){
            PageUtils.singleClick(driver, deviceReg);
        }
        if(ar.cfsCertificateOfFreeSale){
            WaitUtils.waitForElementToBeClickable(driver, cfsCertification, TIMEOUT_DEFAULT, false);
            PageUtils.singleClick(driver, cfsCertification);
        }
        if(ar.clinicalInvestigation){
            WaitUtils.waitForElementToBeClickable(driver, By.xpath(".//span[.='Selected Services']//following::input[3]"), TIMEOUT_DEFAULT, false);
            PageUtils.singleClick(driver, clinicalInvestigation);
        }
        if(ar.aitsAdverseIncidentTrackingSystem){
            WaitUtils.waitForElementToBeClickable(driver, By.xpath(".//span[.='Selected Services']//following::input[4]"), TIMEOUT_DEFAULT, false);
            PageUtils.singleClick(driver, aitsAdverseIncidient);
        }

        //Some weired bug where input boxes looses value on focus
        if(exception) {
            try {
                PageUtils.selectFromAutoSuggestedListItems(driver, ".PickerWidget---picker_value", ar.country, false);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        //Submit form : remember to verify
        submit.click();

        return new ActionsTabPage(driver);
    }


    public ActionsTabPage clickCancel() {
        PageUtils.doubleClick(driver, cancel);
        return new ActionsTabPage(driver);
    }

    public List<String> getListOfAutosuggestionsFor(String searchTerm) {
        WaitUtils.isPageLoadingComplete(driver,TIMEOUT_PAGE_LOAD);
        WaitUtils.waitForElementToBeClickable(driver, By.cssSelector(".PickerWidget---picker_value"), TIMEOUT_5_SECOND, false);
        List<String> matchesFromAutoSuggests = PageUtils.getListOfMatchesFromAutoSuggests(driver, By.cssSelector(".PickerWidget---picker_value"), searchTerm);
        System.out.println(matchesFromAutoSuggests);
        return matchesFromAutoSuggests;
    }
}
