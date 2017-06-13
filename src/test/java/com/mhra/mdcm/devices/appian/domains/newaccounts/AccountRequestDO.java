package com.mhra.mdcm.devices.appian.domains.newaccounts;

import com.mhra.mdcm.devices.appian.session.ScenarioSession;
import com.mhra.mdcm.devices.appian.session.SessionKey;
import com.mhra.mdcm.devices.appian.utils.selenium.others.FileUtils;
import com.mhra.mdcm.devices.appian.utils.selenium.others.RandomDataUtils;
import com.mhra.mdcm.devices.appian.utils.selenium.others.TestHarnessUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TPD_Auto
 *
 * OVERRIDE THE DEFAULTS
 */
public class AccountRequestDO {

    public static final String MANUFACTURER_RT_TEST = "ManufacturerAccountRT01Test";
    public static final String AUTHORISED_REP_RT_TEST = "AuthorisedRepAccountRT01Test";
    public static final String DISTRIBUTOR_RT_TEST = "DistributorRT01Test";
    public static final String NOTIFIED_BODY_RT_TEST = "NotifiedBodyRT01Test";
    public String organisationName;

    //Organisation Details
    public String address1;
    public String address2;
    public String townCity;
    public String postCode;
    public String country;
    public String telephone;
    public String fax;
    public String website;
    public boolean addressType;

    //Organisation type
    public String organisationType;
    public String vatRegistrationNumber;
    public String companyRegistrationNumber;

    //Contact Person Details
    public String title;
    public String firstName;
    public String lastName;
    public String jobTitle;
    public String phoneNumber;
    public String email;

    //Organisation Role
    //public String autorisedRep;
    //public String manufacturer;
    public boolean isManufacturer;
    public String organisationRole;

    //Services of interest
    public boolean deviceRegistration;
    public boolean cfsCertificateOfFreeSale;
    public boolean clinicalInvestigation;
    public boolean aitsAdverseIncidentTrackingSystem;


    public AccountRequestDO(ScenarioSession scenarioSession) {
        createDefaultRandom();
        //Update as required
        if(scenarioSession!=null){
            String loggedInUser = (String) scenarioSession.getData(SessionKey.loggedInUser);
            if(loggedInUser.contains("authorisedRep")){
                isManufacturer = false;
            }
            setUserDetails(getLoggedInUserName(scenarioSession));
        }
    }

    private String getLoggedInUserName(ScenarioSession scenarioSession) {
        String selectedProfile = System.getProperty("spring.profiles.active");
        String unameKeyValue = (String) scenarioSession.getData(SessionKey.loggedInUser);
        String userName = FileUtils.getSpecificPropertyFromFile(FileUtils.userFileName, selectedProfile + ".username." + unameKeyValue);
        return userName;
    }

    private void createDefaultRandom() {

        organisationName = RandomDataUtils.getRandomTestName("OrganisationTest");//.replace("_", "");

        //Organisation Details
        address1 = RandomDataUtils.getRandomNumberBetween(1, 200) + " " + RandomDataUtils.generateTestNameStartingWith("Test", 5) + " GrowLand Avenue";
        if(address1.equals("")){
            address1 = "111 This is weired St";
        }
        address2 = "South West";
        townCity = "London";
        postCode = "UB" + RandomDataUtils.getRandomNumberBetween(1, 19) + " " + RandomDataUtils.getRandomNumberBetween(1, 10) + "UU";
        country = "United Kingdom";
        addressType = true;
        telephone = "07941" + (int) RandomDataUtils.getRandomDigits(7);
        fax = ""; //"044941" + (int) RandomDataUtils.getRandomDigits(7);
        website = "www." + organisationName.toLowerCase() + ".com";

        //Organisation type
        organisationType = "Other";
        vatRegistrationNumber = "0161" + (int) RandomDataUtils.getRandomDigits(7);
        companyRegistrationNumber = "0895" + (int) RandomDataUtils.getRandomDigits(7);

        //Contact Person Details
        title = getRandomTitle();
        firstName = RandomDataUtils.generateTestNameStartingWith("Noor", 2); //RandomDataUtils.getRandomTestName("Noor").replace("_", "");
        lastName = RandomDataUtils.generateTestNameStartingWith("Uddin", 2); //RandomDataUtils.getRandomTestName("Uddin").replace("_", "");

        //Get real first name and last name

        jobTitle = getRandomJobTitle();
        phoneNumber = "01351" + (int) RandomDataUtils.getRandomDigits(7);
        email = "mhra.uat@gmail.com";

        //Organisation Role
        isManufacturer = true;
        organisationRole = "Manufacturer";

        //Services of interest
        deviceRegistration = true;
        cfsCertificateOfFreeSale = true;
        clinicalInvestigation = false;
        aitsAdverseIncidentTrackingSystem = false;
    }

