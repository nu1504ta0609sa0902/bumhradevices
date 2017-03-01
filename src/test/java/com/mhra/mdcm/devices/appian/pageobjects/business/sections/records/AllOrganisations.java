package com.mhra.mdcm.devices.appian.pageobjects.business.sections.records;

import com.mhra.mdcm.devices.appian.pageobjects._Page;
import com.mhra.mdcm.devices.appian.utils.selenium.others.RandomDataUtils;
import com.mhra.mdcm.devices.appian.utils.selenium.page.PageUtils;
import com.mhra.mdcm.devices.appian.utils.selenium.page.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
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
public class AllOrganisations extends _Page {

    @FindBy(xpath = ".//h2[.='Status']//following::tr")
    List<WebElement> listOfAllOrganisations;
    @FindBy(xpath = ".//td[1]")
    List<WebElement> listOfAllOrganisationsNames;
    @FindBy(xpath = ".//table//th")
    List<WebElement> listOfTableColumns;
    @FindBy(xpath = ".//*[.='Status']//following::tr//td[2]")
    List<WebElement> listOfOrganisationRoles;

    //TABLE Heading
    @FindBy(linkText = "Name")
    WebElement thOrganisationName;

    //Search box and filters
    @FindBy(xpath = ".//*[contains(@class, 'filter')]//following::input[1]")
    WebElement searchBox;
    @FindBy(css = ".selected")
    List<WebElement> listOfFilters;

    @Autowired
    public AllOrganisations(WebDriver driver) {
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

        if(expectedHeadings.contains("All Organisations")){
            itemsDisplayed = listOfAllOrganisations.size() > 0;
        }
        return itemsDisplayed;
    }

    public List<String> isTableColumnCorrect(String[] columns) {
        WaitUtils.waitForElementToBeClickable(driver, By.xpath(".//table//th") , TIMEOUT_DEFAULT, false);
        List<String> columnsNotFound = PageUtils.areTheColumnsCorrect(columns, listOfTableColumns);
        return columnsNotFound;
    }

    public String getRandomOrganisation(boolean exists) {
        String name = RandomDataUtils.getRandomTestName("NonExistingOrg");

        //Search for an existing name
        if(exists){
            WaitUtils.waitForElementToBeClickable(driver, By.partialLinkText("Manufacturer") , TIMEOUT_DEFAULT, false);
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

    public AllOrganisations searchForAllOrganisation(String searchTerm) {
        WaitUtils.waitForElementToBeClickable(driver, searchBox, TIMEOUT_DEFAULT, false);
        PageUtils.searchPageFor(searchTerm, searchBox);
        return new AllOrganisations(driver);
    }

    /**
     * By default heading contains an anchor, therefore we should do -1
     * @return
     */
    public int getNumberOfMatches() {
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        WaitUtils.waitForPageToLoad(driver, By.xpath("WaitForPageToLoad") , TIMEOUT_5_SECOND, false);
        WaitUtils.waitForElementToBeClickable(driver, By.partialLinkText("Manufacturer") , TIMEOUT_DEFAULT, false);
        int size = listOfAllOrganisations.size();
        size = (size-1);
        return size;
    }

    public boolean isOrderedAtoZ() {
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        WaitUtils.waitForElementToBeClickable(driver, By.xpath(".//h2[.='Status']//following::a[2]"), TIMEOUT_5_SECOND, false);
        boolean isOrderedAToZ = PageUtils.isOrderedAtoZ(listOfAllOrganisationsNames, 1);
        return isOrderedAToZ;
    }

    public AllOrganisations filterBy(String organisationRole) {

        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        By by = By.partialLinkText(organisationRole);
        WaitUtils.waitForElementToBeClickable(driver, by, TIMEOUT_10_SECOND, false);
        WebElement element = driver.findElement(by);
        PageUtils.doubleClick(driver, element);

        return new AllOrganisations(driver);
    }


    public AllOrganisations sortBy(String tableHeading, int numberOfTimesToClick) {
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        if (tableHeading.equals("Name")) {
            for (int c = 0; c < numberOfTimesToClick; c++) {
                WaitUtils.waitForElementToBeClickable(driver, thOrganisationName, TIMEOUT_DEFAULT, false);
                thOrganisationName.click();
                WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
                //WaitUtils.nativeWaitInSeconds(2);
            }
        }

        return new AllOrganisations(driver);
    }


    public boolean areAllOrganisationRoleOfType(String organisationType) {
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        boolean allMatched = true;
        for(WebElement el: listOfOrganisationRoles){
            String text = el.getText();
            log.info(text);
            allMatched = text.contains(organisationType);
            if(!allMatched){
                break;
            }
        }

        return allMatched;
    }


    public boolean atLeast1MatchFound(String searchText) {
        boolean atLeast1MatchFound = true;
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        WaitUtils.waitForElementToBeClickable(driver, By.cssSelector(".appian-informationPanel b"), TIMEOUT_40_SECOND, false);
        try{
            //WaitUtils.waitForElementToBeClickable(driver, By.partialLinkText(searchText), TIMEOUT_5_SECOND, false);
            int actualCount = (listOfAllOrganisations.size()-1);
            atLeast1MatchFound = actualCount >= 1;
        }catch (Exception e){
            log.error("Timeout : Trying to search");
            atLeast1MatchFound = false;
        }

        return atLeast1MatchFound;
    }

    public AllOrganisations clearFilterByOrganisation() {
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        WaitUtils.waitForElementToBeClickable(driver, listOfFilters.get(0), TIMEOUT_3_SECOND, false);
        listOfFilters.get(0).click();
        return new AllOrganisations(driver);
    }


    public boolean areOrganisationOfRoleVisible(String organisationType) {
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
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
}
