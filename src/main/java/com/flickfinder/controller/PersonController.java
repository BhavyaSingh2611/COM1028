package com.flickfinder.controller;

import com.flickfinder.dao.PersonDAO;
import com.flickfinder.model.Movie;
import com.flickfinder.model.Person;
import com.flickfinder.util.Defaults;
import io.javalin.http.Context;
import io.javalin.util.JavalinLogger;

import java.sql.SQLException;
import java.util.List;

import static com.flickfinder.util.Utils.coalesce;

/**
 * The controller for the people endpoints.
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
public class PersonController {

    /**
    * The person data access object.
    */
    private final PersonDAO personDAO;

    /**
     * Constructs a PersonController object and initializes the personDAO.
     */
    public PersonController(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }

    /**
     * Returns a list of all people in the database.
     *
     * @param ctx the Javalin context
     */
    public void getAllPeople(Context ctx) {
        try {
            int limit = Integer.parseInt(coalesce(ctx.queryParam("limit"), Integer.toString(Defaults.LIMIT)));
            ctx.json(personDAO.getAllPeople(limit));
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
     * Returns the person with the specified id.
     *
     * @param ctx the Javalin context
     */
    public void getPersonById(Context ctx) {
        try {
            int id = Integer.parseInt(ctx.pathParam("id"));

            Person person = personDAO.getPersonById(id);
            if (person == null) {
                ctx.status(404);
                ctx.result("Person not found");
                return;
            }

            ctx.json(person);
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
     * Returns a list of all movies starring the person with the specified id.
     *
     * @param ctx the Javalin context
     */
    public void getMoviesStarringPerson(Context ctx) {
        try {
            int id = Integer.parseInt(ctx.pathParam("id"));

            List<Movie> movies = personDAO.getMovieById(id);
            if (movies.isEmpty()) {
                ctx.status(404);
                ctx.result("Movies not found");
                return;
            }
            ctx.json(movies);
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