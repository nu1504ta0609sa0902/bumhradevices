package com.mhra.mdcm.devices.appian.utils.jenkins;

import org.openqa.selenium.WebDriver;

import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;

/**
 * Created by a-Uddinn on 4/7/2017.
 */
public class ProxyAuthentication implements Runnable {

    final WebDriver driver;
    final String url;

    public ProxyAuthentication(WebDriver driver, String baseUrl) {
        this.driver = driver;
        this.url = baseUrl;
    }

    @Override
    public void run() {
        try {
            login();
        } catch (Exception ex) {
            System.out.println("Error in Login Thread: " + ex.getMessage());
        }
    }


    public void login2() throws Exception {
        System.out.println("Trying to enter proxy login details via URL");
        //wait - increase this wait period if required
        Thread.sleep(5000);
        driver.get("http://mca\\uddinn:Welcome2023@10.2.22.60:8000");
        driver.get(url);
    }

    public void login() throws Exception {

        //Now try to enter some value
        System.out.println("Trying to enter proxy login details via ROBOT API");
        //wait - increase this wait period if required
        Thread.sleep(5000);

        //create robot for keyboard operations
        Robot rb = new Robot();

        //Tab to a different window
        String inJenkinsWindowLoosesFocusOnLauch = System.getProperty("using.ci");
        System.out.println("Using CI : " + inJenkinsWindowLoosesFocusOnLauch);
        if(inJenkinsWindowLoosesFocusOnLauch!=null) {
            System.out.println("In Jenkins : Using CI ");
            rb.keyPress(KeyEvent.VK_WINDOWS);
            rb.keyPress(KeyEvent.VK_DOWN);
            rb.keyRelease(KeyEvent.VK_WINDOWS);
            rb.keyRelease(KeyEvent.VK_DOWN);
        }else{
            System.out.println("Running from CMD or IDE");
        }

        //Enter user name by ctrl-v
        StringSelection username = new StringSelection("mca\\uddinn");
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(username, null);
        rb.keyPress(KeyEvent.VK_CONTROL);
        rb.keyPress(KeyEvent.VK_V);
        rb.keyRelease(KeyEvent.VK_V);
        rb.keyRelease(KeyEvent.VK_CONTROL);

        //tab to password entry field
        rb.keyPress(KeyEvent.VK_TAB);
        rb.keyRelease(KeyEvent.VK_TAB);
        Thread.sleep(1000);

        //Enter password by ctrl-v
        StringSelection pwd = new StringSelection("Welcome2023");
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(pwd, null);
        rb.keyPress(KeyEvent.VK_CONTROL);
        rb.keyPress(KeyEvent.VK_V);
        rb.keyRelease(KeyEvent.VK_V);
        rb.keyRelease(KeyEvent.VK_CONTROL);

        //press enter
        rb.keyPress(KeyEvent.VK_ENTER);
        rb.keyRelease(KeyEvent.VK_ENTER);

        //wait
        Thread.sleep(1000);

        //maximise
        driver.manage().window().maximize();
    }
}