package com.flickfinder.model;

/**
 * Represents a movie with its unique identifier, title, release year, rating and votes.
 */
public class MovieRating extends Movie {
    private double rating;
    private int votes;

    /**
     * Constructs a Movie object with the specified id, title, and year.
     *
     * @param id    the unique identifier of the movie
     * @param title the title of the movie
     * @param year  the release year of the movie
     * @param rating the rating of the movie
     * @param votes the votes of the movie
     */
    public MovieRating(int id, String title, int year, double rating, int votes) {
        super(id, title, year);
        this.rating = rating;
        this.votes = votes;
    }

    /**
     * Returns the rating of the movie.
     *
     * @return the rating of the movie
     */
    public double getRating() {
        return this.rating;
    }

    /**
     * Sets the rating of the movie.
     *
     * @param rating the rating to set
     */
    public void setRating(double rating) {
        this.rating = rating;
    }

    /**
     * Returns the number of votes for the movie.
     *
     * @return the number of votes for the movie
     */
    public int getVotes() {
        return this.votes;
    }

    /**
     * Sets the number of votes for the movie.
     *
     * @param votes the number of votes to set
     */
    public void setVotes(int votes) {
        this.votes = votes;
    }

    /**
     * Returns a string representation of the MovieRating object.
     * This is primarily used for debugging purposes.
     *
     * @return a string representation of the MovieRating object
     */
    @Override
    public String toString() {
        return String.format("MovieRating [id=%d, title=%s, year=%d, rating=%.2f, votes=%d]",
                super.getId(), super.getTitle(), super.getYear(), this.rating, this.votes);
    }
}
