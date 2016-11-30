package com.mhra.mdcm.devices.appian.domains.newaccounts.others;

/**
 * Created by TPD_Auto on 22/11/2016.
 */
public class MyAccount {

    //Contact person details
    public String title;
    public String firstName;
    public String lastName;
    public String jobTitle;
    public String email;
    public String telephone;

    //Organisation details
    public String orgName;
    public String address1;
    public String address2;
    public String cityTown;
    public String postCode;
    public String country;
    public String orgTelephone;
    public String website;

    @Override
    public String toString() {
        return "MyAccount{" +
                "title='" + title + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", jobTitle='" + jobTitle + '\'' +
                ", email='" + email + '\'' +
                ", telephone='" + telephone + '\'' +
                '}';
    }
}
