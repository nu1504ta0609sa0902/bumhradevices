package com.mhra.mdcm.devices.appian.pageobjects;

import org.codehaus.groovy.runtime.powerassert.SourceText;
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

    /**
     * Different timeouts for different actions: Use appropriately
     * Any pages which takes more than 10 seconds should be looked at
     **/
    public static final boolean USE_DEFAULT_TIME_FOR_ALL = true;
    public static int TIMEOUT_DEFAULT = 30;

    //This will wait for 1*60 seconds "Waiting..." message to dissappear, unless you set to 0
    public static final int TIMEOUT_PAGE_LOAD = 0;

    public static int TIMEOUT_1_SECOND = 1;
    public static int TIMEOUT_2_SECOND = 2;
    public static int TIMEOUT_3_SECOND = 3;
    public static int TIMEOUT_5_SECOND = 5;
    public static int TIMEOUT_10_SECOND = 10;
    public static int TIMEOUT_15_SECOND = 15;
    public static int TIMEOUT_20_SECOND = 20;
    public static int TIMEOUT_30_SECOND = 30;
    public static int TIMEOUT_40_SECOND = 40;
    public static int TIMEOUT_50_SECOND = 50;
    public static int TIMEOUT_60_SECOND = 60;
    public static int TIMEOUT_90_SECOND = 90;
    public static int TIMEOUT_120_SECOND = 120;

    //Driver shared across the pages
    public WebDriver driver;
    public final Logger log = LoggerFactory.getLogger(_Page.class);

    @Autowired
    public _Page(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);

        if(USE_DEFAULT_TIME_FOR_ALL){
            setDefaultTimeForAll();
        }
    }

    /**
     * RESET USE_DEFAULT_TIME_FOR_ALL=false
     *
     * USING DEFAULT TIMEOUT FOR ALL WAITS : Only use if system is very slow
     * SET USE_DEFAULT_TIME_FOR_ALL=false TO STOP USING SUCH LARGE TIMEOUTS
     */
    private static void setDefaultTimeForAll() {

        TIMEOUT_1_SECOND = TIMEOUT_DEFAULT;
        TIMEOUT_2_SECOND = TIMEOUT_DEFAULT;
        TIMEOUT_3_SECOND = TIMEOUT_DEFAULT;
        TIMEOUT_5_SECOND = TIMEOUT_DEFAULT;
        TIMEOUT_10_SECOND = TIMEOUT_DEFAULT;
        TIMEOUT_15_SECOND = TIMEOUT_DEFAULT;
        TIMEOUT_20_SECOND = TIMEOUT_DEFAULT;
        TIMEOUT_30_SECOND = TIMEOUT_DEFAULT;
        TIMEOUT_40_SECOND = TIMEOUT_DEFAULT;
        TIMEOUT_50_SECOND = TIMEOUT_DEFAULT;
        TIMEOUT_60_SECOND = TIMEOUT_DEFAULT;
        TIMEOUT_90_SECOND = TIMEOUT_DEFAULT;
    }

    public WebDriver getDriver() {
        return driver;
    }

    public String getTitle(){
        return driver.getTitle();
    }
}
