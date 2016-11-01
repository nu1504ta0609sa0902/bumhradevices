package com.mhra.mdcm.devices.appian.utils.datadriven;

import com.mhra.mdcm.devices.appian.pageobjects.LoginPage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TPD_Auto on 01/11/2016.
 *
 * Note: I was forced to write some data driven testing in this way
 */
public class JUnitUtils {


    public static String getExpectedHeading(String username){
        String expectedHeading = "Tasks";
        if(username.contains("manufacturer")){
            expectedHeading = "MHRA Service";
        }else if(username.contains("authorised")){
            expectedHeading = "MHRA Service";
        }
        return expectedHeading;
    }

    public static LoginPage logoutIfLoggedIn(String currentLoggedInUser, LoginPage loginPage){
        if(currentLoggedInUser!=null){
            if(currentLoggedInUser.toLowerCase().contains("business")){
                loginPage = loginPage.logoutIfLoggedIn();
            }else if(currentLoggedInUser.toLowerCase().contains("manufacturer")){
                loginPage = loginPage.logoutIfLoggedInOthers();
            }else if(currentLoggedInUser.toLowerCase().contains("authorised")){
                loginPage = loginPage.logoutIfLoggedInOthers();
            }
        }

        return loginPage;
    }

    public static List<String> getListOfTabSections() {
        List<String> listOfItems = new ArrayList<>();
        listOfItems.add("News");
        listOfItems.add("Tasks");
        listOfItems.add("Records");
        listOfItems.add("Reports");
        listOfItems.add("Actions");
        return listOfItems;
    }

    public static List<String> getListOfRecordsPageLinks() {
        List<String> listOfItems = new ArrayList<>();
        listOfItems.add("Accounts");
        listOfItems.add("Products");
        listOfItems.add("Devices");
        listOfItems.add("All Organisations");
        listOfItems.add("Actions");
        return listOfItems;
    }
}
