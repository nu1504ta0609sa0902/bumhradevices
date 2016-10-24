package com.mhra.mdcm.devices.appian.steps.d1.external;

import com.mhra.mdcm.devices.appian.pageobjects.external.PortalPage;
import com.mhra.mdcm.devices.appian.steps.common.CommonSteps;
import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.springframework.context.annotation.Scope;

/**
 * Created by TPD_Auto
 */
@Scope("cucumber-glue")
public class PortalPageSteps extends CommonSteps {


    @Then("^I should see the following portal \"([^\"]*)\" links$")
    public void i_should_see_the_following_links(String delimitedLinks) throws Throwable {
        PortalPage portalPage = mainNavigationBar.clickPortals();
        boolean areLinksVisible = portalPage.areLinksVisible(delimitedLinks);
        Assert.assertThat("Expected to see the following links : " + delimitedLinks, areLinksVisible, Matchers.is(true));
    }

    @And("^All the links \"([^\"]*)\" are clickable$")
    public void allTheAreClickable(String delimitedLinks) throws Throwable {
        PortalPage portalPage = mainNavigationBar.clickPortals();
        boolean areLinksClickable = portalPage.areLinksClickable(delimitedLinks);
        Assert.assertThat("Not all links are clickable : " + delimitedLinks, areLinksClickable, Matchers.is(true));
    }
}
