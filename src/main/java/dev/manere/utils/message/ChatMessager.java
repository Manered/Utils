package dev.manere.utils.message;

import dev.manere.utils.server.ServerUtils;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;

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
    public ChatMessager(Player player) {
        this.player = player;
    }

    /**
     * Static factory method for creating a ChatMessager.
     *
     * @param player The player to send messages to.
     * @return An instance of ChatMessager.
     */
    public static ChatMessager of(Player player) {
        return new ChatMessager(player);
    }

    /**
     * Sends a colored message to the player's chat box.
     *
     * @param text The message to be sent.
     */
    public void send(Component text) {
        player.sendMessage(text);
    }

    /**
     * Broadcasts a colored message to all online players' chat boxes.
     *
     * @param text The message to be broadcast.
     */
    public void broadcast(Component text) {
        ServerUtils
                .online()
                .forEach(to -> to
                        .sendMessage(text));
    }
}
