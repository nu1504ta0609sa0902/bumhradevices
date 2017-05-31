package com.mhra.mdcm.devices.appian.utils.selenium.others;

import com.mhra.mdcm.devices.appian.domains.newaccounts.ManufacturerRequestDO;
import com.mhra.mdcm.devices.appian.domains.newaccounts.AccountRequestDO;
import com.mhra.mdcm.devices.appian.domains.newaccounts.DeviceDO;
import com.mhra.mdcm.devices.appian.session.ScenarioSession;
import org.apache.xpath.operations.Bool;
import org.openqa.selenium.*;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

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
    public static AccountRequestDO updateBusinessDefaultsWithData(Map<String, String> dataSets, ScenarioSession scenarioSession) {
        AccountRequestDO defaultAccount = new AccountRequestDO(scenarioSession);

        if(dataSets!=null){
            String accountType = dataSets.get("accountType");
            String accountNameBeginsWith = dataSets.get("accountNameBeginsWith");
            String countryName = dataSets.get("countryName");

            if(isNotEmptyOrNull(accountType)){

                //If others like distributor or notifiedBody
                if(accountType.equals("distributor")){
                    defaultAccount.organisationRole = "distributor";
                }else if(accountType.equals("notifiedbody")){
                    defaultAccount.organisationRole = "notifiedbody";
                }else{
                    //Than set manufacturer
                    if(accountType.contains("manufacturer")){
                        defaultAccount.isManufacturer = true;
                    }else{
                        defaultAccount.isManufacturer = false;
                    }
                }
                defaultAccount.updateName(scenarioSession);
            }
            if(isNotEmptyOrNull(countryName)){
                defaultAccount.country = countryName;
            }
            if(isNotEmptyOrNull(accountNameBeginsWith)){
                defaultAccount.organisationName = RandomDataUtils.getRandomTestName(accountNameBeginsWith);
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

    public static ManufacturerRequestDO updateManufacturerDefaultsWithData(Map<String, String> dataSets, ScenarioSession scenarioSession) {
        ManufacturerRequestDO defaultAccount = new ManufacturerRequestDO(scenarioSession);

        if(dataSets!=null){
            String accountType = dataSets.get("accountType");
            String accountNameBeginsWith = dataSets.get("accountNameBeginsWith");
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
            if(isNotEmptyOrNull(accountNameBeginsWith)){
                defaultAccount.organisationName = RandomDataUtils.getRandomTestName(accountNameBeginsWith);
            }
        }

        return defaultAccount;
    }


    public static DeviceDO updateDeviceData(Map<String, String> dataSets, ScenarioSession scenarioSession) {
        DeviceDO dd = new DeviceDO(scenarioSession);

        if(dataSets!=null){

            String deviceType = dataSets.get("deviceType");
            String gmdnDefinition = dataSets.get("gmdnDefinition");
            String gmdnCode = dataSets.get("gmdnCode");
            String customMade = dataSets.get("customMade");
            String relatedDeviceSterile = dataSets.get("relatedDeviceSterile");
            String relatedDeviceMeasuring = dataSets.get("relatedDeviceMeasuring");
            String riskClassification = dataSets.get("riskClassification");
            String notifiedBody = dataSets.get("notifiedBody");
            String isBearingCEMarking = dataSets.get("isBearingCEMarking");
            String devicesCompatible = dataSets.get("devicesCompatible");

            String productName = dataSets.get("productName");
            String productMake = dataSets.get("productMake");
            String productModel = dataSets.get("productModel");
            String subjectToPerfEval = dataSets.get("subjectToPerfEval");
            String newProduct = dataSets.get("newProduct");
            String conformsToCTS = dataSets.get("conformsToCTS");

            String listOfProductNames = dataSets.get("listOfProductNames");

            String addCertificate = dataSets.get("addCertificate");
            String addProducts = dataSets.get("addProducts");
            String addDevices = dataSets.get("addDevices");
            String docType = dataSets.get("docType");

            if(isNotEmptyOrNull(deviceType)){
                dd.deviceType = deviceType;
            }
            if(isNotEmptyOrNull(gmdnDefinition)){
                dd.gmdnTermOrDefinition = gmdnDefinition;
            }
            if(isNotEmptyOrNull(gmdnCode)){
                dd.gmdnCode = gmdnCode;
            }
            if(isNotEmptyOrNull(customMade)){
                dd.isCustomMade = Boolean.parseBoolean(customMade);
            }
            if(isNotEmptyOrNull(relatedDeviceSterile)){
                dd.isDeviceSterile = Boolean.parseBoolean(relatedDeviceSterile);
            }
            if(isNotEmptyOrNull(relatedDeviceMeasuring)){
                dd.isDeviceMeasuring = Boolean.parseBoolean(relatedDeviceMeasuring);
            }
            if(isNotEmptyOrNull(riskClassification)){
                dd.riskClassification = riskClassification;
            }
            if(isNotEmptyOrNull(notifiedBody)){
                dd.notifiedBody = notifiedBody;
            }

            if(isNotEmptyOrNull(isBearingCEMarking)){
                dd.isBearingCEMarking = Boolean.parseBoolean(isBearingCEMarking);
            }
            if(isNotEmptyOrNull(devicesCompatible)){
                dd.isDeviceCompatible = Boolean.parseBoolean(devicesCompatible);
            }

            //IVD risk classification
            if(isNotEmptyOrNull(productName)){
                dd.productName = productName;
            }
            if(isNotEmptyOrNull(productMake)){
                dd.productMake = productMake;
            }
            if(isNotEmptyOrNull(productModel)){
                dd.productModel = productModel;
            }
            if(isNotEmptyOrNull(subjectToPerfEval)){
                dd.isSubjectToPerfEval = Boolean.parseBoolean(subjectToPerfEval);
            }
            if(isNotEmptyOrNull(newProduct)){
                dd.isNewProduct = Boolean.parseBoolean(newProduct);
            }
            if(isNotEmptyOrNull(conformsToCTS)){
                dd.isConformsToCTS = Boolean.parseBoolean(conformsToCTS);
            }

            if(isNotEmptyOrNull(listOfProductNames)){
                if(listOfProductNames.contains(",")){
                    String[] data = listOfProductNames.split(",");
                    for(String d: data){
                        dd.listOfProductName.add(RandomDataUtils.getRandomTestName(d));
                    }
                }else{
                    dd.listOfProductName.add(listOfProductNames);
                }
            }

            //Add certificate and product : set to false to test negative scenario
            if(isNotEmptyOrNull(addCertificate)){
                dd.addCertificate = Boolean.valueOf(addCertificate);
            }
            if(isNotEmptyOrNull(addProducts)){
                dd.addProducts = Boolean.valueOf(addProducts);
            }
            if(isNotEmptyOrNull(addDevices)){
                dd.addDevices = Boolean.valueOf(addDevices);
            }
            if(isNotEmptyOrNull(docType)){
                dd.docType = docType.replace(" ","");
            }
        }
        return dd;
    }

    public static List<String> getListOfSearchTermsForGMDN() {
        List<String> listOfGmdnsSearchTerms = new ArrayList<>(Arrays.asList("cat", "res", "tis", "sco", "con", "pro"));
        return listOfGmdnsSearchTerms;
    }

    public static void takeScreenShot(WebDriver driver, String name) {
        File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        String currentDir = FileUtils.getFileFullPath("tmp", "screenshots");

        String timeStamp = new SimpleDateFormat("HHmm").format(Calendar.getInstance().getTime());
        String subDir = "SS_" + new SimpleDateFormat("yyyyMMdd").format(Calendar.getInstance().getTime());
        try {
            org.apache.commons.io.FileUtils.copyFile(scrFile, new File(currentDir + File.separator + subDir + File.separator + timeStamp + "_" + name + ".png"));
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }
}
