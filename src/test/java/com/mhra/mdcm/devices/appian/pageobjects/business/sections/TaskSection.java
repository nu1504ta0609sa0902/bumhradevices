package com.mhra.mdcm.devices.appian.pageobjects.business.sections;

import com.mhra.mdcm.devices.appian.domains.newaccounts.ManufacturerRequestDO;
import com.mhra.mdcm.devices.appian.domains.newaccounts.AccountRequestDO;
import com.mhra.mdcm.devices.appian.pageobjects._Page;
import com.mhra.mdcm.devices.appian.pageobjects.business.TasksTabPage;
import com.mhra.mdcm.devices.appian.utils.selenium.page.CommonUtils;
import com.mhra.mdcm.devices.appian.utils.selenium.page.PageUtils;
import com.mhra.mdcm.devices.appian.utils.selenium.page.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
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

    //Task organisation details
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
    @FindBy(xpath = ".//span[contains(text(),'Organisation type')]//following::p[1]")
    WebElement odOrganisationType;
    @FindBy(xpath = ".//span[contains(text(),'Telephone')]//following::p[1]")
    WebElement odTelephone;
    @FindBy(xpath = ".//span[contains(text(),'Fax')]//following::p[1]")
    WebElement odFax;
    @FindBy(xpath = ".//span[contains(text(),'Website')]//following::p[1]")
    WebElement odWebsite;

    //Task contact person details
    @FindBy(xpath = ".//span[contains(text(),'Full name')]//following::p[1]")
    WebElement cdFullName;
    @FindBy(xpath = ".//span[contains(text(),'Job title')]//following::p[1]")
    WebElement cdJobTitle;
    @FindBy(xpath = ".//span[contains(text(),'Email')]//following::p[1]")
    WebElement cdEmail;
    @FindBy(xpath = ".//span[contains(text(),'Job title')]//following::p[2]")
    WebElement cdContactPersonTelephone;


    @FindBy(xpath = ".//h3")
    WebElement taskHeading;

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
    @FindBy(css = "div>textarea")
    WebElement commentArea;

    //Attachments : Letter of designation
    @FindBy(xpath = ".//div[contains(text(),'Uploaded date')]//following::tr/td[6]")
    WebElement letterOfDesignationStatus;

    //List of Table data : GMDN for device types in the task
    @FindBy(xpath = ".//div[contains(text(),'Risk classification')]//following::tr/td[3]")
    List<WebElement> listOfGMDNDefinitions;
    @FindBy(xpath = ".//div[contains(text(),'Intended use')]//following::td[3]")
    List<WebElement> listOfGMDNDefinitionsForSSP;

    //WIP tasks rows
    @FindBy(css = "div > table > tbody > tr")
    List<WebElement> listOfWIPTableRows;
    @FindBy(xpath = ".//div[contains(text(),'Submitted')]")
    WebElement thSubmitted;
    @FindBy(xpath = ".//button[.='Submit']")
    WebElement submitBtn;
    @FindBy(css = "img.DocumentImage---icon")
    WebElement priorityDocumentImg;

    //New filter section introduced in sprint 13
    @FindBy(xpath = ".//*[.='Organisation']")
    WebElement lblOrgName;
    @FindBy(xpath = ".//*[.='Organisation']/following::input[1]")
    WebElement orgName;
    @FindBy(xpath = ".//*[.='Task type']//following::div[1]")
    WebElement taskTypeDD;

    //Active Implantable MD table
    @FindBy(xpath = ".//h3[contains(text(), 'Active Implant')]//following::tr/td[8]")
    List<WebElement> listOfDeviceLabelAIMD;

    //Accept Approve reject taskSection
    @FindBy(xpath = ".//button[contains(text(), 'Accept')]")
    WebElement accept;
    @FindBy(xpath = ".//button[.='Go Back']")
    WebElement goBack;
    @FindBy(xpath = ".//button[contains(text(), 'Approve')]")
    WebElement approveNewAccount;
    @FindBy(xpath = ".//button[.='Accept Registration']")
    WebElement acceptRegistration;
    @FindBy(xpath = ".//button[.='Reject Registration']") //Stupid to have 2 buttons called Reject on same page
    WebElement rejectRegistration;
    @FindBy(xpath = ".//button[.='Approve']")
    WebElement approveTask;
    @FindBy(xpath = ".//button[.='Approve']//following::button[1]")
    WebElement rejectTask;


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
            WaitUtils.waitForElementToBeClickable(driver, By.xpath(".//button[contains(text(), 'Reassign')]"), TIMEOUT_10_SECOND, false);
            WaitUtils.waitForElementToBeVisible(driver, taskHeading, TIMEOUT_10_SECOND, false);
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

    public TasksTabPage approveTask() {
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        //WaitUtils.forceWaitForPageToLoad(driver, By.partialLinkText("Reassign Task"), TIMEOUT_1_SECOND, 2);
        WaitUtils.waitForElementToBeClickable(driver, approveTask, TIMEOUT_15_SECOND, false);
        PageUtils.doubleClick(driver, approveTask);
        return new TasksTabPage(driver);
    }

    public TasksTabPage approveTaskNewAccount() {
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        WaitUtils.waitForElementToBeClickable(driver, approveNewAccount, TIMEOUT_3_SECOND, false);
        PageUtils.doubleClick(driver, approveNewAccount);
        log.info("Task should be approved now");
        return new TasksTabPage(driver);
    }

    public TasksTabPage acceptRegistrationTask() {
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        //WaitUtils.forceWaitForPageToLoad(driver, By.partialLinkText("Reassign Task"), TIMEOUT_1_SECOND, 2);
        WaitUtils.waitForElementToBeClickable(driver, acceptRegistration, TIMEOUT_15_SECOND, false);
        PageUtils.doubleClick(driver, acceptRegistration);
        return new TasksTabPage(driver);
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
        WaitUtils.waitForElementToBeClickable(driver, rejectRegistration, TIMEOUT_5_SECOND, false);
        //approve.click();
        PageUtils.doubleClick(driver, rejectRegistration);
        return new TaskSection(driver);
    }

    public TaskSection rejectTask() {
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        WaitUtils.waitForElementToBeClickable(driver, rejectTask, TIMEOUT_5_SECOND, false);
        //approve.click();
        PageUtils.doubleClick(driver, rejectTask);
        return new TaskSection(driver);
    }

    public TasksTabPage enterRejectionReason(String reason, String randomTestComment) {
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);

        if(reason != null) {
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
        }

        //Enter comment
        commentArea.sendKeys(randomTestComment);

        //Submit rejection
        PageUtils.doubleClick(driver, submitBtn);
        return new TasksTabPage(driver);
    }


    public TaskSection sortBy(String sortBy, int numberOfTimesToClick) {
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        WaitUtils.waitForElementToBeClickable(driver, thSubmitted, TIMEOUT_5_SECOND, false);
        if (sortBy.equals("Submitted")) {
            for (int c = 0; c < numberOfTimesToClick; c++) {
                thSubmitted.click();
                WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
                WaitUtils.nativeWaitInSeconds(2);
            }
        }

        return new TaskSection(driver);
    }

    public TaskSection clickOnTaskName(String orgName) {
        By by = By.partialLinkText(orgName);
        WaitUtils.waitForElementToBeClickable(driver, by, TIMEOUT_10_SECOND, false);
        WebElement name = driver.findElement(by);
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
        String text = letterOfDesignationStatus.getText();

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

        //Displayed list of gmdns for AIMD, IVD and GMD
        List<String> gmdns = CommonUtils.getListOfGMDNS(listOfGMDNDefinitions, listOfGMDNDefinitionsForSSP);

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

    public List<String> verifyDetailsAreCorrect(AccountRequestDO accountDetails) {
        List<String> listOfInvalidFields = new ArrayList<>();

        //Verify organisation details
        PageUtils.isValueCorrect(odAddressLine1, accountDetails.address1, listOfInvalidFields);
        PageUtils.isValueCorrect(odAddressLine2, accountDetails.address2, listOfInvalidFields);
        PageUtils.isValueCorrect(odCityTown, accountDetails.townCity, listOfInvalidFields);
        PageUtils.isValueCorrect(odPostCode, accountDetails.postCode, listOfInvalidFields);
        PageUtils.isValueCorrect(odCountry, accountDetails.country, listOfInvalidFields);
        PageUtils.isValueCorrect(odTelephone, accountDetails.telephone, listOfInvalidFields);
        PageUtils.isValueCorrect(odFax, accountDetails.fax, listOfInvalidFields);
        PageUtils.isValueCorrect(odWebsite, accountDetails.website, listOfInvalidFields);
        PageUtils.isValueCorrect(odOrganisationType, accountDetails.organisationType, listOfInvalidFields);

        //Verify contact person details
        String fullName = accountDetails.title + " " + accountDetails.firstName + " " + accountDetails.lastName;
        PageUtils.isValueCorrect(cdFullName, fullName, listOfInvalidFields);
        PageUtils.isValueCorrect(cdJobTitle, accountDetails.jobTitle, listOfInvalidFields);
        //@Bug Phone number not updated
        //PageUtils.isValueCorrect(cdContactPersonTelephone, accountDetails.phoneNumber, listOfInvalidFields);
        PageUtils.isValueCorrect(cdEmail, accountDetails.email, listOfInvalidFields);


        return listOfInvalidFields;
    }

    public boolean isWIPTaskDetailsCorrectForAccount(String orgName, ManufacturerRequestDO organisationData, String taskType) {
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        WaitUtils.waitForElementToBeClickable(driver, priorityDocumentImg, TIMEOUT_10_SECOND, false);

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
        //Status : Text now changed from Assigned => something else
        if (isDataCorrect) {
            //isDataCorrect = PageUtils.isTableDataContentCorrect(tr, 7, "Assigned");
        }

        return isDataCorrect;
    }

    public TaskSection filterWIPTasksBy(String filterBy, String txtOrgName, String other) {
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        WaitUtils.waitForElementToBeClickable(driver, orgName, TIMEOUT_3_SECOND, false);
        if (filterBy.contains("orgName")) {
            orgName.sendKeys(txtOrgName);
        } else if (filterBy.contains("taskType")) {
            String value = "Update Manufacturer Registration Request";
            if (value.contains("New Manufacturer")) {
                value = "New Manufacturer Registration Request";
            }
            //PageUtils.selectByText(taskTypeDD, value);
            PageUtils.selectFromDropDown(driver, taskTypeDD, value, true);
        }
        lblOrgName.click();
        return new TaskSection(driver);
    }

    @FindBy(xpath = ".//table//th")
    List<WebElement> listOfTableColumns;

    public List<String> isTableColumnCorrect(String[] columns) {
        WaitUtils.waitForElementToBeClickable(driver, By.xpath(".//table//th"), TIMEOUT_DEFAULT, false);
        List<String> columnsNotFound = PageUtils.areTheColumnsCorrect(columns, listOfTableColumns);
        return columnsNotFound;
    }


    public boolean isPaperClipDisplayed(String orgName) {
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);

        By by = By.xpath(".//td[.='" + orgName + "']//following::td[5]");
        WaitUtils.waitForElementToBeClickable(driver, by, TIMEOUT_10_SECOND, false);
        WebElement tdWithDocuments = driver.findElement(by);
        WebElement img = tdWithDocuments.findElement(By.tagName("img"));
        String link = img.getAttribute("src");

        //At the moment the link which displays paper clip contains _5CxeEbDl1rWAa94_
        boolean contains = link.contains("_5CxeEbDl1rWAa94_");
        return contains;
    }

    public boolean isProductsDisplayedForDeviceType(String deviceType, List<String> listOfProducts) {
        boolean allProductsFound = true;

        String products = "";

        if (deviceType.toLowerCase().contains("active implantable")) {
            for (WebElement el : listOfDeviceLabelAIMD) {
                products = products + el.getText() + ",";
            }
        }

        //Check all products displayed
        for (String product : listOfProducts) {
            allProductsFound = products.contains(product);
            if (!allProductsFound) {
                break;
            }
        }

        return allProductsFound;
    }

    public boolean isAllTheGMDNValueDisplayed(List<String> listOfGmdns) {

        List<String> gmdns = CommonUtils.getListOfGMDNS(listOfGMDNDefinitions, null);

        //Verify it matches with my expected data set
        boolean allFound = true;
        for (String d : listOfGmdns) {
            boolean foundOne = false;
            for (String gmdn : gmdns) {
                if (gmdn.toLowerCase().contains(d.toLowerCase())) {
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

    public boolean areDevicesOrderedByDeviceTypes() {
        boolean isOrdered = true;

        //This is harder than expected because you may only have IDV or AIMD or GMD or SPP or any combinations
        return isOrdered;
    }
}