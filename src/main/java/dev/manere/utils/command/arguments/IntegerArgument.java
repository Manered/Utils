package dev.manere.utils.command.arguments;

import org.bukkit.command.CommandSender;

public class IntegerArgument implements CommandArgument<Integer> {
    @Override
    public Integer parse(CommandSender sender, String arg) throws NumberFormatException {
        return Integer.parseInt(arg);
    }
}
