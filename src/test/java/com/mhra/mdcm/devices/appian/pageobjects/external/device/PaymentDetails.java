package com.mhra.mdcm.devices.appian.pageobjects.external.device;

import com.mhra.mdcm.devices.appian.pageobjects._Page;
import com.mhra.mdcm.devices.appian.pageobjects.external.manufacturer.ManufacturerList;
import com.mhra.mdcm.devices.appian.session.ScenarioSession;
import com.mhra.mdcm.devices.appian.session.SessionKey;
import com.mhra.mdcm.devices.appian.utils.selenium.page.PageUtils;
import com.mhra.mdcm.devices.appian.utils.selenium.page.WaitUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by TPD_Auto
 *
 * When viewing products related to added devices
 */
@Component
public class PaymentDetails extends _Page {


    //Payment methods and fee details
    @FindBy(xpath = ".//*[contains(text(),'payment method')]/following::img[1]")
    WebElement paymentWorldPay;
    @FindBy(xpath = ".//*[contains(text(),'payment method')]/following::img[2]")
    WebElement paymentBACS;
    @FindBy(xpath = ".//*[contains(text(),'payment method')]/following::img[3]")
    WebElement linkHereToInitiateWorldpay;
    @FindBy(xpath = ".//a[contains(text(),'Proceed to worldpay')]")
    WebElement linkProceedToWorldpay;
    @FindBy(xpath = ".//button[contains(text(),'Submit Application')]")
    WebElement btnCompleteApplication;
    @FindBy(xpath = ".//div[@role='listbox']")
    WebElement ddAddressBox;
    @FindBy(xpath = ".//*[contains(text(), 'Payment details')]/following::strong[@class='StrongText---richtext_strong']")
    WebElement feeTotalAmount;

    //File upload buttons
    @FindBy(css = ".MultipleFileUploadWidget---ui-inaccessible")
    WebElement multiFileUpload;

    //Back to services
    @FindBy(partialLinkText = "Back to service")
    WebElement linkBackToService;

    //Reference number
    @FindBy(xpath = ".//h3[contains(text(), 'Application complete')]/following::h4[1]")
    WebElement txtApplicationReference;

    @Autowired
    public PaymentDetails(WebDriver driver) {
        super(driver);
    }


    public PaymentDetails enterPaymentDetails(String paymentMethod, ScenarioSession scenarioSession) {
        String proofOfPayments = "CompletionOfTransfer1.pdf";
        scenarioSession.putData(SessionKey.paymentProofDocuments, proofOfPayments);
        WaitUtils.waitForElementToBeClickable(driver, ddAddressBox, TIMEOUT_15_SECOND);

        //Select billing address:
        PageUtils.selectFromDropDown(driver, ddAddressBox , "Registered Address", false);

        if(paymentMethod.toLowerCase().contains("world")){
            WaitUtils.waitForElementToBeClickable(driver, paymentWorldPay, TIMEOUT_15_SECOND);
            paymentWorldPay.click();

            //Click "here" link
            WaitUtils.waitForElementToBeClickable(driver, linkHereToInitiateWorldpay, TIMEOUT_10_SECOND);
            linkHereToInitiateWorldpay.click();

            //Focus on different tab
            PaymentWorldPay payment = new PaymentWorldPay(driver);
            payment.performWorldPayPayment("Card Details", scenarioSession);

        }else if(paymentMethod.toLowerCase().contains("bacs")){
            paymentBACS.click();
            WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
            PageUtils.uploadDocument(multiFileUpload, proofOfPayments, 1, 3);
        }

        //Complete the application
        WaitUtils.waitForElementToBeClickable(driver, btnCompleteApplication, TIMEOUT_15_SECOND);
        btnCompleteApplication.click();
        return new PaymentDetails(driver);
    }


    public String getApplicationReferenceNumber() {
        WaitUtils.waitForElementToBeClickable(driver, txtApplicationReference, TIMEOUT_10_SECOND);
        return txtApplicationReference.getText();
    }

    public ManufacturerList backToService() {
        try {
            WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
            WaitUtils.waitForElementToBeClickable(driver, linkBackToService, TIMEOUT_10_SECOND);
            linkBackToService.click();
            log.info("Link: Back to services");
        }catch (Exception e){}
        return new ManufacturerList(driver);
    }
}
