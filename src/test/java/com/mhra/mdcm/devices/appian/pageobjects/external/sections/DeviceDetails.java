package com.mhra.mdcm.devices.appian.pageobjects.external.sections;

import com.mhra.mdcm.devices.appian.domains.newaccounts.ManufacturerOrganisationRequest;
import com.mhra.mdcm.devices.appian.domains.newaccounts.DeviceData;
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
 */
@Component
public class DeviceDetails extends _Page {


    //All GMDN definition table data
    @FindBy(xpath = ".//a[.='Risk classification']//following::td[2]")
    List<WebElement> listOfGMDNDefinitions;

    //Table headers
    @FindBy(xpath = ".//h6[contains(text(),'General Medical')]//following::th/a")
    List<WebElement> listOfGeneralMedicalDeviceTableHeadings;
    @FindBy(xpath = ".//h6[contains(text(),'Vitro')]//following::th/a")
    List<WebElement> listOfVitroDiagnosticsDeviceTableHeadings;
    @FindBy(xpath = ".//h6[contains(text(),'Active Implantable')]//following::th/a")
    List<WebElement> listOfActiveImplantableDeviceTableHeadings;
    @FindBy(xpath = ".//h6[contains(text(),'Procedure Pack')]//following::th/a")
    List<WebElement> listOfSystemProcedurePackDeviceTableHeadings;

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
    public boolean isDisplayedDeviceDataCorrect(ManufacturerOrganisationRequest manufacaturerData, DeviceData deviceData) {
        //Check displayed devices are correct
        String device = deviceData.gmdnTermOrDefinition;
        boolean allHeadingValid = isDeviceTableHeadingCorrect(deviceData);
        boolean allValid = isDevicesGMDNDisplayedCorrect(device);
        return allValid && allHeadingValid;
    }

    private boolean isDeviceTableHeadingCorrect(DeviceData dd) {
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

}
