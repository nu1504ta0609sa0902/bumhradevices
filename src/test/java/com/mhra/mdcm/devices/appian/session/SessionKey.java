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

    public static String loggedInUser = "Current Logged In User";
    public static String organisationName = "Organisation Name";
    public static String position = "Position Of Item";
    public static String searchTerm = "Search Term";
    public static String updatedData = "Account Data Object";
    public static String taskType = "New Account Or Manufacturer";
    public static String registeredStatus = "Registered Or Not";
    public static String deviceData = "Device Data";
    public static String manufacturerData = "Manufactuerer Or AuthorisedRep Data";
    public static String organisationCountry = "Organisation Country";
    public static String listOfDeviceDO = "List of Device Data";
    public static String autoSuggestResults = "List of items returned by autosuggest fields";
    public static String listOfGmndsAdded = "List of GMDNs added by the current scenario";
    public static String listOfProductsAdded = "List of products added";
    public static String newAccountName = "New Account Name";
    public static String numberOfCertificates = "Number of certificates";
    public static String listOfCFSCountryPairs = "List of CFS Country pairs";
    public static String newApplicationReferenceNumber = "Account Reference Number Created For A New Account";
    public static String emailBody = "Email body text";
    public static String newUserName = "Username for new account";
    public static String temporaryPassword = "Temporary Password created for new account";
    public static String updatedPassword = "Update Password for new account";
    public static String listOfAllCertificatesAddedToApplication = "All the certificates added to application";
    public static String taskAssignedTo = "Task can be assigned to a colleague or Nobody";
    public static String temporaryReference = "Temporary reference created when an application is saved";
    public static String address = "Address we expect";
    public static String postCode = "Postcode we expect";
    public static String paymentMethod = "Payment method WorldPay or BACS";
    public static String paymentProofDocuments = "Proof of payment document";
}
