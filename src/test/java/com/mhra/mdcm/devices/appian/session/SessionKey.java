package com.mhra.mdcm.devices.appian.session;

/**
 * Will be used in ScenarioSession
 * <p>
 * Helps with maintaining data between steps
 *
 * @author TPD_Auto
 */
public class SessionKey {
    //Current env and scenario
    public static String environment;
    public static String scenarioName;

//    public static final String ECID = "EC ID";
//    public static String storedValue = "StoredValue";
//
//    public static String storedNotification = "Notification";
//    public static String invoice = "Invoice";
//    public static String notificationStatus = "Notification Status";
//    public static String submitter = "Submitter";
//    public static String listOfInvoices = "List Of Invoices";
//    public static String notificationCount = "Notifications Count";
//    public static String comment = "Comments";
//    public static String substance = "Substance";
//    public static String bannedTxt = "Product Banned";
//    public static String qaPercentageValue = "Quality Assurance";
//    public static String previousECID = "Previous ECID";
//    public static String searchTerm = "Search Term";
//    public static String reportName = "Report Name";
//    public static String emailContent = "Email Attachment Content";
//    public static String zipFileLocation = "Zip Notification Data File";
//    public static String xmlDataFileLocation = "XML Notification Data File";
//    public static String tcaNumber = "TCA Number New";
//    public static String isDisplayed = "Is Something Displayed";
    public static String loggedInUser = "Current Logged In User";
}
