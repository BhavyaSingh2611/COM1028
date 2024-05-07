package com.flickfinder.dao;

import com.flickfinder.model.Movie;
import com.flickfinder.model.MovieRating;
import com.flickfinder.model.Person;
import com.flickfinder.util.Database;
import com.flickfinder.util.Seeder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test for the Movie Data Access Object.
 * This uses an in-memory database for testing purposes.
 */

class MovieDAOTest {

    /**
     * Seeder
     */

    Seeder seeder;
    /**
     * The movie data access object.
     */

    private MovieDAO movieDAO;

    /**
     * Sets up the database connection and creates the tables.
     * We are using an in-memory database for testing purposes.
     * This gets passed to the Database class to get a connection to the database.
     * As it's a singleton class, the entire application will use the same
     * connection.
     */
    @BeforeEach
    void setUp() {
        var url = "jdbc:sqlite::memory:";
        seeder = new Seeder(url);
        Database.getInstance(seeder.getConnection());
        movieDAO = new MovieDAO();
    }

    /**
     * Tests the getAllMovies method.
     * We expect to get a list of all movies in the database.
     * We have seeded the database with 5 movies, so we expect to get 5 movies back.
     * At this point, we avoid checking the actual content of the list.
     */
    @Test
    void testGetAllMovies() {
        try {
            List<Movie> movies = movieDAO.getAllMovies();
            assertEquals(5, movies.size());
        } catch (SQLException e) {
            fail("SQLException thrown");
            e.printStackTrace();
        }
    }

    /**
     * Tests the getAllMovies method with a limit.
     * We expect to get a list of all movies in the database.
     * We are calling with limit 3, so we expect to get 3 movies back.
     * At this point, we avoid checking the actual content of the list.
     */
    @Test
    void testGetAllMoviesWithLimit() {
        try {
            List<Movie> movies = movieDAO.getAllMovies(3);
            assertEquals(3, movies.size());
        } catch (SQLException e) {
            fail("SQLException thrown");
            e.printStackTrace();
        }
    }

    /**
     * Tests the getMovieById method.
     * We expect to get the movie with the specified id.
     */
    @Test
    void testGetMovieById() {
        Movie movie;
        try {
            movie = movieDAO.getMovieById(1);
            assertEquals("The Shawshank Redemption", movie.getTitle());
        } catch (SQLException e) {
            fail("SQLException thrown");
            e.printStackTrace();
        }
    }

    /**
     * Tests the getMovieById method with an invalid id. Null should be returned.
     */
    @Test
    void testGetMovieByIdInvalidId() {
        try {
            Movie movie = movieDAO.getMovieById(1000);
            assertNull(movie);
        } catch (SQLException e) {
            fail("SQLException thrown");
            e.printStackTrace();
        }
    }

    /**
     * Tests the getRatingsByYear method.
     * We expect to get a list of all movies in the database with the specified year.
     * only 1 movie was released in 1972 present in the database, so we expect to get 1 movie back.
     * At this point, we avoid checking the actual content of the list.
     */
    @Test
    void testGetRatingsByYear() {
        try {
            List<MovieRating> movies = movieDAO.getRatingsByYear(1972);
            assertEquals(1, movies.size());
        } catch (SQLException e) {
            fail("SQLException thrown");
            e.printStackTrace();
        }
    }

    /**
     * Tests the getPeopleByMovieId method.
     * We expect to get a list of all people in the database with the specified movie id.
     * movie with id 1 has 2 people starring, so we expect to get 2 people back.
     * At this point, we avoid checking the actual content of the list.
     */
    @Test
    void testGetPeopleByMovieId() {
        try {
            List<Person> people = movieDAO.getPeopleByMovieId(1);
            assertEquals(2, people.size());
        } catch (SQLException e) {
            fail("SQLException thrown");
            e.printStackTrace();
        }
    }

    /**
     * Closes the database connection.
     */
    @AfterEach
    void tearDown() {
        seeder.closeConnection();
    }
}