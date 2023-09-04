package dev.manere.utils.command.arguments;

import org.bukkit.command.CommandSender;

public class BooleanArgument implements CommandArgument<Boolean> {
    @Override
    public Boolean parse(CommandSender sender, String arg) throws NumberFormatException {
        return Boolean.parseBoolean(arg);
    }
}
