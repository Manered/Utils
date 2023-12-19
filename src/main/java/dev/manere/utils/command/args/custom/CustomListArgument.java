package dev.manere.utils.command.args.custom;

import dev.manere.utils.command.args.exception.ArgumentParseException;
import dev.manere.utils.command.impl.dispatcher.CommandContext;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * Represents a custom argument that deals with a list of string arguments.
 */
public abstract class CustomListArgument<T> implements CustomArgument<List<T>> {
    /**
     * Parses a list of string arguments into a specific type.
     *
     * @param args The list of string arguments to be parsed.
     * @return The parsed value, or null if parsing fails.
     * @throws ArgumentParseException If an error occurs during the parsing process.
     */
    @Nullable
    public abstract T parse(@Nullable List<String> args) throws ArgumentParseException;

    /**
     * Parses a specific string argument at the given index into a specific type.
     *
     * @param ctx        The CommandContext providing information about the command execution.
     * @param indexStart The index of the argument to be parsed.
     * @return The parsed value, or null if parsing fails.
     * @throws ArgumentParseException If an error occurs during the parsing process.
     */
    @Nullable
    public abstract T parse(@NotNull CommandContext ctx, int indexStart) throws ArgumentParseException;

    /**
     * Parses a string argument into a list of a specific type.
     *
     * @param ctx The CommandContext providing information about the command execution.
     * @param arg The string argument to be parsed.
     * @return The parsed list, or null if parsing fails.
     */
    @Nullable
    public List<T> parse(@NotNull CommandContext ctx, @Nullable String arg) {
        return null;
    }
}
