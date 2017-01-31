package com.mhra.mdcm.devices.appian.utils.selenium.others;

import com.mhra.mdcm.devices.appian.domains.newaccounts.DeviceData;
import com.mhra.mdcm.devices.appian.session.ScenarioSession;
import com.mhra.mdcm.devices.appian.session.SessionKey;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TPD_Auto on 31/01/2017.
 */
public class StepsUtils {

    public static void addToDeviceDataList(ScenarioSession scenarioSession, DeviceData dd) {
        List<DeviceData> listOfDeviceData = (List<DeviceData>) scenarioSession.getData(SessionKey.deviceDataList);
        if(listOfDeviceData == null){
            listOfDeviceData = new ArrayList<>();
        }

        listOfDeviceData.add(dd);
        scenarioSession.putData(SessionKey.deviceDataList, listOfDeviceData);
    }
}
