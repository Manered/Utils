package dev.manere.utils.sql.query;

import dev.manere.utils.sql.enums.PrimaryColumn;

import java.util.ArrayList;
import java.util.List;

/**
 * The `SQLTableBuilder` class provides a convenient way to construct SQL table creation queries.
 */
public class SQLTableBuilder {
    private String tableName;
    private final List<Column> columns = new ArrayList<>();

    /**
     * Constructs and returns a new instance of SQLTableBuilder.
     *
     * @return A new instance of SQLTableBuilder.
     */
    public static SQLTableBuilder of() {
        return new SQLTableBuilder();
    }

    /**
     * Sets the name of the table being built.
     *
     * @param tableName The name of the table.
     * @return The current instance of SQLTableBuilder.
     */
    public SQLTableBuilder name(String tableName) {
        this.tableName = tableName;
        return this;
    }

    /**
     * Adds a column to the table being built.
     *
     * @param name      The name of the column.
     * @param type      The data type of the column.
     * @param isPrimary Indicates whether the column is a primary key.
     * @return The current instance of SQLTableBuilder.
     */
    public SQLTableBuilder column(String name, String type, PrimaryColumn isPrimary) {
        Column column = new Column(name, type, isPrimary);
        columns.add(column);
        return this;
    }

    public SQLTableBuilder column(String name, String type) {
        return column(name, type, PrimaryColumn.FALSE);
    }

    public SQLTableBuilder column(String name, String type, boolean isPrimary) {
        return isPrimary ? column(name, type, PrimaryColumn.TRUE) : column(name, type, PrimaryColumn.FALSE);
    }

    /**
     * Builds and returns the SQL query for creating the table.
     *
     * @return A String representing the SQL create table query.
     */
    public String build() {
        StringBuilder query = new StringBuilder("CREATE TABLE IF NOT EXISTS ");
        query.append(tableName).append(" (");

        List<String> primaryKeys = new ArrayList<>();

        for (Column column : columns) {
            query.append(column.name()).append(" ").append(column.type());

            if (column.isPrimary() == PrimaryColumn.TRUE) {
                primaryKeys.add(column.name());
            }

            query.append(", ");
        }

        query.setLength(query.length() - 2); // Remove the last comma and space

        if (!primaryKeys.isEmpty()) {
            query.append(", PRIMARY KEY(");

            for (String pk : primaryKeys) {
                query.append(pk).append(", ");
            }

            query.setLength(query.length() - 2); // Remove last comma and space
            query.append(")");
        }

        query.append(")");

        return query.toString();
    }

    /**
     * A record representing a column in the table.
     */
    public record Column(String name, String type, PrimaryColumn isPrimary) {

    }
}
