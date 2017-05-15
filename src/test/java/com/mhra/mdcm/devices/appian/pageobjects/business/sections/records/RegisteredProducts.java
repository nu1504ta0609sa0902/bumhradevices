package com.mhra.mdcm.devices.appian.pageobjects.business.sections.records;

import com.mhra.mdcm.devices.appian.enums.PageHeaders;
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
public class RegisteredProducts extends _Page {

    @FindBy(xpath = ".//th[@abbr='Authorised Representative']//following::tr")
    List<WebElement> listOfAllProducts;
    @FindBy(xpath = ".//td[6]")
    List<WebElement> listOfAllManufacturerNames;
    @FindBy(xpath = ".//td[1]")
    List<WebElement> listOfDeviceTypes;
    @FindBy(xpath = ".//td[4]")
    List<WebElement> listOfAllProductNames;
    @FindBy(xpath = ".//table//th")
    List<WebElement> listOfTableHeadings;

    //Table headings
    @FindBy(xpath = ".//th[1]")
    WebElement thDeviceType;
    @FindBy(xpath = ".//th[6]")
    WebElement thManufacturer;
    @FindBy(xpath = ".//th[8]")
    WebElement thAuthorisedRep;

    //Search box and filters
    @FindBy(xpath = ".//*[contains(@class, 'filter')]//following::input[1]")
    WebElement searchBox;
    @FindBy(xpath = ".//button[.='Search']")
    WebElement btnSearch;
    @FindBy(xpath = ".//span[@class='DropdownWidget---inline_label']")
    List<WebElement> listOfDropDownFilters;
    @FindBy(linkText = "Clear Filters")
    WebElement clearFilters;

    @Autowired
    public RegisteredProducts(WebDriver driver) {
        super(driver);
    }

    public boolean isHeadingCorrect(String expectedHeadings) {
        By by = By.xpath(".//h1[.='" + expectedHeadings + "']");
        WaitUtils.waitForElementToBeClickable(driver, by, TIMEOUT_10_SECOND);
        WebElement heading = driver.findElement(by);
        boolean contains = heading.getText().contains(expectedHeadings);
        return contains;
    }

    public boolean isItemsDisplayed(String expectedHeadings) {
        boolean itemsDisplayed = false;
        WaitUtils.waitForElementToBeClickable(driver, By.xpath(".//h1[.='" + expectedHeadings + "']"), TIMEOUT_10_SECOND);

        if (expectedHeadings.contains(PageHeaders.PAGE_HEADERS_REGISTERED_PRODUCTS.header)) {
            itemsDisplayed = listOfAllProducts.size() > 0;
        }

        return itemsDisplayed;
    }

    public List<String> isTableColumnCorrect(String[] columns) {
        WaitUtils.waitForElementToBeClickable(driver, By.xpath(".//table//th"), TIMEOUT_DEFAULT);
        List<String> columnsNotFound = PageUtils.areTheColumnsCorrect(columns, listOfTableHeadings);
        return columnsNotFound;
    }

    public RegisteredProducts searchForAllProducts(String searchTerm) {
        WaitUtils.waitForElementToBeClickable(driver, searchBox, TIMEOUT_DEFAULT);
        PageUtils.searchPageFor(searchTerm, searchBox);
        return new RegisteredProducts(driver);
    }

    public boolean atLeast1MatchFound(String searchText) {
        boolean atLeast1MatchFound = true;
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        WaitUtils.waitForElementToBeClickable(driver, clearFilters, TIMEOUT_DEFAULT);
        try {
            //WaitUtils.waitForElementToBeClickable(driver, By.partialLinkText(searchText), TIMEOUT_5_SECOND);
            int actualCount = (listOfAllProducts.size() - 1);
            atLeast1MatchFound = actualCount >= 1;
        } catch (Exception e) {
            log.error("Timeout : Trying to search");
            atLeast1MatchFound = false;
        }

        return atLeast1MatchFound;
    }

    public String getARandomProductEntry() {
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        WaitUtils.waitForElementToBeClickable(driver, By.xpath(".//td[6]"), TIMEOUT_5_SECOND);

        int position = RandomDataUtils.getSimpleRandomNumberBetween(1, listOfAllManufacturerNames.size() - 1, false);
        WebElement manufacaturerNameLink = listOfAllManufacturerNames.get(position);
        String manufacturerName = manufacaturerNameLink.getText();
        return manufacturerName;
    }

