package com.mhra.mdcm.devices.appian.domains.newaccounts;

import com.mhra.mdcm.devices.appian.session.ScenarioSession;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TPD_Auto
 *
 * OVERRIDE THE DEFAULTS
 */
public class DeviceDO {

    public static int deviceCount = 1;
    public static final String MANUFACTURER_RT_TEST = "ManufacturerRT01Test";
    public static final String AUTHORISED_REP_RT_TEST = "AuthorisedRepRT01Test";
    private final ScenarioSession scenarioSession;

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
    public boolean isBearingCEMarking;

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

    //CFS Certificate Related
    public boolean isAddingAnotherDevice;
    public boolean addCertificate;
    public boolean addProducts;
    public boolean addDevices;
    public String docType;
    public String certificateType;
    public int monthsInFutureOrPast;

    public DeviceDO(ScenarioSession scenarioSession) {
        this.scenarioSession = scenarioSession; 
        createDefaultRandom();
    }

    /**
     * Some default values assumed
     */
    private void createDefaultRandom() {

        isCustomMade = true;
        isDeviceSterile = true;
        isDeviceMeasuring = true;

        isDeviceCompatible = true;
        isBearingCEMarking = true;

        addCertificate = true;
        addProducts = true;
        addDevices = true;

        docType = "pdf";
        certificateType = "Full Quality Assurance";
        monthsInFutureOrPast = 0;
    }

    public String getGMDN() {
        String gmdn = gmdnCode;
        if(gmdn == null || gmdn.equals("")){
            gmdn = gmdnTermOrDefinition;
        }
        return gmdn;
    }

    public void setAnotherCertificate(boolean isAddingAnotherDevice) {
        if(isAddingAnotherDevice) {
            this.isAddingAnotherDevice = true;
            deviceCount++;
        }
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
                ", isBearingCEMarking=" + isBearingCEMarking +
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
