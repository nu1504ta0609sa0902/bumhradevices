package com.mhra.mdcm.devices.appian.pageobjects.external;

import com.mhra.mdcm.devices.appian.domains.newaccounts.ManufacturerRequestDO;
import com.mhra.mdcm.devices.appian.pageobjects._Page;
import com.mhra.mdcm.devices.appian.pageobjects.external.cfs.CFSManufacturerList;
import com.mhra.mdcm.devices.appian.pageobjects.external.device.AddDevices;
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
public class _CreateCFSManufacturerTestHarnessPage extends _Page {

    @FindBy(css = ".FieldLayout---field_error")
    List<WebElement> errorMessages;

    //Organisation details
    @FindBy(xpath = ".//label[.='Organisation name']//following::input[1]")
    WebElement orgName;
    @FindBy(xpath = ".//*[contains(text(),'Address line 1')]//following::input[1]")
    WebElement addressLine1;
    @FindBy(xpath = ".//*[contains(text(),'Address line 2')]//following::input[1]")
    WebElement addressLine2;
    @FindBy(xpath = ".//label[contains(text(),'City')]//following::input[1]")
    WebElement townCity;
    @FindBy(xpath = ".//label[contains(text(),'Post')]//following::input[1]")
    WebElement postCode;
    @FindBy(xpath = ".//label[contains(text(),'Post')]//following::input[@type='text'][2]")
    WebElement telephone;
    @FindBy(xpath = ".//label[contains(text(),'Fax')]//following::input[1]")
    WebElement fax;
    @FindBy(xpath = ".//label[contains(text(),'Website')]//following::input[1]")
    WebElement website;
    @FindBy(xpath = ".//label[contains(text(),'Country')]//following::input[1]")
    WebElement country;

    //Contact Person Details
    @FindBy(xpath = ".//span[contains(text(),'Title')]//following::div[@role='listbox']")
    WebElement title;
    @FindBy(xpath = ".//label[.='First name']//following::input[1]")
    WebElement firstName;
    @FindBy(xpath = ".//label[.='Last name']//following::input[1]")
    WebElement lastName;
    @FindBy(xpath = ".//label[contains(text(),'Job title')]//following::input[1]")
    WebElement jobTitle;
    @FindBy(xpath = ".//label[.='Email']//following::input[1]")
    WebElement emailAddress;
    @FindBy(xpath = ".//label[.='Email']//following::input[2]")
    WebElement phoneNumber;

    //Letter of designation
    @FindBy(xpath = ".//label[contains(text(),'Authorised representative')]")
    WebElement authorisedRep;
    @FindBy(xpath = ".//label[contains(text(),'Distributor')]")
    WebElement distributor;
    @FindBy(css = ".FileUploadWidget---ui-inaccessible")
    WebElement fileUpload;

    //Buttons : Submit and cancel
    @FindBy(xpath = ".//button[contains(text(),'Save Registration')]")
    WebElement btnSaveRegistration;
    @FindBy(xpath = ".//button[contains(text(),'Continue')]")
    WebElement btnDeclareDevices;
    @FindBy(xpath = ".//button[.='Next']")
    WebElement btnNext;
    @FindBy(xpath = ".//button[.='Back']")
    WebElement btnBack;
    @FindBy(xpath = ".//button[.='Cancel']")
    WebElement btnCancel;

    //HTML Alert box
    @FindBy(xpath = ".//button[.='Yes']")
    WebElement btnYes;
    @FindBy(xpath = ".//button[.='No']")
    WebElement btnNo;

    //Indicators
    @FindBy(css = ".MilestoneWidget---milestone a")
    List<WebElement> listOfIndicators;
    @FindBy(css = ".MilestoneWidget---current a")
    WebElement selectedIndicator;


    @Autowired
    public _CreateCFSManufacturerTestHarnessPage(WebDriver driver) {
        super(driver);
    }

    /**
     * HELPS TESTERS CREATE TEST DATA ON THE GO
     * @param ar
     * @return
     */
    public AddDevices createTestOrganisation(ManufacturerRequestDO ar) throws Exception {
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        WaitUtils.waitForElementToBeClickable(driver, By.cssSelector(".PickerWidget---picker_value"), TIMEOUT_10_SECOND);
        WaitUtils.waitForElementToBeClickable(driver, orgName, TIMEOUT_3_SECOND);
        orgName.sendKeys(ar.organisationName);

        boolean exception = false;
        try {
            PageUtils.selectFromAutoSuggestedListItemsManufacturers(driver, ".PickerWidget---picker_value", ar.country, true);
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

        //Contact Person Details
        try {
            PageUtils.singleClick(driver, title);
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

        if(exception){
            PageUtils.selectFromAutoSuggestedListItemsManufacturers(driver, ".PickerWidget---picker_value", ar.country, true);
        }

        //Upload letter of designation
        if(ar.isManufacturer || !ar.isManufacturer){
            //Introduced in sprint 26, Not sure about this
            PageUtils.singleClick(driver, authorisedRep);
        }
        String fileName = "DesignationLetter1.pdf";
        if(!ar.isManufacturer){
            fileName = "DesignationLetter2.pdf";
        }

        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        PageUtils.uploadDocument(fileUpload, fileName, 2, 2);

        //Submit form : remember to verify
        try{
            btnDeclareDevices.click();
        }catch (Exception e){
            btnNext.click();
        }

        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);

        return new AddDevices(driver);
    }


    public boolean isErrorMessageDisplayed() {
        try {
            WaitUtils.waitForElementToBeVisible(driver, By.cssSelector(".FieldLayout---field_error"), TIMEOUT_3_SECOND);
            boolean isDisplayed = errorMessages.size() > 0;
            return isDisplayed;
        }catch (Exception e){
            return false;
        }
    }

    public _CreateCFSManufacturerTestHarnessPage clickBackButton() {
        //WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        WaitUtils.waitForElementToBeClickable(driver, btnBack, TIMEOUT_15_SECOND);
        btnBack.click();
        return new _CreateCFSManufacturerTestHarnessPage(driver);
    }

    public boolean isAlertBoxPresent() {
        WaitUtils.waitForElementToBeClickable(driver, btnYes, TIMEOUT_5_SECOND);
        WaitUtils.waitForElementToBeClickable(driver, btnNo, TIMEOUT_3_SECOND);
        return true;
    }

    public _CreateCFSManufacturerTestHarnessPage clickAlertButtonNo() {
        WaitUtils.waitForElementToBeClickable(driver, btnYes, TIMEOUT_5_SECOND);
        btnYes.click();
        return new _CreateCFSManufacturerTestHarnessPage(driver);
    }

    public CFSManufacturerList clickAlertButtonYes() {
        WaitUtils.waitForElementToBeClickable(driver, btnNo, TIMEOUT_5_SECOND);
        btnNo.click();
        return new CFSManufacturerList(driver);
    }

    public boolean isInProvideManufacturerDetailsPage() {
        return PageUtils.isElementClickable(driver, orgName, TIMEOUT_5_SECOND);
    }

    public boolean isIndicationStageDisplayed(String indicators) {
        return AssertUtils.checkCommaDelimitedTextContainsCorrectData(indicators, listOfIndicators);
    }
}
