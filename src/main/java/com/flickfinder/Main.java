package com.flickfinder;

import com.flickfinder.util.Database;

/**
 * Entry point of the application.
 */

public class Main {
    /**
     * The port that the server should run on.
     */
    static int port = 8000;

    /**
     * Set up a Javalin server and the database.
     * 
     * @param args The command line arguments.
     */
    public static void main(String[] args) {
        final String dbPath = "src/main/resources/movies.db";

        Database.getInstance("jdbc:sqlite:" + dbPath);
        AppConfig.startServer(port);
    }
}