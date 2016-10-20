package com.mhra.mdcm.devices.appian.utils.selenium.page;

/**
 * Created by TPD_Auto
 */
public class AssertUtils {

    public static String getExpectedName(String name){
        String val = "";
        switch (name){
            case "Auto.Business": val = "Auto Business"; break;
            case "Auto.Manufacturer": val = "Auto Manufacturer"; break;
            case "Auto.AuthorisedRep": val = "Auto AuthorisedRep"; break;
            case "Noor.Uddin.Business": val = "Noor Uddin Business"; break;
            case "Noor.Uddin.Manufacturer": val = "Noor Uddin Manufacturer"; break;
            case "Noor.Uddin.AuthorisedRep": val = "Noor Uddin AuthorisedRep"; break;
        }
        return val;
    }

}

