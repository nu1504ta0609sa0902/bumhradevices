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
public class ContactPersonDetails extends _Page {

    //Fields related to a contact person details
    @FindBy(xpath = ".//span[contains(text(),'Title')]//following::div[@role='listbox']")
    WebElement title;
    @FindBy(xpath = ".//label[.='First name']//following::input[1]")
    WebElement firstName;
    @FindBy(xpath = ".//label[.='Last name']//following::input[1]")
    WebElement lastName;
    @FindBy(xpath = ".//label[contains(text(),'Job title')]//following::input[1]")
    WebElement jobTitle;
    @FindBy(xpath = ".//label[.='Email']//following::input[1]")
    WebElement email;
    @FindBy(xpath = ".//label[.='Telephone']//following::input[1]")
    WebElement telephone;
    @FindBy(xpath = ".//*[contains(text(),'main point of contact')]//following::label[1]")
    WebElement mainContactYes;
    @FindBy(xpath = ".//*[contains(text(),'main point of contact')]//following::label[2]")
    WebElement mainContactNo;

    //Submit changes to contact person details
    @FindBy(xpath = ".//button[.='Save']")
    WebElement saveBtn;
    @FindBy(xpath = ".//button[.='Submit']")
    WebElement submitBtn;
    @FindBy(xpath = ".//button[.='Cancel']")
    WebElement cancelBtn;

    //Confirm changes to be submitted
    @FindBy(xpath = ".//button[.='Yes']")
    WebElement confirmYes;
    @FindBy(xpath = ".//button[.='No']")
    WebElement confirmNo;

    //One more chance to cancel the changes
    @FindBy(xpath = ".//button[contains(text(),'Save')]")
    List<WebElement> btnSave;
    @FindBy(xpath = ".//button[contains(text(),'Cancel')]")
    WebElement btnCancel;

    @FindBy(css = ".component_error")
    List <WebElement> errorMessages;

    @Autowired
    public ContactPersonDetails(WebDriver driver) {
        super(driver);
    }


    public ContactPersonDetails updateFollowingFields(String keyValuePairToUpdate, AccountRequestDO updatedData) {
        WaitUtils.waitForElementToBeClickable(driver, saveBtn, TIMEOUT_DEFAULT);

        String[] dataPairs = keyValuePairToUpdate.split(",");

        for (String pairs : dataPairs) {
            //String[] split = pairs.split("=");
            String key = pairs;

            if (key.equals("contact.title")) {
                PageUtils.selectFromDropDown(driver, title, updatedData.title, false);
            }else if (key.equals("contact.firstname")) {
                PageUtils.updateElementValue(driver, firstName, updatedData.firstName, TIMEOUT_5_SECOND);
            } else if (key.equals("contact.lastname")) {
                PageUtils.updateElementValue(driver, lastName, updatedData.lastName, TIMEOUT_5_SECOND);
            } else if (key.equals("contact.job.title")) {
                PageUtils.updateElementValue(driver, jobTitle, updatedData.jobTitle, TIMEOUT_5_SECOND);
            } else if (key.equals("contact.email")) {
                PageUtils.updateElementValue(driver, email, updatedData.email, TIMEOUT_5_SECOND);
            } else if (key.equals("contact.telephone")) {
                PageUtils.updateElementValue(driver, telephone, updatedData.telephone, TIMEOUT_5_SECOND);
            }

        }

        PageUtils.doubleClick(driver, saveBtn);

        return new ContactPersonDetails(driver);
    }

    public ContactPersonDetails addNewContactPerson(AccountRequestDO contactNew, boolean submitForm) {
        WaitUtils.waitForElementToBeClickable(driver, saveBtn, TIMEOUT_10_SECOND);

        //Add a new contact details
        PageUtils.selectFromDropDown(driver, title, contactNew.title, false);
        PageUtils.updateElementValue(driver, firstName, contactNew.firstName, TIMEOUT_5_SECOND);
        PageUtils.updateElementValue(driver, lastName, contactNew.lastName, TIMEOUT_5_SECOND);
        PageUtils.updateElementValue(driver, jobTitle, contactNew.jobTitle, TIMEOUT_5_SECOND);
        PageUtils.updateElementValue(driver, email, contactNew.email, TIMEOUT_5_SECOND);
        PageUtils.updateElementValue(driver, telephone, contactNew.telephone, TIMEOUT_5_SECOND);

        //Is main point of contact
        if(contactNew.isMainPointOfContact){
            mainContactYes.click();
        }else{
            mainContactNo.click();
        }

        if(submitForm){
            PageUtils.doubleClick(driver, saveBtn);
        }

        return new ContactPersonDetails(driver);
    }


    public ContactPersonDetails confirmChangesRelateToOrganisation(boolean confirm) {
        WaitUtils.waitForElementToBeClickable(driver, confirmYes, TIMEOUT_DEFAULT);
        if(confirm){
            confirmYes.click();
        }else{
            confirmNo.click();
        }
        return new ContactPersonDetails(driver);
    }

    public MyAccountPage saveChanges(boolean saveChanges) {
        WaitUtils.waitForElementToBeClickable(driver, btnCancel, TIMEOUT_DEFAULT);
        if(saveChanges){
            btnSave.get(1).click();
        }else{
            btnCancel.click();
        }
        return new MyAccountPage(driver);
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

}
