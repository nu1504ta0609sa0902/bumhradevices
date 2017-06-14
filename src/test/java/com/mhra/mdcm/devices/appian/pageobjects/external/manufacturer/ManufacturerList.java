package com.mhra.mdcm.devices.appian.pageobjects.external.manufacturer;

import com.mhra.mdcm.devices.appian.pageobjects._Page;
import com.mhra.mdcm.devices.appian.pageobjects.external._CreateManufacturerTestHarnessPage;
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
public class ManufacturerList extends _Page {

    //Register new manufacturer buttons
    @FindBy(xpath = ".//button[contains(text(), 'Register new manufacturer')]")
    WebElement linkRegisterNewManufacturer;
    @FindBy(xpath = ".//button[.='Register My Organisation']")
    WebElement linkRegisterMyNewOrganisation;

    //Registered or completed manufactureres
    @FindBy(xpath = ".//h2[contains(text(),'Manufacturer')]//following::tbody[1]/tr/td[1]")
    List<WebElement> listOfManufacturerNames;
    @FindBy(xpath = ".//h2[contains(text(),'Manufacturer')]//following::tbody[1]/tr/td[4]")
    List<WebElement> listOfManufacturerStatuses;
    @FindBy(xpath = ".//h2[contains(text(),'Manufacturer')]//following::tbody[1]/tr")
    List<WebElement> listOfManufacturerRows;
    @FindBy(xpath = ".//*[contains(text(), 'egistration status')]")
    WebElement thManufacturerRegistrationStatus;

    //Registration in progress table
    @FindBy(xpath = ".//*[contains(text(),'Applications')]//following::tbody[1]/tr/td[3]")
    List<WebElement> listOfManufacturerNamesInProgress;

    //
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

    @FindBy(xpath = ".//tr/th")
    List<WebElement> listOfTableHeadings;
    @FindBy(xpath = ".//tr/th")
    WebElement singleTableHeader;


    @Autowired
    public ManufacturerList(WebDriver driver) {
        super(driver);
    }


    public ManufacturerViewDetails viewAManufacturer(String manufacturerName) {
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        if(manufacturerName == null){
            //Than view a random one
            int index = RandomDataUtils.getNumberBetween(0, listOfManufacturerNames.size() - 1);
            WebElement link = listOfManufacturerNames.get(index);
            link.click();
        }else{
            WaitUtils.waitForElementToBeClickable(driver, By.partialLinkText(manufacturerName), TIMEOUT_10_SECOND);
            WebElement man = driver.findElement(By.partialLinkText(manufacturerName));
            man.click();
        }
        return new ManufacturerViewDetails(driver);
    }

    public String getARandomManufacturerName() {
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        WaitUtils.waitForElementToBeClickable(driver, By.cssSelector(".elements---global_p .elements---global_a"), TIMEOUT_5_SECOND);
        WaitUtils.waitForElementToBeClickable(driver, By.xpath(".//h2[contains(text(),'Manufacturer')]//following::tbody[1]/tr"), TIMEOUT_15_SECOND);
        int index = RandomDataUtils.getNumberBetween(0, listOfManufacturerNames.size() - 1);
        WebElement link = listOfManufacturerNames.get(index);
        String name = link.getText();
        return name;
    }

    public boolean isManufacturerDisplayedInList(String manufacturerName){
        //WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        boolean found = false;
        try {
            WaitUtils.waitForElementToBeClickable(driver, By.cssSelector(".elements---global_p .elements---global_a"), TIMEOUT_10_SECOND);
            WaitUtils.nativeWaitInSeconds(2);
            for (WebElement item : listOfManufacturerNames) {
                String name = item.getText();
                if (name.contains(manufacturerName)) {
                    found = true;
                    break;
                }
            }
        }catch (Exception e){
            //If no manufacturers in the list than return true
            if(listOfManufacturerNames.size() == 0){
                found = true;
            }
        }
        return found;
    }


    public ManufacturerList clickNext(){
        WaitUtils.waitForElementToBeClickable(driver, nextPage, TIMEOUT_5_SECOND);
        nextPage.click();
        return new ManufacturerList(driver);
    }

    public ManufacturerList clickPrev(){
        WaitUtils.waitForElementToBeClickable(driver, prevPage, TIMEOUT_5_SECOND);
        prevPage.click();
        return new ManufacturerList(driver);
    }

    public ManufacturerList clickLastPage(){
        WaitUtils.waitForElementToBeClickable(driver, lastPage, TIMEOUT_5_SECOND);
        lastPage.click();
        return new ManufacturerList(driver);
    }


    public _CreateManufacturerTestHarnessPage registerNewManufacturer() {
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        WaitUtils.waitForElementToBeClickable(driver, linkRegisterNewManufacturer, TIMEOUT_DEFAULT);
        linkRegisterNewManufacturer.click();
        return new _CreateManufacturerTestHarnessPage(driver);
    }

    public _CreateManufacturerTestHarnessPage registerMyOrganisation() {
        WaitUtils.waitForElementToBeClickable(driver, linkRegisterMyNewOrganisation, TIMEOUT_DEFAULT);
        linkRegisterMyNewOrganisation.click();
        return new _CreateManufacturerTestHarnessPage(driver);
    }

