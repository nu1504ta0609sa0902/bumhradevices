package com.mhra.mdcm.devices.appian.pageobjects.business.sections;

import com.mhra.mdcm.devices.appian.pageobjects._Page;
import com.mhra.mdcm.devices.appian.pageobjects.business.TasksPage;
import com.mhra.mdcm.devices.appian.utils.selenium.page.PageUtils;
import com.mhra.mdcm.devices.appian.utils.selenium.page.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by TPD_Auto
 *
 * TaskSection and WorkInProgress section
 *
 * WorkInProgress section will replace TaskSection
 */
@Component
public class TaskSection extends _Page {

    @FindBy(xpath = ".//h4")
    WebElement taskHeading;

    //Accept taskSection
    @FindBy(xpath = ".//button[.='Accept']")
    WebElement accept;
    @FindBy(xpath = ".//button[.='Go Back']")
    WebElement goBack;

    //Approve reject taskSection
    @FindBy(xpath = ".//button[.='Approve']")
    WebElement approve;
    @FindBy(xpath = ".//button[.='Approve']//following::button[1]") //Stupid to have 2 buttons called Reject on same page
    WebElement reject;

    //Rejection reason
    @FindBy(css = ".gwt-RadioButton:nth-child(1)")
    WebElement reasonAccountAlreadyExists;
    @FindBy(xpath = ".gwt-RadioButton:nth-child(2)")
    WebElement reasonNotRegisteredInUk;
    @FindBy(xpath = ".gwt-RadioButton:nth-child(3)")
    WebElement reasonNoAuthorisationEvidenceProvided;
    @FindBy(xpath = ".gwt-RadioButton:nth-child(4)")
    WebElement reasonNonQualifyingParty;
    @FindBy(xpath = ".gwt-RadioButton:nth-child(5)")
    WebElement other;

    @FindBy(css = ".aui-TextAreaInput")
    WebElement commentArea;
    @FindBy(xpath = ".//button[.='Submit']")
    WebElement submitBtn;

    @FindBy(partialLinkText = "Submitted")
    WebElement submitted;


    @Autowired
    public TaskSection(WebDriver driver) {
        super(driver);
    }

    public boolean isCorrectTask(String orgName) {
        WaitUtils.forceWaitForPageToLoad(driver, By.partialLinkText("Reassign Task"), TIMEOUT_1_SECOND, 2);
        WaitUtils.waitForElementToBeVisible(driver, By.xpath(".//h4"), TIMEOUT_10_SECOND, false);
        boolean contains = taskHeading.getText().contains(orgName);
        return contains;
    }

    public TaskSection acceptTask() {
        try {
            WaitUtils.waitForElementToBeVisible(driver, accept, 5, false);
            WaitUtils.waitForElementToBeClickable(driver, accept, 5, false);
            if (accept.isDisplayed()) {
                PageUtils.doubleClick(driver, accept);
            }
        } catch (Exception e) {
            log.info("Task Already Accepted ");
        }
        return new TaskSection(driver);
    }

    public TasksPage approveTask() {
        WaitUtils.forceWaitForPageToLoad(driver, By.partialLinkText("Reassign Task"), TIMEOUT_1_SECOND, 2);
        WaitUtils.waitForElementToBeClickable(driver, approve, TIMEOUT_5_SECOND, false);
        //approve.click();
        PageUtils.doubleClick(driver, approve);
        return new TasksPage(driver);
    }

    /**
     * Rejecting a taskSection requires user to verify a reason
     *
     * So work flow is different
     * @return
     */
    public TaskSection rejectTask() {
        WaitUtils.forceWaitForPageToLoad(driver, By.partialLinkText("Reassign Task"), TIMEOUT_1_SECOND, 2);
        WaitUtils.waitForElementToBeClickable(driver, reject, TIMEOUT_5_SECOND, false);
        //approve.click();
        PageUtils.doubleClick(driver, reject);
        return new TaskSection(driver);
    }

    public TasksPage enterRejectionReason(String reason, String randomTestComment) {
        WaitUtils.forceWaitForPageToLoad(driver, By.partialLinkText("Reassign Task"), TIMEOUT_1_SECOND, 2);
        if(reason.contains("Other")){
            //Comment is mandatory
            WaitUtils.waitForElementToBeClickable(driver, other, TIMEOUT_10_SECOND, false);
            other.click();
            PageFactory.initElements(driver, this);
            WaitUtils.waitForElementToBeClickable(driver, commentArea, TIMEOUT_10_SECOND, false);
            commentArea.sendKeys(randomTestComment);
        }else if(reason.contains("Account already exists")){
            WaitUtils.waitForElementToBeClickable(driver, reasonAccountAlreadyExists, TIMEOUT_10_SECOND, false);
            PageUtils.doubleClick(driver, reasonAccountAlreadyExists);
        }else if(reason.contains("Not registered in the UK")){
            WaitUtils.waitForElementToBeClickable(driver, reasonNotRegisteredInUk, TIMEOUT_10_SECOND, false);
            PageUtils.doubleClick(driver, reasonNotRegisteredInUk);
        }else if(reason.contains("No authorisation evidence provided")){
            WaitUtils.waitForElementToBeClickable(driver, reasonNoAuthorisationEvidenceProvided, TIMEOUT_10_SECOND, false);
            PageUtils.doubleClick(driver, reasonNoAuthorisationEvidenceProvided);
        }else if(reason.contains("Non-qualifying party")){
            WaitUtils.waitForElementToBeClickable(driver, reasonNonQualifyingParty, TIMEOUT_10_SECOND, false);
            PageUtils.doubleClick(driver, reasonNonQualifyingParty);
        }

        //Submit rejection
        PageUtils.singleClick(driver, submitBtn);
        return new TasksPage(driver);
    }


    public TaskSection sortBy(String sortBy, int numberOfTimesToClick) {
        WaitUtils.waitForElementToBeClickable(driver, submitted, TIMEOUT_DEFAULT, false);
        if(sortBy.equals("Submitted")){
            for(int c = 0; c < numberOfTimesToClick; c++) {
                submitted.click();
                WaitUtils.forceWaitForPageToLoad(driver, By.partialLinkText("Doesnotexists"), TIMEOUT_1_SECOND, 3);
            }
        }

        return new TaskSection(driver);
    }

    public TaskSection clickOnTaskName(String orgName) {
        WaitUtils.waitForElementToBeClickable(driver, By.partialLinkText(orgName), TIMEOUT_5_SECOND, false);
        WebElement name = driver.findElement(By.partialLinkText(orgName));
        PageUtils.doubleClick(driver, name);
        return new TaskSection(driver);
    }

    public boolean isTaskVisibleWithName(String orgName) {
        boolean isVisible = true;
        try {
            WaitUtils.waitForElementToBeClickable(driver, By.partialLinkText(orgName), TIMEOUT_5_SECOND, false);
        }catch (Exception e){
            isVisible = false;
        }
        return isVisible;
    }
}
