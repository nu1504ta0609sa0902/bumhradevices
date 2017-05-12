package com.mhra.mdcm.devices.appian.pageobjects.business;


import com.mhra.mdcm.devices.appian.pageobjects._Page;
import com.mhra.mdcm.devices.appian.utils.selenium.page.WaitUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by TPD_Auto
 */
@Component
public class ActionsTabPage extends _Page {

    @FindBy(css = ".aui-ActionLink.GFWJSJ4DDQ")
    WebElement linkCreateTestAccount;

    @Autowired
    public ActionsTabPage(WebDriver driver) {
        super(driver);
    }

    public boolean isInActionsPage() {
        try {
            WaitUtils.waitForElementToBeClickable(driver, linkCreateTestAccount, TIMEOUT_5_SECOND);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public _CreateAccountTestsData gotoTestsHarnessPage() {
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        try {
            WaitUtils.waitForElementToBeClickable(driver, linkCreateTestAccount, TIMEOUT_15_SECOND);
            WaitUtils.waitForAlert(driver, TIMEOUT_3_SECOND);
        }catch (Exception e) {}

        linkCreateTestAccount.click();
        return new _CreateAccountTestsData(driver);
    }
}
