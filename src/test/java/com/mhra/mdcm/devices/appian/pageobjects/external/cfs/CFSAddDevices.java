package com.mhra.mdcm.devices.appian.pageobjects.external.cfs;

import com.mhra.mdcm.devices.appian.domains.newaccounts.DeviceDO;
import com.mhra.mdcm.devices.appian.pageobjects._Page;
import com.mhra.mdcm.devices.appian.pageobjects.external.manufacturer.ManufacturerDetails;
import com.mhra.mdcm.devices.appian.utils.selenium.others.RandomDataUtils;
import com.mhra.mdcm.devices.appian.utils.selenium.others.TestHarnessUtils;
import com.mhra.mdcm.devices.appian.utils.selenium.page.CommonUtils;
import com.mhra.mdcm.devices.appian.utils.selenium.page.PageUtils;
import com.mhra.mdcm.devices.appian.utils.selenium.page.WaitUtils;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by TPD_Auto
 */
@Component
public class CFSAddDevices extends _Page {

    public static String gmdnSelected = null;

    @FindBy(css = ".RadioButtonGroup---choice_pair>label")
    List<WebElement> listOfDeviceTypes;
    @FindBy(xpath = ".//label[contains(text(),'GMDN Code')]//following::a[string-length(text()) > 0]")
    WebElement aGmdnMatchesReturnedBySearch;
    @FindBy(xpath = ".//label[contains(text(),'GMDN Code')]//following::a[string-length(text()) > 0]")
    List<WebElement> listOfGmdnMatchesReturnedBySearch;
    @FindBy(css = ".ParagraphText---richtext_paragraph .StrongText---richtext_strong")
    WebElement labelValidGMDNCodeMessage;

    //Device types radio buttons
    @FindBy(xpath = ".//label[contains(text(),'General Medical')]")
    WebElement generalMedicalDevice;
    @FindBy(xpath = ".//label[contains(text(),'Vitro Diagnostic Device')]")
    WebElement inVitroDiagnosticDevice;
    @FindBy(xpath = ".//label[contains(text(),'Active Implantable')]")
    WebElement activeImplantableMedicalDevice;
    @FindBy(xpath = ".//label[contains(text(),'Procedure Pack')]")
    WebElement systemOrProcedurePack;

    //GMDN search by selecting a radio button
    @FindBy(css = "input[type='text']")
    WebElement tbxGMDNDefinitionOrTerm;

    //Custom made, sterile and measuring
    @FindBy(xpath = ".//span[contains(text(),'custom made')]//following::label[1]")
    WebElement radioCustomMadeYes;
    @FindBy(xpath = ".//span[contains(text(),'custom made')]//following::label[2]")
    WebElement radioCustomMadeNo;
    @FindBy(xpath = ".//span[contains(text(),'device sterile')]//following::label[1]")
    WebElement radioDeviceSterileYes;
    @FindBy(xpath = ".//span[contains(text(),'device sterile')]//following::label[2]")
    WebElement radioDeviceSterileNo;
    @FindBy(xpath = ".//span[contains(text(),'device measuring')]//following::label[1]")
    WebElement radioDeviceMeasuringYes;
    @FindBy(xpath = ".//span[contains(text(),'device measuring')]//following::label[2]")
    WebElement radioDeviceMeasuringNo;

    //Custom Made = No, Then enter risk classification
    @FindBy(xpath = ".//span[contains(text(),'risk class')]//following::label[1]")
    WebElement radioRiskClass1;
    @FindBy(xpath = ".//span[contains(text(),'risk class')]//following::label[2]")
    WebElement radioRiskClass2a;
    @FindBy(xpath = ".//span[contains(text(),'risk class')]//following::label[3]")
    WebElement radioRiskClass2b;
    @FindBy(xpath = ".//span[contains(text(),'risk class')]//following::label[4]")
    WebElement radioRiskClass3;

    //Notified bodies
    @FindBy(xpath = ".//span[contains(text(),'Notified Body')]//following::label[1]")
    WebElement nb0086BSI;
    @FindBy(xpath = ".//span[contains(text(),'Notified Body')]//following::label[2]")
    WebElement nb0088Lloyd;
    @FindBy(xpath = ".//span[contains(text(),'Notified Body')]//following::label[3]")
    WebElement nb0120SGS;
    @FindBy(xpath = ".//span[contains(text(),'Notified Body')]//following::label[4]")
    WebElement nb0473Amtac;
    @FindBy(xpath = ".//span[contains(text(),'Notified Body')]//following::label[5]")
    WebElement nb0843ULI;
    @FindBy(xpath = ".//span[contains(text(),'Notified Body')]//following::label[6]")
    WebElement nbOther;

