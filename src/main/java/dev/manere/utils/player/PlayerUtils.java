package dev.manere.utils.player;

import dev.manere.utils.item.ItemBuilder;
import dev.manere.utils.library.Utils;
import dev.manere.utils.text.color.ColorUtils;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.List;
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
     * @param count  The number of empty messages to send.
     */
    public static void clearChat(Player player, int count) {
        for (int i = 0; i < count; i++) {
            player.sendMessage("");
        }
    }

    /**
     * Hides the target player from all other online players.
     *
     * @param target The player to hide.
     */
    public static void hide(Player target) {
        for (Player player : Utils.getPlugin().getServer().getOnlinePlayers()) {
            player.hidePlayer(Utils.getPlugin(), target);
        }
    }

    /**
     * Shows the target player to all other online players.
     *
     * @param target The player to show.
     */
    public static void show(Player target) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.showPlayer(Utils.getPlugin(), target);
        }
    }

    /**
     * Fully heals the given player and restores various attributes.
     *
     * @param player The player to heal.
     */
    public static void heal(Player player) {
        player.setSaturation(20.0f);
        player.setHealth(Objects.requireNonNull(player.getAttribute(Attribute.GENERIC_MAX_HEALTH)).getValue());
        player.setArrowsInBody(0);
        player.setFoodLevel(20);
    }

    /**
     * Sends a colored message to the given player.
     *
     * @param player The player to send the message to.
     * @param text   The text of the message.
     */
    public static void chat(Player player, String text) {
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

    /**
     * Teleports the player to the specified location.
     *
     * @param player    The player to teleport.
     * @param location  The target location.
     */
    public static void teleport(Player player, Location location) {
        player.teleport(location);
    }

    /**
     * Teleports the player to the location of the target player.
     *
     * @param player    The player to teleport.
     * @param target    The target player.
     */
    public static void teleport(Player player, Player target) {
        player.teleport(target.getLocation());
    }

    /**
     * Gives the player the specified item.
     *
     * @param player    The player to give the item to.
     * @param item      The item to give.
     */
    public static void giveItem(Player player, ItemBuilder item) {
        player.getInventory().addItem(item.build());
    }

    /**
     * Clears the inventory of the specified player.
     *
     * @param player    The player whose inventory will be cleared.
     */
    public static void clearInventory(Player player) {
        player.getInventory().clear();
    }

    /**
     * Applies a potion effect to the specified player.
     *
     * @param player    The player to apply the potion effect to.
     * @param type      The type of potion effect.
     * @param duration  The duration of the potion effect in ticks.
     * @param amplifier The amplifier of the potion effect.
     */
    public static void applyPotionEffect(Player player, PotionEffectType type, int duration, int amplifier) {
        player.addPotionEffect(new PotionEffect(type, duration, amplifier));
    }

    /**
     * Removes a potion effect from the specified player.
     *
     * @param player    The player to remove the potion effect from.
     * @param type      The type of potion effect to remove.
     */
    public static void removePotionEffect(Player player, PotionEffectType type) {
        player.removePotionEffect(type);
    }

    /**
     * Clears all active potion effects from the specified player.
     *
     * @param player    The player whose potion effects will be cleared.
     */
    public static void clearPotionEffects(Player player) {
        for (PotionEffect effect : player.getActivePotionEffects()) {
            player.removePotionEffect(effect.getType());
        }
    }

    /**
     * Removes the specified item from the player's inventory.
     *
     * @param player    The player from whose inventory the item will be removed.
     * @param item      The item to be removed.
     */
    public static void removeItem(Player player, ItemBuilder item) {
        player.getInventory().removeItem(item.build());
    }

    /**
     * Sets the game mode of a list of players.
     *
     * @param gamemode  The game mode to be set.
     * @param players   The list of players whose game mode will be changed.
     */
    public static void setGamemodeOfPlayers(GameMode gamemode, List<Player> players) {
        for (Player player : players) {
            player.setGameMode(gamemode);
        }
    }

    /**
     * Gets the online player associated with the given Player object.
     *
     * @param player The Player object representing the online player.
     * @return The online player associated with the provided Player object.
     */
    public static Player getPlayer(Player player) {
        return player;
    }

    /**
     * Gets the online player with the specified UUID.
     *
     * @param uuid The UUID of the player.
     * @return The online player with the specified UUID, or null if not found.
     */
    public static Player getPlayer(UUID uuid) {
        return Utils.getPlugin().getServer().getPlayer(uuid);
    }

    /**
     * Gets the online player with the specified UUID string.
     *
     * @param uuid The UUID string of the player.
     * @return The online player with the specified UUID, or null if not found.
     * @throws IllegalArgumentException If the UUID string is invalid.
     */
    public static Player getPlayer(String uuid) {
        return Utils.getPlugin().getServer().getPlayer(UUID.fromString(uuid));
    }

    /**
     * Gets the online player with the given player name.
     *
     * @param playerName The name of the player.
     * @return The online player with the provided name, or null if not found.
     */
    public static Player getPlayerFromName(String playerName) {
        return Utils.getPlugin().getServer().getPlayer(playerName);
    }

    /**
     * Gets the offline player with the specified UUID.
     *
     * @param uuid The UUID of the player.
     * @return The offline player with the specified UUID.
     */
    public static OfflinePlayer getOfflinePlayer(UUID uuid) {
        return Utils.getPlugin().getServer().getOfflinePlayer(uuid);
    }

    /**
     * Gets the offline player with the specified UUID string.
     *
     * @param uuid The UUID string of the player.
     * @return The offline player with the specified UUID.
     * @throws IllegalArgumentException If the UUID string is invalid.
     */
    public static OfflinePlayer getOfflinePlayer(String uuid) {
        return Utils.getPlugin().getServer().getOfflinePlayer(UUID.fromString(uuid));
    }

    /**
     * Gets the offline player with the given player name.
     * <p>{@code WARNING}: This is deprecated. Please avoid using this!</p>
     *
     * @param playerName The name of the player.
     * @return The offline player with the provided name.
     */
    public static OfflinePlayer getOfflinePlayerFromName(String playerName) {
        return Utils.getPlugin().getServer().getOfflinePlayer(playerName);
    }
}
