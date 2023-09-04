package dev.manere.utils.command.arguments;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;

import java.util.UUID;

public class OfflinePlayerArgument implements CommandArgument<OfflinePlayer> {
    @Override
    public OfflinePlayer parse(CommandSender sender, String arg) {
        return Bukkit.getOfflinePlayer(UUID.fromString(arg));
    }
}