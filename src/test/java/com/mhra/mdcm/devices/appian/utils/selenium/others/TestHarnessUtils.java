package com.mhra.mdcm.devices.appian.utils.selenium.others;

import com.mhra.mdcm.devices.appian.domains.AccountRequest;
import com.mhra.mdcm.devices.appian.session.ScenarioSession;
import com.mhra.mdcm.devices.appian.utils.selenium.page.WaitUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.Map;

/**
 * Created by TPD_Auto on 14/07/2016.
 */
public class TestHarnessUtils {

    /**
     * Add specific data we want to AccountRequest object
     *
     * So that we can override default data
     *
     * @param dataSets
     * @return
     */
    public static AccountRequest updateDefaultsWithData(Map<String, String> dataSets, ScenarioSession scenarioSession) {
        AccountRequest defaultAccount = new AccountRequest(scenarioSession);

        if(dataSets!=null){
            String accountType = dataSets.get("accountType");
            String countryName = dataSets.get("countryName");

            if(isNotEmptyOrNull(accountType)){
                if(accountType.contains("manufacturer")){
                    defaultAccount.isManufacturer = true;
                }else{
                    defaultAccount.isManufacturer = false;
                }
                defaultAccount.updateName(scenarioSession);
            }
            if(isNotEmptyOrNull(countryName)){
                defaultAccount.country = countryName;
            }
        }

        return defaultAccount;
    }

    private static boolean isNotEmptyOrNull(String data) {
        boolean isValid = true;

        if(data == null || data.trim().equals("")){
            isValid = false;
        }

        return isValid;
    }

    /**
     *
     * @param driver
     * @param element
     * @param value
     * @param timeOut
     */
    public static void updateElementValue(WebDriver driver, WebElement element, String value, int timeOut) {
        //WaitUtils.waitForElementToBeClickable(driver, element, timeOut, false);
        //WaitUtils.waitForElementToBeVisible(driver, element, timeOut, false);
        //element.click();
        element.clear();
        WaitUtils.nativeWaitInSeconds(1);
        element.sendKeys(RandomDataUtils.generateTestNameStartingWith(value, 0));
    }
}
