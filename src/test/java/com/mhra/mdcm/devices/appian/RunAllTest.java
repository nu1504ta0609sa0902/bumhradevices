package com.mhra.mdcm.devices.appian;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

/**
 * Created by TPD_Auto
 * VM Options required : -ea -Dspring.profiles.active=mhratest -Dcurrent.browser=gc -Dgenerate.report=true
 */
@RunWith(Cucumber.class)
@CucumberOptions(
        features = "classpath:com.mhra.mdcm.devices.appian/stories/all_sprints",
        glue = {"classpath:com.mhra.mdcm.devices.appian.steps","classpath:com.mhra.mdcm.devices.appian.steps.common"},
        plugin = {"pretty", "html:target/cucumber-html-report", "json:target/cucumber.json", "rerun:target/failedScenarios.txt"}
        , monochrome = true
)
public class RunAllTest {
}
