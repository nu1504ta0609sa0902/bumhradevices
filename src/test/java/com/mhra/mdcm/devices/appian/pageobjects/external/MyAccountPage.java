package com.mhra.mdcm.devices.appian.pageobjects.external;

import com.mhra.mdcm.devices.appian.domains.MyAccount;
import com.mhra.mdcm.devices.appian.pageobjects._Page;
import com.mhra.mdcm.devices.appian.utils.selenium.others.TestHarnessUtils;
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
 * We can use this for both editing of PERSON AND ORGANISATION
 *
 * As the pages are small and simple
 *
 * We can move to its own PO later on
 */
@Component
public class MyAccountPage extends _Page {

    @FindBy(xpath = ".//button[contains(text(),'Organisation')]")
    WebElement amendOrganisationDetails;

    @FindBy(xpath = ".//button[contains(text(),'Person')]")
    WebElement amendContactPersonDetails;

    @FindBy(css = ".//span[contains(text(),'Title')]//following::select[1]")
    WebElement title;
    @FindBy(css = ".//span[contains(text(),'First')]//following::input[1]")
    WebElement firstName;
    @FindBy(css = ".//span[contains(text(),'Last')]//following::input[1]")
    WebElement lastName;
    @FindBy(css = ".//span[contains(text(),'Job')]//following::input[1]")
    WebElement jobTitle;
    @FindBy(css = ".//span[contains(text(),'Email')]//following::input[1]")
    WebElement email;
    @FindBy(css = ".//span[contains(text(),'Telephone')]//following::input[1]")
    WebElement telephone;

    @FindBy(xpath = ".//button[contains(text(),'Submit')]")
    WebElement submitBtn;
    @FindBy(css = ".//button[contains(text(),'Cancel')]")
    WebElement cancelBtn;




    @Autowired
    public MyAccountPage(WebDriver driver) {
        super(driver);
    }

    public MyAccountPage amendContactPersonDetails() {
        amendContactPersonDetails.click();
        return new MyAccountPage(driver);
    }

    public MyAccountPage updateFollowingFields(String keyValuePairToUpdate) {
        WaitUtils.waitForElementToBeClickable(driver, submitBtn, TIMEOUT_DEFAULT, false);

        String[] dataPairs = keyValuePairToUpdate.split(",");

        for(String pairs: dataPairs){
            String[] split = pairs.split("=");
            String key = split[0];
            String value = split[1];
            if(key.equals("contact.title")){
                PageUtils.selectByText(title, "Mr.");
//            }else if(key.equals("contact.firstname")){
//                TestHarnessUtils.updateElementValue(driver, firstName, updatedData.organisationName, TIMEOUT_DEFAULT);
//            }else if(key.equals("contact.lastname")){
//                TestHarnessUtils.updateElementValue(driver, lastName, updatedData.organisationName, TIMEOUT_DEFAULT);
//            }else if(key.equals("contact.job.title")){
//                TestHarnessUtils.updateElementValue(driver, jobTitle, updatedData.organisationName, TIMEOUT_DEFAULT);
//            }else if(key.equals("contact.email")){
//                TestHarnessUtils.updateElementValue(driver, email, updatedData.organisationName, TIMEOUT_DEFAULT);
//            }else if(key.equals("contact.telephone")){
//                TestHarnessUtils.updateElementValue(driver, telephone, updatedData.organisationName, TIMEOUT_DEFAULT);
            }
        }

        PageUtils.doubleClick(driver, submitBtn);

        return new MyAccountPage(driver);
    }

    public MyAccount savePreviousDetails() {
        String titleText = title.getText();
        String firstNameText = firstName.getText();
        String lastNameText = lastName.getText();
        String jobTitleText = jobTitle.getText();
        String emailText = email.getText();
        String telephoneText = telephone.getText();

        MyAccount saveAccountDetails = new MyAccount();
        saveAccountDetails.setTitle(titleText);
        saveAccountDetails.setFirstName(firstNameText);
        saveAccountDetails.setLastName(lastNameText);
        saveAccountDetails.setJobTitle(jobTitleText);
        saveAccountDetails.setEmail(emailText);
        saveAccountDetails.setTelephone(telephoneText);

        return saveAccountDetails;
    }
}
