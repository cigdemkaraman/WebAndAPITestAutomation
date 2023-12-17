package models;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Random;

public class HomePageModel extends BaseModel {
    public String productDescription;
    public String searchKey;
    public String productPrice;



    public void verifyTitle(String text) {
        try {
            assertEquals(driver.getTitle(), text);
            logger.info("title checked successfully");
        } catch (Exception e) {
            logger.error("title not checked successfully");
            assertFail();
        }
    }

    public void click(By by) {
        try {
            clickElement(by);
            logger.info("Clicked element: " + by);
        } catch (Exception e) {
            logger.error("Can not clicked element: " + by);
            assertFail();
        }
    }

    public void isDisplayed(By by) {
        isElementDisplayed(by);
        logger.info("Element is displayed successfully");
    }

    public void writeText(By by, String text) {
        waitUntilVisibilityOfElementLocatedBy(by);
        WebElement element = findElement(by);
        Actions action = new Actions(driver);
        action.moveToElement(findElement(by));
        element.sendKeys(text);
        action.build().perform();
    }


    public void selectByValue(By by, String value) {
        waitUntilClickableOfElementLocatedBy(by);
        WebElement selectElement = findElement(by);
        Select select = new Select(selectElement);
        select.selectByValue(value);
    }

    public String getText(By by) {
        return findElement(by).getText();
    }

    public void isElementDisplayed(By by) {
        findElement(by).isDisplayed();
    }

    public void assertEquals(String actualText, String expectedText) {
        logger.info("actualText: " + actualText + " expectedText: " + expectedText);
        Assert.assertTrue(actualText.contains(expectedText));
    }


    public void assertFail() {
        Assert.fail();
    }

    public void getExcelRowAndColumn(int r, int c) {
        Workbook workbook = null;
        try {
            workbook = new XSSFWorkbook("input.xlsx");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Sheet sheet = workbook.getSheetAt(0);
        Row row = sheet.getRow(r);
        searchKey = String.valueOf(row.getCell(c));

        logger.info(searchKey + " read information from excel");
    }

    public void randomSelect(By by) {
        waitUntilVisibilityOfElementLocatedBy(by);
        waitUntilClickableOfElementLocatedBy(by);
        List<WebElement> pageResult = driver.findElements(by);
        Random random = new Random();
        pageResult.get(random.nextInt(pageResult.size())).click();
        logger.info("A random element is selected.");
    }

    public void getProductDetailAndSave(By desc, By price) {
        productDescription = driver.findElement(desc).getText();
        productPrice = driver.findElement(price).getText();
        logger.info("product Description: '" + productDescription + "' - productPrice: " + productPrice + "'");

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("output.txt", true));
            writer.append("product Description: '")
                    .append(productDescription)
                    .append("' - productPrice: ")
                    .append(productPrice)
                    .append("'\n");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String priceFormat(String t1) {
        t1 = t1.replace("TL", "");
        t1 = t1.replace(" ", "");
        t1 = t1.replace(",00", "");
        return t1;
    }

    public void assertEqualsPrice(String actualText, String expectedText) {
        logger.info("actualText: " + actualText + " expectedText: " + expectedText);
        Assert.assertTrue(priceFormat(actualText).contains(priceFormat(expectedText)));
    }
}
