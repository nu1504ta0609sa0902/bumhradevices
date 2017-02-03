package com.mhra.mdcm.devices.appian.utils.selenium.page;

import org.openqa.selenium.WebElement;

import java.util.List;

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

    public static boolean areChangesDisplayed(WebElement element, String value) {
        String txt = element.getText();
        boolean found = txt.contains(value);
        return found;
    }

    public static boolean isNumeric(String gmdnCode) {
        boolean isNumeric = true;
        try{
            Integer.parseInt(gmdnCode);
        }catch (Exception e){
            isNumeric = false;
        }
        return isNumeric;
    }

    public static boolean areAllDataInAutosuggestCorrect(List<String> listOfMatches, String commaDelimitedExpectedMatches) {
        String[] data = commaDelimitedExpectedMatches.split(",");
        boolean allFound = true;
        for(String d: data){
            boolean contains = listOfMatches.contains(d);
            if(!contains){
                allFound = false;
                break;
            }
        }
        return allFound;
    }
}

