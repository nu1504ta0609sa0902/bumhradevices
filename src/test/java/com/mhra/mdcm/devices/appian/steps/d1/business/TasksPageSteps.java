package com.mhra.mdcm.devices.appian.steps.d1.business;

import com.mhra.mdcm.devices.appian.domains.newaccounts.ManufacturerRequestDO;
import com.mhra.mdcm.devices.appian.domains.newaccounts.AccountRequestDO;
import com.mhra.mdcm.devices.appian.pageobjects.MainNavigationBar;
import com.mhra.mdcm.devices.appian.session.SessionKey;
import com.mhra.mdcm.devices.appian.steps.common.CommonSteps;
import com.mhra.mdcm.devices.appian.utils.selenium.others.RandomDataUtils;
import com.mhra.mdcm.devices.appian.utils.selenium.others.TestHarnessUtils;
import com.mhra.mdcm.devices.appian.utils.selenium.page.StepsUtils;
import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;
import org.springframework.context.annotation.Scope;

import java.util.List;
import java.util.Map;

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
        int count2 = 0;
        do {
            mainNavigationBar = new MainNavigationBar(driver);
            tasksPage = mainNavigationBar.clickTasks();

            //Click on link number X
            boolean isLinkVisible = tasksPage.isLinkVisible(orgName);
            if (isLinkVisible) {
                taskSection = tasksPage.clickOnLinkWithText(orgName);
                isCorrectTask = taskSection.isCorrectTask(orgName, taskType);
                if (isCorrectTask) {
                    contains = true;
                } else {
                    count2++;
                }
            }else{
                count2++;
            }
        } while (!contains && count2 <= 5);

        //If its still not found than try the first 1 again
        if (!contains) {
            taskSection = tasksPage.clickOnLinkWithText(orgName);
            isCorrectTask = taskSection.isCorrectTask(orgName, taskType);
            if (isCorrectTask) {
                contains = true;
            }
        }
        scenarioSession.putData(SessionKey.taskType, taskType);
        assertThat("Task not found for organisation : " + orgName, contains, is(equalTo(true)));

    }


    @Then("^I view new task with link \"([^\"]*)\" for the new account$")
    public void i_view_new_task_for_the_new_account(String link) throws Throwable {
        String registeredStatus = (String) scenarioSession.getData(SessionKey.registeredStatus);
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
            }else{
                count++;
            }
        } while (!contains && count <= 2);

        //If its still not found than try the first 1 again
        if (!contains) {
            taskSection = tasksPage.clickOnLinkWithText(orgName);
            isCorrectTask = taskSection.isCorrectTask(orgName);
            if (isCorrectTask) {
                contains = true;
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
                tasksPage = taskSection.approveTaskNewAccount();
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
            reason = null;
        }else if(taskType!=null && taskType.contains("New Manufacturer")){
            taskSection = taskSection.rejectRegistrationTask();
        }else{
            //Assume New Manufacturer
            taskSection = taskSection.rejectRegistrationTask();
        }

        //Enter a reason for rejection @bug : reason for rejection radio buttons no longer appearing
        if(reason!=null) {
            if (reason.equals("Other")) {
                //Rejection process is slightly different, you need to enter a rejection reason
                tasksPage = taskSection.enterRejectionReason("Other", RandomDataUtils.getRandomTestName("Comment Test"));
            } else {
                tasksPage = taskSection.enterRejectionReason(reason, RandomDataUtils.getRandomTestName("Reject task because : " + reason));
            }
        }else {
            taskSection.enterRejectionReason(null, RandomDataUtils.getRandomTestName("Reject task because : " + reason));
        }
    }


    @Then("^The task should be removed from tasks list$")
    public void theTaskShouldBeRemovedFromTaskList() {
        String orgName = (String) scenarioSession.getData(SessionKey.organisationName);
        boolean linkVisible = tasksPage.isLinkVisible(orgName, 3);
        assertThat("Task should be removed for organisation : " + orgName, linkVisible, is(equalTo(false)));
    }

    @Then("^The task with link \"([^\"]*)\" should be removed from tasks list$")
    public void theTaskWithLinkShouldBeRemovedFromTaskList(String link) {
        String registeredStatus = (String) scenarioSession.getData(SessionKey.registeredStatus);
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

        //Sort by submitted, at the moment sorting by default not working as expected
        taskSection = taskSection.sortBy("Submitted", 2);
    }

    @When("^I go to WIP page and sort by submitted date (\\d+) times$")
    public void iGoToWIPTaksPageAndSortXNumberOfTimes(int numberOfTimesToSort) throws Throwable {
        mainNavigationBar = new MainNavigationBar(driver);
        tasksPage = mainNavigationBar.clickTasks();
        taskSection = tasksPage.gotoWIPTasksPage();

        //Sort by submitted, at the moment sorting by default not working as expected
        taskSection = taskSection.sortBy("Submitted", numberOfTimesToSort);
    }


    @When("^I wait for task to appear for stored manufacturer in WIP page$")
    public void iWaitForTaskToAppearForStoredManufacturerInWIPPage() throws Throwable {
        String orgName = (String) scenarioSession.getData(SessionKey.organisationName);
        boolean isVisible = false;
        int count = 0;
        do {
            isVisible = taskSection.isTaskVisibleWithName(orgName);
            if(!isVisible){
                tasksPage = mainNavigationBar.clickTasks();
                taskSection = tasksPage.gotoWIPTasksPage();
                //Sort by submitted, at the moment sorting by default not working as expected
                taskSection = taskSection.sortBy("Submitted", 2);
            }
            count++;
        }while(!isVisible && count < 5);
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

        //Sort by submitted, at the moment sorting doesn't work as expected
        taskSection = taskSection.sortBy("Submitted", 2);

        //Click on link number X
        taskSection = taskSection.clickOnTaskName(orgName);
        boolean isCorrectTask = taskSection.isCorrectTask(orgName);

        String taskType = (String) scenarioSession.getData(SessionKey.taskType);
        if(taskType == null)
        scenarioSession.putData(SessionKey.taskType, "New Account");

        assertThat("Task not found for organisation : " + orgName, isCorrectTask, is(equalTo(true)));

    }


    @When("^I view task for the new account in WIP page$")
    public void i_view_task_related_to_stored_account(){
        String orgName = (String) scenarioSession.getData(SessionKey.organisationName);

        //Click on link number X
        taskSection = taskSection.clickOnTaskName(orgName);
        boolean isCorrectTask = taskSection.isCorrectTask(orgName);

        String taskType = (String) scenarioSession.getData(SessionKey.taskType);
        if(taskType == null)
            scenarioSession.putData(SessionKey.taskType, "New Account");

        assertThat("Task not found for organisation : " + orgName, isCorrectTask, is(equalTo(true)));
    }


    @When("^I view task for the stored account in WIP page$")
    public void i_view_task_for_the_stored_account(){
        String orgName = (String) scenarioSession.getData(SessionKey.organisationName);

        //Click on link number X
        taskSection = taskSection.clickOnTaskName(orgName);
        boolean isCorrectTask = taskSection.isCorrectTask(orgName);

        String taskType = (String) scenarioSession.getData(SessionKey.taskType);
        if(taskType == null)
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

    /**
     * Manufacturer account request
     * Use only after a new account has been created using manufacturer/authorised rep view
     * @throws Throwable
     */
    @Then("^Verify the WIP entry details for the new account is correct$")
    public void verify_the_WIP_entry_details_for_the_new_account_is_correct() throws Throwable {
        String orgName = (String) scenarioSession.getData(SessionKey.organisationName);
        ManufacturerRequestDO organisationData = (ManufacturerRequestDO) scenarioSession.getData(SessionKey.manufacturerData);
        boolean isWIPDataCorrect = taskSection.isWIPTaskDetailsCorrectForAccount(orgName, organisationData, "New Manufacturer Registration Request");
        assertThat("WIP page not showing correct data for : " + orgName, isWIPDataCorrect, is(equalTo(true)));
    }

    /**
     * Only after a NEW manufacturer was created as part of the scenario
     * @param taskType
     * @throws Throwable
     */
    @Then("^Check the WIP entry details for the \"([^\"]*)\" task is correct$")
    public void verify_the_WIP_entry_details_for_the_task_is_correct(String taskType) throws Throwable {
        String orgName = (String) scenarioSession.getData(SessionKey.organisationName);
        ManufacturerRequestDO organisationData = null;
        if(taskType.contains("New Manufacturer")){
            organisationData = (ManufacturerRequestDO) scenarioSession.getData(SessionKey.manufacturerData);
        }
        boolean isWIPDataCorrect = taskSection.isWIPTaskDetailsCorrectForAccount(orgName, organisationData, taskType);
        assertThat("WIP page not showing correct data for : " + orgName, isWIPDataCorrect, is(equalTo(true)));
    }

    /**
     * DOCUMENTS COLUMN NOW REMOVED @_sprint16 5450
     * @throws Throwable
     */
    @And("^The WIP task for stored manufacturer should contain a paper click image$")
    public void theWIPTaskForStoredManufacturerShouldContainAPaperClickImage() throws Throwable {
        String orgName = (String) scenarioSession.getData(SessionKey.organisationName);
        boolean isPaperClipDisplayed = taskSection.isPaperClipDisplayed(orgName);
        assertThat("Expected to see paper clip for : " + orgName, isPaperClipDisplayed, is(true));
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

    @When("^I go to completed task page$")
    public void i_go_to_completed_task_page() throws Throwable {

        boolean tasks = mainNavigationBar.isCorrectPage("Tasks");
        if(!tasks) {
            mainNavigationBar = new MainNavigationBar(driver);
            tasksPage = mainNavigationBar.clickTasks();
        }

        taskSection = tasksPage.gotoCompletedTasksPage();
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

//    @And("^Check task contains correct devices \"([^\"]*)\" and other details$")
//    public void checkCorrectDevicesAreDisplayed(String deviceList) throws Throwable {
//        boolean isDevicesCorrect = taskSection.isDevicesDisplayedCorrect(deviceList);
//        assertThat("Expected to see following devices : " + deviceList, isDevicesCorrect, is(equalTo(true)));
//    }

    @And("^Check task contains correct stored devices and other details$")
    public void checkTaskContainsCorrectDevicesAreDisplayed() throws Throwable {
        List<String> listOfGmdns = (List<String>) scenarioSession.getData(SessionKey.listOfGmndsAdded);
        String deviceList = StepsUtils.getCommaDelimitedData(listOfGmdns);
        boolean isDevicesCorrect = taskSection.isDevicesDisplayedCorrect(deviceList);
        assertThat("Expected to see following devices : " + deviceList, isDevicesCorrect, is(equalTo(true)));
    }


    @Then("^validate task is displaying correct new account details$")
    public void validate_new_account_details() throws Throwable {
        AccountRequestDO newAccount = (AccountRequestDO) scenarioSession.getData(SessionKey.manufacturerData);
        log.info(newAccount.toString());
        List<String> listOfDetailsWhichAreIncorrect = taskSection.verifyDetailsAreCorrect(newAccount);

        assertThat("Following information was incorrect : " + listOfDetailsWhichAreIncorrect, listOfDetailsWhichAreIncorrect.size() == 0, is(equalTo(true)));
    }

    @When("^I filter WIP tasks by stored organisation name$")
    public void i_filter_WIP_tasks_by_stored_org_name() throws Throwable {
        String orgName = (String) scenarioSession.getData(SessionKey.organisationName);
        taskSection = taskSection.filterWIPTasksBy("orgName", orgName, null);
    }

    @When("^I filter WIP tasks by \"([^\"]*)\"$")
    public void i_filter_WIP_tasks_by(String filterBy) throws Throwable {
        String orgName = (String) scenarioSession.getData(SessionKey.organisationName);
        String taskType = (String) scenarioSession.getData(SessionKey.taskType);

        //Filter section is hidden now: so need to expand it
        taskSection = taskSection.filterWIPTasksBy(filterBy, orgName, taskType);
    }


    @Then("^I should see the following columns for completed task page$")
    public void i_should_see_the_following_columns_for_completed_task_page(Map<String, String> dataValues) throws Throwable {
        String columnsDelimitedTxt = dataValues.get("columns");
        String[] columns = columnsDelimitedTxt.split(",");
        log.info("Expected columns : " + columnsDelimitedTxt);

        List<String> tableColumnsNotFound = taskSection.isTableColumnCorrect(columns);
        Assert.assertThat("Following columns not found : " + tableColumnsNotFound, tableColumnsNotFound.size() == 0, is(true));
    }


    @When("^I assign the AWIP page task to me and \"([^\"]*)\" the generated task$")
    public void i_assign_AWIP_task_and_accept_the_task_and_the_generated_task(String approveOrReject) throws Throwable {
        //accept the taskSection and approve or reject it
        businessManufacturerDetails = businessManufacturerDetails.assignAWIPTaskToMe();
        businessManufacturerDetails = businessManufacturerDetails.confirmAWIPIAssignment(true);
        businessManufacturerDetails = businessManufacturerDetails.clickOnSummaryTab();

        //Approve or reject
        String taskType = (String) scenarioSession.getData(SessionKey.taskType);
        if (approveOrReject.equals("approve")) {
            if(taskType!=null && taskType.contains("New Account")) {
                businessManufacturerDetails = businessManufacturerDetails.approveAWIPTaskNewAccount();
                businessManufacturerDetails = businessManufacturerDetails.confirmAWIPIAssignment(true);
            }else if(taskType!=null && taskType.contains("New Manufacturer")){
                businessManufacturerDetails = businessManufacturerDetails.approveAWIPManufacturerTask();
                businessManufacturerDetails = businessManufacturerDetails.approveAWIPAllDevices();
                businessManufacturerDetails = businessManufacturerDetails.completeTheApplication();
            }else if(taskType!=null && taskType.contains("Update Manufacturer Registration Request")){
                tasksPage = taskSection.approveTask();
            }else{
                //Assume New Manufacturer
                tasksPage = taskSection.acceptRegistrationTask();
            }
        } else {
            if(taskType!=null && taskType.contains("New Account")) {
                businessManufacturerDetails = businessManufacturerDetails.rejectAWIPNewAccountRegistration();
                businessManufacturerDetails = businessManufacturerDetails.enterRejectionReason("Account already exists", RandomDataUtils.getRandomTestName("Account already exists "));
            }else if(taskType!=null && taskType.contains("New Manufacturer")){
                businessManufacturerDetails = businessManufacturerDetails.rejectAWIPManufacturerTask();
                businessManufacturerDetails = businessManufacturerDetails.enterManufacturerRejectionReason("Submitted in error", RandomDataUtils.getRandomTestName("Account already exists "));
            }
        }
    }

    @When("^I assign the generated AWIP page task to me$")
    public void i_assign_AWIP_task_to_me() throws Throwable {
        //accept the taskSection and approve or reject it
        businessManufacturerDetails = businessManufacturerDetails.assignAWIPTaskToMe();
        businessManufacturerDetails = businessManufacturerDetails.confirmAWIPIAssignment(true);
    }


    @When("^I assign the AWIP page task to me and \"([^\"]*)\" with following \"([^\"]*)\"$")
    public void i_assign_AWIP_task_and_accept_the_task_and_the_generated_task(String reject, String reason) throws Throwable {
        //accept the taskSection and approve or reject it
        businessManufacturerDetails = businessManufacturerDetails.assignAWIPTaskToMe();
        businessManufacturerDetails = businessManufacturerDetails.confirmAWIPIAssignment(true);

        //Reject with reason
        if (reject.equals("reject")) {
            String taskType = (String) scenarioSession.getData(SessionKey.taskType);
            if(taskType!=null && taskType.contains("New Account")) {
                businessManufacturerDetails = businessManufacturerDetails.rejectAWIPNewAccountRegistration();
                businessManufacturerDetails = businessManufacturerDetails.enterRejectionReason("Account already exists", RandomDataUtils.getRandomTestName("Account already exists "));
            }else if(taskType!=null && taskType.contains("New Manufacturer")){
                businessManufacturerDetails = businessManufacturerDetails.rejectAWIPManufacturerTask();
                businessManufacturerDetails = businessManufacturerDetails.enterManufacturerRejectionReason("Submitted in error", RandomDataUtils.getRandomTestName("Account already exists "));
            }
        }
    }

    @When("^I complete the already assigned task$")
    public void i_complete_the_alredy_assigned_generated_task() throws Throwable {
        //accept the taskSection and approve or reject it
        businessManufacturerDetails = businessManufacturerDetails.clickOnSummaryTab();
        businessManufacturerDetails = businessManufacturerDetails.completeTheApplication();
    }

    @When("^I go to application WIP page$")
    public void iGoToApplicationWIPTaksPage() throws Throwable {
        mainNavigationBar = new MainNavigationBar(driver);
        tasksPage = mainNavigationBar.clickTasks();
        taskSection = tasksPage.gotoApplicationWIPPage();
    }

    @When("^I go to AWIP page$")
    public void iGoToAWIPTaksPage() throws Throwable {
        mainNavigationBar = new MainNavigationBar(driver);
        tasksPage = mainNavigationBar.clickTasks();
        taskSection = tasksPage.gotoApplicationWIPPage();
    }

    @Then("^I search and view new task in AWIP page for the new account$")
    public void i_should_see_a_new_task_in_AWIP_page_for_the_new_account() throws Throwable {
        String accountNameOrReference = (String) scenarioSession.getData(SessionKey.newApplicationReferenceNumber);
        if(!TestHarnessUtils.isNotEmptyOrNull(accountNameOrReference))
            accountNameOrReference = (String) scenarioSession.getData(SessionKey.newAccountName);

        mainNavigationBar = new MainNavigationBar(driver);
        tasksPage = mainNavigationBar.clickTasks();
        taskSection = tasksPage.gotoApplicationWIPPage();
        taskSection = taskSection.searchAWIPPageForAccount(accountNameOrReference);
        boolean isCompleted = taskSection.isSearchingCompleted();
        businessManufacturerDetails = taskSection.viewAccountByReferenceNumber(accountNameOrReference);
    }

    @Then("^I search and view new task in AWIP page for the newly created manufacturer$")
    public void i_should_see_a_new_task_in_AWIP_page_for_the_newly_created_manufacturer() throws Throwable {
        String organisationNameOrReference = (String) scenarioSession.getData(SessionKey.newApplicationReferenceNumber);
        if(!TestHarnessUtils.isNotEmptyOrNull(organisationNameOrReference))
            organisationNameOrReference = (String) scenarioSession.getData(SessionKey.organisationName);

        mainNavigationBar = new MainNavigationBar(driver);
        tasksPage = mainNavigationBar.clickTasks();
        taskSection = tasksPage.gotoApplicationWIPPage();
        taskSection = taskSection.searchAWIPPageForAccount(organisationNameOrReference);
        boolean isCompleted = taskSection.isSearchingCompleted();

        //get the reference number
        String reference = taskSection.getTheApplicationReferenceNumber();
        log.info("Found Applicaiton reference number : " + reference);

        scenarioSession.putData(SessionKey.newApplicationReferenceNumber, reference);
        businessManufacturerDetails = taskSection.clickOnReferenceNumberReturnedBySearchResult(1);
    }


    @Then("^I search and view \"([^\"]*)\" in AWIP page for the newly created manufacturer$")
    public void i_search_and_view_for_org_in_AWIP_page_for_the_newly_created_manufacturer(String orgName) throws Throwable {
        mainNavigationBar = new MainNavigationBar(driver);
        tasksPage = mainNavigationBar.clickTasks();
        taskSection = tasksPage.gotoApplicationWIPPage();
        taskSection = taskSection.searchAWIPPageForAccount(orgName);
        boolean isSearchCompleted = taskSection.isSearchingCompleted();

        businessManufacturerDetails = taskSection.clickOnReferenceNumberReturnedBySearchResult(1);
        scenarioSession.putData(SessionKey.organisationName, orgName);
    }


    @Then("^I search for new task in AWIP page for the new account$")
    public void i_search_for_the_new_task_in_AWIP_page_for_the_new_account() throws Throwable {
        String accountNameOrReference = (String) scenarioSession.getData(SessionKey.newApplicationReferenceNumber);
        if(!TestHarnessUtils.isNotEmptyOrNull(accountNameOrReference))
            accountNameOrReference = (String) scenarioSession.getData(SessionKey.newAccountName);

        mainNavigationBar = new MainNavigationBar(driver);
        tasksPage = mainNavigationBar.clickTasks();
        taskSection = tasksPage.gotoApplicationWIPPage();
        taskSection = taskSection.searchAWIPPageForAccount(accountNameOrReference);
    }

    @Then("^I search for task in AWIP page for the manufacturer$")
    public void i_search_for_the_new_task_in_AWIP_page_for_the_new_manufacturer() throws Throwable {
        String accountNameOrReference = (String) scenarioSession.getData(SessionKey.organisationName);
        mainNavigationBar = new MainNavigationBar(driver);
        tasksPage = mainNavigationBar.clickTasks();
        taskSection = tasksPage.gotoApplicationWIPPage();
        taskSection = taskSection.searchAWIPPageForAccount(accountNameOrReference);
        taskSection.isSearchingCompleted();
    }

    @Then("^The task status in AWIP page should be \"([^\"]*)\" for the new account$")
    public void the_task_status_in_AWIP_page_should_be_for_the_new_account(String status) throws Throwable {
        String accountNameOrReference = (String) scenarioSession.getData(SessionKey.newApplicationReferenceNumber);
        mainNavigationBar = new MainNavigationBar(driver);
        tasksPage = mainNavigationBar.clickTasks();
        taskSection = tasksPage.gotoApplicationWIPPage();
        taskSection = taskSection.searchAWIPPageForAccount(accountNameOrReference);
        taskSection.isSearchingCompleted();

        boolean isStatusCorrect = taskSection.isAWIPTaskStatusCorrect(status);
        //Assert.assertThat("Expected Status in Application WIP page : " + status, isStatusCorrect, is(true));
    }

    @Then("^The task status in AWIP page should be \"([^\"]*)\" for the newly created manufacturer$")
    public void the_task_status_in_AWIP_page_should_be_for_the_newly_created_manufacturer(String status) throws Throwable {
        String accountNameOrReference = (String) scenarioSession.getData(SessionKey.organisationName);
        mainNavigationBar = new MainNavigationBar(driver);
        tasksPage = mainNavigationBar.clickTasks();
        taskSection = tasksPage.gotoApplicationWIPPage();
        taskSection = taskSection.searchAWIPPageForAccount(accountNameOrReference);
        boolean isCompleted = taskSection.isSearchingCompleted();

        //get the reference number
        String reference = taskSection.getTheApplicationReferenceNumber();
        log.info("Found Applicaiton reference number : " + reference);
        scenarioSession.putData(SessionKey.newApplicationReferenceNumber, reference);

        boolean isStatusCorrect = taskSection.isAWIPTaskStatusCorrect(status);
        Assert.assertThat("Expected Status in Application WIP page : " + status + " for application : " + reference, isStatusCorrect, is(true));
    }

    @Then("^I should see a button with the following text \"([^\"]*)\"$")
    public void i_should_see_the_option_to(String button) throws Throwable {
        boolean isVisible = businessManufacturerDetails.isButtonVisibleWithText(button, 5);
        Assert.assertThat("Expected following button : " + button, isVisible, is(true));

    }

    @Then("^I should not see any option related to approving reject and completing the application$")
    public void i_should_not_see_any_option_related_to_approving_reject_and_completing_the_application() throws Throwable {
        boolean isApproveManufacturerInVisible = businessManufacturerDetails.isButtonVisibleWithText("Approve manufacturer", 2);
        boolean isRejectManufacturerINVisible = businessManufacturerDetails.isButtonVisibleWithText("Reject manufacturer", 2);
        Assert.assertThat("This button should not be displayed: Approve manufacturer" , isApproveManufacturerInVisible, is(false));
        Assert.assertThat("This button should not be displayed: Reject manufacturer" , isRejectManufacturerINVisible, is(false));
    }



    @And("^I assign the task to another user called \"([^\"]*)\"$")
    public void iAssignTheTaskToAnotherUserCalled(String assignTo) throws Throwable {
        businessManufacturerDetails = businessManufacturerDetails.assignAWIPTaskToColleague();
        if(assignTo.contains("Nobody")){
            businessManufacturerDetails = businessManufacturerDetails.assigntToNobody();
        }else{
            businessManufacturerDetails = businessManufacturerDetails.assignToColleague(assignTo);
        }
        scenarioSession.putData(SessionKey.taskAssignedTo, assignTo);
    }

    @Then("^Verify the task is assigned to the correct user$")
    public void theTaskIsAssignedToTheCorrectUser() throws Throwable {
        String assignedTo = (String) scenarioSession.getData(SessionKey.taskAssignedTo);
        boolean isAssignedToCorrectUser = taskSection.isTaskAssignedToCorrectUser(assignedTo);
        if(assignedTo.equals("Nobody")){
            assignedTo = "";
        }
        Assert.assertThat("Task should be assigned to someone called : " + assignedTo , isAssignedToCorrectUser, is(true));
    }

    @And("^I assign the AWIP page task to me and \"([^\"]*)\" without completing the application$")
    public void iAssignTheAWIPPageTaskToMeAndWithoutCompletingTheApplication(String approveOrReject) throws Throwable {
            //accept the taskSection and approve or reject it
            businessManufacturerDetails = businessManufacturerDetails.assignAWIPTaskToMe();
            businessManufacturerDetails = businessManufacturerDetails.confirmAWIPIAssignment(true);

            //Approve or reject
            String taskType = (String) scenarioSession.getData(SessionKey.taskType);
            if (approveOrReject.equals("approve")) {
                if(taskType!=null && taskType.contains("New Manufacturer")){
                    businessManufacturerDetails = businessManufacturerDetails.approveAWIPManufacturerTask();
                }
            } else {
                if(taskType!=null && taskType.contains("New Account")) {
                    businessManufacturerDetails = businessManufacturerDetails.rejectAWIPNewAccountRegistration();
                    businessManufacturerDetails = businessManufacturerDetails.enterRejectionReason("Account already exists", RandomDataUtils.getRandomTestName("Account already exists "));
                }
            }
        }
}
