package com.mhra.mdcm.devices.appian.utils.selenium.page;


import com.mhra.mdcm.devices.appian.pageobjects._Page;
import com.mhra.mdcm.devices.appian.utils.selenium.others.FileUtils;
import com.mhra.mdcm.devices.appian.utils.selenium.others.RandomDataUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author TPD_Auto
 */
public class PageUtils {


    public static void selectByText(WebElement selectElement, String visibleText) {
        Select select = new Select(selectElement);
        select.selectByVisibleText(visibleText);
    }

    public static String getCurrentSelectedOption(WebElement selectElement) {
        Select select = new Select(selectElement);
        WebElement selectedOption = select.getFirstSelectedOption();
        String text = selectedOption.getText();
        return text;
    }


    public static List<String> getListOfOptions(WebElement selectElement) {
        Select select = new Select(selectElement);
        List<WebElement> options = select.getOptions();
        List<String> loo = new ArrayList<>();

        for(WebElement o: options){
            String text = o.getText();
            if(!text.contains("Please Select")){
                loo.add(text);
            }
        }
        return loo;
    }

    public static void selectByIndex(WebElement selectElement, String index) {
        Select select = new Select(selectElement);
        int i = Integer.parseInt(index);
        select.selectByIndex(i);
    }

    public static void clickOption(WebDriver driver, WebElement option,  boolean status) {
        if(status){
            clickIfVisible(driver, option);
            //option.click();
        }
    }

    public static void clickOption(WebElement option1, WebElement option2, boolean status) {
        if(status){
            option1.click();
        }else{
            option2.click();
        }
    }

    public static void clickOptionAdvanced(WebDriver driver, WebElement option1, WebElement option2, boolean status) {
        if(status){
            clickIfVisible(driver, option1);
        }else{
            clickIfVisible(driver, option2);
        }
    }

    public static void enterDate(WebDriver driver, WebElement element, String dateTxt) {
        //element.click();
        Actions actions = new Actions(driver);
        actions.moveToElement(element).click().sendKeys(dateTxt).build().perform();
    }

    public static void doubleClick(WebDriver driver, WebElement element) {
        Actions ac = new Actions(driver);
        ac.moveToElement(element).doubleClick(element).build().perform();
    }

    public static void singleClick(WebDriver driver, WebElement element) {
        Actions ac = new Actions(driver);
        ac.moveToElement(element).click(element).build().perform();
    }

    public static void clickIfVisible(WebDriver driver, WebElement element) {
        try{
            //IE sometimes doesn't click the element
            element.sendKeys(Keys.SPACE);
        }catch(Exception e){
            try {
                if (element.isDisplayed() && !element.isSelected()) {
                    Actions ac = new Actions(driver);
                    //ac.moveToElement(element).doubleClick(element).sendKeys(Keys.SPACE).build().perform();
                    ac.moveToElement(element).click(element).sendKeys(Keys.SPACE).build().perform();
                    //ac.moveToElement(element).sendKeys(Keys.SPACE).build().perform();
                }
            }catch(Exception e2){
            }
        }

        if(!element.isSelected()){
            WaitUtils.waitForElementToBeClickable(driver, element, _Page.TIMEOUT_3_SECOND);
            doubleClick(driver, element);
        }
    }

    public static void typeText(WebElement element, String text) {
        element.sendKeys(text);
    }

    public static void uploadDocument(WebElement element, String fileName, int timeWaitForItToBeClickable, int timeWaitForDocumentUploadToFinish){
        String fullPath = FileUtils.getFileFullPath("tmp" + File.separator + "data" + File.separator + "pdfs", fileName);
        WaitUtils.nativeWaitInSeconds(timeWaitForItToBeClickable);
        element.sendKeys(fullPath);
        //We will have to wait for uploading to finish
        WaitUtils.nativeWaitInSeconds(timeWaitForDocumentUploadToFinish);
    }

