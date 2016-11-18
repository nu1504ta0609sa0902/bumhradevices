package com.mhra.mdcm.devices.appian;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

/**
 * Created by TPD_Auto
 */
@RunWith(Cucumber.class)
@CucumberOptions(
        monochrome = true,
        features = "@target/failedScenario.txt",
        plugin = {"pretty",
                "html:target/cucumber-reports",
                "json:target/rerunFailedScenarios.json","rerun:target/failedScenario.txt"},
        tags = "~@ignore"
)
public class RunFailedScenarios {
}
