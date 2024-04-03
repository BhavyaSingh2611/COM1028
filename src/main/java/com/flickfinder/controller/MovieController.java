package com.flickfinder.controller;

import java.sql.SQLException;
import java.util.List;

import com.flickfinder.dao.MovieDAO;
import com.flickfinder.model.Movie;

import com.flickfinder.model.MovieRating;
import com.flickfinder.model.Person;
import com.flickfinder.util.Defaults;
import io.javalin.http.Context;
import io.javalin.util.JavalinLogger;

import static com.flickfinder.util.Utils.coalesce;

/**
 * The controller for the movie endpoints.
 * 
 * The controller acts as an intermediary between the HTTP routes and the DAO.
 * 
 * As you can see each method in the controller class is responsible for
 * handling a specific HTTP request.
 * 
 * Methods a Javalin Context object as a parameter and uses it to send a
 * response back to the client.
 * We also handle business logic in the controller, such as validating input and
 * handling errors.
 *
 * Notice that the methods don't return anything. Instead, they use the Javalin
 * Context object to send a response back to the client.
 */

public class MovieController {

	/**
	 * The movie data access object.
	 */

	private final MovieDAO movieDAO;

	/**
	 * Constructs a MovieController object and initializes the movieDAO.
	 */
	public MovieController(MovieDAO movieDAO) {
		this.movieDAO = movieDAO;
	}

	/**
	 * Returns a list of all movies in the database.
	 * 
	 * @param ctx the Javalin context
	 */
	public void getAllMovies(Context ctx) {
		try {
			int limit = Integer.parseInt(coalesce(ctx.queryParam("limit"), Integer.toString(Defaults.LIMIT)));
			ctx.json(movieDAO.getAllMovies(limit));
		} catch (SQLException e) {
			ctx.status(500);
			ctx.result("Database error");
			JavalinLogger.error("Database error", e);
		} catch (NumberFormatException e) {
			ctx.status(400);
			ctx.result("Invalid limit parameter");
			JavalinLogger.error("Non numeric limit parameter", e);
		}
	}

	/**
	 * Returns the movie with the specified id.
	 * 
	 * @param ctx the Javalin context
	 */
	public void getMovieById(Context ctx) {
		try {
			int id = Integer.parseInt(ctx.pathParam("id"));

			Movie movie = movieDAO.getMovieById(id);
			if (movie == null) {
				ctx.status(404);
				ctx.result("Movie not found");
				return;
			}
			ctx.json(movie);
		} catch (SQLException e) {
			ctx.status(500);
			ctx.result("Database error");
			JavalinLogger.error("Database error", e);
		} catch (NumberFormatException e) {
			ctx.status(400);
			ctx.result("Invalid id parameter");
			JavalinLogger.error("Non numeric id parameter", e);
		}
	}

	/**
	 * Returns a list of movies released in the specified year.
	 *
	 * @param ctx the Javalin context
	 */
	public void getRatingsByYear(Context ctx) {
		try {
			int year = Integer.parseInt(ctx.pathParam("year"));
			int limit = Integer.parseInt(coalesce(ctx.queryParam("limit"), Integer.toString(Defaults.LIMIT)));
			int votes = Integer.parseInt(coalesce(ctx.queryParam("votes"), Integer.toString(Defaults.VOTES)));

			List<MovieRating> ratings = movieDAO.getRatingsByYear(limit, votes, year);
			if (ratings.isEmpty()) {
				ctx.status(404);
				ctx.result("No movies found");
				return;
			}

			ctx.json(ratings);
		} catch (SQLException e) {
			ctx.status(500);
			ctx.result("Database error");
			JavalinLogger.error("Database error", e);
		} catch (NumberFormatException e) {
			ctx.status(400);
			ctx.result("Invalid parameter(s)");
			JavalinLogger.error("Non numeric parameter(s)", e);
		}
	}

	/**
	 * Returns a list of people who worked on the movie with the specified id.
	 *
	 * @param ctx the Javalin context
	 */
	public void getPeopleByMovieId(Context ctx) {
		try {
			int id = Integer.parseInt(ctx.pathParam("id"));

			List<Person> people = movieDAO.getPeopleById(id);
			if (people.isEmpty()) {
				ctx.status(404);
				ctx.result("Movie not found");
				return;
			}

			ctx.json(people);
		} catch (SQLException e) {
			ctx.status(500);
			ctx.result("Database error");
			JavalinLogger.error("Database error", e);
		} catch (NumberFormatException e) {
			ctx.status(400);
			ctx.result("Invalid id parameter");
			JavalinLogger.error("Non numeric id parameter", e);
		}
	}
}