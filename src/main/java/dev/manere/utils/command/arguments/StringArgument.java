package dev.manere.utils.command.arguments;

import org.bukkit.command.CommandSender;

/**
 * A CommandArgument implementation for parsing String arguments.
 */
public class StringArgument implements CommandArgument<String> {

    /**
     * Returns the argument as a String.
     *
     * @param sender The CommandSender who provided the argument.
     * @param arg    The argument to be parsed.
     * @return The parsed String value.
     */
    @Override
    public String parse(CommandSender sender, String arg) {
        return arg;
    }
}