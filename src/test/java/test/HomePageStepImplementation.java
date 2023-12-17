package test;

import com.thoughtworks.gauge.Step;
import elements.HomePageElements;
import models.HomePageModel;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import java.lang.reflect.Field;

public class HomePageStepImplementation extends HomePageModel {

    @Step("Verify that the Beymen Homepage opens")
    public void VerifyHomePage() {
        verifyTitle(HomePageElements.TITLE);
    }


    @Step("<row>st row <column>st column of excel is taken")
    public void ReadExcelFile(Integer row, Integer column) {
        getExcelRowAndColumn(row, column);
        logger.info("Read excel successfully");
    }

    @Step("Verify that Home Page Opened")
    public void VerifyHomePageOpened() {
        isDisplayed(HomePageElements.BEYMEN_LOGO);
    }

    @Step("In the search field, the data read from excel is written.")
    public void EnterSearchField() {
        writeText(HomePageElements.SEARCH_INPUT_AREA, searchKey);
    }

    public By getElements(String key) {
        try {
            String homePageElementsClassName = "elements.HomePageElements";
            Class<?> elementsClass = Class.forName(homePageElementsClassName); // convert string classname to class
            Object obj = elementsClass.newInstance(); // invoke empty constructor

            Field temp = obj.getClass().getField(key);
            By by = (By) temp.get(obj);
            return by;
        } catch (Exception e) {
            logger.info("Element not found");
            return null;
        }
    }

    @Step("Click on the <element> element.")
    public void clickElements(String element) {
        By by = getElements(element);
        waitUntilVisibilityOfElementLocatedBy(by);
        waitUntilClickableOfElementLocatedBy(by);
        click(by);
    }

    @Step("The value of element <element> is selected as <value>.")
    public void clickElements(String element, String value) {
        selectByValue(getElements(element), value);
    }

    @Step("Verify that <element> element <value> value")
    public void verify(String element, String value) {
        assertEquals(getText(getElements(element)), value);
        logger.info("value is checked successfully");
    }

    @Step("wait <millisecond> millisecond")
    public void wait(int millisecond) throws InterruptedException {
        Thread.sleep(millisecond);
    }

    @Step("Clear search text area.")
    public void ClearTextArea() {
        WebElement toClear = driver.findElement(HomePageElements.SEARCH_SUGGESTION_INPUT_AREA);
        toClear.clear();
        toClear.sendKeys(Keys.CONTROL + "a");
        toClear.sendKeys(Keys.DELETE);
        logger.info("Search text area has been cleared.");
    }


    @Step("Write the data read from excel in <str> element")
    public void Search(String str) {

        writeText(HomePageElements.SEARCH_INPUT_AREA, searchKey);
        logger.info("Search and Enter successfully.");
    }

    @Step("Get the url information in the <element> element and random page open.")
    public void RandomSelect(String element) {
        randomSelect(getElements(element));
    }

    @Step("Get product price from <price> element and product detail from <detail> element.")
    public void getProductInfo(String price, String detail) {
        getProductDetailAndSave(getElements(detail), getElements(price));
    }

    @Step("Verify that the price information in the basket and the price information in the product detail are the same.")
    public void verifyBasketPriceWithDetailPage() {
        assertEqualsPrice(getText(getElements("SALE_PRICE")), productPrice);
    }


    @Step("Write the data read from excel in <str> element and press enter")
    public void SearchAndEnter(String str) {

        WebElement element = driver.findElement(getElements(str));
        element.sendKeys(searchKey);
        element.sendKeys(Keys.ENTER);
        logger.info("Search and Enter successfully.");
    }

}
