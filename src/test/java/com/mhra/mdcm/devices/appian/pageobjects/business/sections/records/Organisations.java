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
public class Organisations extends _Page {

    @FindBy(xpath = ".//th[@abbr='Status']//following::tr")
    List<WebElement> listOfAllOrganisations;
    @FindBy(xpath = ".//td[1]")
    List<WebElement> listOfAllOrganisationsNames;
    @FindBy(xpath = ".//td[2]")
    List<WebElement> listOfOrganisationRoles;
    @FindBy(xpath = ".//td[6]")
    List<WebElement> listOfAllStatus;

    //TABLE Heading
    @FindBy(xpath = ".//th[1]")
    WebElement thOrganisationName;
    @FindBy(xpath = ".//table//th")
    List<WebElement> listOfTableHeadings;

    //Search box and filters
    @FindBy(xpath = ".//*[contains(@class, 'filter')]//following::input[1]")
    WebElement searchBox;
    @FindBy(xpath = ".//span[@class='DropdownWidget---inline_label']")
    List<WebElement> listOfDropDownFilters;
    @FindBy(linkText = "Clear Filters")
    WebElement clearFilters;
    @FindBy(xpath = ".//button[contains(text(),'Search')]")
    WebElement btnSearch;

    @Autowired
    public Organisations(WebDriver driver) {
        super(driver);
    }

    public boolean isHeadingCorrect(String expectedHeadings) {
        By by = By.xpath(".//h1[.='" + expectedHeadings + "']");
        WaitUtils.waitForElementToBeClickable(driver, by, TIMEOUT_DEFAULT);
        WebElement heading = driver.findElement(by);
        boolean contains = heading.getText().contains(expectedHeadings);
        return contains;
    }

    public boolean isItemsDisplayed(String expectedHeadings) {
        boolean itemsDisplayed = false;
        WaitUtils.waitForElementToBeClickable(driver, By.xpath(".//h1[.='" + expectedHeadings + "']") , TIMEOUT_DEFAULT);

        if(expectedHeadings.contains(PageHeaders.PAGE_HEADERS_ORGANISATIONS.header)){
            itemsDisplayed = listOfAllOrganisations.size() > 0;
        }
        return itemsDisplayed;
    }

    public List<String> isTableColumnCorrect(String[] columns) {
        WaitUtils.waitForElementToBeClickable(driver, By.xpath(".//table//th") , TIMEOUT_DEFAULT);
        List<String> columnsNotFound = PageUtils.areTheColumnsCorrect(columns, listOfTableHeadings);
        return columnsNotFound;
    }

    public String getRandomOrganisation(boolean exists) {
        String name = RandomDataUtils.getRandomTestName("NonExistingOrg");

        //Search for an existing name
        if(exists){
            WaitUtils.waitForElementToBeClickable(driver, By.partialLinkText("Manufacturer") , TIMEOUT_DEFAULT);
            boolean found = false;
            do {
                int randomNumberBetween = RandomDataUtils.getSimpleRandomNumberBetween(0, listOfAllOrganisationsNames.size()-1, false);
                WebElement element = listOfAllOrganisationsNames.get(randomNumberBetween);
                String orgName = element.getText();
                if(orgName!=null && !orgName.trim().equals("")){
                    name = orgName;
                    found = true;
                }
            }while(!found);

        }
        return name;
    }

    public Organisations searchForAllOrganisation(String searchTerm) {
        WaitUtils.waitForElementToBeClickable(driver, searchBox, TIMEOUT_DEFAULT);
        PageUtils.searchPageFor(searchTerm, searchBox);
        return new Organisations(driver);
    }

    /**
     * By default heading contains an anchor, therefore we should do -1
     * @return
     */
    public int getNumberOfMatches() {
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        WaitUtils.waitForElementToBeClickable(driver, By.xpath(".//th[@abbr='Status']//following::tr") , TIMEOUT_DEFAULT);
        WaitUtils.waitForElementToBeClickable(driver, clearFilters , TIMEOUT_DEFAULT);
        int size = listOfAllOrganisations.size();
        return size;
    }

    public boolean isOrderedAtoZ() {
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        WaitUtils.waitForElementToBeClickable(driver, By.xpath(".//td[1]"), TIMEOUT_5_SECOND);
        boolean isOrderedAToZ = PageUtils.isOrderedAtoZ(listOfAllOrganisationsNames, 1);
        return isOrderedAToZ;
    }

    public Organisations filterBy(String organisationRole) {
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        PageUtils.selectFromDropDown(driver, listOfDropDownFilters.get(0) , organisationRole, false);
        return new Organisations(driver);
    }


    public Organisations sortBy(String tableHeading, int numberOfTimesToClick) {
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        if (tableHeading.equals("Name")) {
            for (int c = 0; c < numberOfTimesToClick; c++) {
                WaitUtils.waitForElementToBeClickable(driver, thOrganisationName, TIMEOUT_10_SECOND);
                thOrganisationName.click();
                WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
            }
        }

        return new Organisations(driver);
    }


