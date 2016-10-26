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
}
