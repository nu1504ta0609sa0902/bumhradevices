package com.mhra.mdcm.devices.appian.steps.d1.business;

import com.mhra.mdcm.devices.appian.domains.newaccounts.AccountRequest;
import com.mhra.mdcm.devices.appian.pageobjects.MainNavigationBar;
import com.mhra.mdcm.devices.appian.session.SessionKey;
import com.mhra.mdcm.devices.appian.steps.common.CommonSteps;
import com.mhra.mdcm.devices.appian.utils.selenium.others.RandomDataUtils;
import com.mhra.mdcm.devices.appian.utils.selenium.page.WaitUtils;
import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.springframework.context.annotation.Scope;

import java.util.List;

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
                taskSection = tasksPage.clickOnTaskNumber(0, taskType);
                isCorrectTask = taskSection.isCorrectTask(orgName);
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


    @Then("^I view new task with link \"([^\"]*)\" for the new account$")
    public void i_view_new_task_for_the_new_account(String link) throws Throwable {
        String registeredStatus = (String) scenarioSession.getData(SessionKey.organisationRegistered);
        String orgName = (String) scenarioSession.getData(SessionKey.organisationName);

        if(registeredStatus!=null && registeredStatus.toLowerCase().equals("not registered")){
            link = link.replace("Update","New");
        }

        //Go to tasks page
        mainNavigationBar = new MainNavigationBar(driver);
        //tasksPage = mainNavigationBar.clickTasks();

        //Verify new taskSection generated and its the correct one
        boolean contains = false;
        boolean isCorrectTask = false;
        int count = 0;

        do {
            //mainNavigationBar = new MainNavigationBar(driver);
            tasksPage = mainNavigationBar.clickTasks();

            //Click on link number X
            boolean isLinkVisible = tasksPage.isLinkVisible(orgName);
            if (isLinkVisible) {
                taskSection = tasksPage.clickOnLinkWithText(orgName);
                isCorrectTask = taskSection.isCorrectTask(orgName);
                if (isCorrectTask) {
                    contains = true;
                } else {
                    count++;
                }
            }
        } while (!contains && count <= 5);

        if(!contains) {

//            do {
//                //Refresh each time, it may take a while for the new task to arrive
//                tasksPage = mainNavigationBar.clickTasks();
//
//                //Click on link number X
//                taskSection = tasksPage.clickOnTaskNumber(count, link);
//                isCorrectTask = taskSection.isCorrectTask(orgName);
//                if (isCorrectTask) {
//                    contains = true;
//                    scenarioSession.putData(SessionKey.position, count);
//                } else {
//                    //Try position 0 again
//                    tasksPage = mainNavigationBar.clickTasks();
//                    taskSection = tasksPage.clickOnTaskNumber(0, link);
//                    isCorrectTask = taskSection.isCorrectTask(orgName);
//                    if (isCorrectTask) {
//                        scenarioSession.putData(SessionKey.position, count);
//                        contains = true;
//                    }
//                    count++;
//                }
//            } while (!contains && count <= 5);

            //If its still not found than try the first 1 again, because it may have taken few seconds longer than usual
            if (!contains) {
                //mainNavigationBar = new MainNavigationBar(driver);
                tasksPage = mainNavigationBar.clickTasks();
                taskSection = tasksPage.clickOnTaskNumber(0, link);
                isCorrectTask = taskSection.isCorrectTask(orgName);
                if (isCorrectTask) {
                    scenarioSession.putData(SessionKey.position, count);
                    contains = true;
                }
            }
        }

        assertThat("Task not found for organisation : " + orgName, contains, is(equalTo(true)));

    }

    @When("^I assign the task to me and \"([^\"]*)\" the generated task$")
    public void i_accept_the_task_and_the_generated_task(String approveOrReject) throws Throwable {
        //accept the taskSection and approve or reject it
        taskSection = taskSection.acceptTask();

        //Approve or reject
        String taskType = (String) scenarioSession.getData(SessionKey.taskType);
        if (approveOrReject.equals("approve")) {
            if(taskType!=null && taskType.contains("New Account")) {
                tasksPage = taskSection.approveTask();
            }else if(taskType!=null && taskType.contains("New Manufacturer")){
                tasksPage = taskSection.acceptRegistrationTask();
            }else if(taskType!=null && taskType.contains("Update Manufacturer Registration Request")){
                tasksPage = taskSection.approveTask();
            }else{
                //Assume New Manufacturer
                tasksPage = taskSection.acceptRegistrationTask();
            }
        } else {
            //Rejection process is slightly different, you need to enter a rejection reason
            taskSection = taskSection.rejectTask();
            tasksPage = taskSection.enterRejectionReason("This may have been removed", RandomDataUtils.getRandomTestName("Account already exists "));
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
        //int position = (int) scenarioSession.getData(SessionKey.position);
        //taskSection = tasksPage.clickOnTaskNumber(position, "New Service Request");
        //boolean isHeadingMatched = taskSection.isCorrectTask(orgName);
        boolean linkVisible = tasksPage.isLinkVisible(orgName, 5);
        assertThat("Task should be removed for organisation : " + orgName, linkVisible, is(equalTo(false)));
    }

    @Then("^The task with link \"([^\"]*)\" should be removed from tasks list$")
    public void theTaskWithLinkShouldBeRemovedFromTaskList(String link) {
        String registeredStatus = (String) scenarioSession.getData(SessionKey.organisationRegistered);
        String orgName = (String) scenarioSession.getData(SessionKey.organisationName);

        if(registeredStatus!=null && registeredStatus.toLowerCase().equals("not registered")){
            link = link.replace("Update","New");
        }

        //int position = (int) scenarioSession.getData(SessionKey.position);
        //taskSection = tasksPage.clickOnTaskNumber(position, link);
        boolean isVisible = tasksPage.isLinkVisible(orgName, 5);
        assertThat("Task should be removed for organisation : " + orgName, isVisible, is(equalTo(false)));
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

        scenarioSession.putData(SessionKey.taskType, "New Account");

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

    @And("^The completed task status should update to \"([^\"]*)\"$")
    public void theCompletedTaskStatusShouldUpdateTo(String expectedStatus) throws Throwable {

        boolean tasks = mainNavigationBar.isCorrectPage("Tasks");
        if(!tasks) {
            mainNavigationBar = new MainNavigationBar(driver);
            tasksPage = mainNavigationBar.clickTasks();
        }

        taskSection = tasksPage.gotoCompletedTasksPage();

        taskSection = taskSection.sortBy("Submitted", 2);

        String orgName = (String) scenarioSession.getData(SessionKey.organisationName);

        boolean isTaskStatusCorrect = taskSection.isCompletedTaskStatusCorrect(orgName, expectedStatus);
        assertThat("Expected completed task status : " + expectedStatus, isTaskStatusCorrect, is(equalTo(true)));

        //boolean isCorrectTask = taskSection.isOrganisationDisplayedOnLink(orgName);
        //assertThat("Task not found in completed list : " + orgName, isCorrectTask, is(equalTo(true)));
    }



    @And("^The completed task status of new account should update to \"([^\"]*)\"$")
    public void theCompletedTaskStatusOfNewAccountShouldUpdateTo(String expectedStatus) throws Throwable {

        boolean tasks = mainNavigationBar.isCorrectPage("Tasks");
        if(!tasks) {
            mainNavigationBar = new MainNavigationBar(driver);
            tasksPage = mainNavigationBar.clickTasks();
        }

        taskSection = tasksPage.gotoCompletedTasksPage();

        taskSection = taskSection.sortBy("Submitted", 2);

        String orgName = (String) scenarioSession.getData(SessionKey.organisationName);

        boolean isTaskStatusCorrect = taskSection.isCompletedTaskStatusCorrect2(orgName, expectedStatus);
        assertThat("Expected completed task status : " + expectedStatus, isTaskStatusCorrect, is(equalTo(true)));

        //boolean isCorrectTask = taskSection.isOrganisationDisplayedOnLink(orgName);
        //assertThat("Task not found in completed list : " + orgName, isCorrectTask, is(equalTo(true)));
    }


    @When("^I download the letter of designation$")
    public void i_download_the_letter_of_designation() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @And("^Check task contains correct devices \"([^\"]*)\" and other details$")
    public void checkCorrectDevicesAreDisplayed(String deviceList) throws Throwable {
        boolean isDevicesCorrect = taskSection.isDevicesDisplayedCorrect(deviceList);
        assertThat("Expected to see following devices : " + deviceList, isDevicesCorrect, is(equalTo(true)));
    }

    @And("^The status of designation letter should be \"([^\"]*)\"$")
    public void theStatusShouldBe(String expectedStatus) throws Throwable {
        boolean isStatusCorrect = taskSection.isDesignationLetterStatusCorrect(expectedStatus);
        assertThat("Expected to see letter of designation status : " + expectedStatus, isStatusCorrect, is(equalTo(true)));
    }


    @Then("^validate task is displaying correct new account details$")
    public void validate_new_account_details() throws Throwable {
        AccountRequest newAccount = (AccountRequest) scenarioSession.getData(SessionKey.manufacturerData);
        log.info(newAccount.toString());
        List<String> listOfDetailsWhichAreIncorrect = taskSection.verifyDetailsAreCorrect(newAccount);

        assertThat("Following information was incorrect : " + listOfDetailsWhichAreIncorrect, listOfDetailsWhichAreIncorrect.size() == 0, is(equalTo(true)));
    }
}
