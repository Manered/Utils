package dev.manere.utils.storage.sql.query;

import dev.manere.utils.storage.sql.enums.QueryOperation;

import java.util.Objects;

/**
 * A utility class for constructing SQL UPDATE queries.
 */
public class SQLUpdateQuery {
    private String tableName;
    private String setColumn;
    private Object setValue;
    private String whereColumn;
    private QueryOperation operation;
    private Object whereValue;

    /**
     * Creates a new instance of SQLUpdateQuery.
     * @return A new instance of SQLUpdateQuery.
     */
    public static SQLUpdateQuery of() {
        return new SQLUpdateQuery();
    }

    /**
     * Sets the name of the table to perform the update operation on.
     * @param tableName The name of the table.
     * @return The updated SQLUpdateQuery instance.
     */
    public SQLUpdateQuery table(String tableName) {
        this.tableName = tableName;
        return this;
    }

    /**
     * Sets the column and value to be updated.
     * @param column The name of the column.
     * @param value The value to be set in the column.
     * @return The updated SQLUpdateQuery instance.
     */
    public SQLUpdateQuery value(String column, Object value) {
        this.setColumn = column;
        this.setValue = value;
        return this;
    }

    /**
     * Sets the column, operation, and value for the WHERE clause.
     * @param column The name of the column in the WHERE clause.
     * @param operation The operation to be performed (e.g., EQUALS).
     * @param value The value to be used in the WHERE clause.
     * @return The updated SQLUpdateQuery instance.
     */
    public SQLUpdateQuery where(String column, QueryOperation operation, Object value) {
        this.whereColumn = column;
        this.operation = operation;
        this.whereValue = value;
        return this;
    }

    /**
     * Builds the SQL UPDATE query based on the provided information.
     * @return The constructed SQL UPDATE query as a String.
     */
    public String build() {
        StringBuilder query = new StringBuilder("UPDATE ");
        query.append(tableName).append(" SET ");
        query.append(setColumn).append(" = ");

        if (setValue instanceof String) {
            query.append("'").append(setValue).append("'");
        } else {
            query.append(setValue);
        }

        query.append(" WHERE ").append(whereColumn).append(" ");

        if (Objects.requireNonNull(operation) == QueryOperation.EQUALS) {
            query.append("=").append(" '").append(whereValue).append("'");
        }

        return query.toString();
    }
}
