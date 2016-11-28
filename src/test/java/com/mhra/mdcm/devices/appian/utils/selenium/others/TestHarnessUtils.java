package com.mhra.mdcm.devices.appian.utils.selenium.others;

import com.mhra.mdcm.devices.appian.domains.newaccounts.AccountManufacturerRequest;
import com.mhra.mdcm.devices.appian.domains.newaccounts.AccountRequest;
import com.mhra.mdcm.devices.appian.session.ScenarioSession;

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
    public static AccountRequest updateBusinessDefaultsWithData(Map<String, String> dataSets, ScenarioSession scenarioSession) {
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

    public static AccountManufacturerRequest updateManufacturerDefaultsWithData(Map<String, String> dataSets, ScenarioSession scenarioSession) {
        AccountManufacturerRequest defaultAccount = new AccountManufacturerRequest(scenarioSession);

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
}
