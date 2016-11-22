package com.mhra.mdcm.devices.appian.steps.d1.external;

import com.mhra.mdcm.devices.appian.domains.MyAccount;
import com.mhra.mdcm.devices.appian.pageobjects.MainNavigationBar;
import com.mhra.mdcm.devices.appian.steps.common.CommonSteps;
import cucumber.api.java.en.When;
import org.springframework.context.annotation.Scope;

/**
 * Created by TPD_Auto
 */
@Scope("cucumber-glue")
public class MyAccountPageSteps extends CommonSteps {



    @When("^I go to my accounts page$")
    public void iGoToMyAccountsPage() throws Throwable {
        mainNavigationBar = new MainNavigationBar(driver);
        myAccountPage = mainNavigationBar.clickMyAccount();
    }


    @When("^I update the following data \"([^\"]*)\"$")
    public void i_update_the_following_data(String keyValuePair) throws Throwable {
        myAccountPage = myAccountPage.amendContactPersonDetails();

        //Incase you want to come back and change it log it
        MyAccount previousDetails = myAccountPage.savePreviousDetails();
        log.warn(previousDetails.toString());

        //Update details
        myAccountPage = myAccountPage.updateFollowingFields(keyValuePair);
    }
}
