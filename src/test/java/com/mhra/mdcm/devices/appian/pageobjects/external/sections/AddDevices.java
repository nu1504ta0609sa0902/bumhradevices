package com.mhra.mdcm.devices.appian.pageobjects.external.sections;

import com.mhra.mdcm.devices.appian.domains.newaccounts.DeviceData;
import com.mhra.mdcm.devices.appian.pageobjects._Page;
import com.mhra.mdcm.devices.appian.pageobjects.external.ExternalHomePage;
import com.mhra.mdcm.devices.appian.utils.selenium.others.RandomDataUtils;
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

    @FindBy(css = ".component_error")
    List<WebElement> errorMessages;

    @FindBy(xpath = ".//button[.='Add device']")
    WebElement btnAddDevice;

    @FindBy(css = ".GFWJSJ4DCW label")
    List<WebElement> listOfDeviceTypes;

    //Device types
    @FindBy(xpath = ".//*[contains(text(),'ype of device')]//following::input[1]")
    WebElement generalMedicalDevice;
    @FindBy(xpath = ".//*[contains(text(),'ype of device')]//following::input[2]")
    WebElement inVitroDiagnosticDevice;
    @FindBy(xpath = ".//*[contains(text(),'ype of device')]//following::input[3]")
    WebElement activeImplantableMedicalDevice;
    @FindBy(xpath = ".//*[contains(text(),'ype of device')]//following::input[4]")
    WebElement systemOrProcedurePack;

    //GMDN search by
    @FindBy(xpath = ".//*[contains(text(),'Search GMDN')]//following::input[1]")
    WebElement radioByGMDNCode;
    @FindBy(xpath = ".//*[contains(text(),'Search GMDN')]//following::input[2]")
    WebElement radioGMDNDefinitionOrTerm;
    @FindBy(css = "input.gwt-SuggestBox")
    WebElement tbxGMDNDefinitionOrTerm;
    @FindBy(css = "input.aui-TextInput.GFWJSJ4DC0")
    WebElement tbxGMDNCode;

    //Custom made, sterile and measuring
    @FindBy(xpath = ".//*[contains(text(),'custom made')]//following::input[1]")
    WebElement radioCustomMadeYes;
    @FindBy(xpath = ".//*[contains(text(),'custom made')]//following::input[2]")
    WebElement radioCustomMadeNo;
    @FindBy(xpath = ".//*[contains(text(),'device sterile')]//following::input[1]")
    WebElement radioDeviceSterileYes;
    @FindBy(xpath = ".//*[contains(text(),'device sterile')]//following::input[2]")
    WebElement radioDeviceSterileNo;
    @FindBy(xpath = ".//*[contains(text(),'device measuring')]//following::input[1]")
    WebElement radioDeviceMeasuringYes;
    @FindBy(xpath = ".//*[contains(text(),'device measuring')]//following::input[2]")
    WebElement radioDeviceMeasuringNo;

    //Custom Made = No, Then enter risk classification
    @FindBy(xpath = ".//*[contains(text(),'risk class')]//following::input[1]")
    WebElement radioRiskClass1;
    @FindBy(xpath = ".//*[contains(text(),'risk class')]//following::input[2]")
    WebElement radioRiskClass2a;
    @FindBy(xpath = ".//*[contains(text(),'risk class')]//following::input[3]")
    WebElement radioRiskClass2b;
    @FindBy(xpath = ".//*[contains(text(),'risk class')]//following::input[4]")
    WebElement radioRiskClass3;
    @FindBy(xpath = ".//*[contains(text(),'Notified Body')]//following::input[1]")
    WebElement nb0086BSI;

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
    @FindBy(xpath = ".//*[contains(text(),'pack incorporate')]//following::input[1]")
    WebElement ppPackIncorporatedYes;
    @FindBy(xpath = ".//*[contains(text(),'pack incorporate')]//following::input[2]")
    WebElement ppPackIncorporatedNo;
    @FindBy(xpath = ".//*[contains(text(),'devices compatible')]//following::input[1]")
    WebElement ppDevicesCompatibleYes;
    @FindBy(xpath = ".//*[contains(text(),'devices compatible')]//following::input[2]")
    WebElement ppDevicesCompatibleNo;

    //Add product
    @FindBy(xpath = ".//button[.='Add product']")
    WebElement addProduct;
    @FindBy(xpath = ".//*[contains(text(),'Product name')]//following::input[1]")
    WebElement pdProductName;
    @FindBy(xpath = ".//*[contains(text(),'Product make')]//following::input[1]")
    WebElement pdProductMake;
    @FindBy(xpath = ".//*[contains(text(),'Product model')]//following::input[1]")
    WebElement pdProductModel;

    @FindBy(xpath = ".//*[contains(text(),'performance eval')]//following::input[1]")
    WebElement radioSubjectToPerformanceEvalYes;
    @FindBy(xpath = ".//*[contains(text(),'performance eval')]//following::input[2]")
    WebElement radioSubjectToPerformanceEvalNo;
    @FindBy(xpath = ".//*[contains(text(),'product new')]//following::input[1]")
    WebElement radioProductNewYes;
    @FindBy(xpath = ".//*[contains(text(),'product new')]//following::input[2]")
    WebElement radioProductNewNo;
    @FindBy(xpath = ".//*[contains(text(),'product conform to')]//following::input[1]")
    WebElement radioConformsToCTSYes;
    @FindBy(xpath = ".//*[contains(text(),'product conform to')]//following::input[2]")
    WebElement radioConformsToCTSNo;
    @FindBy(xpath = ".//*[contains(text(),'provide the CTS')]//following::input[1]")
    WebElement txtCTSReference;
    @FindBy(xpath = ".//*[contains(text(),'demonstrated compliance')]//following::textarea[1]")
    WebElement txtDemonstratedCompliance;
    @FindBy(xpath = ".//*[contains(text(),'testing method')]//following::textarea[1]")
    WebElement txtTestingMethod;
    @FindBy(xpath = ".//*[contains(text(),'device label')]//following::input[1]")
    WebElement txtProductNameLabel;

    //Option to add other devices
    @FindBy(xpath = ".//button[contains(text(),'Add another device')]")
    WebElement btnAddAnotherDevice;

    //Confirm and btnDeclareDevices
    @FindBy(css = "button.GFWJSJ4DCF")
    WebElement btnConfirm;
    @FindBy(css = "button.GFWJSJ4DAF.GFWJSJ4DCF")
    WebElement btnReviewYourOrder;
    @FindBy(xpath = ".//button[.='Proceed to payment']")
    WebElement btnProceedToPayment;
    @FindBy(css = ".gwt-FileUpload")
    WebElement fileUpload;
    @FindBy(css = ".gwt-FileUpload")
    List<WebElement> listOfFileUploads;
    @FindBy(css = ".left .GFWJSJ4DCF")
    WebElement submit;
    @FindBy(css = ".left .GFWJSJ4DCF")
    WebElement submitConfirm;


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

        WaitUtils.waitForElementToBeClickable(driver, By.cssSelector(".GFWJSJ4DCW label"), TIMEOUT_5_SECOND, false);
        for (WebElement e : listOfDeviceTypes) {
            String text = e.getText();
            if (text.toLowerCase().contains("general medical device") || text.toLowerCase().contains("in vitro diagnostic device") ||
                    text.toLowerCase().contains("active implantable medical device") || text.toLowerCase().contains("system or procedure pack")) {
                allCorrect = true;
            } else {
                allCorrect = false;
                break;
            }
        }

        return allCorrect;
    }


    public boolean isErrorMessageDisplayed() {
        try {
            WaitUtils.waitForElementToBeVisible(driver, By.cssSelector(".component_error"), 3, false);
            boolean isDisplayed = errorMessages.size() > 0;
            return isDisplayed;
        } catch (Exception e) {
            return false;
        }
    }

    public AddDevices addFollowingDevice(DeviceData dd) {
        WaitUtils.waitForElementToBeClickable(driver, generalMedicalDevice, TIMEOUT_5_SECOND, false);
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
        WaitUtils.waitForElementToBeClickable(driver, btnConfirm, TIMEOUT_5_SECOND, false);
        PageUtils.doubleClick(driver, btnConfirm);

        return new AddDevices(driver);
    }

    private void addActiveImplantableDevice(DeviceData dd) {
        searchByGMDN(dd);
        customMade(dd);
        int numberOfProductName = dd.listOfProductName.size();
        if(numberOfProductName <= 1) {
            if(numberOfProductName==1){
                dd.productName = dd.listOfProductName.get(0);
            }
            //List of device to add
            if (dd.isCustomMade) {
                productLabelName(dd);
            }
        }else{
            for(String x: dd.listOfProductName){
                productLabelName(x);
            }
        }
        //saveProduct(dd);
    }

    private void addProcedurePackDevice(DeviceData dd) {
        searchByGMDN(dd);
        customMade(dd);
        deviceSterile(dd);
        deviceMeasuring(dd);
        notifiedBody(dd);
        packIncorporated(dd);
        devicesCompatible(dd);
        saveProduct(dd);
    }

    private void addVitroDiagnosticDevice(DeviceData dd) {
        searchByGMDN(dd);
        riskClassificationIVD(dd);

        //If more than 1 product listed
        int numberOfProductName = dd.listOfProductName.size();
        if(numberOfProductName <= 1) {
            if(numberOfProductName == 1){
                dd.productName = dd.listOfProductName.get(0);
            }
            //List of device to add
            addProduct(dd);
            notifiedBody(dd);
            subjectToPerformanceEval(dd);
            productNewToMarket(dd);
            if(dd.riskClassification.toLowerCase().contains("list a"))
            conformToCTS(dd);
            saveProduct(dd);
        }else{
            for(String x: dd.listOfProductName){
                dd.productName = x;
                addProduct(dd);
                notifiedBody(dd);
                subjectToPerformanceEval(dd);
                productNewToMarket(dd);
                if(dd.riskClassification.toLowerCase().contains("list a"))
                conformToCTS(dd);
                saveProduct(dd);

                //Remove this if we find a better solution
                WaitUtils.nativeWaitInSeconds(1);
            }
        }
    }


    private void addGeneralMedicalDevice(DeviceData dd) {
        searchByGMDN(dd);
        customMade(dd);
        deviceSterile(dd);
        deviceMeasuring(dd);
        if (!dd.isCustomMade) {
            riskClassification(dd);
            notifiedBody(dd);
        }
        //saveProduct(dd);
    }

    private void productLabelName(DeviceData dd) {
        WaitUtils.waitForElementToBeClickable(driver, By.xpath(".//button[.='Add Product']"), TIMEOUT_5_SECOND, false);
        driver.findElement(By.xpath(".//button[.='Add Product']")).click();
        WaitUtils.waitForElementToBeClickable(driver, txtProductNameLabel, TIMEOUT_5_SECOND, false);
        txtProductNameLabel.sendKeys(RandomDataUtils.getRandomTestName("Label"));

        PageUtils.uploadDocument(fileUpload, "DeviceLabelDoc2.pdf", 1, 3);
        PageUtils.uploadDocument(listOfFileUploads.get(1), "DeviceInstructionForUse1.pdf", 1, 3);

        //Save product label details
        WaitUtils.waitForElementToBeClickable(driver, By.xpath(".//button[.='Save Product']"), TIMEOUT_5_SECOND, false);
        driver.findElement(By.xpath(".//button[.='Save Product']")).click();

    }


    private void productLabelName(String labelName) {
        WaitUtils.waitForElementToBeClickable(driver, By.xpath(".//button[.='Add Product']"), TIMEOUT_5_SECOND, false);
        driver.findElement(By.xpath(".//button[.='Add Product']")).click();
        WaitUtils.waitForElementToBeClickable(driver, txtProductNameLabel, TIMEOUT_5_SECOND, false);
        txtProductNameLabel.sendKeys(labelName);

        PageUtils.uploadDocument(fileUpload, "DeviceLabelDoc2.pdf", 1, 3);
        PageUtils.uploadDocument(listOfFileUploads.get(1), "DeviceInstructionForUse1.pdf", 1, 3);

        //Save product label details
        WaitUtils.waitForElementToBeClickable(driver, By.xpath(".//button[.='Save Product']"), TIMEOUT_5_SECOND, false);
        driver.findElement(By.xpath(".//button[.='Save Product']")).click();
    }

    private void conformToCTS(DeviceData dd) {
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

    private void saveProduct(DeviceData dd) {
        WebElement saveProduct = driver.findElement(By.xpath(".//button[.='Save product']"));
        saveProduct.click();
    }

    private void productNewToMarket(DeviceData dd) {
        if (dd.isNewProduct) {
            PageUtils.clickIfVisible(driver, radioProductNewYes);
        } else {
            PageUtils.clickIfVisible(driver, radioProductNewNo);
        }
    }

    private void subjectToPerformanceEval(DeviceData dd) {
        if (dd.isSubjectToPerfEval) {
            PageUtils.clickIfVisible(driver, radioSubjectToPerformanceEvalYes);
        } else {
            PageUtils.clickIfVisible(driver, radioSubjectToPerformanceEvalNo);
        }
    }

    private void addProduct(DeviceData dd) {
        WaitUtils.waitForElementToBeClickable(driver, addProduct, TIMEOUT_5_SECOND, false);
        addProduct.click();

        //Wait for form to be visible
        WaitUtils.waitForElementToBeClickable(driver, pdProductModel, TIMEOUT_5_SECOND, false);
        if (dd.productName != null || !dd.productName.equals("")) {
            pdProductName.sendKeys(dd.productName);
        } else if (dd.productMake != null || !dd.productMake.equals("")) {
            pdProductMake.sendKeys(dd.productMake);
        }

        pdProductModel.sendKeys(dd.productModel);
    }

    private void devicesCompatible(DeviceData dd) {
        if (dd.isDeviceCompatible) {
            PageUtils.clickIfVisible(driver, ppDevicesCompatibleYes);
        } else {
            PageUtils.clickIfVisible(driver, ppDevicesCompatibleNo);
        }
    }

    private void packIncorporated(DeviceData dd) {
        if (dd.isPackIncorporated) {
            PageUtils.clickIfVisible(driver, ppPackIncorporatedYes);
        } else {
            PageUtils.clickIfVisible(driver, ppPackIncorporatedNo);
        }
    }

    private void notifiedBody(DeviceData dd) {
        WaitUtils.waitForElementToBeClickable(driver, nb0086BSI, TIMEOUT_5_SECOND, false);
        //Select notified body
        if (dd.notifiedBody.toLowerCase().contains("0086")) {
            PageUtils.clickIfVisible(driver, nb0086BSI);
        }
    }

    private void riskClassificationIVD(DeviceData dd) {
        WaitUtils.waitForElementToBeClickable(driver, ivdIVDGeneral, TIMEOUT_5_SECOND, false);
        WaitUtils.nativeWaitInSeconds(1);

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

    private void selectDeviceType(DeviceData dd) {
        WaitUtils.waitForElementToBeClickable(driver, generalMedicalDevice, TIMEOUT_5_SECOND, false);
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

    private void deviceMeasuring(DeviceData dd) {
        WaitUtils.waitForElementToBeClickable(driver, radioDeviceMeasuringYes, TIMEOUT_5_SECOND, false);
        if (dd.isDeviceMeasuring) {
            PageUtils.clickIfVisible(driver, radioDeviceMeasuringYes);
        } else {
            PageUtils.clickIfVisible(driver, radioDeviceMeasuringNo);
        }
    }

    private void deviceSterile(DeviceData dd) {
        WaitUtils.waitForElementToBeClickable(driver, radioDeviceSterileYes, TIMEOUT_5_SECOND, false);
        if (dd.isDeviceSterile) {
            PageUtils.clickIfVisible(driver, radioDeviceSterileYes);
        } else {
            PageUtils.clickIfVisible(driver, radioDeviceSterileNo);
        }
    }

    private void customMade(DeviceData dd) {
        WaitUtils.waitForElementToBeClickable(driver, radioCustomMadeYes, TIMEOUT_5_SECOND, false);
        if (dd.isCustomMade) {
            PageUtils.clickIfVisible(driver, radioCustomMadeYes);
        } else {
            PageUtils.clickIfVisible(driver, radioCustomMadeNo);
            //riskClassification(dd);
        }
    }

    private void riskClassification(DeviceData dd) {
        WaitUtils.waitForElementToBeClickable(driver, radioRiskClass1, TIMEOUT_5_SECOND, false);
        WaitUtils.waitForElementToBeClickable(driver, nb0086BSI, TIMEOUT_5_SECOND, false);
        String lcRiskClassiffication = dd.riskClassification.toLowerCase();
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

    private void searchByGMDN(DeviceData dd) {
        if (dd.gmdnTermOrDefinition != null) {
            //Default is search by gmdn term or definition
            WaitUtils.waitForElementToBeClickable(driver, radioGMDNDefinitionOrTerm, TIMEOUT_5_SECOND, false);
            radioGMDNDefinitionOrTerm.click();
            WaitUtils.waitForElementToBeClickable(driver, tbxGMDNDefinitionOrTerm, TIMEOUT_5_SECOND, false);
            //tbxGMDNDefinitionOrTerm.sendKeys(dd.gmdnTermOrDefinition);
            PageUtils.selectFromAutoSuggests(driver, By.cssSelector("input.gwt-SuggestBox"), dd.gmdnTermOrDefinition);
        } else {
            WaitUtils.waitForElementToBeClickable(driver, tbxGMDNCode, TIMEOUT_5_SECOND, false);
            tbxGMDNCode.sendKeys(dd.gmdnCode);
            PageUtils.selectFromAutoSuggests(driver, By.cssSelector("input.gwt-SuggestBox"), dd.gmdnTermOrDefinition);
        }
    }

    public boolean isOptionToAddAnotherDeviceVisible() {
        WaitUtils.waitForElementToBeClickable(driver, btnAddAnotherDevice, TIMEOUT_10_SECOND, false);
        boolean isVisible = btnAddAnotherDevice.isDisplayed() && btnAddAnotherDevice.isEnabled();
        return isVisible;
    }

    public AddDevices submit() {
        WaitUtils.nativeWaitInSeconds(1);
        WaitUtils.waitForElementToBeClickable(driver, btnProceedToPayment, TIMEOUT_5_SECOND, false);
        btnProceedToPayment.click();
        return new AddDevices(driver);
    }

    public ExternalHomePage submitConfirm() {
        WaitUtils.nativeWaitInSeconds(1);
        WaitUtils.waitForElementToBeClickable(driver, submitConfirm, TIMEOUT_5_SECOND, false);
        submitConfirm.click();
        return new ExternalHomePage(driver);
    }
}