    //List of notified bodies
    @FindBy(xpath = ".//span[contains(text(),'Notified Body')]//following::input[@type='radio']//following::label")
    List<WebElement> listOfNotifiedBodies;

    //Add product
    @FindBy(xpath = ".//button[.='Add product']")
    WebElement addProduct;
    @FindBy(xpath = ".//button[contains(text(),'Add Product')]")
    WebElement addProduct2;
    @FindBy(xpath = ".//*[contains(text(),'Medical Device Name')]//following::input[1]")
    WebElement pdProductName;
    @FindBy(xpath = ".//*[contains(text(),'and model')]//following::input[2]")
    WebElement pdProductModel;

    //Option to add other devices
    @FindBy(xpath = ".//button[contains(text(),'Add another device')]")
    WebElement btnAddAnotherDevice;

    //File upload buttons
    @FindBy(css = ".FileUploadWidget---ui-inaccessible")
    WebElement fileUpload;
    @FindBy(xpath = ".//button[.='Upload']")
    List<WebElement> listOfFileUploads;

    //Error messages
    @FindBy(css = ".FieldLayout---field_error")
    List<WebElement> alreadyExistsErrorMessages;
    @FindBy(css = ".FieldLayout---field_error")
    WebElement errMessage;
    @FindBy(css = ".MessageLayout---error")
    WebElement mhrsErrorMessage;
    @FindBy(css = ".FieldLayout---field_error")
    List<WebElement> fieldErrorMessages;
    @FindBy(css = ".FieldLayout---field_error")
    WebElement validationErrMessage;

    //Device Summary
    @FindBy(xpath = ".//div[contains(text(),'Term name')]//following::a")
    List<WebElement> listOfGMDNLinksInSummary;
    @FindBy(partialLinkText = "Change Notified Body")
    WebElement linkChangeNotifiedBody;

    //All GMDN table
    @FindBy(xpath = ".//*[contains(text(),'Term definition')]//following::tr/td[2]")
    List<WebElement> listOfAllGmdnTermDefinitions;

    //Links
    @FindBy(partialLinkText = "View all GMDN terms")
    WebElement viewAllGMDNTermDefinition;
    @FindBy(partialLinkText = "Back to service")
    WebElement linkBackToService;
    @FindBy(xpath = ".//*[contains(@aria-label, 'Clear file')]")
    WebElement imgClearFileSelected;

    //Confirm and btnDeclareDevices
    @FindBy(xpath = ".//button[.='Continue']")
    WebElement btnProceedToPayment;
    @FindBy(css = ".CheckboxGroup---choice_pair>label")
    WebElement cbxConfirmInformation;
    @FindBy(xpath = ".//label[contains(text(),'understand the requirements')]")
    WebElement cbxConfirmTermsAndConditions;

    //Submit and save buttons
    @FindBy(xpath = ".//button[.='Add device']")
    WebElement btnAddDevice;
    @FindBy(xpath = ".//button[.='Save']")
    WebElement btnSaveProgress;
    @FindBy(xpath = ".//button[contains(text(), 'Save & exit')]")
    WebElement btnSaveAndExit;
    @FindBy(xpath = ".//button[.='Continue']")
    WebElement btnContinue;
    @FindBy(xpath = ".//button[.='Upload certificate']")
    WebElement btnUploadCertificate;
    @FindBy(xpath = ".//button[.='Submit for approval']")
    WebElement btnSubmitForApproval;
    @FindBy(xpath = ".//button[.='Back']")
    WebElement btnBack;

    //Add CE Certificate details
    @FindBy(xpath = ".//td[2]")
    List<WebElement> listOfAllCECertificates;
    @FindBy(css = ".DropdownWidget---dropdown_value")
    List<WebElement> listOfDropDownFilters;
    @FindBy(xpath = ".//*[contains(text(), 'Devices')]/following::h2/a")
    List<WebElement> listOfDevicesAdded;
    @FindBy(css = "input.DatePickerWidget---text")
    WebElement datePicker;
    @FindBy(xpath = ".//*[contains(text(),'reference number')]//following::input[1]")
    WebElement tbxCertificateReferenceNumber;
    @FindBy(xpath = ".//h3[contains(text(),'Add products')]")
    WebElement headingProductsPage;
    @FindBy(xpath = ".//h3[contains(text(),'Upload CE')]")
    WebElement headingCECertificatesPage;
    @FindBy(xpath = ".//td[8]")
    WebElement removeCertificate;

