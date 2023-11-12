package dev.manere.utils.text;

import dev.manere.utils.text.color.TextStyle;
import net.kyori.adventure.text.Component;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Represents a simple text utility with formatting options.
 */
public class Text {
    private final String string;

    /**
     * Constructs a new Text instance
     * @param string the string to use.
     */
    public Text(String string) {
        this.string = string;
    }

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
    public String styledString() {
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
     * @return The unformatted string.
     */
    @Override
    public String toString() {
        return string;
    }
}
