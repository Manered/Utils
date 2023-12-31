package dev.manere.utils.command.impl.suggestions;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a suggestion in a command.
 * Suggestions are tab completions.
 * <P></P>
 * For now, there is only one working non-factory/static method: text().
 * It returns the actual completion value.
 * <P></P>
 * There is no way to implement tooltip and color yet until we make a switch
 * from using Bukkit command system to Brigadier or an API like cloud.
 * That would defeat the purpose of this entire Command API sadly, so I think eventually I will
 * start working with Brigadier to provide completions.
 * <P></P>
 * There is a currently experimental brigadier (commodore specifically)
 * completion system provided when registering your command.
 */
public class Suggestion {
    private final String text;

    /**
     * Default tooltip is an empty Component
     * due to not being implemented yet
     */
    private final Component tooltip = Component.empty();

    /**
     * Default color is the vanilla non-brigadier completion color
     * (AQUA, &b) due to not being implemented yet
     */
    private final NamedTextColor color = NamedTextColor.AQUA;

    private Suggestion(@NotNull String text) {
        this.text = text;
    }

    private Suggestion(@NotNull String text, @NotNull Component tooltip) {
        throw new UnsupportedOperationException();
    }

    private Suggestion(@NotNull String text, @NotNull Component tooltip, @NotNull NamedTextColor color) {
        throw new UnsupportedOperationException();
    }

    /**
     * Constructs a suggestion with the given text.
     * @param text The text of the suggestion.
     * @return The newly-constructed Suggestion.
     */
    public static @NotNull Suggestion text(@NotNull String text) {
        return new Suggestion(text);
    }

    /**
     * Constructs a suggestion with the given number converted to text.
     * @param number The value of the suggestion.
     * @return The newly-constructed Suggestion.
     */
    public static @NotNull Suggestion number(int number) {
        return Suggestion.text(String.valueOf(number));
    }

    /**
     * Gets the text of the suggestion.
     *
     * @return The text of the suggestion.
     */
    public @NotNull String text() {
        return text;
    }

    /**
     * Gets the tooltip component of the suggestion.
     *
     * @return The tooltip component of the suggestion.
     * @apiNote <strong>THIS IS CURRENTLY NOT IMPLEMENTED.</strong>
     *          This will default to an empty Component.
     */
    public @NotNull Component tooltip() {
        return Component.empty();
    }

    /**
     * Gets the color of the suggestion.
     *
     * @return The color of the suggestion.
     * @apiNote <strong>THIS IS CURRENTLY NOT IMPLEMENTED.</strong>
     *          This will default to the vanilla non-brigadier completion color
     *          (AQUA, &b)
     */
    public @NotNull NamedTextColor color() {
        return NamedTextColor.AQUA;
    }
}