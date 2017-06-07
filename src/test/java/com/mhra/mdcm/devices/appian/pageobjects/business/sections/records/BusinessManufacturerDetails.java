package com.mhra.mdcm.devices.appian.pageobjects.business.sections.records;

import com.mhra.mdcm.devices.appian.pageobjects._Page;
import com.mhra.mdcm.devices.appian.pageobjects.external._CreateCFSManufacturerTestHarnessPage;
import com.mhra.mdcm.devices.appian.pageobjects.external.cfs.CFSManufacturerList;
import com.mhra.mdcm.devices.appian.utils.selenium.page.PageUtils;
import com.mhra.mdcm.devices.appian.utils.selenium.page.WaitUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by TPD_Auto
 */
@Component
public class BusinessManufacturerDetails extends _Page {

    //Headings
    @FindBy(xpath = ".//h1")
    WebElement heading;
    @FindBy(xpath = ".//h2")
    WebElement subHeading;

    //Links to other sections like devices, documents
    @FindBy(partialLinkText = "product details")
    WebElement devicesAndProductDetails;
    @FindBy(xpath = ".//button[contains(text(),'Edit Account Information')]")
    WebElement editAccountInfoLink;

    //PARD message
    @FindBy(xpath = ".//*[contains(text(),'PARD selection')]//following::p[1]")
    WebElement pardMessage;

    //Unregister a registered device
    @FindBy(xpath = ".//button[contains(text(),'Unregister Manufacturer')]")
    WebElement btnUnregisterManufacturer;
    @FindBy(xpath = ".//button[contains(text(),'Unregister')]")
    WebElement btnUnregister;
    @FindBy(css = ".FileUploadWidget---ui-inaccessible")
    WebElement fileUpload;

    //HTML Alert box
    @FindBy(xpath = ".//button[.='Yes']")
    WebElement btnYes;
    @FindBy(xpath = ".//button[.='No']")
    WebElement btnNo;

    //Unregister reason
    @FindBy(xpath = ".//label[contains(text(),'Ceased Trading')]")
    WebElement rbCeasedTrading;
    @FindBy(xpath = ".//label[contains(text(),'No Longer Represented')]")
    WebElement rbNoLongerRpresented;

    //Unregisteration notifications
    @FindBy(xpath = ".//h2[contains(text(),'Send unregisteration')]//following::label[1]")
    WebElement cbToAuthorisedRep;
    @FindBy(xpath = ".//h2[contains(text(),'Send unregisteration')]//following::label[2]")
    WebElement cbToManufacturer;


    @Autowired
    public BusinessManufacturerDetails(WebDriver driver) {
        super(driver);
    }

    public boolean isManufacturerHeadingCorrect(String searchTerm) {
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        boolean contains = true;
        try {
            WaitUtils.waitForElementToBeClickable(driver, heading, TIMEOUT_10_SECOND);
            contains = heading.getText().contains(searchTerm);
        }catch (Exception e){
            WaitUtils.waitForElementToBeClickable(driver, devicesAndProductDetails, TIMEOUT_3_SECOND);
            contains = subHeading.getText().contains(searchTerm);
        }
        return contains;
    }

    public BusinessDeviceDetails clickOnDevicesLink(String link) {
        WaitUtils.waitForElementToBeClickable(driver, devicesAndProductDetails, TIMEOUT_3_SECOND);
        devicesAndProductDetails.click();
        return new BusinessDeviceDetails(driver);
    }


    public EditAccount gotoEditAccountInformation() {
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        WaitUtils.waitForElementToBeClickable(driver, editAccountInfoLink, TIMEOUT_DEFAULT);
        PageUtils.doubleClick(driver, editAccountInfoLink);
        //editAccountInfoLink.click();
        return new EditAccount(driver);
    }

    public boolean isPARDMessaageCorrect(String expectedMessage) {
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        WaitUtils.waitForElementToBeClickable(driver, pardMessage, TIMEOUT_DEFAULT);
        String msg = pardMessage.getText();
        log.info("Message : " + msg);
        boolean found = msg.contains(expectedMessage);
        return found;
    }

    public BusinessManufacturerDetails clickUnregisterManufacturerBtn() {
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        WaitUtils.waitForElementToBeClickable(driver, btnUnregisterManufacturer, TIMEOUT_DEFAULT);
        btnUnregisterManufacturer.click();
        return new BusinessManufacturerDetails(driver);
    }

    public BusinessManufacturerDetails submitUnRegistrationWithReason(String reason, boolean confirmUnregisttratoin) {
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        WaitUtils.waitForElementToBeVisible(driver, btnUnregister, TIMEOUT_DEFAULT);

        //Select a reason
        if(reason.contains("Ceased Trading")){
            rbCeasedTrading.click();
        }else if(reason.contains("No Longer Represented")){
            rbNoLongerRpresented.click();
            PageUtils.uploadDocument(fileUpload, "LetterOfCancellation1.pdf", 1, 2);
        }

        //Select send notifications to
        cbToAuthorisedRep.click();
        cbToManufacturer.click();

        //Click unregister button and confirm
        WaitUtils.waitForElementToBeClickable(driver, btnUnregister, TIMEOUT_DEFAULT);
        btnUnregister.click();
        return new BusinessManufacturerDetails(driver);
    }

    public BusinessManufacturerDetails clickAlertButtonYes() {
        WaitUtils.waitForElementToBeClickable(driver, btnYes, TIMEOUT_5_SECOND);
        btnYes.click();
        return new BusinessManufacturerDetails(driver);
    }

    public BusinessManufacturerDetails clickAlertButtonNo() {
        WaitUtils.waitForElementToBeClickable(driver, btnNo, TIMEOUT_5_SECOND);
        btnNo.click();
        return new BusinessManufacturerDetails(driver);
    }

}
