package dev.manere.utils.command.arguments;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * A CommandArgument implementation for parsing Player arguments.
 */
public class PlayerArgument implements CommandArgument<Player> {

    /**
     * Parses the argument into a Player object.
     *
     * @param sender The CommandSender who provided the argument.
     * @param arg    The argument to be parsed.
     * @return The parsed Player object.
     */
    @Override
    public Player parse(CommandSender sender, String arg) {
        return Bukkit.getPlayer(arg);
    }
}