    public static WebElement getRandomElementFromList(List<WebElement> listOfECIDLinks) {
        String index = RandomDataUtils.getSimpleRandomNumberBetween(0, listOfECIDLinks.size() - 1);
        int i = Integer.parseInt(index);
        if(i < 0){
            i = 0;
        }
        WebElement element = listOfECIDLinks.get(i);
        return element;
    }



    public static String getText(WebElement element) {
        element.click();
        String existingName = element.getText();
        if(existingName.equals(""))
            existingName = element.getAttribute("value");
        return existingName;
    }



    public static void setBrowserZoom(WebDriver driver, String currentBrowser) {
        String selectedProfile = System.getProperty("current.browser");
        System.out.println(currentBrowser);
        if(currentBrowser!=null && currentBrowser.equals("ie")){
            Actions action = new Actions(driver);
            action.keyDown(Keys.CONTROL).sendKeys(String.valueOf(0)).perform();
        }
    }

    public static void acceptAlert(WebDriver driver, String accept, int timeToWait) {
        try {
            WaitUtils.waitForAlert(driver, timeToWait);
            boolean present = WaitUtils.isAlertPresent(driver);
            if (present) {
                if (accept.equals("accept")) {
                    driver.switchTo().alert().accept();
                } else {
                    driver.switchTo().alert().dismiss();
                }
            }
        }catch (Exception e){}
    }

    public static void acceptAlert(WebDriver driver, boolean accept, int timeToWait) {
        try {
            WaitUtils.waitForAlert(driver, timeToWait);
            boolean present = WaitUtils.isAlertPresent(driver);
            if (present) {
                if (accept) {
                    driver.switchTo().alert().accept();
                } else {
                    driver.switchTo().alert().dismiss();
                }
            }
        }catch (Exception e){}
    }

    public static boolean isCorrectPage(WebDriver driver, String ecid) {
        return driver.getTitle().contains(ecid);
    }


    public static void updateElementValue(WebDriver driver, WebElement element, String value, int timeOut) {
        WaitUtils.nativeWaitInSeconds(1);
        WaitUtils.waitForElementToBeClickable(driver, element, timeOut);
        element.clear();
        element.sendKeys(RandomDataUtils.generateTestNameStartingWith(value, 0));
    }

    public static boolean isDisplayed(WebDriver driver, WebElement manufacturerDropDown, int timeOut) {
        boolean isDisplayed = true;
        try{
            WaitUtils.waitForElementToBeClickable(driver, manufacturerDropDown, timeOut);
        }catch (Exception e){
            isDisplayed = false;
        }
        return isDisplayed;
    }

    public static void isValueCorrect(WebElement element, String value, List<String> listOfInvalidFields) {
        String text = element.getText();
        if(!text.contains(value)){
            listOfInvalidFields.add(value);
        }
    }

    public static WebElement findCorrectElement(List<WebElement> listOfElements, String productMake) {
        WebElement element = null;
        for(WebElement el: listOfElements){
            String txt = el.getText();
            if(txt.toLowerCase().contains(productMake.toLowerCase())){
                element = el;
                break;
            }
        }
        return element;
    }

    public static boolean isTableHeadingCorrect(String commaDelimitedHeading, List<WebElement> listOfTableHeadings) {

        String lowerCaseHeadings = commaDelimitedHeading.toLowerCase();
        //Get list of headings
        boolean allFound = true;
        for(WebElement el: listOfTableHeadings){
            String heading = el.getText().toLowerCase();
            System.out.println(heading);
            if(!lowerCaseHeadings.contains(heading)){
                allFound = false;
                break;
            }
        }
        return allFound;
    }

