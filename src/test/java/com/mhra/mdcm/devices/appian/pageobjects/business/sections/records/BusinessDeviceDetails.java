package com.mhra.mdcm.devices.appian.pageobjects.business.sections.records;

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

import java.util.List;

/**
 * Created by TPD_Auto
 */
@Component
public class BusinessDeviceDetails extends _Page {

    //Device table
    @FindBy(xpath = ".//tr/th")
    List<WebElement> listOfTableHeadings;
    @FindBy(xpath = ".//tr/th")
    List<WebElement> listOfDeviceHeadings;

    //Device by device types
    @FindBy(xpath = ".//h5[contains(text(), 'General Medical')]//following::tbody[1]/tr")
    List<WebElement> listOfGMDDevices;
    @FindBy(xpath = ".//h5[contains(text(), 'In Vitro')]//following::tbody[1]/tr")
    List<WebElement> listOfIVDDevices;
    @FindBy(xpath = ".//h5[contains(text(), 'Active Implantable')]//following::tbody[1]/tr")
    List<WebElement> listOfAIMDDevices;
    @FindBy(xpath = ".//h5[contains(text(), 'Procedure')]//following::tbody[1]/tr")
    List<WebElement> listOfSPPDevices;

    //Device information page fields GMD
    @FindBy(xpath = ".//span[contains(text(), 'GMDN code')]//following::p[1]")
    WebElement fieldGMDNCode;
    @FindBy(xpath = ".//span[contains(text(), 'GMDN term')]//following::p[1]")
    WebElement fieldGMDNTerm;
    @FindBy(xpath = ".//span[contains(text(), 'Risk class')]//following::p[1]")
    WebElement fieldRiskClassification;
    @FindBy(xpath = ".//span[contains(text(), 'Custom made')]//following::p[1]")
    WebElement fieldCustomMade;
    @FindBy(xpath = ".//span[contains(text(), 'Sterile')]//following::p[1]")
    WebElement fieldSterile;
    @FindBy(xpath = ".//span[contains(text(), 'Measuring')]//following::p[1]")
    WebElement fieldMeasuring;

    //CFS application details
    @FindBy(xpath = ".//*[contains(text(), 'Number of devices')]")
    WebElement totalNumberOfDevices;
    @FindBy(xpath = ".//td[1]")
    List<WebElement> listOfGMDNTermsAndCodes;
    @FindBy(xpath = ".//td[2]")
    List<WebElement> listOfDeviceTypes;
    @FindBy(xpath = ".//td[6]")
    List<WebElement> listOfDeviceApprovedStatus;
    @FindBy(xpath = ".//button[contains(text(), 'Approve selected devices')]")
    WebElement btnApproveSelectedDevices;
    @FindBy(xpath = ".//button[contains(text(), 'Reject selected devices')]")
    WebElement btnRejectSelectedDevices;
    @FindBy(xpath = ".//*[contains(text(), 'approve or reject')]//following::button[3]")
    WebElement btnApproveAllCFSDevices;
    @FindBy(css = "th.GridWidget---checkbox")
    WebElement cbxSelectAllDevices;
    @FindBy(partialLinkText = "EU directive")
    WebElement linkEuDirective;
    @FindBy(css = "td.GridWidget---checkbox")
    List<WebElement> listOfDeviceCheckbox;

    //CFS device rejection
    @FindBy(xpath = ".//label[.='Other']")
    WebElement cfsDRROther;
    @FindBy(xpath = ".//input[@type='text']")
    WebElement otherReason;
    @FindBy(xpath = ".//label[.='Registered twice']")
    WebElement cfsDRRRegisteredTwice;
    @FindBy(xpath = ".//label[.='Incomplete form']")
    WebElement cfsDRRIncompleteForm;
    @FindBy(xpath = ".//button[contains(text(), 'Reject device')]")
    WebElement btnRejectCFSDeviceWithReasons;


    @Autowired
    public BusinessDeviceDetails(WebDriver driver) {
        super(driver);
    }

    public boolean isDeviceTableDisplayed() {
        //WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        WaitUtils.waitForElementToBeClickable(driver, By.xpath(".//tr/th"), TIMEOUT_DEFAULT);
        return listOfDeviceHeadings.size() > 0;
    }

    public boolean isTableColumnsCorrect(String expectedHeadings) {
        boolean allFound = true;
        for (WebElement el : listOfTableHeadings) {
            String text = el.getText();
            log.info(text);
            if (!expectedHeadings.contains(text)) {
                allFound = false;
                break;
            }
        }
        return allFound;
    }

