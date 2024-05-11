package com.flickfinder.controller;

import com.flickfinder.dao.PersonDAO;
import com.flickfinder.util.Defaults;
import io.javalin.http.Context;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ArrayList;

import static org.mockito.Mockito.*;

class PersonControllerTest {
    /**
     * The context object, later we will mock it.
     */
    private Context ctx;

    /**
     * The movie data access object.
     */
    private PersonDAO personDAO;

    /**
     * The movie controller.
     */
    private PersonController personController;

    @BeforeEach
    void setUp() {
        personDAO = mock(PersonDAO.class);
        ctx = mock(Context.class);
        personController = new PersonController(personDAO);
    }

    /**
     * Tests the getAllPeople method.
     * We expect to get a list of all people in the database.
     */
    @Test
    void testGetAllPeople() {
        personController.getAllPeople(ctx);
        try {
            verify(personDAO).getAllPeople(Defaults.LIMIT);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Test that the controller returns a 500 status code when a database error
     * occurs
     *
     * @throws SQLException
     */
    @Test
    void testThrows500ExceptionWhenGetAllDatabaseError() throws SQLException {
        when(personDAO.getAllPeople(Defaults.LIMIT)).thenThrow(new SQLException());
        personController.getAllPeople(ctx);
        verify(ctx).status(500);
    }

    /**
     * Test that the controller returns a 400 status code when a non-numeric limit
     * parameter is passed
     */
    @Test
    void testThrows400ExceptionWhenInvalidLimit() {
        when(ctx.queryParam("limit")).thenReturn("<non-numeric>");
        personController.getAllPeople(ctx);
        verify(ctx).status(400);
    }

    /**
     * Tests the getPersonById method.
     * We expect to get the person with the specified id.
     */
    @Test
    void testGetPersonById() {
        when(ctx.pathParam("id")).thenReturn("1");
        personController.getPersonById(ctx);
        try {
            verify(personDAO).getPersonById(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Test that the controller returns a 500 status code when a database error
     * occurs
     *
     * @throws SQLException
     */
    @Test
    void testThrows500ExceptionWhenGetByIdDatabaseError() throws SQLException {
        when(ctx.pathParam("id")).thenReturn("1");
        when(personDAO.getPersonById(1)).thenThrow(new SQLException());
        personController.getPersonById(ctx);
        verify(ctx).status(500);
    }

    /**
     * Test that the controller returns a 404 status code when a person with the
     * specified id is not found
     *
     * @throws SQLException
     */
    @Test
    void testThrows404ExceptionWhenPersonNotFound() throws SQLException {
        when(ctx.pathParam("id")).thenReturn("1");
        when(personDAO.getPersonById(1)).thenReturn(null);
        personController.getPersonById(ctx);
        verify(ctx).status(404);
    }

    /**
     * Test that the controller returns a 400 status code when a non-numeric id
     * parameter is passed
     */
    @Test
    void testThrows400ExceptionWhenInvalidId() {
        when(ctx.pathParam("id")).thenReturn("<non-numeric>");
        personController.getPersonById(ctx);
        verify(ctx).status(400);
    }

    /**
     * Tests the getMoviesStarringPerson method.
     * We expect to get a list of all movies starring the person with the specified id.
     */
    @Test
    void testGetMoviesStarringPerson() {
        when(ctx.pathParam("id")).thenReturn("1");
        personController.getMoviesStarringPerson(ctx);
        try {
            verify(personDAO).getMoviesByPersonId(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Test that the controller returns a 500 status code when a database error
     * occurs
     *
     * @throws SQLException
     */
    @Test
    void testThrows500ExceptionWhenGetMoviesDatabaseError() throws SQLException {
        when(ctx.pathParam("id")).thenReturn("1");
        when(personDAO.getMoviesByPersonId(1)).thenThrow(new SQLException());
        personController.getMoviesStarringPerson(ctx);
        verify(ctx).status(500);
    }

    /**
     * Test that the controller returns a 404 status code when a person with the
     * specified id is not found
     *
     * @throws SQLException
     */
    @Test
    void testThrows404ExceptionWhenMoviesNotFound() throws SQLException {
        when(ctx.pathParam("id")).thenReturn("1");
        when(personDAO.getMoviesByPersonId(1)).thenReturn(new ArrayList<>());
        personController.getMoviesStarringPerson(ctx);
        verify(ctx).status(404);
    }

    /**
     * Test that the controller returns a 400 status code when a non-numeric id
     * parameter is passed
     */
    @Test
    void testThrows400ExceptionWhenInvalidIdMoviesStarring() {
        when(ctx.pathParam("id")).thenReturn("<non-numeric>");
        personController.getMoviesStarringPerson(ctx);
        verify(ctx).status(400);
    }
}