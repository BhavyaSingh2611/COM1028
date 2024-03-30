package com.flickfinder.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.flickfinder.model.Movie;
import com.flickfinder.util.Database;

/**
 * The Data Access Object for the Movie table.
 * 
 * This class is responsible for getting data from the Movies table in the
 * database.
 * 
 */
public class MovieDAO {

	/**
	 * The connection to the database.
	 */
	private final Connection connection;

	/**
	 * Constructs a SQLiteMovieDAO object and gets the database connection.
	 * 
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
		List<Movie> movies = new ArrayList<>(50);

		ResultSet rs = connection.createStatement().executeQuery("SELECT * FROM movies LIMIT 50");
		while (rs.next()) {
			movies.add(new Movie(rs.getInt("id"), rs.getString("title"), rs.getInt("year")));
		}

		return movies;
	}

	/**
	 * Returns the movie with the specified id.
	 * 
	 * @param id the id of the movie
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
}
