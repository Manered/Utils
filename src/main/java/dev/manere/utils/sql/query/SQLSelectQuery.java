package dev.manere.utils.sql.query;

import dev.manere.utils.sql.enums.QueryOperation;

import java.util.Objects;

/**
 * This class provides a builder for generating SQL SELECT queries.
 */
public class SQLSelectQuery {
    private String tableName;
    private String whereColumn;
    private QueryOperation operation;
    private Object value;

    /**
     * Creates a new instance of SQLSelectQuery.
     *
     * @return A new instance of SQLSelectQuery.
     */
    public static SQLSelectQuery of() {
        return new SQLSelectQuery();
    }

    /**
     * Sets the table name for the SELECT query.
     *
     * @param tableName The name of the table to select from.
     * @return The current SQLSelectQuery instance for method chaining.
     */
    public SQLSelectQuery from(String tableName) {
        this.tableName = tableName;
        return this;
    }

    /**
     * Specifies a WHERE clause for the SELECT query.
     *
     * @param column    The column to apply the condition on.
     * @param operation The operation for the condition (e.g., EQUALS, LESS_THAN, etc.).
     * @param value     The value to compare against in the condition.
     * @return The current SQLSelectQuery instance for method chaining.
     */
    public SQLSelectQuery where(String column, QueryOperation operation, Object value) {
        this.whereColumn = column;
        this.operation = operation;
        this.value = value;
        return this;
    }

    /**
     * Builds and returns the SQL SELECT query as a string.
     *
     * @return The generated SQL SELECT query.
     */
    public String build() {
        StringBuilder query = new StringBuilder("SELECT * FROM ");
        query.append(tableName);

        if (whereColumn != null) {
            query.append(" WHERE ").append(whereColumn).append(" ");
            if (Objects.requireNonNull(operation) == QueryOperation.EQUALS) {
                query.append("=").append(" '").append(value).append("'");
            }
        }

        return query.toString();
    }
}
