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
    List<WebElement> listOfTableColumns;
    @FindBy(xpath = ".//h2[.='GMDN term']//following::tr")
    List<WebElement> listOfAllDevices;
    @FindBy(xpath = ".//td[2]")
    List<WebElement> listOfGmdnCode;
    @FindBy(xpath = ".//td[3]")
    List<WebElement> listOfGmdnTerms;

    //List of manufacturers using gmdn code
    @FindBy(xpath = ".//td[3]")
    List<WebElement> listOfOrganisationNames;
    @FindBy(xpath = ".//td[3]")
    List<WebElement> listOfAuthorisedReps;
    @FindBy(xpath = ".//td[3]")
    List<WebElement> listOfCountries;

    //Search box
    @FindBy(xpath = ".//*[contains(@class, 'filter')]//following::input[1]")
    WebElement searchBox;

    @Autowired
    public AllDevices(WebDriver driver) {
        super(driver);
    }


    public boolean isHeadingCorrect(String expectedHeadings) {
        WaitUtils.waitForElementToBeClickable(driver, By.xpath(".//h2[.='" + expectedHeadings + "']") , TIMEOUT_DEFAULT, false);
        WebElement heading = driver.findElement(By.xpath(".//h2[.='" + expectedHeadings + "']"));
        boolean contains = heading.getText().contains(expectedHeadings);
        return contains;
    }


    public boolean isItemsDisplayed(String expectedHeadings) {
        boolean itemsDisplayed = false;
        WaitUtils.waitForElementToBeClickable(driver, By.xpath(".//h2[.='" + expectedHeadings + "']") , TIMEOUT_DEFAULT, false);

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
        WaitUtils.waitForElementToBeClickable(driver, By.cssSelector(".appian-informationPanel b"), TIMEOUT_40_SECOND, false);
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
        List<String> columnsNotFound = PageUtils.areTheColumnsCorrect(columns, listOfTableColumns);
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
}
