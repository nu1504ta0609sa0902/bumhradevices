package com.mhra.mdcm.devices.appian.pageobjects.external.device;

import com.mhra.mdcm.devices.appian.domains.newaccounts.DeviceDO;
import com.mhra.mdcm.devices.appian.pageobjects._Page;
import com.mhra.mdcm.devices.appian.pageobjects.external.manufacturer.ManufacturerList;
import com.mhra.mdcm.devices.appian.utils.selenium.others.RandomDataUtils;
import com.mhra.mdcm.devices.appian.utils.selenium.others.TestHarnessUtils;
import com.mhra.mdcm.devices.appian.utils.selenium.page.CommonUtils;
import com.mhra.mdcm.devices.appian.utils.selenium.page.PageUtils;
import com.mhra.mdcm.devices.appian.utils.selenium.page.WaitUtils;
import org.openqa.selenium.By;
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
public class AddDevices extends _Page {

    public static String gmdnSelected = null;

    @FindBy(css = ".FieldLayout---field_error")
    List<WebElement> errorMessages;

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
    @FindBy(xpath = ".//label[contains(text(), 'Product name')]//following::input[1]")
    WebElement txtProductName;
    @FindBy(xpath = ".//label[contains(text(), 'Product make')]//following::input[1]")
    WebElement txtProductMake;
    @FindBy(xpath = ".//label[contains(text(), 'Product model')]//following::input[1]")
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
    WebElement nb0088BSI;
    @FindBy(xpath = ".//span[contains(text(),'Notified Body')]//following::label[3]")
    WebElement nb0120BSI;
    @FindBy(xpath = ".//span[contains(text(),'Notified Body')]//following::label[4]")
    WebElement nb0473BSI;
    @FindBy(xpath = ".//span[contains(text(),'Notified Body')]//following::label[5]")
    WebElement nb0843BSI;
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
    @FindBy(xpath = ".//*[contains(text(),'Enter name')]//following::input[1]")
    WebElement pdProductName;
    @FindBy(xpath = ".//label[contains(text(),'and model')]")
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
    @FindBy(css = ".FileUploadWidget---ui-inaccessible")
    List<WebElement> listOfFileUploads;

    //Confirm and btnDeclareDevices
    @FindBy(xpath = ".//button[contains(text(),'Review your order')]")
    WebElement btnReviewYourOrder;
    @FindBy(xpath = ".//button[.='Continue']")
    WebElement btnProceedToPayment;
    @FindBy(xpath = ".//button[.='Continue']")
    WebElement btnProceedToReview;
    @FindBy(xpath = ".//button[contains(text(),'Finish')]")
    WebElement btnFinish;
    @FindBy(xpath = ".//button[.='Remove']")
    WebElement btnRemove;
    @FindBy(css = ".Button---primary")
    WebElement submitConfirm;
    @FindBy(css = ".Button---primary")
    WebElement bthSubmitConfirm;
    @FindBy(css = ".CheckboxGroup---choice_pair>label")
    WebElement cbxConfirmInformation;

    //Submit and save buttons
    @FindBy(xpath = ".//button[.='Add device']")
    WebElement btnAddDevice;
    @FindBy(xpath = ".//button[.='Save']")
    WebElement btnSaveProgress;

    //Error message
    @FindBy(css = ".component_error")
    WebElement errMessage;
    @FindBy(css = ".MessageLayout---error")
    WebElement validationErrMessage;

    //Device Summary
    @FindBy(xpath = ".//div[contains(text(),'GMDN code')]//following::a")
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


    @Autowired
    public AddDevices(WebDriver driver) {
        super(driver);
    }

    public AddDevices addDevice() {
        WaitUtils.waitForElementToBeClickable(driver, btnAddDevice, TIMEOUT_10_SECOND, false);
        btnAddDevice.click();
        return new AddDevices(driver);
    }

