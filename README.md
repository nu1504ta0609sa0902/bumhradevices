# bumhradevices


--Create skeleton spring boot test project

mvn archetype:generate\
 -DarchetypeGroupId=am.ik.archetype\
 -DarchetypeArtifactId=spring-boot-blank-archetype\
 -DarchetypeVersion=1.0.5

 -- Requires VM arguments
 -Dspring.profiles.active=one of mhradevicesdev, mhradevicestest, live

 -- Example running
mvn clean test -Dtest=RunAllTest -Dcurrent.browser=gc -Dspring.profiles.active=mhradevicestest -Dgenerate.report=true -Dcucumber.options="--tags @poc --tags ~@ignore --format json:target/cucumber-report-myReport.json"

mvn clean test -Dtest=RunAllTest -Dcurrent.browser=ie -Dspring.profiles.active=mhradevicestest -Dgenerate.report=true -Dcucumber.options="--tags @poc"
mvn clean test -Dtest=RunAllTest -Dcurrent.browser=gc -Dspring.profiles.active=mhradevicestest -Dgenerate.report=true -Dcucumber.options="--tags @poc"

mvn clean test -Dcurrent.browser=ie -Dspring.profiles.active=mhradevicestest -Dgenerate.report=true -Dcucumber.options="--tags @poc"
mvn clean test -Dcurrent.browser=gc -Dspring.profiles.active=mhradevicestest -Dgenerate.report=true -Dcucumber.options="--tags @poc"
mvn clean test -Dcurrent.browser=ff -Dspring.profiles.active=mhradevicestest -Dgenerate.report=true -Dcucumber.options="--tags @poc"

mvn test -Dcurrent.browser=ie -Dspring.profiles.active=mhradevicestest -Dgenerate.report=true -Dcucumber.options="--tags @poc"
mvn test -Dcurrent.browser=ie -Dspring.profiles.active=mhradevicestest -Dgenerate.report=true -Dcucumber.options="--tags @poc --plugin json:target/change_me.json"

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
