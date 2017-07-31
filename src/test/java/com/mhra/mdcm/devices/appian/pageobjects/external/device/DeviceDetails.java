package com.mhra.mdcm.devices.appian.pageobjects.external.device;

import com.mhra.mdcm.devices.appian.domains.newaccounts.ManufacturerRequestDO;
import com.mhra.mdcm.devices.appian.domains.newaccounts.DeviceDO;
import com.mhra.mdcm.devices.appian.pageobjects._Page;
import com.mhra.mdcm.devices.appian.pageobjects.external.PaymentDetails;
import com.mhra.mdcm.devices.appian.pageobjects.external.manufacturer.ManufacturerDetails;
import com.mhra.mdcm.devices.appian.session.ScenarioSession;
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
 * When viewing devices added to a specific manufacturer
 */
@Component
public class DeviceDetails extends _Page {


    /**
     * --------------------------------------------
     * DR (DEVICE REGISTRATION) RELATED
     * ---------------------------------------------
     */

    //All GMDN definition table data
    @FindBy(xpath = ".//*[contains(text(),'Medical device name')]//following::tr/td[3]")
    List<WebElement> listOfGMDNDefinitions;
    @FindBy(xpath = ".//*[text()='Medical device name']//following::tr/td[3]")
    WebElement firstGMDNDefinition;

    //Table headers for each device types
    @FindBy(xpath = ".//h5[contains(text(),'General Medical')]//following::thead[1]/tr/th")
    List<WebElement> listOfGeneralMedicalDeviceTableHeadings;
    @FindBy(xpath = ".//h5[contains(text(),'Vitro')]//following::thead[1]/tr/th")
    List<WebElement> listOfVitroDiagnosticsDeviceTableHeadings;
    @FindBy(xpath = ".//h5[contains(text(),'Active Implantable')]//following::thead[1]/tr/th")
    List<WebElement> listOfActiveImplantableDeviceTableHeadings;
    @FindBy(xpath = ".//h5[contains(text(),'Procedure Pack')]//following::thead[1]/tr/th")
    List<WebElement> listOfSystemProcedurePackDeviceTableHeadings;


    /**
     * --------------------------------------------
     * CFS RELATED
     * ---------------------------------------------
     */

    @FindBy(xpath = ".//label[contains(text(),'understand the requirements')]")
    WebElement cbxConfirmTermsAndConditions;

    @FindBy(css = ".GridWidget---checkbox")
    List<WebElement> listOfAllCheckbox;
    @FindBy(css = "td.GridWidget---checkbox")
    List<WebElement> listOfDeviceProductCheckbox;
    @FindBy(css = ".PickerWidget---picker_value")
    List<WebElement> listOfCountryPickers;
    @FindBy(xpath = ".//*[contains(text(),'Number of')]//following::input[@type='text']")
    List<WebElement> listOfTbxNumberOfCFS;
    @FindBy(xpath = ".//div[contains(text(),'Number of certificates')]/following::tr/td[1]")
    List<WebElement> listOfCountryNames;
    @FindBy(css = "div.PickerTokenWidget---chip > a.PickerTokenWidget---remove > i")
    List<WebElement> listOfCountryNamesRemoveX;
    @FindBy(xpath = ".//div[contains(text(),'Number of certificates')]/following::tr/td[2]")
    List<WebElement> listOfNumberOfCertificates;
    @FindBy(css = ".DropdownWidget---dropdown_value")
    List<WebElement> listOfDropDownFilters;
    @FindBy(xpath = ".//*[contains(text(), 'GMDN term')]//following::li")
    List<WebElement> listOfGMDNTerms;

