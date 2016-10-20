package com.mhra.mdcm.devices.appian.pageobjects.business;

import com.mhra.mdcm.devices.appian.pageobjects._Page;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by TPD_Auto
 */
@Component
public class ReportsPage extends _Page {

    @Autowired
    public ReportsPage(WebDriver driver) {
        super(driver);
    }

}
