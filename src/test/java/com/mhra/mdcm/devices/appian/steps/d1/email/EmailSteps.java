package com.mhra.mdcm.devices.appian.steps.d1.email;

import com.mhra.mdcm.devices.appian.session.SessionKey;
import com.mhra.mdcm.devices.appian.steps.common.CommonSteps;
import com.mhra.mdcm.devices.appian.utils.email.GmailEmail;
import com.mhra.mdcm.devices.appian.utils.selenium.page.WaitUtils;
import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.springframework.context.annotation.Scope;

/**
 * Created by TPD_Auto
 */
@Scope("cucumber-glue")
public class EmailSteps extends CommonSteps {


    @And("^I should received an email for stored manufacturer with heading containing \"([^\"]*)\" and stored application identifier$")
    public void iShouldReceivedAnEmailForStoredManufacturerWithHeadingContainingAndStoredApplicationIdentifier(String subjectHeading) throws Throwable {

        String accountNameOrReference = (String) scenarioSession.getData(SessionKey.newApplicationReferenceNumber);
        if(accountNameOrReference == null){
            accountNameOrReference = (String) scenarioSession.getData(SessionKey.organisationName);
        }

        boolean foundMessage = false;
        String messageBody = null;
        int attempt = 0;
        do {
            messageBody = GmailEmail.readMessageSubjectHeadingContainsIdentifier(7, 10, subjectHeading, accountNameOrReference);

            //Break from loop if invoices read from the email server
            if (messageBody!=null) {
                foundMessage = true;
                break;
            } else {
                //Wait for 10 seconds and try again, Thread.sleep required because this is checking email
                WaitUtils.nativeWaitInSeconds(10);
            }
            attempt++;
        } while (!foundMessage && attempt < 15);

        Assert.assertThat("Message should not be empty : " + messageBody, messageBody!=null, Matchers.is(true));
    }

    @And("^I should received an email for stored account with heading \"([^\"]*)\"$")
    public void iShouldReceivedAnEmailForStoredAccountWithHeading(String emailHeading) throws Throwable {

        String org = (String) scenarioSession.getData(SessionKey.newAccountName);
        String accountNameOrReference = (String) scenarioSession.getData(SessionKey.newApplicationReferenceNumber);

        boolean foundMessage = false;
        String messageBody = null;
        int attempt = 0;
        do {
            messageBody = GmailEmail.readMessageForSpecifiedOrganisations(7, 10, emailHeading, accountNameOrReference);

            //Break from loop if invoices read from the email server
            if (messageBody!=null) {
                foundMessage = true;
                break;
            } else {
                //Wait for 10 seconds and try again, Thread.sleep required because this is checking email
                WaitUtils.nativeWaitInSeconds(10);
            }
            attempt++;
        } while (!foundMessage && attempt < 15);

        Assert.assertThat("Message should not be empty : " + messageBody, messageBody!=null, Matchers.is(true));
        //Assert.assertThat("Organisation Name Expected : " + org, messageBody.contains(org) || messageBody.contains(accountNameOrReference), Matchers.is(true));
    }

    @And("^I should received an email for stored manufacturer with heading \"([^\"]*)\"$")
    public void iShouldReceivedAnEmailForStoredManufacturerWithHeading(String emailHeading) throws Throwable {
        String accountNameOrReference = (String) scenarioSession.getData(SessionKey.newApplicationReferenceNumber);

        boolean foundMessage = false;
        String messageBody = null;
        int attempt = 0;
        do {
            messageBody = GmailEmail.readMessageForSpecifiedOrganisations(7, 10, emailHeading, accountNameOrReference);

            //Break from loop if invoices read from the email server
            if (messageBody!=null) {
                foundMessage = true;
                break;
            } else {
                //Wait for 10 seconds and try again, Thread.sleep required because this is checking email and its outside of selenium scope
                WaitUtils.nativeWaitInSeconds(10);
            }
            attempt++;
        } while (!foundMessage && attempt < 15);

        Assert.assertThat("Message should not be empty : " + messageBody, messageBody!=null, Matchers.is(true));
    }


    @And("^I should received an email with subject heading \"([^\"]*)\"$")
    public void iShouldReceivedAnEmailWithSubjectHeading(String emailHeading) throws Throwable {

        boolean foundMessage = false;
        String messageBody = null;
        int attempt = 0;
        do {
            messageBody = GmailEmail.getMessageReceivedWithSubjectHeading(3, 5, emailHeading);

            //Break from loop if invoices read from the email server
            if (messageBody!=null) {
                scenarioSession.putData(SessionKey.emailBody, messageBody);
                break;
            } else {
                //Wait for 10 seconds and try again, Thread.sleep required because this is checking email
                WaitUtils.nativeWaitInSeconds(10);
            }
            attempt++;
        } while (!foundMessage && attempt < 15);

        Assert.assertThat("Message should not be empty : " + messageBody, messageBody!=null, Matchers.is(true));
    }


    @And("^I should received an email for stored manufacturer with heading \"([^\"]*)\" and stored application identifier$")
    public void iShouldReceivedAnEmailForStoredManufacturerWithHeadingAndApplicationIdentifier(String emailHeading) throws Throwable {
        String accountNameOrReference = (String) scenarioSession.getData(SessionKey.newApplicationReferenceNumber);

        boolean foundMessage = false;
        String messageBody = null;
        int attempt = 0;
        do {
            messageBody = GmailEmail.getMessageReceivedWithHeadingAndBodyContainsIdentifier(7, 10, emailHeading, accountNameOrReference);

            //Break from loop if invoices read from the email server
            if (messageBody!=null) {
                scenarioSession.putData(SessionKey.emailBody, messageBody);
                foundMessage = true;
                break;
            } else {
                //Wait for 10 seconds and try again, Thread.sleep required because this is checking email and its outside of selenium scope
                WaitUtils.nativeWaitInSeconds(10);
            }
            attempt++;
        } while (!foundMessage && attempt < 15);

        Assert.assertThat("Message should not be empty : " + messageBody, messageBody!=null, Matchers.is(true));
        Assert.assertThat("Found message with heading : " + emailHeading + ", And reference : " + accountNameOrReference, foundMessage, Matchers.is(true));
    }

    @And("^I should received an email with password for new account with heading \"([^\"]*)\" and stored username$")
    public void iShouldReceivedAnEmailWithPasswordForNewAccountWithHeadingAndApplicationIdentifier(String emailHeading) throws Throwable {
        String userName = (String) scenarioSession.getData(SessionKey.newUserName);

        boolean foundMessage = false;
        String messageBody = null;
        int attempt = 0;
        do {
            messageBody = GmailEmail.getMessageReceivedWithHeadingAndBodyContainsIdentifier(2, 10, emailHeading, userName);

            //Break from loop if invoices read from the email server
            if (messageBody!=null) {
                scenarioSession.putData(SessionKey.emailBody, messageBody);
                foundMessage = true;
                break;
            } else {
                //Wait for 10 seconds and try again, Thread.sleep required because this is checking email and its outside of selenium scope
                WaitUtils.nativeWaitInSeconds(10);
            }
            attempt++;
        } while (!foundMessage && attempt < 15);

        if(messageBody!=null){
            String tempPassword = messageBody.substring(messageBody.indexOf("d:")+3, messageBody.indexOf("To log")-1);
            scenarioSession.putData(SessionKey.temporaryPassword, tempPassword);
        }
        Assert.assertThat("Message should not be empty : " + messageBody, messageBody!=null, Matchers.is(true));
        Assert.assertThat("Found message with heading : " + emailHeading + ", And username : " + userName, foundMessage, Matchers.is(true));
    }
}