    //Delete device or application
    @FindBy(xpath = ".//*[contains(text(), 'Devices')]/following::button[text()='Delete Device']")
    List<WebElement> listOfDeleteButtons;
    @FindBy(xpath = ".//*[contains(text(), 'Devices')]/following::button[text()='Delete Application']")
    WebElement deleteApplicationButtons;

    //Application completed: reference number
    @FindBy(xpath = ".//*[contains(text(), 'successfully submitted')]/following::strong[1]")
    WebElement txtApplicationReference;
    @FindBy(partialLinkText = "Back to ")
    WebElement linkBackToManufacturersDetails;


    @Autowired
    public CFSAddDevices(WebDriver driver) {
        super(driver);
    }

    public CFSAddDevices addDevice() {
        WaitUtils.waitForElementToBeClickable(driver, btnAddDevice, TIMEOUT_10_SECOND);
        btnAddDevice.click();
        return new CFSAddDevices(driver);
    }

    public boolean isDeviceTypeCorrect() {
        boolean allCorrect = false;

        WaitUtils.waitForElementToBeClickable(driver, generalMedicalDevice, TIMEOUT_10_SECOND);
        for (WebElement e : listOfDeviceTypes) {
            String text = e.getText();
            if (text.toLowerCase().contains("general medical") || text.toLowerCase().contains("in vitro diagnostic") ||
                    text.toLowerCase().contains("active implantable") || text.toLowerCase().contains("system or procedure pack")) {
                allCorrect = true;
            } else {
                allCorrect = false;
                break;
            }
        }

        return allCorrect;
    }


    public boolean isErrorMessageDisplayed(String message) {
        boolean isDisplayed = false;
        try {
            WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
            WaitUtils.waitForElementToBeClickable(driver, errMessage, TIMEOUT_1_SECOND);
            for (WebElement msg : alreadyExistsErrorMessages) {
                String txt = msg.getText();
                System.out.println("Error message : " + txt);
                isDisplayed = txt.toLowerCase().contains(message.toLowerCase());
                if (isDisplayed) {
                    break;
                }
            }
        } catch (Exception e) {
            isDisplayed = false;
        }
        return isDisplayed;
    }

