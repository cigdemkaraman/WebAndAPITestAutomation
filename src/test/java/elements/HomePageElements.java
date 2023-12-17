package elements;

import org.openqa.selenium.By;

public class HomePageElements {
    public static final String TITLE = "Beymen.com – Türkiye’nin Tek Dijital Lüks Platformu";
    public static final String URL = "https://www.beymen.com/";
    public static final By REEJECT_COKKIES = By.id("onetrust-reject-all-handler");
    public static final By CHOOSE_GENDER_MAN = By.id("genderManButton");
    public static final By CHOOSE_GENDER_WOMAN = By.id("genderWomanButton");
    public static final By CLOSE_GENDER_POPUP = By.xpath("//div[contains(@class,'gender')]//*[contains(@class,'icon-close')]");
    public static final By SEARCH_INPUT_AREA = By.xpath("//input[@class='o-header__search--input']");
    public static final By GENDER_POPUP = By.xpath("//div[@class='genderPopup__bottom']");
    public static final By SEARCH_SUGGESTION_INPUT_AREA = By.name("qSugesstion");
    public static final By BEYMEN_LOGO = By.xpath("//a[@href='/']/img");
    public static final By PRODUCT_LIST = By.xpath("//div[@id='productList']//div[@class='m-productCard__photo']/a");
    public static final By PRODUCT_DETAIL = By.xpath("//span[contains(@class,'productDetail')]");
    public static final By PRODUCT_PRICE = By.id("priceNew");
    public static final By CHOOSE_FIRST_VARIATION = By.xpath("//span[@class='m-variation__item'] | //span[@class='m-variation__item -criticalStock']");
    public static final By ADD_BASKET = By.id("addBasket");
    public static final By MY_CART = By.xpath("//a[@title='Sepetim']");
    public static final By GO_TO_MY_CART = By.xpath("//button[contains(text(),'Sepete Git')]");
    public static final By SALE_PRICE = By.xpath("//span[contains(@class,'salePrice')]");
    public static final By QUANTITY_SELECT = By.id("quantitySelect0-key-0");
    public static final By REMOVE_CART_ITEM = By.id("removeCartItemBtn0-key-0");

}
