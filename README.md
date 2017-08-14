# Devices Automated Tests

-------------------------------------------------
-- Selenium Webdriver Locator Recommendations:
-------------------------------------------------

    - ID
    - Name
    - CSS
    - Class
    - Tag
    - XPath : we are forced to use this because the ID are auto generated and they change, class names are
    numbers and not easy to understand.

-------------------------------------------------
Steps: separated based on Business or Manufacturer
-------------------------------------------------

-- Steps and page object separated into 2 sections
    - business : business site
    |
    - external : manufacturer and authorisedRep

-------------------------------------------------
Programme variables
-------------------------------------------------

-Dcurrent.browser= Selects which browser to run the tests on gc=Chrome, ie=InternetExplorer, ff=Firefox
-Dtest=RunAllTest
-Dspring.profiles.active=Which profile to use for testing : mhratest, mhradev etc
-Dgenerate.report=Generate pretty reports
-Dcucumber.options="--tags @someTagName"
-Dis.remote=Required if running in a remote server with proxy

-------------------------------------------------
 -- MUST BE PROVIDED
-------------------------------------------------

-Dspring.profiles.active=mhratest
-Dcurrent.browser=gc

-------------------------------------------------
 -- Example running from command prompt
-------------------------------------------------

mvn clean test -Dtest=RunAllTest -Dcurrent.browser=gc -Dspring.profiles.active=mhratest

mvn clean test -Dtest=RunAllTest -Dcurrent.browser=ie -Dspring.profiles.active=mhratest -Dgenerate.report=true -Dcucumber.options="--tags @poc"
mvn clean test -Dtest=RunAllTest -Dcurrent.browser=gc -Dspring.profiles.active=mhratest -Dgenerate.report=true -Dcucumber.options="--tags @poc"

mvn clean test -Dcurrent.browser=ie -Dspring.profiles.active=mhratest -Dgenerate.report=true -Dcucumber.options="--tags @poc"
mvn clean test -Dcurrent.browser=gc -Dspring.profiles.active=mhratest -Dgenerate.report=true -Dcucumber.options="--tags @poc"
mvn clean test -Dcurrent.browser=ff -Dspring.profiles.active=mhratest -Dgenerate.report=true -Dcucumber.options="--tags @poc"

mvn test -Dcurrent.browser=ie -Dspring.profiles.active=mhratest -Dgenerate.report=true -Dcucumber.options="--tags @poc"
mvn test -Dcurrent.browser=ie -Dspring.profiles.active=mhratest -Dgenerate.report=true -Dcucumber.options="--tags @poc --plugin json:target/change_me.json"



-------------------------------------------------
Runner Class
-------------------------------------------------

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "classpath:com.mhra.mdcm.devices.appian/stories/all_sprints",
        glue = {"classpath:com.mhra.mdcm.devices.appian.steps","classpath:com.mhra.mdcm.devices.appian.steps.common"},
        plugin = {"pretty", "html:target/cucumber-html-report", "json:target/sprint1_tests.json"}
        , monochrome = true, tags = {"@_sprint1,~@ignore"}
)

ADD VM OPTIONS: -ea -Dspring.profiles.active=mhratest -Dcurrent.browser=gc

-------------------------------------------------
Page Objects
-------------------------------------------------

- Try to reflect the page structure in the actual app under test
- Helps with structuring your steps in a more meaning full way
