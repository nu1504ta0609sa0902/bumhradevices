package com.mhra.mdcm.devices.appian.utils.selenium.page;

import com.mhra.mdcm.devices.appian.utils.jenkins.ProxyAuthentication;
import com.mhra.mdcm.devices.appian.utils.jenkins.ProxyAuthenticationSikuli;
import org.openqa.selenium.WebDriver;

/**
 * Created by a-Uddinn on 4/11/2017.
 */
public class AuthenticationUtils {

    public static void performBasicAuthentication(WebDriver driver, String baseUrl) {
        String browser = System.getProperty("current.browser");
        if(browser!=null && browser.toLowerCase().equals("gc")) {
            //Only required if behind a proxy : works for Chrome
            //driver.manage().window().maximize();
            driver.get(baseUrl);
            //(new Thread(new ProxyAuthentication(driver, baseUrl))).start();
            try {
                new ProxyAuthenticationSikuli(driver, baseUrl).login(false);
            } catch (Exception e) {
                try {
                    //Try again
                    new ProxyAuthenticationSikuli(driver, baseUrl).login(false);
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        }
    }
}
