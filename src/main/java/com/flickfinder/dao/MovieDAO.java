package com.flickfinder.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.flickfinder.model.Movie;
import com.flickfinder.model.MovieRating;
import com.flickfinder.model.Person;
import com.flickfinder.util.Database;
import com.flickfinder.util.Defaults;

/**
 * The Data Access Object for the Movie table.
 *
 * This class is responsible for getting data from the Movies table in the
 * database.
 */
public class MovieDAO {

	/**
	 * The connection to the database.
	 */
	private final Connection connection;

	/**
	 * Constructs a SQLiteMovieDAO object and gets the database connection.
	 */
	public MovieDAO() {
		this.connection = Database.getInstance().getConnection();
	}

	/**
	 * Returns a list of all movies in the database.
	 * 
	 * @return a list of all movies in the database
	 * @throws SQLException if a database error occurs
	 */
	public List<Movie> getAllMovies() throws SQLException {
		return this.getAllMovies(Defaults.LIMIT);
	}

	/**
	 * Returns a list of all movies in the database.
	 *
	 * @param limit the maximum number of movies to return
	 * @return a list of all movies in the database
	 * @throws SQLException if a database error occurs
	 */
	public List<Movie> getAllMovies(int limit) throws SQLException {
		List<Movie> movies = new ArrayList<>(limit);

		PreparedStatement ps = connection.prepareStatement("SELECT * FROM movies LIMIT ?");
		ps.setInt(1, limit);

		ResultSet rs = ps.executeQuery();

		while (rs.next()) {
			movies.add(new Movie(rs.getInt("id"), rs.getString("title"), rs.getInt("year")));
		}

		return movies;
	}

	/**
	 * Returns the movie with the specified id.
	 * 
	 * @param id the id of the movie
	 *
	 * @return the movie with the specified id
	 * @throws SQLException if a database error occurs
	 */
	public Movie getMovieById(int id) throws SQLException {
		PreparedStatement ps = connection.prepareStatement("SELECT * FROM movies WHERE id = ?");
		ps.setInt(1, id);

		ResultSet rs = ps.executeQuery();

		if (rs.next()) {
			return new Movie(rs.getInt("id"), rs.getString("title"), rs.getInt("year"));
		}
		
		return null;
	}

	/**
	 * Returns a list of all movies released in year, with minimum number of votes in the database.
	 *
	 * @param limit the maximum number of movies to return
	 * @param votes the minimum number of votes a movie must have to be included
	 * @param year  the year of the movies to return
	 * @return a list of all movies in the database
	 * @throws SQLException if a database error occurs
	 */
	public List<MovieRating> getRatingsByYear(int limit, int votes, int year) throws SQLException {
		List<MovieRating> movies = new ArrayList<>(limit);
		PreparedStatement ps = connection.prepareStatement("SELECT * FROM movies, ratings WHERE movies.year = ? AND movies.id = ratings.movie_id AND ratings.votes > ? ORDER BY ratings.rating DESC LIMIT ?");
		ps.setInt(1, year);
		ps.setInt(2, votes);
		ps.setInt(3, limit);

		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			movies.add(new MovieRating(rs.getInt("id"), rs.getString("title"), rs.getInt("year"), rs.getDouble("rating"), rs.getInt("votes")));
		}
		return movies;
	}

	/**
	 * Returns a list of all movies released in year, with minimum number of votes in the database.
	 *
	 * @param votes the minimum number of votes a movie must have to be included
	 * @param year  the year of the movies to return
	 * @return a list of all movies in the database
	 * @throws SQLException if a database error occurs
	 */
	public List<MovieRating> getRatingsByYear(int votes, int year) throws SQLException {
		return this.getRatingsByYear(Defaults.LIMIT, votes, year);
	}

	/**
	 * Returns a list of all movies released in year with minimum {@value com.flickfinder.util.Defaults#VOTES} votes in the database.
	 *
	 * @param year the year of the movies to return
	 * @return a list of all movies in the database
	 * @throws SQLException if a database error occurs
	 */
	public List<MovieRating> getRatingsByYear(int year) throws SQLException {
		return this.getRatingsByYear(Defaults.LIMIT, Defaults.VOTES, year);
	}

	/**
	 * Returns a list of people who worked on the movie with the specified id.
	 *
	 * @param id the id of the movie
	 * @return a list of people who worked on the movie with the specified id
	 * @throws SQLException if a database error occurs
	 */
	public List<Person> getPeopleById(int id) throws SQLException {
		List<Person> people = new ArrayList<>(20);
		PreparedStatement ps = connection.prepareStatement("SELECT * FROM people, stars WHERE stars.movie_id = ? AND people.id = stars.person_id");
		ps.setInt(1, id);

		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			people.add(new Person(rs.getInt("id"), rs.getString("name"), rs.getInt("birth")));
		}

		return people;
	}
}

