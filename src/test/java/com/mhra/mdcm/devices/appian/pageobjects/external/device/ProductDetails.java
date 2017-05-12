package com.mhra.mdcm.devices.appian.pageobjects.external.device;

import com.mhra.mdcm.devices.appian.domains.newaccounts.DeviceDO;
import com.mhra.mdcm.devices.appian.pageobjects._Page;
import com.mhra.mdcm.devices.appian.utils.selenium.page.AssertUtils;
import com.mhra.mdcm.devices.appian.utils.selenium.page.WaitUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by TPD_Auto
 *
 * When viewing products related to added devices
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
    @FindBy(xpath = ".//span[contains(text(),'Notified')]//following::p[1]")
    WebElement notifiedBody;


    @Autowired
    public ProductDetails(WebDriver driver) {
        super(driver);
    }

    public boolean isProductOrDeviceDetailValid(DeviceDO deviceData) {
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        WaitUtils.waitForElementToBeClickable(driver, customMade, TIMEOUT_10_SECOND);
        boolean allValid = true;
        String deviceType = deviceData.deviceType;
        if (deviceType.equals("General Medical Device")) {
            allValid = isGeneralMedicalDeviceValid(deviceData);
        } else if (deviceType.equals("In Vitro Diagnostic Device")) {
            allValid = isGeneralMedicalDeviceValid(deviceData);
        } else if (deviceType.equals("System or Procedure Pack")) {
            allValid = isGeneralMedicalDeviceValid(deviceData);
        } else if (deviceType.equals("Active Implantable Medical Devices")) {
            allValid = isGeneralMedicalDeviceValid(deviceData);
        }

        return allValid;
    }

    private boolean isGeneralMedicalDeviceValid(DeviceDO deviceData) {
        boolean allValid = true;
        String fields[] = new String[]{
                "gmdn", "risk classification", "custom made", "sterile", "measuring", "notified body"
        };

        for (String field : fields) {
            if (field.equals("gmdn")) {
                //Check and verify data is correct
                String termOrDefinition = deviceData.gmdnTermOrDefinition;
                if (termOrDefinition != null && !termOrDefinition.equals("")) {
                    WaitUtils.waitForElementToBeClickable(driver, gmdnTermDefinition, TIMEOUT_5_SECOND);
                    allValid = AssertUtils.areChangesDisplayed(gmdnTermDefinition, termOrDefinition);
                } else {
                    //Gmdn code
                    WaitUtils.waitForElementToBeClickable(driver, gmdnCode, TIMEOUT_5_SECOND);
                    allValid = AssertUtils.areChangesDisplayed(gmdnCode, deviceData.gmdnCode);
                }
            } else if (field.equals("custom made")) {
                boolean data = deviceData.isCustomMade;
                if (data) {
                    allValid = AssertUtils.areChangesDisplayed(customMade, "Yes");
                } else {
                    allValid = AssertUtils.areChangesDisplayed(customMade, "No");
                }
            } else if (field.equals("risk classification")) {
                //Only relevant if its not custom made
                if (!deviceData.isCustomMade) {
                    String data = deviceData.riskClassification;
                    if (data != null && !data.equals("")) {
                        data = "Class";
                        allValid = AssertUtils.areChangesDisplayed(riskClassification, data);
                    }
                }
            } else if (field.equals("sterile")) {
                //Only relevant if its not custom made
                if (!deviceData.isCustomMade) {
                    boolean data = deviceData.isDeviceSterile;
                    if (data) {
                        allValid = AssertUtils.areChangesDisplayed(sterile, "Yes");
                    } else {
                        allValid = AssertUtils.areChangesDisplayed(sterile, "No");
                    }
                }
            } else if (field.equals("measuring")) {
                //Only relevant if its not custom made
                if (!deviceData.isCustomMade) {
                    boolean data = deviceData.isDeviceMeasuring;
                    if (data) {
                        allValid = AssertUtils.areChangesDisplayed(measuring, "Yes");
                    } else {
                        allValid = AssertUtils.areChangesDisplayed(measuring, "No");
                    }
                }
            } else if (field.equals("notified body")) {
                //Only relevant if its not custom made : No longer required verified by Hasan 09/05/2017 email
//                if (!deviceData.isCustomMade) {
//                    String notifiedBodyTxt = deviceData.notifiedBody;
//                    if (notifiedBodyTxt != null) {
//                        allValid = AssertUtils.areChangesDisplayed(notifiedBody, notifiedBodyTxt);
//                    }
//                }
            }

            if (!allValid) {
                log.error("Field data not correct : " + field);
                break;
            }
        }

        return allValid;
    }
}
