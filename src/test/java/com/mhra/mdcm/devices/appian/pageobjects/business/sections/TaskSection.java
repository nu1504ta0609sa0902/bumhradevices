package com.mhra.mdcm.devices.appian.pageobjects.business.sections;

import com.mhra.mdcm.devices.appian.domains.newaccounts.AccountManufacturerRequest;
import com.mhra.mdcm.devices.appian.domains.newaccounts.AccountRequest;
import com.mhra.mdcm.devices.appian.pageobjects._Page;
import com.mhra.mdcm.devices.appian.pageobjects.business.TasksPage;
import com.mhra.mdcm.devices.appian.utils.selenium.page.PageUtils;
import com.mhra.mdcm.devices.appian.utils.selenium.page.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TPD_Auto
 * <p>
 * TaskSection and WorkInProgress section
 * <p>
 * WorkInProgress section will replace TaskSection
 */
@Component
public class TaskSection extends _Page {

    //Task details
    @FindBy(xpath = ".//span[contains(text(),'line 1')]//following::p[1]")
    WebElement odAddressLine1;
    @FindBy(xpath = ".//span[contains(text(),'line 2')]//following::p[1]")
    WebElement odAddressLine2;
    @FindBy(xpath = ".//span[contains(text(),'City')]//following::p[1]")
    WebElement odCityTown;
    @FindBy(xpath = "  .//span[contains(text(),'Postcode')]//following::p[1]")
    WebElement odPostCode;
    @FindBy(xpath = ".//span[contains(text(),'Country')]//following::p[1]")
    WebElement odCountry;
    @FindBy(xpath = "  .//label[contains(text(),'Organisation type')]//following::div[1]")
    WebElement odOrganisationType;
    @FindBy(xpath = ".//span[contains(text(),'Telephone')]//following::p[1]")
    WebElement odTelephone;
    @FindBy(xpath = ".//span[contains(text(),'Fax')]//following::p[1]")
    WebElement odFax;
    @FindBy(xpath = ".//span[contains(text(),'Website')]//following::p[1]")
    WebElement odWebsite;

    @FindBy(xpath = ".//*[contains(text(),'Full name')]//following::div[1]")
    WebElement cdFullName;
    @FindBy(xpath = ".//span[contains(text(),'Job title')]//following::p[1]")
    WebElement cdJobTitle;
    @FindBy(xpath = ".//h3[contains(text(),'Contact Person Details')]//following::span[contains(text(),'Telephone')]//following::p[1]")
    WebElement cdContactPersonTelephone;
    @FindBy(xpath = ".//span[contains(text(),'Email')]//following::p[1]")
    WebElement cdEmail;


    @FindBy(xpath = ".//h4")
    WebElement taskHeading;

    //Accept taskSection
    @FindBy(xpath = ".//button[.='Accept']")
    WebElement accept;
    @FindBy(xpath = ".//button[.='Go Back']")
    WebElement goBack;

    //Approve reject taskSection
    @FindBy(xpath = ".//button[.='Accept Registration']")
    WebElement acceptRegistration;
    @FindBy(xpath = ".//button[.='Accept Registration']//following::button[1]") //Stupid to have 2 buttons called Reject on same page
    WebElement rejectRegistration;
    @FindBy(xpath = ".//button[.='Approve']")
    WebElement approveTask;
    @FindBy(xpath = ".//button[.='Approve']//following::button[1]")
    WebElement rejectTask;

    //Rejection reason
    @FindBy(xpath = ".//*[.='Reasons']//following::input[1]")
    WebElement reasonAccountAlreadyExists;
    @FindBy(xpath = ".//*[.='Reasons']//following::input[2]")
    WebElement reasonNotRegisteredInUk;
    @FindBy(xpath = ".//*[.='Reasons']//following::input[3]")
    WebElement reasonNoAuthorisationEvidenceProvided;
    @FindBy(xpath = ".//*[.='Reasons']//following::input[4]")
    WebElement reasonNonQualifyingParty;
    @FindBy(xpath = ".//*[.='Reasons']//following::input[5]")
    WebElement other;

