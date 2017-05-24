package com.mhra.mdcm.devices.appian.pageobjects.external.device;

import com.mhra.mdcm.devices.appian.domains.newaccounts.ManufacturerRequestDO;
import com.mhra.mdcm.devices.appian.domains.newaccounts.DeviceDO;
import com.mhra.mdcm.devices.appian.pageobjects._Page;
import com.mhra.mdcm.devices.appian.utils.selenium.page.AssertUtils;
import com.mhra.mdcm.devices.appian.utils.selenium.page.CommonUtils;
import com.mhra.mdcm.devices.appian.utils.selenium.page.PageUtils;
import com.mhra.mdcm.devices.appian.utils.selenium.page.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TPD_Auto
 * <p>
 * When viewing devices added to a specific manufacturer
 */
@Component
public class DeviceDetails extends _Page {


    //All GMDN definition table data
    @FindBy(xpath = ".//*[contains(text(),'Risk classification')]//following::tr/td[2]")
    List<WebElement> listOfGMDNDefinitions;
    @FindBy(xpath = ".//*[text()='Risk classification']//following::tr/td[2]")
    WebElement firstGMDNDefinition;

    //Table headers for each device types
    @FindBy(xpath = ".//h5[contains(text(),'General Medical')]//following::thead[1]/tr/th")
    List<WebElement> listOfGeneralMedicalDeviceTableHeadings;
    @FindBy(xpath = ".//h5[contains(text(),'Vitro')]//following::thead[1]/tr/th")
    List<WebElement> listOfVitroDiagnosticsDeviceTableHeadings;
    @FindBy(xpath = ".//h5[contains(text(),'Active Implantable')]//following::thead[1]/tr/th")
    List<WebElement> listOfActiveImplantableDeviceTableHeadings;
    @FindBy(xpath = ".//h5[contains(text(),'Procedure Pack')]//following::thead[1]/tr/th")
    List<WebElement> listOfSystemProcedurePackDeviceTableHeadings;


    /**
     * --------------------------------------------
     * CFS RELATED
     * ---------------------------------------------
     */

    @FindBy(css = ".GridWidget---checkbox")
    List<WebElement> listOfAllCheckbox;
    @FindBy(css = "td.GridWidget---checkbox")
    List<WebElement> listOfDeviceCheckbox;
    @FindBy(css = ".PickerWidget---picker_value")
    List<WebElement> listOfCountryPickers;
    @FindBy(xpath = ".//*[contains(text(),'Number of')]//following::input")
    List<WebElement> listOfTbxNumberOfCFS;
    @FindBy(xpath = ".//div[contains(text(),'Number of certificates')]/following::tr/td[1]")
    List<WebElement> listOfCountryNames;
    @FindBy(css = "div.PickerTokenWidget---chip > a.PickerTokenWidget---remove > i")
    List<WebElement> listOfCountryNamesRemoveX;
    @FindBy(xpath = ".//div[contains(text(),'Number of certificates')]/following::tr/td[2]")
    List<WebElement> listOfNumberOfCertificates;

    @FindBy(xpath = ".//h4")
    WebElement txtManufacturerName;
    @FindBy(xpath = ".//*[contains(text(),'Total number of certificates')]")
    WebElement txtTotalNumberOfCertificates;
    @FindBy(xpath = ".//*[contains(text(),'Price:')]")
    WebElement txtTotalPriceOfCertificates;
    @FindBy(xpath = ".//*[contains(text(),'Number of')]//following::input")
    WebElement tbxNumberOfCFS;
    @FindBy(xpath = ".//label")
    WebElement cbxSelectAllDevices;
    @FindBy(xpath = ".//div[contains(text(),'Number of certificates')]//following::p[2]")
    WebElement txtNumberOfCertificates;
    @FindBy(partialLinkText = "Add country")
    WebElement linkAddCountry;
    @FindBy(partialLinkText = "Edit devices")
    WebElement linkEditDevices;
    @FindBy(partialLinkText = "Edit country and")
    WebElement linkEditCountryAndCertificates;

    //Buttons
    @FindBy(xpath = ".//button[contains(text(), 'Order CFS')]")
    WebElement btnOrderCFS;
    @FindBy(xpath = ".//button[contains(text(), 'Continue')]")
    WebElement btnContinue;
    @FindBy(xpath = ".//button[contains(text(), 'Continue to Payment')]")
    WebElement btnContinueToPayment;
    @FindBy(xpath = ".//button[contains(text(), 'Submit')]")
    WebElement btnSubmitPayment;
    @FindBy(xpath = ".//button[contains(text(), 'Finish')]")
    WebElement btnFinish;


    @Autowired
    public DeviceDetails(WebDriver driver) {
        super(driver);
    }


