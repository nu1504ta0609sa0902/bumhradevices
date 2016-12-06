package com.mhra.mdcm.devices.appian.steps.d1.business;

import com.mhra.mdcm.devices.appian.pageobjects.MainNavigationBar;
import com.mhra.mdcm.devices.appian.session.SessionKey;
import com.mhra.mdcm.devices.appian.steps.common.CommonSteps;
import com.mhra.mdcm.devices.appian.utils.selenium.others.RandomDataUtils;
import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.springframework.context.annotation.Scope;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

/**
 * Created by TPD_Auto on 18/07/2016.
 */
@Scope("cucumber-glue")
public class TasksPageSteps extends CommonSteps {

    @When("^I go to tasks page$")
    public void iGoToTasksPage() throws Throwable {
        mainNavigationBar = new MainNavigationBar(driver);
        tasksPage = mainNavigationBar.clickTasks();
    }

    @Then("^I should see a new task for the new account$")
    public void i_should_see_a_new_task_for_the_new_account() throws Throwable {
        String orgName = (String) scenarioSession.getData(SessionKey.organisationName);
        String taskType = "New Account Request";
        //Verify new taskSection generated and its the correct one
        boolean contains = false;
        boolean isCorrectTask = false;
        int count = 0;
        do {
            mainNavigationBar = new MainNavigationBar(driver);
            tasksPage = mainNavigationBar.clickTasks();

            //Click on link number X
            taskSection = tasksPage.clickOnTaskNumber(count, taskType);
            isCorrectTask = taskSection.isCorrectTask(orgName);
            if (isCorrectTask) {
                contains = true;
                scenarioSession.putData(SessionKey.position, count);
            } else {
                count++;
            }
        } while (!contains && count <= 5);

        //If its still not found than try the first 1 again
        if (!contains) {
            taskSection = tasksPage.clickOnTaskNumber(0, taskType);
            isCorrectTask = taskSection.isCorrectTask(orgName);
        }
        scenarioSession.putData(SessionKey.taskType, taskType);
        assertThat("Task not found for organisation : " + orgName, contains, is(equalTo(true)));

    }


    @Then("^I should see a new task with link \"([^\"]*)\" for the new account$")
    public void i_should_see_a_new_task_for_the_new_account(String link) throws Throwable {
        String orgName = (String) scenarioSession.getData(SessionKey.organisationName);
        //Verify new taskSection generated and its the correct one
        boolean contains = false;
        boolean isCorrectTask = false;
        int count = 0;
        do {
            mainNavigationBar = new MainNavigationBar(driver);
            tasksPage = mainNavigationBar.clickTasks();

            //Click on link number X
            taskSection = tasksPage.clickOnTaskNumber(count, link);
            isCorrectTask = taskSection.isCorrectTask(orgName);
            if (isCorrectTask) {
                contains = true;
                scenarioSession.putData(SessionKey.position, count);
            } else {
                count++;
            }
        } while (!contains && count <= 5);

        //If its still not found than try the first 1 again
        if (!contains) {
            taskSection = tasksPage.clickOnTaskNumber(0, link);
            isCorrectTask = taskSection.isCorrectTask(orgName);
        }

        assertThat("Task not found for organisation : " + orgName, contains, is(equalTo(true)));

    }

    @When("^I assign the task to me and \"([^\"]*)\" the generated task$")
    public void i_accept_the_task_and_the_generated_task(String approveOrReject) throws Throwable {
        //accept the taskSection and approve or reject it
        taskSection = taskSection.acceptTask();

        //Approve or reject
        if (approveOrReject.equals("approve")) {
            String taskType = (String) scenarioSession.getData(SessionKey.taskType);
            if(taskType!=null && taskType.contains("New Account")) {
                tasksPage = taskSection.approveTask();
            }else if(taskType!=null && taskType.contains("New Manufacturer")){
                tasksPage = taskSection.acceptRegistrationTask();
            }else{
                //Assume New Manufacturer
                tasksPage = taskSection.acceptRegistrationTask();
            }
        } else {
            //Rejection process is slightly different, you need to enter a rejection reason
            taskSection = taskSection.rejectTask();
            tasksPage = taskSection.enterRejectionReason("Account already exists", RandomDataUtils.getRandomTestName("Account already exists "));
        }
    }


