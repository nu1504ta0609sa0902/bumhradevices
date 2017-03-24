package com.mhra.mdcm.devices.appian.pageobjects.external.myaccount;

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
public class ContactPersonDetails extends _Page {

    @FindBy(xpath = ".//span[contains(text(),'Title')]//following::select[1]")
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

    @FindBy(xpath = ".//button[.='Submit']")
    WebElement submitBtn;
    @FindBy(xpath = ".//button[.='Cancel']")
    WebElement cancelBtn;

    @FindBy(xpath = ".//button[.='Yes']")
    WebElement confirmYes;
    @FindBy(xpath = ".//button[.='No']")
    WebElement confirmNo;

    @FindBy(xpath = ".//button[contains(text(),'Save')]")
    List<WebElement> saveYes;
    @FindBy(xpath = ".//button[contains(text(),'Cancel')]")
    WebElement saveNo;

    @FindBy(css = ".component_error")
    List <WebElement> errorMessages;

    @Autowired
    public ContactPersonDetails(WebDriver driver) {
        super(driver);
    }


    public ContactPersonDetails updateFollowingFields(String keyValuePairToUpdate, AccountRequest updatedData) {
        WaitUtils.waitForElementToBeClickable(driver, submitBtn, TIMEOUT_DEFAULT, false);

        String[] dataPairs = keyValuePairToUpdate.split(",");

        for (String pairs : dataPairs) {
            //String[] split = pairs.split("=");
            String key = pairs;

            if (key.equals("contact.title")) {
                PageUtils.selectByText(title, updatedData.title);
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

        PageUtils.doubleClick(driver, submitBtn);

        return new ContactPersonDetails(driver);
    }


    public ContactPersonDetails confirmChangesRelateToOrganisation(boolean confirm) {
        WaitUtils.waitForElementToBeClickable(driver, confirmYes, TIMEOUT_DEFAULT, false);
        if(confirm){
            confirmYes.click();
        }else{
            confirmNo.click();
        }
        return new ContactPersonDetails(driver);
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

    public boolean isErrorMessageDisplayed() {
        try {
            WaitUtils.waitForElementToBeVisible(driver, By.cssSelector(".component_error"), 3, false);
            boolean isDisplayed = errorMessages.size() > 0;
            return isDisplayed;
        }catch (Exception e){
            return false;
        }
    }
}
