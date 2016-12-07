package com.mhra.mdcm.devices.appian.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

/**
 * @author TPD_Auto
 */
@ContextConfiguration(locations = {"/cucumber.mhra.devices.xml"})
public class _Page {

    public static final boolean USE_DEBUG_TIME = true;
    public static final int TIMEOUT_1_SECOND = 1;
    public static final int TIMEOUT_3_SECOND = 3;
    public static final int TIMEOUT_5_SECOND = 5;
    public static final int TIMEOUT_10_SECOND = 10;
    public static final int TIMEOUT_15_SECOND = 15;
    public static final int TIMEOUT_20_SECOND = 20;
    public static final int TIMEOUT_30_SECOND = 30;
    public static final int TIMEOUT_60_SECOND = 60;
    public static final int TIMEOUT_DEFAULT = TIMEOUT_15_SECOND;

    public WebDriver driver;
    public final Logger log = LoggerFactory.getLogger(_Page.class);

    @Autowired
    public _Page(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public WebDriver getDriver() {
        return driver;
    }

    public String getTitle(){
        return driver.getTitle();
    }
}
