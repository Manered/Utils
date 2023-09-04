package dev.manere.utils.command.arguments;

import org.bukkit.command.CommandSender;

public interface CommandArgument<T> {
    T parse(CommandSender sender, String arg);
}