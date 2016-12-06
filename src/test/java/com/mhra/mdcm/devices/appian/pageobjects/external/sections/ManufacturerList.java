package com.mhra.mdcm.devices.appian.pageobjects.external.sections;

import com.mhra.mdcm.devices.appian.pageobjects._Page;
import com.mhra.mdcm.devices.appian.pageobjects.external.ExternalHomePage;
import com.mhra.mdcm.devices.appian.utils.selenium.others.RandomDataUtils;
import com.mhra.mdcm.devices.appian.utils.selenium.page.WaitUtils;
import org.openqa.selenium.By;
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
public class ManufacturerList extends _Page {

    @FindBy(css = "button.GFWJSJ4DCF")
    WebElement linkRegisterNewManufacturer;

    @FindBy(css = "td>div>a")
    List<WebElement> listOfManufacturerNames;

    @FindBy(css = ".GFWJSJ4DFDC div")
    WebElement itemCount;
    @FindBy(css = ".gwt-Image[aria-label='Next page']")
    WebElement nextPage;
    @FindBy(css = ".gwt-Image[aria-label='Previous page']")
    WebElement prevPage;
    @FindBy(css = ".gwt-Image[aria-label='Last page']")
    WebElement lastPage;

    @Autowired
    public ManufacturerList(WebDriver driver) {
        super(driver);
    }


    public ManufacturerDetails viewAManufacturer(String manufacturerName) {
        if(manufacturerName == null){
            //Than view a random one
            int index = RandomDataUtils.getNumberBetween(0, listOfManufacturerNames.size() - 1);
            WebElement link = listOfManufacturerNames.get(index);
            link.click();
        }else{
            WaitUtils.waitForElementToBeClickable(driver, By.partialLinkText(manufacturerName), TIMEOUT_5_SECOND, false);
            WebElement man = driver.findElement(By.partialLinkText(manufacturerName));
            man.click();
        }
        return new ManufacturerDetails(driver);
    }

    public String getARandomManufacturerName() {
        WaitUtils.waitForElementToBeClickable(driver, By.cssSelector(".left>div>a"), TIMEOUT_5_SECOND, false);
        WaitUtils.waitForElementToBeVisible(driver, By.cssSelector(".left>div>a"), TIMEOUT_5_SECOND, false);
        int index = RandomDataUtils.getNumberBetween(0, listOfManufacturerNames.size() - 1);
        WebElement link = listOfManufacturerNames.get(index);
        String name = link.getText();
        return name;
    }

    public boolean isManufacturerDisplayedInList(String manufacturerName){
        WaitUtils.nativeWaitInSeconds(2);
        WaitUtils.waitForElementToBeClickable(driver, By.cssSelector("td>div>a"), TIMEOUT_5_SECOND, false);
        boolean found = false;
        for(WebElement item: listOfManufacturerNames){
            String name = item.getText();
            if (name.contains(manufacturerName)) {
                found = true;
                break;
            }
        }
        return found;
    }


    public ManufacturerList clickNext(){
        WaitUtils.waitForElementToBeClickable(driver, nextPage, TIMEOUT_5_SECOND, false);
        nextPage.click();
        return new ManufacturerList(driver);
    }

    public ManufacturerList clickPrev(){
        WaitUtils.waitForElementToBeClickable(driver, prevPage, TIMEOUT_5_SECOND, false);
        prevPage.click();
        return new ManufacturerList(driver);
    }

    public ManufacturerList clickLastPage(){
        WaitUtils.waitForElementToBeClickable(driver, lastPage, TIMEOUT_5_SECOND, false);
        lastPage.click();
        return new ManufacturerList(driver);
    }


    public CreateManufacturerTestsData registerNewManufacturer() {
        WaitUtils.waitForElementToBeClickable(driver, linkRegisterNewManufacturer, TIMEOUT_DEFAULT, false);
        linkRegisterNewManufacturer.click();
        return new CreateManufacturerTestsData(driver);
    }

    public int getNumberOfPages() {
        try {
            WaitUtils.waitForElementToBeClickable(driver, itemCount, TIMEOUT_5_SECOND, false);
            String text = itemCount.getText();
            String total = text.substring(text.indexOf("of") + 3);
            String itemPerPage = text.substring(text.indexOf("-") + 1, text.indexOf(" of "));

            int tt = Integer.parseInt(total.trim());
            int noi = Integer.parseInt(itemPerPage.trim());

            int reminder = tt % noi;
            int numberOfPage = (tt/noi) - 1;
            if(reminder > 0){
                numberOfPage++;
            }

            return numberOfPage;
        }catch (Exception e){
            return 0;
        }
    }
}
