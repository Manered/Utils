package dev.manere.utils.player;

import dev.manere.utils.text.color.ColorUtils;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.Objects;
import java.util.UUID;

/**
 * A utility class for common operations related to players in Bukkit.
 */
public class PlayerUtils {
    /**
     * Resets various attributes and states of the given player to default values.
     *
     * @param player The player to reset.
     */
    public static void reset(Player player) {
        player.setFlying(false);
        player.setAllowFlight(false);
        player.setGameMode(GameMode.SURVIVAL);
        player.setLevel(0);
        player.setExp(0);
        player.setTotalExperience(0);
        player.setFoodLevel(20);
        player.setHealth(20);
        player.getInventory().clear();
    }

    /**
     * Clears the chat for the given player by sending empty messages.
     *
     * @param player The player whose chat will be cleared.
     */
    public static void clearChat(Player player) {
        for (int i = 0; i < 100; i++) {
            player.sendMessage("");
        }
    }

    /**
     * Hides the target player from all other online players.
     *
     * @param plugin The plugin instance.
     * @param target The player to hide.
     */
    public static void hide(Plugin plugin, Player target) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.hidePlayer(plugin, target);
        }
    }

    /**
     * Shows the target player to all other online players.
     *
     * @param plugin The plugin instance.
     * @param target The player to show.
     */
    public static void show(Plugin plugin, Player target) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.showPlayer(plugin, target);
        }
    }

    /**
     * Fully heals the given player and restores various attributes.
     *
     * @param player The player to heal.
     */
    public static void heal(Player player) {
        player.setSaturation(20.0f);
        player.setHealth(player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue());
        player.setArrowsInBody(0);
        player.setFoodLevel(20);
    }

    /**
     * Sends a colored message to the given player.
     *
     * @param player The player to send the message to.
     * @param text   The text of the message.
     */
    public static void message(Player player, String text) {
        player.sendMessage(
                ColorUtils.color(
                        text
                )
        );
    }

    /**
     * Sends a title and subtitle with customizable timings to the given player.
     *
     * @param player    The player to send the title to.
     * @param title     The title text.
     * @param subtitle  The subtitle text.
     * @param fadeIn    Time in ticks for title fade-in.
     * @param stay      Time in ticks for title to stay.
     * @param fadeOut   Time in ticks for title fade-out.
     */
    public static void title(Player player, String title, String subtitle, int fadeIn, int stay, int fadeOut) {
        player.sendTitle(
                ColorUtils.color(title),
                ColorUtils.color(subtitle),
                fadeIn,
                stay,
                fadeOut
        );
    }

    /**
     * Broadcasts a title and subtitle with customizable timings to all online players.
     *
     * @param title     The title text.
     * @param subtitle  The subtitle text.
     * @param fadeIn    Time in ticks for title fade-in.
     * @param stay      Time in ticks for title to stay.
     * @param fadeOut   Time in ticks for title fade-out.
     */
    public static void broadcastTitle(String title, String subtitle, int fadeIn, int stay, int fadeOut) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.sendTitle(
                    ColorUtils.color(title),
                    ColorUtils.color(subtitle),
                    fadeIn,
                    stay,
                    fadeOut
            );
        }
    }
}