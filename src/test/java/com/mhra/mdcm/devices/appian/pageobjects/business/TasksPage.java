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

    @FindBy(partialLinkText = "New Account Request")
    List<WebElement> listOfNewAccount;

    @Autowired
    public TasksPage(WebDriver driver) {
        super(driver);
    }

    public TaskSection clickOnTaskNumber(int count) {
        WaitUtils.waitForElementToBeClickable(driver, By.partialLinkText("New Account Request"), TIMEOUT_DEFAULT, false);
        WebElement taskLink = listOfNewAccount.get(count);
        taskLink.click();
        return new TaskSection(driver);
    }
}
