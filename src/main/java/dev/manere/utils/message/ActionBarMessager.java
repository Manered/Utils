package dev.manere.utils.message;

import dev.manere.utils.scheduler.Schedulers;
import dev.manere.utils.server.ServerUtils;
import dev.manere.utils.text.color.ColorUtils;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;

/**
 * Utility class for sending messages to players in the ActionBar.
 */
public class ActionBarMessager {
    private final Player player;

    /**
     * Constructor for ActionBarMessager.
     *
     * @param player The player to send messages to.
     */
    public ActionBarMessager(Player player) {
        this.player = player;
    }

    /**
     * Static factory method for creating an ActionBarMessager.
     *
     * @param player The player to send messages to.
     * @return An instance of ActionBarMessager.
     */
    public static ActionBarMessager of(Player player) {
        return new ActionBarMessager(player);
    }

    /**
     * Sends a colored message to the player's ActionBar.
     *
     * @param message The message to be sent.
     */
    public void send(Component message) {
        player.sendActionBar(message);
    }

    /**
     * Clears the player's ActionBar by sending an empty message.
     */
    public void clear() {
        send(ColorUtils.component(" "));
    }

    /**
     * Sends a message to the player's ActionBar with a delay and repeating interval.
     *
     * @param text  The message to be sent.
     * @param after The delay before the first message is sent (in ticks).
     * @param every The interval between subsequent messages (in ticks).
     */
    public void send(Component text, long after, long every) {
        Schedulers.async()
                .repeating(task -> send(text), after, every);
    }

    /**
     * Broadcasts a colored message to all online players' ActionBars.
     *
     * @param text The message to be broadcast.
     */
    public void broadcast(Component text) {
        ServerUtils
                .online()
                .forEach(to -> to
                        .sendActionBar(text));
    }
}
