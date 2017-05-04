package com.mhra.mdcm.devices.appian.pageobjects.external.cfs;

import com.mhra.mdcm.devices.appian.pageobjects._Page;
import com.mhra.mdcm.devices.appian.pageobjects.external._CreateCFSManufacturerTestsData;
import com.mhra.mdcm.devices.appian.pageobjects.external.device.DeviceDetails;
import com.mhra.mdcm.devices.appian.pageobjects.external.manufacturer.ManufacturerViewDetails;
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
public class CFSManufacturerList extends _Page {
    //PAGINATION
    @FindBy(css = ".GridWidget---count")
    WebElement itemCount;
    @FindBy(css = ".GridWidget---count")
    List<WebElement> itemCounts;
    @FindBy(css = "[aria-label='Next page']")
    WebElement nextPage;
    @FindBy(css = "[aria-label='Previous page']")
    WebElement prevPage;
    @FindBy(css = "[aria-label='Last page']")
    WebElement lastPage;

    //List of table data
    @FindBy(xpath = ".//tr/th")
    List<WebElement> listOfTableHeadings;
    @FindBy(xpath = ".//div[contains(text(),'Manufacturer registration')]//following::tbody[1]/tr/td[1]")
    List<WebElement> listOfOrganisationNames;

    //About your organisations : Not always displayed
    @FindBy(xpath = ".//*[contains(text(), 'export products')]//following::label[1]")
    WebElement rbtExportOutsideOfEUYes;
    @FindBy(xpath = ".//*[contains(text(), 'export products')]//following::label[2]")
    WebElement rbtExportOutsideOfEUNo;
    @FindBy(xpath = ".//*[contains(text(), 'What products')]//following::label[1]")
    WebElement rbtExportsMedicinalProducts;
    @FindBy(xpath = ".//*[contains(text(), 'What products')]//following::label[2]")
    WebElement rbtExportsOtherProducts;

    //Buttons
    @FindBy(xpath = ".//button[contains(text(), 'Submit')]")
    WebElement btnSubmit;
    @FindBy(xpath = ".//button[contains(text(), 'Add new man')]")
    WebElement btnAddNewManufacturer;

    @Autowired
    public CFSManufacturerList(WebDriver driver) {
        super(driver);
    }


    public CFSManufacturerList clickNext(){
        WaitUtils.waitForElementToBeClickable(driver, nextPage, TIMEOUT_5_SECOND, false);
        nextPage.click();
        return new CFSManufacturerList(driver);
    }

    public CFSManufacturerList clickPrev(){
        WaitUtils.waitForElementToBeClickable(driver, prevPage, TIMEOUT_5_SECOND, false);
        prevPage.click();
        return new CFSManufacturerList(driver);
    }

    public CFSManufacturerList clickLastPage(){
        WaitUtils.waitForElementToBeClickable(driver, lastPage, TIMEOUT_5_SECOND, false);
        lastPage.click();
        return new CFSManufacturerList(driver);
    }

    public boolean isTableHeadingCorrect(String commaDelimitedHeading) {
        String lowerCaseHeadings = commaDelimitedHeading.toLowerCase();
        return PageUtils.isTableHeadingCorrect(lowerCaseHeadings, listOfTableHeadings, 1, 4);
    }

    public CFSManufacturerList tellUsAboutYourOrganisation() {
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        boolean isVisble = PageUtils.isVisible(driver, rbtExportsMedicinalProducts, TIMEOUT_3_SECOND);
        if(isVisble) {
            WaitUtils.waitForElementToBeClickable(driver, rbtExportOutsideOfEUYes, TIMEOUT_5_SECOND, false);
            rbtExportOutsideOfEUYes.click();
            rbtExportsMedicinalProducts.click();
            btnSubmit.click();
        }
        return new CFSManufacturerList(driver);
    }

    public boolean isManufacturerListDisplayed() {
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        boolean isVisible = PageUtils.isVisible(driver, btnAddNewManufacturer, TIMEOUT_3_SECOND);
        return isVisible;
    }

    public _CreateCFSManufacturerTestsData addNewManufacturer() {
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        WaitUtils.waitForElementToBeClickable(driver, btnAddNewManufacturer, TIMEOUT_5_SECOND, false);
        btnAddNewManufacturer.click();
        return new _CreateCFSManufacturerTestsData(driver);
    }

    public String getARandomOrganisationName() {
        WebElement element = PageUtils.getRandomElementFromList(listOfOrganisationNames);
        String name = element.getText();
        return name;
    }

    public DeviceDetails viewManufacturer(String name) {
        WaitUtils.waitForElementToBeClickable(driver, By.partialLinkText(name), TIMEOUT_10_SECOND, false);
        WebElement man = driver.findElement(By.partialLinkText(name));
        man.click();
        return new DeviceDetails(driver);
    }
}
