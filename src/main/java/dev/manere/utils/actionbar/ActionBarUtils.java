package dev.manere.utils.actionbar;

import dev.manere.utils.library.Utils;
import dev.manere.utils.text.color.ColorUtils;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

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
    public static void sendAll(String text) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            sendAsync(player, text);
        }
    }

    /**
     * Sends an action bar text periodically to a specific player.
     *
     * @param delay   The delay before the first text is sent.
     * @param period  The time between subsequent texts.
     * @param player  The player to send the text to.
     * @param text The text to send.
     */
    public static void sendFor(long delay, long period, Player player, String text) {
        Utils.getPlugin().getServer().getScheduler().runTaskTimer(Utils.getPlugin(), () -> sendAsync(player, text), delay, period);
    }

    /**
     * Sends an action bar text to a specific player asynchronously.
     *
     * @param player  The player to send the text to.
     * @param text The text to send.
     */
    private static void sendAsync(Player player, String text) {
        Utils.getPlugin().getServer().getScheduler().runTaskAsynchronously(Utils.getPlugin(), () -> send(player, text));
    }
}
