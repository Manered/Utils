package dev.manere.utils.command.arguments;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PlayerArgument implements CommandArgument<Player> {
    @Override
    public Player parse(CommandSender sender, String arg) {
        return Bukkit.getPlayer(arg);
    }
}