    public static boolean isTableHeadingCorrect(String commaDelimitedHeading, List<WebElement> listOfTableHeadings, int headingFrom, int headingTo) {

        String lowerCaseHeadings = commaDelimitedHeading.toLowerCase();
        //Get list of headings
        boolean allFound = true;
        int position = 1;
        for(WebElement el: listOfTableHeadings){
            if(position >= headingFrom && position <= headingTo) {
                String heading = el.getText().toLowerCase();
                System.out.println(heading);
                if (!lowerCaseHeadings.contains(heading)) {
                    allFound = false;
                    break;
                }
            }
            position++;
        }
        return allFound;
    }

    public static boolean isSpecificTableHeadingCorrect(String commaDelimitedHeading, List<WebElement> listOfTableHeadings) {
        String lowerCaseHeadings = commaDelimitedHeading.toLowerCase();
        String[] tableHeadings = lowerCaseHeadings.split(",");
        //Get list of headings
        String headings = "";
        for(WebElement el: listOfTableHeadings){
            String heading = getText(el).toLowerCase();
            headings = headings + "," + heading;
        }

        boolean found = true;
        for(String head: tableHeadings){
            found = headings.contains(head);
            if(!found){
                break;
            }
        }
        return found;
    }

    public static boolean isOrderedAtoZ(List<WebElement> listOfElements, int everyXItem) {
        int getFirstX = 20;
        int reminder = 1;
        if(everyXItem == 1){
            reminder = 0;
        }
        List<String> listOfOrderedOrganisations = new ArrayList<>();

        //Get list of account names
        int position = 0;   //Only even ones are organisation name
        int elementCount = 0;
        for(WebElement el: listOfElements){

            //At the moment only the even ones are organisation names
            if(position!=0 && position % everyXItem == reminder){
                String orgName = el.getText();

                //
                if(!orgName.equals("Next") && !orgName.equals("Previous"))
                    listOfOrderedOrganisations.add(orgName);
            }

            if(elementCount == (getFirstX*everyXItem)){  //Every 2nd link is an organisation name
                break;
            }

            elementCount++;
            position++;
        }

        //Check if a-Z
        String previous = "";
        for (final String current: listOfOrderedOrganisations) {
            //Its <=0 organisation should be unique
            if (!current.equals("") && !previous.equals("") && current.compareToIgnoreCase(previous) <= 0)
                return false;
            previous = current;
        }

        return true;
    }

    public static WebElement getElementMatchingText(List<WebElement> listOfWebElements, String textToMatch) {
        WebElement elementFound = null;
        for(WebElement el: listOfWebElements){
            String txt = el.getText();
            //System.out.println(txt);
            boolean contains = txt.contains(textToMatch);
            if(contains){
                elementFound = el;
                break;
            }
        }
        return elementFound;
    }

    public static boolean isTableDataContentCorrect(WebElement tr, int tableDataPosition, String textToMatch) {
        if(textToMatch!=null) {
            List<WebElement> tdElements = tr.findElements(By.tagName("td"));
            WebElement element = tdElements.get(tableDataPosition);
            String actualText = element.getText();
            boolean matched = actualText.contains(textToMatch);
            return matched;
        }else{
            return true;
        }
    }

    public static List<String> areTheColumnsCorrect(String[] columns, List<WebElement> listOfTableColumns) {
        List<String> listOfColumns = new ArrayList<>();
        for(WebElement el: listOfTableColumns){
            String text = el.getText();
            //System.out.println(text);
            if(text!=null){
                listOfColumns.add(text);
            }
        }

        //Verify columns matches expectation
        List<String> columnsNotFound = new ArrayList<>();
        for(String c: columns){
            c = c.trim();
            if(!listOfColumns.contains(c)){
                System.out.println("Column Not Found : " + c);
                columnsNotFound.add(c);
            }
        }

        return columnsNotFound;
    }

    public static void searchPageFor(String searchTerm, WebElement searchBox) {
        searchBox.clear();
        searchBox.sendKeys(searchTerm);
        searchBox.sendKeys(Keys.ENTER);
    }

