package dev.manere.utils.command.arguments;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;

import java.util.UUID;

/**
 * A CommandArgument implementation for parsing OfflinePlayer arguments.
 */
public class OfflinePlayerArgument implements CommandArgument<OfflinePlayer> {

    /**
     * Parses the argument into an OfflinePlayer object.
     *
     * @param sender The CommandSender who provided the argument.
     * @param arg    The argument to be parsed.
     * @return The parsed OfflinePlayer object.
     */
    @Override
    public OfflinePlayer parse(CommandSender sender, String arg) {
        return Bukkit.getOfflinePlayer(UUID.fromString(arg));
    }
}