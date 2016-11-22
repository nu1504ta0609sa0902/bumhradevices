# bumhradevices

-- Selenium Webdriver Locator Recommendations:
    - ID
    - Name
    - CSS
    - Class
    - Tag
    - XPath : we are forced to use this because the ID are auto generated and they change, class names are
    numbers and not easy to understand.

--Programme variables
-Dtest.as.user=Yaaseen,Lambros,Andrew,Noor,Auto defaults to Auto
-Dcurrent.browser=gc,ff,pjs
-Dtest=RunAllTest
-Dspring.profiles.active=mhratest
-Dgenerate.report=true
-Dcucumber.options="--tags @poc"

--Create skeleton spring boot test project

mvn archetype:generate\
 -DarchetypeGroupId=am.ik.archetype\
 -DarchetypeArtifactId=spring-boot-blank-archetype\
 -DarchetypeVersion=1.0.5

 -- Requires VM arguments
 -Dspring.profiles.active=one of mhradev, mhratest, live

 -- Example running
mvn clean test -Dtest=RunAllTest -Dcurrent.browser=gc -Dspring.profiles.active=mhratest -Dgenerate.report=true -Dcucumber.options="--tags @poc --tags ~@ignore --format json:target/cucumber-report-myReport.json"

mvn clean test -Dtest=RunAllTest -Dcurrent.browser=ie -Dspring.profiles.active=mhratest -Dgenerate.report=true -Dcucumber.options="--tags @poc"
mvn clean test -Dtest=RunAllTest -Dcurrent.browser=gc -Dspring.profiles.active=mhratest -Dgenerate.report=true -Dcucumber.options="--tags @poc"

mvn clean test -Dcurrent.browser=ie -Dspring.profiles.active=mhratest -Dgenerate.report=true -Dcucumber.options="--tags @poc"
mvn clean test -Dcurrent.browser=gc -Dspring.profiles.active=mhratest -Dgenerate.report=true -Dcucumber.options="--tags @poc"
mvn clean test -Dcurrent.browser=ff -Dspring.profiles.active=mhratest -Dgenerate.report=true -Dcucumber.options="--tags @poc"

mvn test -Dcurrent.browser=ie -Dspring.profiles.active=mhratest -Dgenerate.report=true -Dcucumber.options="--tags @poc"
mvn test -Dcurrent.browser=ie -Dspring.profiles.active=mhratest -Dgenerate.report=true -Dcucumber.options="--tags @poc --plugin json:target/change_me.json"

-- Example settings for a feature file

--plugin
org.jetbrains.plugins.cucumber.java.run.CucumberJvmSMFormatter
--plugin
json:target/cucumber.json
--monochrome
--name
"^Create an invoice processing of different types of notification$"


------------------PhantomJS Tests-----------------------

Don't need to do the following:

	 * 	- Selenium Server or PhantomJS
	 * 	- Should work out of the box



----------- Page Objects -------------------

- Try to reflect the page structure in the actual app under test
