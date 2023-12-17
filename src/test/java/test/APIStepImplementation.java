package test;

import com.thoughtworks.gauge.Step;
import io.restassured.config.DecoderConfig;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.Random;

import static io.restassured.RestAssured.config;
import static io.restassured.RestAssured.given;

public class APIStepImplementation {

    private static final String API_KEY = "your_api_key"; //your_api_key
    private static final String API_TOKEN = "your_api_token"; //your_api_token
    public final Logger logger = LogManager.getLogger(this.getClass());
    public static String boardId;
    public static String boardListId;
    public static String lastCardId;


    @Step("Create a new board with <name> name.")
    public void createBoard(String name) {
        BasicConfigurator.configure();
        Response response = given()
                .config(config().decoderConfig(new DecoderConfig().noContentDecoders()))
                .pathParams("name", name)
                .pathParams("key", API_KEY)
                .pathParams("token", API_TOKEN)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .post("https://api.trello.com/1/boards/?name={name}&key={key}&token={token}")
                .then()
                .statusCode(200)
                .extract().response();

        if (response != null) {
            boardId = response.jsonPath().get("id");
            logger.info("Created board id: " + boardId);
        } else {
            logger.error("Response is null");
        }
    }

    @Step("Get board id information")
    public String getBoardId() {
        Response response = given().config(config().decoderConfig(new DecoderConfig().noContentDecoders()))
                .when()
                .get("https://api.trello.com/1/members/me/boards?key={key}&token={token}", API_KEY, API_TOKEN)
                .then().extract().response();
        if (response != null) {
            List<String> id = response.jsonPath().getList("id");
            boardId = id.get(0);
            logger.info("Fetched board id: " + boardId);
            return boardId;
        } else {
            logger.error("Response is null");
            return null;
        }
    }

    @Step("Delete board")
    public void deleteBoard() {
        Response response = given().config(config().decoderConfig(new DecoderConfig().noContentDecoders()))
                .pathParams("id", boardId)
                .pathParams("key", API_KEY)
                .pathParams("token", API_TOKEN)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .delete("https://api.trello.com/1/boards/{id}?key={key}&token={token}")
                .then()
                .statusCode(200)
                .extract().response();

        if (response != null) {
            logger.info("Deleted board id: " + boardId);
        } else {
            logger.error("Response is null");
        }
    }

    @Step("Delete last card")
    public void deleteCard() {
        Response response = given().config(config().decoderConfig(new DecoderConfig().noContentDecoders()))
                .pathParams("id", lastCardId)
                .pathParams("key", API_KEY)
                .pathParams("token", API_TOKEN)
                .when()
                .delete("https://api.trello.com/1/cards/{id}?key={key}&token={token}")
                .then()
                .statusCode(200)
                .extract().response();

        if (response != null) {
            logger.info("Deleted card id: " + lastCardId);
        } else {
            logger.error("Response is null");
        }
    }

    @Step("Get board list")
    public String getBoardLists() {
        Response response = given().config(config().decoderConfig(new DecoderConfig().noContentDecoders()))
                .pathParams("id", boardId)
                .pathParams("key", API_KEY)
                .pathParams("token", API_TOKEN)
                .accept(ContentType.JSON)
                .when()
                .get("https://api.trello.com/1/boards/{id}/lists?key={key}&token={token}")
                .then()
                .statusCode(200)
                .extract().response();

        if (response != null) {
            List<String> id = response.jsonPath().getList("id");
            boardListId = id.get(0);
            logger.info("Fetched board list id: " + boardListId);
            return boardListId;
        } else {
            logger.error("Response is null");
            return null;
        }
    }


    @Step("Create a new card with <name> name and <desc> description.")
    public void createCard(String name, String desc) {
        String body = "{\n" +
                "    \"name\": \"" + name + "\",\n" +
                "    \"desc\": \"" + desc + "\"}";
        Response response = given().config(config().decoderConfig(new DecoderConfig().noContentDecoders()))
                .pathParams("IDList", boardListId)
                .pathParams("key", API_KEY)
                .pathParams("token", API_TOKEN)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .body(body)
                .post("https://api.trello.com/1/cards?idList={IDList}&key={key}&token={token}")
                .then()
                .statusCode(200)
                .extract().response();

        if (response != null) {
            lastCardId = response.jsonPath().get("id");
            logger.info("Created card id: " + lastCardId);
        } else {
            logger.error("Response is null");
        }
    }


    @Step("Get a random card information")
    public String randomGetCards() {
        Response response = given().config(config().decoderConfig(new DecoderConfig().noContentDecoders()))
                .pathParams("idList", boardListId)
                .pathParams("key", API_KEY)
                .pathParams("token", API_TOKEN)
                .accept(ContentType.JSON)
                .when()
                .get("https://api.trello.com/1/lists/{idList}/cards?key={key}&token={token}")
                .then()
                .statusCode(200)
                .extract().response();

        if (response != null) {
            List<String> cardsID = response.jsonPath().getList("id");
            Random random = new Random();
            int randomCardIndex = random.nextInt(cardsID.size());
            lastCardId = cardsID.get(randomCardIndex);
            logger.info("Randomly fetched card id: " + lastCardId);
            return lastCardId;
        } else {
            logger.error("Response is null");
            return null;
        }
    }

    @Step("Update the information of the last card as <name> name <desc> description")
    public void updateCard(String name, String desc) {
        String body = "{\n" +
                "    \"name\": \"" + name + "\",\n" +
                "    \"desc\": \"" + desc + "\"}";
        Response response = given().config(config().decoderConfig(new DecoderConfig().noContentDecoders()))
                .pathParams("id", lastCardId)
                .pathParams("key", API_KEY)
                .pathParams("token", API_TOKEN)
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .body(body)
                .when()
                .put("https://api.trello.com/1/cards/{id}?key={key}&token={token}")
                .then()
                .statusCode(200)
                .extract().response();

        if (response != null) {
            lastCardId = response.jsonPath().get("id");
            logger.info("Updated card id: " + lastCardId);
        } else {
            logger.error("Response is null");
        }
    }


}