    public boolean isDeviceTypeCorrect() {
        boolean allCorrect = false;

        WaitUtils.waitForElementToBeClickable(driver, generalMedicalDevice, TIMEOUT_10_SECOND, false);
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
        //WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        try {
            WaitUtils.nativeWaitInSeconds(1);
            WaitUtils.waitForElementToBeVisible(driver, By.cssSelector(".FieldLayout---field_error"), 3, false);
            WaitUtils.waitForElementToBeClickable(driver, By.cssSelector(".FieldLayout---field_error"), 3, false);
            boolean isDisplayed = false;
            for (WebElement msg : errorMessages) {
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
        WaitUtils.waitForElementToBeVisible(driver, errMessage, 10, false);
        boolean contains = errMessage.getText().contains(expectedErrorMsg);
        return contains;
    }

    public AddDevices addFollowingDevice(DeviceDO dd) {
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        //WaitUtils.nativeWaitInSeconds(1);
        WaitUtils.waitForElementToBeClickable(driver, generalMedicalDevice, TIMEOUT_DEFAULT, false);
        WaitUtils.waitForElementToBeClickable(driver, systemOrProcedurePack, TIMEOUT_3_SECOND, false);
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
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        WaitUtils.waitForElementToBeClickable(driver, btnSaveProgress, TIMEOUT_10_SECOND, false);
        PageUtils.doubleClick(driver, btnSaveProgress);

        return new AddDevices(driver);
    }



    public AddDevices addInvalidFollowingDevice(DeviceDO dd) {
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        WaitUtils.waitForElementToBeClickable(driver, generalMedicalDevice, TIMEOUT_DEFAULT, false);
        WaitUtils.waitForElementToBeClickable(driver, systemOrProcedurePack, TIMEOUT_3_SECOND, false);
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

        return new AddDevices(driver);
    }

    private void addActiveImplantableDevice(DeviceDO dd) {
        searchByGMDN(dd);
        customMade(dd);
        int numberOfProductName = dd.listOfProductName.size();
        if (numberOfProductName <= 1) {
            if (numberOfProductName == 1) {
                dd.productName = dd.listOfProductName.get(0);
            }
            //List of device to add
            if (dd.isCustomMade) {
                productLabelName(dd);
            }
        } else {
            for (String x : dd.listOfProductName) {
                productLabelName(x);
            }
        }
        //saveProduct(dd);
    }

    private void addProcedurePackDevice(DeviceDO dd) {
        searchByGMDN(dd);
        deviceSterile(dd);

        if (dd.isDeviceSterile) {
            notifiedBody(dd);
        }
        isBearingCEMarking(dd);
        devicesCompatible(dd);
        //saveProduct(dd);
    }

    private void addVitroDiagnosticDevice(DeviceDO dd) {
        searchByGMDN(dd);
        riskClassificationIVD(dd);

        //No product needs to be added when Risk Classification = IVD General
        if (dd.riskClassification != null && !dd.riskClassification.equals("ivd general")) {
            //If more than 1 product listed
            int numberOfProductName = dd.listOfProductName.size();
            if (numberOfProductName <= 1) {
                if (numberOfProductName == 1) {
                    dd.productName = dd.listOfProductName.get(0);
                }
                //List of device to add
                addProduct(dd);
                notifiedBody(dd);
                subjectToPerformanceEval(dd);
                productNewToMarket(dd);
                if (dd.riskClassification.toLowerCase().contains("list a"))
                    conformToCTS(dd);
                saveProduct(dd);

                //Remove this if we find a better solution
                WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
                WaitUtils.nativeWaitInSeconds(1);
            } else {
                for (String x : dd.listOfProductName) {
                    dd.productName = x;
                    addProduct(dd);
                    notifiedBody(dd);
                    subjectToPerformanceEval(dd);
                    productNewToMarket(dd);
                    if (dd.riskClassification.toLowerCase().contains("list a"))
                        conformToCTS(dd);
                    saveProduct(dd);

                    //Remove this if we find a better solution
                    WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
                    WaitUtils.nativeWaitInSeconds(1);
                }
            }

            //Product Details Table Heading Check
            boolean headingCorrect = verifyProductDetailsHeading();
            if(!headingCorrect){
                throw new RuntimeException("Product details table not correct. Expected : Name Make Model Product code");
            }
        }
    }

    private boolean verifyProductDetailsHeading() {
        String expectedHeadings = "name,make,model,product code";
        boolean allHeadingCorrect = true;

        for(WebElement el: listOfProductDetailsTable){
            String text = el.getText().toLowerCase();
            //System.out.println("Table headings : " + text);
            if(!expectedHeadings.contains(text)){
                allHeadingCorrect = false;
                break;
            }
        }

        return allHeadingCorrect;
    }


    private void addGeneralMedicalDevice(DeviceDO dd) {
        searchByGMDN(dd);
        customMade(dd);

        if (!dd.isCustomMade) {
            deviceSterile(dd);
            deviceMeasuring(dd);
            if (!dd.isCustomMade) {
                riskClassification(dd);
                notifiedBody(dd);
            }
        }
        //saveProduct(dd);
    }

    private void productLabelName(DeviceDO dd) {
//        WaitUtils.waitForElementToBeClickable(driver, saveProduct2, TIMEOUT_5_SECOND, false);
//        saveProduct2.click();
        WaitUtils.waitForElementToBeClickable(driver, txtProductNameLabel, TIMEOUT_5_SECOND, false);
        txtProductNameLabel.sendKeys(RandomDataUtils.getRandomTestName("Label"));

        PageUtils.uploadDocument(fileUpload, "DeviceLabelDoc2.pdf", 1, 3);
        PageUtils.uploadDocument(listOfFileUploads.get(0), "DeviceInstructionForUse1.pdf", 1, 3);

        //Save product label details
        WaitUtils.waitForElementToBeClickable(driver, saveProduct2, TIMEOUT_5_SECOND, false);
        saveProduct2.click();

    }


    private void productLabelName(String labelName) {
        WaitUtils.waitForElementToBeClickable(driver, addProduct2, TIMEOUT_10_SECOND, false);
        addProduct2.click();

        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        WaitUtils.nativeWaitInSeconds(1);
        WaitUtils.waitForElementToBeClickable(driver, txtProductNameLabel, TIMEOUT_10_SECOND, false);
        txtProductNameLabel.sendKeys(labelName);

        PageUtils.uploadDocument(fileUpload, "DeviceLabelDoc2.pdf", 1, 3);
        PageUtils.uploadDocument(listOfFileUploads.get(0), "DeviceInstructionForUse1.pdf", 1, 3);

        //Save product label details
        WaitUtils.waitForElementToBeClickable(driver, saveProduct, TIMEOUT_5_SECOND, false);
        saveProduct.click();
    }

    private void conformToCTS(DeviceDO dd) {
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        if (dd.isConformsToCTS) {
            PageUtils.clickIfVisible(driver, radioConformsToCTSYes);
            WaitUtils.waitForElementToBeClickable(driver, txtCTSReference, TIMEOUT_5_SECOND, false);
            txtCTSReference.sendKeys("CTS039458430958");
        } else {
            PageUtils.clickIfVisible(driver, radioConformsToCTSNo);
            WaitUtils.waitForElementToBeClickable(driver, txtDemonstratedCompliance, TIMEOUT_5_SECOND, false);
            txtDemonstratedCompliance.sendKeys("Demonstrated Compliance");
            txtTestingMethod.sendKeys("Manually Tested");
        }
    }

    private void saveProduct(DeviceDO dd) {
        WaitUtils.waitForElementToBeClickable(driver, saveProduct2, TIMEOUT_5_SECOND, false);
        saveProduct2.click();
    }

    private void productNewToMarket(DeviceDO dd) {
        if (dd.isNewProduct) {
            PageUtils.clickIfVisible(driver, radioProductNewYes);
        } else {
            PageUtils.clickIfVisible(driver, radioProductNewNo);
        }
    }

    private void subjectToPerformanceEval(DeviceDO dd) {
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        WaitUtils.nativeWaitInSeconds(1);
        WaitUtils.waitForElementToBeClickable(driver, radioSubjectToPerformanceEvalYes, TIMEOUT_DEFAULT, false);
        WaitUtils.waitForElementToBeVisible(driver, radioSubjectToPerformanceEvalYes, TIMEOUT_DEFAULT, false);
        if (dd.isSubjectToPerfEval) {
            PageUtils.clickIfVisible(driver, radioSubjectToPerformanceEvalYes);
        } else {
            PageUtils.clickIfVisible(driver, radioSubjectToPerformanceEvalNo);
        }
    }

    private void addProduct(DeviceDO dd) {
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        //WaitUtils.waitForElementToBeClickable(driver, addProduct, TIMEOUT_10_SECOND, false);
        //WaitUtils.nativeWaitInSeconds(2);
        //addProduct.click();
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);

        //Wait for form to be visible
        WaitUtils.waitForElementToBeClickable(driver, cbxProductName, TIMEOUT_10_SECOND, false);
        if (dd.productName != null && !dd.productName.equals("")) {
            cbxProductName.click();
            WaitUtils.waitForElementToBeClickable(driver, pdProductName, TIMEOUT_10_SECOND, false);
            pdProductName.sendKeys(dd.productName);
        } else if (dd.productMake != null || !dd.productMake.equals("")) {
            cbxMakeAndModel.click();
            WaitUtils.waitForElementToBeClickable(driver, pdProductModel, TIMEOUT_10_SECOND, false);
            pdProductMake.sendKeys(dd.productMake);
            pdProductModel.sendKeys(dd.productModel);
        }

    }

