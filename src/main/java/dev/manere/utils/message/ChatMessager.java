package dev.manere.utils.message;

import dev.manere.utils.server.ServerUtils;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

/**
 * Utility class for sending messages to players in the chat.
 */
public class ChatMessager {
    private final Player player;

    /**
     * Constructor for ChatMessager.
     *
     * @param player The player to send messages to.
     */
    public ChatMessager(@NotNull Player player) {
        this.player = player;
    }

    /**
     * Static factory method for creating a ChatMessager.
     *
     * @param player The player to send messages to.
     * @return An instance of ChatMessager.
     */
    public static @NotNull ChatMessager of(@NotNull Player player) {
        return new ChatMessager(player);
    }

    /**
     * Sends a colored message to the player's chat box.
     *
     * @param text The message to be sent.
     */
    public void send(@NotNull Component text) {
        player.sendMessage(text);
    }

    /**
     * Broadcasts a colored message to all online players' chat boxes.
     *
     * @param text The message to be broadcast.
     */
    public void broadcast(@NotNull Component text) {
        ServerUtils.online().forEach(to -> to.sendMessage(text));
    }
}
