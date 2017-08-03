package com.mhra.mdcm.devices.appian.pageobjects;


import com.mhra.mdcm.devices.appian.pageobjects.business.*;
import com.mhra.mdcm.devices.appian.pageobjects.external.MyAccountPage;
import com.mhra.mdcm.devices.appian.pageobjects.external.ExternalHomePage;
import com.mhra.mdcm.devices.appian.utils.selenium.page.PageUtils;
import com.mhra.mdcm.devices.appian.utils.selenium.page.WaitUtils;
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

    //Business
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

    //Manufacturers and authorisedRep
    @FindBy(partialLinkText = "MHRA home")
    WebElement linkHOME;
    @FindBy(linkText = "Organisations")
    WebElement linkMyOrganisations;
    @FindBy(partialLinkText = "Summary")
    WebElement linkSummary;

    //Signout and profile section
    @FindBy(xpath = ".//span[contains(@style, 'personalization')]")
    WebElement photoIcon;
    @FindBy(xpath = ".//*[contains(text(),'Sign Out')]")
    WebElement signOutLink;
    @FindBy(xpath = ".//*[contains(text(),'Profile')]")
    WebElement profileLink;

    @FindBy(css = ".appian-menu-item.appian-menu-item-selected")
    WebElement currentSelection;


    @Autowired
    public MainNavigationBar(WebDriver driver) {
        super(driver);
    }

    //==========================================================
    //
    // BUSINESS NAVIGATION BAR
    //
    //==========================================================


    public NewsTabPage clickNews() {
        WaitUtils.waitForElementToBeClickable(driver, news, TIMEOUT_10_SECOND);
        PageUtils.doubleClick(driver, news);
        return new NewsTabPage(driver);
    }

    public TasksTabPage clickTasks() {
        //WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        WaitUtils.waitForElementToBeClickable(driver, tasks, TIMEOUT_10_SECOND);
        tasks.click();
        PageUtils.acceptAlert(driver, true, 2);
        return new TasksTabPage(driver);
    }

    public RecordsTabPage clickRecords() {
        //WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        WaitUtils.waitForElementToBeClickable(driver, records, TIMEOUT_10_SECOND);
        PageUtils.doubleClick(driver, records);
        return new RecordsTabPage(driver);
    }

    public ReportsTabPage clickReports() {
        WaitUtils.waitForElementToBeClickable(driver, reports, TIMEOUT_10_SECOND);
        reports.click();
        PageUtils.doubleClick(driver, reports);
        return new ReportsTabPage(driver);
    }

    public String getCurrentSelectedMenu() {
        WaitUtils.waitForElementToBeClickable(driver, currentSelection, TIMEOUT_10_SECOND);
        String selectedMenu = currentSelection.getText();
        return selectedMenu;
    }

    public ActionsTabPage clickActions() {
        //WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        WaitUtils.waitForElementToBeClickable(driver, actions, TIMEOUT_10_SECOND);
        actions.click();
        PageUtils.doubleClick(driver, actions);
        return new ActionsTabPage(driver);
    }

    public boolean isCorrectPage(String expectedHeading) {
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        String title = getTitle();
        boolean isCorrectPage = title.contains(expectedHeading);
        return isCorrectPage;
    }


    //==========================================================
    //
    // MUNUFACTURER AND AUTHORISEDREP NAVIGATION BAR
    //
    //==========================================================


    public ExternalHomePage clickExternalHOME() {
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        WaitUtils.waitForElementToBeClickable(driver, linkHOME, TIMEOUT_10_SECOND);
        PageUtils.doubleClick(driver, linkHOME);
        PageUtils.acceptAlert(driver, "accept", 1);
        return new ExternalHomePage(driver);
    }

    public MyAccountPage clickMyOrganisationsTab() {
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        WaitUtils.waitForElementToBeClickable(driver, linkMyOrganisations, TIMEOUT_5_SECOND);
        PageUtils.doubleClick(driver, linkMyOrganisations);
        return new MyAccountPage(driver);
    }

    public MyAccountPage gotoMyAccountProfilePage() {
        WaitUtils.waitForElementToBeClickable(driver, photoIcon, TIMEOUT_10_SECOND);
        if (photoIcon.isDisplayed()) {
            PageUtils.singleClick(driver, photoIcon);
            WaitUtils.waitForElementToBeClickable(driver, profileLink, TIMEOUT_5_SECOND);
            profileLink.click();
        }
        return new MyAccountPage(driver);
    }
}
