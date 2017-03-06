package com.mhra.mdcm.devices.appian.pageobjects.business.sections.records;

import com.mhra.mdcm.devices.appian.pageobjects._Page;
import com.mhra.mdcm.devices.appian.utils.selenium.page.PageUtils;
import com.mhra.mdcm.devices.appian.utils.selenium.page.WaitUtils;
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
public class BusinessDeviceDetails extends _Page {

    //Device table
    @FindBy(xpath = ".//td/a")
    List<WebElement> listOfTableHeadings;
    @FindBy(xpath = ".//div/h6")
    List<WebElement> listOfDeviceHeadings;

    //Device by device types
    @FindBy(xpath = ".//h6[contains(text(), 'General Medical')]//following::tbody[1]/tr")
    List<WebElement> listOfGMDDevices;
    @FindBy(xpath = ".//h6[contains(text(), 'In Vitro')]//following::tbody[1]/tr")
    List<WebElement> listOfIVDDevices;
    @FindBy(xpath = ".//h6[contains(text(), 'Active Implantable')]//following::tbody[1]/tr")
    List<WebElement> listOfAIMDDevices;
    @FindBy(xpath = ".//h6[contains(text(), 'Procedure')]//following::tbody[1]/tr")
    List<WebElement> listOfSPPDevices;

    //Device information page fields

    @Autowired
    public BusinessDeviceDetails(WebDriver driver) {
        super(driver);
    }

    public boolean isDeviceTableDisplayed() {
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        return listOfDeviceHeadings.size() > 0;
    }

    public boolean isTableColumnsCorrect(String expectedHeadings) {
        boolean allFound = true;
        for(WebElement el: listOfTableHeadings){
            String text = el.getText();
            log.info(text);
            if(!expectedHeadings.contains(text)){
                allFound = false;
                break;
            }
        }
        return allFound;
    }

    public BusinessDeviceDetails viewDeviceOfType(String deviceType, String gmdnTermOrDefinition) {
        if(deviceType.contains("general medical")){
            PageUtils.clickOnGMDNCodeOrDefinition(listOfGMDDevices, gmdnTermOrDefinition);
        }else if(deviceType.contains("vitro")){
            PageUtils.clickOnGMDNCodeOrDefinition(listOfIVDDevices, gmdnTermOrDefinition);
        }else if(deviceType.contains("active")){
            PageUtils.clickOnGMDNCodeOrDefinition(listOfAIMDDevices, gmdnTermOrDefinition);
        }else if(deviceType.contains("procedure pack")){
            PageUtils.clickOnGMDNCodeOrDefinition(listOfSPPDevices, gmdnTermOrDefinition);
        }
        return new BusinessDeviceDetails(driver);
    }

    public boolean areDeviceInformationPageShowingCorrectFields(String deviceType) {
        boolean fieldsCorrect = true;
        if(deviceType.contains("general medical")){

        }else if(deviceType.contains("vitro")){

        }else if(deviceType.contains("active")){

        }else if(deviceType.contains("procedure pack")){

        }
    }


    public boolean isDisplayedOrgFieldsCorrect() {
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        boolean fieldsDisplayed = true;
        try {
            //WaitUtils.waitForElementToBeClickable(driver, orgName, TIMEOUT_3_SECOND, false);
            WaitUtils.waitForElementToBeClickable(driver, orgAddressLine1, TIMEOUT_3_SECOND, false);
            WaitUtils.waitForElementToBeClickable(driver, orgAddressLine2, TIMEOUT_3_SECOND, false);
            WaitUtils.waitForElementToBeClickable(driver, orgCityTown, TIMEOUT_3_SECOND, false);
            WaitUtils.waitForElementToBeClickable(driver, orgPostCode, TIMEOUT_3_SECOND, false);
            WaitUtils.waitForElementToBeClickable(driver, orgCountry, TIMEOUT_3_SECOND, false);
            WaitUtils.waitForElementToBeClickable(driver, orgTelephone, TIMEOUT_3_SECOND, false);
            WaitUtils.waitForElementToBeClickable(driver, orgFax, TIMEOUT_3_SECOND, false);
            WaitUtils.waitForElementToBeClickable(driver, webSite, TIMEOUT_3_SECOND, false);
        }catch (Exception e){
            e.printStackTrace();
            fieldsDisplayed = false;
        }
        return fieldsDisplayed;
    }
}
