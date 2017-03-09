package com.mhra.mdcm.devices.appian.utils.selenium.others;

import com.mhra.mdcm.devices.appian.domains.newaccounts.ManufacturerOrganisationRequest;
import com.mhra.mdcm.devices.appian.domains.newaccounts.AccountRequest;
import com.mhra.mdcm.devices.appian.domains.newaccounts.DeviceData;
import com.mhra.mdcm.devices.appian.session.ScenarioSession;
import com.mhra.mdcm.devices.appian.utils.selenium.page.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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

    public static ManufacturerOrganisationRequest updateManufacturerDefaultsWithData(Map<String, String> dataSets, ScenarioSession scenarioSession) {
        ManufacturerOrganisationRequest defaultAccount = new ManufacturerOrganisationRequest(scenarioSession);

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


    public static DeviceData updateDeviceData(Map<String, String> dataSets, ScenarioSession scenarioSession) {
        DeviceData dd = new DeviceData(scenarioSession);

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
        }
        return dd;
    }

    public static void selectCountryFromAutoSuggests(WebDriver driver, String elementPath, String countryName, boolean throwException) throws Exception {
            boolean completed = true;
            int count = 0;
            do {
                try {

                    count++;    //It will go forever without this
                    WebElement country = driver.findElements(By.cssSelector(elementPath)).get(0);
                    new Actions(driver).moveToElement(country).perform();

                    //Enter the country I am interested in
                    country.sendKeys("\n");
                    country.clear();
                    country.sendKeys(countryName, Keys.ENTER);
                    new WebDriverWait(driver, 3).until(ExpectedConditions.elementToBeClickable(By.cssSelector(".item")));
                    country.sendKeys(Keys.ARROW_DOWN, Keys.ENTER);

                    completed = true;
                } catch (Exception e) {
                    completed = false;
                    WaitUtils.nativeWaitInSeconds(1);
                    //PageFactory.initElements(driver, this);
                }
            } while (!completed && count < 1);

            if(!completed && throwException){
                throw new Exception("Country name not selected");
            }
        }

    public static List<String> getListOfSearchTermsForGMDN() {
        List<String> listOfGmdnsSearchTerms = new ArrayList<>(Arrays.asList("cat", "res", "tis", "sco", "con", "pro"));
        return listOfGmdnsSearchTerms;
    }
}
