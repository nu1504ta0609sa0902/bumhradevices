package com.mhra.mdcm.devices.appian.pageobjects.business.sections;

import com.mhra.mdcm.devices.appian.pageobjects._Page;
import com.mhra.mdcm.devices.appian.utils.selenium.others.RandomDataUtils;
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
public class Accounts extends _Page {

    @FindBy(xpath = ".//h2[.='Status']//following::a")
    List<WebElement> listOfAccounts;
    @FindBy(xpath = ".//table//th")
    List<WebElement> listOfTableColumns;

    //Search box
    @FindBy(xpath = ".//*[contains(@class, 'filter')]//following::input[1]")
    WebElement searchBox;

    @Autowired
    public Accounts(WebDriver driver) {
        super(driver);
    }


    public boolean isHeadingCorrect(String expectedHeadings) {
        WaitUtils.waitForElementToBeClickable(driver, By.xpath(".//h2[.='" + expectedHeadings + "']") , 10, false);
        WebElement heading = driver.findElement(By.xpath(".//h2[.='" + expectedHeadings + "']"));
        boolean contains = heading.getText().contains(expectedHeadings);
        return contains;
    }


    public boolean isItemsDisplayed(String expectedHeadings) {
        boolean itemsDisplayed = false;
        WaitUtils.waitForElementToBeClickable(driver, By.xpath(".//h2[.='" + expectedHeadings + "']") , 10, false);

        if(expectedHeadings.contains("Accounts")){
            itemsDisplayed = listOfAccounts.size() > 0;
        }

        return itemsDisplayed;
    }

    public List<String> isTableColumnCorrect(String[] columns) {
        WaitUtils.waitForElementToBeClickable(driver, By.xpath(".//table//th") , 10, false);
        List<String> listOfColumns = new ArrayList<>();
        for(WebElement el: listOfTableColumns){
            String text = el.getText();
            if(text!=null){
                listOfColumns.add(text);
            }
        }

        //Verify columns matches expectation
        List<String> columnsNotFound = new ArrayList<>();
        for(String c: columns){
            if(!listOfColumns.contains(c)){
                System.out.println("Column Not Found : " + c);
                columnsNotFound.add(c);
            }
        }

        return columnsNotFound;
    }

    public Accounts searchForAccount(String orgName) {
        WaitUtils.waitForElementToBeClickable(driver, searchBox, 10, false);
        searchBox.clear();
        searchBox.sendKeys(orgName);
        searchBox.sendKeys(Keys.ENTER);
        return new Accounts(driver);
    }

    public boolean numberOfMatchesShouldBe(int minCount) {
        try{
            WaitUtils.waitForElementToBeClickable(driver, By.xpath(".//h2[.='Status']//following::a[2]"), TIMEOUT_SMALL, false);
            int actualCount = (listOfAccounts.size()-1)/2;
            return actualCount >= minCount;
        }catch (Exception e){
            return minCount == 0;
        }
    }

    public String getARandomAccount() {
        WaitUtils.waitForElementToBeClickable(driver, By.xpath(".//h2[.='Status']//following::a[2]"), TIMEOUT_SMALL, false);
        int actualCount = (listOfAccounts.size()-1)/2;
        int position = RandomDataUtils.getSimpleRandomNumberBetween(1, listOfAccounts.size() - 1, false);
        WebElement accountLinks = listOfAccounts.get(position);
        String accountName = accountLinks.getText();
        return accountName;
    }

    public Accounts viewSpecifiedAccount(String randomAccountName) {
        WaitUtils.waitForElementToBeClickable(driver, By.partialLinkText(randomAccountName), TIMEOUT_SMALL, false);
        WebElement accountLinks = driver.findElement(By.partialLinkText(randomAccountName));
        accountLinks.click();
        return new Accounts(driver);
    }
}
