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
        WaitUtils.waitForElementToBeVisible(driver, linkAccounts, TIMEOUT_10_SECOND);
        WaitUtils.waitForElementToBeClickable(driver, linkAccounts, TIMEOUT_10_SECOND);
        //linkAccounts.click();
        PageUtils.singleClick(driver, linkAccounts);
        return new Accounts(driver);
    }

    public RegisteredDevices clickOnRegisteredDevices() {
        WaitUtils.waitForElementToBeVisible(driver, linkRegisteredDevices, TIMEOUT_10_SECOND);
        WaitUtils.waitForElementToBeClickable(driver, linkRegisteredDevices, TIMEOUT_10_SECOND);
        linkRegisteredDevices.click();
        return new RegisteredDevices(driver);
    }

    public Organisations clickOnOrganisations() {
        WaitUtils.waitForElementToBeVisible(driver, linkOrganisations, TIMEOUT_10_SECOND);
        WaitUtils.waitForElementToBeClickable(driver, linkOrganisations, TIMEOUT_10_SECOND);
        linkOrganisations.click();
        return new Organisations(driver);
    }

    public boolean isCorrectPage() {
        try {
            WaitUtils.waitForElementToBeVisible(driver, linkAll , TIMEOUT_5_SECOND);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    public RegisteredDevices clickOnGMDNDevices() {
        WaitUtils.waitForElementToBeVisible(driver, linkGMDNDevices, TIMEOUT_10_SECOND);
        WaitUtils.waitForElementToBeClickable(driver, linkGMDNDevices, TIMEOUT_10_SECOND);
        linkGMDNDevices.click();
        return new RegisteredDevices(driver);
    }

    public RegisteredProducts clickOnRegisteredProducts() {
        WaitUtils.waitForElementToBeVisible(driver, linkRegisteredProducts, TIMEOUT_20_SECOND);
        WaitUtils.waitForElementToBeClickable(driver, linkRegisteredProducts, TIMEOUT_10_SECOND);
        linkRegisteredProducts.click();
        return new RegisteredProducts(driver);
    }
}