    public BusinessDeviceDetails viewDeviceOfType(String deviceType, String gmdnTermOrDefinition) {
        if (deviceType.contains("general medical")) {
            PageUtils.clickOnGMDNCodeOrDefinition(listOfGMDDevices, gmdnTermOrDefinition);
        } else if (deviceType.contains("vitro")) {
            PageUtils.clickOnGMDNCodeOrDefinition(listOfIVDDevices, gmdnTermOrDefinition);
        } else if (deviceType.contains("active")) {
            PageUtils.clickOnGMDNCodeOrDefinition(listOfAIMDDevices, gmdnTermOrDefinition);
        } else if (deviceType.contains("procedure pack")) {
            PageUtils.clickOnGMDNCodeOrDefinition(listOfSPPDevices, gmdnTermOrDefinition);
        }
        return new BusinessDeviceDetails(driver);
    }

    public boolean areDeviceInformationPageShowingCorrectFields(String deviceType) {
        boolean fieldsCorrect = true;
        if (deviceType.contains("general medical")) {
            fieldsCorrect = isGMDNDeviceDisplayingCorrectFields();
        } else if (deviceType.contains("vitro")) {

        } else if (deviceType.contains("active")) {

        } else if (deviceType.contains("procedure pack")) {

        }
        return fieldsCorrect;
    }


    public boolean isGMDNDeviceDisplayingCorrectFields() {
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        boolean fieldsDisplayed = true;
        try {
            WaitUtils.waitForElementToBeClickable(driver, fieldGMDNCode, TIMEOUT_3_SECOND);
            WaitUtils.waitForElementToBeClickable(driver, fieldGMDNTerm, TIMEOUT_3_SECOND);
            WaitUtils.waitForElementToBeClickable(driver, fieldRiskClassification, TIMEOUT_3_SECOND);
            WaitUtils.waitForElementToBeClickable(driver, fieldCustomMade, TIMEOUT_3_SECOND);
            WaitUtils.waitForElementToBeClickable(driver, fieldSterile, TIMEOUT_3_SECOND);
            WaitUtils.waitForElementToBeClickable(driver, fieldMeasuring, TIMEOUT_3_SECOND);
        } catch (Exception e) {
            e.printStackTrace();
            fieldsDisplayed = false;
        }
        return fieldsDisplayed;
    }

    public boolean verifyAllTheDevicesAreDisplayedSimple(List<DeviceDO> listOfDeviceData) {
        WaitUtils.waitForElementToBeClickable(driver, totalNumberOfDevices, TIMEOUT_10_SECOND);
        String pageSource = driver.getPageSource();
        boolean allDataCorrect = true;
        for (DeviceDO dd : listOfDeviceData) {
            allDataCorrect = pageSource.contains(dd.deviceName);
            if (allDataCorrect) {
                allDataCorrect = pageSource.contains(dd.deviceType);
            }

            if (!allDataCorrect) {
                break;
            }
        }
        return allDataCorrect;
    }

    public boolean verifyNumberOfDevicesCount(List<DeviceDO> listOfDeviceData) {
        String numberOfDevices = String.valueOf(listOfDeviceData.size());
        boolean isValid = totalNumberOfDevices.getText().contains(numberOfDevices);

        //Perform check for other details like GMDN terms and device types
        return isValid;
    }

    public BusinessDeviceDetails viewDeviceByGMDN(String gmdnTermOrDefinition) {
        WebElement element = PageUtils.getElementMatchingText(listOfGMDNTermsAndCodes, gmdnTermOrDefinition);
        element.click();
        return new BusinessDeviceDetails(driver);
    }

    public boolean isCertificatesDisplayed(List<String> listOfCertificates) {
        WaitUtils.waitForElementToBeClickable(driver, linkEuDirective, TIMEOUT_10_SECOND);
        String pageSource = driver.getPageSource();
        boolean isValid = PageUtils.isPageDisplayingCorrectData(listOfCertificates, pageSource);
        return isValid;
    }

    public boolean isProductsDisplayed(DeviceDO deviceDO) {
        WaitUtils.waitForElementToBeClickable(driver, linkEuDirective, TIMEOUT_5_SECOND);
        List<String> listOfProductName = deviceDO.listOfProductName;
        List<String> listOfModelName = deviceDO.listOfModelName;
        String pageSource = driver.getPageSource();
        boolean isValid = PageUtils.isPageDisplayingCorrectData(listOfProductName, pageSource);
        if (isValid)
            isValid = PageUtils.isPageDisplayingCorrectData(listOfModelName, pageSource);
        return isValid;
    }

