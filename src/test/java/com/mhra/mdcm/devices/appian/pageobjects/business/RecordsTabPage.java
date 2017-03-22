package com.mhra.mdcm.devices.appian.pageobjects.business;

import com.mhra.mdcm.devices.appian.pageobjects._Page;
import com.mhra.mdcm.devices.appian.pageobjects.business.sections.records.Accounts;
import com.mhra.mdcm.devices.appian.pageobjects.business.sections.records.AllOrganisations;
import com.mhra.mdcm.devices.appian.pageobjects.business.sections.records.Devices;
import com.mhra.mdcm.devices.appian.pageobjects.business.sections.records.Products;
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
public class RecordsTabPage extends _Page {

    @FindBy(linkText = "Accounts")
    WebElement linkAccounts;
    @FindBy(linkText = "Devices")
    WebElement linkDevices;
    @FindBy(linkText = "Products")
    WebElement linkProducts;
    @FindBy(linkText = "All Organisations")
    WebElement linkAllOrganisations;
    @FindBy(linkText = "All Devices")
    WebElement linkAllDevices;
    @FindBy(linkText = "All Products")
    WebElement linkAllProducts;

    @FindBy(partialLinkText = "All")
    WebElement linkAll;

    @Autowired
    public RecordsTabPage(WebDriver driver) {
        super(driver);
    }

    public Accounts clickOnAccounts() {
        WaitUtils.waitForElementToBeClickable(driver, By.linkText("Accounts"), 10, false);
        WaitUtils.waitForElementToBeClickable(driver, linkAccounts, 10, false);
        //linkAccounts.click();
        PageUtils.singleClick(driver, linkAccounts);
        return new Accounts(driver);
    }

    public Devices clickOnDevices() {
        WaitUtils.waitForElementToBeClickable(driver, By.linkText("Devices"), 10, false);
        WaitUtils.waitForElementToBeClickable(driver, linkDevices, 10, false);
        linkDevices.click();
        return new Devices(driver);
    }

    public Products clickOnProducts() {
        WaitUtils.waitForElementToBeClickable(driver, By.linkText("Products"), 20, false);
        WaitUtils.waitForElementToBeClickable(driver, linkProducts, 10, false);
        linkProducts.click();
        return new Products(driver);
    }

    public AllOrganisations clickOnAllOrganisations() {
        WaitUtils.waitForElementToBeClickable(driver, By.partialLinkText("All Organisations"), 10, false);
        WaitUtils.waitForElementToBeClickable(driver, linkAllOrganisations, 10, false);
        linkAllOrganisations.click();
        return new AllOrganisations(driver);
    }

    public boolean isCorrectPage() {
        try {
            WaitUtils.waitForElementToBeVisible(driver, linkAll , TIMEOUT_5_SECOND, false);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    public Devices clickOnAllDevices() {
        WaitUtils.waitForElementToBeClickable(driver, By.linkText("All Devices"), 10, false);
        WaitUtils.waitForElementToBeClickable(driver, linkAllDevices, 10, false);
        linkAllDevices.click();
        return new Devices(driver);
    }

    public Products clickOnAllProducts() {
        WaitUtils.waitForElementToBeClickable(driver, By.linkText("All Products"), 20, false);
        WaitUtils.waitForElementToBeClickable(driver, linkAllProducts, 10, false);
        linkAllProducts.click();
        return new Products(driver);
    }
}
