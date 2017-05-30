package com.mhra.mdcm.devices.appian.pageobjects.business;

import com.mhra.mdcm.devices.appian.pageobjects._Page;
import com.mhra.mdcm.devices.appian.pageobjects.business.sections.TaskSection;
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
public class TasksTabPage extends _Page {

    @FindBy(partialLinkText = "New Service Request")
    List<WebElement> listOfNewServiceRequest;

    @FindBy(partialLinkText = "New Manufacturer Registration Request")
    List<WebElement> listOfNewManufacturerRequest;

    @FindBy(partialLinkText = "Update Manufacturer Registration Request")
    List<WebElement> listOfUpdateManufacturerRegRequest;

    @FindBy(partialLinkText = "New Account Request")
    List<WebElement> listOfNewAccount;

    @FindBy(xpath = ".//span[contains(text(),'Work In Progress')]")
    WebElement workInProgress;

    @FindBy(xpath = ".//span[contains(text(),'Application work in')]")
    WebElement applicationWorkInProgress;

    @FindBy(xpath = ".//span[contains(text(),'Completed Tasks')]")
    WebElement completedTasks;

    @Autowired
    public TasksTabPage(WebDriver driver) {
        super(driver);
    }

    public TaskSection clickOnTaskNumber(int count, String link) {
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        WaitUtils.waitForElementToBeClickable(driver, By.partialLinkText("Work In Progress"), TIMEOUT_5_SECOND);

        try {
            if (link.contains("New Account")) {
                WebElement taskLink = listOfNewAccount.get(count);
                taskLink.click();
            } else if (link.contains("New Manufacturer")) {
                WebElement taskLink = listOfNewManufacturerRequest.get(count);
                taskLink.click();
            } else if (link.contains("New Service Request")) {
                WebElement taskLink = listOfNewServiceRequest.get(count);
                taskLink.click();
            } else if (link.contains("Update Manufacturer Reg")) {
                WebElement taskLink = listOfUpdateManufacturerRegRequest.get(count);
                taskLink.click();
            }
        }catch (Exception e){
            //No items meaning there is no task with specified link
        }
        return new TaskSection(driver);
    }

    public TaskSection gotoWIPTasksPage() {
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        WaitUtils.waitForElementToBeVisible(driver, workInProgress, TIMEOUT_DEFAULT);
        WaitUtils.waitForElementToBeClickable(driver, workInProgress, TIMEOUT_DEFAULT);
        workInProgress.click();
        return new TaskSection(driver);
    }

    public TaskSection gotoCompletedTasksPage() {
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        WaitUtils.waitForElementToBeVisible(driver, completedTasks, TIMEOUT_DEFAULT);
        WaitUtils.waitForElementToBeClickable(driver, completedTasks, TIMEOUT_DEFAULT);
        completedTasks.click();
        return new TaskSection(driver);
    }

    public TaskSection clickOnLinkWithText(String orgName) {
        WaitUtils.waitForElementToBeClickable(driver, By.partialLinkText(orgName), TIMEOUT_DEFAULT);
        WebElement taskLink = driver.findElement(By.partialLinkText(orgName));
        taskLink.click();
        return new TaskSection(driver);
    }


    public boolean isLinkVisible(String link){
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        boolean isVisible = true;
        try{
            WaitUtils.waitForElementToBeClickable(driver, By.partialLinkText(link), TIMEOUT_10_SECOND);
        }catch (Exception e){
            isVisible = false;
        }
        return isVisible;
    }

    public boolean isLinkVisible(String link, int timeout){
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        boolean isVisible = true;
        try{
            if(timeout == 0){
                timeout = TIMEOUT_10_SECOND;
            }

            WaitUtils.waitForElementToBeClickable(driver, By.partialLinkText(link), timeout);
            String txt = driver.findElement(By.partialLinkText(link)).findElement(By.xpath("following::div")).getText();
            log.info("Time received : " + txt);
        }catch (Exception e){
            isVisible = false;
        }
        return isVisible;
    }

    public TaskSection gotoApplicationWIPPage() {
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        applicationWorkInProgress.click();
        return new TaskSection(driver);
    }
}
