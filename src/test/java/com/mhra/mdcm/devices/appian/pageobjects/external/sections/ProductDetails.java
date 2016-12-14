package com.mhra.mdcm.devices.appian.pageobjects.external.sections;

import com.mhra.mdcm.devices.appian.domains.newaccounts.DeviceData;
import com.mhra.mdcm.devices.appian.pageobjects._Page;
import com.mhra.mdcm.devices.appian.pageobjects.external.ExternalHomePage;
import com.mhra.mdcm.devices.appian.utils.selenium.others.RandomDataUtils;
import com.mhra.mdcm.devices.appian.utils.selenium.others.TestHarnessUtils;
import com.mhra.mdcm.devices.appian.utils.selenium.page.AssertUtils;
import com.mhra.mdcm.devices.appian.utils.selenium.page.CommonUtils;
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
public class ProductDetails extends _Page {

    //General Medical Device Data
    @FindBy(xpath = ".//span[.='GMDN code']//following::p[1]")
    WebElement gmdnCode;
    @FindBy(xpath = ".//span[.='GMDN term']//following::p[1]")
    WebElement gmdnTermDefinition;
    @FindBy(xpath = ".//span[.='Risk classification']//following::p[1]")
    WebElement riskClassification;
    @FindBy(xpath = ".//span[.='Custom made']//following::p[1]")
    WebElement customMade;
    @FindBy(xpath = ".//span[contains(text(),'Sterile')]//following::p[1]")
    WebElement sterile;
    @FindBy(xpath = ".//span[contains(text(),'Measuring')]//following::p[1]")
    WebElement measuring;


    @Autowired
    public ProductDetails(WebDriver driver) {
        super(driver);
    }

    public boolean isProductOrDeviceDetailValid(DeviceData deviceData) {
        boolean allValid = true;
        String deviceType = deviceData.deviceType;
        if(deviceType.equals("General Medical Device")){
            allValid = isGeneralMedicalDeviceValid(deviceData);
        }

        return allValid;
    }

    private boolean isGeneralMedicalDeviceValid(DeviceData deviceData) {
        boolean allValid = true;
        String fields [] = new String []{
            "gmdn", "risk classification", "custom made", "sterile", "measuring"
        };

        for(String field: fields){
            if(field.equals("gmdn")){
                //Check and verify data is correct
                String termOrDefinition = deviceData.gmdnTermOrDefinition;
                if(termOrDefinition!=null && !termOrDefinition.equals("")){
                    WaitUtils.waitForElementToBeClickable(driver, gmdnTermDefinition, TIMEOUT_5_SECOND, false);
                    allValid = AssertUtils.areChangesDisplayed(gmdnTermDefinition, termOrDefinition);
                }else{
                    //Gmdn code
                    WaitUtils.waitForElementToBeClickable(driver, gmdnCode, TIMEOUT_5_SECOND, false);
                    allValid =  AssertUtils.areChangesDisplayed(gmdnCode, deviceData.gmdnCode);
                }
            }else if(field.equals("risk classification")){
                String data = deviceData.riskClassification;
                if(data!=null && !data.equals("")){
                    data = "Class";
                    allValid = AssertUtils.areChangesDisplayed(riskClassification, data);
                }
            }else if(field.equals("custom made")){
                boolean data = deviceData.isCustomMade;
                if(data){
                    allValid = AssertUtils.areChangesDisplayed(customMade, "Yes");
                }else{
                    allValid = AssertUtils.areChangesDisplayed(customMade, "No");
                }
            }else if(field.equals("sterile")){
                boolean data = deviceData.isDeviceSterile;
                if(data){
                    allValid = AssertUtils.areChangesDisplayed(sterile, "Yes");
                }else{
                    allValid = AssertUtils.areChangesDisplayed(sterile, "No");
                }
            }else if(field.equals("measuring")){
                boolean data = deviceData.isDeviceMeasuring;
                if(data){
                    allValid = AssertUtils.areChangesDisplayed(measuring, "Yes");
                }else{
                    allValid = AssertUtils.areChangesDisplayed(measuring, "No");
                }
            }

            if(!allValid){
                break;
            }
        }

        return allValid;
    }
}