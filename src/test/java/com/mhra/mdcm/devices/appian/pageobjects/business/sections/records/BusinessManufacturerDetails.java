package com.mhra.mdcm.devices.appian.pageobjects.business.sections.records;

import com.mhra.mdcm.devices.appian.pageobjects._Page;
import com.mhra.mdcm.devices.appian.utils.selenium.page.PageUtils;
import com.mhra.mdcm.devices.appian.utils.selenium.page.WaitUtils;
import org.openqa.selenium.By;
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
    @FindBy(xpath = ".//h4")
    WebElement heading;
    @FindBy(xpath = ".//h2")
    WebElement subHeading;

    //Links to other sections like devices, documents
    @FindBy(partialLinkText = "product details")
    WebElement devicesAndProductDetails;
    @FindBy(linkText = "Edit Account Information")
    WebElement editAccountInfoLink;

    //PARD message
    @FindBy(xpath = ".//*[contains(text(),'PARD selection')]//following::p[1]")
    WebElement pardMessage;

    @Autowired
    public BusinessManufacturerDetails(WebDriver driver) {
        super(driver);
    }

    public boolean isManufacturerHeadingCorrect(String searchTerm) {
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        boolean contains = true;
        try {
            contains = heading.getText().contains(searchTerm);
        }catch (Exception e){
            contains = subHeading.getText().contains(searchTerm);
        }
        return contains;
    }

    public BusinessDeviceDetails clickOnDevicesLink(String link) {
        WaitUtils.waitForElementToBeClickable(driver, devicesAndProductDetails, TIMEOUT_3_SECOND, false);
        devicesAndProductDetails.click();
        return new BusinessDeviceDetails(driver);
    }


    public EditAccounts gotoEditAccountInformation() {
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        WaitUtils.waitForElementToBeClickable(driver, editAccountInfoLink, TIMEOUT_DEFAULT, false);
        PageUtils.doubleClick(driver, editAccountInfoLink);
        //editAccountInfoLink.click();
        return new EditAccounts(driver);
    }

    public boolean isPARDMessaageCorrect(String expectedMessage) {
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        WaitUtils.waitForElementToBeClickable(driver, pardMessage, TIMEOUT_DEFAULT, false);
        String msg = pardMessage.getText();
        log.info("Message : " + msg);
        boolean found = msg.contains(expectedMessage);
        return found;
    }
}
