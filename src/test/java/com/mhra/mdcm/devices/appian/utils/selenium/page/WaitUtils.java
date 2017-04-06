package com.mhra.mdcm.devices.appian.utils.selenium.page;

import com.google.common.base.Predicate;

import com.mhra.mdcm.devices.appian.pageobjects._Page;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author TPD_Auto
 */
public class WaitUtils {

    //mhratest is very slow, set this to 0 once slow issue is fixed
    static int timeForTesting = 60;

    public static void waitForElementToBeClickable(WebDriver driver, WebElement element, int maxTimeToWait) {
        maxTimeToWait = resetMaxTime(maxTimeToWait);
        new WebDriverWait(driver, maxTimeToWait).until(ExpectedConditions.elementToBeClickable(element));
    }

    public static void waitForElementToBeClickable(WebDriver driver, By by, int maxTimeToWait) {
        maxTimeToWait = resetMaxTime(maxTimeToWait);
        new WebDriverWait(driver, maxTimeToWait).until(ExpectedConditions.elementToBeClickable(by));
    }

    public static void waitForElementToBeVisible(WebDriver driver, WebElement element, int maxTimeToWait) {
        maxTimeToWait = resetMaxTime(maxTimeToWait);
        new WebDriverWait(driver, maxTimeToWait).until(ExpectedConditions.visibilityOf(element));
    }

    public static void waitForElementToBeVisible(WebDriver driver, By by, int maxTimeToWait) {
        maxTimeToWait = resetMaxTime(maxTimeToWait);
        WebElement element = driver.findElement(by);
        new WebDriverWait(driver, maxTimeToWait).until(ExpectedConditions.visibilityOf(element));
    }

    private static int resetMaxTime(int maxTimeToWait) {
        if (timeForTesting > 0) {
            maxTimeToWait = timeForTesting;
        }
        return maxTimeToWait;
    }

    /**
     * DON'T USE FOR WAITING FOR PAGES, UNLESS ITS TO DO WITH SOME NATIVE COMPONENTS WHICH SELENIUM CAN'T HANDLE
     * <p>
     * Should be used for non selenium related tasks
     * <p>
     * Example when we upload a document
     *
     * THIS SHOULD BE LAST OPTION : IF WE CAN'T DO IT WITH EXPLICIT WAITS THAN USE IT
     *
     * @param tis
     */
    public static void nativeWaitInSeconds(int tis) {
        try {
            Thread.sleep(1000 * tis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param driver
     * @param by
     * @param maxTimeToWait
     * @param overrideTimeSpecified
     */
    public static void waitForElementToBeClickable(WebDriver driver, By by, int maxTimeToWait, boolean overrideTimeSpecified) {
        if (overrideTimeSpecified)
            maxTimeToWait = resetMaxTime(maxTimeToWait);
        new WebDriverWait(driver, maxTimeToWait).until(ExpectedConditions.elementToBeClickable(by));
    }


    /**
     * @param driver
     * @param element
     * @param maxTimeToWait
     * @param overrideTimeSpecified
     */
    public static void waitForElementToBeClickable(WebDriver driver, WebElement element, int maxTimeToWait, boolean overrideTimeSpecified) {
        if (overrideTimeSpecified)
            maxTimeToWait = resetMaxTime(maxTimeToWait);
        new WebDriverWait(driver, maxTimeToWait).until(ExpectedConditions.elementToBeClickable(element));
    }


    public static void waitForElementToBeVisible(WebDriver driver, WebElement element, int maxTimeToWait, boolean overrideTimeSpecified) {
        if (overrideTimeSpecified)
            maxTimeToWait = resetMaxTime(maxTimeToWait);
        new WebDriverWait(driver, maxTimeToWait).until(ExpectedConditions.visibilityOf(element));
    }


    public static void waitForElementToBeVisible(WebDriver driver, By by, int maxTimeToWait, boolean overrideTimeSpecified) {
        if (overrideTimeSpecified)
            maxTimeToWait = resetMaxTime(maxTimeToWait);
        WebElement element = driver.findElement(by);
        new WebDriverWait(driver, maxTimeToWait).until(ExpectedConditions.visibilityOf(element));
    }

    public static void waitForAlert(WebDriver driver, int maxTimeToWait, boolean overrideTimeSpecified) {
        if (overrideTimeSpecified)
            maxTimeToWait = resetMaxTime(maxTimeToWait);
        new WebDriverWait(driver, maxTimeToWait).until(ExpectedConditions.alertIsPresent());
    }

    public static void setImplicitWaits(WebDriver driver) {
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    public static boolean isAlertPresent(WebDriver driver) {
        try {
            driver.switchTo().alert();
            return true;
        } catch (NoAlertPresentException e) {
            return false;
        }
    }


    public static void waitForPageToLoad(WebDriver driver, By by, int maxTimeToWait, boolean overrideTimeSpecified) {
        try {
            new WebDriverWait(driver, maxTimeToWait).until(ExpectedConditions.presenceOfElementLocated(by));
        }catch (Exception e){
            //Aim is to pause the page for sometimes
        }
    }


    /**
     * Page is loaded if we cannot see the message "Waiting..."
     *
     * So if "Waiting..." message is not displayed than page loading completed
     * @param driver
     * @param timeout
     * @return
     */
    public static boolean isPageLoadingComplete(WebDriver driver, int timeout){
        //nativeWaitInSeconds(1);
        boolean isLoadedFully = false;
        long start = System.currentTimeMillis();
        try {
            boolean isWaitingMessageDisplayed = isWaitingMessageDisplayed(driver);
            if(isWaitingMessageDisplayed) {
                int count = 0;
                do {
                    driver.manage().timeouts().implicitlyWait(timeout, TimeUnit.SECONDS);
                    List<WebElement> elements = driver.findElements(By.xpath(".//div[@class='appian-indicator-message' and @style='display: none;']"));
                    if (elements.size() == 1) {
                        isLoadedFully = true;
                    } else {
                        //System.out.println("-----PAGE NOT LOADED YET-----");
                    }
                    //driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
                    //elements = driver.findElements(By.xpath(".//div[@class='appian-indicator-message' and @style=' ']"));
                    driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
                    count++;
                } while (!isLoadedFully && count < 50);
            }

        }catch (Exception e){
            driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
            isLoadedFully = false;
        }

        //long diffMiliseconds = (System.currentTimeMillis() - start);
        //if(diffMiliseconds > 1000 * 3)
        //System.out.println("\nPage Took : " + diffMiliseconds + " milliseconds to load");

        return isLoadedFully;
    }

    private static boolean isWaitingMessageDisplayed(WebDriver driver) {
        boolean isDisplayed = true;
        try{
            waitForElementToBeClickable(driver, By.xpath(".//div[@class='appian-indicator-message' and @style='display: none;']"), _Page.TIMEOUT_1_SECOND, false);
            System.out.println("Waiting message is displayed");
        }catch (Exception e){
            isDisplayed = false;
        }
        return isDisplayed;
    }
}
