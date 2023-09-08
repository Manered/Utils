package dev.manere.utils.command.arguments;

import org.bukkit.command.CommandSender;

/**
 * A generic interface for parsing command arguments.
 *
 * @param <T> The type to parse the argument into.
 */
public interface CommandArgument<T> {
    /**
     * Parses the argument into the specified type.
     *
     * @param sender The CommandSender who provided the argument.
     * @param arg    The argument to be parsed.
     * @return The parsed value of type T.
     */
    T parse(CommandSender sender, String arg);
}
