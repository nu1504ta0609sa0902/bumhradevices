package com.mhra.mdcm.devices.appian.domains.newaccounts;

import com.mhra.mdcm.devices.appian.session.ScenarioSession;
import com.mhra.mdcm.devices.appian.session.SessionKey;
import com.mhra.mdcm.devices.appian.utils.selenium.others.FileUtils;
import com.mhra.mdcm.devices.appian.utils.selenium.others.RandomDataUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by TPD_Auto
 *
 * OVERRIDE THE DEFAULTS
 */
public class DeviceData {

    public static final String MANUFACTURER_RT_TEST = "ManufacturerRT01Test";
    public static final String AUTHORISED_REP_RT_TEST = "AuthorisedRepRT01Test";

    //Device type
    public String deviceType;

    //GMDN search type
    public String gmdnCode;
    public String gmdnTermOrDefinition;

    //Other properties
    public boolean isCustomMade;
    public boolean isDeviceSterile;
    public boolean isDeviceMeasuring;
    public boolean isDeviceCompatible;
    public boolean isPackIncorporated;

    //If Custom made = No
    public String riskClassification;
    public String notifiedBody;

    //IVD risk classification
    public List<String> listOfProductName = new ArrayList<>();
    public String productName;
    public String productMake;
    public String productModel;
    public boolean isSubjectToPerfEval;
    public boolean isNewProduct;
    public boolean isConformsToCTS;

    public DeviceData(ScenarioSession scenarioSession) {
        createDefaultRandom();
    }

    private void createDefaultRandom() {

        deviceType = "General Medical Device";

        gmdnTermOrDefinition = "Adhesive";
        gmdnCode = "10001";

        isCustomMade = true;
        isDeviceSterile = true;
        isDeviceMeasuring = true;

        riskClassification = "Class1";  //Class2a, Class2b, Class3
        notifiedBody = "NB 0086 BSI";

        isDeviceCompatible = true;
        isPackIncorporated = true;

    }

    @Override
    public String toString() {
        return "DeviceData{" +
                "deviceType='" + deviceType + '\'' +
                ", gmdnCode='" + gmdnCode + '\'' +
                ", gmdnTermOrDefinition='" + gmdnTermOrDefinition + '\'' +
                ", isCustomMade=" + isCustomMade +
                ", isDeviceSterile=" + isDeviceSterile +
                ", isDeviceMeasuring=" + isDeviceMeasuring +
                ", isDeviceCompatible=" + isDeviceCompatible +
                ", isPackIncorporated=" + isPackIncorporated +
                ", riskClassification='" + riskClassification + '\'' +
                ", notifiedBody='" + notifiedBody + '\'' +
                ", productName='" + productName + '\'' +
                ", productMake='" + productMake + '\'' +
                ", productModel='" + productModel + '\'' +
                ", isSubjectToPerfEval=" + isSubjectToPerfEval +
                ", isNewProduct=" + isNewProduct +
                ", isConformsToCTS=" + isConformsToCTS +
                '}';
    }
}
