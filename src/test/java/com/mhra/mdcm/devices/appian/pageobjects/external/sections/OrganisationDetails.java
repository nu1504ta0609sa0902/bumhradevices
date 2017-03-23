package com.mhra.mdcm.devices.appian.pageobjects.external.sections;

import com.mhra.mdcm.devices.appian.domains.newaccounts.AccountRequest;
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
    @FindBy(css = "button.GFWJSJ4DCF")
    WebElement submitBtn;
    @FindBy(css = ".GFWJSJ4DFXC.left button.GFWJSJ4DNE")
    WebElement cancelBtn;

    @Autowired
    public OrganisationDetails(WebDriver driver) {
        super(driver);
    }

    public OrganisationDetails updateFollowingFields(String keyValuePairToUpdate, AccountRequest updatedData) {

        WaitUtils.waitForElementToBeClickable(driver, orgName, TIMEOUT_5_SECOND, false);
        String[] dataPairs = keyValuePairToUpdate.split(",");

        for (String pairs : dataPairs) {

            String key = pairs;
            boolean orgNameUpdated = false;

            if (key.equals("org.name")) {
                PageUtils.updateElementValue(driver, orgName, updatedData.organisationName, TIMEOUT_5_SECOND);
                orgNameUpdated = true;
            }else if (key.equals("org.address1")) {
                PageUtils.updateElementValue(driver, orgAddressLine1, updatedData.address1, TIMEOUT_5_SECOND);
            }else if (key.equals("org.address2")) {
                PageUtils.updateElementValue(driver, orgAddressLine2, updatedData.address2, TIMEOUT_5_SECOND);
            }else if (key.equals("org.city")) {
                PageUtils.updateElementValue(driver, orgCityTown, updatedData.townCity, TIMEOUT_5_SECOND);
            }else if (key.equals("org.postcode")) {
                PageUtils.updateElementValue(driver, orgPostCode, updatedData.postCode, TIMEOUT_5_SECOND);
            }else if (key.equals("org.country")) {
//                driver.findElement(By.cssSelector(".GFWJSJ4DEY.GFWJSJ4DIY a:nth-child(2)")).click();
//                driver.findElement(By.cssSelector(".GFWJSJ4DEY.GFWJSJ4DMX>div input")).clear();
                try {
                    PageUtils.selectFromAutoSuggestedListItems(driver, ".PickerWidget---picker_value", updatedData.country, false);
                } catch (Exception e) {
                    e.printStackTrace();
                }
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
            WaitUtils.waitForElementToBeVisible(driver, By.cssSelector(".component_error"), 3, false);
            boolean isDisplayed = errorMessages.size() > 0;
            return isDisplayed;
        }catch (Exception e){
            return false;
        }
    }

    public OrganisationDetails confirmChanges(boolean confirm) {
        WaitUtils.waitForElementToBeClickable(driver, confirmYes, TIMEOUT_DEFAULT, false);
        if(confirm){
            confirmYes.click();
        }else{
            confirmNo.click();
        }
        return new OrganisationDetails(driver);
    }

    public MyAccountPage saveChanges(boolean saveChanges) {
        WaitUtils.waitForElementToBeClickable(driver, saveNo, TIMEOUT_DEFAULT, false);
        if(saveChanges){
            saveYes.get(1).click();
        }else{
            saveNo.click();
        }
        return new MyAccountPage(driver);
    }


    public boolean isAddressTypeEditable() {
        boolean isEditable = true;
        WaitUtils.waitForElementToBeClickable(driver, orgName, TIMEOUT_5_SECOND, false);
        WaitUtils.waitForElementToBeClickable(driver, addressType, TIMEOUT_5_SECOND, false);
        try{
            addressType.sendKeys("not editable");
        }catch (Exception e){
            isEditable = false;
        }
        return isEditable;
    }
}
