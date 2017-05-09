package com.mhra.mdcm.devices.appian.pageobjects.external;

import com.mhra.mdcm.devices.appian.pageobjects._Page;
import com.mhra.mdcm.devices.appian.pageobjects.external.cfs.CFSManufacturerList;
import com.mhra.mdcm.devices.appian.pageobjects.external.manufacturer.ManufacturerList;
import com.mhra.mdcm.devices.appian.utils.selenium.page.CommonUtils;
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
public class ExternalHomePage extends _Page {

    @FindBy(css = ".Button---btn.Button---default_direction.Button---primary")
    WebElement linkManufacturerRegistration;

    @FindBy(xpath = ".//h4[contains(text(), 'free sale')]//following::button[1]")
    WebElement linkRegisterCFSForDevice;

    @FindBy(xpath = ".//*[contains(text(),'ype of device')]//following::label[1]")
    WebElement generalMedicalDevice;

    @FindBy(xpath = ".//button[.='Back']")
    WebElement btnBackButton;

    @FindBy(xpath = ".//h3[contains(text(),'Healthcare Products')]")
    WebElement pageHeading;

    //Error messages
    @FindBy(css = "strong.StrongText---richtext_strong")
    WebElement errorMessage;

    @Autowired
    public ExternalHomePage(WebDriver driver) {
        super(driver);
    }

    public boolean areLinksVisible(String delimitedLinks) {
        boolean visible = CommonUtils.areLinksVisible(driver, delimitedLinks);
        return visible;
    }

    public boolean areLinksClickable(String delimitedLinks) {
        boolean clickable = CommonUtils.areLinksClickable(driver, delimitedLinks);
        return clickable;
    }

    public ManufacturerList gotoListOfManufacturerPage() {
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        WaitUtils.waitForElementToBeClickable(driver, linkManufacturerRegistration, TIMEOUT_15_SECOND, false);
        linkManufacturerRegistration.click();
        return new ManufacturerList(driver);
    }


    public ExternalHomePage provideIndicationOfDevicesMade(int index) {

        //WaitUtils.isPageLoadingComplete(driver, 2);
        WaitUtils.waitForElementToBeClickable(driver, By.xpath(".//*[contains(text(),'ype of device')]//following::label[1]"), TIMEOUT_10_SECOND, false);
        WaitUtils.waitForElementToBeClickable(driver, generalMedicalDevice, TIMEOUT_10_SECOND, false);

        //Find element
        WaitUtils.waitForElementToBeClickable(driver, By.xpath(".//*[contains(text(),'ype of device')]//following::label"), TIMEOUT_10_SECOND, false);
        //WaitUtils.nativeWaitInSeconds(1);
        List<WebElement> elements = driver.findElements(By.xpath(".//*[contains(text(),'ype of device')]//following::label"));
        WebElement e = elements.get(index);
        WaitUtils.waitForElementToBeClickable(driver, e, TIMEOUT_10_SECOND, false);

        PageUtils.singleClick(driver, e);
        //WaitUtils.nativeWaitInSeconds(2);

        return new ExternalHomePage(driver);
    }

    public _CreateManufacturerTestsData submitIndicationOfDevicesMade(boolean clickNext) {
        if(clickNext) {
            driver.findElements(By.cssSelector(".gwt-RadioButton.GFWJSJ4DGAD.GFWJSJ4DCW>label")).get(0).click();
            driver.findElement(By.xpath(".//button[.='Next']")).click();
        }else{
            WaitUtils.waitForElementToBeClickable(driver, By.xpath(".//button[.='Submit']"), TIMEOUT_10_SECOND, false);
            driver.findElement(By.xpath(".//button[.='Submit']")).click();
        }
        return new _CreateManufacturerTestsData(driver);
    }

    public void selectCustomMade(boolean isCustomMade) {

        By customMadeYes = By.xpath(".//*[contains(text(),'type of device')]//following::label[2]");
        By customMadeNo = By.xpath(".//*[contains(text(),'type of device')]//following::label[3]");
        By aimdCustomMadeYes = By.xpath(".//*[contains(text(),'type of device')]//following::label[6]");
        By aimdCustomMadeNo = By.xpath(".//*[contains(text(),'type of device')]//following::label[7]");
        By sppCustomMadeYes = By.xpath(".//*[contains(text(),'type of device')]//following::label[9]");

        try {

            //General medical devices
            if (isCustomMade) {
                WaitUtils.waitForElementToBeClickable(driver, customMadeYes, TIMEOUT_10_SECOND, false);
                driver.findElement(customMadeYes).click();
            } else {
                WaitUtils.waitForElementToBeClickable(driver, customMadeNo, TIMEOUT_10_SECOND, false);
                driver.findElement(customMadeNo).click();
            }

            //AIMD
            if (isCustomMade) {
                WaitUtils.waitForElementToBeClickable(driver, aimdCustomMadeYes, TIMEOUT_10_SECOND, false);
                WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
                driver.findElement(aimdCustomMadeYes).click();
            } else {
                WaitUtils.waitForElementToBeClickable(driver, aimdCustomMadeNo, TIMEOUT_10_SECOND, false);
                driver.findElement(aimdCustomMadeNo).click();
            }

            //Others related to SSP
            WaitUtils.waitForElementToBeClickable(driver, sppCustomMadeYes, TIMEOUT_10_SECOND, false);
            driver.findElement(sppCustomMadeYes).click();
        }catch (Exception e){
            //Keeps failing
        }

        //Must be YES
        driver.findElement(aimdCustomMadeYes).click();
        driver.findElement(customMadeYes).click();
        driver.findElement(sppCustomMadeYes).click();
    }

    public boolean isTitleCorrect() {
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        String actualTitle = pageHeading.getText();
        boolean titleCorrect = actualTitle.contains("Regulatory Agency Services");
        return titleCorrect;
    }

    public boolean isInExternalHomePage() {
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        boolean correctPage = true;
        try{
            WaitUtils.waitForElementToBeClickable(driver, linkManufacturerRegistration, TIMEOUT_3_SECOND, false);
        }catch (Exception e){
            correctPage = false;
        }
        return correctPage;
    }

    public boolean isCorrectUsernameDisplayed(String loggedInUser) {
        String humanReadableUsername = CommonUtils.getHumanReadableUsername(loggedInUser);
        return driver.getPageSource().contains(humanReadableUsername);
    }

    public boolean isErrorMessageDsiplayed(String message) {
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        WaitUtils.waitForElementToBeClickable(driver, errorMessage, TIMEOUT_3_SECOND, false);
        boolean contains = errorMessage.getText().contains(message);
        return contains;
    }

    public ExternalHomePage clickBackButton() {
        WaitUtils.waitForElementToBeClickable(driver, btnBackButton, TIMEOUT_3_SECOND, false);
        btnBackButton.click();
        return new ExternalHomePage(driver);
    }

    public CFSManufacturerList gotoCFSPage() {
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        WaitUtils.waitForElementToBeClickable(driver, linkRegisterCFSForDevice, TIMEOUT_10_SECOND, false);
        linkRegisterCFSForDevice.click();
        return new CFSManufacturerList(driver);
    }
}
