package dev.manere.utils.sql;

import javax.annotation.Nonnull;
import java.sql.*;

/**
 * A utility class for connecting to an SQL database.
 */
public class SQLConnector {
    private final SQLAuth authentication;
    private Connection connection;

    /**
     * Constructs an SQLConnector with the provided authentication details.
     *
     * @param authentication The authentication details for connecting to the database.
     */
    public SQLConnector(@Nonnull SQLAuth authentication) {
        this.authentication = authentication;
    }

    /**
     * Connects to the SQL database using the provided authentication details.
     */
    public void connect() {
        try {
            this.connection = DriverManager.getConnection("jdbc:mysql://" + authentication.host + ":" + authentication.port + "/" + authentication.database + "?autoReconnect=true", authentication.user, authentication.password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Closes the database connection if it is open.
     */
    public void close() {
        try {
            if (this.hasConnection()) {
                this.connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Checks if the database connection is open.
     *
     * @return True if the connection is open, false otherwise.
     */
    public boolean hasConnection() {
        return this.connection != null;
    }

    /**
     * Returns the current database connection.
     *
     * @return The current database connection.
     */
    public Connection getConnection() {
        return this.connection;
    }

    /**
     * Executes an SQL update query.
     *
     * @param sql The SQL update query to execute.
     */
    public void update(String sql) {
        try {
            PreparedStatement st = this.connection.prepareStatement(sql);
            st.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Executes an SQL query and returns the result set.
     *
     * @param sql The SQL query to execute.
     * @return The result set from the query.
     */
    public ResultSet query(String sql) {
        try {
            PreparedStatement st = this.connection.prepareStatement(sql);
            return st.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}