    @FindBy(css = "h1.TitleText---page_header")
    WebElement txtManufacturerName;
    @FindBy(xpath = ".//*[contains(text(),'Total number of certificates')]")
    WebElement txtTotalNumberOfCertificates;
    @FindBy(xpath = ".//*[contains(text(),'Price:')]")
    WebElement txtTotalPriceOfCertificates;
    @FindBy(xpath = ".//*[contains(text(),'Number of')]//following::input")
    WebElement tbxNumberOfCFS;
    @FindBy(xpath = ".//*[contains(text(),'Number of')]//following::input[2]")
    WebElement tbxNumberOfCFSNoCountry;
    @FindBy(xpath = ".//*[contains(text(),'specify a country')]")
    WebElement cbxDonotSpecifyACountry;
    @FindBy(css = "th.GridWidget---checkbox")
    WebElement cbxSelectAllDevices;
    @FindBy(xpath = ".//div[contains(text(),'Number of certificates')]//following::p[2]")
    WebElement txtNumberOfCertificates;
    @FindBy(partialLinkText = "Add country")
    WebElement linkAddCountry;
    @FindBy(partialLinkText = "Edit devices")
    WebElement linkEditDevices;
    @FindBy(partialLinkText = "Edit country and")
    WebElement linkEditCountryAndCertificates;

    //Payment methods
    @FindBy(xpath = ".//*[contains(text(),'payment method')]/following::img[1]")
    WebElement paymentWorldPay;
    @FindBy(xpath = ".//*[contains(text(),'payment method')]/following::img[2]")
    WebElement paymentBACS;
    @FindBy(xpath = ".//a[contains(text(),'here')]")
    WebElement linkHereToInitiateWorldpay;
    @FindBy(partialLinkText = "Back to ")
    WebElement linkBackToManufacturer;
    @FindBy(xpath = ".//a[contains(text(),'Proceed to worldpay')]")
    WebElement linkProceedToWorldpay;
    @FindBy(xpath = ".//button[contains(text(),'Complete application')]")
    WebElement btnCompleteApplication;
    @FindBy(xpath = ".//div[@role='listbox']")
    WebElement ddAddressBox;
    @FindBy(css = ".FileUploadWidget---ui-inaccessible")
    WebElement fileUpload;
    @FindBy(xpath = ".//*[contains(text(), 'successfully submitted')]/following::strong[1]")
    WebElement txtApplicationReference;

    //Address fields
    @FindBy(xpath = ".//*[contains(text(), 'printed on CFS')]/following::p[2]")
    WebElement addressToBePrintedOnCFS;
    @FindBy(xpath = ".//*[contains(text(), 'Certificate delivery address')]/following::p[2]")
    WebElement addressCertificateDelivery;
    @FindBy(xpath = ".//*[contains(text(), 'additional address on cert')]//following::div[1]")
    WebElement additionalAddressDD;
    @FindBy(xpath = ".//*[contains(text(), 'addresses on schedule')]//following::div[1]")
    WebElement moreAddressOnScheduleDD;

    //Search and filter CFS devices
    @FindBy(xpath = ".//*[contains(text(), 'Search by medical')]/following::input[1]")
    WebElement tbxMedicalDeviceName;


    //Buttons
    @FindBy(xpath = ".//button[contains(text(), 'Order CFS')]")
    WebElement btnOrderCFS;
    @FindBy(xpath = ".//button[contains(text(), 'Continue')]")
    WebElement btnContinue;
    @FindBy(xpath = ".//button[contains(text(), 'Save & exit')]")
    WebElement btnSaveAndExit;
    @FindBy(xpath = ".//button[contains(text(), 'Continue to Payment')]")
    WebElement btnContinueToPayment;
    @FindBy(xpath = ".//button[contains(text(), 'Submit')]")
    WebElement btnSubmitPayment;
    @FindBy(xpath = ".//button[contains(text(), 'Finish')]")
    WebElement btnFinish;
    @FindBy(xpath = ".//button[contains(text(), 'Search')]")
    WebElement btnSearch;
    @FindBy(xpath = ".//*[contains(text(),'Number of')]//following::button[1]")
    WebElement btnEditDevicesList;
    @FindBy(xpath = ".//button[contains(text(),'Add devices')]")
    WebElement btnAddDevices;
    @FindBy(xpath = ".//button[contains(text(),'Manage devices')]")
    WebElement btnManageDevice;


    @Autowired
    public DeviceDetails(WebDriver driver) {
        super(driver);
    }


    /**
     * Manufacturer details are correct and valid
     *
     * @param manufacaturerData
     * @param deviceData
     * @return
     */
    public boolean isDisplayedDeviceDataCorrect(ManufacturerRequestDO manufacaturerData, DeviceDO deviceData) {
        //Check displayed devices are correct
        String device = "General Medical Device"; //deviceData.gmdnTermOrDefinition;
        boolean allHeadingValid = isDeviceTableHeadingCorrect(deviceData);
        boolean allValid = isDevicesGMDNDisplayedCorrect(device);
        return allValid && allHeadingValid;
    }


