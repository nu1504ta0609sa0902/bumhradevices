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

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TPD_Auto 
 */
@Component
public class AllProducts extends _Page {

    @FindBy(xpath = ".//h2[.='Authorised Representative']//following::a")
    List<WebElement> listOfAllProducts;
    @FindBy(xpath = ".//td[6]")
    List<WebElement> listOfAllManufacturerNames;
    @FindBy(xpath = ".//td[4]")
    List<WebElement> listOfAllProductNames;
    @FindBy(xpath = ".//table//th")
    List<WebElement> listOfTableColumns;

    //Search box
    @FindBy(xpath = ".//*[contains(@class, 'filter')]//following::input[1]")
    WebElement searchBox;

    @Autowired
    public AllProducts(WebDriver driver) {
        super(driver);
    }

    public boolean isHeadingCorrect(String expectedHeadings) {
        WaitUtils.waitForElementToBeClickable(driver, By.xpath(".//h2[.='" + expectedHeadings + "']") , 10, false);
        WebElement heading = driver.findElement(By.xpath(".//h2[.='" + expectedHeadings + "']"));
        boolean contains = heading.getText().contains(expectedHeadings);
        return contains;
    }

    public boolean isItemsDisplayed(String expectedHeadings) {
        boolean itemsDisplayed = false;
        WaitUtils.waitForElementToBeClickable(driver, By.xpath(".//h2[.='" + expectedHeadings + "']") , 10, false);

        if(expectedHeadings.contains("All Products")){
            itemsDisplayed = listOfAllProducts.size() > 0;
        }

        return itemsDisplayed;
    }

    public List<String> isTableColumnCorrect(String[] columns) {
        WaitUtils.waitForElementToBeClickable(driver, By.xpath(".//table//th") , TIMEOUT_DEFAULT, false);
        List<String> columnsNotFound = PageUtils.areTheColumnsCorrect(columns, listOfTableColumns);
        return columnsNotFound;
    }

    public AllProducts searchForAllProducts(String searchTerm) {
        WaitUtils.waitForElementToBeClickable(driver, searchBox, TIMEOUT_DEFAULT, false);
        PageUtils.searchPageFor(searchTerm, searchBox);
        return new AllProducts(driver);
    }

    public boolean atLeast1MatchFound(String searchText) {
        boolean atLeast1MatchFound = true;
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        WaitUtils.waitForElementToBeClickable(driver, By.cssSelector(".appian-informationPanel b"), TIMEOUT_40_SECOND, false);
        try{
            //WaitUtils.waitForElementToBeClickable(driver, By.partialLinkText(searchText), TIMEOUT_5_SECOND, false);
            int actualCount = (listOfAllProducts.size()-1)/2;
            atLeast1MatchFound = actualCount >= 1;
        }catch (Exception e){
            log.error("Timeout : Trying to search");
            atLeast1MatchFound = false;
        }

        return atLeast1MatchFound;
    }

    public String getARandomProductEntry() {
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        WaitUtils.waitForElementToBeClickable(driver, By.xpath(".//td[6]"), TIMEOUT_5_SECOND, false);

        int position = RandomDataUtils.getSimpleRandomNumberBetween(1, listOfAllManufacturerNames.size() - 1, false);
        WebElement manufacaturerNameLink = listOfAllManufacturerNames.get(position);
        String manufacturerName = manufacaturerNameLink.getText();
        return manufacturerName;
    }

    public BusinessManufacturerDetails viewManufacturerByText(String searchTerm) {
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        WaitUtils.waitForElementToBeClickable(driver, By.xpath(".//td[6]"), TIMEOUT_5_SECOND, false);
        int position = RandomDataUtils.getSimpleRandomNumberBetween(1, listOfAllManufacturerNames.size() - 1, false);
        WebElement manufacaturerNameLink = listOfAllManufacturerNames.get(position);
        manufacaturerNameLink = manufacaturerNameLink.findElement(By.tagName("a"));
        PageUtils.doubleClick(driver, manufacaturerNameLink);
        return new BusinessManufacturerDetails(driver);
    }

    public BusinessProductDetails viewProductBy(String tableHeading) {
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        WaitUtils.waitForElementToBeClickable(driver, By.xpath(".//td[4]"), TIMEOUT_5_SECOND, false);

        List<WebElement> listOfElement = getListOfElement(tableHeading);
        WebElement found = null;
        int count = 0;
        do {
            int position = RandomDataUtils.getSimpleRandomNumberBetween(1, listOfElement.size() - 1, false);
            WebElement product = listOfElement.get(position);
            product = product.findElement(By.tagName("a"));
            boolean isProductNameEmpty = product.getText().trim().equals("");
            if(!isProductNameEmpty){
                found = product;
                break;
            }
            count++;
        }while(found==null && count < 5);

        if(found!=null){
            found.click();
        }
        return new BusinessProductDetails(driver);
    }

    private List<WebElement> getListOfElement(String tableHeading) {
        if(tableHeading.equals("Product Name"))
            return listOfAllProductNames;
        else if(tableHeading.equals("Manufacturer")){
            return listOfAllManufacturerNames;
        }
        return null;
    }
}
