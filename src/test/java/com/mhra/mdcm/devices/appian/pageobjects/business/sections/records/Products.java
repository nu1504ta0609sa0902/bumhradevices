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
public class Products extends _Page {

    @FindBy(xpath = ".//h2[.='model']//following::a")
    List<WebElement> listOfProducts;

    @Autowired
    public Products(WebDriver driver) {
        super(driver);
    }

    public boolean isHeadingCorrect(String expectedHeadings) {
        By by = By.xpath(".//h2[.='" + expectedHeadings + "']");
        WaitUtils.waitForElementToBeClickable(driver, by, TIMEOUT_10_SECOND);
        WebElement heading = driver.findElement(by);
        boolean contains = heading.getText().contains(expectedHeadings);
        return contains;
    }

    public boolean isItemsDisplayed(String expectedHeadings) {
        boolean itemsDisplayed = false;
        WaitUtils.waitForElementToBeClickable(driver, By.xpath(".//h2[.='" + expectedHeadings + "']") , TIMEOUT_10_SECOND);

        if(expectedHeadings.contains("Products")){
            itemsDisplayed = listOfProducts.size() > 0;
        }

        return itemsDisplayed;
    }

}