    public static boolean isTableDataContentIsEmpty(WebElement tr, int tableDataPosition) {
        List<WebElement> tdElements = tr.findElements(By.tagName("td"));
        WebElement element = tdElements.get(tableDataPosition);
        String actualText = element.getText();
        boolean isEmpty = actualText.trim().equals("");
        return isEmpty;
    }

    public static void clickOnGMDNCodeOrDefinition(List<WebElement> listOfDevices, String gmdnTermOrDefinition) {
        WebElement linkToClick = null;
        for(WebElement tr: listOfDevices){
            try {
                linkToClick = tr.findElement(By.partialLinkText(gmdnTermOrDefinition));
                if (linkToClick != null) {
                    break;
                }
            }catch (Exception e){
                //Not found
            }
        }

        //If link doesn't exist than next step will fail
        if(linkToClick!=null){
            linkToClick.click();
        }

    }

    public static void selectFromAutoSuggestedListItemsManufacturers(WebDriver driver, String elementPath, String countryName, boolean throwException) throws Exception {
        boolean completed = true;
        int count = 0;
        do {
            try {

                count++;    //It will go forever without this
                WebElement country = driver.findElements(By.cssSelector(elementPath)).get(0);
                country.sendKeys(countryName);
                WaitUtils.nativeWaitInSeconds(1);
                WaitUtils.waitForElementToBeClickable(driver,By.cssSelector("li[role='option']") , _Page.TIMEOUT_5_SECOND);

                //Get list of options displayed
                WaitUtils.nativeWaitInSeconds(1);
                List<WebElement> countryOptions = driver.findElements(By.cssSelector("li[role='option']"));
                WebElement item = countryOptions.get(0);
                String text = item.getText();
                //System.out.println("country : " + text);

                if(text!=null && !text.contains("Searching")) {
                    PageUtils.singleClick(driver, item);
                    completed = true;
                }
            } catch (Exception e) {
                e.printStackTrace();
                completed = false;
                WaitUtils.nativeWaitInSeconds(1);
            }
        } while (!completed && count < 3);

        if (!completed && throwException) {
            throw new Exception("Country name not selected");
        }
    }


    public static void selectFromAutoSuggestedListItemsManufacturers(WebDriver driver, WebElement element, String countryName ) {
        boolean completed = true;
        int count = 0;
        do {
            try {

                count++;    //It will go forever without this
                WebElement country = element;
                country.sendKeys(countryName);
                WaitUtils.nativeWaitInSeconds(1);
                WaitUtils.waitForElementToBeClickable(driver,By.cssSelector("li[role='option']") , _Page.TIMEOUT_5_SECOND);

                //Get list of options displayed
                WaitUtils.nativeWaitInSeconds(1);
                List<WebElement> countryOptions = driver.findElements(By.cssSelector("li[role='option']"));
                WebElement item = countryOptions.get(0);
                String text = item.getText();
                //System.out.println("country : " + text);

                if(text!=null && !text.contains("Searching")) {
                    PageUtils.singleClick(driver, item);
                    completed = true;
                }
            } catch (Exception e) {
                completed = false;
                WaitUtils.nativeWaitInSeconds(1);
            }
        } while (!completed && count < 3);
    }


    public static void selectFromAutoSuggestedListItems(WebDriver driver, String elementPath, String countryName, boolean throwException) throws Exception {
        boolean completed = true;
        int count = 0;
        do {
            try {

                count++;    //It will go forever without this
                WebElement country = driver.findElements(By.cssSelector(elementPath)).get(0);
                country.sendKeys(countryName);
                WaitUtils.nativeWaitInSeconds(1);
                WaitUtils.waitForElementToBeClickable(driver,By.cssSelector("li[role='option']") , _Page.TIMEOUT_5_SECOND);

                //Get list of options displayed
                WaitUtils.isPageLoadingComplete(driver, 1);
                List<WebElement> countryOptions = driver.findElements(By.cssSelector("li[role='option']"));
                WebElement item = countryOptions.get(0);
                String text = item.getText();
                //System.out.println("country : " + text);

                if(text!=null && !text.contains("Searching")) {
                    PageUtils.singleClick(driver, item);
                    completed = true;
                }
            } catch (Exception e) {
                completed = false;
                WaitUtils.nativeWaitInSeconds(1);
            }
        } while (!completed && count < 3);

        if (!completed && throwException) {
            throw new Exception("Country name not selected");
        }
    }


