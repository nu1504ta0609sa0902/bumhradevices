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
public class AddDevices extends _Page {

    @FindBy(css = ".component_error")
    List <WebElement> errorMessages;

    @FindBy(xpath = ".//button[.='Add device']")
    WebElement btnAddDevice;

    @FindBy(css = ".GFWJSJ4DCW label")
    List<WebElement> listOfDeviceTypes;

    @Autowired
    public AddDevices(WebDriver driver) {
        super(driver);
    }

    public AddDevices addDevice() {
        WaitUtils.waitForElementToBeClickable(driver, btnAddDevice, TIMEOUT_5_SECOND, false);
        btnAddDevice.click();
        return new AddDevices(driver);
    }

    public boolean isDeviceTypeCorrect() {
        boolean allCorrect = false;

        WaitUtils.waitForElementToBeClickable(driver, By.cssSelector(".GFWJSJ4DCW label"), TIMEOUT_5_SECOND, false);
        for(WebElement e: listOfDeviceTypes){
            String text = e.getText();
            if(text.toLowerCase().contains("general medical device") || text.toLowerCase().contains("in vitro diagnostic device") ||
                    text.toLowerCase().contains("active implantable medical device") || text.toLowerCase().contains("system or procedure pack")){
                allCorrect = true;
            }else{
                allCorrect = false;
                break;
            }
        }

        return allCorrect;
    }
}