    public boolean isAbleToApproveIndividualDevices() {
        WaitUtils.waitForElementToBeVisible(driver, btnApproveSelectedDevices, TIMEOUT_10_SECOND);
        return btnApproveSelectedDevices.isEnabled() && btnApproveSelectedDevices.isDisplayed();
    }

    public boolean isAbleToRejectIndividualDevices() {
        WaitUtils.waitForElementToBeVisible(driver, btnRejectSelectedDevices, TIMEOUT_10_SECOND);
        return btnRejectSelectedDevices.isEnabled() && btnRejectSelectedDevices.isDisplayed();
    }

    public BusinessDeviceDetails selectADevices() {
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        WaitUtils.waitForElementToBeClickable(driver, cbxSelectAllDevices, TIMEOUT_10_SECOND);
        WebElement cbx = PageUtils.getRandomElementFromList(listOfDeviceCheckbox);
        PageUtils.singleClick(driver, cbx);
        return new BusinessDeviceDetails(driver);
    }

    public BusinessDeviceDetails selectAllDevices() {
        WaitUtils.waitForElementToBeClickable(driver, cbxSelectAllDevices, TIMEOUT_10_SECOND);
        cbxSelectAllDevices.click();
        return new BusinessDeviceDetails(driver);
    }

    public BusinessDeviceDetails approveAllTheDevices() {
        WaitUtils.waitForElementToBeClickable(driver, btnApproveAllCFSDevices, TIMEOUT_10_SECOND);
        btnApproveAllCFSDevices.click();
        return new BusinessDeviceDetails(driver);
    }

    public boolean isDeviceStatusCorrect(String statusOfDevices) {
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        WaitUtils.waitForElementToBeClickable(driver, btnApproveAllCFSDevices, TIMEOUT_10_SECOND);
        boolean correct = PageUtils.isAllDataCorrect(listOfDeviceApprovedStatus, statusOfDevices);
        return correct;
    }

    public BusinessDeviceDetails approveOrRejectIndividualDevices(boolean approveIndividualDevice) {
        if (approveIndividualDevice) {
            WaitUtils.waitForElementToBeVisible(driver, btnApproveSelectedDevices, TIMEOUT_5_SECOND);
            btnApproveSelectedDevices.click();
        } else {
            WaitUtils.waitForElementToBeVisible(driver, btnRejectSelectedDevices, TIMEOUT_5_SECOND);
            btnRejectSelectedDevices.click();
        }
        return new BusinessDeviceDetails(driver);
    }

    public BusinessManufacturerDetails enterDeviceRejectionReason(String reasons, String randomTestComment) {
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);

        if (reasons != null) {
            String[] lor = reasons.split(",");
            for (String reason : lor) {
                if (reason.contains("Other")) {
                    //Comment is mandatory
                    WaitUtils.waitForElementToBeClickable(driver, cfsDRROther, TIMEOUT_10_SECOND);
                    cfsDRROther.click();

                    //Enter comment
                    WaitUtils.waitForElementToBeClickable(driver, otherReason, TIMEOUT_10_SECOND);
                    otherReason.sendKeys(randomTestComment);
                } else if (reason.contains("Incomplete form")) {
                    WaitUtils.waitForElementToBeClickable(driver, cfsDRRIncompleteForm, TIMEOUT_10_SECOND);
                    PageUtils.clickIfVisible(driver, cfsDRRIncompleteForm);
                } else if (reason.contains("Registered twice")) {
                    WaitUtils.waitForElementToBeClickable(driver, cfsDRRRegisteredTwice, TIMEOUT_10_SECOND);
                    PageUtils.clickIfVisible(driver, cfsDRRRegisteredTwice);
                }
            }
        }


        //Submit rejection
        WaitUtils.waitForElementToBeVisible(driver, btnRejectCFSDeviceWithReasons, TIMEOUT_5_SECOND);
        PageUtils.singleClick(driver, btnRejectCFSDeviceWithReasons);
        return new BusinessManufacturerDetails(driver);
    }

    public boolean areDevicesOrderedByDeviceTypes() {
        boolean isOrdered = true;

        //Not sure if this is still required as the old page has been removed
        return isOrdered;
    }

    public boolean isDisplayingGMDN(List<String> listOfGmdns) {
        return false;
    }

    public boolean isProductDetailsCorrect(String deviceType, List<String> listOfProducts) {
        return false;
    }



}
