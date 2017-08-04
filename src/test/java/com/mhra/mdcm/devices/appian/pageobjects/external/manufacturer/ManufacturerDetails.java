package com.mhra.mdcm.devices.appian.pageobjects.external.manufacturer;

import com.mhra.mdcm.devices.appian.domains.newaccounts.AccountRequestDO;
import com.mhra.mdcm.devices.appian.domains.newaccounts.DeviceDO;
import com.mhra.mdcm.devices.appian.domains.newaccounts.ManufacturerRequestDO;
import com.mhra.mdcm.devices.appian.pageobjects._Page;
import com.mhra.mdcm.devices.appian.pageobjects.business.sections.records.BusinessManufacturerDetails;
import com.mhra.mdcm.devices.appian.pageobjects.external.cfs.CFSAddDevices;
import com.mhra.mdcm.devices.appian.pageobjects.external.device.AddDevices;
import com.mhra.mdcm.devices.appian.pageobjects.external.device.DeviceDetails;
import com.mhra.mdcm.devices.appian.pageobjects.external.device.ProductDetails;
import com.mhra.mdcm.devices.appian.utils.selenium.others.RandomDataUtils;
import com.mhra.mdcm.devices.appian.utils.selenium.page.AssertUtils;
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
public class ManufacturerDetails extends _Page {

    @FindBy(css = ".component_error")
    List<WebElement> errorMessages;

    //Buttons for adding, editing devices
    @FindBy(xpath = ".//button[contains(text(),'Add a device')]")
    WebElement addADevice;
    @FindBy(xpath = ".//button[contains(text(),'Add device')]")
    WebElement addADeviceCFS;
    @FindBy(xpath = ".//button[contains(text(),'Declare devices')]")
    WebElement declareADevice;
    @FindBy(xpath = ".//button[contains(text(),'Continue')]")
    WebElement btnContinue;
    @FindBy(xpath = ".//button[contains(text(),'Register Manufacturer')]")
    WebElement btnRegisterManufactuerer;
    @FindBy(xpath = ".//button[contains(text(),'Unregister Manufacturer')]")
    WebElement btnUnRegisterManufactuerer;
    @FindBy(xpath = ".//button[contains(text(),'Edit Account Information')]")
    WebElement amendRepresentativeParty;
    @FindBy(xpath = ".//button[contains(text(),'Edit Account Information')]")
    WebElement editAccountInformation;
    @FindBy(xpath = ".//*[contains(text(),'Ceased Trading')]//following::button[contains(text(),'Unregister')]")
    WebElement btnUnregister;

    //Contact details
    @FindBy(xpath = ".//span[contains(text(),'Full')]//following::p[1]")
    WebElement fullName;
    @FindBy(xpath = ".//span[contains(text(),'First')]//following::p[1]")
    WebElement firstName;
    @FindBy(xpath = ".//span[contains(text(),'Last')]//following::p[1]")
    WebElement lastName;
    @FindBy(xpath = ".//span[contains(text(),'Job')]//following::p[1]")
    WebElement jobTitle;
    @FindBy(xpath = ".//span[contains(text(),'Email')]//following::p[1]")
    WebElement email;
    @FindBy(xpath = ".//*[contains(text(),'Person Details')]//following::span[.='Telephone']/following::p[1]")
    WebElement telephone;
    @FindBy(xpath = ".//a[contains(text(),'Person Details')]//following::span[.='Telephone']/following::p[1]")
    WebElement telephone2;

    //Organisation details
    @FindBy(xpath = ".//span[contains(text(),'Role')]//following::p[1]")
    WebElement role;

