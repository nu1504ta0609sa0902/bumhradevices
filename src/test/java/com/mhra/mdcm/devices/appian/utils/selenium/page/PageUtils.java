package com.mhra.mdcm.devices.appian.utils.selenium.page;


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
            WaitUtils.waitForElementToBeClickable(driver, element, 2, false);
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

    public static WebElement getRandomNotification(List<WebElement> listOfECIDLinks) {
        String index = RandomDataUtils.getSimpleRandomNumberBetween(0, listOfECIDLinks.size() - 1);
        WebElement element = listOfECIDLinks.get(Integer.parseInt(index));
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
            WaitUtils.waitForAlert(driver, timeToWait, false);
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
            WaitUtils.waitForAlert(driver, timeToWait, false);
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

    public static void selectFromAutosuggests(WebDriver driver, WebElement element, String selectOption) {
        //You will need to wait for auto suggested element to appear and than select accordingly
        new Actions(driver).moveToElement(element).perform();
        boolean completed = true;
        int count = 0;
        do {
            try {
                count++;
                element.getText();
                element.sendKeys(selectOption);
                new WebDriverWait(driver, 2).until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".item")));
                element.getText();
                element.sendKeys(Keys.ARROW_DOWN, Keys.ENTER);
                completed = true;
            }catch (Exception e){
                completed = false;
                WaitUtils.nativeWaitInSeconds(1);
            }
        }while (!completed && count <= 3);
    }


    public static void selectFromAutoSuggests(WebDriver driver, By elementPath, String text )   {
        boolean completed = true;
        int count = 0;
        do {
            try {

                count++;    //It will go forever without this
                WebElement country = driver.findElements(elementPath).get(0);
                new Actions(driver).moveToElement(country).perform();

                //Enter the country I am interested in
                country.sendKeys("\n");
                country.clear();
                country.sendKeys(text, Keys.ENTER);
                new WebDriverWait(driver, 3).until(ExpectedConditions.elementToBeClickable(By.cssSelector(".item")));
                country.sendKeys(Keys.ARROW_DOWN, Keys.ENTER);

                completed = true;
            } catch (Exception e) {
                completed = false;
                WaitUtils.nativeWaitInSeconds(1);
                //PageFactory.initElements(driver, this);
            }
        } while (!completed && count < 1);
    }


    public static void updateElementValue(WebDriver driver, WebElement element, String value, int timeOut) {
        WaitUtils.nativeWaitInSeconds(1);
        WaitUtils.waitForElementToBeClickable(driver, element, timeOut, false);
        element.clear();
        element.sendKeys(RandomDataUtils.generateTestNameStartingWith(value, 0));
    }

    public static boolean isDisplayed(WebDriver driver, WebElement manufacturerDropDown, int timeOut) {
        boolean isDisplayed = true;
        try{
            WaitUtils.waitForElementToBeClickable(driver, manufacturerDropDown, timeOut, false);
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
            if(!lowerCaseHeadings.contains(heading)){
                allFound = false;
                break;
            }
        }
        return allFound;
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

    public static WebElement getTableRow(List<WebElement> listOfWIPTableRows, String textToMatch) {
        WebElement trFound = null;
        for(WebElement tr: listOfWIPTableRows){
            String txt = tr.getText();
            //System.out.println(txt);
            boolean contains = txt.contains(textToMatch);
            if(contains){
                trFound = tr;
                break;
            }
        }
        return trFound;
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
            System.out.println(text);
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
}
