package dev.manere.utils.actionbar;

import dev.manere.utils.color.ColorUtils;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

/**
 * Utility class for sending action bar messages to players.
 */
public class ActionBarUtils {

    /**
     * Sends an action bar message to a specific player.
     *
     * @param player  The player to send the message to.
     * @param message The message to send.
     */
    public static void send(Player player, String message) {
        player.spigot().sendMessage(
                ChatMessageType.ACTION_BAR,
                TextComponent.fromLegacyText(
                        ColorUtils.translateAlternateColorCodes(
                                message
                        )
                )
        );
    }

    /**
     * Sends an action bar message to all online players.
     *
     * @param message The message to send.
     */
    public static void sendAll(String message) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.spigot().sendMessage(
                    ChatMessageType.ACTION_BAR,
                    TextComponent.fromLegacyText(
                            ColorUtils.translateAlternateColorCodes(
                                    message
                            )
                    )
            );
        }
    }

    /**
     * Sends an action bar message periodically to a specific player.
     *
     * @param delay   The delay before the first message is sent.
     * @param period  The time between subsequent messages.
     * @param plugin  The Plugin instance responsible for scheduling.
     * @param player  The player to send the message to.
     * @param message The message to send.
     */
    public static void sendFor(long delay, long period, Plugin plugin, Player player, String message) {
        Bukkit.getScheduler().runTaskTimer(plugin, () -> send(player, message), delay, period);
    }
}
