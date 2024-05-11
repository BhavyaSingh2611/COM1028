package com.flickfinder.dao;

import com.flickfinder.model.Movie;
import com.flickfinder.model.Person;
import com.flickfinder.util.Database;
import com.flickfinder.util.Seeder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test for the Person Data Access Object.
 * This uses an in-memory database for testing purposes.
 */
class PersonDAOTest {

    /**
     * Seeder
     */
    Seeder seeder;
    /**
     * The person data access object.
     */
    private PersonDAO personDAO;

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
        personDAO = new PersonDAO();
    }

    /**
     * Tests the getAllPeople method.
     * We expect to get a list of all people in the database.
     * We have seeded the database with 5 people, so we expect to get 5 people back.
     * At this point, we avoid checking the actual content of the list.
     */
    @Test
    void testGetAllPeople() {
        try {
            List<Person> people = personDAO.getAllPeople();
            assertEquals(5, people.size());
        } catch (Exception e) {
            fail("SQLException was thrown.");
            e.printStackTrace();
        }
    }

    /**
     * Tests the getAllPeople method with a limit.
     * We expect to get a list of all people in the database.
     * We are calling with limit 3, so we expect to get 3 people back.
     * At this point, we avoid checking the actual content of the list.
     */
    @Test
    void testGetAllPeopleWithLimit() {
        try {
            List<Person> people = personDAO.getAllPeople(3);
            assertEquals(3, people.size());
        } catch (Exception e) {
            fail("SQLException was thrown.");
            e.printStackTrace();
        }
    }

    /**
     * Tests the getPersonById method.
     * We expect to get a person with the specified id.
     */
    @Test
    void testGetPersonById() {
        try {
            Person person = personDAO.getPersonById(1);
            assertEquals("Tim Robbins", person.getName());
        } catch (Exception e) {
            fail("SQLException was thrown.");
            e.printStackTrace();
        }
    }

    /**
     * Tests the getPersonById method with an invalid id. Null should be returned.
     */
    @Test
    void testGetPersonByIdWithInvalidId() {
        try {
            Person person = personDAO.getPersonById(1000);
            assertNull(person);
        } catch (Exception e) {
            fail("SQLException was thrown.");
            e.printStackTrace();
        }
    }

    /**
     * Tests the getMovieById method.
     * We expect to get a list of all movies starring the person with the specified id.
     */
    @Test
    void testGetMoviesStarringPerson() {
        try {
            List<Movie> movies = personDAO.getMovieByPersonId(4);
            assertEquals(2, movies.size());
        } catch (Exception e) {
            fail("SQLException was thrown.");
            e.printStackTrace();
        }
    }

    /**
     * Tests the getMovieById method with an invalid id. Empty list should be returned.
     */
    @Test
    void testGetMoviesStarringPersonWithInvalidId() {
        try {
            List<Movie> movies = personDAO.getMovieByPersonId(1000);
            assertTrue(movies.isEmpty());
        } catch (Exception e) {
            fail("SQLException was thrown.");
            e.printStackTrace();
        }
    }
}