    /**
     * Manufacturer details are correct and valid
     *
     * @param manufacaturerData
     * @param deviceData
     * @return
     */
    public boolean isDisplayedDeviceDataCorrect(ManufacturerRequestDO manufacaturerData, DeviceDO deviceData) {
        //Check displayed devices are correct
        String device = deviceData.gmdnTermOrDefinition;
        boolean allHeadingValid = isDeviceTableHeadingCorrect(deviceData);
        boolean allValid = isDevicesGMDNDisplayedCorrect(device);
        return allValid && allHeadingValid;
    }

    private boolean isDeviceTableHeadingCorrect(DeviceDO dd) {
        boolean isCorrect = false;
        String headings = "";
        if (dd.deviceType.toLowerCase().contains("general medical device")) {
            headings = "GMDN code,GMDN definition,Risk classification";
            isCorrect = PageUtils.isTableHeadingCorrect(headings, listOfGeneralMedicalDeviceTableHeadings);
        } else if (dd.deviceType.toLowerCase().contains("vitro diagnostic")) {
            isCorrect = PageUtils.isTableHeadingCorrect(headings, listOfVitroDiagnosticsDeviceTableHeadings);
        } else if (dd.deviceType.toLowerCase().contains("active implantable")) {
            isCorrect = PageUtils.isTableHeadingCorrect(headings, listOfActiveImplantableDeviceTableHeadings);
        } else if (dd.deviceType.toLowerCase().contains("procedure pack")) {
            isCorrect = PageUtils.isTableHeadingCorrect(headings, listOfSystemProcedurePackDeviceTableHeadings);
        }
        return isCorrect;
    }

    public boolean isDevicesGMDNDisplayedCorrect(String deviceList) {
        String[] data = deviceList.split(",");
        WaitUtils.waitForElementToBeClickable(driver, firstGMDNDefinition, TIMEOUT_10_SECOND);

        //Displayed list of gmdns
        List<String> gmdns = new ArrayList<>();
        for (WebElement el : listOfGMDNDefinitions) {
            gmdns.add(el.getText().toLowerCase());
        }

        //Verify it matches with my expected data set
        boolean allFound = true;
        for (String d : data) {
            boolean foundOne = false;
            for (String gmdn : gmdns) {
                if (gmdn.contains(d.toLowerCase())) {
                    foundOne = true;
                    break;
                }
            }

            //All of them must exists, therefore foundOne should be true
            if (!foundOne) {
                allFound = false;
                break;
            }
        }

        return allFound;
    }

    public DeviceDetails orderCFS() {
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        WaitUtils.waitForElementToBeClickable(driver, btnOrderCFS, TIMEOUT_5_SECOND);
        btnOrderCFS.click();
        return new DeviceDetails(driver);
    }

