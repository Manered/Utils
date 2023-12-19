package dev.manere.utils.command.args.custom;

import dev.manere.utils.command.args.exception.ArgumentParseException;
import dev.manere.utils.command.impl.dispatcher.CommandContext;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Represents a custom argument that can parse a string argument into a specific type.
 *
 * @param <T> The type to which the argument will be parsed.
 */
public interface CustomArgument<T> {
    /**
     * Parses the given argument into the specified type.
     *
     * @param ctx The CommandContext providing information about the command execution.
     * @param arg The string argument to be parsed.
     * @return The parsed value of type T, or null if parsing fails.
     * @throws ArgumentParseException If an error occurs during the parsing process.
     */
    @Nullable
    T parse(@NotNull CommandContext ctx, @Nullable String arg) throws ArgumentParseException;
}
