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


    public static void removeFromDeviceDataList(ScenarioSession scenarioSession, String deviceName) {
        List<DeviceDO> listOfDeviceData = (List<DeviceDO>) scenarioSession.getData(SessionKey.listOfDevicesAdded);

        int position = 0;
        for(DeviceDO dd: listOfDeviceData){
            if(deviceName.equals(dd.deviceName)){
                break;
            }
            position++;
        }

        //Remove and update
        listOfDeviceData.remove(position);
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

    public static void addCertificatesToAllCertificateList(ScenarioSession scenarioSession, DeviceDO dd) {
        List<String> listOfAllCertificates = (List<String>) scenarioSession.getData(SessionKey.listOfAllCertificatesAddedToApplication);
        if(listOfAllCertificates == null){
            listOfAllCertificates = new ArrayList<>();
        }

        for(String c: dd.listOfCertificates){
            String x = c.split("\\.")[0];
            listOfAllCertificates.add(x);
        }
        scenarioSession.putData(SessionKey.listOfAllCertificatesAddedToApplication, listOfAllCertificates);
    }
}
