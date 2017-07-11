package com.mhra.mdcm.devices.appian.enums;

/**
 * Created by TPD_Auto on 27/04/2017.
 */
public enum LinksRecordPage {

    LINK_ACCOUNTS("Accounts"),
    LINK_GMDN_DEVICES("GMDN Devices"),
    LINK_ORGANISATIONS("Organisations"),
    LINK_REGISTERED_DEVICES("Registered Devices"),
    LINK_REGISTERED_PRODUCTS("Registered Products"),
    LINK_CFS_ORGANISATIONS("CFS Organisations"),
    LINK_APPLICATIONS("Applications");

    public String link;

    LinksRecordPage(String link) {
        this.link = link;
    }
}
