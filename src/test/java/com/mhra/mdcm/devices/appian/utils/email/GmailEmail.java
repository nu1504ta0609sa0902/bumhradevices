package com.mhra.mdcm.devices.appian.utils.email;

import com.mhra.mdcm.devices.appian.domains.newaccounts.sort.SortByMessageNumber;
import com.mhra.mdcm.devices.appian.session.SessionKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMultipart;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author TPD_Auto
 */
public class GmailEmail {

    public static final Logger log = LoggerFactory.getLogger(GmailEmail.class);

    public static final String UNINVOICED_NOTIFICATIONS = "Uninvoiced Notifications";
    public static final String ANNUAL_INVOICED_NOTIFICATIONS = "Annual Notification Invoices";

    public static void main(String[] args) {
        String tempPassword = null;

        String messageBody = GmailEmail.accountCreatedInLastXDays(3600*7, 300, "Appian for MHRA (TEST) account creation");
        log.warn(messageBody);
    }

    private static String accountCreatedInLastXDays(double min, int numberOfMessgesToCheck, String subjectHeading) {
        log.info("Find all the accounts created in the specified time : " + subjectHeading);
        String bodyText = null;
        Properties props = getEmailServerConfiguration();

        try {
            Store store = null;
            Folder inbox = null;
            Message[] messages = getMessagesFromEmailServer(props, store, inbox);

            for (int i = 0; i < messages.length; i++) {
                Message message = messages[i];
                Date sentDate = message.getSentDate();
                String subject = message.getSubject();
                //log.warn(subject);
                Address[] froms = message.getFrom();

                for (Address from : froms) {
                    String emailAddress = froms == null ? null : ((InternetAddress) from).getAddress();
                    if (emailAddress != null && (emailAddress.contains("appian") || emailAddress.contains("incessant") || emailAddress.contains("mhra.gov.uk"))) {

                        boolean isMessageReceivedToday = isMessageReceivedToday(subject, subjectHeading, sentDate);

                        //Ignore the ones created today
                        if ((!isMessageReceivedToday && subject.contains(subjectHeading))) {

                            //If recent
                            boolean isRecent = receivedInLast(min, sentDate);
                            if(isRecent){
                                if (isRecent && subject.contains(subjectHeading)) {
                                    String messageBody = getTextFromMessage(message);

                                    String user = messageBody.substring(messageBody.indexOf("e:")+3, messageBody.indexOf("Temp")-1);
                                    System.out.println(user);
                                }
                            }
                        } else {
                            //log.warn("Message is old or not relevant" );
                        }
                    }
                }

                if (i > numberOfMessgesToCheck || bodyText != null) {
                    //Most likely no emails received yet
                    log.info(bodyText);
                    break;
                }
            }

            if(inbox!=null)
                inbox.close(true);
            if(store!=null)
                store.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return bodyText;
    }


    /**
     * Read message received in the last few minutes for the specified organisation
     *
     * @param min
     * @param numberOfMessgesToCheck
     * @param subjectHeading
     * @param organisationIdentifier
     * @return
     */
    public static String readMessageForSpecifiedOrganisations(double min, int numberOfMessgesToCheck, String subjectHeading, String organisationIdentifier) {

        log.info("Waiting for email with heading : " + subjectHeading);
        String bodyText = null;
        Properties props = getEmailServerConfiguration();

        try {
            Store store = null;
            Folder inbox = null;
            Message[] messages = getMessagesFromEmailServer(props, store, inbox);

            for (int i = 0; i < messages.length; i++) {
                Message message = messages[i];
                Date sentDate = message.getSentDate();
                String subject = message.getSubject();
                //log.warn(subject);
                Address[] froms = message.getFrom();

                for (Address from : froms) {
                    String emailAddress = froms == null ? null : ((InternetAddress) from).getAddress();
                    if (emailAddress != null && (emailAddress.contains("appian") || emailAddress.contains("incessant") || emailAddress.contains("mhra.gov.uk"))) {

                        boolean isMessageReceivedToday = isMessageReceivedToday(subject, subjectHeading, sentDate);
                        //isMessageReceivedToday = true;
                        if ((isMessageReceivedToday && subject.contains(subjectHeading))) {

                            //If recent
                            boolean isRecent = receivedInLast(min, sentDate);
                            if(isRecent){
                                if (isRecent && subject.contains(subjectHeading)) {
                                    log.warn("---------------------------------");
                                    log.warn("Recent email received : " + subject);
                                    log.warn("---------------------------------");
                                    String body = getTextFromMessage(message);
                                    //log.warn("Body Text : " + body);

                                    if (body.contains(organisationIdentifier)) {
                                        log.info("Message RECEIVED");
                                        bodyText = body;
                                        break;
                                    }
                                }
                            }
                        } else {
                            //log.warn("Message is old or not relevant" );
                        }
                    }
                }

                if (i > numberOfMessgesToCheck || bodyText != null) {
                    //Most likely no emails received yet
                    log.info(bodyText);
                    break;
                }
            }

            if(inbox!=null)
            inbox.close(true);
            if(store!=null)
            store.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return bodyText;

    }

    public static String getMessageReceivedWithHeadingAndBodyContainsIdentifier(double min, int numberOfMessgesToCheck, String subjectHeading, String organisationIdentifier) {

        log.info("Waiting for email with heading : " + subjectHeading);
        String bodyText = null;
        Properties props = getEmailServerConfiguration();

        try {
            Store store = null;
            Folder inbox = null;
            Message[] messages = getMessagesFromEmailServer(props, store, inbox);

            for (int i = 0; i < messages.length; i++) {
                Message message = messages[i];
                Date sentDate = message.getSentDate();
                String subject = message.getSubject();
                //log.warn(subject);
                Address[] froms = message.getFrom();

                for (Address from : froms) {
                    String emailAddress = froms == null ? null : ((InternetAddress) from).getAddress();
                    if (emailAddress != null && (emailAddress.contains("appian") || emailAddress.contains("incessant") || emailAddress.contains("mhra.gov.uk"))) {

                        //If received today and subject contains the correct text
                        boolean isMessageReceivedToday = isMessageReceivedToday(subject, subjectHeading, sentDate);
                        if (isMessageReceivedToday && subject.contains(subjectHeading)) {

                            //If message is received in the last X min
                            boolean isRecent = receivedInLast(min, sentDate);
                            if (isRecent) {
                                log.warn("---------------------------------");
                                log.warn("Recent email received : " + subject);
                                log.warn("---------------------------------");
                                String body = getTextFromMessage(message);
                                //log.warn("Body Text : " + body);

                                if (body.contains(organisationIdentifier)) {
                                    log.info("Message RECEIVED");
                                    bodyText = body;
                                    break;
                                }
                            }
                        }
                    }
                }

                if (i > numberOfMessgesToCheck || bodyText != null) {
                    //Most likely no emails received yet
                    log.info(bodyText);
                    break;
                }
            }

            if(inbox!=null)
            inbox.close(true);
            if(store!=null)
            store.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return bodyText;

    }

    public static String readMessageSubjectHeadingContainsIdentifier(double min, int numberOfMessgesToCheck, String subjectHeading, String organisationIdentifier) {

        log.info("Waiting for email with subject heading containing : " + subjectHeading + " and identifier : " + organisationIdentifier);
        String bodyText = null;
        Properties props = getEmailServerConfiguration();

        try {
            Store store = null;
            Folder inbox = null;
            Message[] messages = getMessagesFromEmailServer(props, store, inbox);

            for (int i = 0; i < messages.length; i++) {
                Message message = messages[i];
                Date sentDate = message.getSentDate();
                String subject = message.getSubject();
                //log.warn(subject);
                Address[] froms = message.getFrom();

                for (Address from : froms) {
                    String emailAddress = froms == null ? null : ((InternetAddress) from).getAddress();
                    if (emailAddress != null && (emailAddress.contains("appian") || emailAddress.contains("incessant") || emailAddress.contains("mhra.gov.uk")|| emailAddress.contains("appiancloud.com")|| emailAddress.contains("worldpay.com"))) {

                        //If received today and subject contains the correct text
                        boolean isMessageReceivedToday = isMessageReceivedToday(subject, subjectHeading, sentDate);
                        if (isMessageReceivedToday && subject.contains(subjectHeading) && subject.contains(organisationIdentifier)) {

                            //If message is received in the last X min
                            boolean isRecent = receivedInLast(min, sentDate);
                            if (isRecent) {
                                log.warn("---------------------------------");
                                log.warn("Recent email received : " + subject);
                                log.warn("---------------------------------");
                                String body = getTextFromMessage(message);
                                if(body!=null){
                                    log.info("Message RECEIVED");
                                    bodyText = body;
                                    break;
                                }
                            }
                        }
                    }
                }

                if (i > numberOfMessgesToCheck || bodyText != null) {
                    //Most likely no emails received yet
                    log.info(bodyText);
                    break;
                }
            }

            if(inbox!=null)
                inbox.close(true);
            if(store!=null)
                store.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return bodyText;
    }

    public static String getMessageReceivedWithSubjectHeading(double min, int numberOfMessgesToCheck, String subjectHeading) {

        log.info("Waiting for email with heading : " + subjectHeading);
        String bodyText = null;
        Properties props = getEmailServerConfiguration();

        try {
            Store store = null;
            Folder inbox = null;
            Message[] messages = getMessagesFromEmailServer(props, store, inbox);

            for (int i = 0; i < messages.length; i++) {
                Message message = messages[i];
                Date sentDate = message.getSentDate();
                String subject = message.getSubject();
                //log.warn(subject);
                Address[] froms = message.getFrom();

                for (Address from : froms) {
                    String emailAddress = froms == null ? null : ((InternetAddress) from).getAddress();
                    if (emailAddress != null && (emailAddress.contains("appian") || emailAddress.contains("incessant") || emailAddress.contains("mhra.gov.uk")|| emailAddress.contains("worldpay.com"))) {

                        //If received today and subject contains the correct text
                        boolean isMessageReceivedToday = isMessageReceivedToday(subject, subjectHeading, sentDate);
                        if (isMessageReceivedToday && subject.contains(subjectHeading)) {

                            //If message is received in the last X min
                            boolean isRecent = receivedInLast(min, sentDate);
                            if (isRecent) {
                                log.warn("---------------------------------");
                                log.warn("Recent email received : " + subject);
                                log.warn("---------------------------------");
                                String body = getTextFromMessage(message);
                                if(body!=null){
                                    log.info("Message RECEIVED");
                                    bodyText = body;
                                    break;
                                }
                            }
                        }
                    }
                }

                if (i > numberOfMessgesToCheck || bodyText != null) {
                    //Most likely no emails received yet
                    log.info(bodyText);
                    break;
                }
            }

            if(inbox!=null)
                inbox.close(true);
            if(store!=null)
                store.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return bodyText;

    }

    private static Message[] getMessagesFromEmailServer(Properties props, Store store, Folder inbox) {
        try {
            final String username = "mhra.uat@gmail.com";
            final String password = "MHRA1234";

            Session session = Session.getInstance(props,
                    new javax.mail.Authenticator() {
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(username, password);
                        }
                    });

            store = session.getStore("imaps");
            store.connect("smtp.gmail.com", username, password);

            inbox = store.getFolder("inbox");
            inbox.open(Folder.READ_ONLY);

            int messageCount = inbox.getMessageCount();
            Message[] messages = inbox.getMessages();
            List<Message> lom = Arrays.asList(messages);
            Collections.sort(lom, new SortByMessageNumber());
            messages = lom.toArray(new Message[lom.size()]);
            //log.warn("Number of messages : " + messages.length);
            return messages;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static Properties getEmailServerConfiguration() {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        return props;
    }

    /**
     * We are only interested in todays email
     *
     * @param subject
     * @param subjectHeading
     * @param sentDate       @return
     */
    private static boolean isMessageReceivedToday(String subject, String subjectHeading, Date sentDate) {

        if (subjectHeading == null || subjectHeading.equals("")) {
            return true;
        } else {
            String date = sentDate.toString();

            //Verify it was received today
            Calendar calendar = Calendar.getInstance();
            int dom = calendar.get(Calendar.DAY_OF_MONTH);
            String monthStr = new SimpleDateFormat("MMM").format(sentDate);
            String dayStr = new SimpleDateFormat("EEE").format(sentDate);
            String dayNumberStr = String.valueOf(dom);
            if (dom < 10) {
                dayNumberStr = "0" + dayNumberStr;
            }

            if (date.contains(dayNumberStr) && date.contains(dayStr) && date.contains(monthStr))
                return true;
            else
                return false;
        }
    }

    /**
     * Check if email was received in the last X minuit
     *
     * @param i            received in last few minuites
     * @param receivedDate
     * @return
     */
    private static boolean receivedInLast(double i, Date receivedDate) {
        long receivedTime = receivedDate.getTime();
        long currentTime = new Date().getTime();
        double time = i * 60 * 1000;

        if (receivedTime > (currentTime - time))
            return true;
        else
            return false;
    }


    private static String getTextFromMessage(Message message) throws Exception {
        String result = "";
        if (message.isMimeType("text/plain")) {
            result = message.getContent().toString();
        } else if (message.isMimeType("multipart/*")) {
            MimeMultipart mimeMultipart = (MimeMultipart) message.getContent();
            result = getTextFromMimeMultipart(mimeMultipart);
        }
        return result;
    }

    private static String getTextFromMimeMultipart(
            MimeMultipart mimeMultipart) throws Exception {
        String result = "";
        int count = mimeMultipart.getCount();
        for (int i = 0; i < count; i++) {
            BodyPart bodyPart = mimeMultipart.getBodyPart(i);
            if (bodyPart.isMimeType("text/plain")) {
                result = result + "\n" + bodyPart.getContent();
                break; // without break same text appears twice in my tests
            } else if (bodyPart.isMimeType("text/html")) {
                String html = (String) bodyPart.getContent();
                result = result + "\n" + org.jsoup.Jsoup.parse(html).text();
            } else if (bodyPart.getContent() instanceof MimeMultipart) {
                result = result + getTextFromMimeMultipart((MimeMultipart) bodyPart.getContent());
            }
        }
        return result;
    }
}