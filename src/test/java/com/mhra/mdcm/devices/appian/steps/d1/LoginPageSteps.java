package com.mhra.mdcm.devices.appian.steps.d1;

import com.mhra.mdcm.devices.appian.steps.common.CommonSteps;
import com.mhra.mdcm.devices.appian.utils.selenium.page.AssertUtils;
import com.mhra.mdcm.devices.appian.utils.selenium.page.PageUtils;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.springframework.context.annotation.Scope;

/**
 * Created by TPD_Auto on 17/10/2016.
 */
@Scope("cucumber-glue")
public class LoginPageSteps extends CommonSteps {


    @When("^I am logged into appian as \"([^\"]*)\" user$")
    public void i_am_logged_into_appian_as_user(String username) throws Throwable {
        loginPage = loginPage.loadPage(baseUrl);
        try {
            mainNavigationBar = loginPage.login(username);
        }catch(Exception e) {
            PageUtils.acceptAlert(driver, "accept");
            try {
                mainNavigationBar = loginPage.reloginUsing(username);
            }catch (Exception e2){}
        }
    }


    @Then("^I should see name of logged in user as \"([^\"]*)\"$")
    public void i_should_see_name_of_logged_in_user_as(String expectedName) throws Throwable {
        String actualName = loginPage.getLoggedInUserName();
        expectedName = AssertUtils.getExpectedName(expectedName);
        Assert.assertThat(actualName.toLowerCase(), Matchers.equalTo(expectedName));
    }


}