    public boolean isDisplayedDeviceDataCorrectForCFS(DeviceDO deviceData) {
        return false;
    }

    private boolean isDeviceTableHeadingCorrect(DeviceDO dd) {
        boolean isCorrect = false;
        String headings = "";
        if (dd.deviceType.toLowerCase().contains("general medical device")) {
            headings = "GMDN code,GMDN definition,Risk classification";
            isCorrect = PageUtils.isTableHeadingCorrect(headings, listOfGeneralMedicalDeviceTableHeadings);
        } else if (dd.deviceType.toLowerCase().contains("vitro diagnostic")) {
            isCorrect = PageUtils.isTableHeadingCorrect(headings, listOfVitroDiagnosticsDeviceTableHeadings);
        } else if (dd.deviceType.toLowerCase().contains("active implantable")) {
            isCorrect = PageUtils.isTableHeadingCorrect(headings, listOfActiveImplantableDeviceTableHeadings);
        } else if (dd.deviceType.toLowerCase().contains("procedure pack")) {
            isCorrect = PageUtils.isTableHeadingCorrect(headings, listOfSystemProcedurePackDeviceTableHeadings);
        }
        return isCorrect;
    }

    public boolean isDevicesGMDNDisplayedCorrect(String deviceList) {
        String[] data = deviceList.split(",");
        WaitUtils.waitForElementToBeClickable(driver, firstGMDNDefinition, TIMEOUT_10_SECOND);

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
                if (gmdn.contains(d.toLowerCase())) {
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

    public DeviceDetails clickOrderCFSButton() {
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        WaitUtils.waitForElementToBeClickable(driver, btnOrderCFS, TIMEOUT_15_SECOND);
        btnOrderCFS.click();
        return new DeviceDetails(driver);
    }

    public DeviceDetails selectADevices() {
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        WaitUtils.waitForElementToBeClickable(driver, cbxSelectAllDevices, TIMEOUT_10_SECOND);
        WebElement cbx = PageUtils.getRandomElementFromList(listOfDeviceProductCheckbox);
        PageUtils.singleClick(driver, cbx);
        //Wait for continue button to be clickable
        WaitUtils.waitForElementToBeClickable(driver, btnContinue, TIMEOUT_5_SECOND);
        PageUtils.singleClick(driver, btnContinue);
        return new DeviceDetails(driver);
    }

    public DeviceDetails selectAllDevices() {
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD * 2);
        WaitUtils.waitForElementToBeClickable(driver, cbxSelectAllDevices, TIMEOUT_10_SECOND);
        cbxSelectAllDevices.click();

        //Wait for continue button to be clickable
        WaitUtils.waitForElementToBeClickable(driver, btnContinue, TIMEOUT_5_SECOND);
        PageUtils.singleClick(driver, btnContinue);
        return new DeviceDetails(driver);
    }

    public DeviceDetails enterACertificateDetails(String countryName, String noOfCFS, boolean continueToNextStep) {
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);

        if (countryName == null || countryName.equals("")) {
            WaitUtils.waitForElementToBeClickable(driver, cbxDonotSpecifyACountry, TIMEOUT_10_SECOND);
            //Enter number of certificates
            WaitUtils.waitForElementToBeClickable(driver, tbxNumberOfCFSNoCountry, TIMEOUT_5_SECOND);
            tbxNumberOfCFSNoCountry.sendKeys(noOfCFS);
            cbxDonotSpecifyACountry.click();
        } else {
            WaitUtils.waitForElementToBeClickable(driver, By.cssSelector(".PickerWidget---picker_value"), TIMEOUT_10_SECOND);
            try {
                PageUtils.selectFromAutoSuggestedListItemsManufacturers(driver, ".PickerWidget---picker_value", countryName, true);
            } catch (Exception e) {
            }
            //Enter number of certificates
            WaitUtils.waitForElementToBeClickable(driver, tbxNumberOfCFS, TIMEOUT_5_SECOND);
            tbxNumberOfCFS.sendKeys(noOfCFS);
        }

        txtTotalNumberOfCertificates.click();
        //Submit
        if (continueToNextStep)
            btnContinue.click();
        return new DeviceDetails(driver);

    }

