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
public class TasksPage extends _Page {

    @FindBy(partialLinkText = "New Service Request")
    List<WebElement> listOfNewServiceRequest;

    @FindBy(partialLinkText = "New Manufacturer Registration Request")
    List<WebElement> listOfNewManufacturerRequest;

    @FindBy(partialLinkText = "New Account Request")
    List<WebElement> listOfNewAccount;

    @FindBy(xpath = ".//span[contains(text(),'Work In Progress')]")
    WebElement workInProgress;

    @FindBy(xpath = ".//span[contains(text(),'Completed Tasks')]")
    WebElement completedTasks;

    @Autowired
    public TasksPage(WebDriver driver) {
        super(driver);
    }

    public TaskSection clickOnTaskNumber(int count, String link) {
        WaitUtils.nativeWaitInSeconds(1);
        WaitUtils.waitForElementToBeClickable(driver, By.partialLinkText("Work In Progress"), TIMEOUT_5_SECOND, false);
        //WaitUtils.waitForElementToBeClickable(driver, By.partialLinkText(link), TIMEOUT_5_SECOND, false);
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
            }
        }catch (Exception e){
            //No items meaning there is no task with specified link
        }
        return new TaskSection(driver);
    }

    public TaskSection gotoWIPTasksPage() {
        WaitUtils.waitForElementToBeVisible(driver, workInProgress, TIMEOUT_DEFAULT, false);
        WaitUtils.waitForElementToBeClickable(driver, workInProgress, TIMEOUT_DEFAULT, false);
        workInProgress.click();
        return new TaskSection(driver);
    }

    public TaskSection gotoCompletedTasksPage() {
        WaitUtils.waitForElementToBeVisible(driver, completedTasks, TIMEOUT_DEFAULT, false);
        WaitUtils.waitForElementToBeClickable(driver, completedTasks, TIMEOUT_DEFAULT, false);
        completedTasks.click();
        return new TaskSection(driver);
    }
}
