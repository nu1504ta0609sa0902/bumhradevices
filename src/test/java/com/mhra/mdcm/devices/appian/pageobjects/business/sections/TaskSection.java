package com.mhra.mdcm.devices.appian.pageobjects.business.sections;

import com.mhra.mdcm.devices.appian.domains.newaccounts.ManufacturerRequestDO;
import com.mhra.mdcm.devices.appian.domains.newaccounts.AccountRequestDO;
import com.mhra.mdcm.devices.appian.pageobjects._Page;
import com.mhra.mdcm.devices.appian.pageobjects.business.TasksTabPage;
import com.mhra.mdcm.devices.appian.pageobjects.business.sections.records.BusinessDeviceDetails;
import com.mhra.mdcm.devices.appian.pageobjects.business.sections.records.BusinessManufacturerDetails;
import com.mhra.mdcm.devices.appian.utils.selenium.others.RandomDataUtils;
import com.mhra.mdcm.devices.appian.utils.selenium.page.CommonUtils;
import com.mhra.mdcm.devices.appian.utils.selenium.page.PageUtils;
import com.mhra.mdcm.devices.appian.utils.selenium.page.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
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

    //Task page heading
    @FindBy(xpath = ".//h3")
    WebElement taskHeading;
    @FindBy(xpath = ".//a[contains(text(),'Organisation Details')]//following::p[1]")
    WebElement taskHeading2;

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
    @FindBy(xpath = ".//span[contains(text(),'Letter of designation')]//following::a")
    WebElement linkLetterOfDesignation;

    //List of Table data : GMDN for device types in the task
    @FindBy(xpath = ".//div[contains(text(),'Custom made')]//following::tr/td[1]")
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

    //Application WIP page
    @FindBy(xpath = ".//*[text()='Priority']/following::tr/td[1]")
    List<WebElement> listOfApplicationReferences;
    @FindBy(xpath = ".//*[text()='Priority']/following::tr/td[1]")
    WebElement aApplicationReference;
    @FindBy(xpath = ".//*[text()='Priority']/following::tr/td[4]/div/img")
    WebElement applicationStatus;
    @FindBy(xpath = ".//*[text()='Priority']/following::tr/td[2]")
    WebElement applicationType;
    @FindBy(xpath = ".//*[text()='Priority']/following::tr/td[5]")
    WebElement applicationAssignedTo;
    @FindBy(xpath = ".//*[text()='Priority']/following::tr")
    List<WebElement> listOfApplicationData;
    @FindBy(partialLinkText = "Filter application")
    WebElement linkFilterApplication;
    @FindBy(xpath = ".//*[contains(text(), 'Search by manufacturer')]/following::input[1]")
    WebElement tbxSearchByManufacturer;
    @FindBy(xpath = ".//button[text()='Search']")
    WebElement btnSearchForManufacuturer;
    @FindBy(xpath = ".//button[text()='Clear']")
    WebElement btnClearSearchField;


    //New filter section introduced in sprint 13
    @FindBy(xpath = ".//*[.='Organisation']")
    WebElement lblOrgName;
    @FindBy(xpath = ".//*[.='Application assigned to']/following::input[1]")
    WebElement byApplicationAssignedTo;
    @FindBy(xpath = ".//*[.='Task type']//following::div[1]")
    WebElement taskTypeDD;
    @FindBy(xpath = ".//*[.='Filter by service']//following::div[1]")
    WebElement byServiceDD;
    @FindBy(xpath = ".//*[.='Application type']//following::div[1]")
    WebElement byApplicationTypeDD;
    @FindBy(xpath = ".//*[.='Application status']//following::div[1]")
    WebElement byApplicationStatusDD;
    @FindBy(xpath = ".//*[.='Applicant role']//following::div[1]")
    WebElement byApplicationRoleDD;
    @FindBy(xpath = ".//*[.='Priority']//following::div[1]")
    WebElement byPriorityDD;
    @FindBy(partialLinkText = "Show Filter")
    WebElement linkShowFilters;
    @FindBy(partialLinkText = "Hide Filter")
    WebElement linkHideFilters;
    @FindBy(xpath = ".//*[contains(text(),'Due date')]//following::input[1]")
    WebElement dueDate;

    //Active Implantable MD table
    @FindBy(xpath = ".//h3[contains(text(), 'Active Implant')]//following::tr/td[8]")
    List<WebElement> listOfDeviceLabelAIMD;

    //Accept Approve reject taskSection
    @FindBy(xpath = ".//button[contains(text(), 'Accept')]")
    WebElement accept;
    @FindBy(xpath = ".//button[.='Go Back']")
    WebElement goBack;
    @FindBy(xpath = ".//button[contains(text(), 'Approve Account')]")
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
            //For new account
            WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
            WaitUtils.waitForElementToBeVisible(driver, taskHeading2, TIMEOUT_3_SECOND);
            boolean contains = taskHeading2.getText().contains(orgName);
            return contains;
        } catch (Exception e) {
            return false;
        }
    }


    public boolean isCorrectTask(String orgName, String taskType) {

//        if (orgName == null) {
//            return true;
//        }

        WebElement header = taskHeading2;
        if (taskType != null && taskType.contains("New Account Request")) {
            header = taskHeading;
        }

        try {
            //For new account
            WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
            WaitUtils.waitForElementToBeVisible(driver, header, TIMEOUT_3_SECOND);
            boolean contains = header.getText().contains(orgName);
            return contains;
        } catch (Exception e) {
            return false;
        }
    }

    public TaskSection acceptTask() {
        try {
            WaitUtils.waitForElementToBeVisible(driver, accept, TIMEOUT_5_SECOND);
            WaitUtils.waitForElementToBeClickable(driver, accept, TIMEOUT_5_SECOND);
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
        WaitUtils.waitForElementToBeClickable(driver, approveTask, TIMEOUT_15_SECOND);
        PageUtils.doubleClick(driver, approveTask);
        return new TasksTabPage(driver);
    }

    public TasksTabPage approveTaskNewAccount() {
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        WaitUtils.waitForElementToBeClickable(driver, approveNewAccount, TIMEOUT_3_SECOND);
        PageUtils.doubleClick(driver, approveNewAccount);
        log.info("Task should be approved now");
        return new TasksTabPage(driver);
    }

    public TasksTabPage acceptRegistrationTask() {
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        WaitUtils.waitForElementToBeClickable(driver, acceptRegistration, TIMEOUT_15_SECOND);
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
        WaitUtils.waitForElementToBeClickable(driver, rejectRegistration, TIMEOUT_5_SECOND);
        //approve.click();
        PageUtils.doubleClick(driver, rejectRegistration);
        return new TaskSection(driver);
    }

    public TaskSection rejectTask() {
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        WaitUtils.waitForElementToBeClickable(driver, rejectTask, TIMEOUT_10_SECOND);
        //approve.click();
        PageUtils.doubleClick(driver, rejectTask);
        return new TaskSection(driver);
    }

    public TasksTabPage enterRejectionReason(String reason, String randomTestComment) {
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);

        if (reason != null) {
            if (reason.contains("Other")) {
                //Comment is mandatory
                WaitUtils.waitForElementToBeClickable(driver, other, TIMEOUT_10_SECOND);
                other.click();
            } else if (reason.contains("Account already exists")) {
                WaitUtils.waitForElementToBeClickable(driver, reasonAccountAlreadyExists, TIMEOUT_10_SECOND);
                PageUtils.clickIfVisible(driver, reasonAccountAlreadyExists);
            } else if (reason.contains("Not registered in the UK")) {
                WaitUtils.waitForElementToBeClickable(driver, reasonNotRegisteredInUk, TIMEOUT_10_SECOND);
                PageUtils.clickIfVisible(driver, reasonNotRegisteredInUk);
            } else if (reason.contains("No authorisation evidence provided")) {
                WaitUtils.waitForElementToBeClickable(driver, reasonNoAuthorisationEvidenceProvided, TIMEOUT_10_SECOND);
                PageUtils.clickIfVisible(driver, reasonNoAuthorisationEvidenceProvided);
            } else if (reason.contains("Non-qualifying party")) {
                WaitUtils.waitForElementToBeClickable(driver, reasonNonQualifyingParty, TIMEOUT_10_SECOND);
                PageUtils.clickIfVisible(driver, reasonNonQualifyingParty);
            } else {
                //They have changed rejection process: The options have disappeared

            }
        }

        //Enter comment
        WaitUtils.waitForElementToBeClickable(driver, commentArea, TIMEOUT_10_SECOND);
        commentArea.sendKeys(randomTestComment);

        //Submit rejection
        PageUtils.doubleClick(driver, submitBtn);
        return new TasksTabPage(driver);
    }


    public TaskSection sortBy(String sortBy, int numberOfTimesToClick) {
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        if (sortBy.equals("Submitted")) {
            for (int c = 0; c < numberOfTimesToClick; c++) {
                WaitUtils.waitForElementToBeClickable(driver, thSubmitted, TIMEOUT_15_SECOND);
                thSubmitted.click();
                WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
                //WaitUtils.nativeWaitInSeconds(2);
            }
        }

        return new TaskSection(driver);
    }

    public TaskSection clickOnTaskName(String orgName) {
        By by = By.partialLinkText(orgName);
        WaitUtils.waitForElementToBeClickable(driver, by, TIMEOUT_10_SECOND);
        WebElement name = driver.findElement(by);
        PageUtils.doubleClick(driver, name);
        return new TaskSection(driver);
    }

    public boolean isTaskVisibleWithName(String orgName) {
        boolean isVisible = true;
        try {
            WaitUtils.waitForElementToBeClickable(driver, By.partialLinkText(orgName), TIMEOUT_5_SECOND);
        } catch (Exception e) {
            isVisible = false;
        }
        return isVisible;
    }

    public boolean isOrganisationDisplayedOnLink(String orgName) {
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD );
        boolean contains = driver.getPageSource().contains(orgName);
        return contains;
    }

    public boolean isDesignationLetterStatusCorrect(String expectedStatus) {
        WaitUtils.waitForElementToBeVisible(driver, letterOfDesignationStatus, TIMEOUT_5_SECOND);
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
        By by = By.xpath(".//td[2]/p[contains(text(),'" + orgName + "')]//following::td[3]");
        WaitUtils.waitForElementToBeClickable(driver, by, TIMEOUT_10_SECOND);
        boolean contains = driver.findElement(by).getText().contains(expectedStatus);
        return contains;

    }

    public boolean isCompletedTaskStatusCorrect1(String orgName, String expectedStatus) {
        By by = By.xpath(".//td[contains(.,'" + orgName + "')]//following::td[4]");
        WaitUtils.waitForElementToBeClickable(driver, by, TIMEOUT_10_SECOND);
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
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
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
        WaitUtils.waitForElementToBeClickable(driver, By.partialLinkText(orgName), TIMEOUT_10_SECOND);

        WebElement tr = PageUtils.getElementMatchingText(listOfWIPTableRows, orgName);
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

    public TaskSection filterWIPTasksBy(String filterBy, String value) {
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);

        if(filterBy.contains("byService")) {
            WaitUtils.waitForElementToBeClickable(driver, tbxSearchByManufacturer, TIMEOUT_15_SECOND);
        }else{
            WaitUtils.waitForElementToBeClickable(driver, byApplicationAssignedTo, TIMEOUT_15_SECOND);
        }

        if(filterBy.contains("byService")){
            PageUtils.selectFromDropDown(driver, byServiceDD, value, true);
        } else if(filterBy.equals("dueDate")){
            dueDate.sendKeys(RandomDataUtils.getDateInFutureDays(2), Keys.TAB);
        } else if(filterBy.contains("byPriority")){
            PageUtils.selectFromDropDown(driver, byPriorityDD, value, true);
        }  else if(filterBy.contains("byApplicationType")){
            PageUtils.selectFromDropDown(driver, byApplicationTypeDD, value, true);
        } else if(filterBy.contains("byApplicationStatus") || filterBy.contains("Application status")){
            PageUtils.selectFromDropDown(driver, byApplicationStatusDD, value, true);
        } else if(filterBy.contains("byApplicationRole")){
            PageUtils.selectFromDropDown(driver, byApplicationRoleDD, value, true);
        } else if(filterBy.contains("byApplicationAssignedTo")){
            try {
                PageUtils.selectFromAutoSuggestedListItemsManufacturers(driver, ".PickerWidget---picker_value", value, true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        tbxSearchByManufacturer.click();
        return new TaskSection(driver);
    }

    @FindBy(xpath = ".//table//th")
    List<WebElement> listOfTableColumns;

    public List<String> isTableColumnCorrect(String[] columns) {
        WaitUtils.waitForElementToBeClickable(driver, By.xpath(".//table//th"), TIMEOUT_10_SECOND);
        List<String> columnsNotFound = PageUtils.areTheColumnsCorrect(columns, listOfTableColumns);
        return columnsNotFound;
    }


    public boolean isPaperClipDisplayed(String orgName) {
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);

        By by = By.xpath(".//td[.='" + orgName + "']//following::td[5]");
        WaitUtils.waitForElementToBeClickable(driver, by, TIMEOUT_10_SECOND);
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

    public List<String> isApplicationReferenceFormatCorrect(int lengthOfReference, String dateFormat) {
        WaitUtils.waitForElementToBeClickable(driver, linkShowFilters, TIMEOUT_10_SECOND);
        List<String> listOfInvalidReferences = new ArrayList<>();
        boolean isValid = true;
        for (WebElement el : listOfApplicationReferences) {
            String reference = el.getText();
            if (reference.length() == lengthOfReference) {
                //First 8 characters are date value
                reference = reference.substring(0, 8);
                isValid = RandomDataUtils.isDateFormatValid(dateFormat, reference);
            } else {
                isValid = false;
            }
            //Add to invalid list
            if (!isValid) {
                listOfInvalidReferences.add(reference);
            }
        }
        return listOfInvalidReferences;
    }

    public TaskSection searchAWIPPageForAccount(String accountNameOrReference) {
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        WaitUtils.waitForElementToBeClickable(driver, tbxSearchByManufacturer, TIMEOUT_30_SECOND);
        tbxSearchByManufacturer.sendKeys(accountNameOrReference);
        btnSearchForManufacuturer.click();
        listOfApplicationReferences.size();
        return new TaskSection(driver);
    }

    public BusinessManufacturerDetails viewAccountByReferenceNumber(String reference) {
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        WaitUtils.waitForElementToBeClickable(driver, By.linkText(reference), TIMEOUT_15_SECOND);
        WebElement referenceToClick = PageUtils.findElementWithText(listOfApplicationReferences, reference);
        referenceToClick.click();
        return new BusinessManufacturerDetails(driver);
    }

    public BusinessManufacturerDetails clickOnReferenceNumberReturnedBySearchResult(int i) {
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        WaitUtils.waitForElementToBeClickable(driver, aApplicationReference, TIMEOUT_15_SECOND);
        WebElement firstMatch = listOfApplicationReferences.get(i - 1);
        firstMatch.click();
        return new BusinessManufacturerDetails(driver);
    }


    public boolean isAWIPTaskStatusCorrect(String status) {
        WaitUtils.waitForElementToBeClickable(driver, aApplicationReference, TIMEOUT_10_SECOND);
        boolean contains = applicationStatus.getAttribute("aria-label").toLowerCase().contains(status);
        return contains;
    }

    public boolean isAWIPApplicationTypeCorrect(String applicationTypeTxt) {
        WaitUtils.waitForElementToBeClickable(driver, aApplicationReference, TIMEOUT_10_SECOND);
        boolean contains = applicationType.getText().contains(applicationTypeTxt);
        return contains;
    }


    public String getTheApplicationReferenceNumber() {
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        WebElement element = listOfApplicationReferences.get(0);
        return element.getText();
    }

    public boolean isSearchingCompleted() {
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        return PageUtils.isElementClickable(driver, btnClearSearchField, TIMEOUT_10_SECOND);
    }

    public boolean isTaskAssignedToCorrectUser(String assignedTo) {
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        WaitUtils.waitForElementToBeClickable(driver, applicationAssignedTo, TIMEOUT_10_SECOND);

        if (assignedTo.contains("Nobody")) {
            //If assigned to nobody than it should be empty
            return applicationAssignedTo.getText().equals("");
        } else {
            boolean contains = applicationAssignedTo.getText().contains(assignedTo);
            return contains;
        }
    }


    public TaskSection expandFilterSection(boolean expand) {
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        if(expand){
            WaitUtils.waitForElementToBeClickable(driver, linkShowFilters, TIMEOUT_15_SECOND);
            linkShowFilters.click();
        }else{
            //Hide it
            WaitUtils.waitForElementToBeClickable(driver, linkHideFilters, TIMEOUT_15_SECOND);
            linkHideFilters.click();
        }
        return new TaskSection(driver);
    }
}