    public static void selectFromDropDown(WebDriver driver, WebElement element, String text, boolean throwException){
        boolean completed = true;
        int count = 0;
        do {
            try {
                count++;    //It will go forever without this
                PageUtils.singleClick(driver, element);
                WaitUtils.isPageLoadingComplete(driver, _Page.TIMEOUT_PAGE_LOAD);
                WaitUtils.waitForElementToBeClickable(driver, By.xpath(".//div[contains(text(), '"+ text + "')]"), _Page.TIMEOUT_3_SECOND);
                WebElement titleToSelect = driver.findElement(By.xpath(".//div[contains(text(), '"+ text + "')]"));
                PageUtils.singleClick(driver, titleToSelect);
                completed = true;
            } catch (Exception e) {
                completed = false;
                WaitUtils.nativeWaitInSeconds(1);
            }
        } while (!completed && count < 3);

    }


    public static List<String> getListOfMatchesFromAutoSuggests(WebDriver driver, By elementPath, String text )   {
        List<String> listOfCountries = new ArrayList<>();

        try {
            WebElement country = driver.findElements((elementPath)).get(0);
            country.sendKeys(text);
            WaitUtils.isPageLoadingComplete(driver, 1);
            WaitUtils.waitForElementToBeClickable(driver,By.cssSelector("li[role='option']") , _Page.TIMEOUT_5_SECOND);

            //Generate list of items
            WaitUtils.isPageLoadingComplete(driver, 1);
            List<WebElement> listOfItems = driver.findElements(By.cssSelector("li[role='option']"));
            for (WebElement el : listOfItems) {
                String countryName = (el.getText());
                listOfCountries.add(countryName);
            }
        }catch (Exception e){
            listOfCountries.add("No results found");
        }

        return listOfCountries;
    }

    public static boolean isVisible(WebDriver driver, WebElement element, int timeout) {
        boolean isVisible = true;
        try {
            WaitUtils.waitForElementToBeClickable(driver, element, timeout);
        }catch (Exception e){
            isVisible = false;
        }
        return isVisible;
    }

    public static boolean clickOneOfTheFollowing(WebDriver driver, WebElement btn, WebElement btn2, int timeout) {
        boolean clicked = clickElement(driver, btn, timeout, true);
        if(!clicked){
            clicked = clickElement(driver, btn2,timeout,true);
        }
        return clicked;
    }

    private static boolean clickElement(WebDriver driver, WebElement btn, int timeout, boolean singleClick) {
        boolean clicked = true;
        try {
            WaitUtils.waitForElementToBeClickable(driver, btn, timeout);
            if(singleClick) {
                singleClick(driver, btn);
            }else{
                doubleClick(driver, btn);
            }
        }catch (Exception e){
            clicked = false;
        }
        return clicked;
    }

    public static boolean isElementClickable(WebDriver driver, WebElement element, int timeoutSecond) {
        boolean clickable = true;
        try{
            WaitUtils.waitForElementToBeClickable(driver, element, timeoutSecond);
        }catch (Exception e){
            //Its not clickable
        }
        return clickable;
    }

    public static boolean isLinkVisible(WebDriver driver, String manufacturerName) {
        boolean clickable = true;
        try{
            driver.findElement(By.partialLinkText(manufacturerName));
        }catch (Exception e){
            clickable = false;
        }
        return clickable;
    }
}