    public boolean isFieldErrorMessageDisplayed(String message) {
        try {
            WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
            boolean isDisplayed = false;
            for (WebElement msg : fieldErrorMessages) {
                String txt = msg.getText();
                System.out.println("Error message : " + txt);
                isDisplayed = txt.toLowerCase().contains(message.toLowerCase());
                if (isDisplayed) {
                    break;
                }
            }
            return isDisplayed;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isMHRAErrorMessageCorrect(String expectedErrorMsg) {
        WaitUtils.waitForElementToBeVisible(driver, mhrsErrorMessage, 10);
        boolean contains = mhrsErrorMessage.getText().contains(expectedErrorMsg);
        return contains;
    }

    public CFSAddDevices addFollowingDevice(DeviceDO dd, boolean isRegistered) {
        //WaitUtils.isPageLoadingCompleteInMilliseconds(driver, TIMEOUT_PAGE_LOAD);
        WaitUtils.waitForElementToBeClickable(driver, generalMedicalDevice, TIMEOUT_15_SECOND);
        WaitUtils.waitForElementToBeClickable(driver, systemOrProcedurePack, TIMEOUT_3_SECOND);
        //Select device type
        selectDeviceType(dd);

        if (dd.deviceType.toLowerCase().contains("general medical device")) {
            addGeneralMedicalDevice(dd);
        } else if (dd.deviceType.toLowerCase().contains("vitro diagnostic")) {
            addVitroDiagnosticDevice(dd);
        } else if (dd.deviceType.toLowerCase().contains("active implantable")) {
            addActiveImplantableDevice(dd);
        } else if (dd.deviceType.toLowerCase().contains("procedure pack")) {
            addProcedurePackDevice(dd);
        } else {
            //Verify all error messages if possible
        }

        //Business doing testing so don't do any write only tests
//        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
//
//        if(isRegistered){
//            WaitUtils.waitForElementToBeClickable(driver, btnReviewYourOrder, TIMEOUT_10_SECOND);
//            PageUtils.doubleClick(driver, btnReviewYourOrder);
//        }else {
//            WaitUtils.waitForElementToBeClickable(driver, btnSaveProgress, TIMEOUT_10_SECOND);
//            PageUtils.doubleClick(driver, btnSaveProgress);
//        }

        return new CFSAddDevices(driver);
    }


    public CFSAddDevices addPartiallyFilledDevices(DeviceDO dd) {
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        WaitUtils.waitForElementToBeClickable(driver, generalMedicalDevice, TIMEOUT_DEFAULT);
        WaitUtils.waitForElementToBeClickable(driver, systemOrProcedurePack, TIMEOUT_3_SECOND);
        //Select device type
        selectDeviceType(dd);

        if (dd.deviceType.toLowerCase().contains("general medical device")) {
            addGeneralMedicalDevice(dd);
        } else if (dd.deviceType.toLowerCase().contains("vitro diagnostic")) {
            addVitroDiagnosticDevice(dd);
        } else if (dd.deviceType.toLowerCase().contains("active implantable")) {
            addActiveImplantableDevice(dd);
        } else if (dd.deviceType.toLowerCase().contains("procedure pack")) {
            addProcedurePackDevice(dd);
        } else {
            //Verify all error messages if possible
        }

        return new CFSAddDevices(driver);
    }


    public CFSAddDevices selectADeviceWithoutAddingOtherDetails(DeviceDO dd) {
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        WaitUtils.waitForElementToBeClickable(driver, generalMedicalDevice, TIMEOUT_DEFAULT);
        WaitUtils.waitForElementToBeClickable(driver, systemOrProcedurePack, TIMEOUT_3_SECOND);
        //Select device type
        selectDeviceType(dd);
        searchByGMDNForPartiallyFilledDevice(dd);

        return new CFSAddDevices(driver);
    }

    private void addProcedurePackDevice(DeviceDO dd) {
        //In CFS if device type is System or Procedure Pack, Than it should show an error message
    }

    private void addVitroDiagnosticDevice(DeviceDO dd) {
        //In CFS if device type is In Vitro Diagnostic Device, Than it should show an error message
    }

    private void addActiveImplantableDevice(DeviceDO dd) {
        if (dd.addDevices) {
            searchByGMDN(dd);
            customMade(dd);

            //In CFS if custom made = yes, Than it should show an error message @5207
            if (!dd.isCustomMade) {
                clickContinue();

                if (dd.addCertificate) {
                    uploadCECertificates(dd);

                    if (dd.addProducts) {
                        clickContinue();
                        //Add more than 1 products
                        if (dd.listOfProductName.size() > 0) {
                            for (String name : dd.listOfProductName) {
                                dd.productName = name;
                                if (dd.productModel == null) {
                                    dd.productModel = RandomDataUtils.getRandomTestName("Model1");
                                }
                                addProduct(dd);
                                dd.productModel = null;
                            }
                        } else {
                            addProduct(dd);
                        }
                        clickContinue();
                    } else {
                        //WaitUtils.waitForElementToBeClickable(driver, addProduct, TIMEOUT_5_SECOND);
                    }
                } else {
                    WaitUtils.waitForElementToBeClickable(driver, btnUploadCertificate, TIMEOUT_5_SECOND);
                }
            }
        }
    }


    private void addGeneralMedicalDevice(DeviceDO dd) {

        if (dd.addDevices) {
            searchByGMDN(dd);
            customMade(dd);

            //In CFS if custom made = yes, Than it should show an error message @5207
            if (!dd.isCustomMade) {
                riskClassification(dd);

                //In CFS if risk classification is class1 , Than it should show an error message @5207
                if (!dd.riskClassification.equals("class1")) {
                    deviceSterile(dd);

                    if (dd.addCertificate) {
                        clickContinue();
                        uploadCECertificates(dd);

                        if (dd.addProducts) {
                            clickContinue();
                            //Add more than 1 products : Not sure why we need to add product for GMD
                            if (dd.listOfProductName.size() > 0) {
                                for (String name : dd.listOfProductName) {
                                    dd.productName = name;
                                    if (dd.productModel == null) {
                                        String model = RandomDataUtils.getRandomTestName("Model1");
                                        dd.productModel = model;
                                        dd.listOfModelName.add(model);
                                    }
                                    addProduct(dd);
                                    dd.productModel = null;
                                }
                            } else {
                                if (dd.productName == null) {
                                    dd.productName = RandomDataUtils.getRandomTestName("Product1");
                                }
                                if (dd.productModel == null) {
                                    String model = RandomDataUtils.getRandomTestName("Model1");
                                    dd.productModel = model;
                                    dd.listOfModelName.add(model);
                                }
                                addProduct(dd);
                            }
                            clickContinue();
                        } else {
                            //WaitUtils.waitForElementToBeClickable(driver, addProduct, TIMEOUT_5_SECOND);
                        }
                    } else {
                        //WaitUtils.waitForElementToBeClickable(driver, btnUploadCertificate, TIMEOUT_5_SECOND);
                    }
                }
            }
        }

    }

    private void clickUploadCertificate() {
        WaitUtils.waitForElementToBeClickable(driver, btnUploadCertificate, TIMEOUT_10_SECOND);
        PageUtils.doubleClick(driver, btnUploadCertificate);
    }

    private void uploadCECertificates(DeviceDO dd) {
        //WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        //PageFactory.initElements(driver, this);

        WaitUtils.waitForElementToBeClickable(driver, datePicker, TIMEOUT_15_SECOND);
        String docType = dd.docType;
        String[] docTypes = docType.split(",");

        int count = 1;
        for (String dt : docTypes) {
            if (count > 1) {
                PageUtils.singleClick(driver, imgClearFileSelected);
            }
            String certName = "CECertificate" + dd.deviceCount + "." + dt;
            PageUtils.uploadDocument(fileUpload, "certs", certName, 1, 3);
            dd.listOfCertificates.add(certName);
            count++;
        }

        //Select certificate type and enter date
        PageUtils.selectFromDropDown(driver, listOfDropDownFilters.get(0), dd.certificateType, false);
        datePicker.sendKeys(RandomDataUtils.getDateInFutureMonths(dd.monthsInFutureOrPast), Keys.TAB);
        String ctsRef = RandomDataUtils.getRandomTestName("CTS").replace("_", "");
        tbxCertificateReferenceNumber.sendKeys(ctsRef);
        dd.ctsCertificateReference = ctsRef;

        boolean isErrorMessageDisplayed = fieldErrorMessages.size() > 0;

        if (!isErrorMessageDisplayed) {
            //select notified body
            notifiedBody(dd);
            WaitUtils.nativeWaitInSeconds(1);
            clickUploadCertificate();
        }
    }

    private void clickContinue() {
        //WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        WaitUtils.waitForElementToBeVisible(driver, btnContinue, TIMEOUT_10_SECOND);
        WaitUtils.waitForElementToBeClickable(driver, btnContinue, TIMEOUT_10_SECOND);
        PageUtils.doubleClick(driver, btnContinue);
    }


    private void addProduct(DeviceDO dd) {
        //WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        //PageUtils.clickOneOfTheFollowing(driver, addProduct, addProduct2, TIMEOUT_5_SECOND);

        WaitUtils.waitForElementToBeClickable(driver, pdProductName, TIMEOUT_10_SECOND);
        pdProductName.sendKeys(dd.productName);
        pdProductModel.sendKeys(dd.productModel);

        PageUtils.clickOneOfTheFollowing(driver, addProduct, addProduct2, TIMEOUT_5_SECOND);
    }


    private void notifiedBody(DeviceDO dd) {
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        changeNotifiedBody();
        boolean notifiedBodyOptionsCorrect = isNotifiedBodyListDisplayingCorrectDetails();

        WaitUtils.waitForElementToBeVisible(driver, nbOther, TIMEOUT_5_SECOND);
        WaitUtils.waitForElementToBeClickable(driver, nbOther, TIMEOUT_5_SECOND);

        //Select notified body
        if (notifiedBodyOptionsCorrect && dd.notifiedBody != null && dd.notifiedBody.toLowerCase().contains("bsi")) {
            //WaitUtils.waitForElementToBeClickable(driver, nb0086BSI, TIMEOUT_5_SECOND);
            PageUtils.singleClick(driver, nb0086BSI);
        } else if (notifiedBodyOptionsCorrect && dd.notifiedBody != null && dd.notifiedBody.toLowerCase().contains("lloyd")) {
            //WaitUtils.waitForElementToBeClickable(driver, nb0088Lloyd, TIMEOUT_5_SECOND);
            PageUtils.singleClick(driver, nb0088Lloyd);
        } else if (notifiedBodyOptionsCorrect && dd.notifiedBody != null && dd.notifiedBody.toLowerCase().contains("sgs")) {
            //WaitUtils.waitForElementToBeClickable(driver, nb0120SGS, TIMEOUT_5_SECOND);
            PageUtils.singleClick(driver, nb0120SGS);
        } else if (notifiedBodyOptionsCorrect && dd.notifiedBody != null && dd.notifiedBody.toLowerCase().contains("amtac")) {
            //WaitUtils.waitForElementToBeClickable(driver, nb0473Amtac, TIMEOUT_5_SECOND);
            PageUtils.singleClick(driver, nb0473Amtac);
        } else if (notifiedBodyOptionsCorrect && dd.notifiedBody != null && dd.notifiedBody.toLowerCase().contains("ul inter")) {
            //WaitUtils.waitForElementToBeClickable(driver, nb0843ULI, TIMEOUT_5_SECOND);
            PageUtils.singleClick(driver, nb0843ULI);
        } else if (notifiedBodyOptionsCorrect && dd.notifiedBody != null && dd.notifiedBody.toLowerCase().contains("Other")) {
            PageUtils.singleClick(driver, nbOther);
        } else {
            //PageUtils.clickIfVisible(driver, nb0086BSI);
        }
    }

    private void changeNotifiedBody() {
        try {
            WaitUtils.waitForElementToBeClickable(driver, linkChangeNotifiedBody, TIMEOUT_1_SECOND);
            linkChangeNotifiedBody.click();
            WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        } catch (Exception e) {
            //Bug which maintains previous selection of notified body
        }
    }

    private boolean isNotifiedBodyListDisplayingCorrectDetails() {
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        WaitUtils.waitForElementToBeClickable(driver, nb0086BSI, TIMEOUT_3_SECOND);
        boolean numberOfNB = listOfNotifiedBodies.size() >= 6;
        String txt = PageUtils.getText(listOfNotifiedBodies.get(5));
        boolean otherDisplayed = txt.contains("Other");
        return numberOfNB && otherDisplayed;
    }


    private void selectDeviceType(DeviceDO dd) {
        WaitUtils.waitForElementToBeClickable(driver, generalMedicalDevice, TIMEOUT_10_SECOND);
        WaitUtils.waitForElementToBeClickable(driver, systemOrProcedurePack, TIMEOUT_10_SECOND);
        String lcDeviceType = dd.deviceType.toLowerCase();
        if (lcDeviceType.contains("general medical device")) {
            PageUtils.clickIfVisible(driver, generalMedicalDevice);
        } else if (lcDeviceType.contains("vitro diagnostic")) {
            PageUtils.clickIfVisible(driver, inVitroDiagnosticDevice);
        } else if (lcDeviceType.contains("active implantable")) {
            PageUtils.clickIfVisible(driver, activeImplantableMedicalDevice);
        } else if (lcDeviceType.contains("procedure pack")) {
            PageUtils.clickIfVisible(driver, systemOrProcedurePack);
        }
    }


    private void deviceSterile(DeviceDO dd) {
        WaitUtils.waitForElementToBeClickable(driver, radioDeviceSterileYes, TIMEOUT_5_SECOND);
        if (dd.isDeviceSterile) {
            PageUtils.clickIfVisible(driver, radioDeviceSterileYes);
        } else {
            PageUtils.clickIfVisible(driver, radioDeviceSterileNo);
        }
    }

    private void customMade(DeviceDO dd) {
        //WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        WaitUtils.waitForElementToBeClickable(driver, radioCustomMadeYes, TIMEOUT_10_SECOND);
        if (dd.isCustomMade) {
            PageUtils.clickIfVisible(driver, radioCustomMadeYes);
        } else {
            PageUtils.clickIfVisible(driver, radioCustomMadeNo);
            //riskClassification(dd);
        }
    }

    private void riskClassification(DeviceDO dd) {
        WaitUtils.waitForElementToBeClickable(driver, radioRiskClass1, TIMEOUT_5_SECOND);
        String lcRiskClassiffication = dd.riskClassification.toLowerCase();
        if (lcRiskClassiffication != null) {
            if (lcRiskClassiffication.contains("class1")) {
                PageUtils.clickIfVisible(driver, radioRiskClass1);
            } else if (lcRiskClassiffication.contains("class2a")) {
                PageUtils.clickIfVisible(driver, radioRiskClass2a);
            } else if (lcRiskClassiffication.contains("class2b")) {
                PageUtils.clickIfVisible(driver, radioRiskClass2b);
            } else if (lcRiskClassiffication.contains("class3")) {
                PageUtils.clickIfVisible(driver, radioRiskClass3);
            }
        }
    }


    /**
     * MAINLY USED FOR TESTING : duplicates and error messages
     * @param dd
     */
    private void searchByGMDNForPartiallyFilledDevice(DeviceDO dd) {
        if (dd.gmdnTermOrDefinition != null) {

            List<String> arrayOfDeviceBecauseTheyKeepBloodyChanging = TestHarnessUtils.getListOfSearchTermsForGMDN();
            int pos = -1;
            String searchFor = dd.gmdnTermOrDefinition;

            WaitUtils.waitForElementToBeClickable(driver, tbxGMDNDefinitionOrTerm, TIMEOUT_10_SECOND);
            tbxGMDNDefinitionOrTerm.clear();
            tbxGMDNDefinitionOrTerm.sendKeys(searchFor);
            WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);

            //Wait for list of items to appear and add it only if its not a duplicate
            WaitUtils.waitForElementToBeClickable(driver, aGmdnMatchesReturnedBySearch, TIMEOUT_DEFAULT);
            int noi = CommonUtils.getNumberOfItemsInList(driver, listOfGmdnMatchesReturnedBySearch);
            int randomPosition = RandomDataUtils.getARandomNumberBetween(0, noi - 1);

            //Click gmdn from search results
            WebElement element = CommonUtils.getElementFromList(listOfGmdnMatchesReturnedBySearch, randomPosition);
            element.click();

            //Set device name for later verification
            dd.deviceName = element.getText();

        } else {
            WaitUtils.waitForElementToBeClickable(driver, tbxGMDNDefinitionOrTerm, TIMEOUT_5_SECOND);
            tbxGMDNDefinitionOrTerm.clear();
            tbxGMDNDefinitionOrTerm.sendKeys(dd.gmdnCode);
        }
    }

    private void searchByGMDN(DeviceDO dd) {
        if (dd.gmdnTermOrDefinition != null) {

            List<String> arrayOfDeviceBecauseTheyKeepBloodyChanging = TestHarnessUtils.getListOfSearchTermsForGMDN();
            int pos = -1;
            String searchFor = dd.gmdnTermOrDefinition;
            boolean isErrorMessageDisplayed = false;
            do {
                WaitUtils.waitForElementToBeClickable(driver, tbxGMDNDefinitionOrTerm, TIMEOUT_10_SECOND);
                tbxGMDNDefinitionOrTerm.clear();
                tbxGMDNDefinitionOrTerm.sendKeys(searchFor);
                WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);

                //Wait for list of items to appear and add it only if its not a duplicate
                WaitUtils.waitForElementToBeClickable(driver, aGmdnMatchesReturnedBySearch, TIMEOUT_DEFAULT);
                int noi = CommonUtils.getNumberOfItemsInList(driver, listOfGmdnMatchesReturnedBySearch);
                int randomPosition = RandomDataUtils.getARandomNumberBetween(0, noi - 1);

                //Click gmdn from search results
                WebElement element = CommonUtils.getElementFromList(listOfGmdnMatchesReturnedBySearch, randomPosition);

                //Set device name for later verification
                dd.deviceName = element.getText();
                element.click();

                //If its a duplicate Try again
                isErrorMessageDisplayed = isErrorMessageDisplayed("device already exists for this manufacturer");
                if (isErrorMessageDisplayed) {
                    //Try again
                    //arrayOfDeviceBecauseTheyKeepBloodyChanging.remove(pos);
                    pos++;
                    searchFor = arrayOfDeviceBecauseTheyKeepBloodyChanging.get(pos);
                } else {
                    log.info(dd.deviceType + " => " + searchFor);
                    gmdnSelected = PageUtils.getText(tbxGMDNDefinitionOrTerm);  //tbxGMDNDefinitionOrTerm.getText();
                }

            } while (isErrorMessageDisplayed);

            //Default is search by gmdn term or definition : This removed 03/02/2017 push
            //previousGMDNSelection(dd);
        } else {
            WaitUtils.waitForElementToBeClickable(driver, tbxGMDNDefinitionOrTerm, TIMEOUT_5_SECOND);
            tbxGMDNDefinitionOrTerm.clear();
            tbxGMDNDefinitionOrTerm.sendKeys(dd.gmdnCode);
        }
    }

