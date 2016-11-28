package com.mhra.mdcm.devices.appian.pageobjects.external;

import com.mhra.mdcm.devices.appian.pageobjects._Page;
import com.mhra.mdcm.devices.appian.pageobjects.external.sections.AddDevices;
import com.mhra.mdcm.devices.appian.pageobjects.external.sections.CreateManufacturerTestsData;
import com.mhra.mdcm.devices.appian.pageobjects.external.sections.ManufacturerDetails;
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

    @FindBy(linkText = "Manufacturer Registration")
    WebElement linkManufacturerRegistration;
    @FindBy(css = ".GFWJSJ4DCF")
    WebElement linkRegisterAnotherManufacturer;
    @FindBy(css = "button.GFWJSJ4DCF")
    WebElement linkRegisterNewManufacturer;

    @FindBy(css = ".gwt-ListBox.GFWJSJ4DC0")
    WebElement manufacturerDropDown;
    @FindBy(css = "Declare devices for")
    WebElement linkDeclareDevicesFor;

    @FindBy(css = ".left>div>a")
    List<WebElement> listOfManufacturerNames;

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

    public ExternalHomePage gotoManufacturerRegistrationPage() {
        WaitUtils.waitForElementToBeClickable(driver, linkManufacturerRegistration, TIMEOUT_DEFAULT, false);
        linkManufacturerRegistration.click();
        return new ExternalHomePage(driver);
    }

    public ManufacturerDetails viewAManufacturer(String manufacturerName) {
        if(manufacturerName == null){
            //Than view a random one
            int index = RandomDataUtils.getNumberBetween(0, listOfManufacturerNames.size() - 1);
            WebElement link = listOfManufacturerNames.get(index);
            link.click();
        }else{
            WaitUtils.waitForElementToBeClickable(driver, By.partialLinkText(manufacturerName), TIMEOUT_5_SECOND, false);
            WebElement man = driver.findElement(By.partialLinkText(manufacturerName));
            man.click();
        }
        return new ManufacturerDetails(driver);
    }

    public String getARandomManufacturerName() {
        WaitUtils.waitForElementToBeVisible(driver, By.cssSelector(".left>div>a"), TIMEOUT_5_SECOND, false);
        int index = RandomDataUtils.getNumberBetween(0, listOfManufacturerNames.size() - 1);
        WebElement link = listOfManufacturerNames.get(index);
        String name = link.getText();
        return name;
    }

    public ExternalHomePage registerAnotherManufacturer() {
        WaitUtils.waitForElementToBeClickable(driver, linkRegisterAnotherManufacturer, TIMEOUT_DEFAULT, false);
        linkRegisterAnotherManufacturer.click();
        return new ExternalHomePage(driver);
    }

    public CreateManufacturerTestsData registerNewManufacturer() {
        WaitUtils.waitForElementToBeClickable(driver, linkRegisterNewManufacturer, TIMEOUT_DEFAULT, false);
        WaitUtils.waitForElementToBeClickable(driver, manufacturerDropDown, TIMEOUT_5_SECOND, false);
        linkRegisterNewManufacturer.click();
        return new CreateManufacturerTestsData(driver);
    }

    public boolean isNewManufacturerInTheList(String name) {
        WaitUtils.waitForElementToBeClickable(driver, manufacturerDropDown, TIMEOUT_5_SECOND, false);
        String selectedOption = manufacturerDropDown.getText(); //PageUtils.getCurrentSelectedOption(manufacturerDropDown);
        boolean found = selectedOption.contains(name);
        return found;
    }

    public boolean isLinkDisplayed(String partialLink) {
        WaitUtils.waitForElementToBeClickable(driver, By.partialLinkText(partialLink), TIMEOUT_5_SECOND, false );
        WebElement declareDevicesLink = driver.findElement(By.partialLinkText(partialLink));
        boolean displayed = declareDevicesLink.isDisplayed();
        return displayed;
    }

    public ExternalHomePage selectManufacturerFromList(String name) {
        WaitUtils.waitForElementToBeClickable(driver, manufacturerDropDown, TIMEOUT_5_SECOND, false);
        PageUtils.selectByText(manufacturerDropDown, name);
        return new ExternalHomePage(driver);
    }

    public AddDevices gotoAddDevicesPageForManufacturer(String name) {
        WaitUtils.waitForElementToBeClickable(driver, manufacturerDropDown, TIMEOUT_5_SECOND, false);
        WaitUtils.waitForElementToBeClickable(driver, By.partialLinkText(name), TIMEOUT_5_SECOND, false);
        //linkDeclareDevicesFor.click();
        driver.findElement(By.partialLinkText(name)).click();
        return new AddDevices(driver);
    }
}
