package dev.manere.utils.storage.sql.query;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents a SQL INSERT query builder that allows you to construct an SQL INSERT statement
 * for a specified table with one or more column-value pairs.
 */
public class SQLInsertQuery {
    private String tableName;
    private final Map<String, Object> values = new HashMap<>();

    /**
     * Static factory method to create an instance of the SQLInsertQuery class.
     *
     * @return A new SQLInsertQuery instance.
     */
    public static SQLInsertQuery of() {
        return new SQLInsertQuery();
    }

    /**
     * Specifies the target table for the INSERT query.
     *
     * @param tableName The name of the table where the data will be inserted.
     * @return The SQLInsertQuery instance to allow for method chaining.
     */
    public SQLInsertQuery into(String tableName) {
        this.tableName = tableName;
        return this;
    }

    /**
     * Adds a column-value pair to the INSERT query.
     *
     * @param column The name of the column where the value will be inserted.
     * @param value The value to insert into the specified column.
     * @return The SQLInsertQuery instance to allow for method chaining.
     */
    public SQLInsertQuery value(String column, Object value) {
        values.put(column, value);
        return this;
    }

    /**
     * Builds and returns the SQL INSERT query string based on the provided table name and
     * column-value pairs.
     *
     * @return The constructed SQL INSERT query as a String.
     */
    public String build() {
        StringBuilder query = new StringBuilder("INSERT INTO ");
        query.append(tableName).append(" (");

        for (String column : values.keySet()) {
            query.append(column).append(", ");
        }

        query.setLength(query.length() - 2); // Remove the last comma and space
        query.append(") VALUES (");

        for (Object value : values.values()) {
            if (value instanceof String) {
                query.append("'").append(value).append("', ");
            } else {
                query.append(value).append(", ");
            }
        }

        query.setLength(query.length() - 2); // Remove the last comma and space
        query.append(")");

        return query.toString();
    }
}