    public void searchForGMDN(String searchTerm) {
        WaitUtils.waitForElementToBeClickable(driver, tbxGMDNDefinitionOrTerm, TIMEOUT_5_SECOND);
        tbxGMDNDefinitionOrTerm.clear();
        tbxGMDNDefinitionOrTerm.sendKeys(searchTerm);

        //Wait for list of items to appear and add it only if its not a duplicate
        //WaitUtils.waitForElementToBeClickable(driver, By.xpath(".//div[contains(text(),'Term')]//following::td"), TIMEOUT_DEFAULT);

    }


    public CFSAddDevices proceedToPayment() {
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        WaitUtils.waitForElementToBeClickable(driver, cbxConfirmInformation, TIMEOUT_10_SECOND);
        cbxConfirmInformation.click();
        WaitUtils.waitForElementToBeClickable(driver, btnProceedToPayment, TIMEOUT_10_SECOND);
        btnProceedToPayment.click();
        log.info("Proceed to payment");
        return new CFSAddDevices(driver);
    }


    public CFSAddDevices addAnotherDevice() {
        WaitUtils.nativeWaitInSeconds(2);
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        WaitUtils.waitForElementToBeClickable(driver, btnAddAnotherDevice, TIMEOUT_10_SECOND);
        btnAddAnotherDevice.click();
        return new CFSAddDevices(driver);
    }


