package com.mhra.mdcm.devices.appian.enums;

/**
 * Created by TPD_Auto on 27/04/2017.
 */
public enum PageHeaders {

    PAGE_HEADERS_ACCOUNTS("Accounts"),
    PAGE_HEADERS_GMDN_DEVICES("GMDN Devices"),
    PAGE_HEADERS_ORGANISATIONS("Organisations"),
    PAGE_HEADERS_REGISTERED_DEVICES("Registered Devices"),
    PAGE_HEADERS_REGISTERED_PRODUCTS("Registered Products"),
    PAGE_HEADERS_APPLICATIONS("Applications");

    public String header;

    PageHeaders(String header) {
        this.header = header;
    }
}
