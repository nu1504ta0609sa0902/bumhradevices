package com.mhra.mdcm.devices.appian.pageobjects.business.sections.records;

import com.mhra.mdcm.devices.appian.pageobjects._Page;
import com.mhra.mdcm.devices.appian.utils.selenium.others.RandomDataUtils;
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
public class AllDevices extends _Page {

    @FindBy(xpath = ".//table//th")
    List<WebElement> listOfTableHeadings;
    @FindBy(xpath = ".//h1[.='All Devices']//following::tr")
    List<WebElement> listOfAllDevices;
    @FindBy(xpath = ".//td[2]")
    List<WebElement> listOfGmdnCode;
    @FindBy(xpath = ".//*[.='GMDN term']//following::tr//td[1]")
    List<WebElement> listOfDeviceTypes;

    //List of manufacturers using gmdn code
    @FindBy(xpath = ".//td[3]")
    List<WebElement> listOfOrganisationNames;

    //Search box
    @FindBy(xpath = ".//*[contains(@class, 'filter')]//following::input[1]")
    WebElement searchBox;
    @FindBy(xpath = ".//span[@class='DropdownWidget---inline_label']")
    List<WebElement> listOfDropDownFilters;
    @FindBy(linkText = "Clear Filters")
    WebElement clearFilters;

    @Autowired
    public AllDevices(WebDriver driver) {
        super(driver);
    }


    public boolean isHeadingCorrect(String expectedHeadings) {
        By by = By.xpath(".//h1[.='" + expectedHeadings + "']");
        WaitUtils.waitForElementToBeClickable(driver, by , TIMEOUT_DEFAULT, false);
        WebElement heading = driver.findElement(by);
        boolean contains = heading.getText().contains(expectedHeadings);
        return contains;
    }


    public boolean isItemsDisplayed(String expectedHeadings) {
        boolean itemsDisplayed = false;
        WaitUtils.waitForElementToBeClickable(driver, By.xpath(".//h1[.='" + expectedHeadings + "']") , TIMEOUT_DEFAULT, false);

        if(expectedHeadings.contains("All Devices")){
            itemsDisplayed = listOfAllDevices.size() > 0;
        }

        return itemsDisplayed;
    }

    public AllDevices searchForAllDevices(String searchTerm) {
        WaitUtils.waitForElementToBeClickable(driver, searchBox, TIMEOUT_DEFAULT, false);
        PageUtils.searchPageFor(searchTerm, searchBox);
        return new AllDevices(driver);
    }

    public String getARandomGMDNCode() {
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        WaitUtils.waitForElementToBeClickable(driver, By.xpath(".//td[2]"), TIMEOUT_5_SECOND, false);

        int position = RandomDataUtils.getSimpleRandomNumberBetween(1, listOfGmdnCode.size() - 1, false);
        WebElement gmdnCode = listOfGmdnCode.get(position);
        String gmdnLink = gmdnCode.getText();
        return gmdnLink;
    }


    public boolean atLeast1MatchFound(String searchText) {
        boolean atLeast1MatchFound = true;
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        WaitUtils.waitForElementToBeClickable(driver, clearFilters, TIMEOUT_40_SECOND, false);
        try{
            //They have a hidden "a" tag in the page
            int actualCount = listOfAllDevices.size() - 1;
            atLeast1MatchFound = actualCount >= 1;
        }catch (Exception e){
            log.error("Timeout : Trying to search");
            atLeast1MatchFound = false;
        }

        return atLeast1MatchFound;
    }

    public List<String> isTableColumnCorrect(String[] columns) {
        WaitUtils.waitForElementToBeClickable(driver, By.xpath(".//table//th") , TIMEOUT_DEFAULT, false);
        List<String> columnsNotFound = PageUtils.areTheColumnsCorrect(columns, listOfTableHeadings);
        return columnsNotFound;
    }

    public AllDevices clickOnARandomGMDNCode() {
        //String gmdnCode = getARandomGMDNCode();
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        int position = RandomDataUtils.getSimpleRandomNumberBetween(1, listOfGmdnCode.size() - 1, false);
        WebElement gmdnCode = listOfGmdnCode.get(position);
        gmdnCode = gmdnCode.findElement(By.tagName("a"));
        PageUtils.doubleClick(driver, gmdnCode);
        return new AllDevices(driver);

    }

    public boolean isListOfManufacturersVisible() {
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        WebElement element = listOfOrganisationNames.get(0);
        boolean isVisible = true;
        try {
            WaitUtils.waitForElementToBeClickable(driver, element, TIMEOUT_3_SECOND, false);
        }catch (Exception e){
            isVisible = false;
        }
        return isVisible;
    }

    public boolean isListOfManufacturersUsingDeviceTableColumnCorrect(String[] columns) {
        WaitUtils.waitForElementToBeClickable(driver, By.xpath(".//table//th") , TIMEOUT_DEFAULT, false);
        List<String> columnsNotFound = PageUtils.areTheColumnsCorrect(columns, listOfTableHeadings);
        return columnsNotFound.size() == 0;
    }

    public AllDevices filterByDeviceType(String deviceType) {
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        PageUtils.selectFromDropDown(driver, listOfDropDownFilters.get(0) , deviceType, false);
        return new AllDevices(driver);
    }


    public boolean areAllDevicesOfType(String deviceType) {

        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        WaitUtils.waitForElementToBeClickable(driver, clearFilters, TIMEOUT_10_SECOND, false);
        boolean allMatched = true;
        for(WebElement el: listOfDeviceTypes){
            String text = el.getText();
            //log.info(text);
            if(!text.contains("revious") && !text.contains("ext")) {
                allMatched = text.contains(deviceType);
                if (!allMatched) {
                    break;
                }
            }
        }
        return allMatched;
    }

    public AllDevices clearFilter() {
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        WaitUtils.waitForElementToBeClickable(driver, clearFilters, TIMEOUT_3_SECOND, false);
        clearFilters.click();
        return new AllDevices(driver);
    }



    public boolean areDevicesOfTypeVisible(String deviceType) {
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        WaitUtils.waitForElementToBeClickable(driver, clearFilters, TIMEOUT_10_SECOND, false);
        boolean aMatchFound = false;
        for(WebElement el: listOfDeviceTypes){
            String text = el.getText();
            //log.info(text);
            if(!text.contains("revious") && !text.contains("ext")) {
                aMatchFound = text.contains(deviceType);
                if (aMatchFound) {
                    break;
                }
            }
        }
        return aMatchFound;
    }
}
