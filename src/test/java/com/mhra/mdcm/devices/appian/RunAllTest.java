package com.mhra.mdcm.devices.appian;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

/**
 * Created by TPD_Auto on 12/07/2016.
 */
@RunWith(Cucumber.class)
@CucumberOptions(
        plugin = {"pretty", "html:target/cucumber-html-report", "json:target/all_tests.json"}
        , monochrome = true, tags = {"~@ignore"}
)
public class RunAllTest {
}
