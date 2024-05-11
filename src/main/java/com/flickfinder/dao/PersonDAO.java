package com.flickfinder.dao;

import com.flickfinder.model.Movie;
import com.flickfinder.model.Person;
import com.flickfinder.util.Database;
import com.flickfinder.util.Defaults;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PersonDAO {
    /**
     * The connection to the database.
     */
    private final Connection connection;

    /**
     * Constructs a SQLitePersonDAO object and gets the database connection.
     */
    public PersonDAO() {
        this.connection = Database.getInstance().getConnection();
    }

    /**
     * Returns a list of all people in the database.
     *
     * @return a list of all people in the database
     * @throws SQLException if a database error occurs
     */
    public List<Person> getAllPeople() throws SQLException {
        return this.getAllPeople(Defaults.LIMIT);
    }

    /**
     * Returns a list of all people in the database.
     *
     * @param limit the maximum number of people to return
     * @return a list of all people in the database
     * @throws SQLException if a database error occurs
     */
    public List<Person> getAllPeople(int limit) throws SQLException {
        List<Person> people = new ArrayList<>(limit);

        PreparedStatement ps = connection.prepareStatement("SELECT * FROM people LIMIT ?");
        ps.setInt(1, limit);

        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            people.add(new Person(rs.getInt("id"), rs.getString("name"), rs.getInt("birth")));
        }

        return people;
    }

    /**
     * Returns the person with the specified id.
     *
     * @param id the unique identifier of the person
     * @return the person with the specified id
     * @throws SQLException if a database error occurs
     */
    public Person getPersonById(int id) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("SELECT * FROM people WHERE id = ?");
        ps.setInt(1, id);

        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            return new Person(rs.getInt("id"), rs.getString("name"), rs.getInt("birth"));
        }

        return null;
    }

    /**
     * Returns a list of all movies starring the person with the specified id.
     *
     * @param id the unique identifier of the person
     * @return a list of all movies starring the person with the specified id
     * @throws SQLException if a database error occurs
     */
    public List<Movie> getMoviesByPersonId(int id) throws SQLException {
        List<Movie> movies = new ArrayList<>(20);
        PreparedStatement ps = connection.prepareStatement("SELECT * FROM movies, stars WHERE stars.person_id = ? AND movies.id = stars.movie_id");
        ps.setInt(1, id);

        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            movies.add(new Movie(rs.getInt("id"), rs.getString("title"), rs.getInt("year")));
        }

        return movies;
    }
}