    public DeviceDetails enterMultipleCertificateDetails(String data, boolean clickAddCountryLink, int whichLine) {
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        WaitUtils.waitForElementToBeClickable(driver, linkAddCountry, TIMEOUT_10_SECOND);

        String[] values = data.split("=");
        if (values.length == 2) {
            String countryName = values[0];
            String noOfCFS = values[1];
            try {
                //Select a country
                PageUtils.selectFromAutoSuggestedListItemsManufacturers(driver, listOfCountryPickers.get(whichLine - 1), countryName);
            } catch (Exception e) {
            }

            //Enter number of certificates
            listOfTbxNumberOfCFS.get(whichLine - 1).sendKeys(noOfCFS);

            if (clickAddCountryLink)
                linkAddCountry.click();
        } else {
            log.info("Invalid CFS Country pair data : " + data);
        }

        return new DeviceDetails(driver);
    }

    public DeviceDetails clickContinueButton() {
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        WaitUtils.waitForElementToBeClickable(driver, btnContinue, TIMEOUT_10_SECOND);
        btnContinue.click();
        return new DeviceDetails(driver);
    }

    public DeviceDetails agreeToTermsAndConditions() {
        WaitUtils.waitForElementToBeClickable(driver, cbxConfirmTermsAndConditions, TIMEOUT_10_SECOND);
        cbxConfirmTermsAndConditions.click();
        return new DeviceDetails(driver);
    }

    public DeviceDetails continueToPaymentAfterReviewFinished() {
        //WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        WaitUtils.waitForElementToBeClickable(driver, btnContinueToPayment, TIMEOUT_10_SECOND);
        btnContinueToPayment.click();
        return new DeviceDetails(driver);
    }

    public ManufacturerDetails saveAndExitCFSOrderApplication() {
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        WaitUtils.waitForElementToBeClickable(driver, btnSaveAndExit, TIMEOUT_10_SECOND);
        btnSaveAndExit.click();
        return new ManufacturerDetails(driver);
    }

    public DeviceDetails submitPayment() {
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        WaitUtils.waitForElementToBeClickable(driver, btnSubmitPayment, TIMEOUT_10_SECOND);
        btnSubmitPayment.click();
        return new DeviceDetails(driver);
    }

    public DeviceDetails finishPayment() {
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        WaitUtils.waitForElementToBeClickable(driver, btnFinish, TIMEOUT_10_SECOND);
        btnFinish.click();
        return new DeviceDetails(driver);
    }

    public boolean isNumberOfCertificatesCorrect(String number) {
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        WaitUtils.waitForElementToBeClickable(driver, txtNumberOfCertificates, TIMEOUT_15_SECOND);
        String txt = txtNumberOfCertificates.getText().trim();
        return number.equals(txt);
    }

    public boolean isManufacturerNameCorrect(String name) {
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        WaitUtils.waitForElementToBeClickable(driver, txtManufacturerName, TIMEOUT_10_SECOND);
        String txt = txtManufacturerName.getText().trim();
        return txt.contains(name);
    }

    public boolean areTheCountriesDisplayedCorrect(String[] data) {
        List<String> countries = CommonUtils.getListOfCountries(data);
        List<String> listOfTexts = CommonUtils.getListOfText(listOfCountryNames);
        System.out.println("List of countries expected : " + countries);
        System.out.println("List of countries displayed : " + listOfTexts);

        boolean isCorrect = true;
        for (String country : countries) {
            if (!listOfTexts.contains(country.trim())) {
                log.error("Country name not found : " + country);
                isCorrect = false;
                break;
            }
        }

        return isCorrect;
    }

    public boolean areTheCertificateCountCorrect(String[] data) {
        List<String> listOfData = CommonUtils.getListOfData(data);

        int count = 0;
        boolean isDataCorrect = true;

        for (WebElement el : listOfCountryNames) {
            String countryAtPositionX = listOfCountryNames.get(count).getText();
            String numberOfCertAtPositionX = listOfNumberOfCertificates.get(count).getText();
            String toCheck = countryAtPositionX + "=" + numberOfCertAtPositionX;

            if (!listOfData.contains(toCheck)) {
                isDataCorrect = false;
                break;
            }
            count++;
        }

        return isDataCorrect;
    }

