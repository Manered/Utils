package dev.manere.utils.command.arguments;

import org.bukkit.command.CommandSender;

/**
 * A CommandArgument implementation for parsing boolean arguments.
 */
public class BooleanArgument implements CommandArgument<Boolean> {

    /**
     * Parses the argument into a Boolean value.
     *
     * @param sender The CommandSender who provided the argument.
     * @param arg    The argument to be parsed.
     * @return The parsed Boolean value.
     * @throws NumberFormatException If the argument cannot be parsed into a Boolean.
     */
    @Override
    public Boolean parse(CommandSender sender, String arg) throws NumberFormatException {
        return Boolean.parseBoolean(arg);
    }
}
