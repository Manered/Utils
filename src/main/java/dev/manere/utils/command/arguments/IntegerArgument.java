package dev.manere.utils.command.arguments;

import org.bukkit.command.CommandSender;

/**
 * A CommandArgument implementation for parsing integer arguments.
 */
public class IntegerArgument implements CommandArgument<Integer> {

    /**
     * Parses the argument into an Integer value.
     *
     * @param sender The CommandSender who provided the argument.
     * @param arg    The argument to be parsed.
     * @return The parsed Integer value.
     * @throws NumberFormatException If the argument cannot be parsed into an Integer.
     */
    @Override
    public Integer parse(CommandSender sender, String arg) throws NumberFormatException {
        return Integer.parseInt(arg);
    }
}
