package com.mhra.mdcm.devices.appian;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

/**
 * Created by TPD_Auto
 */
@RunWith(Cucumber.class)
@CucumberOptions(
        plugin = {"pretty", "html:target/cucumber-html-report", "json:target/all_tests.json", "rerun:target/failedScenarios.txt"}
        , monochrome = true, tags = {"~@ignore"}
)
public class RunAllTest {
}
