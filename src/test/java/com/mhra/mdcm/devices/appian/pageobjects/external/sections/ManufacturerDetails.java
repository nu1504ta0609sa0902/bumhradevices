package com.mhra.mdcm.devices.appian.pageobjects.external.sections;

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
public class ManufacturerDetails extends _Page {

    @FindBy(css = ".component_error")
    List <WebElement> errorMessages;

    @FindBy(css = "a.GFWJSJ4DGR[aria-label='Add a device']")
    WebElement addADevice;
    @FindBy(xpath = ".//a[contains(text(),'Amend Represented')]")
    WebElement amendRepresentativeParty;

    @FindBy(css = "div>h4")
    WebElement orgName;

    @Autowired
    public ManufacturerDetails(WebDriver driver) {
        super(driver);
    }

    public boolean isOrganisationNameCorrect(String name) {
        WaitUtils.waitForElementToBeClickable(driver, orgName, TIMEOUT_DEFAULT, false);
        boolean contains = orgName.getText().contains(name);
        return contains;
    }


    public boolean isErrorMessageDisplayed() {
        try {
            WaitUtils.waitForElementToBeVisible(driver, By.cssSelector(".component_error"), 3, false);
            boolean isDisplayed = errorMessages.size() > 0;
            return isDisplayed;
        }catch (Exception e){
            return false;
        }
    }

    public AddDevices clickAddDeviceBtn() {
        WaitUtils.waitForElementToBeClickable(driver, addADevice, TIMEOUT_10_SECOND, false);
        //WaitUtils.waitForElementToBeClickable(driver, amendRepresentativeParty, TIMEOUT_5_SECOND, false);
        addADevice.click();
        return new AddDevices(driver);
    }
}