    private void devicesCompatible(DeviceDO dd) {
        if (dd.isDeviceCompatible) {
            PageUtils.clickIfVisible(driver, ppDevicesCompatibleYes);
        } else {
            PageUtils.clickIfVisible(driver, ppDevicesCompatibleNo);
        }
    }

    private void isBearingCEMarking(DeviceDO dd) {
        if (dd.isBearingCEMarking) {
            PageUtils.clickIfVisible(driver, ppIsBearingCEMarkingYes);
        } else {
            PageUtils.clickIfVisible(driver, ppIsBearingCEMarkingNo);
        }
    }

    private void notifiedBody(DeviceDO dd) {
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        changeNotifiedBody();
        boolean notifiedBodyOptionsCorrect = isNotifiedBodyListDisplayingCorrectDetails();

        WaitUtils.waitForElementToBeVisible(driver, nb0086BSI, TIMEOUT_5_SECOND, false);
        WaitUtils.waitForElementToBeClickable(driver, nb0086BSI, TIMEOUT_5_SECOND, false);

        //Select notified body
        if (notifiedBodyOptionsCorrect && dd.notifiedBody != null && dd.notifiedBody.toLowerCase().contains("bsi")) {
            PageUtils.singleClick(driver, nb0086BSI);
        }else if (notifiedBodyOptionsCorrect && dd.notifiedBody != null && dd.notifiedBody.toLowerCase().contains("Other")) {
            PageUtils.clickIfVisible(driver, nbOther);
        }else{
            //PageUtils.clickIfVisible(driver, nb0086BSI);
        }
    }