    private String getRandomTitle(){

        List<String> listOfTitles = new ArrayList<>();
        listOfTitles.add("Mr.");
        listOfTitles.add("Mrs.");
        listOfTitles.add("Miss");
        listOfTitles.add("Dr.");
        listOfTitles.add("Ms.");
        listOfTitles.add("Prof.");
        String index = RandomDataUtils.getSimpleRandomNumberBetween(0, listOfTitles.size() - 1);
        String title = listOfTitles.get(Integer.parseInt(index));

        return title;
    }

    private String getRandomJobTitle() {
        List<String> listOfTitles = new ArrayList<>();
        listOfTitles.add("Tester");
        listOfTitles.add("DeveloperInTest");
        listOfTitles.add("Head of Manufacturing");
        listOfTitles.add("The Boss");
        listOfTitles.add("Head of Testing");
        listOfTitles.add("Automated Tester");
        listOfTitles.add("Chief");
        listOfTitles.add("Superman");
        listOfTitles.add("Batman");
        listOfTitles.add("CTO Microsoft");
        listOfTitles.add("Darth Vadar");
        listOfTitles.add("Master Of Universe");
        listOfTitles.add("Bill And Ted");
        listOfTitles.add("SHIELD");
        listOfTitles.add("Demolition Man");
        listOfTitles.add("Ninja");
        listOfTitles.add("Marines");

        String index = RandomDataUtils.getSimpleRandomNumberBetween(0, listOfTitles.size() - 1);
        String title = listOfTitles.get(Integer.parseInt(index));

        return title;
    }

    public static void main(String[] args){
        AccountRequestDO ar = new AccountRequestDO(null);
    }

    public void updateName(ScenarioSession scenarioSession) {
        if(organisationRole!=null){
            if(organisationRole.toLowerCase().equals("distributor")){
                organisationName = organisationName.replace("OrganisationTest", DISTRIBUTOR_RT_TEST);
                website = website.replace("organisationtest", DISTRIBUTOR_RT_TEST);
            }else if(organisationRole.toLowerCase().equals("notifiedbody")){
                organisationName = organisationName.replace("OrganisationTest", NOTIFIED_BODY_RT_TEST);
                website = website.replace("organisationtest", NOTIFIED_BODY_RT_TEST);
            }else{
                if(isManufacturer){
                    organisationName = organisationName.replace("OrganisationTest", MANUFACTURER_RT_TEST);
                    website = website.replace("organisationtest", MANUFACTURER_RT_TEST);
                }else{
                    organisationName = organisationName.replace("OrganisationTest", AUTHORISED_REP_RT_TEST);
                    website = website.replace("organisationtest", AUTHORISED_REP_RT_TEST);
                }
            }
        }

        if(scenarioSession!=null){
            setUserDetails(getLoggedInUserName(scenarioSession));
        }
    }

