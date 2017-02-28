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

import java.util.List;

/**
 * Created by TPD_Auto 
 */
@Component
public class Devices extends _Page {

    //List of all devices and types
    @FindBy(xpath = ".//h2[.='Device Id']//following::a")
    List<WebElement> listOfDevices;
    @FindBy(xpath = ".//*[.='GMDN term']//following::tr//td[1]")
    List<WebElement> listOfDeviceTypes;

    //Filter values
    @FindBy(partialLinkText = "IVD")
    WebElement filterByDeviceType;

    @Autowired
    public Devices(WebDriver driver) {
        super(driver);
    }


    public boolean isHeadingCorrect(String expectedHeadings) {
        WaitUtils.waitForElementToBeClickable(driver, By.xpath(".//h2[.='" + expectedHeadings + "']") , TIMEOUT_DEFAULT, false);
        WebElement heading = driver.findElement(By.xpath(".//h2[.='" + expectedHeadings + "']"));
        boolean contains = heading.getText().contains(expectedHeadings);
        return contains;
    }


    public boolean isItemsDisplayed(String expectedHeadings) {
        boolean itemsDisplayed = false;
        WaitUtils.waitForElementToBeClickable(driver, By.xpath(".//h2[.='" + expectedHeadings + "']") , TIMEOUT_DEFAULT, false);

        if(expectedHeadings.equals("Devices")){
            itemsDisplayed = listOfDevices.size() > 0;
        }

        return itemsDisplayed;
    }

    public Devices filterBy(String deviceType) {
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        By by = By.partialLinkText(deviceType);
        WaitUtils.waitForElementToBeClickable(driver, by, TIMEOUT_10_SECOND, false);
        WebElement element = driver.findElement(by);
        PageUtils.doubleClick(driver, element);
        return new Devices(driver);
    }

    public boolean areAllDevicesOfType(String deviceType) {

        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
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

    public Devices clearFilterByDeviceType() {
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        WaitUtils.waitForElementToBeClickable(driver, filterByDeviceType, TIMEOUT_3_SECOND, false);
        filterByDeviceType.click();
        return new Devices(driver);
    }

    public boolean areDevicesOfTypeVisible(String deviceType) {
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        boolean aMatchFound = false;
        for(WebElement el: listOfDeviceTypes){
            String text = el.getText();
            //log.info(text);
            aMatchFound = text.contains(deviceType);
            if (aMatchFound) {
                break;
            }
        }
        return aMatchFound;
    }
}