    public int getNumberOfPages(int whichPagination) {
        int index = whichPagination - 1;
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        WaitUtils.waitForElementToBeClickable(driver, linkRegisterNewManufacturer, TIMEOUT_10_SECOND);
        boolean isPaginationDisplayed = isPaginationDisplayed(index);

        if(isPaginationDisplayed) {
            itemCount = itemCounts.get(index);
            WaitUtils.waitForElementToBeClickable(driver, itemCount, TIMEOUT_5_SECOND);
            try {
                String text = itemCount.getText();
                String total = text.substring(text.indexOf("of") + 3);
                String itemPerPage = text.substring(text.indexOf("\n") + 3, text.indexOf(" of "));

                int tt = Integer.parseInt(total.trim().replace(",",""));
                int noi = Integer.parseInt(itemPerPage.trim());

                int reminder = tt % noi;
                int numberOfPage = (tt / noi) - 1;
                if (reminder > 0) {
                    numberOfPage++;
                }

                return numberOfPage;
            } catch (Exception e) {
                return 0;
            }
        }else{
            return 0;
        }
    }

    private boolean isPaginationDisplayed(int index) {
        boolean isDisplayed = true;
        try{
            itemCount = itemCounts.get(index);
            WaitUtils.waitForElementToBeClickable(driver, By.cssSelector(".elements---global_p .elements---global_a"), TIMEOUT_30_SECOND);
            WaitUtils.waitForElementToBeVisible(driver, itemCount, TIMEOUT_10_SECOND);
        }catch (Exception e){
            isDisplayed = false;
        }
        return isDisplayed;
    }

    public String getRegistrationStatus(String name) {
        String registered = "";
        for(WebElement tr: listOfManufacturerRows){
            try {
                //WebElement link = tr.findElement(By.partialLinkText(name));
                registered = tr.findElement(By.xpath("td[4]")).getText();
                String n = tr.findElement(By.xpath("td[1]")).getText();

                if(n.contains(name)){
                    break;
                }
            }catch (Exception ex){}
        }
        return registered;
    }


    public ManufacturerList sortBy(String sortBy, int numberOfTimesToClick) {
        WaitUtils.waitForElementToBeClickable(driver, thManufacturerRegistrationStatus, TIMEOUT_DEFAULT);
        if(sortBy.equals("Registration status")){
            for(int c = 0; c < numberOfTimesToClick; c++) {
                thManufacturerRegistrationStatus.click();
                WaitUtils.waitForElementToBeClickable(driver, thManufacturerRegistrationStatus, TIMEOUT_DEFAULT);
            }
        }

        return new ManufacturerList(driver);
    }

    public String getOrganisationCountry(String name) {
        String country = "";
        for(WebElement tr: listOfManufacturerRows){
            try {
                //WebElement link = tr.findElement(By.partialLinkText(name));
                country = tr.findElement(By.xpath("td[3]")).getText();
                String n = tr.findElement(By.xpath("td[1]")).getText();
                if(n.contains(name)){
                    break;
                }
            }catch (Exception ex){}
        }
        return country;
    }

    public String getARandomManufacturerNameWithStatus(String status) {
        String name = null;
        boolean found = false;
        int attempts = 1;
        do {
            name = getARandomManufacturerName();
            String registered = getRegistrationStatus(name);
            if(registered.toLowerCase().equals(status.toLowerCase())){
                found = true;
            }else{
                name = null;
            }
            attempts++;
        }while(attempts < 10 && !found);

        return name;
    }

    public boolean isManufacturerLinkDisplayed(String name) {
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        boolean found = true;
        try {
            WaitUtils.waitForElementToBeClickable(driver, By.cssSelector(".elements---global_p .elements---global_a"), TIMEOUT_30_SECOND);
            WaitUtils.waitForElementToBeClickable(driver, By.partialLinkText(name), TIMEOUT_5_SECOND);
        }catch (Exception e){
            found = false;
        }
        return found;
    }

    public boolean isTableHeadingCorrect(String commaDelimitedHeading) {
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        WaitUtils.waitForElementToBeVisible(driver, singleTableHeader, TIMEOUT_10_SECOND);
        String lowerCaseHeadings = commaDelimitedHeading.toLowerCase();
        return PageUtils.isTableHeadingCorrect(lowerCaseHeadings, listOfTableHeadings, 1, 4);
    }

    public boolean isSpecificTableHeadingCorrect(String commaDelimitedHeading) {
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        WaitUtils.waitForElementToBeVisible(driver, linkRegisterNewManufacturer, TIMEOUT_15_SECOND);
        WaitUtils.waitForElementToBeClickable(driver,linkRegisterNewManufacturer, TIMEOUT_5_SECOND);
        String lowerCaseHeadings = commaDelimitedHeading.toLowerCase();
        return PageUtils.isSpecificTableHeadingCorrect(lowerCaseHeadings, listOfTableHeadings);
    }

    public boolean isManufacturerLinkDisplayedOnInProgressTable(String name) {
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        WaitUtils.waitForElementToBeVisible(driver, linkRegisterNewManufacturer, TIMEOUT_15_SECOND);
        boolean found = false;
        for(WebElement manufacturer: listOfManufacturerNamesInProgress){
            String manName = manufacturer.getText();
            if(manName.contains(name)){
                found = true;
                break;
            }
        }

        return found;
    }

    public boolean isRegistraionInProgressDisplayingManufacturer(String manufacturerName) {
        //WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        boolean found = false;
        try {
            WaitUtils.waitForElementToBeClickable(driver, By.cssSelector(".elements---global_p .elements---global_a"), TIMEOUT_10_SECOND);
            WaitUtils.nativeWaitInSeconds(2);
            for (WebElement item : listOfManufacturerNamesInProgress) {
                String name = item.getText();
                if (name.contains(manufacturerName)) {
                    found = true;
                    break;
                }
            }
        }catch (Exception e){
            //If no manufacturers in the list than return true
            if(listOfManufacturerNamesInProgress.size() == 0){
                found = true;
            }
        }
        return found;
    }
}
