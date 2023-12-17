package models;

import driver.Driver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class BaseModel extends Driver {

    public static void waitUntilPresenceOfElementLocatedBy(By by) {
        webDriverWait.until(ExpectedConditions.presenceOfElementLocated(by));
    }

    public static void waitUntilVisibilityOfElementLocatedBy(By by) {
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(by));
    }

    public static void waitUntilClickableOfElementLocatedBy(By by) {
        webDriverWait.until(ExpectedConditions.elementToBeClickable(by));
    }

    public WebElement findElement(By by) {
        waitUntilVisibilityOfElementLocatedBy(by);
        waitUntilPresenceOfElementLocatedBy(by);
        return driver.findElement(by);
    }

    public void clickElement(By by) {
        waitUntilClickableOfElementLocatedBy(by);
        Actions action = new Actions(driver);
        action.moveToElement(findElement(by));
        action.click();
        action.build().perform();
    }


}
