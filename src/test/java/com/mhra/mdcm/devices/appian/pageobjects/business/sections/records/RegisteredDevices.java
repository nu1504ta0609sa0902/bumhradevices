package com.mhra.mdcm.devices.appian.pageobjects.business.sections.records;

import com.mhra.mdcm.devices.appian.enums.PageHeaders;
import com.mhra.mdcm.devices.appian.pageobjects._Page;
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
public class RegisteredDevices extends _Page {

    //List of all devices and types
    @FindBy(xpath = ".//*[.='Device Id']//following::a")
    List<WebElement> listOfDevices;
    @FindBy(xpath = ".//th[@abbr='Device Id']//following::tr//td[1]")
    List<WebElement> listOfDeviceTypes;

    //Filter values
    @FindBy(xpath = ".//span[@class='DropdownWidget---inline_label']")
    List<WebElement> listOfDropDownFilters;
    @FindBy(linkText = "Clear Filters")
    WebElement clearFilters;

    @Autowired
    public RegisteredDevices(WebDriver driver) {
        super(driver);
    }


    public boolean isHeadingCorrect(String expectedHeadings) {
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        By by = By.xpath(".//h1[.='" + expectedHeadings + "']");
        WaitUtils.waitForElementToBeClickable(driver, by, TIMEOUT_10_SECOND);
        WebElement heading = driver.findElement(by);
        boolean contains = heading.getText().contains(expectedHeadings);
        return contains;
    }


    public boolean isItemsDisplayed(String expectedHeadings) {
        boolean itemsDisplayed = false;
        WaitUtils.waitForElementToBeClickable(driver, By.xpath(".//h1[.='" + expectedHeadings + "']") , TIMEOUT_10_SECOND);

        if(expectedHeadings.equals(PageHeaders.PAGE_HEADERS_REGISTERED_DEVICES.header)){
            itemsDisplayed = listOfDevices.size() > 0;
        }

        return itemsDisplayed;
    }

    public RegisteredDevices filterBy(String deviceType) {
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        PageUtils.selectFromDropDown(driver, listOfDropDownFilters.get(0) , deviceType, false);
        return new RegisteredDevices(driver);
    }

    public boolean areAllDevicesOfType(String deviceType) {

        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        WaitUtils.waitForElementToBeClickable(driver, clearFilters, TIMEOUT_10_SECOND);
        boolean allMatched = true;
        for(WebElement el: listOfDeviceTypes){
            String text = el.getText();
            //log.info(text);
            if(!text.contains("revious") && !text.contains("ext")) {
                allMatched = text.contains(deviceType);
                if (!allMatched) {
                    break;
                }
            }
        }
        return allMatched;
    }

    public RegisteredDevices clearFilterByDeviceType() {
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        WaitUtils.waitForElementToBeClickable(driver, clearFilters, TIMEOUT_3_SECOND);
        clearFilters.click();
        return new RegisteredDevices(driver);
    }


    public RegisteredDevices sortBy(String tableHeading, int numberOfTimesToClick) {
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        if (tableHeading.equals("Name")) {
            for (int c = 0; c < numberOfTimesToClick; c++) {
//                WaitUtils.waitForElementToBeClickable(driver, thOrganisationName, TIMEOUT_10_SECOND);
//                thOrganisationName.click();
//                WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
            }
        }

        return new RegisteredDevices(driver);
    }
}