    public boolean areAllOrganisationRoleOfType(String organisationType) {
        organisationType = organisationType.toLowerCase();
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        WaitUtils.waitForElementToBeClickable(driver, clearFilters, TIMEOUT_10_SECOND);
        WaitUtils.waitForElementToBeClickable(driver, listOfOrganisationRoles.get(0), TIMEOUT_10_SECOND);
        WaitUtils.nativeWaitInSeconds(5);

        boolean allMatched = true;
        for(WebElement el: listOfOrganisationRoles){
            String text = el.getText().toLowerCase();
            //log.info(text);
            if(!text.contains("revious") && !text.contains("ext")) {
                allMatched = text.contains(organisationType) || text.equals("");;
                if (!allMatched) {
                    break;
                }
            }
        }

        return allMatched;
    }


    public boolean atLeast1MatchFound(String searchText) {
        boolean atLeast1MatchFound = true;
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        WaitUtils.waitForElementToBeClickable(driver, clearFilters, TIMEOUT_10_SECOND);
        try{
            //WaitUtils.waitForElementToBeClickable(driver, By.partialLinkText(searchText), TIMEOUT_5_SECOND);
            int actualCount = (listOfAllOrganisations.size()-1);
            atLeast1MatchFound = actualCount >= 1;
        }catch (Exception e){
            log.error("Timeout : Trying to search");
            atLeast1MatchFound = false;
        }

        return atLeast1MatchFound;
    }

    public Organisations clearFilterByOrganisation() {
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        WaitUtils.waitForElementToBeClickable(driver, clearFilters, TIMEOUT_3_SECOND);
        clearFilters.click();
        return new Organisations(driver);
    }


    public boolean areOrganisationOfRoleVisible(String organisationType, String searchTerm) {
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        WebElement element = clearFilters;
        if(searchTerm == null)
            element = btnSearch;
        WaitUtils.waitForElementToBeClickable(driver, element, TIMEOUT_10_SECOND);

        boolean aMatchFound = false;
        for(WebElement el: listOfOrganisationRoles){
            String text = el.getText();
            log.info(text);
            aMatchFound = text.contains(organisationType);
            if (aMatchFound) {
                break;
            }
        }
        return aMatchFound;
    }

    public boolean areAllStatusOfType(String value) {
        value = value.toLowerCase();
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        WaitUtils.waitForElementToBeClickable(driver, clearFilters, TIMEOUT_10_SECOND);
        WaitUtils.waitForElementToBeClickable(driver, listOfAllStatus.get(0), TIMEOUT_10_SECOND);
        WaitUtils.nativeWaitInSeconds(5);

        boolean allMatched = true;
        for(WebElement el: listOfAllStatus){
            String text = el.getText().toLowerCase();
            //log.info(text);
            if(!text.contains("revious") && !text.contains("ext")) {
                allMatched = text.equals(value);
                if (!allMatched) {
                    break;
                }
            }
        }

        return allMatched;
    }

    public boolean areStatusOfTypeVisible(String status, String searchTerm) {
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);

        WebElement element = clearFilters;
        if(searchTerm == null)
            element = btnSearch;
        WaitUtils.waitForElementToBeClickable(driver, element, TIMEOUT_10_SECOND);

        boolean aMatchFound = false;
        for(WebElement el: listOfAllStatus){
            String text = el.getText();
            log.info(text);
            if(!text.contains("revious") && !text.contains("ext")) {
                //Status fields contains various fields
                aMatchFound = text.contains(status) || text.equals("");
                if (aMatchFound) {
                    break;
                }
            }
        }
        return aMatchFound;
    }

    public Organisations clearFilterByStatus() {
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        WaitUtils.waitForElementToBeClickable(driver, clearFilters, TIMEOUT_3_SECOND);
        clearFilters.click();
        return new Organisations(driver);
    }

    public BusinessManufacturerDetails viewManufacturerByText(String searchTerm) {
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        WaitUtils.waitForElementToBeClickable(driver, clearFilters, TIMEOUT_30_SECOND);
        WebElement manufacturer = null;
        boolean found = false;
        do {
            int randomNumberBetween = RandomDataUtils.getSimpleRandomNumberBetween(0, listOfAllOrganisationsNames.size()-1, false);
            if(listOfAllOrganisationsNames.size() == 1)
                randomNumberBetween = 0;
            WebElement element = listOfAllOrganisationsNames.get(randomNumberBetween);
            String orgName = element.getText();
            if(orgName.contains(searchTerm)){
                manufacturer = element.findElement(By.tagName("a"));
                found = true;
            }
        }while(!found);

        //Click the manufacturer name
        if(manufacturer!=null) {
            PageUtils.doubleClick(driver, manufacturer);
            //manufacturer.click();
        }else {
            throw new RuntimeException("No manufacturer found for search term : " + searchTerm);
        }

        return new BusinessManufacturerDetails(driver);
    }

    public boolean isSearchingCompleted(String searchTerm) {
        boolean seachingCompleted = false;
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        try {
            if(searchTerm!=null && !searchTerm.equals(""))
            WaitUtils.waitForElementToBeClickable(driver, clearFilters, TIMEOUT_30_SECOND);
            seachingCompleted = true;
        }catch (Exception e){}
        return seachingCompleted;
    }
}
