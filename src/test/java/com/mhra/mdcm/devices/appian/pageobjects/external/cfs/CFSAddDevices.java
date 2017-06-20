package com.mhra.mdcm.devices.appian.pageobjects.external.cfs;

import com.mhra.mdcm.devices.appian.domains.newaccounts.DeviceDO;
import com.mhra.mdcm.devices.appian.pageobjects._Page;
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

    //Product details verification : After adding a product (IVD and AIMD)
    @FindBy(xpath = ".//div[contains(text(), 'Product code')]//following::tr/td[1]")
    List<WebElement> listOfProductNames;
    @FindBy(xpath = ".//div[contains(text(), 'Product code')]//following::tr/td[2]")
    List<WebElement> listOfProductMake;
    @FindBy(xpath = ".//div[contains(text(), 'Product code')]//following::tr/td[3]")
    List<WebElement> listOfProductModel;

    //Product details form inputs
    @FindBy(xpath = ".//*[contains(text(),'Enter name')]//following::input[1]")
    WebElement txtProductName;
    @FindBy(xpath = ".//*[contains(text(),'and model')]//following::input[1]")
    WebElement txtProductMake;
    @FindBy(xpath = ".//*[contains(text(),'and model')]//following::input[2]")
    WebElement txtProductModel;
    @FindBy(xpath = ".//*[.='Product details']//following::th/a")
    List<WebElement> listOfProductDetailsTable;

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

    //IVD risk classification
    @FindBy(xpath = ".//label[contains(text(),'List A')]")
    WebElement ivdListA;
    @FindBy(xpath = ".//label[contains(text(),'List B')]")
    WebElement ivdListB;
    @FindBy(xpath = ".//label[contains(text(),'Self-Test')]")
    WebElement ivdSelfTest;
    @FindBy(xpath = ".//label[contains(text(),'IVD General')]")
    WebElement ivdIVDGeneral;

    //Procedure pack
    @FindBy(xpath = ".//span[contains(text(),'pack incorporate')]//following::label[1]")
    WebElement ppIsBearingCEMarkingYes;
    @FindBy(xpath = ".//span[contains(text(),'pack incorporate')]//following::label[2]")
    WebElement ppIsBearingCEMarkingNo;
    @FindBy(xpath = ".//span[contains(text(),'devices compatible')]//following::label[1]")
    WebElement ppDevicesCompatibleYes;
    @FindBy(xpath = ".//span[contains(text(),'devices compatible')]//following::label[2]")
    WebElement ppDevicesCompatibleNo;

    //Add product
    @FindBy(xpath = ".//button[.='Add product']")
    WebElement addProduct;
    @FindBy(xpath = ".//button[contains(text(),'Add Product')]")
    WebElement addProduct2;
    @FindBy(xpath = ".//button[.='Save Product']")
    WebElement saveProduct;
    @FindBy(xpath = ".//button[.='Save product']")
    WebElement saveProduct2;
    @FindBy(xpath = ".//label[.='Enter name']")
    WebElement cbxProductName;
    @FindBy(xpath = ".//*[contains(text(),'Medical Device Name')]//following::input[1]")
    WebElement pdProductName;
    @FindBy(xpath = ".//label[contains(text(),'Model')]")
    WebElement cbxMakeAndModel;
    @FindBy(xpath = ".//*[contains(text(),'and model')]//following::input[1]")
    WebElement pdProductMake;
    @FindBy(xpath = ".//*[contains(text(),'and model')]//following::input[2]")
    WebElement pdProductModel;

    @FindBy(xpath = ".//*[contains(text(),'performance eval')]//following::label[1]")
    WebElement radioSubjectToPerformanceEvalYes;
    @FindBy(xpath = ".//*[contains(text(),'performance eval')]//following::label[2]")
    WebElement radioSubjectToPerformanceEvalNo;
    @FindBy(xpath = ".//*[contains(text(),'product new')]//following::label[1]")
    WebElement radioProductNewYes;
    @FindBy(xpath = ".//*[contains(text(),'product new')]//following::label[2]")
    WebElement radioProductNewNo;
    @FindBy(xpath = ".//*[contains(text(),'product conform to')]//following::label[1]")
    WebElement radioConformsToCTSYes;
    @FindBy(xpath = ".//*[contains(text(),'product conform to')]//following::label[2]")
    WebElement radioConformsToCTSNo;
    @FindBy(xpath = ".//*[contains(text(),'provide the CTS')]//following::input[1]")
    WebElement txtCTSReference;
    @FindBy(xpath = ".//*[contains(text(),'demonstrated compliance')]//following::textarea[1]")
    WebElement txtDemonstratedCompliance;
    @FindBy(xpath = ".//*[contains(text(),'testing method')]//following::textarea[1]")
    WebElement txtTestingMethod;
    @FindBy(xpath = ".//*[contains(text(),'product name as it appears')]//following::input[1]")
    WebElement txtProductNameLabel;

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
    @FindBy(xpath = ".//button[contains(text(),'Review your order')]")
    WebElement btnReviewYourOrder;
    @FindBy(xpath = ".//button[.='Continue']")
    WebElement btnProceedToPayment;
    @FindBy(xpath = ".//button[.='Continue']")
    WebElement btnProceedToReview;
    @FindBy(xpath = ".//button[contains(text(),'Finish')]")
    WebElement btnFinish;
    @FindBy(xpath = ".//button[contains(text(),'Remove')]")
    WebElement btnRemove;
    @FindBy(css = ".Button---primary")
    WebElement submitConfirm;
    @FindBy(css = ".Button---primary")
    WebElement btnSubmitConfirm;
    @FindBy(css = ".CheckboxGroup---choice_pair>label")
    WebElement cbxConfirmInformation;

    //Submit and save buttons
    @FindBy(xpath = ".//button[.='Add device']")
    WebElement btnAddDevice;
    @FindBy(xpath = ".//button[.='Save']")
    WebElement btnSaveProgress;
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
    @FindBy(xpath = ".//*[contains(text(), 'Devices')]/following::button[text()='Delete Device']")
    List<WebElement> listOfDeleteButtons;
    @FindBy(css = "input.DatePickerWidget---text")
    WebElement datePicker;
    @FindBy(xpath = ".//*[contains(text(),'reference number')]//following::input[1]")
    WebElement tbxCertificateReferenceNumber;
    @FindBy(xpath = ".//h3[contains(text(),'Add products')]")
    WebElement headingProductsPage;
    @FindBy(xpath = ".//h3[contains(text(),'Upload CE')]")
    WebElement headingCECertificatesPage;


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
        try {
            WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
            boolean isDisplayed = false;
            for (WebElement msg : alreadyExistsErrorMessages) {
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

    public boolean isErrorMessageCorrect(String expectedErrorMsg) {
        WaitUtils.waitForElementToBeVisible(driver, errMessage, 10);
        boolean contains = errMessage.getText().contains(expectedErrorMsg);
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
                                    dd.productModel = RandomDataUtils.getRandomTestName("Model");
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
                                        String model = RandomDataUtils.getRandomTestName("Model");
                                        dd.productModel = model;
                                        dd.listOfModelName.add(model);
                                    }
                                    addProduct(dd);
                                    dd.productModel = null;
                                }
                            } else {
                                if (dd.productName == null) {
                                    dd.productName = RandomDataUtils.getRandomTestName("Product");
                                }
                                if (dd.productModel == null) {
                                    String model = RandomDataUtils.getRandomTestName("Model");
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
                        WaitUtils.waitForElementToBeClickable(driver, btnUploadCertificate, TIMEOUT_5_SECOND);
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
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        //PageFactory.initElements(driver, this);

        WaitUtils.waitForElementToBeClickable(driver, datePicker, TIMEOUT_10_SECOND);
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
            clickUploadCertificate();
        }
    }

    private void clickContinue() {
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        WaitUtils.waitForElementToBeClickable(driver, btnContinue, TIMEOUT_10_SECOND);
        PageUtils.doubleClick(driver, btnContinue);
    }


    private void addProduct(DeviceDO dd) {
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
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
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
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
                element.click();

                //Set device name for later verification
                dd.deviceName = element.getText();

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


    public CFSManufacturerList submitApplicationForApproval() {
        WaitUtils.waitForElementToBeClickable(driver, btnSubmitForApproval, TIMEOUT_10_SECOND);
        btnSubmitForApproval.click();
        return new CFSManufacturerList(driver);
    }

    public boolean isContinueButtonEnabled() {
        WaitUtils.waitForElementToBeVisible(driver, btnContinue, TIMEOUT_5_SECOND);
        boolean enabled = btnContinue.isEnabled();
        return enabled;
    }

    public CFSAddDevices removeAttachedCertificate() {
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
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
        return listOfDevicesAdded.size() == size;
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
}
