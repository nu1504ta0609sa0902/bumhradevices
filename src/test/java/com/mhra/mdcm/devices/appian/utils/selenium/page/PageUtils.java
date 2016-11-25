package com.mhra.mdcm.devices.appian.utils.selenium.page;


import com.mhra.mdcm.devices.appian.utils.selenium.others.RandomDataUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

/**
 * @author TPD_Auto
 */
public class PageUtils {


    public static void selectByText(WebElement selectElement, String visibleText) {
        Select select = new Select(selectElement);
        select.selectByVisibleText(visibleText);
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

    public static void uploadDocument(WebElement element, String fileName){
        WaitUtils.nativeWaitInSeconds(2);
        element.sendKeys(fileName);
        //We will have to wait for uploading to finish
        WaitUtils.nativeWaitInSeconds(6);
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

    public static void acceptAlert(WebDriver driver, String accept) {
        try {
            WaitUtils.waitForAlert(driver, 3, false);
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

    public static void acceptAlert(WebDriver driver, boolean accept) {
        try {
            WaitUtils.waitForAlert(driver, 5, false);
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
}
