package dev.manere.utils.command.arguments;

import org.bukkit.command.CommandSender;

/**
 * A CommandArgument implementation for parsing float arguments.
 */
public class FloatArgument implements CommandArgument<Float> {

    /**
     * Parses the argument into a Float value.
     *
     * @param sender The CommandSender who provided the argument.
     * @param arg    The argument to be parsed.
     * @return The parsed Float value.
     * @throws NumberFormatException If the argument cannot be parsed into a Float.
     */
    @Override
    public Float parse(CommandSender sender, String arg) throws NumberFormatException {
        return Float.parseFloat(arg);
    }
}
