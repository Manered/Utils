package dev.manere.utils.storage.sql.connection;

import dev.manere.utils.storage.sql.auth.SQLAuthentication;
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
        SQLAuthentication authentication = new SQLAuthentication();

        authentication.database("database");
        authentication.host("localhost");
        authentication.password("");
        authentication.username("root");
        authentication.port(3306);

        return new SQLConnector(authentication);
    }

    /**
     * Establishes a connection to the SQL database.
     * @return A Connection object representing the established database connection.
     * @throws RuntimeException If an error occurs during the connection process.
     */
    public Connection connect() {
        String url = "jdbc:mysql://" + authentication.getHost() + ":" + authentication.getPort() + "/" + authentication.getDatabase();

        try {
            return DriverManager.getConnection(url, authentication.getUsername(), authentication.getPassword());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Retrieves the authentication details associated with this SQL connector.
     * @return The SQLAuthentication object containing the authentication details.
     */
    @Override
    public SQLAuthentication authentication() {
        return new SQLAuthentication();
    }
}
