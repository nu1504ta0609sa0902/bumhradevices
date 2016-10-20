package com.mhra.mdcm.devices.appian.pageobjects.business;

import com.mhra.mdcm.devices.appian.pageobjects._Page;
import com.mhra.mdcm.devices.appian.utils.selenium.page.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by TPD_Auto
 */
@Component
public class RecordsPage extends _Page {

    @FindBy(partialLinkText = "Accounts")
    WebElement linkAccounts;
    @FindBy(partialLinkText = "Devices")
    WebElement linkDevices;
    @FindBy(partialLinkText = "Products")
    WebElement linkProducts;

    @FindBy(xpath = ".//h2[.='Status']//following::a")
    List<WebElement> listOfAccounts;
    @FindBy(xpath = ".//h2[.='device Id']//following::a")
    List<WebElement> listOfDevices;
    @FindBy(xpath = ".//h2[.='model']//following::a")
    List<WebElement> listOfProducts;

    @Autowired
    public RecordsPage(WebDriver driver) {
        super(driver);
    }

    public RecordsPage clickOnAccounts() {
        WaitUtils.isElementPartOfDomAdvanced2(driver, By.partialLinkText("Accounts"), 10, false);
        WaitUtils.waitForElementToBeClickable(driver, linkAccounts, 10, false);
        linkAccounts.click();
        return new RecordsPage(driver);
    }

    public RecordsPage clickOnDevices() {
        WaitUtils.isElementPartOfDomAdvanced2(driver, By.partialLinkText("Devices"), 10, false);
        WaitUtils.waitForElementToBeClickable(driver, linkDevices, 10, false);
        linkDevices.click();
        return new RecordsPage(driver);
    }

    public RecordsPage clickOnProducts() {
        WaitUtils.isElementPartOfDomAdvanced2(driver, By.partialLinkText("Products"), 10, false);
        WaitUtils.waitForElementToBeClickable(driver, linkProducts, 10, false);
        linkProducts.click();
        return new RecordsPage(driver);
    }

    public boolean isHeadingCorrect(String expectedHeadings) {
        WaitUtils.waitForElementToBeClickable(driver, By.xpath(".//h2[.='" + expectedHeadings + "']") , 10, false);
        WebElement heading = driver.findElement(By.xpath(".//h2[.='" + expectedHeadings + "']"));
        boolean contains = heading.getText().contains(expectedHeadings);
        return contains;
    }

    public boolean isItemsDisplayed(String expectedHeadings) {
        boolean itemsDisplayed = false;
        WaitUtils.waitForElementToBeClickable(driver, By.xpath(".//h2[.='" + expectedHeadings + "']") , 10, false);

        if(expectedHeadings.contains("Accounts")){
            itemsDisplayed = listOfAccounts.size() > 0;
        }else if(expectedHeadings.contains("Devices")){
            itemsDisplayed = listOfDevices.size() > 0;
        }else if(expectedHeadings.contains("Products")){
            itemsDisplayed = listOfProducts.size() > 0;
        }
        return itemsDisplayed;
    }
}
