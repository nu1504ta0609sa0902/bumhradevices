package com.mhra.mdcm.devices.appian.pageobjects.external;

import com.mhra.mdcm.devices.appian.domains.newaccounts.ManufacturerRequestDO;
import com.mhra.mdcm.devices.appian.pageobjects._Page;
import com.mhra.mdcm.devices.appian.session.ScenarioSession;
import com.mhra.mdcm.devices.appian.session.SessionKey;
import com.mhra.mdcm.devices.appian.utils.selenium.others.RandomDataUtils;
import com.mhra.mdcm.devices.appian.utils.selenium.page.PageUtils;
import com.mhra.mdcm.devices.appian.utils.selenium.page.WaitUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by TPD_Auto
 * <p>
 * P
 */
@Component
public class PaymentDetails extends _Page {

    //Order summary
    @FindBy(id = "language")
    WebElement language;
    @FindBy(css = ".box.summary td.summaryRef")
    WebElement paymentReference;
    @FindBy(css = ".box.summary td.summaryAmount")
    WebElement paymentAmount;

    //Payment details
    @FindBy(id = "cardNumber")
    WebElement cardNumber;
    @FindBy(id = "cardholderName")
    WebElement cardholderName;
    @FindBy(id = "securityCode")
    WebElement securityCode;
    @FindBy(id = "expiryMonth")
    WebElement expiryMonth;
    @FindBy(id = "expiryYear")
    WebElement expiryYear;

    //Contact email and address
    @FindBy(id = "contactDetailscontactEmailRo")
    WebElement contactDetailscontactEmailRo;
    @FindBy(id = "billingAddressLine1Ro")
    WebElement billingAddressLine1Ro;
    @FindBy(id = "billingAddressLine2Ro")
    WebElement billingAddressLine2Ro;
    @FindBy(id = "billingAddressCityRo")
    WebElement billingAddressCityRo;
    @FindBy(id = "billingAddressPostcodeRo")
    WebElement billingAddressPostcodeRo;
    @FindBy(id = "billingAddressCountryRo")
    WebElement billingAddressCountryRo;

    //Submit
    @FindBy(id = "submitButton")
    WebElement btnMakePayment;
    @FindBy(xpath = ".//*[contains(text(), 'Payment complete')]")
    WebElement paymentCompletedText;

    @Autowired
    public PaymentDetails(WebDriver driver) {
        super(driver);
    }

    public void performWorldPayPayment(String title, ScenarioSession scenarioSession) {
        ManufacturerRequestDO manufacaturerData = (ManufacturerRequestDO) scenarioSession.getData(SessionKey.manufacturerData);
        String parentHandle = driver.getWindowHandle();

        //Move to the newly opened tab
        boolean switched = false;
        for (String winHandle : driver.getWindowHandles()) {
            if(!parentHandle.equals(winHandle)) {
                driver.switchTo().window(winHandle);
                switched = true;
            }
        }

        try {
            //Enter payment details
            WaitUtils.waitForElementToBeClickable(driver, cardNumber, TIMEOUT_20_SECOND);
            cardNumber.sendKeys("4444333322221111");
            cardholderName.sendKeys("AUTHORISED");
            PageUtils.selectByIndex(expiryMonth, RandomDataUtils.getRandomNumberBetween(1, 12));
            PageUtils.selectByIndex(expiryYear, RandomDataUtils.getRandomNumberBetween(2, 7));

            //Too fast, value is lost therefore we need to click on somewhere else
            cardholderName.click();
            WaitUtils.waitForElementToBeClickable(driver, securityCode, TIMEOUT_3_SECOND);
            securityCode.sendKeys("555");

            //CAPTCHA


            //Verify descriptions and emails are correct
            if(manufacaturerData!=null) {
                boolean contains = contactDetailscontactEmailRo.getText().contains(manufacaturerData.email);
                if (!contains) {
                    throw new RuntimeException("Email is not valid, it should be : " + manufacaturerData.email);
                }
            }

            //Before submitting: check length of security key is correct
            if(!PageUtils.isElementClickable(driver, btnMakePayment, TIMEOUT_2_SECOND) && securityCode.getText().length() != 3){
                securityCode.clear();
                WaitUtils.nativeWaitInSeconds(1);
                securityCode.sendKeys("555");
            }

            //Submit for payment
            WaitUtils.waitForElementToBeClickable(driver, btnMakePayment, TIMEOUT_5_SECOND);
            btnMakePayment.click();

            //Close and go back to devices: wait for payment to complete
            PageUtils.isElementClickable(driver, paymentCompletedText, TIMEOUT_10_SECOND);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(switched) {
                driver.close();
                driver.switchTo().window(parentHandle);
            }
        }

    }


}
