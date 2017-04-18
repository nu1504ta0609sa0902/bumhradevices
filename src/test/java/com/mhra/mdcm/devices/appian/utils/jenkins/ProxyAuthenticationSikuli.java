package com.mhra.mdcm.devices.appian.utils.jenkins;

import com.mhra.mdcm.devices.appian.utils.selenium.others.FileUtils;
import org.openqa.selenium.WebDriver;
import org.sikuli.script.Pattern;
import org.sikuli.script.Screen;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.File;

/**
 * Created by a-Uddinn on 4/7/2017.
 */
public class ProxyAuthenticationSikuli {

    final WebDriver driver;
    final String url;

    public ProxyAuthenticationSikuli(WebDriver driver, String baseUrl) {
        this.driver = driver;
        this.url = baseUrl;
    }

    public void login(boolean useRobotApiToTab) throws Exception {

        //Now try to enter some value
        System.out.println("Trying to enter proxy login details via ROBOT API");
        //wait - increase this wait period if required
        Thread.sleep(5000);

        //create robot for keyboard operations
        Robot rb = new Robot();

        //Tab to a different window
        enterProxyAuthentication(rb, useRobotApiToTab);

        //Find fields and enter values
        Screen screen = new Screen();
        Pattern usernameField = new Pattern(FileUtils.getFileFullPath("tmp" + File.separator + "sikuli", "usernameField.png"));
        Pattern passwordField = new Pattern(FileUtils.getFileFullPath("tmp" + File.separator + "sikuli", "passwordField.png"));
        Pattern submitBtn = new Pattern(FileUtils.getFileFullPath("tmp" + File.separator + "sikuli", "submitBtn.png"));
        Pattern dialogBox = new Pattern(FileUtils.getFileFullPath("tmp" + File.separator + "sikuli", "dialogBox.png"));
        Pattern neverBtn = new Pattern(FileUtils.getFileFullPath("tmp" + File.separator + "sikuli", "neverBtn.png"));

        String userName = FileUtils.getSpecificPropertyFromFile("envs" + File.separator + "mhradevicestest.properties", "proxy.username");
        String password = FileUtils.getSpecificPropertyFromFile("envs" + File.separator + "mhradevicestest.properties", "proxy.password");
        try {
            screen.click(dialogBox);
        }catch (Exception e){}
        screen.click(usernameField);
        screen.type(usernameField, userName);
        screen.type(passwordField, password);
        screen.click(submitBtn);

        //wait
//        Thread.sleep(1500);
//        screen = new Screen();
//        screen.click(neverBtn);
        Thread.sleep(1000);

        //maximise
        driver.manage().window().maximize();
    }

    private void enterProxyAuthentication(Robot rb, boolean useRobotApiToTab) {

        String inJenkinsWindowLoosesFocusOnLauch = System.getProperty("using.ci");
        System.out.println("Using CI : " + inJenkinsWindowLoosesFocusOnLauch);
        if(inJenkinsWindowLoosesFocusOnLauch!=null) {
            System.out.println("In Jenkins : Using CI ");
            if(useRobotApiToTab) {
                rb.keyPress(KeyEvent.VK_ALT);
                rb.keyPress(KeyEvent.VK_TAB);
                rb.keyRelease(KeyEvent.VK_ALT);
                rb.keyRelease(KeyEvent.VK_TAB);
            }else {
                rb.keyPress(KeyEvent.VK_WINDOWS);
                rb.keyPress(KeyEvent.VK_DOWN);
                rb.keyRelease(KeyEvent.VK_WINDOWS);
                rb.keyRelease(KeyEvent.VK_DOWN);
            }
        }else{
            System.out.println("Running with CMD or IDE");
        }
    }
}