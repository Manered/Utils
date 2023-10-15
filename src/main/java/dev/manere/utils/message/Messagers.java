package dev.manere.utils.message;

import org.bukkit.entity.Player;

/**
 * Utility class for obtaining instances of message senders.
 */
public class Messagers {

    /**
     * Creates an instance of ActionBarMessager for a specific player.
     *
     * @param player The player to send messages to.
     * @return An instance of ActionBarMessager.
     */
    public static ActionBarMessager actionBar(Player player) {
        return new ActionBarMessager(player);
    }

    /**
     * Creates an instance of ChatMessager for a specific player.
     *
     * @param player The player to send messages to.
     * @return An instance of ChatMessager.
     */
    public static ChatMessager chat(Player player) {
        return new ChatMessager(player);
    }
}
