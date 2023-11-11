package dev.manere.utils.sql.query;

import dev.manere.utils.sql.enums.QueryOperation;

import java.util.Objects;

/**
 * A class for constructing SQL DELETE queries dynamically.
 */
public class SQLDeleteQuery {
    private String tableName;
    private String whereColumn;
    private QueryOperation operation;
    private Object value;

    /**
     * Creates a new instance of SQLDeleteQuery.
     * @return A new SQLDeleteQuery instance.
     */
    public static SQLDeleteQuery of() {
        return new SQLDeleteQuery();
    }

    /**
     * Sets the name of the table from which to delete records.
     * @param tableName The name of the table.
     * @return The current SQLDeleteQuery instance for method chaining.
     */
    public SQLDeleteQuery from(String tableName) {
        this.tableName = tableName;
        return this;
    }

    /**
     * Sets the column, operation, and value for the WHERE clause to filter records.
     * @param column The name of the column to filter on.
     * @param operation The QueryOperation to use in the WHERE clause.
     * @param value The value to compare in the WHERE clause.
     * @return The current SQLDeleteQuery instance for method chaining.
     */
    public SQLDeleteQuery where(String column, QueryOperation operation, Object value) {
        this.whereColumn = column;
        this.operation = operation;
        this.value = value;
        return this;
    }

    /**
     * Builds the SQL DELETE query based on the configured parameters.
     * @return A string representing the constructed SQL DELETE query.
     */
    public String build() {
        StringBuilder query = new StringBuilder("DELETE FROM ");
        query.append(tableName).append(" WHERE ").append(whereColumn).append(" ");

        if (Objects.requireNonNull(operation) == QueryOperation.EQUALS) {
            query.append("=").append(" '").append(value).append("'");
        }

        return query.toString();
    }
}