    public BusinessManufacturerDetails viewManufacturerByText(String searchTerm) {
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        WaitUtils.waitForElementToBeClickable(driver, By.xpath(".//td[6]"), TIMEOUT_5_SECOND);
        int position = RandomDataUtils.getSimpleRandomNumberBetween(1, listOfAllManufacturerNames.size() - 1, false);
        WebElement manufacaturerNameLink = listOfAllManufacturerNames.get(position);
        manufacaturerNameLink = manufacaturerNameLink.findElement(By.tagName("a"));
        PageUtils.doubleClick(driver, manufacaturerNameLink);
        return new BusinessManufacturerDetails(driver);
    }

    public BusinessProductDetails viewProductBy(String tableHeading) {
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        WaitUtils.waitForElementToBeClickable(driver, By.xpath(".//td[4]"), TIMEOUT_5_SECOND);

        List<WebElement> listOfElement = getListOfElement(tableHeading);
        WebElement found = null;
        int count = 0;
        do {
            int position = RandomDataUtils.getSimpleRandomNumberBetween(1, listOfElement.size() - 1, false);
            WebElement product = listOfElement.get(position);
            product = product.findElement(By.tagName("a"));
            boolean isProductNameEmpty = product.getText().trim().equals("");
            if (!isProductNameEmpty) {
                found = product;
                break;
            }
            count++;
        } while (found == null && count < 5);

        if (found != null) {
            found.click();
        }
        return new BusinessProductDetails(driver);
    }

    private List<WebElement> getListOfElement(String tableHeading) {
        if (tableHeading.equals("Product Name"))
            return listOfAllProductNames;
        else if (tableHeading.equals("Manufacturer")) {
            return listOfAllManufacturerNames;
        }
        return null;
    }

    public RegisteredProducts filterByDeviceType(String deviceType) {
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        PageUtils.selectFromDropDown(driver, listOfDropDownFilters.get(0), deviceType, false);
        return new RegisteredProducts(driver);
    }

    public boolean areAllProductOfType(String value) {
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        WaitUtils.waitForElementToBeClickable(driver, clearFilters, TIMEOUT_10_SECOND);
        WaitUtils.waitForElementToBeClickable(driver, listOfDropDownFilters.get(0), TIMEOUT_10_SECOND);
        WaitUtils.nativeWaitInSeconds(5);

        boolean allMatched = true;
        for (WebElement el : listOfDeviceTypes) {
            String text = el.getText();
            log.info(text);
            if (!text.contains("revious") && !text.contains("ext")) {
                allMatched = text.contains(value);
                if (!allMatched) {
                    break;
                }
            }
        }

        return allMatched;
    }

    public RegisteredProducts clearFilterByStatus() {
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        WaitUtils.waitForElementToBeClickable(driver, clearFilters, TIMEOUT_3_SECOND);
        clearFilters.click();
        return new RegisteredProducts(driver);
    }

    public boolean areDevicesOfTypeVisible(String value) {
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        WaitUtils.waitForElementToBeClickable(driver, clearFilters, TIMEOUT_10_SECOND);
        boolean aMatchFound = false;
        for (WebElement el : listOfDeviceTypes) {
            String text = el.getText();
            log.info(text);
            if (!text.contains("revious") && !text.contains("ext")) {
                aMatchFound = text.contains(value);
                if (aMatchFound) {
                    break;
                }
            }
        }
        return aMatchFound;
    }

    public boolean isSearchingCompleted() {
        boolean seachingCompleted = false;
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        try {
            WaitUtils.waitForElementToBeClickable(driver, clearFilters, TIMEOUT_30_SECOND);
            seachingCompleted = true;
        } catch (Exception e) {
        }
        return seachingCompleted;
    }


    public RegisteredProducts sortBy(String tableHeading, int numberOfTimesToClick) {

        for (int c = 0; c < numberOfTimesToClick; c++) {
            WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
            if (tableHeading.equals("Device Type")) {
                WaitUtils.waitForElementToBeClickable(driver, thDeviceType, TIMEOUT_10_SECOND);
                thDeviceType.click();
            } else if (tableHeading.equals("Manufacturer")) {
                WaitUtils.waitForElementToBeClickable(driver, thManufacturer, TIMEOUT_10_SECOND);
                thManufacturer.click();
            } else if (tableHeading.equals("Authorised Representative")) {
                WaitUtils.waitForElementToBeClickable(driver, thAuthorisedRep, TIMEOUT_10_SECOND);
                thAuthorisedRep.click();
            }
        }

        return new RegisteredProducts(driver);
    }
}
