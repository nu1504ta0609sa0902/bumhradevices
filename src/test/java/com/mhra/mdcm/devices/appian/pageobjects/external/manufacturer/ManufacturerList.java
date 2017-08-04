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
    WebElement btnRegisterNewManufacturer;
    @FindBy(xpath = ".//button[contains(text(), 'Add new manufacturer')]")
    WebElement btnAddNewManufacturer;
    @FindBy(xpath = ".//button[.='Register My Organisation']")
    WebElement btnRegisterMyNewOrganisation;
    @FindBy(partialLinkText = "Click here")
    WebElement linkClickHere;

    //Registered or completed manufactureres
    @FindBy(xpath = ".//h2[contains(text(),'Your Organisation')]//following::tbody[1]/tr/td[1]")
    WebElement yourOrganisation;
    @FindBy(xpath = ".//h2[contains(text(),'Manufacturer')]//following::tbody[1]/tr/td[1]")
    List<WebElement> listOfManufacturerNames;
    @FindBy(xpath = ".//h2[contains(text(),'Manufacturer')]//following::tbody[1]/tr/td[4]")
    List<WebElement> listOfManufacturerStatuses;
    @FindBy(xpath = ".//h2[contains(text(),'Manufacturers you represent')]//following::tbody[1]/tr")
    List<WebElement> listOfManufacturerRows;
    @FindBy(xpath = ".//*[contains(text(), 'Manufacturers you represent')]/following::th[@abbr='Registration Status']")
    WebElement thManufacturerRegistrationStatus;
    @FindBy(xpath = ".//h2[contains(text(),'Manufacturer')]//following::tbody[1]//img[@alt='Registered']")
    List<WebElement> listOfRegisteredManufacturerNames;
    @FindBy(xpath = ".//h2[contains(text(),'Manufacturer')]//following::tbody[1]//img[@alt='Not Registered']")
    List<WebElement> listOfNotRegisteredManufacturerNames;

    //Registration in progress table
    @FindBy(xpath = ".//*[contains(text(),'Applications')]//following::tbody[1]/tr/td[3]")
    List<WebElement> listOfManufacturerNamesInProgress;
    @FindBy(xpath = ".//*[contains(text(),'Applications')]//following::tbody[1]/tr/td[3]")
    WebElement manufacturerInProgress;
    @FindBy(css = ".elements---global_p .elements---global_a")
    WebElement manufacturerName;

    //Search box
    @FindBy(xpath = ".//*[contains(text(),'Search by manufacturer')]//following::input[1]")
    WebElement searchBox;
    @FindBy(xpath = ".//button[.='Search']")
    WebElement btnSearch;

    //Pagination
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

    //Draft manufacturer applications page
    @FindBy(partialLinkText = "pplications ")
    WebElement linkDraftApplications;
    @FindBy(partialLinkText = "TEMP201")
    WebElement anApplication;


    @Autowired
    public ManufacturerList(WebDriver driver) {
        super(driver);
    }


    public ManufacturerDetails viewAManufacturer(String manufacturerName) {
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
        return new ManufacturerDetails(driver);
    }

    public String getARandomManufacturerName() {
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        WaitUtils.waitForElementToBeClickable(driver, manufacturerName, TIMEOUT_5_SECOND);
        WaitUtils.waitForElementToBeClickable(driver, By.xpath(".//h2[contains(text(),'Manufacturer')]//following::tbody[1]/tr"), TIMEOUT_15_SECOND);
        int index = RandomDataUtils.getNumberBetween(0, listOfManufacturerNames.size() - 1);
        WebElement link = listOfManufacturerNames.get(index);
        String name = link.getText();
        return name;
    }

    public String getARandomManufacturerName(String status) {
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        WaitUtils.waitForElementToBeClickable(driver, manufacturerName, TIMEOUT_5_SECOND);
        WaitUtils.waitForElementToBeClickable(driver, By.xpath(".//*[contains(text(),'Search by manufacturer')]/following::tbody[1]//img"), TIMEOUT_15_SECOND);

        List<WebElement> loi = null;
        if(status.toLowerCase().contains("not registered")){
            loi = listOfNotRegisteredManufacturerNames;
        }else{
            loi = listOfRegisteredManufacturerNames;
        }

        int index = RandomDataUtils.getNumberBetween(0, loi.size() - 1);
        WebElement link = listOfManufacturerNames.get(index);
        String name = link.getText();
        return name;
    }

    public boolean isManufacturerDisplayedInList(String manufacturerNameValue){
        //WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        boolean found = false;
        try {
            WaitUtils.waitForElementToBeClickable(driver, manufacturerName, TIMEOUT_10_SECOND);
            WaitUtils.nativeWaitInSeconds(2);
            for (WebElement item : listOfManufacturerNames) {
                String name = item.getText();
                if (name.contains(manufacturerNameValue)) {
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
        //WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        WaitUtils.waitForElementToBeClickable(driver, btnRegisterNewManufacturer, TIMEOUT_10_SECOND);
        btnRegisterNewManufacturer.click();
        return new _CreateManufacturerTestHarnessPage(driver);
    }

    public _CreateManufacturerTestHarnessPage addNewManufacturer() {
        //WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        WaitUtils.waitForElementToBeClickable(driver, btnAddNewManufacturer, TIMEOUT_10_SECOND);
        btnAddNewManufacturer.click();
        return new _CreateManufacturerTestHarnessPage(driver);
    }

    public _CreateManufacturerTestHarnessPage registerMyOrganisation() {
        WaitUtils.waitForElementToBeClickable(driver, btnRegisterMyNewOrganisation, TIMEOUT_10_SECOND);
        btnRegisterMyNewOrganisation.click();
        return new _CreateManufacturerTestHarnessPage(driver);
    }

    public int getNumberOfPages(int whichPagination) {
        int index = whichPagination - 1;
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        WaitUtils.waitForElementToBeClickable(driver, btnAddNewManufacturer, TIMEOUT_15_SECOND);
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
            WaitUtils.waitForElementToBeClickable(driver, manufacturerName , TIMEOUT_30_SECOND);
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
                registered = tr.findElement(By.xpath("td[4]/div/img")).getAttribute("alt");
                String n = tr.findElement(By.xpath("td[1]")).getText();

                if(n.contains(name)){
                    break;
                }
            }catch (Exception ex){}
        }
        return registered;
    }


    public ManufacturerList sortBy(String sortBy, int numberOfTimesToClick) {
        WaitUtils.waitForElementToBeClickable(driver, thManufacturerRegistrationStatus, TIMEOUT_10_SECOND);
        if(sortBy.equals("Registration status")){
            for(int c = 0; c < numberOfTimesToClick; c++) {
                thManufacturerRegistrationStatus.click();
                WaitUtils.waitForElementToBeClickable(driver, thManufacturerRegistrationStatus, TIMEOUT_5_SECOND);
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

    public String getARandomManufacturerNameWithStatus(String registeredStatus) {
        //WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        String name = null;
        boolean found = false;
        int attempts = 1;
        do {
            name = getARandomManufacturerName(registeredStatus);
            //String registered = getRegistrationStatus(name);
            //if(registered.toLowerCase().equals(status.toLowerCase())){
            //    found = true;
            //}else{
            //    name = null;
            //}
            attempts++;
        }while(attempts < 10 && !found);

        return name;
    }

    public boolean isManufacturerLinkDisplayed(String name) {
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        boolean found = true;
        try {
            WaitUtils.waitForElementToBeClickable(driver, manufacturerName, TIMEOUT_30_SECOND);
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
        // WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        WaitUtils.waitForElementToBeClickable(driver, btnAddNewManufacturer, TIMEOUT_15_SECOND);
        String lowerCaseHeadings = commaDelimitedHeading.toLowerCase();
        return PageUtils.isSpecificTableHeadingCorrect(lowerCaseHeadings, listOfTableHeadings);
    }

    public boolean isManufacturerLinkDisplayedOnInProgressTable(String name) {
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        WaitUtils.waitForElementToBeClickable(driver, manufacturerInProgress, TIMEOUT_15_SECOND);
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

    public boolean isRegistraionInProgressDisplayingManufacturer(String manufacturerNameValue) {
        //WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        boolean found = false;
        try {
            WaitUtils.waitForElementToBeClickable(driver,manufacturerName, TIMEOUT_10_SECOND);
            WaitUtils.nativeWaitInSeconds(2);
            for (WebElement item : listOfManufacturerNamesInProgress) {
                String name = item.getText();
                if (name.contains(manufacturerNameValue)) {
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

    public ManufacturerList clickOnLinkToDisplayManufacturers() {
        WaitUtils.waitForElementToBeVisible(driver, linkClickHere, TIMEOUT_15_SECOND);
        linkClickHere.click();
        return new ManufacturerList(driver);
    }

    public ManufacturerList searchForManufacturer(String name) {
        WaitUtils.waitForElementToBeVisible(driver, searchBox, TIMEOUT_15_SECOND);
        searchBox.sendKeys(name);
        btnSearch.click();
        return new ManufacturerList(driver);

    }
    public boolean isSearchingCompleted(WebDriver driver, int timeout) {
        return PageUtils.isElementNotVisible(driver, itemCount, timeout);
    }

    public boolean isYourOrganisationCorrect(String name) {
        WaitUtils.waitForElementToBeVisible(driver, yourOrganisation, TIMEOUT_15_SECOND);
        boolean isCorrect = yourOrganisation.getText().contains(name);
        return isCorrect;
    }

    public ManufacturerList gotoDraftApplications() {
        WaitUtils.waitForElementToBeClickable(driver, linkDraftApplications, TIMEOUT_10_SECOND);
        linkDraftApplications.click();
        return new ManufacturerList(driver);
    }

    /**
     * This should allow us to continue from where we saved the application
     *
     * So not sure which page it will be
     * @param name
     */
    public void viewADraftManufacturer(String name) {
        //There should be at least 1 match
        WaitUtils.waitForElementToBeClickable(driver, anApplication, TIMEOUT_10_SECOND);
        anApplication.click();
    }
}
