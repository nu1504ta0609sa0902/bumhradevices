package com.mhra.mdcm.devices.appian.domains;

import com.mhra.mdcm.devices.appian.utils.selenium.others.RandomDataUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TPD_Auto on 25/10/2016.
 *
 * OVERRIDE THE DEFAULTS
 */
public class AccountRequest {

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

    //Services of interest
    public boolean deviceRegistration;
    public boolean cfsCertificateOfFreeSale;
    public boolean clinicalInvestigation;
    public boolean aitsAdverseIncidentTrackingSystem;


    public AccountRequest() {
        createDefaultRandom();
    }

    private void createDefaultRandom() {

        organisationName = RandomDataUtils.getRandomTestName("OrganisationTest").replace("_", "");

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
        fax = "044941" + (int) RandomDataUtils.getRandomDigits(7);
        website = "www." + organisationName.toLowerCase() + ".com";

        //Organisation type
        organisationType = "Limited Company";
        vatRegistrationNumber = "0161" + (int) RandomDataUtils.getRandomDigits(7);
        companyRegistrationNumber = "0895" + (int) RandomDataUtils.getRandomDigits(7);

        //Contact Person Details
        title = "Prof.";
        firstName = RandomDataUtils.generateTestNameStartingWith("Noor", 2); //RandomDataUtils.getRandomTestName("Noor").replace("_", "");
        lastName = RandomDataUtils.generateTestNameStartingWith("Uddin", 2); //RandomDataUtils.getRandomTestName("Uddin").replace("_", "");
        jobTitle = getRandomJobTitle();
        phoneNumber = "01351" + (int) RandomDataUtils.getRandomDigits(7);
        email = "mhra.uat@gmail.com";

        //Organisation Role
        //autorisedRep = "false";
        //manufacturer = "true";
        isManufacturer = true;

        //Services of interest
        deviceRegistration = true;
        cfsCertificateOfFreeSale = false;
        clinicalInvestigation = false;
        aitsAdverseIncidentTrackingSystem = false;
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
        AccountRequest ar = new AccountRequest();
    }

    public void updateName() {
        if(isManufacturer){
            organisationName = organisationName.replace("OrganisationTest", "ManufacturerTest");
            website = website.replace("organisationtest", "ManufacturerTest");
        }else{
            organisationName = organisationName.replace("OrganisationTest", "AuthorisedRepTest");
            website = website.replace("organisationtest", "AuthorisedRepTest");
        }
    }


    @Override
    public String toString() {
        return "AccountRequest{" +
                "\norganisationName='" + organisationName + '\'' +
//                "\naddress1='" + address1 + '\'' +
//                "\naddress2='" + address2 + '\'' +
//                "\ntownCity='" + townCity + '\'' +
//                "\npostCode='" + postCode + '\'' +
//                "\ncountry='" + country + '\'' +
//                "\ntelephone='" + telephone + '\'' +
//                "\nfax='" + fax + '\'' +
                "\nwebsite='" + website + '\'' +
                "\naddressType=" + addressType +
                "\norganisationType='" + organisationType + '\'' +
//                "\nvatRegistrationNumber='" + vatRegistrationNumber + '\'' +
//                "\ncompanyRegistrationNumber='" + companyRegistrationNumber + '\'' +
//                "\ntitle='" + title + '\'' +
//                "\nfirstName='" + firstName + '\'' +
//                "\nlastName='" + lastName + '\'' +
//                "\njobTitle='" + jobTitle + '\'' +
//                "\nphoneNumber='" + phoneNumber + '\'' +
                "\nemail='" + email + '\'' +
                "\nisManufacturer=" + isManufacturer +
//                "\ndeviceRegistration=" + deviceRegistration +
//                "\ncfsCertificateOfFreeSale=" + cfsCertificateOfFreeSale +
//                "\nclinicalInvestigation=" + clinicalInvestigation +
//                "\naitsAdverseIncidentTrackingSystem=" + aitsAdverseIncidentTrackingSystem +
                '}';
    }
}
