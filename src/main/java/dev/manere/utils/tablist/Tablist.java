package dev.manere.utils.tablist;

import dev.manere.utils.text.color.TextStyle;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.JoinConfiguration;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * The Tablist class provides utility methods for managing and updating player tablists.
 */
public class Tablist {
    private static final List<Component> header = new ArrayList<>();
    private static final List<Component> footer = new ArrayList<>();

    /**
     * Sets the header of the tablist for all players.
     *
     * @param header The list of components to set as the header.
     */
    public static void header(List<Component> header) {
        Tablist.header.clear();
        Tablist.header.addAll(header);
    }

    /**
     * Sets the header of the tablist for all players.
     *
     * @param header The components to set as the header.
     */
    public static void header(Component... header) {
        header(Arrays.asList(header));
    }

    /**
     * Sets the header of the tablist for all players.
     *
     * @param header The strings to set as the header, with optional text styling.
     */
    public static void header(String... header) {
        header(TextStyle.style(header));
    }

    /**
     * Sets the footer of the tablist for all players.
     *
     * @param footer The list of components to set as the footer.
     */
    public static void footer(List<Component> footer) {
        Tablist.footer.clear();
        Tablist.footer.addAll(footer);
    }

    /**
     * Sets the footer of the tablist for all players.
     *
     * @param footer The components to set as the footer.
     */
    public static void footer(Component... footer) {
        footer(Arrays.asList(footer));
    }

    /**
     * Sets the footer of the tablist for all players.
     *
     * @param footer The strings to set as the footer, with optional text styling.
     */
    public static void footer(String... footer) {
        footer(TextStyle.style(footer));
    }

    /**
     * Updates the tablist for a specific player.
     *
     * @param player The player whose tablist needs to be updated.
     */
    public static void update(Player player) {
        Component joinedHeader = Component.empty();
        Component joinedFooter = Component.empty();

        if (!header.isEmpty()) {
            joinedHeader = Component.join(JoinConfiguration.newlines(), header);
        }

        if (!footer.isEmpty()) {
            joinedFooter = Component.join(JoinConfiguration.newlines(), footer);
        }

        player.sendPlayerListHeaderAndFooter(joinedHeader, joinedFooter);
    }

    /**
     * Updates the tablist for a list of players.
     *
     * @param players The list of players whose tablists need to be updated.
     */
    public static void update(List<Player> players) {
        for (Player player : players) {
            Tablist.update(player);
        }
    }

    /**
     * Updates the tablist for an array of players.
     *
     * @param players The array of players whose tablists need to be updated.
     */
    public static void update(Player... players) {
        for (Player player : players) {
            Tablist.update(player);
        }
    }
}
