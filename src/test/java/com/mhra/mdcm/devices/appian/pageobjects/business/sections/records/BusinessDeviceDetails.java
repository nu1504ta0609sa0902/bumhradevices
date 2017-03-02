package com.mhra.mdcm.devices.appian.pageobjects.business.sections.records;

import com.mhra.mdcm.devices.appian.pageobjects._Page;
import com.mhra.mdcm.devices.appian.utils.selenium.page.WaitUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by TPD_Auto
 */
@Component
public class BusinessDeviceDetails extends _Page {

    @FindBy(xpath = ".//td/a")
    List<WebElement> listOfTableHeadings;

    @FindBy(xpath = ".//div/h6")
    List<WebElement> listOfDeviceHeadings;

    @Autowired
    public BusinessDeviceDetails(WebDriver driver) {
        super(driver);
    }

    public boolean isDeviceTableDisplayed() {
        WaitUtils.isPageLoadingComplete(driver, TIMEOUT_PAGE_LOAD);
        return listOfDeviceHeadings.size() > 0;
    }

    public boolean isTableColumnsCorrect(String expectedHeadings) {
        boolean allFound = true;
        for(WebElement el: listOfTableHeadings){
            String text = el.getText();
            log.info(text);
            if(!expectedHeadings.contains(text)){
                allFound = false;
                break;
            }
        }
        return allFound;
    }
}
