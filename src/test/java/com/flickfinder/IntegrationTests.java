package com.flickfinder;

import com.flickfinder.util.Database;
import com.flickfinder.util.Seeder;
import io.javalin.Javalin;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItems;

/**
 * These are our integration tests.
 * We are testing the application as a whole, including the database.
 */
class IntegrationTests {

    /**
     * The Javalin app.*
     */
    Javalin app;

    /**
     * The seeder object.
     */
    Seeder seeder;

    /**
     * The port number. Try and use a different port number from your main
     * application.
     */
    int port = 6000;

    /**
     * The base URL for our test application.
     */
    String baseURL = "http://localhost:" + port;

    /**
     * Bootstraps the application before each test.
     */
    @BeforeEach
    void setUp() {
        var url = "jdbc:sqlite::memory:";
        seeder = new Seeder(url);
        Database.getInstance(seeder.getConnection());
        app = AppConfig.startServer(port);
    }

    /**
     * Test that the application retrieves a list of all movies.
     * Notice how we are checking the actual content of the list.
     * At this higher level, we are not concerned with the implementation details.
     */

    @Test
    void retrievesAListOfAllMovies() {
        given().when().get(baseURL + "/movies").then().assertThat().statusCode(200)
                .body("id", hasItems(1, 2, 3, 4, 5))
                .body("title", hasItems("The Shawshank Redemption", "The Godfather",
                        "The Godfather: Part II", "The Dark Knight", "12 Angry Men"))
                .body("year", hasItems(1994, 1972, 1974, 2008, 1957));
    }

    /**
     * Test that the application retrieves a single movie by its id.
     */
    @Test
    void retrievesASingleMovieById() {

        given().when().get(baseURL + "/movies/1").then().assertThat().statusCode(200)
                .body("id", equalTo(1))
                .body("title", equalTo("The Shawshank Redemption"))
                .body("year", equalTo(1994));
    }

    /**
     * Test that the application retrieves people by movie id.
     */
    @Test
    void retrievesPeopleByMovieId() {
        given().when().get(baseURL + "/movies/1/stars").then().assertThat().statusCode(200)
                .body("id", hasItems(1, 2))
                .body("name", hasItems("Tim Robbins", "Morgan Freeman"))
                .body("birth", hasItems(1958, 1937));
    }

    /**
     * Test that the application retrieves ratings by year.
     */
    @Test
    void retrievesRatingsByYear() {
        given().when().get(baseURL + "/movies/ratings/1994").then().assertThat().statusCode(200)
                .body("id", hasItems(1))
                .body("title", hasItems("The Shawshank Redemption"))
                .body("year", hasItems(1994))
                .body("rating", hasItems(9.3f))
                .body("votes", hasItems(2200000));
    }

    /**
     * Test that the application retrieves all people.
     */
    @Test
    void retrievesAllPeople() {
        given().when().get(baseURL + "/people").then().assertThat().statusCode(200)
                .body("id", hasItems(1, 2, 3, 4, 5))
                .body("name", hasItems("Tim Robbins", "Morgan Freeman", "Christopher Nolan", "Al Pacino", "Henry Fonda"))
                .body("birth", hasItems(1958, 1937, 1970, 1940, 1905));
    }

    /**
     * Test that the application retrieves a person by id.
     */
    @Test
    void retrievesPersonById() {
        given().when().get(baseURL + "/people/1").then().assertThat().statusCode(200)
                .body("id", equalTo(1))
                .body("name", equalTo("Tim Robbins"))
                .body("birth", equalTo(1958));
    }

    /**
     * Test that the application retrieves movies starring a person.
     */
    @Test
    void retrievesMoviesStarringPerson() {
        given().when().get(baseURL + "/people/4/movies").then().assertThat().statusCode(200)
                .body("id", hasItems(2, 3))
                .body("title", hasItems("The Godfather",
                        "The Godfather: Part II"))
                .body("year", hasItems(1972, 1974));
    }

    /**
     * Tears down the application after each test.
     * We want to make sure that each test runs in isolation.
     */
    @AfterEach
    void tearDown() {
        seeder.closeConnection();
        app.stop();
    }
}
