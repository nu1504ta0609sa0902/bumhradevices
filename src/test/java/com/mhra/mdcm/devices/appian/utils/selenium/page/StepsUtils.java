package com.mhra.mdcm.devices.appian.utils.selenium.page;

import com.mhra.mdcm.devices.appian.domains.newaccounts.DeviceDO;
import com.mhra.mdcm.devices.appian.session.ScenarioSession;
import com.mhra.mdcm.devices.appian.session.SessionKey;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TPD_Auto on 31/01/2017.
 */
public class StepsUtils {

    public static void addToDeviceDataList(ScenarioSession scenarioSession, DeviceDO dd) {
        List<DeviceDO> listOfDeviceData = (List<DeviceDO>) scenarioSession.getData(SessionKey.listOfDevicesAdded);
        if(listOfDeviceData == null){
            listOfDeviceData = new ArrayList<>();
        }

        listOfDeviceData.add(dd);
        scenarioSession.putData(SessionKey.listOfDevicesAdded, listOfDeviceData);
    }


    public static void addToListOfStrings(ScenarioSession scenarioSession, String sessionKey, String value) {
        List<String> listOfStrings = (List<String>) scenarioSession.getData(sessionKey);
        if(listOfStrings == null){
            listOfStrings = new ArrayList<>();
        }

        listOfStrings.add(value);
        scenarioSession.putData(sessionKey, listOfStrings);
    }


    public static void addToListOfStrings(ScenarioSession scenarioSession, String sessionKey, List<String> values) {
        List<String> listOfStrings = (List<String>) scenarioSession.getData(sessionKey);
        if(listOfStrings == null){
            listOfStrings = new ArrayList<>();
        }

        for(String item: values) {
            listOfStrings.add(item);
        }

        scenarioSession.putData(sessionKey, listOfStrings);
    }

    public static String getCommaDelimitedData(List<String> listOfGmdns) {
        String commaDelimited = "";
        for(String x: listOfGmdns){
            if(x.contains(",")){
                commaDelimited = commaDelimited + "," + x.split(",")[0];
            }else{
                commaDelimited = commaDelimited + "," + x ;
            }
        }
        return commaDelimited;
    }
}
