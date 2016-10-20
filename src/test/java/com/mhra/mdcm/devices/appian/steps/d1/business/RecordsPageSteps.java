package com.mhra.mdcm.devices.appian.steps.d1.business;

import com.mhra.mdcm.devices.appian.pageobjects.LoginPage;
import com.mhra.mdcm.devices.appian.session.SessionKey;
import com.mhra.mdcm.devices.appian.steps.common.CommonSteps;
import com.mhra.mdcm.devices.appian.utils.selenium.page.AssertUtils;
import com.mhra.mdcm.devices.appian.utils.selenium.page.PageUtils;
import cucumber.api.PendingException;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.springframework.context.annotation.Scope;

/**
 * Created by TPD_Auto
 */
@Scope("cucumber-glue")
public class RecordsPageSteps extends CommonSteps {

    @When("^I go to records page and click on \"([^\"]*)\"$")
    public void iGoToPage(String page) throws Throwable {
        recordsPage = mainNavigationBar.clickRecords();

        if(page.equals("Accounts")){
            recordsPage = recordsPage.clickOnAccounts();
        }else if(page.equals("Devices")){
            recordsPage = recordsPage.clickOnDevices();
        }else if(page.equals("Products")){
            recordsPage = recordsPage.clickOnProducts();
        }
    }

    @Then("^I should see \"([^\"]*)\" items$")
    public void iShouldSeeItems(String expectedHeadings) throws Throwable {
        boolean isHeadingCorrect = recordsPage.isHeadingCorrect(expectedHeadings);
        boolean isItemsDisplayed = recordsPage.isItemsDisplayed(expectedHeadings);
        Assert.assertThat("Heading should be : " + expectedHeadings, isHeadingCorrect, Matchers.is(true));
    }
}
