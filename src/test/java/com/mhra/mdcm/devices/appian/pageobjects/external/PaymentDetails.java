package com.mhra.mdcm.devices.appian.pageobjects.external;

import com.mhra.mdcm.devices.appian.domains.newaccounts.DeviceDO;
import com.mhra.mdcm.devices.appian.pageobjects._Page;
import com.mhra.mdcm.devices.appian.utils.selenium.others.RandomDataUtils;
import com.mhra.mdcm.devices.appian.utils.selenium.page.AssertUtils;
import com.mhra.mdcm.devices.appian.utils.selenium.page.PageUtils;
import com.mhra.mdcm.devices.appian.utils.selenium.page.WaitUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Calendar;

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
    @FindBy(id = "contactContainerPanel1wayContainer")
    WebElement btnMakePayment;

    @Autowired
    public PaymentDetails(WebDriver driver) {
        super(driver);
    }

    public void performWorldPayPayment(String title) {
        String parentHandle = driver.getWindowHandle(); // get the current window handle
        System.out.println(parentHandle);               //Prints the parent window handle

        //Move to the newly opened tab
        for (String winHandle : driver.getWindowHandles()) { //Gets the new window handle
            System.out.println(winHandle);
            driver.switchTo().window(winHandle);        // switch focus of WebDriver to the next found window handle (that's your newly opened window)
        }

        //Enter payment details
        cardNumber.sendKeys("4444333322221111");
        cardholderName.sendKeys("Mr " + RandomDataUtils.getRandomTestNameStartingWith("Noor", 5));
        securityCode.sendKeys(RandomDataUtils.getRandomNumberBetween(101,999));
        PageUtils.selectByIndex(expiryMonth, RandomDataUtils.getRandomNumberBetween(1,12));
        PageUtils.selectByIndex(expiryYear, RandomDataUtils.getRandomNumberBetween(2,12));

        btnMakePayment.click();
        //Close and go back to devices
        driver.close();
        driver.switchTo().window(parentHandle);

    }


}
