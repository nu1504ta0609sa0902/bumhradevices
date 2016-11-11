package com.mhra.mdcm.devices.appian.utils.selenium.others;

import com.mhra.mdcm.devices.appian.domains.AccountRequest;

import java.util.Map;

/**
 * Created by TPD_Auto on 14/07/2016.
 */
public class TestHarnessUtils {

    public static AccountRequest updateDefaultsWithData(Map<String, String> dataSets) {
        AccountRequest defaultAccount = new AccountRequest();

        if(dataSets!=null){
            String accountType = dataSets.get("accountType");
            String countryName = dataSets.get("countryName");

            if(isNotEmptyOrNull(accountType)){
                if(accountType.contains("authorisedRep")){
                    defaultAccount.isManufacturer = false;
                }else{
                    defaultAccount.isManufacturer = true;
                }
                defaultAccount.updateName();
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
}
