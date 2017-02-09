package com.mhra.mdcm.devices.appian.utils.selenium.page;

import com.mhra.mdcm.devices.appian.utils.selenium.others.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.io.File;
import java.util.List;
import java.util.Properties;

/**
 * Created by TPD_Auto on 20/10/2016.
 */
public class CommonUtils {


    public static  boolean areLinksVisible(WebDriver driver, String delimitedLinks) {
        boolean allLinksVisible = true;
        String[] links = delimitedLinks.split(",");
        for(String aLink: links){
            WaitUtils.waitForElementToBeClickable(driver, By.partialLinkText(aLink), 10, false);
            boolean isDisplayed = driver.findElement(By.partialLinkText(aLink)).isDisplayed();
            if(!isDisplayed){
                allLinksVisible = false;
                System.out.println("Link not visible : " + aLink);
                break;
            }
        }

        return allLinksVisible;
    }


    public static boolean areLinksClickable(WebDriver driver, String delimitedLinks) {
        boolean allLinksClickable = true;
        String[] links = delimitedLinks.split(",");
        for(String aLink: links){
            try{
                WaitUtils.waitForElementToBeClickable(driver, By.partialLinkText(aLink), 10, false);
            }catch (Exception e){
                allLinksClickable = false;
                System.out.println("Link not clickable : " + aLink);
                break;
            }
        }

        return allLinksClickable;
    }

    public static WebElement getElementWithLink(List<WebElement> listOfLinks, String linkText) {
        WebElement found = null;
        for(WebElement el: listOfLinks){
            String text = el.getText();
            if(text.contains(linkText)){
                found = el;
                break;
            }
        }
        return found;
    }

    public static String getHumanReadableUsername(String loggedInUser) {

        String selectedProfile = System.getProperty("spring.profiles.active");
        String name = FileUtils.getSpecificPropertyFromFile(FileUtils.userFileName, selectedProfile + ".username." + loggedInUser);

        String[] split = name.split("\\.");
        name = split[0] + " " + split[1];
        if(split.length > 2){
            name = name + " " + split[2];
        }

        return name;

    }

    public static boolean isNumericValue(String searchFor) {
        boolean isNumeric = true;
        try{
            Integer.parseInt(searchFor);
        }catch (Exception e){
            isNumeric = false;
        }
        return isNumeric;
    }
}
