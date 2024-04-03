package com.flickfinder.controller;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.SQLException;
import java.util.ArrayList;

import com.flickfinder.util.Defaults;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.flickfinder.dao.MovieDAO;

import io.javalin.http.Context;

/**
 * Test for the Movie Controller.
 */

class MovieControllerTest {

	/**
	 * The context object, later we will mock it.
	 */
	private Context ctx;

	/**
	 * The movie data access object.
	 */
	private MovieDAO movieDAO;

	/**
	 * The movie controller.
	 */
	private MovieController movieController;

	@BeforeEach
	void setUp() {
		movieDAO = mock(MovieDAO.class);
		ctx = mock(Context.class);
		movieController = new MovieController(movieDAO);
	}

	/**
	 * Tests the getAllMovies method.
	 * We expect to get a list of all movies in the database.
	 */

	@Test
	void testGetAllMovies() {
		movieController.getAllMovies(ctx);
		try {
			verify(movieDAO).getAllMovies(Defaults.LIMIT);
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
		when(movieDAO.getAllMovies(Defaults.LIMIT)).thenThrow(new SQLException());
		movieController.getAllMovies(ctx);
		verify(ctx).status(500);
	}

	/**
	 * Test that the controller returns a 400 status code when an invalid limit is
	 * passed.
	 *
	 * @throws SQLException
	 */
	@Test
	void testThrows400ExceptionWhenInvalidLimit() {
		when(ctx.queryParam("limit")).thenReturn("<non-numeric>");
		movieController.getAllMovies(ctx);
		verify(ctx).status(400);
	}

	/**
	 * Tests the getMovieById method.
	 * We expect to get the movie with the specified id.
	 */

	@Test
	void testGetMovieById() {
		when(ctx.pathParam("id")).thenReturn("1");
		movieController.getMovieById(ctx);
		try {
			verify(movieDAO).getMovieById(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Test a 500 status code is returned when a database error occurs.
	 * 
	 * @throws SQLException
	 */

	@Test
	void testThrows500ExceptionWhenGetByIdDatabaseError() throws SQLException {
		when(ctx.pathParam("id")).thenReturn("1");
		when(movieDAO.getMovieById(1)).thenThrow(new SQLException());
		movieController.getMovieById(ctx);
		verify(ctx).status(500);
	}

	/**
	 * Test that the controller returns a 404 status code when a movie is not found
	 * or
	 * database error.
	 * 
	 * @throws SQLException
	 */

	@Test
	void testThrows404ExceptionWhenNoMovieFound() throws SQLException {
		when(ctx.pathParam("id")).thenReturn("1");
		when(movieDAO.getMovieById(1)).thenReturn(null);
		movieController.getMovieById(ctx);
		verify(ctx).status(404);
	}

	/**
	 * Test that the controller returns a 400 status code when an invalid id is
	 * passed.
	 *
	 * @throws SQLException
	 */
	@Test
	void testThrows400ExceptionWhenInvalidId() {
		when(ctx.pathParam("id")).thenReturn("<non-numeric>");
		movieController.getMovieById(ctx);
		verify(ctx).status(400);
	}

	/**
	 * Tests the getRatingsByYear method.
	 * We expect to get a list of movies released in the specified year.
	 */
	@Test
	void testGetRatingsByYear() {
		when(ctx.pathParam("year")).thenReturn("2021");
		movieController.getRatingsByYear(ctx);
		try {
			verify(movieDAO).getRatingsByYear(Defaults.LIMIT, Defaults.VOTES, 2021);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	/**
	 * Test that the controller returns a 404 status code when no movies are found
	 * for the specified year.
	 *
	 * @throws SQLException
	 */
	@Test
	void testThrows404ExceptionWhenNoMoviesFound() throws SQLException {
		when(ctx.pathParam("year")).thenReturn("2021");
		when(movieDAO.getRatingsByYear(Defaults.LIMIT, Defaults.VOTES, 2021)).thenReturn(new ArrayList<>());
		movieController.getRatingsByYear(ctx);
		verify(ctx).status(404);
	}

	/**
	 * Test that the controller returns a 500 status code when a database error
	 * occurs
	 *
	 * @throws SQLException
	 */
	@Test
	void testThrows500ExceptionWhenGetRatingsByYearDatabaseError() throws SQLException {
		when(ctx.pathParam("year")).thenReturn("2021");
		when(movieDAO.getRatingsByYear(Defaults.LIMIT, Defaults.VOTES, 2021)).thenThrow(new SQLException());
		movieController.getRatingsByYear(ctx);
		verify(ctx).status(500);
	}

	/**
	 * Test that the controller returns a 400 status code when an invalid year is
	 * passed.
     */
	@Test
	void testThrows400ExceptionWhenInvalidYear() {
		when(ctx.pathParam("year")).thenReturn("<non-numeric>");
		movieController.getRatingsByYear(ctx);
		verify(ctx).status(400);
	}

	/**
	 * Test that the controller returns a 400 status code when an invalid limit is
	 * passed.
	 */
	@Test
	void testThrows400ExceptionWhenInvalidLimitRatingsByYear() {
		when(ctx.pathParam("year")).thenReturn("2021");
		when(ctx.queryParam("limit")).thenReturn("<non-numeric>");
		movieController.getRatingsByYear(ctx);
		verify(ctx).status(400);
	}

	/**
	 * Test that the controller returns a 400 status code when an invalid votes is
	 * passed.
     */
	@Test
	void testThrows400ExceptionWhenInvalidVotesRatingsByYear() {
		when(ctx.pathParam("year")).thenReturn("2021");
		when(ctx.queryParam("votes")).thenReturn("<non-numeric>");
		movieController.getRatingsByYear(ctx);
		verify(ctx).status(400);
	}

	/**
	 * Tests the getPeopleByMovieId method.
	 * We expect to get a list of people who worked on the movie with the specified id.
	 */
	@Test
	void testGetPeopleByMovieId() {
		when(ctx.pathParam("id")).thenReturn("1");
		movieController.getPeopleByMovieId(ctx);
		try {
			verify(movieDAO).getPeopleById(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	/**
	 * Test that the controller returns a 404 status code when no people are found
	 * for the specified movie id.
	 *
	 * @throws SQLException
	 */
	@Test
	void testThrows404ExceptionWhenNoPeopleFound() throws SQLException {
		when(ctx.pathParam("id")).thenReturn("1");
		when(movieDAO.getPeopleById(1)).thenReturn(new ArrayList<>());
		movieController.getPeopleByMovieId(ctx);
		verify(ctx).status(404);
	}

	/**
	 * Test that the controller returns a 500 status code when a database error
	 * occurs
	 *
	 * @throws SQLException
	 */
	@Test
	void testThrows500ExceptionWhenGetPeopleByMovieIdDatabaseError() throws SQLException {
		when(ctx.pathParam("id")).thenReturn("1");
		when(movieDAO.getPeopleById(1)).thenThrow(new SQLException());
		movieController.getPeopleByMovieId(ctx);
		verify(ctx).status(500);
	}

	/**
	 * Test that the controller returns a 400 status code when an invalid id is
	 * passed.
	 */
	@Test
	void testThrows400ExceptionWhenInvalidIdPeopleByMovieId() {
		when(ctx.pathParam("id")).thenReturn("<non-numeric>");
		movieController.getPeopleByMovieId(ctx);
		verify(ctx).status(400);
	}
}