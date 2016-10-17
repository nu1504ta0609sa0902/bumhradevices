package com.mhra.mdcm.devices.appian.pageobjects;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author TPD_Auto
 */
@Component
public class MainNavigationBar extends _Page {

    @FindBy(partialLinkText = "News")
    WebElement news;
    @FindBy(partialLinkText = "Tasks")
    WebElement tasks;
    @FindBy(partialLinkText = "Records")
    WebElement records;
    @FindBy(partialLinkText = "Reports")
    WebElement reports;
    @FindBy(partialLinkText = "Actions")
    WebElement actions;

    @FindBy(css = ".appian-menu-item.appian-menu-item-selected")
    WebElement currentSelection;


    @Autowired
    public MainNavigationBar(WebDriver driver) {
        super(driver);
    }

//    public NewsPage clickNews() {
//        //try {
//        WaitUtils.waitForElementToBeClickable(driver, news, 30, false);
//        PageUtils.doubleClick(driver, news);
//        //news.click();
//        return new NewsPage(driver);
////        } catch (Exception e) {
////            return null;
////        }
//    }
//
//    public TasksPage clickTasks() {
//        WaitUtils.waitForElementToBeClickable(driver, tasks, 30, false);
//        tasks.click();
//        PageUtils.doubleClick(driver, tasks);
//        return new TasksPage(driver);
//    }
//
//    public RecordsPage clickRecords() {
//        WaitUtils.waitForElementToBeClickable(driver, records, 30, false);
//        records.click();
//        PageUtils.doubleClick(driver, records);
//        return new RecordsPage(driver);
//    }
//
//    public ReportsPage clickReports() {
//        WaitUtils.waitForElementToBeClickable(driver, reports, 30, false);
//        reports.click();
//        PageUtils.doubleClick(driver, reports);
//        return new ReportsPage(driver);
//    }
//
//    public String getCurrentSelectedMenu() {
//        WaitUtils.waitForElementToBeClickable(driver, currentSelection, 30, false);
//        String selectedMenu = currentSelection.getText();
//        return selectedMenu;
//    }
//
//    public ActionsPage clickActions() {
//        WaitUtils.waitForElementToBeClickable(driver, actions, 30);
//        actions.click();
//        PageUtils.doubleClick(driver, actions);
//        return new ActionsPage(driver);
//    }


}
