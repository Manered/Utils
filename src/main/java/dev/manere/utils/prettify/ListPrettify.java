package dev.manere.utils.prettify;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Utility class related to converting a list to a string.
 * As an example:
 * <p></p>
 * From: ["a", "b", "c", "d"]
 * <p>
 * To: a, b, c, d
 */
public class ListPrettify {

    /**
     * Converts a List of Strings into a single comma-separated String.
     *
     * @param input A List of Strings to be prettified.
     * @return A String containing the elements of the input List separated by commas.
     */
    public static @NotNull String strings(List<String> input) {
        return input.toString()
                .replaceAll("\\[", "")
                .replaceAll("]", "");
    }

    /**
     * Converts a List of Players into a single comma-separated String of player names.
     *
     * @param input A List of Player objects to be prettified.
     * @return A String containing the names of the players in the input List separated by commas.
     */
    public static @NotNull String players(List<Player> input) {
        List<String> inputToString = input.stream()
                .map(Player::getName)
                .collect(Collectors.toList());

        return strings(inputToString);
    }
}
