package com.mhra.mdcm.devices.appian.pageobjects.business;

import com.mhra.mdcm.devices.appian.pageobjects._Page;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by TPD_Auto
 */
@Component
public class TasksPage extends _Page {

    @Autowired
    public TasksPage(WebDriver driver) {
        super(driver);
    }

}
