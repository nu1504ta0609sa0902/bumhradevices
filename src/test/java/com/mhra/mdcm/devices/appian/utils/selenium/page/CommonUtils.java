package com.mhra.mdcm.devices.appian.utils.selenium.page;

import com.mhra.mdcm.devices.appian.utils.selenium.others.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.io.File;
import java.util.ArrayList;
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
            String text = el.getText().toLowerCase();
            if(text.contains(linkText.toLowerCase())){
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

    public static int getNumberOfItemsInList(WebDriver driver, List<WebElement> listOfTermsOrCodeMatches) {
        int numberOfItems = 0;
        for(WebElement element: listOfTermsOrCodeMatches){
            try {
                //String text = element.findElement(By.tagName("a")).getText();
                String text = element.getText();
                if (!text.trim().equals("") && !text.contains("View all GMDN")) {
                    numberOfItems++;
                }
            }catch (Exception e){}
        }
        return numberOfItems;
    }

    public static List<String> getListOfGMDNS(List<WebElement> listOfGMDNDefinitions, List<WebElement> listOfGMDNDefinitionsForSSP) {
        List<String> gmdns = new ArrayList<>();
        for (WebElement el : listOfGMDNDefinitions) {
            gmdns.add(el.getText().toLowerCase());
        }

        //List of gmdns for SPP
        if(listOfGMDNDefinitionsForSSP!=null && listOfGMDNDefinitionsForSSP.size() >= 1) {
            String sppGMDN = listOfGMDNDefinitionsForSSP.get(0).getText();
            gmdns.add(sppGMDN);
        }
        return gmdns;
    }

    public static List<String> getListOfCountries(String[] data) {
        List<String> countries = new ArrayList<>();
        for(String d: data){
            String country = d.split("=")[0];
            countries.add(country);
        }
        return countries;
    }

    public static List<String> getListOfText(List<WebElement> listOfElements) {
        List<String> list = new ArrayList<>();
        for(WebElement e: listOfElements){
            list.add(e.getText());
        }
        return list;
    }

    public static List<String> getListOfData(String[] data) {
        List<String> list = new ArrayList<>();
        for(String d: data){
            list.add(d);
        }
        return list;
    }
}
