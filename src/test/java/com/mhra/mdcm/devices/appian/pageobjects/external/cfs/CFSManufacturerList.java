package com.mhra.mdcm.devices.appian.pageobjects.external.cfs;

import com.mhra.mdcm.devices.appian.pageobjects._Page;
import com.mhra.mdcm.devices.appian.pageobjects.external._CreateCFSManufacturerTestHarnessPage;
import com.mhra.mdcm.devices.appian.pageobjects.external.manufacturer.ManufacturerDetails;
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
    @FindBy(xpath = ".//h2[contains(text(),'Manufacturers you represent')]//following::tbody[1]/tr/td[1]")
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

    //CFS manufacturer search and filter
    @FindBy(xpath = ".//*[contains(text(), 'Search by manufacturer name')]//following::input")
    WebElement tbxSearchTerm;


    //Buttons
    @FindBy(xpath = ".//button[contains(text(), 'Submit')]")
    WebElement btnSubmit;
    @FindBy(xpath = ".//button[contains(text(), 'Add new manufacturer')]")
    WebElement btnAddNewManufacturer;
    @FindBy(xpath = ".//button[.='Continue']")
    WebElement btnContinue;
    @FindBy(xpath = ".//button[.='Search']")
    WebElement btnSearch;

    @Autowired
    public CFSManufacturerList(WebDriver driver) {
        super(driver);
    }


    public CFSManufacturerList clickNext(){
        WaitUtils.waitForElementToBeClickable(driver, nextPage, TIMEOUT_5_SECOND);
        nextPage.click();
        return new CFSManufacturerList(driver);
    }

    public CFSManufacturerList clickPrev(){
        WaitUtils.waitForElementToBeClickable(driver, prevPage, TIMEOUT_5_SECOND);
        prevPage.click();
        return new CFSManufacturerList(driver);
    }

    public CFSManufacturerList clickLastPage(){
        WaitUtils.waitForElementToBeClickable(driver, lastPage, TIMEOUT_5_SECOND);
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
            WaitUtils.waitForElementToBeClickable(driver, rbtExportOutsideOfEUYes, TIMEOUT_5_SECOND);
            rbtExportOutsideOfEUYes.click();
            rbtExportsMedicinalProducts.click();
            btnSubmit.click();
        }
        return new CFSManufacturerList(driver);
    }

    public boolean isManufacturerListDisplayed() {
        //WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        boolean isVisible = PageUtils.isVisible(driver, btnAddNewManufacturer, TIMEOUT_10_SECOND);
        return isVisible;
    }

    public _CreateCFSManufacturerTestHarnessPage addNewManufacturer() {
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        WaitUtils.waitForElementToBeClickable(driver, btnAddNewManufacturer, TIMEOUT_5_SECOND);
        btnAddNewManufacturer.click();
        return new _CreateCFSManufacturerTestHarnessPage(driver);
    }

    public String getARandomOrganisationName() {
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        WebElement element = PageUtils.getRandomElementFromList(listOfOrganisationNames);
        String name = element.getText();
        return name;
    }

    public ManufacturerDetails viewManufacturer(String name) {
        WaitUtils.waitForElementToBeClickable(driver, By.partialLinkText(name), TIMEOUT_10_SECOND);
        WebElement man = driver.findElement(By.partialLinkText(name));
        man.click();
        return new ManufacturerDetails(driver);
    }

    public String getARandomOrganisationName(String orgName) {
        String name = "ItDoesNotExists";
        boolean isFound = isManufacturerDisplayedInList(orgName);
        if(isFound){
            name = getManufacturerWithName(orgName);
        }
        return name;
    }

    public String getManufacturerWithName(String orgName) {
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        return PageUtils.getElementMatchingText(listOfOrganisationNames, orgName).getText();
    }

    public boolean isManufacturerDisplayedInList(String manufacturerName){
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        boolean found = PageUtils.isLinkVisible(driver, manufacturerName);
        return found;
    }

    public CFSAddDevices clickContinue() {
        try {
            WaitUtils.waitForElementToBeClickable(driver, btnContinue, TIMEOUT_10_SECOND);
            btnContinue.click();
        }catch (Exception e){}
        return new CFSAddDevices(driver);
    }

    public CFSManufacturerList searchForManufacturer(String searchTerm) {
        WaitUtils.waitForElementToBeClickable(driver, tbxSearchTerm, TIMEOUT_5_SECOND);
        tbxSearchTerm.sendKeys(searchTerm);
        btnSearch.click();
        return new CFSManufacturerList(driver);
    }
}
