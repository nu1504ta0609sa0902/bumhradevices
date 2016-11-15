package com.mhra.mdcm.devices.appian.pageobjects.business.sections;

import com.gargoylesoftware.htmlunit.Page;
import com.mhra.mdcm.devices.appian.pageobjects._Page;
import com.mhra.mdcm.devices.appian.utils.selenium.others.RandomDataUtils;
import com.mhra.mdcm.devices.appian.utils.selenium.page.AssertUtils;
import com.mhra.mdcm.devices.appian.utils.selenium.page.PageUtils;
import com.mhra.mdcm.devices.appian.utils.selenium.page.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
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
public class EditAccounts extends _Page {

    @FindBy(xpath = ".//h2[.='Status']//following::a")
    List<WebElement> listOfAccounts;
    @FindBy(xpath = ".//table//th")
    List<WebElement> listOfTableColumns;

    //Edit information related to an account
    @FindBy(linkText = "Edit Account Information")
    WebElement editAccountInfoLink;
    @FindBy(xpath = ".//label[contains(text(),'Job title')]//following::input[1]")
    WebElement jobTitle;
    @FindBy(xpath = ".//button[.='Submit']")
    WebElement submitBtn;

    //Updated information related to an account
    @FindBy(xpath = ".//span[.='Job title']//following::p[1]")
    WebElement jobTitleTxt;
    @FindBy(xpath = ".//span[contains(text(),'Address type')]//following::input[1]")
    WebElement addressType;
    @FindBy(xpath = ".//label[.='Email']//following::input[1]")
    WebElement emailAddress;
    @FindBy(xpath = ".//h3[contains(text(),'Person Details')]//following::input[5]")
    WebElement phoneNumber;


    //Search box
    @FindBy(xpath = ".//*[contains(@class, 'filter')]//following::input[1]")
    WebElement searchBox;

    @Autowired
    public EditAccounts(WebDriver driver) {
        super(driver);
    }


    public Accounts editAccountInformation(String keyValuePairToUpdate) {
        String[] dataPairs = keyValuePairToUpdate.split(",");

        for(String pairs: dataPairs){
            String[] split = pairs.split("=");
            String key = split[0];
            String value = split[1];
            if(key.equals("job.title")){
                WaitUtils.waitForElementToBeClickable(driver, jobTitle, TIMEOUT_DEFAULT, false);
                jobTitle.clear();
                jobTitle.sendKeys(RandomDataUtils.generateTestNameStartingWith(value, 5));
            }
        }

        //Bug: email and telephone is not maintained
        enterMissingData();

        //Submit data, but you must select address types
        addressType.click();
        submitBtn.click();

        return new Accounts(driver);
    }

    public void enterMissingData() {
        PageUtils.singleClick(driver, addressType);
        emailAddress.sendKeys("buggyRemovesEmail@test.com");
        phoneNumber.sendKeys("01351" + (int) RandomDataUtils.getRandomDigits(7));
    }
}
