package com.mhra.mdcm.devices.appian.pageobjects.external.device;

import com.mhra.mdcm.devices.appian.domains.newaccounts.ManufacturerRequestDO;
import com.mhra.mdcm.devices.appian.domains.newaccounts.DeviceDO;
import com.mhra.mdcm.devices.appian.pageobjects._Page;
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
 *
 * When viewing devices added to a specific manufacturer
 */
@Component
public class DeviceDetails extends _Page {


    //All GMDN definition table data
    @FindBy(xpath = ".//*[contains(text(),'Risk classification')]//following::tr/td[2]")
    List<WebElement> listOfGMDNDefinitions;

    //Table headers for each device types
    @FindBy(xpath = ".//h5[contains(text(),'General Medical')]//following::thead[1]/tr/th")
    List<WebElement> listOfGeneralMedicalDeviceTableHeadings;
    @FindBy(xpath = ".//h5[contains(text(),'Vitro')]//following::thead[1]/tr/th")
    List<WebElement> listOfVitroDiagnosticsDeviceTableHeadings;
    @FindBy(xpath = ".//h5[contains(text(),'Active Implantable')]//following::thead[1]/tr/th")
    List<WebElement> listOfActiveImplantableDeviceTableHeadings;
    @FindBy(xpath = ".//h5[contains(text(),'Procedure Pack')]//following::thead[1]/tr/th")
    List<WebElement> listOfSystemProcedurePackDeviceTableHeadings;


    /**--------------------------------------------
     * CFS RELATED
     *---------------------------------------------*/

    @FindBy(css = ".GridWidget---checkbox")
    List<WebElement> listOfDeviceCheckbox;

    @FindBy(xpath = ".//*[contains(text(),'Number of')]//following::input")
    WebElement txtNumberOfCFS;
    @FindBy(xpath = ".//label")
    WebElement cbxSelectAllDevices;

    //Buttons
    @FindBy(xpath = ".//button[contains(text(), 'Order CFS')]")
    WebElement btnOrderCFS;
    @FindBy(xpath = ".//button[contains(text(), 'Continue')]")
    WebElement btnContinue;
    @FindBy(xpath = ".//button[contains(text(), './/button[text()='Continue to Payment']')]")
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
        WaitUtils.waitForElementToBeClickable(driver, By.xpath(".//a[.='Risk classification']//following::td[2]"), TIMEOUT_10_SECOND, false);

        //Displayed list of gmdns
        List<String> gmdns = new ArrayList<>();
        for(WebElement el: listOfGMDNDefinitions){
            gmdns.add(el.getText().toLowerCase());
        }

        //Verify it matches with my expected data set
        boolean allFound = true;
        for(String d: data){
            boolean foundOne = false;
            for(String gmdn: gmdns){
                if(gmdn.contains(d.toLowerCase())){
                    foundOne = true;
                    break;
                }
            }

            //All of them must exists, therefore foundOne should be true
            if(!foundOne){
                allFound = false;
                break;
            }
        }

        return allFound;
    }

    public DeviceDetails orderCFS() {
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        WaitUtils.waitForElementToBeClickable(driver, btnOrderCFS, TIMEOUT_5_SECOND, false);
        btnOrderCFS.click();
        return new DeviceDetails(driver);
    }

    public DeviceDetails selectDevices() {
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD*2);
        WaitUtils.waitForElementToBeClickable(driver, cbxSelectAllDevices, TIMEOUT_5_SECOND, false);
        WebElement cbx = PageUtils.getRandomElementFromList(listOfDeviceCheckbox);
        PageUtils.singleClick(driver, cbx);
        //Wait for continue button to be clickable
        WaitUtils.waitForElementToBeClickable(driver, btnContinue, TIMEOUT_5_SECOND, false);
        //btnContinue.click();
        PageUtils.singleClick(driver, btnContinue);
        return new DeviceDetails(driver);
    }

    public DeviceDetails enterACertificateDetails(String countryName, String noOfCFS) {
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        try {
            PageUtils.selectFromAutoSuggestedListItemsManufacturers(driver, ".PickerWidget---picker_value", countryName, true);
        }catch (Exception e){
        }
        //Enter number of certificates
        WaitUtils.waitForElementToBeClickable(driver, txtNumberOfCFS, TIMEOUT_5_SECOND, false);
        txtNumberOfCFS.sendKeys(noOfCFS);

        //Submit
        btnContinue.click();
        return new DeviceDetails(driver);

    }

    public DeviceDetails reviewCFSDetails() {
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        WaitUtils.waitForElementToBeClickable(driver, btnContinueToPayment, TIMEOUT_5_SECOND, false);
        btnContinueToPayment.click();
        return new DeviceDetails(driver);
    }

    public DeviceDetails submitPayment() {
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        WaitUtils.waitForElementToBeClickable(driver, btnSubmitPayment, TIMEOUT_5_SECOND, false);
        btnSubmitPayment.click();
        return new DeviceDetails(driver);
    }

    public DeviceDetails finishPayment() {
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        WaitUtils.waitForElementToBeClickable(driver, btnFinish, TIMEOUT_5_SECOND, false);
        btnFinish.click();
        return new DeviceDetails(driver);
    }
}
