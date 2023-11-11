package dev.manere.utils.sql.connection;

import dev.manere.utils.sql.auth.SQLAuthentication;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * A record representing an SQL connector with specified authentication details.
 */
public record SQLConnector(SQLAuthentication authentication) {

    /**
     * Creates a new SQL connector with default authentication settings.
     * @return A new SQLConnector instance with default authentication settings.
     */
    public static SQLConnector of() {
        SQLAuthentication authentication = SQLAuthentication.of()
                .database("database")
                .host("localhost")
                .password("")
                .username("root")
                .port(3306);

        return new SQLConnector(authentication);
    }

    /**
     * Creates a new SQL connector.
     * @return A new SQLConnector.
     */
    public static SQLConnector of(SQLAuthentication authentication) {
        return new SQLConnector(authentication);
    }

    /**
     * Establishes a connection to the SQL database.
     * @return A Connection object representing the established database connection.
     * @throws RuntimeException If an error occurs during the connection process.
     */
    public Connection connect() {
        String url = "jdbc:mysql://" + authentication.host() + ":" + authentication.port() + "/" + authentication.database();

        try {
            return DriverManager.getConnection(url, authentication.username(), authentication.password());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