    public DeviceDetails selectDevices() {
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD * 2);
        WaitUtils.waitForElementToBeClickable(driver, cbxSelectAllDevices, TIMEOUT_5_SECOND);
        WebElement cbx = PageUtils.getRandomElementFromList(listOfDeviceCheckbox);
        PageUtils.singleClick(driver, cbx);
        //Wait for continue button to be clickable
        WaitUtils.waitForElementToBeClickable(driver, btnContinue, TIMEOUT_5_SECOND);
        PageUtils.singleClick(driver, btnContinue);
        return new DeviceDetails(driver);
    }

    public DeviceDetails enterACertificateDetails(String countryName, String noOfCFS) {
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        try {
            PageUtils.selectFromAutoSuggestedListItemsManufacturers(driver, ".PickerWidget---picker_value", countryName, true);
        } catch (Exception e) {
        }
        //Enter number of certificates
        WaitUtils.waitForElementToBeClickable(driver, tbxNumberOfCFS, TIMEOUT_5_SECOND);
        tbxNumberOfCFS.sendKeys(noOfCFS);

        //Submit
        btnContinue.click();
        return new DeviceDetails(driver);

    }

    public DeviceDetails enterMultipleCertificateDetails(String data, boolean clickAddCountryLink, int whichPicker) {
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        WaitUtils.waitForElementToBeClickable(driver, tbxNumberOfCFS, TIMEOUT_5_SECOND);
        WaitUtils.waitForElementToBeClickable(driver, linkAddCountry, TIMEOUT_5_SECOND);

        String[] values = data.split("=");
        if (values.length == 2) {
            String countryName = values[0];
            String noOfCFS = values[1];
            try {
                PageUtils.selectFromAutoSuggestedListItemsManufacturers(driver, listOfCountryPickers.get(whichPicker-1), countryName);
            } catch (Exception e) {
            }
            //Enter number of certificates
            listOfTbxNumberOfCFS.get(whichPicker-1).sendKeys(noOfCFS);

            if (clickAddCountryLink)
                linkAddCountry.click();
        } else {
            log.info("Invalid CFS Country pair data : " + data);
        }

        return new DeviceDetails(driver);
    }

    public DeviceDetails clickContinueButton() {
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        WaitUtils.waitForElementToBeClickable(driver, btnContinue, TIMEOUT_10_SECOND);
        btnContinue.click();
        return new DeviceDetails(driver);
    }

    public DeviceDetails continueToPaymentAfterReviewFinished() {
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        WaitUtils.waitForElementToBeClickable(driver, btnContinueToPayment, TIMEOUT_10_SECOND);
        btnContinueToPayment.click();
        return new DeviceDetails(driver);
    }

    public DeviceDetails submitPayment() {
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        WaitUtils.waitForElementToBeClickable(driver, btnSubmitPayment, TIMEOUT_10_SECOND);
        btnSubmitPayment.click();
        return new DeviceDetails(driver);
    }

    public DeviceDetails finishPayment() {
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        WaitUtils.waitForElementToBeClickable(driver, btnFinish, TIMEOUT_10_SECOND);
        btnFinish.click();
        return new DeviceDetails(driver);
    }

    public boolean isNumberOfCertificatesCorrect(String number) {
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        WaitUtils.waitForElementToBeClickable(driver, btnContinueToPayment, TIMEOUT_10_SECOND);
        String txt = txtNumberOfCertificates.getText().trim();
        return number.equals(txt);
    }

    public boolean isManufacturerNameCorrect(String name) {
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        WaitUtils.waitForElementToBeClickable(driver, btnContinueToPayment, TIMEOUT_10_SECOND);
        String txt = txtManufacturerName.getText().trim();
        return txt.contains(name);
    }

    public boolean areTheCountriesDisplayedCorrect(String[] data) {
        List<String> countries = CommonUtils.getListOfCountries(data);
        List<String> listOfTexts = CommonUtils.getListOfText(listOfCountryNames);
        System.out.println("List of countries expected : " + countries);
        System.out.println("List of countries displayed : " + listOfTexts);

        boolean isCorrect = true;
        for(String country: countries){
            if(!listOfTexts.contains(country.trim())){
                log.error("Country name not found : " + country);
                isCorrect = false;
                break;
            }
        }

        return isCorrect;
    }

    public boolean areTheCertificateCountCorrect(String[] data) {
        List<String> listOfData = CommonUtils.getListOfData(data);

        int count = 0;
        boolean isDataCorrect = true;

        for(WebElement el: listOfCountryNames){
            String countryAtPositionX = listOfCountryNames.get(count).getText();
            String numberOfCertAtPositionX = listOfNumberOfCertificates.get(count).getText();
            String toCheck = countryAtPositionX + "=" + numberOfCertAtPositionX;

            if(!listOfData.contains(toCheck)){
                isDataCorrect = false;
                break;
            }
            count++;
        }

        return isDataCorrect;
    }

    public DeviceDetails clickEditDevicesLink() {
        linkEditDevices.click();
        return new DeviceDetails(driver);
    }

    public DeviceDetails clickEditCountryAndCertificateLink() {
        linkEditCountryAndCertificates.click();
        return new DeviceDetails(driver);
    }

    public boolean isTotalNumberOfCertificatesCorrect(String numberOfCFS) {
        boolean valid = txtTotalNumberOfCertificates.getText().contains(numberOfCFS);
        return valid;
    }


    public boolean isTotalNumberOfCertificatesCorrect(String[] data) {
        List<String> counts = CommonUtils.getListOfCertificateCounts(data);
        int total = 0;
        for(String c: counts){
            total = total + Integer.parseInt(c);
        }
        boolean valid = txtTotalNumberOfCertificates.getText().contains(Integer.toString(total));
        return valid;
    }

    public DeviceDetails updateCountryNumber(int pos, String countryName) {
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        WebElement element = listOfCountryNamesRemoveX.get(pos - 1);
        element.click();

        //Reenter value
        try {
            WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
            PageUtils.selectFromAutoSuggestedListItemsManufacturers(driver, ".PickerWidget---picker_value", countryName, true);
        } catch (Exception e) {
        }
        return new DeviceDetails(driver);
    }

    public DeviceDetails updateNumberOfCFS(int pos, String numberOfCFS) {
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        WebElement element = listOfTbxNumberOfCFS.get(pos - 1);
        element.clear();
        element.sendKeys(numberOfCFS);
        return new DeviceDetails(driver);
    }

    public boolean isTotalCostOfCertificatesCorrect(int totalCost) {
        //Verify total cost
        boolean isCorrect = txtTotalPriceOfCertificates.getText().contains(String.valueOf(totalCost));
        return isCorrect;
    }
}
