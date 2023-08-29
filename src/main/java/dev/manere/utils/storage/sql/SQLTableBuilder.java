package dev.manere.utils.storage.sql;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicReference;

/**
 * A utility class for building SQL table creation statements.
 */
public class SQLTableBuilder {
    private final String name;
    private final ArrayList<String> columns;
    private String primary;

    /**
     * Constructs a new SQLTableBuilder for the specified table name.
     *
     * @param name The name of the table to be created.
     */
    public SQLTableBuilder(@Nonnull String name) {
        this.name = name;
        this.columns = new ArrayList<>();
    }

    /**
     * Adds a column to the table with default attributes.
     *
     * @param name    The name of the column.
     * @param type    The data type of the column.
     * @param notNull Whether the column should be marked as NOT NULL.
     * @return The current SQLTableBuilder instance for method chaining.
     */
    public SQLTableBuilder addColumn(@Nonnull String name, @Nonnull SQLDataType type, boolean notNull) {
        return addColumn(name, type, notNull, null, "");
    }

    /**
     * Adds a column to the table with specified attributes and a default value.
     *
     * @param name        The name of the column.
     * @param type        The data type of the column.
     * @param notNull     Whether the column should be marked as NOT NULL.
     * @param defaultValue The default value for the column.
     * @return The current SQLTableBuilder instance for method chaining.
     */
    public SQLTableBuilder addColumn(@Nonnull String name, @Nonnull SQLDataType type, boolean notNull, Object defaultValue) {
        return addColumn(name, type, notNull, defaultValue, "");
    }

    /**
     * Adds a column to the table with specified attributes.
     *
     * @param name        The name of the column.
     * @param type        The data type of the column.
     * @param notNull     Whether the column should be marked as NOT NULL.
     * @param defaultValue The default value for the column.
     * @param params      Additional parameters for the column definition.
     * @return The current SQLTableBuilder instance for method chaining.
     */
    public SQLTableBuilder addColumn(@Nonnull String name, @Nonnull SQLDataType type, boolean notNull, Object defaultValue, Object... params) {
        String column = name + " " + type.name()
                + (params[0] != "" ? "(" + (Arrays.toString(params)).replace("[", "").replace("]", "") + ")" : "")
                + (notNull ? " NOT NULL" : "")
                + (defaultValue == null ? "" : " DEFAULT " + defaultValue);
        this.columns.add(column);
        return this;
    }

    /**
     * Sets the primary key column for the table.
     *
     * @param columnName The name of the primary key column.
     * @return The current SQLTableBuilder instance for method chaining.
     */
    public SQLTableBuilder setPrimary(@Nonnull String columnName) {
        this.primary = columnName;
        return this;
    }

    /**
     * Builds the SQL CREATE TABLE statement.
     *
     * @return The SQL CREATE TABLE statement as a string.
     */
    public String build() {
        String table = "CREATE TABLE IF NOT EXISTS " + this.name;
        if (!this.columns.isEmpty()) {

            AtomicReference<String> statement = new AtomicReference<>();

            this.columns.forEach(col -> statement.set(statement + col + ", "));

            table = table + " (" + statement;

            if (primary == null) {
                return table + ")";
            }

            return format(table + "PRIMARY KEY (" + primary + "))");


        }
        return format(table);
    }

    /**
     * @param string The string to replace "(null" to "(" in
     * @return The string with "(null" replaced to "("
     */
    private String format(@Nonnull String string) {
        return string.replace("(null", "(");
    }
}
