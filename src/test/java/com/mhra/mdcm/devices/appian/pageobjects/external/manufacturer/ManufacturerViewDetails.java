package com.mhra.mdcm.devices.appian.pageobjects.external.manufacturer;

import com.mhra.mdcm.devices.appian.domains.newaccounts.AccountRequestDO;
import com.mhra.mdcm.devices.appian.domains.newaccounts.DeviceDO;
import com.mhra.mdcm.devices.appian.pageobjects._Page;
import com.mhra.mdcm.devices.appian.pageobjects.external.device.AddDevices;
import com.mhra.mdcm.devices.appian.pageobjects.external.device.DeviceDetails;
import com.mhra.mdcm.devices.appian.pageobjects.external.device.ProductDetails;
import com.mhra.mdcm.devices.appian.utils.selenium.page.AssertUtils;
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
public class ManufacturerViewDetails extends _Page {

    @FindBy(css = ".component_error")
    List <WebElement> errorMessages;

    //Buttons for adding, editing devices
    @FindBy(xpath = ".//button[contains(text(),'Add a device')]")
    WebElement addADevice;
    @FindBy(xpath = ".//button[contains(text(),'Declare devices')]")
    WebElement declareADevice;
    @FindBy(xpath = ".//button[contains(text(),'Continue')]")
    WebElement btnContinue;
    @FindBy(xpath = ".//button[contains(text(),'Edit Account Information')]")
    WebElement amendRepresentativeParty;
    @FindBy(xpath = ".//button[contains(text(),'Edit Account Information')]")
    WebElement editAccountInformation;

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
    @FindBy(xpath = ".//h3[contains(text(),'Person Details')]//following::span[.='Telephone']/following::p[1]")
    WebElement telephone;
    @FindBy(xpath = ".//a[contains(text(),'Person Details')]//following::span[.='Telephone']/following::p[1]")
    WebElement telephone2;

    //Organisation details
    @FindBy(xpath = ".//span[contains(text(),'Role')]//following::p[1]")
    WebElement role;

    //ORGANISATION DETAILS
    @FindBy(css = "div>h1")
    WebElement orgName;
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
    @FindBy(partialLinkText = "product details")
    WebElement devicesAndProductDetails;

    @Autowired
    public ManufacturerViewDetails(WebDriver driver) {
        super(driver);
    }

    public boolean isOrganisationNameCorrect(String name, boolean isManufacturer) {
        WaitUtils.isPageLoadingComplete(driver, 1);
        boolean contains = false;
        if(isManufacturer){
            WaitUtils.waitForElementToBeClickable(driver, orgName, TIMEOUT_10_SECOND, false);
            contains = orgName.getText().contains(name);
        }else{
            WaitUtils.waitForElementToBeClickable(driver, orgName, TIMEOUT_10_SECOND, false);
            contains = orgName.getText().contains(name);
        }
        return contains;
    }


    public boolean isErrorMessageDisplayed() {
        try {
            WaitUtils.waitForElementToBeVisible(driver, By.cssSelector(".component_error"), 3, false);
            boolean isDisplayed = errorMessages.size() > 0;
            return isDisplayed;
        }catch (Exception e){
            return false;
        }
    }

    public AddDevices clickAddDeviceBtn() {
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        WaitUtils.waitForElementToBeVisible(driver, addADevice, TIMEOUT_15_SECOND, false);
        WaitUtils.waitForElementToBeClickable(driver, addADevice, TIMEOUT_15_SECOND, false);
        addADevice.click();
        return new AddDevices(driver);
    }

    public AddDevices clickContinueToAddDevices(String registeredStatus) {

        try {
            if (registeredStatus != null && registeredStatus.toLowerCase().equals("registered"))
                return clickAddDeviceBtn();
            else
                return clickContinueBtn();
        }catch (Exception e){
            btnContinue.click();
            return new AddDevices(driver);
        }
    }

    public AddDevices clickContinueBtn(){
        try {
            WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
            WaitUtils.waitForElementToBeVisible(driver, btnContinue, TIMEOUT_5_SECOND, false);
            WaitUtils.waitForElementToBeClickable(driver, btnContinue, TIMEOUT_5_SECOND, false);
            btnContinue.click();
        }catch (Exception e){}
        return new AddDevices(driver);
    }