    //ORGANISATION DETAILS
    @FindBy(css = "div>h1")
    WebElement orgName;
    @FindBy(xpath = ".//span[.='Registered Address']//following::p[1]")
    WebElement orgAddressFull;
    @FindBy(xpath = ".//span[.='Address line 1']//following::p[1]")
    WebElement orgAddressLine1;
    @FindBy(xpath = ".//span[contains(text(),'Address line 2')]//following::p[1]")
    WebElement orgAddressLine2;
    @FindBy(xpath = ".//span[contains(text(),'City')]//following::p[1]")
    WebElement orgCityTown;
    @FindBy(xpath = ".//span[contains(text(),'Post')]//following::p[1]")
    WebElement orgPostCode;
    @FindBy(xpath = ".//span[contains(text(),'Country')]//following::p[1]")
    WebElement orgCountry;
    @FindBy(xpath = ".//span[contains(text(),'Telephone')]//following::p[1]")
    WebElement orgTelephone;
    @FindBy(xpath = ".//span[contains(text(),'Fax')]//following::p[1]")
    WebElement orgFax;
    @FindBy(xpath = ".//span[contains(text(),'Website')]//following::p[1]")
    WebElement webSite;

    //Links to other sections like devices, documents
    @FindBy(xpath = ".//*[contains(text(),'Devices &')]")
    WebElement devicesAndProductDetailsTab;
    @FindBy(partialLinkText = "Summary")
    WebElement summaryTab;

    //Tabs: Summary, Applications etc
    @FindBy(partialLinkText = "Applications")
    WebElement tabApplicationDetails;
    @FindBy(xpath = ".//td[2]")
    WebElement tdApplicationType;
    @FindBy(xpath = ".//td[3]")
    WebElement tdApplicationDate;
    @FindBy(xpath = ".//td[4]")
    WebElement tdApplicationStatus;

    //CFS related buttons
    @FindBy(xpath = ".//button[contains(text(), 'Order CFS')]")
    WebElement btnOrderCFS;

    //Unregister reason
    @FindBy(xpath = ".//*[contains(text(),'Merged OR Acquired')]")
    WebElement rbMergedOrAcquired;
    @FindBy(xpath = ".//*[contains(text(),'Ceased Trading')]")
    WebElement rbCeasedTrading;
    @FindBy(xpath = ".//*[contains(text(),'No Longer Makes Registerable Devices')]")
    WebElement rbNoLongerMakesRegisterableDevices;
    @FindBy(xpath = ".//*[contains(text(),'No Longer Makes Any Medical Device')]")
    WebElement rbNoLongerMakesAnyMedicalDevice;
    @FindBy(xpath = ".//*[contains(text(),'No Longer Represented')]")
    WebElement rbNoLongerRpresented;
    @FindBy(css = ".FileUploadWidget---ui-inaccessible")
    WebElement fileUpload;

    @FindBy(xpath = ".//button[.='Save and Exit']")
    WebElement btnSaveAndExit;

    @Autowired
    public ManufacturerDetails(WebDriver driver) {
        super(driver);
    }

    public boolean isOrganisationNameCorrect(String name, boolean isManufacturer) {
        WaitUtils.isPageLoadingComplete(driver, 1);
        boolean contains = false;
        if (isManufacturer) {
            WaitUtils.waitForElementToBeClickable(driver, orgName, TIMEOUT_10_SECOND);
            contains = orgName.getText().contains(name);
        } else {
            WaitUtils.waitForElementToBeClickable(driver, orgName, TIMEOUT_10_SECOND);
            contains = orgName.getText().contains(name);
        }
        return contains;
    }


    public boolean isErrorMessageDisplayed() {
        try {
            WaitUtils.waitForElementToBeVisible(driver, By.cssSelector(".component_error"), 3);
            boolean isDisplayed = errorMessages.size() > 0;
            return isDisplayed;
        } catch (Exception e) {
            return false;
        }
    }

    public AddDevices clickAddDeviceBtn() {
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        WaitUtils.waitForElementToBeVisible(driver, addADevice, TIMEOUT_15_SECOND);
        WaitUtils.waitForElementToBeClickable(driver, addADevice, TIMEOUT_15_SECOND);
        addADevice.click();
        return new AddDevices(driver);
    }