    public DeviceDetails clickEditButton() {
        btnEditDevicesList.click();
        return new DeviceDetails(driver);
    }

    public DeviceDetails clickEditCountryAndCertificateLink() {
        linkEditCountryAndCertificates.click();
        return new DeviceDetails(driver);
    }

    public boolean isTotalNumberOfCertificatesCorrect(String numberOfCFS) {
        boolean valid = txtTotalNumberOfCertificates.getText().contains(numberOfCFS);
        return valid;
    }


    public boolean isTotalNumberOfCertificatesCorrect(String[] data) {
        List<String> counts = CommonUtils.getListOfCertificateCounts(data);
        int total = 0;
        for (String c : counts) {
            total = total + Integer.parseInt(c);
        }
        boolean valid = txtTotalNumberOfCertificates.getText().contains(Integer.toString(total));
        return valid;
    }

    public DeviceDetails updateCountryNumber(int pos, String countryName) {
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        WaitUtils.waitForElementToBeClickable(driver, tbxNumberOfCFS, TIMEOUT_5_SECOND);
        WebElement element = listOfCountryNamesRemoveX.get(pos - 1);
        element.click();

        txtManufacturerName.click();
        //Reenter value
        try {
            PageUtils.selectFromAutoSuggestedListItemsManufacturers(driver, ".PickerWidget---picker_value", countryName, true);
        } catch (Exception e) {
        }
        return new DeviceDetails(driver);
    }

    public DeviceDetails updateNumberOfCFS(int pos, String numberOfCFS) {
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        WaitUtils.waitForElementToBeClickable(driver, tbxNumberOfCFS, TIMEOUT_5_SECOND);
        WebElement element = listOfTbxNumberOfCFS.get(pos - 1);
        element.clear();
        element.sendKeys(numberOfCFS);
        return new DeviceDetails(driver);
    }

    public boolean isTotalCostOfCertificatesCorrect(int totalCost) {
        //Verify total cost
        boolean isCorrect = txtTotalPriceOfCertificates.getText().contains(String.valueOf(totalCost));
        return isCorrect;
    }

    public DeviceDetails selectAGMDNTerm(String gmdn) {
        WaitUtils.waitForElementToBeClickable(driver, btnSearch, TIMEOUT_15_SECOND);
        PageUtils.selectFromDropDown(driver, listOfDropDownFilters.get(0), gmdn, false);
        return new DeviceDetails(driver);
    }

    public DeviceDetails selectARandomGMDNTerm() {
        WaitUtils.waitForElementToBeClickable(driver, btnSearch, TIMEOUT_15_SECOND);
        listOfDropDownFilters.get(0).click();

        //Get gmdn terms available and select the first one: @todo Change to randomly select
        List<String> listOfOptions = PageUtils.getListOfElementsForDropDown(listOfGMDNTerms);
        PageUtils.selectFromDropDown(driver, listOfDropDownFilters.get(0), listOfOptions.get(0), false);
        return new DeviceDetails(driver);
    }

    public DeviceDetails selectARandomGMDNTerm(String searchTerm) {
        WaitUtils.waitForElementToBeClickable(driver, btnSearch, TIMEOUT_15_SECOND);
        WebElement element = listOfDropDownFilters.get(0);
        element.click();

        //Get gmdn terms available and select the first one: @todo Change to randomly select
        List<String> listOfOptions = PageUtils.getListOfElementsForDropDown(listOfGMDNTerms);
        String option = PageUtils.findOptionMatchingSearchTerm(listOfOptions, searchTerm);
        PageUtils.selectFromDropDown(driver, listOfDropDownFilters.get(0), option, false);
        return new DeviceDetails(driver);
    }

