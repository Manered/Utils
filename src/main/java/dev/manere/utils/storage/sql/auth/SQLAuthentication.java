package dev.manere.utils.storage.sql.auth;

import dev.manere.utils.storage.sql.connection.SQLConnector;

/**
 * This class provides a builder pattern for configuring SQL authentication details
 * to be used with a {@link dev.manere.utils.storage.sql.connection.SQLConnector}.
 */
public class SQLAuthentication {
    private String host;
    private int port;
    private String database;
    private String username;
    private String password;

    /**
     * Sets the host for the SQL connection.
     *
     * @param host The host address of the SQL server.
     * @return This SQLAuthentication instance for method chaining.
     */
    public SQLAuthentication host(String host) {
        this.host = host;
        return this;
    }

    /**
     * Sets the port for the SQL connection.
     *
     * @param port The port number of the SQL server.
     * @return This SQLAuthentication instance for method chaining.
     */
    public SQLAuthentication port(int port) {
        this.port = port;
        return this;
    }

    /**
     * Sets the name of the database for the SQL connection.
     *
     * @param database The name of the database.
     * @return This SQLAuthentication instance for method chaining.
     */
    public SQLAuthentication database(String database) {
        this.database = database;
        return this;
    }

    /**
     * Sets the username for authentication to the SQL server.
     *
     * @param username The username for authentication.
     * @return This SQLAuthentication instance for method chaining.
     */
    public SQLAuthentication username(String username) {
        this.username = username;
        return this;
    }

    /**
     * Sets the password for authentication to the SQL server.
     *
     * @param password The password for authentication.
     * @return This SQLAuthentication instance for method chaining.
     */
    public SQLAuthentication password(String password) {
        this.password = password;
        return this;
    }

    /**
     * Builds and returns a new instance of {@link dev.manere.utils.storage.sql.connection.SQLConnector}
     * with the configured authentication details.
     *
     * @return A new SQLConnector instance.
     */
    public SQLConnector build() {
        return new SQLConnector(this);
    }

    /**
     * Gets the host address set for the SQL connection.
     *
     * @return The host address.
     */
    public String getHost() {
        return host;
    }

    /**
     * Gets the port number set for the SQL connection.
     *
     * @return The port number.
     */
    public int getPort() {
        return port;
    }

    /**
     * Gets the name of the database set for the SQL connection.
     *
     * @return The database name.
     */
    public String getDatabase() {
        return database;
    }

    /**
     * Gets the username set for authentication to the SQL server.
     *
     * @return The authentication username.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Gets the password set for authentication to the SQL server.
     *
     * @return The authentication password.
     */
    public String getPassword() {
        return password;
    }
}
