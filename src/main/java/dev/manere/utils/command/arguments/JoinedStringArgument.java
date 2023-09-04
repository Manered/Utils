package dev.manere.utils.command.arguments;

import org.bukkit.command.CommandSender;

public class JoinedStringArgument implements CommandArgument<String> {
    @Override
    public String parse(CommandSender sender, String arg) {
        return String.join(arg);
    }
}