    private void changeNotifiedBody() {
        try{
            WaitUtils.waitForElementToBeClickable(driver, linkChangeNotifiedBody, TIMEOUT_1_SECOND, false);
            linkChangeNotifiedBody.click();
            WaitUtils.waitForElementToBeClickable(driver, nb0086BSI, TIMEOUT_1_SECOND, false);
            WaitUtils.nativeWaitInSeconds(1);
        }catch (Exception e){
            //Bug which maintains previous selection of notified body
        }
    }

    private boolean isNotifiedBodyListDisplayingCorrectDetails() {
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        WaitUtils.waitForElementToBeClickable(driver, nb0086BSI, TIMEOUT_3_SECOND, false);
        boolean numberOfNB = listOfNotifiedBodies.size() >= 6;
        String txt = PageUtils.getText(listOfNotifiedBodies.get(5));
        boolean otherDisplayed = txt.contains("Other");
        return numberOfNB && otherDisplayed;
    }

    private void riskClassificationIVD(DeviceDO dd) {
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        WaitUtils.waitForElementToBeClickable(driver, ivdIVDGeneral, TIMEOUT_10_SECOND, false);
        //WaitUtils.nativeWaitInSeconds(1);

        String lcRiskClassification = dd.riskClassification.toLowerCase();

        if (lcRiskClassification.contains("ivd general")) {
            //WaitUtils.waitForElementToBePartOfDOM(driver, By.xpath(".//label[contains(text(),'IVD General')]"), TIMEOUT_5_SECOND, false);
            //PageUtils.clickIfVisible(driver, ivdIVDGeneral);
            ivdIVDGeneral.click();
        } else if (lcRiskClassification.contains("list a")) {
            //WaitUtils.waitForElementToBePartOfDOM(driver, By.xpath(".//label[contains(text(),'List A')]"), TIMEOUT_5_SECOND, false);
            //PageUtils.clickIfVisible(driver, ivdListA);
            ivdListA.click();
        } else if (lcRiskClassification.contains("list b")) {
            //WaitUtils.waitForElementToBePartOfDOM(driver, By.xpath(".//label[contains(text(),'List B')]"), TIMEOUT_5_SECOND, false);
            //PageUtils.clickIfVisible(driver, ivdListB);
            ivdListB.click();
        } else if (lcRiskClassification.contains("self-test")) {
            //WaitUtils.waitForElementToBePartOfDOM(driver, By.xpath(".//label[contains(text(),'Self-Test')]"), TIMEOUT_5_SECOND, false);
            //PageUtils.clickIfVisible(driver, ivdSelfTest);
            ivdSelfTest.click();
        }
    }

