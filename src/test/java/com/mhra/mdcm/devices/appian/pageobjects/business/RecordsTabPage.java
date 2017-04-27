package com.mhra.mdcm.devices.appian.pageobjects.business;

import com.mhra.mdcm.devices.appian.pageobjects._Page;
import com.mhra.mdcm.devices.appian.pageobjects.business.sections.records.*;
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
public class RecordsTabPage extends _Page {

    @FindBy(linkText = "Accounts")
    WebElement linkAccounts;
    @FindBy(linkText = "Organisations")
    WebElement linkOrganisations;
    @FindBy(linkText = "GMDN Devices")
    WebElement linkGMDNDevices;
    @FindBy(linkText = "Registered Devices")
    WebElement linkRegisteredDevices;
    @FindBy(linkText = "Registered Products")
    WebElement linkRegisteredProducts;
    @FindBy(linkText = "Products")
    WebElement linkProducts;

    @FindBy(partialLinkText = "All")
    WebElement linkAll;

    @Autowired
    public RecordsTabPage(WebDriver driver) {
        super(driver);
    }

    public Accounts clickOnAccounts() {
        WaitUtils.waitForElementToBeVisible(driver, linkAccounts, 10, false);
        WaitUtils.waitForElementToBeClickable(driver, linkAccounts, 10, false);
        //linkAccounts.click();
        PageUtils.singleClick(driver, linkAccounts);
        return new Accounts(driver);
    }

    public RegisteredDevices clickOnRegisteredDevices() {
        WaitUtils.waitForElementToBeVisible(driver, linkRegisteredDevices, 10, false);
        WaitUtils.waitForElementToBeClickable(driver, linkRegisteredDevices, 10, false);
        linkRegisteredDevices.click();
        return new RegisteredDevices(driver);
    }

//    public Products clickOnProducts() {
//        WaitUtils.waitForElementToBeVisible(driver, linkProducts, 20, false);
//        WaitUtils.waitForElementToBeClickable(driver, linkProducts, 10, false);
//        linkProducts.click();
//        return new Products(driver);
//    }

    public Organisations clickOnOrganisations() {
        WaitUtils.waitForElementToBeVisible(driver, linkOrganisations, 10, false);
        WaitUtils.waitForElementToBeClickable(driver, linkOrganisations, 10, false);
        linkOrganisations.click();
        return new Organisations(driver);
    }

    public boolean isCorrectPage() {
        try {
            WaitUtils.waitForElementToBeVisible(driver, linkAll , TIMEOUT_5_SECOND, false);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    public RegisteredDevices clickOnGMDNDevices() {
        WaitUtils.waitForElementToBeVisible(driver, linkGMDNDevices, 10, false);
        WaitUtils.waitForElementToBeClickable(driver, linkGMDNDevices, 10, false);
        linkGMDNDevices.click();
        return new RegisteredDevices(driver);
    }

    public RegisteredProducts clickOnRegisteredProducts() {
        WaitUtils.waitForElementToBeVisible(driver, linkRegisteredProducts, 20, false);
        WaitUtils.waitForElementToBeClickable(driver, linkRegisteredProducts, 10, false);
        linkRegisteredProducts.click();
        return new RegisteredProducts(driver);
    }
}
