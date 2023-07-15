package dev.manere.utils.actionbar;

import dev.manere.utils.color.ColorUtils;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class ActionBarUtils {
    public static void send(Player player, String message) {
        player.spigot().sendMessage(
                ChatMessageType.ACTION_BAR,
                TextComponent.fromLegacyText(
                        ColorUtils.translate(
                                message
                        )
                )
        );
    }

    public static void sendAll(String message) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.spigot().sendMessage(
                    ChatMessageType.ACTION_BAR,
                    TextComponent.fromLegacyText(
                            ColorUtils.translate(
                                    message
                            )
                    )
            );
        }
    }

    public void sendFor(long delay, long period, JavaPlugin plugin, Player player, String message) {
        Bukkit.getScheduler().runTaskTimer(plugin, () -> send(player, message), delay, period);
    }
}