    //Letter of designation
    @FindBy(css = ".gwt-ListBox.GFWJSJ4DB0")
    WebElement letterOfDesignationStatus;

    @FindBy(css = ".aui-TextAreaInput")
    WebElement commentArea;
    @FindBy(xpath = ".//a[.='Risk classification']//following::td[2]")
    List<WebElement> listOfGMDNDefinitions;
    @FindBy(css = "div.aui-DataGrid-Table> table > tbody > tr")
    List<WebElement> listOfWIPTableRows;

    @FindBy(partialLinkText = "Submitted")
    WebElement submitted;
    @FindBy(xpath = ".//button[.='Submit']")
    WebElement submitBtn;


    @Autowired
    public TaskSection(WebDriver driver) {
        super(driver);
    }

    public boolean isCorrectTask(String orgName) {
        if (orgName == null) {
            return true;
        }

        try {
            WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
            //WaitUtils.forceWaitForPageToLoad(driver, By.partialLinkText("Reassign Task"), TIMEOUT_1_SECOND, 2);
            WaitUtils.waitForElementToBeVisible(driver, By.xpath(".//h4"), TIMEOUT_10_SECOND, false);
            boolean contains = taskHeading.getText().contains(orgName);
            return contains;
        } catch (Exception e) {
            return false;
        }
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
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        //WaitUtils.forceWaitForPageToLoad(driver, By.partialLinkText("Reassign Task"), TIMEOUT_1_SECOND, 2);
        WaitUtils.waitForElementToBeClickable(driver, approveTask, TIMEOUT_15_SECOND, false);
        PageUtils.doubleClick(driver, approveTask);
        return new TasksPage(driver);
    }

    public TasksPage acceptRegistrationTask() {
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        //WaitUtils.forceWaitForPageToLoad(driver, By.partialLinkText("Reassign Task"), TIMEOUT_1_SECOND, 2);
        WaitUtils.waitForElementToBeClickable(driver, acceptRegistration, TIMEOUT_15_SECOND, false);
        PageUtils.doubleClick(driver, acceptRegistration);
        return new TasksPage(driver);
    }

    /**
     * Rejecting a taskSection requires user to verify a reason
     * <p>
     * So work flow is different
     *
     * @return
     */
    public TaskSection rejectRegistrationTask() {
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        //WaitUtils.forceWaitForPageToLoad(driver, By.partialLinkText("Reassign Task"), TIMEOUT_1_SECOND, 2);
        WaitUtils.waitForElementToBeClickable(driver, rejectRegistration, TIMEOUT_5_SECOND, false);
        //approve.click();
        PageUtils.doubleClick(driver, rejectRegistration);
        return new TaskSection(driver);
    }

    public TaskSection rejectTask() {
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        //WaitUtils.forceWaitForPageToLoad(driver, By.partialLinkText("Reassign Task"), TIMEOUT_1_SECOND, 2);
        WaitUtils.waitForElementToBeClickable(driver, rejectTask, TIMEOUT_5_SECOND, false);
        //approve.click();
        PageUtils.doubleClick(driver, rejectTask);
        return new TaskSection(driver);
    }