    public CFSAddDevices agreeToTandC() {
        WaitUtils.waitForElementToBeClickable(driver, cbxConfirmInformation, TIMEOUT_20_SECOND);
        cbxConfirmInformation.click();
        return new CFSAddDevices(driver);
    }

    public CFSManufacturerList submitApplicationForApproval() {
        WaitUtils.waitForElementToBeClickable(driver, btnSubmitForApproval, TIMEOUT_20_SECOND);
        btnSubmitForApproval.click();
        return new CFSManufacturerList(driver);
    }

    public boolean isContinueButtonEnabled() {
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        WaitUtils.waitForElementToBeVisible(driver, btnContinue, TIMEOUT_10_SECOND);
        boolean enabled = PageUtils.isElementClickable(driver, btnContinue, TIMEOUT_2_SECOND);
        return enabled;
    }

    public CFSAddDevices removeAttachedCertificate() {
        WaitUtils.waitForElementToBeClickable(driver, removeCertificate, TIMEOUT_10_SECOND);
        removeCertificate.click();
        return new CFSAddDevices(driver);
    }

    public CFSAddDevices removeDevice(String deviceName) {
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        int position = PageUtils.getElementPositionInList(listOfDevicesAdded, deviceName);

        //Now click open it
        WebElement deviceLink = listOfDevicesAdded.get(position);
        deviceLink.click();

        //Now remove it
        WebElement deleteMe = listOfDeleteButtons.get(position);
        deleteMe.click();

        return new CFSAddDevices(driver);
    }

