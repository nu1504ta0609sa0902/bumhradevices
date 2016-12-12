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
    public static String organisationRegistered = "Registered Or Not";
    public static String deviceData = "Device Data";
    public static String manufacturerData = "Manufactuerer Or AuthorisedRep Data";
}
