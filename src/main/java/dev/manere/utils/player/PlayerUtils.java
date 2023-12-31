package dev.manere.utils.player;

import dev.manere.utils.item.ItemBuilder;
import dev.manere.utils.library.Utils;
import dev.manere.utils.message.ActionBarMessager;
import dev.manere.utils.message.ChatMessager;
import dev.manere.utils.message.Messagers;
import dev.manere.utils.text.color.TextStyle;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.time.Duration;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.IntStream;

/**
 * A utility class for common operations related to players in Bukkit.
 */
public class PlayerUtils {
    /**
     * Resets various attributes and states of the given player to default values.
     *
     * @param player The player to reset.
     */
    public static void reset(@NotNull Player player) {
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
    public static void clearChat(@NotNull Player player, int count) {
        IntStream.range(0, count)
                .mapToObj(i -> "")
                .forEach(player::sendMessage);
    }

    /**
     * Hides the target player from all other online players.
     *
     * @param target The player to hide.
     */
    public static void hide(@NotNull Player target) {
        Utils.plugin()
                .getServer()
                .getOnlinePlayers()
                .forEach(player -> player.hidePlayer(
                        Utils.plugin(),
                        target));
    }

    /**
     * Shows the target player to all other online players.
     *
     * @param target The player to show.
     */
    public static void show(@NotNull Player target) {
        Bukkit.getOnlinePlayers()
                .forEach(player -> player.showPlayer(
                        Utils.plugin(),
                        target));
    }

    /**
     * Fully heals the given player and restores various attributes.
     *
     * @param player The player to heal.
     */
    public static void heal(@NotNull Player player) {
        player.setSaturation(20.0f);
        player.setHealth(Objects.requireNonNull(player.getAttribute(Attribute.GENERIC_MAX_HEALTH)).getValue());
        player.setArrowsInBody(0);
        player.setFoodLevel(20);
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
    public static void title(@NotNull Player player, @NotNull Component title, @NotNull Component subtitle, @NotNull Duration fadeIn, @NotNull Duration stay, @NotNull Duration fadeOut) {
        Audience audience = Audience.audience(player);

        audience.showTitle(Title.title(title, subtitle, Title.Times.times(fadeIn, stay, fadeOut)));
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
    public static void broadcastTitle(@NotNull Component title, @NotNull Component subtitle, @NotNull Duration fadeIn, @NotNull Duration stay, @NotNull Duration fadeOut) {
        Bukkit.getOnlinePlayers()
                .forEach(player -> title(player, title, subtitle, fadeIn, stay, fadeOut));
    }

    /**
     * Teleports the player to the specified location.
     *
     * @param player    The player to teleport.
     * @param location  The target location.
     */
    public static void teleport(@NotNull Player player, @NotNull Location location) {
        player.teleport(location);
    }

    /**
     * Teleports the player to the location of the target player.
     *
     * @param player    The player to teleport.
     * @param target    The target player.
     */
    public static void teleport(@NotNull Player player, @NotNull Player target) {
        player.teleport(target.getLocation());
    }

    /**
     * Gives the player the specified item.
     *
     * @param player    The player to give the item to.
     * @param item      The item to give.
     */
    public static void giveItem(@NotNull Player player, @NotNull ItemBuilder item) {
        player.getInventory().addItem(item.build());
    }

    /**
     * Clears the inventory of the specified player.
     *
     * @param player    The player whose inventory will be cleared.
     */
    public static void clearInventory(@NotNull Player player) {
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
    public static void applyPotionEffect(@NotNull Player player, @NotNull PotionEffectType type, int duration, int amplifier) {
        player.addPotionEffect(new PotionEffect(type, duration, amplifier));
    }

    /**
     * Removes a potion effect from the specified player.
     *
     * @param player    The player to remove the potion effect from.
     * @param type      The type of potion effect to remove.
     */
    public static void removePotionEffect(@NotNull Player player, @NotNull PotionEffectType type) {
        player.removePotionEffect(type);
    }

    /**
     * Clears all active potion effects from the specified player.
     *
     * @param player    The player whose potion effects will be cleared.
     */
    public static void clearPotionEffects(@NotNull Player player) {
        player.getActivePotionEffects()
                .stream()
                .map(PotionEffect::getType)
                .forEach(player::removePotionEffect);
    }

    /**
     * Removes the specified item from the player's inventory.
     *
     * @param player    The player from whose inventory the item will be removed.
     * @param item      The item to be removed.
     */
    public static void removeItem(@NotNull Player player, @NotNull ItemBuilder item) {
        player.getInventory().removeItem(item.build());
    }

    /**
     * Sets the game mode of a list of players.
     *
     * @param gamemode  The game mode to be set.
     * @param players   The list of players whose game mode will be changed.
     */
    public static void gamemodeOfPlayers(@NotNull GameMode gamemode, @NotNull List<Player> players) {
        players.forEach(player -> player.setGameMode(gamemode));
    }

    /**
     * Gets the online player associated with the given Player object.
     *
     * @param player The Player object representing the online player.
     * @return The online player associated with the provided Player object.
     */
    public static @NotNull Player player(@NotNull Player player) {
        return player;
    }

    /**
     * Gets the online player with the specified UUID.
     *
     * @param uuid The UUID of the player.
     * @return The online player with the specified UUID, or null if not found.
     */
    public static @Nullable Player player(@NotNull UUID uuid) {
        return Utils.plugin().getServer().getPlayer(uuid);
    }

    /**
     * Gets the online player with the given player name.
     *
     * @param playerName The name of the player.
     * @return The online player with the provided name, or null if not found.
     */
    public static @Nullable Player player(@NotNull String playerName) {
        return Utils.plugin().getServer().getPlayer(playerName);
    }

    /**
     * Gets the offline player with the specified UUID.
     *
     * @param uuid The UUID of the player.
     * @return The offline player with the specified UUID.
     */
    public static @NotNull OfflinePlayer offline(@NotNull UUID uuid) {
        return Utils.plugin().getServer().getOfflinePlayer(uuid);
    }

    /**
     * Sends a message to a player.
     *
     * @param player The player that will receive a message.
     * @param text The message to send.
     */
    public static void chat(@NotNull Player player, @NotNull String text) {
        Messagers.chat(player).send(TextStyle.component(text));
    }

    /**
     * Sends a message to a player.
     *
     * @param player The player that will receive a message.
     * @param text The message to send.
     */
    public static void chat(@NotNull Player player, @NotNull Component text) {
        Messagers.chat(player).send(text);
    }

    /**
     * Retrieves the ChatMessager of the given player.
     *
     * @param player The player of the ChatMessager that will be returned.
     * @return The ChatMessager of the given player.
     */
    public static @NotNull ChatMessager chatMessager(@NotNull Player player) {
        return Messagers.chat(player);
    }

    /**
     * Sends an actionbar message to a player.
     *
     * @param player The player that will receive a message.
     * @param text The message to send.
     */
    public static void actionBar(@NotNull Player player, @NotNull String text) {
        Messagers.actionBar(player).send(TextStyle.component(text));
    }

    /**
     * Sends an actionbar message to a player.
     *
     * @param player The player that will receive a message.
     * @param text The message to send.
     */
    public static void actionBar(@NotNull Player player, @NotNull Component text) {
        Messagers.actionBar(player).send(text);
    }

    /**
     * Retrieves the ActionBarMessager of the given player.
     *
     * @param player The player of the ActionBarMessager that will be returned.
     * @return The ActionBarMessager of the given player.
     */
    public static @NotNull ActionBarMessager actionBarMessager(@NotNull Player player) {
        return Messagers.actionBar(player);
    }
}
