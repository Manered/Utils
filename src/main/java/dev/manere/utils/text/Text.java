package dev.manere.utils.text;

import dev.manere.utils.misc.Storable;
import dev.manere.utils.text.color.TextStyle;
import net.kyori.adventure.text.Component;

/**
 * Represents a simple text utility with formatting options.
 * @param string The text string to be encapsulated.
 */
public record Text(String string) implements Storable<Text> {
    /**
     * Creates a new Text instance with the specified string.
     *
     * @param string The text string to be encapsulated.
     * @return A new Text instance.
     */
    public static Text text(String string) {
        return new Text(string);
    }

    /**
     * Converts the text to a Kyori Adventure Component with the specified text style.
     *
     * @return A Kyori Adventure Component representing the text with applied style.
     */
    public Component component() {
        return TextStyle.color(string);
    }

    /**
     * Converts the text to a legacy string with the specified text style.
     *
     * @return A legacy string representing the text with applied style.
     */
    public String colored() {
        return TextStyle.legacy(string);
    }

    /**
     * Converts the text to a normal string that isn't colored, etc.
     *
     * @return A normal string that isn't colored, etc.
     */
    public String string() {
        return string;
    }

    /**
     * Used whenever you store a Text object in a configuration file.
     *
     * @return The unformatted string.
     */
    @Override
    public String toString() {
        return string;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String serialize() {
        return string;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Text deserialize(String serialized) {
        return Text.text(serialized);
    }
}
