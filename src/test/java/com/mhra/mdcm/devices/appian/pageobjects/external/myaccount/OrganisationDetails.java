package com.mhra.mdcm.devices.appian.pageobjects.external.myaccount;

import com.mhra.mdcm.devices.appian.domains.newaccounts.AccountRequestDO;
import com.mhra.mdcm.devices.appian.pageobjects._Page;
import com.mhra.mdcm.devices.appian.pageobjects.external.MyAccountPage;
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
public class OrganisationDetails extends _Page {

    //ORGANISATION DETAILS
    @FindBy(xpath = ".//label[.='Organisation name']//following::input[1]")
    WebElement orgName;
    @FindBy(xpath = ".//label[.='Address line 1']//following::input[1]")
    WebElement orgAddressLine1;
    @FindBy(xpath = ".//label[contains(text(),'Address line 2')]//following::input[1]")
    WebElement orgAddressLine2;
    @FindBy(xpath = ".//label[contains(text(),'Address line 3')]//following::input[1]")
    WebElement orgAddressLine3;
    @FindBy(xpath = ".//label[contains(text(),'Address line 4')]//following::input[1]")
    WebElement orgAddressLine4;
    @FindBy(xpath = ".//label[contains(text(),'Address line 4')]//following::input[2]")
    WebElement stateCountyOrProvince;
    @FindBy(xpath = ".//label[contains(text(),'City')]//following::input[1]")
    WebElement orgCityTown;
    @FindBy(xpath = ".//label[.='Postcode']//following::input[1]")
    WebElement orgPostCode;
    @FindBy(css = ".GFWJSJ4DEY.GFWJSJ4DIY>div")
    WebElement orgCountry;
    @FindBy(xpath = ".//label[contains(text(),'Telephone')]//following::input[1]")
    WebElement orgTelephone;
    @FindBy(xpath = ".//label[contains(text(),'Website')]//following::input[1]")
    WebElement webSite;

    @FindBy(xpath = ".//span[contains(text(),'Address type')]//following::p[1]")
    WebElement addressType;

    @FindBy(css = ".component_error")
    List <WebElement> errorMessages;

    @FindBy(xpath = ".//button[.='Yes']")
    WebElement confirmYes;
    @FindBy(xpath = ".//button[.='No']")
    WebElement confirmNo;

    @FindBy(xpath = ".//button[contains(text(),'Save')]")
    List<WebElement> saveYes;
    @FindBy(xpath = ".//button[contains(text(),'Cancel')]")
    WebElement saveNo;

    //Submit or cancel button
    @FindBy(xpath = ".//button[contains(text(),'Submit')]")
    WebElement submitBtn;
    @FindBy(css = ".//button[contains(text(),'Cancel')]")
    WebElement cancelBtn;

    @Autowired
    public OrganisationDetails(WebDriver driver) {
        super(driver);
    }

    public OrganisationDetails updateFollowingFields(String keyValuePairToUpdate, AccountRequestDO updatedData) {

        WaitUtils.waitForElementToBeClickable(driver, orgName, TIMEOUT_5_SECOND);
        String[] dataPairs = keyValuePairToUpdate.split(",");

        boolean orgNameUpdated = false;
        for (String pairs : dataPairs) {

            String key = pairs;

            if (key.equals("org.name")) {
                PageUtils.updateElementValue(driver, orgName, updatedData.organisationName, TIMEOUT_5_SECOND);
                orgNameUpdated = true;
            }else if (key.equals("org.address1")) {
                PageUtils.updateElementValue(driver, orgAddressLine1, updatedData.address1, TIMEOUT_5_SECOND);
            }else if (key.equals("org.address2")) {
                PageUtils.updateElementValue(driver, orgAddressLine2, updatedData.address2, TIMEOUT_5_SECOND);
            }else if (key.equals("org.address3")) {
                PageUtils.updateElementValue(driver, orgAddressLine3, updatedData.address3, TIMEOUT_5_SECOND);
            }else if (key.equals("org.address4")) {
                PageUtils.updateElementValue(driver, orgAddressLine3, updatedData.address4, TIMEOUT_5_SECOND);
            }else if (key.equals("org.state.county.province")) {
                PageUtils.updateElementValue(driver, stateCountyOrProvince, updatedData.stateCountyOrProvince, TIMEOUT_5_SECOND);
            }else if (key.equals("org.city")) {
                PageUtils.updateElementValue(driver, orgCityTown, updatedData.townCity, TIMEOUT_5_SECOND);
            }else if (key.equals("org.postcode")) {
                PageUtils.updateElementValue(driver, orgPostCode, updatedData.postCode, TIMEOUT_5_SECOND);
            }else if (key.equals("org.country")) {
                //PageUtils.selectFromDropDown(driver, orgCountry, updatedData.country);
            }else if (key.equals("org.telephone")) {
                PageUtils.updateElementValue(driver, orgTelephone, updatedData.telephone, TIMEOUT_5_SECOND);
            }else if (key.equals("org.website")) {
                if(orgNameUpdated)
                PageUtils.updateElementValue(driver, webSite, updatedData.website, TIMEOUT_5_SECOND);
            }
        }

        PageUtils.doubleClick(driver, submitBtn);

        return new OrganisationDetails(driver);
    }


    public boolean isErrorMessageDisplayed() {
        try {
            WaitUtils.waitForElementToBeVisible(driver, By.cssSelector(".component_error"), 3);
            boolean isDisplayed = errorMessages.size() > 0;
            return isDisplayed;
        }catch (Exception e){
            return false;
        }
    }

    public OrganisationDetails confirmChanges(boolean confirm) {
        WaitUtils.waitForElementToBeClickable(driver, confirmYes, TIMEOUT_DEFAULT);
        if(confirm){
            confirmYes.click();
        }else{
            confirmNo.click();
        }
        return new OrganisationDetails(driver);
    }

    public MyAccountPage saveChanges(boolean saveChanges) {
        WaitUtils.waitForElementToBeClickable(driver, saveNo, TIMEOUT_DEFAULT);
        if(saveChanges){
            saveYes.get(1).click();
        }else{
            saveNo.click();
        }
        return new MyAccountPage(driver);
    }


    public boolean isAddressTypeEditable() {
        boolean isEditable = true;
        WaitUtils.waitForElementToBeClickable(driver, orgName, TIMEOUT_5_SECOND);
        WaitUtils.waitForElementToBeClickable(driver, addressType, TIMEOUT_5_SECOND);
        try{
            addressType.sendKeys("not editable");
        }catch (Exception e){
            isEditable = false;
        }
        return isEditable;
    }
}