    public void updateName(String searchTerm) {
        if(organisationRole!=null){
            if(searchTerm.toLowerCase().equals("distributor")){
                organisationName = organisationName.replace("OrganisationTest", DISTRIBUTOR_RT_TEST);
                website = website.replace("organisationtest", DISTRIBUTOR_RT_TEST);
            }else if(searchTerm.toLowerCase().equals("notifiedbody")){
                organisationName = organisationName.replace("OrganisationTest", NOTIFIED_BODY_RT_TEST);
                website = website.replace("organisationtest", NOTIFIED_BODY_RT_TEST);
            }else{
                if(searchTerm.contains("anufacturer")){
                    organisationName = organisationName.replace("OrganisationTest", searchTerm);
                    website = website.replace("organisationtest", searchTerm);
                }else{
                    organisationName = organisationName.replace("OrganisationTest", searchTerm);
                    website = website.replace("organisationtest", searchTerm);
                }
            }
        }
    }

    public void setUserDetails(String loggedInAs) {
        String[] data = loggedInAs.split("\\.");
        //System.out.println(data);
        firstName = data[0];

        //Because we have Auto.Business and Noor.Uddin.Business
        String name = generateLastName();
        if(data.length == 2){
            lastName = name;
        }else {
            if(data.length == 1){
                lastName = name;
                //ASSUMING excel sheet username is something like Manufacturer_NU or AuthorisedRep_AT etc
                String initial = loggedInAs.split("_")[1];
                firstName = TestHarnessUtils.getHardcodedFirstName(initial);
            }else {
                String business = data[2];
                lastName = data[1] + "." + name;
            }
        }
    }

    private String generateLastName() {
        String business = "";

        if(organisationRole!=null){
            if(organisationRole.toLowerCase().equals("distributor")){
                business = "Manufacturer";
            }else if(organisationRole.toLowerCase().equals("notifiedbody")){
                business = "NotifiedBody";
            }else{
                //It can only be a manufacturer or authorisedRep
                if(isManufacturer){
                    business = "Manufacturer";
                }else{
                    business = "AuthorisedRep";
                }
            }
        }

        return business;
    }

//    public String getUserName(boolean aRandomOne) {
//        String lastName = generateLastName();
//        if(aRandomOne){
//            lastName = lastName + RandomDataUtils.getTodaysDate(false, "");
//        }
//        return lastName;
//    }


    public String getUserName(boolean aRandomOne) {
        String lastName = generateLastName();
        if(aRandomOne){
            lastName = lastName + RandomDataUtils.getTodaysDate(false, "") + "_" + RandomDataUtils.getRandomNumberBetween(100, 100000);
        }
        return lastName ;
    }

    @Override
    public String toString() {
        return "AccountRequest{" +
                "\norganisationName='" + organisationName + '\'' +
                "\naddress1='" + address1 + '\'' +
                "\naddress2='" + address2 + '\'' +
                "\ntownCity='" + townCity + '\'' +
                "\npostCode='" + postCode + '\'' +
                "\ncountry='" + country + '\'' +
                "\ntelephone='" + telephone + '\'' +
                "\nfax='" + fax + '\'' +
                "\nwebsite='" + website + '\'' +
                "\naddressType=" + addressType +
                "\norganisationType='" + organisationType + '\'' +
                "\nvatRegistrationNumber='" + vatRegistrationNumber + '\'' +
                "\ncompanyRegistrationNumber='" + companyRegistrationNumber + '\'' +
                "\ntitle='" + title + '\'' +
                "\nfirstName='" + firstName + '\'' +
                "\nlastName='" + lastName + '\'' +
                "\njobTitle='" + jobTitle + '\'' +
                "\nphoneNumber='" + phoneNumber + '\'' +
                "\nemail='" + email + '\'' +
                "\nisManufacturer=" + isManufacturer +
                "\ndeviceRegistration=" + deviceRegistration +
                "\ncfsCertificateOfFreeSale=" + cfsCertificateOfFreeSale +
                "\nclinicalInvestigation=" + clinicalInvestigation +
                "\naitsAdverseIncidentTrackingSystem=" + aitsAdverseIncidentTrackingSystem +
                '}';
    }

}
