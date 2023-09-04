package dev.manere.utils.command.arguments;

import org.bukkit.command.CommandSender;

public class FloatArgument implements CommandArgument<Float> {
    @Override
    public Float parse(CommandSender sender, String arg) throws NumberFormatException {
        return Float.parseFloat(arg);
    }
}