    public boolean isReviewPageShowingCorrectNumberOfDevices(int size) {
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        WaitUtils.waitForElementToBeClickable(driver, deleteApplicationButtons, TIMEOUT_10_SECOND);
        boolean matched = listOfDevicesAdded.size() == size;
        if (!matched) {
            log.info("Number of devices displayed : " + listOfDevicesAdded.size());
        }
        return matched;
    }

    public boolean isReviewPageShowingCorrectDeviceNames(List<DeviceDO> listOfDeviceDataObjects) {
        return false;
    }

    public boolean isDeviceDetailsCorrect(List<DeviceDO> listOfDeviceDataObjects) {
        return false;
    }

    public boolean isCECerficatesCorrect(List<DeviceDO> listOfDeviceDataObjects) {
        return false;
    }

    public boolean isProductDetailsCorrect(List<DeviceDO> listOfDeviceDataObjects) {
        return false;
    }

    public CFSAddDevices clickBackButton() {
        WaitUtils.waitForElementToBeClickable(driver, btnBack, TIMEOUT_5_SECOND);
        btnBack.click();
        return new CFSAddDevices(driver);
    }

    public boolean isInProductsPage() {
        return PageUtils.isElementClickable(driver, headingProductsPage, TIMEOUT_10_SECOND);
    }

