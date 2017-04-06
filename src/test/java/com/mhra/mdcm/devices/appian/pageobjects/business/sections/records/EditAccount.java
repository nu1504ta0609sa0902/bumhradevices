package com.mhra.mdcm.devices.appian.pageobjects.business.sections.records;

import com.mhra.mdcm.devices.appian.domains.newaccounts.AccountRequestDO;
import com.mhra.mdcm.devices.appian.pageobjects._Page;
import com.mhra.mdcm.devices.appian.utils.selenium.others.RandomDataUtils;

import com.mhra.mdcm.devices.appian.utils.selenium.page.PageUtils;
import com.mhra.mdcm.devices.appian.utils.selenium.page.WaitUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by TPD_Auto
 */
@Component
public class EditAccount extends _Page {

    @FindBy(xpath = ".//label[contains(text(),'Organisation name')]//following::input[1]")
    WebElement orgName;

    //PARD options
    @FindBy(xpath = ".//*[contains(text(),'Organisation name')]//following::label[1]")
    WebElement pardNameOptIn;
    @FindBy(xpath = ".//*[contains(text(),'Organisation name')]//following::label[2]")
    WebElement pardNameOptOut;
    @FindBy(xpath = ".//*[contains(text(),'Website')]//following::label[1]")
    WebElement pardAddressOptIn;
    @FindBy(xpath = ".//*[contains(text(),'Website')]//following::label[2]")
    WebElement pardAddressOptOut;

    //ORGANISATION ROLES
    @FindBy(xpath = ".//span[contains(text(),'Selected roles')]//following::label[1]")
    WebElement roleAuthorisedRep;
    @FindBy(xpath = ".//span[contains(text(),'Selected roles')]//following::label[2]")
    WebElement roleManufacturer;

    //ORGANISATION DETAILS
    @FindBy(xpath = ".//label[contains(text(),'line 1')]//following::input[1]")
    WebElement orgAddressLine1;
    @FindBy(xpath = ".//label[contains(text(),'line 2')]//following::input[1]")
    WebElement orgAddressLine2;
    @FindBy(xpath = ".//label[contains(text(),'City')]//following::input[1]")
    WebElement orgCityTown;
    @FindBy(xpath = ".//label[contains(text(),'Postcode')]//following::input[1]")
    WebElement orgPostCode;
    @FindBy(xpath = ".//span[contains(text(),'Address type')]//following::label[1]")
    WebElement addressType;
    @FindBy(xpath = ".//label[contains(text(),'Postcode')]//following::input[@type='text'][2]")
    WebElement orgTelephone;
    @FindBy(xpath = ".//label[contains(text(),'Postcode')]//following::input[@type='text'][3]")
    WebElement orgFax;
    @FindBy(xpath = ".//label[contains(text(),'Website')]//following::input[1]")
    WebElement webSite;
    @FindBy(xpath = ".//span[contains(text(),'Country')]//following::a[1]")
    WebElement orgCountryClear;
    @FindBy(xpath = ".//label[contains(text(),'Country')]//following::input[1]")
    WebElement orgCountry;

    //CONTACT PERSON DETAILS
    @FindBy(xpath = ".//label[contains(text(),'Job title')]//following::input[1]")
    WebElement jobTitle;
    @FindBy(xpath = ".//label[.='Email']//following::input[1]")
    WebElement emailAddress;
    @FindBy(xpath = ".//label[.='Email']//following::input[2]")
    WebElement phoneNumber;

    //Edit information related to an account
    @FindBy(xpath = ".//button[.='Submit']")
    WebElement submitBtn;
    @FindBy(xpath = ".//button[.='Cancel']")
    WebElement cancelBtn;


    @Autowired
    public EditAccount(WebDriver driver) {
        super(driver);
    }


