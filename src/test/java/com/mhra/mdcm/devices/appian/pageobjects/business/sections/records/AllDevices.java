package com.mhra.mdcm.devices.appian.pageobjects.business.sections.records;

import com.mhra.mdcm.devices.appian.pageobjects._Page;
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
public class AllDevices extends _Page {

    @FindBy(xpath = ".//h2[.='GMDN definition']//following::a")
    List<WebElement> listOfAllDevices;

    @Autowired
    public AllDevices(WebDriver driver) {
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

        if(expectedHeadings.contains("All Devices")){
            itemsDisplayed = listOfAllDevices.size() > 0;
        }

        return itemsDisplayed;
    }
}
