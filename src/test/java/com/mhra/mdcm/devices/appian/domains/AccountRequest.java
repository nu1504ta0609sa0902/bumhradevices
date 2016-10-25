package com.mhra.mdcm.devices.appian.domains;

import com.mhra.mdcm.devices.appian.utils.selenium.others.RandomDataUtils;

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
    public String autorisedRep;
    public String manufacturer;
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

        organisationName = RandomDataUtils.getRandomTestName("OrganisationTest");

        //Organisation Details
        address1 = RandomDataUtils.getRandomNumberBetween(1, 200) + " " + RandomDataUtils.getRandomTestName("Test").replace("_", "") + " GrowLand Avenue";
        address2 = "";
        townCity = "London";
        postCode = "UB" + RandomDataUtils.getRandomNumberBetween(1, 19) + " " + RandomDataUtils.getRandomNumberBetween(1, 10) + "UU";
        country = "United Kingdom";
        addressType = true;
        telephone = "07941" + (int) RandomDataUtils.getRandomDigits(7);
        fax = "";
        website = "www." + organisationName.toLowerCase() + ".com";

        //Organisation type
        organisationType = "Limited Company";
        vatRegistrationNumber = "0161" + (int) RandomDataUtils.getRandomDigits(7);
        companyRegistrationNumber = "0895" + (int) RandomDataUtils.getRandomDigits(7);

        //Contact Person Details
        title = "Prof.";
        firstName = RandomDataUtils.getRandomTestName("TestFirstName");
        lastName = RandomDataUtils.getRandomTestName("TestLastName");
        jobTitle = "Tester";
        phoneNumber = "01351" + (int) RandomDataUtils.getRandomDigits(7);;
        email = "mhra.uat@gmail.com";

        //Organisation Role
        autorisedRep = "false";
        manufacturer = "true";
        isManufacturer = true;

        //Services of interest
        deviceRegistration = true;
        cfsCertificateOfFreeSale = true;
        clinicalInvestigation = true;
        aitsAdverseIncidentTrackingSystem = true;
    }

}
