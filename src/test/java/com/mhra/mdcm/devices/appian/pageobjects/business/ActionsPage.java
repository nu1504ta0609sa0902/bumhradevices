package com.mhra.mdcm.devices.appian.pageobjects.business;


import com.mhra.mdcm.devices.appian.pageobjects._Page;
import com.mhra.mdcm.devices.appian.pageobjects.business.sections.CreateTestsData;
import com.mhra.mdcm.devices.appian.utils.selenium.page.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by TPD_Auto
 */
@Component
public class ActionsPage extends _Page {

    @FindBy(partialLinkText = "Create Test Account")
    WebElement linkCreateTestAccount;

    @Autowired
    public ActionsPage(WebDriver driver) {
        super(driver);
    }

    public CreateTestsData gotoTestsHarnessPage() {
        WaitUtils.waitForElementToBePartOfDOM(driver, By.partialLinkText("Create Test Account"), TIMEOUT_MEDIUM, false);
        WaitUtils.waitForElementToBeClickable(driver, linkCreateTestAccount, TIMEOUT_MEDIUM, false);
        linkCreateTestAccount.click();
        return new CreateTestsData(driver);
    }
}
