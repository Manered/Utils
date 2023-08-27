package dev.manere.utils.sql;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;

/**
 * Represents authentication details for connecting to an SQL database.
 */
public class SQLAuth {
    public String host = "localhost";
    public int port = 3306;
    public String user = "root";
    public String password = "password";
    public String database = "database";

    /**
     * Default constructor with default authentication values.
     */
    public SQLAuth() {
    }

    /**
     * Constructor to set authentication values.
     *
     * @param host     The hostname of the database server.
     * @param port     The port number to connect to.
     * @param user     The username for authentication.
     * @param password The password for authentication.
     * @param database The name of the database to connect to.
     */
    public SQLAuth(@Nonnull String host, @Nonnegative int port, @Nonnull String user, @Nonnull String password, @Nonnull String database) {
        this.host = host;
        this.port = port;
        this.user = user;
        this.password = password;
        this.database = database;
    }
}