    public ManufacturerEditDetails amendRepresentedParty() {
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        WaitUtils.waitForElementToBeClickable(driver, amendRepresentativeParty, TIMEOUT_10_SECOND, false);
        amendRepresentativeParty.click();
        return new ManufacturerEditDetails(driver);
    }

    public boolean isCorrectPage() {
        try {
            WaitUtils.waitForElementToBeClickable(driver, addADevice, TIMEOUT_5_SECOND, false);
            WaitUtils.waitForElementToBeClickable(driver, amendRepresentativeParty, TIMEOUT_5_SECOND, false);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean verifyManufacturerUpdatesDisplayedOnPage(String keyValuePairToUpdate, AccountRequestDO updatedData) {

        WaitUtils.waitForElementToBeVisible(driver, email, TIMEOUT_DEFAULT, false);
        WaitUtils.waitForElementToBeVisible(driver, fullName, TIMEOUT_5_SECOND, false);

        boolean allChangesDisplayed = true;

        //Check for the following
        String[] dataPairs = keyValuePairToUpdate.split(",");

        for(String pairs: dataPairs){
            //String[] split = pairs.split("=");
            String key = pairs;

            //Contact details
            if (key.equals("contact.title")) {
                allChangesDisplayed = AssertUtils.areChangesDisplayed(fullName,  updatedData.title);
            } else if (key.equals("contact.firstname")) {
                allChangesDisplayed = AssertUtils.areChangesDisplayed(fullName,  updatedData.firstName);
            } else if (key.equals("contact.lastname")) {
                allChangesDisplayed = AssertUtils.areChangesDisplayed(fullName,  updatedData.lastName);
            } else if (key.equals("contact.job.title")) {
                allChangesDisplayed = AssertUtils.areChangesDisplayed(jobTitle,  updatedData.jobTitle);
            } else if (key.equals("contact.email")) {
                allChangesDisplayed = AssertUtils.areChangesDisplayed(email,  updatedData.email);
            } else if (key.equals("contact.telephone")) {
                allChangesDisplayed = AssertUtils.areChangesDisplayed(telephone,  updatedData.telephone);
            }

            boolean orgNameUpdated = false;
            //Organisation details
            if (key.equals("org.name")) {
                allChangesDisplayed = AssertUtils.areChangesDisplayed(orgName, updatedData.organisationName);
                orgNameUpdated = true;
            }else if (key.equals("org.address1")) {
                allChangesDisplayed = AssertUtils.areChangesDisplayed(orgAddressLine1, updatedData.address1);
            }else if (key.equals("org.address2")) {
                allChangesDisplayed = AssertUtils.areChangesDisplayed(orgAddressLine2, updatedData.address2);
            }else if (key.equals("org.city")) {
                allChangesDisplayed = AssertUtils.areChangesDisplayed(orgCityTown, updatedData.townCity);
            }else if (key.equals("org.postcode")) {
                allChangesDisplayed = AssertUtils.areChangesDisplayed(orgPostCode, updatedData.postCode);
            }else if (key.equals("org.country")) {
                allChangesDisplayed = AssertUtils.areChangesDisplayed(orgCountry, updatedData.country);
            }else if (key.equals("org.telephone")) {
                allChangesDisplayed = AssertUtils.areChangesDisplayed(orgTelephone, updatedData.telephone);
            }else if (key.equals("org.website")) {
                if(orgNameUpdated)
                allChangesDisplayed = AssertUtils.areChangesDisplayed(webSite, updatedData.website);
            }


            if(!allChangesDisplayed){
                break;
            }
        }

        return allChangesDisplayed;
    }

    public ManufacturerEditDetails editAccountInformation() {
        WaitUtils.waitForElementToBeClickable(driver, editAccountInformation, TIMEOUT_10_SECOND, false);
        editAccountInformation.click();
        return new ManufacturerEditDetails(driver);
    }

    public ProductDetails viewProduct(DeviceDO deviceData) {
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        if(deviceData.gmdnTermOrDefinition!=null) {
            WaitUtils.waitForElementToBeClickable(driver, By.partialLinkText(deviceData.gmdnTermOrDefinition), TIMEOUT_10_SECOND, false);
            driver.findElement(By.partialLinkText(deviceData.gmdnTermOrDefinition)).click();
        }
        return new ProductDetails(driver);
    }


    public boolean isDisplayedOrgFieldsCorrect(String org) {
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        boolean fieldsDisplayed = true;
        try {
            WaitUtils.waitForElementToBeClickable(driver, orgName, TIMEOUT_3_SECOND, false);
            WaitUtils.waitForElementToBeClickable(driver, orgAddressLine1, TIMEOUT_1_SECOND, false);
            WaitUtils.waitForElementToBeClickable(driver, orgAddressLine2, TIMEOUT_1_SECOND, false);
            WaitUtils.waitForElementToBeClickable(driver, orgCityTown, TIMEOUT_1_SECOND, false);
            WaitUtils.waitForElementToBeClickable(driver, orgPostCode, TIMEOUT_1_SECOND, false);
            WaitUtils.waitForElementToBeClickable(driver, orgCountry, TIMEOUT_1_SECOND, false);
            WaitUtils.waitForElementToBeClickable(driver, orgTelephone, TIMEOUT_1_SECOND, false);
            WaitUtils.waitForElementToBeClickable(driver, orgFax, TIMEOUT_1_SECOND, false);
            WaitUtils.waitForElementToBeClickable(driver, webSite, TIMEOUT_1_SECOND, false);
        }catch (Exception e){
            e.printStackTrace();
            fieldsDisplayed = false;
        }
        return fieldsDisplayed;
    }

    public boolean isDisplayedContactPersonFieldsCorrect(String org) {
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        boolean fieldsDisplayed = true;
        try {
            WaitUtils.waitForElementToBeClickable(driver, orgName, TIMEOUT_3_SECOND, false);
            WaitUtils.waitForElementToBeClickable(driver, fullName, TIMEOUT_1_SECOND, false);
            WaitUtils.waitForElementToBeClickable(driver, jobTitle, TIMEOUT_1_SECOND, false);
            WaitUtils.waitForElementToBeClickable(driver, email, TIMEOUT_1_SECOND, false);
            WaitUtils.waitForElementToBeClickable(driver, telephone, TIMEOUT_1_SECOND, false);
        }catch (Exception e){
            e.printStackTrace();
            fieldsDisplayed = false;
        }
        return fieldsDisplayed;
    }

    public DeviceDetails clickOnDevicesAndProductDetailsLink() {
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        WaitUtils.waitForElementToBeClickable(driver, devicesAndProductDetails, TIMEOUT_10_SECOND, false);
        devicesAndProductDetails.click();
        return new DeviceDetails(driver);
    }

    public AddDevices clickDeclareDeviceBtn() {
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        WaitUtils.waitForElementToBeVisible(driver, declareADevice, TIMEOUT_5_SECOND, false);
        WaitUtils.waitForElementToBeClickable(driver, declareADevice, TIMEOUT_5_SECOND, false);
        declareADevice.click();
        return new AddDevices(driver);
    }

    public ManufacturerViewDetails refreshThePage() {
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        WaitUtils.waitForElementToBeClickable(driver, fullName, TIMEOUT_10_SECOND, false);
        driver.navigate().refresh();
        return new ManufacturerViewDetails(driver);
    }

    public AddDevices gotoAddDevicesPage(String registeredStatus) {
        try {
            if (registeredStatus != null && registeredStatus.toLowerCase().equals("registered"))
                return clickAddDeviceBtn();
            else
                return clickDeclareDeviceBtn();
        }catch (Exception e){
            btnContinue.click();
            return new AddDevices(driver);
        }
    }

    public boolean isDisplayedContactPersonFieldsCorrectForNonRegisteredManufacturer(String org) {
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        boolean fieldsDisplayed = true;
        try {
            //WaitUtils.waitForElementToBeClickable(driver, orgName, TIMEOUT_3_SECOND, false);
            WaitUtils.waitForElementToBeClickable(driver, firstName, TIMEOUT_1_SECOND, false);
            WaitUtils.waitForElementToBeClickable(driver, lastName, TIMEOUT_1_SECOND, false);
            WaitUtils.waitForElementToBeClickable(driver, jobTitle, TIMEOUT_1_SECOND, false);
            WaitUtils.waitForElementToBeClickable(driver, email, TIMEOUT_1_SECOND, false);
            WaitUtils.waitForElementToBeClickable(driver, telephone2, TIMEOUT_1_SECOND, false);
        }catch (Exception e){
            e.printStackTrace();
            fieldsDisplayed = false;
        }
        return fieldsDisplayed;
    }
}