    public AddDevices clickContinueToAddDevices(String registeredStatus) {

        try {
            if (registeredStatus != null && registeredStatus.toLowerCase().equals("registered"))
                return clickAddDeviceBtn();
            else
                //return clickRegisterManufacturerBtn();
                return clickContinueBtn();
        } catch (Exception e) {
            btnContinue.click();
            return new AddDevices(driver);
        }
    }

    public AddDevices clickContinueBtn() {
        try {
            WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
            WaitUtils.waitForElementToBeVisible(driver, btnContinue, TIMEOUT_5_SECOND);
            WaitUtils.waitForElementToBeClickable(driver, btnContinue, TIMEOUT_5_SECOND);
            btnContinue.click();
        } catch (Exception e) {
        }
        return new AddDevices(driver);
    }

    public AddDevices clickRegisterManufacturerBtn() {
        try {
            WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
            WaitUtils.waitForElementToBeVisible(driver, btnRegisterManufactuerer, TIMEOUT_15_SECOND);
            WaitUtils.waitForElementToBeClickable(driver, btnRegisterManufactuerer, TIMEOUT_5_SECOND);
            btnRegisterManufactuerer.click();
        } catch (Exception e) {
        }
        return new AddDevices(driver);
    }


    public ManufacturerEditDetails amendRepresentedParty() {
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        WaitUtils.waitForElementToBeClickable(driver, amendRepresentativeParty, TIMEOUT_10_SECOND);
        amendRepresentativeParty.click();
        return new ManufacturerEditDetails(driver);
    }

