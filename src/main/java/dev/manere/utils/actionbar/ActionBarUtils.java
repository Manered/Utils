package dev.manere.utils.actionbar;

import dev.manere.utils.text.color.ColorUtils;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

/**
 * Utility class for sending action bar texts to players.
 */
public class ActionBarUtils {

    /**
     * Sends an action bar text to a specific player.
     *
     * @param player  The player to send the text to.
     * @param text The text to send.
     */
    public static void send(Player player, String text) {
        player.spigot().sendMessage(
                ChatMessageType.ACTION_BAR,
                TextComponent.fromLegacyText(
                        ColorUtils.color(
                                text
                        )
                )
        );
    }

    /**
     * Sends an action bar text to all online players.
     *
     * @param text The text to send.
     */
    public static void sendAll(Plugin plugin, String text) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            sendAsync(plugin, player, text);
        }
    }

    /**
     * Sends an action bar text periodically to a specific player.
     *
     * @param delay   The delay before the first text is sent.
     * @param period  The time between subsequent texts.
     * @param plugin  The Plugin instance responsible for scheduling.
     * @param player  The player to send the text to.
     * @param text The text to send.
     */
    public static void sendFor(long delay, long period, Plugin plugin, Player player, String text) {
        Bukkit.getScheduler().runTaskTimer(plugin, () -> sendAsync(plugin, player, text), delay, period);
    }

    /**
     * Sends an action bar text to a specific player asynchronously.
     *
     * @param player  The player to send the text to.
     * @param text The text to send.
     */
    private static void sendAsync(Plugin plugin, Player player, String text) {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> send(player, text));
    }
}