    public TasksPage enterRejectionReason(String reason, String randomTestComment) {
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        //WaitUtils.forceWaitForPageToLoad(driver, By.partialLinkText("Reassign Task"), TIMEOUT_1_SECOND, 2);
        if (reason.contains("Other")) {
            //Comment is mandatory
            WaitUtils.waitForElementToBeClickable(driver, other, TIMEOUT_10_SECOND, false);
            other.click();
            PageFactory.initElements(driver, this);
            WaitUtils.nativeWaitInSeconds(1);
            WaitUtils.waitForElementToBeClickable(driver, commentArea, TIMEOUT_10_SECOND, false);
        } else if (reason.contains("Account already exists")) {
            WaitUtils.waitForElementToBeClickable(driver, reasonAccountAlreadyExists, TIMEOUT_10_SECOND, false);
            PageUtils.clickIfVisible(driver, reasonAccountAlreadyExists);
        } else if (reason.contains("Not registered in the UK")) {
            WaitUtils.waitForElementToBeClickable(driver, reasonNotRegisteredInUk, TIMEOUT_10_SECOND, false);
            PageUtils.clickIfVisible(driver, reasonNotRegisteredInUk);
        } else if (reason.contains("No authorisation evidence provided")) {
            WaitUtils.waitForElementToBeClickable(driver, reasonNoAuthorisationEvidenceProvided, TIMEOUT_10_SECOND, false);
            PageUtils.clickIfVisible(driver, reasonNoAuthorisationEvidenceProvided);
        } else if (reason.contains("Non-qualifying party")) {
            WaitUtils.waitForElementToBeClickable(driver, reasonNonQualifyingParty, TIMEOUT_10_SECOND, false);
            PageUtils.clickIfVisible(driver, reasonNonQualifyingParty);
        } else {
            //They have changed rejection process: The options have disappeared

        }

        //Enter comment
        commentArea.sendKeys(randomTestComment);

        //Submit rejection
        PageUtils.doubleClick(driver, submitBtn);
        return new TasksPage(driver);
    }


    public TaskSection sortBy(String sortBy, int numberOfTimesToClick) {
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        WaitUtils.waitForElementToBeClickable(driver, submitted, TIMEOUT_DEFAULT, false);
        if (sortBy.equals("Submitted")) {
            for (int c = 0; c < numberOfTimesToClick; c++) {
                submitted.click();
                WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
                WaitUtils.nativeWaitInSeconds(2);
            }
        }

        return new TaskSection(driver);
    }

    public TaskSection clickOnTaskName(String orgName) {
        WaitUtils.waitForElementToBeClickable(driver, By.partialLinkText(orgName), TIMEOUT_10_SECOND, false);
        WebElement name = driver.findElement(By.partialLinkText(orgName));
        PageUtils.doubleClick(driver, name);
        return new TaskSection(driver);
    }

    public boolean isTaskVisibleWithName(String orgName) {
        boolean isVisible = true;
        try {
            WaitUtils.waitForElementToBeClickable(driver, By.partialLinkText(orgName), TIMEOUT_5_SECOND, false);
        } catch (Exception e) {
            isVisible = false;
        }
        return isVisible;
    }

    public boolean isOrganisationDisplayedOnLink(String orgName) {
        boolean contains = driver.getPageSource().contains(orgName);
        return contains;
    }

    public boolean isDesignationLetterStatusCorrect(String expectedStatus) {
        WaitUtils.waitForElementToBeVisible(driver, letterOfDesignationStatus, TIMEOUT_5_SECOND, false);
        Select sl = new Select(letterOfDesignationStatus);
        String text = sl.getFirstSelectedOption().getText();

        //Verify status is correct
        boolean statusMatched = false;
        if (text != null && text.contains(expectedStatus)) {
            statusMatched = true;
        }

        return statusMatched;
    }

    public boolean isDevicesDisplayedCorrect(String deviceList) {
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        //WaitUtils.nativeWaitInSeconds(2);
        String[] data = deviceList.toLowerCase().split(",");

        //Displayed list of gmdns
        List<String> gmdns = new ArrayList<>();
        for (WebElement el : listOfGMDNDefinitions) {
            gmdns.add(el.getText().toLowerCase());
        }

        //Verify it matches with my expected data set
        boolean allFound = true;
        for (String d : data) {
            boolean foundOne = false;
            for (String gmdn : gmdns) {
                if (gmdn.contains(d)) {
                    foundOne = true;
                    break;
                }
            }

            //All of them must exists, therefore foundOne should be true
            if (!foundOne) {
                allFound = false;
                break;
            }
        }

        return allFound;
    }

    public boolean isCompletedTaskStatusCorrect2(String orgName, String expectedStatus) {
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        By by = By.xpath(".//td[.='" + orgName + "']//following::td[3]");
        WaitUtils.waitForElementToBeClickable(driver, by, TIMEOUT_10_SECOND, false);
        boolean contains = driver.findElement(by).getText().contains(expectedStatus);
        return contains;

    }