    private void selectDeviceType(DeviceDO dd) {
        WaitUtils.waitForElementToBeClickable(driver, generalMedicalDevice, TIMEOUT_10_SECOND, false);
        WaitUtils.waitForElementToBeClickable(driver, systemOrProcedurePack, TIMEOUT_10_SECOND, false);
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

    private void deviceMeasuring(DeviceDO dd) {
        WaitUtils.waitForElementToBeClickable(driver, radioDeviceMeasuringYes, TIMEOUT_5_SECOND, false);
        if (dd.isDeviceMeasuring) {
            PageUtils.clickIfVisible(driver, radioDeviceMeasuringYes);
        } else {
            PageUtils.clickIfVisible(driver, radioDeviceMeasuringNo);
        }
    }

    private void deviceSterile(DeviceDO dd) {
        WaitUtils.waitForElementToBeClickable(driver, radioDeviceSterileYes, TIMEOUT_5_SECOND, false);
        if (dd.isDeviceSterile) {
            PageUtils.clickIfVisible(driver, radioDeviceSterileYes);
        } else {
            PageUtils.clickIfVisible(driver, radioDeviceSterileNo);
        }
    }

    private void customMade(DeviceDO dd) {
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        WaitUtils.waitForElementToBeClickable(driver, radioCustomMadeYes, TIMEOUT_10_SECOND, false);
        if (dd.isCustomMade) {
            PageUtils.clickIfVisible(driver, radioCustomMadeYes);
        } else {
            PageUtils.clickIfVisible(driver, radioCustomMadeNo);
            //riskClassification(dd);
        }
    }

    private void riskClassification(DeviceDO dd) {
        WaitUtils.waitForElementToBeClickable(driver, radioRiskClass1, TIMEOUT_5_SECOND, false);
        WaitUtils.waitForElementToBeClickable(driver, nb0086BSI, TIMEOUT_5_SECOND, false);
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
                WaitUtils.waitForElementToBeClickable(driver, tbxGMDNDefinitionOrTerm, TIMEOUT_5_SECOND, false);
                tbxGMDNDefinitionOrTerm.clear();
                tbxGMDNDefinitionOrTerm.sendKeys(searchFor);
                WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);

                //Wait for list of items to appear and add it only if its not a duplicate
                WaitUtils.waitForElementToBeClickable(driver, aGmdnMatchesReturnedBySearch, TIMEOUT_DEFAULT, false);
                int noi = CommonUtils.getNumberOfItemsInList(driver, listOfGmdnMatchesReturnedBySearch);
                int randomPosition = RandomDataUtils.getARandomNumberBetween(0, noi-1);

                //Click gmdn from search results
                WebElement element = listOfGmdnMatchesReturnedBySearch.get(randomPosition);
                element.click();
                //element.findElement(By.tagName("a")).click();
                //WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);

                //If its a duplicate Try again
                isErrorMessageDisplayed = isErrorMessageDisplayed("Duplicate");
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
            WaitUtils.waitForElementToBeClickable(driver, tbxGMDNDefinitionOrTerm, TIMEOUT_5_SECOND, false);
            tbxGMDNDefinitionOrTerm.clear();
            tbxGMDNDefinitionOrTerm.sendKeys(dd.gmdnCode);
        }
    }

    public void searchForGMDN(String searchTerm){
        WaitUtils.waitForElementToBeClickable(driver, tbxGMDNDefinitionOrTerm, TIMEOUT_5_SECOND, false);
        tbxGMDNDefinitionOrTerm.clear();
        tbxGMDNDefinitionOrTerm.sendKeys(searchTerm);

        //Wait for list of items to appear and add it only if its not a duplicate
        //WaitUtils.waitForElementToBeClickable(driver, By.xpath(".//div[contains(text(),'Term')]//following::td"), TIMEOUT_DEFAULT, false);

    }

    public boolean isOptionToAddAnotherDeviceVisible() {
        try {
            WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
            WaitUtils.waitForElementToBeClickable(driver, btnAddAnotherDevice, TIMEOUT_10_SECOND, false);
            boolean isVisible = btnAddAnotherDevice.isDisplayed() && btnAddAnotherDevice.isEnabled();
            return isVisible;
        } catch (Exception e) {
            return false;
        }
    }

    public AddDevices proceedToPayment() {
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        WaitUtils.waitForElementToBeClickable(driver, cbxConfirmInformation, TIMEOUT_10_SECOND, false);
        cbxConfirmInformation.click();
        WaitUtils.waitForElementToBeClickable(driver, btnProceedToPayment, TIMEOUT_10_SECOND, false);
        btnProceedToPayment.click();
        log.info("Proceed to payment");
        return new AddDevices(driver);
    }

    public AddDevices submitRegistration() {
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        WaitUtils.waitForElementToBeClickable(driver, submitConfirm, TIMEOUT_5_SECOND, false);
        submitConfirm.click();
        return new AddDevices(driver);
    }

    public ManufacturerList finish() {
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        WaitUtils.waitForElementToBeClickable(driver, btnFinish, TIMEOUT_15_SECOND, false);
        btnFinish.click();
        return new ManufacturerList(driver);
    }

    public boolean isGMDNValueDisplayed(DeviceDO data) {
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        WaitUtils.waitForElementToBeClickable(driver, btnAddAnotherDevice, TIMEOUT_15_SECOND, false);
        WaitUtils.waitForElementToBeClickable(driver, btnProceedToPayment, TIMEOUT_15_SECOND, false);
        boolean isDisplayed = false;
        String valueToCheck = "";

        if (data.gmdnTermOrDefinition != null) {
            valueToCheck = data.gmdnTermOrDefinition.toLowerCase();
        } else {
            valueToCheck = data.gmdnCode;
        }

        for (WebElement el : listOfGMDNLinksInSummary) {
            String text = el.getText();
            if (text.toLowerCase().contains(valueToCheck)) {
                isDisplayed = true;
                break;
            }
        }

        return isDisplayed;
    }

    public boolean isGMDNValueDisplayed(String valueToCheck) {
        boolean isCorrect = false;

        for (WebElement el : listOfGMDNLinksInSummary) {
            String text = el.getText();
            //System.out.println("GMDN : " + text);
            if (text.contains(valueToCheck)) {
                isCorrect = true;
                break;
            }
        }

        return isCorrect;
    }

    public AddDevices addAnotherDevice() {
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        WaitUtils.waitForElementToBeClickable(driver, btnAddAnotherDevice, TIMEOUT_5_SECOND, false);
        btnAddAnotherDevice.click();
        return new AddDevices(driver);
    }


    public AddDevices viewDeviceWithGMDNValue(String gmdnCode) {
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        WebElement el = CommonUtils.getElementWithLink(listOfGMDNLinksInSummary, gmdnCode);
        WaitUtils.waitForElementToBeClickable(driver, el, TIMEOUT_5_SECOND, false);
        el.click();
        return new AddDevices(driver);
    }

    public AddDevices removeSelectedDevice() {
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        WaitUtils.waitForElementToBeClickable(driver, btnRemove, TIMEOUT_5_SECOND, false);
        btnRemove.click();
        return new AddDevices(driver);
    }

    public boolean isProductDetailsCorrect(DeviceDO data) {
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        boolean allCorrect = true;

        if (data.productName != null && !data.productName.equals("")) {
            allCorrect = listOfProductNames.get(0).getText().contains(data.productName);
        } else {
            //Confirm make and model
            allCorrect = listOfProductModel.get(0).getText().contains(data.productModel);
            if (allCorrect) {
                allCorrect = listOfProductMake.get(0).getText().contains(data.productMake);
            }
        }

        return allCorrect;
    }

    public AddDevices viewAProduct(DeviceDO data) {
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        WebElement link;
        if (data.productName != null && !data.productName.equals("")) {
            link = PageUtils.findCorrectElement(listOfProductNames, data.productName);
        } else {
            //Confirm model and make
            link = PageUtils.findCorrectElement(listOfProductMake, data.productMake);
        }

        //WaitUtils.waitForElementToBeClickable(driver, link, TIMEOUT_5_SECOND, false);
        driver.findElement(By.linkText(link.getText())).click();
        return new AddDevices(driver);
    }


    public boolean isCTSAndOthereDetailsCorrect(DeviceDO data) {
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        boolean allCorrect = true;
        String txt;

        //Verify product make and models correct
        if (data.productName != null && !data.productName.equals("")) {
            txt = PageUtils.getText(txtProductName);
            allCorrect = txt.contains(data.productName);
        } else {
            txt = PageUtils.getText(txtProductMake); //txtProductMake.getText();
            allCorrect = txt.contains(data.productMake);
            if (allCorrect) {
                txt = PageUtils.getText(txtProductModel);
                allCorrect = txt.contains(data.productModel);
            }
        }

        //Verify other selection : CTS, product new etc


        return allCorrect;
    }

    public AddDevices removeAllDevices(List<DeviceDO> listOfDeviceData) {
        for (DeviceDO data : listOfDeviceData) {
            String gmdnCode = data.getGMDN();
            viewDeviceWithGMDNValue(gmdnCode);
            removeSelectedDevice();
        }

        return new AddDevices(driver);
    }

    public boolean isAllTheGMDNValueDisplayed(List<String> listOfGmdns) {
        WaitUtils.isPageLoadingComplete(driver, 1);
        boolean allDisplayed = true;
        for (String gmdn : listOfGmdns) {
            allDisplayed = isGMDNValueDisplayed(gmdn);
            if (!allDisplayed) {
                break;
            }
        }
        return allDisplayed;
    }

    public AddDevices searchForDevice(DeviceDO dd, String deviceType, String gmdnTermCodeOrDefinition) {

        dd.gmdnTermOrDefinition = gmdnTermCodeOrDefinition;
        if(deviceType != null){
            dd.deviceType = deviceType;
            selectDeviceType(dd);
        }
        searchForGMDN(dd.gmdnTermOrDefinition);
        return new AddDevices(driver);
    }

    public boolean atLeast1MatchFound(String searchTerm) {
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        boolean isNumber = CommonUtils.isNumericValue(searchTerm);
        if(!isNumber) {
            try {
                WaitUtils.waitForElementToBeClickable(driver, listOfGmdnMatchesReturnedBySearch.get(0), TIMEOUT_3_SECOND, false);
                int noi = CommonUtils.getNumberOfItemsInList(driver, listOfGmdnMatchesReturnedBySearch);
                boolean atLeast1Match = noi >= 1 ? true : false;
                return atLeast1Match;
            }catch (Exception e){
                return false;
            }
        }else{
            //Verify a valid device id is entered
            boolean isValidGMDN = labelValidGMDNCodeMessage.getText().contains("Valid GMDN");
            return isValidGMDN;
        }
    }


    public AddDevices viewAllGmdnTermDefinitions(DeviceDO dd, String deviceType) {
        if(deviceType!=null) {
            dd.deviceType = deviceType;
            selectDeviceType(dd);
        }
        WaitUtils.waitForElementToBeClickable(driver, viewAllGMDNTermDefinition, TIMEOUT_3_SECOND, false);
        viewAllGMDNTermDefinition.click();
        return new AddDevices(driver);
    }

    public AddDevices clickViewAllGmdnTermDefinitions() {
        WaitUtils.waitForElementToBeClickable(driver, viewAllGMDNTermDefinition, TIMEOUT_3_SECOND, false);
        viewAllGMDNTermDefinition.click();
        return new AddDevices(driver);
    }

    public boolean isAllGMDNTableDisplayed() {
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        boolean isDisplayed = listOfAllGmdnTermDefinitions.size() >= 1 ? true : false;
        return isDisplayed;
    }

    public boolean isValidationErrorMessageCorrect(String expectedErrorMsg) {
        WaitUtils.waitForElementToBeVisible(driver, validationErrMessage, 10, false);
        boolean contains = validationErrMessage.getText().contains(expectedErrorMsg);
        return contains;
    }

    public boolean isAbleToSubmitForReview() {
        boolean isAbleToSubmit = true;
        try{
            WaitUtils.waitForElementToBeClickable(driver, btnReviewYourOrder, TIMEOUT_3_SECOND, false);
        }catch (Exception e){
            isAbleToSubmit = false;
        }
        return isAbleToSubmit;
    }

    public boolean isValidationErrorMessageVisible() {
        boolean isErrorMessageDisplayed = true;
        try{
            WaitUtils.waitForElementToBeClickable(driver, validationErrMessage, TIMEOUT_3_SECOND, false);
        }catch (Exception e){
            isErrorMessageDisplayed = false;
        }
        return isErrorMessageDisplayed;
    }

    public AddDevices save() {
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        WaitUtils.waitForElementToBeClickable(driver, btnSaveProgress, TIMEOUT_5_SECOND, false);
        PageUtils.doubleClick(driver, btnSaveProgress);
        return new AddDevices(driver);
    }


    public AddDevices proceedToReview() {
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        WaitUtils.nativeWaitInSeconds(2);
        WaitUtils.waitForElementToBeClickable(driver, btnProceedToReview, TIMEOUT_10_SECOND, false);
        btnProceedToReview.click();
        log.info("Proceed to review before payment");
        return new AddDevices(driver);
    }

    public AddDevices saveDevice() {
        WaitUtils.waitForElementToBeClickable(driver, btnSaveProgress, TIMEOUT_10_SECOND, false);
        PageUtils.doubleClick(driver, btnSaveProgress);
        return new AddDevices(driver);
    }
    
    public AddDevices confirmPayment() {
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        WaitUtils.waitForElementToBeClickable(driver, bthSubmitConfirm, TIMEOUT_10_SECOND, false);
        bthSubmitConfirm.click();
        log.info("Submit for registration");
        return new AddDevices(driver);
    }

    public ManufacturerList backToService() {
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        WaitUtils.waitForElementToBeClickable(driver, linkBackToService, TIMEOUT_10_SECOND, false);
        linkBackToService.click();
        return new ManufacturerList(driver);
    }
}