    @When("^I assign the task to me and reject the task for following reason \"([^\"]*)\"$")
    public void i_reject_the_task_for_following_reasons(String reason) throws Throwable {
        //accept the taskSection and approve or reject it
        taskSection = taskSection.acceptTask();

        String taskType = (String) scenarioSession.getData(SessionKey.taskType);
        if(taskType!=null && taskType.contains("New Account")) {
            taskSection = taskSection.rejectTask();
        }else if(taskType!=null && taskType.contains("New Manufacturer")){
            taskSection = taskSection.rejectRegistrationTask();
        }else{
            //Assume New Manufacturer
            taskSection = taskSection.rejectRegistrationTask();
        }

        //Enter a reason for rejection
        if(reason.equals("Other")) {
            //Rejection process is slightly different, you need to enter a rejection reason
            tasksPage = taskSection.enterRejectionReason("Other", RandomDataUtils.getRandomTestName("Comment Test"));
        }else {
            tasksPage = taskSection.enterRejectionReason(reason, RandomDataUtils.getRandomTestName("Reject task because : " + reason));
        }
    }


    @Then("^The task should be removed from tasks list$")
    public void theTaskShouldBeRemovedFromTaskList() {
        String orgName = (String) scenarioSession.getData(SessionKey.organisationName);
        int position = (int) scenarioSession.getData(SessionKey.position);
        taskSection = tasksPage.clickOnTaskNumber(position, "New Service Request");
        boolean isHeadingMatched = taskSection.isCorrectTask(orgName);
        assertThat("Task should be removed for organisation : " + orgName, isHeadingMatched, is(equalTo(false)));
    }

    @Then("^The task with link \"([^\"]*)\" should be removed from tasks list$")
    public void theTaskWithLinkShouldBeRemovedFromTaskList(String link) {
        String orgName = (String) scenarioSession.getData(SessionKey.organisationName);
        int position = (int) scenarioSession.getData(SessionKey.position);
        taskSection = tasksPage.clickOnTaskNumber(position, link);
        boolean isHeadingMatched = taskSection.isCorrectTask(orgName);
        assertThat("Task should be removed for organisation : " + orgName, isHeadingMatched, is(equalTo(false)));
    }

    @When("^I go to WIP tasks page$")
    public void iGoToWIPTaksPage() throws Throwable {
        mainNavigationBar = new MainNavigationBar(driver);
        tasksPage = mainNavigationBar.clickTasks();
        taskSection = tasksPage.gotoWIPTasksPage();
    }


    @Then("^I should see a new task for the new account in WIP page$")
    public void i_should_see_a_new_task_for_the_new_account_in_WIP_page() throws Throwable {
        String orgName = (String) scenarioSession.getData(SessionKey.organisationName);

        boolean tasks = mainNavigationBar.isCorrectPage("Tasks");
        if(!tasks) {
            mainNavigationBar = new MainNavigationBar(driver);
            tasksPage = mainNavigationBar.clickTasks();
        }

        taskSection = tasksPage.gotoWIPTasksPage();

        //Sort by submitted, at the moment sorting doesnt work as expected
        taskSection = taskSection.sortBy("Submitted", 2);

        //Click on link number X
        taskSection = taskSection.clickOnTaskName(orgName);
        boolean isCorrectTask = taskSection.isCorrectTask(orgName);

        assertThat("Task not found for organisation : " + orgName, isCorrectTask, is(equalTo(true)));

    }


    @Then("^The task should be removed from WIP tasks list$")
    public void theTaskShouldBeRemovedFromWIPTaskList() {
        String orgName = (String) scenarioSession.getData(SessionKey.organisationName);

        //Sort by submitted, at the moment sorting doesnt work as expected
        taskSection = taskSection.sortBy("Submitted", 2);
        boolean isTaskVisible = taskSection.isTaskVisibleWithName(orgName);
        assertThat("Task not found for organisation : " + orgName, isTaskVisible, is(equalTo(false)));
    }

    @And("^The task status should update to \"([^\"]*)\"$")
    public void theTaskStatusShouldUpdateTo(String expectedStatus) throws Throwable {

        boolean tasks = mainNavigationBar.isCorrectPage("Tasks");
        if(!tasks) {
            mainNavigationBar = new MainNavigationBar(driver);
            tasksPage = mainNavigationBar.clickTasks();
        }

        taskSection = tasksPage.gotoCompletedTasksPage();

        taskSection = taskSection.sortBy("Submitted", 2);

        String orgName = (String) scenarioSession.getData(SessionKey.organisationName);
        boolean isCorrectTask = taskSection.isOrganisationDisplayedOnLink(orgName);
        assertThat("Task not found in completed list : " + orgName, isCorrectTask, is(equalTo(true)));
    }


    @When("^I download the letter of designation$")
    public void i_download_the_letter_of_designation() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        //throw new PendingException();
    }

    @And("^Check devices displayed and other details are correct$")
    public void checkCorrectDevicesAreDisplayed() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        //throw new PendingException();
    }
}