    public DeviceDetails enterPaymentDetails(String paymentMethod, ScenarioSession scenarioSession) {
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        WaitUtils.waitForElementToBeClickable(driver, ddAddressBox, TIMEOUT_15_SECOND);

        //Select billing address:
        PageUtils.selectFromDropDown(driver, ddAddressBox, "Registered Address", false);

        if (paymentMethod.toLowerCase().contains("world")) {
            WaitUtils.waitForElementToBeClickable(driver, paymentWorldPay, TIMEOUT_15_SECOND);
            paymentWorldPay.click();
            //Click "here" link
            WaitUtils.waitForElementToBeClickable(driver, linkHereToInitiateWorldpay, TIMEOUT_10_SECOND);
            linkHereToInitiateWorldpay.click();
            //Link "Proceed to worldpay"
            WaitUtils.waitForElementToBeClickable(driver, linkProceedToWorldpay, TIMEOUT_10_SECOND);
            linkProceedToWorldpay.click();

            //Focus on different tab
            PaymentDetails payment = new PaymentDetails(driver);
            payment.performWorldPayPayment("Card Details", scenarioSession);

            //When completed
            WaitUtils.waitForElementToBeClickable(driver, linkHereToInitiateWorldpay, TIMEOUT_10_SECOND);
            PageFactory.initElements(driver, this);
            linkHereToInitiateWorldpay.click();

        } else if (paymentMethod.toLowerCase().contains("bacs")) {
            paymentBACS.click();
            WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
            PageUtils.uploadDocument(fileUpload, "CompletionOfTransfer1.pdf", 1, 3);
        }

        //Complete the application
        WaitUtils.waitForElementToBeClickable(driver, btnCompleteApplication, TIMEOUT_10_SECOND);
        btnCompleteApplication.click();
        return new DeviceDetails(driver);
    }

    public String getApplicationReferenceNumber() {
        WaitUtils.waitForElementToBeClickable(driver, linkBackToManufacturer, TIMEOUT_15_SECOND);
        WaitUtils.waitForElementToBeClickable(driver, txtApplicationReference, TIMEOUT_15_SECOND);
        return txtApplicationReference.getText();
    }

    public DeviceDetails searchByMedicalDeviceName(String searchTerm) {
        WaitUtils.waitForElementToBeClickable(driver, tbxMedicalDeviceName, TIMEOUT_10_SECOND);
        tbxMedicalDeviceName.clear();
        tbxMedicalDeviceName.sendKeys(searchTerm);
        btnSearch.click();
        return new DeviceDetails(driver);
    }

    public boolean isDeviceFound() {
        //WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        WaitUtils.waitForElementToBeClickable(driver, btnSearch, TIMEOUT_10_SECOND);
        return listOfDeviceProductCheckbox.size() > 0;
    }

    public boolean isNumberOfProductsDisplayedCorrect(int expected) {
        //WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        WaitUtils.waitForElementToBeClickable(driver, btnSearch, TIMEOUT_10_SECOND);
        return listOfDeviceProductCheckbox.size() == expected;
    }

    public AddDevices clickAddDeviceBtn() {
        WaitUtils.waitForElementToBeClickable(driver, btnAddDevices, TIMEOUT_15_SECOND);
        PageUtils.singleClick(driver, btnAddDevices);
        //btnAddDevices.click();
        return new AddDevices(driver);
    }

    public DeviceDetails clickManageDevices() {
        WaitUtils.waitForElementToBeClickable(driver, btnManageDevice, TIMEOUT_15_SECOND);
        PageUtils.doubleClick(driver, btnManageDevice);
        //btnManageDevice.click();
        return new DeviceDetails(driver);
    }

    public boolean isAddressToBePrintedOnCFSVisible() {
        return PageUtils.isElementClickable(driver, addressToBePrintedOnCFS, TIMEOUT_10_SECOND);
    }

    public boolean isAdditionalAddressVisible() {
        return PageUtils.isElementClickable(driver, additionalAddressDD, TIMEOUT_10_SECOND);
    }

    public boolean isMoreAddressesOnScheduleVisible() {
        return PageUtils.isElementClickable(driver, moreAddressOnScheduleDD, TIMEOUT_10_SECOND);
    }

    public boolean isSiteAddressVisible() { return false; }

    public boolean isAuthorisedRepAddressVisible() {
        return false;
    }

    public boolean isDistributorAddressVisible() {
        return false;
    }

    public boolean isDeliveryAddressVisible() {
        return PageUtils.isElementClickable(driver, addressCertificateDelivery, TIMEOUT_10_SECOND);
    }


    public boolean isInDeviceDetailsPage(int timeoutSecond) {
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        try {
            WaitUtils.waitForElementToBeClickable(driver, btnManageDevice, timeoutSecond);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
