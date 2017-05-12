package com.mhra.mdcm.devices.appian.pageobjects.business.sections.records;

import com.mhra.mdcm.devices.appian.pageobjects._Page;
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
public class BusinessProductDetails extends _Page {

    @FindBy(xpath = ".//h4")
    WebElement heading;

    //Device information
    @FindBy(xpath = ".//span[.='GMDN code']//following::p[1]")
    WebElement diGmdnCode;
    @FindBy(xpath = ".//span[.='GMDN term']//following::p[1]")
    WebElement diGmdnTerm;
    @FindBy(xpath = ".//span[.='Risk classification']//following::p[1]")
    WebElement diRiskClassification;

    //Product information
    @FindBy(xpath = ".//span[.='Product code']//following::p[1]")
    WebElement piProductCode;
    @FindBy(xpath = ".//span[.='Product make']//following::p[1]")
    WebElement piProductMake;
    @FindBy(xpath = ".//span[.='Product model']//following::p[1]")
    WebElement piProductModel;
    @FindBy(xpath = ".//span[.='Product name']//following::p[1]")
    WebElement piProductName;


    @Autowired
    public BusinessProductDetails(WebDriver driver) {
        super(driver);
    }


    public boolean areAllFieldsVisible() {
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        boolean fieldsDisplayed = true;
        try {
            WaitUtils.waitForElementToBeClickable(driver, diGmdnCode, TIMEOUT_3_SECOND);
            WaitUtils.waitForElementToBeClickable(driver, diGmdnTerm, TIMEOUT_3_SECOND);
            WaitUtils.waitForElementToBeClickable(driver, diRiskClassification, TIMEOUT_3_SECOND);
            WaitUtils.waitForElementToBeClickable(driver, piProductCode, TIMEOUT_3_SECOND);
            WaitUtils.waitForElementToBeClickable(driver, piProductMake, TIMEOUT_3_SECOND);
            WaitUtils.waitForElementToBeClickable(driver, piProductModel, TIMEOUT_3_SECOND);
            WaitUtils.waitForElementToBeClickable(driver, piProductName, TIMEOUT_3_SECOND);
        }catch (Exception e){
            e.printStackTrace();
            fieldsDisplayed = false;
        }
        return fieldsDisplayed;
    }
}