    public boolean isCompletedTaskStatusCorrect1(String orgName, String expectedStatus) {
        By by = By.xpath(".//td[contains(.,'" + orgName + "')]//following::td[4]");
        WaitUtils.waitForElementToBeClickable(driver, by, TIMEOUT_10_SECOND, false);
        boolean contains = driver.findElement(by).getText().contains(expectedStatus);
        return contains;

    }

    public boolean isCompletedTaskStatusCorrect(String orgName, String expectedStatus) {
        try {
            return isCompletedTaskStatusCorrect1(orgName, expectedStatus);
        } catch (Exception e) {
            return isCompletedTaskStatusCorrect2(orgName, expectedStatus);
        }
    }

    public List<String> verifyDetailsAreCorrect(AccountRequest accountDetails) {
        List<String> listOfInvalidFields = new ArrayList<>();

        //Verify organisation details
        PageUtils.isValueCorrect(odAddressLine1, accountDetails.address1, listOfInvalidFields);
        PageUtils.isValueCorrect(odAddressLine2, accountDetails.address2, listOfInvalidFields);
        PageUtils.isValueCorrect(odCityTown, accountDetails.townCity, listOfInvalidFields);
        PageUtils.isValueCorrect(odPostCode, accountDetails.postCode, listOfInvalidFields);
        PageUtils.isValueCorrect(odCountry, accountDetails.country, listOfInvalidFields);
        PageUtils.isValueCorrect(odOrganisationType, accountDetails.organisationType, listOfInvalidFields);
        PageUtils.isValueCorrect(odTelephone, accountDetails.telephone, listOfInvalidFields);
        PageUtils.isValueCorrect(odFax, accountDetails.fax, listOfInvalidFields);
        PageUtils.isValueCorrect(odWebsite, accountDetails.website, listOfInvalidFields);

        //Verify contact person details
        String fullName = accountDetails.title + " " + accountDetails.firstName + " " + accountDetails.lastName;
        PageUtils.isValueCorrect(cdFullName, fullName, listOfInvalidFields);
        PageUtils.isValueCorrect(cdJobTitle, accountDetails.jobTitle, listOfInvalidFields);
        PageUtils.isValueCorrect(cdContactPersonTelephone, accountDetails.phoneNumber, listOfInvalidFields);
        PageUtils.isValueCorrect(cdEmail, accountDetails.email, listOfInvalidFields);


        return listOfInvalidFields;
    }

    public boolean isWIPTaskDetailsCorrectForAccount(String orgName, AccountManufacturerRequest organisationData, String taskType) {
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        WebElement tr = PageUtils.getTableRow(listOfWIPTableRows, orgName);
        //Task
        boolean isDataCorrect = PageUtils.isTableDataContentCorrect(tr, 1, taskType);
        //Name: Organisation name
        if (isDataCorrect) {
            isDataCorrect = PageUtils.isTableDataContentCorrect(tr, 2, orgName);
        }
        //role
        if (isDataCorrect && organisationData != null) {
            isDataCorrect = PageUtils.isTableDataContentCorrect(tr, 3, organisationData.getRoleName());
        } else {
            isDataCorrect = !(PageUtils.isTableDataContentIsEmpty(tr, 3));
        }
        //Task Owner
        if (isDataCorrect) {
            isDataCorrect = PageUtils.isTableDataContentCorrect(tr, 4, "Staff");
        }
        //Submitted Date
        if (isDataCorrect && organisationData != null) {
            isDataCorrect = PageUtils.isTableDataContentCorrect(tr, 5, organisationData.submissionDate);
        } else {
            isDataCorrect = !(PageUtils.isTableDataContentIsEmpty(tr, 5));
        }
        //Status
        if (isDataCorrect) {
            isDataCorrect = PageUtils.isTableDataContentCorrect(tr, 6, "Assigned");
        }

        return isDataCorrect;
    }
}
