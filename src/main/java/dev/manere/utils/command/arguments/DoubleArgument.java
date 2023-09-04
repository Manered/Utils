package dev.manere.utils.command.arguments;

import org.bukkit.command.CommandSender;

public class DoubleArgument implements CommandArgument<Double> {
    @Override
    public Double parse(CommandSender sender, String arg) throws NumberFormatException {
        return Double.parseDouble(arg);
    }
}