    public Accounts editAccountInformation(String keyValuePairToUpdate, AccountRequestDO updatedData) {
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        WaitUtils.waitForElementToBeClickable(driver, submitBtn, TIMEOUT_DEFAULT, false);

        String[] dataPairs = keyValuePairToUpdate.split(",");

        for(String pairs: dataPairs){
            //String[] split = pairs.split("=");
            String key = pairs;

            if(key.equals("job.title")){
                PageUtils.updateElementValue(driver, jobTitle, updatedData.jobTitle, TIMEOUT_DEFAULT);
            }else if(key.equals("org.name")){
                PageUtils.updateElementValue(driver, orgName, updatedData.organisationName, TIMEOUT_DEFAULT);
            }else if(key.equals("address.line1")){
                PageUtils.updateElementValue(driver, orgAddressLine1, updatedData.address1, TIMEOUT_DEFAULT);
            }else if(key.equals("address.line2")){
                PageUtils.updateElementValue(driver, orgAddressLine2, updatedData.address2, TIMEOUT_DEFAULT);
            }else if(key.equals("city.town")){
                PageUtils.updateElementValue(driver, orgCityTown, updatedData.townCity, TIMEOUT_DEFAULT);
            }else if(key.equals("country")){
                //THIS NEEDS TO SELECT FROM AUTO SUGGESTS NOW
                //PageUtils.selectFromDropDown(driver, orgCountry, updatedData.country, false);
            }else if(key.equals("postcode")){
                PageUtils.updateElementValue(driver, orgPostCode, updatedData.postCode, TIMEOUT_DEFAULT);
            }else if(key.equals("org.telephone")){
                PageUtils.updateElementValue(driver, orgTelephone, updatedData.telephone, TIMEOUT_DEFAULT);
            }else if(key.equals("org.fax")){
                PageUtils.updateElementValue(driver, orgFax, updatedData.fax, TIMEOUT_DEFAULT);
            }
        }

        //Bug: email and telephone is not maintained
        //enterMissingData();

        //Submit data, but you must select address types
        PageUtils.doubleClick(driver, submitBtn);

        return new Accounts(driver);
    }

    public void enterMissingData() {
        WaitUtils.waitForElementToBeClickable(driver,phoneNumber,TIMEOUT_DEFAULT, false);
        phoneNumber.clear();
        phoneNumber.sendKeys("01351" + (int) RandomDataUtils.getRandomDigits(7));
        PageUtils.singleClick(driver, addressType);
        WaitUtils.waitForElementToBeClickable(driver,emailAddress,TIMEOUT_DEFAULT, false);
        emailAddress.clear();
        emailAddress.sendKeys("mhra.uat@gmail.com");
    }

    public boolean isPardOptionSelected(String pardOption, String nameOrAddress) {
        boolean isSelected = false;
        WaitUtils.waitForElementToBeClickable(driver, pardNameOptIn, TIMEOUT_3_SECOND, false);

        if(nameOrAddress.equals("name") && pardOption.contains("in")){
            isSelected = pardNameOptIn.isSelected();
        }else if(nameOrAddress.equals("name") && pardOption.contains("out")){
            isSelected = pardNameOptOut.isSelected();
        }else if(nameOrAddress.equals("address") && pardOption.contains("in")){
            isSelected = pardAddressOptIn.isSelected();
        }else if(nameOrAddress.equals("address") && pardOption.contains("out")){
            isSelected = pardAddressOptOut.isSelected();
        }
        return isSelected;
    }

    public BusinessManufacturerDetails updatePARDOptionFor(String pardOption, String nameOrAddress) {
        WaitUtils.waitForElementToBeClickable(driver, pardNameOptIn, TIMEOUT_3_SECOND, false);
        if(nameOrAddress.equals("name") && pardOption.contains("in")){
            pardNameOptIn.click();
        }else if(nameOrAddress.equals("name") && pardOption.contains("out")){
            pardNameOptOut.click();
        }else if(nameOrAddress.equals("address") && pardOption.contains("in")){
            pardAddressOptIn.click();
        }else if(nameOrAddress.equals("address") && pardOption.contains("out")){
            pardAddressOptOut.click();
        }

        //Submit the form
        submitBtn.click();
        return new BusinessManufacturerDetails(driver);
    }

    public BusinessManufacturerDetails updatePARDOptionsFor(String pardOptions) {
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        WaitUtils.waitForElementToBeClickable(driver, pardAddressOptIn, TIMEOUT_5_SECOND, false);
        String[] updateNameAndAddress = pardOptions.split(",");
        for(String whichOne: updateNameAndAddress){
            String[] keyValue = whichOne.split("=");
            String nameOrAddress = keyValue[0];
            String optInOrOut = keyValue[1];
            updatePARDOptionFor2(optInOrOut, nameOrAddress);
        }

        //Submit the form
        submitBtn.click();
        return new BusinessManufacturerDetails(driver);
    }

    private void updatePARDOptionFor2(String pardOption, String nameOrAddress) {
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        WaitUtils.waitForElementToBeClickable(driver, pardNameOptIn, TIMEOUT_5_SECOND, false);
        if(nameOrAddress.equals("name") && pardOption.contains("in")){
            pardNameOptIn.click();
        }else if(nameOrAddress.equals("name") && pardOption.contains("out")){
            pardNameOptOut.click();
        }else if(nameOrAddress.equals("address") && pardOption.contains("in")){
            pardAddressOptIn.click();
        }else if(nameOrAddress.equals("address") && pardOption.contains("out")){
            pardAddressOptOut.click();
        }
    }
}
