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

    @FindBy(css = ".SafeImage.GFWJSJ4DOFB")
    WebElement linkManufacturerRegistration;
    @FindBy(css = ".GFWJSJ4DCF")
    WebElement linkRegisterAnotherManufacturer;
    @FindBy(css = "button.GFWJSJ4DCF")
    WebElement linkRegisterNewManufacturer;

    @FindBy(css = ".gwt-ListBox.GFWJSJ4DC0")
    WebElement manufacturerDropDown;
    @FindBy(css = "Declare devices for")
    WebElement linkDeclareDevicesFor;

    @FindBy(css = "td>div>a")
    List<WebElement> listOfManufacturerNames;

    @FindBy(css = ".GFWJSJ4DFDC div")
    WebElement itemCount;
    @FindBy(css = ".gwt-Image[aria-label='Next page']")
    WebElement nextPage;
    @FindBy(css = ".gwt-Image[aria-label='Previous page']")
    WebElement prevPage;
    @FindBy(css = ".gwt-Image[aria-label='Last page']")
    WebElement lastPage;

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

    public ExternalHomePage gotoListOfManufacturerPage() {
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
        WaitUtils.waitForElementToBeClickable(driver, By.cssSelector(".left>div>a"), TIMEOUT_5_SECOND, false);
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
        //boolean isDisplayed = PageUtils.isDisplayed(driver, manufacturerDropDown, TIMEOUT_5_SECOND);
        linkRegisterNewManufacturer.click();
        //PageUtils.doubleClick(driver, linkRegisterNewManufacturer);
        return new CreateManufacturerTestsData(driver);
    }

    public boolean isNewManufacturerInTheList(String name) {
        WaitUtils.waitForElementToBeClickable(driver, manufacturerDropDown, TIMEOUT_10_SECOND, false);
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
        boolean isDisplayed = PageUtils.isDisplayed(driver, manufacturerDropDown, TIMEOUT_5_SECOND);
        if(isDisplayed) {
            //WaitUtils.waitForElementToBeClickable(driver, manufacturerDropDown, TIMEOUT_5_SECOND, false);
            WaitUtils.waitForElementToBeClickable(driver, By.partialLinkText(name), TIMEOUT_5_SECOND, false);
        }
        //linkDeclareDevicesFor.click();
        driver.findElement(By.partialLinkText(name)).click();
        return new AddDevices(driver);
    }

    public String getARandomManufacturerNameFromDropDown() {
        WaitUtils.waitForElementToBeClickable(driver, manufacturerDropDown, TIMEOUT_5_SECOND, false);
        List<String> listOfOptions = PageUtils.getListOfOptions(manufacturerDropDown);
        String index = RandomDataUtils.getRandomNumberBetween(0, listOfOptions.size());
        String name = listOfOptions.get(Integer.parseInt(index));
        return name;
    }

    public boolean isManufacturerDisplayedInList(String manufacturerName){
        WaitUtils.nativeWaitInSeconds(2);
        WaitUtils.waitForElementToBeClickable(driver, By.cssSelector("td>div>a"), TIMEOUT_5_SECOND, false);
        boolean found = false;
        for(WebElement item: listOfManufacturerNames){
            String name = item.getText();
            if (name.contains(manufacturerName)) {
                found = true;
                break;
            }
        }
        return found;
    }

    public int getNumberOfPages() {
        try {
            WaitUtils.waitForElementToBeClickable(driver, itemCount, TIMEOUT_5_SECOND, false);
            String text = itemCount.getText();
            String total = text.substring(text.indexOf("of") + 3);
            String itemPerPage = text.substring(text.indexOf("-") + 1, text.indexOf(" of "));

            int tt = Integer.parseInt(total.trim());
            int noi = Integer.parseInt(itemPerPage.trim());

            int reminder = tt % noi;
            int numberOfPage = (tt/noi) - 1;
            if(reminder > 0){
                numberOfPage++;
            }

            return numberOfPage;
        }catch (Exception e){
            return 0;
        }
    }

    public ExternalHomePage clickNext(){
        WaitUtils.waitForElementToBeClickable(driver, nextPage, TIMEOUT_5_SECOND, false);
        nextPage.click();
        return new ExternalHomePage(driver);
    }

    public ExternalHomePage clickPrev(){
        WaitUtils.waitForElementToBeClickable(driver, prevPage, TIMEOUT_5_SECOND, false);
        prevPage.click();
        return new ExternalHomePage(driver);
    }

    public ExternalHomePage clickLastPage(){
        WaitUtils.waitForElementToBeClickable(driver, lastPage, TIMEOUT_5_SECOND, false);
        lastPage.click();
        return new ExternalHomePage(driver);
    }

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
        WaitUtils.nativeWaitInSeconds(1);
        List<WebElement> elements = driver.findElements(By.cssSelector(".GFWJSJ4DPV.GFWJSJ4DCAD input"));
        WebElement e = elements.get(index);
        WaitUtils.waitForElementToBeClickable(driver, e, TIMEOUT_10_SECOND, false);
        PageUtils.clickIfVisible(driver, e);

        return new ExternalHomePage(driver);
    }

    public ExternalHomePage submitIndicationOfDevicesMade(boolean clickNext) {
        if(clickNext) {
            driver.findElements(By.cssSelector(".gwt-RadioButton.GFWJSJ4DGAD.GFWJSJ4DCW>label")).get(0).click();
            driver.findElement(By.xpath(".//button[.='Next']")).click();
        }else{
            driver.findElement(By.xpath(".//button[.='Submit']")).click();
        }
        return new ExternalHomePage(driver);
    }

    public void selectCustomMade(boolean isCustomMade) {
        WaitUtils.waitForElementToBeClickable(driver, By.xpath(".//button[.='custom made']//following::input[1]"), TIMEOUT_3_SECOND, false);
        driver.findElement(By.xpath(".//button[.='custom made']//following::input[1]")).click();
    }
}
