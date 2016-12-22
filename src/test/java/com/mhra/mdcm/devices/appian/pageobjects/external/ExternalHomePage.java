package com.mhra.mdcm.devices.appian.pageobjects.external;

import com.mhra.mdcm.devices.appian.pageobjects._Page;
import com.mhra.mdcm.devices.appian.pageobjects.external.sections.AddDevices;
import com.mhra.mdcm.devices.appian.pageobjects.external.sections.CreateManufacturerTestsData;
import com.mhra.mdcm.devices.appian.pageobjects.external.sections.ManufacturerDetails;
import com.mhra.mdcm.devices.appian.pageobjects.external.sections.ManufacturerList;
import com.mhra.mdcm.devices.appian.utils.selenium.others.RandomDataUtils;
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

    @FindBy(css = ".SafeImage.GFWJSJ4DOFB")
    WebElement linkManufacturerRegistration;
    @FindBy(css = ".GFWJSJ4DCF")
    WebElement linkRegisterAnotherManufacturer;

    @FindBy(css = ".gwt-ListBox.GFWJSJ4DC0")
    WebElement manufacturerDropDown;
    @FindBy(css = "Declare devices for")
    WebElement linkDeclareDevicesFor;

    @FindBy(xpath = ".//*[contains(text(),'ype of device')]//following::input[1]")
    WebElement generalMedicalDevice;


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
        WaitUtils.waitForElementToBeClickable(driver, linkManufacturerRegistration, TIMEOUT_DEFAULT, false);
        linkManufacturerRegistration.click();
        return new ManufacturerList(driver);
    }


//    public ExternalHomePage registerAnotherManufacturer() {
//        WaitUtils.waitForElementToBeClickable(driver, linkRegisterAnotherManufacturer, TIMEOUT_DEFAULT, false);
//        linkRegisterAnotherManufacturer.click();
//        return new ExternalHomePage(driver);
//    }
//
//    public boolean isNewManufacturerInTheList(String name) {
//        WaitUtils.waitForElementToBeClickable(driver, manufacturerDropDown, TIMEOUT_10_SECOND, false);
//        String selectedOption = manufacturerDropDown.getText(); //PageUtils.getCurrentSelectedOption(manufacturerDropDown);
//        boolean found = selectedOption.contains(name);
//        return found;
//    }

//    public boolean isLinkDisplayed(String partialLink) {
//        WaitUtils.waitForElementToBeClickable(driver, By.partialLinkText(partialLink), TIMEOUT_5_SECOND, false );
//        WebElement declareDevicesLink = driver.findElement(By.partialLinkText(partialLink));
//        boolean displayed = declareDevicesLink.isDisplayed();
//        return displayed;
//    }
//
//    public ExternalHomePage selectManufacturerFromList(String name) {
//        WaitUtils.waitForElementToBeClickable(driver, manufacturerDropDown, TIMEOUT_5_SECOND, false);
//        PageUtils.selectByText(manufacturerDropDown, name);
//        return new ExternalHomePage(driver);
//    }

//    public AddDevices gotoAddDevicesPageForManufacturer(String name) {
//        boolean isDisplayed = PageUtils.isDisplayed(driver, manufacturerDropDown, TIMEOUT_5_SECOND);
//        if(isDisplayed) {
//            //WaitUtils.waitForElementToBeClickable(driver, manufacturerDropDown, TIMEOUT_5_SECOND, false);
//            WaitUtils.waitForElementToBeClickable(driver, By.partialLinkText(name), TIMEOUT_5_SECOND, false);
//        }
//        //linkDeclareDevicesFor.click();
//        driver.findElement(By.partialLinkText(name)).click();
//        return new AddDevices(driver);
//    }
//
//    public String getARandomManufacturerNameFromDropDown() {
//        WaitUtils.waitForElementToBeClickable(driver, manufacturerDropDown, TIMEOUT_5_SECOND, false);
//        List<String> listOfOptions = PageUtils.getListOfOptions(manufacturerDropDown);
//        String index = RandomDataUtils.getRandomNumberBetween(0, listOfOptions.size());
//        String name = listOfOptions.get(Integer.parseInt(index));
//        return name;
//    }


    public ExternalHomePage provideIndicationOfDevicesMade() {
        List<WebElement> elements = driver.findElements(By.cssSelector(".GFWJSJ4DPV.GFWJSJ4DCAD input"));
        for(WebElement e: elements){
            WaitUtils.waitForElementToBeClickable(driver, e, TIMEOUT_3_SECOND, false);
            PageUtils.clickIfVisible(driver, e);
        }

        List<WebElement> elements2 = driver.findElements(By.cssSelector(".GFWJSJ4DPV.GFWJSJ4DCAD input"));
        for(WebElement e: elements2){
            WaitUtils.waitForElementToBeClickable(driver, e, TIMEOUT_3_SECOND, false);
            PageUtils.clickIfVisible(driver, e);
        }

        return new ExternalHomePage(driver);
    }


    public ExternalHomePage provideIndicationOfDevicesMade(int index) {
        WaitUtils.waitForElementToBePartOfDOM(driver, By.xpath(".//*[contains(text(),'ype of device')]//following::input[1]"), TIMEOUT_10_SECOND, false);
        WaitUtils.waitForElementToBeVisible(driver, generalMedicalDevice, TIMEOUT_10_SECOND, false);

        //Find element
        WaitUtils.waitForElementToBePartOfDOM(driver, By.cssSelector(".GFWJSJ4DPV.GFWJSJ4DCAD input"), TIMEOUT_10_SECOND, false);
        List<WebElement> elements = driver.findElements(By.cssSelector(".GFWJSJ4DPV.GFWJSJ4DCAD input"));
        WebElement e = elements.get(index);
        WaitUtils.waitForElementToBeClickable(driver, e, TIMEOUT_10_SECOND, false);

        PageUtils.clickIfVisible(driver, e);
        WaitUtils.nativeWaitInSeconds(3);

        return new ExternalHomePage(driver);
    }

    public ExternalHomePage submitIndicationOfDevicesMade(boolean clickNext) {
        if(clickNext) {
            driver.findElements(By.cssSelector(".gwt-RadioButton.GFWJSJ4DGAD.GFWJSJ4DCW>label")).get(0).click();
            driver.findElement(By.xpath(".//button[.='Next']")).click();
        }else{
            WaitUtils.waitForElementToBeClickable(driver, By.xpath(".//button[.='Submit']"), TIMEOUT_10_SECOND, false);
            driver.findElement(By.xpath(".//button[.='Submit']")).click();
        }
        return new ExternalHomePage(driver);
    }

    public void selectCustomMade(boolean isCustomMade) {
        By customMadeYes = By.xpath(".//*[contains(text(),'custom made')]//following::input[1]");
        By customMadeNo = By.xpath(".//*[contains(text(),'custom made)]//following::input[2]");
        if(isCustomMade) {
            WaitUtils.waitForElementToBeClickable(driver,customMadeYes , TIMEOUT_10_SECOND, false);
            driver.findElement(customMadeYes).click();
        }else{
            WaitUtils.waitForElementToBeClickable(driver, customMadeNo, TIMEOUT_10_SECOND, false);
            driver.findElement(customMadeNo).click();
        }
    }
}