    public boolean isInCertificatesPage() {
        return PageUtils.isElementClickable(driver, headingCECertificatesPage, TIMEOUT_10_SECOND);
    }

    public boolean isCECertificateCorrect(List<String> certificatesExpected) {
        WaitUtils.waitForElementToBeClickable(driver, headingCECertificatesPage, TIMEOUT_5_SECOND);
        boolean allMatched = PageUtils.isAllItemsDisplayed(listOfAllCECertificates, certificatesExpected);
        return allMatched;
    }

    public ManufacturerDetails saveAndExitNewCFSManufacturerApplication() {
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        WaitUtils.waitForElementToBeClickable(driver, btnSaveAndExit, TIMEOUT_10_SECOND);
        btnSaveAndExit.click();
        return new ManufacturerDetails(driver);
    }

    public String getApplicationReferenceNumber() {
        WaitUtils.waitForElementToBeClickable(driver, txtApplicationReference, TIMEOUT_15_SECOND);
        return txtApplicationReference.getText();

//        WaitUtils.waitForElementToBeClickable(driver, txtApplicationReference, TIMEOUT_5_SECOND);
//        String text = txtApplicationReference.getText();
//        int start = text.indexOf("ber is");
//        String ref = text.substring(start+7, start+21);
//        return ref;
    }

    public CFSAddDevices removeAddedDevice() {
        return null;
    }
}
