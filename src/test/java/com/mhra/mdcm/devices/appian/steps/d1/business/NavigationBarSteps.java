package com.mhra.mdcm.devices.appian.steps.d1.business;

import com.mhra.mdcm.devices.appian.steps.common.CommonSteps;
import cucumber.api.java.en.Then;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.springframework.context.annotation.Scope;

/**
 * Created by TPD_Auto on 18/07/2016.
 */
@Scope("cucumber-glue")
public class NavigationBarSteps extends CommonSteps {


    @Then("^I should see correct page heading \"([^\"]*)\"$")
    public void i_should_see_correct_page_heading(String expectedHeading) throws Throwable {
        boolean isCorrectPage = mainNavigationBar.isInCorrectPage(expectedHeading);
        Assert.assertThat("Expected page : " + expectedHeading, isCorrectPage, Matchers.is(true));
    }
}