    public boolean isCorrectPage() {
        try {
            WaitUtils.waitForElementToBeClickable(driver, addADevice, TIMEOUT_5_SECOND);
            WaitUtils.waitForElementToBeClickable(driver, amendRepresentativeParty, TIMEOUT_5_SECOND);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean verifyManufacturerUpdatesDisplayedOnPage(String keyValuePairToUpdate, AccountRequestDO updatedData) {

        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        WaitUtils.waitForElementToBeVisible(driver, email, TIMEOUT_10_SECOND);
        WaitUtils.waitForElementToBeVisible(driver, fullName, TIMEOUT_5_SECOND);

        boolean allChangesDisplayed = true;

        //Check for the following
        String[] dataPairs = keyValuePairToUpdate.split(",");

        for (String pairs : dataPairs) {
            //String[] split = pairs.split("=");
            String key = pairs;

            //Contact details
            if (key.equals("contact.title")) {
                allChangesDisplayed = AssertUtils.areChangesDisplayed(fullName, updatedData.title);
            } else if (key.equals("contact.firstname")) {
                allChangesDisplayed = AssertUtils.areChangesDisplayed(fullName, updatedData.firstName);
            } else if (key.equals("contact.lastname")) {
                allChangesDisplayed = AssertUtils.areChangesDisplayed(fullName, updatedData.lastName);
            } else if (key.equals("contact.job.title")) {
                allChangesDisplayed = AssertUtils.areChangesDisplayed(jobTitle, updatedData.jobTitle);
            } else if (key.equals("contact.email")) {
                allChangesDisplayed = AssertUtils.areChangesDisplayed(email, updatedData.email);
            } else if (key.equals("contact.telephone")) {
                allChangesDisplayed = AssertUtils.areChangesDisplayed(telephone, updatedData.telephone);
            }

            boolean orgNameUpdated = false;
            //Organisation details
            if (key.equals("org.name")) {
                allChangesDisplayed = AssertUtils.areChangesDisplayed(orgName, updatedData.organisationName);
                orgNameUpdated = true;
            } else if (key.equals("org.address1")) {
                allChangesDisplayed = AssertUtils.areChangesDisplayed(orgAddressLine1, updatedData.address1);
            } else if (key.equals("org.address2")) {
                allChangesDisplayed = AssertUtils.areChangesDisplayed(orgAddressLine2, updatedData.address2);
            } else if (key.equals("org.city")) {
                allChangesDisplayed = AssertUtils.areChangesDisplayed(orgCityTown, updatedData.townCity);
            } else if (key.equals("org.postcode")) {
                allChangesDisplayed = AssertUtils.areChangesDisplayed(orgPostCode, updatedData.postCode);
            } else if (key.equals("org.country")) {
                allChangesDisplayed = AssertUtils.areChangesDisplayed(orgCountry, updatedData.country);
            } else if (key.equals("org.telephone")) {
                allChangesDisplayed = AssertUtils.areChangesDisplayed(orgTelephone, updatedData.telephone);
            } else if (key.equals("org.website")) {
                if (orgNameUpdated)
                    allChangesDisplayed = AssertUtils.areChangesDisplayed(webSite, updatedData.website);
            }


            if (!allChangesDisplayed) {
                break;
            }
        }

        return allChangesDisplayed;
    }

    public ManufacturerEditDetails editAccountInformation() {
        WaitUtils.waitForElementToBeClickable(driver, editAccountInformation, TIMEOUT_10_SECOND);
        editAccountInformation.click();
        return new ManufacturerEditDetails(driver);
    }

    public ProductDetails viewProduct(DeviceDO deviceData) {
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        if (deviceData.gmdnTermOrDefinition != null) {
            WaitUtils.waitForElementToBeClickable(driver, By.partialLinkText(deviceData.gmdnTermOrDefinition), TIMEOUT_10_SECOND);
            driver.findElement(By.partialLinkText(deviceData.gmdnTermOrDefinition)).click();
        }
        return new ProductDetails(driver);
    }


    public boolean isDisplayedOrgFieldsCorrect(String org, String status) {
        //WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        boolean fieldsDisplayed = true;
        try {
            WaitUtils.waitForElementToBeClickable(driver, orgName, TIMEOUT_10_SECOND);
//            if (status.toLowerCase().contains("not")) {
//                WaitUtils.waitForElementToBeClickable(driver, orgAddressFull, TIMEOUT_3_SECOND);
//            } else {
//                WaitUtils.waitForElementToBeClickable(driver, orgAddressLine1, TIMEOUT_3_SECOND);
//                WaitUtils.waitForElementToBeClickable(driver, orgAddressLine2, TIMEOUT_1_SECOND);
//                WaitUtils.waitForElementToBeClickable(driver, orgCityTown, TIMEOUT_1_SECOND);
//                WaitUtils.waitForElementToBeClickable(driver, orgPostCode, TIMEOUT_1_SECOND);
//                WaitUtils.waitForElementToBeClickable(driver, orgCountry, TIMEOUT_1_SECOND);
//            }
            try {
                WaitUtils.waitForElementToBeClickable(driver, orgAddressFull, TIMEOUT_1_SECOND);
            }catch (Exception e){
                WaitUtils.waitForElementToBeClickable(driver, orgAddressLine1, TIMEOUT_3_SECOND);
                WaitUtils.waitForElementToBeClickable(driver, orgAddressLine2, TIMEOUT_1_SECOND);
                WaitUtils.waitForElementToBeClickable(driver, orgCityTown, TIMEOUT_1_SECOND);
                WaitUtils.waitForElementToBeClickable(driver, orgPostCode, TIMEOUT_1_SECOND);
                WaitUtils.waitForElementToBeClickable(driver, orgCountry, TIMEOUT_1_SECOND);
            }
            WaitUtils.waitForElementToBeClickable(driver, orgTelephone, TIMEOUT_1_SECOND);
            WaitUtils.waitForElementToBeClickable(driver, orgFax, TIMEOUT_1_SECOND);
            WaitUtils.waitForElementToBeClickable(driver, webSite, TIMEOUT_1_SECOND);
        } catch (Exception e) {
            e.printStackTrace();
            fieldsDisplayed = false;
        }
        return fieldsDisplayed;
    }

    public boolean isDisplayedContactPersonFieldsCorrect(String org) {
        //WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        boolean fieldsDisplayed = true;
        try {
            WaitUtils.waitForElementToBeClickable(driver, orgName, TIMEOUT_10_SECOND);
            try {
                WaitUtils.waitForElementToBeClickable(driver, fullName, TIMEOUT_1_SECOND);
            }catch (Exception e){
                WaitUtils.waitForElementToBeClickable(driver, firstName, TIMEOUT_1_SECOND);
                WaitUtils.waitForElementToBeClickable(driver, lastName, TIMEOUT_1_SECOND);
            }
            WaitUtils.waitForElementToBeClickable(driver, jobTitle, TIMEOUT_1_SECOND);
            WaitUtils.waitForElementToBeClickable(driver, email, TIMEOUT_1_SECOND);
            WaitUtils.waitForElementToBeClickable(driver, telephone, TIMEOUT_1_SECOND);
        } catch (Exception e) {
            e.printStackTrace();
            fieldsDisplayed = false;
        }
        return fieldsDisplayed;
    }

    public DeviceDetails clickOnDevicesAndProductDetailsLink() {
        WaitUtils.waitForElementToBeClickable(driver, devicesAndProductDetailsTab, TIMEOUT_15_SECOND);
        //PageUtils.singleClick(driver,devicesAndProductDetailsTab);
        devicesAndProductDetailsTab.click();
        return new DeviceDetails(driver);
    }

    public DeviceDetails clickOnSummaryLink() {
        WaitUtils.waitForElementToBeClickable(driver, summaryTab, TIMEOUT_15_SECOND);
        PageUtils.singleClick(driver,summaryTab);
        return new DeviceDetails(driver);
    }

    public boolean isDevicesAndProductTabVisible(){
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        return PageUtils.isVisible(driver, devicesAndProductDetailsTab, TIMEOUT_3_SECOND);
    }

    public AddDevices clickDeclareDeviceBtn() {
        //WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        WaitUtils.waitForElementToBeVisible(driver, declareADevice, TIMEOUT_5_SECOND);
        WaitUtils.waitForElementToBeClickable(driver, declareADevice, TIMEOUT_5_SECOND);
        declareADevice.click();
        return new AddDevices(driver);
    }

    public ManufacturerDetails refreshThePage() {
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        WaitUtils.waitForElementToBeClickable(driver, fullName, TIMEOUT_10_SECOND);
        driver.navigate().refresh();
        return new ManufacturerDetails(driver);
    }

    public AddDevices gotoAddDevicesPage(String registeredStatus) {
        try {
            if (registeredStatus != null && registeredStatus.toLowerCase().equals("registered"))
                return clickAddDeviceBtn();
            else
                return clickDeclareDeviceBtn();
        } catch (Exception e) {
            btnContinue.click();
            return new AddDevices(driver);
        }
    }

    public boolean isDisplayedContactPersonFieldsCorrectForNonRegisteredManufacturer(String org) {
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        boolean fieldsDisplayed = true;
        try {
            //WaitUtils.waitForElementToBeClickable(driver, orgName, TIMEOUT_3_SECOND);
            WaitUtils.waitForElementToBeClickable(driver, firstName, TIMEOUT_1_SECOND);
            WaitUtils.waitForElementToBeClickable(driver, lastName, TIMEOUT_1_SECOND);
            WaitUtils.waitForElementToBeClickable(driver, jobTitle, TIMEOUT_1_SECOND);
            WaitUtils.waitForElementToBeClickable(driver, email, TIMEOUT_1_SECOND);
            WaitUtils.waitForElementToBeClickable(driver, telephone2, TIMEOUT_1_SECOND);
        } catch (Exception e) {
            e.printStackTrace();
            fieldsDisplayed = false;
        }
        return fieldsDisplayed;
    }

    public CFSAddDevices clickContinue() {
        try {
            WaitUtils.waitForElementToBeClickable(driver, btnContinue, TIMEOUT_3_SECOND);
            btnContinue.click();
        } catch (Exception e) {
        }
        return new CFSAddDevices(driver);
    }

    public CFSAddDevices clickAddDeviceCFS() {
        //WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        WaitUtils.waitForElementToBeClickable(driver, addADeviceCFS, TIMEOUT_15_SECOND);
        addADeviceCFS.click();
        return new CFSAddDevices(driver);
    }


    public DeviceDetails clickOrderCFSButton() {
        //WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        WaitUtils.waitForElementToBeClickable(driver, btnOrderCFS, TIMEOUT_15_SECOND);
        btnOrderCFS.click();
        return new DeviceDetails(driver);
    }

    public ManufacturerDetails clickApplicationTab() {
        //WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        WaitUtils.waitForElementToBeClickable(driver, tabApplicationDetails, TIMEOUT_15_SECOND);
        tabApplicationDetails.click();
        return new ManufacturerDetails(driver);
    }

    public boolean isApplicationReferenceVisible(String reference) {
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        return PageUtils.isLinkVisible(driver, reference);
    }

    public boolean isApplicaitonStatusCorrect(String draft) {
        WaitUtils.waitForElementToBeClickable(driver, tdApplicationStatus, TIMEOUT_10_SECOND);
        return tdApplicationStatus.getText().contains(draft);
    }

    public boolean isApplicationDateCorrect() {
        String date = RandomDataUtils.getTodaysDate(true, "/");
        WaitUtils.waitForElementToBeClickable(driver, tdApplicationDate, TIMEOUT_10_SECOND);
        return tdApplicationDate.getText().contains(date);
    }

    public boolean isApplicationTypeCorrect(String type) {
        WaitUtils.waitForElementToBeClickable(driver, tdApplicationType, TIMEOUT_10_SECOND);
        return tdApplicationType.getText().contains(type);
    }

    public boolean isUnregisterBtnDisplayed() {
        return PageUtils.isVisible(driver, btnUnRegisterManufactuerer, TIMEOUT_10_SECOND);
    }

    public ManufacturerDetails clickUnregisterManufacturerBtn() {
        WaitUtils.waitForElementToBeClickable(driver, btnUnRegisterManufactuerer, TIMEOUT_10_SECOND);
        btnUnRegisterManufactuerer.click();
        return new ManufacturerDetails(driver);
    }

    public ManufacturerDetails submitUnregistrationWithReasons(String reason, boolean confirmUnregisttration) {
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        WaitUtils.waitForElementToBeVisible(driver, btnUnregister, TIMEOUT_10_SECOND);

        //Select a reason
        if(reason.contains("Ceased Trading")){
            rbCeasedTrading.click();
        }else if(reason.contains("No Longer Represented")){
            rbNoLongerRpresented.click();
            PageUtils.uploadDocument(fileUpload, "LetterOfCancellation1.pdf", 1, 2);
        }

        //Click unregister button and confirm
        WaitUtils.waitForElementToBeClickable(driver, btnUnregister, TIMEOUT_10_SECOND);
        btnUnregister.click();
        return new ManufacturerDetails(driver);
    }

    public AddDevices clickContinueButton() {
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        WaitUtils.waitForElementToBeClickable(driver, btnContinue, TIMEOUT_10_SECOND);
        btnContinue.click();
        return new AddDevices(driver);
    }

    public boolean isDisplayingCorrectData(ManufacturerRequestDO manufacaturerData) {
        WaitUtils.waitForElementToBeClickable(driver, fullName, TIMEOUT_10_SECOND);
        String fullNameText = fullName.getText();
        boolean isCorrect = fullNameText.contains(manufacaturerData.firstName);
        if(isCorrect){
            isCorrect = fullNameText.contains(manufacaturerData.lastName);
        }

        if(isCorrect){
            isCorrect = email.getText().contains(manufacaturerData.email);
        }

        if(isCorrect){
            isCorrect = orgAddressFull.getText().contains(manufacaturerData.address1);
        }
        return isCorrect;
    }

    public boolean isSaveAndExitButtonVisible() {
        return PageUtils.isElementClickable(driver, btnSaveAndExit, TIMEOUT_2_SECOND);
    }

}
