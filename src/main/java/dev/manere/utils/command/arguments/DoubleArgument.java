package dev.manere.utils.command.arguments;

import org.bukkit.command.CommandSender;

/**
 * A CommandArgument implementation for parsing double arguments.
 */
public class DoubleArgument implements CommandArgument<Double> {

    /**
     * Parses the argument into a Double value.
     *
     * @param sender The CommandSender who provided the argument.
     * @param arg    The argument to be parsed.
     * @return The parsed Double value.
     * @throws NumberFormatException If the argument cannot be parsed into a Double.
     */
    @Override
    public Double parse(CommandSender sender, String arg) throws NumberFormatException {
        return Double.parseDouble(arg);
    }
}
