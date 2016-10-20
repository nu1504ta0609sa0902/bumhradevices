package com.mhra.mdcm.devices.appian.pageobjects.business;

import com.mhra.mdcm.devices.appian.pageobjects._Page;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by TPD_Auto
 */
@Component
public class NewsPage extends _Page {

    @Autowired
    public NewsPage(WebDriver driver) {
        super(driver);
    }
}
