package com.flickfinder.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test for the MovieRating Model.
 */
public class MovieRatingTest {

    /**
     * The MovieRating object to be tested.
     */
    private MovieRating movieRating;

    /**
     * Set up the MovieRating object before each test.
     *
     */
    @BeforeEach
    public void setUp() {
        movieRating = new MovieRating(1, "The Matrix", 1999, 8.7, 15000);
    }

    /**
     * Test the MovieRating object is created with the correct values.
     */
    @Test
    public void testMovieCreated() {
        assertEquals(1, movieRating.getId());
        assertEquals("The Matrix", movieRating.getTitle());
        assertEquals(1999, movieRating.getYear());
        assertEquals(8.7, movieRating.getRating());
        assertEquals(15000, movieRating.getVotes());
    }

    /**
     * Test the MovieRated object is updated with the correct value setters.
     */
    @Test
    public void testMovieSetters() {
        movieRating.setId(2);
        movieRating.setTitle("The Matrix Reloaded");
        movieRating.setYear(2003);
        movieRating.setRating(7.2);
        movieRating.setVotes(20000);
        assertEquals(2, movieRating.getId());
        assertEquals("The Matrix Reloaded", movieRating.getTitle());
        assertEquals(2003, movieRating.getYear());
        assertEquals(7.2, movieRating.getRating());
        assertEquals(20000, movieRating.getVotes());
    }
}
